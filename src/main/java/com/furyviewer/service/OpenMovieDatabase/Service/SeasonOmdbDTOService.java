package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.domain.Season;
import com.furyviewer.domain.Series;
import com.furyviewer.repository.SeasonRepository;
import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.service.OpenMovieDatabase.Repository.SeasonOmdbDTORepository;
import com.furyviewer.service.TheMovieDB.Service.SeriesTmdbDTOService;
import com.furyviewer.service.util.DateConversorService;
import com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Servicio encargado de recuperar informacion de una Season desde SeasonOmdbDTORepository y la convierte al
 * formato FuryViewer.
 * @author IFriedkin
 * @see com.furyviewer.service.OpenMovieDatabase.Repository.SeasonOmdbDTORepository
 */
@Service
public class SeasonOmdbDTOService {
    /**
     * Key proporcionada por la api de OpenMovieDataBase para poder hacer peticiones.
     */
    private final String apikey = "eb62550d";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private final SeasonOmdbDTORepository apiService =
        SeasonOmdbDTORepository.retrofit.create(SeasonOmdbDTORepository.class);

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeOmdbDTOService episodeOmdbDTOService;

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private SeriesTmdbDTOService seriesTmdbDTOService;

    /**
     * Devuelve la informacion de una season en el formato proporcionado por OpenMovieDataBase.
     * @param title String | Titulo de la serie a buscar.
     * @param seasonNum int | Numero de la season a buscar.
     * @return SeasonOmdbDTO | Informacion con el formato proporcionado por la API.
     * @throws IOException En caso de que no se pueda hacer la peticion a la api se lanza la excepcion.
     */
    public SeasonOmdbDTO getSeason(String title, int seasonNum) throws IOException {
        SeasonOmdbDTO season;
        Call<SeasonOmdbDTO> callSeason = apiService.getSeason(apikey, title, seasonNum);

        season = callSeason.execute().body();
        System.out.println(season);

        return season;
    }

    public SeasonOmdbDTO getSeasonByImdbId(String imdbId, int seasonNum) throws IOException {
        SeasonOmdbDTO season;
        Call<SeasonOmdbDTO> callSeason = apiService.getSeasonByImdbId(apikey, imdbId, seasonNum);

        Response<SeasonOmdbDTO> response = callSeason.execute();

        if (response.isSuccessful()) {
            season = response.body();
        } else {
            throw new IOException(response.message());
        }

        return season;
    }

    /**
     * Convierte la informacion de una season de OMDB al formato de Furyviewer.
     * @param title String | Titulo de la serie.
     * @param totalSeasons int | Numero total de seasons de la serie.
     */
    public void importSeason(String title, int totalSeasons, String imdbId) {
        Series ss = seriesRepository.findByImdb_id(imdbId).get();

        for (int i = 1; i <= totalSeasons; i++) {

            //Ponemos mote al bucle y lo utilizamos para hacer la petición hasta tres veces para asegurarnos de que
            // podemos realizar la petición a la api.
            getSeason:
            for (int j = 0; j < 3; j++) {
                try {
                    Season se = new Season();

                    SeasonOmdbDTO seasonOmdbDTO = getSeasonByImdbId(imdbId, i);

                    //Comprobamos que la API nos devuelve información.
                    if (seasonOmdbDTO.getResponse().equalsIgnoreCase("true")) {
                        se.setNumber(i);
                        se.setReleaseDate(dateConversorService.
                            releaseDateOMDBSeason(seasonOmdbDTO.getEpisodes().get(0).getReleased()));
                        se.setSeries(ss);

                        seasonRepository.save(se);

                        int numEpisodes = Integer.parseInt(seasonOmdbDTO.getEpisodes().get(
                            seasonOmdbDTO.getEpisodes().size() - 1).getEpisode());
                        int numEpisodeAux = seriesTmdbDTOService.getNumEpisodes(title, i);
                        if (numEpisodes < numEpisodeAux)
                        {
                            numEpisodes = numEpisodeAux;
                        }

                        episodeOmdbDTOService.
                            importEpisode(title, numEpisodes, se, imdbId);
                    }

                    //Salimos del bucle
                    break getSeason;

                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        }
    }
}
