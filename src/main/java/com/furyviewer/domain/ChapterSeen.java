package com.furyviewer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ChapterSeen.
 */
@Entity
@Table(name = "chapter_seen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChapterSeen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seen")
    private Boolean seen;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private Episode episode;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isSeen() {
        return seen;
    }

    public ChapterSeen seen(Boolean seen) {
        this.seen = seen;
        return this;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public ChapterSeen date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Episode getEpisode() {
        return episode;
    }

    public ChapterSeen episode(Episode episode) {
        this.episode = episode;
        return this;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public User getUser() {
        return user;
    }

    public ChapterSeen user(User user) {
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
        ChapterSeen chapterSeen = (ChapterSeen) o;
        if (chapterSeen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chapterSeen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChapterSeen{" +
            "id=" + getId() +
            ", seen='" + isSeen() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
