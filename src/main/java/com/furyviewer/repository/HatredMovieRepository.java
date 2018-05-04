package com.furyviewer.repository;

import com.furyviewer.domain.HatredMovie;
import com.furyviewer.domain.Movie;
import com.furyviewer.domain.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HatredMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HatredMovieRepository extends JpaRepository<HatredMovie, Long> {

    @Query("select hatred_movie from HatredMovie hatred_movie where hatred_movie.user.login = ?#{principal.username}")
    List<HatredMovie> findByUserIsCurrentUser();

    Optional<HatredMovie> findByMovieAndUserLogin(Movie movie, String login);

    @Query("select count(hatred_movie) from HatredMovie hatred_movie where hatred_movie.id=:MovieId")
    Long HatredMovieT(@Param("MovieId") Long id);

    @Query("select h.movie from HatredMovie h group by h.movie order by avg (h.hated) desc")
    List<Movie> topHatredMovies();

    @Query("select h.hated from HatredMovie h where h.user=:User and h.movie.id = :id ")
    Integer markHatredMovie(@Param("User") User u, @Param("id") Long id);

    HatredMovie findByUserAndMovieId(User u, Long id);

    @Query("SELECT COUNT(h.hated) FROM HatredMovie h WHERE h.hated=true AND h.movie.id=:id")
    Integer countHatredMovie(@Param("id") Long id);

    @Query("SELECT h.movie FROM HatredMovie h WHERE h.hated=true AND h.user.login=:login")
    List<Movie> findHatedMovieUser(@Param("login") String login);
}
