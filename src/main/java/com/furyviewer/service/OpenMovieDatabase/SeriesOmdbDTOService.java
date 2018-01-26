package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Country;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.enumeration.SeriesEmittingEnum;
import com.furyviewer.repository.CountryRepository;
import com.furyviewer.repository.SeriesRepository;
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
    private CountryRepository countryRepository;

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private SeasonOmdbDTOService seasonOmdbDTOService;

    @Autowired
    private GenreService genreService;

    SeriesOmdbDTORepository apiService = SeriesOmdbDTORepository.retrofit.create(SeriesOmdbDTORepository.class);

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

    public Series importSeries(String title){

        Optional<Series> s = seriesRepository.findByName(title);
        if(s.isPresent()){
            return s.get();
        }

        SeriesOmdbDTO seriesOmdbDTO = getSeries(title);

        Series ss = new Series();

        ss.setName(seriesOmdbDTO.getTitle());
        ss.setDescription(seriesOmdbDTO.getPlot());

        String[] years = seriesOmdbDTO.getYear().split("-");

        if (years.length==1){
            ss.setState(SeriesEmittingEnum.EMITTING);
        }
        else {
            ss.setState(SeriesEmittingEnum.ENDED);
        }

        ss.setReleaseDate(dateConversorService.releseDateOMDB(seriesOmdbDTO.getReleased()));
        ss.setImgUrl(seriesOmdbDTO.getPoster());
        ss.setImdb_id(seriesOmdbDTO.getImdbID());
        ss.setAwards(seriesOmdbDTO.getAwards());
        ss.setGenres(genreService.importGenre(seriesOmdbDTO.getGenre()));

        Optional<Country> c = countryRepository.findByName(seriesOmdbDTO.getCountry());
        if(c.isPresent()){
            ss.setCountry(c.get());
        }
        else{
            Country country = new Country();
            country.setName(seriesOmdbDTO.getCountry());
            countryRepository.save(country);
            ss.setCountry(country);
        }

        seriesRepository.save(ss);

        seasonOmdbDTOService.importSeason(title, Integer.parseInt(seriesOmdbDTO.getTotalSeasons()));

        return ss;
    }
}
