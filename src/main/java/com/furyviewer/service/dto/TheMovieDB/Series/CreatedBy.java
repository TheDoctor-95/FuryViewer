
package com.furyviewer.service.dto.TheMovieDB.Series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion basica de los creadores de una series desde TheMovieDatabase.
 */
public class CreatedBy {
    /**
     * id interno de la api del creador.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Nombre del creador.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Path para reconstruir la imagen del creador.
     */
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    /**
     * Devuelve el id interno de la api del creador.
     * @return Integer | id del creador.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el nombre del creador.
     * @return String | Nombre del creador.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el path para poder construir la url de la imagen.
     * @return String | Path de la imagen.
     */
    public String getProfilePath() {
        return profilePath;
    }
}
