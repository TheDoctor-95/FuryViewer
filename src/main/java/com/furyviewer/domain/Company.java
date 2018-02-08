package com.furyviewer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "funding_date")
    private LocalDate fundingDate;

    @Column(name = "clossing_date")
    private LocalDate clossingDate;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "url_web")
    private String urlWeb;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Movie> movies = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Series> series = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getFundingDate() {
        return fundingDate;
    }

    public Company fundingDate(LocalDate fundingDate) {
        this.fundingDate = fundingDate;
        return this;
    }

    public void setFundingDate(LocalDate fundingDate) {
        this.fundingDate = fundingDate;
    }

    public LocalDate getClossingDate() {
        return clossingDate;
    }

    public Company clossingDate(LocalDate clossingDate) {
        this.clossingDate = clossingDate;
        return this;
    }

    public void setClossingDate(LocalDate clossingDate) {
        this.clossingDate = clossingDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Company imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public Company urlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
        return this;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
    }

    public Country getCountry() {
        return country;
    }

    public Company country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public Company movies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public Company addMovie(Movie movie) {
        this.movies.add(movie);
        movie.setCompany(this);
        return this;
    }

    public Company removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.setCompany(null);
        return this;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Set<Series> getSeries() {
        return series;
    }

    public Company series(Set<Series> series) {
        this.series = series;
        return this;
    }

    public Company addSerie(Series series) {
        this.series.add(series);
        series.setCompany(this);
        return this;
    }

    public Company removeSerie(Series series) {
        this.series.remove(series);
        series.setCompany(null);
        return this;
    }

    public void setSeries(Set<Series> series) {
        this.series = series;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fundingDate='" + getFundingDate() + "'" +
            ", clossingDate='" + getClossingDate() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", urlWeb='" + getUrlWeb() + "'" +
            "}";
    }
}
