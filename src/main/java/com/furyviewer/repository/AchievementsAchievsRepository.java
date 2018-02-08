package com.furyviewer.repository;

import com.furyviewer.domain.AchievementsAchievs;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the AchievementsAchievs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AchievementsAchievsRepository extends JpaRepository<AchievementsAchievs, Long> {

    @Query("select achievements_achievs from AchievementsAchievs achievements_achievs where achievements_achievs.user.login = ?#{principal.username}")
    List<AchievementsAchievs> findByUserIsCurrentUser();

}
