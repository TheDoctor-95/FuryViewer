package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.FavouriteMovie;
import com.furyviewer.domain.Movie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the FavouriteMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavouriteMovieRepository extends JpaRepository<FavouriteMovie, Long> {

    @Query("select favourite_movie from FavouriteMovie favourite_movie where favourite_movie.user.login = ?#{principal.username}")
    List<FavouriteMovie> findByUserIsCurrentUser();

    Optional<FavouriteMovie> findByMovieAndUserLogin(Movie movie, String login);

}
