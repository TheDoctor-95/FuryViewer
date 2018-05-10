package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.Episode;
import com.furyviewer.domain.Series;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Episode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode>findEpisodeByName(String name);

    @Query("select e from Episode e where season.id=:id order by e.number asc")
    List<Episode>getEpisodeBySeason(@Param("id") Long id);

    List<Episode> findBySeasonIdOrderByReleaseDate(Long id);

    Episode findByNumberAndSeasonNumberAndSeasonSeriesId(int numberEpisode, int numberSeason, Long idSeries);


    @Query("select e from Episode e where :artist member of e.actors order by e.releaseDate desc")
    List<Episode> getEpisodeByActorsOrderByReleaseDate(@Param("artist") Artist artist);

    //@Query("select e from Episode e where :artist member of e.director order by e.releaseDate desc")
    //List<Episode> getEpisodeByDirectorOrderByReleaseDate(@Param("artist") Artist artist);

    //@Query("select e from Episode e where :artist member of e.scriptwriter order by e.releaseDate desc")
    //List<Episode> getEpisodeByScriptwriterOrderByReleaseDate(@Param("artist") Artist artist);

    @Query("select e from Episode e where e.director =:artist order by e.releaseDate desc")
    List<Episode> findEpisodeByDirectorOrderByReleaseDate(@Param("artist") Artist artist);

    @Query("select e from Episode e where e.scriptwriter =:artist order by e.releaseDate desc")
    List<Episode> findEpisodeByScriptwriterOrderByReleaseDate(@Param("artist") Artist artist);

    //@Query("select episode from Episode episode left join fetch episode.scriptwriter where episode.scriptwriter =:artist order by episode.releaseDate desc")
    //List<Series> findSeriesByScriptwriterOrderByReleaseDate(@Param("artist") Artist artist);

    List<Episode> findBySeasonSeriesIn(List<Series> seriesList);

    @Query("SELECT e FROM Episode e WHERE e.season.id=:id AND e.number=:numEpisode")
    Optional<Episode> getEpisode(@Param("id") Long id, @Param("numEpisode") Integer numEpisode);
}
