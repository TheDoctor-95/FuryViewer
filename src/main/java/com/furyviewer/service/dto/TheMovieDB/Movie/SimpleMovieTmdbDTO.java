
package com.furyviewer.service.dto.TheMovieDB.Movie;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la lista de los resultados entrantes de movie desde TheMovieDataBase.
 */
public class SimpleMovieTmdbDTO {

    /**
     * Numero de pagina del resultado.
     */
    @SerializedName("page")
    @Expose
    private Integer page;

    /**
     * Numero total de resultados ofrecidos por la api.
     */
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    /**
     * Numero total de paginas del resultado.
     */
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    /**
     * Lista que contiene las diferentes movies proporcionadas por la api.
     */
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    /**
     * Devuelve la pagina actual de los resultados paginados.
     * @return Integer | Numero de la pagina.
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Devuelve el numero total de resultados.
     * @return Integer | Numero total de resultados.
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * Devuelve el numero total de paginas.
     * @return Integer | Numero total de paginas.
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * Devuelve la lista con todos los resultados que coinciden con la busqueda.
     * @return List | Lista con la informacion basica de las movies.
     */
    public List<Result> getResults() {
        return results;
    }
}
