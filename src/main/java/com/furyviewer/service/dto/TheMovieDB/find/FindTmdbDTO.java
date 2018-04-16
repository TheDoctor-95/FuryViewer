
package com.furyviewer.service.dto.TheMovieDB.find;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindTmdbDTO {

    @SerializedName("movie_results")
    @Expose
    private List<Object> movieResults = null;
    @SerializedName("person_results")
    @Expose
    private List<Object> personResults = null;
    @SerializedName("tv_results")
    @Expose
    private List<TvResult> tvResults = null;
    @SerializedName("tv_episode_results")
    @Expose
    private List<Object> tvEpisodeResults = null;
    @SerializedName("tv_season_results")
    @Expose
    private List<Object> tvSeasonResults = null;

    public List<Object> getMovieResults() {
        return movieResults;
    }

    public void setMovieResults(List<Object> movieResults) {
        this.movieResults = movieResults;
    }

    public List<Object> getPersonResults() {
        return personResults;
    }

    public void setPersonResults(List<Object> personResults) {
        this.personResults = personResults;
    }

    public List<TvResult> getTvResults() {
        return tvResults;
    }

    public void setTvResults(List<TvResult> tvResults) {
        this.tvResults = tvResults;
    }

    public List<Object> getTvEpisodeResults() {
        return tvEpisodeResults;
    }

    public void setTvEpisodeResults(List<Object> tvEpisodeResults) {
        this.tvEpisodeResults = tvEpisodeResults;
    }

    public List<Object> getTvSeasonResults() {
        return tvSeasonResults;
    }

    public void setTvSeasonResults(List<Object> tvSeasonResults) {
        this.tvSeasonResults = tvSeasonResults;
    }

}
