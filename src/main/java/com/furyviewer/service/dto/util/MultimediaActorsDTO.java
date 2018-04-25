package com.furyviewer.service.dto.util;

import java.time.LocalDate;

public class MultimediaActorsDTO {

    private Long id;
    private String title;
    private LocalDate releaseDate;
    private String type;
    private String urlCartel;

    public MultimediaActorsDTO() {
    }

    public MultimediaActorsDTO(Long id, String title, LocalDate releaseDate, String type, String urlCartel) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.type = type;
        this.urlCartel = urlCartel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlCartel() {
        return urlCartel;
    }

    public void setUrlCartel(String urlCartel) {
        this.urlCartel = urlCartel;
    }

}
