package com.idev4.loans.service;

import com.idev4.loans.domain.*;
import com.idev4.loans.dto.*;
import com.idev4.loans.dto.tab.DashboardDto;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

/**
 * Service Implementation for managing MwClnt.
 */
@Service
@Transactional
public class MwClntService {

    private final Logger log = LoggerFactory.getLogger(MwClntService.class);

    private final MwClntRepository mwClntRepository;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final MwAddrRepository mwAddrRepository;

    private final MwClntPermAddrService mwClntPermAddrService;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRelRepository mwClntRelRepository;

    private final MwClntCnicTknRepository mwClntCnicTknRepository;

    private final MwRefCdValRepository mwRefCdValRepository;

    private final EntityManager em;

    private final MwLoanAppTrnsRepository mwLoanAppTrnsRepository;

    private final MwPrdRepository mwPrdRepository;

    @Autowired
    MwCnicUpdRepository mwCnicUpdRepository;

    @Autowired
    MwLoanAppDocRepository mwLoanAppDocRepository;

    @Autowired
    MwPortRepository mwPortRepository;

    public MwClntService(MwClntRepository mwClntRepository, MwAddrRelRepository mwAddrRelRepository, MwAddrRepository mwAddrRepository,
                         EntityManager em, MwClntPermAddrService mwClntPermAddrService, MwLoanAppRepository mwLoanAppRepository,
                         MwClntRelRepository mwClntRelRepository, MwClntCnicTknRepository mwClntCnicTknRepository,
                         MwRefCdValRepository mwRefCdValRepository, MwLoanAppTrnsRepository mwLoanAppTrnsRepository, MwPrdRepository mwPrdRepository) {
        this.mwClntRepository = mwClntRepository;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwAddrRepository = mwAddrRepository;
        this.mwClntPermAddrService = mwClntPermAddrService;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRelRepository = mwClntRelRepository;
        this.em = em;
        this.mwClntCnicTknRepository = mwClntCnicTknRepository;
        this.mwRefCdValRepository = mwRefCdValRepository;
        this.mwLoanAppTrnsRepository = mwLoanAppTrnsRepository;
        this.mwPrdRepository = mwPrdRepository;
    }

    /**
     * Save a mwClnt.
     *
     * @param mwClnt the entity to save
     * @return the persisted entity
     */
    public MwClnt save(MwClnt mwClnt) {
        log.debug("Request to save MwClnt : {}", mwClnt);
        return mwClntRepository.save(mwClnt);
    }

    /**
     * Get all the mwClnts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwClnt> findAll(Pageable pageable) {
        log.debug("Request to get all MwClnts");
        return mwClntRepository.findAll(pageable);
    }

    /**
     * Get one mwClnt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwClnt findOne(Long id) {
        log.debug("Request to get MwClnt : {}", id);
        return mwClntRepository.findOneByClntSeqAndCrntRecFlg(id, true);
    }

    public PersonalInfoDto getNewClientId(PersonalInfoDto mwClnt) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        String valquery = Queries.statusSeq;
        Query qr = em.createNativeQuery(valquery);
        List<Object[]> resultSet = qr.getResultList();

        long draftSeq = 0L;
        long completedStatus = 0L;
        long activeStatus = 0L;
        for (Object[] str : resultSet) {
            if (str[1].toString().toLowerCase().equals("draft"))
                draftSeq = Long.valueOf(str[2].toString());
            if (str[1].toString().toLowerCase().equals("completed"))
                completedStatus = Long.valueOf(str[2].toString());
            if (str[1].toString().toLowerCase().equals("active"))
                activeStatus = Long.valueOf(str[2].toString());
        }
        MwClnt clnt = mwClntRepository.findOneByCnicNumAndCrntRecFlg(mwClnt.cnicNum, true);
        if (clnt != null) {
            if (clnt.getDob() == null || clnt.getFrstNm() == null) {
                PersonalInfoDto dto = new PersonalInfoDto();
                dto.clientSeq = clnt.getClntSeq();
                dto.cnicNum = clnt.getCnicNum();
                try {
                    dto.expiryDate = Date.from(clnt.getCnicExpryDt());
                } catch (Exception e) {
                    // TODO: handle exception
                    dto.expiryDate = null;
                }
                dto.isSAN = (clnt.isCoBwrSanFlg() == null) ? false : clnt.isCoBwrSanFlg();
                dto.selfPDC = (clnt.isSlfPdcFlg() == null) ? false : clnt.isSlfPdcFlg();
                List<MwLoanApp> exLoans = mwLoanAppRepository.findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc(clnt.getClntSeq(),
                        completedStatus);

                MwLoanApp loan = new MwLoanApp();
                loan.setPrntLoanAppSeq(loan.getLoanAppSeq());
                loan.setClntSeq(clnt.getClntSeq());
                loan.setPortSeq(mwClnt.portKey);
                loan.setLoanCyclNum(1L);
                loan.setSyncFlg(true);
                loan.setRqstdLoanAmt(0.0);
                loan.setAprvdLoanAmt(0.0);
                loan.setRcmndLoanAmt(0.0);
                loan.setPrdSeq(-1L);
                loan.setCrtdBy("w-" + user);
                loan.setCrtdDt(Instant.now());
                loan.setLastUpdBy("w-" + user);
                loan.setLastUpdDt(Instant.now());
                loan.setTblScrn(false);
                loan.setRelAddrAsClntFLg(false);
                loan.setCoBwrAddrAsClntFlg(false);
                exLoans.forEach(exLoan -> {
                    if (exLoan.getLoanCyclNum() >= loan.getLoanCyclNum()) {
                        loan.setLoanCyclNum(exLoan.getLoanCyclNum() + 1L);
                    }
                    if (exLoan.getPscScore() != null) {
                        dto.pscScore = exLoan.getPscScore();
                        dto.previousPscScore = exLoan.getPscScore();
                    }
                });
                loan.setLoanAppSeq(
                        Common.GenerateLoanAppSequence(mwClnt.cnicNum + "", loan.getLoanCyclNum().toString(), TableNames.MW_LOAN_APP));
                loan.setEffStartDt(Instant.now());
                loan.setLoanAppStsDt(Instant.now());
                loan.setCrntRecFlg(true);
                loan.setDelFlg(false);
                loan.setLoanAppSts(draftSeq);
                loan.setLoanId(String.format("%04d", loan.getLoanAppSeq()));
                MwLoanApp app = mwLoanAppRepository.save(loan);
                if (app != null)
                    dto.loanAppSeq = "" + app.getLoanAppSeq();
                dto.isNomDetailAvailable = true;
                dto.loanCyclNum = loan.getLoanCyclNum();
                dto.loanAppSeq = "" + loan.getLoanAppSeq();
                return dto;
            } else {
                PersonalInfoDto dto = findClientInformation(clnt.getClntSeq());

                MwLoanApp loan = new MwLoanApp();
                List<MwLoanApp> exAppList = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlgOrderByLoanCyclNumDesc(clnt.getClntSeq(),
                        true);

                MwLoanApp exApp = null;

                if (exAppList != null && exAppList.size() > 0) {
                    exApp = exAppList.get(0);
                    if (exApp != null) {

                        if (exApp.getLoanAppSts() == completedStatus) {

                            if (exApp.getLoanCyclNum() != null)
                                loan.setLoanCyclNum(exApp.getLoanCyclNum() + 1L);
                            if (exApp.getAprvdLoanAmt() != null)
                                dto.previousAmount = exApp.getAprvdLoanAmt().longValue();
                            if (exApp.getPscScore() != null)
                                dto.previousPscScore = exApp.getPscScore();
                            loan.setLoanAppStsDt(Instant.now());
                            loan.setEffStartDt(Instant.now());
                            loan.setCrntRecFlg(true);
                            loan.setDelFlg(false);
                            loan.setLoanAppSts(draftSeq);
                            loan.setPortSeq(mwClnt.portKey);
                            loan.setLoanAppSeq(Common.GenerateLoanAppSequence(mwClnt.cnicNum + "", loan.getLoanCyclNum().toString(),
                                    TableNames.MW_LOAN_APP));
                            loan.setPrntLoanAppSeq(loan.getLoanAppSeq());
                            loan.setClntSeq(clnt.getClntSeq());
                            loan.setLoanId(String.format("%04d", loan.getLoanAppSeq()));
                            loan.setSyncFlg(true);
                            loan.setRqstdLoanAmt(0.0);
                            loan.setAprvdLoanAmt(0.0);
                            loan.setRcmndLoanAmt(0.0);
                            loan.setCrtdBy("w-" + user);
                            loan.setCrtdDt(Instant.now());
                            loan.setLastUpdBy("w-" + user);
                            loan.setLastUpdDt(Instant.now());
                            loan.setPrdSeq(-1L);
                            loan.setTblScrn(false);
                            loan.setRelAddrAsClntFLg(false);
                            loan.setCoBwrAddrAsClntFlg(false);
                            MwLoanApp app = mwLoanAppRepository.save(loan);
                            if (app != null)
                                dto.loanAppSeq = "" + app.getLoanAppSeq();

                            dto.loanCyclNum = loan.getLoanCyclNum();
                        }

                    }
                }

                dto.isNomDetailAvailable = true;
                return dto;
            }
        } else {
            log.debug("Request to ===================== MwClnt : { else }");

            // Zohaib Asim - Dated 23-05-2022 - Changed to Database Sequence
            // long seq = Common.GenerateClientSequence( mwClnt.cnicNum + "" );
            long seq = 0;
            Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_CLNT")
                    .setParameter("userId", user);
            Object tblSeqRes = qry.getSingleResult();

            if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                seq = Long.parseLong(tblSeqRes.toString());
            }
            // End

            clnt = new MwClnt();
            clnt.setClntSeq(seq);
            clnt.setCnicNum(mwClnt.cnicNum);
            clnt.setEffStartDt(Instant.now());
            clnt.setCrntRecFlg(true);
            clnt.setCnicIssueDt(Instant.parse(mwClnt.cnicIssueDate));
            clnt.setCnicExpryDt(mwClnt.expiryDate.toInstant());
            clnt.setSyncFlg(true);
            clnt.setCrtdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            clnt.setCrtdDt(Instant.now());
            clnt.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            clnt.setLastUpdDt(Instant.now());
            clnt.setClntStsKey(activeStatus);
            clnt.setClntId("0" + seq);

            clnt.setFrstNm("N/A");
            clnt.setDob(Instant.now());
            clnt.setNumOfDpnd(0L);
            clnt.setErngMemb(0L);
            clnt.setNumOfChldrn(0L);
            clnt.setNumOfErngMemb(0L);
            clnt.setYrsRes(0L);
            clnt.setMnthsRes(0L);
            clnt.setPortKey(mwClnt.portKey);
            clnt.setMrtlStsKey(-1L);
            clnt.setOccKey(-1L);
            clnt.setClntStsKey(-1L);
            clnt.setResTypKey(-1L);
            clnt.setDisFlg(false);
            clnt.setNomDtlAvailableFlg(true);
            clnt.setSlfPdcFlg(false);
            clnt.setCrntAddrPermFlg(false);
            clnt.setCoBwrSanFlg(false);
            clnt.setPhNum("0");
            clnt.setTotIncmOfErngMemb(0L);
            clnt.setClntStsDt(Instant.now());
            clnt.setClntStsKey(-1L);
            clnt.setSmHsldFlg(false);
            clnt.setPftFlg(false);
            clnt.setEduLvlKey(-1L);
            clnt.setGndrKey(-1L);
            clnt.setHseHldMemb(0L);

            clnt = mwClntRepository.save(clnt);
            mwClnt.clientSeq = clnt.getClntSeq();

            MwLoanApp loan = new MwLoanApp();
            loan.setLoanAppSeq(Common.GenerateLoanAppSequence(mwClnt.cnicNum + "", "1", TableNames.MW_LOAN_APP));
            loan.setPrntLoanAppSeq(loan.getLoanAppSeq());
            loan.setClntSeq(clnt.getClntSeq());
            loan.setLoanCyclNum(1L);
            loan.setLoanAppStsDt(Instant.now());
            loan.setEffStartDt(Instant.now());
            loan.setCrntRecFlg(true);
            loan.setPortSeq(mwClnt.portKey);
            loan.setDelFlg(false);
            loan.setLoanAppSts(draftSeq);
            loan.setSyncFlg(true);
            loan.setRqstdLoanAmt(0.0);
            loan.setAprvdLoanAmt(0.0);
            loan.setRcmndLoanAmt(0.0);
            loan.setPrdSeq(-1L);
            loan.setTblScrn(false);
            loan.setRelAddrAsClntFLg(false);
            loan.setCoBwrAddrAsClntFlg(false);
            loan.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            loan.setLastUpdDt(Instant.now());
            loan.setCrtdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            loan.setCrtdDt(Instant.now());
            loan.setLoanId(String.format("%04d", loan.getLoanAppSeq()));
            MwLoanApp app = mwLoanAppRepository.save(loan);
            if (app != null)
                mwClnt.loanAppSeq = "" + app.getLoanAppSeq();
            mwClnt.isNomDetailAvailable = true;
            mwClnt.isSAN = false;
            mwClnt.loanCyclNum = loan.getLoanCyclNum();
            mwClnt.previousAmount = 0L;
            return mwClnt;
        }
    }

    public long updateClientBasicInfo(PersonalInfoDto mwClnt, String currUser) {
        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(mwClnt.clientSeq, true);
        if (clnt == null)
            return 0;

        Instant curIns = Instant.now();
        clnt.setClntSeq(clnt.getClntSeq());
        clnt.setClntId(String.format("%012d", clnt.getClntSeq()));
        clnt.setCnicNum(mwClnt.cnicNum);
        clnt.setCrntRecFlg(true);
        clnt.setCrtdBy("w-" + currUser);
        clnt.setCrtdDt(curIns);
        clnt.setDelFlg(false);
        clnt.setDisFlg(mwClnt.disableFlag);
        clnt.setDob(mwClnt.dob.toInstant());
        clnt.setEduLvlKey(mwClnt.eduLvlKey);
        clnt.setEffStartDt(clnt.getEffStartDt());
        clnt.setErngMemb(mwClnt.earningMembers);
        clnt.setFrstNm(mwClnt.firstName);
        clnt.setFthrFrstNm(mwClnt.fathrFirstName);
        clnt.setFthrLastNm(mwClnt.fathrLastName);
        clnt.setGndrKey(mwClnt.genderKey);
        clnt.setHseHldMemb(mwClnt.houseHoldMember);
        clnt.setLastNm(mwClnt.lastName);
        clnt.setLastUpdBy("w-" + currUser);
        clnt.setLastUpdDt(curIns);
        clnt.setMrtlStsKey(mwClnt.maritalStatusKey);
        clnt.setMthrMadnNm(mwClnt.motherMaidenName);
        clnt.setNatrOfDisKey(mwClnt.natureDisabilityKey);
        clnt.setNickNm(mwClnt.nickName);
        clnt.setSpzFrstNm(mwClnt.spzFirstName);
        clnt.setSpzLastNm(mwClnt.spzLastName);
        clnt.setNumOfChldrn(mwClnt.numOfChidren);
        clnt.setNumOfDpnd(mwClnt.numOfDependts);
        clnt.setNumOfErngMemb(mwClnt.earningMembers);
        clnt.setOccKey(mwClnt.occupationKey);
        clnt.setPhNum(mwClnt.phone);
        clnt.setPortKey(mwClnt.portKey);

        if (mwClnt.bizDtl != null && !mwClnt.bizDtl.isEmpty()) {
            clnt.setBizDtl(mwClnt.bizDtl);
        }

        clnt.setCrntAddrPermFlg(mwClnt.isPermAddress);
        clnt.setMnthsRes(mwClnt.mnthsOfResidence);
        clnt.setYrsRes((mwClnt.yearsOfResidence != null) ? mwClnt.yearsOfResidence : 0L);
        clnt.setResTypKey(mwClnt.residenceTypeKey);
        clnt.setSlfPdcFlg(mwClnt.selfPDC == null ? false : mwClnt.selfPDC);
        clnt.setSyncFlg(true);
        // clnt.setNomDtlAvailableFlg(mwClnt.isNomDetailAvailable);
        if (mwClnt.tokenDate != null && mwClnt.tokenNum != null && mwClnt.tokenNum != null) {
            MwCnicTkn exTkn = mwClntCnicTknRepository.findOneByLoanAppSeqAndCrntRecFlg(Long.parseLong(mwClnt.loanAppSeq), true);
            if (exTkn != null) {
                exTkn.setCrntRecFlg(false);
                exTkn.setLastUpdBy("w-" + currUser);
                exTkn.setLastUpdDt(curIns);
                mwClntCnicTknRepository.save(exTkn);
            }
            MwCnicTkn tkn = new MwCnicTkn();
            tkn.setCnicTknExpryDt(mwClnt.tokenDate.toInstant());
            tkn.setCnicTknNum(mwClnt.tokenNum);
            long loanCycleNum = 0;
            MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(Long.parseLong(mwClnt.loanAppSeq), true);
            if (app != null)
                loanCycleNum = app.getLoanCyclNum();
            tkn.setCnicTknSeq(
                    (exTkn == null) ? Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_CNIC_TKN, loanCycleNum)
                            : exTkn.getCnicTknSeq());
            tkn.setCrntRecFlg(true);
            tkn.setCrtdBy(currUser);
            tkn.setCrtdDt(curIns);
            tkn.setDelFlg(false);
            tkn.setEffStartDt(curIns);
            tkn.setLoanAppSeq(Long.parseLong(mwClnt.loanAppSeq));
            tkn.setLastUpdBy("w-" + currUser);
            tkn.setLastUpdDt(Instant.now());
            mwClntCnicTknRepository.save(tkn);
        }

        // clnt.setNomDtlAvailableFlg();
        // clnt.setSlfPdcFlg(Boolean);
        // clnt.setCnicSmartCardFlg(Boolean);
        // clnt.setCoBwrSanFlg(Boolean);
        // clnt.setTotIncmOfErngMemb(Long);
        // clnt.setCnicFmlyNum(String);

        return mwClntRepository.save(clnt).getClntSeq();

    }

    public long updateClientBasicInfoOnUpdate(PersonalInfoDto mwClnt, String currUser) {
        // MwClnt exClnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg( mwClnt.clientSeq, true );

        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(mwClnt.clientSeq, true);
        Instant currIns = Instant.now();
        if (clnt == null)
            return 0;

        clnt.setLastUpdDt(currIns);
        clnt.setLastUpdBy(currUser);
        clnt.setCnicNum(mwClnt.cnicNum);
        clnt.setCnicIssueDt(Instant.parse(mwClnt.cnicIssueDate));
        clnt.setCnicExpryDt(mwClnt.expiryDate.toInstant());
        clnt.setDisFlg(mwClnt.disableFlag);
        clnt.setDob(mwClnt.dob.toInstant());
        clnt.setEduLvlKey(mwClnt.eduLvlKey);
        clnt.setErngMemb(mwClnt.earningMembers);
        clnt.setFrstNm(mwClnt.firstName);
        clnt.setFthrFrstNm(mwClnt.fathrFirstName);
        clnt.setFthrLastNm(mwClnt.fathrLastName);
        clnt.setGndrKey(mwClnt.genderKey);
        clnt.setHseHldMemb(mwClnt.houseHoldMember);
        clnt.setLastNm(mwClnt.lastName);
        clnt.setMrtlStsKey(mwClnt.maritalStatusKey);
        clnt.setMthrMadnNm(mwClnt.motherMaidenName);
        clnt.setNatrOfDisKey(mwClnt.natureDisabilityKey);
        clnt.setNickNm(mwClnt.nickName);
        clnt.setSpzFrstNm(mwClnt.spzFirstName);
        clnt.setSpzLastNm(mwClnt.spzLastName);
        clnt.setNumOfChldrn(mwClnt.numOfChidren);
        clnt.setNumOfDpnd(mwClnt.numOfDependts);
        clnt.setNumOfErngMemb(mwClnt.earningMembers);
        clnt.setOccKey(mwClnt.occupationKey);
        clnt.setPhNum(mwClnt.phone);
        clnt.setPortKey(mwClnt.portKey);

        clnt.setCrntAddrPermFlg(mwClnt.isPermAddress);
        clnt.setMnthsRes(mwClnt.mnthsOfResidence);
        clnt.setYrsRes(mwClnt.yearsOfResidence);
        clnt.setResTypKey(mwClnt.residenceTypeKey);
        clnt.setSlfPdcFlg(mwClnt.selfPDC);
        clnt.setSyncFlg(true);

        if (mwClnt.tokenDate != null && mwClnt.tokenNum != null) {
            MwCnicTkn exTkn = mwClntCnicTknRepository.findOneByLoanAppSeqAndCrntRecFlg(Long.parseLong(mwClnt.loanAppSeq), true);

            long loanCycleNum = 0;
            MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(Long.parseLong(mwClnt.loanAppSeq), true);
            if (app != null)
                loanCycleNum = app.getLoanCyclNum();

            if (exTkn == null) {
                exTkn = new MwCnicTkn();
                exTkn.setCnicTknSeq(Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_CNIC_TKN, loanCycleNum));
                exTkn.setCrntRecFlg(true);
                exTkn.setCrtdBy("w-" + currUser);
                exTkn.setCrtdDt(Instant.now());
                exTkn.setDelFlg(false);
                exTkn.setEffStartDt(Instant.now());
                exTkn.setLoanAppSeq(Long.parseLong(mwClnt.loanAppSeq));

            }

            exTkn.setLastUpdBy("w-" + currUser);
            exTkn.setLastUpdDt(currIns);
            exTkn.setCnicTknExpryDt(mwClnt.tokenDate.toInstant());
            exTkn.setCnicTknNum(mwClnt.tokenNum);

            mwClntCnicTknRepository.save(exTkn);

        }

        // exClnt.setCrntRecFlg( false );
        // exClnt.setEffEndDt( currIns );
        // exClnt.setLastUpdBy( "w-" + currUser );
        // exClnt.setLastUpdDt( currIns );
        // mwClntRepository.save( exClnt );

        // MwClnt clnt = new MwClnt();

        // clnt.setClntSeq( mwClnt.clientSeq );
        // clnt.setClntId( exClnt.getClntId() );
        // clnt.setCnicNum( mwClnt.cnicNum );
        // clnt.setCnicExpryDt( mwClnt.expiryDate.toInstant() );
        // clnt.setCrntRecFlg( true );
        // clnt.setCrtdBy( "w-" + currUser );
        // clnt.setCrtdDt( currIns );
        // clnt.setDelFlg( false );
        // clnt.setDisFlg( mwClnt.disableFlag );
        // clnt.setDob( mwClnt.dob.toInstant() );
        // clnt.setEduLvlKey( mwClnt.eduLvlKey );
        // clnt.setEffStartDt( currIns );
        // clnt.setErngMemb( mwClnt.earningMembers );
        // clnt.setFrstNm( mwClnt.firstName );
        // clnt.setFthrFrstNm( mwClnt.fathrFirstName );
        // clnt.setFthrLastNm( mwClnt.fathrLastName );
        // clnt.setGndrKey( mwClnt.genderKey );
        // clnt.setHseHldMemb( mwClnt.houseHoldMember );
        // clnt.setLastNm( mwClnt.lastName );
        // clnt.setLastUpdBy( "w-" + currUser );
        // clnt.setLastUpdDt( currIns );
        // clnt.setMrtlStsKey( mwClnt.maritalStatusKey );
        // clnt.setMthrMadnNm( mwClnt.motherMaidenName );
        // clnt.setNatrOfDisKey( mwClnt.natureDisabilityKey );
        // clnt.setNickNm( mwClnt.nickName );
        // clnt.setSpzFrstNm( mwClnt.spzFirstName );
        // clnt.setSpzLastNm( mwClnt.spzLastName );
        // clnt.setNumOfChldrn( mwClnt.numOfChidren );
        // clnt.setNumOfDpnd( mwClnt.numOfDependts );
        // clnt.setNumOfErngMemb( mwClnt.earningMembers );
        // clnt.setOccKey( mwClnt.occupationKey );
        // clnt.setPhNum( mwClnt.phone );
        // clnt.setPortKey( mwClnt.portKey );
        //
        // clnt.setCrntAddrPermFlg( mwClnt.isPermAddress );
        // clnt.setMnthsRes( mwClnt.mnthsOfResidence );
        // clnt.setYrsRes( mwClnt.yearsOfResidence );
        // clnt.setResTypKey( mwClnt.residenceTypeKey );
        //
        // clnt.setClntStsKey( exClnt.getClntStsKey() );
        //
        // clnt.setSlfPdcFlg( mwClnt.selfPDC );
        // clnt.setNomDtlAvailableFlg( exClnt.getNomDtlAvailableFlg() );
        // clnt.setSyncFlg( true );
        // clnt.setCoBwrSanFlg( exClnt.getCoBwrSanFlg() );
        // if ( mwClnt.tokenDate != null && mwClnt.tokenNum != null && mwClnt.tokenNum != null ) {
        // MwCnicTkn exTkn = mwClntCnicTknRepository.findOneByLoanAppSeqAndCrntRecFlg( Long.parseLong( mwClnt.loanAppSeq ), true );
        // if ( exTkn != null ) {
        // exTkn.setCrntRecFlg( false );
        // exTkn.setLastUpdBy( "w-" + currUser );
        // exTkn.setLastUpdDt( Instant.now() );
        // mwClntCnicTknRepository.save( exTkn );
        // }
        // long loanCycleNum = 0;
        // MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg( Long.parseLong( mwClnt.loanAppSeq ), true );
        // if ( app != null )
        // loanCycleNum = app.getLoanCyclNum();
        // MwCnicTkn tkn = new MwCnicTkn();
        // tkn.setCnicTknExpryDt( mwClnt.tokenDate.toInstant() );
        // tkn.setCnicTknNum( mwClnt.tokenNum );
        // tkn.setCnicTknSeq(
        // ( exTkn == null ) ? Common.GenerateTableSequence( exClnt.getCnicNum().toString(), TableNames.MW_CNIC_TKN, loanCycleNum )
        // : exTkn.getCnicTknSeq() );
        // tkn.setCrntRecFlg( true );
        // tkn.setCrtdBy( "w-" + currUser );
        // tkn.setCrtdDt( Instant.now() );
        // tkn.setDelFlg( false );
        // tkn.setEffStartDt( Instant.now() );
        // tkn.setLoanAppSeq( Long.parseLong( mwClnt.loanAppSeq ) );
        // tkn.setLastUpdBy( "w-" + currUser );
        // tkn.setLastUpdDt( Instant.now() );
        // mwClntCnicTknRepository.save( tkn );
        // }
        // clnt.setSlfPdcFlg(Boolean);
        // clnt.setCnicSmartCardFlg(Boolean);
        // clnt.setCoBwrSanFlg(Boolean);
        // clnt.setTotIncmOfErngMemb(Long);
        // clnt.setCnicFmlyNum(String);

        return mwClntRepository.save(clnt).getClntSeq();

    }

    // @Transactional
    // public void updateFlag(long clientSeq, boolean flag) {
    // MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(clientSeq,true);
    // if (clnt == null)
    // return;
    // clnt.setNomDtlAvailableFlg(flag);
    // mwClntRepository.save(clnt);
    // return;
    // }
    @Transactional
    public void updateFlag(long clientSeq, boolean flag, String currUser) {
        MwLoanApp loan;
        MwClnt clnt = this.mwClntRepository.findOneByClntSeqAndCrntRecFlg(clientSeq, true);
        if (clnt == null) {
            return;
        }
        clnt.setLastUpdDt(Instant.now());
        clnt.setLastUpdBy(currUser);
        clnt.setNomDtlAvailableFlg(flag);
        clnt.setSyncFlg(true);
        this.mwClntRepository.save(clnt);
        List<MwLoanApp> loans = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlgOrderByLoanCyclNumDesc(clientSeq, true);
        if (loans.size() > 0 && (loan = (MwLoanApp) loans.get(0)) != null) {
            if (flag) {
                // MwClntRel rel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg( loan.getLoanAppSeq(), 3L, true );
                // if ( rel != null ) {
                // rel.setEffEndDt( Instant.now() );
                // rel.setCrntRecFlg( false );
                // rel.setLastUpdBy( "w-" );
                // rel.setLastUpdDt( Instant.now() );
                // this.mwClntRelRepository.save( rel );
                // return;
                // }
            } else {
                MwClntRel rel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(loan.getLoanAppSeq(), 1L, true);
                if (rel != null) {
                    rel.setEffEndDt(Instant.now());
                    rel.setCrntRecFlg(false);
                    rel.lastUpdBy(currUser);
                    rel.setLastUpdDt(Instant.now());
                    this.mwClntRelRepository.save(rel);
                    return;
                }
            }
        }
    }

    // @Transactional
    // public void updateFlagisSan(long clientSeq, boolean flag) {
    // MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(clientSeq,true);
    // if (clnt == null)
    // return;
    // clnt.setCoBwrSanFlg(flag);
    // mwClntRepository.save(clnt);
    // return;
    // }

    @Transactional
    public void updateFlagisSan(long clientSeq, boolean flag, String currUser) {
        MwLoanApp loan;
        MwClntRel rel;
        MwClnt clnt = this.mwClntRepository.findOneByClntSeqAndCrntRecFlg(clientSeq, true);
        if (clnt == null) {
            return;
        }
        clnt.setLastUpdDt(Instant.now());
        clnt.setLastUpdBy(currUser);
        clnt.setCoBwrSanFlg(flag);
        clnt.setSyncFlg(true);

        this.mwClntRepository.save(clnt);
        List<MwLoanApp> loans = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlgOrderByLoanCyclNumDesc(clientSeq, true);
        if (loans.size() > 0 && flag) {
            loan = loans.get(0);
            if (loan != null) {
                rel = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(loan.getLoanAppSeq(), 3L, true);
                if (rel != null) {
                    rel.setEffEndDt(Instant.now());
                    rel.setCrntRecFlg(false);
                    rel.setLastUpdBy(currUser);
                    rel.lastUpdDt(Instant.now());
                    mwClntRelRepository.save(rel);
                }
            }
        }
    }

    @Transactional
    public PersonalInfoDto findClientInformation(long clientSeq) {

        MwClnt clnt = null;
        MwAddrRel addRel = null;
        MwAddr addr = null;

        String valquery = Queries.statusSeq;
        Query qr = em.createNativeQuery(valquery);
        List<Object[]> resultSet = qr.getResultList();
        long draftSeq = 0L;
        long completedStatus = 0L;
        for (Object[] str : resultSet) {
            if (str[1].toString().toLowerCase().equals("draft"))
                draftSeq = Long.valueOf(str[2].toString());
            if (str[1].toString().toLowerCase().equals("completed"))
                completedStatus = Long.valueOf(str[2].toString());
        }

        clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(clientSeq, true);
        if (clnt == null)
            return null;
        if (clnt != null)
            addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(clientSeq, "Client", true);
        if (addRel != null)
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);

        PersonalInfoDto mwClnt = new PersonalInfoDto();
        mwClnt.clientSeq = clnt.getClntSeq();
        mwClnt.cnicNum = clnt.getCnicNum();
        mwClnt.clientId = clnt.getClntId();
        mwClnt.cnicIssueDate = (clnt.getCnicIssueDt() == null) ? null : String.valueOf(Date.from(clnt.getCnicIssueDt()));
        mwClnt.disableFlag = (clnt.isDisFlg() == null) ? false : clnt.isDisFlg();
        mwClnt.dob = (clnt.getDob() == null) ? null : Date.from(clnt.getDob());
        mwClnt.expiryDate = Date.from(clnt.getCnicExpryDt());
        mwClnt.eduLvlKey = clnt.getEduLvlKey();
        mwClnt.earningMembers = clnt.getErngMemb();
        mwClnt.firstName = clnt.getFrstNm();
        mwClnt.fathrFirstName = clnt.getFthrFrstNm();
        mwClnt.fathrLastName = clnt.getFthrLastNm();
        mwClnt.genderKey = clnt.getGndrKey();
        mwClnt.houseHoldMember = clnt.getHseHldMemb();
        mwClnt.lastName = clnt.getLastNm();
        mwClnt.maritalStatusKey = clnt.getMrtlStsKey();
        mwClnt.motherMaidenName = clnt.getMthrMadnNm();
        mwClnt.natureDisabilityKey = clnt.getNatrOfDisKey();
        mwClnt.nickName = clnt.getNickNm();
        mwClnt.numOfChidren = clnt.getNumOfChldrn();
        mwClnt.numOfDependts = clnt.getNumOfDpnd();
        mwClnt.earningMembers = clnt.getNumOfErngMemb();
        mwClnt.occupationKey = clnt.getOccKey();
        mwClnt.phone = clnt.getPhNum();
        mwClnt.portKey = (clnt.getPortKey() == null) ? 0 : clnt.getPortKey();
        mwClnt.residenceTypeKey = clnt.getResTypKey();
        mwClnt.isNomDetailAvailable = (clnt.isNomDtlAvailableFlg() == null) ? true : clnt.isNomDtlAvailableFlg();
        mwClnt.isSAN = (clnt.isCoBwrSanFlg() == null) ? false : clnt.isCoBwrSanFlg();
        mwClnt.selfPDC = (clnt.isSlfPdcFlg() == null) ? false : clnt.isSlfPdcFlg();
        mwClnt.bizDtl = clnt.getBizDtl();
        mwClnt.totIncmOfErngMemb = (clnt.getTotIncmOfErngMemb() == null) ? 0L : clnt.getTotIncmOfErngMemb();

        // Added by Areeba Naveed - Dated 13-05-2022
        mwClnt.membrshpDt = clnt.getMembrshpDt();
        mwClnt.refCdLeadTypSeq = clnt.getRefCdLeadTypSeq();
        // End

        // clnt.getSlfPdcFlg();
        // clnt.getTotIncmOfErngMemb();
        // mwClnt.isPermAddress = clnt.isResTypKey();
        // clnt.getCnicSmartCardFlg();
        // clnt.getCoBwrSanFlg(Boolean);

        if (addr != null) {

            Query q = em.createNativeQuery(Queries.entityAddress + addr.getCitySeq());

            List<Object[]> cityCombinations = q.getResultList();

            for (Object[] comb : cityCombinations) {

                mwClnt.country = Long.valueOf((comb[0] == null) ? "0" : comb[0].toString());
                mwClnt.countryName = (comb[1] == null) ? "" : comb[1].toString();
                mwClnt.province = Long.valueOf((comb[2] == null) ? "0" : comb[2].toString());
                mwClnt.provinceName = (comb[3] == null) ? "" : comb[3].toString();
                mwClnt.district = Long.valueOf((comb[4] == null) ? "0" : comb[4].toString());
                mwClnt.districtName = (comb[5] == null) ? "" : comb[5].toString();
                mwClnt.tehsil = Long.valueOf((comb[6] == null) ? "0" : comb[6].toString());
                mwClnt.tehsilName = (comb[7] == null) ? "" : comb[7].toString();
                mwClnt.uc = Long.valueOf((comb[8] == null) ? "0" : comb[8].toString());
                mwClnt.ucName = ((comb[9] == null) ? "" : comb[9].toString()) + " "
                        + ((comb[10] == null) ? "" : comb[10].toString());
                mwClnt.city = Long.valueOf((comb[11] == null) ? "0" : comb[11].toString());
                mwClnt.cityName = (comb[12] == null) ? "" : comb[12].toString();
            }

            mwClnt.addresSeq = addr.getAddrSeq();
            mwClnt.spzFirstName = clnt.getSpzFrstNm();
            mwClnt.spzLastName = clnt.getSpzLastNm();
            mwClnt.houseNum = addr.getHseNum();
            mwClnt.sreet_area = addr.getStrt();
            mwClnt.community = addr.getCmntySeq();
            String comstr = "select cmnty_nm, cmnty_seq from mw_cmnty cmnty where cmnty.crnt_rec_flg=1 and cmnty.del_flg=0 and cmnty.cmnty_seq="
                    + addr.getCmntySeq();
            Query comqr = em.createNativeQuery(comstr);
            List<Object[]> comresultSet = comqr.getResultList();
            for (Object[] str : comresultSet) {
                mwClnt.communityName = str[0] == null ? "" : str[0].toString();
            }
            mwClnt.village = addr.getVlg();
            mwClnt.otherDetails = addr.getOthDtl();
            mwClnt.lat = addr.getLatitude() == null ? 0.0 : addr.getLatitude();
            mwClnt.lon = addr.getLongitude() == null ? 0.0 : addr.getLongitude();
        }

        mwClnt.yearsOfResidence = clnt.getYrsRes();
        mwClnt.mnthsOfResidence = clnt.getMnthsRes();
        mwClnt.isPermAddress = clnt.isCrntAddrPermFlg();

        if (!clnt.isCrntAddrPermFlg()) {
            MwClntPermAddr clntPermAddr = mwClntPermAddrService.findOne(clientSeq);
            if (clntPermAddr != null)
                mwClnt.permAddress = clntPermAddr.getPermAddrStr();
        }

        List<MwLoanApp> loans = mwLoanAppRepository.findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc(clientSeq, completedStatus);
        if (loans.size() > 0) {
            if (loans.get(0) != null) {
                mwClnt.previousAmount = loans.get(0).getAprvdLoanAmt().longValue();
                mwClnt.loanCyclNum = loans.get(0).getLoanCyclNum() + 1L;
                mwClnt.previousPscScore = loans.get(0).getPscScore();
            }
        }

        return mwClnt;
    }


    @Transactional
    public PersonalInfoDto findClientLoansInformation(long clientSeq) {

        MwClnt clnt = null;
        MwAddrRel addRel = null;
        MwAddr addr = null;

        String valquery = Queries.statusSeq;
        Query qr = em.createNativeQuery(valquery);
        List<Object[]> resultSet = qr.getResultList();
        long draftSeq = 0L;
        long completedStatus = 0L;
        for (Object[] str : resultSet) {
            if (str[1].toString().toLowerCase().equals("draft"))
                draftSeq = Long.valueOf(str[2].toString());
            if (str[1].toString().toLowerCase().equals("completed"))
                completedStatus = Long.valueOf(str[2].toString());
        }

        clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(clientSeq, true);
        if (clnt == null)
            return null;
        if (clnt != null)
            addRel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(clientSeq, "Client", true);
        if (addRel != null)
            addr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(addRel.getAddrSeq(), true);

        PersonalInfoDto mwClnt = new PersonalInfoDto();
        mwClnt.clientSeq = clnt.getClntSeq();
        mwClnt.cnicNum = clnt.getCnicNum();
        mwClnt.clientId = clnt.getClntId();
        mwClnt.cnicIssueDate = (clnt.getCnicIssueDt() == null) ? null : String.valueOf(clnt.getCnicIssueDt());
        mwClnt.disableFlag = (clnt.isDisFlg() == null) ? false : clnt.isDisFlg();
        mwClnt.dob = (clnt.getDob() == null) ? null : Date.from(clnt.getDob());
        mwClnt.expiryDate = Date.from(clnt.getCnicExpryDt());
        mwClnt.eduLvlKey = clnt.getEduLvlKey();
        mwClnt.earningMembers = clnt.getErngMemb();
        mwClnt.firstName = clnt.getFrstNm();
        mwClnt.fathrFirstName = clnt.getFthrFrstNm();
        mwClnt.fathrLastName = clnt.getFthrLastNm();
        mwClnt.genderKey = clnt.getGndrKey();
        mwClnt.houseHoldMember = clnt.getHseHldMemb();
        mwClnt.lastName = clnt.getLastNm();
        mwClnt.maritalStatusKey = clnt.getMrtlStsKey();
        mwClnt.motherMaidenName = clnt.getMthrMadnNm();
        mwClnt.natureDisabilityKey = clnt.getNatrOfDisKey();
        mwClnt.nickName = clnt.getNickNm();
        mwClnt.numOfChidren = clnt.getNumOfChldrn();
        mwClnt.numOfDependts = clnt.getNumOfDpnd();
        mwClnt.earningMembers = clnt.getNumOfErngMemb();
        mwClnt.occupationKey = clnt.getOccKey();
        mwClnt.phone = clnt.getPhNum();
        mwClnt.portKey = (clnt.getPortKey() == null) ? 0 : clnt.getPortKey();
        mwClnt.residenceTypeKey = clnt.getResTypKey();
        mwClnt.isNomDetailAvailable = (clnt.isNomDtlAvailableFlg() == null) ? true : clnt.isNomDtlAvailableFlg();
        mwClnt.isSAN = (clnt.isCoBwrSanFlg() == null) ? false : clnt.isCoBwrSanFlg();
        mwClnt.selfPDC = (clnt.isSlfPdcFlg() == null) ? false : clnt.isSlfPdcFlg();
        mwClnt.bizDtl = clnt.getBizDtl();
        mwClnt.totIncmOfErngMemb = (clnt.getTotIncmOfErngMemb() == null) ? 0L : clnt.getTotIncmOfErngMemb();

        // Added by Areeba Naveed - Dated 13-05-2022
        mwClnt.membrshpDt = clnt.getMembrshpDt();
        mwClnt.refCdLeadTypSeq = clnt.getRefCdLeadTypSeq();
        // End

        // clnt.getSlfPdcFlg();
        // clnt.getTotIncmOfErngMemb();
        // mwClnt.isPermAddress = clnt.isResTypKey();
        // clnt.getCnicSmartCardFlg();
        // clnt.getCoBwrSanFlg(Boolean);

        if (addr != null) {

            Query q = em.createNativeQuery(Queries.entityAddress + addr.getCitySeq());

            List<Object[]> cityCombinations = q.getResultList();

            for (Object[] comb : cityCombinations) {

                mwClnt.country = Long.valueOf((comb[0] == null) ? "0" : comb[0].toString());
                mwClnt.countryName = (comb[1] == null) ? "" : comb[1].toString();
                mwClnt.province = Long.valueOf((comb[2] == null) ? "0" : comb[2].toString());
                mwClnt.provinceName = (comb[3] == null) ? "" : comb[3].toString();
                mwClnt.district = Long.valueOf((comb[4] == null) ? "0" : comb[4].toString());
                mwClnt.districtName = (comb[5] == null) ? "" : comb[5].toString();
                mwClnt.tehsil = Long.valueOf((comb[6] == null) ? "0" : comb[6].toString());
                mwClnt.tehsilName = (comb[7] == null) ? "" : comb[7].toString();
                mwClnt.uc = Long.valueOf((comb[8] == null) ? "0" : comb[8].toString());
                mwClnt.ucName = ((comb[9] == null) ? "" : comb[9].toString()) + " "
                        + ((comb[10] == null) ? "" : comb[10].toString());
                mwClnt.city = Long.valueOf((comb[11] == null) ? "0" : comb[11].toString());
                mwClnt.cityName = (comb[12] == null) ? "" : comb[12].toString();
            }

            mwClnt.addresSeq = addr.getAddrSeq();
            mwClnt.spzFirstName = clnt.getSpzFrstNm();
            mwClnt.spzLastName = clnt.getSpzLastNm();
            mwClnt.houseNum = addr.getHseNum();
            mwClnt.sreet_area = addr.getStrt();
            mwClnt.community = addr.getCmntySeq();
            String comstr = "select cmnty_nm, cmnty_seq from mw_cmnty cmnty where cmnty.crnt_rec_flg=1 and cmnty.del_flg=0 and cmnty.cmnty_seq="
                    + addr.getCmntySeq();
            Query comqr = em.createNativeQuery(comstr);
            List<Object[]> comresultSet = comqr.getResultList();
            for (Object[] str : comresultSet) {
                mwClnt.communityName = str[0] == null ? "" : str[0].toString();
            }
            mwClnt.village = addr.getVlg();
            mwClnt.otherDetails = addr.getOthDtl();
            mwClnt.lat = addr.getLatitude() == null ? 0.0 : addr.getLatitude();
            mwClnt.lon = addr.getLongitude() == null ? 0.0 : addr.getLongitude();
        }

        mwClnt.yearsOfResidence = clnt.getYrsRes();
        mwClnt.mnthsOfResidence = clnt.getMnthsRes();
        mwClnt.isPermAddress = clnt.isCrntAddrPermFlg();

        if (!clnt.isCrntAddrPermFlg()) {
            MwClntPermAddr clntPermAddr = mwClntPermAddrService.findOne(clientSeq);
            if (clntPermAddr != null)
                mwClnt.permAddress = clntPermAddr.getPermAddrStr();
        }

        List<MwLoanApp> loans = mwLoanAppRepository.findLoansByClntSeq(clientSeq);
        if (loans.size() > 0) {
            if (loans.get(0) != null) {
                mwClnt.previousAmount = loans.get(0).getAprvdLoanAmt().longValue();
                mwClnt.loanCyclNum = loans.get(0).getLoanCyclNum() + 1L;
                mwClnt.previousPscScore = loans.get(0).getPscScore();

                mwClnt.prevLoanProd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(loans.get(0).prdSeq, true).getPrdNm();
                mwClnt.prevLoanRemainInst = mwLoanAppRepository.findRemainingInstallmentOfPreviousLoan(loans.get(0).getLoanAppSeq());
                mwClnt.prevLoanOst = String.valueOf(mwLoanAppRepository.findRemainingOutstandingOfPreviousLoan(loans.get(0).getLoanAppSeq()));
            }
        }

        return mwClnt;
    }

    public MwClnt getActiveLoanClnt(String clntId) {
        // MwClnt clnt = null;
        // MwRefCdVal val = mwRefCdValRepository.findRefCdByGrpAndVal( "0106", "0005" );
        // if ( val != null ) {
        // List< MwClnt > clnts = mwClntRepository.findIfActiveLoan( clntId, val.getRefCdSeq() );
        // if ( clnts.size() > 0 )
        // clnt = clnts.get( 0 );
        // }
        return mwClntRepository.findOneByClntIdAndCrntRecFlg(clntId, true);
        // return clnt;
    }

    public MwClnt getClientByCNIC(Long cnic) {
        return mwClntRepository.findOneByCnicNumAndCrntRecFlg(cnic, true);
    }

    public List getClientsForBranch(Long brnchSeq) {
        List respClnts = new ArrayList<>();
        List<MwClnt> clnts = mwClntRepository.findAllClntsForBrnch(brnchSeq);
        clnts.forEach(clnt -> {
            Map<String, Object> clntMap = new HashMap<>();
            clntMap.put("clntSeq", clnt.getClntSeq());
            clntMap.put("clntName", clnt.getFrstNm() + " " + clnt.getLastNm());
            respClnts.add(clnts);
        });
        return respClnts;
    }

    public List<AppDto> getClientsListing() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("0======000 ---  REQUEST USER:  ", user);
        Query q = em.createNativeQuery(Queries.clientsListing);
        List<Object[]> results = q.getResultList();
        List<AppDto> data = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (Object[] obj : results) {
                AppDto dto = new AppDto();
                dto.loanAppId = obj[0].toString();
                dto.loanAppStatus = obj[1].toString();
                dto.firstName = obj[2].toString();
                if (obj[3] != null) {
                    dto.lastName = obj[3].toString();
                }
                dto.clientId = (obj[4] == null) ? "" : obj[4].toString();
                dto.cnicNum = obj[5].toString();
                dto.gender = obj[6].toString();
                dto.maritalStatus = obj[7].toString();
                dto.house_num = obj[8].toString();
                dto.city = obj[9].toString();
                dto.uc = obj[10].toString();
                dto.tehsil = obj[11].toString();
                dto.dist = obj[12].toString();
                dto.state = obj[13].toString();
                dto.country = obj[14].toString();
                dto.portfolio = obj[15].toString();
                dto.branch = obj[16].toString();
                dto.area = obj[17].toString();
                dto.region = obj[18].toString();
                data.add(dto);
            }
        }
        return data;
    }

    public List<AppDto> getClientsListingForUserId(String userId) {
        Query q = em.createNativeQuery(Queries.clientsListing(userId));
        List<Object[]> results = q.getResultList();
        List<AppDto> data = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (Object[] obj : results) {
                String status = (obj[1] == null) ? "" : obj[1].toString();
                if (userId.contains("noorb") || userId.contains("omer")) {
                    AppDto dto = createObject(obj);
                    if (!dto.firstName.equalsIgnoreCase(""))
                        data.add(dto);
                } else if (userId.equalsIgnoreCase("nimra") && !status.toLowerCase().equals("draft")
                        && !status.toLowerCase().equals("need more clarification")) {
                    AppDto dto = createObject(obj);
                    if (!dto.firstName.equalsIgnoreCase(""))
                        data.add(dto);
                } else if (userId.equalsIgnoreCase("admin")) {
                    AppDto dto = createObject(obj);
                    if (!dto.firstName.equalsIgnoreCase(""))
                        data.add(dto);
                }
            }
        }
        return data;
    }

    public List<AppDto> getClientsListingForUserId(String userId, String role) {
        Query q = em.createNativeQuery(Queries.clientsListing(userId));
        List<Object[]> results = q.getResultList();
        List<AppDto> data = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (Object[] obj : results) {
                String status = (obj[1] == null) ? "" : obj[1].toString();
                if (role.equals("bdo")) {
                    AppDto dto = createObject(obj);
                    if (!dto.firstName.equalsIgnoreCase(""))
                        data.add(dto);
                } else if (role.equals("bm") && !status.toLowerCase().equals("draft")
                        && !status.toLowerCase().equals("need more clarification")) {
                    AppDto dto = createObject(obj);
                    if (!dto.firstName.equalsIgnoreCase(""))
                        data.add(dto);
                } else if (role.equals("admin")) {
                    AppDto dto = createObject(obj);
                    if (!dto.firstName.equalsIgnoreCase(""))
                        data.add(dto);
                }
            }
        }
        return data;
    }

//    public Map< String, Object > getClientsListingForUserId( String userId, String role, Integer pageIndex, Integer pageSize, String sort,
//            String direction, String filterString, Long brnchSeq ) {
//
//        String valquery = Queries.statusSeq;
//        Query qr = em.createNativeQuery( valquery );
//        List< Object[] > resultSet = qr.getResultList();
//
//        long draftSeq = 0L;
//        long completedStatus = 0L;
//        long activeStatus = 0L;
//        List< Long > stsForRole = new ArrayList<>();
//        String stsList = "";
////		String filterLastSixMonth = ""; // to remove last six month check for ITO
//        for ( Object[] str : resultSet ) {
//            if ( !str[ 3 ].toString().equals( "1682" ) ) {
//                if ( str[ 3 ].toString().equals( "0001" ) || str[ 3 ].toString().equals( "0003" ) ) {
//                    if ( !role.equals( "bm" ) ) {
//                        stsForRole.add( Long.valueOf( str[ 2 ].toString() ) );
//                        stsList = ( stsList.length() > 0 ) ? stsList + "," + str[ 2 ].toString() : stsList + str[ 2 ].toString();
//                    }
//                } else if(role.equals("ito")){ // enable AppSts (Active, Approved, Advanced) for ITO
//                    if(str[ 3 ].toString().equals( "0005" ) || str[ 3 ].toString().equals( "0004" ) || str[ 3 ].toString().equals( "1305" )){
//                        stsList = ( stsList.length() > 0 ) ? stsList + "," + str[ 2 ].toString() : stsList + str[ 2 ].toString();
//                        stsForRole.add( Long.valueOf( str[ 2 ].toString() ) );
//                       // filterLastSixMonth = " and trunc(app.LAST_UPD_DT) between trunc(add_months( sysdate, -6 )) and trunc(sysdate) ";
//                    }
//                }else {
//                    stsList = ( stsList.length() > 0 ) ? stsList + "," + str[ 2 ].toString() : stsList + str[ 2 ].toString();
//                    stsForRole.add( Long.valueOf( str[ 2 ].toString() ) );
//                }
//                if ( str[ 1 ].toString().toLowerCase().equals( "draft" ) )
//                    draftSeq = Long.valueOf( str[ 2 ].toString() );
//                if ( str[ 1 ].toString().toLowerCase().equals( "completed" ) )
//                    completedStatus = Long.valueOf( str[ 2 ].toString() );
//                if ( str[ 1 ].toString().toLowerCase().equals( "active" ) )
//                    activeStatus = Long.valueOf( str[ 2 ].toString() );
//            }
//        }
//
//        /** Modified by Yousaf Dated: 02-Feb-2022
//         * Changes Portfolio Transfer
//         */
//
//        //String countHdr = "SELECT count(app.loan_app_seq)";
//        String appHdr = "SELECT app.loan_app_seq, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = app.loan_app_sts AND crnt_rec_flg = 1 ) AS loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_seq, clnt.cnic_num, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = clnt.gndr_key AND crnt_rec_flg = 1 ) AS gender_key, ( SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = clnt.mrtl_sts_key AND crnt_rec_flg = 1 ) AS marital_sts, ad.hse_num, cit.city_nm, uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm, app.loan_app_sts_dt, app.rcmnd_loan_amt, app.aprvd_loan_amt, prd.prd_nm, cmnty.cmnty_nm, ad.strt, ad.OTH_DTL, ad.vlg, prd.prd_seq, app.loan_id,(select max(INST_NUM) from MW_PYMT_SCHED_HDR psh join MW_PYMT_SCHED_DTL psd on psd.PYMT_SCHED_HDR_SEQ=psh.PYMT_SCHED_HDR_SEQ and psd.crnt_rec_flg=1 where psh.crnt_rec_flg=1 and psh.loan_app_Seq=app.loan_app_seq and pymt_sts_key not in (945)) inst_num, emp.emp_nm, (select count(1) from mw_asoc_prd_rel apr where apr.prd_seq=app.prd_seq and apr.crnt_rec_flg=1), clnt.CLNT_ID, "
//                + "fn_verisys_bm_approval (app.loan_app_seq) verisys, get_Verisys_Status (app.loan_app_seq, 'W') verisys_desc, (select count(1) from RPTB_PORT_TRF_DETAIL trf where trf.loan_app_seq = app.prnt_loan_app_seq) trf_la_seq ";
//
//        String nativeQuery = Queries.clientsListingForPagination( userId, stsList );
//
//        if ( filterString != null && filterString.length() > 0 ) {
//            String[] splited = filterString.split( "\\s+" );
//            for ( int i = 0; i < splited.length; i++ ) {
//                nativeQuery = nativeQuery
//                        + " and (Lower( (SELECT ref_cd_dscr FROM mw_ref_cd_val WHERE ref_cd_seq = app.loan_app_sts AND crnt_rec_flg = 1 )) Like '%%' or LOWER( clnt.frst_nm ) Like '%%' or LOWER( clnt.last_nm ) Like '%%'  or LOWER( port.port_nm ) Like '%%' or TO_CHAR(app.loan_app_sts_dt, 'dd-mm-yyyy') Like '%%' or TO_CHAR(app.LAST_UPD_DT, 'dd-mm-yyyy') Like '%%' or LOWER( prd.prd_nm ) Like '%%'  OR lower(app.loan_id) Like '%%' OR lower(clnt.CLNT_ID) Like '%%'  ) ";
//                nativeQuery = nativeQuery.replaceAll( "%%", "%" + splited[ i ] + "%" );
//            }
//
//        }
//
//        if ( brnchSeq != null ) {
//            nativeQuery = nativeQuery + "and brnch.brnch_seq=" + brnchSeq.longValue();
//        }
//
////		if (role.equals("ito")) {
////            nativeQuery += filterLastSixMonth;
////        }
//
//        nativeQuery = nativeQuery + " Order By " + ( ( sort == null ) ? "app.loan_app_sts_dt" : sort ) + " "
//                + ( ( direction == null ) ? "desc" : direction );
//
//        // Modified by Areeba - 29-11-2022 - Count Removal
//        //Long totalCountResult = new BigDecimal( em.createNativeQuery( countHdr + nativeQuery ).getSingleResult().toString() ).longValue();
//
//        Query q = em.createNativeQuery( appHdr + nativeQuery );
//        q.setFirstResult( ( pageIndex ) * pageSize );
//        q.setMaxResults( pageSize );
//        List< Object[] > results = q.getResultList();
//        List< AppDto > data = new ArrayList<>();
//        if ( results != null && results.size() > 0 ) {
//            for ( Object[] obj : results ) {
//                AppDto dto = createObject( obj );
//                data.add( dto );
//            }
//        }
//
//        //int pageNumber = ( int ) ( ( totalCountResult.longValue() / pageSize ) + 1 );
//        Map< String, Object > resp = new HashMap<>();
//        resp.put( "loans", data );
////        resp.put( "count", totalCountResult.longValue() );
////        resp.put( "pageNumber", pageNumber );
//        resp.put( "count", 100l );
//        resp.put( "pageNumber", 0l );
//        return resp;
//    }

    // Added by Areeba - Process Application Query - 12-12-2022
    public Map<String, Object> getClientsListingForUserId(String userId, String role, Integer pageIndex, Integer pageSize, String sort,
                                                          String direction, String filterString, Long brnchSeq) {

        String valquery = Queries.statusSeq;
        Query qr = em.createNativeQuery(valquery);
        List<Object[]> resultSet = qr.getResultList();

        long draftSeq = 0L;
        long completedStatus = 0L;
        long activeStatus = 0L;
        List<Long> stsForRole = new ArrayList<>();
        String stsList = "";
//		String filterLastSixMonth = ""; // to remove last six month check for ITO
        for (Object[] str : resultSet) {
            if (!str[3].toString().equals("1682")) {
                if (str[3].toString().equals("0001") || str[3].toString().equals("0003")) {
                    if (!role.equals("bm")) {
                        stsForRole.add(Long.valueOf(str[2].toString()));
                        stsList = (stsList.length() > 0) ? stsList + "," + str[2].toString() : stsList + str[2].toString();
                    }
                } else if (role.equals("ito")) { // enable AppSts (Active, Approved, Advanced) for ITO
                    if (str[3].toString().equals("0005") || str[3].toString().equals("0004") || str[3].toString().equals("1305")) {
                        stsList = (stsList.length() > 0) ? stsList + "," + str[2].toString() : stsList + str[2].toString();
                        stsForRole.add(Long.valueOf(str[2].toString()));
                        // filterLastSixMonth = " and trunc(app.LAST_UPD_DT) between trunc(add_months( sysdate, -6 )) and trunc(sysdate) ";
                    }
                } else {
                    stsList = (stsList.length() > 0) ? stsList + "," + str[2].toString() : stsList + str[2].toString();
                    stsForRole.add(Long.valueOf(str[2].toString()));
                }
                if (str[1].toString().toLowerCase().equals("draft"))
                    draftSeq = Long.valueOf(str[2].toString());
                if (str[1].toString().toLowerCase().equals("completed"))
                    completedStatus = Long.valueOf(str[2].toString());
                if (str[1].toString().toLowerCase().equals("active"))
                    activeStatus = Long.valueOf(str[2].toString());
            }
        }

        /** Modified by Yousaf Dated: 02-Feb-2022
         * Changes Portfolio Transfer
         */

        String nativeQuery = Queries.clientsListingForPagination(userId);
        String nativeQuery2 = Queries.clientsListingForPaginationP2(userId, stsList);
        String nativeQuery3 = Queries.clientsListingForPaginationP3();

        if (brnchSeq != null) {
            nativeQuery = nativeQuery + "AND LA.BRNCH_SEQ = NVL ( " + brnchSeq.longValue() + ", LA.BRNCH_SEQ)" +
                    nativeQuery2 + "AND LA.BRNCH_SEQ = NVL ( " + brnchSeq.longValue() + ", LA.BRNCH_SEQ)" +
                    nativeQuery3;
        } else {
            nativeQuery = nativeQuery + nativeQuery2 + nativeQuery3;
        }
        if (filterString != null && filterString.length() > 0) {
            nativeQuery = nativeQuery
                    + " WHERE (   LOWER (ref_cd_dscr) LIKE LOWER('%%')\n" +
                    "             OR LOWER (frst_nm) LIKE LOWER('%%')\n" +
                    "             OR LOWER (last_nm) LIKE LOWER('%%')\n" +
                    "             OR LOWER (CLNT_NM) LIKE LOWER('%%')\n" +
                    "             OR LOWER (prd_nm) LIKE LOWER('%%')\n" +
                    "             OR LOWER (LOAN_APP_ID) LIKE LOWER('%%')\n" +
                    "             OR LOWER (CLNT_ID) LIKE LOWER('%%')) ";
            nativeQuery = nativeQuery.replaceAll("%%", "%" + filterString + "%");
        }

        nativeQuery = nativeQuery + " Order By " + ((sort == null) ? "loan_app_sts_dt" : sort) + " "
                + ((direction == null) ? "desc" : direction);

        Query q = em.createNativeQuery(nativeQuery);
        q.setFirstResult((pageIndex) * pageSize);
        q.setMaxResults(pageSize);
        List<Object[]> results = q.getResultList();
        List<AppListDto> data = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (Object[] obj : results) {
                AppListDto dto = createListObject(obj);
                data.add(dto);
            }
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("loans", data);
        resp.put("count", 100l);
        resp.put("pageNumber", 0l);
        return resp;
    }

    public AppDto createObject(Object[] obj) {
        AppDto dto = new AppDto();
        dto.loanAppSeq = (obj[0] == null) ? 0L : new BigDecimal(obj[0].toString()).longValue();
        dto.loanAppStatus = (obj[1] == null) ? "" : obj[1].toString();
        dto.firstName = (obj[2] == null) ? "" : obj[2].toString();
        if (obj[3] != null) {
            dto.lastName = obj[3].toString();
        }
        dto.clientSeq = (obj[4] == null) ? "" : obj[4].toString();
        dto.cnicNum = (obj[5] == null) ? "" : obj[5].toString();
        dto.gender = (obj[6] == null) ? "" : obj[6].toString();
        dto.maritalStatus = (obj[7] == null) ? "" : obj[7].toString();
        dto.house_num = (obj[8] == null) ? "" : obj[8].toString();
        dto.city = (obj[9] == null) ? "" : obj[9].toString();
        dto.uc = (obj[10] == null) ? "" : obj[10].toString();
        dto.tehsil = (obj[11] == null) ? "" : obj[11].toString();
        dto.dist = (obj[12] == null) ? "" : obj[12].toString();
        dto.state = (obj[13] == null) ? "" : obj[13].toString();
        dto.country = (obj[14] == null) ? "" : obj[14].toString();
        dto.portfolio = (obj[15] == null) ? "" : obj[15].toString();
        dto.branch = (obj[16] == null) ? "" : obj[16].toString();
        dto.area = (obj[17] == null) ? "" : obj[17].toString();
        dto.region = (obj[18] == null) ? "" : obj[18].toString();
        dto.stsDate = (obj[19] == null) ? "" : obj[19].toString();
        dto.recAmount = (obj[20] == null) ? "" : obj[20].toString();
        dto.aprAmount = (obj[21] == null) ? "" : obj[21].toString();
        dto.prdNm = (obj[22] == null) ? "" : obj[22].toString();
        dto.cmntyNm = (obj[23] == null) ? "" : obj[23].toString();
        dto.street = (obj[24] == null) ? "" : obj[24].toString();
        dto.other_detail = (obj[25] == null) ? "" : obj[25].toString();
        dto.village = (obj[26] == null) ? "" : obj[26].toString();
        dto.prdSeq = (obj[27] == null) ? 0L : new BigDecimal(obj[27].toString()).longValue();
        dto.loanAppId = (obj[28] == null) ? "" : obj[28].toString();
        dto.paidInst = (obj[29] == null) ? 0 : new BigDecimal(obj[29].toString()).longValue();
        dto.empNm = (obj[30] == null) ? "" : obj[30].toString();
        dto.assocPrdCount = (obj[31] == null) ? 0 : new BigDecimal(obj[31].toString()).longValue();
        dto.clientId = (obj[32] == null) ? "" : obj[32].toString();
        dto.verisysCount = (obj[33] == null) ? 0 : Integer.valueOf(obj[33].toString());
        dto.verisysDesc = (obj[34] == null) ? "" : obj[34].toString();
        dto.trf_la_seq = (obj[35] == null) ? "" : obj[35].toString();
        return dto;
    }

    // Added by Areeba - Process Application Query - 12-12-2022
    public AppListDto createListObject(Object[] obj) {
        AppListDto dto = new AppListDto();

        dto.firstName = (obj[0] == null) ? "" : obj[0].toString();
        if (obj[1] != null) {
            dto.lastName = obj[1].toString();
        }
        dto.clientId = (obj[2] == null) ? "" : obj[2].toString();
        dto.loanAppId = (obj[3] == null) ? "" : obj[3].toString();
        dto.clientName = (obj[4] == null) ? "" : obj[4].toString();
        dto.clientSeq = (obj[5] == null) ? "" : obj[5].toString();
        dto.loanAppSeq = (obj[6] == null) ? 0L : new BigDecimal(obj[6].toString()).longValue();
        dto.loanAppStatus = (obj[7] == null) ? "" : obj[7].toString();
        dto.recAmount = (obj[8] == null) ? "" : obj[8].toString();
        dto.aprAmount = (obj[9] == null) ? "" : obj[9].toString();
        dto.prdSeq = (obj[10] == null) ? 0L : new BigDecimal(obj[10].toString()).longValue();
        dto.prdNm = (obj[11] == null) ? "" : obj[11].toString();
        dto.empNm = (obj[12] == null) ? "" : obj[12].toString();
        dto.stsDate = (obj[13] == null) ? "" : obj[13].toString();
        dto.paidInst = (obj[14] == null) ? 0 : new BigDecimal(obj[14].toString()).longValue();
        dto.trf_la_seq = (obj[15] == null) ? "" : obj[15].toString();
        dto.verisysCount = (obj[16] == null) ? 0 : Integer.valueOf(obj[16].toString());
        dto.verisysDesc = (obj[17] == null) ? "" : obj[17].toString();
        dto.assocPrdCount = (obj[18] == null) ? 0 : new BigDecimal(obj[18].toString()).longValue();

        return dto;
    }

    /*  Added by Yousaf : Dated 02-Feb-2022
       Portfolio Transfer
     */
    public List<AppDto> getClientsListingForUserId(FilterDTO filter) {
        String genderQuery = "";
        String query = "SELECT app.loan_app_seq, val3.ref_cd_dscr AS loan_app_sts, clnt.frst_nm, clnt.last_nm, clnt.clnt_id, clnt.cnic_num, val2.ref_cd_dscr as gender_key, val1.ref_cd_dscr as maritalSts, ad.hse_num, cit.city_nm, uc.uc_nm, thsl.thsl_nm, dist.dist_nm, st.st_nm, cntry.cntry_nm, port.port_nm, brnch.brnch_nm, area.area_nm, reg.reg_nm, app.loan_app_sts_dt, app.rcmnd_loan_amt, app.aprvd_loan_amt, prd.prd_nm, cmnty.cmnty_nm, ad.strt, ad.oth_dtl, ad.vlg, prd.prd_seq , app.loan_id,vw.paid_inst_num, emp.emp_nm, trf.clnt_seq trf_clnt_seq FROM mw_loan_app app JOIN mw_acl acl ON acl.port_seq =app.port_seq AND acl.user_id = '"
                + filter.userId
                + "' JOIN mw_clnt clnt ON app.clnt_seq =clnt.clnt_seq AND clnt.crnt_rec_flg=1 left outer join RPTB_PORT_TRF_DETAIL trf on trf.loan_app_seq = app.loan_app_seq LEFT OUTER JOIN mw_addr_rel ar ON ar.enty_Key =clnt.clnt_SEQ AND ar.crnt_rec_flg=1 AND ar.enty_typ ='Client' LEFT OUTER JOIN mw_addr ad ON ad.addr_seq =ar.addr_seq AND ad.crnt_rec_flg=1 LEFT OUTER JOIN mw_city_uc_rel rel ON rel.city_uc_rel_seq=ad.city_seq LEFT OUTER JOIN mw_city cit ON cit.city_seq =rel.city_seq AND cit.crnt_rec_flg=1 LEFT OUTER JOIN mw_uc uc ON uc.UC_SEQ =rel.uc_seq AND uc.crnt_rec_flg=1 LEFT OUTER JOIN mw_thsl thsl ON thsl.thsl_seq =uc.thsl_seq AND thsl.crnt_rec_flg=1 LEFT OUTER JOIN mw_dist dist ON dist.dist_seq =thsl.dist_seq AND dist.crnt_rec_flg=1 LEFT OUTER JOIN mw_st st ON st.st_seq =dist.st_seq AND st.crnt_rec_flg=1 LEFT OUTER JOIN mw_cntry cntry ON cntry.cntry_seq =st.st_seq AND cntry.crnt_rec_flg=1 LEFT OUTER JOIN mw_port port ON port.port_seq =app.port_seq AND port.crnt_rec_flg=1 LEFT OUTER JOIN mw_brnch brnch ON brnch.brnch_seq =port.brnch_seq AND brnch.crnt_rec_flg=1 LEFT OUTER JOIN mw_area area ON area.area_seq =brnch.area_seq AND area.crnt_rec_flg=1 LEFT OUTER JOIN mw_reg reg ON reg.reg_seq =area.reg_seq AND reg.crnt_rec_flg =1 LEFT OUTER JOIN mw_prd prd ON prd.prd_seq = app.prd_seq AND prd.crnt_rec_flg = 1 LEFT OUTER JOIN mw_cmnty cmnty ON cmnty.cmnty_seq = ad.cmnty_seq AND cmnty.crnt_rec_flg = 1 JOIN mw_ref_cd_val val2 ON val2.ref_cd_seq=clnt.gndr_key ";
        if (filter.genderCd != null && !filter.genderCd.equals("")) {
            filter.genderQuery = " AND val2.REF_CD='" + filter.genderCd + "' ";
            query = query + " AND val2.REF_CD='" + filter.genderCd + "' ";
        }

        query = query + " and val2.REF_CD_GRP_KEY=6 and val2.crnt_rec_flg=1 JOIN mw_ref_cd_val val1 ON val1.ref_cd_seq=clnt.mrtl_sts_key ";

        if (filter.maritalStsCd != null && !filter.maritalStsCd.equals("")) {
            filter.maritalQuery = " AND val2.REF_CD='" + filter.maritalStsCd + "' ";
            query = query + " AND val1.REF_CD='" + filter.maritalStsCd + "' ";
        }

        query = query + " and val1.REF_CD_GRP_KEY=7 and val1.crnt_rec_flg=1 JOIN mw_ref_cd_val val3 ON val3.ref_cd_seq=app.loan_app_sts ";

        if (filter.StatusCd != null && !filter.StatusCd.equals("")) {
            query = query + " AND val3.REF_CD='" + filter.StatusCd + "' ";
        }

        query = query + " AND val3.REF_CD_GRP_KEY=106 AND val3.crnt_rec_flg  =1 ";

        if (filter.geographyQuery != null && !filter.geographyQuery.equals("")) {
            query = query + " " + filter.geographyQuery;
        }

        query = query + " LEFT OUTER JOIN vw_loan_app vw on vw.loan_seq=app.loan_app_seq"
                + " LEFT OUTER JOIN mw_port_emp_rel perel on perel.port_seq=port.port_seq and perel.crnt_rec_flg=1"
                + " LEFT OUTER JOIN mw_emp emp on emp.emp_seq=perel.emp_seq"
                + " WHERE app.crnt_rec_flg=1 ORDER BY app.loan_app_sts_dt DESC ";
        String userId = filter.userId;
        Query q = em.createNativeQuery(query);
        List<Object[]> results = q.getResultList();
        List<AppDto> data = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (Object[] obj : results) {
                String status = (obj[1] == null) ? "" : obj[1].toString();
                if (filter.role.equals("bdo")) {
                    AppDto dto = createObject(obj);
                    data.add(dto);
                } else if (filter.role.equals("bm") && !status.toLowerCase().equals("draft")
                        && !status.toLowerCase().equals("need more clarification")) {
                    AppDto dto = createObject(obj);
                    data.add(dto);
                } else if (filter.role.equals("admin")) {
                    AppDto dto = createObject(obj);
                    data.add(dto);
                }
            }
        }
        return data;
    }

    public List<AppDto> getClientsListingWithFilters(String type, long seq) {

        StringBuffer query = new StringBuffer(Queries.clientsListingFilter);
        if (type.equals("country")) {
            query.append(" and cntry.cntry_seq=" + seq);
        } else if (type.equals("state")) {
            query.append(" and st.st_seq=" + seq);
        } else if (type.equals("district")) {
            query.append(" and dist.dist_seq=" + seq);
        } else if (type.equals("tehsil")) {
            query.append(" and thsl.thsl_seq=" + seq);
        } else if (type.equals("uc")) {
            query.append(" and uc.uc_seq=" + seq);
        } else if (type.equals("region")) {
            query.append(" and reg.reg_seq=" + seq);
        } else if (type.equals("area")) {
            query.append(" and area.area_seq=" + seq);
        } else if (type.equals("brnch")) {
            query.append(" and brnch.brnch_seq=" + seq);
        } else if (type.equals("port")) {
            query.append(" and port.port_seq=" + seq);
        } else if (type.equals("cmnty")) {
            query.append(" and cmnty.cmnty_seq=" + seq);
        }
        query.append(" ORDER BY app.LOAN_APP_SEQ DESC");
        log.debug("Type is: {} Seq is {}", type, seq);

        Query q = em.createNativeQuery(query.toString());
        List<Object[]> results = q.getResultList();
        List<AppDto> data = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (Object[] obj : results) {
                AppDto dto = new AppDto();
                dto.loanAppId = obj[0].toString();
                dto.loanAppStatus = obj[1].toString();
                dto.firstName = obj[2].toString();
                dto.lastName = (obj[3] == null) ? "" : obj[3].toString();
                dto.clientId = (obj[4] == null) ? "" : obj[4].toString();
                dto.cnicNum = obj[5].toString();
                dto.gender = obj[6].toString();
                dto.maritalStatus = obj[7].toString();
                dto.house_num = (obj[8] == null) ? "" : obj[4].toString();
                dto.city = obj[9].toString();
                dto.uc = obj[10].toString();
                dto.tehsil = obj[11].toString();
                dto.dist = obj[12].toString();
                dto.state = obj[13].toString();
                dto.country = obj[14].toString();
                dto.portfolio = obj[15].toString();
                dto.branch = obj[16].toString();
                dto.area = obj[17].toString();
                dto.region = obj[18].toString();
                data.add(dto);
            }
        }
        return data;
    }

    public Map<String, Object> getClientHistory(long clientSeq) {
        Map<String, Object> resp = new HashMap<String, Object>();
        PersonalInfoDto clientDto = findClientInformation(clientSeq);
        if (clientDto == null)
            return null;
        resp.put("client", clientDto);
        List<MwLoanApp> apps = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlgOrderByLoanCyclNumDesc(clientSeq, true);
        List<Map> loanApps = new ArrayList<>();
        apps.forEach(app -> {
            Map<String, Object> loan = new HashMap<String, Object>();
            loan.put("loan", app);
            loan.put("schedule", getSingleLoanRecovery(app.getPrntLoanAppSeq(), app.getClntSeq()));
            Query query = em
                    .createNativeQuery(
                            "select DSBMT_DT from MW_DSBMT_VCHR_HDR where del_flg=0 and crnt_rec_flg=1 and loan_app_seq=:loanAppSeq")
                    .setParameter("loanAppSeq", app.getLoanAppSeq());
            Object dsbmtDtObj = null;
            List results = query.getResultList();
            if (!results.isEmpty()) {
                dsbmtDtObj = (Object) results.get(0);
                String dsbmtDt = (dsbmtDtObj == null) ? "" : dsbmtDtObj.toString();
                loan.put("dsbmtDt", dsbmtDt);
            }
            loanApps.add(loan);
        });
        resp.put("loans", loanApps);
        return resp;
    }

    public Map<String, Object> getDashboardDataForTab(String user, Long brnchSeq) throws IOException {
        Map<String, Object> resp = new HashMap<>();

        String q;
        q = Common.readFile(Charset.defaultCharset(), "MyDashboard_INSRT.txt");
        Query rs5 = em.createNativeQuery(q).setParameter("userId", user).setParameter("BRNCH_SEQ", brnchSeq);

        // Query q = em.createNativeQuery( com.idev4.rds.util.Queries.OVER_DUE_LOANS ).setParameter( "brnch", obj[ 4 ].toString() )
        // .setParameter( "prdSeq", prdSeq ).setParameter( "aDt", asDt );
        int insertStatement = rs5.executeUpdate();

        String selectQueryStr = Common.readFile(Charset.defaultCharset(), "MyDashboard_SLCT.txt");
        Query selectQuery = em.createNativeQuery(selectQueryStr);
        List<Object[]> dresults = selectQuery.getResultList();

        // String dashQuery = Common.readFile( Charset.defaultCharset(), "dashboardQuery.txt" );
        // Query dquery = em.createNativeQuery( dashQuery ).setParameter( "userId", user ).setParameter( "BRNCH_SEQ", brnchSeq );
        // List dresults = dquery.getResultList();
        Map<String, Object> obj = new HashMap();
        if (!dresults.isEmpty()) {
            Object[] dresultsObj = (Object[]) dresults.get(0);
            obj.put("act_clnt", (dresultsObj[0] != null) ? dresultsObj[0].toString() : "");
            obj.put("ost_amt", (dresultsObj[1] != null) ? dresultsObj[1].toString() : "");
            obj.put("par_30", (dresultsObj[2] != null) ? dresultsObj[2].toString() : "");
            obj.put("par_1", (dresultsObj[3] != null) ? dresultsObj[3].toString() : "");
            obj.put("due_clnts", (dresultsObj[4] != null) ? dresultsObj[4].toString() : "");
            obj.put("tat", (dresultsObj[5] != null) ? dresultsObj[5].toString() : "");
            obj.put("ror", (dresultsObj[6] != null) ? dresultsObj[6].toString() : "");
            obj.put("od_clnt", (dresultsObj[7] != null) ? dresultsObj[7].toString() : "");
            obj.put("od_amt", (dresultsObj[8] != null) ? dresultsObj[8].toString() : "");
            obj.put("new_clnt_dsbmt_amt", (dresultsObj[9] != null) ? dresultsObj[9].toString() : "");
            obj.put("rpt_clnt_dsbmt_amt", (dresultsObj[10] != null) ? dresultsObj[10].toString() : "");
            obj.put("new_fp_clnt", (dresultsObj[11] != null) ? dresultsObj[11].toString() : "");
            obj.put("rpt_fp_clnt", (dresultsObj[12] != null) ? dresultsObj[12].toString() : "");
            obj.put("od_amt_1_day", (dresultsObj[13] != null) ? dresultsObj[13].toString() : "");
            obj.put("od_amt_30_day", (dresultsObj[14] != null) ? dresultsObj[14].toString() : "");
            obj.put("utl_chk", (dresultsObj[15] != null) ? dresultsObj[15].toString() : "");
            obj.put("pnd_clnt", (dresultsObj[16] != null) ? dresultsObj[16].toString() : "");
            obj.put("prj_clnt", (dresultsObj[17] != null) ? dresultsObj[17].toString() : "");
            obj.put("new_clnt", (dresultsObj[18] != null) ? dresultsObj[18].toString() : "");
            obj.put("rpt_clnt", (dresultsObj[19] != null) ? dresultsObj[19].toString() : "");

            // Added by Zohaib Asim - Dated 20-05-2022 - To Revise Female Participation Formula
            obj.put("new_clnt_1", (dresultsObj[20] != null) ? dresultsObj[20].toString() : "");
            obj.put("rpt_clnt_1", (dresultsObj[21] != null) ? dresultsObj[21].toString() : "");
        }
        resp.put("dashboard", obj);

        Query dasboardTargetQuery = em.createNativeQuery(Common.readFile(Charset.defaultCharset(), "dashboardTargetQuery.txt"))
                .setParameter("userId", user).setParameter("brnch_seq", brnchSeq);
        List<Object[]> dasboardTargetResult = dasboardTargetQuery.getResultList();
        List<Map<String, ?>> dasboardTargetList = new ArrayList();
        dasboardTargetResult.forEach(l -> {
            Map<String, Object> map = new HashMap();
            map.put("prd_cmnt", l[0] == null ? "" : l[0].toString());
            map.put("act_clnt", l[1] == null ? "" : l[1].toString());
            map.put("trgt_clnt", l[2] == null ? "" : l[2].toString());
            map.put("trgt_amt", l[3] == null ? "" : l[3].toString());
            map.put("prt_cnt", l[4] == null ? "" : l[4].toString());
            map.put("achvd", l[5] == null ? "" : l[5].toString());
            map.put("achvd_amt", l[6] == null ? "" : l[6].toString());
            dasboardTargetList.add(map);
        });
        resp.put("targets", dasboardTargetList);

        Query achvdTargetQuery = em.createNativeQuery(Common.readFile(Charset.defaultCharset(), "achvdTargetQuery.txt"))
                .setParameter("userId", user);
        List<Object[]> achvdTargetResult = achvdTargetQuery.getResultList();
        List<Map<String, ?>> achvdTargetList = new ArrayList();
        achvdTargetResult.forEach(l -> {
            Map<String, Object> map = new HashMap();
            map.put("achvdChks", l[0] == null ? "" : l[0].toString());
            map.put("trgtChks", l[1] == null ? "" : l[1].toString());
            map.put("maxTrgt", l[2] == null ? "" : l[2].toString());
            achvdTargetList.add(map);
        });
        resp.put("chks", achvdTargetList);
        return resp;
    }

    public Map<String, Object> getClientHistoryForTab(long clientSeq) {
        Map<String, Object> resp = new HashMap<>();

        PersonalInfoDto clientDto = findClientInformation(clientSeq);
        if (clientDto == null) {
            return null;
        }
        Map<String, Object> clntMap = new HashMap<String, Object>();
        clntMap.put("clientId", clientDto.clientId);
        clntMap.put("clientSeq", clientDto.clientSeq);
        clntMap.put("name", ((clientDto.firstName != null) ? clientDto.firstName : "") + ((clientDto.firstName != null) ? " " : "")
                + ((clientDto.lastName != null) ? clientDto.lastName : ""));
        clntMap.put("fathrName",
                ((clientDto.fathrFirstName != null) ? clientDto.fathrFirstName : "")
                        + ((clientDto.fathrFirstName != null) ? " " : "")
                        + ((clientDto.fathrLastName != null) ? clientDto.fathrLastName : ""));
        clntMap.put("spzName", ((clientDto.spzFirstName != null) ? clientDto.spzFirstName : "")
                + ((clientDto.spzFirstName != null) ? " " : "") + ((clientDto.spzLastName != null) ? clientDto.spzLastName : ""));
        clntMap.put("cnicNum", clientDto.cnicNum);
        String addrStr = "House No. " + clientDto.houseNum
                + (clientDto.sreet_area != null && clientDto.sreet_area.length() > 0 ? ", " + clientDto.sreet_area : "")
                + (clientDto.communityName != null && clientDto.communityName.length() > 0 ? ", " + clientDto.communityName : "")
                + (clientDto.village != null && clientDto.village.length() > 0 ? ", " + clientDto.village : "")
                + (clientDto.otherDetails != null && clientDto.otherDetails.length() > 0 ? ", " + clientDto.otherDetails : "") + ".";
        clntMap.put("address", addrStr);
        MwRefCdVal val = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(clientDto.genderKey, true);
        clntMap.put("gender", (val != null) ? val.getRefCdDscr() : null);

        String brnchPortQuery = "select b.brnch_nm, prt.port_nm from  mw_brnch b  join mw_port prt on prt.BRNCH_SEQ=b.BRNCH_SEQ and prt.crnt_rec_flg=1 \r\n"
                + "            join mw_loan_app ap on ap.PORT_SEQ=prt.PORT_SEQ and ap.crnt_rec_flg=1 and ap.loan_cycl_num=(select max(loan_cycl_num) from mw_loan_app where crnt_rec_flg=1 and clnt_seq=ap.clnt_seq) \r\n"
                + "             join mw_clnt clnt on clnt.clnt_Seq = ap.clnt_seq and clnt.crnt_rec_flg=1\r\n"
                + "            where b.CRNT_REC_FLG=1 and clnt.CLNT_seq=" + clientDto.clientSeq;
        Query bpquery = em.createNativeQuery(brnchPortQuery);
        List bpresults = bpquery.getResultList();
        if (!bpresults.isEmpty()) {
            Object[] bpresultsObj = (Object[]) bpresults.get(0);
            clntMap.put("branch", (bpresultsObj[0] != null) ? bpresultsObj[0].toString() : "");
            clntMap.put("port", (bpresultsObj[1] != null) ? bpresultsObj[1].toString() : "");
        }
        resp.put("client", clntMap);
        // Modified By Naveed - Date - 04-04-2022
        // Same loan cycle id issue encase Need More clarification
        List<MwLoanApp> apps = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlgAndLoanAppStsNotOrderByLoanCyclNumDesc(clientSeq, true, 701);
        // end by Naveed
        List<Map> loanApps = new ArrayList<>();
        List<Map> assocLoan = new ArrayList<>();
        apps.forEach(app -> {
            Map<String, Object> loan = new HashMap<String, Object>();
            MwPrd prd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(app.getPrdSeq(), true);
            if (prd != null) {
                Map<String, Object> product = new HashMap<String, Object>();
                product.put("prdName", prd.getPrdNm());
                product.put("cmnt", prd.getPrdCmnt());
                product.put("prdId", prd.getPrdId());
                product.put("prdSeq", prd.getPrdSeq());
                loan.put("product", product);
            }

            Map<String, Object> loanMap = new HashMap<String, Object>();
            loanMap.put("loanAppSeq", app.getLoanAppSeq());
            loanMap.put("loanId", app.getLoanAppId());
            loanMap.put("loanAppSeq", app.getLoanAppSeq());
            loanMap.put("loanCyclNum", app.getLoanCyclNum());
            loanMap.put("aprvdLoanAmt", app.getAprvdLoanAmt());
            loanMap.put("rcmndLoanAmt", app.getRcmndLoanAmt());
            loanMap.put("loanAppSts", app.getLoanAppSts());
            loanMap.put("loanAppStsDt", app.getLoanAppStsDt());
            loanMap.put("prntLoanAppSeq", app.getPrntLoanAppSeq());
            loanMap.put("portSeq", app.getPortSeq());


            // added by Yousaf Date: 14-Feb-2022.
            // get brnch //
            Query query1 = em
                    .createNativeQuery(
                            "select brnch_nm from mw_brnch where crnt_rec_flg=1 and brnch_seq = :brnchSeq")
                    .setParameter("brnchSeq", app.getBrnchSeq());
            Object brnchNm1 = null;
            List results1 = query1.getResultList();
            if (!results1.isEmpty()) {
                brnchNm1 = (Object) results1.get(0);
                String brnchNm = (brnchNm1 == null) ? "" : brnchNm1.toString();
                loanMap.put("brnchNm", brnchNm);
            }
            // end get brnch name //

            if (app.getLoanAppSeq().longValue() == app.getPrntLoanAppSeq().longValue()) {
                loan.put("schedule", getSingleLoanRecovery(app.getPrntLoanAppSeq(), app.getClntSeq()));
                Query query = em
                        .createNativeQuery(
                                "select DSBMT_DT from MW_DSBMT_VCHR_HDR where del_flg=0 and crnt_rec_flg=1 and loan_app_seq=:loanAppSeq")
                        .setParameter("loanAppSeq", app.getLoanAppSeq());
                Object dsbmtDtObj = null;
                List results = query.getResultList();
                if (!results.isEmpty()) {
                    dsbmtDtObj = (Object) results.get(0);
                    String dsbmtDt = (dsbmtDtObj == null) ? "" : dsbmtDtObj.toString();
                    loanMap.put("dsbmtDt", dsbmtDt);
                }
                loan.put("loan", loanMap);
                loanApps.add(loan);
            } else {
                loan.put("loan", loanMap);
                assocLoan.add(loan);
            }
        });

        loanApps.forEach(loan -> {
            Map loanApp = (Map) loan.get("loan");
            Long loanSeq = Long.parseLong(loanApp.get("loanAppSeq").toString());
            List<Map> assocArray = new ArrayList<>();
            assocLoan.forEach(assoc -> {
                Map assocLoanApp = (Map) assoc.get("loan");
                Long prntLoanSeq = Long.parseLong(assocLoanApp.get("prntLoanAppSeq").toString());
                if (prntLoanSeq.longValue() == loanSeq.longValue()) {
                    assocArray.add(assoc);
                }
            });
            loan.put("assocLoans", assocArray);
        });

        resp.put("loans", loanApps);
        return resp;
    }

    public List<RecoveryListingDTO> getSingleLoanRecovery(long loanSeq, long clntSeq) {
        Query q = em.createNativeQuery(Queries.ALLONERECOVERYBYLOANAPP).setParameter("loanSeq", loanSeq).setParameter("clntSeq",
                clntSeq);
        List<Object[]> results = q.getResultList();
        List<RecoveryListingDTO> recoveryListingDTOs = new ArrayList();
        if (results != null && results.size() != 0) {
            results.forEach(obj -> {
                RecoveryListingDTO recoveryListingDTO = new RecoveryListingDTO();
                recoveryListingDTO.paySchedDtlSeq = new BigDecimal(obj[0].toString()).longValue();
                // recoveryListingDTO.clntSeq = obj[ 1 ].toString();
                // recoveryListingDTO.frstNm = obj[ 2 ] != null ? obj[ 2 ].toString() : "";
                // recoveryListingDTO.lastNm = obj[ 3 ] != null ? obj[ 3 ].toString() : "";
                // recoveryListingDTO.loanId = obj[ 4 ] != null ? obj[ 4 ].toString() : "";
                recoveryListingDTO.instNum = (obj[1] == null) ? 0L : new BigDecimal(obj[1].toString()).longValue();
                recoveryListingDTO.ppalAmtDue = (obj[2] == null) ? 0L : new BigDecimal(obj[2].toString()).longValue();
                recoveryListingDTO.totChrgDue = (obj[3] == null ? 0L : new BigDecimal(obj[3].toString()).longValue())
                        + (obj[4] == null ? 0L : new BigDecimal(obj[4].toString()).longValue());
                recoveryListingDTO.dueDt = obj[5] == null ? "" : obj[5].toString();
                recoveryListingDTO.refCd = (obj[6] == null) ? "" : obj[6].toString();
                // recoveryListingDTO.branchNm = obj[ 11 ] != null ? obj[ 11 ].toString() : "";
                recoveryListingDTO.trxSeq = (obj[7] == null) ? "" : obj[7].toString();
                recoveryListingDTO.pymtDt = (obj[8] == null) ? "" : obj[8].toString();
                recoveryListingDTO.instr = (obj[9] == null) ? "" : obj[9].toString();
                recoveryListingDTO.rcvryTyp = (obj[10] == null) ? "" : obj[10].toString();
                recoveryListingDTO.pymtAmt = obj[11] == null ? "" : obj[11].toString();
                recoveryListingDTO.post = obj[12] == null ? "" : obj[12].toString();
                recoveryListingDTO.trxPymt = obj[13] == null ? "" : obj[13].toString();
                recoveryListingDTO.pymtType = obj[14] == null ? "" : obj[14].toString();
                recoveryListingDTO.prdNm = obj[15] == null ? "" : obj[15].toString();
                recoveryListingDTOs.add(recoveryListingDTO);
            });
        }
        return recoveryListingDTOs;
    }

    public DashboardDto getDashboardData(DashboardDto dto) {
        String valquery = Queries.dashBoardQuery;
        valquery = valquery.replaceAll(":startDate", dto.startDate);
        valquery = valquery.replaceAll(":endDate", dto.endDate);
        Query qr = em.createNativeQuery(valquery);
        // qr.setParameter( "startDate", dto.startDate );
        // qr.setParameter( "endDate", dto.endDate );
        qr.setParameter("ports", dto.ports);
        qr.setParameter("grps", dto.grps);
        List<Object[]> resultSet = qr.getResultList();
        if (resultSet != null) {
            resultSet.forEach(obj -> {
                dto.trgtClnts = obj[0] == null ? "0" : obj[0].toString();
                dto.achvdClnts = obj[1] == null ? "0" : obj[1].toString();
                dto.totAmtDue = obj[2] == null ? "0" : obj[2].toString();
                dto.totAmtRcvd = obj[3] == null ? "0" : obj[3].toString();
                dto.tbCmpltd = obj[4] == null ? "0" : obj[4].toString();
                dto.rtndClnt = obj[5] == null ? "0" : obj[5].toString();
                dto.tat = obj[6] == null ? "0" : obj[6].toString();
                dto.newLoanSize = obj[7] == null ? "0" : obj[7].toString();
                dto.rptLoanSize = obj[8] == null ? "0" : obj[8].toString();
                dto.femalePart = obj[9] == null ? "0" : obj[9].toString();
                dto.ipClnt = obj[10] == null ? "0" : obj[10].toString();
                dto.ipAmt = obj[11] == null ? "0" : obj[11].toString();
            });
        }
        return dto;
    }

    public void getPdcDetailReport(String fromDt, String toDt, long brnchSeq, String userId) {

        // Query bi = em.createNativeQuery( com.idev4.rds.util.Queries.BRANCH_INFO_BY_BRANCH ).setParameter( "brnchSeq", brnchSeq );
        // Object[] obj = ( Object[] ) bi.getSingleResult();

        Map<String, Object> params = new HashMap<>();
        // params.put( "reg_nm", obj[ 0 ].toString() );
        // params.put( "area_nm", obj[ 1 ].toString() );
        // params.put( "brnch_nm", obj[ 2 ].toString() );
        // params.put( "brnch_cd", obj[ 3 ].toString() );
        // params.put( "curr_user", userId );

        Query pdcDetail = em.createNativeQuery("select distinct clnt.frst_nm||' '||clnt.last_nm clnt_nm,\r\n"
                + "    cbr.frst_nm||' '||cbr.last_nm pdc_prvdr_nm,\r\n" + "    bnk.ref_cd_dscr,\r\n" + "    phdr.acct_num,\r\n"
                + "    phdr.pdc_hdr_seq,\r\n" + "--    ap.loan_app_seq\r\n" + "    chq_num\r\n" + "from mw_loan_app ap\r\n"
                + "join mw_clnt clnt on clnt.clnt_seq=ap.clnt_seq\r\n"
                + "join mw_dsbmt_vchr_hdr dh on dh.loan_app_seq=ap.loan_app_seq and dh.crnt_rec_flg=1\r\n"
                + "join mw_clnt_rel cbr on cbr.loan_app_seq=ap.loan_app_seq and cbr.crnt_rec_flg=1 and cbr.rel_typ_flg=3\r\n"
                + "join mw_ref_cd_val asts on asts.ref_cd_seq=ap.loan_app_sts and asts.crnt_rec_flg=1\r\n"
                + "join mw_pdc_hdr phdr on phdr.loan_app_seq=ap.loan_app_seq and phdr.crnt_rec_flg=1\r\n"
                + "join mw_pdc_dtl pdtl on pdtl.pdc_hdr_seq=phdr.pdc_hdr_seq and pdtl.crnt_rec_flg=1\r\n"
                + "join mw_ref_cd_val bnk on bnk.ref_cd_seq=phdr.bank_key and asts.crnt_rec_flg=1\r\n" + "where ap.crnt_rec_flg=1\r\n"
                + "  and ( (asts.ref_cd='0005' and to_date(ap.loan_app_sts_dt) <= :to_dt and ap.crnt_rec_flg=1) --toDate \r\n"
                + "                    or (asts.ref_cd='0006' and to_date(ap.loan_app_sts_dt) > :to_dt)--toDate\r\n"
                + "                    or (asts.ref_cd='1245' and to_date(ap.loan_app_sts_dt) > :to_dt))--toDate\r\n"
                + "  and dh.dsbmt_dt between :from_dt and :to_dt").setParameter("from_dt", fromDt).setParameter("to_dt", toDt);
        List<Object[]> pdcDetailList = pdcDetail.getResultList();
        List<Map<String, ?>> reportParams = new ArrayList();
        pdcDetailList.forEach(l -> {
            Map<String, Object> map = new HashMap();
            map.put("CLNT_NM", l[0] == null ? "" : l[0].toString());
            map.put("PDC_PRVDR_NM", l[1] == null ? "" : l[1].toString());
            map.put("REF_CD_DESC", l[2] == null ? "" : l[2].toString());
            map.put("ACCT_NUM", l[3] == null ? "" : l[3].toString());
            map.put("PDC_HDR_SEQ", l[4] == null ? "" : l[4].toString());
            map.put("CHQ_NUM", l[5] == null ? "" : l[5].toString());
            reportParams.add(map);
        });
        // params.put( "dataset", getJRDataSource( reportParams ) );
        params.put("from_dt", fromDt);
        params.put("to_dt", toDt);
        // return reportComponent.generateReport( PDC_DETAIL, params, getJRDataSource( reportParams ) );
    }

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * Client wise Portfolio Transfer */
    @Transactional
    public ResponseEntity updatePortfolio(AppDto[] dtos) {
        Map<String, String> resp = new HashMap<String, String>();

        //
        for (AppDto dto : dtos) {
            MwPort newPortSeq = mwPortRepository.findOneByPortSeqAndCrntRecFlg(Long.parseLong(dto.portSeq), true);

            log.info("PortfolioTransferClientDetail-> dto: " + dto);
            log.info("NewPortfolio: " + newPortSeq);

            //
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("port_trf_pkg.prc_portfolio_transfer");
            storedProcedure.registerStoredProcedureParameter("P_TO_PORT_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("L_TO_BRNCH_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("L_FROM_BRNCH_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_CLIENT_SEQ", Long.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_LOGIN_USER", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("P_RTN_MSG", String.class, ParameterMode.OUT);

            Long frmBrnchSeq = Long.parseLong(dto.branchSeq);
            Long toBrnchSeq = Long.parseLong(newPortSeq.getBrnchSeq().toString());
            Long clntSeq = Long.parseLong(dto.clientId);
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();

            storedProcedure.setParameter("P_TO_PORT_SEQ", Long.parseLong(dto.portSeq));
            storedProcedure.setParameter("L_FROM_BRNCH_SEQ", frmBrnchSeq);
            storedProcedure.setParameter("L_TO_BRNCH_SEQ", toBrnchSeq);
            storedProcedure.setParameter("P_CLIENT_SEQ", clntSeq);
            storedProcedure.setParameter("P_LOGIN_USER", userId);
            // execute SP => RETURN success message : success
            String returnMessage = "";
            try {
                storedProcedure.execute();
                returnMessage = storedProcedure.getOutputParameterValue("P_RTN_MSG").toString();
                log.info(returnMessage);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                storedProcedure = null;
                System.gc();
            }

            if (!returnMessage.toString().equals("success")) {
                resp.put("failed", returnMessage);
                return ResponseEntity.ok().body(resp);
            }
        }
        resp.put("success", "Updated Successfully");
        return ResponseEntity.ok().body(resp);
    }

    /* Added by Yousaf Ali - Dated 18-Jan-2022
     * Complete Portfolio Transfer */
    @Transactional
    public ResponseEntity CompletePortfoliTransfer(long fromPort, long fromBranch, long toPort, long toBrnch, String userId) {
        Map<String, String> resp = new HashMap<String, String>();

        log.info("FromPort: " + fromPort + ", ToPort: " + toPort + ", ToBranch: " + toBrnch + ", User:" + userId);

        // Procedure
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("port_trf_pkg.prc_portfolio_transfer_port");
        storedProcedure.registerStoredProcedureParameter("P_FROM_PORT_SEQ", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_TO_PORT_SEQ", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("L_FROM_BRNCH_SEQ", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("L_TO_BRNCH_SEQ", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_LOGIN_USER", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("V_RTN_MSG", String.class, ParameterMode.OUT);

        storedProcedure.setParameter("P_FROM_PORT_SEQ", fromPort);
        storedProcedure.setParameter("P_TO_PORT_SEQ", toPort);
        storedProcedure.setParameter("L_FROM_BRNCH_SEQ", fromBranch);
        storedProcedure.setParameter("L_TO_BRNCH_SEQ", toBrnch);
        storedProcedure.setParameter("P_LOGIN_USER", userId);
        String returnMessage = "";
        try {
            storedProcedure.execute();
            returnMessage = storedProcedure.getOutputParameterValue("V_RTN_MSG").toString();
            log.info(returnMessage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            storedProcedure = null;
            System.gc();
        }
        if (!returnMessage.toString().equals("success")) {
            resp.put("failed", returnMessage);
            return ResponseEntity.ok().body(resp);
        }
        resp.put("success", "Updated Successfully");
        return ResponseEntity.ok().body(resp);
    }

    public boolean updateClientPortfolio(String seq, Long portKey) {
        MwClnt exClnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(Long.parseLong(seq), true);
        if (exClnt == null)
            return false;

        String curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        exClnt.setLastUpdBy("ex-transfer");
        exClnt.setLastUpdDt(Instant.now());
        exClnt.setCrntRecFlg(false);
        exClnt.setDelFlg(true);
        exClnt.setEffEndDt(Instant.now());
        mwClntRepository.save(exClnt);
        MwClnt clnt = new MwClnt();
        clnt.setBizDtl(exClnt.getBizDtl());
        clnt.setClntId(exClnt.getClntId());
        clnt.setClntSeq(exClnt.getClntSeq());
        clnt.setClntStsKey(exClnt.getClntStsKey());
        clnt.setCnicIssueDt(exClnt.getCnicIssueDt());
        clnt.setCnicExpryDt(exClnt.getCnicExpryDt());
        clnt.setCnicNum(exClnt.getCnicNum());
        clnt.setCoBwrSanFlg(exClnt.getCoBwrSanFlg());
        clnt.setCrntAddrPermFlg(exClnt.getCrntAddrPermFlg());
        clnt.setCrntRecFlg(true);
        clnt.setCrtdBy(exClnt.getCrtdBy());
        clnt.setCrtdDt(exClnt.getCrtdDt());
        clnt.setDelFlg(false);
        clnt.setDisFlg(exClnt.getDisFlg());
        clnt.setDob(exClnt.getDob());
        clnt.setEduLvlKey(exClnt.getEduLvlKey());
        clnt.setEffStartDt(Instant.now());
        clnt.setErngMemb(exClnt.getErngMemb());
        clnt.setFrstNm(exClnt.getFrstNm());
        clnt.setFthrFrstNm(exClnt.getFthrFrstNm());
        clnt.setFthrLastNm(exClnt.getFthrLastNm());
        clnt.setGndrKey(exClnt.getGndrKey());
        clnt.setHseHldMemb(exClnt.getHseHldMemb());
        clnt.setLastNm(exClnt.getLastNm());
        clnt.setLastUpdBy("transfer");
        clnt.setLastUpdDt(Instant.now());
        clnt.setMnthsRes(exClnt.getMnthsRes());
        clnt.setMrtlStsKey(exClnt.getMrtlStsKey());
        clnt.setMthrMadnNm(exClnt.getMthrMadnNm());
        clnt.setNatrOfDisKey(exClnt.getNatrOfDisKey());
        clnt.setNickNm(exClnt.getNickNm());
        clnt.setNomDtlAvailableFlg(exClnt.getNomDtlAvailableFlg());
        clnt.setNumOfChldrn(exClnt.getNumOfChldrn());
        clnt.setNumOfDpnd(exClnt.getNumOfDpnd());
        clnt.setNumOfErngMemb(exClnt.getNumOfErngMemb());
        clnt.setOccKey(exClnt.getOccKey());
        clnt.setPhNum(exClnt.getPhNum());
        clnt.setPortKey(portKey);
        clnt.setResTypKey(exClnt.getResTypKey());
        clnt.setSlfPdcFlg(exClnt.getSlfPdcFlg());
        clnt.setSpzFrstNm(exClnt.getSpzFrstNm());
        clnt.setSpzLastNm(exClnt.getSpzLastNm());
        clnt.setSyncFlg(exClnt.getSyncFlg());
        clnt.setTotIncmOfErngMemb(exClnt.getTotIncmOfErngMemb());
        clnt.setYrsRes(exClnt.getYrsRes());
        mwClntRepository.save(clnt);
        MwRefCdVal val = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0006");
        if (val != null) {
            List<MwLoanApp> exLoans = mwLoanAppRepository.findAllByClntSeqAndLoanAppStsNotAndCrntRecFlg(clnt.getClntSeq(),
                    val.getRefCdSeq(), true);
            List<MwLoanApp> loans = new ArrayList<>();
            List<MwLoanAppTrans> transfers = new ArrayList<>();
            exLoans.forEach(exLoan -> {
                exLoan.setLastUpdBy("ex-transfer");
                exLoan.setLastUpdDt(Instant.now());
                exLoan.setCrntRecFlg(false);
                exLoan.setDelFlg(true);
                exLoan.setEffEndDt(Instant.now());
                mwLoanAppRepository.save(exLoan);
                MwLoanApp loan = new MwLoanApp();
                loan.setAprvdLoanAmt(exLoan.getAprvdLoanAmt());
                loan.setClntSeq(exLoan.getClntSeq());
                loan.setCmnt(exLoan.getCmnt());
                loan.setCoBwrAddrAsClntFlg(exLoan.getCoBwrAddrAsClntFlg());
                loan.setCrdtBnd(exLoan.getCrdtBnd());
                loan.setCrntRecFlg(true);
                loan.setCrtdBy(exLoan.getCrtdBy());
                loan.setCrtdDt(exLoan.getCrtdDt());
                loan.setDelFlg(false);
                loan.setEffStartDt(Instant.now());
                loan.setLastUpdBy("transfer");
                loan.setLastUpdDt(Instant.now());
                loan.setLoanAppId(exLoan.getLoanAppId());
                loan.setLoanAppSeq(exLoan.getLoanAppSeq());
                loan.setLoanAppSts(exLoan.getLoanAppSts());
                loan.setLoanAppStsDt(exLoan.getLoanAppStsDt());
                loan.setLoanCyclNum(exLoan.getLoanCyclNum());
                loan.setLoanId(exLoan.getLoanId());
                loan.setLoanUtlCmnt(exLoan.getLoanUtlCmnt());
                loan.setLoanUtlStsSeq(exLoan.getLoanUtlStsSeq());
                loan.setPortSeq(portKey);
                loan.setPrdSeq(exLoan.getPrdSeq());
                loan.setPrntLoanAppSeq(exLoan.getPrntLoanAppSeq());
                loan.setPscScore(exLoan.getPscScore());
                loan.setRcmndLoanAmt(exLoan.getRcmndLoanAmt());
                loan.setRejectionReasonCd(exLoan.getRejectionReasonCd());
                loan.setRelAddrAsClntFLg(exLoan.getRelAddrAsClntFLg());
                loan.setRqstdLoanAmt(exLoan.getRqstdLoanAmt());
                loan.setSyncFlg(exLoan.getSyncFlg());
                loan.setTblScrn(exLoan.getTblScrn());
                loans.add(loan);

                Long trnsSeq = SequenceFinder.findNextVal(Sequences.LOAN_APP_TRNS_SEQ);
                MwLoanAppTrans trns = new MwLoanAppTrans();
                trns.setCrntRecFlg(true);
                trns.setCrtdBy(curUser);
                trns.setCrtdDt(Instant.now());
                trns.setDelFlg(false);
                trns.setEffStartDt(Instant.now());
                trns.setFromPort(exLoan.getPortSeq());
                trns.setLastUpdBy(curUser);
                trns.setLastUpdDt(Instant.now());
                trns.setLoanAppSeq(exLoan.getLoanAppSeq());
                trns.setLoanAppTrnsSeq(trnsSeq);
                trns.setToPort(portKey);
                trns.setTrnsDt(Instant.now());
                transfers.add(trns);
            });
            mwLoanAppRepository.save(loans);
            mwLoanAppTrnsRepository.save(transfers);
        }
        return true;
    }

    // Edited by Areeba
    public List getUpdForUser() {
        String clntUpdStr = "SELECT upd.loan_app_seq,\n" +
                "       clnt.frst_nm || ' ' || clnt.last_nm     AS CLNT_NM,\n" +
                "       upd.cnic_expry_dt,\n" +
                "       upd.cnic_frnt_pic,\n" +
                "       upd.cnic_bck_pic,\n" +
                "       val.ref_cd_dscr,\n" +
                "       upd.cmnt,\n" +
                "       clnt.CNIC_NUM,\n" +
                "       'CLIENT'                                REL_TYP\n" +
                "  FROM mw_cnic_upd  upd\n" +
                "       JOIN mw_ref_cd_val val\n" +
                "           ON     val.REF_CD_SEQ = upd.upd_sts\n" +
                "              AND val.CRNT_REC_FLG = 1\n" +
                "              AND val.ref_cd = '0002'\n" +
                "       JOIN mw_ref_cd_grp grp\n" +
                "           ON     grp.ref_cd_grp_seq = val.ref_cd_grp_key\n" +
                "              AND grp.CRNT_REC_FLG = 1\n" +
                "              AND grp.ref_cd_grp = '0106'\n" +
                "       JOIN mw_loan_app app\n" +
                "           ON app.loan_app_seq = upd.loan_app_seq AND app.crnt_rec_flg = 1\n" +
                "       JOIN mw_ref_cd_val val2\n" +
                "           ON     val2.REF_CD_SEQ = app.loan_app_sts\n" +
                "              AND val2.CRNT_REC_FLG = 1\n" +
                "              AND (   val2.ref_cd = '1305'\n" +
                "                   OR val2.ref_cd = '0009'\n" +
                "                   OR val2.ref_cd = '0005'\n" +
                "                   OR val2.ref_cd = '0004'\n" +
                "                   OR val2.ref_cd = '0003'\n" +
                "                   OR val2.ref_cd = '0002')\n" +
                "       JOIN mw_clnt clnt\n" +
                "           ON clnt.CNIC_NUM = upd.CNIC_NUM AND clnt.crnt_rec_flg = 1\n" +
                "       JOIN mw_acl acl\n" +
                "           ON acl.port_seq = app.port_seq AND acl.user_id = :userId\n" +
                "UNION ALL\n" +
                "SELECT upd.loan_app_seq,\n" +
                "       clnt.frst_nm || ' ' || clnt.last_nm\n" +
                "           AS CLNT_NM,\n" +
                "       upd.cnic_expry_dt,\n" +
                "       upd.cnic_frnt_pic,\n" +
                "       upd.cnic_bck_pic,\n" +
                "       val.ref_cd_dscr,\n" +
                "       upd.cmnt,\n" +
                "       clnt.CNIC_NUM,\n" +
                "       DECODE (cLNT.REL_TYP_FLG,\n" +
                "               1, 'NOMINEE',\n" +
                "               2, 'NEXT OF KIN',\n" +
                "               3, 'CO-BORROWER',\n" +
                "               4, 'RELATIVE',\n" +
                "               'OTHERS')\n" +
                "           REL_TYP\n" +
                "  FROM mw_cnic_upd  upd\n" +
                "       JOIN mw_ref_cd_val val\n" +
                "           ON     val.REF_CD_SEQ = upd.upd_sts\n" +
                "              AND val.CRNT_REC_FLG = 1\n" +
                "              AND val.ref_cd = '0002'\n" +
                "       JOIN mw_ref_cd_grp grp\n" +
                "           ON     grp.ref_cd_grp_seq = val.ref_cd_grp_key\n" +
                "              AND grp.CRNT_REC_FLG = 1\n" +
                "              AND grp.ref_cd_grp = '0106'\n" +
                "       JOIN mw_loan_app app\n" +
                "           ON app.loan_app_seq = upd.loan_app_seq AND app.crnt_rec_flg = 1\n" +
                "       JOIN mw_ref_cd_val val2\n" +
                "           ON     val2.REF_CD_SEQ = app.loan_app_sts\n" +
                "              AND val2.CRNT_REC_FLG = 1\n" +
                "              AND (   val2.ref_cd = '1305'\n" +
                "                   OR val2.ref_cd = '0009'\n" +
                "                   OR val2.ref_cd = '0005'\n" +
                "                   OR val2.ref_cd = '0004'\n" +
                "                   OR val2.ref_cd = '0003'\n" +
                "                   OR val2.ref_cd = '0002')\n" +
                "       JOIN mw_clnt_REL cLNT\n" +
                "           ON cLNT.CNIC_NUM = upd.CNIC_NUM AND CLNT.LOAN_APP_SEQ = UPD.LOAN_APP_SEQ AND clnt.crnt_rec_flg = 1\n" +
                "       JOIN mw_acl acl\n" +
                "           ON acl.port_seq = app.port_seq AND acl.user_id = :userId";
        Query rs = em.createNativeQuery(clntUpdStr).setParameter("userId",
                SecurityContextHolder.getContext().getAuthentication().getName());

        List<Object[]> clntUpdList = rs.getResultList();
        List<Map<String, ?>> resp = new ArrayList();
        clntUpdList.forEach(l -> {
            Map<String, Object> map = new HashMap();
            map.put("loanAppSeq", l[0] == null ? "" : l[0].toString());
            map.put("clntNm", l[1] == null ? "" : l[1].toString());
            map.put("cnicExpryDt", l[2] == null ? "" : l[2].toString());
            String fStr = "";
            if (l[3] != null) {
                Clob clob = (Clob) l[3];
                try {
                    fStr = clob.getSubString(1, (int) clob.length());
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            map.put("cnicFrntPic", fStr);
            String bStr = "";
            if (l[4] != null) {
                Clob clob = (Clob) l[4];
                try {
                    bStr = clob.getSubString(1, (int) clob.length());
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            map.put("cnicBckPic", bStr);
            map.put("status", l[5] == null ? "" : l[5].toString());
            map.put("cmnt", l[6] == null ? "" : l[6].toString());
            map.put("cnicNum", l[7] == null ? "" : l[7].toString());
            map.put("relTyp", l[8] == null ? "" : l[8].toString());

            resp.add(map);
        });
        return resp;
    }

    // Edited by Areeba
    public long approveCnicUpd(Long loanAppSeq, String cmnt, Long cnicNum, String relTyp) {
        MwCnicUpd cnicUpd = mwCnicUpdRepository.findOneByLoanAppSeq(loanAppSeq);
        if (cnicUpd == null)
            return 0;
        MwRefCdVal apprSts = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0004");
        cnicUpd.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        cnicUpd.setLastUpdDt(Instant.now());
        cnicUpd.setUpdSts(apprSts.getRefCdSeq());
        cnicUpd.setCmnt(cmnt);
        //Updated by Areeba - Dated - 24-3-2022
        cnicUpd.setCnicNum(cnicNum);
        //Ended by Areeba

        List<MwLoanAppDoc> docs = new ArrayList<>();
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        MwLoanAppDoc cnicFrntDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(loanAppSeq, 2, true);
        MwLoanAppDoc updFrntDoc = new MwLoanAppDoc();
        if (cnicFrntDoc != null) {
            cnicFrntDoc.setCrntRecFlg(false);
            cnicFrntDoc.setDelFlg(true);
            cnicFrntDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            cnicFrntDoc.setLastUpdDt(Instant.now());
            docs.add(cnicFrntDoc);
        }
        updFrntDoc.setCrntRecFlg(true);
        updFrntDoc.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        updFrntDoc.setCrtdDt(Instant.now());
        updFrntDoc.setDelFlg(false);
        updFrntDoc.setDocImg(cnicUpd.getCnicFrntPic());
        updFrntDoc.setDocSeq(2l);
        updFrntDoc.setEffStartDt(Instant.now());
        updFrntDoc.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        updFrntDoc.setLastUpdDt(Instant.now());
        updFrntDoc.setLoanAppDocSeq((cnicFrntDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum())
                : cnicFrntDoc.getLoanAppDocSeq()));
        updFrntDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
        docs.add(updFrntDoc);
        MwLoanAppDoc cnicBckDoc = mwLoanAppDocRepository.findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(loanAppSeq, 3, true);
        MwLoanAppDoc updBckDoc = new MwLoanAppDoc();
        if (cnicBckDoc != null) {
            cnicBckDoc.setCrntRecFlg(false);
            cnicBckDoc.setDelFlg(true);
            cnicBckDoc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            cnicBckDoc.setLastUpdDt(Instant.now());
            docs.add(cnicBckDoc);
        }
        updBckDoc.setCrntRecFlg(true);
        updBckDoc.setCrtdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        updBckDoc.setCrtdDt(Instant.now());
        updBckDoc.setDelFlg(false);
        updBckDoc.setDocImg(cnicUpd.getCnicBckPic());
        updBckDoc.setDocSeq(3l);
        updBckDoc.setEffStartDt(Instant.now());
        updBckDoc.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        updBckDoc.setLastUpdDt(Instant.now());
        updBckDoc.setLoanAppDocSeq(
                (cnicBckDoc == null ? Long.parseLong(app.getClntSeq() + "46" + app.getLoanCyclNum()) : cnicBckDoc.getLoanAppDocSeq()));
        updBckDoc.setLoanAppSeq(cnicUpd.getLoanAppSeq());
        docs.add(updBckDoc);
        //cnic update here
        //Updated by Areeba - Dated - 24-3-2022
        if (relTyp.equals("NOMINEE")) {
            MwClntRel loan = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCnicNumAndCrntRecFlg(loanAppSeq, 1, cnicNum, true);
            if (loan != null) {
                loan.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                loan.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                loan.setLastUpdDt(Instant.now());
                mwClntRelRepository.save(loan);
            }
        } else if (relTyp.equals("NEXT OF KIN")) {
            MwClntRel loan = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCnicNumAndCrntRecFlg(loanAppSeq, 2, cnicNum, true);
            if (loan != null) {
                loan.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                loan.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                loan.setLastUpdDt(Instant.now());
                mwClntRelRepository.save(loan);
            }
        } else if (relTyp.equals("CO-BORROWER")) {
            MwClntRel loan = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCnicNumAndCrntRecFlg(loanAppSeq, 3, cnicNum, true);
            if (loan != null) {
                loan.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                loan.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                loan.setLastUpdDt(Instant.now());
                mwClntRelRepository.save(loan);
            }
        } else if (relTyp.equals("RELATIVE")) {
            MwClntRel loan = mwClntRelRepository.findOneByLoanAppSeqAndRelTypFlgAndCnicNumAndCrntRecFlg(loanAppSeq, 4, cnicNum, true);
            if (loan != null) {
                loan.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                loan.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                loan.setLastUpdDt(Instant.now());
                mwClntRelRepository.save(loan);
            }
        } else {
            MwLoanApp loan = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
            if (loan != null) {
                MwClnt clnt = mwClntRepository.findOneByClntSeqAndCnicNumAndCrntRecFlg(loan.getClntSeq(), cnicNum, true);
                if (clnt != null) {
                    clnt.setCnicExpryDt(cnicUpd.getCnicExpryDt());
                    clnt.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                    clnt.setLastUpdDt(Instant.now());
                    mwClntRepository.save(clnt);
                }
            }
        }

        mwLoanAppDocRepository.save(docs);
        mwCnicUpdRepository.save(cnicUpd);
        //Ended by Areeba
        return cnicUpd.getCnicUpdSeq();
    }

    public long rejectCnicUpd(Long loanAppSeq, String cmnt) {
        MwCnicUpd cnicUpd = mwCnicUpdRepository.findOneByLoanAppSeq(loanAppSeq);
        if (cnicUpd == null)
            return 0;
        MwRefCdVal apprSts = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0007");
        cnicUpd.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        cnicUpd.setLastUpdDt(Instant.now());
        cnicUpd.setUpdSts(apprSts.getRefCdSeq());
        cnicUpd.setCmnt(cmnt);
        mwCnicUpdRepository.save(cnicUpd);
        return cnicUpd.getCnicUpdSeq();
    }

    public long sendBckCnicUpd(Long loanAppSeq, String cmnt) {
        MwCnicUpd cnicUpd = mwCnicUpdRepository.findOneByLoanAppSeq(loanAppSeq);
        if (cnicUpd == null)
            return 0;
        MwRefCdVal apprSts = mwRefCdValRepository.findRefCdByGrpAndVal("0106", "0003");
        cnicUpd.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        cnicUpd.setLastUpdDt(Instant.now());
        cnicUpd.setUpdSts(apprSts.getRefCdSeq());
        cnicUpd.setCmnt(cmnt);
        mwCnicUpdRepository.save(cnicUpd);
        return cnicUpd.getCnicUpdSeq();
    }


}


