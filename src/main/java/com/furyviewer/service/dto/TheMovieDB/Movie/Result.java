
package com.furyviewer.service.dto.TheMovieDB.Movie;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion simple de cada movie por separado.
 */
public class Result {
    /**
     * Numero de personas que han votado.
     */
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    /**
     * id interno de la api de TheMovieDataBase
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Indica si tiene trailer.
     */
    @SerializedName("video")
    @Expose
    private Boolean video;

    /**
     * Nota de la movie en TheMovieDataBase.
     */
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    /**
     * Titulo de la movie.
     */
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * Popularidad dentro de TheMovieDataBase.
     */
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    /**
     * Path para poder reconstruir la url de la imagen.
     */
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    /**
     * Idioma original.
     */
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    /**
     * Titulo original.
     */
    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    /**
     * Lista con el id de los generos.
     */
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;

    /**
     * Path para reconstruir la imagen horizontal.
     */
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    /**
     * Indica si la movie esta pensada para un publico adulto.
     */
    @SerializedName("adult")
    @Expose
    private Boolean adult;

    /**
     * Sinopsis de la movie.
     */
    @SerializedName("overview")
    @Expose
    private String overview;

    /**
     * Fecha de estreno.
     */
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    /**
     * Devuelve el numero de votantes.
     * @return Integer | Numero de personas que votaron.
     */
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     * Devuelve el id interno de la api.
     * @return Integer | id de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve si la movie tiene trailer o no.
     * @return Boolean | Indica si hay trailer.
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     * Devuelve la nota de TMDB.
     * @return Double | Nota de la movie.
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * Devuelve el titulo.
     * @return String | Titulo de la movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve la popularidad de la movie en la api.
     * @return Double | Popularidad de la movie.
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * Devuelve el path necesario para construir el enlace de la imagen.
     * @return String | Path de la imagen.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Devuelve el idioma original.
     * @return String | Idioma original.
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * Devuelve el titulo original.
     * @return String | Titulo original.
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Devuelve una lista con el id de los generos.
     * @return List | Lista de ids.
     */
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    /**
     * Devuelve el path de la imagen horizontal.
     * @return String | Path de la imagen.
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * Devuelve si la movie esta pensada para un publico adulto o no.
     * @return Boolean | Publico adulto o no.
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     * Devuelve la sinopsis de la movie.
     * @return String | Sinopsis de la movie.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Devuelve la fecha de estreno.
     * @return String | Fecha de estreno.
     */
    public String getReleaseDate() {
        return releaseDate;
    }
}
