package com.furyviewer.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Servicio que se encarga de convertir los strings que contienen fechas en un formato valido para introducirlo en la
 * base de datos.
 * @author IFriedkin
 * @author TheDoctor-95
 */
@Service
public class DateConversorService {
    @Autowired
    private StringApiCorrector stringApiCorrector;

    /**
     * Metodo que se encarga de convertir el String con formato (dia-mes-anyo) al formato adecuado de LocalDate para la
     * base de datos.
     * @param date String | Contiene la fecha que se debe guardar en la base de datos.
     * @return LocalDate | Devuelve la fecha con el formato adecuado.
     */
    public LocalDate releseDateOMDB(String date){
        LocalDate localDate = null;

        if(stringApiCorrector.eraserNA(date) != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

            localDate = LocalDate.parse(date, formatter);
        }

        return localDate;
    }

    /**
     * Metodo que se encarga de convertir el String con formato (anyo-mes-dia) al formato adecuado de LocalDate para la
     * base de datos.
     * @param date String | Contiene la fecha que se debe guardar en la base de datos.
     * @return LocalDate | Devuelve la fecha con el formato adecuado.
     */
    public LocalDate releaseDateOMDBSeason(String date){
        LocalDate localDate = null;

        if(stringApiCorrector.eraserNA(date) != null) {
            date = reconstructionDate(date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

            localDate = LocalDate.parse(date, formatter);
        }

        return localDate;
    }

    /**
     * Agrega los numeros faltantes a una fecha.
     * @param dateInp String | Fecha entrante que puede estar incompleta.
     * @return String | Fecha completa.
     */
    public String reconstructionDate(String dateInp) {
        String dateOuT = dateInp;

        if(dateInp.split("-").length == 2) {
            dateOuT = dateInp + "-07";
        } else if (dateInp.split("-").length == 1) {
            dateOuT = dateInp + "-04-02";
        }

        return dateOuT;
    }
}
