
package com.furyviewer.service.dto.TheMovieDB.Season;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de la season buscada desde TheMovieDatabase.
 */
public class SeasonTmdbDTO {
    /**
     * Por algun motivo existencialista en la api tienen 2 ids.
     */
    @SerializedName("_id")
    @Expose
    private String id2;

    /**
     * Primera fecha de emision de la season.
     */
    @SerializedName("air_date")
    @Expose
    private String airDate;

    /**
     * Lista que contiene la informacionm de todos los episodes de la season.
     */
    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;

    /**
     * Nombre de la season.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Sinopsis de la season.
     */
    @SerializedName("overview")
    @Expose
    private String overview;

    /**
     * id interno de la api de TMDB.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Path para reconstruir la imagen del poster.
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
     * Devuelve el id existencialista que almacena la api.
     * @return String | id existencialista.
     */
    public String getId2() {
        return id2;
    }

    /**
     * Devuelve la primera fecha de emision.
     * @return String | Fecha de primera emision.
     */
    public String getAirDate() {
        return airDate;
    }

    /**
     * Devuelve una lista con informacion de todos los episodes.
     * @return List | Lista de episodes.
     */
    public List<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * Devuelve el nombre de la season.
     * @return String | Nombre de la season.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve la sinopsis de la season.
     * @return String | Sinopsis.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Devuelve el id interno de la api de TMDB.
     * @return int | id de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el path para poder reconstruir la imagen del poster.
     * @return String | Path del poster.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Devuelve el numero de la season.
     * @return int | Numero de la season.
     */
    public Integer getSeasonNumber() {
        return seasonNumber;
    }
}
