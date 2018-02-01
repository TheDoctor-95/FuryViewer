package com.furyviewer.service;

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
    private CompanyRepository companyRepository;

    public Set<Genre> importGenre(String genreList){
        String[] genres = genreList.split(", ");

        Set<Genre> genreListArray = new HashSet<>();
        for (String genreStr :
            genres) {
            Optional<Genre> genreOptional = genreRepository.findByName(genreStr);
            Genre genre;
            if (genreOptional.isPresent()){
                genre = genreOptional.get();
            }else{
                genre = new Genre();
                genre.setName(genreStr);

                genre = genreRepository.save(genre);

            }
            genreListArray.add(genre);
        }

        return genreListArray;
    }

}
