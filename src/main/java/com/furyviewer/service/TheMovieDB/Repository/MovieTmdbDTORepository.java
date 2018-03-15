package com.furyviewer.service.TheMovieDB.Repository;

import com.furyviewer.service.dto.TheMovieDB.Movie.SimpleMovieTmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de TheMovieDB para recuperar la informacion de Movie.
 * @author IFriedkin
 * @see com.furyviewer.service.dto.TheMovieDB.Movie.SimpleMovieTmdbDTO
 */
public interface MovieTmdbDTORepository {
    /**
     * Devuelve la informacion simple de una Movie proporcionada por la API.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param movieName String | Titulo de la Movie que se quiere buscar.
     * @return Call | Contiene toda la informacion simple de la Movie devuelta por la api.
     */
    @GET("/3/search/movie")
    Call<SimpleMovieTmdbDTO> getSimpleMovie(@Query("api_key") String apikey, @Query("query") String movieName);

    public static String url = "https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
