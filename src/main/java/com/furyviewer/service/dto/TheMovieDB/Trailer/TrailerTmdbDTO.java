
package com.furyviewer.service.dto.TheMovieDB.Trailer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la lista de resultados entrantes desde TheMovieDatabase.
 */
public class TrailerTmdbDTO {
    /**
     * id interno de la api para reconocer movie o series.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Lista que contiene los diferentes trailers ofrecidos por la api.
     */
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    /**
     * Devuelve el id interno de la movie o series.
     * @return Integer | id interno de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve la lista con todos los posibles trailers.
     * @return List | Informacion de todos los trailers.
     */
    public List<Result> getResults() {
        return results;
    }
}
