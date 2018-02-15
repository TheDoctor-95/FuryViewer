
package com.furyviewer.service.dto.TheMovieDB.Company;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion completa de la company buscada desde TheMovieDatabase.
 */
public class CompleteCompanyTmdbDTO {

    /**
     * Descripcion de la company.
     */
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * Localizacion de la central.
     */
    @SerializedName("headquarters")
    @Expose
    private String headquarters;

    /**
     * Pagina oficial de la company.
     */
    @SerializedName("homepage")
    @Expose
    private String homepage;

    /**
     * id interno de la api.
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
     * Informacion simple de una company hija de la company que se ven los datos.
     */
    @SerializedName("parent_company")
    @Expose
    private ParentCompany parentCompany;

    /**
     * Devuelve la descripocion de una company.
     * @return String | Descripcion de la company.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Devuelve la localizacion de la sede.
     * @return String | Localizacion de la sede.
     */
    public String getHeadquarters() {
        return headquarters;
    }

    /**
     * Devuelve la url de la pagina oficial de la company.
     * @return String | url de la company.
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Devuelve el id interno de la api.
     * @return Integer | id de la api.
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

    /**
     * Devuelve el nombre de la company.
     * @return String | Nombre de la company.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve la company hija de la actual.
     * @return ParentCompany | Company hija.
     */
    public ParentCompany getParentCompany() {
        return parentCompany;
    }
}
