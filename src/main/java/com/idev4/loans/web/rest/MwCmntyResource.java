package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwCmnty;
import com.idev4.loans.service.MwCmntyService;
import com.idev4.loans.web.rest.errors.BadRequestAlertException;
import com.idev4.loans.web.rest.util.HeaderUtil;
import com.idev4.loans.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MwCmnty.
 */
@RestController
@RequestMapping("/api")
public class MwCmntyResource {

    private static final String ENTITY_NAME = "mwCmnty";
    private final Logger log = LoggerFactory.getLogger(MwCmntyResource.class);
    private final MwCmntyService mwCmntyService;

    public MwCmntyResource(MwCmntyService mwCmntyService) {
        this.mwCmntyService = mwCmntyService;
    }

    /**
     * POST /mw-cmnties : Create a new mwCmnty.
     *
     * @param mwCmnty the mwCmnty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCmnty, or with status 400 (Bad Request) if the mwCmnty
     * has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-cmnties")
    @Timed
    public ResponseEntity<MwCmnty> createMwCmnty(@RequestBody MwCmnty mwCmnty) throws URISyntaxException {
        log.debug("REST request to save MwCmnty : {}", mwCmnty);
        if (mwCmnty.getCmntySeq() != null) {
            throw new BadRequestAlertException("A new mwCmnty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwCmnty result = mwCmntyService.save(mwCmnty);
        return ResponseEntity.created(new URI("/api/mw-cmnties/" + result.getCmntySeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getCmntySeq().toString())).body(result);
    }

    /**
     * PUT /mw-cmnties : Updates an existing mwCmnty.
     *
     * @param mwCmnty the mwCmnty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwCmnty, or with status 400 (Bad Request) if the mwCmnty is
     * not valid, or with status 500 (Internal Server Error) if the mwCmnty couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-cmnties")
    @Timed
    public ResponseEntity<MwCmnty> updateMwCmnty(@RequestBody MwCmnty mwCmnty) throws URISyntaxException {
        log.debug("REST request to update MwCmnty : {}", mwCmnty);
        if (mwCmnty.getCmntySeq() == null) {
            return createMwCmnty(mwCmnty);
        }
        MwCmnty result = mwCmntyService.save(mwCmnty);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwCmnty.getCmntySeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-cmnties : get all the mwCmnties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCmnties in body
     */
    @GetMapping("/mw-cmnties")
    @Timed
    public ResponseEntity<List<MwCmnty>> getAllMwCmnties(Pageable pageable) {
        log.debug("REST request to get a page of MwCmnties");
        Page<MwCmnty> page = mwCmntyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-cmnties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-cmnties/:id : get the "id" mwCmnty.
     *
     * @param id the id of the mwCmnty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCmnty, or with status 404 (Not Found)
     */
    @GetMapping("/mw-cmnties/{id}")
    @Timed
    public ResponseEntity<MwCmnty> getMwCmnty(@PathVariable Long id) {
        log.debug("REST request to get MwCmnty : {}", id);
        MwCmnty mwCmnty = mwCmntyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCmnty));
    }

}
