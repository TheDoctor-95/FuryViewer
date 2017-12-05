package com.furyviewer.service.MovieDatabase;

import com.furyviewer.domain.Movie;
import com.furyviewer.service.dto.MovieDatabase.Credits;
import com.furyviewer.service.dto.MovieDatabase.KeywordList;
import com.furyviewer.service.dto.MovieDatabase.MovieDTO;
import com.furyviewer.service.dto.MovieDatabase.MovieDTOList;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MovieDTOService {
    public static final String apiKey = "e9146e088c2bfd128d974ae6fe70bdf4";
    static MovieDTORepository apiService = MovieDTORepository.retrofit.create(MovieDTORepository.class);

    public static MovieDTO getMovie(int id){
        MovieDTO movie = null;
        Call<MovieDTO> callMovie = apiService.getMovie(id, apiKey);
        System.out.println(callMovie);
        try {
            movie = callMovie.execute().body();
            System.out.println(movie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movie;
    }
    /*public static List<Movie> getTopRated(){
        Call<MovieResponse> call = apiService.getTopRatedMovies(apiKey);
        List<Movie> moviesList = null;
        try {
            moviesList = call.execute().body().results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesList;
    }*/

    public static List<MovieDTO> getMostPopular(){
        List<MovieDTO> moviesDTO = null;
        Call<MovieDTOList> callMovies = apiService.getMostPopular(apiKey);
        try {
            moviesDTO = callMovies.execute().body().getMoviesDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDTO;
    }

    public static List<MovieDTO> getRainyFilms(){
        List<MovieDTO> moviesDTO = null;
        Call<MovieDTOList> callMovies = apiService.getHorrorMovies(27, apiKey);
        try {
            moviesDTO = callMovies.execute().body().getMoviesDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDTO;
    }

    public static List<MovieDTO> getComedyFilms(){
        List<MovieDTO> moviesDTO = null;
        Call<MovieDTOList> callMovies = apiService.getComedyMovies(35, apiKey);
        try {
            moviesDTO = callMovies.execute().body().getMoviesDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDTO;
    }

    public static List<MovieDTO> getKidFilms(){
        List<MovieDTO> moviesDTO = null;
        Call<MovieDTOList> callMovies = apiService.getAnimationMovies(16, apiKey);
        try {
            moviesDTO = callMovies.execute().body().getMoviesDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDTO;
    }

    public static List<MovieDTO> getMisteryFilms(){
        List<MovieDTO> moviesDTO = null;
        Call<MovieDTOList> callMovies = apiService.getMisteryMovies(9648, apiKey);
        try {
            moviesDTO = callMovies.execute().body().getMoviesDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDTO;
    }

    public static List<MovieDTO> getFictionFilms(){
        List<MovieDTO> moviesDTO = null;
        Call<MovieDTOList> callMovies = apiService.getFictionMovies(878, apiKey);
        try {
            moviesDTO = callMovies.execute().body().getMoviesDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDTO;
    }

    public static List<MovieDTO> getRomanticFilms(){
        List<MovieDTO> moviesDTO = null;
        Call<MovieDTOList> callMovies = apiService.getRomanticMovies(10749, apiKey);
        try {
            moviesDTO = callMovies.execute().body().getMoviesDTO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesDTO;
    }

    public static List<com.furyviewer.service.dto.MovieDatabase.Keyword> getMovieKeywords(int id){
        List<com.furyviewer.service.dto.MovieDatabase.Keyword> keyWordsList = null;
        Call<KeywordList> callKeyWords = apiService.getMovieKeywords(id,apiKey);
        try {
            KeywordList keywordListBody = callKeyWords.execute().body();
            if (keywordListBody != null && keywordListBody.getKeywords().size() > 0){
                keyWordsList = keywordListBody.getKeywords();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return keyWordsList;
    }
    public static MovieDTO getMovieInLang(int id, String lang){
        MovieDTO movie = null;
        Map<String, String> data = new HashMap<>();
        data.put("api_key", apiKey);
        data.put("language", lang);

        // simplified call to request the news with already initialized service
        Call<MovieDTO> callLang = apiService.getMovieInLang(id,data);
        try {
            movie = callLang.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public static Movie getMovieFromDto(MovieDTO d){
        Long id = new Long(d.getId());
        return new Movie(d.getTitle(), id);
    }

    public static Credits getMoviecredits(int id){
        Credits credits = null;
        Call<Credits> callCredits = apiService.getMovieCredits(id, apiKey);
        try {
            credits = callCredits.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credits;
    }
}
