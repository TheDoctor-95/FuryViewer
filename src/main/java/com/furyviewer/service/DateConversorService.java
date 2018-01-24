package com.furyviewer.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class DateConversorService {
    public LocalDate releseDateOMDB(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

        LocalDate localDate = LocalDate.parse(date, formatter);

        return localDate;
    }

    public LocalDate releaseDateOMDBSeason(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

        LocalDate localDate = LocalDate.parse(date, formatter);

        return localDate;
    }
}
