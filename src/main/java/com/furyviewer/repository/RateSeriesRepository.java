package com.furyviewer.repository;

import com.furyviewer.domain.RateMovie;
import com.furyviewer.domain.RateSeries;
import org.hibernate.NonUniqueResultException;
import org.springframework.data.repository.query.Param;
import com.furyviewer.domain.Series;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the RateSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateSeriesRepository extends JpaRepository<RateSeries, Long> {

    @Query("select rate_series from RateSeries rate_series where rate_series.user.login = ?#{principal.username}")
    List<RateSeries> findByUserIsCurrentUser();

    @Query("select rateSeries from RateSeries rateSeries where rateSeries.series.name=:name")
    List<RateSeries> getRateSeries(@Param("name")String name);
    Optional<RateSeries> findBySeriesAndUserLogin(Series series, String login);

    @Query("select avg(rateSeries) from RateSeries rateSeries where rateSeries.id=:SeriesId")
    Double RateSeriesMedia(@Param("SeriesId")Long id);

}
