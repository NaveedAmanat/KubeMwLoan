package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwClntHlthInsr;
import com.idev4.loans.dto.ClntHlthInsrDto;
import com.idev4.loans.dto.HlthInsrMemberDto;
import com.idev4.loans.service.MwClntHlthInsrService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwClntHlthInsr.
 */
@RestController
@RequestMapping("/api")
public class MwClntHlthInsrResource {

    private static final String ENTITY_NAME = "mwClntHlthInsr";
    private final Logger log = LoggerFactory.getLogger(MwClntHlthInsrResource.class);
    private final MwClntHlthInsrService mwClntHlthInsrService;

    public MwClntHlthInsrResource(MwClntHlthInsrService mwClntHlthInsrService) {
        this.mwClntHlthInsrService = mwClntHlthInsrService;
    }

    /**
     * POST /mw-clnt-hlth-insrs : Create a new mwClntHlthInsr.
     *
     * @param mwClntHlthInsr the mwClntHlthInsr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwClntHlthInsr, or with status 400 (Bad Request) if the
     * mwClntHlthInsr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clnt-hlth-insr")
    @Timed
    public ResponseEntity<Map> createMwClntHlthInsr(@RequestBody ClntHlthInsrDto mwClntHlthInsr) throws URISyntaxException {
        log.debug("REST request to save MwClntHlthInsr : {}", mwClntHlthInsr);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwClntHlthInsr.loanAppSeq <= 0) {
            resp.put("error", "Missing Client Seq OR Insurance Plan!!");
            return ResponseEntity.badRequest().body(resp);
        }

        long clntHlthInsrSeq = mwClntHlthInsrService.updateClientHealthInsr(mwClntHlthInsr, currUser);
        resp.put("clntHlthInsrSeq", String.valueOf(clntHlthInsrSeq));
        return ResponseEntity.ok().body(resp);

    }

    /**
     * PUT /mw-clnt-hlth-insrs : Updates an existing mwClntHlthInsr.
     *
     * @param mwClntHlthInsr the mwClntHlthInsr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwClntHlthInsr, or with status 400 (Bad Request) if the
     * mwClntHlthInsr is not valid, or with status 500 (Internal Server Error) if the mwClntHlthInsr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-clnt-hlth-insrs")
    @Timed
    public ResponseEntity<Map> updateMwClntHlthInsr(@RequestBody ClntHlthInsrDto mwClntHlthInsr) throws URISyntaxException {
        log.debug("REST request to update MwClntHlthInsr : {}", mwClntHlthInsr);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwClntHlthInsr.clntHlthInsrSeq <= 0) {
            resp.put("error", "Missing Client Health Insr Seq !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwClntHlthInsr.loanAppSeq <= 0) {
            resp.put("error", "Missing Client Seq OR Insurance Plan!!");
            return ResponseEntity.badRequest().body(resp);
        }

        long clntHlthInsrSeq = mwClntHlthInsrService.updateClientHealthInsrOnUpdate(mwClntHlthInsr, currUser);

        if (clntHlthInsrSeq == 0) {
            resp.put("error", "Client Health Insurance Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("clntHlthInsrSeq", String.valueOf(clntHlthInsrSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET /mw-clnt-hlth-insrs : get all the mwClntHlthInsrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwClntHlthInsrs in body
     */
    @GetMapping("/mw-clnt-hlth-members/{loanAppSeq}")
    @Timed
    public ResponseEntity<List<HlthInsrMemberDto>> getAllMwClntHlthInsrs(@PathVariable Long loanAppSeq, Pageable pageable) {
        log.debug("REST request to get a page of MwClntHlthInsrs");

        List<HlthInsrMemberDto> members = mwClntHlthInsrService.getClientHealthInsuranceMembers(loanAppSeq);

        return ResponseEntity.ok().body(members);
    }

    /**
     * GET /mw-clnt-hlth-insrs/:id : get the "id" mwClntHlthInsr.
     *
     * @param id the id of the mwClntHlthInsr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwClntHlthInsr, or with status 404 (Not Found)
     */
    @GetMapping("/mw-clnt-hlth-insrs/{id}")
    @Timed
    public ResponseEntity<MwClntHlthInsr> getMwClntHlthInsr(@PathVariable Long id) {
        log.debug("REST request to get MwClntHlthInsr : {}", id);
        MwClntHlthInsr mwClntHlthInsr = mwClntHlthInsrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwClntHlthInsr));
    }

    @GetMapping("/mw-clnt-hlth-insr/{loanAppSeq}")
    @Timed
    public ResponseEntity<ClntHlthInsrDto> getClientHealthInsruance(@PathVariable Long loanAppSeq) {
        log.debug("REST request to get a page of MwClntHlthInsrs");

        ClntHlthInsrDto htlhInsr = mwClntHlthInsrService.getClientHealthInsrByLoanApp(loanAppSeq);
        return ResponseEntity.ok().body(htlhInsr);
    }
}
