package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.FavouriteArtist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.furyviewer.domain.User;

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


    @Query("select count(favouriteArtist) from FavouriteArtist favouriteArtist where favouriteArtist.artist.id =:ArtistId")
    Long NumFavArtist(@Param("ArtistId") Long id);

//   @Query("select sum(favourite_artist) from FavouriteArtist favourite_artist left join fetch artist.name where artist.id=:id")
//    List<FavouriteArtist> NumFavArtist();

    FavouriteArtist findByUserAndArtistId(User u, Long id);

    @Query("select a.liked from FavouriteArtist a where a.artist.id=:id and a.user.login=:login ")
    Boolean selectFavouriteArtist(@Param("id") Long id, @Param("login") String login);

    @Query("SELECT COUNT(f.liked) FROM FavouriteArtist f WHERE f.liked=true AND f.artist.id=:id")
    Integer countLikedArtist(@Param("id") Long id);

    @Query("select f.artist from FavouriteArtist f where f.liked=true group by f.artist order by avg (f.liked) desc")
    List<Artist> topFavoriteArtis(Pageable pageable);

    @Query("SELECT COUNT(f) FROM FavouriteArtist f WHERE f.liked=true")
    Integer countTotalArtistFav();
}
