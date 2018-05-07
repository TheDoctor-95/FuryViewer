package com.furyviewer.service.util;

import com.furyviewer.domain.Country;
import com.furyviewer.repository.CountryRepository;
import com.furyviewer.service.GoogleMaps.Service.GoogleMapsDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que se encarga de devolver un Country de la base de datos o en caso de no existir delega en
 * GoogleMapsDTOService para crearlo.
 * @author IFriedkin
 * @see com.furyviewer.service.GoogleMaps.Service.GoogleMapsDTOService
 */
@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private GoogleMapsDTOService googleMapsDTOService;

    @Autowired
    private StringApiCorrectorService stringApiCorrectorService;

    /**
     * Metodo que se encarga de buscar en la base de datos una Country y en caso de no existir la crea.
     * @param countryName String | Nombre de la country devuelto por la api.
     * @return Country | Country creado o encontrado en la base de datos.
     */
    public Country importCountry(String countryName) {
        Country country = null;

        if (stringApiCorrectorService.eraserNA(countryName) != null) {
            //Buscamos countryName
            List<Country> countries = countryRepository.findCountryByName(googleMapsDTOService.getName(countryName));

            if (!countries.isEmpty()) {
                country = countries.get(0);
            } else {
                country = googleMapsDTOService.importCountry(countryName);
            }
        }
        return country;
    }
}
