package com.furyviewer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.furyviewer.domain.enumeration.SeriesStatsEnum;

/**
 * A SeriesStats.
 */
@Entity
@Table(name = "series_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SeriesStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "pending")
    private SeriesStatsEnum pending;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private Series serie;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeriesStatsEnum getPending() {
        return pending;
    }

    public SeriesStats pending(SeriesStatsEnum pending) {
        this.pending = pending;
        return this;
    }

    public void setPending(SeriesStatsEnum pending) {
        this.pending = pending;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public SeriesStats date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Series getSerie() {
        return serie;
    }

    public SeriesStats serie(Series series) {
        this.serie = series;
        return this;
    }

    public void setSerie(Series series) {
        this.serie = series;
    }

    public User getUser() {
        return user;
    }

    public SeriesStats user(User user) {
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
        SeriesStats seriesStats = (SeriesStats) o;
        if (seriesStats.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seriesStats.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeriesStats{" +
            "id=" + getId() +
            ", pending='" + getPending() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
