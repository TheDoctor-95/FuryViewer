package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.SeriesStats;

import com.furyviewer.domain.enumeration.SeriesStatsEnum;
import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.repository.SeriesStatsRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing SeriesStats.
 */
@RestController
@RequestMapping("/api")
public class SeriesStatsResource {

    private final Logger log = LoggerFactory.getLogger(SeriesStatsResource.class);

    private static final String ENTITY_NAME = "seriesStats";

    private final SeriesStatsRepository seriesStatsRepository;

    private final SeriesRepository seriesRepository;

    private final UserRepository userRepository;


    public SeriesStatsResource(SeriesStatsRepository seriesStatsRepository, SeriesRepository seriesRepository, UserRepository userRepository) {
        this.seriesStatsRepository = seriesStatsRepository;

        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /series-stats : Create a new seriesStats.
     *
     * @param seriesStats the seriesStats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seriesStats, or with status 400 (Bad Request) if the seriesStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/series-stats")
    @Timed
    public ResponseEntity<SeriesStats> createSeriesStats(@RequestBody SeriesStats seriesStats) throws URISyntaxException {
        log.debug("REST request to save SeriesStats : {}", seriesStats);
        if (seriesStats.getId() != null) {
            throw new BadRequestAlertException("A new seriesStats cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<SeriesStats> aux = seriesStatsRepository.findByUserAndSerieId(
            userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get(), seriesStats.getSerie().getId());

        if (aux.isPresent()) {
            if (aux.get().getStatus() != null) {
                if (seriesStats.getStatus().toString().equalsIgnoreCase(aux.get().getStatus().toString())) {
                    seriesStats.setStatus(null);
                }
            }
            seriesStats.setId(aux.get().getId());
        }

        seriesStats.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        seriesStats.setDate(ZonedDateTime.now());

        SeriesStats result = seriesStatsRepository.save(seriesStats);
        return ResponseEntity.created(new URI("/api/series-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /series-stats : Updates an existing seriesStats.
     *
     * @param seriesStats the seriesStats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seriesStats,
     * or with status 400 (Bad Request) if the seriesStats is not valid,
     * or with status 500 (Internal Server Error) if the seriesStats couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/series-stats")
    @Timed
    public ResponseEntity<SeriesStats> updateSeriesStats(@RequestBody SeriesStats seriesStats) throws URISyntaxException {
        log.debug("REST request to update SeriesStats : {}", seriesStats);
        if (seriesStats.getId() == null) {
            return createSeriesStats(seriesStats);
        }
        SeriesStats result = seriesStatsRepository.save(seriesStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seriesStats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /series-stats : get all the seriesStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seriesStats in body
     */
    @GetMapping("/series-stats")
    @Timed
    public List<SeriesStats> getAllSeriesStats() {
        log.debug("REST request to get all SeriesStats");
        return seriesStatsRepository.findAll();
    }

    /**
     * GET  /series-stats/:id : get the "id" seriesStats.
     *
     * @param id the id of the seriesStats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seriesStats, or with status 404 (Not Found)
     */
    @GetMapping("/series-stats/{id}")
    @Timed
    public ResponseEntity<SeriesStats> getSeriesStats(@PathVariable Long id) {
        log.debug("REST request to get SeriesStats : {}", id);
        SeriesStats seriesStats = seriesStatsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seriesStats));
    }

    @GetMapping("/series-stats/following/{id}")
    @Timed
    public Long getFollowingSeriesStats(@PathVariable Long id) {
        log.debug("REST request to get SeriesStats : {}", id);
        return seriesStatsRepository.FollowingSeriesStats(id);
        //  return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seriesStats));
    }

    @GetMapping("/series-stats/pending/{id}")
    @Timed
    public Long getPendingSeriesStats(@PathVariable Long id) {
        log.debug("REST request to get SeriesStats : {}", id);
        return seriesStatsRepository.PendingSeriesStats(id);
        // return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seriesStats));
    }

    @GetMapping("/series-stats/seen/{id}")
    @Timed
    public Long getSeenSeriesStats(@PathVariable Long id) {
        log.debug("REST request to get SeriesStats : {}", id);
        return seriesStatsRepository.SeenSeriesStats(id);
// return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seriesStats));
    }

    @GetMapping("/series-stats/status/{id}")
    @Timed
    public ResponseEntity<Map<String, String>> selectSeriesStatus(@PathVariable Long id) {
        log.debug("REST request to get SeriesStatus : {}", id);
        String status = seriesStatsRepository.selectSeriesStatus(id, SecurityUtils.getCurrentUserLogin());
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("url", status);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statusMap));

    }

    @PostMapping("/series-stats/idSeries/{id}/state/{state}")
    @Timed
    public ResponseEntity<SeriesStats> MovieState(@PathVariable Long id, @PathVariable String state) throws URISyntaxException {

        SeriesStats ss = new SeriesStats();
        ss.setSerie(seriesRepository.findOne(id));
        if (state.equalsIgnoreCase("seen")) ss.setStatus(SeriesStatsEnum.SEEN);
        else if (state.equalsIgnoreCase("pending")) ss.setStatus(SeriesStatsEnum.PENDING);
        else ss.setStatus(SeriesStatsEnum.FOLLOWING);
        return createSeriesStats(ss);
    }

    /**
     * DELETE  /series-stats/:id : delete the "id" seriesStats.
     *
     * @param id the id of the seriesStats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/series-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeriesStats(@PathVariable Long id) {
        log.debug("REST request to delete SeriesStats : {}", id);
        seriesStatsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
