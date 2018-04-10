package com.furyviewer.repository;

import com.furyviewer.domain.ReviewSeries;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ReviewSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewSeriesRepository extends JpaRepository<ReviewSeries, Long> {

    @Query("select review_series from ReviewSeries review_series where review_series.user.login = ?#{principal.username}")
    List<ReviewSeries> findByUserIsCurrentUser();

    List<ReviewSeries> findBySeriesIdOrderByDateDesc(Long id);

}
