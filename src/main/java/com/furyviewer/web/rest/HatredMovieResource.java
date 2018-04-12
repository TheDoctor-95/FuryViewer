package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.HatredMovie;

import com.furyviewer.domain.Movie;
import com.furyviewer.repository.HatredMovieRepository;
import com.furyviewer.repository.MovieRepository;
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
 * REST controller for managing HatredMovie.
 */
@RestController
@RequestMapping("/api")
public class HatredMovieResource {

    private final Logger log = LoggerFactory.getLogger(HatredMovieResource.class);

    private static final String ENTITY_NAME = "hatredMovie";

    private final HatredMovieRepository hatredMovieRepository;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    public HatredMovieResource(HatredMovieRepository hatredMovieRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.hatredMovieRepository = hatredMovieRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * POST  /hatred-movies : Create a new hatredMovie.
     *
     * @param hatredMovie the hatredMovie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hatredMovie, or with status 400 (Bad Request) if the hatredMovie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hatred-movies")
    @Timed
    public ResponseEntity<HatredMovie> createHatredMovie(@RequestBody HatredMovie hatredMovie) throws URISyntaxException {
        log.debug("REST request to save HatredMovie : {}", hatredMovie);
        if (hatredMovie.getId() != null) {
            throw new BadRequestAlertException("A new hatredMovie cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<HatredMovie> existingHatredMovie = hatredMovieRepository.findByMovieAndUserLogin(hatredMovie.getMovie(), SecurityUtils.getCurrentUserLogin());

        if (existingHatredMovie.isPresent()) {
            hatredMovie.setId(existingHatredMovie.get().getId());
            hatredMovie.setHated(!existingHatredMovie.get().isHated());
        }

        hatredMovie.setDate(ZonedDateTime.now());
        hatredMovie.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        HatredMovie result = hatredMovieRepository.save(hatredMovie);
        return ResponseEntity.created(new URI("/api/hatred-movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PostMapping("/hatred-movies/id/{idMovie}/hate")
    @Timed
    public ResponseEntity<HatredMovie> hateMovie(@PathVariable Long idMovie) throws URISyntaxException {

        log.debug("REST request to save FavouriteMovie : {}", idMovie);

        Movie m = movieRepository.findOne(idMovie);

        HatredMovie hm = new HatredMovie();
        hm.setMovie(m);
        hm.setHated(true);

        return createHatredMovie(hm);

    }





    /**
     * PUT  /hatred-movies : Updates an existing hatredMovie.
     *
     * @param hatredMovie the hatredMovie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hatredMovie,
     * or with status 400 (Bad Request) if the hatredMovie is not valid,
     * or with status 500 (Internal Server Error) if the hatredMovie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hatred-movies")
    @Timed
    public ResponseEntity<HatredMovie> updateHatredMovie(@RequestBody HatredMovie hatredMovie) throws URISyntaxException {
        log.debug("REST request to update HatredMovie : {}", hatredMovie);
        if (hatredMovie.getId() == null) {
            return createHatredMovie(hatredMovie);
        }
        HatredMovie result = hatredMovieRepository.save(hatredMovie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hatredMovie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hatred-movies : get all the hatredMovies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hatredMovies in body
     */
    @GetMapping("/hatred-movies")
    @Timed
    public List<HatredMovie> getAllHatredMovies() {
        log.debug("REST request to get all HatredMovies");
        return hatredMovieRepository.findAll();
    }

    /**
     * GET  /hatred-movies/:id : get the "id" hatredMovie.
     *
     * @param id the id of the hatredMovie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hatredMovie, or with status 404 (Not Found)
     */
    @GetMapping("/hatred-movies/{id}")
    @Timed
    public ResponseEntity<HatredMovie> getHatredMovie(@PathVariable Long id) {
        log.debug("REST request to get HatredMovie : {}", id);
        HatredMovie hatredMovie = hatredMovieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hatredMovie));
    }

    @GetMapping("/hatred-movies/{id}/user")
    @Timed
    public ResponseEntity<HatredMovie> getHatredMovieUse(@PathVariable Long id) {
        log.debug("REST request to get HatredMovie : {}", id);
        HatredMovie hatredMovie = hatredMovieRepository.findByUserAndMovieId(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get(), id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hatredMovie));
    }



    @GetMapping("/num-hatred-movies/{id}")
    @Timed
    public Long getHatredMovieT(@PathVariable Long id) {
        log.debug("REST request to get HatredMovie : {}", id);
        return hatredMovieRepository.HatredMovieT(id);
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hatredMovie));
    }



    /**
     * DELETE  /hatred-movies/:id : delete the "id" hatredMovie.
     *
     * @param id the id of the hatredMovie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hatred-movies/{id}")
    @Timed
    public ResponseEntity<Void> deleteHatredMovie(@PathVariable Long id) {
        log.debug("REST request to delete HatredMovie : {}", id);
        hatredMovieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/hatred-movies/count-hatred-movie/{id}")
    @Timed
    public ResponseEntity<Integer> hatredArtist(@PathVariable Long id) {
        log.debug("REST request to get number of hates of artist");
        Integer num = Math.toIntExact(hatredMovieRepository.countHatredMovie(id));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(num));
    }
}
