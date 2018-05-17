package com.furyviewer.service.dto.util;

import com.furyviewer.domain.Episode;

import java.time.LocalDate;
import java.util.List;

public class CalendarDTO {

    private LocalDate releaseDate;
    private List<EpisodesHomeDTO> episodes;

    public CalendarDTO(LocalDate releaseDate, List<EpisodesHomeDTO> episodes) {
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

    public List<EpisodesHomeDTO> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodesHomeDTO> episodes) {
        this.episodes = episodes;
    }
}
