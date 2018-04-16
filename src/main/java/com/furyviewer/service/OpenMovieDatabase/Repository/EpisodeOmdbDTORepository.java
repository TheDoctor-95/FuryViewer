package com.furyviewer.service.OpenMovieDatabase.Repository;

import com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de OpenMovieDataBase para recuperar la informacion de Episode.
 * @author IFriedkin
 * @see com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO
 */
public interface EpisodeOmdbDTORepository {
    /**
     * Devuelve toda la informacion de un Episode proporcionada por la api de OpenMovieDataBase.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param title String | Titulo de la Series que se quiere buscar.
     * @param season int | Numero de la Season que se quiere buscar.
     * @param episode int | Numero del Episode que se quiere buscar.
     * @return Call | Contiene toda la informacion de Episode devuelta por la api.
     */
    @GET("/?type=episode")
    Call<EpisodeOmdbDTO> getEpisode(@Query("apikey") String apikey, @Query("t") String title,
                                   @Query("Season") int season, @Query("Episode") int episode);

    @GET("/?type=episode")
    Call<EpisodeOmdbDTO> getEpisodebyImdbId(@Query("apikey") String apikey, @Query("i") String imdbId,
                                    @Query("Season") int season, @Query("Episode") int episode);

    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
