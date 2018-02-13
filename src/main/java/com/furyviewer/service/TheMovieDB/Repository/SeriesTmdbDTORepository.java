package com.furyviewer.service.TheMovieDB.Repository;

import com.furyviewer.service.dto.TheMovieDB.Series.CompleteSeriesTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Series.SimpleSeriesTmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de TheMovieDB para recuperar la información de Series.
 * @author IFriedkin
 */
public interface SeriesTmdbDTORepository {
    /**
     * Devuelve la información simple de una Series proporcionada por la API.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param seriesName String | Nombre de la Series que se quiere buscar.
     * @return Call<SimpleSeriesTmdbDTO> | Contiene toda la información simple de la Series devuelta por la api.
     */
    @GET("/3/search/tv")
    Call<SimpleSeriesTmdbDTO> getSimpleSeries(@Query("api_key") String apikey, @Query("query") String seriesName);

    /**
     * Devuelve la información completa de una Series proporcionada por la API.
     * @param id int | id interno de la API para reconocer la Series.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @return Call<CompleteSeriesTmdbDTO> | Contiene toda la información completa de la Series devuelta por la api.
     */
    @GET("/3/tv/{id}")
    Call<CompleteSeriesTmdbDTO> getCompleteSeries(@Path("id") int id, @Query("api_key") String apikey);

    public static String url = " https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
