package com.furyviewer.service;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.ArtistType;
import com.furyviewer.domain.enumeration.ArtistTypeEnum;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.ArtistTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistTypeRepository artistTypeRepository;

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
        ArtistType atDirector = artistTypeRepository.findByName(ArtistTypeEnum.DIRECTOR);
        if(optionalDirector.isPresent()){
            artist = optionalDirector.get();

            if(!artist.getArtistTypes().contains(atDirector)){
                artist.addArtistType(atDirector);
                artistRepository.save(artist);
            }

        }else{
            artist = new Artist();
            artist.setName(director);
            artist.addArtistType(atDirector);
            artist = artistRepository.save(artist);
        }

        return artist;
    }

    public Artist importScripwriter(String escitor){
        String[] scripwriterArray = escitor.split(",|\\(");

        Optional<Artist> optionalScripwriter = artistRepository.findByName(scripwriterArray[0]);
        Artist artist;
        ArtistType atScripwriter = artistTypeRepository.findByName(ArtistTypeEnum.SCRIPTWRITER);


        if(optionalScripwriter.isPresent()){
            artist = optionalScripwriter.get();

            if(!artist.getArtistTypes().contains(atScripwriter)){
                artist.addArtistType(atScripwriter);
                artistRepository.save(artist);
            }
        }else{
            artist = new Artist();
            artist.setName(scripwriterArray[0]);
            artist.addArtistType(atScripwriter);
            artist = artistRepository.save(artist);
        }

        return artist;
    }
}
