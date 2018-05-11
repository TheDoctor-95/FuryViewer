package com.furyviewer.repository;

import com.furyviewer.domain.Artist;
import com.furyviewer.domain.Genre;
import com.furyviewer.domain.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Movie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> , JpaSpecificationExecutor<Movie>{
    @Query("select distinct movie from Movie movie left join fetch movie.genres left join fetch movie.actorMains left join fetch movie.actorSecondaries")
    List<Movie> findAllWithEagerRelationships();

    @Query("select movie from Movie movie left join fetch movie.genres left join fetch movie.actorMains left join fetch movie.actorSecondaries where movie.id =:id")
    Movie findOneWithEagerRelationships(@Param("id") Long id);

    List<Movie>findMovieByName(String name);

    Optional<Movie> findByName(String name);

    Optional<Movie> findMovieByImdbIdExternalApi(String ImdbId);

    @Query("select m from Movie m where :artist member of m.actorMains order by m.releaseDate desc")
    List<Movie> getByArtistOrderbyDate(@Param("artist") Artist artist);

    //@Query("select m from Movie m where :artist member of m.director order by m.releaseDate desc")
    //List<Movie> getByDirectorOrderbyDate(@Param("artist") Artist artist);

    @Query("select movie from Movie movie left join fetch movie.director where movie.director =:artist order by movie.releaseDate desc")
    List<Movie> findMovieByDirectorOrderByReleaseDate(@Param("artist") Artist artist);

    @Query("select movie from Movie movie left join fetch movie.scriptwriter where movie.scriptwriter =:artist order by movie.releaseDate desc")
    List<Movie> findMovieByScriptwriterOrderByReleaseDate(@Param("artist") Artist artist);


    @Query("select m from Movie m where :genre member of m.genres")
    List<Movie>getMoviesByGenre(@Param("genre")Genre genre);



    @Query("select distinct movie from Movie movie left join fetch movie.genres left join fetch movie.actorMains where movie.id =:id")
    List<Movie> findOneWithEagerRelationshipsLimit(@Param("id") Long id, Pageable pageable);

    @Query("select m.actorMains from Movie m where m.id=:id")
    List<Artist> getMainActorsByMovie(@Param("id")Long id);

    @Query("select m.actorMains from Movie m where m.id=:id")
    List<Artist> getMainActorsByMovieLimit(@Param("id")Long id, Pageable pageable);




}
