
package com.furyviewer.service.dto.TheMovieDB.Episode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gestiona la informacion de los ids externos proporcionada por TheMovieDatabase.
 */
public class EpisodeExternalIdDTO {
    /**
     * id de IMDB.
     */
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    /**
     * id secundadario de freebase.
     */
    @SerializedName("freebase_mid")
    @Expose
    private String freebaseMid;

    /**
     * id de freebase.
     */
    @SerializedName("freebase_id")
    @Expose
    private String freebaseId;

    /**
     * id de TVDB.
     */
    @SerializedName("tvdb_id")
    @Expose
    private Integer tvdbId;

    /**
     * id de TVRage.
     */
    @SerializedName("tvrage_id")
    @Expose
    private Integer tvrageId;

    /**
     * id interno de TMDB.
     */
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * Devuelve el id de IMDB.
     * @return String | id de IMDB.
     */
    public String getImdbId() {
        return imdbId;
    }

    /**
     * Devuelve el id secundario de Freebase.
     * @return String | id secundario de freebase.
     */
    public String getFreebaseMid() {
        return freebaseMid;
    }

    /**
     * Devuelve el id de freebase.
     * @return String | id de freebase.
     */
    public String getFreebaseId() {
        return freebaseId;
    }

    /**
     * Devuelve el id de TVDB.
     * @return int | id de TVDB.
     */
    public Integer getTvdbId() {
        return tvdbId;
    }

    /**
     * Devuelve el id de TVRage.
     * @return int | id de TVRage.
     */
    public Integer getTvrageId() {
        return tvrageId;
    }

    /**
     * Devuelve el id interno de la api.
     * @return int | id de TMDB.
     */
    public Integer getId() {
        return id;
    }
}
