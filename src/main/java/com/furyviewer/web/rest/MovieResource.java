package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Artist;
import com.furyviewer.domain.Movie;

import com.furyviewer.repository.*;
import com.furyviewer.security.SecurityUtils;
import com.furyviewer.service.SmartSearch.Movie.MovieQueryService;
import com.furyviewer.service.OpenMovieDatabase.Service.MovieOmdbDTOService;
import com.furyviewer.service.dto.Criteria.MovieBCriteria;
import com.furyviewer.service.dto.OpenMovieDatabase.MovieOmdbDTO;
import com.furyviewer.service.dto.util.ActorsLimitDTO;
import com.furyviewer.service.util.ArtistLimitService;
import com.furyviewer.service.util.MarksService;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Movie.
 */
@RestController
@RequestMapping("/api")
public class MovieResource {

    private final Logger log = LoggerFactory.getLogger(MovieResource.class);

    private static final String ENTITY_NAME = "movie";

    private final MovieRepository movieRepository;

    private final OkHttpClient client = new OkHttpClient();

    private final MovieQueryService movieQueryService;

    private final HatredMovieRepository hatredMovieRepository;

    private final FavouriteMovieRepository favouriteMovieRepository;

    private final MovieStatsRepository movieStatsRepository;

    private final UserRepository userRepository;

    private final RateMovieRepository rateMovieRepository;

    private final ArtistRepository artistRepository;

    private final MovieOmdbDTOService movieOmdbDTOService;

    private final MarksService marksService;

    private final ArtistLimitService artistLimitService;



    public MovieResource(MovieRepository movieRepository, MovieQueryService movieQueryService, HatredMovieRepository hatredMovieRepository, FavouriteMovieRepository favouriteMovieRepository, MovieStatsRepository movieStatsRepository, UserRepository userRepository, RateMovieRepository rateMovieRepository, ArtistRepository artistRepository, MovieOmdbDTOService movieOmdbDTOService, MarksService marksService, ArtistLimitService artistLimitService) {
        this.movieRepository = movieRepository;
        this.movieQueryService = movieQueryService;
        this.hatredMovieRepository = hatredMovieRepository;
        this.favouriteMovieRepository = favouriteMovieRepository;
        this.movieStatsRepository = movieStatsRepository;
        this.userRepository = userRepository;
        this.rateMovieRepository = rateMovieRepository;
        this.artistRepository = artistRepository;
        this.movieOmdbDTOService = movieOmdbDTOService;
        this.marksService = marksService;
        this.artistLimitService = artistLimitService;
    }

    /**
     * POST  /movies : Create a new movie.
     *
     * @param movie the movie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movie, or with status 400 (Bad Request) if the movie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movies")
    @Timed
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) throws URISyntaxException {
        log.debug("REST request to save Movie : {}", movie);
        if (movie.getId() != null) {
            throw new BadRequestAlertException("A new movie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Movie result = movieRepository.save(movie);
        return ResponseEntity.created(new URI("/api/movies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movies : Updates an existing movie.
     *
     * @param movie the movie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movie,
     * or with status 400 (Bad Request) if the movie is not valid,
     * or with status 500 (Internal Server Error) if the movie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movies")
    @Timed
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) throws URISyntaxException {
        log.debug("REST request to update Movie : {}", movie);
        if (movie.getId() == null) {
            return createMovie(movie);
        }
        Movie result = movieRepository.save(movie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movies : get all the movies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movies in body
     */
    @GetMapping("/movies")
    @Timed
    public List<Movie> getAllMovies() {
        log.debug("REST request to get all Movies");
        return movieRepository.findAllWithEagerRelationships();
        }


    @GetMapping("/movies/Pending/")
    @Timed
    public List<Movie> getPendingMovies() {
        log.debug("REST request to get all Movies");
        return movieStatsRepository.pendingMovies(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
    }

    @GetMapping("/movies/Pending/5")
    @Timed
    public List<Movie> getPendingMovies5() {
        log.debug("REST request to get all Movies");
        List<Movie> movieList = movieStatsRepository.pendingMovies(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        if(movieList.size()<5){
            return movieList;
        }else{
            return movieList.subList(0, 5);
        }
    }

    @GetMapping("/movies/totalFavHate/{id}")
    @Timed
    public Double getTotalFavHate(@PathVariable Long id) {
        log.debug("REST request to get Series by name");
        return marksService.totalFavHate(favouriteMovieRepository.countLikedMovie(id),
            hatredMovieRepository.countHatredMovie(id));
    }

    @GetMapping("/movies/sumaFavHate/{id}")
    @Timed
    public Integer getSumaFavHate(@PathVariable Long id) {
        log.debug("REST request to get Series by name");
        return (favouriteMovieRepository.countLikedMovie(id) + hatredMovieRepository.countHatredMovie(id));
    }


    /**
     * GET  /movies/:id : get the "id" movie.
     *
     * @param id the id of the movie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movie, or with status 404 (Not Found)
     */
    @GetMapping("/movies/{id}")
    @Timed
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        Movie movie = movieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }

    @GetMapping("/movies/{id}/actors")
    @Transactional
    @Timed
    public ResponseEntity<Set<Artist>> getActors(@PathVariable Long id) {
        log.debug("REST request to get Movie : {}", id);
        Movie movie = movieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie.getActorMains()));
    }





    @GetMapping("/importMovieByName/{name}")
    @Timed

    public ResponseEntity<Movie> importMovieByName(@PathVariable String name) {
        log.debug("REST request to get Movies by name", name);
         Movie movie = movieOmdbDTOService.importMovieByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }


    /**
     *
     * Find movies by name (commit obligado.... Pau e Ibra son malos....)
     *
     * @param name
     * @return
     */
    @GetMapping("/moviesByName/{name}")
    @Timed
    public ResponseEntity<List<Movie>> findMovieByName(@PathVariable String name) {
        log.debug("REST request to get Movies by name", name);
        List<Movie> movie =movieRepository.findMovieByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }

    /**
    Find movies by Top

     */

    @GetMapping("/movies/topPelis/")
    @Timed
    public ResponseEntity<List<Movie>> findTopPelis() {
        log.debug("REST request to get Movies by name");
        List<Movie> movie = rateMovieRepository.topPelis(new PageRequest(0, 5));
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }

    /**
     * Top Hatred Movies
     *
     */
    @GetMapping("/movies/topHatredPelis/")
    @Timed
    public ResponseEntity<List<Movie>> findHatredPelis() {
        log.debug("REST request to get top hatred Movies");
        List<Movie> movie = hatredMovieRepository.topHatredMovies();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }



    /**
     * DELETE  /movies/:id : delete the "id" movie.
     *
     * @param id the id of the movie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movies/{id}")
    @Timed
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.debug("REST request to delete Movie : {}", id);
        movieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/movie-s")
    @Timed
    public ResponseEntity<List<Movie>> getAllMovieS(MovieBCriteria criteria) {
        log.debug("REST request to get MoviesS by criteria: {}", criteria);
        List<Movie> entityList = movieQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    @GetMapping("/movie-api/test")
    @Timed
    public MovieOmdbDTO getTestInicial() throws Exception {


        return movieOmdbDTOService.getMovieByName("Justice League");
    }


    @GetMapping("/movies/by-artist/{id}")
    @Timed
    public List<Movie> getAllMoviesFromArtist(@PathVariable Long id){
        log.debug("Get to request movies from artist order by date desc");
        return movieRepository.getByArtistOrderbyDate(artistRepository.findOne(id));
    }



    @GetMapping("/movies/by-director/{id}")
    @Timed
    public List<Movie> getAllMoviesFromDirector(@PathVariable Long id){
        log.debug("Get to request movies from director order by date desc");
        return movieRepository.findMovieByDirectorOrderByReleaseDate(artistRepository.findOne(id));
    }

    @GetMapping("/movies/by-scriptwriter/{id}")
    @Timed
    public List<Movie> getAllMoviesFromScriptWriter(@PathVariable Long id){
        log.debug("Get to request movies from scriptwriter order by date desc");
        return movieRepository.findMovieByScriptwriterOrderByReleaseDate(artistRepository.findOne(id));
    }

    //@GetMapping("/movies/{id}/actors-limit")
    //@Transactional
    //@Timed
    //public ResponseEntity<Set<Artist>> getActorsLimit(@PathVariable Long id) {
    //    log.debug("REST request to get Movie : {}", id);
    //    Pageable topEight = new PageRequest(0, 8);
    //    List<Movie> movie = movieRepository.findOneWithEagerRelationshipsLimit(id, topEight);
    //    return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie.getActorMains()));
    //}

    @GetMapping("/movies/{id}/actors-limit")
    @Timed
    public ResponseEntity<List<ActorsLimitDTO>> getActorsLimit(@PathVariable Long id) {
        List<ActorsLimitDTO> a = artistLimitService.getInfoFromArtist(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(a));
    }

    @GetMapping("/movies/{id}/actors-limitless")
    @Timed
    public ResponseEntity<List<ActorsLimitDTO>> getActorsLimitless(@PathVariable Long id) {
        List<ActorsLimitDTO> a = artistLimitService.getInfoFromArtistLimitless(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(a));
    }
}
