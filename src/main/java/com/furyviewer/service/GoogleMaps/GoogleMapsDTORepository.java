package com.furyviewer.service.GoogleMaps;

import com.furyviewer.service.dto.GoogleMaps.GoogleMapsDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsDTORepository {
    @GET("/maps/api/geocode/json?language=en")
    Call<GoogleMapsDTO> getCoordinates(@Query("address") String address, @Query("key") String apikey);

    public static String url = "https://maps.googleapis.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
