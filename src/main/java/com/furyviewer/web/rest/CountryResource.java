package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Country;

import com.furyviewer.repository.*;
import com.furyviewer.service.GoogleMaps.Service.GoogleMapsDTOService;
import com.furyviewer.service.dto.GoogleMaps.GoogleMapsDTO;
import com.furyviewer.service.dto.util.UserStats;
import com.furyviewer.service.util.MarksService;
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
 * REST controller for managing Country.
 */
@RestController
@RequestMapping("/api")
public class CountryResource {

    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    private static final String ENTITY_NAME = "country";

    private final CountryRepository countryRepository;

    private final MarksService homeService;

    private final GoogleMapsDTOService googleMapsDTOService;

    private final ArtistRepository artistRepository;

    private final MovieRepository movieRepository;

    private final SeriesRepository seriesRepository;

    private final UserRepository userRepository;

    public CountryResource(CountryRepository countryRepository, MarksService homeService, GoogleMapsDTOService googleMapsDTOService, ArtistRepository artistRepository, MovieRepository movieRepository, SeriesRepository seriesRepository, UserRepository userRepository) {
        this.countryRepository = countryRepository;
        this.homeService = homeService;
        this.googleMapsDTOService = googleMapsDTOService;
        this.artistRepository = artistRepository;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /countries : Create a new country.
     *
     * @param country the country to create
     * @return the ResponseEntity with status 201 (Created) and with body the new country, or with status 400 (Bad Request) if the country has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/countries")
    @Timed
    public ResponseEntity<Country> createCountry(@RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to save Country : {}", country);
        if (country.getId() != null) {
            throw new BadRequestAlertException("A new country cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Country result = countryRepository.save(country);
        return ResponseEntity.created(new URI("/api/countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /countries : Updates an existing country.
     *
     * @param country the country to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated country,
     * or with status 400 (Bad Request) if the country is not valid,
     * or with status 500 (Internal Server Error) if the country couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/countries")
    @Timed
    public ResponseEntity<Country> updateCountry(@RequestBody Country country) throws URISyntaxException {
        log.debug("REST request to update Country : {}", country);
        if (country.getId() == null) {
            return createCountry(country);
        }
        Country result = countryRepository.save(country);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, country.getId().toString()))
            .body(result);
    }

    /**
     * GET  /countries : get all the countries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of countries in body
     */
    @GetMapping("/countries")
    @Timed
    public List<Country> getAllCountries() {
        log.debug("REST request to get all Countries");
        return countryRepository.findAll();
        }

    /**
     * GET  /countries/:id : get the "id" country.
     *
     * @param id the id of the country to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the country, or with status 404 (Not Found)
     */
    @GetMapping("/countries/{id}")
    @Timed
    public ResponseEntity<Country> getCountry(@PathVariable Long id) {
        log.debug("REST request to get Country : {}", id);
        Country country = countryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(country));
    }

    /**
     *
     * Find countries by name (commit obligado.... Pau e Ibra son malos....)
     *
     * @param name
     * @return
     */
    @GetMapping("/countriesByName/{name}")
    @Timed
    public ResponseEntity<List<Country>> findCountryByName(@PathVariable String name) {
        log.debug("REST request to get Companies by name", name);
        List<Country> country =countryRepository.findCountryByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(country));
    }

    /**
     * DELETE  /countries/:id : delete the "id" country.
     *
     * @param id the id of the country to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/countries/{id}")
    @Timed
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        log.debug("REST request to delete Country : {}", id);
        countryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/country-api/test")
    @Timed
    public GoogleMapsDTO getTestInicial() throws Exception {
        return  googleMapsDTOService.getCoordinates("usa");
    }

    @GetMapping("/countries/count-absolute-favourite/")
    @Timed
    public ResponseEntity<Integer> absoluteCountFav() {
        log.debug("REST request to get number of likes of artist");
        Integer num = Math.toIntExact(homeService.totalFavorites());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(num));
    }

    @GetMapping("/countries/count-absolute-hatred/")
    @Timed
    public ResponseEntity<Integer> absoluteCountHatred() {
        log.debug("REST request to get number of likes of artist");
        Integer num = Math.toIntExact(homeService.totalHatreds());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(num));
    }

    @GetMapping("/countries/count-absolute-artist/")
    @Timed
    public ResponseEntity<Integer> absoluteCountArtist() {
        log.debug("REST request to get number of likes of artist");
        Integer num = Math.toIntExact(artistRepository.totalArtist());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(num));
    }

    @GetMapping("/countries/count-absolute-movie/")
    @Timed
    public ResponseEntity<Integer> absoluteCountMovie() {
        log.debug("REST request to get number of likes of artist");
        Integer num = Math.toIntExact(movieRepository.totalMovies());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(num));
    }

    @GetMapping("/countries/count-absolute-series/")
    @Timed
    public ResponseEntity<Integer> absoluteCountSeries() {
        log.debug("REST request to get number of likes of artist");
        Integer num = Math.toIntExact(seriesRepository.totalSeries());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(num));
    }

    @GetMapping("/countries/count-absolute-user/")
    @Timed
    public ResponseEntity<Integer> absoluteCountUser() {
        log.debug("REST request to get number of likes of artist");
        Integer num = Math.toIntExact(userRepository.totalUsers());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(num));
    }

    @GetMapping("/countries/user-stats/")
    @Timed
    public ResponseEntity<UserStats> absoluteUserStats() {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(homeService.stats()));
    }
}
