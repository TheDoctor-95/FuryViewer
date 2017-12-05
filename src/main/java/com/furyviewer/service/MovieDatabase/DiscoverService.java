package com.furyviewer.service.MovieDatabase;

import com.furyviewer.service.dto.MovieDatabase.MovieDTO;
import com.furyviewer.service.dto.MovieDatabase.MovieDiscover;
import com.furyviewer.service.dto.MovieDatabase.Result;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoverService {
    @Inject
    private MovieDTOService movieDTOService;

    public static final String apiKey = "e9146e088c2bfd128d974ae6fe70bdf4";
    static DiscoverRepository apiService = DiscoverRepository.retrofit.create(DiscoverRepository.class);

    public List<MovieDTO> getAloneMovies(){
        List<Result> resultList = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        Call<MovieDiscover> callMovie =
            apiService.getMoviesByGenresAndCertification(apiKey, "14,53", null, null);
        getResultsFromCall(callMovie, movies, resultList);
        return movies;
    }

    public List<MovieDTO> getAloneClearMovies(){
        List<Result> resultList = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        Call<MovieDiscover> callMovie =
            apiService.getMoviesByGenresAndCertification(apiKey, "878,12", null, null);
        getResultsFromCall(callMovie, movies, resultList);
        return movies;
    }

    public List<MovieDTO> getPartnerMovies(){
        List<Result> resultList = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        Call<MovieDiscover> callMovie =
            apiService.getMoviesByGenresAndCertification(apiKey, "9648,27", null, null);
        getResultsFromCall(callMovie, movies, resultList);
        return movies;
    }



    public List<MovieDTO> getPartnerClearMovies(){
        List<Result> resultList = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        Call<MovieDiscover> callMovie =
            apiService.getMoviesByGenresAndCertification(apiKey, "10749,35", null, null);
        getResultsFromCall(callMovie, movies, resultList);
        return movies;
    }

    public List<MovieDTO> getFriendsMovies(){
        List<Result> resultList = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        Call<MovieDiscover> callMovie =
            apiService.getMoviesByGenresAndCertification(apiKey, "28,53", null, null);
        getResultsFromCall(callMovie, movies, resultList);
        return movies;
    }

    public List<MovieDTO> getFriendsClearMovies(){
        List<Result> resultList = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        Call<MovieDiscover> callMovie =
            apiService.getMoviesByGenresAndCertification(apiKey, "12,35", null, null);
        getResultsFromCall(callMovie, movies, resultList);
        return movies;
    }

    public List<MovieDTO> getKidsMovies(){
        List<Result> resultList = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        Call<MovieDiscover> callMovie =
            apiService.getMoviesByGenresAndCertification(apiKey, "10751,16", "27", "G");
       getResultsFromCall(callMovie, movies, resultList);
        return movies;
    }

    public List<MovieDTO> getFamilyMovies(){
        List<Result> resultList = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        Call<MovieDiscover> callMovie =
            apiService.getMoviesByGenresAndCertification(apiKey, null, "27", "G");
        getResultsFromCall(callMovie, movies, resultList);
        return movies;
    }

    public void getResultsFromCall(Call<MovieDiscover> callMovie, List<MovieDTO> movies, List<Result> resultList){
        try {
            resultList = callMovie.execute().body().getResults();
            for (Result result : resultList){
                movies.add(movieDTOService.getMovie(result.getId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
