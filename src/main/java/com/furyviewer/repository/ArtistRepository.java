package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.ArtistType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Artist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {
    @Query("select distinct artist from Artist artist left join fetch artist.artistTypes")
    List<Artist> findAllWithEagerRelationships();

    @Query("select artist from Artist artist left join fetch artist.artistTypes where artist.id =:id")
    Artist findOneWithEagerRelationships(@Param("id") Long id);

    List<Artist>findArtistByName(String name);

    Optional<Artist> findByName(String name);
//    @Query("select artist from Artist artist where artist.artistTypes.name=com.furyviewer.domain.enumeration.ArtistTypeEnum.DIRECTOR")
//    List<Artist> findArtistDirector();

 //   List<Artist>findArtistByArtistTypes(String name);

    //@Query("select artist from Artist artist left join fetch artist.artistTypes where artist.artistTypes.name =:name")
  //  Artist findArtistByArtistTypes(@Param("name") String name);

    @Query("select artist from Artist artist where :artistType member of artist.artistTypes")
    List<Artist> findArtistByArtistType(@Param("artistType") ArtistType artistType);

    @Query("select artist.imgUrl from Artist artist where artist.id =:id")
    String returnImageArtist(@Param("id") Long id);

}

