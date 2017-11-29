package com.furyviewer.repository;

import com.furyviewer.domain.HatredSeries;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the HatredSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HatredSeriesRepository extends JpaRepository<HatredSeries, Long> {

    @Query("select hatred_series from HatredSeries hatred_series where hatred_series.user.login = ?#{principal.username}")
    List<HatredSeries> findByUserIsCurrentUser();

}
