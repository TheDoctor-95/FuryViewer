package com.furyviewer.repository;

import com.furyviewer.domain.MovieStats;
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

}
