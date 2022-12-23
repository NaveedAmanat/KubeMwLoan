package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwClntRel;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.AddressDto;
import com.idev4.loans.dto.NomineeDto;
import com.idev4.loans.dto.tab.PrevInsuranceMemDto;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.service.MwClntRelService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing MwClntRel.
 */
@RestController
@RequestMapping("/api")
public class MwClntRelResource {

    private static final String ENTITY_NAME = "mwClntRel";
    private final Logger log = LoggerFactory.getLogger(MwClntRelResource.class);
    private final MwClntRelService mwClntRelService;

    private final MwAddrResource mwAddrResource;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    public MwClntRelResource(MwClntRelService mwClntRelService, MwAddrResource mwAddrResource, MwLoanAppRepository mwLoanAppRepository,
                             MwClntRepository mwClntRepository) {
        this.mwClntRelService = mwClntRelService;
        this.mwAddrResource = mwAddrResource;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * POST /mw-clnt-rels : Create a new mwClntRel.
     *
     * @param mwClntRel the mwClntRel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwClntRel, or with status 400 (Bad Request) if the
     * mwClntRel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-clnt-rels")
    @Timed
    public ResponseEntity<MwClntRel> createMwClntRel(@RequestBody MwClntRel mwClntRel) throws URISyntaxException {
        log.debug("REST request to save MwClntRel : {}", mwClntRel);
        if (mwClntRel.getClntRelSeq() != null) {
            throw new BadRequestAlertException("A new mwClntRel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwClntRel result = mwClntRelService.save(mwClntRel);
        return ResponseEntity.created(new URI("/api/mw-clnt-rels/" + result.getClntRelSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getClntRelSeq().toString())).body(result);
    }

    /**
     * PUT /mw-clnt-rels : Updates an existing mwClntRel.
     *
     * @param mwClntRel the mwClntRel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwClntRel, or with status 400 (Bad Request) if the
     * mwClntRel is not valid, or with status 500 (Internal Server Error) if the mwClntRel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-clnt-rels")
    @Timed
    public ResponseEntity<MwClntRel> updateMwClntRel(@RequestBody MwClntRel mwClntRel) throws URISyntaxException {
        log.debug("REST request to update MwClntRel : {}", mwClntRel);
        if (mwClntRel.getClntRelSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwClntRel result = mwClntRelService.save(mwClntRel);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwClntRel.getClntRelSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-clnt-rels : get all the mwClntRels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwClntRels in body
     */
    @GetMapping("/mw-clnt-rels")
    @Timed
    public ResponseEntity<List<MwClntRel>> getAllMwClntRels(Pageable pageable) {
        log.debug("REST request to get a page of MwClntRels");
        Page<MwClntRel> page = mwClntRelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-clnt-rels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-clnt-rels/:id : get the "id" mwClntRel.
     *
     * @param id the id of the mwClntRel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwClntRel, or with status 404 (Not Found)
     */
    @GetMapping("/mw-clnt-rels/{id}")
    @Timed
    public ResponseEntity<MwClntRel> getMwClntRel(@PathVariable Long id) {
        log.debug("REST request to get MwClntRel : {}", id);
        MwClntRel mwClntRel = mwClntRelService.findOne(id);
        return ResponseEntity.ok().body(mwClntRel);
    }

    @PutMapping("/save-clnt-rels")
    @Timed
    public ResponseEntity<Map> saveMwClntRel(@RequestBody NomineeDto dto) throws URISyntaxException {
        log.debug("REST request to save MwClntRel : {}", dto);
        String namesRegex = "^[a-zA-Z\\s]+$";
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.loanAppSeq <= 0) {
            resp.put("error", "Invalid Loan Application Associated !!");
            return ResponseEntity.badRequest().body(resp);
        }

        // if(String.valueOf(dto.cnicNum).length()!=13) {
        // resp.put("error", "Invalid Cnic Number");
        // return ResponseEntity.badRequest().body(resp);
        // }

        if (!dto.firstName.matches(namesRegex)) {
            resp.put("error", "Names have Numbers OR Special Characters !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        long clntSeq = mwClntRelService.saveClientRelative(dto, currUser);

        if (dto.typFlg == 3 || dto.typFlg == 4) {

            MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
            if (app != null) {
                MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
                if (clnt != null) {
                    AddressDto addrDto = getAddressDto(dto);
                    addrDto.cnicNum = clnt.getCnicNum();
                    addrDto.cycleNum = app.getLoanCyclNum();
                    addrDto.clientRelSeq = clntSeq;
                    if (dto.typFlg == 3 && !dto.relAddrAsClntFlg)
                        return mwAddrResource.createCoborrowerAddr(addrDto);
                    if (dto.typFlg == 4)
                        return mwAddrResource.createClientRelAddress(addrDto);
                }
            }

        }
        resp.put("clntRelSeq", String.valueOf(clntSeq));

        return ResponseEntity.ok().body(resp);
    }

    @PutMapping("/update-clnt-rels")
    @Timed
    public ResponseEntity<Map> updateMwClntRel(@RequestBody NomineeDto dto) throws URISyntaxException {

        log.debug("REST request to update MwNom : {}", dto);

        String namesRegex = "^[a-zA-Z\\s]+$";
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.loanAppSeq <= 0) {
            resp.put("error", "Invalid Loan Application Associated !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.clntRelSeq <= 0) {
            resp.put("error", "Invalid Client Rel Seq !!");
            return ResponseEntity.badRequest().body(resp);
        }

        // if(String.valueOf(dto.cnicNum).length()!=13) {
        // resp.put("error", "Invalid Cnic Number");
        // return ResponseEntity.badRequest().body(resp);
        // }

        if (!dto.firstName.matches(namesRegex) || !dto.lastName.matches(namesRegex)) {
            resp.put("error", "Names have Numbers OR Special Characters !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        long clientSeq = mwClntRelService.updateClientRelative(dto, currUser);

        if (clientSeq == 0) {
            resp.put("error", "Client Rel not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        resp.put("nomineeSeq", String.valueOf(dto));

        if ((dto.typFlg == 3 && !dto.relAddrAsClntFlg) || dto.typFlg == 4) {
            AddressDto addrDto = getAddressDto(dto);
            addrDto.addrSeq = dto.addresSeq;
            addrDto.clientRelSeq = clientSeq;
            addrDto.houseNum = dto.houseNum;
            addrDto.sreet_area = dto.sreet_area;
            addrDto.community = dto.community;
            addrDto.village = dto.village;
            addrDto.otherDetails = dto.otherDetails;
            addrDto.city = dto.city;
            addrDto.lat = dto.lat;
            addrDto.lon = dto.lon;
            addrDto.yearsOfResidence = dto.yearsOfResidence;
            addrDto.mnthsOfResidence = dto.mnthsOfResidence;
            addrDto.isPermAddress = dto.isPermAddress;
            MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
            if (app != null) {
                MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
                if (clnt != null) {
                    addrDto.cnicNum = clnt.getCnicNum();
                    addrDto.cycleNum = app.getLoanCyclNum();
                    if (dto.typFlg == 3 && !dto.relAddrAsClntFlg) {
                        if (dto.addresSeq == null) {
                            mwAddrResource.createCoborrowerAddr(addrDto);
                        } else {
                            mwAddrResource.updateCoborrowerAddr(addrDto);
                        }
                    }
                    if (dto.typFlg == 4) {
                        if (dto.addresSeq == null) {
                            mwAddrResource.createClientRelAddress(addrDto);
                        } else {
                            mwAddrResource.updateClientRelAddress(addrDto);
                        }
                    }
                }
            }
        }
        resp.put("clntRelSeq", String.valueOf(clientSeq));
        return ResponseEntity.ok().body(resp);

    }

    @PostMapping("/get-client-rel")
    @Timed
    public ResponseEntity<NomineeDto> getClientrelative(@RequestBody NomineeDto dto) throws URISyntaxException {
        log.debug("REST request to save MwClntRel : {}", dto);
        if (dto.loanAppSeq <= 0) {
            throw new BadRequestAlertException("Missing Loan App ID", ENTITY_NAME, "idexists");
        }
        if (dto.typFlg <= 0) {
            throw new BadRequestAlertException("Missing Type Flag ID", ENTITY_NAME, "idexists");
        }

        Map<String, Object> resp = new HashMap<>();

        NomineeDto resDto = mwClntRelService.getClientRelativeInformation(dto.loanAppSeq, dto.typFlg);

        return ResponseEntity.ok().body(resDto);
    }

    @PostMapping("/get-client-rel-previous-loan")
    @Timed
    public ResponseEntity<NomineeDto> getClientrelativeForPrevoiousLoan(@RequestBody NomineeDto dto) throws URISyntaxException {
        log.debug("REST request to save MwClntRel : {}", dto);
        if (dto.clientSeq <= 0L)
            throw new BadRequestAlertException("Missing Loan App ID", "mwClntRel", "idexists");
        if (dto.typFlg <= 0L)
            throw new BadRequestAlertException("Missing Type Flag ID", "mwClntRel", "idexists");

        Map<String, Object> resp = new HashMap<>();
        NomineeDto resDto = mwClntRelService.getClientRelativeInformationForPreviousLoan(dto.clientSeq, dto.typFlg);
        return ResponseEntity.ok().body(resDto);
    }

    // getClientRelativeeInformation
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

    @GetMapping("/mw-clnt-prvs-insr/{cycl}/{clntSeq}")
    @Timed
    public ResponseEntity<PrevInsuranceMemDto> getPrevInsuranceMemDto(@PathVariable Long cycl, @PathVariable Long clntSeq)
            throws URISyntaxException {
        if (clntSeq <= 0L)
            throw new BadRequestAlertException("Client Sequence Not Found", "mwClntRel", "idexists");

        PrevInsuranceMemDto resDto = mwClntRelService.getPrevInsrPlan(clntSeq, cycl);
        return ResponseEntity.ok().body(resDto);
    }

}
