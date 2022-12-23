package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwHlthInsrMemb;
import com.idev4.loans.dto.HlthInsrMemberDto;
import com.idev4.loans.service.MwHlthInsrMembService;
import com.idev4.loans.web.rest.util.HeaderUtil;
import com.idev4.loans.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing MwHlthInsrMemb.
 */
@RestController
@RequestMapping("/api")
public class MwHlthInsrMembResource {

    private static final String ENTITY_NAME = "mwHlthInsrMemb";
    private final Logger log = LoggerFactory.getLogger(MwHlthInsrMembResource.class);
    private final MwHlthInsrMembService mwHlthInsrMembService;

    public MwHlthInsrMembResource(MwHlthInsrMembService mwHlthInsrMembService) {
        this.mwHlthInsrMembService = mwHlthInsrMembService;
    }

    /**
     * POST /mw-hlth-insr-membs : Create a new mwHlthInsrMemb.
     *
     * @param mwHlthInsrMemb the mwHlthInsrMemb to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwHlthInsrMemb, or with status 400 (Bad Request) if the
     * mwHlthInsrMemb has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-hlth-insr-memb")
    @Timed
    public ResponseEntity<Map> createMwHlthInsrMemb(@RequestBody HlthInsrMemberDto mwHlthInsrMemb) throws URISyntaxException {
        log.debug("REST request to save MwHlthInsrMemb : {}", mwHlthInsrMemb);
        Map<String, String> resp = new HashMap<String, String>();
        String namesRegex = "^[a-zA-Z\\s]+$";
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwHlthInsrMemb.loanAppSeq <= 0) {
            resp.put("error", "Missing Client Health Insurance Plan Seq !!");
            return ResponseEntity.badRequest().body(resp);
        }

        // if(mwHlthInsrMemb.maritalStatusKey<=0 || mwHlthInsrMemb.genderKey<=0) {
        // resp.put("error", "Invalid values selected from Dropdowns !!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        //
        // if(String.valueOf(mwHlthInsrMemb.memberCnicNum).length()!=13) {
        // resp.put("error", "Invalid CNIC Number Entered !!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        // if(!mwHlthInsrMemb.memberName.matches(namesRegex)) {
        // resp.put("error", "Invalid Name Entered !!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        long htlhInsrMemberSeq = mwHlthInsrMembService.addInsrMember(mwHlthInsrMemb, currUser);
        resp.put("hlthInsrMemberSeq", String.valueOf(htlhInsrMemberSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT /mw-hlth-insr-membs : Updates an existing mwHlthInsrMemb.
     *
     * @param mwHlthInsrMemb the mwHlthInsrMemb to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwHlthInsrMemb, or with status 400 (Bad Request) if the
     * mwHlthInsrMemb is not valid, or with status 500 (Internal Server Error) if the mwHlthInsrMemb couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-hlth-insr-membs")
    @Timed
    public ResponseEntity<Map> updateMwHlthInsrMemb(@RequestBody HlthInsrMemberDto mwHlthInsrMemb) throws URISyntaxException {
        log.debug("REST request to update MwHlthInsrMemb : {}", mwHlthInsrMemb);

        log.debug("REST request to save MwHlthInsrMemb : {}", mwHlthInsrMemb);
        Map<String, String> resp = new HashMap<String, String>();
        String namesRegex = "^[a-zA-Z\\s]+$";
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwHlthInsrMemb.loanAppSeq <= 0 || mwHlthInsrMemb.hlthInsrMemberSeq <= 0) {
            resp.put("error", "Missing Client Health Insurance Seq Seq !!");
            return ResponseEntity.badRequest().body(resp);
        }

        // if(mwHlthInsrMemb.maritalStatusKey<=0 || mwHlthInsrMemb.genderKey<=0) {
        // resp.put("error", "Invalid values selected from Dropdowns !!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        // if(String.valueOf(mwHlthInsrMemb.memberCnicNum).length()!=13) {
        // resp.put("error", "Invalid CNIC Number Entered !!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        if (!mwHlthInsrMemb.memberName.matches(namesRegex)) {
            resp.put("error", "Invalid Name Entered !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long htlhInsrMemberSeq = mwHlthInsrMembService.updateInsrMember(mwHlthInsrMemb, currUser);

        if (htlhInsrMemberSeq == 0) {
            resp.put("error", "Health Insurance Member Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("htlhInsrMemberSeq", String.valueOf(htlhInsrMemberSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET /mw-hlth-insr-membs : get all the mwHlthInsrMembs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwHlthInsrMembs in body
     */
    @GetMapping("/mw-hlth-insr-membs")
    @Timed
    public ResponseEntity<List<MwHlthInsrMemb>> getAllMwHlthInsrMembs(Pageable pageable) {
        log.debug("REST request to get a page of MwHlthInsrMembs");
        Page<MwHlthInsrMemb> page = mwHlthInsrMembService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-hlth-insr-membs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/hlth-insr-membs-by-client/{loanAppSeq}")
    @Timed
    public ResponseEntity<List<HlthInsrMemberDto>> getAllMwHlthInsrMembsByClient(@PathVariable long loanAppSeq) {
        log.debug("REST request to get a page of MwHlthInsrMembs");
        List<HlthInsrMemberDto> membs = mwHlthInsrMembService.findAllByLoanAppSeq(loanAppSeq);

        return ResponseEntity.ok().body(membs);
    }

    /**
     * GET /mw-hlth-insr-membs/:id : get the "id" mwHlthInsrMemb.
     *
     * @param id
     *            the id of the mwHlthInsrMemb to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwHlthInsrMemb, or with status 404 (Not Found)
     */
    // @GetMapping("/mw-hlth-insr-membs/{id}")
    // @Timed
    // public ResponseEntity<MwHlthInsrMemb> getMwHlthInsrMemb(@PathVariable Long id) {
    // log.debug("REST request to get MwHlthInsrMemb : {}", id);
    // MwHlthInsrMemb mwHlthInsrMemb = mwHlthInsrMembService.findOne(id);
    // return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwHlthInsrMemb));
    // }

    /**
     * DELETE /mw-hlth-insr-membs/:id : delete the "id" mwHlthInsrMemb.
     *
     * @param id the id of the mwHlthInsrMemb to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    // Modified by Areeba - Added param loanappseq - 29-11-2022 (Production Issue)
    @DeleteMapping("/mw-hlth-insr-membs/{id}/{loanAppSeq}")
    @Timed
    public ResponseEntity<Void> deleteMwHlthInsrMemb(@PathVariable Long id, @PathVariable Long loanAppSeq) {
        log.debug("REST request to delete MwHlthInsrMemb : {}", id);
        mwHlthInsrMembService.delete(id, loanAppSeq);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
