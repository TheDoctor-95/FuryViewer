package com.furyviewer.repository;

import com.furyviewer.domain.Movie;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.Social;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Social entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialRepository extends JpaRepository<Social, Long> {

    Optional<Social> findBySeriesAndType(Series series, String type);

    Optional<Social> findByMovieAndType(Movie movie, String type);

    @Query("SELECT s FROM Social s WHERE s.movie.id=:id AND s.type<>'Trailer'")
    List<Social> findMarksMovie(@Param("id") Long id);

    List<Social> getSocialByMovieId(String id);

    List<Social> getSocialBySeriesId(String id);

    @Query("SELECT s.url as urlTrailer FROM Social s WHERE s.series.id=:id AND s.type='Trailer'")
    String selectSeriesIdForTrailer(@Param("id") Long id);

    @Query("SELECT s.url as urlTrailer FROM Social s WHERE s.movie.id=:id AND s.type='Trailer'")
    String selectMoviesIdForTrailer(@Param("id") Long id);


}
