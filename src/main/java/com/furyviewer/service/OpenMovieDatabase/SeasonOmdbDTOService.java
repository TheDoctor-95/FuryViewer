package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Season;
import com.furyviewer.domain.Series;
import com.furyviewer.repository.SeasonRepository;
import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.service.DateConversorService;
import com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.Optional;

@Service
public class SeasonOmdbDTOService {
    public final String apikey = "eb62550d";

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeOmdbDTOService episodeOmdbDTOService;

    @Autowired
    private DateConversorService dateConversorService;

    SeasonOmdbDTORepository apiService = SeasonOmdbDTORepository.retrofit.create(SeasonOmdbDTORepository.class);

    public SeasonOmdbDTO getSeason(String title, int seasonNum) {
        SeasonOmdbDTO season = new SeasonOmdbDTO();
        Call<SeasonOmdbDTO> callSeason = apiService.getSeason(apikey, title, seasonNum);

        try{
            season = callSeason.execute().body();
            System.out.println(season);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return season;
    }

    public void importSeason (String title, int totalSeasons) {


        Series ss = seriesRepository.findByName(title).get();

        for(int i= 1; i<=totalSeasons; i++) {
            Season se = new Season();

            se.setNumber(i);

            SeasonOmdbDTO seasonOmdbDTO = getSeason(title, i);
            se.setReleaseDate(dateConversorService.releaseDateOMDBSeason(seasonOmdbDTO.getEpisodes().get(0).getReleased()));

            se.setSeries(ss);

            seasonRepository.save(se);

            episodeOmdbDTOService.importEpisode(title,seasonOmdbDTO.getEpisodes().size(),se);
        }
    }
}
