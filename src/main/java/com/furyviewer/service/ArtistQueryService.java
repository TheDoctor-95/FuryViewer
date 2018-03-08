package com.furyviewer.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.*; // for static metamodels
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.service.dto.ArtistCriteria;


/**
 * Service for executing complex queries for Artist entities in the database.
 * The main input is a {@link ArtistCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Artist} or a {@link Page} of {%link Artist} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ArtistQueryService extends QueryService<Artist> {

    private final Logger log = LoggerFactory.getLogger(ArtistQueryService.class);


    private final ArtistRepository artistRepository;

    public ArtistQueryService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * Return a {@link List} of {%link Artist} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Artist> findByCriteria(ArtistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Artist> specification = createSpecification(criteria);
        return artistRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Artist} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Artist> findByCriteria(ArtistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Artist> specification = createSpecification(criteria);
        return artistRepository.findAll(specification, page);
    }

    /**
     * Function to convert ArtistCriteria to a {@link Specifications}
     */
    private Specifications<Artist> createSpecification(ArtistCriteria criteria) {
        Specifications<Artist> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Artist_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Artist_.name));
            }
            if (criteria.getBirthdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthdate(), Artist_.birthdate));
            }
            if (criteria.getSex() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSex(), Artist_.sex));
            }
            if (criteria.getDeathdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeathdate(), Artist_.deathdate));
            }
            if (criteria.getImgUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgUrl(), Artist_.imgUrl));
            }
            if (criteria.getImdb_id() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImdb_id(), Artist_.imdb_id));
            }
            if (criteria.getAwards() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAwards(), Artist_.awards));
            }
            if (criteria.getBiography() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBiography(), Artist_.biography));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCountryId(), Artist_.country, Country_.id));
            }
            if (criteria.getArtistTypeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getArtistTypeId(), Artist_.artistTypes, ArtistType_.id));
            }
            if (criteria.getFavoriteArtistId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getFavoriteArtistId(), Artist_.favoriteArtists, FavouriteArtist_.id));
            }
            if (criteria.getHatredArtistId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getHatredArtistId(), Artist_.hatredArtists, HatredArtist_.id));
            }
            if (criteria.getMovieDirectorId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMovieDirectorId(), Artist_.movieDirectors, Movie_.id));
            }
            if (criteria.getMovieScriptwriterId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMovieScriptwriterId(), Artist_.movieScriptwriters, Movie_.id));
            }
            if (criteria.getMovieMainActorId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMovieMainActorId(), Artist_.movieMainActors, Movie_.id));
            }
            if (criteria.getMovieSecondaryActorId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMovieSecondaryActorId(), Artist_.movieSecondaryActors, Movie_.id));
            }
        }
        return specification;
    }

}
