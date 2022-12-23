package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.AddressDto;
import com.idev4.loans.dto.AppDto;
import com.idev4.loans.dto.ClntHeatlhInsuranceCardDto;
import com.idev4.loans.dto.PersonalInfoDto;
import com.idev4.loans.dto.tab.DashboardDto;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.service.MwClntHlthInsrCardService;
import com.idev4.loans.service.MwClntPermAddrService;
import com.idev4.loans.service.MwClntService;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing MwClnt.
 */
@RestController
@RequestMapping("/api")
public class MwClntResource {

    private static final String ENTITY_NAME = "mwClnt";
    private final Logger log = LoggerFactory.getLogger(MwClntResource.class);
    private final MwClntService mwClntService;

    private final MwClntHlthInsrCardService mwClntHlthInsrCardService;

    private final MwAddrResource mwAddrResource;

    private final MwClntPermAddrService mwClntPermAddrService;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    public MwClntResource(MwClntService mwClntService, MwAddrResource mwAddrResource, MwClntPermAddrService mwClntPermAddrService,
                          MwClntHlthInsrCardService mwClntHlthInsrCardService, MwLoanAppRepository mwLoanAppRepository,
                          MwClntRepository mwClntRepository) {
        this.mwClntService = mwClntService;
        this.mwAddrResource = mwAddrResource;
        this.mwClntPermAddrService = mwClntPermAddrService;
        this.mwClntHlthInsrCardService = mwClntHlthInsrCardService;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * POST /mw-clnts : Create a new mwClnt.
     *
     * @param mwClnt the mwClnt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwClnt, or with status 400 (Bad Request) if the mwClnt has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-mw-clnt")
    @Timed
    public ResponseEntity<Object> createMwClnt(@RequestBody PersonalInfoDto mwClnt) throws URISyntaxException {
        log.debug("REST request to save MwClnt : {}", mwClnt);
        Map<String, String> resp = new HashMap<String, String>();

        if (String.valueOf(mwClnt.cnicNum).length() != 13) {
            resp.put("error", "Invalid Cnic Number");
            return ResponseEntity.badRequest().body(resp);
        }
        if (mwClnt.expiryDate == null) {
            resp.put("error", "Invalid Cnic Expiry Date !!");
            return ResponseEntity.badRequest().body(resp);
        }

        PersonalInfoDto client = mwClntService.getNewClientId(mwClnt);
        return ResponseEntity.ok().body(client);
    }

    /**
     * PUT /mw-clnts : Updates an existing mwClnt.
     *
     * @param mwClnt the mwClnt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwClnt, or with status 400 (Bad Request) if the mwClnt is
     * not valid, or with status 500 (Internal Server Error) if the mwClnt couldn't be updated
     * @throws URISyntaxException       if the Location URI syntax is incorrect
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @PutMapping("/update-mw-clnt")
    @Timed
    public ResponseEntity<Map> updateMwClnt(@RequestBody PersonalInfoDto mwClnt)
            throws URISyntaxException, IllegalArgumentException, IllegalAccessException {
        log.debug("REST request to update MwClnt : {}", mwClnt);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity<Map> error = doClientValidation(mwClnt);
        if (error != null)
            return error;

        long clntS = mwClntService.updateClientBasicInfo(mwClnt, currUser);

        if (clntS == 0) {
            resp.put("error", "Client Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        long permSeq = 0L;
        if (!mwClnt.isPermAddress) {
            permSeq = mwClntPermAddrService.addPermAddress(mwClnt, currUser);
        }
        AddressDto addrDto = findAddressFromClientInfo(mwClnt);
        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(clntS, true);
        // if ( clnt != null ) {
        // MwLoanApp app = mwLoanAppRepository.findOneByClntSeqAndCrntRecFlg( clntS, true );
        // if ( app != null ) {
        // addrDto.cycleNum = app.getLoanCyclNum();
        // addrDto.cnicNum = clnt.getCnicNum();
        // return mwAddrResource.createClientAddr( addrDto );
        // }
        // }
        try {
            if (Long.parseLong(mwClnt.loanAppSeq) != 0L) {
                MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(Long.parseLong(mwClnt.loanAppSeq), true);
                if (app != null) {
                    addrDto.cycleNum = app.getLoanCyclNum();
                    addrDto.cnicNum = clnt.getCnicNum();
                    return mwAddrResource.createClientAddr(addrDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        resp.put("clientSeq", String.valueOf(clntS));
        return ResponseEntity.ok().body(resp);
    }

    @PutMapping("/update-mw-clnt-on-update")
    @Timed
    public ResponseEntity<Map> updateMwClntOnUpdate(@RequestBody PersonalInfoDto mwClnt)
            throws URISyntaxException, IllegalArgumentException, IllegalAccessException {
        log.debug("REST request to update MwClnt : {}", mwClnt);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity<Map> error = doClientValidation(mwClnt);
        if (error != null)
            return error;
        long clntSeq = mwClntService.updateClientBasicInfoOnUpdate(mwClnt, currUser);

        if (clntSeq == 0) {
            resp.put("error", "Client Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long permSeq = 0L;
        if (!mwClnt.isPermAddress) {
            permSeq = mwClntPermAddrService.updatePermAddress(mwClnt, currUser);
        }

        AddressDto addrDto = findAddressFromClientInfo(mwClnt);

        try {
            if (Long.parseLong(mwClnt.loanAppSeq) != 0L) {
                MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(Long.parseLong(mwClnt.loanAppSeq), true);
                if (app != null) {
                    addrDto.cycleNum = app.getLoanCyclNum();
                    addrDto.cnicNum = mwClnt.cnicNum;
                    if (mwClnt.addresSeq == null) {
                        return mwAddrResource.createClientAddr(addrDto);
                    } else {
                        return mwAddrResource.updateClientAddr(addrDto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg( clntSeq, true );
        // if ( clnt != null ) {
        // MwLoanApp app = mwLoanAppRepository.findOneByClntSeqAndCrntRecFlg( clntSeq, true );
        // if ( app != null ) {
        // addrDto.cycleNum = app.getLoanCyclNum();
        // addrDto.cnicNum = clnt.getCnicNum();
        // mwAddrResource.updateClientAddr( addrDto );
        // }
        // }

        // try {
        // if ( Long.parseLong( mwClnt.loanAppSeq ) != 0L ) {
        // MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg( Long.parseLong( mwClnt.loanAppSeq ), true );
        // if ( app != null ) {
        // addrDto.cycleNum = app.getLoanCyclNum();
        // addrDto.cnicNum = clnt.getCnicNum();
        // return mwAddrResource.createClientAddr( addrDto );
        // }
        // }
        // } catch ( Exception e ) {
        // e.printStackTrace();
        // }

        resp.put("clientSeq", String.valueOf(clntSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET /mw-clnts : get all the mwClnts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwClnts in body
     */
    @GetMapping("/mw-clnts")
    @Timed
    public ResponseEntity<List<MwClnt>> getAllMwClnts(Pageable pageable) {
        log.debug("REST request to get a page of MwClnts");
        Page<MwClnt> page = mwClntService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-clnts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-clnts/:id : get the "id" mwClnt.
     *
     * @param id the id of the mwClnt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwClnt, or with status 404 (Not Found)
     */
    @GetMapping("/mw-clnts/{id}")
    @Timed
    public ResponseEntity<PersonalInfoDto> getMwClnt(@PathVariable Long id) {
        log.debug("REST request to get MwClnt : {}", id);
        PersonalInfoDto mwClnt = mwClntService.findClientInformation(id);
        return ResponseEntity.ok().body(mwClnt);
    }

    @GetMapping("/mw-clnts-loans/{id}")
    @Timed
    public ResponseEntity<PersonalInfoDto> getMwClntLoan(@PathVariable Long id) {
        log.debug("REST request to get MwClnt : {}", id);
        PersonalInfoDto mwClnt = mwClntService.findClientLoansInformation(id);
        return ResponseEntity.ok().body(mwClnt);
    }

    @GetMapping("/get-clnt-ifactive-loan/{clntId}")
    @Timed
    public ResponseEntity<MwClnt> getActiveLoanClient(@PathVariable String clntId) {
        return ResponseEntity.ok().body(mwClntService.getActiveLoanClnt(clntId));
    }

    @GetMapping("/get-clnt-by-cnic/{cnic}")
    @Timed
    public ResponseEntity<MwClnt> getClientByCNIC(@PathVariable Long cnic) {
        return ResponseEntity.ok().body(mwClntService.getClientByCNIC(cnic));
    }

    @GetMapping("/get-clnt-for-brnch/{brnchSeq}")
    @Timed
    public ResponseEntity<List> getClientsForBranch(@PathVariable Long brnchSeq) {
        return ResponseEntity.ok().body(mwClntService.getClientsForBranch(brnchSeq));
    }

    @GetMapping("/get-clients-listing")
    @Timed
    public ResponseEntity<List<AppDto>> getClientsListing() {
        List<AppDto> res = mwClntService.getClientsListing();
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/get-clients-listing-for-user/{userId}")
    @Timed
    public ResponseEntity<List<AppDto>> getClientsListingForUser(@PathVariable String userId) {
        List<AppDto> res = mwClntService.getClientsListingForUserId(userId);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/get-clients-listing-for-user/{userId}/{role}")
    @Timed
    public ResponseEntity<List<AppDto>> getClientsListingForUserBasedOnRole(@PathVariable String userId, @PathVariable String role) {
        List<AppDto> res = mwClntService.getClientsListingForUserId(userId, role);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/get-clients-listing-for-user")
    public ResponseEntity<Map> getClientsListingForUserBasedOnRole(@RequestParam String user, @RequestParam String role,
                                                                   @RequestParam Integer pageIndex, @RequestParam(required = false) String filter,
                                                                   @RequestParam(required = false) String sort, @RequestParam(required = false) String direction,
                                                                   @RequestParam Integer pageSize, @RequestParam(required = false) Long brnchSeq) {
        return ResponseEntity.ok()
                .body(mwClntService.getClientsListingForUserId(user, role, pageIndex, pageSize, sort, direction, filter, brnchSeq));
    }

    // @PostMapping ( "/get-clients-listing-for-user" )
    // @Timed
    // public ResponseEntity< List< AppDto > > getClientsListingForUser( @RequestBody FilterDTO dto ) {
    // List< AppDto > res = mwClntService.getClientsListingForUserId( dto );
    // return ResponseEntity.ok().body( res );
    // }

    @GetMapping("/get-clients-listing-with-filters/{type}/{seq}")
    @Timed
    public ResponseEntity<List<AppDto>> getClientsListingWithFilters(@PathVariable String type, @PathVariable long seq) {
        List<AppDto> res = mwClntService.getClientsListingWithFilters(type, seq);
        return ResponseEntity.ok().body(res);
    }

    private ResponseEntity<Map> doClientValidation(PersonalInfoDto mwClnt) {
        Map<String, String> resp = new HashMap<String, String>();
        String namesRegex = "^[a-zA-Z \\s]+$";
        if (mwClnt.clientSeq <= 0) {
            resp.put("error", "Invalid or Missing ClientSeq !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (mwClnt.firstName != null) {
            if (!mwClnt.firstName.matches(namesRegex)) {
                resp.put("error", "Names have Numbers OR Special Characters !! ");
                return ResponseEntity.badRequest().body(resp);
            }
        } else {
            resp.put("error", "Missing Names !! ");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwClnt.numOfDependts > 99 || mwClnt.numOfDependts < 0) {
            resp.put("error", "Invalid Number of Dependants.");
            return ResponseEntity.badRequest().body(resp);
        }
        if (mwClnt.numOfChidren > 99 || mwClnt.numOfChidren < 0) {
            resp.put("error", "Invalid Number of Children.");
            return ResponseEntity.badRequest().body(resp);
        }
        if (mwClnt.earningMembers > 99 || mwClnt.earningMembers < 0) {
            resp.put("error", "Invalid Number of Earning Members.");
            return ResponseEntity.badRequest().body(resp);
        }
        if (mwClnt.fathrFirstName != null && !mwClnt.fathrFirstName.isEmpty() && mwClnt.fathrLastName != null
                && !mwClnt.fathrLastName.isEmpty()) {
            if (!mwClnt.fathrFirstName.matches(namesRegex) || !mwClnt.fathrLastName.matches(namesRegex)) {
                resp.put("error", "Names have Numbers OR Special Characters.");
                return ResponseEntity.badRequest().body(resp);
            }
        } else {
            // resp.put("error", "Missing Father's Name !! ");
            // return ResponseEntity.badRequest().body(resp);
        }
        if (mwClnt.spzFirstName != null && !mwClnt.spzFirstName.isEmpty() && mwClnt.spzLastName != null
                && !mwClnt.spzLastName.isEmpty()) {
            if (!mwClnt.spzFirstName.matches(namesRegex) || !mwClnt.spzLastName.matches(namesRegex)) {
                resp.put("error", "Spouse Name has Special Characters.");
                return ResponseEntity.badRequest().body(resp);
            }
        }
        if (mwClnt.maritalStatusKey <= 0 || mwClnt.eduLvlKey <= 0 || mwClnt.occupationKey <= 0 || mwClnt.genderKey <= 0) {
            resp.put("error", "Invalid Value from Dropdowns.");
            return ResponseEntity.badRequest().body(resp);
        }
        if (mwClnt.dob == null) {
            resp.put("error", "Date of Birth is Null.");
            return ResponseEntity.badRequest().body(resp);
        }

        return null;
    }

    @GetMapping("/update-isNomDetailAvailable/{id}/{flag}")
    @Timed
    public ResponseEntity<String> setFlag(@PathVariable Long id, @PathVariable Boolean flag) {
        log.debug("REST request to get MwClnt : {}", id);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        mwClntService.updateFlag(id, flag, currUser);
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/update-isSAN/{id}/{flag}")
    @Timed
    public ResponseEntity<String> setFlagisSAN(@PathVariable Long id, @PathVariable Boolean flag) {
        log.debug("REST request to get MwClnt : {}", id);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        mwClntService.updateFlagisSan(id, flag, currUser);
        return ResponseEntity.ok().body("");
    }

    private AddressDto findAddressFromClientInfo(PersonalInfoDto mwClnt) {
        AddressDto addrDto = new AddressDto();
        addrDto.addrSeq = mwClnt.addresSeq;
        addrDto.clientSeq = mwClnt.clientSeq;
        addrDto.houseNum = mwClnt.houseNum;
        addrDto.sreet_area = mwClnt.sreet_area;
        addrDto.community = mwClnt.community;
        addrDto.village = mwClnt.village;
        addrDto.otherDetails = mwClnt.otherDetails;
        addrDto.city = mwClnt.city;
        addrDto.lat = mwClnt.lat;
        addrDto.lon = mwClnt.lon;
        addrDto.yearsOfResidence = mwClnt.yearsOfResidence;
        addrDto.mnthsOfResidence = mwClnt.mnthsOfResidence;
        addrDto.isPermAddress = mwClnt.isPermAddress;
        return addrDto;
    }

    @PostMapping("/add-health-card")
    @Timed
    public ResponseEntity<ClntHeatlhInsuranceCardDto> getClientsListingForUser(@RequestBody ClntHeatlhInsuranceCardDto dto) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        ClntHeatlhInsuranceCardDto res = mwClntHlthInsrCardService.createHealthCard(dto, currUser);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/get-client-history/{clientSeq}")
    @Timed
    public ResponseEntity<Map> getClientHistory(@PathVariable Long clientSeq) {
        log.debug("REST request to get MwClntHistory : {}", clientSeq);
        Map<String, Object> resp = mwClntService.getClientHistory(clientSeq);
        if (resp == null) {
            resp = new HashMap<>();
            resp.put("error", "Client not found");
            ResponseEntity.badRequest().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/get-client-history-tab/{clientSeq}")
    @Timed
    public ResponseEntity<Map> getClientHistoryForTab(@PathVariable Long clientSeq) {
        log.debug("REST request to get MwClntHistory : {}", clientSeq);
        Map<String, Object> resp = mwClntService.getClientHistoryForTab(clientSeq);
        if (resp == null) {
            resp = new HashMap<>();
            resp.put("error", "Client not found");
            ResponseEntity.badRequest().body(resp);
        }
        return ResponseEntity.ok().body(resp);
    }


    @PostMapping("/get-dashboard-data")
    @Timed
    public ResponseEntity getDashboardData(@RequestBody DashboardDto dto) {
        log.debug("REST request to get DASHBOARD DATA : {}", dto.toString());
        if (dto.startDate == null || dto.startDate.length() == 0 || dto.endDate == null || dto.endDate.length() == 0 || dto.grps == null
                || dto.grps.size() <= 0 || dto.grps == null || dto.grps.size() <= 0)
            return ResponseEntity.badRequest().body("{\"error\":\"Parameters Missing.\"}");
        return ResponseEntity.ok().body(mwClntService.getDashboardData(dto));
    }

    @GetMapping("/get-dashboard-data/{brnchSeq}")
    @Timed
    public ResponseEntity<Map> getDashboardData(@PathVariable Long brnchSeq) throws IOException {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("REST request to get MwClntHistory : {}", currUser);
        Map<String, Object> resp = mwClntService.getDashboardDataForTab(currUser, brnchSeq);
        return ResponseEntity.ok().body(resp);
    }

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * Client wise  Portfolio Transfer */
    @PutMapping("/update-client-port")
    @Timed
    public ResponseEntity updatePortfolio(@RequestBody AppDto[] dtos) {
        return mwClntService.updatePortfolio(dtos);
    }

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * All  Portfolio Transfer */
    @GetMapping("/transfer-all-port/{fromPort}/{fromBranch}/{toPort}/{toBrnch}")
    @Timed
    public ResponseEntity transferAllPort(@PathVariable long fromPort, @PathVariable long fromBranch, @PathVariable long toPort, @PathVariable long toBrnch) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return mwClntService.CompletePortfoliTransfer(fromPort, fromBranch, toPort, toBrnch, currUser);
    }

    @GetMapping("/get-cnic-upd")
    @Timed
    public ResponseEntity<List> getCnicUpd() throws IOException {
        return ResponseEntity.ok().body(mwClntService.getUpdForUser());
    }

    // Edited by Areeba
    @GetMapping("/approve-cnic-upd/{loanAppSeq}/{cmnt:.+}/{cnicNum}/{relTyp}")
    @Timed
    public ResponseEntity<Map> approveCnicUpd(@PathVariable Long loanAppSeq, @PathVariable String cmnt, @PathVariable Long cnicNum, @PathVariable String relTyp) throws IOException {
        Map<String, String> resp = new HashMap<String, String>();
        if (loanAppSeq == null || loanAppSeq == 0L) {
            resp.put("error", "Loan App Seq is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long seq = mwClntService.approveCnicUpd(loanAppSeq, cmnt, cnicNum, relTyp);

        if (seq == 0) {
            resp.put("error", "Cnic Upd Not Found");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("success", "Cnic Upd Approved");
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/reject-cnic-upd/{loanAppSeq}/{cmnt:.+}")
    @Timed
    public ResponseEntity<Map> rejectCnicUpd(@PathVariable Long loanAppSeq, @PathVariable String cmnt) throws IOException {
        Map<String, String> resp = new HashMap<String, String>();
        if (loanAppSeq == null || loanAppSeq == 0L) {
            resp.put("error", "Loan App Seq is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long seq = mwClntService.rejectCnicUpd(loanAppSeq, cmnt);

        if (seq == 0) {
            resp.put("error", "Cnic Upd Not Found");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("success", "Cnic Upd Approved");
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/send-back-cnic-upd/{loanAppSeq}/{cmnt:.+}")
    @Timed
    public ResponseEntity<Map> sndBckCnicUpd(@PathVariable Long loanAppSeq, @PathVariable String cmnt) throws IOException {
        Map<String, String> resp = new HashMap<String, String>();
        if (loanAppSeq == null || loanAppSeq == 0L) {
            resp.put("error", "Loan App Seq is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long seq = mwClntService.sendBckCnicUpd(loanAppSeq, cmnt);

        if (seq == 0L) {
            resp.put("error", "Cnic Upd Not Found");
            return ResponseEntity.badRequest().body(resp);
        }

        resp.put("success", "Cnic Upd Approved");
        return ResponseEntity.ok().body(resp);
    }


}
