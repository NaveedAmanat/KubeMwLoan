package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwCbwr;
import com.idev4.loans.dto.AddressDto;
import com.idev4.loans.dto.NomineeDto;
import com.idev4.loans.service.MwCbwrService;
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
 * REST controller for managing MwCbwr.
 */
@RestController
@RequestMapping("/api")
public class MwCbwrResource {

    private static final String ENTITY_NAME = "mwCbwr";
    private final Logger log = LoggerFactory.getLogger(MwCbwrResource.class);
    private final MwCbwrService mwCbwrService;

    private final MwAddrResource mwAddrResource;

    public MwCbwrResource(MwCbwrService mwCbwrService, MwAddrResource mwAddrResource) {
        this.mwCbwrService = mwCbwrService;
        this.mwAddrResource = mwAddrResource;
    }

    /**
     * POST /mw-cbwrs : Create a new mwCbwr.
     *
     * @param mwCbwr the mwCbwr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCbwr, or with status 400 (Bad Request) if the mwCbwr has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-application-coborrower")
    @Timed
    public ResponseEntity<Map> createMwCbwr(@RequestBody NomineeDto mwNom) throws URISyntaxException {
        log.debug("REST request to save coborrower : {}", mwNom);
        String namesRegex = "^[a-zA-Z\\s]+$";
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (String.valueOf(mwNom.cnicNum).length() != 13) {
            resp.put("error", "Invalid Cnic Number");
            return ResponseEntity.badRequest().body(resp);
        }

        if (!mwNom.firstName.matches(namesRegex) || !mwNom.lastName.matches(namesRegex)) {
            resp.put("error", "Names have Numbers OR Special Characters !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        long cbwrSeq = mwCbwrService.addNewCbwr(mwNom, currUser);

        AddressDto addrDto = new AddressDto();
        addrDto.coBorrowerSeq = cbwrSeq;
        addrDto.houseNum = mwNom.houseNum;
        addrDto.sreet_area = mwNom.sreet_area;
        addrDto.community = mwNom.community;
        addrDto.village = mwNom.village;
        addrDto.otherDetails = mwNom.otherDetails;
        addrDto.city = mwNom.city;
        addrDto.lat = mwNom.lat;
        addrDto.lon = mwNom.lon;
        addrDto.yearsOfResidence = mwNom.yearsOfResidence;
        addrDto.mnthsOfResidence = mwNom.mnthsOfResidence;
        addrDto.isPermAddress = mwNom.isPermAddress;

        return mwAddrResource.createCoborrowerAddr(addrDto);
    }

    /**
     * PUT /mw-noms : Updates an existing mwNom.
     *
     * @param mwNom the mwNom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwNom, or with status 400 (Bad Request) if the mwNom is not
     * valid, or with status 500 (Internal Server Error) if the mwNom couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-app-coborrower")
    @Timed
    public ResponseEntity<Map> updateMwNom(@RequestBody NomineeDto mwNom) throws URISyntaxException {

        log.debug("REST request to update coborrower : {}", mwNom);

        String namesRegex = "^[a-zA-Z\\s]+$";
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwNom.isCoBorrower) {
            if (mwNom.coBorrowerSeq <= 0) {
                resp.put("error", "Invalid CoBorrower Seq !!");
                return ResponseEntity.badRequest().body(resp);
            }
        } else {
            if (mwNom.nomineeSeq <= 0) {
                resp.put("error", "Invalid Nominee Seq !!");
                return ResponseEntity.badRequest().body(resp);
            }
        }

        if (String.valueOf(mwNom.cnicNum).length() != 13) {
            resp.put("error", "Invalid Cnic Number");
            return ResponseEntity.badRequest().body(resp);
        }

        if (!mwNom.firstName.matches(namesRegex) || !mwNom.lastName.matches(namesRegex)) {
            resp.put("error", "Names have Numbers OR Special Characters !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        long cbwrSeq = mwCbwrService.updateExistingCbwr(mwNom, currUser);

        if (cbwrSeq == 0) {
            resp.put("error", "Coborrower not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        AddressDto addrDto = new AddressDto();
        addrDto.addrSeq = mwNom.addresSeq;
        addrDto.coBorrowerSeq = cbwrSeq;
        addrDto.houseNum = mwNom.houseNum;
        addrDto.sreet_area = mwNom.sreet_area;
        addrDto.community = mwNom.community;
        addrDto.village = mwNom.village;
        addrDto.otherDetails = mwNom.otherDetails;
        addrDto.city = mwNom.city;
        addrDto.lat = mwNom.lat;
        addrDto.lon = mwNom.lon;
        addrDto.yearsOfResidence = mwNom.yearsOfResidence;
        addrDto.mnthsOfResidence = mwNom.mnthsOfResidence;
        addrDto.isPermAddress = mwNom.isPermAddress;

        return mwAddrResource.updateCoborrowerAddr(addrDto);

    }

    /**
     * GET /mw-cbwrs : get all the mwCbwrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCbwrs in body
     */
    @GetMapping("/mw-cbwrs")
    @Timed
    public ResponseEntity<List<MwCbwr>> getAllMwCbwrs(Pageable pageable) {
        log.debug("REST request to get a page of MwCbwrs");
        Page<MwCbwr> page = mwCbwrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-cbwrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-cbwrs/:id : get the "id" mwCbwr.
     *
     * @param id the id of the mwCbwr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCbwr, or with status 404 (Not Found)
     */
    @GetMapping("/mw-cbwrs/{id}")
    @Timed
    public ResponseEntity<MwCbwr> getMwCbwr(@PathVariable Long id) {
        log.debug("REST request to get MwCbwr : {}", id);
        Optional<MwCbwr> mwCbwr = mwCbwrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mwCbwr);
    }

    @GetMapping("/get-coborrower-of-loan-app/{loanAppSeq}")
    @Timed
    public ResponseEntity<NomineeDto> getMwNomOfClient(@PathVariable Long loanAppSeq) {
        log.debug("REST request to get MwNom : {}", loanAppSeq);
        NomineeDto mwNom = mwCbwrService.getExistingCbwrByApplication(loanAppSeq);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwNom));
    }
}
