package com.furyviewer.service.dto.Criteria;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * MovieBCriteria recibe todas las opciones de filtrado para realizar la búsqueda inteligente desde MovieResource.
 * @author Whoger
 */
public class MovieBCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter release_date;

    private StringFilter description;

    private DoubleFilter duration;

    private StringFilter imdb_id_external_api;

    private StringFilter img_url;

    private StringFilter awards;

    private LocalDateFilter dvd_release;

    private LongFilter countryId;

    private LongFilter companyId;

    public MovieBCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LocalDateFilter getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDateFilter release_date) {
        this.release_date = release_date;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public DoubleFilter getDuration() {
        return duration;
    }

    public void setDuration(DoubleFilter duration) {
        this.duration = duration;
    }

    public StringFilter getImdb_id_external_api() {
        return imdb_id_external_api;
    }

    public void setImdb_id_external_api(StringFilter imdb_id_external_api) {
        this.imdb_id_external_api = imdb_id_external_api;
    }

    public StringFilter getImg_url() {
        return img_url;
    }

    public void setImg_url(StringFilter img_url) {
        this.img_url = img_url;
    }

    public StringFilter getAwards() {
        return awards;
    }

    public void setAwards(StringFilter awards) {
        this.awards = awards;
    }

    public LocalDateFilter getDvd_release() {
        return dvd_release;
    }

    public void setDvd_release(LocalDateFilter dvd_release) {
        this.dvd_release = dvd_release;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "MovieBCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (release_date != null ? "release_date=" + release_date + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (duration != null ? "duration=" + duration + ", " : "") +
                (imdb_id_external_api != null ? "imdb_id_external_api=" + imdb_id_external_api + ", " : "") +
                (img_url != null ? "img_url=" + img_url + ", " : "") +
                (awards != null ? "awards=" + awards + ", " : "") +
                (dvd_release != null ? "dvd_release=" + dvd_release + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
