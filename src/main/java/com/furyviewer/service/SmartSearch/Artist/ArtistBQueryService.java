package com.furyviewer.service.SmartSearch.Artist;

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
import com.furyviewer.service.dto.Criteria.ArtistBCriteria;

/**
 * ArtistBQueryService se encarga de crear un constructor dinamico de Artist para poder realizar la busqueda
 * inteligente.
 * @author Whoger
 */
@Service
@Transactional(readOnly = true)
public class ArtistBQueryService extends QueryService<Artist> {

    private final Logger log = LoggerFactory.getLogger(ArtistBQueryService.class);


    private final ArtistRepository artistRepository;

    public ArtistBQueryService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * Devuelve una lista de Artist con todos los que coinciden con los parametros de busqueda.
     * @param criteria ArtistBCriteria | Contiene los filtros por los que se buscara en Artist.
     * @return List | Lista de los Artist encontrados.
     */
    @Transactional(readOnly = true)
    public List<Artist> findByCriteria(ArtistBCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Artist> specification = createSpecification(criteria);
        return artistRepository.findAll(specification);
    }

    /**
     * Devuelve una lista paginada de Artist con todos los que coinciden con los parametros de busqueda.
     * @param criteria ArtistBCriteria | Contiene los filtros por los que se buscara en Artist.
     * @param page Pageable | Pagina los datos devueltos.
     * @return Page | Lista paginada de los Artist encontrados.
     */
    @Transactional(readOnly = true)
    public Page<Artist> findByCriteria(ArtistBCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Artist> specification = createSpecification(criteria);
        return artistRepository.findAll(specification, page);
    }

    /**
     * Convierte ArtistBCriteria a Specifications para crear el constructor que servira para realizar la busqueda
     * inteligente.
     * @param criteria ArtistBCriteria | Contiene los filtros por los que se buscara en Artist.
     * @return Specifications | Contiene los parametros de búsqueda.
     */
    private Specifications<Artist> createSpecification(ArtistBCriteria criteria) {
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
            if (criteria.getImdbId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImdbId(), Artist_.imdb_id));
            }
            if (criteria.getAwards() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAwards(), Artist_.awards));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCountryId(), Artist_.country, Country_.id));
            }
        }
        return specification;
    }

}
