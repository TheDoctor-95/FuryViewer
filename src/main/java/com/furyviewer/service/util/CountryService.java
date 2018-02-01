package com.furyviewer.service.util;

import com.furyviewer.domain.Country;
import com.furyviewer.repository.CountryRepository;
import com.furyviewer.service.GoogleMaps.GoogleMapsDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    CountryRepository countryRepository;

    @Autowired
    GoogleMapsDTOService googleMapsDTOService;

    /**
     * Método que se encarga de buscar en la base de datos una Country y en caso de no existir la crea.
     * @param countryName String | Nombre de la country devuelto por la api.
     * @return Country | Country creado o encontrado en la base de datos.
     */
    public Country importCountry(String countryName) {
        String[] countryNames = countryName.split(", ");
        countryName = countryNames[0];
        //Buscamos countryName
        Optional<Country> c = countryRepository.findByName(countryName);

        Country country = new Country();

        if(c.isPresent()){
            country = c.get();
        }
        else{
            country.setName(countryName);
            country.setLatitude(googleMapsDTOService.getLatitude(countryName));
            country.setLongitude(googleMapsDTOService.getLongitude(countryName));
            countryRepository.save(country);
        }

        return country;
    }
}
