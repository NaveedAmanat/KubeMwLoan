package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwForm;
import com.idev4.loans.service.MwFormService;
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
 * REST controller for managing MwForm.
 */
@RestController
@RequestMapping("/api")
public class MwFormResource {

    private static final String ENTITY_NAME = "mwForm";
    private final Logger log = LoggerFactory.getLogger(MwFormResource.class);
    private final MwFormService mwFormService;

    public MwFormResource(MwFormService mwFormService) {
        this.mwFormService = mwFormService;
    }

    /**
     * POST /mw-forms : Create a new mwForm.
     *
     * @param mwForm the mwForm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwForm, or with status 400 (Bad Request) if the mwForm has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-forms")
    @Timed
    public ResponseEntity<MwForm> createMwForm(@RequestBody MwForm mwForm) throws URISyntaxException {
        log.debug("REST request to save MwForm : {}", mwForm);
        if (mwForm.getFormSeq() != null) {
            throw new BadRequestAlertException("A new mwForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwForm result = mwFormService.save(mwForm);
        return ResponseEntity.created(new URI("/api/mw-forms/" + result.getFormSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getFormSeq().toString())).body(result);
    }

    /**
     * PUT /mw-forms : Updates an existing mwForm.
     *
     * @param mwForm the mwForm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwForm, or with status 400 (Bad Request) if the mwForm is
     * not valid, or with status 500 (Internal Server Error) if the mwForm couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-forms")
    @Timed
    public ResponseEntity<MwForm> updateMwForm(@RequestBody MwForm mwForm) throws URISyntaxException {
        log.debug("REST request to update MwForm : {}", mwForm);
        if (mwForm.getFormSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwForm result = mwFormService.save(mwForm);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwForm.getFormSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-forms : get all the mwForms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwForms in body
     */
    @GetMapping("/mw-forms")
    @Timed
    public ResponseEntity<List<MwForm>> getAllMwForms(Pageable pageable) {
        log.debug("REST request to get a page of MwForms");
        Page<MwForm> page = mwFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-forms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-forms/:id : get the "id" mwForm.
     *
     * @param id the id of the mwForm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwForm, or with status 404 (Not Found)
     */
    @GetMapping("/mw-forms/{id}")
    @Timed
    public ResponseEntity<MwForm> getMwForm(@PathVariable Long id) {
        log.debug("REST request to get MwForm : {}", id);
        Optional<MwForm> mwForm = mwFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mwForm);
    }

}
