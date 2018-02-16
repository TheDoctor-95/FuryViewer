package com.furyviewer.service.util;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.ArtistType;
import com.furyviewer.domain.enumeration.ArtistTypeEnum;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.ArtistTypeRepository;
import com.furyviewer.service.TheMovieDB.Service.ArtistTmdbDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Servicio que se encarga de devolver un Artist de la base de datos o en caso de no existir delega en
 * ArtistTmdbDTOService para crearla.
 *
 * @author IFriedkin
 * @author TheDoctor-95
 * @see com.furyviewer.service.TheMovieDB.Service.ArtistTmdbDTOService
 */
@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistTypeRepository artistTypeRepository;

    @Autowired
    private NAEraserService naEraserService;

    @Autowired
    private ArtistTmdbDTOService artistTmdbDTOService;

    /**
     * Metodo que se encarga de convertir un String en los objetos de la clase Artist necesarios que contiene su nombre
     * y main_actor como tipo de artista.
     *
     * @param actorsListStr String | Contiene el nombre de los artistas.
     * @return Set | Set que contiene la informacion de los artistas.
     */
    public Set<Artist> importActors(String actorsListStr) {
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
                    artist = artistTmdbDTOService.importArtist(actorStr, atMainActor);
                }

                artists.add(artist);
            }
        }
        return artists;
    }


    /**
     * Metodo que se encarga de convertir un String en un objeto de la clase Artist que contiene su nombre y
     * director como tipo de artista.
     *
     * @param director String | Contiene el nombre del artista.
     * @return Artist | Objeto que contiene la informacion del artista.
     */
    public Artist importDirector(String director) {
        Artist artist = null;

        if (naEraserService.eraserNA(director) != null) {
            String[] directorArray = director.split(", | \\(");
            Optional<Artist> optionalDirector = artistRepository.findByName(directorArray[0]);
            ArtistType atDirector = artistTypeRepository.findByName(ArtistTypeEnum.DIRECTOR);

            //En caso de que el artista exista se comprueba si ya tiene asignado el tipo director.
            if (optionalDirector.isPresent()) {
                artist = optionalDirector.get();

                if (!artist.getArtistTypes().contains(atDirector)) {
                    artist.addArtistType(atDirector);
                    artistRepository.save(artist);
                }

            } else {
                //Se crea un artista desde cero.
                artist = artistTmdbDTOService.importArtist(directorArray[0], atDirector);
            }
        }

        return artist;
    }

    /**
     * Metodo que se encarga de convertir un String en un objeto de la clase Artist que contiene su nombre y
     * scripwriter como tipo de artista.
     *
     * @param scripwriter String | Contiene el nombre del artista.
     * @return Artist | Objeto que contiene la informacion del artista.
     */
    public Artist importScripwriter(String scripwriter) {
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
                artist = artistTmdbDTOService.importArtist(scripwriterArray[0], atScripwriter);
            }
        }

        return artist;
    }

    /**
     * Devuelve todos los Artist de una Series a partir del id.
     *
     * @param serieID Long | id de la Series.
     * @return List | List que contiene la informacion de todos los Artist.
     */
    @Transactional
    public List<Artist> findActorBySerieId(Long serieID) {
        return artistRepository.findAll().stream()
            .filter(artist -> artist.getEpisodes().stream()
                .anyMatch(episode -> episode.getSeason().getSeries().getId().equals(serieID)))
            .sorted((a1, a2) -> {
                long numEpisodes1 = a1.getEpisodes().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                long numEpisodes2 = a2.getEpisodes().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                return compare(numEpisodes1, numEpisodes2);
            })
            .collect(Collectors.toList());
    }

    @Transactional
    public Artist findDirectorBySerieId(Long serieID) {
        return artistRepository.findAll().stream()
            .filter(artist -> artist.getEpisodeDirectors().stream()
                .anyMatch(episode -> episode.getSeason().getSeries().getId().equals(serieID)))
            .sorted((a1, a2) -> {
                long numEpisodes1 = a1.getEpisodeDirectors().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                long numEpisodes2 = a2.getEpisodeDirectors().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                return compare(numEpisodes1, numEpisodes2);

            })
            .collect(Collectors.toList()).get(0);
    }

    @Transactional
    public Artist findScriprirterBySerieId(Long serieID) {
        return artistRepository.findAll().stream()
            .filter(artist -> artist.getEpisodesScriptwriters().stream()
                .anyMatch(episode -> episode.getSeason().getSeries().getId().equals(serieID)))
            .sorted((a1, a2) -> {
                long numEpisodes1 = a1.getEpisodesScriptwriters().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                long numEpisodes2 = a2.getEpisodesScriptwriters().stream().filter(episode -> episode.getSeason().getSeries().getId().equals(serieID))
                    .count();
                return compare(numEpisodes1, numEpisodes2);
            })
            .collect(Collectors.toList()).get(0);
    }

    public int compare(long l1, long l2) {
        if (l1 > l2) return -1;
        else if (l1 < l2) return 1;
        else return 0;
    }
}
