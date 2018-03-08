package com.furyviewer.service.OpenMovieDatabase.Repository;

import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de OpenMovieDataBase para recuperar la informacion de Movie.
 * @author TheDoctor-95
 * @see com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO
 */
public interface MovieOmdbDTORepository {
    /**
     * Devuelve toda la informacion de una Movie proporcionada por la api de OpenMovieDataBase.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param title String | Titulo de la Movie que se quiere buscar.
     * @return Call | Contiene toda la informacion de Movie devuelta por la api.
     */
    @GET("/?type=movie&plot=full")
    Call<MovieOmdbDTO> getMovie(@Query("apikey") String apikey, @Query("t") String title);

    @GET("/?type=movie&plot=full")
    Call<MovieOmdbDTO> getMovieByTitleAndYear(@Query("apikey") String apikey, @Query("t") String title,
                                              @Query("y") String year);


    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
