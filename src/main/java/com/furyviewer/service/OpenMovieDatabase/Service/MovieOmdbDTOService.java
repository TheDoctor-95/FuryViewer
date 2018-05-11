package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.domain.Movie;
import com.furyviewer.repository.MovieRepository;

import com.furyviewer.service.OpenMovieDatabase.Repository.MovieOmdbDTORepository;
import com.furyviewer.service.TheMovieDB.Service.FindTmdbDTOService;
import com.furyviewer.service.TheMovieDB.Service.MovieTmdbDTOService;
import com.furyviewer.service.TheMovieDB.Service.TrailerTmdbDTOService;
import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import com.furyviewer.service.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Servicio encargado de recuperar informacion de una Movie desde MovieOmdbDTORepository y la convierte al
 * formato FuryViewer.
 *
 * @author IFriedkin
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
    private StringApiCorrectorService stringApiCorrectorService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private TrailerTmdbDTOService trailerTmdbDTOService;

    @Autowired
    private MovieTmdbDTOService movieTmdbDTOService;

    /**
     * Devuelve la informacion de una movie a partir del titulo en el formato proporcionado por OpenMovieDataBase.
     *
     * @param title String | Titulo de la movie.
     * @return MovieOmdbDTO | Informacion con el formato proporcionado por la API.
     */
    public MovieOmdbDTO getMovieByName(String title) {
        MovieOmdbDTO movie = new MovieOmdbDTO();
        Call<MovieOmdbDTO> callMovie = apiService.getMovie(apikey, title);

        try {
            movie = callMovie.execute().body();
            System.out.println(movie);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movie;
    }

    /**
     * Devuelve la informacion de una movie a partir del id de IMDB en el formato proporcionado por OpenMovieDataBase.
     *
     * @param ImdbId String | id de IMDB.
     * @return MovieOmdbDTO | Informacion con el formato proporcionado por la API.
     */
    public MovieOmdbDTO getMovieByImdbId(String ImdbId) {
        MovieOmdbDTO movie = null;
        Call<MovieOmdbDTO> callMovie = apiService.getMovieByImdbId(apikey, ImdbId);

        try {
            Response<MovieOmdbDTO> response = callMovie.execute();

            if (response.isSuccessful()) {
                movie = new MovieOmdbDTO();
                movie = response.body();
                System.out.println(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movie;
    }

    /**
     * Devuelve una Movie a partir del titulo existente en la base de datos o en caso de no existir hace una peticion a
     * la api.
     *
     * @param title String | Titulo de la movie.
     * @return Movie | Contiene la informacion de una movie en el formato FuryViewer.
     */
    @Transactional
    public Movie importMovieByName(String title) {
        MovieOmdbDTO movieOmdbDTO = getMovieByName(title);

        Optional<Movie> mdb = movieRepository.findMovieByImdbIdExternalApi(movieOmdbDTO.getImdbID());

        if (mdb.isPresent()) {
            return mdb.get();
        }

        Movie m = new Movie();

        //Comprobamos que la API nos devuelve información.
        if (movieOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            m = importMovie(movieOmdbDTO);
        } else {
            System.out.println("==================\nBúsqueda sin resultados\n==================");
        }

        return m;
    }

    /**
     * Devuelve una Movie a partir del id de IMDB existente en la base de datos o en caso de no existir hace una
     * peticion a la api.
     *
     * @param imdbId String | Titulo de la movie.
     * @return Movie | Contiene la informacion de una movie en el formato FuryViewer.
     */
    @Transactional
    public Movie importMovieByImdbId(String imdbId) {
        MovieOmdbDTO movieOmdbDTO = getMovieByImdbId(imdbId);

        Optional<Movie> mdb = movieRepository.findMovieByImdbIdExternalApi(imdbId);

        Movie m = new Movie();

        //En caso de tener la movie en la base de datos renovamos la informacion dde la release date del dvd.
        if (mdb.isPresent()) {
            m = mdb.get();
            m.setDvd_release(dateConversorService.releseDateOMDB(movieOmdbDTO.getDVD()));
            m = movieRepository.save(m);

            return m;
        }

        //Comprobamos que la API nos devuelve información.
        if (movieOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            m = importMovie(movieOmdbDTO);
        } else {
            System.out.println("==================\nBúsqueda sin resultados\n==================");
        }

        return m;
    }

    /**
     * Convierte la informacion de una movie de OMDB al formato de FuryViewer.
     *
     * @param movieOmdbDTO MovieOmdbDTO | Informacion de la Movie propocionada por la api.
     * @return Movie | Contiene la informacion de una movie en el formato FuryViewer.
     */
    public Movie importMovie(MovieOmdbDTO movieOmdbDTO) {
        Movie m = new Movie();
        m.setName(movieOmdbDTO.getTitle());

        if(!movieOmdbDTO.getRuntime().equalsIgnoreCase("N/A")) {
            m.setDuration(Double.parseDouble(movieOmdbDTO.getRuntime().split(" ")[0]));
        } else {
            m.setDuration(-1.0);
        }

        m.setDescription(stringApiCorrectorService.eraserNA(movieOmdbDTO.getPlot()));
        m.setImdbIdExternalApi(stringApiCorrectorService.eraserNA(movieOmdbDTO.getImdbID()));

        String imgURL = stringApiCorrectorService.eraserNA(movieOmdbDTO.getPoster());
        if (!movieOmdbDTO.getPoster().equalsIgnoreCase("N/A")) {
            m.setImgUrl(imgURL);
        } else {
            m.setImgUrl("https://image.ibb.co/czS1CH/noimage.jpg");
        }

        m.setAwards(stringApiCorrectorService.eraserNA(movieOmdbDTO.getAwards()));

        if (!movieOmdbDTO.getReleased().equalsIgnoreCase("N/A"))
            m.setReleaseDate(dateConversorService.releseDateOMDB(movieOmdbDTO.getReleased()));
        else
            m.releaseDate(LocalDate.of(2005,11,5));
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

        movieTmdbDTOService.importActors(m);

        return m;
    }
}
