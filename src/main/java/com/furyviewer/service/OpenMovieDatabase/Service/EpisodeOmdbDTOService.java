package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.domain.Season;
import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.domain.Episode;
import com.furyviewer.service.OpenMovieDatabase.Repository.EpisodeOmdbDTORepository;
import com.furyviewer.service.TheMovieDB.Service.SeriesTmdbDTOService;
import com.furyviewer.service.util.ArtistService;
import com.furyviewer.service.util.DateConversorService;
import com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO;
import com.furyviewer.service.util.StringApiCorrectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

/**
 * Servicio encargado de recuperar informacion de un Episode desde EpisodeOmdbDTORepository y la convierte al
 * formato FuryViewer.
 * @author IFriedkin
 * @see com.furyviewer.service.OpenMovieDatabase.Repository.EpisodeOmdbDTORepository
 */
@Service
public class EpisodeOmdbDTOService {
    /**
     * Key proporcionada por la api de OpenMovieDataBase para poder hacer peticiones.
     */
    private final String apikey = "eb62550d";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private final EpisodeOmdbDTORepository apiService =
        EpisodeOmdbDTORepository.retrofit.create(EpisodeOmdbDTORepository.class);

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private StringApiCorrectorService stringApiCorrectorService;

    @Autowired
    private SeriesTmdbDTOService seriesTmdbDTOService;

    /**
     * Devuelve la informacion de un episode en el formato proporcionado por OpenMovieDataBase.
     * @param title String | Titulo de la Series a buscar.
     * @param seasonNum int | Numero de la Season a buscar.
     * @param episodeNum int | Numero del Episode a buscar.
     * @return EpisodeOmdbDTO | Informacion con el formato proporcionado por la API.
     * @throws IOException En caso de que no se pueda hacer la peticion a la api se lanza la excepcion.
     */
    public EpisodeOmdbDTO getEpisode(String title, int seasonNum, int episodeNum) throws IOException {
        EpisodeOmdbDTO episode;
        Call<EpisodeOmdbDTO> callEpisode = apiService.getEpisode(apikey, title, seasonNum, episodeNum);

        episode = callEpisode.execute().body();
        System.out.println(episode);

        return episode;
    }

    /**
     * Convierte la informacion de un episode de OMDB al formato de Furyviewer.
     * @param title String | Titulo de la serie.
     * @param totalEpisodes int | Numero total de episodes de la season.
     * @param se Season | Season a la que agregar los episodes.
     */
    public void importEpisode(String title, int totalEpisodes, Season se) {
        for (int i = 1; i <= totalEpisodes; i++) {

            //Ponemos mote al bucle y lo utilizamos para hacer la petición hasta tres veces para asegurarnos de que
            // podemos realizar la petición a la api.
            getEpisode:
            for (int j = 0; j < 3; j++) {
                try {
                    Episode ep = new Episode();

                    //ep.setName("Episode " + i);
                    EpisodeOmdbDTO episodeOmdbDTO = getEpisode(title, se.getNumber(), i);

                    //Comprobamos que la API nos devuelve información.
                    if (episodeOmdbDTO.getResponse().equalsIgnoreCase("true")) {
                        ep.setNumber(i);
                        ep.setName(episodeOmdbDTO.getTitle());

                        ep.setDuration(Double.parseDouble(episodeOmdbDTO.getRuntime().split(" ")[0]));

                        ep.setReleaseDate(dateConversorService.releseDateOMDB(episodeOmdbDTO.getReleased()));

                        ep.setImdbId(stringApiCorrectorService.eraserNA(episodeOmdbDTO.getImdbID()));
                        ep.setSeason(se);

                        ep.setDescription(stringApiCorrectorService.eraserNA(episodeOmdbDTO.getPlot()));

                        ep.setActors(artistService.importActors(episodeOmdbDTO.getActors()));
                        ep.setDirector(artistService.importDirector(episodeOmdbDTO.getDirector()));
                        ep.setScriptwriter(artistService.importScripwriter(episodeOmdbDTO.getWriter()));

                        ep = episodeRepository.save(ep);

                        System.out.println("==================\nImportado..." + title + " " + se.getNumber() + "x" +
                            i + "\n==================");
                    }
                    else {
                        seriesTmdbDTOService.importEpisode(title, i, se);
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
