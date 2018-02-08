package com.furyviewer.service.util;

import com.furyviewer.domain.Company;
import com.furyviewer.repository.CompanyRepository;
import com.furyviewer.service.TheMovieDB.CompanyTmdbDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NAEraserService naEraserService;

    @Autowired
    private CompanyTmdbDTOService companyTmdbDTOService;

    /**
     * Método que se encarga de devolver una Company, ya sea encontrando una en la DB o creando una nueva con el
     * parámetro que le llega.
     * @param name String | Nombre de la company.
     * @return Company | Objeto que contiene la información de la company.
     */
    public Company importCompany(String name) {
        Company c = null;

        if (naEraserService.eraserNA(name) != null) {
            Optional<Company> optionalCompany = companyRepository.findCompanyByName(name);

            if (optionalCompany.isPresent()) {
                c = optionalCompany.get();
            } else {
                c = companyTmdbDTOService.importCompany(name);
            }
        }

        return c;
    }
}
