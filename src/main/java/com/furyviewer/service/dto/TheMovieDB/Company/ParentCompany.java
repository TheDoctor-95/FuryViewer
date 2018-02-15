
package com.furyviewer.service.dto.TheMovieDB.Company;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion simple de la company hija.
 */
public class ParentCompany {

    /**
     * Nombre de la company.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * id interno de la api.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Path para reconstruir la url de la imagen.
     */
    @SerializedName("logo_path")
    @Expose
    private String logoPath;

    /**
     * Devuelve el nombre de la company.
     * @return String | Nombre de la company.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el id interno de la api.
     * @return Integer | id interno de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el path necesario para reconstruir la imagen del logo.
     * @return String | Path del logo.
     */
    public String getLogoPath() {
        return logoPath;
    }
}
