package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwNom;
import com.idev4.loans.dto.AddressDto;
import com.idev4.loans.dto.NomineeDto;
import com.idev4.loans.service.MwNomService;
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
 * REST controller for managing MwNom.
 */
@RestController
@RequestMapping("/api")
public class MwNomResource {

    private static final String ENTITY_NAME = "mwNom";
    private final Logger log = LoggerFactory.getLogger(MwNomResource.class);
    private final MwNomService mwNomService;

    private final MwAddrResource mwAddrResource;

    public MwNomResource(MwNomService mwNomService, MwAddrResource mwAddrResource) {
        this.mwNomService = mwNomService;
        this.mwAddrResource = mwAddrResource;
    }

    /**
     * POST /mw-noms : Create a new mwNom.
     *
     * @param mwNom the mwNom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwNom, or with status 400 (Bad Request) if the mwNom has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-client-nominee")
    @Timed
    public ResponseEntity<Map> createMwNom(@RequestBody NomineeDto mwNom) throws URISyntaxException {
        log.debug("REST request to save MwNom : {}", mwNom);
        String namesRegex = "^[a-zA-Z\\s]+$";
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwNom.loanAppSeq <= 0) {
            resp.put("error", "Invalid Loan Application Associated !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (String.valueOf(mwNom.cnicNum).length() != 13) {
            resp.put("error", "Invalid Cnic Number");
            return ResponseEntity.badRequest().body(resp);
        }

        if (!mwNom.firstName.matches(namesRegex) || !mwNom.lastName.matches(namesRegex)) {
            resp.put("error", "Names have Numbers OR Special Characters !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        long nomSeq = mwNomService.addNomineeForClient(mwNom, currUser);

        AddressDto addrDto = getAddressDto(mwNom);
        addrDto.nomineeSeq = nomSeq;

        return mwAddrResource.createNomineeAddr(addrDto);
    }

    /**
     * PUT /mw-noms : Updates an existing mwNom.
     *
     * @param mwNom the mwNom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwNom, or with status 400 (Bad Request) if the mwNom is not
     * valid, or with status 500 (Internal Server Error) if the mwNom couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-noms")
    @Timed
    public ResponseEntity<Map> updateMwNom(@RequestBody NomineeDto mwNom) throws URISyntaxException {

        log.debug("REST request to update MwNom : {}", mwNom);

        String namesRegex = "^[a-zA-Z\\s]+$";
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwNom.loanAppSeq <= 0) {
            resp.put("error", "Invalid Loan Application Associated !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwNom.nomineeSeq <= 0) {
            resp.put("error", "Invalid Nominee Seq !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (String.valueOf(mwNom.cnicNum).length() != 13) {
            resp.put("error", "Invalid Cnic Number");
            return ResponseEntity.badRequest().body(resp);
        }

        if (!mwNom.firstName.matches(namesRegex) || !mwNom.lastName.matches(namesRegex)) {
            resp.put("error", "Names have Numbers OR Special Characters !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        long nomSeq = mwNomService.updateNomineeForClient(mwNom, currUser);

        if (nomSeq == 0) {
            resp.put("error", "Nominee not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        resp.put("nomineeSeq", String.valueOf(nomSeq));
        // AddressDto addrDto = getAddressDto(mwNom);
        // addrDto.addrSeq = mwNom.addresSeq;

        return ResponseEntity.ok(resp);

    }

    /**
     * GET /mw-noms : get all the mwNoms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwNoms in body
     */
    @GetMapping("/mw-noms")
    @Timed
    public ResponseEntity<List<MwNom>> getAllMwNoms(Pageable pageable) {
        log.debug("REST request to get a page of MwNoms");
        Page<MwNom> page = mwNomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-noms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-noms/:id : get the "id" mwNom.
     *
     * @param id the id of the mwNom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwNom, or with status 404 (Not Found)
     */
    @GetMapping("/mw-noms/{id}")
    @Timed
    public ResponseEntity<MwNom> getMwNom(@PathVariable Long id) {
        log.debug("REST request to get MwNom : {}", id);
        MwNom mwNom = mwNomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwNom));
    }

    @GetMapping("/get-nominee-of-loan-app/{loanAppSeq}")
    @Timed
    public ResponseEntity<NomineeDto> getMwNomOfClient(@PathVariable Long loanAppSeq) {
        log.debug("REST request to get MwNom : {}", loanAppSeq);
        NomineeDto mwNom = mwNomService.getNomineeOfLoanApplication(loanAppSeq);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwNom));
    }

    private AddressDto getAddressDto(NomineeDto mwNom) {
        AddressDto addrDto = new AddressDto();
        addrDto.addrSeq = mwNom.addresSeq;
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
        return addrDto;
    }
}
