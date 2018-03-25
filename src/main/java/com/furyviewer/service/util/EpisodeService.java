package com.furyviewer.service.util;


import com.furyviewer.domain.Episode;
import com.furyviewer.domain.Season;
import com.furyviewer.repository.ChapterSeenRepository;
import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.repository.SeriesStatsRepository;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.service.dto.util.EpisodesHomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<EpisodesHomeDTO> getNextEpisodes(){
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

                if(nextEpisode!=null) {
                    episodesHomeDTO.setEpisodeNumber(nextEpisode.getNumber());
                    episodesHomeDTO.setId(nextEpisode.getSeason().getSeries().getId());
                    episodesHomeDTO.setSeasonNumber(nextEpisode.getSeason().getNumber());
                    episodesHomeDTO.setTitleEpisode(nextEpisode.getName());
                    episodesHomeDTO.setTitleSeries(nextEpisode.getSeason().getSeries().getName());
                    episodesHomeDTO.setUrlCartel(nextEpisode.getSeason().getSeries().getImgUrl());

                    episodes.add(episodesHomeDTO);
                }
            }
        );
        return episodes;

    }

}
