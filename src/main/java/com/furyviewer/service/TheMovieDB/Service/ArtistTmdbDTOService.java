package com.furyviewer.service.TheMovieDB.Service;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.ArtistType;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.service.TheMovieDB.Repository.ArtistTmdbDTORepository;
import com.furyviewer.service.dto.TheMovieDB.Artist.ArtistFinalTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Artist.ArtistTmdbDTO;
import com.furyviewer.service.util.CountryService;
import com.furyviewer.service.util.DateConversorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Servicio encargado de recuperar información de un Artist desde ArtistTmdbDTORepository y la convierte al
 * formato FuryViewer.
 * @author IFriedkin
 */
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

    private final ArtistTmdbDTORepository apiTMDB =
        ArtistTmdbDTORepository.retrofit.create(ArtistTmdbDTORepository.class);

    /**
     * Método que se encarga de pedir a la api de TheMovieDB la información básica de un actor.
     * @param artistName String | Nombre del actor a buscar.
     * @return ArtistTmdbDTO | Información en el formato proporcionado por la API.
     * @throws IOException En caso de que no se pueda hacer la petición a la api se lanza la excepción.
     */
    public ArtistTmdbDTO getArtist(String artistName) throws IOException {
        ArtistTmdbDTO artist;
        Call<ArtistTmdbDTO> callArtist = apiTMDB.getArtist(apikey, artistName);

        Response<ArtistTmdbDTO> response = callArtist.execute();

        if(response.isSuccessful()){
            artist = response.body();
            System.out.println(artist);
        }
        else {
            throw new IOException(response.message());
        }

        return artist;
    }

    /**
     * Método que se encarga de devolver el id del artista en la API de TMDB.
     * @param artistName String | Nombre del artista a buscar.
     * @return int | El id interno de la api de TMDB.
     * @throws IOException En caso de que no se pueda hacer la petición a la api se lanza la excepción.
     */
    public int getID(String artistName) throws IOException {
        int id;

        ArtistTmdbDTO artistTmdbDTO = getArtist(artistName);

        id = artistTmdbDTO.getResults().get(0).getId();

        return id;
    }

    /**
     * Método que se encarga de recuperar toda la información del artist de la api de TMDB.
     * @param artistName String | Nombre del artista a buscar.
     * @return ArtistFinalTmdbDTO | Información en el formato proporcionado por la API.
     * @throws IOException En caso de que no se pueda hacer la petición a la api se lanza la excepción.
     */
    public ArtistFinalTmdbDTO getArtistComplete(String artistName) throws IOException {
        ArtistFinalTmdbDTO artist;
        int id = getID(artistName);

        Call<ArtistFinalTmdbDTO> callArtist = apiTMDB.getFinalArtist(id, apikey);

        Response<ArtistFinalTmdbDTO> response = callArtist.execute();

        if (response.isSuccessful()) {
            artist = response.body();
        } else {
            throw new IOException(response.message());
        }

        return artist;
    }

    /**
     * Convierte la información de un artist de TMDB al formato de FuryViewer.
     * @param artistName String | Nombre del artista a buscar.
     * @param artistType ArtistType | Tipo del artista.
     * @return Artist | Contiene la información de un artist en el formato FuryViewer.
     */
    public Artist importArtist(String artistName, ArtistType artistType) {
        Artist artist = new Artist();

        artist.setName(artistName);
        artist.addArtistType(artistType);


        //Ponemos mote al bucle y lo utilizamos para hacer la petición hasta tres veces para asegurarnos de que
        // podemos realizar la petición a la api.
        getArtist:
        for (int i = 0; i < 3; i++) {
            try {
                ArtistFinalTmdbDTO artistFinalTmdbDTO = getArtistComplete(artistName);

                if (artistFinalTmdbDTO.getBirthday() != null) {
                    artist.setBirthdate(
                        dateConversorService.releaseDateOMDBSeason(artistFinalTmdbDTO.getBirthday().toString()));

                }
                if (artistFinalTmdbDTO.getDeathday() != null) {
                    if(artistFinalTmdbDTO.getDeathday().toString().split("-").length == 3) {
                        artist.setDeathdate(
                            dateConversorService.releaseDateOMDBSeason(artistFinalTmdbDTO.getDeathday().toString()));
                    }
                }

                if (artistFinalTmdbDTO.getGender() != null) {
                    switch (artistFinalTmdbDTO.getGender()) {
                        case 0:
                            artist.setSex("Undefined");
                            break;
                        case 1:
                            artist.setSex("Female");
                            break;
                        case 2:
                            artist.setSex("Male");
                            break;
                    }
                }

                if (artistFinalTmdbDTO.getProfilePath() != null) {
                    artist.setImgUrl(pathImage + artistFinalTmdbDTO.getProfilePath());
                }
                if (artistFinalTmdbDTO.getImdbId() != null) {
                    artist.setImdb_id(artistFinalTmdbDTO.getImdbId());
                }
                if (artistFinalTmdbDTO.getPlaceOfBirth() != null) {
                    artist.setCountry(countryService.importCountry(artistFinalTmdbDTO.getPlaceOfBirth().toString()));
                }

                //Salimos del bucle
                break getArtist;

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    System.out.println("Durmiendo el thread 5 segundos");
                    Thread.sleep(5000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        artist = artistRepository.save(artist);

        return artist;
    }
}
