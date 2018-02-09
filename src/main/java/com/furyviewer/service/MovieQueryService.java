package com.furyviewer.service;

import java.util.List;

import com.furyviewer.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.furyviewer.domain.Movie;
import com.furyviewer.domain.*; // for static metamodels
import com.furyviewer.service.dto.MovieBCriteria;


/**
 * Service for executing complex queries for MovieB entities in the database.
 * The main input is a {@link MovieBCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link MovieB} or a {@link Page} of {%link MovieB} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class MovieQueryService extends QueryService<Movie> {

    private final Logger log = LoggerFactory.getLogger(MovieQueryService.class);


    private final MovieRepository movieRepository;

    public MovieQueryService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Return a {@link List} of {%link MovieB} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Movie> findByCriteria(MovieBCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Movie> specification = createSpecification(criteria);
        return movieRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link MovieB} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Movie> findByCriteria(MovieBCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Movie> specification = createSpecification(criteria);
        return movieRepository.findAll(specification, page);
    }

    /**
     * Function to convert MovieBCriteria to a {@link Specifications}
     */
    private Specifications<Movie> createSpecification(MovieBCriteria criteria) {
        Specifications<Movie> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Movie_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Movie_.name));
            }
            if (criteria.getRelease_date() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRelease_date(), Movie_.releaseDate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Movie_.description));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), Movie_.duration));
            }
            if (criteria.getImdb_id_external_api() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImdb_id_external_api(), Movie_.imdbIdExternalApi));
            }
            if (criteria.getImg_url() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImg_url(), Movie_.imgUrl));
            }
            if (criteria.getAwards() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAwards(), Movie_.awards));
            }
            if (criteria.getDvd_release() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDvd_release(), Movie_.dvd_release));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCountryId(), Movie_.country, Country_.id));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCompanyId(), Movie_.company, Company_.id));
            }
        }
        return specification;
    }

}
