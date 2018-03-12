package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Movie;
import com.furyviewer.domain.RateMovie;

import com.furyviewer.repository.MovieRepository;
import com.furyviewer.repository.RateMovieRepository;
import com.furyviewer.repository.UserRepository;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RateMovie.
 */
@RestController
@RequestMapping("/api")
public class RateMovieResource {

    private final Logger log = LoggerFactory.getLogger(RateMovieResource.class);

    private static final String ENTITY_NAME = "rateMovie";

    private final RateMovieRepository rateMovieRepository;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    public RateMovieResource(RateMovieRepository rateMovieRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.rateMovieRepository = rateMovieRepository;
        this.userRepository=userRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * POST  /rate-movies : Create a new rateMovie.
     *
     * @param rateMovie the rateMovie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rateMovie, or with status 400 (Bad Request) if the rateMovie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rate-movies")
    @Timed
    public ResponseEntity<RateMovie> createRateMovie(@RequestBody RateMovie rateMovie) throws URISyntaxException {
        log.debug("REST request to save RateMovie : {}", rateMovie);
        if (rateMovie.getId() != null) {
            throw new BadRequestAlertException("A new rateMovie cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<RateMovie> existingRateMovie = rateMovieRepository.findByMovieAndUserLogin(rateMovie.getMovie(), SecurityUtils.getCurrentUserLogin());

        if (existingRateMovie.isPresent()) {
            rateMovie.setId(existingRateMovie.get().getId());
        }


        rateMovie.setDate(ZonedDateTime.now());
        rateMovie.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        RateMovie result = rateMovieRepository.save(rateMovie);
        return ResponseEntity.created(new URI("/api/rate-movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/rate-movies/id/{idMovie}/rate/{rate}")
    @Timed
    public ResponseEntity<RateMovie> RateMovie(@PathVariable Long idMovie, @PathVariable int rate) throws URISyntaxException {

        Movie m = movieRepository.findOne(idMovie);

        RateMovie rm = new RateMovie();
        rm.setMovie(m);
        rm.setRate(rate);

        return createRateMovie(rm);


    }

    /**
     * PUT  /rate-movies : Updates an existing rateMovie.
     *
     * @param rateMovie the rateMovie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rateMovie,
     * or with status 400 (Bad Request) if the rateMovie is not valid,
     * or with status 500 (Internal Server Error) if the rateMovie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rate-movies")
    @Timed
    public ResponseEntity<RateMovie> updateRateMovie(@RequestBody RateMovie rateMovie) throws URISyntaxException {
        log.debug("REST request to update RateMovie : {}", rateMovie);
        if (rateMovie.getId() == null) {
            return createRateMovie(rateMovie);
        }
        RateMovie result = rateMovieRepository.save(rateMovie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rateMovie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rate-movies : get all the rateMovies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rateMovies in body
     */
    @GetMapping("/rate-movies")
    @Timed
    public List<RateMovie> getAllRateMovies() {
        log.debug("REST request to get all RateMovies");
        return rateMovieRepository.findAll();
    }

    /**
     * GET  /rate-movies/:id : get the "id" rateMovie.
     *
     * @param id the id of the rateMovie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rateMovie, or with status 404 (Not Found)
     */
    @GetMapping("/rate-movies/{id}")
    @Timed
    public ResponseEntity<RateMovie> getRateMovie(@PathVariable Long id) {
        log.debug("REST request to get RateMovie : {}", id);
        RateMovie rateMovie = rateMovieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rateMovie));
    }

    @GetMapping("/rate-movies/mediaMovie/{id}")
    @Timed
    public ResponseEntity<Double> getRateMovieMedia(@PathVariable Long id) {
        log.debug("REST request to get RateMovie : {}", id);
        Double media = rateMovieRepository.RateMovieMedia(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(media));
    }

    @GetMapping("/rate-movies/MovieRate/{id}")
    @Timed
    public ResponseEntity<RateMovie> getRateMovieUSer(@PathVariable Long id) {
        log.debug("REST request to get RateMovie : {}", id);
        RateMovie rateMovie = rateMovieRepository.findByUserAndMovieId(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get(), id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rateMovie));
    }

    /**
     * DELETE  /rate-movies/:id : delete the "id" rateMovie.
     *
     * @param id the id of the rateMovie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rate-movies/{id}")
    @Timed
    public ResponseEntity<Void> deleteRateMovie(@PathVariable Long id) {
        log.debug("REST request to delete RateMovie : {}", id);
        rateMovieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
