package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.HatredArtist;

import com.furyviewer.repository.HatredArtistRepository;
import com.furyviewer.repository.UserRepository;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HatredArtist.
 */
@RestController
@RequestMapping("/api")
public class HatredArtistResource {

    private final Logger log = LoggerFactory.getLogger(HatredArtistResource.class);

    private static final String ENTITY_NAME = "hatredArtist";

    private final HatredArtistRepository hatredArtistRepository;

    private final UserRepository userRepository;

    public HatredArtistResource(HatredArtistRepository hatredArtistRepository, UserRepository userRepository) {
        this.hatredArtistRepository = hatredArtistRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /hatred-artists : Create a new hatredArtist.
     *
     * @param hatredArtist the hatredArtist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hatredArtist, or with status 400 (Bad Request) if the hatredArtist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hatred-artists")
    @Timed
    public ResponseEntity<HatredArtist> createHatredArtist(@RequestBody HatredArtist hatredArtist) throws URISyntaxException {
        log.debug("REST request to save HatredArtist : {}", hatredArtist);
        if (hatredArtist.getId() != null) {
            throw new BadRequestAlertException("A new hatredArtist cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<HatredArtist> existingHatredArtist = hatredArtistRepository.findByArtistAndUserLogin(hatredArtist.getArtist(), SecurityUtils.getCurrentUserLogin());

        if(existingHatredArtist.isPresent()){
            throw new BadRequestAlertException("Artista ya añadido en Hatred", ENTITY_NAME, "hatredExist");
        }

        hatredArtist.setDate(ZonedDateTime.now());
        hatredArtist.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        HatredArtist result = hatredArtistRepository.save(hatredArtist);
        return ResponseEntity.created(new URI("/api/hatred-artists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hatred-artists : Updates an existing hatredArtist.
     *
     * @param hatredArtist the hatredArtist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hatredArtist,
     * or with status 400 (Bad Request) if the hatredArtist is not valid,
     * or with status 500 (Internal Server Error) if the hatredArtist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hatred-artists")
    @Timed
    public ResponseEntity<HatredArtist> updateHatredArtist(@RequestBody HatredArtist hatredArtist) throws URISyntaxException {
        log.debug("REST request to update HatredArtist : {}", hatredArtist);
        if (hatredArtist.getId() == null) {
            return createHatredArtist(hatredArtist);
        }
        HatredArtist result = hatredArtistRepository.save(hatredArtist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hatredArtist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hatred-artists : get all the hatredArtists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hatredArtists in body
     */
    @GetMapping("/hatred-artists")
    @Timed
    public List<HatredArtist> getAllHatredArtists() {
        log.debug("REST request to get all HatredArtists");
        return hatredArtistRepository.findAll();
        }

    /**
     * GET  /hatred-artists/:id : get the "id" hatredArtist.
     *
     * @param id the id of the hatredArtist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hatredArtist, or with status 404 (Not Found)
     */
    @GetMapping("/hatred-artists/{id}")
    @Timed
    public ResponseEntity<HatredArtist> getHatredArtist(@PathVariable Long id) {
        log.debug("REST request to get HatredArtist : {}", id);
        HatredArtist hatredArtist = hatredArtistRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hatredArtist));
    }
    @GetMapping("/num-hatred-artists/{id}")
    @Timed
    public Long getNumHatredArtist(@PathVariable Long id) {
        log.debug("REST request to get HatredArtist : {}", id);
        return hatredArtistRepository.NumHatredArtist(id);
       // return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hatredArtist));
    }

    /**
     * DELETE  /hatred-artists/:id : delete the "id" hatredArtist.
     *
     * @param id the id of the hatredArtist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hatred-artists/{id}")
    @Timed
    public ResponseEntity<Void> deleteHatredArtist(@PathVariable Long id) {
        log.debug("REST request to delete HatredArtist : {}", id);
        hatredArtistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    //@GetMapping("/count-hatred-artists/{id}")
    //@Timed
    //public HatredArtist countHatredArtist(@PathVariable Long id) {
    //    log.debug("REST request to get HatredArtist : {}", id);
    //    return hatredArtistRepository.countHatredArtistByArtistAndHated (id);
        // return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hatredArtist));
    //}


}
