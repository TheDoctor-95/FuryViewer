package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.ArtistType;
import com.furyviewer.domain.enumeration.ArtistTypeEnum;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * Spring Data JPA repository for the ArtistType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistTypeRepository extends JpaRepository<ArtistType, Long> {
    ArtistType findByName(ArtistTypeEnum ate);


    // @Query("select artist from Artist artist where ArtistType.name= :name")
    // List<Artist> findArtistByType(@Param("name") String name);

}
