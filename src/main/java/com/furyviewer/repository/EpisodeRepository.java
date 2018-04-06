package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.Episode;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the Episode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode>findEpisodeByName(String name);

    @Query("select e from Episode e where season.id=:id")
    List<Episode>getEpisodeBySeason(@Param("id") Long id);

    List<Episode> findBySeasonIdOrderByReleaseDate(Long id);

    Episode findByNumberAndSeasonNumberAndSeasonSeriesId(int numberEpisode, int numberSeason, Long idSeries);

}
