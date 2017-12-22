package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.FavouriteSeries;

import com.furyviewer.repository.FavouriteSeriesRepository;
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
 * REST controller for managing FavouriteSeries.
 */
@RestController
@RequestMapping("/api")
public class FavouriteSeriesResource {

    private final Logger log = LoggerFactory.getLogger(FavouriteSeriesResource.class);

    private static final String ENTITY_NAME = "favouriteSeries";

    private final FavouriteSeriesRepository favouriteSeriesRepository;

    private final UserRepository userRepository;

    public FavouriteSeriesResource(FavouriteSeriesRepository favouriteSeriesRepository, UserRepository userRepository) {
        this.favouriteSeriesRepository = favouriteSeriesRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /favourite-series : Create a new favouriteSeries.
     *
     * @param favouriteSeries the favouriteSeries to create
     * @return the ResponseEntity with status 201 (Created) and with body the new favouriteSeries, or with status 400 (Bad Request) if the favouriteSeries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/favourite-series")
    @Timed
    public ResponseEntity<FavouriteSeries> createFavouriteSeries(@RequestBody FavouriteSeries favouriteSeries) throws URISyntaxException {
        log.debug("REST request to save FavouriteSeries : {}", favouriteSeries);
        if (favouriteSeries.getId() != null) {
            throw new BadRequestAlertException("A new favouriteSeries cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<FavouriteSeries> existingFavouriteSeries = favouriteSeriesRepository.findBySeriesAndUserLogin(favouriteSeries.getSeries(), SecurityUtils.getCurrentUserLogin());

        if(existingFavouriteSeries.isPresent()){
            throw new BadRequestAlertException("El serie ya esta añadida a favoritos", ENTITY_NAME, "favoriteExist");
        }

        favouriteSeries.setDate(ZonedDateTime.now());
        favouriteSeries.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        FavouriteSeries result = favouriteSeriesRepository.save(favouriteSeries);
        return ResponseEntity.created(new URI("/api/favourite-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /favourite-series : Updates an existing favouriteSeries.
     *
     * @param favouriteSeries the favouriteSeries to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated favouriteSeries,
     * or with status 400 (Bad Request) if the favouriteSeries is not valid,
     * or with status 500 (Internal Server Error) if the favouriteSeries couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/favourite-series")
    @Timed
    public ResponseEntity<FavouriteSeries> updateFavouriteSeries(@RequestBody FavouriteSeries favouriteSeries) throws URISyntaxException {
        log.debug("REST request to update FavouriteSeries : {}", favouriteSeries);
        if (favouriteSeries.getId() == null) {
            return createFavouriteSeries(favouriteSeries);
        }
        FavouriteSeries result = favouriteSeriesRepository.save(favouriteSeries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, favouriteSeries.getId().toString()))
            .body(result);
    }

    /**
     * GET  /favourite-series : get all the favouriteSeries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of favouriteSeries in body
     */
    @GetMapping("/favourite-series")
    @Timed
    public List<FavouriteSeries> getAllFavouriteSeries() {
        log.debug("REST request to get all FavouriteSeries");
        return favouriteSeriesRepository.findAll();
        }

    /**
     * GET  /favourite-series/:id : get the "id" favouriteSeries.
     *
     * @param id the id of the favouriteSeries to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the favouriteSeries, or with status 404 (Not Found)
     */
    @GetMapping("/favourite-series/{id}")
    @Timed
    public ResponseEntity<FavouriteSeries> getFavouriteSeries(@PathVariable Long id) {
        log.debug("REST request to get FavouriteSeries : {}", id);
        FavouriteSeries favouriteSeries = favouriteSeriesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(favouriteSeries));
    }


    @GetMapping("/num-favourite-series/{id}")
    @Timed
    public Long getNumFavsSerie(@PathVariable Long id) {
        log.debug("REST request to get FavouriteSeries : {}", id);
        return favouriteSeriesRepository.NumFavsSerie(id);
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(favouriteSeries));
    }

    /**
     * DELETE  /favourite-series/:id : delete the "id" favouriteSeries.
     *
     * @param id the id of the favouriteSeries to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/favourite-series/{id}")
    @Timed
    public ResponseEntity<Void> deleteFavouriteSeries(@PathVariable Long id) {
        log.debug("REST request to delete FavouriteSeries : {}", id);
        favouriteSeriesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
