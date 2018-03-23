package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Season;

import com.furyviewer.repository.SeasonRepository;
import com.furyviewer.service.OpenMovieDatabase.Service.SeasonOmdbDTOService;
import com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Season.
 */
@RestController
@RequestMapping("/api")
public class SeasonResource {

    private final Logger log = LoggerFactory.getLogger(SeasonResource.class);

    private static final String ENTITY_NAME = "season";

    private final SeasonRepository seasonRepository;

    @Autowired
    SeasonOmdbDTOService seasonOmdbDTOService;

    public SeasonResource(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    /**
     * POST  /seasons : Create a new season.
     *
     * @param season the season to create
     * @return the ResponseEntity with status 201 (Created) and with body the new season, or with status 400 (Bad Request) if the season has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seasons")
    @Timed
    public ResponseEntity<Season> createSeason(@RequestBody Season season) throws URISyntaxException {
        log.debug("REST request to save Season : {}", season);
        if (season.getId() != null) {
            throw new BadRequestAlertException("A new season cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Season result = seasonRepository.save(season);
        return ResponseEntity.created(new URI("/api/seasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seasons : Updates an existing season.
     *
     * @param season the season to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated season,
     * or with status 400 (Bad Request) if the season is not valid,
     * or with status 500 (Internal Server Error) if the season couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seasons")
    @Timed
    public ResponseEntity<Season> updateSeason(@RequestBody Season season) throws URISyntaxException {
        log.debug("REST request to update Season : {}", season);
        if (season.getId() == null) {
            return createSeason(season);
        }
        Season result = seasonRepository.save(season);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, season.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seasons : get all the seasons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seasons in body
     */
    @GetMapping("/seasons")
    @Timed
    public List<Season> getAllSeasons() {
        log.debug("REST request to get all Seasons");
        return seasonRepository.findAll();
        }

    /**
     * GET  /seasons/:id : get the "id" season.
     *
     * @param id the id of the season to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the season, or with status 404 (Not Found)
     */
    @GetMapping("/seasons/Seasons-by-Series/{id}")
    @Timed
    public ResponseEntity <List<Season>> findSeasonsBySeriesId(@PathVariable Long id) {
        log.debug("REST request to get Season : {}", id);
        List<Season> seasons = seasonRepository.findSeasonsBySeriesId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seasons));
    }


    /**
     * GET
     *
     * @param id the id of the series to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the season, or with status 404 (Not Found)
     */
    @GetMapping("/seasons/{id}")
    @Timed
    public ResponseEntity<Season> getSeason(@PathVariable Long id) {
        log.debug("REST request to get Season : {}", id);
        Season season = seasonRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(season));
    }

    /**
     * DELETE  /seasons/:id : delete the "id" season.
     *
     * @param id the id of the season to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seasons/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        log.debug("REST request to delete Season : {}", id);
        seasonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/season-api/test")
    @Timed
    public SeasonOmdbDTO getTestInicial() throws Exception {


        return seasonOmdbDTOService.getSeason("American Horror Story", 1);
    }


}
