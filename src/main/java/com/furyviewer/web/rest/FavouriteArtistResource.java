package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Artist;
import com.furyviewer.domain.FavouriteArtist;

import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.FavouriteArtistRepository;
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
import java.util.*;

/**
 * REST controller for managing FavouriteArtist.
 */
@RestController
@RequestMapping("/api")
public class FavouriteArtistResource {

    private final Logger log = LoggerFactory.getLogger(FavouriteArtistResource.class);

    private static final String ENTITY_NAME = "favouriteArtist";

    private final FavouriteArtistRepository favouriteArtistRepository;

    private final UserRepository userRepository;

    private final ArtistRepository artistRepository;

    public FavouriteArtistResource(FavouriteArtistRepository favouriteArtistRepository, UserRepository userRepository, ArtistRepository artistRepository) {
        this.favouriteArtistRepository = favouriteArtistRepository;
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }

    /**
     * POST  /favourite-artists : Create a new favouriteArtist.
     *
     * @param favouriteArtist the favouriteArtist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new favouriteArtist, or with status 400 (Bad Request) if the favouriteArtist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/favourite-artists")
    @Timed
    public ResponseEntity<FavouriteArtist> createFavouriteArtist(@RequestBody FavouriteArtist favouriteArtist) throws URISyntaxException {
        log.debug("REST request to save FavouriteArtist : {}", favouriteArtist);
        if (favouriteArtist.getId() != null) {
            throw new BadRequestAlertException("A new favouriteArtist cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<FavouriteArtist> existingFavoriteArtist = favouriteArtistRepository.findByArtistAndUserLogin(favouriteArtist.getArtist(), SecurityUtils.getCurrentUserLogin());

        if(existingFavoriteArtist.isPresent()){
            favouriteArtist=existingFavoriteArtist.get();
            favouriteArtist.setLiked(!favouriteArtist.isLiked());
        }

        favouriteArtist.setDate(ZonedDateTime.now());

        favouriteArtist.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        FavouriteArtist result = favouriteArtistRepository.save(favouriteArtist);
        return ResponseEntity.created(new URI("/api/favourite-artists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /favourite-artists : Updates an existing favouriteArtist.
     *
     * @param favouriteArtist the favouriteArtist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated favouriteArtist,
     * or with status 400 (Bad Request) if the favouriteArtist is not valid,
     * or with status 500 (Internal Server Error) if the favouriteArtist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/favourite-artists")
    @Timed
    public ResponseEntity<FavouriteArtist> updateFavouriteArtist(@RequestBody FavouriteArtist favouriteArtist) throws URISyntaxException {
        log.debug("REST request to update FavouriteArtist : {}", favouriteArtist);
        if (favouriteArtist.getId() == null) {
            return createFavouriteArtist(favouriteArtist);
        }
        FavouriteArtist result = favouriteArtistRepository.save(favouriteArtist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, favouriteArtist.getId().toString()))
            .body(result);
    }

    @PostMapping("/favourite-artists/Artist/{id}")
    @Timed
    public ResponseEntity<FavouriteArtist> createFavouriteArtist(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to save HatredSeries : {}", id);

        Artist artist = artistRepository.findOne(id);

        FavouriteArtist fa = new FavouriteArtist();
        fa.setArtist(artist);
        fa.setLiked(true);

        return createFavouriteArtist(fa);
    }

    /**
     * GET  /favourite-artists : get all the favouriteArtists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of favouriteArtists in body
     */
    @GetMapping("/favourite-artists")
    @Timed
    public List<FavouriteArtist> getAllFavouriteArtists() {
        log.debug("REST request to get all FavouriteArtists");
        return favouriteArtistRepository.findAll();
        }

    /**
     * GET  /favourite-artists/:id : get the "id" favouriteArtist.
     *
     * @param id the id of the favouriteArtist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the favouriteArtist, or with status 404 (Not Found)
     */
    @GetMapping("/favourite-artists/{id}")
    @Timed
    public ResponseEntity<FavouriteArtist> getFavouriteArtist(@PathVariable Long id) {
        log.debug("REST request to get FavouriteArtist : {}", id);
        FavouriteArtist favouriteArtist = favouriteArtistRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(favouriteArtist));
    }

    @GetMapping("/favourite-artists/num{id}")
    @Timed
    public Long getNumFavArtist(@PathVariable Long id) {
        log.debug("REST request to get FavouriteArtist : {}", id);
        return favouriteArtistRepository.NumFavArtist(id);
        //return ResponseUtil.wrapOrNotFound(Optional.ofNullable(favouriteArtist));
    }

    /**
     * DELETE  /favourite-artists/:id : delete the "id" favouriteArtist.
     *
     * @param id the id of the favouriteArtist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/favourite-artists/{id}")
    @Timed
    public ResponseEntity<Void> deleteFavouriteArtist(@PathVariable Long id) {
        log.debug("REST request to delete FavouriteArtist : {}", id);
        favouriteArtistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/favourite-artists/check-favorite-artist/{id}")
    @Timed
    public ResponseEntity<Map<String, Boolean>> selectFavouriteArtist(@PathVariable Long id){
        log.debug("REST request to get Artist if favourite: {}", id );
        Boolean check = favouriteArtistRepository.selectFavouriteArtist(id,SecurityUtils.getCurrentUserLogin());
        Map<String, Boolean> checkMap = new HashMap<>();
        checkMap.put("like", check);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(checkMap));
    }

    @PostMapping("/favourite-artists/id/{idArtist}/liked")
    @Timed
    public ResponseEntity<FavouriteArtist> favouriteSeries(@PathVariable Long idArtist) throws URISyntaxException {
        log.debug("REST request to save FavouriteArtist : {}", idArtist);

        Artist a = artistRepository.findOne(idArtist);

        FavouriteArtist fA = new FavouriteArtist();
        fA.setArtist(a);
        fA.setLiked(true);

        return createFavouriteArtist(fA);
    }

    @GetMapping("/favourite-artists/count-favourite-artist/{id}")
    @Timed
    public ResponseEntity<Integer> LikedArtist(@PathVariable Long id) {
        log.debug("REST request to get number of likes of artist");
        Integer num = Math.toIntExact(favouriteArtistRepository.countLikedArtist(id));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(num));
    }




}
