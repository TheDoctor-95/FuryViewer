package com.furyviewer.repository;

import com.furyviewer.domain.MovieStats;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;

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

}
