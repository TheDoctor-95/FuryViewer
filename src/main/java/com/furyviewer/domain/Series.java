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

import com.furyviewer.domain.enumeration.SeriesEmittingEnum;

/**
 * A Series.
 */
@Entity
@Table(name = "series")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Series extends Multimedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private SeriesEmittingEnum state;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "imdb_id")
    private String imdb_id;

    @Column(name = "awards")
    private String awards;

    @ManyToOne
    private Company company;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "series_genre",
               joinColumns = @JoinColumn(name="series_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="genres_id", referencedColumnName="id"))
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "series")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReviewSeries> reviews = new HashSet<>();

    @OneToMany(mappedBy = "series")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FavouriteSeries> favoriteSeries = new HashSet<>();

    @OneToMany(mappedBy = "series")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RateSeries> rateSeries = new HashSet<>();

    @OneToMany(mappedBy = "serie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SeriesStats> stats = new HashSet<>();

    @OneToMany(mappedBy = "series")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HatredSeries> hatedSeries = new HashSet<>();

    @OneToMany(mappedBy = "series")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Season> seasons = new HashSet<>();

    @OneToMany(mappedBy = "series")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Social> socials = new HashSet<>();

    @ManyToOne
    private Country country;

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

    public Series name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Series description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SeriesEmittingEnum getState() {
        return state;
    }

    public Series state(SeriesEmittingEnum state) {
        this.state = state;
        return this;
    }

    public void setState(SeriesEmittingEnum state) {
        this.state = state;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Series releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Series imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public Series imdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
        return this;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getAwards() {
        return awards;
    }

    public Series awards(String awards) {
        this.awards = awards;
        return this;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public Company getCompany() {
        return company;
    }

    public Series company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Series genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public Series addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getSeries().add(this);
        return this;
    }

    public Series removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getSeries().remove(this);
        return this;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<ReviewSeries> getReviews() {
        return reviews;
    }

    public Series reviews(Set<ReviewSeries> reviewSeries) {
        this.reviews = reviewSeries;
        return this;
    }

    public Series addReview(ReviewSeries reviewSeries) {
        this.reviews.add(reviewSeries);
        reviewSeries.setSeries(this);
        return this;
    }

    public Series removeReview(ReviewSeries reviewSeries) {
        this.reviews.remove(reviewSeries);
        reviewSeries.setSeries(null);
        return this;
    }

    public void setReviews(Set<ReviewSeries> reviewSeries) {
        this.reviews = reviewSeries;
    }

    public Set<FavouriteSeries> getFavoriteSeries() {
        return favoriteSeries;
    }

    public Series favoriteSeries(Set<FavouriteSeries> favouriteSeries) {
        this.favoriteSeries = favouriteSeries;
        return this;
    }

    public Series addFavoriteSerie(FavouriteSeries favouriteSeries) {
        this.favoriteSeries.add(favouriteSeries);
        favouriteSeries.setSeries(this);
        return this;
    }

    public Series removeFavoriteSerie(FavouriteSeries favouriteSeries) {
        this.favoriteSeries.remove(favouriteSeries);
        favouriteSeries.setSeries(null);
        return this;
    }

    public void setFavoriteSeries(Set<FavouriteSeries> favouriteSeries) {
        this.favoriteSeries = favouriteSeries;
    }

    public Set<RateSeries> getRateSeries() {
        return rateSeries;
    }

    public Series rateSeries(Set<RateSeries> rateSeries) {
        this.rateSeries = rateSeries;
        return this;
    }

    public Series addRateSerie(RateSeries rateSeries) {
        this.rateSeries.add(rateSeries);
        rateSeries.setSeries(this);
        return this;
    }

    public Series removeRateSerie(RateSeries rateSeries) {
        this.rateSeries.remove(rateSeries);
        rateSeries.setSeries(null);
        return this;
    }

    public void setRateSeries(Set<RateSeries> rateSeries) {
        this.rateSeries = rateSeries;
    }

    public Set<SeriesStats> getStats() {
        return stats;
    }

    public Series stats(Set<SeriesStats> seriesStats) {
        this.stats = seriesStats;
        return this;
    }

    public Series addStat(SeriesStats seriesStats) {
        this.stats.add(seriesStats);
        seriesStats.setSerie(this);
        return this;
    }

    public Series removeStat(SeriesStats seriesStats) {
        this.stats.remove(seriesStats);
        seriesStats.setSerie(null);
        return this;
    }

    public void setStats(Set<SeriesStats> seriesStats) {
        this.stats = seriesStats;
    }

    public Set<HatredSeries> getHatedSeries() {
        return hatedSeries;
    }

    public Series hatedSeries(Set<HatredSeries> hatredSeries) {
        this.hatedSeries = hatredSeries;
        return this;
    }

    public Series addHatedSerie(HatredSeries hatredSeries) {
        this.hatedSeries.add(hatredSeries);
        hatredSeries.setSeries(this);
        return this;
    }

    public Series removeHatedSerie(HatredSeries hatredSeries) {
        this.hatedSeries.remove(hatredSeries);
        hatredSeries.setSeries(null);
        return this;
    }

    public void setHatedSeries(Set<HatredSeries> hatredSeries) {
        this.hatedSeries = hatredSeries;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public Series seasons(Set<Season> seasons) {
        this.seasons = seasons;
        return this;
    }

    public Series addSeason(Season season) {
        this.seasons.add(season);
        season.setSeries(this);
        return this;
    }

    public Series removeSeason(Season season) {
        this.seasons.remove(season);
        season.setSeries(null);
        return this;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    public Set<Social> getSocials() {
        return socials;
    }

    public Series socials(Set<Social> socials) {
        this.socials = socials;
        return this;
    }

    public Series addSocial(Social social) {
        this.socials.add(social);
        social.setSeries(this);
        return this;
    }

    public Series removeSocial(Social social) {
        this.socials.remove(social);
        social.setSeries(null);
        return this;
    }

    public void setSocials(Set<Social> socials) {
        this.socials = socials;
    }

    public Country getCountry() {
        return country;
    }

    public Series country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        Series series = (Series) o;
        if (series.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), series.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Series{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", imdb_id='" + getImdb_id() + "'" +
            ", awards='" + getAwards() + "'" +
            "}";
    }
}
