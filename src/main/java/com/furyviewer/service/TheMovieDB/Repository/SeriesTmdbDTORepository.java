package com.furyviewer.service.TheMovieDB.Repository;

import com.furyviewer.service.dto.TheMovieDB.Series.CompleteSeriesTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Series.SimpleSeriesTmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SeriesTmdbDTORepository {
    @GET("/3/search/tv")
    Call<SimpleSeriesTmdbDTO> getSimpleSeries(@Query("api_key") String apikey, @Query("query") String artistName);

    @GET("/3/tv/{id}")
    Call<CompleteSeriesTmdbDTO> getCompleteSeries(@Path("id") int id, @Query("api_key") String apikey);

    public static String url = " https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
