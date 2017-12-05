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
 * A Movie.
 */
@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @Column(name = "duration")
    private Double duration;

    @ManyToOne
    private Artist director;

    @ManyToOne
    private Artist scriptwriter;

    @ManyToOne
    private Company company;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "movie_genre",
               joinColumns = @JoinColumn(name="movies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="genres_id", referencedColumnName="id"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "movie_actor_main",
               joinColumns = @JoinColumn(name="movies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="actor_mains_id", referencedColumnName="id"))
    private Set<Artist> actorMains = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "movie_actor_secondary",
               joinColumns = @JoinColumn(name="movies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="actor_secondaries_id", referencedColumnName="id"))
    private Set<Artist> actorSecondaries = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReviewMovie> reviews = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FavouriteMovie> favoriteMovies = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RateMovie> rateMovies = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MovieStats> stats = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HatredMovie> hatedMovies = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Social> socials = new HashSet<>();


    public Movie() {
    }

    public Movie(String title, Long id) {

        this.name=title;
        this.id=id;
    }

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

    public Movie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Movie releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public Movie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImg() {
        return img;
    }

    public Movie img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public Movie imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Double getDuration() {
        return duration;
    }

    public Movie duration(Double duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Artist getDirector() {
        return director;
    }

    public Movie director(Artist artist) {
        this.director = artist;
        return this;
    }

    public void setDirector(Artist artist) {
        this.director = artist;
    }

    public Artist getScriptwriter() {
        return scriptwriter;
    }

    public Movie scriptwriter(Artist artist) {
        this.scriptwriter = artist;
        return this;
    }

    public void setScriptwriter(Artist artist) {
        this.scriptwriter = artist;
    }

    public Company getCompany() {
        return company;
    }

    public Movie company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Movie genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public Movie addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getMovies().add(this);
        return this;
    }

    public Movie removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getMovies().remove(this);
        return this;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Artist> getActorMains() {
        return actorMains;
    }

    public Movie actorMains(Set<Artist> artists) {
        this.actorMains = artists;
        return this;
    }

    public Movie addActorMain(Artist artist) {
        this.actorMains.add(artist);
        artist.getMovieMainActors().add(this);
        return this;
    }

    public Movie removeActorMain(Artist artist) {
        this.actorMains.remove(artist);
        artist.getMovieMainActors().remove(this);
        return this;
    }

    public void setActorMains(Set<Artist> artists) {
        this.actorMains = artists;
    }

    public Set<Artist> getActorSecondaries() {
        return actorSecondaries;
    }

    public Movie actorSecondaries(Set<Artist> artists) {
        this.actorSecondaries = artists;
        return this;
    }

    public Movie addActorSecondary(Artist artist) {
        this.actorSecondaries.add(artist);
        artist.getMovieSecondaryActors().add(this);
        return this;
    }

    public Movie removeActorSecondary(Artist artist) {
        this.actorSecondaries.remove(artist);
        artist.getMovieSecondaryActors().remove(this);
        return this;
    }

    public void setActorSecondaries(Set<Artist> artists) {
        this.actorSecondaries = artists;
    }

    public Set<ReviewMovie> getReviews() {
        return reviews;
    }

    public Movie reviews(Set<ReviewMovie> reviewMovies) {
        this.reviews = reviewMovies;
        return this;
    }

    public Movie addReview(ReviewMovie reviewMovie) {
        this.reviews.add(reviewMovie);
        reviewMovie.setMovie(this);
        return this;
    }

    public Movie removeReview(ReviewMovie reviewMovie) {
        this.reviews.remove(reviewMovie);
        reviewMovie.setMovie(null);
        return this;
    }

    public void setReviews(Set<ReviewMovie> reviewMovies) {
        this.reviews = reviewMovies;
    }

    public Set<FavouriteMovie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Movie favoriteMovies(Set<FavouriteMovie> favouriteMovies) {
        this.favoriteMovies = favouriteMovies;
        return this;
    }

    public Movie addFavoriteMovie(FavouriteMovie favouriteMovie) {
        this.favoriteMovies.add(favouriteMovie);
        favouriteMovie.setMovie(this);
        return this;
    }

    public Movie removeFavoriteMovie(FavouriteMovie favouriteMovie) {
        this.favoriteMovies.remove(favouriteMovie);
        favouriteMovie.setMovie(null);
        return this;
    }

    public void setFavoriteMovies(Set<FavouriteMovie> favouriteMovies) {
        this.favoriteMovies = favouriteMovies;
    }

    public Set<RateMovie> getRateMovies() {
        return rateMovies;
    }

    public Movie rateMovies(Set<RateMovie> rateMovies) {
        this.rateMovies = rateMovies;
        return this;
    }

    public Movie addRateMovie(RateMovie rateMovie) {
        this.rateMovies.add(rateMovie);
        rateMovie.setMovie(this);
        return this;
    }

    public Movie removeRateMovie(RateMovie rateMovie) {
        this.rateMovies.remove(rateMovie);
        rateMovie.setMovie(null);
        return this;
    }

    public void setRateMovies(Set<RateMovie> rateMovies) {
        this.rateMovies = rateMovies;
    }

    public Set<MovieStats> getStats() {
        return stats;
    }

    public Movie stats(Set<MovieStats> movieStats) {
        this.stats = movieStats;
        return this;
    }

    public Movie addStat(MovieStats movieStats) {
        this.stats.add(movieStats);
        movieStats.setMovie(this);
        return this;
    }

    public Movie removeStat(MovieStats movieStats) {
        this.stats.remove(movieStats);
        movieStats.setMovie(null);
        return this;
    }

    public void setStats(Set<MovieStats> movieStats) {
        this.stats = movieStats;
    }

    public Set<HatredMovie> getHatedMovies() {
        return hatedMovies;
    }

    public Movie hatedMovies(Set<HatredMovie> hatredMovies) {
        this.hatedMovies = hatredMovies;
        return this;
    }

    public Movie addHatedMovie(HatredMovie hatredMovie) {
        this.hatedMovies.add(hatredMovie);
        hatredMovie.setMovie(this);
        return this;
    }

    public Movie removeHatedMovie(HatredMovie hatredMovie) {
        this.hatedMovies.remove(hatredMovie);
        hatredMovie.setMovie(null);
        return this;
    }

    public void setHatedMovies(Set<HatredMovie> hatredMovies) {
        this.hatedMovies = hatredMovies;
    }

    public Set<Social> getSocials() {
        return socials;
    }

    public Movie socials(Set<Social> socials) {
        this.socials = socials;
        return this;
    }

    public Movie addSocial(Social social) {
        this.socials.add(social);
        social.setMovie(this);
        return this;
    }

    public Movie removeSocial(Social social) {
        this.socials.remove(social);
        social.setMovie(null);
        return this;
    }

    public void setSocials(Set<Social> socials) {
        this.socials = socials;
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
        Movie movie = (Movie) o;
        if (movie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + imgContentType + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }
}
