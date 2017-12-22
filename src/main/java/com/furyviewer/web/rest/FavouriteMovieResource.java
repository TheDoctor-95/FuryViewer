package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.FavouriteMovie;

import com.furyviewer.repository.FavouriteMovieRepository;
import com.furyviewer.repository.UserRepository;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FavouriteMovie.
 */
@RestController
@RequestMapping("/api")
public class FavouriteMovieResource {

    private final Logger log = LoggerFactory.getLogger(FavouriteMovieResource.class);

    private static final String ENTITY_NAME = "favouriteMovie";

    private FavouriteMovieRepository favouriteMovieRepository = null;

    private final UserRepository userRepository;

    public FavouriteMovieResource(FavouriteMovieRepository favouriteMovieRepository, UserRepository userRepository) {
        this.favouriteMovieRepository = favouriteMovieRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /favourite-movies : Create a new favouriteMovie.
     *
     * @param favouriteMovie the favouriteMovie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new favouriteMovie, or with status 400 (Bad Request) if the favouriteMovie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/favourite-movies")
    @Timed
    public ResponseEntity<FavouriteMovie> createFavouriteMovie(@RequestBody FavouriteMovie favouriteMovie) throws URISyntaxException {
        log.debug("REST request to save FavouriteMovie : {}", favouriteMovie);
        if (favouriteMovie.getId() != null) {
            throw new BadRequestAlertException("A new favouriteMovie cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<FavouriteMovie> favoriteMovieExisting = favouriteMovieRepository.findByMovieAndUserLogin(favouriteMovie.getMovie(), SecurityUtils.getCurrentUserLogin());

        if(favoriteMovieExisting.isPresent()){
            throw new BadRequestAlertException("Movie ja añadida a favoritas", ENTITY_NAME, "favoriteExists");
        }

        favouriteMovie.setDate(ZonedDateTime.now());
        favouriteMovie.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        FavouriteMovie result = favouriteMovieRepository.save(favouriteMovie);
        return ResponseEntity.created(new URI("/api/favourite-movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /favourite-movies : Updates an existing favouriteMovie.
     *
     * @param favouriteMovie the favouriteMovie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated favouriteMovie,
     * or with status 400 (Bad Request) if the favouriteMovie is not valid,
     * or with status 500 (Internal Server Error) if the favouriteMovie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/favourite-movies")
    @Timed
    public ResponseEntity<FavouriteMovie> updateFavouriteMovie(@RequestBody FavouriteMovie favouriteMovie) throws URISyntaxException {
        log.debug("REST request to update FavouriteMovie : {}", favouriteMovie);
        if (favouriteMovie.getId() == null) {
            return createFavouriteMovie(favouriteMovie);
        }
        FavouriteMovie result = favouriteMovieRepository.save(favouriteMovie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, favouriteMovie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /favourite-movies : get all the favouriteMovies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of favouriteMovies in body
     */
    @GetMapping("/favourite-movies")
    @Timed
    public List<FavouriteMovie> getAllFavouriteMovies() {
        log.debug("REST request to get all FavouriteMovies");
        return favouriteMovieRepository.findAll();
        }

    /**
     * GET  /favourite-movies/:id : get the "id" favouriteMovie.
     *
     * @param id the id of the favouriteMovie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the favouriteMovie, or with status 404 (Not Found)
     */
    @GetMapping("/favourite-movies/{id}")
    @Timed
    public ResponseEntity<FavouriteMovie> getFavouriteMovie(@PathVariable Long id) {
        log.debug("REST request to get FavouriteMovie : {}", id);
        FavouriteMovie favouriteMovie = favouriteMovieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(favouriteMovie));
    }

    @GetMapping("/num-favourite-movies/{id}")
    @Timed
    public Long getNumFavsMovie(@PathVariable Long id) {
        log.debug("REST request to get FavouriteMovie : {}", id);
        return favouriteMovieRepository.NumFavsMovie(id);
      //  return ResponseUtil.wrapOrNotFound(Optional.ofNullable(favouriteMovie));
    }

    /**
     * DELETE  /favourite-movies/:id : delete the "id" favouriteMovie.
     *
     * @param id the id of the favouriteMovie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/favourite-movies/{id}")
    @Timed
    public ResponseEntity<Void> deleteFavouriteMovie(@PathVariable Long id) {
        log.debug("REST request to delete FavouriteMovie : {}", id);
        favouriteMovieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
