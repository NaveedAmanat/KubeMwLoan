package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwBizAprslIncmHdr;
import com.idev4.loans.dto.IncomeHdrDto;
import com.idev4.loans.service.MwBizAprslIncmHdrService;
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
 * REST controller for managing MwBizAprslIncmHdr.
 */
@RestController
@RequestMapping("/api")
public class MwBizAprslIncmHdrResource {

    private static final String ENTITY_NAME = "mwBizAprslIncmHdr";
    private final Logger log = LoggerFactory.getLogger(MwBizAprslIncmHdrResource.class);
    private final MwBizAprslIncmHdrService mwBizAprslIncmHdrService;

    public MwBizAprslIncmHdrResource(MwBizAprslIncmHdrService mwBizAprslIncmHdrService) {
        this.mwBizAprslIncmHdrService = mwBizAprslIncmHdrService;
    }

    /**
     * POST  /mw-biz-aprsl-incm-hdrs : Create a new mwBizAprslIncmHdr.
     *
     * @param mwBizAprslIncmHdr the mwBizAprslIncmHdr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwBizAprslIncmHdr, or with status 400 (Bad Request) if the mwBizAprslIncmHdr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-biz-aprsl-incm-hdrs")
    @Timed
    public ResponseEntity<Map> createMwBizAprslIncmHdr(@RequestBody IncomeHdrDto mwBizAprslIncmHdr) throws URISyntaxException {
        log.debug("REST request to save MwBizAprslIncmHdr : {}", mwBizAprslIncmHdr);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwBizAprslIncmHdr.maxSaleMonth < 0 || mwBizAprslIncmHdr.maxSaleMonth > 12) {
            resp.put("error", "Invalid Number of Max Sale Month !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwBizAprslIncmHdr.minSaleMonth < 0 || mwBizAprslIncmHdr.minSaleMonth > 12) {
            resp.put("error", "Invalid Number of Max Sale Month !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwBizAprslIncmHdr.maxMonthSale < 0 || mwBizAprslIncmHdr.minMonthSale < 0) {
            resp.put("error", "Invalid Sales Entered !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        long bizAprslIncmHdrSeq = mwBizAprslIncmHdrService.saveBusinessIncomeHdr(mwBizAprslIncmHdr, currUser);
        resp.put("incomeHdrSeq", String.valueOf(bizAprslIncmHdrSeq));
        return ResponseEntity.ok().body(resp);
    }


    /**
     * PUT  /mw-biz-aprsl-incm-hdrs : Updates an existing mwBizAprslIncmHdr.
     *
     * @param mwBizAprslIncmHdr the mwBizAprslIncmHdr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwBizAprslIncmHdr,
     * or with status 400 (Bad Request) if the mwBizAprslIncmHdr is not valid,
     * or with status 500 (Internal Server Error) if the mwBizAprslIncmHdr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-biz-aprsl-incm-hdrs")
    @Timed
    public ResponseEntity<Map> updateMwBizAprslIncmHdr(@RequestBody IncomeHdrDto mwBizAprslIncmHdr) throws URISyntaxException {
        log.debug("REST request to update MwBizAprslIncmHdr : {}", mwBizAprslIncmHdr);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwBizAprslIncmHdr.bizAprslSeq <= 0) {
            resp.put("error", "Bad Business Seq Found !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwBizAprslIncmHdr.maxSaleMonth < 0 || mwBizAprslIncmHdr.maxSaleMonth > 12) {
            resp.put("error", "Invalid Number of Max Sale Month !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwBizAprslIncmHdr.minSaleMonth < 0 || mwBizAprslIncmHdr.minSaleMonth > 12) {
            resp.put("error", "Invalid Number of Max Sale Month !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwBizAprslIncmHdr.maxMonthSale < 0 || mwBizAprslIncmHdr.minMonthSale < 0) {
            resp.put("error", "Invalid Sales Entered !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        long bizAprslIncmHdrSeq = mwBizAprslIncmHdrService.updateBusinessIncomeHdr(mwBizAprslIncmHdr, currUser);

        if (bizAprslIncmHdrSeq == 0) {
            resp.put("error", "Business Apraisal Income Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("incomeHdrSeq", String.valueOf(bizAprslIncmHdrSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET  /mw-biz-aprsl-incm-hdrs : get all the mwBizAprslIncmHdrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBizAprslIncmHdrs in body
     */
    @GetMapping("/mw-biz-aprsl-incm-hdrs")
    @Timed
    public ResponseEntity<List<MwBizAprslIncmHdr>> getAllMwBizAprslIncmHdrs(Pageable pageable) {
        log.debug("REST request to get a page of MwBizAprslIncmHdrs");
        Page<MwBizAprslIncmHdr> page = mwBizAprslIncmHdrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-biz-aprsl-incm-hdrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mw-biz-aprsl-incm-hdrs/:id : get the "id" mwBizAprslIncmHdr.
     *
     * @param id the id of the mwBizAprslIncmHdr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBizAprslIncmHdr, or with status 404 (Not Found)
     */
    @GetMapping("/mw-biz-aprsl-incm-hdrs/{id}")
    @Timed
    public ResponseEntity<MwBizAprslIncmHdr> getMwBizAprslIncmHdr(@PathVariable Long id) {
        log.debug("REST request to get MwBizAprslIncmHdr : {}", id);
        MwBizAprslIncmHdr mwBizAprslIncmHdr = mwBizAprslIncmHdrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBizAprslIncmHdr));
    }

    /**
     * DELETE  /mw-biz-aprsl-incm-hdrs/:id : delete the "id" mwBizAprslIncmHdr.
     *
     * @param id the id of the mwBizAprslIncmHdr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-biz-aprsl-incm-hdrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwBizAprslIncmHdr(@PathVariable Long id) {
        log.debug("REST request to delete MwBizAprslIncmHdr : {}", id);
        mwBizAprslIncmHdrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
