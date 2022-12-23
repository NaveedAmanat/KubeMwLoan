package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwClntPermAddr;
import com.idev4.loans.service.MwClntPermAddrService;
import com.idev4.loans.web.rest.errors.BadRequestAlertException;
import com.idev4.loans.web.rest.util.HeaderUtil;
import com.idev4.loans.web.rest.util.PaginationUtil;
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

/**
 * REST controller for managing MwClntPermAddr.
 */
@RestController
@RequestMapping("/api")
public class MwClntPermAddrResource {

    private static final String ENTITY_NAME = "mwClntPermAddr";
    private final Logger log = LoggerFactory.getLogger(MwClntPermAddrResource.class);
    private final MwClntPermAddrService mwClntPermAddrService;

    public MwClntPermAddrResource(MwClntPermAddrService mwClntPermAddrService) {
        this.mwClntPermAddrService = mwClntPermAddrService;
    }

    /**
     * POST /mw-clnt-perm-addrs : Create a new mwClntPermAddr.
     *
     * @param mwClntPermAddr the mwClntPermAddr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwClntPermAddr, or with status 400 (Bad Request) if the
     * mwClntPermAddr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-clnt-perm-addrs")
    @Timed
    public ResponseEntity<MwClntPermAddr> createMwClntPermAddr(@RequestBody MwClntPermAddr mwClntPermAddr) throws URISyntaxException {
        log.debug("REST request to save MwClntPermAddr : {}", mwClntPermAddr);
        if (mwClntPermAddr.getClntSeq() != null) {
            throw new BadRequestAlertException("A new mwClntPermAddr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwClntPermAddr result = mwClntPermAddrService.save(mwClntPermAddr);
        return ResponseEntity.created(new URI("/api/mw-clnt-perm-addrs/" + result.getClntSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getClntSeq().toString())).body(result);
    }

    /**
     * PUT /mw-clnt-perm-addrs : Updates an existing mwClntPermAddr.
     *
     * @param mwClntPermAddr the mwClntPermAddr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwClntPermAddr, or with status 400 (Bad Request) if the
     * mwClntPermAddr is not valid, or with status 500 (Internal Server Error) if the mwClntPermAddr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-clnt-perm-addrs")
    @Timed
    public ResponseEntity<MwClntPermAddr> updateMwClntPermAddr(@RequestBody MwClntPermAddr mwClntPermAddr) throws URISyntaxException {
        log.debug("REST request to update MwClntPermAddr : {}", mwClntPermAddr);
        if (mwClntPermAddr.getClntSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwClntPermAddr result = mwClntPermAddrService.save(mwClntPermAddr);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwClntPermAddr.getClntSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-clnt-perm-addrs : get all the mwClntPermAddrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwClntPermAddrs in body
     */
    @GetMapping("/mw-clnt-perm-addrs")
    @Timed
    public ResponseEntity<List<MwClntPermAddr>> getAllMwClntPermAddrs(Pageable pageable) {
        log.debug("REST request to get a page of MwClntPermAddrs");
        Page<MwClntPermAddr> page = mwClntPermAddrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-clnt-perm-addrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-clnt-perm-addrs/:id : get the "id" mwClntPermAddr.
     *
     * @param id the id of the mwClntPermAddr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwClntPermAddr, or with status 404 (Not Found)
     */
    @GetMapping("/mw-clnt-perm-addrs/{id}")
    @Timed
    public ResponseEntity<MwClntPermAddr> getMwClntPermAddr(@PathVariable Long id) {
        log.debug("REST request to get MwClntPermAddr : {}", id);
        MwClntPermAddr mwClntPermAddr = mwClntPermAddrService.findOne(id);
        return ResponseEntity.ok().body(mwClntPermAddr);
    }

}
