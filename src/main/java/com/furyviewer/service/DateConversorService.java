package com.furyviewer.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class DateConversorService {
    public LocalDate releseDateOMDB(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

        LocalDate localDate = localDate = LocalDate.parse(date, formatter);


        return localDate;
    }

    public LocalDate releaseDateOMDBSeason(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

        LocalDate localDate = localDate = LocalDate.parse(date, formatter);


        return localDate;
    }
}
/*
    @GetMapping("/importSeriesByName/{name}")
    @Timed
    @Transactional
    public ResponseEntity<Series> importSeriesByName(@PathVariable String name) {
        log.debug("REST request to get Series by name", name);
        Series movie = seriesOmdbDTOService.importSeries(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(movie));
    }
* */
