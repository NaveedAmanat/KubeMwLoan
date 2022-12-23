package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwHlthInsrPlan;
import com.idev4.loans.service.MwHlthInsrPlanService;
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
 * REST controller for managing MwHlthInsrPlan.
 */
@RestController
@RequestMapping("/api")
public class MwHlthInsrPlanResource {

    private static final String ENTITY_NAME = "mwHlthInsrPlan";
    private final Logger log = LoggerFactory.getLogger(MwHlthInsrPlanResource.class);
    private final MwHlthInsrPlanService mwHlthInsrPlanService;

    public MwHlthInsrPlanResource(MwHlthInsrPlanService mwHlthInsrPlanService) {
        this.mwHlthInsrPlanService = mwHlthInsrPlanService;
    }

    /**
     * POST /mw-hlth-insr-plans : Create a new mwHlthInsrPlan.
     *
     * @param mwHlthInsrPlan the mwHlthInsrPlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwHlthInsrPlan, or with status 400 (Bad Request) if the
     * mwHlthInsrPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-hlth-insr-plans")
    @Timed
    public ResponseEntity<MwHlthInsrPlan> createMwHlthInsrPlan(@RequestBody MwHlthInsrPlan mwHlthInsrPlan) throws URISyntaxException {
        log.debug("REST request to save MwHlthInsrPlan : {}", mwHlthInsrPlan);
        if (mwHlthInsrPlan.getHlthInsrPlanSeq() != null) {
            throw new BadRequestAlertException("A new mwHlthInsrPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwHlthInsrPlan result = mwHlthInsrPlanService.save(mwHlthInsrPlan);
        return ResponseEntity.created(new URI("/api/mw-hlth-insr-plans/" + result.getHlthInsrPlanSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getHlthInsrPlanSeq().toString())).body(result);
    }

    /**
     * PUT /mw-hlth-insr-plans : Updates an existing mwHlthInsrPlan.
     *
     * @param mwHlthInsrPlan the mwHlthInsrPlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwHlthInsrPlan, or with status 400 (Bad Request) if the
     * mwHlthInsrPlan is not valid, or with status 500 (Internal Server Error) if the mwHlthInsrPlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-hlth-insr-plans")
    @Timed
    public ResponseEntity<MwHlthInsrPlan> updateMwHlthInsrPlan(@RequestBody MwHlthInsrPlan mwHlthInsrPlan) throws URISyntaxException {
        log.debug("REST request to update MwHlthInsrPlan : {}", mwHlthInsrPlan);
        if (mwHlthInsrPlan.getHlthInsrPlanSeq() == null) {
            return createMwHlthInsrPlan(mwHlthInsrPlan);
        }
        MwHlthInsrPlan result = mwHlthInsrPlanService.save(mwHlthInsrPlan);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwHlthInsrPlan.getHlthInsrPlanSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-hlth-insr-plans : get all the mwHlthInsrPlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwHlthInsrPlans in body
     */
    @GetMapping("/get-hlth-insr-plans")
    @Timed
    public ResponseEntity<List<MwHlthInsrPlan>> getAllMwHlthInsrPlans(Pageable pageable) {
        log.debug("REST request to get a page of MwHlthInsrPlans");
        List<MwHlthInsrPlan> plans = mwHlthInsrPlanService.getAllHlthInsurancePlans();
        return ResponseEntity.ok().body(plans);
    }

    /**
     * GET /mw-hlth-insr-plans/:id : get the "id" mwHlthInsrPlan.
     *
     * @param id the id of the mwHlthInsrPlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwHlthInsrPlan, or with status 404 (Not Found)
     */
    @GetMapping("/mw-hlth-insr-plans/{id}")
    @Timed
    public ResponseEntity<MwHlthInsrPlan> getMwHlthInsrPlan(@PathVariable Long id) {
        log.debug("REST request to get MwHlthInsrPlan : {}", id);
        MwHlthInsrPlan mwHlthInsrPlan = mwHlthInsrPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwHlthInsrPlan));
    }

}
