package com.furyviewer.service.SmartSearch.Series;

import com.furyviewer.domain.Country_;
import com.furyviewer.domain.Series;
import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.service.dto.Criteria.SeriesBCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.furyviewer.domain.Series_.*;

/**
 * SeriesQueryService se encarga de crear un constructor dinamico de Series para poder realizar la busqueda
 * inteligente.
 * @author Whoger
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
     * Devuelve una lista de Series con todas las que coinciden con los parametros de busqueda.
     * @param criteria SeriesBCriteria | Contiene los filtros por los que se buscara en Series.
     * @return List | Lista de las Series encontradas.
     */
    @Transactional(readOnly = true)
    public List<Series> findByCriteria(SeriesBCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Series> specification = createSpecification(criteria);
        return seriesRepository.findAll(specification);
    }

    /**
     * Devuelve una lista paginada de Series con todas las que coinciden con los parametros de busqueda.
     * @param criteria SeriesBCriteria | Contiene los filtros por los que se buscara en Series.
     * @param page Pageable | Pagina los datos devueltos.
     * @return Page | Lista paginada de las Series encontradas.
     */
    @Transactional(readOnly = true)
    public Page<Series> findByCriteria(SeriesBCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Series> specification = createSpecification(criteria);
        return seriesRepository.findAll(specification, page);
    }

    /**
     * Convierte SeriesBCriteria a Specifications para crear el constructor que servira para realizar la
     * busqueda inteligente.
     * @param criteria SeriesBCriteria | Contiene los filtros por los que se buscara en Series.
     * @return Specifications | Contiene los parametros de busqueda.
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
