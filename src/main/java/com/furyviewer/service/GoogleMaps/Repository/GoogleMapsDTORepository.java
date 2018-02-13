package com.furyviewer.service.GoogleMaps.Repository;

import com.furyviewer.service.dto.GoogleMaps.GoogleMapsDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de GoogleMapsGeocoding para recuperar la información de una
 * localización.
 * @author IFriedkin
 */
public interface GoogleMapsDTORepository {
    /**
     * Devuelve las coordenadas e información muy básica en inglés de una localización gracias a GoogleMapsGeocodingAPI.
     * @param address String | Localización que se quiere buscar.
     * @param apikey String | Key requerida por la api para ppoder hacer peticiones.
     * @return Call<GoogleMaps> | Contiene la información de la localización devuelta por la api.
     */
    @GET("/maps/api/geocode/json?language=en")
    Call<GoogleMapsDTO> getCoordinates(@Query("address") String address, @Query("key") String apikey);

    public static String url = "https://maps.googleapis.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
