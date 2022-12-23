package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwLoanUtlPlan;
import com.idev4.loans.dto.LoanUtilDto;
import com.idev4.loans.service.MwLoanUtlPlanService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwLoanUtlPlan.
 */
@RestController
@RequestMapping("/api")
public class MwLoanUtlPlanResource {

    private static final String ENTITY_NAME = "mwLoanUtlPlan";
    private final Logger log = LoggerFactory.getLogger(MwLoanUtlPlanResource.class);
    private final MwLoanUtlPlanService mwLoanUtlPlanService;

    public MwLoanUtlPlanResource(MwLoanUtlPlanService mwLoanUtlPlanService) {
        this.mwLoanUtlPlanService = mwLoanUtlPlanService;
    }

    /**
     * POST  /mw-loan-utl-plans : Create a new mwLoanUtlPlan.
     *
     * @param mwLoanUtlPlan the mwLoanUtlPlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwLoanUtlPlan, or with status 400 (Bad Request) if the mwLoanUtlPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-loan-utl-plans")
    @Timed
    public ResponseEntity<Map> createMwLoanUtlPlan(@RequestBody LoanUtilDto mwLoanUtlPlan) throws URISyntaxException {
        log.debug("REST request to save MwLoanUtlPlan : {}", mwLoanUtlPlan);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (mwLoanUtlPlan.loanAppSeq <= 0 || mwLoanUtlPlan.loanUtilAmount <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long utilPlanSeq = mwLoanUtlPlanService.createLoanUtilPlan(mwLoanUtlPlan, currUser);
        resp.put("utilPlanSeq", String.valueOf(utilPlanSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT  /mw-loan-utl-plans : Updates an existing mwLoanUtlPlan.
     *
     * @param mwLoanUtlPlan the mwLoanUtlPlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwLoanUtlPlan,
     * or with status 400 (Bad Request) if the mwLoanUtlPlan is not valid,
     * or with status 500 (Internal Server Error) if the mwLoanUtlPlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-loan-utl-plans")
    @Timed
    public ResponseEntity<Map> updateMwLoanUtlPlan(@RequestBody LoanUtilDto mwLoanUtlPlan) throws URISyntaxException {
        log.debug("REST request to update MwLoanUtlPlan : {}", mwLoanUtlPlan);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwLoanUtlPlan.utilPlanSeq <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwLoanUtlPlan.loanAppSeq <= 0 || mwLoanUtlPlan.loanUtilAmount <= 0) {
            resp.put("error", "Bad values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long utilPlanSeq = mwLoanUtlPlanService.updateLoanUtilPlan(mwLoanUtlPlan, currUser);

        if (utilPlanSeq == 0) {
            resp.put("error", "Util Plan Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("utilPlanSeq", String.valueOf(utilPlanSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET  /mw-loan-utl-plans : get all the mwLoanUtlPlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwLoanUtlPlans in body
     */
    @GetMapping("/mw-loan-utl-plans")
    @Timed
    public ResponseEntity<List<MwLoanUtlPlan>> getAllMwLoanUtlPlans(Pageable pageable) {
        log.debug("REST request to get a page of MwLoanUtlPlans");
        Page<MwLoanUtlPlan> page = mwLoanUtlPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-loan-utl-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/get-loan-utl-plans-by-loan-app/{loanAppSeq}")
    @Timed
    public ResponseEntity<Map> getAllMwLoanUtlPlansByClient(@PathVariable long loanAppSeq) {
        log.debug("REST request to get a page of MwLoanUtlPlans");
        Map resp = mwLoanUtlPlanService.getUtilPlanOfLoanApplication(loanAppSeq);
        if (resp == null) {
            resp = new HashMap<String, String>();
            resp.put("error", "No Util Plan Found for Loan Application");
            return ResponseEntity.ok().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }


    /**
     * GET  /mw-loan-utl-plans/:id : get the "id" mwLoanUtlPlan.
     *
     * @param id the id of the mwLoanUtlPlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwLoanUtlPlan, or with status 404 (Not Found)
     */
    @GetMapping("/mw-loan-utl-plans/{id}")
    @Timed
    public ResponseEntity<MwLoanUtlPlan> getMwLoanUtlPlan(@PathVariable Long id) {
        log.debug("REST request to get MwLoanUtlPlan : {}", id);
        MwLoanUtlPlan mwLoanUtlPlan = mwLoanUtlPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwLoanUtlPlan));
    }

    /**
     * DELETE  /mw-loan-utl-plans/:id : delete the "id" mwLoanUtlPlan.
     *
     * @param id the id of the mwLoanUtlPlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delete-loan-utl-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwLoanUtlPlan(@PathVariable Long id) {
        log.debug("REST request to delete MwLoanUtlPlan : {}", id);
        mwLoanUtlPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
