package com.furyviewer.repository;

import com.furyviewer.domain.ChapterSeen;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the ChapterSeen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChapterSeenRepository extends JpaRepository<ChapterSeen, Long> {

    @Query("select chapter_seen from ChapterSeen chapter_seen where chapter_seen.user.login = ?#{principal.username}")
    List<ChapterSeen> findByUserIsCurrentUser();

    List<ChapterSeen> findBySeenAndEpisodeIdAndUserLogin(boolean seen, Long episodeId, String userLogin);

    List<ChapterSeen> findBySeenAndEpisodeSeasonSeriesIdAndUserLogin(boolean seen, Long seriesId, String userLogin);

    List<ChapterSeen> findBySeenAndUserLogin(boolean seen, String userLogin);

    @Query("SELECT cs.seen FROM ChapterSeen cs WHERE cs.user.login=:login AND cs.episode.id= :id ")
    boolean isSeen(@Param("login") String login, @Param("id") Long id);

}
