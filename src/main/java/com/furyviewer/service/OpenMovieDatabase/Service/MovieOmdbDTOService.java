package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.domain.Movie;
import com.furyviewer.repository.MovieRepository;

import com.furyviewer.service.OpenMovieDatabase.Repository.MovieOmdbDTORepository;
import com.furyviewer.service.TheMovieDB.Service.TrailerTmdbDTOService;
import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import com.furyviewer.service.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

/**
 * Servicio encargado de recuperar informacion de una Movie desde MovieOmdbDTORepository y la convierte al
 * formato FuryViewer.
 * @author TheDoctor-95
 * @see com.furyviewer.service.OpenMovieDatabase.Repository.MovieOmdbDTORepository
 */
@Service
public class MovieOmdbDTOService {
    /**
     * Key proporcionada por la api de OpenMovieDataBase para poder hacer peticiones.
     */
    private static final String apikey = "eb62550d";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private static MovieOmdbDTORepository apiService =
        MovieOmdbDTORepository.retrofit.create(MovieOmdbDTORepository.class);

    @Autowired
    private GenreService genreService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private NAEraserService naEraserService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private TrailerTmdbDTOService trailerTmdbDTOService;

    /**
     * Devuelve la informacion de una movie en el formato proporcionado por OpenMovieDataBase.
     * @param title String | Titulo de la movie.
     * @return MovieOmdbDTO | Informacion con el formato proporcionado por la API.
     */
    public MovieOmdbDTO getMovieByName(String title) {
        MovieOmdbDTO movie = new MovieOmdbDTO();
        Call<MovieOmdbDTO> callMovie = apiService.getMovie(apikey, title);

        try{
            movie = callMovie.execute().body();
            System.out.println(movie);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return movie;
    }

    /**
     * Devuelve una Movie existente en la base de datos o en caso de no existir hace una peticion a la api.
     * @param title String | Titulo de la movie.
     * @return Movie | Contiene la informacion de una movie en el formato FuryViewer.
     */
    @Transactional
    public Movie importMovieByName(String title){

        Optional<Movie> mdb = movieRepository.findByName(title);
        if(mdb.isPresent()){
            return mdb.get();
        }

        MovieOmdbDTO movieOmdbDTO = getMovieByName(title);

        Movie m = new Movie();

        //Comprobamos que la API nos devuelve información.
        if (movieOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            m = importMovie(movieOmdbDTO);
        }
        return m;
    }

    /**
     * Convierte la informacion de una movie de OMDB al formato de FuryViewer.
     * @param movieOmdbDTO MovieOmdbDTO | Informacion de la Movie propocionada por la api.
     * @return Movie | Contiene la informacion de una movie en el formato FuryViewer.
     */
    public Movie importMovie(MovieOmdbDTO movieOmdbDTO){
        Movie m = new Movie();
        m.setName(movieOmdbDTO.getTitle());

        m.setDuration(Double.parseDouble(movieOmdbDTO.getRuntime().split(" ")[0]));

        m.setDescription(naEraserService.eraserNA(movieOmdbDTO.getPlot()));
        m.setImdbIdExternalApi(naEraserService.eraserNA(movieOmdbDTO.getImdbID()));
        m.setImgUrl(naEraserService.eraserNA(movieOmdbDTO.getPoster()));
        m.setAwards(naEraserService.eraserNA(movieOmdbDTO.getAwards()));

        m.setReleaseDate(dateConversorService.releseDateOMDB(movieOmdbDTO.getReleased()));
        m.setCountry(countryService.importCountry(movieOmdbDTO.getCountry()));
        m.setDvd_release(dateConversorService.releseDateOMDB(movieOmdbDTO.getDVD()));
        movieRepository.save(m);

        m.setCompany(companyService.importCompany(movieOmdbDTO.getProduction()));
        m.setGenres(genreService.importGenre(movieOmdbDTO.getGenre()));

        movieRepository.save(m);

        m.setActorMains(artistService.importActors(movieOmdbDTO.getActors()));
        m.setDirector(artistService.importDirector(movieOmdbDTO.getDirector()));
        m.setScriptwriter(artistService.importScripwriter(movieOmdbDTO.getWriter()));

        m = movieRepository.save(m);

        marksService.markTransformationMovie(movieOmdbDTO.getRatings(), m);
        m = movieRepository.save(m);

        trailerTmdbDTOService.importMovieTrailer(m);
        return m;
    }
}
