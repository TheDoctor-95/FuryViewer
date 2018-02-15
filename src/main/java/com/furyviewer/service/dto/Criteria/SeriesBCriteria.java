package com.furyviewer.service.dto.Criteria;

import java.io.Serializable;
import com.furyviewer.domain.enumeration.SeriesEmittingEnum;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * SeriesBCriteria recibe todas las opciones de filtrado para realizar la busqueda inteligente desde SeriesResource.
 * @author Whoger
 */
public class SeriesBCriteria implements Serializable {
    /**
     * Class for filtering SeriesEmittingEnum
     */
    public static class SeriesEmittingEnumFilter extends Filter<SeriesEmittingEnum> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private SeriesEmittingEnumFilter state;

    private LocalDateFilter release_date;

    private StringFilter img_url;

    private StringFilter imdb_id;

    private StringFilter awards;

    private LongFilter countryId;

    public SeriesBCriteria() {
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public SeriesEmittingEnumFilter getState() {
        return state;
    }

    public void setState(SeriesEmittingEnumFilter state) {
        this.state = state;
    }

    public LocalDateFilter getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDateFilter release_date) {
        this.release_date = release_date;
    }

    public StringFilter getImg_url() {
        return img_url;
    }

    public void setImg_url(StringFilter img_url) {
        this.img_url = img_url;
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

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "SeriesBCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (release_date != null ? "release_date=" + release_date + ", " : "") +
                (img_url != null ? "img_url=" + img_url + ", " : "") +
                (imdb_id != null ? "imdb_id=" + imdb_id + ", " : "") +
                (awards != null ? "awards=" + awards + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
            "}";
    }

}
