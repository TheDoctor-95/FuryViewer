package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

@Service
public class SeasonOmdbService {
    public final String apikey = "66f5a28";
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
}
