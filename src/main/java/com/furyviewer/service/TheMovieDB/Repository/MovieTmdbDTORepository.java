package com.furyviewer.service.TheMovieDB.Repository;

import com.furyviewer.service.dto.TheMovieDB.Movie.SimpleMovieTmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de TheMovieDB para recuperar la información de Movie.
 * @author IFriedkin
 */
public interface MovieTmdbDTORepository {
    /**
     * Devuelve la información simple de una Movie proporcionada por la API.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param movieName String | Título de la Movie que se quiere buscar.
     * @return Call<SimpleMovieTmdbDTO> | Contiene toda la información simple de la Movie devuelta por la api.
     */
    @GET("/3/search/movie")
    Call<SimpleMovieTmdbDTO> getSimpleMovie(@Query("api_key") String apikey, @Query("query") String movieName);

    public static String url = " https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
