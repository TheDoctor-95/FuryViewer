package com.furyviewer.repository;

import com.furyviewer.domain.FavouriteMovie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the FavouriteMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavouriteMovieRepository extends JpaRepository<FavouriteMovie, Long> {

    @Query("select favourite_movie from FavouriteMovie favourite_movie where favourite_movie.user.login = ?#{principal.username}")
    List<FavouriteMovie> findByUserIsCurrentUser();

}
