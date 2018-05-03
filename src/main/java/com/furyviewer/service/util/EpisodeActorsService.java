package com.furyviewer.service.util;

import com.furyviewer.domain.Artist;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.repository.MovieRepository;
import com.furyviewer.service.dto.util.MultimediaActorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EpisodeActorsService {

    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MovieRepository movieRepository;


    public List<MultimediaActorsDTO> getSeriesAndMoviesFromActor(Artist artist){

        List<MultimediaActorsDTO> multimediaSeriesActorsDTOs = episodeRepository.getEpisodeByActorsOrderByReleaseDate(artist)
            .stream()
            .map(episode -> episode.getSeason().getSeries())
            .distinct()
            .map(series ->  new MultimediaActorsDTO(
                series.getId(),
                series.getName(),
                series.getReleaseDate(),
                "series",
                series.getImgUrl()
            ))
            .collect(Collectors.toList());





        List<MultimediaActorsDTO> multimediaMoviesActorsDTOs = movieRepository.getByArtistOrderbyDate(artist)
            .stream()
            .distinct()
            .map(movie ->  new MultimediaActorsDTO(
                movie.getId(),
                movie.getName(),
                movie.getReleaseDate(),
                "movie",
                movie.getImgUrl()
            ))
            .collect(Collectors.toList());


        List<MultimediaActorsDTO> multimediaMoviesDirectorsDTOs = movieRepository.findMovieByDirectorOrderByReleaseDate(artist)
            .stream()
            .distinct()
            .map(movie ->  new MultimediaActorsDTO(
                movie.getId(),
                movie.getName(),
                movie.getReleaseDate(),
                "movie",
                movie.getImgUrl()
            ))
            .collect(Collectors.toList());

        List<MultimediaActorsDTO> multimediaMoviesScriptwritersDTOs = movieRepository.findMovieByScriptwriterOrderByReleaseDate(artist)
            .stream()
            .distinct()
            .map(movie ->  new MultimediaActorsDTO(
                movie.getId(),
                movie.getName(),
                movie.getReleaseDate(),
                "movie",
                movie.getImgUrl()
            ))
            .collect(Collectors.toList());

        List<MultimediaActorsDTO> multimediaActorsDTO = new ArrayList<>();
        multimediaActorsDTO.addAll(multimediaMoviesActorsDTOs);
        multimediaActorsDTO.addAll(multimediaMoviesDirectorsDTOs);
        multimediaActorsDTO.addAll(multimediaMoviesScriptwritersDTOs);
        multimediaActorsDTO.addAll(multimediaSeriesActorsDTOs);


        multimediaActorsDTO.sort(Comparator.comparing(MultimediaActorsDTO::getReleaseDate).reversed());

        return multimediaActorsDTO;



    }
}
