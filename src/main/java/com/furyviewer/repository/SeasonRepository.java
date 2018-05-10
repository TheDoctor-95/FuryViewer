package com.furyviewer.repository;


import com.furyviewer.domain.Season;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Season entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    @Query("SELECT s.id FROM Season s WHERE s.series.id = :id ORDER BY s.number")
    List<Long> findSeasons(@Param("id") Long id);

    @Query("SELECT COUNT(e) FROM Episode e WHERE e.season.id=:id")
    Integer countEpisodesForSeason(@Param("id") Long id);

    @Query("SELECT s FROM Season s WHERE s.series.id=:id AND s.number=:numSeason")
    Optional<Season> getSeason(@Param("id") Long id, @Param("numSeason") Integer numSeason);
}
