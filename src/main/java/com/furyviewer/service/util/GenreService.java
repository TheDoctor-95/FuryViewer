package com.furyviewer.service.util;

import com.furyviewer.domain.Genre;
import com.furyviewer.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Servicio que se encarga de devolver un Genre de la base de datos o en caso de no existir lo crea a partir del nombre.
 * @author TheDoctor-95
 */
@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private StringApiCorrector stringApiCorrector;

    /**
     * Metodo que se encarga de convertir un String en los objetos de la clase Genre necesarios.
     * @param genreList String | Contiene el nombre de los genre.
     * @return Set | Set que contiene la informacion de los genres.
     */
    public Set<Genre> importGenre(String genreList){
        Set<Genre> genreListArray = new HashSet<>();

        if (stringApiCorrector.eraserNA(genreList) != null) {
            String[] genres = genreList.split(", ");

            for (String genreStr : genres) {
                Optional<Genre> genreOptional = genreRepository.findByName(genreStr);
                Genre genre;

                if (genreOptional.isPresent()) {
                    genre = genreOptional.get();
                } else {
                    genre = new Genre();
                    genre.setName(genreStr);

                    genre = genreRepository.save(genre);
                }
                genreListArray.add(genre);
            }
        }
        return genreListArray;
    }
}
