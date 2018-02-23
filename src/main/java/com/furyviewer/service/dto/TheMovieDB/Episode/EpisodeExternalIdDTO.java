
package com.furyviewer.service.dto.TheMovieDB.Episode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeExternalIdDTO {

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("freebase_mid")
    @Expose
    private String freebaseMid;
    @SerializedName("freebase_id")
    @Expose
    private String freebaseId;
    @SerializedName("tvdb_id")
    @Expose
    private Integer tvdbId;
    @SerializedName("tvrage_id")
    @Expose
    private Integer tvrageId;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getFreebaseMid() {
        return freebaseMid;
    }

    public void setFreebaseMid(String freebaseMid) {
        this.freebaseMid = freebaseMid;
    }

    public String getFreebaseId() {
        return freebaseId;
    }

    public void setFreebaseId(String freebaseId) {
        this.freebaseId = freebaseId;
    }

    public Integer getTvdbId() {
        return tvdbId;
    }

    public void setTvdbId(Integer tvdbId) {
        this.tvdbId = tvdbId;
    }

    public Integer getTvrageId() {
        return tvrageId;
    }

    public void setTvrageId(Integer tvrageId) {
        this.tvrageId = tvrageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
