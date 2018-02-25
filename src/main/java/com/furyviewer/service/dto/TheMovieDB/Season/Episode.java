
package com.furyviewer.service.dto.TheMovieDB.Season;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion del episode proporcionado desde SeasonTmdbDTO.
 */
public class Episode {
    /**
     * Fecha de emision del episode.
     */
    @SerializedName("air_date")
    @Expose
    private String airDate;

    /**
     * Lista con la informacion de la crew que participa en el episode.
     */
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;

    /**
     * Numero del episode.
     */
    @SerializedName("episode_number")
    @Expose
    private Integer episodeNumber;

    /**
     * Lista con la informacion de las gueststars del episode.
     */
    @SerializedName("guest_stars")
    @Expose
    private List<GuestStar> guestStars = null;

    /**
     * Titulo del episode.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Sinopsis del episode.
     */
    @SerializedName("overview")
    @Expose
    private String overview;

    /**
     * id interno de la api.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Codigo de produccion.
     */
    @SerializedName("production_code")
    @Expose
    private String productionCode;

    /**
     * Numero de la season a la que pertence el episode.
     */
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;

    /**
     * Path necesario para reconstruir la imagen.
     */
    @SerializedName("still_path")
    @Expose
    private String stillPath;

    /**
     * Nota de la api de TMDB.
     */
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    /**
     * Numero de votantes en la api.
     */
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    /**
     * Devuelve la fecha de la primera emision del episode.
     * @return String | Fecha de emision.
     */
    public String getAirDate() {
        return airDate;
    }

    /**
     * Devuelve una lista de la crew que ha participado.
     * @return List | Crew que participa.
     */
    public List<Crew> getCrew() {
        return crew;
    }

    /**
     * Devuelve el numero del episode.
     * @return int | Numero del episode.
     */
    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    /**
     * Devuelve una lista de la gueststars que han participado.
     * @return List | Crew que participa.
     */
    public List<GuestStar> getGuestStars() {
        return guestStars;
    }

    /**
     * Devuelve el titulo del episode.
     * @return String | Titulo del episode.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve la sinopsis del episode.
     * @return String | Sinopsis del episode.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Devuelve el id interno de la api.
     * @return int | id de la api.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Devuelve el codigo de produccion.
     * @return String | Codigo de produccion.
     */
    public String getProductionCode() {
        return productionCode;
    }

    /**
     * Devuelve el numero de la season.
     * @return int | Numero de la season.
     */
    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    /**
     * Devuelve el path necesario para reconstruir la imagen.
     * @return String | Path de la imagen.
     */
    public String getStillPath() {
        return stillPath;
    }

    /**
     * Devuelve la nota de la api.
     * @return double | Nota de la api.
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * Devuelve el numero de votantes de la api.
     * @return int | Votantes de la api.
     */
    public Integer getVoteCount() {
        return voteCount;
    }
}
