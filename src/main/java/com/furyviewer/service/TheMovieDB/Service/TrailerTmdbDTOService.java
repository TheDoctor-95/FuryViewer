package com.furyviewer.service.TheMovieDB.Service;

import com.furyviewer.domain.Movie;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.Social;
import com.furyviewer.repository.SocialRepository;
import com.furyviewer.service.TheMovieDB.Repository.TrailerTmdbDTORepository;
import com.furyviewer.service.dto.TheMovieDB.Trailer.Result;
import com.furyviewer.service.dto.TheMovieDB.Trailer.TrailerTmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * Servicio encargado de recuperar informacion de un Trailer desde TrailerTmdbDTORepository y la convierte al
 * formato FuryViewer.
 * @author IFriedkin
 * @see com.furyviewer.service.TheMovieDB.Repository.TrailerTmdbDTORepository
 */
@Service
public class TrailerTmdbDTOService {
    /**
     * Key proporcionada por la api de TheMovieDB para poder hacer peticiones.
     */
    private final String apikey = "08526181d206d48ab49b3fa0be7ad1bf";

    /**
     * Path necesario para poder construir el enlace del trailer.
     */
    private final String pathVideo = "https://www.youtube.com/embed/";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private final TrailerTmdbDTORepository apiTMDB =
        TrailerTmdbDTORepository.retrofit.create(TrailerTmdbDTORepository.class);

    @Autowired
    private MovieTmdbDTOService movieTmdbDTOService;

    @Autowired
    private SeriesTmdbDTOService seriesTmdbDTOService;

    @Autowired
    private SocialRepository socialRepository;

    /**
     * Se convierte el trailer de la movie del formato de la api TMDB al formato de FuryViewer.
     * @param movie Movie | Movie de la que se quiere encontrar el trailer.
     */
    public void importMovieTrailer(Movie movie) {
        Social social = new Social();
        int id = movieTmdbDTOService.getIdTmdbMovie(movie.getName());

        if(id != -1) {
            Call<TrailerTmdbDTO> callTrailer = apiTMDB.getMovieTrailer(id, apikey);

            try {
                Response<TrailerTmdbDTO> response = callTrailer.execute();

                if (response.isSuccessful()) {
                    TrailerTmdbDTO trailerRes = response.body();

                    if (!trailerRes.getResults().isEmpty()) {
                        int size = trailerRes.getResults().get(0).getSize();

                        List<Result> resultTrailer = trailerRes.getResults();

                        //Buscamos el trailer con mejor resolución.
                        for (Result trailer : resultTrailer) {
                            if (size <= trailer.getSize()) {
                                social.setUrl(pathVideo + trailer.getKey());
                                social.setType("Trailer");
                                social.setMovie(movie);

                                size = trailer.getSize();
                            }
                        }

                        socialRepository.save(social);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Se convierte el trailer de la series del formato de la api TMDB al formato de FuryViewer.
     * @param ss Series | Series de la que se quiere encontrar el trailer.
     */
    public void importSeriesTrailer(Series ss) {
        Social social = new Social();
        int id = seriesTmdbDTOService.getIdTmdbSeries(ss.getName());

        if(id != -1) {
            Call<TrailerTmdbDTO> callTrailer = apiTMDB.getSeriesTrailer(id, apikey);

            try {
                Response<TrailerTmdbDTO> response = callTrailer.execute();

                if (response.isSuccessful()) {
                    TrailerTmdbDTO trailerRes = response.body();

                    if (!trailerRes.getResults().isEmpty()) {
                        int size = trailerRes.getResults().get(0).getSize();

                        List<Result> resultTrailer = trailerRes.getResults();

                        //Buscamos el trailer con mejor resolución.
                        for (Result trailer : resultTrailer) {
                            if (size <= trailer.getSize()) {
                                social.setUrl(pathVideo + trailer.getKey());
                                social.setType("Trailer");
                                social.setSeries(ss);

                                size = trailer.getSize();
                            }
                        }

                        socialRepository.save(social);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
