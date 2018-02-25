
package com.furyviewer.service.dto.TheMovieDB.Episode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de los actores principales proporcionados por TheMovieDataBase.
 */
public class Cast {
    /**
     * Nombre del personaje.
     */
    @SerializedName("character")
    @Expose
    private String character;

    /**
     * id secundario de TMDB.
     */
    @SerializedName("credit_id")
    @Expose
    private String creditId;

    /**
     * Genero del artist.
     */
    @SerializedName("gender")
    @Expose
    private Integer gender;

    /**
     * id interno de la api.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Nombre del artist.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Orden de importancia.
     */
    @SerializedName("order")
    @Expose
    private Integer order;

    /**
     * Path necesario para poder reconstruir la imagen.
     */
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    /**
     * Devuelve el nombre del personaje.
     * @return String | Nombre del personaje.
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Devuelve el id secundario de TMDB.
     * @return String | id secundario.
     */
    public String getCreditId() {
        return creditId;
    }

    /**
     * Devuelve el genero del artist.
     * @return int | Genero del artist.
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * Devuelve el id interno de la api.
     * @return int | id interno de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el nombre del artist.
     * @return String | Nombre del artist.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el orden de importancia de los artist.
     * @return int | Orden de importancia.
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * Devuelve el path necesario para reconstruir la imagen.
     * @return String | Path para la imagen.
     */
    public String getProfilePath() {
        return profilePath;
    }
}
