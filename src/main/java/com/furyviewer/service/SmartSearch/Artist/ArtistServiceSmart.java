package com.furyviewer.service.SmartSearch.Artist;

import com.furyviewer.domain.Artist;
import com.furyviewer.repository.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  ArtistServiceSmart crea el resource para poder hacer peticiones desde la api.
 *  @author Whoger
 */
@Service
@Transactional
public class ArtistServiceSmart {

    private final Logger log = LoggerFactory.getLogger(ArtistServiceSmart.class);

    private final ArtistRepository artistRepository;

    public ArtistServiceSmart(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * Guarda el Artist en la base de datos.
     * @param artist Artist | Artist que se tiene que guardar.
     * @return Artist | Artist guardado.
     */
    public Artist save(Artist artist) {
        log.debug("Request to save Artist : {}", artist);
        return artistRepository.save(artist);
    }

    /**
     * Devuelve todos los Artist de la base de datos.
     * @return List<Artist> | Lista con todos los Artist.
     */
    @Transactional(readOnly = true)
    public List<Artist> findAll() {
        log.debug("Request to get all ArtistS");
        return artistRepository.findAll();
    }

    /**
     * Devuelve la información de un Artist a partir de su id.
     * @param id Long | id del Artist que se quiere buscar.
     * @return Artist | Información del Artist buscado.
     */
    @Transactional(readOnly = true)
    public Artist findOne(Long id) {
        log.debug("Request to get Artist : {}", id);
        return artistRepository.findOne(id);
    }

    /**
     * Elimina un Artist de la base de datos a partir del id.
     * @param id Long | id del Artist que se quiere eliminar.
     */
    public void delete(Long id) {
        log.debug("Request to delete Artist : {}", id);
        artistRepository.delete(id);
    }
}
