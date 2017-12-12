package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.FavouriteArtist;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the FavouriteArtist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavouriteArtistRepository extends JpaRepository<FavouriteArtist, Long> {

    @Query("select favourite_artist from FavouriteArtist favourite_artist where favourite_artist.user.login = ?#{principal.username}")
    List<FavouriteArtist> findByUserIsCurrentUser();

    Optional<FavouriteArtist> findByArtistAndUserLogin(Artist artist, String login);

}
