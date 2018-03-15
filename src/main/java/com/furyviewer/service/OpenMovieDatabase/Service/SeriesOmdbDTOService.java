package com.furyviewer.service.OpenMovieDatabase.Service;

import com.furyviewer.domain.Series;
import com.furyviewer.domain.enumeration.SeriesEmittingEnum;
import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.service.OpenMovieDatabase.Repository.SeriesOmdbDTORepository;
import com.furyviewer.service.TheMovieDB.Service.SeriesTmdbDTOService;
import com.furyviewer.service.TheMovieDB.Service.TrailerTmdbDTOService;
import com.furyviewer.service.util.*;
import com.furyviewer.service.dto.OpenMovieDatabase.SeriesOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

/**
 * Servicio encargado de recuperar informacion de una Series desde SeriesOmdbDTORepository y la convierte al
 * formato FuryViewer.
 * @author IFriedkin
 * @see com.furyviewer.service.OpenMovieDatabase.Repository.SeriesOmdbDTORepository
 */
@Service
public class SeriesOmdbDTOService {
    /**
     * Key proporcionada por la api de OpenMovieDataBase para poder hacer peticiones.
     */
    private final String apikey = "eb62550d";

    /**
     * Se establece conexion para poder hacer peticiones a la api.
     */
    private static SeriesOmdbDTORepository apiService =
        SeriesOmdbDTORepository.retrofit.create(SeriesOmdbDTORepository.class);

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private SeasonOmdbDTOService seasonOmdbDTOService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private StringApiCorrectorService stringApiCorrectorService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SeriesTmdbDTOService seriesTmdbDTOService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private TrailerTmdbDTOService trailerTmdbDTOService;

    /**
     * Devuelve la informacion a partir del titulo de una serie en el formato proporcionado por OpenMovieDataBase.
     * @param title String | Titulo de la serie a buscar.
     * @return SeriesOmdbDTO | Informacion con el formato proporcionado por la API.
     */
    public SeriesOmdbDTO getSeries(String title) {
        SeriesOmdbDTO series = new SeriesOmdbDTO();
        Call<SeriesOmdbDTO> callSeries = apiService.getSeries(apikey, title);

        try{
            series = callSeries.execute().body();
            System.out.println(series);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return series;
    }

    /**
     * Devuelve la informacion a partir del id de IMDB de una serie en el formato proporcionado por OpenMovieDataBase.
     * @param ImdbId String | id de IMDB.
     * @return SeriesOmdbDTO | Informacion con el formato proporcionado por la API.
     */
    public SeriesOmdbDTO getSeriesByImdbId(String ImdbId) {
        SeriesOmdbDTO series = new SeriesOmdbDTO();
        Call<SeriesOmdbDTO> callSeries = apiService.getSeriesByImdbId(apikey, ImdbId);

        try{
            series = callSeries.execute().body();
            System.out.println(series);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return series;
    }

    /**
     * Devuelve una Series a partir del titulo existente en la base de datos o en caso de no existir hace una peticion a
     * la api.
     * @param title String | Titulo de la series.
     * @return Series | Contiene la informacion de una serie en el formato FuryViewer.
     */
    @Transactional
    public Series importSeriesByTitle(String title){
        //Comprobamos si ya está en nuestra base de datos.
        SeriesOmdbDTO seriesOmdbDTO = getSeries(title);

        Optional<Series> s = seriesRepository.findByImdb_id(seriesOmdbDTO.getImdbID());

        if(s.isPresent()){
            return s.get();
        }

        Series ss = new Series();

        //Comprobamos que la API nos devuelve información.
        if (seriesOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            ss = importSeries(seriesOmdbDTO);
        } else {
            System.out.println("==================\nBúsqueda sin resultados\n==================");
        }

        return ss;
    }

    /**
     * Devuelve una Series a partir del ide de IMDB existente en la base de datos o en caso de no existir hace una
     * peticion a la api.
     * @param imdbId String | Titulo de la series.
     * @return Series | Contiene la informacion de una serie en el formato FuryViewer.
     */
    @Transactional
    public Series importSeriesByImdbId(String imdbId){
        //Comprobamos si ya está en nuestra base de datos.
        SeriesOmdbDTO seriesOmdbDTO = getSeriesByImdbId(imdbId);

        Optional<Series> s = seriesRepository.findByImdb_id(seriesOmdbDTO.getImdbID());

        if(s.isPresent()){
            return s.get();
        }

        Series ss = new Series();

        //Comprobamos que la API nos devuelve información.
        if (seriesOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            ss = importSeries(seriesOmdbDTO);
        } else {
            System.out.println("==================\nBúsqueda sin resultados\n==================");
        }

        return ss;
    }

    /**
     * Convierte la informacion de una series de OMDB al formato de FuryViewer.
     * @param seriesOmdbDTO SeriesOmdbDTO | Informacion de la Series propocionada por la api.
     * @return Series | Contiene la informacion de una series en el formato FuryViewer.
     */
    public Series importSeries (SeriesOmdbDTO seriesOmdbDTO) {
        Series ss = new Series();

        ss.setName(seriesOmdbDTO.getTitle());
        ss.setDescription(stringApiCorrectorService.eraserNA(seriesOmdbDTO.getPlot()));

        if (seriesOmdbDTO.getYear().length() == 5) {
            ss.setState(SeriesEmittingEnum.emiting);
        } else {
            ss.setState(SeriesEmittingEnum.ended);
        }

        ss.setReleaseDate(dateConversorService.releseDateOMDB(seriesOmdbDTO.getReleased()));

        ss.setImgUrl(stringApiCorrectorService.eraserNA(seriesOmdbDTO.getPoster()));
        ss.setImdb_id(stringApiCorrectorService.eraserNA(seriesOmdbDTO.getImdbID()));
        ss.setAwards(stringApiCorrectorService.eraserNA(seriesOmdbDTO.getAwards()));

        ss.setGenres(genreService.importGenre(seriesOmdbDTO.getGenre()));
        ss.setCountry(countryService.importCountry(seriesOmdbDTO.getCountry()));

        String companyName = seriesTmdbDTOService.getCompanyName(seriesOmdbDTO.getTitle());
        if (companyName != null) {
            ss.setCompany(companyService.importCompany(companyName));
        }

        ss = seriesRepository.save(ss);

        trailerTmdbDTOService.importSeriesTrailer(ss);

        marksService.markTransformationSeries(seriesOmdbDTO.getRatings(), ss);

        seasonOmdbDTOService.importSeason(seriesOmdbDTO.getTitle(), Integer.parseInt(seriesOmdbDTO.getTotalSeasons()));

        return ss;
    }
}
