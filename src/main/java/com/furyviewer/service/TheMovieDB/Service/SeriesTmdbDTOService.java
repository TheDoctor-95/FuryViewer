package com.furyviewer.service.TheMovieDB.Service;

import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.service.TheMovieDB.Repository.SeriesTmdbDTORepository;
import com.furyviewer.domain.Episode;
import com.furyviewer.service.dto.TheMovieDB.Episode.Cast;
import com.furyviewer.service.dto.TheMovieDB.Episode.EpisodeCastingDTO;
import com.furyviewer.service.dto.TheMovieDB.Season.Crew;
import com.furyviewer.service.dto.TheMovieDB.Episode.EpisodeExternalIdDTO;
import com.furyviewer.service.dto.TheMovieDB.Season.SeasonTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Series.CompleteSeriesTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Series.Season;
import com.furyviewer.service.dto.TheMovieDB.Series.SimpleSeriesTmdbDTO;
import com.furyviewer.service.util.ArtistService;
import com.furyviewer.service.util.DateConversorService;
import com.furyviewer.service.util.StringApiCorrector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * Servicio encargado de recuperar informacion de una Series desde SeriesTmdbDTORepository.
 *
 * @author IFriedkin
 * @see com.furyviewer.service.TheMovieDB.Repository.SeriesTmdbDTORepository
 */
@Service
public class SeriesTmdbDTOService {
    /**
     * Key proporcionada por la api de TheMovieDB para poder hacer peticiones.
     */
    private final String apikey = "08526181d206d48ab49b3fa0be7ad1bf";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private final SeriesTmdbDTORepository apiTMDB =
        SeriesTmdbDTORepository.retrofit.create(SeriesTmdbDTORepository.class);

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private StringApiCorrector stringApiCorrector;

    @Autowired
    private EpisodeRepository episodeRepository;

    /**
     * Devuelve el id de la series a partir del titulo.
     *
     * @param seriesName String | Series a buscar.
     * @return int | id de la serie de la api TMDB.
     */
    public int getIdTmdbSeries(String seriesName) {
        int id = -1;
        SimpleSeriesTmdbDTO series;
        Call<SimpleSeriesTmdbDTO> callSeries = apiTMDB.getSimpleSeries(apikey, seriesName);

        try {
            Response<SimpleSeriesTmdbDTO> response = callSeries.execute();

            if (response.isSuccessful()) {
                series = response.body();
                System.out.println(series);
                id = series.getResults().get(0).getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * Devuelve el nombre de la company a partir del titulo de la series.
     *
     * @param seriesName String | Series a buscar.
     * @return String | Nombre de la company.
     */
    public String getCompanyName(String seriesName) {
        CompleteSeriesTmdbDTO series;
        int id = getIdTmdbSeries(seriesName);
        String companyName = null;

        if (id != -1) {
            try {
                Call<CompleteSeriesTmdbDTO> callSeries = apiTMDB.getCompleteSeries(id, apikey);

                Response<CompleteSeriesTmdbDTO> response = callSeries.execute();

                if (response.isSuccessful()) {
                    series = response.body();
                    companyName = series.getProductionCompanies().get(0).getName();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return companyName;
    }

    /**
     * Devuelve el numero total de episodes de una season.
     * @param seriesName String | titulo de la series a buscar.
     * @param numSeason int |Numero de la season a buscar.
     * @return int | Numero total de episodios.
     */
    public int getNumEpisodes(String seriesName, int numSeason) {
        int numEpisodes = -1;
        int id = getIdTmdbSeries(seriesName);

        if (id != -1) {
            try {
                Call<CompleteSeriesTmdbDTO> callSeries = apiTMDB.getCompleteSeries(id, apikey);

                Response<CompleteSeriesTmdbDTO> response = callSeries.execute();

                if (response.isSuccessful()) {
                    CompleteSeriesTmdbDTO series = response.body();

                    for (Season season : series.getSeasons()) {

                        if (season.getSeasonNumber() == numSeason) {
                            return season.getEpisodeCount();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return numEpisodes;
    }

    /**
     * Devuelve la duración estandar de un episode.
     * @param seriesId int | id de la api de TMDB.
     * @return double | Duracion estandar de un episode.
     */
    public double getDurationEpisode(int seriesId) {
        double duration = -1;

        CompleteSeriesTmdbDTO series;

        getDuration:
        for (int i = 0; i < 3; i++) {
            try {
                Call<CompleteSeriesTmdbDTO> callSeries = apiTMDB.getCompleteSeries(seriesId, apikey);

                Response<CompleteSeriesTmdbDTO> response = callSeries.execute();

                if (response.isSuccessful()) {
                    series = response.body();

                    if (series.getEpisodeRunTime() != null) {
                        duration = series.getEpisodeRunTime().get(0);
                    }
                }

                //Salimos del bucle
                break getDuration;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(5000L);
                    System.out.println("Durmiendo el thread 5 segundos desde SeriesTmdbDTOService#getDurationEpisode");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return duration;
    }

    /**
     * Devuelve el id de IMDB de un episode a partir del id de la serie, el numero de la season y el numero del episode.
     * @param seriesId int | id de la api de TMDB.
     * @param seasonNum int | Numero de la season a buscar.
     * @param episodeNum int | Numero del episode a buscar.
     * @return String | id de IMDB.
     */
    public String getImdbId(int seriesId, int seasonNum, int episodeNum) {
        String id = null;
        EpisodeExternalIdDTO external;

        getImdb:
        for (int i = 0; i < 3; i++) {
            try {
                Call<EpisodeExternalIdDTO> callExternal =
                    apiTMDB.getExternalId(seriesId, seasonNum, episodeNum, apikey);

                Response<EpisodeExternalIdDTO> response = callExternal.execute();

                if (response.isSuccessful()) {
                    external = response.body();

                    if (external.getImdbId() != null) {
                        id = external.getImdbId();
                    }
                }

                //Salimos del bucle
                break getImdb;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    System.out.println("Durmiendo el thread 5 segundos desde SeriesTmdbDTOService#getImdbId");
                    Thread.sleep(5000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return id;
    }

    public void importActors (int idTmdb, Episode ep) {
        getActors:
        for (int i = 0; i < 3; i++) {
            try {
                Call<EpisodeCastingDTO> callCasting =
                    apiTMDB.getCasting(idTmdb, ep.getSeason().getNumber(), ep.getNumber(), apikey);

                Response<EpisodeCastingDTO> response = callCasting.execute();

                if(response.isSuccessful()) {
                    List<Cast> casting = response.body().getCast();

                    ep.setActors(artistService.importActorsTMdb(casting));

                    episodeRepository.save(ep);
                }

                break getActors;

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    System.out.println("Durmiendo el thread 5 segundos desde SeriesTmdbDTOService#importActors");
                    Thread.sleep(5000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Convierte la informacion de un episode de TMDB al formato de Furyviewer.
     * @param seriesName String | Titulo de la series a buscar.
     * @param episodeNum int | Numero del episode a buscar.
     * @param season Season | Season a la que pertenece el episode.
     * @throws IOException En caso de que no se pueda hacer la peticion a la api se lanza la execpcion.
     */
    public void importEpisode(String seriesName, int episodeNum,
                              com.furyviewer.domain.Season season) throws IOException {
        int seriesId = getIdTmdbSeries(seriesName);
        SeasonTmdbDTO se;

        Call<SeasonTmdbDTO> callSeason = apiTMDB.getSeason(seriesId, season.getNumber(), apikey);

        Response<SeasonTmdbDTO> response = callSeason.execute();

        if (response.isSuccessful()) {
            se = response.body();
            Episode ep = new Episode();


            ep.setNumber(episodeNum);

            episodeNum = episodeNum - 1;

            if (se.getEpisodes().get(episodeNum).getName() != null) {
                ep.setName(se.getEpisodes().get(episodeNum).getName());
            }

            double duration = getDurationEpisode(seriesId);
            if (duration != -1) {
                ep.setDuration(duration);
            }

            if (se.getEpisodes().get(episodeNum).getAirDate() != null) {
                ep.setReleaseDate(dateConversorService.releaseDateOMDBSeason(
                    se.getEpisodes().get(episodeNum).getAirDate()));
            }

            String imdbId = getImdbId(seriesId, season.getNumber(), episodeNum);
            if (imdbId != null) {
                ep.setImdbId(imdbId);
            }

            ep.setSeason(season);

            if (se.getEpisodes().get(episodeNum).getOverview() != null) {
                ep.setDescription(stringApiCorrector.eraserEvilBytes(se.getEpisodes().get(episodeNum).getOverview()));
            }

            if (se.getEpisodes().get(episodeNum).getCrew() != null) {
                for (Crew crew : se.getEpisodes().get(episodeNum).getCrew()) {
                    if (crew.getJob().equalsIgnoreCase("Director")) {
                        ep.setDirector(artistService.importDirector(crew.getName()));
                    } else if (crew.getJob().equalsIgnoreCase("Writer")) {
                        ep.setScriptwriter(artistService.importScripwriter(crew.getName()));
                    }
                }
            }

            ep = episodeRepository.save(ep);

            importActors(seriesId, ep);

            System.out.println(se);
        } else {
            throw new IOException(response.message());
        }
    }
}
