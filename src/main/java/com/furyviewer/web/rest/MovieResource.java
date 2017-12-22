package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Movie;

import com.furyviewer.repository.MovieRepository;
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
 * REST controller for managing Movie.
 */
@RestController
@RequestMapping("/api")
public class MovieResource {

    private final Logger log = LoggerFactory.getLogger(MovieResource.class);

    private static final String ENTITY_NAME = "movie";

    private final MovieRepository movieRepository;

    public MovieResource(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * POST  /movies : Create a new movie.
     *
     * @param movie the movie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movie, or with status 400 (Bad Request) if the movie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movies")
    @Timed
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws URISyntaxException {
        log.debug("REST request to save Movie : {}", movie);
        if (movie.getId() != null) {
            throw new BadRequestAlertException("A new movie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Movie result = movieRepository.save(movie);
        return ResponseEntity.created(new URI("/api/movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movies : Updates an existing movie.
     *
     * @param movie the movie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movie,
     * or with status 400 (Bad Request) if the movie is not valid,
     * or with status 500 (Internal Server Error) if the movie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movies")
    @Timed
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) throws URISyntaxException {
        log.debug("REST request to update Movie : {}", movie);
        if (movie.getId() == null) {
            return createMovie(movie);
        }
        Movie result = movieRepository.save(movie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movies : get all the movies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movies in body
     */
    @GetMapping("/movies")
    @Timed
    public List<Movie> getAllMovies() {
        log.debug("REST request to get all Movies");
        return movieRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /movies/:id : get the "id" movie.
     *
     * @param id the id of the movie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movie, or with status 404 (Not Found)
     */
    @GetMapping("/movies/{id}")
    @Timed
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        Movie movie = movieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }

    /**
     *
     * Find movies by name (commit obligado.... Pau e Ibra son malos....)
     *
     * @param name
     * @return
     */
    @GetMapping("/moviesByName/{name}")
    @Timed
    public ResponseEntity<List<Movie>> findMovieByName(@PathVariable String name) {
        log.debug("REST request to get Movies by name", name);
        List<Movie> movie =movieRepository.findMovieByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }

    /**
     * DELETE  /movies/:id : delete the "id" movie.
     *
     * @param id the id of the movie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movies/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.debug("REST request to delete Movie : {}", id);
        movieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
