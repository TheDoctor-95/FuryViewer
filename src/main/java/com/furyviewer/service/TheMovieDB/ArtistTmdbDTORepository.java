package com.furyviewer.service.TheMovieDB;

import com.furyviewer.service.dto.TheMovieDB.ArtistFinalTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.ArtistTmdbDTO;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArtistTmdbDTORepository {
    @GET("/3/search/person")
    Call<ArtistTmdbDTO> getArtist(@Query("api_key") String apikey, @Query("query") String artistName);

    @GET("/3/person/{id}")
    Call<ArtistFinalTmdbDTO> getFinalArtist(@Path("id") int id, @Query("api_key") String apikey);

    public static String url = " https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
