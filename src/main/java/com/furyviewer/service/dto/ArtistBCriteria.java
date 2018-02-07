package com.furyviewer.service.dto;

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
 * Criteria class for the ArtistB entity. This class is used in ArtistBResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /artist-bs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
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
