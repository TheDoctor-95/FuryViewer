
package com.furyviewer.service.dto.OpenMovieDatabase;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de la season buscada desde OpenMovieDatabase.
 */
public class SeasonOmdbDTO {
    /**
     * Titulo de la series.
     */
    @SerializedName("Title")
    @Expose
    private String title;
    /**
     * Numero de la season.
     */
    @SerializedName("Season")
    @Expose
    private String season;

    /**
     * Numero total de seasons.
     */
    @SerializedName("totalSeasons")
    @Expose
    private String totalSeasons;

    /**
     * Lista con la informacion basica de episodes.
     */
    @SerializedName("Episodes")
    @Expose
    private List<Episode> episodes = null;

    /**
     * Comprobacion de la llamada a la api.
     */
    @SerializedName("Response")
    @Expose
    private String response;

    /**
     * Devuelve el titulo de la series.
     * @return String | Titulo de la series.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve el numero de la season actual.
     * @return String | Numero de la season.
     */
    public String getSeason() {
        return season;
    }

    /**
     * Devuelve el numero total de seasons.
     * @return String | Numero total de seasons.
     */
    public String getTotalSeasons() {
        return totalSeasons;
    }

    /**
     * Devuelve una lista con la informacion basica de episodes.
     * @return List | Lista de episodes.
     */
    public List<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * Devuelve una comprobacion de si la peticion a la api se ha podido realizar.
     * @return String | Comprobacion de la api.
     */
    public String getResponse() {
        return response;
    }
}
