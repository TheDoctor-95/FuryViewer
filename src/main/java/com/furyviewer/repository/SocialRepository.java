package com.furyviewer.repository;

import com.furyviewer.domain.Movie;
import com.furyviewer.domain.Series;
import com.furyviewer.domain.Social;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Social entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialRepository extends JpaRepository<Social, Long> {

    Optional<Social> findBySeriesAndType(Series series, String type);

    Optional<Social> findByMovieAndType(Movie movie, String type);

}
