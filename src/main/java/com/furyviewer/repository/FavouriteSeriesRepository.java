package com.furyviewer.repository;

import com.furyviewer.domain.FavouriteSeries;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Pageable;
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

    @Query("select s.liked from FavouriteSeries s where s.series.id=:id")
    Boolean selectFavouriteSeries(@Param("id") Long id);

    @Query("select count(f.liked) from FavouriteSeries f where f.liked=true and f.series.id=:id")
    Integer countLikedSeries(@Param("id") Long id);

    @Query("select s.liked from FavouriteSeries s where s.series.id=:id and s.user.login = :login")
    Boolean selectFavouriteSeriesAndUser(@Param("id") Long id, @Param("login") String login);

    @Query("select fs.series from FavouriteSeries fs where fs.liked=true and fs.user.login=:login")
    List<Series> findFavoriteSeriesUserLogin(@Param("login") String login, Pageable pageable);

    @Query("select count(fs.series) from FavouriteSeries fs where fs.liked=true and fs.user.login=:login")
    Integer countFavoriteSeriesUserLogin(@Param("login") String login);

}
