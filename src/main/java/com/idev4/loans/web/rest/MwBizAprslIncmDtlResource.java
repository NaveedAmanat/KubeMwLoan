package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwBizAprslIncmDtl;
import com.idev4.loans.dto.IncomeDtlDto;
import com.idev4.loans.service.MwBizAprslIncmDtlService;
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
 * REST controller for managing MwBizAprslIncmDtl.
 */
@RestController
@RequestMapping("/api")
public class MwBizAprslIncmDtlResource {

    private static final String ENTITY_NAME = "mwBizAprslIncmDtl";
    private final Logger log = LoggerFactory.getLogger(MwBizAprslIncmDtlResource.class);
    private final MwBizAprslIncmDtlService mwBizAprslIncmDtlService;

    public MwBizAprslIncmDtlResource(MwBizAprslIncmDtlService mwBizAprslIncmDtlService) {
        this.mwBizAprslIncmDtlService = mwBizAprslIncmDtlService;
    }

    /**
     * POST  /mw-biz-aprsl-incm-dtls : Create a new mwBizAprslIncmDtl.
     *
     * @param mwBizAprslIncmDtl the mwBizAprslIncmDtl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwBizAprslIncmDtl, or with status 400 (Bad Request) if the mwBizAprslIncmDtl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-biz-aprsl-incm-dtls")
    @Timed
    public ResponseEntity<Map> createMwBizAprslIncmDtl(@RequestBody IncomeDtlDto mwBizAprslIncmDtl) throws URISyntaxException {
        log.debug("REST request to save MwBizAprslIncmDtl : {}", mwBizAprslIncmDtl);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwBizAprslIncmDtl.incomeAmount < 0 || Long.parseLong(mwBizAprslIncmDtl.IncomeHdrSeq) < 0 || mwBizAprslIncmDtl.incomeTypeKey < 0) {
            resp.put("error", "Invalid Amount Or Income Header !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long incomeDtlSeq = mwBizAprslIncmDtlService.addBusinessIncomeDetails(mwBizAprslIncmDtl, currUser);
        resp.put("incomeDtlSeq", String.valueOf(incomeDtlSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT  /mw-biz-aprsl-incm-dtls : Updates an existing mwBizAprslIncmDtl.
     *
     * @param mwBizAprslIncmDtl the mwBizAprslIncmDtl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwBizAprslIncmDtl,
     * or with status 400 (Bad Request) if the mwBizAprslIncmDtl is not valid,
     * or with status 500 (Internal Server Error) if the mwBizAprslIncmDtl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-biz-aprsl-incm-dtls")
    @Timed
    public ResponseEntity<Map> updateMwBizAprslIncmDtl(@RequestBody IncomeDtlDto mwBizAprslIncmDtl) throws URISyntaxException {
        log.debug("REST request to update MwBizAprslIncmDtl : {}", mwBizAprslIncmDtl);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (Long.parseLong(mwBizAprslIncmDtl.incomeDtlSeq) < 0) {
            resp.put("error", "Bad Income Detail Seq !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwBizAprslIncmDtl.incomeAmount < 0 || Long.parseLong(mwBizAprslIncmDtl.IncomeHdrSeq) < 0 || mwBizAprslIncmDtl.incomeTypeKey < 0) {
            resp.put("error", "Invalid Amount Or Income Header !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long incomeDtlSeq = mwBizAprslIncmDtlService.updateBusinessIncomeDetails(mwBizAprslIncmDtl, currUser);

        if (incomeDtlSeq == 0) {
            resp.put("error", "Business Apraisal Income Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("incomeDtlSeq", String.valueOf(incomeDtlSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET  /mw-biz-aprsl-incm-dtls : get all the mwBizAprslIncmDtls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBizAprslIncmDtls in body
     */
    @GetMapping("/mw-biz-aprsl-incm-dtls")
    @Timed
    public ResponseEntity<List<MwBizAprslIncmDtl>> getAllMwBizAprslIncmDtls(Pageable pageable) {
        log.debug("REST request to get a page of MwBizAprslIncmDtls");
        Page<MwBizAprslIncmDtl> page = mwBizAprslIncmDtlService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-biz-aprsl-incm-dtls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mw-biz-aprsl-incm-dtls/:id : get the "id" mwBizAprslIncmDtl.
     *
     * @param id the id of the mwBizAprslIncmDtl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBizAprslIncmDtl, or with status 404 (Not Found)
     */
    @GetMapping("/mw-biz-aprsl-incm-dtls/{id}")
    @Timed
    public ResponseEntity<MwBizAprslIncmDtl> getMwBizAprslIncmDtl(@PathVariable Long id) {
        log.debug("REST request to get MwBizAprslIncmDtl : {}", id);
        MwBizAprslIncmDtl mwBizAprslIncmDtl = mwBizAprslIncmDtlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBizAprslIncmDtl));
    }

    /**
     * DELETE  /mw-biz-aprsl-incm-dtls/:id : delete the "id" mwBizAprslIncmDtl.
     *
     * @param id the id of the mwBizAprslIncmDtl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-biz-aprsl-incm-dtls/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwBizAprslIncmDtl(@PathVariable Long id) {
        log.debug("REST request to delete MwBizAprslIncmDtl : {}", id);
        mwBizAprslIncmDtlService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
