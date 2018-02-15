
package com.furyviewer.service.dto.TheMovieDB.Series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de los generos de una series desde TheMovieDatabase.
 */
public class Genre {

    /**
     * id interno de la api del genero.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Nombre del genero.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Devuelve el id interno de la api del genero.
     * @return Integer | id del genero.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el nombre del genero.
     * @return String | Nombre del genero.
     */
    public String getName() {
        return name;
    }
}
