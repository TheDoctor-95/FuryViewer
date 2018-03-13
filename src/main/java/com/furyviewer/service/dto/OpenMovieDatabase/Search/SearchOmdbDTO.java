
package com.furyviewer.service.dto.OpenMovieDatabase.Search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de Search buscada desde OpenMovieDatabase.
 */
public class SearchOmdbDTO {
    /**
     * Lista con los resultados que coinciden con el titulo.
     */
    @SerializedName("Search")
    @Expose
    private List<Search> search = null;

    /**
     * Numero total de resultados.
     */
    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    /**
     * Comprobacion de que la peticion a la api se ha realizado.
     */
    @SerializedName("Response")
    @Expose
    private String response;

    /**
     * Devuelve la lista de resultados que coinciden con el titulo.
     * @return List | Lista de Search.
     */
    public List<Search> getSearch() {
        return search;
    }

    /**
     * Devuelve el numero total de resultados.
     * @return String | Numero total de resultados.
     */
    public String getTotalResults() {
        return totalResults;
    }

    /**
     * Devuelve la comprobacion de la peticion a la api.
     * @return String | Comprobacion de la peticion.
     */
    public String getResponse() {
        return response;
    }
}
