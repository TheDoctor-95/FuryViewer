
package com.furyviewer.service.dto.TheMovieDB.Company;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion simple de cada company por separado.
 */
public class Result {

    /**
     * id interno de la api de TheMovieDataBase
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Path para reconstruir la url del logo.
     */
    @SerializedName("logo_path")
    @Expose
    private String logoPath;

    /**
     * Nombre de la company.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Devuelve el id interno de la api.
     * @return Integer | id de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el path necesario para reconstruir la url de la imagen.
     * @return String | Path de la imagen.
     */
    public String getLogoPath() {
        return logoPath;
    }

    /**
     * Devuelve el nombre.
     * @return String | Nombre de la company.
     */
    public String getName() {
        return name;
    }
}
