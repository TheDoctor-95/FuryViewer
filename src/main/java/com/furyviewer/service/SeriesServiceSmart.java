package com.furyviewer.service;

import com.furyviewer.domain.Series;
import com.furyviewer.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Series.
 */
@Service
@Transactional
public class SeriesServiceSmart {

    private final Logger log = LoggerFactory.getLogger(SeriesServiceSmart.class);

    private SeriesRepository seriesRepository;

    public SeriesServiceSmart(SeriesRepository seriesBRepository) {
        this.seriesRepository = seriesRepository;
    }

    /**
     * Save a seriesB.
     *
     * @param series the entity to save
     * @return the persisted entity
     */
    public Series save(Series series) {
        log.debug("Request to save Series : {}", series);
        return seriesRepository.save(series);
    }

    /**
     *  Get all the seriesBS.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Series> findAll() {
        log.debug("Request to get all SeriesS");
        return seriesRepository.findAll();
    }

    /**
     *  Get one seriesB by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Series findOne(Long id) {
        log.debug("Request to get Series : {}", id);
        return seriesRepository.findOne(id);
    }

    /**
     *  Delete the  seriesB by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Series : {}", id);
        seriesRepository.delete(id);
    }
}
