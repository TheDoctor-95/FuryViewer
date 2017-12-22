package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.MovieStats;

import com.furyviewer.repository.MovieStatsRepository;
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
 * REST controller for managing MovieStats.
 */
@RestController
@RequestMapping("/api")
public class MovieStatsResource {

    private final Logger log = LoggerFactory.getLogger(MovieStatsResource.class);

    private static final String ENTITY_NAME = "movieStats";

    private final MovieStatsRepository movieStatsRepository;

    public MovieStatsResource(MovieStatsRepository movieStatsRepository) {
        this.movieStatsRepository = movieStatsRepository;
    }

    /**
     * POST  /movie-stats : Create a new movieStats.
     *
     * @param movieStats the movieStats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movieStats, or with status 400 (Bad Request) if the movieStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movie-stats")
    @Timed
    public ResponseEntity<MovieStats> createMovieStats(@RequestBody MovieStats movieStats) throws URISyntaxException {
        log.debug("REST request to save MovieStats : {}", movieStats);
        if (movieStats.getId() != null) {
            throw new BadRequestAlertException("A new movieStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovieStats result = movieStatsRepository.save(movieStats);
        return ResponseEntity.created(new URI("/api/movie-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movie-stats : Updates an existing movieStats.
     *
     * @param movieStats the movieStats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieStats,
     * or with status 400 (Bad Request) if the movieStats is not valid,
     * or with status 500 (Internal Server Error) if the movieStats couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movie-stats")
    @Timed
    public ResponseEntity<MovieStats> updateMovieStats(@RequestBody MovieStats movieStats) throws URISyntaxException {
        log.debug("REST request to update MovieStats : {}", movieStats);
        if (movieStats.getId() == null) {
            return createMovieStats(movieStats);
        }
        MovieStats result = movieStatsRepository.save(movieStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movieStats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movie-stats : get all the movieStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movieStats in body
     */
    @GetMapping("/movie-stats")
    @Timed
    public List<MovieStats> getAllMovieStats() {
        log.debug("REST request to get all MovieStats");
        return movieStatsRepository.findAll();
        }

    /**
     * GET  /movie-stats/:id : get the "id" movieStats.
     *
     * @param id the id of the movieStats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movieStats, or with status 404 (Not Found)
     */
    @GetMapping("/movie-stats/{id}")
    @Timed
    public ResponseEntity<MovieStats> getMovieStats(@PathVariable Long id) {
        log.debug("REST request to get MovieStats : {}", id);
        MovieStats movieStats = movieStatsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movieStats));
    }

    @GetMapping("/movie-stats-pending/{id}")
    @Timed
    public Long getPendingMovieStats(@PathVariable Long id) {
        log.debug("REST request to get MovieStats : {}", id);
       return movieStatsRepository.PendingMovieStats(id);
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movieStats));
    }

    @GetMapping("/movie-stats-seen/{id}")
    @Timed
    public Long getSeenMovieStats(@PathVariable Long id) {
        log.debug("REST request to get MovieStats : {}", id);
        return movieStatsRepository.SeenMovieStats(id);
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movieStats));
    }

    /**
     * DELETE  /movie-stats/:id : delete the "id" movieStats.
     *
     * @param id the id of the movieStats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movie-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovieStats(@PathVariable Long id) {
        log.debug("REST request to delete MovieStats : {}", id);
        movieStatsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
