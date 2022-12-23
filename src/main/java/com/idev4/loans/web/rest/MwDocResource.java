package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwDoc;
import com.idev4.loans.service.MwDocService;
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
 * REST controller for managing MwDoc.
 */
@RestController
@RequestMapping("/api")
public class MwDocResource {

    private static final String ENTITY_NAME = "mwDoc";
    private final Logger log = LoggerFactory.getLogger(MwDocResource.class);
    private final MwDocService mwDocService;

    public MwDocResource(MwDocService mwDocService) {
        this.mwDocService = mwDocService;
    }

    /**
     * POST /mw-docs : Create a new mwDoc.
     *
     * @param mwDoc the mwDoc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwDoc, or with status 400 (Bad Request) if the mwDoc has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-docs")
    @Timed
    public ResponseEntity<MwDoc> createMwDoc(@RequestBody MwDoc mwDoc) throws URISyntaxException {
        log.debug("REST request to save MwDoc : {}", mwDoc);
        if (mwDoc.getDocSeq() != null) {
            throw new BadRequestAlertException("A new mwDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwDoc result = mwDocService.save(mwDoc);
        return ResponseEntity.created(new URI("/api/mw-docs/" + result.getDocSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getDocSeq().toString())).body(result);
    }

    /**
     * PUT /mw-docs : Updates an existing mwDoc.
     *
     * @param mwDoc the mwDoc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwDoc, or with status 400 (Bad Request) if the mwDoc is not
     * valid, or with status 500 (Internal Server Error) if the mwDoc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-docs")
    @Timed
    public ResponseEntity<MwDoc> updateMwDoc(@RequestBody MwDoc mwDoc) throws URISyntaxException {
        log.debug("REST request to update MwDoc : {}", mwDoc);
        if (mwDoc.getDocSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwDoc result = mwDocService.save(mwDoc);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwDoc.getDocSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-docs : get all the mwDocs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwDocs in body
     */
    @GetMapping("/mw-docs")
    @Timed
    public ResponseEntity<List<MwDoc>> getAllMwDocs(Pageable pageable) {
        log.debug("REST request to get a page of MwDocs");
        Page<MwDoc> page = mwDocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-docs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-docs/:id : get the "id" mwDoc.
     *
     * @param id the id of the mwDoc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwDoc, or with status 404 (Not Found)
     */
    @GetMapping("/mw-docs/{id}")
    @Timed
    public ResponseEntity<MwDoc> getMwDoc(@PathVariable Long id) {
        log.debug("REST request to get MwDoc : {}", id);
        MwDoc mwDoc = mwDocService.findOne(id);
        return ResponseEntity.ok().body(mwDoc);
    }

}
