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
 * Criteria class for the Artist entity. This class is used in ArtistResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /artists?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ArtistCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter birthdate;

    private StringFilter sex;

    private LocalDateFilter deathdate;

    private StringFilter imgUrl;

    private StringFilter imdb_id;

    private StringFilter awards;

    private StringFilter biography;

    private LongFilter countryId;

    private LongFilter artistTypeId;

    private LongFilter favoriteArtistId;

    private LongFilter hatredArtistId;

    private LongFilter movieDirectorId;

    private LongFilter movieScriptwriterId;

    private LongFilter movieMainActorId;

    private LongFilter movieSecondaryActorId;

    public ArtistCriteria() {
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

    public StringFilter getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(StringFilter imdb_id) {
        this.imdb_id = imdb_id;
    }

    public StringFilter getAwards() {
        return awards;
    }

    public void setAwards(StringFilter awards) {
        this.awards = awards;
    }

    public StringFilter getBiography() {
        return biography;
    }

    public void setBiography(StringFilter biography) {
        this.biography = biography;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public LongFilter getArtistTypeId() {
        return artistTypeId;
    }

    public void setArtistTypeId(LongFilter artistTypeId) {
        this.artistTypeId = artistTypeId;
    }

    public LongFilter getFavoriteArtistId() {
        return favoriteArtistId;
    }

    public void setFavoriteArtistId(LongFilter favoriteArtistId) {
        this.favoriteArtistId = favoriteArtistId;
    }

    public LongFilter getHatredArtistId() {
        return hatredArtistId;
    }

    public void setHatredArtistId(LongFilter hatredArtistId) {
        this.hatredArtistId = hatredArtistId;
    }

    public LongFilter getMovieDirectorId() {
        return movieDirectorId;
    }

    public void setMovieDirectorId(LongFilter movieDirectorId) {
        this.movieDirectorId = movieDirectorId;
    }

    public LongFilter getMovieScriptwriterId() {
        return movieScriptwriterId;
    }

    public void setMovieScriptwriterId(LongFilter movieScriptwriterId) {
        this.movieScriptwriterId = movieScriptwriterId;
    }

    public LongFilter getMovieMainActorId() {
        return movieMainActorId;
    }

    public void setMovieMainActorId(LongFilter movieMainActorId) {
        this.movieMainActorId = movieMainActorId;
    }

    public LongFilter getMovieSecondaryActorId() {
        return movieSecondaryActorId;
    }

    public void setMovieSecondaryActorId(LongFilter movieSecondaryActorId) {
        this.movieSecondaryActorId = movieSecondaryActorId;
    }

    @Override
    public String toString() {
        return "ArtistCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (deathdate != null ? "deathdate=" + deathdate + ", " : "") +
                (imgUrl != null ? "imgUrl=" + imgUrl + ", " : "") +
                (imdb_id != null ? "imdb_id=" + imdb_id + ", " : "") +
                (awards != null ? "awards=" + awards + ", " : "") +
                (biography != null ? "biography=" + biography + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
                (artistTypeId != null ? "artistTypeId=" + artistTypeId + ", " : "") +
                (favoriteArtistId != null ? "favoriteArtistId=" + favoriteArtistId + ", " : "") +
                (hatredArtistId != null ? "hatredArtistId=" + hatredArtistId + ", " : "") +
                (movieDirectorId != null ? "movieDirectorId=" + movieDirectorId + ", " : "") +
                (movieScriptwriterId != null ? "movieScriptwriterId=" + movieScriptwriterId + ", " : "") +
                (movieMainActorId != null ? "movieMainActorId=" + movieMainActorId + ", " : "") +
                (movieSecondaryActorId != null ? "movieSecondaryActorId=" + movieSecondaryActorId + ", " : "") +
            "}";
    }

}
