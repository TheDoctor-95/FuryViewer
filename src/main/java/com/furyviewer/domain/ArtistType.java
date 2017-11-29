package com.furyviewer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.furyviewer.domain.enumeration.ArtistTypeEnum;

/**
 * A ArtistType.
 */
@Entity
@Table(name = "artist_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArtistType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ArtistTypeEnum name;

    @ManyToMany(mappedBy = "artistTypes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Artist> artists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArtistTypeEnum getName() {
        return name;
    }

    public ArtistType name(ArtistTypeEnum name) {
        this.name = name;
        return this;
    }

    public void setName(ArtistTypeEnum name) {
        this.name = name;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public ArtistType artists(Set<Artist> artists) {
        this.artists = artists;
        return this;
    }

    public ArtistType addArtist(Artist artist) {
        this.artists.add(artist);
        artist.getArtistTypes().add(this);
        return this;
    }

    public ArtistType removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.getArtistTypes().remove(this);
        return this;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
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
        ArtistType artistType = (ArtistType) o;
        if (artistType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), artistType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArtistType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
