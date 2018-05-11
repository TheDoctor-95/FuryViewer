package com.furyviewer.service.util;

import com.furyviewer.domain.*;
import com.furyviewer.repository.*;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.service.dto.util.MultimediaActorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar toda la informacion relacionada con la watchlist.
 * @author TheDoctor-95
 */
@Service
public class WatchlistService {

    @Autowired
    private FavouriteMovieRepository favouriteMovieRepository;

    @Autowired
    private FavouriteSeriesRepository favouriteSeriesRepository;

    @Autowired
    private HatredSeriesRepository hatredSeriesRepository;

    @Autowired
    private HatredMovieRepository hatredMovieRepository;

    @Autowired
    private SeriesStatsRepository seriesStatsRepository;

    @Autowired
    private MovieStatsRepository movieStatsRepository;

    /**
     * Devuelve la lista de movies o series dependiendo del lugar de la whatchlist donde este el usuario.
     * @param multimedia String | Tiene el valor predeterminado de movie o series para filtrar.
     * @param option String | Contiene el valor del lugar de la wathclist donde esta el usuario.
     * @param pageable Pageable | Limites de la paginacion.
     * @return List | Lista con todas las movies o series que concuerden con la opcion de de la watchlist.
     */
    public List<MultimediaActorsDTO> whatchlistMultimedia(String multimedia, String option, Pageable pageable) {
        List<MultimediaActorsDTO> multimediaActorsDTOList = new ArrayList<>();

        if (multimedia.equalsIgnoreCase("movie")) {
            List<Movie> movieList = new ArrayList<>();

            switch (option){
                case "hatred":
                    movieList = hatredMovieRepository.findHatedMovieUser(SecurityUtils.getCurrentUserLogin(), pageable);
                    break;

                case "favorite":
                    movieList = favouriteMovieRepository.findFavoriteMovieUserLogin(SecurityUtils.getCurrentUserLogin(),pageable);
                    break;

                case "pending":
                    movieList = movieStatsRepository.pendingMoviesUserLogin(SecurityUtils.getCurrentUserLogin(),pageable);
                    break;

                case "seen":
                    movieList = movieStatsRepository.seenMovie(SecurityUtils.getCurrentUserLogin(),pageable);
                    break;
            }

            movieList.forEach(
                movie -> {
                    multimediaActorsDTOList.add(trasform(movie));
                }
            );

        } else {
            List<Series> seriesList = new ArrayList<>();

            switch (option){
                case "hatred":
                    seriesList = hatredSeriesRepository.findHatedSeriesUser(SecurityUtils.getCurrentUserLogin(),pageable);
                    break;

                case "favorite":
                    seriesList = favouriteSeriesRepository.findFavoriteSeriesUserLogin(SecurityUtils.getCurrentUserLogin(),pageable);
                    break;

                case "following":
                    seriesList = seriesStatsRepository.followingSeriesUserPageable(SecurityUtils.getCurrentUserLogin(),pageable);
                    break;

                case "pending":
                    seriesList = seriesStatsRepository.pendingSeriesUser(SecurityUtils.getCurrentUserLogin(),pageable);
                    break;

                case "seen":
                    seriesList = seriesStatsRepository.seenSeriesUser(SecurityUtils.getCurrentUserLogin(),pageable);
                    break;
            }

            seriesList.forEach(
                series -> {
                    multimediaActorsDTOList.add(trasform(series));
                }
            );
        }

        return multimediaActorsDTOList;
    }

    /**
     * Prepara las series o movies en el formato MultimediaActorsDTO para optimizar la query para el frontend.
     * @param multimedia Multimedia | Series o movie que se quiere optimizar.
     * @return List | Informacion optimizada de las series o movies.
     * @see com.furyviewer.service.dto.util.MultimediaActorsDTO
     */
    public MultimediaActorsDTO trasform(Multimedia multimedia) {
        MultimediaActorsDTO multimediaActorsDTO = new MultimediaActorsDTO();

        if (multimedia.getClass() == Series.class) {
            Series series = (Series) multimedia;

            multimediaActorsDTO.setId(series.getId());
            multimediaActorsDTO.setType("series");
            multimediaActorsDTO.setReleaseDate(series.getReleaseDate());
            multimediaActorsDTO.setTitle(series.getName());
            multimediaActorsDTO.setUrlCartel(series.getImgUrl());
        } else {
            Movie movie = (Movie) multimedia;

            multimediaActorsDTO.setId(movie.getId());
            multimediaActorsDTO.setType("movie");
            multimediaActorsDTO.setReleaseDate(movie.getReleaseDate());
            multimediaActorsDTO.setTitle(movie.getName());
            multimediaActorsDTO.setUrlCartel(movie.getImgUrl());
        }

        return multimediaActorsDTO;
    }

    /**
     * Cuenta el numero de series y movies que hay en cada opcion de la watchlist.
     * @param multimedia String | Tiene el valor predeterminado de movie o series para filtrar.
     * @param option String | Contiene el valor del lugar de la wathclist donde esta el usuario.
     * @return Integer | Numero de movies o series que hay en cada opcion.
     */
    public Integer countList(String multimedia, String option){
        if (multimedia.equalsIgnoreCase("movie")) {

            switch (option){
                case "hatred":
                    return  hatredMovieRepository.countHatredMovieUser(SecurityUtils.getCurrentUserLogin());

                case "favorite":
                    return  favouriteMovieRepository.countLikesUser(SecurityUtils.getCurrentUserLogin());

                case "pending":
                    return  movieStatsRepository.countPendingMoviesUserLogin(SecurityUtils.getCurrentUserLogin());

                case "seen":
                    return  movieStatsRepository.countSeenMovie(SecurityUtils.getCurrentUserLogin());
            }
        } else {
            switch (option){
                case "hatred":
                    return hatredSeriesRepository.countHatedSeriesUser(SecurityUtils.getCurrentUserLogin());

                case "favorite":
                    return favouriteSeriesRepository.countFavoriteSeriesUserLogin(SecurityUtils.getCurrentUserLogin());

                case "following":
                    return seriesStatsRepository.countFollowingSeriesUser(SecurityUtils.getCurrentUserLogin());

                case "pending":
                    return seriesStatsRepository.countPendingSeriesUser(SecurityUtils.getCurrentUserLogin());

                case "seen":
                    return seriesStatsRepository.countSeenSeriesUser(SecurityUtils.getCurrentUserLogin());

            }
        }

        return 0;
    }
}
