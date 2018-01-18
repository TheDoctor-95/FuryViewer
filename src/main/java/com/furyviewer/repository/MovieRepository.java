package com.furyviewer.repository;

import com.furyviewer.domain.Movie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Movie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select distinct movie from Movie movie left join fetch movie.genres left join fetch movie.actorMains left join fetch movie.actorSecondaries")
    List<Movie> findAllWithEagerRelationships();

    @Query("select movie from Movie movie left join fetch movie.genres left join fetch movie.actorMains left join fetch movie.actorSecondaries where movie.id =:id")
    Movie findOneWithEagerRelationships(@Param("id") Long id);

    List<Movie>findMovieByName(String name);

    Optional<Movie> findByName(String name);

}
