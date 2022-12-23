package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwQst;
import com.idev4.loans.dto.QuestionDto;
import com.idev4.loans.dto.SchoolQuestionsDto;
import com.idev4.loans.service.MwQstService;
import com.idev4.loans.web.rest.errors.BadRequestAlertException;
import com.idev4.loans.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MwQst.
 */
@RestController
@RequestMapping("/api")
public class MwQstResource {

    private static final String ENTITY_NAME = "mwQst";
    private final Logger log = LoggerFactory.getLogger(MwQstResource.class);
    private final MwQstService mwQstService;

    public MwQstResource(MwQstService mwQstService) {
        this.mwQstService = mwQstService;
    }

    /**
     * POST /mw-qsts : Create a new mwQst.
     *
     * @param mwQst the mwQst to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwQst, or with status 400 (Bad Request) if the mwQst has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-qsts")
    @Timed
    public ResponseEntity<MwQst> createMwQst(@RequestBody MwQst mwQst) throws URISyntaxException {
        log.debug("REST request to save MwQst : {}", mwQst);
        if ((Object) mwQst.getQstSeq() != null) {
            throw new BadRequestAlertException("A new mwQst cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwQst result = mwQstService.save(mwQst);
        return ResponseEntity.created(new URI("/api/mw-qsts/" + result.getQstSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, ((Object) result.getQstSeq()).toString()))
                .body(result);
    }

    /**
     * PUT /mw-qsts : Updates an existing mwQst.
     *
     * @param mwQst the mwQst to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwQst, or with status 400 (Bad Request) if the mwQst is not
     * valid, or with status 500 (Internal Server Error) if the mwQst couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-qsts")
    @Timed
    public ResponseEntity<MwQst> updateMwQst(@RequestBody MwQst mwQst) throws URISyntaxException {
        log.debug("REST request to update MwQst : {}", mwQst);
        // if ((Object)mwQst.getQstSeq() == null) {
        // return createMwQst(mwQst);
        // }
        MwQst result = mwQstService.updateQst(mwQst);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ((Object) mwQst.getQstSeq()).toString()))
                .body(result);
    }

    /**
     * GET /mw-qsts : get all the mwQsts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwQsts in body
     */
    @GetMapping("/get-qst-answr-list")
    @Timed
    public ResponseEntity<List<QuestionDto>> getAllMwQsts(Pageable pageable) {
        log.debug("REST request to get a page of MwQsts");
        List<QuestionDto> ques = mwQstService.getAllActiveQuestions();
        return ResponseEntity.ok().body(ques);
    }

    @GetMapping("/get-qst-list/{type}")
    @Timed
    public ResponseEntity<List<QuestionDto>> getAllMwQsts(@PathVariable Long type) {
        log.debug("REST request to get MwQsts for type " + type);
        List<QuestionDto> ques = mwQstService.getAllActiveQuestionsByType(type);
        return ResponseEntity.ok().body(ques);
    }

    @GetMapping("/get-qst-list-by-type/{type}")
    @Timed
    public ResponseEntity<List<SchoolQuestionsDto>> getAllMwQstsByType(@PathVariable Long type) {
        log.debug("REST request to get MwQsts for type " + type);
        List<SchoolQuestionsDto> ques = mwQstService.getAllQuestionsForByType(type);
        return ResponseEntity.ok().body(ques);
    }

    /**
     * GET /mw-qsts/:id : get the "id" mwQst.
     *
     * @param id the id of the mwQst to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwQst, or with status 404 (Not Found)
     */
    @GetMapping("/mw-qsts/{id}")
    @Timed
    public ResponseEntity<MwQst> getMwQst(@PathVariable Long id) {
        log.debug("REST request to get MwQst : {}", id);
        MwQst mwQst = mwQstService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwQst));
    }

}
