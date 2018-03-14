package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Artist;
import com.furyviewer.domain.Movie;

import com.furyviewer.repository.MovieRepository;
import com.furyviewer.repository.MovieStatsRepository;
import com.furyviewer.repository.RateMovieRepository;
import com.furyviewer.repository.HatredMovieRepository;
import com.furyviewer.repository.UserRepository;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.service.SmartSearch.Movie.MovieQueryService;
import com.furyviewer.service.OpenMovieDatabase.Service.MovieOmdbDTOService;
import com.furyviewer.service.dto.Criteria.MovieBCriteria;
import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Movie.
 */
@RestController
@RequestMapping("/api")
public class MovieResource {

    private final Logger log = LoggerFactory.getLogger(MovieResource.class);

    private static final String ENTITY_NAME = "movie";

    private final MovieRepository movieRepository;

    private final OkHttpClient client = new OkHttpClient();

    @Autowired
    private MovieQueryService movieQueryService;

    @Autowired
    private HatredMovieRepository hatredMovieRepository;

    @Autowired
    private MovieStatsRepository movieStatsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RateMovieRepository rateMovieRepository;

    @Inject
    private MovieOmdbDTOService movieOmdbDTOService;



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


    @GetMapping("/movies/Pending/")
    @Timed
    public List<Movie> getPendingMovies() {
        log.debug("REST request to get all Movies");
        return movieStatsRepository.pendingMovies(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
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

    @GetMapping("/movies/{id}/actors")
    @Transactional
    @Timed
    public ResponseEntity<Set<Artist>> getActors(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        Movie movie = movieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie.getActorMains()));
    }


    @GetMapping("/importMovieByName/{name}")
    @Timed

    public ResponseEntity<Movie> importMovieByName(@PathVariable String name) {
        log.debug("REST request to get Movies by name", name);
         Movie movie = movieOmdbDTOService.importMovieByName(name);
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
    Find movies by Top

     */

    @GetMapping("/movies/topPelis/")
    @Timed
    public ResponseEntity<List<Movie>> findTopPelis() {
        log.debug("REST request to get Movies by name");
        List<Movie> movie = rateMovieRepository.topPelis();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }

    /**
     * Top Hatred Movies
     *
     */
    @GetMapping("/movies/topHatredPelis/")
    @Timed
    public ResponseEntity<List<Movie>> findHatredPelis() {
        log.debug("REST request to get top hatred Movies");
        List<Movie> movie = hatredMovieRepository.topHatredMovies();
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


    @GetMapping("/movie-s")
    @Timed
    public ResponseEntity<List<Movie>> getAllMovieS(MovieBCriteria criteria) {
        log.debug("REST request to get MoviesS by criteria: {}", criteria);
        List<Movie> entityList = movieQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    @GetMapping("/movie-api/test")
    @Timed
    public MovieOmdbDTO getTestInicial() throws Exception {


        return movieOmdbDTOService.getMovieByName("Justice League");
    }





}
