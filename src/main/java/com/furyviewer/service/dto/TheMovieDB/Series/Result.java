
package com.furyviewer.service.dto.TheMovieDB.Series;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion simple de cada series por separado.
 */
public class Result {
    /**
     * Titulo original de la series.
     */
    @SerializedName("original_name")
    @Expose
    private String originalName;

    /**
     * id interno de la api de TheMovieDataBase
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Titulo de la series.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Numero de personas que han votado.
     */
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    /**
     * Nota de la series en TheMovieDataBase.
     */
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    /**
     * Path para poder construir la imagen de la series.
     */
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    /**
     * Primera fecha de emision de la series.
     */
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;

    /**
     * Popularidad de la serie.
     */
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * ids internos de la api para reconocer los generos.
     */
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;

    /**
     * Idioma original de la serie.
     */
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    /**
     * Imagen en horizontal de la series.
     */
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    /**
     * Sinopsis de la series.
     */
    @SerializedName("overview")
    @Expose
    private String overview;

    /**
     * Countrys en las que se ha rodado la series.
     */
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;

    /**
     * Devuelve el titulo original de la series.
     * @return String | Titulo original.
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
     * Devuelve el titulo de la series.
     * @return String | Titulo de la series.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el numero de votantes de la serie.
     * @return Integer | Numero de personas que votaron.
     */
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     * Devuelve la nota de TMDB de la series.
     * @return Double | Nota de la series.
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * Devuelve el path necesario para construir el enlace de la imagen.
     * @return String | Path de la imagen.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Devuelve la primera fecha de emision de la series.
     * @return String | Fecha de la primera emision.
     */
    public String getFirstAirDate() {
        return firstAirDate;
    }

    /**
     * Devuelve la popularidad de la series.
     * @return Double | Popularidad de la series.
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * Devuelve una lista con las ids de los generos.
     * @return List | ids de los generos.
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
     * Devuelve el path de la imagen horizontal.
     * @return String | Path de la imagen.
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * Devuelve la sinopsis de la series.
     * @return String | Sinopsis de la series.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Devuelve las countrys en las que se ha rodado la series.
     * @return List | Lista de countrys.
     */
    public List<String> getOriginCountry() {
        return originCountry;
    }
}
