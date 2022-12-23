package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwRefCdVal;
import com.idev4.loans.service.MwRefCdValService;
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
 * REST controller for managing MwRefCdVal.
 */
@RestController
@RequestMapping("/api")
public class MwRefCdValResource {

    private static final String ENTITY_NAME = "mwRefCdVal";
    private final Logger log = LoggerFactory.getLogger(MwRefCdValResource.class);
    private final MwRefCdValService mwRefCdValService;

    public MwRefCdValResource(MwRefCdValService mwRefCdValService) {
        this.mwRefCdValService = mwRefCdValService;
    }

    /**
     * POST /mw-ref-cd-vals : Create a new mwRefCdVal.
     *
     * @param mwRefCdVal the mwRefCdVal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwRefCdVal, or with status 400 (Bad Request) if the
     * mwRefCdVal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-ref-cd-vals")
    @Timed
    public ResponseEntity<MwRefCdVal> createMwRefCdVal(@RequestBody MwRefCdVal mwRefCdVal) throws URISyntaxException {
        log.debug("REST request to save MwRefCdVal : {}", mwRefCdVal);
        if (mwRefCdVal.getRefCdSeq() != null) {
            throw new BadRequestAlertException("A new mwRefCdVal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwRefCdVal result = mwRefCdValService.save(mwRefCdVal);
        return ResponseEntity.created(new URI("/api/mw-ref-cd-vals/" + result.getRefCdSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getRefCdSeq().toString())).body(result);
    }

    /**
     * PUT /mw-ref-cd-vals : Updates an existing mwRefCdVal.
     *
     * @param mwRefCdVal the mwRefCdVal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwRefCdVal, or with status 400 (Bad Request) if the
     * mwRefCdVal is not valid, or with status 500 (Internal Server Error) if the mwRefCdVal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-ref-cd-vals")
    @Timed
    public ResponseEntity<MwRefCdVal> updateMwRefCdVal(@RequestBody MwRefCdVal mwRefCdVal) throws URISyntaxException {
        log.debug("REST request to update MwRefCdVal : {}", mwRefCdVal);
        if (mwRefCdVal.getRefCdSeq() == null) {
            return createMwRefCdVal(mwRefCdVal);
        }
        MwRefCdVal result = mwRefCdValService.save(mwRefCdVal);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwRefCdVal.getRefCdSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-ref-cd-vals : get all the mwRefCdVals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwRefCdVals in body
     */
    @GetMapping("/mw-ref-cd-vals")
    @Timed
    public ResponseEntity<List<MwRefCdVal>> getAllMwRefCdVals(Pageable pageable) {
        log.debug("REST request to get a page of MwRefCdVals");
        Page<MwRefCdVal> page = mwRefCdValService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-ref-cd-vals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-ref-cd-vals/:id : get the "id" mwRefCdVal.
     *
     * @param id the id of the mwRefCdVal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwRefCdVal, or with status 404 (Not Found)
     */
    @GetMapping("/mw-ref-cd-vals/{id}")
    @Timed
    public ResponseEntity<MwRefCdVal> getMwRefCdVal(@PathVariable Long id) {
        log.debug("REST request to get MwRefCdVal : {}", id);
        MwRefCdVal mwRefCdVal = mwRefCdValService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwRefCdVal));
    }

}
