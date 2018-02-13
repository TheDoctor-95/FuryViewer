package com.furyviewer.service.OpenMovieDatabase.Repository;

import com.furyviewer.service.dto.OpenMovieDatabase.SeriesOmdbDTO;
import retrofit2.http.Query;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Repositorio encargado de conectar con la api de OpenMovieDataBase para recuperar la información de Series.
 * @author IFriedkin
 */
public interface SeriesOmdbDTORepository {
    /**
     * Devuelve toda la información de una Series devuelta por la api de OpenMovieDataBase.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param title String | Título de la Series que se quiere buscar.
     * @return Call<SeriesOmdbDTO> | Contiene toda la información de Series devuelta por la api.
     */
    @GET("/?type=series&plot=full")
    Call<SeriesOmdbDTO> getSeries(@Query("apikey") String apikey, @Query("t") String title);

    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
