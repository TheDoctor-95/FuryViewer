package com.furyviewer.service.util;


import com.furyviewer.domain.Artist;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.repository.MovieRepository;
import com.furyviewer.service.dto.util.ActorLimitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class ArtistLimitService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    public List<ActorLimitDTO> getInfoFromArtist(Long id){
        Pageable top = new PageRequest(0, 4);
        List<ActorLimitDTO> list = movieRepository.getMainActorsByMovieLimit(id, top)
        .stream()
        .map(artist -> new ActorLimitDTO(
            artist.getId(),
            artist.getName(),
            artist.getImgUrl()
        ))
            .collect(Collectors.toList());

        return list;
    }

    public List<ActorLimitDTO> getInfoFromArtistLimitless(Long id){
        List<ActorLimitDTO> list = movieRepository.getMainActorsByMovie(id)
            .stream()
            .map(artist -> new ActorLimitDTO(
                artist.getId(),
                artist.getName(),
                artist.getImgUrl()
            ))
            .collect(Collectors.toList());

        return list;
    }


    @Transactional
    public List<ActorLimitDTO> findActorBySerieId(Long serieID) {
        return artistRepository.findAllWithEagerRelationships().stream()
            .filter(artist -> artist.getEpisodes().stream()
                .anyMatch(episode -> episode.getSeason().getSeries().getId().equals(serieID)))
            .sorted((a1, a2) -> {
                long numEpisodes1 = a1.getEpisodes().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                long numEpisodes2 = a2.getEpisodes().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                return compare(numEpisodes1, numEpisodes2);
            })
            .map(artist -> new ActorLimitDTO(
                artist.getId(),
                artist.getName(),
                artist.getImgUrl()
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public List<ActorLimitDTO> findActorBySerieIdLimit(Long serieID) {
        List<ActorLimitDTO> a = artistRepository.findAllWithEagerRelationships().stream()
            .filter(artist -> artist.getEpisodes().stream()
                .anyMatch(episode -> episode.getSeason().getSeries().getId().equals(serieID)))
            .sorted((a1, a2) -> {
                long numEpisodes1 = a1.getEpisodes().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                long numEpisodes2 = a2.getEpisodes().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                return compare(numEpisodes1, numEpisodes2);
            })
            .map(artist -> new ActorLimitDTO(
                artist.getId(),
                artist.getName(),
                artist.getImgUrl()
            ))
            .collect(Collectors.toList());

        if(a.size() > 4){
            return a.subList(0,4);
        }else{
            return a;
        }
    }

    public int compare(long l1, long l2) {
        if (l1 > l2) return -1;
        else if (l1 < l2) return 1;
        else return 0;
    }
}



