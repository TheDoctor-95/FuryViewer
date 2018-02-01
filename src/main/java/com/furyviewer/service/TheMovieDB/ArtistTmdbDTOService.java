package com.furyviewer.service.TheMovieDB;

import com.furyviewer.domain.Artist;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.service.dto.TheMovieDB.ArtistFinalTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.ArtistTmdbDTO;
import com.furyviewer.service.util.CountryService;
import com.furyviewer.service.util.DateConversorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

@Service
public class ArtistTmdbDTOService {
    private final String apikey = "08526181d206d48ab49b3fa0be7ad1bf";
    private final String pathImage = "https://image.tmdb.org/t/p/w500";

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private DateConversorService dateConversorService;

    @Autowired
    private CountryService countryService;

    private final ArtistTmdbDTORepository apiTMDB = ArtistTmdbDTORepository.retrofit.create(ArtistTmdbDTORepository.class);

    public ArtistTmdbDTO getArtist (String artistName) {
        ArtistTmdbDTO artist = new ArtistTmdbDTO();
        Call<ArtistTmdbDTO> callArtist = apiTMDB.getArtist(apikey, artistName);

        try{
            artist = callArtist.execute().body();
            System.out.println(artist);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return artist;
    }

    public int getID(String artistName) {
        int id;

        ArtistTmdbDTO artistTmdbDTO = getArtist(artistName);

        id = artistTmdbDTO.getResults().get(0).getId();

        return id;
    }

    public ArtistFinalTmdbDTO getArtistComplete(String artistName) {
        ArtistFinalTmdbDTO artist = new ArtistFinalTmdbDTO();
        int id = getID(artistName);

        Call<ArtistFinalTmdbDTO> callArtist = apiTMDB.getFinalArtist(id, apikey);

        try{
            artist = callArtist.execute().body();
            System.out.println(artist);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return artist;
    }

    public Artist importArtist(String artistName) {
        Artist artist = new Artist();

        ArtistFinalTmdbDTO artistFinalTmdbDTO = getArtistComplete(artistName);

        artist.setName(artistName);
        if(artistFinalTmdbDTO.getBirthday() != null) {
            artist.setBirthdate(dateConversorService.releaseDateOMDBSeason(artistFinalTmdbDTO.getBirthday().toString()));
        }
        if(artistFinalTmdbDTO.getDeathday() != null) {
            artist.setDeathdate(dateConversorService.releaseDateOMDBSeason(artistFinalTmdbDTO.getDeathday().toString()));
        }

        switch (artistFinalTmdbDTO.getGender()) {
            case 0:
                artist.setSex("Undefined");
            case 1:
                artist.setSex("Female");
            case 2:
                artist.setSex("Male");
        }

        artist.setImgUrl(pathImage + artistFinalTmdbDTO.getProfilePath());
        artist.setImdb_id(artistFinalTmdbDTO.getImdbId());
        //artist.setCountry(countryService.importCountry(countryService.countryArtist(artistFinalTmdbDTO.getPlaceOfBirth().toString())));

        //artist = artistRepository.save(artist);

        return artist;
    }
}
