package com.furyviewer.repository;

import com.furyviewer.domain.Genre;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Genre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findGenreByName(String name);

    Optional<Genre> findByName(String name);



}
