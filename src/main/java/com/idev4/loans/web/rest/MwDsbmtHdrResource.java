package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwDsbmtHdr;
import com.idev4.loans.service.MwDsbmtHdrService;
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
 * REST controller for managing MwDsbmtHdr.
 */
@RestController
@RequestMapping("/api")
public class MwDsbmtHdrResource {

    private static final String ENTITY_NAME = "mwDsbmtHdr";
    private final Logger log = LoggerFactory.getLogger(MwDsbmtHdrResource.class);
    private final MwDsbmtHdrService mwDsbmtHdrService;

    public MwDsbmtHdrResource(MwDsbmtHdrService mwDsbmtHdrService) {
        this.mwDsbmtHdrService = mwDsbmtHdrService;
    }

    /**
     * POST /mw-dsbmt-hdrs : Create a new mwDsbmtHdr.
     *
     * @param mwDsbmtHdr the mwDsbmtHdr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwDsbmtHdr, or with status 400 (Bad Request) if the
     * mwDsbmtHdr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-dsbmt-hdrs")
    @Timed
    public ResponseEntity<MwDsbmtHdr> createMwDsbmtHdr(@RequestBody MwDsbmtHdr mwDsbmtHdr) throws URISyntaxException {
        log.debug("REST request to save MwDsbmtHdr : {}", mwDsbmtHdr);
        if (mwDsbmtHdr.getDsbmtHdrSeq() != null) {
            throw new BadRequestAlertException("A new mwDsbmtHdr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwDsbmtHdr result = mwDsbmtHdrService.save(mwDsbmtHdr);
        return ResponseEntity.created(new URI("/api/mw-dsbmt-hdrs/" + result.getDsbmtHdrSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getDsbmtHdrSeq().toString())).body(result);
    }

    /**
     * PUT /mw-dsbmt-hdrs : Updates an existing mwDsbmtHdr.
     *
     * @param mwDsbmtHdr the mwDsbmtHdr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwDsbmtHdr, or with status 400 (Bad Request) if the
     * mwDsbmtHdr is not valid, or with status 500 (Internal Server Error) if the mwDsbmtHdr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-dsbmt-hdrs")
    @Timed
    public ResponseEntity<MwDsbmtHdr> updateMwDsbmtHdr(@RequestBody MwDsbmtHdr mwDsbmtHdr) throws URISyntaxException {
        log.debug("REST request to update MwDsbmtHdr : {}", mwDsbmtHdr);
        if (mwDsbmtHdr.getDsbmtHdrSeq() == null) {
            return createMwDsbmtHdr(mwDsbmtHdr);
        }
        MwDsbmtHdr result = mwDsbmtHdrService.save(mwDsbmtHdr);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwDsbmtHdr.getDsbmtHdrSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-dsbmt-hdrs : get all the mwDsbmtHdrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwDsbmtHdrs in body
     */
    @GetMapping("/mw-dsbmt-hdrs")
    @Timed
    public ResponseEntity<List<MwDsbmtHdr>> getAllMwDsbmtHdrs(Pageable pageable) {
        log.debug("REST request to get a page of MwDsbmtHdrs");
        Page<MwDsbmtHdr> page = mwDsbmtHdrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-dsbmt-hdrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-dsbmt-hdrs/:id : get the "id" mwDsbmtHdr.
     *
     * @param id the id of the mwDsbmtHdr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwDsbmtHdr, or with status 404 (Not Found)
     */
    @GetMapping("/mw-dsbmt-hdrs/{id}")
    @Timed
    public ResponseEntity<MwDsbmtHdr> getMwDsbmtHdr(@PathVariable Long id) {
        log.debug("REST request to get MwDsbmtHdr : {}", id);
        MwDsbmtHdr mwDsbmtHdr = mwDsbmtHdrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwDsbmtHdr));
    }

}
