package com.furyviewer.repository;

import com.furyviewer.domain.Country;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Country entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findCountryByName(String name);

    Optional<Country> findByName(String name);
}
