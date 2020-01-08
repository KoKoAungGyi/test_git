package com.trustcoreict.demo.web.rest;

import com.trustcoreict.demo.domain.Header;
import com.trustcoreict.demo.service.HeaderService;
import com.trustcoreict.demo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.trustcoreict.demo.domain.Header}.
 */
@RestController
@RequestMapping("/api")
public class HeaderResource {

    private final Logger log = LoggerFactory.getLogger(HeaderResource.class);

    private final HeaderService headerService;

    public HeaderResource(HeaderService headerService) {
        this.headerService = headerService;
    }

    /**
     * {@code GET  /headers} : get all the headers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of headers in body.
     */
    @GetMapping("/headers")
    public ResponseEntity<List<Header>> getAllHeaders(Pageable pageable) {
        log.debug("REST request to get a page of Headers");
        Page<Header> page = headerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /headers/:id} : get the "id" header.
     *
     * @param id the id of the header to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the header, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/headers/{id}")
    public ResponseEntity<Header> getHeader(@PathVariable Long id) {
        log.debug("REST request to get Header : {}", id);
        Optional<Header> header = headerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(header);
    }

    /**
     * {@code SEARCH  /_search/headers?query=:query} : search for the header corresponding
     * to the query.
     *
     * @param query the query of the header search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/headers")
    public ResponseEntity<List<Header>> searchHeaders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Headers for query {}", query);
        Page<Header> page = headerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
