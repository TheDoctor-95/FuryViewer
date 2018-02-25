package com.furyviewer.service.TheMovieDB.Repository;

import com.furyviewer.service.dto.TheMovieDB.Episode.EpisodeCastingDTO;
import com.furyviewer.service.dto.TheMovieDB.Episode.EpisodeExternalIdDTO;
import com.furyviewer.service.dto.TheMovieDB.Season.SeasonTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Series.CompleteSeriesTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Series.SimpleSeriesTmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de TheMovieDB para recuperar la informacion de Series.
 * @author IFriedkin
 * @see com.furyviewer.service.dto.TheMovieDB.Series.SimpleSeriesTmdbDTO
 * @see com.furyviewer.service.dto.TheMovieDB.Series.CompleteSeriesTmdbDTO
 * @see com.furyviewer.service.dto.TheMovieDB.Season.SeasonTmdbDTO
 * @see com.furyviewer.service.dto.TheMovieDB.Episode.EpisodeExternalIdDTO
 * @see com.furyviewer.service.dto.TheMovieDB.Episode.EpisodeCastingDTO
 */
public interface SeriesTmdbDTORepository {
    /**
     * Devuelve la informacion simple de una Series proporcionada por la API.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param seriesName String | Nombre de la Series que se quiere buscar.
     * @return Call | Contiene toda la informacion simple de la Series devuelta por la api.
     */
    @GET("/3/search/tv")
    Call<SimpleSeriesTmdbDTO> getSimpleSeries(@Query("api_key") String apikey, @Query("query") String seriesName);

    /**
     * Devuelve la informacion completa de una Series proporcionada por la API.
     * @param id int | id interno de la API para reconocer la Series.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @return Call | Contiene toda la informacion completa de la Series devuelta por la api.
     */
    @GET("/3/tv/{id}")
    Call<CompleteSeriesTmdbDTO> getCompleteSeries(@Path("id") int id, @Query("api_key") String apikey);

    /**
     * Devuelve la informacion completa de una Season proporcionada por la API.
     * @param id int | id interno de la API para reconocer la Series.
     * @param seasonNumber int | Numero de la season que se quiere buscar.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @return Call | contiene toda la informacion completa de la Season devuelta por la api.
     */
    @GET("/3/tv/{id}/season/{seasonNumber}")
    Call<SeasonTmdbDTO> getSeason(@Path("id") int id, @Path("seasonNumber") int seasonNumber,
                                  @Query("api_key") String apikey);

    /**
     * Devuelve la información de los ids externos para un Episode proporcionada por la API.
     * @param id int | id interno de la API para reconocer la Series.
     * @param seasonNumber int | Numero de la season que se quiere buscar.
     * @param episodeNumber int | Numero del episode que se quiere buscar.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @return Call | contiene toda la informacion completa de la Season devuelta por la api.
     */
    @GET("/3/tv/{id}/season/{seasonNumber}/episode/{episodeNumber}/external_ids")
    Call<EpisodeExternalIdDTO> getExternalId(@Path("id") int id, @Path("seasonNumber") int seasonNumber,
                                             @Path("episodeNumber") int episodeNumber, @Query("api_key") String apikey);

    /**
     * Devuelve la información de los artist que han participado en un episode.
     * @param id int | id interno de la API para reconocer la Series.
     * @param seasonNumber int | Numero de la season que se quiere buscar.
     * @param episodeNumber int | Numero del episode que se quiere buscar.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @return Call | contiene toda la informacion completa de la Season devuelta por la api.
     */
    @GET("/3/tv/{id}/season/{seasonNumber}/episode/{episodeNumber}/credits")
    Call<EpisodeCastingDTO> getCasting(@Path("id") int id, @Path("seasonNumber") int seasonNumber,
                                       @Path("episodeNumber") int episodeNumber, @Query("api_key") String apikey);

    public static String url = " https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
