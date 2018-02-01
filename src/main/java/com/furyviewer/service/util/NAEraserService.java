package com.furyviewer.service.util;

import org.springframework.stereotype.Service;

@Service
public class NAEraserService {
    /**
     * Método que se encarga de devolver un string en null en caso de que la api de OMDB incluya N/A como información.
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
