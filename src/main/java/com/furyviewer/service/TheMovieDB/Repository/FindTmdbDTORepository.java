package com.furyviewer.service.TheMovieDB.Repository;

import com.furyviewer.service.dto.TheMovieDB.find.FindTmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FindTmdbDTORepository {

    @GET("/3/find/{imdbId}?external_source=imdb_id")
    Call<FindTmdbDTO> getFind(@Path("imdbId") String imdbId, @Query("api_key") String apikey);

    public static String url = "https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
