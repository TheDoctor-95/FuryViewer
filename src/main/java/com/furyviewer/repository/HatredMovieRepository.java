package com.furyviewer.repository;

import com.furyviewer.domain.HatredMovie;
import com.furyviewer.domain.Movie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HatredMovie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HatredMovieRepository extends JpaRepository<HatredMovie, Long> {

    @Query("select hatred_movie from HatredMovie hatred_movie where hatred_movie.user.login = ?#{principal.username}")
    List<HatredMovie> findByUserIsCurrentUser();

    Optional<HatredMovie> findByMovieAndUserLogin(Movie movie, String login);
}
