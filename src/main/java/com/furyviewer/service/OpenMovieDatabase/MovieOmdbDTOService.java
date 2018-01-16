package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import retrofit2.Call;

import java.io.IOException;

public class MovieOmdbDTOService {

    public static final String apikey = "66f5a28";
    static MovieOmdbDTORepository apiService = MovieOmdbDTORepository.retrofit.create(MovieOmdbDTORepository.class);

    public static MovieOmdbDTO getMovie(String title) {
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
}
