package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.HatredArtist;
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

}
