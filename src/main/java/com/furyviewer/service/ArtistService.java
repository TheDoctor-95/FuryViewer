package com.furyviewer.service;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.enumeration.ArtistTypeEnum;
import com.furyviewer.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public Set<Artist> importActors(String actorsListStr){
        String[] actors = actorsListStr.split(", ");
        Set<Artist> artists = new HashSet<>();
        for(String actorStr: actors){
            Optional<Artist> optionalActor = artistRepository.findByName(actorStr);
            Artist artist;
            if(optionalActor.isPresent()){
                artist = optionalActor.get();
            }else{
                artist = new Artist();
                artist.setName(actorStr);

                artist = artistRepository.save(artist);
            }

            artists.add(artist);
        }
        return artists;

    }

    public Artist importDirector(String director){
        Optional<Artist> optionalDirector = artistRepository.findByName(director);
        Artist artist;
        if(optionalDirector.isPresent()){
            artist = optionalDirector.get();
        }else{
            artist = new Artist();
            artist.setName(director);
            artist = artistRepository.save(artist);
        }

        return artist;
    }

    public Artist importScripwriter(String escitor){
        String[] esctiroresArray = escitor.split(",|\\(");

        Optional<Artist> optionalScripwriter = artistRepository.findByName(esctiroresArray[0]);
        Artist artist;

        if(optionalScripwriter.isPresent()){
            artist = optionalScripwriter.get();
        }else{
            artist = new Artist();
            artist.setName(esctiroresArray[0]);
            artist = artistRepository.save(artist);
        }

        return artist;
    }
}
