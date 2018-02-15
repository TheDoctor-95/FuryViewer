
package com.furyviewer.service.dto.TheMovieDB.Series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Gestiona la informacion de las companies de una series desde TheMovieDatabase.
 */
public class ProductionCompany {
    /**
     * Nombre de la company de la series.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * id interno de la api de la company.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Devuelve el nombre de la company.
     * @return String | Nombre de la company.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el id interno de la api de la company.
     * @return Integer | id de la company.
     */
    public Integer getId() {
        return id;
    }
}
