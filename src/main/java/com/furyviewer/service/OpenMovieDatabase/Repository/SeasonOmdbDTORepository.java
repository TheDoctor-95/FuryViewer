package com.furyviewer.service.OpenMovieDatabase.Repository;

import com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de OpenMovieDataBase para recuperar la información de Season.
 * @author IFriedkin
 */
public interface SeasonOmdbDTORepository {
    /**
     * Devuelve toda la información de una Season devuelta por la api de OpenMovieDataBase.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param title String | Título de la Series que se quiere buscar.
     * @param season int | Número de la Season que se quiere buscar.
     * @return Call<SeasonOmdbDTO> | Contiene toda la información de Season devuelta por la api.
     */
    @GET("/?type=season")
    Call<SeasonOmdbDTO> getSeason(@Query("apikey") String apikey, @Query("t") String title, @Query("Season") int season);

    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
