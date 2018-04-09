package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.ReviewSeries;

import com.furyviewer.repository.ReviewSeriesRepository;
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
 * REST controller for managing ReviewSeries.
 */
@RestController
@RequestMapping("/api")
public class ReviewSeriesResource {

    private final Logger log = LoggerFactory.getLogger(ReviewSeriesResource.class);

    private static final String ENTITY_NAME = "reviewSeries";

    private final ReviewSeriesRepository reviewSeriesRepository;

    public ReviewSeriesResource(ReviewSeriesRepository reviewSeriesRepository) {
        this.reviewSeriesRepository = reviewSeriesRepository;
    }

    /**
     * POST  /review-series : Create a new reviewSeries.
     *
     * @param reviewSeries the reviewSeries to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reviewSeries, or with status 400 (Bad Request) if the reviewSeries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/review-series")
    @Timed
    public ResponseEntity<ReviewSeries> createReviewSeries(@RequestBody ReviewSeries reviewSeries) throws URISyntaxException {
        log.debug("REST request to save ReviewSeries : {}", reviewSeries);
        if (reviewSeries.getId() != null) {
            throw new BadRequestAlertException("A new reviewSeries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewSeries result = reviewSeriesRepository.save(reviewSeries);
        return ResponseEntity.created(new URI("/api/review-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /review-series : Updates an existing reviewSeries.
     *
     * @param reviewSeries the reviewSeries to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reviewSeries,
     * or with status 400 (Bad Request) if the reviewSeries is not valid,
     * or with status 500 (Internal Server Error) if the reviewSeries couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/review-series")
    @Timed
    public ResponseEntity<ReviewSeries> updateReviewSeries(@RequestBody ReviewSeries reviewSeries) throws URISyntaxException {
        log.debug("REST request to update ReviewSeries : {}", reviewSeries);
        if (reviewSeries.getId() == null) {
            return createReviewSeries(reviewSeries);
        }
        ReviewSeries result = reviewSeriesRepository.save(reviewSeries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reviewSeries.getId().toString()))
            .body(result);
    }

    /**
     * GET  /review-series : get all the reviewSeries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reviewSeries in body
     */
    @GetMapping("/review-series")
    @Timed
    public List<ReviewSeries> getAllReviewSeries() {
        log.debug("REST request to get all ReviewSeries");
        return reviewSeriesRepository.findAll();
        }

    /**
     * GET  /review-series/:id : get the "id" reviewSeries.
     *
     * @param id the id of the reviewSeries to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reviewSeries, or with status 404 (Not Found)
     */
    @GetMapping("/review-series/{id}")
    @Timed
    public ResponseEntity<ReviewSeries> getReviewSeries(@PathVariable Long id) {
        log.debug("REST request to get ReviewSeries : {}", id);
        ReviewSeries reviewSeries = reviewSeriesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reviewSeries));
    }

    /**
     * DELETE  /review-series/:id : delete the "id" reviewSeries.
     *
     * @param id the id of the reviewSeries to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/review-series/{id}")
    @Timed
    public ResponseEntity<Void> deleteReviewSeries(@PathVariable Long id) {
        log.debug("REST request to delete ReviewSeries : {}", id);
        reviewSeriesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/review-series/series/{id}")
    @Timed
    public ResponseEntity<List<ReviewSeries>> getReviewsOfAMovie(@PathVariable Long id) {
        log.debug("REST request to get ReviewMovie : {}", id);
        List<ReviewSeries> reviewMovie = reviewSeriesRepository.findBySeriesIdOrderByDateDesc(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reviewMovie));
    }
}
