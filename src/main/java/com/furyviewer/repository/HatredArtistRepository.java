package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.HatredArtist;
import com.furyviewer.domain.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HatredArtist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HatredArtistRepository extends JpaRepository<HatredArtist, Long> {

    @Query("select hatred_artist from HatredArtist hatred_artist where hatred_artist.user.login = ?#{principal.username}")
    List<HatredArtist> findByUserIsCurrentUser();

    Optional<HatredArtist> findByArtistAndUserLogin(Artist artist, String login);

    @Query("select count(hatredArtist) from HatredArtist hatredArtist where hatredArtist.artist.id =:ArtistId")
    Long NumHatredArtist(@Param("ArtistId") Long id);

    @Query("select h.artist from HatredArtist h group by h.artist order by avg (h.hated) desc")
    List<Artist> topHatredArtist();

    HatredArtist findByUserAndArtistId(User u, Long id);

    //@Query("select count(h.hated) from HatredArtist h group by h.artist")
    //Long countHatredArtist(@Param("id") Long id);

    //HatredArtist countHatredArtistByArtistAndHated(Long id);
}
