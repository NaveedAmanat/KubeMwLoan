package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwDist;
import com.idev4.loans.service.MwDistService;
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
 * REST controller for managing MwDist.
 */
@RestController
@RequestMapping("/api")
public class MwDistResource {

    private static final String ENTITY_NAME = "mwDist";
    private final Logger log = LoggerFactory.getLogger(MwDistResource.class);
    private final MwDistService mwDistService;

    public MwDistResource(MwDistService mwDistService) {
        this.mwDistService = mwDistService;
    }

    /**
     * POST /mw-dists : Create a new mwDist.
     *
     * @param mwDist the mwDist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwDist, or with status 400 (Bad Request) if the mwDist has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-dists")
    @Timed
    public ResponseEntity<MwDist> createMwDist(@RequestBody MwDist mwDist) throws URISyntaxException {
        log.debug("REST request to save MwDist : {}", mwDist);
        if (mwDist.getDistSeq() != null) {
            throw new BadRequestAlertException("A new mwDist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwDist result = mwDistService.save(mwDist);
        return ResponseEntity.created(new URI("/api/mw-dists/" + result.getDistSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getDistSeq().toString())).body(result);
    }

    /**
     * PUT /mw-dists : Updates an existing mwDist.
     *
     * @param mwDist the mwDist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwDist, or with status 400 (Bad Request) if the mwDist is
     * not valid, or with status 500 (Internal Server Error) if the mwDist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-dists")
    @Timed
    public ResponseEntity<MwDist> updateMwDist(@RequestBody MwDist mwDist) throws URISyntaxException {
        log.debug("REST request to update MwDist : {}", mwDist);
        if (mwDist.getDistSeq() == null) {
            return createMwDist(mwDist);
        }
        MwDist result = mwDistService.save(mwDist);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwDist.getDistSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-dists : get all the mwDists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwDists in body
     */
    @GetMapping("/mw-dists")
    @Timed
    public ResponseEntity<List<MwDist>> getAllMwDists(Pageable pageable) {
        log.debug("REST request to get a page of MwDists");
        Page<MwDist> page = mwDistService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-dists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-dists/:id : get the "id" mwDist.
     *
     * @param id the id of the mwDist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwDist, or with status 404 (Not Found)
     */
    @GetMapping("/mw-dists/{id}")
    @Timed
    public ResponseEntity<MwDist> getMwDist(@PathVariable Long id) {
        log.debug("REST request to get MwDist : {}", id);
        MwDist mwDist = mwDistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwDist));
    }

}
