package com.furyviewer.repository;

import com.furyviewer.domain.Series;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Series entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    @Query("select distinct series from Series series left join fetch series.genres")
    List<Series> findAllWithEagerRelationships();

    @Query("select series from Series series left join fetch series.genres where series.id =:id")
    Series findOneWithEagerRelationships(@Param("id") Long id);

    List<Series>findSeriesByName(String name);

    Optional<Series> findByName(String name);

}
