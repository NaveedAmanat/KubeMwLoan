package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwPrd;
import com.idev4.loans.service.MwPrdService;
import com.idev4.loans.web.rest.errors.BadRequestAlertException;
import com.idev4.loans.web.rest.util.HeaderUtil;
import com.idev4.loans.web.rest.util.PaginationUtil;
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

/**
 * REST controller for managing MwPrd.
 */
@RestController
@RequestMapping("/api")
public class MwPrdResource {

    private static final String ENTITY_NAME = "mwPrd";
    private final Logger log = LoggerFactory.getLogger(MwPrdResource.class);
    private final MwPrdService mwPrdService;

    public MwPrdResource(MwPrdService mwPrdService) {
        this.mwPrdService = mwPrdService;
    }

    /**
     * POST /mw-prds : Create a new mwPrd.
     *
     * @param mwPrd the mwPrd to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwPrd, or with status 400 (Bad Request) if the mwPrd has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-prds")
    @Timed
    public ResponseEntity<MwPrd> createMwPrd(@RequestBody MwPrd mwPrd) throws URISyntaxException {
        log.debug("REST request to save MwPrd : {}", mwPrd);
        if (mwPrd.getPrdSeq() != null) {
            throw new BadRequestAlertException("A new mwPrd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwPrd result = mwPrdService.save(mwPrd);
        return ResponseEntity.created(new URI("/api/mw-prds/" + result.getPrdSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getPrdSeq().toString())).body(result);
    }

    /**
     * PUT /mw-prds : Updates an existing mwPrd.
     *
     * @param mwPrd the mwPrd to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwPrd, or with status 400 (Bad Request) if the mwPrd is not
     * valid, or with status 500 (Internal Server Error) if the mwPrd couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-prds")
    @Timed
    public ResponseEntity<MwPrd> updateMwPrd(@RequestBody MwPrd mwPrd) throws URISyntaxException {
        log.debug("REST request to update MwPrd : {}", mwPrd);
        if (mwPrd.getPrdSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwPrd result = mwPrdService.save(mwPrd);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwPrd.getPrdSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-prds : get all the mwPrds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwPrds in body
     */
    @GetMapping("/mw-prds")
    @Timed
    public ResponseEntity<List<MwPrd>> getAllMwPrds(Pageable pageable) {
        log.debug("REST request to get a page of MwPrds");
        Page<MwPrd> page = mwPrdService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-prds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-prds/:id : get the "id" mwPrd.
     *
     * @param id the id of the mwPrd to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwPrd, or with status 404 (Not Found)
     */
    @GetMapping("/mw-prds/{id}")
    @Timed
    public ResponseEntity<MwPrd> getMwPrd(@PathVariable Long id) {
        log.debug("REST request to get MwPrd : {}", id);
        MwPrd mwPrd = mwPrdService.findOne(id);
        return ResponseEntity.ok().body(mwPrd);
    }

}
