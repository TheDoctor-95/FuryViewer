package com.furyviewer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A HatredArtist.
 */
@Entity
@Table(name = "hatred_artist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HatredArtist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hated")
    private Boolean hated;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHated() {
        return hated;
    }

    public HatredArtist hated(Boolean hated) {
        this.hated = hated;
        return this;
    }

    public void setHated(Boolean hated) {
        this.hated = hated;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public HatredArtist date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Artist getArtist() {
        return artist;
    }

    public HatredArtist artist(Artist artist) {
        this.artist = artist;
        return this;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public User getUser() {
        return user;
    }

    public HatredArtist user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        HatredArtist hatredArtist = (HatredArtist) o;
        if (hatredArtist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hatredArtist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HatredArtist{" +
            "id=" + getId() +
            ", hated='" + isHated() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
