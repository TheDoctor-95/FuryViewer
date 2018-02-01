package com.furyviewer.service.util;

import com.furyviewer.domain.Genre;
import com.furyviewer.repository.CompanyRepository;
import com.furyviewer.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private NAEraserService naEraserService;

    /**
     * Método que se encarga de convertir un String en los objetos de la clase Genre necesarios.
     * @param genreList String | Contiene el nombre de los genre.
     * @return Set<Genre> | Set que contiene la información de los genres.
     */
    public Set<Genre> importGenre(String genreList){
        Set<Genre> genreListArray = new HashSet<>();

        if (naEraserService.eraserNA(genreList) != null) {
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
