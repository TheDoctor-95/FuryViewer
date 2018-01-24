package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.Genre;
import com.furyviewer.domain.Movie;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.GenreRepository;
import com.furyviewer.repository.MovieRepository;
import com.furyviewer.service.ArtistService;
import com.furyviewer.service.DateConversorService;
import com.furyviewer.service.GenreService;

import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

@Service
public class MovieOmdbDTOService {

    public static final String apikey = "66f5a28";

    @Autowired
    private GenreService genreService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DateConversorService dateConversorService;

    static MovieOmdbDTORepository apiService = MovieOmdbDTORepository.retrofit.create(MovieOmdbDTORepository.class);


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

    public Movie importMovie(String title){

        Optional<Movie> mdb = movieRepository.findByName(title);
        if(mdb.isPresent()){
            return mdb.get();
        }

        MovieOmdbDTO movieOmdbDTO = getMovie(title);

        Movie m = new Movie();

        m.setName(movieOmdbDTO.getTitle());
        m.setDescription(movieOmdbDTO.getPlot());

        //DATES


        m.setReleaseDate(dateConversorService.releseDateOMDB(movieOmdbDTO.getReleased()));

        //Generos


        m.setDuration(Double.parseDouble(movieOmdbDTO.getRuntime().split(" ")[0]));
        m.setImdbIdExternalApi(movieOmdbDTO.getImdbID());
        movieRepository.save(m);

        m.setGenres(genreService.importGenre(movieOmdbDTO.getGenre()));

        movieRepository.save(m);

         m.setActorMains(artistService.importActors(movieOmdbDTO.getActors()));

         m.setDirector(artistService.importDirector(movieOmdbDTO.getDirector()));

         m.setScriptwriter(artistService.importScripwriter(movieOmdbDTO.getWriter()));

        movieRepository.save(m);

        return m;

    }


}
