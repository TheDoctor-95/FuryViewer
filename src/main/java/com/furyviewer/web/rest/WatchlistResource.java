package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.service.dto.util.MultimediaActorsDTO;
import com.furyviewer.service.util.WatchlistService;
import com.furyviewer.web.rest.util.HeaderUtil;
import com.furyviewer.web.rest.util.PaginationUtil;
import com.furyviewer.web.rest.vm.WatchlistLoadVM;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Watchlist.
 */
@RestController
@RequestMapping("/api/watchlist")
public class WatchlistResource {

    private final Logger log = LoggerFactory.getLogger(WatchlistResource.class);

    private final WatchlistService watchlistService;

    public WatchlistResource(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }


    @GetMapping("/{multimedia}/option/{option}/page/{page}")
    @Timed
    public List<MultimediaActorsDTO> multimedia(@PathVariable String multimedia, @PathVariable String option, @PathVariable Integer page) {

        return watchlistService.whatchlistMultimedia(multimedia, option, new PageRequest(page,15));
    }

}
