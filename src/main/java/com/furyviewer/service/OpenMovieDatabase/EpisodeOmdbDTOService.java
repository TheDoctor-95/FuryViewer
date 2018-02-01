package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Season;
import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.domain.Episode;
import com.furyviewer.service.ArtistService;
import com.furyviewer.service.util.DateConversorService;
import com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;

import java.io.IOException;

@Service
public class EpisodeOmdbDTOService {
    private final String apikey = "eb62550d";

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private ArtistService artistService;

    private final EpisodeOmdbDTORepository apiService = EpisodeOmdbDTORepository.retrofit.create(EpisodeOmdbDTORepository.class);

    /**
     * Devuelve la información de un episode en el formato proporcionado por OpenMovieDataBase.
     * @param title String | Título de la serie a buscar.
     * @param seasonNum int | Número de la temporada a buscar.
     * @param episodeNum int | Número del episode a buscar.
     * @return EpisodeOmdbDTO | Información con el formato proporcionado por la API.
     * @throws IOException
     */
    public EpisodeOmdbDTO getEpisode(String title, int seasonNum, int episodeNum) throws IOException {
        EpisodeOmdbDTO episode;
        Call<EpisodeOmdbDTO> callEpisode = apiService.getEpisode(apikey, title, seasonNum, episodeNum);

        episode = callEpisode.execute().body();
        System.out.println(episode);

        return episode;
    }

    /**
     * Convierte la información de un episode de OMDB al formato de información de Furyviewer.
     * @param title String | Título de la serie.
     * @param totalEpisodes int | Número total de episodes de la season.
     * @param se Season | Season a la que añadir los episodes.
     */
    public void importEpisode(String title, int totalEpisodes, Season se) {
        for (int i = 1; i <= totalEpisodes; i++) {

            //Ponemos mote al bucle y lo utilizamos para hacer la petición hasta tres veces para asegurarnos de que
            // podemos realizar la petición a la api.
            getEpisode:
            for (int j = 0; j < 3; j++) {
                try {
                    Episode ep = new Episode();
                    EpisodeOmdbDTO episodeOmdbDTO = getEpisode(title, se.getNumber(), i);

                    //Comprobamos que la API nos devuelve información.
                    if (episodeOmdbDTO.getResponse().equalsIgnoreCase("true")) {
                        ep.setNumber(i);
                        ep.setName(episodeOmdbDTO.getTitle());

                        String[] time = episodeOmdbDTO.getRuntime().split(" ");
                        ep.setDuration(Double.parseDouble(time[0]));

                        ep.setReleaseDate(dateConversorService.releseDateOMDB(episodeOmdbDTO.getReleased()));

                        ep.setImdbId(episodeOmdbDTO.getImdbID());
                        ep.setSeason(se);

                        if(episodeOmdbDTO.getPlot() == null) {
                            ep.setDescription(episodeOmdbDTO.getPlot());
                        }

                        ep.setActors(artistService.importActors(episodeOmdbDTO.getActors()));
                        ep.setDirector(artistService.importDirector(episodeOmdbDTO.getDirector()));
                        ep.setScriptwriter(artistService.importScripwriter(episodeOmdbDTO.getWriter()));

                        episodeRepository.save(ep);
                    }

                    //Salimos del bucle
                    break getEpisode;

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
