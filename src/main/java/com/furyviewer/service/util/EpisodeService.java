package com.furyviewer.service.util;


import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Episode;
import com.furyviewer.domain.Season;
import com.furyviewer.domain.Series;
import com.furyviewer.repository.*;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.service.dto.util.EpisodeSerieDTO;
import com.furyviewer.service.dto.util.EpisodesHomeDTO;
import com.furyviewer.web.rest.EpisodeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EpisodeService {

    @Autowired
    private SeriesStatsRepository seriesStatsRepository;

    @Autowired
    private ChapterSeenRepository chapterSeenRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    private final Logger log = LoggerFactory.getLogger(EpisodeResource.class);

    @Timed
    public List<EpisodesHomeDTO> getNextEpisodes(){


        log.debug("getNextEpisodes current time: 1 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());

        List<EpisodesHomeDTO> episodes = new ArrayList<>();
        seriesStatsRepository.followingSeriesUser(SecurityUtils.getCurrentUserLogin()).forEach(
            series -> {
                log.debug("getNextEpisodes current time: 2 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
                Episode nextEpisode = nextEpisode(series);
                EpisodesHomeDTO episodesHomeDTO = new EpisodesHomeDTO();

                log.debug("getNextEpisodes current time: 8 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
                if(nextEpisode!=null &&
                    (nextEpisode.getReleaseDate().isBefore(LocalDate.now()) || nextEpisode.getReleaseDate().isEqual(LocalDate.now()))) {
                    episodesHomeDTO.setEpisodeNumber(nextEpisode.getNumber());
                    episodesHomeDTO.setId(nextEpisode.getSeason().getSeries().getId());
                    episodesHomeDTO.setSeasonNumber(nextEpisode.getSeason().getNumber());
                    episodesHomeDTO.setTitleEpisode(nextEpisode.getName());
                    episodesHomeDTO.setTitleSeries(nextEpisode.getSeason().getSeries().getName());
                    episodesHomeDTO.setUrlCartel(nextEpisode.getSeason().getSeries().getImgUrl());

                    episodes.add(episodesHomeDTO);
                }
                log.debug("getNextEpisodes current time: 9", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
            }
        );
        return episodes;

    }

    public Long actualSeason(Long id){
        Series series = seriesRepository.findOne(id);
        return nextEpisode(series).getSeason().getId();
    }

    public Episode nextEpisode(Series series){


        Optional<Season> maxSeasonSeenOptional = chapterSeenRepository.findBySeenAndEpisodeSeasonSeriesIdAndUserLogin(true, series.getId(), SecurityUtils.getCurrentUserLogin()).stream()
            .map(chapterSeen ->  chapterSeen.getEpisode().getSeason())
            .max(Comparator.comparing(com.furyviewer.domain.Season::getNumber));


        log.debug("getNextEpisodes current time: 3 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
        if (maxSeasonSeenOptional.isPresent()){
            Season maxSeasonSeen = maxSeasonSeenOptional.get();
            log.debug("getNextEpisodes current time: 4 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
            Episode maxEpisodeSeen = chapterSeenRepository.findBySeenAndEpisodeSeasonSeriesIdAndUserLogin(true, series.getId(), SecurityUtils.getCurrentUserLogin()).stream()
                .map(chapterSeen -> chapterSeen.getEpisode())
                .filter(episode -> episode.getSeason().equals(maxSeasonSeen))
                .max(Comparator.comparing(com.furyviewer.domain.Episode::getNumber)).get();

            if (maxEpisodeSeen.getNumber()<maxSeasonSeen.getEpisodes().size()){
                log.debug("getNextEpisodes current time: 5 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
                return episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(maxEpisodeSeen.getNumber()+1,maxSeasonSeen.getNumber(),series.getId());
            }else{
                log.debug("getNextEpisodes current time: 6 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
                return episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(1,maxSeasonSeen.getNumber()+1,series.getId());
            }
        }else{
            log.debug("getNextEpisodes current time: 7 ", com.codahale.metrics.Clock.CpuTimeClock.defaultClock().getTime());
            return episodeRepository.findByNumberAndSeasonNumberAndSeasonSeriesId(1,1,series.getId());

        }
    }

    public List<EpisodeSerieDTO> chaptersSeriesBySeasonId(Long id){
        List<EpisodeSerieDTO> episodeSerieDTO = new ArrayList<EpisodeSerieDTO>();
        episodeRepository.getEpisodeBySeason(id).forEach(
            episode -> {
                EpisodeSerieDTO esdto = new EpisodeSerieDTO();
                esdto.setId(episode.getId());
                esdto.setDuration(episode.getDuration());
                esdto.setNumber(episode.getNumber());
                esdto.setTitle(episode.getName());
                esdto.setReleaseDate(episode.getReleaseDate());
                esdto.setPlot(episode.getDescription());
                try{
                    esdto.setSeen(chapterSeenRepository.isSeen(SecurityUtils.getCurrentUserLogin(),esdto.getId()));
                }catch (Exception ex){
                    esdto.setSeen(false);
                }


                episodeSerieDTO.add(esdto);
            }
        );

    return episodeSerieDTO;

    }

}
