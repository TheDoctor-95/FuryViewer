package com.furyviewer.repository;

import com.furyviewer.domain.RateSeries;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the RateSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RateSeriesRepository extends JpaRepository<RateSeries, Long> {

    @Query("select rate_series from RateSeries rate_series where rate_series.user.login = ?#{principal.username}")
    List<RateSeries> findByUserIsCurrentUser();

}
