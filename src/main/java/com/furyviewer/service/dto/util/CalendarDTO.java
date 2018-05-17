package com.furyviewer.service.dto.util;

import com.furyviewer.domain.Episode;

import java.time.LocalDate;
import java.util.List;

public class CalendarDTO {

    private LocalDate releaseDate;
    private List<Episode> episodes;

    public CalendarDTO(LocalDate releaseDate, List<Episode> episodes) {
        this.releaseDate = releaseDate;
        this.episodes = episodes;
    }

    public CalendarDTO() {
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
