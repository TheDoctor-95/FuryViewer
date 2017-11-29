package com.furyviewer.repository;

import com.furyviewer.domain.ArtistType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ArtistType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistTypeRepository extends JpaRepository<ArtistType, Long> {

}
