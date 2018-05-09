package com.furyviewer.repository;

import com.furyviewer.domain.HatredSeries;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HatredSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HatredSeriesRepository extends JpaRepository<HatredSeries, Long> {

    @Query("select hatred_series from HatredSeries hatred_series where hatred_series.user.login = ?#{principal.username}")
    List<HatredSeries> findByUserIsCurrentUser();

    Optional<HatredSeries> findBySeriesAndUserLogin(Series series, String login);

    @Query("select count(hatredSeries) from HatredSeries hatredSeries where hatredSeries.id=:SeriesId")
    Long NumHatredSeries(@Param("SeriesId") Long id);

    @Query("select h.series from HatredSeries h group by h.series order by avg (h.hated) desc ")
    List<Series> topHatredSeries();

    HatredSeries findByUserAndSeriesId(User u, Long id);

    @Query("select count(h.hated) from HatredSeries h where h.hated=true and h.series.id=:id")
    Integer countHatredSeries(@Param("id") Long id);

    @Query("select h.hated from HatredSeries h where h.series.id = :id AND h.user.login = :login")
    Boolean hateSeriesIdAndUserLogin(@Param("id") Long id, @Param("login") String login);

    @Query("SELECT h.series FROM HatredSeries h WHERE h.hated=true AND h.user.login=:login")
    List<Series> findHatedSeriesUser(@Param("login") String login, Pageable pageable);

}
