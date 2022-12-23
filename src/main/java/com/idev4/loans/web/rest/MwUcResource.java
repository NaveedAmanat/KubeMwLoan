package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwUc;
import com.idev4.loans.service.MwUcService;
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
 * REST controller for managing MwUc.
 */
@RestController
@RequestMapping("/api")
public class MwUcResource {

    private static final String ENTITY_NAME = "mwUc";
    private final Logger log = LoggerFactory.getLogger(MwUcResource.class);
    private final MwUcService mwUcService;

    public MwUcResource(MwUcService mwUcService) {
        this.mwUcService = mwUcService;
    }

    /**
     * POST /mw-ucs : Create a new mwUc.
     *
     * @param mwUc the mwUc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwUc, or with status 400 (Bad Request) if the mwUc has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-ucs")
    @Timed
    public ResponseEntity<MwUc> createMwUc(@RequestBody MwUc mwUc) throws URISyntaxException {
        log.debug("REST request to save MwUc : {}", mwUc);
        if (mwUc.getUcSeq() != null) {
            throw new BadRequestAlertException("A new mwUc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwUc result = mwUcService.save(mwUc);
        return ResponseEntity.created(new URI("/api/mw-ucs/" + result.getUcSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getUcSeq().toString())).body(result);
    }

    /**
     * PUT /mw-ucs : Updates an existing mwUc.
     *
     * @param mwUc the mwUc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwUc, or with status 400 (Bad Request) if the mwUc is not
     * valid, or with status 500 (Internal Server Error) if the mwUc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-ucs")
    @Timed
    public ResponseEntity<MwUc> updateMwUc(@RequestBody MwUc mwUc) throws URISyntaxException {
        log.debug("REST request to update MwUc : {}", mwUc);
        if (mwUc.getUcSeq() == null) {
            return createMwUc(mwUc);
        }
        MwUc result = mwUcService.save(mwUc);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwUc.getUcSeq().toString())).body(result);
    }

    /**
     * GET /mw-ucs : get all the mwUcs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwUcs in body
     */
    @GetMapping("/mw-ucs")
    @Timed
    public ResponseEntity<List<MwUc>> getAllMwUcs(Pageable pageable) {
        log.debug("REST request to get a page of MwUcs");
        Page<MwUc> page = mwUcService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-ucs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-ucs/:id : get the "id" mwUc.
     *
     * @param id the id of the mwUc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwUc, or with status 404 (Not Found)
     */
    @GetMapping("/mw-ucs/{id}")
    @Timed
    public ResponseEntity<MwUc> getMwUc(@PathVariable Long id) {
        log.debug("REST request to get MwUc : {}", id);
        MwUc mwUc = mwUcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwUc));
    }

}
