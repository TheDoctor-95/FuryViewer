package com.furyviewer.repository;

import com.furyviewer.domain.Movie;
import com.furyviewer.domain.RateMovie;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the RateMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateMovieRepository extends JpaRepository<RateMovie, Long> {

    @Query("select rate_movie from RateMovie rate_movie where rate_movie.user.login = ?#{principal.username}")
    List<RateMovie> findByUserIsCurrentUser();

    Optional<RateMovie> findByMovieAndUserLogin(Movie movie, String login);

    @Query("select avg(rateMovie.rate) from RateMovie rateMovie where rateMovie.id=:MovieId")
    Double RateMovieMedia(@Param("MovieId")Long id);

}
