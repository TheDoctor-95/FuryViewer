package com.furyviewer.service.util;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servicio que se encarga de controlar que los Stings devueltos por las apis son correctos.
 * @author IFriedkin
 */
@Service
public class StringApiCorrectorService {
    /**
     * Metodo que se encarga de devolver un string en null en caso de que la api de OMDB incluya N/A como informacion.
     * @param dataI String | String el cual se debe comprobar su contenido.
     * @return String | El valor entrante o null.
     */
    public String eraserNA (String dataI) {
        String dataO = null;

        if(!dataI.equalsIgnoreCase("N/A")) {
            return dataI;
        }

        return dataO;
    }

    /**
     * Devuelve un String sin los caracteres que no sean UTF8.
     * @param dataI String | String el cual se debe comprobar su contenido.
     * @return String | String sin caracteres fuera de UTF8.
     */
    public String eraserEvilBytes (String dataI) {
        String dataO = null;

        System.out.println("Eliminamos bytes malignos.");

        try {
            byte[] utf8Bytes = dataI.getBytes("UTF-8");
            dataO = new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Pattern unicodeOutliers = Pattern.compile("[^\\x00-\\x7F]",
            Pattern.UNICODE_CASE | Pattern.CANON_EQ
                | Pattern.CASE_INSENSITIVE);
        Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(dataO);

        dataO = unicodeOutlierMatcher.replaceAll(" ");

        System.out.println("Bytes malignos eliminados.");

        return dataO;
    }
}
