package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwLoanAppDoc;
import com.idev4.loans.domain.MwLoanAppVerisys;
import com.idev4.loans.service.MwLoanAppDocService;
import com.idev4.loans.web.rest.errors.BadRequestAlertException;
import com.idev4.loans.web.rest.util.HeaderUtil;
import com.idev4.loans.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing MwLoanAppDoc.
 */
@RestController
@RequestMapping("/api")
public class MwLoanAppDocResource {

    private static final String ENTITY_NAME = "mwLoanAppDoc";
    private final Logger log = LoggerFactory.getLogger(MwLoanAppDocResource.class);
    @Autowired
    MwLoanAppDocService mwLoanAppDocService;

    /**
     * POST /mw-loan-app-docs : Create a new mwLoanAppDoc.
     *
     * @param mwLoanAppDoc the mwLoanAppDoc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwLoanAppDoc, or with status 400 (Bad Request) if the
     * mwLoanAppDoc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-loan-app-docs")
    @Timed
    public ResponseEntity<MwLoanAppDoc> createMwLoanAppDoc(@RequestBody MwLoanAppDoc mwLoanAppDoc) throws URISyntaxException {
        log.debug("REST request to save MwLoanAppDoc : {}", mwLoanAppDoc);
        if (mwLoanAppDoc.getLoanAppDocSeq() != null) {
            throw new BadRequestAlertException("A new mwLoanAppDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwLoanAppDoc result = mwLoanAppDocService.save(mwLoanAppDoc);
        return ResponseEntity.created(new URI("/api/mw-loan-app-docs/" + result.getLoanAppDocSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getLoanAppDocSeq().toString())).body(result);
    }

    /**
     * PUT /mw-loan-app-docs : Updates an existing mwLoanAppDoc.
     *
     * @param mwLoanAppDoc the mwLoanAppDoc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwLoanAppDoc, or with status 400 (Bad Request) if the
     * mwLoanAppDoc is not valid, or with status 500 (Internal Server Error) if the mwLoanAppDoc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-loan-app-docs")
    @Timed
    public ResponseEntity<MwLoanAppDoc> updateMwLoanAppDoc(@RequestBody MwLoanAppDoc mwLoanAppDoc) throws URISyntaxException {
        log.debug("REST request to update MwLoanAppDoc : {}", mwLoanAppDoc);
        if (mwLoanAppDoc.getLoanAppDocSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwLoanAppDoc result = mwLoanAppDocService.save(mwLoanAppDoc);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwLoanAppDoc.getLoanAppDocSeq().toString()))
                .body(result);
    }

    // Added by Areeba - Dated - 10-05-2022
    // Halaf Nama
    @PutMapping("/add-mw-loan-app-doc/{seq}")
    @Timed
    public ResponseEntity addMwLoanAppDoc(@PathVariable Long seq, @RequestBody String img) throws URISyntaxException {
        log.debug("REST request to update MwLoanAppDoc : {}");
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer result = mwLoanAppDocService.saveDoc(seq, img, currUser);
        Map<String, Object> respData = new HashMap<>();
        respData.put("AddNewLoanAppDoc", result);
        return ResponseEntity.ok().body(respData);
    }
    // Ended by Areeba


    /**
     * GET /mw-loan-app-docs : get all the mwLoanAppDocs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwLoanAppDocs in body
     */
    @GetMapping("/mw-loan-app-docs")
    @Timed
    public ResponseEntity<List<MwLoanAppDoc>> getAllMwLoanAppDocs(Pageable pageable) {
        log.debug("REST request to get a page of MwLoanAppDocs");
        Page<MwLoanAppDoc> page = mwLoanAppDocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-loan-app-docs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-loan-app-docs/:id : get the "id" mwLoanAppDoc.
     *
     * @param id the id of the mwLoanAppDoc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwLoanAppDoc, or with status 404 (Not Found)
     */
    @GetMapping("/mw-loan-app-docs/{id}")
    @Timed
    public ResponseEntity<MwLoanAppDoc> getMwLoanAppDoc(@PathVariable Long id) {
        log.debug("REST request to get MwLoanAppDoc : {}", id);
        MwLoanAppDoc mwLoanAppDoc = mwLoanAppDocService.findOne(id);
        return ResponseEntity.ok().body(mwLoanAppDoc);
    }

    @GetMapping("/mw-loan-app-docs-for-loan-app/{seq}")
    @Timed
    public ResponseEntity<List<MwLoanAppDoc>> getAllMwLoanAppDocsForLoanApp(@PathVariable Long seq) {
        log.debug("REST request to get a page of MwLoanAppDocs");
        List<MwLoanAppDoc> page = mwLoanAppDocService.findAllByLoanAppSeq(seq);
        // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-loan-app-docs");
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // Modified by Rizwan Mahfooz - Date 07-04-2022 - Loan App Seq
    @GetMapping("/mw-loan-app-docs-for-clnt/{clntSeq}/{cyclNum}/{loanAppSeq}")
    @Timed
    public ResponseEntity<List<MwLoanAppDoc>> getMwLoanApp(@PathVariable Long clntSeq, @PathVariable Long cyclNum, @PathVariable Long loanAppSeq) {
        List<MwLoanAppDoc> page = mwLoanAppDocService.findAllByClntSeqAndCycleNum(clntSeq, cyclNum, loanAppSeq);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/print-verisys-report/{loanApp}/{cnicCat}")
    @Timed
    public ResponseEntity<byte[]> getClientVerisysReport(HttpServletResponse reponse, @PathVariable long loanApp, @PathVariable String cnicCat) throws IOException {

        MwLoanAppVerisys mwDoc = mwLoanAppDocService.findVerisysRecord(loanApp, cnicCat);
        if (mwDoc != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            String filename = "Verisys.jpg";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(mwDoc.getDoc(), headers, HttpStatus.OK);
            return response;
        }
        return null;
    }
}
