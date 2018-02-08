package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.domain.Season;
import com.furyviewer.domain.Series;
import com.furyviewer.repository.SeasonRepository;
import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.service.OpenMovieDatabase.Repository.SeasonOmdbDTORepository;
import com.furyviewer.service.util.DateConversorService;
import com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

@Service
public class SeasonOmdbDTOService {
    private final String apikey = "eb62550d";

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeOmdbDTOService episodeOmdbDTOService;

    @Autowired
    private DateConversorService dateConversorService;

    private final SeasonOmdbDTORepository apiService = SeasonOmdbDTORepository.retrofit.create(SeasonOmdbDTORepository.class);

    /**
     * Devuelve la información de una season en el formato proporcionado por OpenMovieDataBase.
     * @param title String | Título de la serie a buscar.
     * @param seasonNum int | Número de la season a buscar.
     * @return SeasonOmdbDTO | Información con el formato proporcionado por la API.
     * @throws IOException
     */
    public SeasonOmdbDTO getSeason(String title, int seasonNum) throws IOException {
        SeasonOmdbDTO season;
        Call<SeasonOmdbDTO> callSeason = apiService.getSeason(apikey, title, seasonNum);

        season = callSeason.execute().body();
        System.out.println(season);

        return season;
    }

    /**
     * Convierte la información de una season de OMDB al formato de información de Furyviewer.
     * @param title String | Título de la serie.
     * @param totalSeasons int | Número total de seasons de la serie.
     */
    public void importSeason(String title, int totalSeasons) {
        Series ss = seriesRepository.findByName(title).get();

        for (int i = 1; i <= totalSeasons; i++) {

            //Ponemos mote al bucle y lo utilizamos para hacer la petición hasta tres veces para asegurarnos de que
            // podemos realizar la petición a la api.
            getSeason:
            for (int j = 0; j < 3; j++) {
                try {
                    Season se = new Season();

                    SeasonOmdbDTO seasonOmdbDTO = getSeason(title, i);

                    //Comprobamos que la API nos devuelve información.
                    if (seasonOmdbDTO.getResponse().equalsIgnoreCase("true")) {
                        se.setNumber(i);
                        se.setReleaseDate(dateConversorService.releaseDateOMDBSeason(seasonOmdbDTO.getEpisodes().get(0).getReleased()));
                        se.setSeries(ss);

                        seasonRepository.save(se);

                        episodeOmdbDTOService.importEpisode(title, seasonOmdbDTO.getEpisodes().size(), se);
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
