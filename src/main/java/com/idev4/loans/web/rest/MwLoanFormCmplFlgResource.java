package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwLoanFormCmplFlg;
import com.idev4.loans.service.MwLoanFormCmplFlgService;
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
 * REST controller for managing MwLoanFormCmplFlg.
 */
@RestController
@RequestMapping("/api")
public class MwLoanFormCmplFlgResource {

    private static final String ENTITY_NAME = "mwLoanFormCmplFlg";
    private final Logger log = LoggerFactory.getLogger(MwLoanFormCmplFlgResource.class);
    private final MwLoanFormCmplFlgService mwLoanFormCmplFlgService;

    public MwLoanFormCmplFlgResource(MwLoanFormCmplFlgService mwLoanFormCmplFlgService) {
        this.mwLoanFormCmplFlgService = mwLoanFormCmplFlgService;
    }

    /**
     * POST /mw-loan-form-cmpl-flgs : Create a new mwLoanFormCmplFlg.
     *
     * @param mwLoanFormCmplFlg the mwLoanFormCmplFlg to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwLoanFormCmplFlg, or with status 400 (Bad Request) if the
     * mwLoanFormCmplFlg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-loan-form-cmpl-flgs")
    @Timed
    public ResponseEntity<MwLoanFormCmplFlg> createMwLoanFormCmplFlg(@RequestBody MwLoanFormCmplFlg mwLoanFormCmplFlg)
            throws URISyntaxException {
        log.debug("REST request to save MwLoanFormCmplFlg : {}", mwLoanFormCmplFlg);
        if (mwLoanFormCmplFlg.getFormSeq() != null) {
            throw new BadRequestAlertException("A new mwLoanFormCmplFlg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwLoanFormCmplFlg result = mwLoanFormCmplFlgService.save(mwLoanFormCmplFlg);
        return ResponseEntity.created(new URI("/api/mw-loan-form-cmpl-flgs/" + result.getFormSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getFormSeq().toString())).body(result);
    }

    /**
     * PUT /mw-loan-form-cmpl-flgs : Updates an existing mwLoanFormCmplFlg.
     *
     * @param mwLoanFormCmplFlg the mwLoanFormCmplFlg to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwLoanFormCmplFlg, or with status 400 (Bad Request) if the
     * mwLoanFormCmplFlg is not valid, or with status 500 (Internal Server Error) if the mwLoanFormCmplFlg couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-loan-form-cmpl-flgs")
    @Timed
    public ResponseEntity<MwLoanFormCmplFlg> updateMwLoanFormCmplFlg(@RequestBody MwLoanFormCmplFlg mwLoanFormCmplFlg)
            throws URISyntaxException {
        log.debug("REST request to update MwLoanFormCmplFlg : {}", mwLoanFormCmplFlg);
        if (mwLoanFormCmplFlg.getFormSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwLoanFormCmplFlg result = mwLoanFormCmplFlgService.save(mwLoanFormCmplFlg);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwLoanFormCmplFlg.getFormSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-loan-form-cmpl-flgs : get all the mwLoanFormCmplFlgs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwLoanFormCmplFlgs in body
     */
    @GetMapping("/mw-loan-form-cmpl-flgs")
    @Timed
    public ResponseEntity<List<MwLoanFormCmplFlg>> getAllMwLoanFormCmplFlgs(Pageable pageable) {
        log.debug("REST request to get a page of MwLoanFormCmplFlgs");
        Page<MwLoanFormCmplFlg> page = mwLoanFormCmplFlgService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-loan-form-cmpl-flgs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-loan-form-cmpl-flgs/:id : get the "id" mwLoanFormCmplFlg.
     *
     * @param id the id of the mwLoanFormCmplFlg to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwLoanFormCmplFlg, or with status 404 (Not Found)
     */
    @GetMapping("/mw-loan-form-cmpl-flgs/{id}")
    @Timed
    public ResponseEntity<MwLoanFormCmplFlg> getMwLoanFormCmplFlg(@PathVariable Long id) {
        log.debug("REST request to get MwLoanFormCmplFlg : {}", id);
        Optional<MwLoanFormCmplFlg> mwLoanFormCmplFlg = mwLoanFormCmplFlgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mwLoanFormCmplFlg);
    }

}
