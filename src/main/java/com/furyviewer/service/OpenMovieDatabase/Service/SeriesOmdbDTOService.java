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

@Service
public class SeriesOmdbDTOService {
    private final String apikey = "eb62550d";

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
    private NAEraserService naEraserService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SeriesTmdbDTOService seriesTmdbDTOService;

    @Autowired
    private TrailerTmdbDTOService trailerTmdbDTOService;

    private static SeriesOmdbDTORepository apiService = SeriesOmdbDTORepository.retrofit.create(SeriesOmdbDTORepository.class);

    /**
     * Devuelve la información de una serie en el formato proporcionado por OpenMovieDataBase.
     * @param title String | Título de la serie a buscar.
     * @return SeriesOmdbDTO | Información con el formato proporcionado por la API.
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
     * Convierte la información de una serie de OMDB al formato de información de FuryViewer.
     * @param title String | Título de la serie.
     * @return Series | Contiene la información de una serie en el formato FuryViewer.
     */
    @Transactional
    public Series importSeries(String title){
        //Comprobamos si ya está en nuestra base de datos.
        Optional<Series> s = seriesRepository.findByName(title);
        if(s.isPresent()){
            return s.get();
        }

        SeriesOmdbDTO seriesOmdbDTO = getSeries(title);

        Series ss = new Series();

        //Comprobamos que la API nos devuelve información.
        if (seriesOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            ss.setName(seriesOmdbDTO.getTitle());
            ss.setDescription(naEraserService.eraserNA(seriesOmdbDTO.getPlot()));

            if (seriesOmdbDTO.getYear().length() == 5) {
                ss.setState(SeriesEmittingEnum.EMITTING);
            } else {
                ss.setState(SeriesEmittingEnum.ENDED);
            }

            ss.setReleaseDate(dateConversorService.releseDateOMDB(seriesOmdbDTO.getReleased()));

            ss.setImgUrl(naEraserService.eraserNA(seriesOmdbDTO.getPoster()));
            ss.setImdb_id(naEraserService.eraserNA(seriesOmdbDTO.getImdbID()));
            ss.setAwards(naEraserService.eraserNA(seriesOmdbDTO.getAwards()));

            ss.setGenres(genreService.importGenre(seriesOmdbDTO.getGenre()));
            ss.setCountry(countryService.importCountry(seriesOmdbDTO.getCountry()));
            ss.setCompany(companyService.importCompany(seriesTmdbDTOService.getCompanyName(title)));

            seriesRepository.save(ss);

            trailerTmdbDTOService.importSeriesTrailer(ss);

            seasonOmdbDTOService.importSeason(title, Integer.parseInt(seriesOmdbDTO.getTotalSeasons()));
        }

        return ss;
    }
}
