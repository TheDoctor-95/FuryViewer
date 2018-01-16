package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Artist;

import com.furyviewer.domain.ArtistType;
import com.furyviewer.domain.enumeration.ArtistTypeEnum;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.ArtistTypeRepository;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Artist.
 */
@RestController
@RequestMapping("/api")
public class ArtistResource {

    private final Logger log = LoggerFactory.getLogger(ArtistResource.class);

    private static final String ENTITY_NAME = "artist";

    private final ArtistRepository artistRepository;


    @Inject
    private ArtistTypeRepository artistTypeRepository;

    public ArtistResource(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * POST  /artists : Create a new artist.
     *
     * @param artist the artist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artist, or with status 400 (Bad Request) if the artist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artists")
    @Timed
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) throws URISyntaxException {
        log.debug("REST request to save Artist : {}", artist);
        if (artist.getId() != null) {
            throw new BadRequestAlertException("A new artist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Artist result = artistRepository.save(artist);
        return ResponseEntity.created(new URI("/api/artists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artists : Updates an existing artist.
     *
     * @param artist the artist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artist,
     * or with status 400 (Bad Request) if the artist is not valid,
     * or with status 500 (Internal Server Error) if the artist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artists")
    @Timed
    public ResponseEntity<Artist> updateArtist(@RequestBody Artist artist) throws URISyntaxException {
        log.debug("REST request to update Artist : {}", artist);
        if (artist.getId() == null) {
            return createArtist(artist);
        }
        Artist result = artistRepository.save(artist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, artist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artists : get all the artists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of artists in body
     */
    @GetMapping("/artists")
    @Timed
    public List<Artist> getAllArtists() {
        log.debug("REST request to get all Artists");
        return artistRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /artists/:id : get the "id" artist.
     *
     * @param id the id of the artist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artist, or with status 404 (Not Found)
     */
    @GetMapping("/artists/{id}")
    @Timed
    public ResponseEntity<Artist> getArtist(@PathVariable Long id) {
        log.debug("REST request to get Artist : {}", id);
        Artist artist = artistRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artist));
    }

    /**
     *
     * Find artists by name (commit obligado.... Pau e Ibra son malos....)
     *
     * @param name
     * @return
     */
    @GetMapping("/artistsByName/{name}")
    @Timed
    public ResponseEntity<List<Artist>> findArtistByName(@PathVariable String name) {
        log.debug("REST request to get Artists by name", name);
        List<Artist> artists =artistRepository.findArtistByName(name);
           return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artists));
    }

    @GetMapping("/artist-types-name/{artistTypeStr}")
    @Timed
    public ResponseEntity <List<Artist>>  findArtistByArtistType(@PathVariable String artistTypeStr) {
        log.debug("REST request to get ArtistType : {}", artistTypeStr);

        ArtistType artistType = artistTypeRepository.findByName(ArtistTypeEnum.valueOf(artistTypeStr.toUpperCase()));

        List<Artist> artists= artistRepository.findArtistByArtistType(artistType);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artists));
    }
//    @GetMapping("/artistByType/{name}")
//    @Timed
//    public ResponseEntity <List<Artist>> findArtistByArtistTypesEquals(@PathVariable String name) {
//        log.debug("REST request to get ArtistType : {}", name);
//        List<Artist> artistType= artistRepository.findArtistByArtistTypesEquals(name);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artistType));
//    }

    /**
     * DELETE  /artists/:id : delete the "id" artist.
     *
     * @param id the id of the artist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artists/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        log.debug("REST request to delete Artist : {}", id);
        artistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
