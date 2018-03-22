package com.furyviewer.repository;


import com.furyviewer.domain.Season;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Season entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    List<Season> findSeasonsBySeriesId(@Param("id") Long id);

}
