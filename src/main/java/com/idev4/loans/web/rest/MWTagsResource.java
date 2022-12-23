package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MWTags;
import com.idev4.loans.dto.ValidationDto;
import com.idev4.loans.service.MWTagsService;
import com.idev4.loans.web.rest.errors.BadRequestAlertException;
import com.idev4.loans.web.rest.util.HeaderUtil;
import com.idev4.loans.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing MWTags.
 */
@RestController
@RequestMapping("/api")
public class MWTagsResource {

    private static final String ENTITY_NAME = "mWTags";
    private final Logger log = LoggerFactory.getLogger(MWTagsResource.class);
    private final MWTagsService mWTagsService;

    public MWTagsResource(MWTagsService mWTagsService) {
        this.mWTagsService = mWTagsService;
    }

    /**
     * POST /mw-tags : Create a new mWTags.
     *
     * @param mWTags the mWTags to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mWTags, or with status 400 (Bad Request) if the mWTags has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-tags")
    @Timed
    public ResponseEntity<MWTags> createMWTags(@RequestBody MWTags mWTags) throws URISyntaxException {
        log.debug("REST request to save MWTags : {}", mWTags);
        if (mWTags.getTagsSeq() != null) {
            throw new BadRequestAlertException("A new mWTags cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MWTags result = mWTagsService.save(mWTags);
        return ResponseEntity.created(new URI("/api/mw-tags/" + result.getTagsSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getTagsSeq().toString())).body(result);
    }

    /**
     * PUT /mw-tags : Updates an existing mWTags.
     *
     * @param mWTags the mWTags to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mWTags, or with status 400 (Bad Request) if the mWTags is
     * not valid, or with status 500 (Internal Server Error) if the mWTags couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-tags")
    @Timed
    public ResponseEntity<MWTags> updateMWTags(@RequestBody MWTags mWTags) throws URISyntaxException {
        log.debug("REST request to update MWTags : {}", mWTags);
        if (mWTags.getTagsSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MWTags result = mWTagsService.save(mWTags);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mWTags.getTagsSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-tags : get all the mWTags.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mWTags in body
     */
    @GetMapping("/mw-tags")
    @Timed
    public ResponseEntity<List<MWTags>> getAllMWTags(Pageable pageable) {
        log.debug("REST request to get a page of MWTags");
        Page<MWTags> page = mWTagsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-tags/:id : get the "id" mWTags.
     *
     * @param id the id of the mWTags to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mWTags, or with status 404 (Not Found)
     */
    @GetMapping("/mw-tags/{id}")
    @Timed
    public ResponseEntity<MWTags> getMWTags(@PathVariable Long id) {
        log.debug("REST request to get MWTags : {}", id);
        MWTags mWTags = mWTagsService.findOne(id);
        return ResponseEntity.ok().body(mWTags);
    }

    @GetMapping("/get_tags_by_user_cnic/{cnic}")
    @Timed
    public ResponseEntity<Map> getMWTags(@PathVariable String cnic) {
        log.debug("REST request to get MWTags : {}", cnic);
        return ResponseEntity.ok().body(mWTagsService.getUserTagsByCnic(cnic));
    }

    @PostMapping("/mw-tags-validation")
    @Timed
    public ResponseEntity<Map> getMWTagsForValidation(@RequestBody ValidationDto dto) {
        log.debug("REST request to get MWTags : {}", dto.toString());
        return ResponseEntity.ok().body(mWTagsService.getUserTagsByCnicforValidation(dto));
    }

    @GetMapping("/mw-tags-validation-for-client/{cnic}")
    @Timed
    public ResponseEntity<Map> getClientHistory(@PathVariable String cnic) {
        log.debug("REST request to get MWTags : {}", cnic);
        if (cnic != null)
            return ResponseEntity.ok().body(mWTagsService.getUserValidation(cnic, true, false, "ClientHistory"));
        throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }

    @PostMapping("/mw-tags-validation-for-clnt-nom-cob")
    @Timed
    public ResponseEntity<Map> getClntNomCobHistory(@RequestBody ValidationDto dto) {
        log.debug("REST request to get MWTags : {}", dto.toString());
        Map<String, Object> resp = new HashMap<String, Object>();
        if (dto.cnicNum != null)
            resp.put("client", mWTagsService.getUserValidation(dto.cnicNum, true, dto.isBm, dto.eventName));
        if (dto.nomCnic != null)
            resp.put("nominee", mWTagsService.getUserValidation(dto.nomCnic, false, dto.isBm, dto.eventName));
        if (dto.cobCnic != null)
            resp.put("coborrower", mWTagsService.getUserValidation(dto.cobCnic, false, dto.isBm, dto.eventName));

        System.out.println("resp : " + resp);

        return ResponseEntity.ok().body(resp);
    }
}
