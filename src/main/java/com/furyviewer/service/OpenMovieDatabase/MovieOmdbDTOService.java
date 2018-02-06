package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Movie;
import com.furyviewer.repository.MovieRepository;

import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import com.furyviewer.service.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

@Service
public class MovieOmdbDTOService {

    private static final String apikey = "eb62550d";

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

    private static MovieOmdbDTORepository apiService = MovieOmdbDTORepository.retrofit.create(MovieOmdbDTORepository.class);

    /**
     * Devuelve la información de una movie en el formato proporcionado por OpenMovieDataBase.
     * @param title String | Título de la movie.
     * @return MovieOmdbDTO | Información con el formato proporcionado por la API.
     */
    public MovieOmdbDTO getMovie(String title) {
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
     * Convierte la información de una movie de OMDB al formato de información de FuryViewer.
     * @param title String | Título de la movie.
     * @return Movie | Contiene la información de una movie en el formato FuryViewer.
     */
    @Transactional
    public Movie importMovie(String title){

        Optional<Movie> mdb = movieRepository.findByName(title);
        if(mdb.isPresent()){
            return mdb.get();
        }

        MovieOmdbDTO movieOmdbDTO = getMovie(title);

        Movie m = new Movie();

        //Comprobamos que la API nos devuelve información.
        if (movieOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            m.setName(movieOmdbDTO.getTitle());

            m.setDuration(Double.parseDouble(movieOmdbDTO.getRuntime().split(" ")[0]));

            m.setDescription(naEraserService.eraserNA(movieOmdbDTO.getPlot()));
            m.setImdbIdExternalApi(naEraserService.eraserNA(movieOmdbDTO.getImdbID()));
            m.setImgUrl(naEraserService.eraserNA(movieOmdbDTO.getPoster()));
            m.setAwards(naEraserService.eraserNA(movieOmdbDTO.getAwards()));

            m.setReleaseDate(dateConversorService.releseDateOMDB(movieOmdbDTO.getReleased()));
            m.setCountry(countryService.importCountry(movieOmdbDTO.getCountry()));

            movieRepository.save(m);

            m.setCompany(companyService.importCompany(movieOmdbDTO.getProduction()));
            m.setGenres(genreService.importGenre(movieOmdbDTO.getGenre()));

            movieRepository.save(m);

            m.setActorMains(artistService.importActors(movieOmdbDTO.getActors()));
            m.setDirector(artistService.importDirector(movieOmdbDTO.getDirector()));
            m.setScriptwriter(artistService.importScripwriter(movieOmdbDTO.getWriter()));

            movieRepository.save(m);
        }
        return m;
    }
}
