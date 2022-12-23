package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.*;
import com.idev4.loans.dto.*;
import com.idev4.loans.repository.*;
import com.idev4.loans.service.MwAddrRelService;
import com.idev4.loans.service.MwBizAprslService;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.PaginationUtil;
import com.idev4.loans.web.rest.util.TableNames;
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
import java.time.Instant;
import java.util.*;

/**
 * REST controller for managing MwBizAprsl.
 */
@RestController
@RequestMapping("/api")
public class MwBizAprslResource {

    private static final String ENTITY_NAME = "mwBizAprsl";
    private final Logger log = LoggerFactory.getLogger(MwBizAprslResource.class);
    private final MwBizAprslService mwBizAprslService;

    private final MwAddrResource mwAddrResource;

    private final MwAddrRelService mwAddrRelService;

    private final MwBizAprslIncmHdrResource mwBizAprslIncmHdrResource;

    private final MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository;

    private final MwBizExpDtlResource mwBizExpDtlResource;

    private final MwBizExpDtlRepository mwBizExpDtlRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final MwAddrRepository mwAddrRepository;

    private final MwBizAprslExtngLvstkRepository mwBizAprslExtngLvstkRepository;

    private final MwBizAprslEstLvstkFinRepository mwBizAprslEstLvstkRepository;
    int count;

    public MwBizAprslResource(MwBizAprslService mwBizAprslService, MwAddrResource mwAddrResource,
                              MwAddrRelService mwAddrRelService, MwBizExpDtlResource mwBizExpDtlResource,
                              MwBizAprslIncmHdrResource mwBizAprslIncmHdrResource,
                              MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository, MwBizExpDtlRepository mwBizExpDtlRepository,
                              MwLoanAppRepository mwLoanAppRepository, MwClntRepository mwClntRepository,
                              MwAddrRelRepository mwAddrRelRepository, MwAddrRepository mwAddrRepository, MwBizAprslExtngLvstkRepository mwBizAprslExtngLvstkRepository,
                              MwBizAprslEstLvstkFinRepository mwBizAprslEstLvstkRepository) {
        this.mwBizAprslService = mwBizAprslService;
        this.mwAddrRelService = mwAddrRelService;
        this.mwAddrResource = mwAddrResource;
        this.mwBizExpDtlResource = mwBizExpDtlResource;
        this.mwBizAprslIncmHdrResource = mwBizAprslIncmHdrResource;
        this.mwBizAprslIncmDtlRepository = mwBizAprslIncmDtlRepository;
        this.mwBizExpDtlRepository = mwBizExpDtlRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.mwBizAprslExtngLvstkRepository = mwBizAprslExtngLvstkRepository;
        this.mwBizAprslEstLvstkRepository = mwBizAprslEstLvstkRepository;

    }

    /**
     * POST /mw-biz-aprsls : Create a new mwBizAprsl.
     *
     * @param mwBizAprsl the mwBizAprsl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     * mwBizAprsl, or with status 400 (Bad Request) if the mwBizAprsl has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/create-biz-aprsls")
    @Timed
    public ResponseEntity<Map> createMwBizAprsl(@RequestBody BusinessAprsalDto mwBizAprsl) throws URISyntaxException {
        log.debug("REST request to save MwBizAprsl : {}", mwBizAprsl);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // if(mwBizAprsl.yearsInBusiness<0 || mwBizAprsl.yearsInBusiness>99) {
        // resp.put("error", "Incorrect Business Years !!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        // if(mwBizAprsl.monthsInBusiness<0 || mwBizAprsl.monthsInBusiness>99) {
        // resp.put("error", "Incorrect Business Months !!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        long bizSeq = mwBizAprslService.addClientBusinessAprsl(mwBizAprsl, currUser);

        AddressDto addrDto = new AddressDto();
        if (!mwBizAprsl.isbizAddrSAC) {
            addrDto.bizSeq = bizSeq;
            addrDto.houseNum = mwBizAprsl.businessAddress.houseNum;
            addrDto.sreet_area = mwBizAprsl.businessAddress.sreet_area;
            addrDto.community = mwBizAprsl.businessAddress.community;
            addrDto.village = mwBizAprsl.businessAddress.village;
            addrDto.otherDetails = mwBizAprsl.businessAddress.otherDetails;
            addrDto.city = mwBizAprsl.businessAddress.city;
            addrDto.lat = mwBizAprsl.businessAddress.lat;
            addrDto.lon = mwBizAprsl.businessAddress.lon;
            addrDto.yearsOfResidence = mwBizAprsl.businessAddress.yearsOfResidence;
            addrDto.mnthsOfResidence = mwBizAprsl.businessAddress.mnthsOfResidence;
            addrDto.isPermAddress = mwBizAprsl.businessAddress.isPermAddress;
        }
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwBizAprsl.loanAppSeq, true);
        if (app != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                addrDto.cnicNum = clnt.getCnicNum();
                addrDto.cycleNum = app.getLoanCyclNum();
                if (!mwBizAprsl.isbizAddrSAC) {
                    ResponseEntity<Map> addr = mwAddrResource.createBusinessAddr(addrDto);
                    resp.put("bizAddressSeq", addr.getBody().get("addrSeq").toString());
                }
                IncomeHdrDto incmDto = new IncomeHdrDto();
                incmDto.bizAprslSeq = bizSeq;
                incmDto.maxMonthSale = mwBizAprsl.maxMonthSale;
                incmDto.maxSaleMonth = mwBizAprsl.maxSaleMonth;
                incmDto.minMonthSale = mwBizAprsl.minMonthSale;
                incmDto.minSaleMonth = mwBizAprsl.minSaleMonth;
                incmDto.cnicNum = clnt.getCnicNum();
                incmDto.cycleNum = app.getLoanCyclNum();
                ResponseEntity<Map> incomeHdrResp = mwBizAprslIncmHdrResource.createMwBizAprslIncmHdr(incmDto);

                List<MwBizAprslIncmDtl> primaryIncomes = new ArrayList<MwBizAprslIncmDtl>();
                int i = 0;
                for (IncomeDtlDto income : mwBizAprsl.primaryIncome) {
                    MwBizAprslIncmDtl in = new MwBizAprslIncmDtl();
                    in.setCrntRecFlg(true);

                    in.setCrtdBy(currUser);
                    in.setCrtdDt(Instant.now());
                    in.setDelFlg(false);
                    in.setEffStartDt(Instant.now());
                    in.setIncmAmt(income.incomeAmount);
                    in.setIncmCtgryKey(income.incomeCategoryKey);
                    //Edited By Areeba
                    in.setIncmDtlSeq(Long.parseLong((Common.GenerateTableSequenceWithLoanAppSeq(mwBizAprsl.loanAppSeq.toString(),
                            TableNames.MW_BIZ_APRSL_INCM_DTL) + "").concat(i + "")));
                    //Ended by Areeba
//					in.setIncmDtlSeq(Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
//							TableNames.MW_BIZ_APRSL_INCM_DTL, app.getLoanCyclNum()) + "").concat(i + "")));
                    in.setIncmTypKey(income.incomeTypeKey);
                    in.setMwBizAprslIncmHdr(Long.valueOf(incomeHdrResp.getBody().get("incomeHdrSeq").toString()));
                    in.setEntyTypFlg(1);
                    in.setSyncFlg(true);
                    in.setLastUpdBy("w-" + currUser);
                    in.setLastUpdDt(Instant.now());
                    primaryIncomes.add(in);
                    i = i + 1;
                    mwBizAprslIncmDtlRepository.save(in);
                }
                i = 0;
                List<MwBizAprslIncmDtl> secondaryIncomes = new ArrayList<MwBizAprslIncmDtl>();
                for (IncomeDtlDto income : mwBizAprsl.secondaryIncome) {
                    MwBizAprslIncmDtl in = new MwBizAprslIncmDtl();
                    in.setCrntRecFlg(true);
                    in.setCrtdBy(currUser);
                    in.setCrtdDt(Instant.now());
                    in.setDelFlg(false);
                    in.setEffStartDt(Instant.now());
                    in.setIncmAmt(income.incomeAmount);
                    in.setIncmCtgryKey(income.incomeCategoryKey);
                    //Edited By Areeba
                    in.setIncmDtlSeq(Long.parseLong((Common.GenerateTableSequenceWithLoanAppSeq(mwBizAprsl.loanAppSeq.toString(),
                            TableNames.MW_BIZ_APRSL_INCM_DTL) + "").concat(i + "")));
                    //Ended by Areeba
//					in.setIncmDtlSeq(Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
//							TableNames.MW_BIZ_APRSL_INCM_DTL, app.getLoanCyclNum()) + "").concat(i + "")));
                    in.setIncmTypKey(income.incomeTypeKey);
                    in.setMwBizAprslIncmHdr(Long.valueOf(incomeHdrResp.getBody().get("incomeHdrSeq").toString()));
                    in.setEntyTypFlg(1);
                    in.setSyncFlg(true);
                    in.setLastUpdBy("w-" + currUser);
                    in.setLastUpdDt(Instant.now());
                    secondaryIncomes.add(in);
                    i++;
                    mwBizAprslIncmDtlRepository.save(in);
                }
                i = 0;
                for (BizExpDto expense : mwBizAprsl.businessExpense) {
                    MwBizExpDtl ex = new MwBizExpDtl();
                    ex.setCrntRecFlg(true);
                    ex.setCrtdBy(currUser);
                    ex.setCrtdDt(Instant.now());
                    ex.setDelFlg(false);
                    ex.setEffStartDt(Instant.now());
                    ex.setExpAmt(expense.expAmount);
                    ex.setExpCtgryKey(expense.expCategoryKey);
                    ex.setExpDtlSeq(Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
                            TableNames.MW_BIZ_EXP_DTL, app.getLoanCyclNum()) + "").concat(i + "")));
                    ex.setExpTypKey(expense.expTypKey);
                    ex.setMwBizAprsl(bizSeq);
                    ex.setEntyTypFlg(1);
                    ex.setSyncFlg(true);
                    ex.setLastUpdBy("w-" + currUser);
                    ex.setLastUpdDt(Instant.now());
                    i++;
                    mwBizExpDtlRepository.save(ex);
                }
                i = 0;
                for (BizExpDto expense : mwBizAprsl.householdExpense) {
                    MwBizExpDtl ex = new MwBizExpDtl();
                    ex.setCrntRecFlg(true);
                    ex.setCrtdBy(currUser);
                    ex.setCrtdDt(Instant.now());
                    ex.setDelFlg(false);
                    ex.setEffStartDt(Instant.now());
                    ex.setExpAmt(expense.expAmount);
                    ex.setExpCtgryKey(expense.expCategoryKey);
                    ex.setExpDtlSeq(Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
                            TableNames.MW_BIZ_EXP_DTL, app.getLoanCyclNum()) + "").concat(i + "")));
                    ex.setExpTypKey(expense.expTypKey);
                    ex.setMwBizAprsl(bizSeq);
                    ex.setEntyTypFlg(1);
                    ex.setSyncFlg(true);
                    ex.setLastUpdBy("w-" + currUser);
                    ex.setLastUpdDt(Instant.now());
                    i++;
                    mwBizExpDtlRepository.save(ex);
                }

                if (incomeHdrResp == null || incomeHdrResp.getBody().get("incomeHdrSeq") == null)
                    return ResponseEntity.badRequest().body(incomeHdrResp.getBody());

                resp.put("bizAprslSeq", String.valueOf(bizSeq));
                resp.put("incomeHdrSeq", incomeHdrResp.getBody().get("incomeHdrSeq").toString());

            }
        }
        /*
         * BizExpDto bizDto = new BizExpDto(); bizDto.bizAprslSeq = bizSeq;
         * bizDto.expAmount = mwBizAprsl.expAmount; bizDto.expCategoryKey =
         * mwBizAprsl.expCategoryKey; bizDto.expTypKey = mwBizAprsl.expTypKey;
         * mwBizExpDtlResource.createMwBizExpDtl(bizDto);
         */

        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT /mw-biz-aprsls : Updates an existing mwBizAprsl.
     *
     * @param mwBizAprsl the mwBizAprsl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * mwBizAprsl, or with status 400 (Bad Request) if the mwBizAprsl is not
     * valid, or with status 500 (Internal Server Error) if the mwBizAprsl
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-biz-aprsls")
    @Timed
    public ResponseEntity<Map> updateMwBizAprsl(@RequestBody BusinessAprsalDto mwBizAprsl) throws URISyntaxException {
        log.debug("REST request to update MwBizAprsl : {}", mwBizAprsl);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (mwBizAprsl.bizAprslSeq <= 0) {
            resp.put("error", "Business Apraisal Seq not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        // if(mwBizAprsl.yearsInBusiness<0 || mwBizAprsl.yearsInBusiness>99) {
        // resp.put("error", "Incorrect Business Years !!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        //
        // if(mwBizAprsl.monthsInBusiness<0 || mwBizAprsl.monthsInBusiness>99) {
        // resp.put("error", "Incorrect Business Months !!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        long bizSeq = mwBizAprslService.updateClientBusinessAprsl(mwBizAprsl, currUser);

        if (bizSeq == 0) {
            resp.put("error", "Business Appraisal Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        AddressDto addrDto = new AddressDto();
        if (!mwBizAprsl.isbizAddrSAC) {
            addrDto.addrSeq = mwBizAprsl.bizAddressSeq;
            addrDto.bizSeq = mwBizAprsl.businessAddress.bizSeq;
            addrDto.houseNum = mwBizAprsl.businessAddress.houseNum;
            addrDto.sreet_area = mwBizAprsl.businessAddress.sreet_area;
            addrDto.community = mwBizAprsl.businessAddress.community;
            addrDto.village = mwBizAprsl.businessAddress.village;
            addrDto.otherDetails = mwBizAprsl.businessAddress.otherDetails;
            addrDto.city = mwBizAprsl.businessAddress.city;
            addrDto.lat = mwBizAprsl.businessAddress.lat;
            addrDto.lon = mwBizAprsl.businessAddress.lon;
            addrDto.yearsOfResidence = mwBizAprsl.businessAddress.yearsOfResidence;
            addrDto.mnthsOfResidence = mwBizAprsl.businessAddress.mnthsOfResidence;
            addrDto.isPermAddress = mwBizAprsl.businessAddress.isPermAddress;
        }
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(mwBizAprsl.loanAppSeq, true);
        if (app != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                addrDto.cnicNum = clnt.getCnicNum();
                addrDto.cycleNum = app.getLoanCyclNum();
                if (!mwBizAprsl.isbizAddrSAC) {
                    ResponseEntity<Map> addr;
                    if (addrDto.addrSeq == null) {
                        addr = mwAddrResource.createBusinessAddr(addrDto);
                        resp.put("bizAddressSeq", addr.getBody().get("addrSeq").toString());
                    } else {
                        addr = mwAddrResource.updateBusinessAddr(addrDto);
                        resp.put("bizAddressSeq", addrDto.addrSeq.toString());
                    }
                    if (addr.getStatusCodeValue() != 200) {
                        resp.put("error", "Business Apraisal Address Not Found !!");
                        return ResponseEntity.badRequest().body(resp);
                    }
                } else {

                    MwAddrRel exRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(bizSeq,
                            Common.businessAddress, true);
                    if (exRel != null) {
                        mwAddrRelRepository.delete(exRel);
                        // exRel.setCrntRecFlg( false );
                        // exRel.setDelFlg( true );
                        // exRel.setLastUpdBy( "w-" + currUser );
                        // exRel.setLastUpdDt( Instant.now() );
                        // exRel.setEffEndDt( Instant.now() );
                        // mwAddrRelRepository.save( exRel );
                        MwAddr exAddr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(exRel.getAddrSeq(), true);
                        if (exAddr != null) {
                            mwAddrRepository.delete(exAddr);
                            // exAddr.setCrntRecFlg( false );
                            // exAddr.setDelFlg( true );
                            // exAddr.setLastUpdBy( "w-" + currUser );
                            // exAddr.setLastUpdDt( Instant.now() );
                            // exAddr.setEffEndDt( Instant.now() );
                            // mwAddrRepository.save( exAddr );
                        }
                    }
                }
                IncomeHdrDto incmDto = new IncomeHdrDto();
                incmDto.bizAprslSeq = bizSeq;
                incmDto.incomeHdrSeq = mwBizAprsl.incomeHdrSeq;
                incmDto.maxMonthSale = mwBizAprsl.maxMonthSale;
                incmDto.maxSaleMonth = mwBizAprsl.maxSaleMonth;
                incmDto.minMonthSale = mwBizAprsl.minMonthSale;
                incmDto.minSaleMonth = mwBizAprsl.minSaleMonth;
                incmDto.cnicNum = clnt.getCnicNum();
                incmDto.cycleNum = app.getLoanCyclNum();
                ResponseEntity<Map> incomeHdrResp = mwBizAprslIncmHdrResource.updateMwBizAprslIncmHdr(incmDto);

                if (incomeHdrResp == null) {
                    resp.put("error", "Some Bad Values Found !!");
                    return ResponseEntity.badRequest().body(resp);
                }

                List<MwBizAprslIncmDtl> primaryIncomes = mwBizAprslIncmDtlRepository
                        .findAllByMwBizAprslIncmHdrAndCrntRecFlgAndEntyTypFlg(
                                Long.valueOf(incomeHdrResp.getBody().get("incomeHdrSeq").toString()), true, 1);
                // Added by Zohaib Asim - Dated 31-05-2022 - Production Issue Duplicate Seq
                // mwBizAprslIncmDtlRepository.deleteInBatch(primaryIncomes);
                List<MwBizAprslIncmDtl> bizAprslIncmDtlList = new ArrayList<>();
                for (MwBizAprslIncmDtl bizAprslIncmDtl : primaryIncomes) {
                    bizAprslIncmDtl.setCrntRecFlg(false);
                    bizAprslIncmDtl.setDelFlg(true);
                    bizAprslIncmDtl.setLastUpdBy(currUser);
                    bizAprslIncmDtl.setLastUpdDt(Instant.now());

                    bizAprslIncmDtlList.add(bizAprslIncmDtl);
                }
                mwBizAprslIncmDtlRepository.save(bizAprslIncmDtlList);
                // End

                int i = 0;
                for (IncomeDtlDto income : mwBizAprsl.primaryIncome) {
                    MwBizAprslIncmDtl in = new MwBizAprslIncmDtl();
                    in.setCrntRecFlg(true);
                    in.setCrtdBy(currUser);
                    in.setCrtdDt(Instant.now());
                    in.setDelFlg(false);
                    in.setEffStartDt(Instant.now());
                    in.setIncmAmt(income.incomeAmount);
                    in.setIncmCtgryKey(income.incomeCategoryKey);

                    // Modified by Zohaib Asim - Dated 27-05-2022 - Production Issue Overriding Income Detail Seq
                    Long seq = 0L;
                    if (income.incomeDtlSeq != null && income.incomeDtlSeq.length() > 0) {
                        seq = Long.parseLong(income.incomeDtlSeq);
                    } else {
                        //Edited By Areeba
                        seq = Long.parseLong((Common.GenerateTableSequenceWithLoanAppSeq(mwBizAprsl.loanAppSeq.toString(),
                                TableNames.MW_BIZ_APRSL_INCM_DTL) + "").concat(i + ""));
                        //Ended by Areeba
//						seq = Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
//								TableNames.MW_BIZ_APRSL_INCM_DTL, app.getLoanCyclNum()) + "").concat(i + ""));
                    }
                    in.setIncmDtlSeq(seq);
                    // End

                    in.setIncmTypKey(income.incomeTypeKey);
                    in.setMwBizAprslIncmHdr(Long.valueOf(incomeHdrResp.getBody().get("incomeHdrSeq").toString()));
                    in.setEntyTypFlg(1);
                    in.setSyncFlg(true);
                    in.setLastUpdBy("w-" + currUser);
                    in.setLastUpdDt(Instant.now());
                    i++;
                    mwBizAprslIncmDtlRepository.save(in);
                }
                i = 0;
                for (IncomeDtlDto income : mwBizAprsl.secondaryIncome) {
                    MwBizAprslIncmDtl in = new MwBizAprslIncmDtl();
                    in.setCrntRecFlg(true);
                    in.setCrtdBy(currUser);
                    in.setCrtdDt(Instant.now());
                    in.setDelFlg(false);
                    in.setEffStartDt(Instant.now());
                    in.setIncmAmt(income.incomeAmount);
                    in.setIncmCtgryKey(income.incomeCategoryKey);
                    // Modified by Zohaib Asim - Dated 27-05-2022 - Production Issue Overriding Income Detail Seq
                    Long seq = 0L;
                    if (income.incomeDtlSeq != null && income.incomeDtlSeq.length() > 0) {
                        seq = Long.parseLong(income.incomeDtlSeq);
                    } else {
                        //Edited By Areeba
                        seq = Long.parseLong((Common.GenerateTableSequenceWithLoanAppSeq(mwBizAprsl.loanAppSeq.toString(),
                                TableNames.MW_BIZ_APRSL_INCM_DTL) + "").concat(i + ""));
                        //Ended by Areeba
//						seq = Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
//								TableNames.MW_BIZ_APRSL_INCM_DTL, app.getLoanCyclNum()) + "").concat(i + ""));
                    }
                    in.setIncmDtlSeq(seq);
                    //
                    in.setIncmTypKey(income.incomeTypeKey);
                    in.setMwBizAprslIncmHdr(Long.valueOf(incomeHdrResp.getBody().get("incomeHdrSeq").toString()));
                    in.setEntyTypFlg(1);
                    in.setSyncFlg(true);
                    in.setLastUpdBy("w-" + currUser);
                    in.setLastUpdDt(Instant.now());
                    i++;
                    mwBizAprslIncmDtlRepository.save(in);
                }

                List<MwBizExpDtl> expenses = mwBizExpDtlRepository.findAllByMwBizAprslAndCrntRecFlgAndEntyTypFlgQry(bizSeq);
                // Added by Zohaib Asim - Dated 31-05-2022 - Production Issue Duplicate Seq
                //mwBizExpDtlRepository.deleteInBatch(expenses);
                List<MwBizExpDtl> bizAprslList = new ArrayList<>();
                for (MwBizExpDtl bizExpDtl : expenses) {
                    bizExpDtl.setCrntRecFlg(false);
                    bizExpDtl.setDelFlg(true);
                    bizExpDtl.setLastUpdBy(currUser);
                    bizExpDtl.setLastUpdDt(Instant.now());

                    bizAprslList.add(bizExpDtl);
                }
                mwBizExpDtlRepository.save(bizAprslList);
                // End

                i = 0;
                for (BizExpDto expense : mwBizAprsl.businessExpense) {
                    MwBizExpDtl ex = new MwBizExpDtl();
                    ex.setCrntRecFlg(true);
                    ex.setCrtdBy(currUser);
                    ex.setCrtdDt(Instant.now());
                    ex.setDelFlg(false);
                    ex.setEffStartDt(Instant.now());
                    ex.setExpAmt(expense.expAmount);
                    ex.setExpCtgryKey(expense.expCategoryKey);

                    // Modified by Zohaib Asim - Dated 27-05-2022 - Production Issue Overriding Expense Detail Seq
                    Long seq = 0L;
                    if (expense.bizExpSeq != null && expense.bizExpSeq > 0) {
                        seq = expense.bizExpSeq;
                    } else {
                        seq = Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
                                TableNames.MW_BIZ_EXP_DTL, app.getLoanCyclNum()) + "").concat(i + ""));
                    }

                    ex.setExpDtlSeq(seq);
                    //
                    ex.setExpTypKey(expense.expTypKey);
                    ex.setMwBizAprsl(bizSeq);
                    ex.setEntyTypFlg(1);
                    ex.setSyncFlg(true);
                    ex.setLastUpdBy("w-" + currUser);
                    ex.setLastUpdDt(Instant.now());
                    i++;
                    mwBizExpDtlRepository.save(ex);
                }
                i = 0;
                for (BizExpDto expense : mwBizAprsl.householdExpense) {
                    MwBizExpDtl ex = new MwBizExpDtl();
                    ex.setCrntRecFlg(true);
                    ex.setCrtdBy(currUser);
                    ex.setCrtdDt(Instant.now());
                    ex.setDelFlg(false);
                    ex.setEffStartDt(Instant.now());
                    ex.setExpAmt(expense.expAmount);
                    ex.setExpCtgryKey(expense.expCategoryKey);

                    // Modified by Zohaib Asim - Dated 27-05-2022 - Production Issue Overriding Expense Detail Seq
                    Long seq = 0L;
                    if (expense.bizExpSeq != null && expense.bizExpSeq > 0) {
                        seq = expense.bizExpSeq;
                    } else {
                        seq = Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
                                TableNames.MW_BIZ_EXP_DTL, app.getLoanCyclNum()) + "").concat(i + ""));
                    }
                    ex.setExpDtlSeq(seq);
                    //
                    ex.setExpTypKey(expense.expTypKey);
                    ex.setMwBizAprsl(bizSeq);
                    ex.setEntyTypFlg(1);
                    ex.setSyncFlg(true);
                    ex.setLastUpdBy("w-" + currUser);
                    ex.setLastUpdDt(Instant.now());
                    i++;
                    mwBizExpDtlRepository.save(ex);
                }

                if (incomeHdrResp.getBody().get("incomeHdrSeq") == null) {
                    return incomeHdrResp;
                }

                /*
                 * BizExpDto bizDto = new BizExpDto(); bizDto.bizAprslSeq = bizSeq;
                 * bizDto.expAmount = mwBizAprsl.expAmount; bizDto.expCategoryKey =
                 * mwBizAprsl.expCategoryKey; bizDto.expTypKey = mwBizAprsl.expTypKey;
                 * mwBizExpDtlResource.createMwBizExpDtl(bizDto);
                 */

                count = 1;
                if (mwBizAprsl.estLvStk != null) {
                    // -----Estimated Livestock------//
                    List<MwBizAprslEstLvstkFin> est = mwBizAprslEstLvstkRepository.findAllByBizAprslSeq(bizSeq);
                    est.forEach(exp -> {
                        exp.setCrntRecFlg(false);
                        exp.setDelFlg(true);
                        exp.setEffEndDt(Instant.now());
                        exp.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                        exp.setLastUpdDt(Instant.now());
                        count++;
                        mwBizAprslEstLvstkRepository.save(exp);
                    });

                    // -----Estimated Livestock------//

                    for (BizAprslEstLvstkFinDto est1 : mwBizAprsl.estLvStk) {
                        MwBizAprslEstLvstkFin ex = new MwBizAprslEstLvstkFin();
                        ex.setCrntRecFlg(true);
                        ex.setCrtdBy("w-" + currUser);
                        ex.setCrtdDt(Instant.now());
                        ex.setDelFlg(false);
                        ex.setEffStartDt(Instant.now());
                        ex.setEstVal(est1.estVal);
                        ex.setAnmlBrd(est1.anmlBrd);
                        ex.setAnmlHc(est1.anmlHc);
                        ex.setAnmlTyp(est1.anmlTyp);
                        ex.setAmtByClient(est1.amtByClient);
                        ex.setAmtFin(est1.amtFin);
                        ex.setBizAprslEstLvstkFinSeq(Long.parseLong(
                                (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_BIZ_APRSL_EST_LVSTK_FIN, app.getLoanCyclNum())
                                        + "").concat(count + "")));
                        ex.setBizAprslSeq(bizSeq);
                        ex.setLastUpdBy("w-" + currUser);
                        ex.setLastUpdDt(Instant.now());
                        count++;
                        mwBizAprslEstLvstkRepository.save(ex);
                    }
                }

                count = 1;
                if (mwBizAprsl.extngLvStk != null) {
                    // -----Existing Livestock------//
                    List<MwBizAprslExtngLvstk> exstng = mwBizAprslExtngLvstkRepository.findAllByBizAprslSeq(bizSeq);
                    exstng.forEach(exp -> {
                        exp.setCrntRecFlg(false);
                        exp.setDelFlg(true);
                        exp.setEffEndDt(Instant.now());
                        exp.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                        exp.setLastUpdDt(Instant.now());
                        count++;
                        mwBizAprslExtngLvstkRepository.save(exp);
                    });

                    // -----Existing Livestock------//

                    for (BizAprslExtngLvstkDto exstng1 : mwBizAprsl.extngLvStk) {
                        MwBizAprslExtngLvstk ex = new MwBizAprslExtngLvstk();
                        ex.setCrntRecFlg(true);
                        ex.setCrtdBy("w-" + currUser);
                        ex.setCrtdDt(Instant.now());
                        ex.setDelFlg(false);
                        ex.setEffStartDt(Instant.now());
                        ex.setEstVal(exstng1.estVal);
                        ex.setAnmlBrd(exstng1.anmlBrd);
                        ex.setAnmlHc(exstng1.anmlHc);
                        ex.setAnmlTyp(exstng1.anmlTyp);
                        ex.setAnmlKnd(exstng1.anmlKnd);
                        ex.setBizAprslExtngLvstkSeq(Long.parseLong(
                                (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_BIZ_APRSL_EXTNG_LVSTK, app.getLoanCyclNum())
                                        + "").concat(count + "")));
                        ex.setBizAprslSeq(bizSeq);
                        ex.setLastUpdBy("w-" + currUser);
                        ex.setLastUpdDt(Instant.now());
                        count++;
                        mwBizAprslExtngLvstkRepository.save(ex);
                    }
                }

                resp.put("bizAprslSeq", String.valueOf(bizSeq));
                resp.put("incomeHdrSeq", incomeHdrResp.getBody().get("incomeHdrSeq").toString());
            }
        }
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET /mw-biz-aprsls : get all the mwBizAprsls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBizAprsls
     * in body
     */
    @GetMapping("/mw-biz-aprsls")
    @Timed
    public ResponseEntity<List<MwBizAprsl>> getAllMwBizAprsls(Pageable pageable) {
        log.debug("REST request to get a page of MwBizAprsls");
        Page<MwBizAprsl> page = mwBizAprslService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-biz-aprsls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/get-biz-aprsl-by-loan-app/{loanAppSeq}")
    @Timed
    public ResponseEntity<Map> getAllMwBizAprslsByClient(@PathVariable long loanAppSeq) {
        log.debug("REST request to get a page of MwBizAprsls");
        Map resp = mwBizAprslService.getBusinessApraisalByLoanApplication(loanAppSeq);

        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET /mw-biz-aprsls/:id : get the "id" mwBizAprsl.
     *
     * @param id the id of the mwBizAprsl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBizAprsl,
     * or with status 404 (Not Found)
     */
    @GetMapping("/mw-biz-aprsls/{id}")
    @Timed
    public ResponseEntity<MwBizAprsl> getMwBizAprsl(@PathVariable Long id) {
        log.debug("REST request to get MwBizAprsl : {}", id);
        MwBizAprsl mwBizAprsl = mwBizAprslService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBizAprsl));
    }

}
