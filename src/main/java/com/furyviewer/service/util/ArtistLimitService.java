package com.furyviewer.service.util;


import com.furyviewer.domain.Artist;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.MovieRepository;
import com.furyviewer.service.dto.util.ActorsLimitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistLimitService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public List<ActorsLimitDTO> getInfoFromArtist(Long id){

        Pageable top = new PageRequest(0, 4);
        List<ActorsLimitDTO> list = movieRepository.getMainActorsByMovieLimit(id, top)
        .stream()
        .map(artist -> new ActorsLimitDTO(
            artist.getId(),
            artist.getName(),
            artist.getImgUrl()
        ))
            .collect(Collectors.toList());

    return list;

    }


    public List<ActorsLimitDTO> getInfoFromArtistLimitless(Long id){

        List<ActorsLimitDTO> list = movieRepository.getMainActorsByMovie(id)
            .stream()
            .map(artist -> new ActorsLimitDTO(
                artist.getId(),
                artist.getName(),
                artist.getImgUrl()
            ))
            .collect(Collectors.toList());

        return list;

    }
}
