package com.furyviewer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.furyviewer.domain.enumeration.MovieStatsEnum;

/**
 * A MovieStats.
 */
@Entity
@Table(name = "movie_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovieStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MovieStatsEnum status;

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

    public MovieStatsEnum getStatus() {
        return status;
    }

    public MovieStats status(MovieStatsEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(MovieStatsEnum status) {
        this.status = status;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public MovieStats date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public MovieStats movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public MovieStats user(User user) {
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
        MovieStats movieStats = (MovieStats) o;
        if (movieStats.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieStats.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieStats{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
