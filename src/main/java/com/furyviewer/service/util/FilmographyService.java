package com.furyviewer.service.util;

import com.furyviewer.domain.Artist;
import com.furyviewer.repository.*;
import com.furyviewer.service.dto.RateMovieStats;
import com.furyviewer.service.dto.util.MultimediaActorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service que se encarga de crear una lista de movies y series para un artista.
 * @author IFriedkin
 * @author Whoger
 */
@Service
public class FilmographyService {
    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RateMovieRepository rateMovieRepository;

    @Autowired
    private GenreRepository genreRepository;

    /**
     * Se encarga de buscar todos los lugares donde ha trabajado un artist para construir una filmografia.
     * @param artist Artist
     * @return List | Lista con todas las movies y series donde ha trabajado el artist.
     */
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

        List<MultimediaActorsDTO> multimediaSeriesDirectorsDTOs = episodeRepository.findEpisodeByDirectorOrderByReleaseDate(artist)
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

        List<MultimediaActorsDTO> multimediaSeriesScriptwritersDTOs = episodeRepository.findEpisodeByScriptwriterOrderByReleaseDate(artist)
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

        multimediaActorsDTO = filterArtist(multimediaActorsDTO, multimediaMoviesActorsDTOs);
        multimediaActorsDTO = filterArtist(multimediaActorsDTO, multimediaMoviesDirectorsDTOs);
        multimediaActorsDTO = filterArtist(multimediaActorsDTO, multimediaMoviesScriptwritersDTOs);
        multimediaActorsDTO = filterArtist(multimediaActorsDTO, multimediaSeriesActorsDTOs);
        multimediaActorsDTO = filterArtist(multimediaActorsDTO, multimediaSeriesDirectorsDTOs);
        multimediaActorsDTO = filterArtist(multimediaActorsDTO, multimediaSeriesScriptwritersDTOs);

        multimediaActorsDTO.sort(Comparator.comparing(MultimediaActorsDTO::getReleaseDate).reversed());

        return multimediaActorsDTO;
    }

    /**
     * Filtra los artist para que en caso de aparecer en diferentes movies o series no se repitan.
     * @param multimediaActorsDTO List | Se guardará la información de las movies y series sin que se re
     * @param multimediaArtistDTOs List | Lista de movies o series que se quiere filtrar.
     * @return List | Lista filtrada de movies y series.
     */
    public List<MultimediaActorsDTO> filterArtist (List<MultimediaActorsDTO> multimediaActorsDTO,
                                                   List<MultimediaActorsDTO> multimediaArtistDTOs) {
        for (MultimediaActorsDTO multi : multimediaArtistDTOs) {
            if (multimediaActorsDTO.isEmpty()) multimediaActorsDTO.add(multi);

            if(!multimediaActorsDTO.contains(multi)) multimediaActorsDTO.add(multi);
        }

        return multimediaActorsDTO;
    }

    public List<MultimediaActorsDTO> topByGenre(Long idGenre) {
        Pageable topTen = new PageRequest(0, 10);
        List<RateMovieStats> rateMovieStats = rateMovieRepository.getMovieStats(genreRepository.findOne(idGenre), topTen);
        List<MultimediaActorsDTO> multimediaActorsDTO = null;

        for (RateMovieStats rate : rateMovieStats) {
            MultimediaActorsDTO multimediaActors =
                new MultimediaActorsDTO(rate.getMovie().getId(),rate.getMovie().getName(),
                    rate.getMovie().getReleaseDate(),"movie",rate.getMovie().getImgUrl(),rate.getAvg());

            multimediaActorsDTO.add(multimediaActors);
        }

        return multimediaActorsDTO;
    }
}
