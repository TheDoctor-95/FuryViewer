package com.furyviewer.repository;

import com.furyviewer.domain.FavouriteSeries;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.User;
import org.springframework.data.repository.query.Param;
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

    @Query("select count(favouriteSeries) from FavouriteSeries favouriteSeries where favouriteSeries.series.id=:SeriesId")
    Long NumFavsSerie(@Param("SeriesId") Long id);

    FavouriteSeries findByUserAndSeriesId(User u, Long id);
}
