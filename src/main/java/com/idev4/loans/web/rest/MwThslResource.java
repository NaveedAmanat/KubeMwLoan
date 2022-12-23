package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwThsl;
import com.idev4.loans.service.MwThslService;
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
 * REST controller for managing MwThsl.
 */
@RestController
@RequestMapping("/api")
public class MwThslResource {

    private static final String ENTITY_NAME = "mwThsl";
    private final Logger log = LoggerFactory.getLogger(MwThslResource.class);
    private final MwThslService mwThslService;

    public MwThslResource(MwThslService mwThslService) {
        this.mwThslService = mwThslService;
    }

    /**
     * POST /mw-thsls : Create a new mwThsl.
     *
     * @param mwThsl the mwThsl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwThsl, or with status 400 (Bad Request) if the mwThsl has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-thsls")
    @Timed
    public ResponseEntity<MwThsl> createMwThsl(@RequestBody MwThsl mwThsl) throws URISyntaxException {
        log.debug("REST request to save MwThsl : {}", mwThsl);
        if (mwThsl.getThslSeq() != null) {
            throw new BadRequestAlertException("A new mwThsl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwThsl result = mwThslService.save(mwThsl);
        return ResponseEntity.created(new URI("/api/mw-thsls/" + result.getThslSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getThslSeq().toString())).body(result);
    }

    /**
     * PUT /mw-thsls : Updates an existing mwThsl.
     *
     * @param mwThsl the mwThsl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwThsl, or with status 400 (Bad Request) if the mwThsl is
     * not valid, or with status 500 (Internal Server Error) if the mwThsl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-thsls")
    @Timed
    public ResponseEntity<MwThsl> updateMwThsl(@RequestBody MwThsl mwThsl) throws URISyntaxException {
        log.debug("REST request to update MwThsl : {}", mwThsl);
        if (mwThsl.getThslSeq() == null) {
            return createMwThsl(mwThsl);
        }
        MwThsl result = mwThslService.save(mwThsl);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwThsl.getThslSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-thsls : get all the mwThsls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwThsls in body
     */
    @GetMapping("/mw-thsls")
    @Timed
    public ResponseEntity<List<MwThsl>> getAllMwThsls(Pageable pageable) {
        log.debug("REST request to get a page of MwThsls");
        Page<MwThsl> page = mwThslService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-thsls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-thsls/:id : get the "id" mwThsl.
     *
     * @param id the id of the mwThsl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwThsl, or with status 404 (Not Found)
     */
    @GetMapping("/mw-thsls/{id}")
    @Timed
    public ResponseEntity<MwThsl> getMwThsl(@PathVariable Long id) {
        log.debug("REST request to get MwThsl : {}", id);
        MwThsl mwThsl = mwThslService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwThsl));
    }

}
