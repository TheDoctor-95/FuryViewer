package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Artist;


import com.furyviewer.domain.ArtistType;
import com.furyviewer.domain.enumeration.ArtistTypeEnum;
import com.furyviewer.repository.ArtistRepository;
import com.furyviewer.repository.FavouriteArtistRepository;
import com.furyviewer.repository.HatredArtistRepository;
import com.furyviewer.repository.ArtistTypeRepository;
import com.furyviewer.service.SmartSearch.Artist.ArtistServiceSmart;
import com.furyviewer.service.TheMovieDB.Service.ArtistTmdbDTOService;
import com.furyviewer.service.dto.Criteria.ArtistBCriteria;
import com.furyviewer.service.dto.TheMovieDB.Artist.CompleteArtistTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Artist.SimpleArtistTmdbDTO;
import com.furyviewer.service.util.ArtistService;
import com.furyviewer.service.util.EpisodeActorsService;
import com.furyviewer.service.dto.util.MultimediaActorsDTO;
import com.furyviewer.service.util.MarksService;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.furyviewer.service.SmartSearch.Artist.ArtistBQueryService;


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
    @Autowired
    private ArtistTmdbDTOService artistTmdbDTOService;

    private final Logger log = LoggerFactory.getLogger(ArtistResource.class);

    private static final String ENTITY_NAME = "artist";

    private final ArtistRepository artistRepository;

    private final HatredArtistRepository hatredArtistRepository;

    private final FavouriteArtistRepository favouriteArtistRepository;

    private final ArtistServiceSmart artistServiceSmart;

    private final ArtistBQueryService artistBQueryService;

    private final ArtistService artistService;

    private final EpisodeActorsService episodeActorsService;

    private final ArtistTypeRepository artistTypeRepository;

    private final MarksService marksService;

    public ArtistResource(ArtistRepository artistRepository, HatredArtistRepository hatredArtistRepository, FavouriteArtistRepository favouriteArtistRepository, ArtistServiceSmart artistServiceSmart, ArtistBQueryService artistBQueryService, ArtistService artistService, EpisodeActorsService episodeActorsService, ArtistTypeRepository artistTypeRepository, MarksService marksService) {
        this.artistRepository = artistRepository;
        this.hatredArtistRepository = hatredArtistRepository;
        this.favouriteArtistRepository = favouriteArtistRepository;
        this.artistServiceSmart = artistServiceSmart;
        this.artistBQueryService = artistBQueryService;
        this.artistService = artistService;
        this.episodeActorsService = episodeActorsService;
        this.artistTypeRepository = artistTypeRepository;
        this.marksService = marksService;
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
    @Transactional
    public ResponseEntity<Artist> getArtist(@PathVariable Long id) {

        log.debug("REST request to get Artist : {}", id);
        Artist artist = artistRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artist));
    }

    /**
     * Encuentra todos los actores de una serie segun su id
     *
     * @param id Long | ID de la serie
     * @return ResponseEntity | Listado de actores
     */
    @GetMapping("/ActorBySeriesID/{id}")
    @Timed
    @Transactional
    public ResponseEntity<List<Artist>> getActorBySeries(@PathVariable Long id) {

        log.debug("REST request to get Artist : {}", id);
        List<Artist> artists = artistService.findActorBySerieId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artists));
    }

    /**
     * Encuentra el director de una serie que ha dirigido mas directores
     *
     * @param id Long | ID de la serie
     * @return ResponseEntity | Director
     */
    @GetMapping("/DirectorBySeriesID/{id}")
    @Timed
    @Transactional
    public ResponseEntity<Artist> getDirectorBySeries(@PathVariable Long id) {

        log.debug("REST request to get Artist : {}", id);
        Artist artists = artistService.findDirectorBySerieId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artists));
    }

    /**
     * Encuentra el esctriror de una serie segun que ha escrito mas episodios
     *
     * @param id Long | ID de la serie
     * @return ResponseEntity | Escritor
     */
    @GetMapping("/ScripwiterBySeriesID/{id}")
    @Timed
    @Transactional
    public ResponseEntity<Artist> getScripwiterBySeries(@PathVariable Long id) {

        log.debug("REST request to get Artist : {}", id);
        Artist artists = artistService.findScriprirterBySerieId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artists));
    }

    @GetMapping("/artist-bs/{id}")
    @Timed
    public ResponseEntity<Artist> getArtistB(@PathVariable Long id) {
        log.debug("REST request to get ArtistB : {}", id);
        Artist artist = artistServiceSmart.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artist));
    }

    /**
     * Find artists by name (commit obligado.... Pau e Ibra son malos....)
     *
     * @param name
     * @return
     */
    @GetMapping("/artistsByName/{name}")
    @Timed
    public ResponseEntity<List<Artist>> findArtistByName(@PathVariable String name) {
        log.debug("REST request to get Artists by name", name);
        List<Artist> artists = artistRepository.findArtistByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artists));
    }

    @GetMapping("/artist-types-name/{artistTypeStr}")
    @Timed
    public ResponseEntity<List<Artist>> findArtistByArtistType(@PathVariable String artistTypeStr) {
        log.debug("REST request to get ArtistType : {}", artistTypeStr);
        try {
            ArtistTypeEnum ate = ArtistTypeEnum.valueOf(artistTypeStr.toUpperCase());
            ArtistType artistType = artistTypeRepository.findByName(ate);

            List<Artist> artists = artistRepository.findArtistByArtistType(artistType);
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artists));
        } catch (IllegalArgumentException e) {

            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, e.getLocalizedMessage());

        }
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

    @GetMapping("/artist-api/test-Tmdb")
    @Timed
    public SimpleArtistTmdbDTO getTestInicial() throws Exception {
        return artistTmdbDTOService.getArtist("Norman Reedus");
    }

    @GetMapping("/artist-api/test-TmdbComplete")
    @Timed
    public CompleteArtistTmdbDTO getTestInfoCompleta() throws Exception {
        return artistTmdbDTOService.getArtistComplete("Norman Reedus");
    }

    @GetMapping("/artist-s")
    @Timed
    public ResponseEntity<List<Artist>> getAllArtistS(ArtistBCriteria criteria) {
        log.debug("REST request to get ArtistS by criteria: {}", criteria);
        List<Artist> entityList = artistBQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }


    /**
     * Top Hatred Movies
     *
     */
    @GetMapping("/artist/topHatredArtist/")
    @Timed
    public ResponseEntity<List<Artist>> findHatredArtist() {
        log.debug("REST request to get top hatred Movies");
        List<Artist> artist = hatredArtistRepository.topHatredArtist();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artist));
    }

    @GetMapping("/artists/{id}/moviesAndSeriesOrderedByDate/")
    @Timed
    public ResponseEntity<List<MultimediaActorsDTO>>f(@PathVariable Long id){
        log.debug("REST request to get Artist : {}", id);
        Artist artist = artistRepository.findOneWithEagerRelationships(id);

        if(artist==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(episodeActorsService.getSeriesAndMoviesFromActor(artist)
            , HttpStatus.OK);
    }

    @GetMapping("/artists/totalFavHate/{id}")
    @Timed
    public Double getTotalFavHate(@PathVariable Long id) {
        log.debug("REST request to get Series by name");
        return marksService.totalFavHate(favouriteArtistRepository.countLikedArtist(id),
            hatredArtistRepository.countHatredArtist(id));
    }

    @GetMapping("/artists/sumaFavHate/{id}")
    @Timed
    public Integer getSumaFavHate(@PathVariable Long id) {
        log.debug("REST request to get Series by name");
        return (favouriteArtistRepository.countLikedArtist(id) + hatredArtistRepository.countHatredArtist(id));
    }
}
