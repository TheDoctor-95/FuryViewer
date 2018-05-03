package com.furyviewer.repository;

import com.furyviewer.domain.Movie;
import com.furyviewer.domain.MovieStats;
import com.furyviewer.domain.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the MovieStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieStatsRepository extends JpaRepository<MovieStats, Long> {

    @Query("select movie_stats from MovieStats movie_stats where movie_stats.user.login = ?#{principal.username}")
    List<MovieStats> findByUserIsCurrentUser();

    @Query("select count(movieStats) from MovieStats movieStats where movieStats.status = com.furyviewer.domain.enumeration.MovieStatsEnum.PENDING and movieStats.id=:MovieId")
    Long PendingMovieStats(@Param("MovieId")Long id);

    @Query("select count(movieStats) from MovieStats movieStats where movieStats.status = com.furyviewer.domain.enumeration.MovieStatsEnum.SEEN and movieStats.id=:MovieId")
    Long SeenMovieStats(@Param("MovieId")Long id);

    /**
     * Devuelve todas peliculas pendientes de un usuario
     * @param user User | Usuario con pelicula pendiente
     * @return Listado de peliculas pendientes s
     */
    @Query("select movieStats.movie from MovieStats movieStats where movieStats.status= com.furyviewer.domain.enumeration.MovieStatsEnum.PENDING and movieStats.user=:User")
    List<Movie> pendingMovies(@Param("User")User user);

    Optional<MovieStats> findByUserAndMovieId(User user, Long id);

    @Query("select m.status from MovieStats m where m.movie.id=:id and m.user.login= :login")
    String selectMovieStatus(@Param("id") Long id, @Param("login") String login);


}
