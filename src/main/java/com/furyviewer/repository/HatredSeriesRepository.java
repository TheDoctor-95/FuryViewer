package com.furyviewer.repository;

import com.furyviewer.domain.HatredSeries;
import com.furyviewer.domain.Series;
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

}
