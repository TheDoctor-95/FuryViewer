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
 * A Episode.
 */
@Entity
@Table(name = "episode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Episode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_number")
    private Integer number;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    private Season season;

    @OneToMany(mappedBy = "episode")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChapterSeen> seens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Episode number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public Episode name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDuration() {
        return duration;
    }

    public Episode duration(Double duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Episode releaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Season getSeason() {
        return season;
    }

    public Episode season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Set<ChapterSeen> getSeens() {
        return seens;
    }

    public Episode seens(Set<ChapterSeen> chapterSeens) {
        this.seens = chapterSeens;
        return this;
    }

    public Episode addSeen(ChapterSeen chapterSeen) {
        this.seens.add(chapterSeen);
        chapterSeen.setEpisode(this);
        return this;
    }

    public Episode removeSeen(ChapterSeen chapterSeen) {
        this.seens.remove(chapterSeen);
        chapterSeen.setEpisode(null);
        return this;
    }

    public void setSeens(Set<ChapterSeen> chapterSeens) {
        this.seens = chapterSeens;
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
        Episode episode = (Episode) o;
        if (episode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), episode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Episode{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", name='" + getName() + "'" +
            ", duration='" + getDuration() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            "}";
    }
}
