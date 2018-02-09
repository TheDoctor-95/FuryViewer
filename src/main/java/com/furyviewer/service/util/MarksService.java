package com.furyviewer.service.util;

import com.furyviewer.domain.Movie;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.Social;
import com.furyviewer.repository.SocialRepository;
import com.furyviewer.service.dto.OpenMovieDatabase.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarksService {

    @Autowired
    SocialRepository socialRepository;

    public void markTransformationSeries(List<Rating> ratings, Series ss) {


        for (Rating r :
            ratings) {
            Optional<Social> seriesOptional = socialRepository.findBySeriesAndType(ss, r.getSource());
            if (!seriesOptional.isPresent()) {
                Social s = new Social();
                s.setType(r.getSource());
                s.setSeries(ss);
                s.setUrl(markTranformation(r.getSource(), r.getValue()));

                socialRepository.save(s);
            }
        }

    }

    private String markTranformation(String source, String value) {
        switch (source.toLowerCase()) {
            case "internet movie database":
                return tranformIMDB(value);
            case "rotten tomatoes":
                return tranformRT(value);
            case "metacritic":
                return tranformMC(value);
            default:
                return null;
        }
    }

    public void markTransformationMovie(List<Rating> ratings, Movie m) {
        for (Rating r :
            ratings) {
            Optional<Social> movieOptional = socialRepository.findByMovieAndType(m, r.getSource());
            if (!movieOptional.isPresent()) {
                Social s = new Social();
                s.setMovie(m);
                s.setType(r.getSource());
                s.setUrl(markTranformation(r.getSource(), r.getValue()));
                socialRepository.save(s);
            }

        }

    }

    public String tranformIMDB(String mark) {
        String markTransform = mark.split("/")[0];
        return markTransform;
    }

    public String tranformRT(String mark) {
        String markTransform = mark.split("%")[0];
        double markInt = Double.parseDouble(markTransform) / 10;
        return " " + markInt;
    }

    public String tranformMC(String mark) {
        String markTransform = mark.split("/")[0];
        double markInt = Double.parseDouble(markTransform) / 10;
        return " " + markInt;
    }

}
