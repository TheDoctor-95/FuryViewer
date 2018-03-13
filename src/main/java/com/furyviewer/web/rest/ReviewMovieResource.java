package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.ReviewMovie;

import com.furyviewer.repository.ReviewMovieRepository;
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
 * REST controller for managing ReviewMovie.
 */
@RestController
@RequestMapping("/api")
public class ReviewMovieResource {

    private final Logger log = LoggerFactory.getLogger(ReviewMovieResource.class);

    private static final String ENTITY_NAME = "reviewMovie";

    private final ReviewMovieRepository reviewMovieRepository;

    public ReviewMovieResource(ReviewMovieRepository reviewMovieRepository) {
        this.reviewMovieRepository = reviewMovieRepository;
    }

    /**
     * POST  /review-movies : Create a new reviewMovie.
     *
     * @param reviewMovie the reviewMovie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reviewMovie, or with status 400 (Bad Request) if the reviewMovie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/review-movies")
    @Timed
    public ResponseEntity<ReviewMovie> createReviewMovie(@RequestBody ReviewMovie reviewMovie) throws URISyntaxException {
        log.debug("REST request to save ReviewMovie : {}", reviewMovie);
        if (reviewMovie.getId() != null) {
            throw new BadRequestAlertException("A new reviewMovie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewMovie result = reviewMovieRepository.save(reviewMovie);
        return ResponseEntity.created(new URI("/api/review-movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /review-movies : Updates an existing reviewMovie.
     *
     * @param reviewMovie the reviewMovie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reviewMovie,
     * or with status 400 (Bad Request) if the reviewMovie is not valid,
     * or with status 500 (Internal Server Error) if the reviewMovie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/review-movies")
    @Timed
    public ResponseEntity<ReviewMovie> updateReviewMovie(@RequestBody ReviewMovie reviewMovie) throws URISyntaxException {
        log.debug("REST request to update ReviewMovie : {}", reviewMovie);
        if (reviewMovie.getId() == null) {
            return createReviewMovie(reviewMovie);
        }
        ReviewMovie result = reviewMovieRepository.save(reviewMovie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reviewMovie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /review-movies : get all the reviewMovies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reviewMovies in body
     */
    @GetMapping("/review-movies")
    @Timed
    public List<ReviewMovie> getAllReviewMovies() {
        log.debug("REST request to get all ReviewMovies");
        return reviewMovieRepository.findAll();
        }

    /**
     * GET  /review-movies/:id : get the "id" reviewMovie.
     *
     * @param id the id of the reviewMovie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reviewMovie, or with status 404 (Not Found)
     */
    @GetMapping("/review-movies/{id}")
    @Timed
    public ResponseEntity<ReviewMovie> getReviewMovie(@PathVariable Long id) {
        log.debug("REST request to get ReviewMovie : {}", id);
        ReviewMovie reviewMovie = reviewMovieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reviewMovie));
    }

    @GetMapping("/review-movies/movie/{id}")
    @Timed
    public ResponseEntity<List<ReviewMovie>> getReviewsOfAMovie(@PathVariable Long id) {
        log.debug("REST request to get ReviewMovie : {}", id);
        List<ReviewMovie> reviewMovie = reviewMovieRepository.findByMovieId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reviewMovie));
    }

    /**
     * DELETE  /review-movies/:id : delete the "id" reviewMovie.
     *
     * @param id the id of the reviewMovie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/review-movies/{id}")
    @Timed
    public ResponseEntity<Void> deleteReviewMovie(@PathVariable Long id) {
        log.debug("REST request to delete ReviewMovie : {}", id);
        reviewMovieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
