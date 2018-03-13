package com.furyviewer.repository;

import com.furyviewer.domain.ReviewMovie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ReviewMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewMovieRepository extends JpaRepository<ReviewMovie, Long> {

    @Query("select review_movie from ReviewMovie review_movie where review_movie.user.login = ?#{principal.username}")
    List<ReviewMovie> findByUserIsCurrentUser();

    List<ReviewMovie> findByMovieId(Long id);

}
