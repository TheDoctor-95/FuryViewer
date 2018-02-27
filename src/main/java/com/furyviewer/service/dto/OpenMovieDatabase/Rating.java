
package com.furyviewer.service.dto.OpenMovieDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion del rating de movie/series proporcionada por TheMovieDatabase.
 */
public class Rating {
    /**
     * Nombre de la pagina a la que pertenece la puntuacion.
     */
    @SerializedName("Source")
    @Expose
    private String source;

    /**
     * Puntuacion de la movie/series de una pagina externa.
     */
    @SerializedName("Value")
    @Expose
    private String value;

    /**
     * Devuelve el nombre de la pagina a la que pertenece la puntuacion.
     * @return String | Nombre de la pagina.
     */
    public String getSource() {
        return source;
    }

    /**
     * Devuelve la nota de la movie/series de una pagina externa.
     * @return String | Nota de la pagina externa.
     */
    public String getValue() {
        return value;
    }
}
