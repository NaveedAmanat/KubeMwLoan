package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwAddr;
import com.idev4.loans.dto.AddressDto;
import com.idev4.loans.service.MwAddrRelService;
import com.idev4.loans.service.MwAddrService;
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
 * REST controller for managing MwAddr.
 */
@RestController
@RequestMapping("/api")
public class MwAddrResource {

    private static final String ENTITY_NAME = "mwAddr";
    private final Logger log = LoggerFactory.getLogger(MwAddrResource.class);
    private final MwAddrService mwAddrService;

    private final MwAddrRelService mwAddrRelService;

    public MwAddrResource(MwAddrService mwAddrService, MwAddrRelService mwAddrRelService) {
        this.mwAddrService = mwAddrService;
        this.mwAddrRelService = mwAddrRelService;
    }

    @GetMapping("/get-address-combinations")
    public List doTest() {
        log.debug("Address Combination Service called !!");
        return mwAddrService.findAddressOptions();
    }

    @GetMapping("/get-address-combinations/{id}")
    public List getAddress(@PathVariable Long id) {
        log.debug("Address Combination Service called !!");
        return mwAddrService.findAddressOptions(id);
    }

    /**
     * POST /mw-addrs : Create a new mwAddr.
     *
     * @param mwAddr the mwAddr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwAddr, or with status 400 (Bad Request) if the mwAddr has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-client-addr")
    @Timed
    public ResponseEntity<Map> createClientAddr(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity<Map> error = doAddressValidation(mwAddr);
        if (error != null)
            return error;

        mwAddrService.saveAddress(mwAddr, currUser, "Client");

        resp.put("clientSeq", String.valueOf(mwAddr.clientSeq));
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/update-client-addr")
    @Timed
    public ResponseEntity<Map> updateClientAddr(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity<Map> error = doAddressValidation(mwAddr);
        if (error != null)
            return error;

        long clntSeq = mwAddrService.updateAddress(mwAddr, currUser);
        if (clntSeq == 0) {
            clntSeq = mwAddrService.saveAddress(mwAddr, currUser, "Client");
        }
        resp.put("clientSeq", String.valueOf(mwAddr.clientSeq));
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/create-nominee-addr")
    @Timed
    public ResponseEntity<Map> createNomineeAddr(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        mwAddrService.saveAddress(mwAddr, currUser, "Nominee");
        resp.put("nomineeSeq", String.valueOf(mwAddr.nomineeSeq));
        return ResponseEntity.ok().body(resp);
    }

    @PutMapping("/update-nominee-addr")
    @Timed
    public ResponseEntity<Map> updateNomineeAddr(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        long addrSeq = mwAddrService.updateAddress(mwAddr, currUser);

        if (addrSeq == 0) {
            resp.put("error", "Nominee Address Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("nomineeSeq", String.valueOf(mwAddr.nomineeSeq));
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/create-coborrower-addr")
    @Timed
    public ResponseEntity<Map> createCoborrowerAddr(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity<Map> error = doAddressValidation(mwAddr);
        if (error != null)
            return error;
        mwAddrService.saveAddress(mwAddr, currUser, "CoBorrower");
        resp.put("coBorrowerSeq", String.valueOf(mwAddr.clientRelSeq));
        return ResponseEntity.ok().body(resp);
    }

    @PutMapping("/update-coborrower-addr")
    @Timed
    public ResponseEntity<Map> updateCoborrowerAddr(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity<Map> error = doAddressValidation(mwAddr);
        if (error != null)
            return error;
        long addrSeq = mwAddrService.updateAddress(mwAddr, currUser);

        if (addrSeq == 0) {
            resp.put("error", "Coborrower Address Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("coBorrowerSeq", String.valueOf(mwAddr.coBorrowerSeq));
        return ResponseEntity.ok().body(resp);
    }

    public ResponseEntity<Map> createClientRelAddress(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        if (mwAddr.community == null) {
            mwAddr.community = -1L;
        }
        log.debug("REST request to save Client Relative MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity<Map> error = doAddressValidation(mwAddr);
        if (error != null)
            return error;
        mwAddrService.saveAddress(mwAddr, currUser, "ClientRel");
        resp.put("clientRelSeq", String.valueOf(mwAddr.clientRelSeq));
        return ResponseEntity.ok().body(resp);
    }

    public ResponseEntity<Map> updateClientRelAddress(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity<Map> error = doAddressValidation(mwAddr);
        if (error != null)
            return error;
        long addrSeq = mwAddrService.updateAddress(mwAddr, currUser);

        if (addrSeq == 0) {
            resp.put("error", "Client Relative Address Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("clientRelSeq", String.valueOf(mwAddr.clientRelSeq));
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/create-business-addr")
    @Timed
    public ResponseEntity<Map> createBusinessAddr(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity<Map> error = doAddressValidation(mwAddr);
        if (error != null)
            return error;
        long addrSeq = mwAddrService.saveAddress(mwAddr, currUser, "Business");
        resp.put("businessSeq", String.valueOf(mwAddr.bizSeq));
        resp.put("addrSeq", String.valueOf(addrSeq));
        return ResponseEntity.ok().body(resp);
    }

    @PutMapping("/update-business-addr")
    @Timed
    public ResponseEntity<Map> updateBusinessAddr(@RequestBody AddressDto mwAddr) throws URISyntaxException {
        log.debug("REST request to save MwAddr : {}", mwAddr);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity<Map> error = doAddressValidation(mwAddr);
        if (error != null)
            return error;

        long addrSeq = mwAddrService.updateAddress(mwAddr, currUser);
        if (addrSeq == 0) {
            resp.put("error", "Business Address Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        resp.put("businessSeq", String.valueOf(mwAddr.bizSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT /mw-addrs : Updates an existing mwAddr.
     *
     * @param mwAddr the mwAddr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwAddr, or with status 400 (Bad Request) if the mwAddr is
     * not valid, or with status 500 (Internal Server Error) if the mwAddr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-addrs")
    @Timed
    public ResponseEntity<MwAddr> updateMwAddr(@RequestBody MwAddr mwAddr) throws URISyntaxException {
        log.debug("REST request to update MwAddr : {}", mwAddr);

        MwAddr result = mwAddrService.save(mwAddr);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwAddr.getAddrSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-addrs : get all the mwAddrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwAddrs in body
     */
    @GetMapping("/mw-addrs")
    @Timed
    public ResponseEntity<List<MwAddr>> getAllMwAddrs(Pageable pageable) {
        log.debug("REST request to get a page of MwAddrs");
        Page<MwAddr> page = mwAddrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-addrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-addrs/:id : get the "id" mwAddr.
     *
     * @param id the id of the mwAddr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwAddr, or with status 404 (Not Found)
     */
    @GetMapping("/mw-addrs/{id}")
    @Timed
    public ResponseEntity<MwAddr> getMwAddr(@PathVariable Long id) {
        log.debug("REST request to get MwAddr : {}", id);
        MwAddr mwAddr = mwAddrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwAddr));
    }

    private ResponseEntity<Map> doAddressValidation(AddressDto mwAddr) {
        Map<String, String> resp = new HashMap<String, String>();
        if (mwAddr.yearsOfResidence != null) {
            if (mwAddr.yearsOfResidence > 99 || mwAddr.yearsOfResidence < 0) {
                resp.put("error", "Invalid Years of Residence !! ");
                return ResponseEntity.badRequest().body(resp);
            }
        }

        if (mwAddr.houseNum == null || mwAddr.houseNum.isEmpty()) {
            resp.put("error", "House Num is Missing !! ");
            return ResponseEntity.badRequest().body(resp);
        }
        if (mwAddr.mnthsOfResidence != null) {
            if (mwAddr.mnthsOfResidence > 99 || mwAddr.mnthsOfResidence < 0) {
                resp.put("error", "Invalid Months of Residence !! ");
                return ResponseEntity.badRequest().body(resp);
            }
        }
        if (mwAddr.village == null || mwAddr.village.isEmpty()) {
            resp.put("error", "House Num is Missing !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwAddr.otherDetails == null || mwAddr.otherDetails.isEmpty()) {
            resp.put("error", "House Num is Missing !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwAddr.city <= 0) {
            resp.put("error", "Invalid City Selected !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        return null;
    }
}
