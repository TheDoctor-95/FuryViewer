package com.furyviewer.service.OpenMovieDatabase.Repository;

import com.furyviewer.service.dto.OpenMovieDatabase.Search.SearchOmdbDTO;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Repositorio encargado de conectar con la api de OpenMovieDataBase para recuperar la informacion de Search.
 * @author IFriedkin
 * @see com.furyviewer.service.dto.OpenMovieDatabase.Search.SearchOmdbDTO
 */
public interface SearchOmdbDTORepository {

    /**
     * Devuelve una lista con todas las movies y series que coinciden con el titulo.
     * @param apikey String | Key requerida por la api para poder hacer peticiones.
     * @param title String | Titulo a buscart en movies y series.
     * @return Call | Contiene toda la informacion de Season devuelta por la api.
     */
    @GET("/")
    Call<SearchOmdbDTO> getSearch(@Query("apikey") String apikey, @Query("s") String title);

    public static String url = "http://www.omdbapi.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
