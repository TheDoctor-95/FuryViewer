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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A Artist.
 */
@Entity
@Table(name = "artist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "sex")
    private String sex;

    @Column(name = "deathdate")
    private LocalDate deathdate;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "imdb_id")
    private String imdb_id;

    @Column(name = "awards")
    private String awards;

    @Column(name = "biography")
    private String biography;

    @ManyToOne
    private Country country;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "artist_artist_type",
               joinColumns = @JoinColumn(name="artists_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="artist_types_id", referencedColumnName="id"))
    private Set<ArtistType> artistTypes = new HashSet<>();

    @OneToMany(mappedBy = "artist")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FavouriteArtist> favoriteArtists = new HashSet<>();

    @OneToMany(mappedBy = "artist")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HatredArtist> hatredArtists = new HashSet<>();

    @OneToMany(mappedBy = "director")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Movie> movieDirectors = new HashSet<>();

    @OneToMany(mappedBy = "scriptwriter")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Movie> movieScriptwriters = new HashSet<>();

    @ManyToMany(mappedBy = "actorMains")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Movie> movieMainActors = new HashSet<>();

    @ManyToMany(mappedBy = "actorSecondaries")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Movie> movieSecondaryActors = new HashSet<>();

    @ManyToMany(mappedBy = "actors")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Episode> episodes = new HashSet<>();

    @OneToMany(mappedBy = "director")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Episode> episodeDirectors = new HashSet<>();

    @OneToMany(mappedBy = "scriptwriter")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Episode> episodesScriptwriters = new HashSet<>();



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

    public Artist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Artist birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public Artist sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getDeathdate() {
        return deathdate;
    }

    public Artist deathdate(LocalDate deathdate) {
        this.deathdate = deathdate;
        return this;
    }

    public void setDeathdate(LocalDate deathdate) {
        this.deathdate = deathdate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Artist imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public Artist imdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
        return this;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getAwards() {
        return awards;
    }

    public Artist awards(String awards) {
        this.awards = awards;
        return this;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getBiography() {
        return biography;
    }

    public Artist biography(String biography) {
        this.biography = biography;
        return this;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Country getCountry() {
        return country;
    }

    public Artist country(Country country) {
        this.country = country;
        return this;
    }

    public Set<Episode> getEpisodes() {
                return episodes;
            }

        public void setEpisodes(Set<Episode> episodes) {
                this.episodes = episodes;
           }

    public Set<Episode> getEpisodeDirectors() {
                return episodeDirectors;
            }

        public void setEpisodeDirectors(Set<Episode> episodeDirectors) {
               this.episodeDirectors = episodeDirectors;
            }

       public Set<Episode> getEpisodesScriptwriters() {
                return episodesScriptwriters;
           }

        public void setEpisodesScriptwriters(Set<Episode> episodesScriptwriters) {
                this.episodesScriptwriters = episodesScriptwriters;
            }


    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<ArtistType> getArtistTypes() {
        return artistTypes;
    }

    public Artist artistTypes(Set<ArtistType> artistTypes) {
        this.artistTypes = artistTypes;
        return this;
    }

    public Artist addArtistType(ArtistType artistType) {
        this.artistTypes.add(artistType);
        artistType.getArtists().add(this);
        return this;
    }

    public Artist removeArtistType(ArtistType artistType) {
        this.artistTypes.remove(artistType);
        artistType.getArtists().remove(this);
        return this;
    }

    public void setArtistTypes(Set<ArtistType> artistTypes) {
        this.artistTypes = artistTypes;
    }

    public Set<FavouriteArtist> getFavoriteArtists() {
        return favoriteArtists;
    }

    public Artist favoriteArtists(Set<FavouriteArtist> favouriteArtists) {
        this.favoriteArtists = favouriteArtists;
        return this;
    }

    public Artist addFavoriteArtist(FavouriteArtist favouriteArtist) {
        this.favoriteArtists.add(favouriteArtist);
        favouriteArtist.setArtist(this);
        return this;
    }

    public Artist removeFavoriteArtist(FavouriteArtist favouriteArtist) {
        this.favoriteArtists.remove(favouriteArtist);
        favouriteArtist.setArtist(null);
        return this;
    }

    public void setFavoriteArtists(Set<FavouriteArtist> favouriteArtists) {
        this.favoriteArtists = favouriteArtists;
    }

    public Set<HatredArtist> getHatredArtists() {
        return hatredArtists;
    }

    public Artist hatredArtists(Set<HatredArtist> hatredArtists) {
        this.hatredArtists = hatredArtists;
        return this;
    }

    public Artist addHatredArtist(HatredArtist hatredArtist) {
        this.hatredArtists.add(hatredArtist);
        hatredArtist.setArtist(this);
        return this;
    }

    public Artist removeHatredArtist(HatredArtist hatredArtist) {
        this.hatredArtists.remove(hatredArtist);
        hatredArtist.setArtist(null);
        return this;
    }

    public void setHatredArtists(Set<HatredArtist> hatredArtists) {
        this.hatredArtists = hatredArtists;
    }

    public Set<Movie> getMovieDirectors() {
        return movieDirectors;
    }

    public Artist movieDirectors(Set<Movie> movies) {
        this.movieDirectors = movies;
        return this;
    }

    public Artist addMovieDirector(Movie movie) {
        this.movieDirectors.add(movie);
        movie.setDirector(this);
        return this;
    }

    public Artist removeMovieDirector(Movie movie) {
        this.movieDirectors.remove(movie);
        movie.setDirector(null);
        return this;
    }

    public void setMovieDirectors(Set<Movie> movies) {
        this.movieDirectors = movies;
    }

    public Set<Movie> getMovieScriptwriters() {
        return movieScriptwriters;
    }

    public Artist movieScriptwriters(Set<Movie> movies) {
        this.movieScriptwriters = movies;
        return this;
    }

    public Artist addMovieScriptwriter(Movie movie) {
        this.movieScriptwriters.add(movie);
        movie.setScriptwriter(this);
        return this;
    }

    public Artist removeMovieScriptwriter(Movie movie) {
        this.movieScriptwriters.remove(movie);
        movie.setScriptwriter(null);
        return this;
    }

    public void setMovieScriptwriters(Set<Movie> movies) {
        this.movieScriptwriters = movies;
    }

    public Set<Movie> getMovieMainActors() {
        return movieMainActors;
    }

    public Artist movieMainActors(Set<Movie> movies) {
        this.movieMainActors = movies;
        return this;
    }

    public Artist addMovieMainActor(Movie movie) {
        this.movieMainActors.add(movie);
        movie.getActorMains().add(this);
        return this;
    }

    public Artist removeMovieMainActor(Movie movie) {
        this.movieMainActors.remove(movie);
        movie.getActorMains().remove(this);
        return this;
    }

    public void setMovieMainActors(Set<Movie> movies) {
        this.movieMainActors = movies;
    }

    public Set<Movie> getMovieSecondaryActors() {
        return movieSecondaryActors;
    }

    public Artist movieSecondaryActors(Set<Movie> movies) {
        this.movieSecondaryActors = movies;
        return this;
    }

    public Artist addMovieSecondaryActor(Movie movie) {
        this.movieSecondaryActors.add(movie);
        movie.getActorSecondaries().add(this);
        return this;
    }

    public Artist removeMovieSecondaryActor(Movie movie) {
        this.movieSecondaryActors.remove(movie);
        movie.getActorSecondaries().remove(this);
        return this;
    }

    public void setMovieSecondaryActors(Set<Movie> movies) {
        this.movieSecondaryActors = movies;
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
        Artist artist = (Artist) o;
        if (artist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), artist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Artist{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", sex='" + getSex() + "'" +
            ", deathdate='" + getDeathdate() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", imdb_id='" + getImdb_id() + "'" +
            ", awards='" + getAwards() + "'" +
            ", biography='" + getBiography() + "'" +
            "}";
    }

}
