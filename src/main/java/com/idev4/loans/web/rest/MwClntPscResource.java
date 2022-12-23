package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwClntPsc;
import com.idev4.loans.dto.ClientPscDto;
import com.idev4.loans.service.MwClntPscService;
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
 * REST controller for managing MwClntPsc.
 */
@RestController
@RequestMapping("/api")
public class MwClntPscResource {

    private static final String ENTITY_NAME = "mwClntPsc";
    private final Logger log = LoggerFactory.getLogger(MwClntPscResource.class);
    private final MwClntPscService mwClntPscService;

    public MwClntPscResource(MwClntPscService mwClntPscService) {
        this.mwClntPscService = mwClntPscService;
    }

    /**
     * POST /mw-clnt-pscs : Create a new mwClntPsc.
     *
     * @param mwClntPsc the mwClntPsc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwClntPsc, or with status 400 (Bad Request) if the
     * mwClntPsc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-clnt-pscs")
    @Timed
    public ResponseEntity<Map> createMwClntPsc(@RequestBody List<ClientPscDto> dtos) throws URISyntaxException {
        log.debug("REST request to save MwClntPsc : {}", dtos);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        for (ClientPscDto dto : dtos) {

            if (dto.questionSeq <= 0 || dto.answerSeq <= 0 || dto.loanAppSeq <= 0) {
                resp.put("error", "Data Missing in PSC !!");
                return ResponseEntity.badRequest().body(resp);
            }

        }

        mwClntPscService.saveClientQuestionsAnswer(dtos, currUser);
        resp.put("success", "Answers Saved Successfully !!");
        return ResponseEntity.ok().body(resp);

    }

    /**
     * PUT /mw-clnt-pscs : Updates an existing mwClntPsc.
     *
     * @param mwClntPsc the mwClntPsc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwClntPsc, or with status 400 (Bad Request) if the
     * mwClntPsc is not valid, or with status 500 (Internal Server Error) if the mwClntPsc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-clnt-pscs")
    @Timed
    public ResponseEntity<Map> updateMwClntPsc(@RequestBody List<ClientPscDto> dtos) throws URISyntaxException {
        log.debug("REST request to update MwClntPsc : {}", dtos);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        for (ClientPscDto dto : dtos) {

            if (dto.questionSeq <= 0 || dto.answerSeq <= 0 || dto.loanAppSeq <= 0) {
                resp.put("error", "Data Missing in PSC !!");
                return ResponseEntity.badRequest().body(resp);
            }

        }

        mwClntPscService.updateClientQuestionsAnswer(dtos, currUser);
        resp.put("error", "Answers Saved Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET /mw-clnt-pscs : get all the mwClntPscs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwClntPscs in body
     */
    @GetMapping("/mw-clnt-pscs")
    @Timed
    public ResponseEntity<List<MwClntPsc>> getAllMwClntPscs(Pageable pageable) {
        log.debug("REST request to get a page of MwClntPscs");
        Page<MwClntPsc> page = mwClntPscService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-clnt-pscs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-clnt-pscs/:id : get the "id" mwClntPsc.
     *
     * @param id the id of the mwClntPsc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwClntPsc, or with status 404 (Not Found)
     */
    @GetMapping("/mw-clnt-pscs/{id}")
    @Timed
    public ResponseEntity<MwClntPsc> getMwClntPsc(@PathVariable Long id) {
        log.debug("REST request to get MwClntPsc : {}", id);
        MwClntPsc mwClntPsc = mwClntPscService.findOne(id);
        return ResponseEntity.ok().body(mwClntPsc);
    }

    @GetMapping("/get-user-qst-answrs/{loanAppSeq}")
    @Timed
    public ResponseEntity<List<ClientPscDto>> getUserQuestionAnswers(@PathVariable Long loanAppSeq) {
        log.debug("REST request to get MwClntPsc : {}", loanAppSeq);
        List<ClientPscDto> mwClntPsc = mwClntPscService.getLoanAppSeqQuestionsAnswer(loanAppSeq);
        return ResponseEntity.ok().body(mwClntPsc);
    }
}
