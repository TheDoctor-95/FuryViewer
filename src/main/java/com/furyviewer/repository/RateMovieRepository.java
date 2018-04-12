package com.furyviewer.repository;

import com.furyviewer.domain.Genre;
import com.furyviewer.domain.Movie;
import com.furyviewer.domain.RateMovie;
import com.furyviewer.domain.User;
import com.furyviewer.service.dto.RateMovieStats;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.Date;
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

    @Query("select rateMovie from RateMovie rateMovie where rateMovie.movie.name=:name")
    List<RateMovie> getRateMovie(@Param("name")String name);

    Optional<RateMovie> findByMovieAndUserLogin(Movie movie, String login);

    @Query("select avg(rateMovie.rate) from RateMovie rateMovie where rateMovie.movie.id=:MovieId")
    Double RateMovieMedia(@Param("MovieId")Long id);

    @Query("select r.movie from RateMovie r group by r.movie order by avg (r.rate) desc")
    List<Movie> topPelis();

    @Query("select r.rate from RateMovie r where r.user=:User and r.movie.id = :id ")
    Integer markPeli(@Param("User") User u, @Param("id") Long id);

    RateMovie findByUserAndMovieId(User u, Long id);

    @Query("select new com.furyviewer.service.dto.RateMovieStats (r.movie, avg(r.rate)) " +
        " from RateMovie r where :genre member of r.movie.genres group by r.movie " +
        " order by avg(r.rate) desc")
    List<RateMovieStats> getMovieStats(@Param("genre")Genre genre, Pageable pageable);

}
