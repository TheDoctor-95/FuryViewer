
package com.furyviewer.service.dto.TheMovieDB.Artist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion simple de cada artist por separado.
 */
public class Result {
    /**
     * Popularidad del artist dentro de TheMovieDataBase
     */
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * id interno de la api de TheMovieDataBase
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Path para reconstruir la imagen del logo.
     */
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    /**
     * Nombre del artist.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Lista corta de movies o series donde aparece.
     */
    @SerializedName("known_for")
    @Expose
    private List<KnownFor> knownFor = null;

    /**
     * Indica si participa en movies o series para adultos.
     */
    @SerializedName("adult")
    @Expose
    private Boolean adult;

    /**
     * Devuelve la popularidad del artist en TMDB.
     * @return Double | Popoularidad del artist.
     */
    public Double getPopularity() {
        return popularity;
    }

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
    public String getProfilePath() {
        return profilePath;
    }

    /**
     * Devuelve el nombre del artist.
     * @return String | Nombre del artist.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve una lista corta de obras donde aparece.
     * @return List | Lista de movies o series.
     */
    public List<KnownFor> getKnownFor() {
        return knownFor;
    }

    /**
     * Devuelve si hace contenido para adultos.
     * @return Boolean
     */
    public Boolean getAdult() {
        return adult;
    }
}
