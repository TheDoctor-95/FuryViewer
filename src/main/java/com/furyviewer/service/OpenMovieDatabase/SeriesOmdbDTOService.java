package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Series;
import com.furyviewer.domain.enumeration.SeriesEmittingEnum;
import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.service.CountryService;
import com.furyviewer.service.DateConversorService;
import com.furyviewer.service.GenreService;
import com.furyviewer.service.dto.OpenMovieDatabase.SeriesOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

@Service
public class SeriesOmdbDTOService {
    public final String apikey = "eb62550d";

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

    SeriesOmdbDTORepository apiService = SeriesOmdbDTORepository.retrofit.create(SeriesOmdbDTORepository.class);

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
     * Convierte la información de una serie de OMDB al formato de información de Furyviewer.
     * @param title String | Título de la serie.
     * @return Series | Contiene la información de una serie en el formato FuryViewer.
     */
    public Series importSeries(String title){

        //comprobamos si ya está en nuestrabase de datos.
        Optional<Series> s = seriesRepository.findByName(title);
        if(s.isPresent()){
            return s.get();
        }

        SeriesOmdbDTO seriesOmdbDTO = getSeries(title);

        Series ss = new Series();

        //Comprobamos que la API nos devuelve información.
        if (seriesOmdbDTO.getResponse().equalsIgnoreCase("true")) {
            ss.setName(seriesOmdbDTO.getTitle());
            ss.setDescription(seriesOmdbDTO.getPlot());

            if (seriesOmdbDTO.getYear().length() == 5) {
                ss.setState(SeriesEmittingEnum.EMITTING);
            } else {
                ss.setState(SeriesEmittingEnum.ENDED);
            }

            ss.setReleaseDate(dateConversorService.releseDateOMDB(seriesOmdbDTO.getReleased()));
            ss.setImgUrl(seriesOmdbDTO.getPoster());
            ss.setImdb_id(seriesOmdbDTO.getImdbID());
            ss.setAwards(seriesOmdbDTO.getAwards());
            ss.setGenres(genreService.importGenre(seriesOmdbDTO.getGenre()));
            ss.setCountry(countryService.importCountry(seriesOmdbDTO.getCountry()));

            seriesRepository.save(ss);

            seasonOmdbDTOService.importSeason(title, Integer.parseInt(seriesOmdbDTO.getTotalSeasons()));
        }

        return ss;
    }
}
