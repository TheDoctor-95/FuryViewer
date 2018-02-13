package com.furyviewer.service.OpenMovieDatabase.Repository;

import com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de OpenMovieDataBase para recuperar la información de Episode.
 * @author IFriedkin
 */
public interface EpisodeOmdbDTORepository {
    /**
     * Devuelve toda la información de un Episode devuelta por la api de OpenMovieDataBase.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param title String | Título de la Series que se quiere buscar.
     * @param season int | Número de la Season que se quiere buscar.
     * @param episode int | Número del Episode que se quiere buscar.
     * @return Call<SeasonOmdbDTO> | Contiene toda la información de Episode devuelta por la api.
     */
    @GET("/?type=episode")
    Call<EpisodeOmdbDTO> getEpisode(@Query("apikey") String apikey, @Query("t") String title,
                                   @Query("Season") int season, @Query("Episode") int episode);

    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
