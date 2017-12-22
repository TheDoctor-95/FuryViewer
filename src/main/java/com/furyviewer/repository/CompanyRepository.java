package com.furyviewer.repository;

import com.furyviewer.domain.Company;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company>findCompanyByName(String name);

}
