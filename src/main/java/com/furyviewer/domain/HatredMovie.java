package com.furyviewer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A HatredMovie.
 */
@Entity
@Table(name = "hatred_movie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HatredMovie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hated")
    private Boolean hated;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private Movie movie;

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

    public HatredMovie hated(Boolean hated) {
        this.hated = hated;
        return this;
    }

    public void setHated(Boolean hated) {
        this.hated = hated;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public HatredMovie date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public HatredMovie movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public HatredMovie user(User user) {
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
        HatredMovie hatredMovie = (HatredMovie) o;
        if (hatredMovie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hatredMovie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HatredMovie{" +
            "id=" + getId() +
            ", hated='" + isHated() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
