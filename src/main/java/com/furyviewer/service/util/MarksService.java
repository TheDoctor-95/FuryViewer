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

/**
 * Servicio que se encarga de guardar en la base de datos las notas de paginas externas tanto de Movie como de Series
 * proporcionadas por la api OpenMovieDatabase.
 * @author TheDoctor-95
 */
@Service
public class MarksService {
    @Autowired
    SocialRepository socialRepository;

    /**
     * Convierte la informacion de los votos de paginas externas proporcionados por la api al formato FuryViewer.
     * @param ratings List | Lista con as diferentes votaciones proporcionadas por la api.
     * @param ss Series | Series de la cual se quiere guardar las votaciones.
     */
    public void markTransformationSeries(List<Rating> ratings, Series ss) {
        for (Rating r : ratings) {
            Optional<Social> seriesOptional = socialRepository.findBySeriesAndType(ss, r.getSource());

            if (!seriesOptional.isPresent()) {
                Social s = new Social();

                if(r.getSource().equalsIgnoreCase("Internet Movie Database")) {
                    s.setType("IMDB");
                } else {
                    s.setType(r.getSource());
                }

                s.setSeries(ss);
                s.setUrl(markTranformation(r.getSource(), r.getValue()));

                socialRepository.save(s);
            }
        }
    }

    /**
     * Devuelve la puntuacion dependiendo de la pagina externa.
     * @param source String | Nombre de la pagina externa a la que pertenece la votacion.
     * @param value String | Votacion en el formato de la pagina externa.
     * @return String | Votacion convertida a una media sobre 10.
     */
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

    /**
     * Convierte la informacion de los votos de paginas externas proporcionados por la api al formato FuryViewer.
     * @param ratings List | Lista con as diferentes votaciones proporcionadas por la api.
     * @param m Movie | Movie de la cual se quiere guardar las votaciones.
     */
    public void markTransformationMovie(List<Rating> ratings, Movie m) {
        for (Rating r : ratings) {
            Optional<Social> movieOptional = socialRepository.findByMovieAndType(m, r.getSource());

            if (!movieOptional.isPresent()) {
                Social s = new Social();

                if(r.getSource().equalsIgnoreCase("Internet Movie Database")) {
                    s.setType("IMDB");
                } else {
                    s.setType(r.getSource());
                }

                s.setMovie(m);
                s.setUrl(markTranformation(r.getSource(), r.getValue()));

                socialRepository.save(s);
            }
        }
    }

    /**
     * Devuelve la votacion sobre 10 de IMDB.
     * @param mark String | Votacion en el formato INMDB.
     * @return String | Votacion sobre 10.
     */
    public String tranformIMDB(String mark) {
        String markTransform = mark.split("/")[0];
        return markTransform;
    }

    /**
     * Devuelve la votacion sobre 10 de Rotten Tomatoes.
     * @param mark String | Votacion en el formato Rotten Tomatoes.
     * @return String | Votacion sobre 10.
     */
    public String tranformRT(String mark) {
        String markTransform = mark.split("%")[0];
        double markInt = Double.parseDouble(markTransform) / 10;
        return " " + markInt;
    }

    /**
     * Devuelve la votacion sobre 10 de MetaCritic.
     * @param mark String | Votacion en el formato Metacritic.
     * @return String | Votacion sobre 10.
     */
    public String tranformMC(String mark) {
        String markTransform = mark.split("/")[0];
        double markInt = Double.parseDouble(markTransform) / 10;
        return " " + markInt;
    }

    public Double totalFavHate(Long fav, Long hate) {
        Integer aux = 0;
        if (fav == null) fav = aux.longValue();
        if (hate == null) hate = aux.longValue();

        Double auxFav = fav.doubleValue();
        Double auxHate = hate.doubleValue();

        return auxFav / (auxFav + auxHate) * 100;
    }
}
