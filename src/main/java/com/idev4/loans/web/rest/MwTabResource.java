package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwRefCdVal;
import com.idev4.loans.dto.CreditBureauDto;
import com.idev4.loans.dto.krkRecAmntDto;
import com.idev4.loans.dto.tab.*;
import com.idev4.loans.repository.MwRefCdValRepository;
import com.idev4.loans.service.CreditBureau;
import com.idev4.loans.service.MwTabService;
import com.idev4.loans.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

//import singleWsdl.wsdl.CR;

@RestController
@RequestMapping("/api")
public class MwTabResource {

    private static MwTabService mwTabService;
    private static CreditBureau creditBureau;
    private final Logger log = LoggerFactory.getLogger(MwTabResource.class);
    @Autowired
    MwRefCdValRepository mwRefCdValRepository;

    @Autowired
    EntityManager em;
    // private static MwLoanAppDocRepository mwLoanAppDocRepository;

    public MwTabResource(MwTabService mwTabService, CreditBureau creditBureau) {
        this.mwTabService = mwTabService;
        this.creditBureau = creditBureau;
    }

    @PutMapping("/submit-complete-application")
    @Timed
    public ResponseEntity<?> submitApplication(@RequestBody TabAppDto dto) throws URISyntaxException, ParseException, Exception {
        log.debug("REST request to update MWTags : {}", dto.toString());

        Map<String, String> resp = new HashMap<String, String>();

        try {

            // added by Yousaf Dated: 13-SEP-2022
            // THIS CODE IS FOR GESA ATTENDANCE CHECK FOR EACH BDO AND BM
            String msg = "";
            Query q = em.createNativeQuery(
                            "select FN_Check_Attendance_Status(:userid) from dual")
                    .setParameter("userid", SecurityContextHolder.getContext().getAuthentication().getName());

            try {
                msg = q.getSingleResult().toString();
            } catch (Exception e) {
                msg = "Data Not Found";
            }

            if (msg.contains("Dear")) {
                resp.put("gesaAttStatus", msg);
                resp.put("canProceed", "false");
                return ResponseEntity.ok().body(resp);
            }

            String formValidateKbkResp = mwTabService.verifyRecommendedAmountForKrkSubmit(dto);
            if (!formValidateKbkResp.equals("clear")) {
                resp.put("status", "5");
                resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
                resp.put("message", formValidateKbkResp);
                resp.put("canProceed", "false");
                return ResponseEntity.ok().body(resp);
            }

            String formValidateResp = mwTabService.formValidationCheck(dto);
            if (!formValidateResp.equals("0")) {
                resp.put("status", "5");
                resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
                resp.put("message", "Client/Nominee CNIC Issue date is Invalid");
                resp.put("canProceed", "false");
                return ResponseEntity.ok().body(resp);
            }

            String verisys = mwTabService.verifyNadraVerisys(dto);
            if (!verisys.equals("0")) {
                resp.put("status", "5");
                resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
                resp.put("message", "NADRA verification is pending");
                resp.put("canProceed", "false");
                return ResponseEntity.ok().body(resp);
            }

            String cbwrCnicNactaCheck = mwTabService.cbrwrNactaCheck(dto);
            if (cbwrCnicNactaCheck.length() > 0) {
                resp.put("status", "5");
                resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
                resp.put("message", cbwrCnicNactaCheck);
                resp.put("canProceed", "false");
                return ResponseEntity.ok().body(resp);
            }

            ResponseEntity<Map> result = mwTabService.submitApplication(dto, SecurityContextHolder.getContext().getAuthentication().getName());

            mwTabService.saveClientVerisys(result, dto, SecurityContextHolder.getContext().getAuthentication().getName());

            boolean nactaCnicTagged = dto.loan_info == null ? false : mwTabService.nactaVerification(dto.loan_info.loan_app_seq);
            if (nactaCnicTagged) {
                resp.put("status", "3");
                MwRefCdVal nactaRefCdVal = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1642");
                resp.put("loan_app_sts", "" + nactaRefCdVal.getRefCdSeq());
                resp.put("message", "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.");
                resp.put("canProceed", "true");
                return ResponseEntity.ok().body(resp);
            }

            String nactaNameCheck = dto.loan_info == null ? "" : mwTabService.nactaNameMatch(dto.loan_info.loan_app_seq);
            if (nactaNameCheck.length() > 0) {
                resp.put("status", "4");
                resp.put("loan_app_sts", "" + dto.loan_info.loan_app_sts);
                resp.put("message", "Name match found with NACTA/UN list. Please complete the verification process before disbursement. "
                        + nactaNameCheck);
                resp.put("canProceed", "true");
                return ResponseEntity.ok().body(resp);
            }

            return result;
        } catch (Exception exception) {
            resp.put("status", "6");
            resp.put("message", getRootCause(exception));
            resp.put("canProceed", "false");
            return ResponseEntity.ok().body(resp);
        }
    }

    private String getRootCause(Exception exception) {
        String exceptionCause = "";
        Throwable cause = exception.getCause();
        while (cause != null) {
            exceptionCause = cause.getMessage();
            cause = cause.getCause();
        }
        return exceptionCause;
    }


    @PostMapping("/verify-recommended-amount-for-krk")
    @Timed
    public ResponseEntity<?> verifyRecommendedAmountForKrk(@RequestBody krkRecAmntDto dto) {

        return ResponseEntity.ok().body("{'message': '" + mwTabService.verifyRecommendedAmountForKrk(dto) + "'}");

    }


    @PutMapping("/submit-complete-application-log")
    @Timed
    @Transactional
    public ResponseEntity<String> log(@RequestBody TabAppDto dto) throws URISyntaxException, ParseException {
        log.debug("TAB APP SUBMIT FOR LOG", dto.toString());
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Success", "Application Saved")).body("LOGGED INFO");
    }

    @PostMapping("/mark-sync-flag")
    @Timed
    public ResponseEntity<String> log(@RequestBody SyncDto dto) throws URISyntaxException, ParseException {
        SyncDto syncDto = mwTabService.markSyncFlag(dto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("Success", "Sync Flags Updated"))
                .body("{'message': 'Success'}");
    }

    // @GetMapping ( "/get-data-for-port/{seq}" )
    // @Timed
    // public ResponseEntity getDataForTab( @PathVariable Long seq ) {
    // SyncDto syncDto = mwTabService.getDataForTab( seq );
    // return ResponseEntity.ok().body( syncDto );
    // }

    @GetMapping("/get-data-for-device/{mac}/{completeDataRequest}")
    @Timed
    public ResponseEntity getDataForTab(@PathVariable String mac, @PathVariable Boolean completeDataRequest) throws ParseException {
        SyncDto syncDto = mwTabService.getDataForDeviceRegistered(mac, completeDataRequest);
        return ResponseEntity.ok().body(syncDto);
    }

    @GetMapping("/update-loan-sync-date/{mac}")
    @Timed
    public ResponseEntity updateLoanSyncDate(@PathVariable String mac) {
        String str = mwTabService.markSyncDate(mac);
        if (str == null)
            return ResponseEntity.badRequest().body("{'error': 'Device Not Registered'}");
        return ResponseEntity.ok().body("{'message': 'Success'}");
    }

    @PostMapping("/submit-anml-rgstr")
    public ResponseEntity<String> submitAnmlRgstr(@RequestBody TabAppDto dto) throws URISyntaxException, ParseException {
        if (dto.anml_rgstr == null)
            return ResponseEntity.badRequest().body("{\"error\":\"Animal Registr List Not Found\"}");
        mwTabService.saveAnmlRgstr(dto.anml_rgstr, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok().body("{\"message\":\"Animal Registr Saved\"}");
    }

    @PostMapping("/credit-scoring")
    public ResponseEntity<String> log(@RequestBody CreditScoringTabDto dto) throws URISyntaxException, ParseException {
        return mwTabService.creditScoring(dto);
    }

    /**
     * @Update, Naveed
     * @Date, 17-08-2022
     * @Description, SCR - CIRPLUS TASDEEQ
     */

    @PostMapping("/tasdeeq-data")
    public String tasdeeqLog(@RequestBody TasdeeqDto dto,
                             @RequestHeader(value = "Authorization") String token) throws URISyntaxException, ParseException {
        // return mwTabService.postCIRPlusTasdeeq( dto, token );

        String response = "";
        try {
            response = mwTabService.postTasdeeq(dto, token);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    // @PostMapping ( "/credit-bureau-report-old" )
    // public List creditBureauReport( @RequestBody CreditBureauDto dto ) {
    // ObjectFactory fact = new ObjectFactory();
    // CR cr = new CR();
    // cr.setMemberCode( fact.createCRMemberCode( "706" ) );
    // cr.setControlBranchCode( fact.createCRControlBranchCode( "7001" ) );
    // cr.setMakerUserName( fact.createCRMakerUserName( "MAKE" ) );
    // cr.setMakerPassword( fact.createCRMakerPassword( "3333eeee" ) );
    // cr.setCheckerUserName( fact.createCRCheckerUserName( "CHK" ) );
    // cr.setCheckerPassword( fact.createCRCheckerPassword( "3333eeee" ) );
    //
    // cr.setSubBranchCode( fact.createCRSubBranchCode( dto.subBranchCode ) );
    // cr.setCnicNo( fact.createCRCnicNo( dto.cnicNo ) );
    // cr.setNicNoOrPassportNo( fact.createCRNicNoOrPassportNo( dto.nicNoOrPassportNo ) );
    // cr.setFirstName( fact.createCR2FirstName( dto.firstName ) );
    // cr.setMiddleName( fact.createCRMiddleName( dto.middleName ) );
    // cr.setLastName( fact.createCRLastName( dto.lastName ) );
    // cr.setGender( fact.createCRGender( dto.gender ) );
    // cr.setDateOfBirth( fact.createCRDateOfBirth( dto.dateOfBirth ) );
    // cr.setFatherOrHusbandFirstName( fact.createCRFatherOrHusbandFirstName( dto.fatherOrHusbandFirstName ) );
    // cr.setFatherOrHusbandMiddleName( fact.createCRFatherOrHusbandMiddleName( dto.fatherOrHusbandMiddleName ) );
    // cr.setFatherOrHusbandLastName( fact.createCRFatherOrHusbandLastName( dto.fatherOrHusbandLastName ) );
    // cr.setCityOrDistrict( fact.createCRCityOrDistrict( dto.cityOrDistrict ) );
    // cr.setAddress( fact.createCRAddress( dto.address ) );
    // cr.setPhoneNo( fact.createCRPhoneNo( dto.phoneNo ) );
    // cr.setCellNo( fact.createCRCellNo( dto.cellNo ) );
    // cr.setEmailAddress( fact.createCREmailAddress( dto.emailAddress ) );
    // cr.setAccountType( fact.createCRAccountType( dto.accountType ) );
    // cr.setAssociationType( fact.createCRAssociationType( dto.associationType ) );
    // cr.setAmount( fact.createCRAmount( dto.amount ) );
    //
    // cr.setApplicationId( fact.createCRApplicationId( dto.applicationId ) );
    // cr.setGroupId( fact.createCRGroupId( dto.groupId ) );
    // cr.setEmployerCompanyName( fact.createCREmployerCompanyName( dto.employerCompanyName ) );
    // cr.setEmployerAddress( fact.createCREmployerAddress( dto.employerAddress ) );
    // cr.setEmployerBusinessCategory( fact.createCREmployerBusinessCategory( dto.employerBusinessCategory ) );
    // cr.setEmployerCellNo( fact.createCREmployerCellNo( dto.employerCellNo ) );
    // cr.setEmployerCityOrDistrict( fact.createCREmployerCityOrDistrict( dto.employerCityOrDistrict ) );
    // cr.setEmployerEmailAddress( fact.createCREmployerEmailAddress( dto.employerEmailAddress ) );
    // cr.setEmployerOwnershipStatus( fact.createCREmployerOwnershipStatus( dto.employerOwnershipStatus ) );
    // cr.setEmployerPhoneNo( fact.createCREmployerPhoneNo( dto.employerPhoneNo ) );
    // cr.setProfession( fact.createCRProfession( dto.profession ) );
    // GetBureauCreditReportResponse obj = creditBureau.getBureauCreditReport( "706D@ta-Check", cr );
    // ArrayOfCRResponse arrayOfCRResponse = obj.getGetBureauCreditReportResult().getValue();
    // List< CRResponse > list = arrayOfCRResponse.getCRResponse();
    // List< CrResponseDto > resp = new ArrayList< CrResponseDto >();
    // list.forEach( item -> {
    // CrResponseDto cdto = new CrResponseDto();
    // cdto.report = item.getReport().getValue();
    // cdto.status = item.getStatus().getValue();
    // resp.add( cdto );
    // } );
    // return resp;
    // }

    @PostMapping("/credit-bureau-report")
    public String creditBureauReport2(@RequestBody CreditBureauDto dto) {
        dto.newApplicationId = dto.applicationId;
        String resp = "";
        try {
            resp = creditBureau.getBureauCreditReportWithParsing(dto);
            // resp = creditBureau.getBureauCreditReport( dto );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resp;
    }

    @GetMapping("/deffer-application/{loanAppSeq}")
    @Timed
    public ResponseEntity defferApplication(@PathVariable Long loanAppSeq) {
        mwTabService.deferLoanApplication(loanAppSeq, "w" + SecurityContextHolder.getContext().getAuthentication().getName());

        return ResponseEntity.ok().body("{'message': 'Success'}");
    }

    @GetMapping("/delete-application/{loanAppSeq}/{sts}/{reason}")
    @Timed
    public ResponseEntity deleteApplication(@PathVariable Long loanAppSeq, @PathVariable Long sts, @PathVariable String reason) {
        mwTabService.deleteApplication(loanAppSeq, "w" + SecurityContextHolder.getContext().getAuthentication().getName(), sts, reason);

        return ResponseEntity.ok().body("{'message': 'Success'}");
    }

    @GetMapping("/check-loan-for-aml/{loanAppSeq}")
    @Timed
    public ResponseEntity<Map> checkLoanForAml(@PathVariable Long loanAppSeq) {
        String nactaNameCheck = mwTabService.nactaNameMatch(loanAppSeq);
        return ResponseEntity.ok().body(mwTabService.amlMtchd(loanAppSeq));
    }

    @GetMapping("/nacta-verification/{loanAppSeq}")
    @Timed
    public ResponseEntity<Map> nactaVerificationonDisbursement(@PathVariable Long loanAppSeq) {

        Map<String, String> resp = new HashMap<String, String>();

        boolean nactaCnicTagged = mwTabService.nactaVerification(loanAppSeq);
        if (nactaCnicTagged) {
            resp.put("status", "3");
            MwRefCdVal nactaRefCdVal = mwRefCdValRepository.findRefCdByGrpAndVal("2785", "1642");
            resp.put("loan_app_sts", "" + nactaRefCdVal.getRefCdSeq());
            resp.put("message", "NACTA Matched. Client and other individual/s (Nominee/Co-borrower/Next of Kin) cannot be disbursed.");
            resp.put("canProceed", "false");
            return ResponseEntity.ok().body(resp);
        }
        String nactaNameCheck = mwTabService.nactaNameMatch(loanAppSeq);
        if (nactaNameCheck.length() > 0) {
            resp.put("status", "4");
            resp.put("message", "Nacta name match found with Nacta/UN List. Please complete the verfication process before Disbursement.");
            resp.put("canProceed", "true");
            return ResponseEntity.ok().body(resp);
        }

        resp.put("status", "0");
        resp.put("message", "");
        resp.put("canProceed", "true");
        return ResponseEntity.ok().body(resp);
    }

    //Updated by Areeba - Dated - 24-3-2022
    @PostMapping("/cnic-upd/{type}")
    public ResponseEntity cnicUpd(@RequestBody CnicUpdDTO dto, @PathVariable String type) {
        Map<String, String> resp = new HashMap<String, String>();
        long seq = mwTabService.saveCnicUpd(dto, type);
        resp.put("success", "CNIC Updated");
        return ResponseEntity.ok().body(resp);
    }

    /*Added By Rizwan Mahfooz - Dated 15-FEB-2022
     * To send cnic expiry pictures*/
    @GetMapping("/get_cnic_expiry_pics/{loan_app_seq}")
    @Timed
    public ResponseEntity get_cnic_expiry_pictures(HttpServletRequest request,
                                                   @PathVariable String loan_app_seq) {
        Map<String, Object> response = mwTabService.sendExpiryPictures(loan_app_seq);
        return ResponseEntity.ok().body(response);
    }
    //Ended by Areeba

    /*Added By Rizwan Mahfooz - Dated 24-1-2022
     * To update geo tags for those clients who does not have correct geo location in database*/
    @GetMapping("/update_geo_tag/{addr_seq}/{latitude:.+}/{longitude:.+}")
    @Timed
    public ResponseEntity update_geoTag(HttpServletRequest request,
                                        @PathVariable String addr_seq,
                                        @PathVariable Double latitude, @PathVariable Double longitude) {
        long response = mwTabService.updateGeoTag(addr_seq, latitude, longitude);
        return ResponseEntity.ok().body(response);
    }

    /*Added By Rizwan Mahfooz - Dated 08-3-2022
     * To update geo tags for Branches in database*/
    @GetMapping("/update_geo_tag_for_branch/{addr_seq}/{latitude:.+}/{longitude:.+}")
    @Timed
    public ResponseEntity update_geoTagForBranch(HttpServletRequest request,
                                                 @PathVariable String addr_seq,
                                                 @PathVariable Double latitude, @PathVariable Double longitude) {
        long response = mwTabService.updateGeoTagForBranch(addr_seq, latitude, longitude);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-data-for-device-tablet/{mac}/{completeDataRequest}")
    @Timed
    public ResponseEntity getSetupDataForDevice(HttpServletRequest request,
                                                @PathVariable String mac, @PathVariable Boolean completeDataRequest)
            throws ParseException, SQLException, IOException {
        Object syncDto = mwTabService.getTabletDataForDeviceRegistered(mac, completeDataRequest);
        return ResponseEntity.ok().body(syncDto);
    }

    /*Added By Rizwan Mahfooz - Dated 21-2-2022
     * To update phone numbers for clients*/
    @GetMapping("/update-phone-no/{client_seq}/{phone_no}")
    @Timed
    public ResponseEntity update_phone_number(HttpServletRequest request,
                                              @PathVariable String client_seq,
                                              @PathVariable String phone_no) {
        String response = mwTabService.updatePhoneNumber(phone_no, client_seq);
        return ResponseEntity.ok().body(response);
    }
    // End by Rizwan

    public class CrResponseDto {

        public String report;

        public String status;
    }

    //End

}
