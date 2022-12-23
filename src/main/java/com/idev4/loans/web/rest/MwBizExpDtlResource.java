package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwBizExpDtl;
import com.idev4.loans.dto.BizExpDto;
import com.idev4.loans.service.MwBizExpDtlService;
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
 * REST controller for managing MwBizExpDtl.
 */
@RestController
@RequestMapping("/api")
public class MwBizExpDtlResource {

    private static final String ENTITY_NAME = "mwBizExpDtl";
    private final Logger log = LoggerFactory.getLogger(MwBizExpDtlResource.class);
    private final MwBizExpDtlService mwBizExpDtlService;

    public MwBizExpDtlResource(MwBizExpDtlService mwBizExpDtlService) {
        this.mwBizExpDtlService = mwBizExpDtlService;
    }

    /**
     * POST  /mw-biz-exp-dtls : Create a new mwBizExpDtl.
     *
     * @param mwBizExpDtl the mwBizExpDtl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwBizExpDtl, or with status 400 (Bad Request) if the mwBizExpDtl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-business-expense")
    @Timed
    public ResponseEntity<Map> createMwBizExpDtl(@RequestBody BizExpDto mwBizExpDtl) throws URISyntaxException {
        log.debug("REST request to save MwBizExpDtl : {}", mwBizExpDtl);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwBizExpDtl.expAmount < 0 || mwBizExpDtl.expCategoryKey < 0 || mwBizExpDtl.expTypKey < 0 || mwBizExpDtl.bizAprslSeq < 0) {
            resp.put("error", "Invalid Amount Or Biz Aprsl Seq !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long expDtlSeq = mwBizExpDtlService.addClientExpenses(mwBizExpDtl, currUser);
        resp.put("expDtlSeq", String.valueOf(expDtlSeq));
        return ResponseEntity.ok().body(resp);

    }

    /**
     * PUT  /mw-biz-exp-dtls : Updates an existing mwBizExpDtl.
     *
     * @param mwBizExpDtl the mwBizExpDtl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwBizExpDtl,
     * or with status 400 (Bad Request) if the mwBizExpDtl is not valid,
     * or with status 500 (Internal Server Error) if the mwBizExpDtl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-biz-exp-dtls")
    @Timed
    public ResponseEntity<Map> updateMwBizExpDtl(@RequestBody BizExpDto mwBizExpDtl) throws URISyntaxException {
        log.debug("REST request to update MwBizExpDtl : {}", mwBizExpDtl);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwBizExpDtl.bizExpSeq <= 0) {
            resp.put("error", "Bad Business Sequence Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwBizExpDtl.expAmount < 0 || mwBizExpDtl.expCategoryKey < 0 || mwBizExpDtl.expTypKey < 0 || mwBizExpDtl.bizAprslSeq < 0) {
            resp.put("error", "Invalid Amount Or Biz Aprsl Seq !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long expDtlSeq = mwBizExpDtlService.updateClientExpenses(mwBizExpDtl, currUser);

        if (expDtlSeq == 0) {
            resp.put("error", "Business Expense Detail Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("expDtlSeq", String.valueOf(expDtlSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET  /mw-biz-exp-dtls : get all the mwBizExpDtls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBizExpDtls in body
     */
    @GetMapping("/mw-biz-exp-dtls")
    @Timed
    public ResponseEntity<List<MwBizExpDtl>> getAllMwBizExpDtls(Pageable pageable) {
        log.debug("REST request to get a page of MwBizExpDtls");
        Page<MwBizExpDtl> page = mwBizExpDtlService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-biz-exp-dtls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    public void deleteAllExpensesByLoanApp(long loanAppSeq, long bizAppSeq) {
        mwBizExpDtlService.deleteAllByLoanAppSeq(loanAppSeq, bizAppSeq);
    }

    /**
     * GET  /mw-biz-exp-dtls/:id : get the "id" mwBizExpDtl.
     *
     * @param id the id of the mwBizExpDtl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBizExpDtl, or with status 404 (Not Found)
     */
    @GetMapping("/mw-biz-exp-dtls/{id}")
    @Timed
    public ResponseEntity<MwBizExpDtl> getMwBizExpDtl(@PathVariable Long id) {
        log.debug("REST request to get MwBizExpDtl : {}", id);
        MwBizExpDtl mwBizExpDtl = mwBizExpDtlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBizExpDtl));
    }

    /**
     * DELETE  /mw-biz-exp-dtls/:id : delete the "id" mwBizExpDtl.
     *
     * @param id the id of the mwBizExpDtl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-biz-exp-dtls/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwBizExpDtl(@PathVariable Long id) {
        log.debug("REST request to delete MwBizExpDtl : {}", id);
        mwBizExpDtlService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
