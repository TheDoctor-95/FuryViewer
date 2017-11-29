package com.furyviewer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RateSeries.
 */
@Entity
@Table(name = "rate_series")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RateSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private Series series;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public RateSeries rate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public RateSeries date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Series getSeries() {
        return series;
    }

    public RateSeries series(Series series) {
        this.series = series;
        return this;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public User getUser() {
        return user;
    }

    public RateSeries user(User user) {
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
        RateSeries rateSeries = (RateSeries) o;
        if (rateSeries.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rateSeries.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RateSeries{" +
            "id=" + getId() +
            ", rate='" + getRate() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
