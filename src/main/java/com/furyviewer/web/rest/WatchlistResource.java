package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.web.rest.util.HeaderUtil;
import com.furyviewer.web.rest.util.PaginationUtil;
import com.furyviewer.web.rest.vm.WatchlistLoadVM;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    /**
     * GET  /watchlist : get watchlist.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the watchlistLoadVM, or with status 404 (Not Found)
     */

    public ResponseEntity<List<WatchlistLoadVM>> getAllWatchlists(@ApiParam Pageable pageable,@RequestParam(required = false) String filter){
        log.debug("REST request to get a page of Watchlists");
        //TODO call repository with pageable and page.getContent()
        List<WatchlistLoadVM> list = new ArrayList<>();
        Page<WatchlistLoadVM> page = new PageImpl<>(list);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,"/api/watchlist");
        return new ResponseEntity<>(page.getContent(),headers,HttpStatus.OK);
    }

}
