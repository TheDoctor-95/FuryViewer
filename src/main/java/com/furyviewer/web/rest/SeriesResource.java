package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Series;

import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.service.OpenMovieDatabase.SeriesOmdbDTOService;
import com.furyviewer.service.dto.OpenMovieDatabase.SeriesOmdbDTO;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Series.
 */
@RestController
@RequestMapping("/api")
public class SeriesResource {

    private final Logger log = LoggerFactory.getLogger(SeriesResource.class);

    private static final String ENTITY_NAME = "series";

    private final SeriesRepository seriesRepository;

    @Autowired
    SeriesOmdbDTOService seriesOmdbDTOService;

    public SeriesResource(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    /**
     * POST  /series : Create a new series.
     *
     * @param series the series to create
     * @return the ResponseEntity with status 201 (Created) and with body the new series, or with status 400 (Bad Request) if the series has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/series")
    @Timed
    public ResponseEntity<Series> createSeries(@RequestBody Series series) throws URISyntaxException {
        log.debug("REST request to save Series : {}", series);
        if (series.getId() != null) {
            throw new BadRequestAlertException("A new series cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Series result = seriesRepository.save(series);
        return ResponseEntity.created(new URI("/api/series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /series : Updates an existing series.
     *
     * @param series the series to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated series,
     * or with status 400 (Bad Request) if the series is not valid,
     * or with status 500 (Internal Server Error) if the series couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/series")
    @Timed
    public ResponseEntity<Series> updateSeries(@RequestBody Series series) throws URISyntaxException {
        log.debug("REST request to update Series : {}", series);
        if (series.getId() == null) {
            return createSeries(series);
        }
        Series result = seriesRepository.save(series);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, series.getId().toString()))
            .body(result);
    }

    /**
     * GET  /series : get all the series.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of series in body
     */
    @GetMapping("/series")
    @Timed
    public List<Series> getAllSeries() {
        log.debug("REST request to get all Series");
        return seriesRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /series/:id : get the "id" series.
     *
     * @param id the id of the series to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the series, or with status 404 (Not Found)
     */
    @GetMapping("/series/{id}")
    @Timed
    public ResponseEntity<Series> getSeries(@PathVariable Long id) {
        log.debug("REST request to get Series : {}", id);
        Series series = seriesRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(series));
    }

    /**
     *
     * Find series by name (commit obligado.... Pau e Ibra son malos....)
     *
     * @param name
     * @return
     */
    @GetMapping("/seriesByName/{name}")
    @Timed
    public ResponseEntity<List<Series>> findSeriesByName(@PathVariable String name) {
        log.debug("REST request to get Series by name", name);
        List<Series> series =seriesRepository.findSeriesByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(series));
    }

    /**
     * DELETE  /series/:id : delete the "id" series.
     *
     * @param id the id of the series to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/series/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeries(@PathVariable Long id) {
        log.debug("REST request to delete Series : {}", id);
        seriesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/series-api/test")
    @Timed
    public SeriesOmdbDTO getTestInicial() throws Exception {


        return seriesOmdbDTOService.getSeries("American Horror Story");
    }

    @GetMapping("/importSeriesByName/{name}")
    @Timed
    @Transactional
    public ResponseEntity<Series> importSeriesByName(@PathVariable String name) {
        log.debug("REST request to get Series by name", name);
        Series movie = seriesOmdbDTOService.importSeries(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }
}
