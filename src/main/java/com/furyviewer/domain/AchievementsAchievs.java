package com.furyviewer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AchievementsAchievs.
 */
@Entity
@Table(name = "achievements_achievs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AchievementsAchievs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Achievement achievement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public AchievementsAchievs date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public AchievementsAchievs user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public AchievementsAchievs achievement(Achievement achievement) {
        this.achievement = achievement;
        return this;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
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
        AchievementsAchievs achievementsAchievs = (AchievementsAchievs) o;
        if (achievementsAchievs.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), achievementsAchievs.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AchievementsAchievs{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
