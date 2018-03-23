package com.furyviewer.service.dto.util;

public class EpisodesHomeDTO {

    private Long id;
    private String titleEpisode;
    private String titleSeries;
    private int seasonNumber;
    private int episodeNumber;
    private String urlCartel;


    public EpisodesHomeDTO() {
    }

    public EpisodesHomeDTO(Long id, String titleEpisode, String titleSeries, int seasonNumber, int episodeNumber, String urlCartel) {
        this.id = id;
        this.titleEpisode = titleEpisode;
        this.titleSeries = titleSeries;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.urlCartel = urlCartel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleEpisode() {
        return titleEpisode;
    }

    public void setTitleEpisode(String titleEpisode) {
        this.titleEpisode = titleEpisode;
    }

    public String getSerieTitle() {
        return titleSeries;
    }

    public void setTitleSeries(String titleSeries) {
        this.titleSeries = titleSeries;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getUrlCartel() {
        return urlCartel;
    }

    public void setUrlCartel(String urlCartel) {
        this.urlCartel = urlCartel;
    }
}
