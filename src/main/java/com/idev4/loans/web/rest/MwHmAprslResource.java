package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwHmAprsl;
import com.idev4.loans.dto.HmAppraisalDto;
import com.idev4.loans.service.MwHmAprslService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwHmAprsl.
 */
@RestController
@RequestMapping("/api")
public class MwHmAprslResource {

    private static final String ENTITY_NAME = "MwHmAprsl";
    private final Logger log = LoggerFactory.getLogger(MwHmAprslResource.class);
    private final MwHmAprslService mwHmAprslService;

    public MwHmAprslResource(MwHmAprslService mwHmAprslService) {
        this.mwHmAprslService = mwHmAprslService;
    }

    /**
     * POST /mw-hm-aprsl : Create a new HmAprsl.
     * <p>
     * the MwHmAprsl to create
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new MwHmAprsl
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-hm-Aprsl")
    @Timed
    public ResponseEntity<MwHmAprsl> createMwHmAprsl(@RequestBody MwHmAprsl mwHmAprsl) throws URISyntaxException {
        log.debug("REST request to save MwHmAprsl : {}", mwHmAprsl);
        MwHmAprsl result = mwHmAprslService.save(mwHmAprsl);
        return ResponseEntity.created(new URI("/api/mw-hm-Aprsl/" + result.getBizAprslSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getBizAprslSeq().toString())).body(result);
    }

    /**
     * PUT /mw-hm-Aprsl : Updates an existing MwHmAprsl.
     *
     * @param mwHmAprsl the MwHmAprsl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated MwHmAprsl, or with status 400 (Bad Request) if the MwHmAprsl is
     * not valid, or with status 500 (Internal Server Error) if the MwHmAprsl couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-hm-Aprsl")
    @Timed
    public ResponseEntity<MwHmAprsl> updateMwHmAprsl(@RequestBody MwHmAprsl mwHmAprsl) throws URISyntaxException {
        log.debug("REST request to update MwHmAprsl : {}", mwHmAprsl);
        if (mwHmAprsl.getBizAprslSeq() == null) {
            return createMwHmAprsl(mwHmAprsl);
        }
        MwHmAprsl result = mwHmAprslService.save(mwHmAprsl);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwHmAprsl.getBizAprslSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-hm-Aprsl : get all the MwHmAprsl.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwHmAprsl in body
     */
    @GetMapping("/mw-hm-Aprsl")
    @Timed
    public ResponseEntity<List<MwHmAprsl>> getAllMwHmAprsl(Pageable pageable) {
        log.debug("REST request to get a page of MwHmAprsl");
        Page<MwHmAprsl> page = mwHmAprslService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-hm-Aprsl");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-hm-Aprsl/:id : get the "id" MwHmAprsl.
     *
     * @param id the id of the MwHmAprsl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the MwHmAprsl, or with status 404 (Not Found)
     */
    @GetMapping("/mw-hm-Aprsl/{id}")
    @Timed
    public ResponseEntity<MwHmAprsl> getMwHmAprsl(@PathVariable Long id) {
        log.debug("REST request to get MwHmAprsl : {}", id);
        MwHmAprsl mwHmAprsl = mwHmAprslService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwHmAprsl));
    }

    @PostMapping("/add-new-hm-aprsl")
    @Timed
    public ResponseEntity<Map> createMwHmAprsl(@RequestBody HmAppraisalDto hmAppraisalDto) throws URISyntaxException {

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        HmAppraisalDto dto = mwHmAprslService.addNewHmAprsl(hmAppraisalDto, currUser);
        Map<String, HmAppraisalDto> respData = new HashMap<String, HmAppraisalDto>();
        respData.put("HmAppraisalDto", dto);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-hm-aprsl/{seq}")
    @Timed
    public ResponseEntity getHmAppraisal(@PathVariable Long seq) {

        HmAppraisalDto hmAppraisal = mwHmAprslService.getHmAppraisal(seq);
        return ResponseEntity.ok().body(hmAppraisal);
    }

    @PutMapping("/update-hm-aprsl")
    @Timed
    public ResponseEntity updateMwHmAprsl(@RequestBody HmAppraisalDto hmAppraisalDto) throws URISyntaxException {

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (hmAppraisalDto.getHmAprslSeq() <= 0) {
            resp.put("error", "Home Appraisal Seq is missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        HmAppraisalDto hmAppraisal = mwHmAprslService.updateHmAppraisal(hmAppraisalDto, currUser);
        Map<String, Object> respData = new HashMap<>();
        respData.put("HmAppraisalDto", hmAppraisal);
        return ResponseEntity.ok().body(respData);
    }
}