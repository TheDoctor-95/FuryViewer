package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Multimedia;
import com.furyviewer.service.OpenMovieDatabase.Service.SearchOmdbDTOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Resource encargado de la gestión de los imports en asincrono.
 * @author IFriedkin
 */
@RestController
@RequestMapping("/api")
public class MultiImportResource {
    private final Logger log = LoggerFactory.getLogger(MultiImportResource.class);

    private final SearchOmdbDTOService searchOmdbDTOService;

    public MultiImportResource(SearchOmdbDTOService searchOmdbDTOService) {
        this.searchOmdbDTOService = searchOmdbDTOService;
    }

    /**
     * Devuelve la primera movie o series encontrada en la peticion a la api, dejando el resto de movies o series
     * importandose en asincrono.
     * @param name String | Titulo a buscar.
     * @return Movie | Series | La primera movie o series de la lista.
     */
    @GetMapping("/multiImportByTitle/{name}")
    @Timed
    public Multimedia multiimport(@PathVariable String name) {
        log.debug("REST request to get Series by name", name);
        return searchOmdbDTOService.multiImport(name);
    }
}
