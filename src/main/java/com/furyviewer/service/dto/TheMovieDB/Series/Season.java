
package com.furyviewer.service.dto.TheMovieDB.Series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion basica de las seasons de una series desde TheMovieDatabase.
 */
public class Season {

    /**
     * Fecha de estreno de la season.
     */
    @SerializedName("air_date")
    @Expose
    private String airDate;

    /**
     * Numero de episodes de la season.
     */
    @SerializedName("episode_count")
    @Expose
    private Integer episodeCount;

    /**
     * id interno de la api.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Path necesario para reconstruir el enlace de la imagen.
     */
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    /**
     * Numero de la season.
     */
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;

    /**
     * Devuelve la fecha de estreno de la season.
     * @return String | Fecha de estreno.
     */
    public String getAirDate() {
        return airDate;
    }

    /**
     * Devuelve el numero de episodes de la season.
     * @return Integer | Numero total de episodes.
     */
    public Integer getEpisodeCount() {
        return episodeCount;
    }

    /**
     * Devuelve el id interno de la api de la season.
     * @return Integer | id de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el path de la imagen.
     * @return String | Path para crear la imagen.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Devuelve el numero de la season.
     * @return Integer | Numero de la season.
     */
    public Integer getSeasonNumber() {
        return seasonNumber;
    }
}
