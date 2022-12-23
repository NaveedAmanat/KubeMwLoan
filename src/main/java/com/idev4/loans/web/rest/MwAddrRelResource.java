package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwAddrRel;
import com.idev4.loans.service.MwAddrRelService;
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MwAddrRel.
 */
@RestController
@RequestMapping("/api")
public class MwAddrRelResource {

    private static final String ENTITY_NAME = "mwAddrRel";
    private final Logger log = LoggerFactory.getLogger(MwAddrRelResource.class);
    private final MwAddrRelService mwAddrRelService;

    public MwAddrRelResource(MwAddrRelService mwAddrRelService) {
        this.mwAddrRelService = mwAddrRelService;
    }

    /**
     * POST  /mw-addr-rels : Create a new mwAddrRel.
     *
     * @param mwAddrRel the mwAddrRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwAddrRel, or with status 400 (Bad Request) if the mwAddrRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-addr-rels")
    @Timed
    public ResponseEntity<MwAddrRel> createMwAddrRel(@RequestBody MwAddrRel mwAddrRel) throws URISyntaxException {
        log.debug("REST request to save MwAddrRel : {}", mwAddrRel);
        return null;
    }

    /**
     * PUT  /mw-addr-rels : Updates an existing mwAddrRel.
     *
     * @param mwAddrRel the mwAddrRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwAddrRel,
     * or with status 400 (Bad Request) if the mwAddrRel is not valid,
     * or with status 500 (Internal Server Error) if the mwAddrRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-addr-rels")
    @Timed
    public ResponseEntity<MwAddrRel> updateMwAddrRel(@RequestBody MwAddrRel mwAddrRel) throws URISyntaxException {
        log.debug("REST request to update MwAddrRel : {}", mwAddrRel);

        return null;
    }

    /**
     * GET  /mw-addr-rels : get all the mwAddrRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwAddrRels in body
     */
    @GetMapping("/mw-addr-rels")
    @Timed
    public ResponseEntity<List<MwAddrRel>> getAllMwAddrRels(Pageable pageable) {
        log.debug("REST request to get a page of MwAddrRels");
        Page<MwAddrRel> page = mwAddrRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-addr-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mw-addr-rels/:id : get the "id" mwAddrRel.
     *
     * @param id the id of the mwAddrRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwAddrRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-addr-rels/{id}")
    @Timed
    public ResponseEntity<MwAddrRel> getMwAddrRel(@PathVariable Long id) {
        log.debug("REST request to get MwAddrRel : {}", id);
        MwAddrRel mwAddrRel = mwAddrRelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwAddrRel));
    }

    /**
     * DELETE  /mw-addr-rels/:id : delete the "id" mwAddrRel.
     *
     * @param id the id of the mwAddrRel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
//    @DeleteMapping("/mw-addr-rels/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteMwAddrRel(@PathVariable Long id) {
//        log.debug("REST request to delete MwAddrRel : {}", id);
//        mwAddrRelService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }
}
