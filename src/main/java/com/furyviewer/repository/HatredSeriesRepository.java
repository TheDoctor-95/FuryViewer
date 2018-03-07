package com.furyviewer.repository;

import com.furyviewer.domain.HatredSeries;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.User;
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
    Long NumHatredSeries(@Param("SeriesId")Long id);

    /*
    @Query("select h.series from HatredSeries  group by h.series order by avg (h.rate) desc ")
    List<Series> topHatredSeries();

    @Query("select h.rate from HatredSeries  where h.user=:User and h.movie.id = :id ")
    Integer markHatredSeries(@Param("User") User u, @Param("id") Long id);
    */
}
