package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwMfcibOthOutsdLoan;
import com.idev4.loans.dto.MfcibDto;
import com.idev4.loans.service.MwMfcibOthOutsdLoanService;
import com.idev4.loans.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwMfcibOthOutsdLoan.
 */
@RestController
@RequestMapping("/api")
public class MwMfcibOthOutsdLoanResource {

    private static final String ENTITY_NAME = "mwMfcibOthOutsdLoan";
    private final Logger log = LoggerFactory.getLogger(MwMfcibOthOutsdLoanResource.class);
    private final MwMfcibOthOutsdLoanService mwMfcibOthOutsdLoanService;

    public MwMfcibOthOutsdLoanResource(MwMfcibOthOutsdLoanService mwMfcibOthOutsdLoanService) {
        this.mwMfcibOthOutsdLoanService = mwMfcibOthOutsdLoanService;
    }

    /**
     * POST  /mw-mfcib-oth-outsd-loans : Create a new mwMfcibOthOutsdLoan.
     *
     * @param mwMfcibOthOutsdLoan the mwMfcibOthOutsdLoan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwMfcibOthOutsdLoan, or with status 400 (Bad Request) if the mwMfcibOthOutsdLoan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-mfcib-oth-outsd-loans")
    @Timed
    public ResponseEntity<Map> createMwMfcibOthOutsdLoan(@RequestBody MfcibDto dto) throws URISyntaxException {
        log.debug("REST request to save MwMfcibOthOutsdLoan : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.instituteName == null || dto.instituteName.isEmpty()) {
            resp.put("error", "Invalid Institue Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dto.currentOutStandingAmount < 0 || dto.totalAmount < 0) {
            resp.put("error", "Invalid Outstanding Amount OR Total Amount !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dto.loanCompletionDate == null || dto.loanPurpose == null || dto.loanPurpose.isEmpty()) {
            resp.put("error", "Invalid Loan Purpose Or Completion Date !!");
            return ResponseEntity.badRequest().body(resp);
        }
        long seq = mwMfcibOthOutsdLoanService.addNewUserToldMfcibLoan(dto, currUser);
        resp.put("mfcibSeq", String.valueOf(seq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT  /mw-mfcib-oth-outsd-loans : Updates an existing mwMfcibOthOutsdLoan.
     *
     * @param mwMfcibOthOutsdLoan the mwMfcibOthOutsdLoan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwMfcibOthOutsdLoan,
     * or with status 400 (Bad Request) if the mwMfcibOthOutsdLoan is not valid,
     * or with status 500 (Internal Server Error) if the mwMfcibOthOutsdLoan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-mfcib-oth-outsd-loans")
    @Timed
    public ResponseEntity<Map> updateMwMfcibOthOutsdLoan(@RequestBody MfcibDto dto) throws URISyntaxException {
        log.debug("REST request to update MwMfcibOthOutsdLoan : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.instituteName == null || dto.instituteName.isEmpty()) {
            resp.put("error", "Invalid Institue Name !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dto.currentOutStandingAmount < 0 || dto.totalAmount < 0) {
            resp.put("error", "Invalid Outstanding Amount OR Total Amount !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (dto.loanCompletionDate == null || dto.loanPurpose == null || dto.loanPurpose.isEmpty()) {
            resp.put("error", "Invalid Loan Purpose Or Completion Date !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long seq = mwMfcibOthOutsdLoanService.updateUserToldMfcibLoan(dto, currUser);
        if (seq == 0) {
            resp.put("error", "Mfcib Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        resp.put("mfcibSeq", String.valueOf(seq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET  /mw-mfcib-oth-outsd-loans : get all the mwMfcibOthOutsdLoans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwMfcibOthOutsdLoans in body
     */
    @GetMapping("/get-mfcib-oth-outsd-loans-by-app/{loanAppSeq}")
    @Timed
    public ResponseEntity<List<MfcibDto>> getAllMwMfcibOthOutsdLoans(@PathVariable long loanAppSeq) {
        log.debug("REST request to get a page of MwMfcibOthOutsdLoans");
        List<MfcibDto> dtos = mwMfcibOthOutsdLoanService.getLoanAppMfcibs(loanAppSeq);
        return ResponseEntity.ok().body(dtos);
    }

    /**
     * GET  /mw-mfcib-oth-outsd-loans/:id : get the "id" mwMfcibOthOutsdLoan.
     *
     * @param id the id of the mwMfcibOthOutsdLoan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwMfcibOthOutsdLoan, or with status 404 (Not Found)
     */
    @GetMapping("/mw-mfcib-oth-outsd-loans/{id}")
    @Timed
    public ResponseEntity<MwMfcibOthOutsdLoan> getMwMfcibOthOutsdLoan(@PathVariable Long id) {
        log.debug("REST request to get MwMfcibOthOutsdLoan : {}", id);
        MwMfcibOthOutsdLoan mwMfcibOthOutsdLoan = mwMfcibOthOutsdLoanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwMfcibOthOutsdLoan));
    }

    /**
     * DELETE  /mw-mfcib-oth-outsd-loans/:id : delete the "id" mwMfcibOthOutsdLoan.
     *
     * @param id the id of the mwMfcibOthOutsdLoan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-mfcib-oth-outsd-loans/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwMfcibOthOutsdLoan(@PathVariable Long id) {
        log.debug("REST request to delete MwMfcibOthOutsdLoan : {}", id);
        mwMfcibOthOutsdLoanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
