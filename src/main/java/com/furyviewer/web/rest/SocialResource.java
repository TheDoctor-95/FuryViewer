package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Social;

import com.furyviewer.repository.SocialRepository;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing Social.
 */
@RestController
@RequestMapping("/api")
public class SocialResource {

    private final Logger log = LoggerFactory.getLogger(SocialResource.class);

    private static final String ENTITY_NAME = "social";

    private final SocialRepository socialRepository;

    public SocialResource(SocialRepository socialRepository) {
        this.socialRepository = socialRepository;
    }

    /**
     * POST  /socials : Create a new social.
     *
     * @param social the social to create
     * @return the ResponseEntity with status 201 (Created) and with body the new social, or with status 400 (Bad Request) if the social has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/socials")
    @Timed
    public ResponseEntity<Social> createSocial(@RequestBody Social social) throws URISyntaxException {
        log.debug("REST request to save Social : {}", social);
        if (social.getId() != null) {
            throw new BadRequestAlertException("A new social cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Social result = socialRepository.save(social);
        return ResponseEntity.created(new URI("/api/socials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /socials : Updates an existing social.
     *
     * @param social the social to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated social,
     * or with status 400 (Bad Request) if the social is not valid,
     * or with status 500 (Internal Server Error) if the social couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/socials")
    @Timed
    public ResponseEntity<Social> updateSocial(@RequestBody Social social) throws URISyntaxException {
        log.debug("REST request to update Social : {}", social);
        if (social.getId() == null) {
            return createSocial(social);
        }
        Social result = socialRepository.save(social);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, social.getId().toString()))
            .body(result);
    }

    /**
     * GET  /socials : get all the socials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socials in body
     */
    @GetMapping("/socials")
    @Timed
    public List<Social> getAllSocials() {
        log.debug("REST request to get all Socials");
        return socialRepository.findAll();
        }

    /**
     * GET  /socials/:id : get the "id" social.
     *
     * @param id the id of the social to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the social, or with status 404 (Not Found)
     */
    @GetMapping("/socials/{id}")
    @Timed
    public ResponseEntity<Social> getSocial(@PathVariable Long id) {
        log.debug("REST request to get Social : {}", id);
        Social social = socialRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(social));
    }

    // Pau comenta el maldito codigo^^

    @GetMapping("/socials/Movie/{id}")
    @Timed
    public ResponseEntity<List<Social>> getSocialMovie(@PathVariable Long id) {
        log.debug("REST request to get Social : {}", id);
        List<Social> social = socialRepository.findMarksMovie(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(social));
    }

    @GetMapping("/socials/Series/{id}")
    @Timed
    public ResponseEntity<List<Social>> getSocialSeries(@PathVariable Long id) {
        log.debug("REST request to get Social : {}", id);
        List<Social> social = socialRepository.findMarksSeries(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(social));
    }

    /**
     * DELETE  /socials/:id : delete the "id" social.
     *
     * @param id the id of the social to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/socials/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocial(@PathVariable Long id) {
        log.debug("REST request to delete Social : {}", id);
        socialRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /socials/movie-trailer-by-id : get the trailer of a movie from its id
     *
     *
     *
     */
    @GetMapping("/socials/movie-trailer/{id}")
    @Timed
    public ResponseEntity<Map<String, String>> selectMovieIdForTrailer(@PathVariable Long id) {
        log.debug("REST request to get Movie trailer : {}", id);
        String url = socialRepository.selectMoviesIdForTrailer(id);
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("url", url);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(urlMap));
    }

    /**
     * GET  /socials/series-trailer:id : get the "id" social.
     *
     * @param id the id of the social to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the social, or with status 404 (Not Found)
     */
    @GetMapping("/socials/series-trailer/{id}")
    @Timed
    public ResponseEntity<String> selectSeriesIdForTrailer(@PathVariable Long id) {
        log.debug("REST request to get Series trailer : {}", id);
        String url = socialRepository.selectSeriesIdForTrailer(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(url));
    }


}
