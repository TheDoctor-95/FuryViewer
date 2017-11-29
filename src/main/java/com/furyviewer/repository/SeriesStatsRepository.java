package com.furyviewer.repository;

import com.furyviewer.domain.SeriesStats;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the SeriesStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeriesStatsRepository extends JpaRepository<SeriesStats, Long> {

    @Query("select series_stats from SeriesStats series_stats where series_stats.user.login = ?#{principal.username}")
    List<SeriesStats> findByUserIsCurrentUser();

}
