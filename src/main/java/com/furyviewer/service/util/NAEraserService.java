package com.furyviewer.service.util;

import org.springframework.stereotype.Service;

/**
 * Servicio que se encarga de eliminar el "N/A" de las respuestas de la api de OpenMovieDataBase.
 * @author IFriedkin
 */
@Service
public class NAEraserService {
    /**
     * Metodo que se encarga de devolver un string en null en caso de que la api de OMDB incluya N/A como informacion.
     * @param dataI String | String el cual se debe comprobar su contenido
     * @return String | El valor entrante o null.
     */
    public String eraserNA (String dataI) {
        String dataO = null;

        if(!dataI.equalsIgnoreCase("N/A")) {
            return dataI;
        }

        return dataO;
    }
}
