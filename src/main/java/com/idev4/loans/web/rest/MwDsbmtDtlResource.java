package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwDsbmtDtl;
import com.idev4.loans.service.MwDsbmtDtlService;
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
 * REST controller for managing MwDsbmtDtl.
 */
@RestController
@RequestMapping("/api")
public class MwDsbmtDtlResource {

    private static final String ENTITY_NAME = "mwDsbmtDtl";
    private final Logger log = LoggerFactory.getLogger(MwDsbmtDtlResource.class);
    private final MwDsbmtDtlService mwDsbmtDtlService;

    public MwDsbmtDtlResource(MwDsbmtDtlService mwDsbmtDtlService) {
        this.mwDsbmtDtlService = mwDsbmtDtlService;
    }

    /**
     * POST /mw-dsbmt-dtls : Create a new mwDsbmtDtl.
     *
     * @param mwDsbmtDtl the mwDsbmtDtl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwDsbmtDtl, or with status 400 (Bad Request) if the
     * mwDsbmtDtl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-dsbmt-dtls")
    @Timed
    public ResponseEntity<MwDsbmtDtl> createMwDsbmtDtl(@RequestBody MwDsbmtDtl mwDsbmtDtl) throws URISyntaxException {
        log.debug("REST request to save MwDsbmtDtl : {}", mwDsbmtDtl);
        if (mwDsbmtDtl.getDsbmtDtlSeq() != null) {
            throw new BadRequestAlertException("A new mwDsbmtDtl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwDsbmtDtl result = mwDsbmtDtlService.save(mwDsbmtDtl);
        return ResponseEntity.created(new URI("/api/mw-dsbmt-dtls/" + result.getDsbmtDtlSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getDsbmtDtlSeq().toString())).body(result);
    }

    /**
     * PUT /mw-dsbmt-dtls : Updates an existing mwDsbmtDtl.
     *
     * @param mwDsbmtDtl the mwDsbmtDtl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwDsbmtDtl, or with status 400 (Bad Request) if the
     * mwDsbmtDtl is not valid, or with status 500 (Internal Server Error) if the mwDsbmtDtl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-dsbmt-dtls")
    @Timed
    public ResponseEntity<MwDsbmtDtl> updateMwDsbmtDtl(@RequestBody MwDsbmtDtl mwDsbmtDtl) throws URISyntaxException {
        log.debug("REST request to update MwDsbmtDtl : {}", mwDsbmtDtl);
        if (mwDsbmtDtl.getDsbmtDtlSeq() == null) {
            return createMwDsbmtDtl(mwDsbmtDtl);
        }
        MwDsbmtDtl result = mwDsbmtDtlService.save(mwDsbmtDtl);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwDsbmtDtl.getDsbmtDtlSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-dsbmt-dtls : get all the mwDsbmtDtls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwDsbmtDtls in body
     */
    @GetMapping("/mw-dsbmt-dtls")
    @Timed
    public ResponseEntity<List<MwDsbmtDtl>> getAllMwDsbmtDtls(Pageable pageable) {
        log.debug("REST request to get a page of MwDsbmtDtls");
        Page<MwDsbmtDtl> page = mwDsbmtDtlService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-dsbmt-dtls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-dsbmt-dtls/:id : get the "id" mwDsbmtDtl.
     *
     * @param id the id of the mwDsbmtDtl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwDsbmtDtl, or with status 404 (Not Found)
     */
    @GetMapping("/mw-dsbmt-dtls/{id}")
    @Timed
    public ResponseEntity<MwDsbmtDtl> getMwDsbmtDtl(@PathVariable Long id) {
        log.debug("REST request to get MwDsbmtDtl : {}", id);
        MwDsbmtDtl mwDsbmtDtl = mwDsbmtDtlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwDsbmtDtl));
    }

}
