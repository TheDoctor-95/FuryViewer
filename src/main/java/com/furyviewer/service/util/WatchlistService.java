package com.furyviewer.service.util;

import com.furyviewer.domain.*;
import com.furyviewer.repository.*;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.service.dto.util.MultimediaActorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<MultimediaActorsDTO> whatchlistMultimedia(String mulimedia, String option) {
        List<MultimediaActorsDTO> multimediaActorsDTOList = new ArrayList<>();

        if (mulimedia.equalsIgnoreCase("movie")) {

            List<Movie> movieList = new ArrayList<>();



            switch (option){
                case "hatred":

                    movieList = hatredMovieRepository.findHatedMovieUser(SecurityUtils.getCurrentUserLogin());
                    break;

                case "favorite":

                    movieList = favouriteMovieRepository.findFavoriteMovieUserLogin(SecurityUtils.getCurrentUserLogin());
                    break;

                case "pending":

                    movieList = movieStatsRepository.pendingMoviesUserLogin(SecurityUtils.getCurrentUserLogin());
                    break;

                case "seen":

                    movieList = movieStatsRepository.seenMovie(SecurityUtils.getCurrentUserLogin());
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

                    seriesList = hatredSeriesRepository.findHatedSeriesUser(SecurityUtils.getCurrentUserLogin());
                    break;

                case "favorite":

                    seriesList = favouriteSeriesRepository.findFavoriteSeriesUserLogin(SecurityUtils.getCurrentUserLogin());
                    break;

                case "following":

                    seriesList = seriesStatsRepository.followingSeriesUser(SecurityUtils.getCurrentUserLogin());
                    break;

                case "pending":

                    seriesList = seriesStatsRepository.pendingSeriesUser(SecurityUtils.getCurrentUserLogin());
                    break;

                case "seen":

                    seriesList = seriesStatsRepository.seenSeriesUser(SecurityUtils.getCurrentUserLogin());
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
}
