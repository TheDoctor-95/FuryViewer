package com.furyviewer.repository;

import com.furyviewer.domain.Series;
import com.furyviewer.domain.SeriesStats;
import org.springframework.data.repository.query.Param;
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


    @Query("select count(seriesStats) from SeriesStats seriesStats where seriesStats.status = com.furyviewer.domain.enumeration.SeriesStatsEnum.FOLLOWING and seriesStats.id=:SeriesId")
    Long FollowingSeriesStats(@Param("SeriesId")Long id);

    @Query("select count(seriesStats) from SeriesStats seriesStats  where seriesStats.status= com.furyviewer.domain.enumeration.SeriesStatsEnum.PENDING and seriesStats.id=:SeriesId")
    Long PendingSeriesStats(@Param("SeriesId")Long id);

    @Query("select count(seriesStats) from SeriesStats seriesStats  where seriesStats.status= com.furyviewer.domain.enumeration.SeriesStatsEnum.SEEN and seriesStats.id=:SeriesId")
    Long SeenSeriesStats(@Param("SeriesId")Long id);

    @Query("select s.status from SeriesStats s where s.serie.id=:id")
    String selectSeriesStatus(@Param("id") Long id);

    @Query("select ss.serie from SeriesStats ss where ss.status=com.furyviewer.domain.enumeration.SeriesStatsEnum.FOLLOWING and ss.user.login=:userLogin")
    List<Series>  followingSeriesUser(@Param("userLogin") String userLogin);
}
