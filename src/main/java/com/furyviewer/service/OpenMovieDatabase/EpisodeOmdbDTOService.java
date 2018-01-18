package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.service.dto.OpenMovieDatabase.Episode;
import com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO;
import com.furyviewer.service.dto.OpenMovieDatabase.SeasonOmdbDTO;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

@Service
public class EpisodeOmdbDTOService {
    public final String apikey = "66f5a28";
    EpisodeOmdbDTORepository apiService = EpisodeOmdbDTORepository.retrofit.create(EpisodeOmdbDTORepository.class);

    public EpisodeOmdbDTO getSeason(String title, int seasonNum, int episodeNum) {
        EpisodeOmdbDTO episode = new EpisodeOmdbDTO();
        Call<EpisodeOmdbDTO> callEpisode = apiService.getEpisode(apikey, title, seasonNum, episodeNum);

        try{
            episode = callEpisode.execute().body();
            System.out.println(episode);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return episode;
    }
}
