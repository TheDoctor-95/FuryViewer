package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.service.dto.OpenMovieDatabase.SeriesOmdbDTO;
import retrofit2.http.Query;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface SeriesOmdbDTORepository {

    @GET("/?type=series")
    Call<SeriesOmdbDTO> getSeries(@Query("apikey") String apikey, @Query("t") String title);








    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
