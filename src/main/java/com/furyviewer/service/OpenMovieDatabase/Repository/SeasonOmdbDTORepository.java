package com.furyviewer.service.OpenMovieDatabase.Repository;

import com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de OpenMovieDataBase para recuperar la informacion de Season.
 * @author IFriedkin
 * @see com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO
 */
public interface SeasonOmdbDTORepository {
    /**
     * Devuelve toda la informacion de una Season proporcionada por la api de OpenMovieDataBase.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param title String | Titulo de la Series que se quiere buscar.
     * @param season int | Numero de la Season que se quiere buscar.
     * @return Call | Contiene toda la informacion de Season devuelta por la api.
     */
    @GET("/?type=season")
    Call<SeasonOmdbDTO> getSeason(@Query("apikey") String apikey, @Query("t") String title, @Query("Season") int season);

    @GET("/?type=season")
    Call<SeasonOmdbDTO> getSeasonByImdbId(@Query("apikey") String apikey, @Query("i") String imdbId, @Query("Season") int season);

    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
