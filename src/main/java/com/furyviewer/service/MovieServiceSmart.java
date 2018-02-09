package com.furyviewer.service;

import com.furyviewer.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.furyviewer.domain.Movie;

/**
 * Service Implementation for managing Movie.
 */
@Service
@Transactional
public class MovieServiceSmart {

    private final Logger log = LoggerFactory.getLogger(MovieServiceSmart.class);

    private final MovieRepository movieRepository;

    public MovieServiceSmart(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Save a movieB.
     *
     * @param movie the entity to save
     * @return the persisted entity
     */
    public Movie save(Movie movie) {
        log.debug("Request to save Movie : {}", movie);
        return movieRepository.save(movie);
    }

    /**
     *  Get all the movieBS.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Movie> findAll() {
        log.debug("Request to get all MovieS");
        return movieRepository.findAll();
    }

    /**
     *  Get one movieB by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Movie findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findOne(id);
    }

    /**
     *  Delete the  movieB by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.delete(id);
    }
}
