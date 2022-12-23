package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.*;
import com.idev4.loans.dto.tab.LoanAppMntrngChks;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.service.*;
import com.idev4.loans.web.rest.util.HeaderUtil;
import com.idev4.loans.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing MwLoanApp.
 */
@RestController
@RequestMapping("/api")
public class MwLoanAppResource {

    private static final String ENTITY_NAME = "mwLoanApp";
    private final Logger log = LoggerFactory.getLogger(MwLoanAppResource.class);
    private final MwLoanAppService mwLoanAppService;

    private final MwClntService mwClntService;

    private final MwBizAprslService mwBizAprslService;

    private final UtilService utilService;

    @Autowired
    MwLoanAppRepository mwLoanAppRepository;

    @Autowired
    MwTabService mwTabService;

    public MwLoanAppResource(MwLoanAppService mwLoanAppService, MwClntService mwClntService, MwBizAprslService mwBizAprslService,
                             UtilService utilService) {
        this.mwLoanAppService = mwLoanAppService;
        this.mwClntService = mwClntService;
        this.mwBizAprslService = mwBizAprslService;
        this.utilService = utilService;
    }

    /**
     * POST /mw-loan-apps : Create a new mwLoanApp.
     *
     * @param mwLoanApp the mwLoanApp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwLoanApp, or with status 400 (Bad Request) if the
     * mwLoanApp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-client-loan-application")
    @Timed
    public ResponseEntity<Map> createMwLoanApp(@RequestBody LoanAppDto mwLoanApp) throws URISyntaxException {
        log.debug("REST request to save MwLoanApp : {}", mwLoanApp);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwLoanApp.clientSeq < 0) {
            resp.put("error", "Client Id Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwLoanApp.reqAmount < 0 || mwLoanApp.recAmount < 0) {
            resp.put("error", "Invalid Amounts Entered !!");
            return ResponseEntity.badRequest().body(resp);
        }

        Map<String, Double> data = mwLoanAppService.addNewLoanApp(mwLoanApp, currUser);
        return ResponseEntity.ok().body(data);

    }

    /**
     * PUT /mw-loan-apps : Updates an existing mwLoanApp.
     *
     * @param mwLoanApp the mwLoanApp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwLoanApp, or with status 400 (Bad Request) if the
     * mwLoanApp is not valid, or with status 500 (Internal Server Error) if the mwLoanApp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-loan-apps")
    @Timed
    public ResponseEntity<Map> updateMwLoanApp(@RequestBody LoanAppDto mwLoanApp) throws URISyntaxException {
        log.debug("REST request to update MwLoanApp : {}", mwLoanApp);

        log.debug("REST request to save MwLoanApp : {}", mwLoanApp);
        Map<String, Object> resp = new HashMap<String, Object>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwLoanApp.clientSeq < 0 || mwLoanApp.loanAppSeq <= 0) {
            resp.put("error", "Bad Sequence Values Received !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (mwLoanApp.reqAmount < 0 || mwLoanApp.recAmount < 0) {
            resp.put("error", "Invalid Amounts Entered !!");
            return ResponseEntity.badRequest().body(resp);
        }

        resp = mwLoanAppService.updateNewLoanApp(mwLoanApp, currUser);

        if (resp == null) {
            resp.put("error", "Loan Application Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET /mw-loan-apps : get all the mwLoanApps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwLoanApps in body
     */
    @GetMapping("/mw-loan-apps")
    @Timed
    public ResponseEntity<List<MwLoanApp>> getAllMwLoanApps(Pageable pageable) {
        log.debug("REST request to get a page of MwLoanApps");
        Page<MwLoanApp> page = mwLoanAppService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-loan-apps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-loan-apps/:id : get the "id" mwLoanApp.
     *
     * @param id the id of the mwLoanApp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwLoanApp, or with status 404 (Not Found)
     */
    @GetMapping("/loan-app-by-seq/{loanAppSeq}")
    @Timed
    public ResponseEntity<Map> getMwLoanApp(@PathVariable Long loanAppSeq) {
        log.debug("REST request to get MwLoanApp : {}", loanAppSeq);
        Map appData = mwLoanAppService.findLoanApplication(loanAppSeq);
        return ResponseEntity.ok().body(appData);
    }

    /**
     * DELETE /mw-loan-apps/:id : delete the "id" mwLoanApp.
     *
     * @param id the id of the mwLoanApp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delete-loan-app/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwLoanApp(@PathVariable Long id) {
        log.debug("REST request to delete MwLoanApp : {}", id);
        mwLoanAppService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/user-disbursed-loans/{id}")
    @Timed
    public ResponseEntity<Map> getUserDisbursedLoan(@PathVariable Long clntSeq) {
        log.debug("REST request to get MwLoanApp : {}", clntSeq);
        Map<String, Map> resp = new HashMap<String, Map>();

        Map disbursedLoans = mwLoanAppService.getUserDisbursedLoans(clntSeq);
        resp.put("disbursedLoans", disbursedLoans);

        return ResponseEntity.ok().body(resp);
    }

    /* Get Application for Submit */
    @GetMapping("/loan-app-submit/{loanAppSeq}")
    @Timed
    public ResponseEntity<Map> getMwLoanApptoSubmit(@PathVariable Long loanAppSeq) {
        Map<String, Object> resp = new HashMap<>();
        Map appData = mwLoanAppService.findLoanApplication(loanAppSeq);
        LoanAppDto loandto = (LoanAppDto) appData.get("loanApp");
        PersonalInfoDto personalInfo = mwClntService.findClientInformation(loandto.clientSeq);
        if (personalInfo == null) {
            resp.put("error", "Client not found");
            ResponseEntity.badRequest().body(resp);
        }
        Map bizapr = mwBizAprslService.getBusinessApraisalByLoanApplication(loanAppSeq);

        resp.put("loanApp", appData.get("loanApp"));
        resp.put("forms", appData.get("forms"));
        resp.put("clntInfo", personalInfo);
        resp.put("BusinessIncome", bizapr.get("BusinessIncome"));
        resp.put("primaryIncome", bizapr.get("primaryIncome"));
        resp.put("secondaryIncome", bizapr.get("secondaryIncome"));
        resp.put("businessExpense", bizapr.get("businessExpense"));
        resp.put("householdExpense", bizapr.get("householdExpense"));
        resp.put("BusinessApraisal", bizapr.get("BusinessApraisal"));
        if (personalInfo.portKey > 0)
            resp.put("locationInfo", mwLoanAppService.locationInfoForSubmit(personalInfo.portKey));
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/submit-loan-application")
    @Timed
    public ResponseEntity<?> submitLoan(@RequestBody LoanAppDto mwLoanApp) {
        return ResponseEntity.ok()
                .body(mwLoanAppService.submitApplication(mwLoanApp, SecurityContextHolder.getContext().getAuthentication().getName()));

    }

    @PostMapping("/submit-loan-associate-application")
    @Timed
    public ResponseEntity<Map> submitAssocLoan(@RequestBody LoanAppDto mwLoanApp) {
        log.debug("Submit MwLoanApp : {}", mwLoanApp);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        // Modified By Naveed - Date - 24-02-2022
        // return proper response instead of just string
        Map<String, String> app = mwLoanAppService.submitAssocApplication(mwLoanApp, currUser);
        return ResponseEntity.ok().body(app);
        // Ended By Naveed
    }

    @PostMapping("/cancel-loan-application")
    @Timed
    public ResponseEntity<LoanAppDto> cancelLoan(@RequestBody LoanAppDto mwLoanApp) {

        log.debug("Submit MwLoanApp : {}", mwLoanApp);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        mwLoanAppService.cancelLoan(mwLoanApp, currUser);

        return ResponseEntity.ok().body(mwLoanApp);

    }

    @PostMapping("/approve-loan-application")
    @Timed
    public ResponseEntity<?> approveLoanApp(@RequestBody LoanAppDto mwLoanApp) {
        return mwLoanAppService.approveLoanApplication(mwLoanApp, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("/reject-loan-application")
    @Timed
    public ResponseEntity<LoanAppDto> rejectLoanApp(@RequestBody LoanAppDto mwLoanApp) {

        log.debug("Submit MwLoanApp : {}", mwLoanApp);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwLoanApp app = mwLoanAppService.rejectLoanApplication(mwLoanApp, currUser);

        if (app != null)
            return ResponseEntity.ok().body(mwLoanApp);

        return ResponseEntity.badRequest().body(mwLoanApp);

    }

    @PostMapping("/send-back-loan-application")
    @Timed
    public ResponseEntity<LoanAppDto> sendBackLoanApp(@RequestBody LoanAppDto mwLoanApp) {

        log.debug("Submit MwLoanApp : {}", mwLoanApp);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwLoanApp app = mwLoanAppService.sendBackLoanApplication(mwLoanApp, currUser);

        if (app != null)
            return ResponseEntity.ok().body(mwLoanApp);

        return ResponseEntity.badRequest().body(mwLoanApp);

    }

    // @PostMapping("/assoc-loan-app-submit/{loanAppSeq}")
    // @Timed
    // public ResponseEntity<Map> getAssocMwLoanApptoSubmit(@RequestBody AssocProductDto dto) {
    // Map appData = mwLoanAppService.findLoanApplication(loanAppSeq);
    // LoanAppDto loandto = (LoanAppDto) appData.get("loanApp");
    // PersonalInfoDto personalInfo = mwClntService.findClientInformation(loandto.clientSeq);
    // Map bizapr = mwBizAprslService.getBusinessApraisalByLoanApplication(loanAppSeq);
    // Map <String, Object> resp = new HashMap<>();
    // resp.put("loanApp", appData.get("loanApp"));
    // resp.put("forms", appData.get("forms"));
    // resp.put("clntInfo", personalInfo);
    // resp.put("BusinessIncome", bizapr.get("BusinessIncome"));
    // resp.put("primaryIncome", bizapr.get("primaryIncome"));
    // resp.put("secondaryIncome", bizapr.get("secondaryIncome"));
    // resp.put("businessExpense", bizapr.get("businessExpense"));
    // resp.put("householdExpense", bizapr.get("householdExpense"));
    // resp.put("BusinessApraisal", bizapr.get("BusinessApraisal"));
    // if(personalInfo.portKey > 0)
    // resp.put("locationInfo", mwLoanAppService.locationInfoForSubmit(personalInfo.portKey));
    // return ResponseEntity.ok().body(resp);
    // }

    @GetMapping("/get-due-sheet/{pb}/{seq}")
    @Timed
    public ResponseEntity<List<DueSheetDTO>> getDueSheet(@PathVariable Long pb, @PathVariable Long seq) throws IOException {
        log.debug("REST request to get DUE SHEET" + SecurityContextHolder.getContext().getAuthentication().getName());
        Map<String, Map> resp = new HashMap<String, Map>();

        return ResponseEntity.ok().body(utilService.getDueSheet(pb, seq));
    }

    @GetMapping("/get-od-clients/{pb}/{seq}")
    @Timed
    public ResponseEntity<List<ODClientsDTO>> getOdClients(@PathVariable Long pb, @PathVariable Long seq) throws IOException {
        log.debug("REST request to get DUE SHEET" + SecurityContextHolder.getContext().getAuthentication().getName());
        Map<String, Map> resp = new HashMap<String, Map>();

        return ResponseEntity.ok().body(utilService.getODClients(pb, seq));
    }

    @PostMapping("/update-loan-util-chck")
    @Timed
    public ResponseEntity updateLoanUtlChk(@RequestBody LoanAppDto mwLoanApp) {
        log.debug("Update Loan Utl Chk MwLoanApp : {}", mwLoanApp);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return mwLoanAppService.updateLoanUtlChk(mwLoanApp, currUser);
    }

    @PostMapping("/update-loan-mntry-chck")
    @Timed
    public ResponseEntity updateLoanMntryChk(@RequestBody LoanAppMntrngChks dto) {
        log.debug("Update Loan Mntry Chk LoanAppMntrngChks : {}", dto.toString());
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return mwLoanAppService.saveMntryChk(dto, currUser);
    }

    @GetMapping("/adv-rul-check/{loanAppSeq}")
    @Timed
    public ResponseEntity<?> advRulCheck(@PathVariable Long loanAppSeq) {
        Map<String, String> resp = new HashMap<String, String>();
        String str = mwLoanAppService.ruleCheck(loanAppSeq);
        resp.put("result", str);
        return ResponseEntity.ok().body(resp);
    }

    /*
     * Added by Naveed - Date 11-05-2022
     * Save Disburse posting checklist against loan
     * */
    @PutMapping("/disburse-posting-check-list/{loanAppSeq}")
    public ResponseEntity<?> disbursePostingCheckList(@RequestBody PostingCheckListDto checkListDto, @PathVariable long loanAppSeq) {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        return mwLoanAppService.disbursePostingCheckList(checkListDto, loanAppSeq, currUser);
    }

    @GetMapping("/get-disburse-posting-check-list/{loanAppSeq}")
    public ResponseEntity<?> getDisbursePostingCheckList(@PathVariable long loanAppSeq) {
        return mwLoanAppService.getDisbursePostingCheckList(loanAppSeq);
    }
    // End by Naveed
}
