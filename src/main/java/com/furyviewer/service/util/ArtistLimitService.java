package com.furyviewer.service.util;

import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.MovieRepository;
import com.furyviewer.service.dto.util.ActorLimitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service que se encarga de optimizar la informacion de los artist al modelo de ActorLimitDTO.
 * @author Whoger
 * @see com.furyviewer.service.dto.util.ActorLimitDTO
 */
@Service
public class ArtistLimitService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ArtistRepository artistRepository;

    /**
     * Prepara cuatro artist de una movie en el formato de ActorsLimitDTO para optimizar la query para el frontend.
     * @param id Long | Id de la movie de la que se quieren saber los artist.
     * @return List | Informacion optimizada de los artist.
     */
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

    /**
     * Prepara todos los artist de una movie en el formato de ActorsLimitDTO para optimizar la query para el frontend.
     * @param id Long | Id de la movie de la que se quieren saber los artist.
     * @return List | Informacion optimizada de los artist.
     */
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

    /**
     * Prepara todoss los artist de una series en el formato de ActorsLimitDTO para optimizar la query para el frontend.
     * @param serieID Long | Id de la series de la que se quieren saber los artist.
     * @return List | Informacion optimizada de los artist.
     */
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

    /**
     * Prepara cuatro artist de una series en el formato de ActorsLimitDTO para optimizar la query para el frontend.
     * @param serieID Long | Id de la series de la que se quieren saber los artist.
     * @return List | Informacion optimizada de los artist.
     */
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

    /**
     * Ayuda a ordenar en orden descendiente los artist.
     * @param l1 long | Valor 1 a comparar.
     * @param l2 long | Valor 2 a comparar.
     * @return int
     */
    public int compare(long l1, long l2) {
        if (l1 > l2) return -1;
        else if (l1 < l2) return 1;
        else return 0;
    }
}



