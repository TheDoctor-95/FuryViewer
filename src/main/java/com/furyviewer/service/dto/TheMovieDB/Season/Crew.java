
package com.furyviewer.service.dto.TheMovieDB.Season;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de la Crew (Artist: Director, Scriptwriter) proporcionados por TheMovieDataBase.
 */
public class Crew {
    /**
     * id secundario de la api.
     */
    @SerializedName("credit_id")
    @Expose
    private String creditId;

    /**
     * Departamento en el que trabaja el artist.
     */
    @SerializedName("department")
    @Expose
    private String department;

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
     * Trabajo que realiza el artist.
     */
    @SerializedName("job")
    @Expose
    private String job;

    /**
     * Nombre del artist.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Path necesario para reconstruir la imagen del artist.
     */
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    /**
     * Devuelve el id secundario de laa api.
     * @return String | id secundario.
     */
    public String getCreditId() {
        return creditId;
    }

    /**
     * Devuelve el departamento al cual pertenece el artist.
     * @return String | Departamento del artist.
     */
    public String getDepartment() {
        return department;
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
     * @return int | id de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el trabajo que realiza el artist.
     * @return String | Trabajo del artist.
     */
    public String getJob() {
        return job;
    }

    /**
     * Devuelve el nombre del artist.
     * @return String | Nombre del artist.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el path necesario para reconstruir la imagen.
     * @return String | Path de la imagen.
     */
    public String getProfilePath() {
        return profilePath;
    }
}
