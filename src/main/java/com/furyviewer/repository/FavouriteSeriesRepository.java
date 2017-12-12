package com.furyviewer.repository;

import com.furyviewer.domain.FavouriteSeries;
import com.furyviewer.domain.Series;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the FavouriteSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavouriteSeriesRepository extends JpaRepository<FavouriteSeries, Long> {

    @Query("select favourite_series from FavouriteSeries favourite_series where favourite_series.user.login = ?#{principal.username}")
    List<FavouriteSeries> findByUserIsCurrentUser();


    Optional<FavouriteSeries> findBySeriesAndUserLogin(Series series, String login);

}
