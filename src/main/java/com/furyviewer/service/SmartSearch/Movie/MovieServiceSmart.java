package com.furyviewer.service.SmartSearch.Movie;

import com.furyviewer.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.furyviewer.domain.Movie;

/**
 *  MovieServiceSmart crea el resource para poder hacer peticiones desde la api.
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
     * Guarda la Movie en la base de datos.
     * @param movie Movie | Movie que se tiene que guardar.
     * @return Movie | Movie guardada.
     */
    public Movie save(Movie movie) {
        log.debug("Request to save Movie : {}", movie);
        return movieRepository.save(movie);
    }

    /**
     * Devuelve todas las Movie de la base de datos.
     * @return List<Movie> | Lista con todas las Movie.
     */
    @Transactional(readOnly = true)
    public List<Movie> findAll() {
        log.debug("Request to get all MovieS");
        return movieRepository.findAll();
    }

    /**
     * Devuelve la información de una Movie a partir de su id.
     * @param id Long | id de la Movie que se quiere buscar.
     * @return Movie | Información de la Movie buscada.
     */
    @Transactional(readOnly = true)
    public Movie findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findOne(id);
    }

    /**
     * Elimina una Movie de la base de datos a partir del id.
     * @param id Long | id de la Movie que se quiere eliminar.
     */
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.delete(id);
    }
}
