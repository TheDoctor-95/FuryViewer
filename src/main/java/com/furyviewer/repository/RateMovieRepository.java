package com.furyviewer.repository;

import com.furyviewer.domain.Movie;
import com.furyviewer.domain.RateMovie;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the RateMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateMovieRepository extends JpaRepository<RateMovie, Long> {

    @Query("select rate_movie from RateMovie rate_movie where rate_movie.user.login = ?#{principal.username}")
    List<RateMovie> findByUserIsCurrentUser();

    @Query("select rateMovie from RateMovie rateMovie where rateMovie.movie.name=:name")
    List<RateMovie> getRateMovie(@Param("name")String name);

}
