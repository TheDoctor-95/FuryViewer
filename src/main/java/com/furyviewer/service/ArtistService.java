package com.furyviewer.service;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.ArtistType;
import com.furyviewer.domain.enumeration.ArtistTypeEnum;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.ArtistTypeRepository;
import com.furyviewer.service.util.NAEraserService;
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

    @Autowired
    private NAEraserService naEraserService;

    /**
     * Método que se encarga de convertir un String en los objetos de la clase Artist necesarios que contiene su nombre
     * y main_actor como tipo de artista.
     * @param actorsListStr String | Contiene el nombre de los artistas.
     * @return Set<Artist> | Set que contiene la información de los artistas.
     */
    public Set<Artist> importActors(String actorsListStr){
        Set<Artist> artists = new HashSet<>();

        if (naEraserService.eraserNA(actorsListStr) != null) {
            String[] actors = actorsListStr.split(", ");
            ArtistType atMainActor = artistTypeRepository.findByName(ArtistTypeEnum.MAIN_ACTOR);

            for (String actorStr : actors) {
                Optional<Artist> optionalActor = artistRepository.findByName(actorStr);
                Artist artist;

                //En caso de que el artista exista se comprueba si ya tiene asignado el tipo main_actor.
                if (optionalActor.isPresent()) {
                    artist = optionalActor.get();

                    if (!artist.getArtistTypes().contains(atMainActor)) {
                        artist.addArtistType(atMainActor);
                        artist = artistRepository.save(artist);
                    }

                } else {
                    //Se crea un artista desde cero.
                    artist = new Artist();
                    artist.setName(actorStr);
                    artist.addArtistType(atMainActor);
                    artist = artistRepository.save(artist);
                }

                artists.add(artist);
            }
        }
        return artists;
    }

    /**
     * Método que se encarga de convertir un String en un objeto de la clase Artist que contiene su nombre y
     * director como tipo de artista.
     * @param director String | Contiene el nombre del artista.
     * @return Artist | Objeto que contiene la información del artista.
     */
    public Artist importDirector(String director){
        Artist artist = null;

        if (naEraserService.eraserNA(director) != null) {
            String[] directorArray = director.split(", | \\(");
            Optional<Artist> optionalDirector = artistRepository.findByName(directorArray[0]);
            ArtistType atDirector = artistTypeRepository.findByName(ArtistTypeEnum.DIRECTOR);

            //En caso de que el artista exista se comprueba si ya tiene asignado el tipo director.
            if(optionalDirector.isPresent()){
                artist = optionalDirector.get();

                if(!artist.getArtistTypes().contains(atDirector)){
                    artist.addArtistType(atDirector);
                    artistRepository.save(artist);
                }

            }else{
                //Se crea un artista desde cero.
                artist = new Artist();
                artist.setName(directorArray[0]);
                artist.addArtistType(atDirector);
                artist = artistRepository.save(artist);
            }
        }

        return artist;
    }

    /**
     * Método que se encarga de convertir un String en un objeto de la clase Artist que contiene su nombre y
     * scripwriter como tipo de artista.
     * @param scripwriter String | Contiene el nombre del artista.
     * @return Artist | Objeto que contiene la información del artista.
     */
    public Artist importScripwriter(String scripwriter){
        Artist artist = null;

        if (naEraserService.eraserNA(scripwriter) != null) {
            String[] scripwriterArray = scripwriter.split(", | \\(");
            Optional<Artist> optionalScripwriter = artistRepository.findByName(scripwriterArray[0]);
            ArtistType atScripwriter = artistTypeRepository.findByName(ArtistTypeEnum.SCRIPTWRITER);

            //En caso de que el artista exista se comprueba si ya tiene asignado el tipo scriptwriter.
            if (optionalScripwriter.isPresent()) {
                artist = optionalScripwriter.get();

                if (!artist.getArtistTypes().contains(atScripwriter)) {
                    artist.addArtistType(atScripwriter);
                    artistRepository.save(artist);
                }

            } else {
                //Se crea un artista desde cero.
                artist = new Artist();
                artist.setName(scripwriterArray[0]);
                artist.addArtistType(atScripwriter);
                artist = artistRepository.save(artist);
            }
        }

        return artist;
    }
}
