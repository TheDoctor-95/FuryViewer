package com.furyviewer.service.MovieDatabase;

import com.furyviewer.service.dto.MovieDatabase.MovieDiscover;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Repository
public interface DiscoverRepository {
    @GET("discover/movie")
    Call<MovieDiscover> getMoviesByGenresAndCertification(@Query("api_key") String apiKey, @Query("with_genres") String withGenres,
                                                          @Query("without_genres") String withoutGenres, @Query("certification") String certification);


    public static String url = "https://api.themoviedb.org/3/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
