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
 * ArtistBCriteria recibe todas las opciones de filtrado para realizar la búsqueda inteligente desde ArtistBResource.
 * @author Whoger
 */
public class ArtistBCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter birthdate;

    private StringFilter sex;

    private LocalDateFilter deathdate;

    private StringFilter imgUrl;

    private StringFilter imdbId;

    private StringFilter awards;

    private LongFilter countryId;

    public ArtistBCriteria() {
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

    public LocalDateFilter getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateFilter birthdate) {
        this.birthdate = birthdate;
    }

    public StringFilter getSex() {
        return sex;
    }

    public void setSex(StringFilter sex) {
        this.sex = sex;
    }

    public LocalDateFilter getDeathdate() {
        return deathdate;
    }

    public void setDeathdate(LocalDateFilter deathdate) {
        this.deathdate = deathdate;
    }

    public StringFilter getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(StringFilter imgUrl) {
        this.imgUrl = imgUrl;
    }

    public StringFilter getImdbId() {
        return imdbId;
    }

    public void setImdbId(StringFilter imdbId) {
        this.imdbId = imdbId;
    }

    public StringFilter getAwards() {
        return awards;
    }

    public void setAwards(StringFilter awards) {
        this.awards = awards;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "ArtistBCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (deathdate != null ? "deathdate=" + deathdate + ", " : "") +
                (imgUrl != null ? "imgUrl=" + imgUrl + ", " : "") +
                (imdbId != null ? "imdbId=" + imdbId + ", " : "") +
                (awards != null ? "awards=" + awards + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
            "}";
    }

}
