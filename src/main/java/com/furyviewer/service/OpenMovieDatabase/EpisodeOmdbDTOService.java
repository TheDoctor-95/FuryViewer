package com.furyviewer.service.OpenMovieDatabase;

import com.furyviewer.domain.Season;
import com.furyviewer.domain.Series;
import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.repository.SeriesRepository;
import com.furyviewer.domain.Episode;
import com.furyviewer.service.ArtistService;
import com.furyviewer.service.DateConversorService;
import com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

@Service
public class EpisodeOmdbDTOService {
    public final String apikey = "eb62550d";

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private ArtistService artistService;

    EpisodeOmdbDTORepository apiService = EpisodeOmdbDTORepository.retrofit.create(EpisodeOmdbDTORepository.class);

    public EpisodeOmdbDTO getEpisode(String title, int seasonNum, int episodeNum) {
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

    public void importEpisode (String title, int totalEpisodes, Season se){
        for(int i= 1; i<=totalEpisodes; i++) {
            Episode ep = new Episode();
            EpisodeOmdbDTO episodeOmdbDTO = getEpisode(title,se.getNumber(),i);

            ep.setNumber(i);
            ep.setName(episodeOmdbDTO.getTitle());

            String[] time = episodeOmdbDTO.getRuntime().split(" ");
            ep.setDuration(Double.parseDouble(time[0]));

            ep.setReleaseDate(dateConversorService.releseDateOMDB(episodeOmdbDTO.getReleased()));

            ep.setImdbId(episodeOmdbDTO.getImdbID());
            ep.setDescription(episodeOmdbDTO.getPlot());
            ep.setSeason(se);

            ep.setActors(artistService.importActors(episodeOmdbDTO.getActors()));
            ep.setDirector(artistService.importDirector(episodeOmdbDTO.getDirector()));
            ep.setScriptwriter(artistService.importScripwriter(episodeOmdbDTO.getWriter()));

            episodeRepository.save(ep);
        }
    }
}
