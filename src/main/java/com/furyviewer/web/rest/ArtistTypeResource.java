package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Artist;
import com.furyviewer.domain.ArtistType;

import com.furyviewer.repository.ArtistTypeRepository;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ArtistType.
 */
@RestController
@RequestMapping("/api")
public class ArtistTypeResource {

    private final Logger log = LoggerFactory.getLogger(ArtistTypeResource.class);

    private static final String ENTITY_NAME = "artistType";

    private final ArtistTypeRepository artistTypeRepository;

    public ArtistTypeResource(ArtistTypeRepository artistTypeRepository) {
        this.artistTypeRepository = artistTypeRepository;
    }

    /**
     * POST  /artist-types : Create a new artistType.
     *
     * @param artistType the artistType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artistType, or with status 400 (Bad Request) if the artistType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artist-types")
    @Timed
    public ResponseEntity<ArtistType> createArtistType(@RequestBody ArtistType artistType) throws URISyntaxException {
        log.debug("REST request to save ArtistType : {}", artistType);
        if (artistType.getId() != null) {
            throw new BadRequestAlertException("A new artistType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtistType result = artistTypeRepository.save(artistType);
        return ResponseEntity.created(new URI("/api/artist-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artist-types : Updates an existing artistType.
     *
     * @param artistType the artistType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artistType,
     * or with status 400 (Bad Request) if the artistType is not valid,
     * or with status 500 (Internal Server Error) if the artistType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artist-types")
    @Timed
    public ResponseEntity<ArtistType> updateArtistType(@RequestBody ArtistType artistType) throws URISyntaxException {
        log.debug("REST request to update ArtistType : {}", artistType);
        if (artistType.getId() == null) {
            return createArtistType(artistType);
        }
        ArtistType result = artistTypeRepository.save(artistType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, artistType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artist-types : get all the artistTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of artistTypes in body
     */
    @GetMapping("/artist-types")
    @Timed
    public List<ArtistType> getAllArtistTypes() {
        log.debug("REST request to get all ArtistTypes");
        return artistTypeRepository.findAll();
        }

    /**
     * GET  /artist-types/:id : get the "id" artistType.
     *
     * @param id the id of the artistType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artistType, or with status 404 (Not Found)
     */
    @GetMapping("/artist-types/{id}")
    @Timed
    public ResponseEntity<ArtistType> getArtistType(@PathVariable Long id) {
        log.debug("REST request to get ArtistType : {}", id);
        ArtistType artistType = artistTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artistType));
    }





//    @GetMapping("/artist-ByType/{name}")
//    @Timed
//    public ResponseEntity <List<Artist>> findArtistByType(@PathVariable String name) {
//        log.debug("REST request to get ArtistType : {}", name);
//        List<Artist> artistType= artistTypeRepository.findArtistByType(name);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artistType));
//    }
    /**
     * DELETE  /artist-types/:id : delete the "id" artistType.
     *
     * @param id the id of the artistType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artist-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtistType(@PathVariable Long id) {
        log.debug("REST request to delete ArtistType : {}", id);
        artistTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
