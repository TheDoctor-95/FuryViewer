package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Multimedia;
import com.furyviewer.service.OpenMovieDatabase.Service.SearchOmdbDTOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MultiImportResource {
    private final Logger log = LoggerFactory.getLogger(SeriesResource.class);

    private final SearchOmdbDTOService searchOmdbDTOService;

    public MultiImportResource(SearchOmdbDTOService searchOmdbDTOService) {
        this.searchOmdbDTOService = searchOmdbDTOService;
    }

    @GetMapping("/multiImportByTitle/{name}")
    @Timed
    public Multimedia multiimport(@PathVariable String name) {
        log.debug("REST request to get Series by name", name);
        return searchOmdbDTOService.multiImport(name);
    }
}
