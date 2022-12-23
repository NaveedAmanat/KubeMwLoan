package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwCity;
import com.idev4.loans.service.MwCityService;
import com.idev4.loans.web.rest.errors.BadRequestAlertException;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MwCity.
 */
@RestController
@RequestMapping("/api")
public class MwCityResource {

    private static final String ENTITY_NAME = "mwCity";
    private final Logger log = LoggerFactory.getLogger(MwCityResource.class);
    private final MwCityService mwCityService;

    public MwCityResource(MwCityService mwCityService) {
        this.mwCityService = mwCityService;
    }

    /**
     * POST /mw-cities : Create a new mwCity.
     *
     * @param mwCity the mwCity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCity, or with status 400 (Bad Request) if the mwCity has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-cities")
    @Timed
    public ResponseEntity<MwCity> createMwCity(@RequestBody MwCity mwCity) throws URISyntaxException {
        log.debug("REST request to save MwCity : {}", mwCity);
        if (mwCity.getCitySeq() != null) {
            throw new BadRequestAlertException("A new mwCity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwCity result = mwCityService.save(mwCity);
        return ResponseEntity.created(new URI("/api/mw-cities/" + result.getCitySeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getCitySeq().toString())).body(result);
    }

    /**
     * PUT /mw-cities : Updates an existing mwCity.
     *
     * @param mwCity the mwCity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwCity, or with status 400 (Bad Request) if the mwCity is
     * not valid, or with status 500 (Internal Server Error) if the mwCity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-cities")
    @Timed
    public ResponseEntity<MwCity> updateMwCity(@RequestBody MwCity mwCity) throws URISyntaxException {
        log.debug("REST request to update MwCity : {}", mwCity);
        if (mwCity.getCitySeq() == null) {
            return createMwCity(mwCity);
        }
        MwCity result = mwCityService.save(mwCity);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwCity.getCitySeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-cities : get all the mwCities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCities in body
     */
    @GetMapping("/mw-cities")
    @Timed
    public ResponseEntity<List<MwCity>> getAllMwCities(Pageable pageable) {
        log.debug("REST request to get a page of MwCities");
        Page<MwCity> page = mwCityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-cities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-cities/:id : get the "id" mwCity.
     *
     * @param id the id of the mwCity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCity, or with status 404 (Not Found)
     */
    @GetMapping("/mw-cities/{id}")
    @Timed
    public ResponseEntity<MwCity> getMwCity(@PathVariable Long id) {
        log.debug("REST request to get MwCity : {}", id);
        MwCity mwCity = mwCityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCity));
    }

}
