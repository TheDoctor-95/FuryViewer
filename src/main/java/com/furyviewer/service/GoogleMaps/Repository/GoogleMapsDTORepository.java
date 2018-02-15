package com.furyviewer.service.GoogleMaps.Repository;

import com.furyviewer.service.dto.GoogleMaps.GoogleMapsDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de GoogleMapsGeocoding para recuperar la informacion de una
 * localizacion.
 * @author IFriedkin
 * @see com.furyviewer.service.dto.GoogleMaps.GoogleMapsDTO
 */
public interface GoogleMapsDTORepository {
    /**
     * Devuelve las coordenadas e informacion muy basica en ingles de una localizacion gracias a GoogleMapsGeocodingAPI.
     * @param address String | Localizacion que se quiere buscar.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @return Call | Contiene la información de la localizacion devuelta por la api.
     */
    @GET("/maps/api/geocode/json?language=en")
    Call<GoogleMapsDTO> getCoordinates(@Query("address") String address, @Query("key") String apikey);

    public static String url = "https://maps.googleapis.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
