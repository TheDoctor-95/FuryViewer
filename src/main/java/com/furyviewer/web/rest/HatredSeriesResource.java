package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.HatredSeries;

import com.furyviewer.repository.HatredSeriesRepository;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HatredSeries.
 */
@RestController
@RequestMapping("/api")
public class HatredSeriesResource {

    private final Logger log = LoggerFactory.getLogger(HatredSeriesResource.class);

    private static final String ENTITY_NAME = "hatredSeries";

    private final HatredSeriesRepository hatredSeriesRepository;

    public HatredSeriesResource(HatredSeriesRepository hatredSeriesRepository) {
        this.hatredSeriesRepository = hatredSeriesRepository;
    }

    /**
     * POST  /hatred-series : Create a new hatredSeries.
     *
     * @param hatredSeries the hatredSeries to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hatredSeries, or with status 400 (Bad Request) if the hatredSeries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hatred-series")
    @Timed
    public ResponseEntity<HatredSeries> createHatredSeries(@RequestBody HatredSeries hatredSeries) throws URISyntaxException {
        log.debug("REST request to save HatredSeries : {}", hatredSeries);
        if (hatredSeries.getId() != null) {
            throw new BadRequestAlertException("A new hatredSeries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HatredSeries result = hatredSeriesRepository.save(hatredSeries);
        return ResponseEntity.created(new URI("/api/hatred-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hatred-series : Updates an existing hatredSeries.
     *
     * @param hatredSeries the hatredSeries to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hatredSeries,
     * or with status 400 (Bad Request) if the hatredSeries is not valid,
     * or with status 500 (Internal Server Error) if the hatredSeries couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hatred-series")
    @Timed
    public ResponseEntity<HatredSeries> updateHatredSeries(@RequestBody HatredSeries hatredSeries) throws URISyntaxException {
        log.debug("REST request to update HatredSeries : {}", hatredSeries);
        if (hatredSeries.getId() == null) {
            return createHatredSeries(hatredSeries);
        }
        HatredSeries result = hatredSeriesRepository.save(hatredSeries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hatredSeries.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hatred-series : get all the hatredSeries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hatredSeries in body
     */
    @GetMapping("/hatred-series")
    @Timed
    public List<HatredSeries> getAllHatredSeries() {
        log.debug("REST request to get all HatredSeries");
        return hatredSeriesRepository.findAll();
        }

    /**
     * GET  /hatred-series/:id : get the "id" hatredSeries.
     *
     * @param id the id of the hatredSeries to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hatredSeries, or with status 404 (Not Found)
     */
    @GetMapping("/hatred-series/{id}")
    @Timed
    public ResponseEntity<HatredSeries> getHatredSeries(@PathVariable Long id) {
        log.debug("REST request to get HatredSeries : {}", id);
        HatredSeries hatredSeries = hatredSeriesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hatredSeries));
    }

    /**
     * DELETE  /hatred-series/:id : delete the "id" hatredSeries.
     *
     * @param id the id of the hatredSeries to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hatred-series/{id}")
    @Timed
    public ResponseEntity<Void> deleteHatredSeries(@PathVariable Long id) {
        log.debug("REST request to delete HatredSeries : {}", id);
        hatredSeriesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
