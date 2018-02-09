package com.furyviewer.service;

import java.util.List;

import com.furyviewer.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.furyviewer.domain.Series;
import com.furyviewer.domain.*; // for static metamodels
import com.furyviewer.service.dto.SeriesBCriteria;

import static com.furyviewer.domain.Series_.*;

/**
 * Service for executing complex queries for SeriesB entities in the database.
 * The main input is a {@link SeriesBCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link SeriesB} or a {@link Page} of {%link SeriesB} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class SeriesQueryService extends QueryService<Series> {

    private final Logger log = LoggerFactory.getLogger(SeriesQueryService.class);


    private final SeriesRepository seriesRepository;

    public SeriesQueryService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    /**
     * Return a {@link List} of {%link SeriesB} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Series> findByCriteria(SeriesBCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Series> specification = createSpecification(criteria);
        return seriesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link SeriesB} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Series> findByCriteria(SeriesBCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Series> specification = createSpecification(criteria);
        return seriesRepository.findAll(specification, page);
    }

    /**
     * Function to convert SeriesBCriteria to a {@link Specifications}
     */
    private Specifications<Series> createSpecification(SeriesBCriteria criteria) {
        Specifications<Series> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), description));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildSpecification(criteria.getState(), state));
            }
            if (criteria.getRelease_date() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRelease_date(), releaseDate));
            }
            if (criteria.getImg_url() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImg_url(), imgUrl));
            }
            if (criteria.getImdb_id() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImdb_id(), imdb_id));
            }
            if (criteria.getAwards() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAwards(), awards));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCountryId(), country, Country_.id));
            }
        }
        return specification;
    }

}
