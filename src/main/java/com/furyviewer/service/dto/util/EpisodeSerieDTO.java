package com.furyviewer.service.dto.util;

import java.time.LocalDate;

public class EpisodeSerieDTO {
    private Long id;
    private int number;
    private String title;
    private LocalDate releaseDate;
    private String plot;
    private double duration;
    private boolean seen;

    public EpisodeSerieDTO() {
    }

    public EpisodeSerieDTO(Long id, int number, String title, LocalDate releaseDate, String plot, double duration, boolean seen) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.releaseDate = releaseDate;
        this.plot = plot;
        this.duration = duration;
        this.seen = seen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
