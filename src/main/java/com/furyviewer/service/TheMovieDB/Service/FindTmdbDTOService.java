package com.furyviewer.service.TheMovieDB.Service;

import com.furyviewer.service.TheMovieDB.Repository.FindTmdbDTORepository;
import com.furyviewer.service.dto.TheMovieDB.find.FindTmdbDTO;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Servicio encargado de recuperar los id de series y movies de TMDB.
 * @author IFriedkin
 * @see com.furyviewer.service.TheMovieDB.Repository.FindTmdbDTORepository
 */
@Service
public class FindTmdbDTOService {
    /**
     * Key proporcionada por la api de TheMovieDB para poder hacer peticiones.
     */
    private final String apikey = "08526181d206d48ab49b3fa0be7ad1bf";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private final FindTmdbDTORepository apiTMDB =
        FindTmdbDTORepository.retrofit.create(FindTmdbDTORepository.class);

    /**
     * Devuelve el id de TMDB de una series a partir del id de IMDB.
     * @param imdbId String | id de IMDB.
     * @return int | id de TMDB.
     */
    public int getIdTmdbSeriesByImdbId(String imdbId) {
        int id = -1;

        getTrailer:
        for (int i = 0; i <3; i++) {
            try {
                FindTmdbDTO find;
                Call<FindTmdbDTO> callSeries = apiTMDB.getFind(imdbId, apikey);

                Response<FindTmdbDTO> response = callSeries.execute();

                if(response.isSuccessful()){
                    find = response.body();
                    System.out.println(find);
                    if (!find.getTvResults().isEmpty()) {
                        id = find.getTvResults().get(0).getId();
                    }
                }

                break getTrailer;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return id;
    }

    /**
     * Devuelve el id de TMDB de una movie a partir del id de IMDB.
     * @param imdbId String | id de IMDB.
     * @return int | id de TMDB.
     */
    public int getIdTmdbMovieByImdbId(String imdbId) {
        int id = -1;

        getTrailer:
        for (int i = 0; i <3; i++) {
            try {
                FindTmdbDTO find;

                Call<FindTmdbDTO> callMovie = apiTMDB.getFind(imdbId, apikey);

                Response<FindTmdbDTO> response = callMovie.execute();

                if (response.isSuccessful()) {
                    find = response.body();
                    System.out.println(find);
                    if (!find.getMovieResults().isEmpty()) {
                        id = find.getMovieResults().get(0).getId();
                    }
                }

                break getTrailer;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return id;
    }
}
