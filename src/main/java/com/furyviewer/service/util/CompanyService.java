package com.furyviewer.service.util;

import com.furyviewer.domain.Company;
import com.furyviewer.repository.CompanyRepository;
import com.furyviewer.service.TheMovieDB.Service.CompanyTmdbDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que se encarga de devolver una Company de la base de datos o en caso de no existir delega en
 * CompanyTmdbDTOService para crearla.
 * @author IFriedkin
 * @author TheDoctor-95
 * @see com.furyviewer.service.TheMovieDB.Service.CompanyTmdbDTOService
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StringApiCorrectorService stringApiCorrectorService;

    @Autowired
    private CompanyTmdbDTOService companyTmdbDTOService;

    /**
     * Devuelve una Company, ya sea encontrando una en la base de datos o creando una nueva con el parametro
     * que le llega.
     * @param name String | Nombre de la company.
     * @return Company | Objeto que contiene la informacion de la company.
     */
    public Company importCompany(String name) {
        Company c = null;

        if (stringApiCorrectorService.eraserNA(name) != null) {
            List<Company> optionalCompany = companyRepository.findCompanyByName(name);

            if (!optionalCompany.isEmpty()) {
                c = optionalCompany.get(0);
            } else {
                c = companyTmdbDTOService.importCompany(name);
            }
        }

        return c;
    }
}
