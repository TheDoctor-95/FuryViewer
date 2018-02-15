
package com.furyviewer.service.dto.TheMovieDB.Artist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona las series o movies donde aparece el artist.
 */
public class KnownFor {

    /**
     * Titulo original.
     */
    @SerializedName("original_name")
    @Expose
    private String originalName;

    /**
     * id interno de la api.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * indica si es series o movie.
     */
    @SerializedName("media_type")
    @Expose
    private String mediaType;

    /**
     * Titulo de la series o movie.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Numero de votos.
     */
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    /**
     * Nota de TheMovieDataBase.
     */
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    /**
     * Path necesario para reconstruir la url de la imagen.
     */
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    /**
     * Fecha de estreno.
     */
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;

    /**
     * Popularidad en TheMovieDataBase.
     */
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * Lista con la id de los generos.
     */
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;

    /**
     * Idioma original.
     */
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    /**
     * Path necesario para reconstruir la imagen horizontal.
     */
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    /**
     * Sinopsis.
     */
    @SerializedName("overview")
    @Expose
    private String overview;

    /**
     * Lista de countries en la que se grabo.
     */
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;

    /**
     * Indica si tiene trailer.
     */
    @SerializedName("video")
    @Expose
    private Boolean video;

    /**
     * Titulo de la series o movie.
     */
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * Titulo original de la series o movie.
     */
    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    /**
     * Indica si es para un publico adulto.
     */
    @SerializedName("adult")
    @Expose
    private Boolean adult;

    /**
     * Fecha de estreno.
     */
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    /**
     * Devuelve el titulo original.
     * @return String | Titulo.
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Devuelve el id interno de la api.
     * @return Integer | id de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve si es movie o series.
     * @return String | tv o movie.
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Devuelve el titulo.
     * @return String | Titulo.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el numero de votos.
     * @return Integer | Numero de votos.
     */
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     * Devuelve la nota de TheMovieDataBase.
     * @return Double | Nota.
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * Devuelve el path necesario para reconstruir la imagen.
     * @return String | Path de la imagen.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Devuelve la fecha de estreno.
     * @return String | Fecha de estreno.
     */
    public String getFirstAirDate() {
        return firstAirDate;
    }

    /**
     * Devuelve la popularidad dentro de la api.
     * @return Double | Popularidad.
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * Devuelve una lista con el id de los generos .
     * @return List | Lista con ids.
     */
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    /**
     * Devuelve el idioma original.
     * @return String | Idioma original.
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * devuelve el path necesario para reconstruir la imagen horizontal.
     * @return String | Path de la imagen.
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * Devuelve la sinopsis.
     * @return String | Sinopsis.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Devuelve una lista de las localizaciones donde se grabo.
     * @return List | Lista de localizaciones.
     */
    public List<String> getOriginCountry() {
        return originCountry;
    }

    /**
     * Devuelve si tiene trailer o no.
     * @return Boolean
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     * Devuelve el titulo.
     * @return String | Titulo.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve el titulo original.
     * @return String | Titulo.
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Devuelve si hace contenido para adultos.
     * @return Boolean
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     * Devuelve la fecha de estreno.
     * @return String | Fecha de estreno.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

}
