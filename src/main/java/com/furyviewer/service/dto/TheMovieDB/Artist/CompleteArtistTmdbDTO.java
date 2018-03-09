
package com.furyviewer.service.dto.TheMovieDB.Artist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion completa de los artists buscada desde TheMovieDatabase.
 */
public class CompleteArtistTmdbDTO {

    /**
     * Fecha de nacimiento.
     */
    @SerializedName("birthday")
    @Expose
    private String birthday;

    /**
     * Fecha de muerte.
     */
    @SerializedName("deathday")
    @Expose
    private Object deathday;

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
     * Lista de lugares donde ha trabajado.
     */
    @SerializedName("also_known_as")
    @Expose
    private List<Object> alsoKnownAs = null;

    /**
     * Genero del artist.
     */
    @SerializedName("gender")
    @Expose
    private Integer gender;

    /**
     //* Biografia del artist.
    //*/
    @SerializedName("biography")
    @Expose
    private String biography;

    /**
     * Popularidad del artist en TheMovieDataBase.
     */
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * Localizacion de nacimiento del artist.
     */
    @SerializedName("place_of_birth")
    @Expose
    private String placeOfBirth;

    /**
     * Path necesario para reconstruir la url de la imagen.
     */
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    /**
     * Indica si hace contenido para adultos o no.
     */
    @SerializedName("adult")
    @Expose
    private Boolean adult;

    /**
     * id de IMDB.
     */
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    /**
     * Pagina oficial del artist.
     */
    @SerializedName("homepage")
    @Expose
    private String homepage;

    /**
     * Devuelve la fecha de nacimiento.
     * @return String | Fecha de nacimiento.
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Devuelve la fecha de muerte.
     * @return String | Fecha de muerte.
     */
    public Object getDeathday() {
        return deathday;
    }

    /**
     * Devuelve el id interno de la api.
     * @return Integer | id de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el nombre.
     * @return String | Nombre del artist.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve una lista de trabajos del artist.
     * @return List
     */
    public List<Object> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    /**
     * Devuelve el genero del artist.
     * @return Integer | Genero.
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * Devuelve la biografia.
     * @return String | Biografia del artist.
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Devuelve la popularidad del artist en la api.
     * @return Double | Popularidad del artist.
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * Devuelve la localizacion de nacimiento.
     * @return String | Lugar de nacimiento.
     */
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * Devuuelve el path necesario para reconstruir la url de la imagen.
     * @return String | Path de la imagen.
     */
    public String getProfilePath() {
        return profilePath;
    }

    /**
     * Devuelve si hace contenido para adultos.
     * @return Boolean
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     * Devuelve el id de IMDB.
     * @return String | id de IMDB.
     */
    public String getImdbId() {
        return imdbId;
    }

    /**
     * devuelve la pagina oficial del artist.
     * @return String | Pagina oficial.
     */
    public String getHomepage() {
        return homepage;
    }
}
