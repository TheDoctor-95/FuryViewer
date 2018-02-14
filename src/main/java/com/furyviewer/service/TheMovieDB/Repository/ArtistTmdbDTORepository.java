package com.furyviewer.service.TheMovieDB.Repository;

import com.furyviewer.service.dto.TheMovieDB.Artist.CompleteArtistTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Artist.SimpleArtistTmdbDTO;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de TheMovieDB para recuperar la informacion de Artist.
 * @author IFriedkin
 * @see com.furyviewer.service.dto.TheMovieDB.Artist.SimpleArtistTmdbDTO
 * @see com.furyviewer.service.dto.TheMovieDB.Artist.CompleteArtistTmdbDTO
 */
public interface ArtistTmdbDTORepository {
    /**
     * Devuelve la informacion simple de un Artist proporcionada por la API.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param artistName String | Nombre del Artist que se quiere buscar.
     * @return Call | Contiene toda la informacion simple del Artist devuelta por la api.
     */
    @GET("/3/search/person")
    Call<SimpleArtistTmdbDTO> getArtist(@Query("api_key") String apikey, @Query("query") String artistName);

    /**
     * Devuelve la informacion completa de un Artist proporcionada por la API.
     * @param id int | id interno de la API para reconocer al Artist.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @return Call | Contiene toda la informacion completa del Artist devuelta por la api.
     */
    @GET("/3/person/{id}")
    Call<CompleteArtistTmdbDTO> getFinalArtist(@Path("id") int id, @Query("api_key") String apikey);

    public static String url = " https://api.themoviedb.org/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
