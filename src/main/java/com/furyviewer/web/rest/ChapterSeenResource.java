package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.ChapterSeen;

import com.furyviewer.domain.Episode;
import com.furyviewer.repository.ChapterSeenRepository;
import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.repository.SeriesStatsRepository;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.domain.Season;
import com.furyviewer.service.dto.util.EpisodesHomeDTO;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ChapterSeen.
 */
@RestController
@RequestMapping("/api")
public class ChapterSeenResource {

    private final Logger log = LoggerFactory.getLogger(ChapterSeenResource.class);

    private static final String ENTITY_NAME = "chapterSeen";

    private final ChapterSeenRepository chapterSeenRepository;

    private final SeriesStatsRepository seriesStatsRepository;

    private final EpisodeRepository episodeRepository;

    public ChapterSeenResource(ChapterSeenRepository chapterSeenRepository, SeriesStatsRepository seriesStatsRepository, EpisodeRepository episodeRepository) {
        this.chapterSeenRepository = chapterSeenRepository;
        this.seriesStatsRepository = seriesStatsRepository;
        this.episodeRepository = episodeRepository;
    }

    /**
     * POST  /chapter-seens : Create a new chapterSeen.
     *
     * @param chapterSeen the chapterSeen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chapterSeen, or with status 400 (Bad Request) if the chapterSeen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chapter-seens")
    @Timed
    public ResponseEntity<ChapterSeen> createChapterSeen(@RequestBody ChapterSeen chapterSeen) throws URISyntaxException {
        log.debug("REST request to save ChapterSeen : {}", chapterSeen);
        if (chapterSeen.getId() != null) {
            throw new BadRequestAlertException("A new chapterSeen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChapterSeen result = chapterSeenRepository.save(chapterSeen);
        return ResponseEntity.created(new URI("/api/chapter-seens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chapter-seens : Updates an existing chapterSeen.
     *
     * @param chapterSeen the chapterSeen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chapterSeen,
     * or with status 400 (Bad Request) if the chapterSeen is not valid,
     * or with status 500 (Internal Server Error) if the chapterSeen couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chapter-seens")
    @Timed
    public ResponseEntity<ChapterSeen> updateChapterSeen(@RequestBody ChapterSeen chapterSeen) throws URISyntaxException {
        log.debug("REST request to update ChapterSeen : {}", chapterSeen);
        if (chapterSeen.getId() == null) {
            return createChapterSeen(chapterSeen);
        }
        ChapterSeen result = chapterSeenRepository.save(chapterSeen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chapterSeen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chapter-seens : get all the chapterSeens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chapterSeens in body
     */
   @GetMapping("/chapter-seens")
    @Timed
    public List<ChapterSeen> getAllChapterSeens() {
        log.debug("REST request to get all ChapterSeens");
        return chapterSeenRepository.findAll();
    }

    @GetMapping("/chapter-seens/next")
    @Timed
    @Transactional
    public List<EpisodesHomeDTO> getNextChapters() {
        log.debug("REST request to get all ChapterSeens");
        List<EpisodesHomeDTO> episodes = new ArrayList<>();

        seriesStatsRepository.followingSeriesUser(SecurityUtils.getCurrentUserLogin()).forEach(
            series -> {
                Optional<Season> maxSeasonSeenOptional = chapterSeenRepository.findBySeenAndEpisodeSeasonSeriesIdAndUserLogin(true, series.getId(), SecurityUtils.getCurrentUserLogin()).stream()
                    .map(chapterSeen ->  chapterSeen.getEpisode().getSeason())
                    .max(Comparator.comparing(com.furyviewer.domain.Season::getNumber));
                EpisodesHomeDTO episodesHomeDTO = new EpisodesHomeDTO();
                Episode nextEpisode;

                if (maxSeasonSeenOptional.isPresent()){
                    Season maxSeasonSeen = maxSeasonSeenOptional.get();
                    Episode maxEpisodeSeen = chapterSeenRepository.findBySeenAndEpisodeSeasonSeriesIdAndUserLogin(true, series.getId(), SecurityUtils.getCurrentUserLogin()).stream()
                        .map(chapterSeen -> chapterSeen.getEpisode())
                        .filter(episode -> episode.getSeason().equals(maxSeasonSeen))
                        .max(Comparator.comparing(com.furyviewer.domain.Episode::getNumber)).get();
                        if (maxEpisodeSeen.getNumber()<maxSeasonSeen.getEpisodes().size()){
                            nextEpisode = episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(maxEpisodeSeen.getNumber()+1,maxSeasonSeen.getNumber(),series.getId());
                        }else{
                            nextEpisode = episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(maxEpisodeSeen.getNumber(),maxSeasonSeen.getNumber()+1,series.getId());
                        }
                }else{
                     nextEpisode = episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(1,1,series.getId());


                }
                episodesHomeDTO.setEpisodeNumber(nextEpisode.getNumber());
                episodesHomeDTO.setId(nextEpisode.getSeason().getSeries().getId());
                episodesHomeDTO.setSeasonNumber(nextEpisode.getSeason().getNumber());
                episodesHomeDTO.setTitleEpisode(nextEpisode.getName());
                episodesHomeDTO.setSerieTitle(nextEpisode.getSeason().getSeries().getName());
                episodesHomeDTO.setUrlCartel(nextEpisode.getSeason().getSeries().getImgUrl());

                episodes.add(episodesHomeDTO);
            }
        );


        return episodes;


//
//        chapterSeenRepository.findBySeenAndUserLogin(true, SecurityUtils.getCurrentUserLogin()).stream()
//
//
//
//
//
//
//        chapterSeenRepository.findBySeenAndUserLogin(true, SecurityUtils.getCurrentUserLogin()).stream()
//            .map(chapterSeen -> {
//
//
//
//
//
//                int totalEpisodes = chapterSeen.getEpisode().getSeason().getEpisodes().size();
//                int numEpisode = chapterSeen.getEpisode().getNumber();
//
//                if (numEpisode<totalEpisodes){
//                     Episode nextEpisode = chapterSeen.getEpisode().getSeason().getEpisodes().stream()
//                        .filter(episode -> episode.getNumber().equals(numEpisode+1))
//                         .findFirst().get();
//
//                     episodes.add(nextEpisode);
//
//                }
//
//            })

    }


    /**
     * GET  /chapter-seens/:id : get the "id" chapterSeen.
     *
     * @param id the id of the chapterSeen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chapterSeen, or with status 404 (Not Found)
     */
    @GetMapping("/chapter-seens/{id}")
    @Timed
    public ResponseEntity<ChapterSeen> getChapterSeen(@PathVariable Long id) {
        log.debug("REST request to get ChapterSeen : {}", id);
        ChapterSeen chapterSeen = chapterSeenRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chapterSeen));
    }

    /**
     * DELETE  /chapter-seens/:id : delete the "id" chapterSeen.
     *
     * @param id the id of the chapterSeen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chapter-seens/{id}")
    @Timed
    public ResponseEntity<Void> deleteChapterSeen(@PathVariable Long id) {
        log.debug("REST request to delete ChapterSeen : {}", id);
        chapterSeenRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
