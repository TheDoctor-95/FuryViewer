package com.furyviewer.service;

import com.furyviewer.domain.Country;
import com.furyviewer.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    CountryRepository countryRepository;

    /**
     * Método que se encarga de buscar en la base de datos una Country y en caso de no existir la crea.
     * @param cy String | Nombre de la country devuelto por la api.
     * @return Country | Country creado o encontrado en la base de datos.
     */
    public Country importCountry(String cy) {
        //Buscamos country
        Optional<Country> c = countryRepository.findByName(cy);

        Country country = new Country();

        if(c.isPresent()){
            country = c.get();
        }
        else{
            country.setName(cy);
            countryRepository.save(country);
        }

        return country;
    }
}
