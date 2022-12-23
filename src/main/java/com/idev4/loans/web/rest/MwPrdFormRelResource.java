package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwPrdFormRel;
import com.idev4.loans.service.MwPrdFormRelService;
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
 * REST controller for managing MwPrdFormRel.
 */
@RestController
@RequestMapping("/api")
public class MwPrdFormRelResource {

    private static final String ENTITY_NAME = "mwPrdFormRel";
    private final Logger log = LoggerFactory.getLogger(MwPrdFormRelResource.class);
    private final MwPrdFormRelService mwPrdFormRelService;

    public MwPrdFormRelResource(MwPrdFormRelService mwPrdFormRelService) {
        this.mwPrdFormRelService = mwPrdFormRelService;
    }

    /**
     * POST /mw-prd-form-rels : Create a new mwPrdFormRel.
     *
     * @param mwPrdFormRel the mwPrdFormRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwPrdFormRel, or with status 400 (Bad Request) if the
     * mwPrdFormRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-prd-form-rels")
    @Timed
    public ResponseEntity<MwPrdFormRel> createMwPrdFormRel(@RequestBody MwPrdFormRel mwPrdFormRel) throws URISyntaxException {
        log.debug("REST request to save MwPrdFormRel : {}", mwPrdFormRel);
        if (mwPrdFormRel.getPrdFormSeq() != null) {
            throw new BadRequestAlertException("A new mwPrdFormRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwPrdFormRel result = mwPrdFormRelService.save(mwPrdFormRel);
        return ResponseEntity.created(new URI("/api/mw-prd-form-rels/" + result.getPrdFormSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getPrdFormSeq().toString())).body(result);
    }

    /**
     * PUT /mw-prd-form-rels : Updates an existing mwPrdFormRel.
     *
     * @param mwPrdFormRel the mwPrdFormRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwPrdFormRel, or with status 400 (Bad Request) if the
     * mwPrdFormRel is not valid, or with status 500 (Internal Server Error) if the mwPrdFormRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-prd-form-rels")
    @Timed
    public ResponseEntity<MwPrdFormRel> updateMwPrdFormRel(@RequestBody MwPrdFormRel mwPrdFormRel) throws URISyntaxException {
        log.debug("REST request to update MwPrdFormRel : {}", mwPrdFormRel);
        if (mwPrdFormRel.getPrdFormSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwPrdFormRel result = mwPrdFormRelService.save(mwPrdFormRel);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwPrdFormRel.getPrdFormSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-prd-form-rels : get all the mwPrdFormRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwPrdFormRels in body
     */
    @GetMapping("/mw-prd-form-rels")
    @Timed
    public ResponseEntity<List<MwPrdFormRel>> getAllMwPrdFormRels(Pageable pageable) {
        log.debug("REST request to get a page of MwPrdFormRels");
        Page<MwPrdFormRel> page = mwPrdFormRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-prd-form-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-prd-form-rels/:id : get the "id" mwPrdFormRel.
     *
     * @param id the id of the mwPrdFormRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwPrdFormRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-prd-form-rels/{id}")
    @Timed
    public ResponseEntity<MwPrdFormRel> getMwPrdFormRel(@PathVariable Long id) {
        log.debug("REST request to get MwPrdFormRel : {}", id);
        Optional<MwPrdFormRel> mwPrdFormRel = mwPrdFormRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mwPrdFormRel);
    }

}
