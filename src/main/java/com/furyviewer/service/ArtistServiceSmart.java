package com.furyviewer.service;

import com.furyviewer.domain.Artist;
import com.furyviewer.repository.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Artist.
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
     * Save a artist.
     *
     * @param artist the entity to save
     * @return the persisted entity
     */
    public Artist save(Artist artist) {
        log.debug("Request to save Artist : {}", artist);
        return artistRepository.save(artist);
    }

    /**
     *  Get all the artistBS.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Artist> findAll() {
        log.debug("Request to get all ArtistS");
        return artistRepository.findAll();
    }

    /**
     *  Get one artistB by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Artist findOne(Long id) {
        log.debug("Request to get Artist : {}", id);
        return artistRepository.findOne(id);
    }

    /**
     *  Delete the  artistB by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Artist : {}", id);
        artistRepository.delete(id);
    }
}
