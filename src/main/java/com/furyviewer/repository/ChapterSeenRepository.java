package com.furyviewer.repository;

import com.furyviewer.domain.ChapterSeen;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ChapterSeen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChapterSeenRepository extends JpaRepository<ChapterSeen, Long> {

    @Query("select chapter_seen from ChapterSeen chapter_seen where chapter_seen.user.login = ?#{principal.username}")
    List<ChapterSeen> findByUserIsCurrentUser();

}
