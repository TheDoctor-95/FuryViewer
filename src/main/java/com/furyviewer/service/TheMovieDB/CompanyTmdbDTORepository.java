package com.furyviewer.service.TheMovieDB;

import com.furyviewer.service.dto.TheMovieDB.Company.CompleteCompanyTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Company.SimpleCompanyTmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CompanyTmdbDTORepository {
    @GET("/3/search/company")
    Call<SimpleCompanyTmdbDTO> getSimpleCompany(@Query("api_key") String apikey, @Query("query") String companyName);

    @GET("/3/company/{id}")
    Call<CompleteCompanyTmdbDTO> getCompleteCompany(@Path("id") int id, @Query("api_key") String apikey);

    public static String url = " https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
