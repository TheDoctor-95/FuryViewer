
package com.furyviewer.service.dto.OpenMovieDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion simple de un episode buscada desde OpenMovieDatabase.
 */
public class Episode {
    /**
     * Titulo del episode.
     */
    @SerializedName("Title")
    @Expose
    private String title;

    /**
     * Fecha de primera emision.
     */
    @SerializedName("Released")
    @Expose
    private String released;

    /**
     * Numero del episode.
     */
    @SerializedName("Episode")
    @Expose
    private String episode;

    /**
     * Nota en IMDB.
     */
    @SerializedName("imdbRating")
    @Expose
    private String imdbRating;

    /**
     * id de IMDB.
     */
    @SerializedName("imdbID")
    @Expose
    private String imdbID;

    /**
     * Devuelve el titulo del episode.
     * @return String | Titulo del episode.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Devuelve la fecha de primera emision.
     * @return String | Fecha de primer emision.
     */
    public String getReleased() {
        return released;
    }

    /**
     * Devuelve el numero del episode.
     * @return String | Numero del episode.
     */
    public String getEpisode() {
        return episode;
    }

    /**
     * Devuelve la nota en IMDB.
     * @return String | Nota en IMDB.
     */
    public String getImdbRating() {
        return imdbRating;
    }

    /**
     * Devuelve el id en IMDB.
     * @return String | id de IMDB.
     */
    public String getImdbID() {
        return imdbID;
    }
}
