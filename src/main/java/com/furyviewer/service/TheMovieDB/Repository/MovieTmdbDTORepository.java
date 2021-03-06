package com.furyviewer.service.TheMovieDB.Repository;

import com.furyviewer.service.dto.TheMovieDB.Episode.EpisodeCastingDTO;
import com.furyviewer.service.dto.TheMovieDB.Movie.SimpleMovieTmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    /**
     * Devuelve la informacion del casting que ha participado en una movie.
     * @param id int | id interno de la API para reconocer la movie.
     * @param apikey String | Titulo de la Movie que se quiere buscar.
     * @return Call | Contiene toda la informacion del casting proporcionada por la api.
     */
    @GET("/3/movie/{id}/credits")
    Call<EpisodeCastingDTO> getCredits(@Path("id") int id, @Query("api_key") String apikey);

    public static String url = "https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
