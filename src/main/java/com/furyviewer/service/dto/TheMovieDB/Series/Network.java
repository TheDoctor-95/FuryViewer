
package com.furyviewer.service.dto.TheMovieDB.Series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Gestiona la informacion de las networks de una series desde TheMovieDatabase.
 */
public class Network {
    /**
     * id interno de la api de la network.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Nombre de la network.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Devuelve el id interno de la api de la network.
     * @return Integer | id de la network.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el nombre de la network.
     * @return String | Nombre de la network.
     */
    public String getName() {
        return name;
    }
}
