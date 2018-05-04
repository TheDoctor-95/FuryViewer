package com.furyviewer.service.util;

import com.furyviewer.domain.FavouriteMovie;
import com.furyviewer.repository.FavouriteMovieRepository;
import com.furyviewer.repository.FavouriteSeriesRepository;
import com.furyviewer.repository.HatredMovieRepository;
import com.furyviewer.repository.HatredSeriesRepository;
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


    public List<MultimediaActorsDTO> whatchlistMultimedia(String mulimedia, String option){
        List<MultimediaActorsDTO> multimediaActorsDTOList = new ArrayList<>();

        favouriteMovieRepository.findFavoriteMovieUserLogin(SecurityUtils.getCurrentUserLogin()).forEach(
            movie -> {
                MultimediaActorsDTO multimediaActorsDTO = new MultimediaActorsDTO();

                multimediaActorsDTO.setId(movie.getId());
                multimediaActorsDTO.setType("movie");
                multimediaActorsDTO.setReleaseDate(movie.getReleaseDate());
                multimediaActorsDTO.setTitle(movie.getName());
                multimediaActorsDTO.setUrlCartel(movie.getImgUrl());

                multimediaActorsDTOList.add(multimediaActorsDTO);
            }
        );

        return multimediaActorsDTOList;
    }
}
