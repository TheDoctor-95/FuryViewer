
package com.furyviewer.service.dto.TheMovieDB.Company;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la lista de los resultados entrantes de company desde TheMovieDataBase.
 */
public class SimpleCompanyTmdbDTO {
    /**
     * Numero de pagina del resultado.
     */
    @SerializedName("page")
    @Expose
    private Integer page;

    /**
     * Lista que contiene las diferentes companies proporcionadas por la api.
     */
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    /**
     * Numero total de paginas del resultado.
     */
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    /**
     * Numero total de resultados ofrecidos por la api.
     */
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    /**
     * Devuelve la pagina actual de los resultados paginados.
     * @return Integer | Numero de la pagina.
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Devuelve la lista con todos los resultados que coinciden con la busqueda.
     * @return List | Lista con la informacion basica de las companies.
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * Devuelve el numero total de paginas.
     * @return Integer | Numero total de paginas.
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * Devuelve el numero total de resultados.
     * @return Integer | Numero total de resultados.
     */
    public Integer getTotalResults() {
        return totalResults;
    }
}
