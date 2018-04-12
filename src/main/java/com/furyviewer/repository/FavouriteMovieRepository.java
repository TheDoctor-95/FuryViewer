package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.FavouriteMovie;
import com.furyviewer.domain.Movie;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Optional;
import com.furyviewer.domain.User;

/**
 * Spring Data JPA repository for the FavouriteMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavouriteMovieRepository extends JpaRepository<FavouriteMovie, Long> {

    @Query("select favourite_movie from FavouriteMovie favourite_movie where favourite_movie.user.login = ?#{principal.username}")
    List<FavouriteMovie> findByUserIsCurrentUser();

    Optional<FavouriteMovie> findByMovieAndUserLogin(Movie movie, String login);

    @Query("select count(favourite_movie) from FavouriteMovie favourite_movie where favourite_movie.movie.id=:MovieId")
    Long NumFavsMovie(@Param("MovieId") Long id);

    FavouriteMovie findByUserAndMovieId(User u, Long id);

    @Query("select m.liked from FavouriteMovie m where m.movie.id=:id")
    Boolean selectFavouriteMovie(@Param("id") Long id);

    @Query("select count(f.liked) from FavouriteMovie f where f.movie.id=:id")
    Long countLikedMovie(@Param("id") Long id);

}
