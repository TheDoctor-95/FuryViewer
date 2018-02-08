package com.furyviewer.repository;

import com.furyviewer.domain.Achievement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Achievement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

}
