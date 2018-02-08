package com.furyviewer.service.OpenMovieDatabase.Repository;

import com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EpisodeOmdbDTORepository {

    @GET("/?type=episode")
    Call<EpisodeOmdbDTO> getEpisode(@Query("apikey") String apikey, @Query("t") String title,
                                   @Query("Season") int season, @Query("Episode") int episode);

    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
