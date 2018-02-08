package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.AchievementsAchievs;

import com.furyviewer.repository.AchievementsAchievsRepository;
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
 * REST controller for managing AchievementsAchievs.
 */
@RestController
@RequestMapping("/api")
public class AchievementsAchievsResource {

    private final Logger log = LoggerFactory.getLogger(AchievementsAchievsResource.class);

    private static final String ENTITY_NAME = "achievementsAchievs";

    private final AchievementsAchievsRepository achievementsAchievsRepository;

    public AchievementsAchievsResource(AchievementsAchievsRepository achievementsAchievsRepository) {
        this.achievementsAchievsRepository = achievementsAchievsRepository;
    }

    /**
     * POST  /achievements-achievs : Create a new achievementsAchievs.
     *
     * @param achievementsAchievs the achievementsAchievs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new achievementsAchievs, or with status 400 (Bad Request) if the achievementsAchievs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/achievements-achievs")
    @Timed
    public ResponseEntity<AchievementsAchievs> createAchievementsAchievs(@RequestBody AchievementsAchievs achievementsAchievs) throws URISyntaxException {
        log.debug("REST request to save AchievementsAchievs : {}", achievementsAchievs);
        if (achievementsAchievs.getId() != null) {
            throw new BadRequestAlertException("A new achievementsAchievs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AchievementsAchievs result = achievementsAchievsRepository.save(achievementsAchievs);
        return ResponseEntity.created(new URI("/api/achievements-achievs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /achievements-achievs : Updates an existing achievementsAchievs.
     *
     * @param achievementsAchievs the achievementsAchievs to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated achievementsAchievs,
     * or with status 400 (Bad Request) if the achievementsAchievs is not valid,
     * or with status 500 (Internal Server Error) if the achievementsAchievs couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/achievements-achievs")
    @Timed
    public ResponseEntity<AchievementsAchievs> updateAchievementsAchievs(@RequestBody AchievementsAchievs achievementsAchievs) throws URISyntaxException {
        log.debug("REST request to update AchievementsAchievs : {}", achievementsAchievs);
        if (achievementsAchievs.getId() == null) {
            return createAchievementsAchievs(achievementsAchievs);
        }
        AchievementsAchievs result = achievementsAchievsRepository.save(achievementsAchievs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, achievementsAchievs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /achievements-achievs : get all the achievementsAchievs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of achievementsAchievs in body
     */
    @GetMapping("/achievements-achievs")
    @Timed
    public List<AchievementsAchievs> getAllAchievementsAchievs() {
        log.debug("REST request to get all AchievementsAchievs");
        return achievementsAchievsRepository.findAll();
        }

    /**
     * GET  /achievements-achievs/:id : get the "id" achievementsAchievs.
     *
     * @param id the id of the achievementsAchievs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the achievementsAchievs, or with status 404 (Not Found)
     */
    @GetMapping("/achievements-achievs/{id}")
    @Timed
    public ResponseEntity<AchievementsAchievs> getAchievementsAchievs(@PathVariable Long id) {
        log.debug("REST request to get AchievementsAchievs : {}", id);
        AchievementsAchievs achievementsAchievs = achievementsAchievsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(achievementsAchievs));
    }

    /**
     * DELETE  /achievements-achievs/:id : delete the "id" achievementsAchievs.
     *
     * @param id the id of the achievementsAchievs to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/achievements-achievs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAchievementsAchievs(@PathVariable Long id) {
        log.debug("REST request to delete AchievementsAchievs : {}", id);
        achievementsAchievsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
