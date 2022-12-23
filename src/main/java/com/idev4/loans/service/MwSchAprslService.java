package com.idev4.loans.service;

import com.idev4.loans.domain.*;
import com.idev4.loans.dto.*;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.Queries;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MwSchAprslService {

    private final Logger log = LoggerFactory.getLogger(MwSchAprslService.class);

    private final MwSchAprslRepository mwSchAprslRepository;

    private final MwSchAtndRepository mwSchAtndRepository;

    private final MwSchGrdRepository mwSchGrdRepository;

    private final MwAddrService mwAddrService;

    private final MwAddrRelService mwAddrRelService;

    private final MwSchQltyChkRepository mwSchQltyChkRepository;

    private final MwQstService mwQstService;

    private final MwRefCdValService mwRefCdValService;

    private final MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository;

    private final MwBizExpDtlRepository mwBizExpDtlRepository;

    private final EntityManager em;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;
    int count = 0;

    public MwSchAprslService(MwSchAprslRepository mwSchAprslRepository, MwSchGrdRepository mwSchGrdRepository,
                             MwSchAtndRepository mwSchAtndRepository, MwSchQltyChkRepository mwSchQltyChkRepository, MwAddrRelService mwAddrRelService,
                             MwAddrService mwAddrService, MwAnswrService mwAnswrService, MwQstService mwQstService, MwRefCdValService mwRefCdValService,
                             EntityManager em, MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository, MwBizExpDtlRepository mwBizExpDtlRepository,
                             MwLoanAppRepository mwLoanAppRepository, MwClntRepository mwClntRepository) {

        this.mwSchAprslRepository = mwSchAprslRepository;
        this.mwSchAtndRepository = mwSchAtndRepository;
        this.mwSchGrdRepository = mwSchGrdRepository;
        this.mwAddrService = mwAddrService;
        this.mwAddrRelService = mwAddrRelService;
        this.mwSchQltyChkRepository = mwSchQltyChkRepository;
        this.mwQstService = mwQstService;
        this.mwRefCdValService = mwRefCdValService;
        this.mwBizAprslIncmDtlRepository = mwBizAprslIncmDtlRepository;
        this.mwBizExpDtlRepository = mwBizExpDtlRepository;
        this.em = em;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    public SchoolAppraisalDto addNewSchAprsl(SchoolAppraisalDto dto, String currUser) {

        MwSchAprsl exMwSchAprsl = mwSchAprslRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(dto.getLoanAppSeq(), false, true);

        if (exMwSchAprsl != null) {
            dto.setSchAprslSeq(exMwSchAprsl.getSchAprslSeq());
            dto = updateSchoolAppraisal(dto, currUser);
            return dto;
        }
        Instant currIns = Instant.now();
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.getLoanAppSeq(), true);
        if (app != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                long schoolAppraisalSeq = Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_SCH_APRSL,
                        app.getLoanCyclNum());
                dto.setSchAprslSeq(schoolAppraisalSeq);
                MwSchAprsl mwSchAprsl = new MwSchAprsl();

                mwSchAprsl.setSchAprslSeq(schoolAppraisalSeq);
                mwSchAprsl.setSchNm(dto.getSchNm());
                mwSchAprsl.setSchRegdFlg(dto.getSchRegdFlg());
                mwSchAprsl.setPefSptFlg(dto.getPefSptFlg());
                mwSchAprsl.setSchArea(dto.getSchArea());
                mwSchAprsl.setSchOwnTypKey(dto.getSchOwnTypKey());
                mwSchAprsl.setRelWthOwnKey(dto.getRelWthOwnKey());
                mwSchAprsl.setSchPpalKey(dto.getSchPpalKey());
                mwSchAprsl.setBldngOwnKey(dto.getBldngOwnKey());
                mwSchAprsl.setSchTypKey(dto.getSchTypKey());
                mwSchAprsl.setSchLvlKey(dto.getSchLvlKey());
                mwSchAprsl.setSchMedmKey(dto.getSchMedmKey());
                mwSchAprsl.setSchAreaUntKey(dto.getSchAreaUntKey());
                mwSchAprsl.setCrtdBy("w-" + currUser);
                mwSchAprsl.setCrtdDt(currIns);
                mwSchAprsl.setLastUpdBy("w-" + currUser);
                mwSchAprsl.setLastUpdDt(currIns);
                mwSchAprsl.setDelFlg(false);
                mwSchAprsl.setEffStartDt(currIns);
                mwSchAprsl.setCrntRecFlg(true);
                mwSchAprsl.setLoanAppSeq(dto.getLoanAppSeq());
                mwSchAprsl.setSchYr(dto.schyr);
                mwSchAprsl.setSyncFlg(true);
                mwSchAprsl.setSchRegdAgy(dto.schRegdAgy);
                mwSchAprslRepository.save(mwSchAprsl);

                // address
                if (dto.getAddressDto().houseNum != null || dto.getAddressDto().city != null || dto.getAddressDto().sreet_area != null
                        || dto.getAddressDto().community != null || dto.getAddressDto().village != null
                        || dto.getAddressDto().otherDetails != null) {
                    AddressDto addressDto = new AddressDto();

                    addressDto.schAprslSeq = schoolAppraisalSeq;
                    addressDto.houseNum = dto.getAddressDto().houseNum;
                    addressDto.sreet_area = dto.getAddressDto().sreet_area;
                    addressDto.community = dto.getAddressDto().community;
                    addressDto.village = dto.getAddressDto().village;
                    addressDto.otherDetails = dto.getAddressDto().otherDetails;
                    addressDto.city = dto.getAddressDto().city;
                    addressDto.district = dto.getAddressDto().district;
                    addressDto.tehsil = dto.getAddressDto().tehsil;
                    addressDto.city = dto.getAddressDto().city;
                    addressDto.uc = dto.getAddressDto().uc;

                    addressDto.cnicNum = clnt.getCnicNum();
                    addressDto.cycleNum = app.getLoanCyclNum();
                    dto.getAddressDto().addrSeq = mwAddrService.saveAddress(addressDto, currUser, "SchoolAppraisal");
                }

                // Attendance

                if (dto.getLastYrDrop() != null || (dto.getTotMaleTchrs() != null && dto.getTotFemTchrs() != null)) {
                    MwSchAtnd mwSchAtnd = new MwSchAtnd();

                    long schoolAtndSeq = Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_SCH_ATND,
                            app.getLoanCyclNum());

                    mwSchAtnd.setSchAtndSeq(schoolAtndSeq);
                    mwSchAtnd.setSchAprslSeq(schoolAppraisalSeq);
                    mwSchAtnd.setTotMaleTchrs(dto.getTotMaleTchrs());
                    mwSchAtnd.setTotFemTchrs(dto.getTotFemTchrs());
                    mwSchAtnd.setLastYrDrop(dto.getLastYrDrop());
                    mwSchAtnd.setCrtdBy("w-" + currUser);
                    mwSchAtnd.setCrtdDt(currIns);
                    mwSchAtnd.setLastUpdBy("w-" + currUser);
                    mwSchAtnd.setLastUpdDt(currIns);
                    mwSchAtnd.setDelFlg(false);
                    mwSchAtnd.setEffStartDt(currIns);
                    mwSchAtnd.setCrntRecFlg(true);
                    mwSchAtnd.setSyncFlg(true);
                    mwSchAtndRepository.save(mwSchAtnd);
                }
                // Grade
                int i = 0;
                for (SchoolGradeDto schoolGradeDto : dto.getSchoolGradeDtos()) {
                    MwSchGrd mwSchGrd = new MwSchGrd();

                    long schoolGrdSeq = Long.parseLong(
                            (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_SCH_GRD, app.getLoanCyclNum())
                                    + "").concat(i + ""));
                    schoolGradeDto.setSchGrdSeq(schoolGrdSeq);
                    mwSchGrd.setSchGrdSeq(schoolGrdSeq);
                    mwSchGrd.setTotFemStdnt(schoolGradeDto.getTotFemStdnt());
                    mwSchGrd.setTotMaleStdnt(schoolGradeDto.getTotMaleStdnt());
                    mwSchGrd.setAvgGrdFee(schoolGradeDto.getAvgFee());
                    mwSchGrd.setNoFeeStdnt(schoolGradeDto.getNoFeeStdnt());
                    mwSchGrd.setFemStdntPrsnt(schoolGradeDto.getFemStdntPrsnt());
                    mwSchGrd.setMaleStdntPrsnt(schoolGradeDto.getMaleStdntPrsnt());
                    mwSchGrd.setGrdKey(schoolGradeDto.getGrdKey());
                    mwSchGrd.setCrtdBy("w-" + currUser);
                    mwSchGrd.setCrtdDt(currIns);
                    mwSchGrd.setLastUpdBy("w-" + currUser);
                    mwSchGrd.setLastUpdDt(currIns);
                    mwSchGrd.setDelFlg(false);
                    mwSchGrd.setEffStartDt(currIns);
                    mwSchGrd.setCrntRecFlg(true);
                    mwSchGrd.setSchAprslSeq(schoolAppraisalSeq);
                    mwSchGrd.setSyncFlg(true);
                    mwSchGrdRepository.save(mwSchGrd);

                }

                // -----------------INCOME------------//

                // ----OTHER INCOME----//
                i = 0;
                for (IncomeDtlDto income : dto.primaryIncome) {
                    MwBizAprslIncmDtl in = new MwBizAprslIncmDtl();
                    in.setCrntRecFlg(true);
                    in.setCrtdBy("w-" + currUser);
                    in.setCrtdDt(Instant.now());
                    in.setDelFlg(false);
                    in.setEffStartDt(Instant.now());
                    in.setIncmAmt(income.incomeAmount);
                    in.setIncmCtgryKey(income.incomeCategoryKey);
                    in.setIncmDtlSeq(Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
                            TableNames.MW_BIZ_APRSL_INCM_DTL, app.getLoanCyclNum()) + "").concat(i + "")));
                    in.setIncmTypKey(income.incomeTypeKey);
                    in.setMwBizAprslIncmHdr(schoolAppraisalSeq);
                    in.setEntyTypFlg(2);
                    in.setSyncFlg(true);
                    in.lastUpdBy("w-" + currUser);
                    in.lastUpdDt(Instant.now());
                    i++;
                    mwBizAprslIncmDtlRepository.save(in);
                }

                // ----SECONDARY INCOME----//
                i = 0;
                for (IncomeDtlDto income : dto.secondaryIncome) {
                    MwBizAprslIncmDtl in = new MwBizAprslIncmDtl();
                    in.setCrntRecFlg(true);
                    in.setCrtdBy(currUser);
                    in.setCrtdDt(Instant.now());
                    in.setDelFlg(false);
                    in.setEffStartDt(Instant.now());
                    in.setIncmAmt(income.incomeAmount);
                    in.setIncmCtgryKey(income.incomeCategoryKey);
                    in.setIncmDtlSeq(Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
                            TableNames.MW_BIZ_APRSL_INCM_DTL, app.getLoanCyclNum()) + "").concat(i + "")));
                    in.setIncmTypKey(income.incomeTypeKey);
                    in.setMwBizAprslIncmHdr(schoolAppraisalSeq);
                    in.setEntyTypFlg(2);
                    in.setSyncFlg(true);
                    in.lastUpdBy("w-" + currUser);
                    in.lastUpdDt(Instant.now());

                    i++;
                    mwBizAprslIncmDtlRepository.save(in);
                }

                // -----BUSINESS EXPENSE------//
                i = 0;
                for (BizExpDto expense : dto.businessExpense) {
                    MwBizExpDtl ex = new MwBizExpDtl();
                    ex.setCrntRecFlg(true);
                    ex.setCrtdBy(currUser);
                    ex.setCrtdDt(Instant.now());
                    ex.setDelFlg(false);
                    ex.setEffStartDt(Instant.now());
                    ex.setExpAmt(expense.expAmount);
                    ex.setExpCtgryKey(expense.expCategoryKey);
                    ex.setExpDtlSeq(Long.parseLong(
                            (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_BIZ_EXP_DTL, app.getLoanCyclNum())
                                    + "").concat(i + "")));
                    ex.setExpTypKey(expense.expTypKey);
                    ex.setMwBizAprsl(schoolAppraisalSeq);
                    ex.setEntyTypFlg(2);
                    ex.setSyncFlg(true);
                    ex.lastUpdBy("w-" + currUser);
                    ex.lastUpdDt(Instant.now());
                    i++;
                    mwBizExpDtlRepository.save(ex);
                }

                // -----BUSINESS EXPENSE------//
                i = 0;
                for (BizExpDto expense : dto.householdExpense) {
                    MwBizExpDtl ex = new MwBizExpDtl();
                    ex.setCrntRecFlg(true);
                    ex.setCrtdBy(currUser);
                    ex.setCrtdDt(Instant.now());
                    ex.setDelFlg(false);
                    ex.setEffStartDt(Instant.now());
                    ex.setExpAmt(expense.expAmount);
                    ex.setExpCtgryKey(expense.expCategoryKey);
                    ex.setExpDtlSeq(Long.parseLong(
                            (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_BIZ_EXP_DTL, app.getLoanCyclNum())
                                    + "").concat(i + "")));
                    ex.setExpTypKey(expense.expTypKey);
                    ex.setMwBizAprsl(schoolAppraisalSeq);
                    ex.setEntyTypFlg(2);
                    ex.setSyncFlg(true);

                    ex.lastUpdBy("w-" + currUser);
                    ex.lastUpdDt(Instant.now());
                    i++;
                    mwBizExpDtlRepository.save(ex);
                }

                // School Quality Check
                // for (SchoolQualityCheckDto schoolQualityCheckDto :
                // dto.getSchoolQualityCheckDtos()) {
                // MwSchQltyChk mwSchQltyChk = new MwSchQltyChk();
                //
                // long schoolQltyChkSeq =
                // SequenceFinder.findNextVal(Sequences.SCH_QLTY_CHK_SEQ);
                //
                // mwSchQltyChk.setSchQltyChkSeq(schoolQltyChkSeq);
                // mwSchQltyChk.setSchAprslSeq(schoolAppraisalSeq);
                // mwSchQltyChk.setQstSeq(schoolQualityCheckDto.getQstSeq());
                // mwSchQltyChk.setAnswrSeq(schoolQualityCheckDto.getAnswrSeq());
                // mwSchQltyChk.setCrtdBy(currUser);
                // mwSchQltyChk.setCrtdDt(currIns);
                // mwSchQltyChk.setLastUpdBy(currUser);
                // mwSchQltyChk.setLastUpdDt(currIns);
                // mwSchQltyChk.setDelFlg(false);
                // mwSchQltyChk.setEffStartDt(currIns);
                // mwSchQltyChk.setCrntRecFlg(true);
                //
                // mwSchQltyChkRepository.save(mwSchQltyChk);
                // }
                //

                // for (SchoolQuestionsDto questionDto : dto.SchoolQAArray) {
                // for (QuestionDto question : questionDto.questions) {
                // MwSchQltyChk chk = new MwSchQltyChk();
                // chk.setSchQltyChkSeq(SequenceFinder.findNextVal(Sequences.SCH_QLTY_CHK_SEQ));
                // chk.setSchAprslSeq(schoolAppraisalSeq);
                // chk.setQstSeq(question.questionKey);
                // chk.setAnswrSeq(question.answerSeq);
                // chk.setCrtdBy(currUser);
                // chk.setCrtdDt(currIns);
                // chk.setLastUpdBy(currUser);
                // chk.setLastUpdDt(currIns);
                // chk.setDelFlg(false);
                // chk.setEffStartDt(currIns);
                // chk.setCrntRecFlg(true);
                // mwSchQltyChkRepository.save(chk);
                // }
                // }
                isSchoolAppraisalFormComplete(dto);
                if (dto.formComplete)
                    Common.updateFormComplFlag(dto.formSeq, dto.getLoanAppSeq(), currUser);

            }
        }
        return dto;
    }

    public SchoolAppraisalDto isSchoolAppraisalFormComplete(SchoolAppraisalDto dto) {
        MwSchAprsl mwSchAprsl = mwSchAprslRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(dto.getLoanAppSeq(), false, true);
        if (mwSchAprsl != null) {
            if (mwSchAprsl.getBldngOwnKey() != null && mwSchAprsl.getPefSptFlg() != null && mwSchAprsl.getRelWthOwnKey() != null
                    && mwSchAprsl.getSchArea() != null && mwSchAprsl.getSchAreaUntKey() != null && mwSchAprsl.getSchLvlKey() != null
                    && mwSchAprsl.getSchMedmKey() != null && mwSchAprsl.getSchNm() != null && mwSchAprsl.getSchOwnTypKey() != null
                    && mwSchAprsl.getSchPpalKey() != null && mwSchAprsl.getSchRegdFlg() != null && mwSchAprsl.getSchTypKey() != null
                    && mwSchAprsl.getSchYr() != null && mwSchAprsl.getBldngOwnKey() != 0 && mwSchAprsl.getSchPpalKey() != 0
                    && mwSchAprsl.getRelWthOwnKey() != 0 && mwSchAprsl.getSchArea() != 0 && mwSchAprsl.getSchAreaUntKey() != 0
                    && mwSchAprsl.getSchLvlKey() != 0 && mwSchAprsl.getSchMedmKey() != 0 && mwSchAprsl.getSchNm() != ""
                    && mwSchAprsl.getSchOwnTypKey() != 0
                    // &&mwSchAprsl.getPefSptFlg() != 0 && mwSchAprsl.getSchRegdFlg() != 0 && mwSchAprsl.getSchAge() != 0 &&
                    // mwSchAprsl.getSchAge() != null
                    && mwSchAprsl.getSchTypKey() != 0 && mwSchAprsl.getSchYr() != 0) {
                if (mwSchAprsl.getSchRegdFlg().longValue() == 1L
                        && (mwSchAprsl.getSchRegdAgy() == null || mwSchAprsl.getSchRegdAgy().equals(""))) {
                    dto.hasBasic = false;
                } else {
                    dto.hasBasic = true;
                }
            } else {
                dto.hasBasic = false;
            }
            MwAddrRel mwAddrRel = mwAddrRelService.getAddressRelationByEntityKeyAndEntyTyp(mwSchAprsl.getSchAprslSeq(),
                    "SchoolAppraisal");
            if (mwAddrRel != null) {
                MwAddr mwAddr = mwAddrService.findOneByAddrSeq(mwAddrRel.getAddrSeq());
                if (mwAddr != null) {
                    if (mwAddr.getHseNum() != null && mwAddr.getCitySeq() != null && mwAddr.getStrt() != null && mwAddr.getOthDtl() != null
                            && mwAddr.getCmntySeq() != null) {
                        dto.hasAddress = true;
                    } else {
                        dto.hasAddress = false;
                    }
                }
            }

            // List<MwBizAprslIncmDtl> income = mwBizAprslIncmDtlRepository
            // .findAllByMwBizAprslIncmHdrAndCrntRecFlgAndEntyTypFlg(mwSchAprsl.getSchAprslSeq(), true, 2);
            // if (income.size() > 0) {
            // for (int z = 0; z < income.size(); z++) {
            // if (income.get(z).getIncmCtgryKey() == 1) {
            // dto.hasIncome = true;break;
            // }
            // }
            // } else {
            // dto.hasIncome = false;
            // }
            dto.hasIncome = true;
            List<MwBizExpDtl> expenses = mwBizExpDtlRepository.findAllByMwBizAprslAndCrntRecFlgAndEntyTypFlg(mwSchAprsl.getSchAprslSeq(),
                    true, 2);
            if (expenses.size() > 0) {
                int i = -1;
                int j = -1;
                for (int z = 0; z < expenses.size(); z++) {
                    if (expenses.get(z).getExpCtgryKey() == 1)
                        i = z;
                    if (expenses.get(z).getExpCtgryKey() == 2)
                        j = z;
                }
                if (i > -1 && j > -1)
                    dto.hasExpense = true;
            } else {
                dto.hasExpense = false;
            }

            MwSchAtnd mwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
            if (mwSchAtnd != null) {
                if (((mwSchAtnd.getTotMaleTchrs() != null) || (mwSchAtnd.getTotFemTchrs() != null))) {
                    long totalTeachers = ((mwSchAtnd.getTotMaleTchrs() == null) ? 0 : mwSchAtnd.getTotMaleTchrs().longValue())
                            + ((mwSchAtnd.getTotFemTchrs() == null) ? 0 : mwSchAtnd.getTotFemTchrs().longValue());
                    if (totalTeachers > 0 && mwSchAtnd.getLastYrDrop() != null)
                        dto.hasAttend = true;
                }
                List<MwSchGrd> mwSchGrds = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);

                // if ( mwSchGrds != null ) {
                // if ( mwSchGrds.size() > 0 && mwSchAtnd.getLastYrDrop() != null ) {
                // dto.hasGrade = true;
                // }
                // }
            }
            List<MwSchGrd> mwSchGrds = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);

            if (mwSchGrds != null) {
                if (mwSchGrds.size() > 0) {
                    dto.hasGrade = true;
                }
            }
            // List<MwSchQltyChk> answers = mwSchQltyChkRepository
            // .findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
            // int i = -1;
            // for(int z=0; z<answers.size();z++) {
            // if (answers.get(z).getAnswrSeq() == null || answers.get(z).getAnswrSeq() == 0L) {
            // i++;
            // }
            // };
            // if(i==-1) {
            // dto.hasQltyChck = true;
            // }

        }

        if (dto.hasBasic && dto.hasAddress && dto.hasExpense && dto.hasAttend && dto.hasGrade)
            dto.formComplete = true;
        return dto;
    }

    public SchoolGradeDto schoolGradeDomainToDto(MwSchGrd mwSchGrd) {
        SchoolGradeDto schoolGradeDto = new SchoolGradeDto();
        schoolGradeDto.setSchGrdSeq(mwSchGrd.getSchGrdSeq());
        schoolGradeDto.setTotFemStdnt(mwSchGrd.getTotFemStdnt());
        schoolGradeDto.setTotMaleStdnt(mwSchGrd.getTotMaleStdnt());
        schoolGradeDto.setAvgFee(mwSchGrd.getAvgGrdFee());
        schoolGradeDto.setNoFeeStdnt(mwSchGrd.getNoFeeStdnt());
        schoolGradeDto.setFemStdntPrsnt(mwSchGrd.getFemStdntPrsnt());
        schoolGradeDto.setMaleStdntPrsnt(mwSchGrd.getMaleStdntPrsnt());
        schoolGradeDto.setGrdKey(mwSchGrd.getGrdKey());
        schoolGradeDto.setSchAprslSeq(mwSchGrd.getSchAprslSeq());

        return schoolGradeDto;
    }

    public SchoolQualityCheckDto schoolQualityCheckDomainToDto(MwSchQltyChk mwSchQltyChk) {
        SchoolQualityCheckDto schoolQualityCheckDto = new SchoolQualityCheckDto();
        schoolQualityCheckDto.setSchQltyChkSeq(mwSchQltyChk.getSchQltyChkSeq());
        schoolQualityCheckDto.setSchAprslSeq(mwSchQltyChk.getSchAprslSeq());
        schoolQualityCheckDto.setQstSeq(mwSchQltyChk.getQstSeq());
        schoolQualityCheckDto.setQstCtgryKey(mwQstService.findOne(mwSchQltyChk.getQstSeq()).getQstCtgryKey());
        schoolQualityCheckDto.setQstCtgry(
                (mwRefCdValService.findOne(mwQstService.findOne(mwSchQltyChk.getQstSeq()).getQstCtgryKey())).getRefCdDscr());
        schoolQualityCheckDto.setQstStr(mwQstService.findOne(mwSchQltyChk.getQstSeq()).getQstStr());
        schoolQualityCheckDto.setAnswrSeq(mwSchQltyChk.getAnswrSeq());
        return schoolQualityCheckDto;
    }

    public SchoolAppraisalDto getSchoolAppraisal(long seq) {
        SchoolAppraisalDto dto = new SchoolAppraisalDto();
        MwSchAprsl mwSchAprsl = mwSchAprslRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(seq, false, true);
        if (mwSchAprsl != null) {
            dto.setSchAprslSeq(mwSchAprsl.getSchAprslSeq());
            dto.setSchNm(mwSchAprsl.getSchNm());
            dto.setSchRegdFlg(mwSchAprsl.getSchRegdFlg());
            dto.setPefSptFlg(mwSchAprsl.getPefSptFlg());
            dto.setSchArea(mwSchAprsl.getSchArea());
            dto.setSchOwnTypKey(mwSchAprsl.getSchOwnTypKey());
            dto.setRelWthOwnKey(mwSchAprsl.getRelWthOwnKey());
            dto.setSchPpalKey(mwSchAprsl.getSchPpalKey());
            dto.setBldngOwnKey(mwSchAprsl.getBldngOwnKey());
            dto.setSchTypKey(mwSchAprsl.getSchTypKey());
            dto.setSchLvlKey(mwSchAprsl.getSchLvlKey());
            dto.setSchMedmKey(mwSchAprsl.getSchMedmKey());
            dto.setSchAreaUntKey(mwSchAprsl.getSchAreaUntKey());
            dto.setLoanAppSeq(mwSchAprsl.getLoanAppSeq());
            dto.schRegdAgy = mwSchAprsl.getSchRegdAgy();
            dto.schyr = mwSchAprsl.getSchYr();
            dto.mwAnswers = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);

            MwAddrRel mwAddrRel = mwAddrRelService.getAddressRelationByEntityKeyAndEntyTyp(mwSchAprsl.getSchAprslSeq(),
                    "SchoolAppraisal");
            if (mwAddrRel != null) {
                MwAddr mwAddr = mwAddrService.findOneByAddrSeq(mwAddrRel.getAddrSeq());

                if (mwAddr != null) {
                    dto.getAddressDto().addrSeq = mwAddr.getAddrSeq();
                    dto.getAddressDto().houseNum = mwAddr.getHseNum();
                    dto.getAddressDto().sreet_area = mwAddr.getStrt();
                    dto.getAddressDto().otherDetails = mwAddr.getOthDtl();
                    dto.getAddressDto().city = mwAddr.getCitySeq();
                    dto.getAddressDto().community = mwAddr.getCmntySeq();
                    dto.getAddressDto().village = mwAddr.getVlg();

                    if (mwAddr.getCitySeq() != null && mwAddr.getCitySeq() != 0) {
                        String query = Queries.getOrgFromCityUcRelSeq + mwAddr.getCitySeq();
                        Query q = em.createNativeQuery(query);
                        List<Object[]> result = q.getResultList();
                        if (result.size() > 0) {
                            for (Object[] st : result) {
                                dto.getAddressDto().city = (st[0] == null) ? 0L : Long.valueOf(st[0].toString());
                                dto.getAddressDto().cityName = (st[2] == null) ? "" : st[2].toString();
                                dto.getAddressDto().uc = (st[4] == null) ? "" : st[4].toString();
                                dto.getAddressDto().ucName = (st[4] == null) ? "" : st[4].toString();
                                dto.getAddressDto().tehsil = (st[6] == null) ? "" : st[6].toString();
                                dto.getAddressDto().tehsilName = (st[6] == null) ? "" : st[6].toString();
                                dto.getAddressDto().district = (st[8] == null) ? "" : st[8].toString();
                                dto.getAddressDto().districtName = (st[8] == null) ? "" : st[8].toString();
                                dto.getAddressDto().district = (st[8] == null) ? "" : st[8].toString();
                                dto.getAddressDto().provinceName = (st[10] == null) ? "" : st[10].toString();
                            }
                        }
                    }

                }
            }
            List<MwBizAprslIncmDtl> income = mwBizAprslIncmDtlRepository
                    .findAllByMwBizAprslIncmHdrAndCrntRecFlgAndEntyTypFlg(mwSchAprsl.getSchAprslSeq(), true, 2);
            dto.primaryIncome = new ArrayList<>();
            dto.secondaryIncome = new ArrayList<>();
            dto.businessExpense = new ArrayList<>();
            dto.householdExpense = new ArrayList<>();
            income.forEach(in -> {
                IncomeDtlDto dt = new IncomeDtlDto();
                dt.incomeAmount = in.getIncmAmt();
                dt.incomeCategoryKey = in.getIncmCtgryKey();
                dt.incomeTypeKey = in.getIncmTypKey();
                if (in.getIncmCtgryKey() == 1) {
                    dto.primaryIncome.add(dt);
                } else {
                    dto.secondaryIncome.add(dt);
                }
            });

            List<MwBizExpDtl> expenses = mwBizExpDtlRepository.findAllByMwBizAprslAndCrntRecFlgAndEntyTypFlg(mwSchAprsl.getSchAprslSeq(),
                    true, 2);

            expenses.forEach(expense -> {
                BizExpDto dt = new BizExpDto();
                dt.expAmount = expense.getExpAmt();
                dt.expTypKey = expense.getExpTypKey();
                dt.expCategoryKey = expense.getExpCtgryKey();
                if (expense.getExpCtgryKey() == 1) {
                    dto.businessExpense.add(dt);
                } else {
                    dto.householdExpense.add(dt);
                }
            });

            MwSchAtnd mwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
            if (mwSchAtnd != null) {
                dto.setSchAtndSeq(mwSchAtnd.getSchAtndSeq());
                dto.setSchAprslSeq(mwSchAtnd.getSchAprslSeq());
                dto.setTotMaleTchrs(mwSchAtnd.getTotMaleTchrs());
                dto.setTotFemTchrs(mwSchAtnd.getTotFemTchrs());
                dto.setLastYrDrop(mwSchAtnd.getLastYrDrop());
            }
            List<MwSchGrd> mwSchGrds = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
            List<SchoolGradeDto> schoolGradeDtos = new ArrayList<SchoolGradeDto>();
            if (mwSchGrds != null) {
                for (MwSchGrd mwSchGrd : mwSchGrds) {
                    schoolGradeDtos.add(schoolGradeDomainToDto(mwSchGrd));
                }
            }
            dto.setSchoolGradeDtos(schoolGradeDtos);
            // List<MwSchQltyChk> mwSchQltyChks = mwSchQltyChkRepository
            // .findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
            // List<SchoolQualityCheckDto> schoolQualityCheckDtos = new ArrayList<SchoolQualityCheckDto>();
            // if (mwSchQltyChks != null) {
            // for (MwSchQltyChk mwSchQltyChk : mwSchQltyChks) {
            // schoolQualityCheckDtos.add(schoolQualityCheckDomainToDto(mwSchQltyChk));
            // }
            // dto.setSchoolQualityCheckDtos(schoolQualityCheckDtos);
            // }

        }
        isSchoolAppraisalFormComplete(dto);
        return dto;
    }

    @Transactional
    public List<SchoolAppraisalDto> getAllSchoolAppraisal() {
        List<SchoolAppraisalDto> dtos = new ArrayList<>();
        List<MwSchAprsl> mwSchAprsls = mwSchAprslRepository.findAllByCrntRecFlg(true);
        if (mwSchAprsls != null) {
            for (MwSchAprsl mwSchAprsl : mwSchAprsls) {

                SchoolAppraisalDto dto = new SchoolAppraisalDto();
                dto.setSchAprslSeq(mwSchAprsl.getSchAprslSeq());
                dto.setSchNm(mwSchAprsl.getSchNm());
                dto.setSchRegdFlg(mwSchAprsl.getSchRegdFlg());
                dto.setPefSptFlg(mwSchAprsl.getPefSptFlg());
                dto.setSchArea(mwSchAprsl.getSchArea());
                dto.setSchOwnTypKey(mwSchAprsl.getSchOwnTypKey());
                dto.setRelWthOwnKey(mwSchAprsl.getRelWthOwnKey());
                dto.setSchPpalKey(mwSchAprsl.getSchPpalKey());
                dto.setBldngOwnKey(mwSchAprsl.getBldngOwnKey());
                dto.setSchTypKey(mwSchAprsl.getSchTypKey());
                dto.setSchLvlKey(mwSchAprsl.getSchLvlKey());
                dto.setSchMedmKey(mwSchAprsl.getSchMedmKey());
                dto.setSchAreaUntKey(mwSchAprsl.getSchAreaUntKey());
                dto.setLoanAppSeq(mwSchAprsl.getLoanAppSeq());

                MwAddrRel mwAddrRel = mwAddrRelService.getAddressRelationByEntityKeyAndEntyTyp(mwSchAprsl.getSchAprslSeq(),
                        "SchoolAppraisal");
                if (mwAddrRel != null) {
                    MwAddr mwAddr = mwAddrService.findOneByAddrSeq(mwAddrRel.getAddrSeq());

                    if (mwAddr != null) {
                        dto.getAddressDto().addrSeq = mwAddr.getAddrSeq();
                        dto.getAddressDto().houseNum = mwAddr.getHseNum();
                        dto.getAddressDto().sreet_area = mwAddr.getStrt();
                        dto.getAddressDto().otherDetails = mwAddr.getOthDtl();
                        dto.getAddressDto().city = mwAddr.getCitySeq();
                        dto.getAddressDto().community = mwAddr.getCmntySeq();
                        dto.getAddressDto().village = mwAddr.getVlg();

                        MwSchAtnd mwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
                        if (mwSchAtnd != null) {
                            dto.setSchAtndSeq(mwSchAtnd.getSchAtndSeq());
                            dto.setSchAprslSeq(mwSchAtnd.getSchAprslSeq());
                            dto.setTotMaleTchrs(mwSchAtnd.getTotMaleTchrs());
                            dto.setTotFemTchrs(mwSchAtnd.getTotFemTchrs());
                            dto.setLastYrDrop(mwSchAtnd.getLastYrDrop());

                            List<MwSchGrd> mwSchGrds = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(),
                                    true);
                            List<SchoolGradeDto> schoolGradeDtos = new ArrayList<SchoolGradeDto>();
                            if (mwSchGrds != null) {
                                for (MwSchGrd mwSchGrd : mwSchGrds) {
                                    schoolGradeDtos.add(this.schoolGradeDomainToDto(mwSchGrd));
                                }
                                List<MwSchQltyChk> mwSchQltyChks = mwSchQltyChkRepository
                                        .findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
                                List<SchoolQualityCheckDto> schoolQualityCheckDtos = new ArrayList<SchoolQualityCheckDto>();
                                if (mwSchQltyChks != null) {
                                    for (MwSchQltyChk mwSchQltyChk : mwSchQltyChks) {
                                        schoolQualityCheckDtos.add(this.schoolQualityCheckDomainToDto(mwSchQltyChk));
                                    }
                                    dto.setSchoolQualityCheckDtos(schoolQualityCheckDtos);
                                } else {
                                    return null;
                                }
                                dto.setSchoolGradeDtos(schoolGradeDtos);
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                } else {
                    AddressDto addressDto = new AddressDto();
                    if (dto.getAddressDto().houseNum != null && dto.getAddressDto().houseNum != ""
                            && dto.getAddressDto().sreet_area != null && dto.getAddressDto().sreet_area != ""
                            && dto.getAddressDto().otherDetails != null && dto.getAddressDto().otherDetails != ""
                            && dto.getAddressDto().city != 0) {
                        addressDto.schAprslSeq = dto.getSchAprslSeq();
                        addressDto.houseNum = dto.getAddressDto().houseNum;
                        addressDto.sreet_area = dto.getAddressDto().sreet_area;
                        addressDto.community = dto.getAddressDto().community;
                        addressDto.village = dto.getAddressDto().village;
                        addressDto.otherDetails = dto.getAddressDto().otherDetails;
                        addressDto.city = dto.getAddressDto().city;
                        addressDto.district = dto.getAddressDto().district;
                        addressDto.tehsil = dto.getAddressDto().tehsil;
                        addressDto.city = dto.getAddressDto().city;
                        addressDto.uc = dto.getAddressDto().uc;

                        mwAddrService.saveAddress(addressDto, "Admin", "SchoolAppraisal");
                    }
                }
                dtos.add(dto);
            }
        } else {
            return null;
        }
        return dtos;
    }

    @Transactional
    public boolean delete(Long seq) {

        MwSchAprsl mwSchAprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(seq, true);
        if (mwSchAprsl != null) {
            mwSchAprsl.setCrntRecFlg(false);
            mwSchAprsl.setDelFlg(true);
            mwSchAprsl.setEffEndDt(Instant.now());
            mwSchAprsl.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            mwSchAprsl.setLastUpdDt(Instant.now());
            MwAddrRel mwAddrRel = mwAddrRelService.getAddressRelationByEntityKeyAndEntyTyp(seq, "SchoolAppraisal");
            if (mwAddrRel != null) {
                mwAddrRel.setSyncFlg(true);
                mwAddrRelService.deleteByEntity(mwAddrRel);

                MwAddr mwAddr = mwAddrService.findOneByAddrSeq(mwAddrRel.getAddrSeq());
                if (mwAddr != null) {
                    mwAddr.setSyncFlg(true);
                    mwAddrService.deleteByEntity(mwAddr);

                    MwSchAtnd mwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(seq, true);
                    if (mwSchAtnd != null) {
                        mwSchAtnd.setCrntRecFlg(false);
                        mwSchAtnd.setDelFlg(true);
                        mwSchAtnd.setEffEndDt(Instant.now());
                        mwSchAtnd.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                        mwSchAtnd.setLastUpdDt(Instant.now());
                        List<MwSchGrd> mwSchGrds = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(seq, true);
                        if (mwSchGrds != null) {
                            List<MwSchQltyChk> mwSchQltyChks = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(seq, true);
                            if (mwSchQltyChks != null) {
                                for (MwSchQltyChk mwSchQltyChk : mwSchQltyChks) {
                                    mwSchQltyChk.setCrntRecFlg(false);
                                    mwSchQltyChk.setDelFlg(true);
                                    mwSchQltyChk.setEffEndDt(Instant.now());
                                    mwSchQltyChk.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                                    mwSchQltyChk.setLastUpdDt(Instant.now());
                                    mwSchQltyChkRepository.save(mwSchQltyChk);
                                }
                                for (MwSchGrd mwSchGrd : mwSchGrds) {
                                    mwSchGrd.setCrntRecFlg(false);
                                    mwSchGrd.setDelFlg(true);
                                    mwSchGrd.setEffEndDt(Instant.now());
                                    mwSchGrd.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                                    mwSchGrd.setLastUpdDt(Instant.now());
                                    mwSchGrdRepository.save(mwSchGrd);
                                }
                                mwSchAtndRepository.save(mwSchAtnd);
                                mwSchAprslRepository.save(mwSchAprsl);

                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public SchoolAppraisalDto updateSchoolAppraisal(SchoolAppraisalDto dto, String currUser) {

        Instant currIns = Instant.now();
        new MwSchAprsl();
        AddressDto addressDto = new AddressDto();
        new MwSchAtnd();
        MwSchAtnd exMwSchAtnd = new MwSchAtnd();
        new ArrayList<>();
        List<MwSchGrd> mwSchGrds = new ArrayList<>();
        new ArrayList<>();
        List<MwSchQltyChk> mwSchQltyChks = new ArrayList<>();

        MwSchAprsl exMwSchAprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(dto.getSchAprslSeq(), true);
        if (exMwSchAprsl == null)
            return null;
        exMwSchAprsl.setSchAprslSeq(exMwSchAprsl.getSchAprslSeq());
        exMwSchAprsl.setSchNm(dto.getSchNm());
        exMwSchAprsl.setSchRegdFlg(dto.getSchRegdFlg());
        exMwSchAprsl.setPefSptFlg(dto.getPefSptFlg());
        exMwSchAprsl.setSchArea(dto.getSchArea());
        exMwSchAprsl.setSchOwnTypKey(dto.getSchOwnTypKey());
        exMwSchAprsl.setRelWthOwnKey(dto.getRelWthOwnKey());
        exMwSchAprsl.setSchPpalKey(dto.getSchPpalKey());
        exMwSchAprsl.setBldngOwnKey(dto.getBldngOwnKey());
        exMwSchAprsl.setSchTypKey(dto.getSchTypKey());
        exMwSchAprsl.setSchLvlKey(dto.getSchLvlKey());
        exMwSchAprsl.setSchMedmKey(dto.getSchMedmKey());
        exMwSchAprsl.setSchAreaUntKey(dto.getSchAreaUntKey());
        exMwSchAprsl.setLoanAppSeq(dto.getLoanAppSeq());
        exMwSchAprsl.setCrtdBy("w-" + currUser);
        exMwSchAprsl.setCrtdDt(exMwSchAprsl.getCrtdDt());
        exMwSchAprsl.setLastUpdBy("w-" + currUser);
        exMwSchAprsl.setLastUpdDt(currIns);
        exMwSchAprsl.setDelFlg(false);
        exMwSchAprsl.setEffStartDt(exMwSchAprsl.getEffStartDt());
        exMwSchAprsl.setSchYr(dto.schyr);
        exMwSchAprsl.setCrntRecFlg(true);
        exMwSchAprsl.setSchRegdAgy(dto.schRegdAgy);
        exMwSchAprsl.setSyncFlg(true);

        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.getLoanAppSeq(), true);
        if (app != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                // -----------------INCOME------------//

                // ----OTHER INCOME----//
                List<MwBizAprslIncmDtl> primaryIncomes = mwBizAprslIncmDtlRepository
                        .findAllByMwBizAprslIncmHdrAndEntyTypFlg(exMwSchAprsl.getSchAprslSeq(), 2);

                primaryIncomes.forEach(incm -> {
                    incm.setCrntRecFlg(false);
                    incm.setDelFlg(true);
                    incm.setEffEndDt(Instant.now());
                    incm.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    incm.setLastUpdDt(Instant.now());
                    count++;
                });
                mwBizAprslIncmDtlRepository.save(primaryIncomes);
                for (IncomeDtlDto income : dto.primaryIncome) {
                    MwBizAprslIncmDtl in = new MwBizAprslIncmDtl();
                    in.setCrntRecFlg(true);
                    in.setCrtdBy("w-" + currUser);
                    in.setCrtdDt(Instant.now());
                    in.setDelFlg(false);
                    in.setEffStartDt(Instant.now());
                    in.setIncmAmt(income.incomeAmount);
                    in.setIncmCtgryKey(income.incomeCategoryKey);
                    in.setIncmDtlSeq(Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
                            TableNames.MW_BIZ_APRSL_INCM_DTL, app.getLoanCyclNum()) + "").concat(count + "")));
                    in.setIncmTypKey(income.incomeTypeKey);
                    in.setMwBizAprslIncmHdr(exMwSchAprsl.getSchAprslSeq());
                    in.setEntyTypFlg(2);
                    in.setSyncFlg(true);
                    in.setLastUpdBy("w-" + currUser);
                    in.setLastUpdDt(Instant.now());
                    count++;
                    mwBizAprslIncmDtlRepository.save(in);
                }

                // ----SECONDARY INCOME----//
                for (IncomeDtlDto income : dto.secondaryIncome) {
                    MwBizAprslIncmDtl in = new MwBizAprslIncmDtl();
                    in.setCrntRecFlg(true);
                    in.setCrtdBy("w-" + currUser);
                    in.setCrtdDt(Instant.now());
                    in.setDelFlg(false);
                    in.setEffStartDt(Instant.now());
                    in.setIncmAmt(income.incomeAmount);
                    in.setIncmCtgryKey(income.incomeCategoryKey);
                    in.setIncmDtlSeq(Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(),
                            TableNames.MW_BIZ_APRSL_INCM_DTL, app.getLoanCyclNum()) + "").concat(count + "")));
                    in.setIncmTypKey(income.incomeTypeKey);
                    in.setMwBizAprslIncmHdr(exMwSchAprsl.getSchAprslSeq());
                    in.setEntyTypFlg(2);
                    in.setSyncFlg(true);
                    in.setLastUpdBy("w-" + currUser);
                    in.setLastUpdDt(Instant.now());
                    count++;
                    mwBizAprslIncmDtlRepository.save(in);
                }

                count = 1;
                // -----BUSINESS EXPENSE------//
                List<MwBizExpDtl> expenses = mwBizExpDtlRepository.findAllByMwBizAprslAndEntyTypFlg(exMwSchAprsl.getSchAprslSeq(), 2);
                expenses.forEach(exp -> {
                    exp.setCrntRecFlg(false);
                    exp.setDelFlg(true);
                    exp.setEffEndDt(Instant.now());
                    exp.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    exp.setLastUpdDt(Instant.now());
                    count++;
                });
                mwBizExpDtlRepository.save(expenses);


                // -----BUSINESS EXPENSE------//

                for (BizExpDto expense : dto.businessExpense) {
                    MwBizExpDtl ex = new MwBizExpDtl();
                    ex.setCrntRecFlg(true);
                    ex.setCrtdBy("w-" + currUser);
                    ex.setCrtdDt(Instant.now());
                    ex.setDelFlg(false);
                    ex.setEffStartDt(Instant.now());
                    ex.setExpAmt(expense.expAmount);
                    ex.setExpCtgryKey(expense.expCategoryKey);
                    ex.setExpDtlSeq(Long.parseLong(
                            (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_BIZ_EXP_DTL, app.getLoanCyclNum())
                                    + "").concat(count + "")));
                    ex.setExpTypKey(expense.expTypKey);
                    ex.setMwBizAprsl(exMwSchAprsl.getSchAprslSeq());
                    ex.setEntyTypFlg(2);
                    ex.setSyncFlg(true);
                    ex.lastUpdBy("w-" + currUser);
                    ex.lastUpdDt(Instant.now());
                    count++;
                    mwBizExpDtlRepository.save(ex);
                }

                // -----BUSINESS EXPENSE------//

                for (BizExpDto expense : dto.householdExpense) {
                    MwBizExpDtl ex = new MwBizExpDtl();
                    ex.setCrntRecFlg(true);
                    ex.setCrtdBy("w-" + currUser);
                    ex.setCrtdDt(Instant.now());
                    ex.setDelFlg(false);
                    ex.setEffStartDt(Instant.now());
                    ex.setExpAmt(expense.expAmount);
                    ex.setExpCtgryKey(expense.expCategoryKey);
                    ex.setExpDtlSeq(Long.parseLong(
                            (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_BIZ_EXP_DTL, app.getLoanCyclNum())
                                    + "").concat(count + "")));
                    ex.setExpTypKey(expense.expTypKey);
                    ex.setMwBizAprsl(exMwSchAprsl.getSchAprslSeq());
                    ex.setEntyTypFlg(2);
                    ex.setSyncFlg(true);

                    ex.lastUpdBy("w-" + currUser);
                    ex.lastUpdDt(Instant.now());
                    count++;
                    mwBizExpDtlRepository.save(ex);
                }


                // List<MwSchQltyChk> exQltChk = mwSchQltyChkRepository
                // .findAllBySchAprslSeqAndCrntRecFlg(exMwSchAprsl.getSchAprslSeq(), true);
                // mwSchQltyChkRepository.deleteInBatch(exQltChk);
                // for (SchoolQuestionsDto questionDto : dto.SchoolQAArray) {
                // for (QuestionDto question : questionDto.questions) {
                // MwSchQltyChk chk = new MwSchQltyChk();
                // chk.setSchQltyChkSeq(SequenceFinder.findNextVal(Sequences.SCH_QLTY_CHK_SEQ));
                // chk.setSchAprslSeq(exMwSchAprsl.getSchAprslSeq());
                // chk.setQstSeq(question.questionKey);
                // chk.setAnswrSeq(question.answerSeq);
                // chk.setCrtdBy(currUser);
                // chk.setCrtdDt(currIns);
                // chk.setLastUpdBy(currUser);
                // chk.setLastUpdDt(currIns);
                // chk.setDelFlg(false);
                // chk.setEffStartDt(currIns);
                // chk.setCrntRecFlg(true);
                // mwSchQltyChkRepository.save(chk);
                // }
                // }
                MwAddrRel mwAddrRel = mwAddrRelService.getAddressRelationByEntityKeyAndEntyTyp(exMwSchAprsl.getSchAprslSeq(),
                        "SchoolAppraisal");
                MwAddr exMwAddr = null;
                if (mwAddrRel != null)
                    exMwAddr = mwAddrService.findOneByAddrSeq(mwAddrRel.getAddrSeq());
                if (mwAddrRel != null) {
                    exMwAddr.setAddrSeq(mwAddrRel.getAddrSeq());
                    exMwAddr.setCitySeq(dto.getAddressDto().city);
                    exMwAddr.setCmntySeq(dto.getAddressDto().community);
                    exMwAddr.setHseNum(dto.getAddressDto().houseNum);
                    exMwAddr.setOthDtl(dto.getAddressDto().otherDetails);
                    exMwAddr.setStrt(dto.getAddressDto().sreet_area);
                    exMwAddr.setVlg(dto.getAddressDto().village);
                    exMwAddr.setLongitude(dto.getAddressDto().lon);
                    exMwAddr.setLatitude(dto.getAddressDto().lat);
                    dto.getAddressDto().addrSeq = mwAddrRel.getAddrSeq();
                    mwAddrService.updateAddress(dto.getAddressDto(), currUser);
                } else {
                    addressDto = new AddressDto();
                    if (dto.getAddressDto().houseNum != null || dto.getAddressDto().city != null || dto.getAddressDto().sreet_area != null
                            || dto.getAddressDto().community != null || dto.getAddressDto().village != null
                            || dto.getAddressDto().otherDetails != null) {
                        addressDto.schAprslSeq = exMwSchAprsl.getSchAprslSeq();
                        addressDto.houseNum = dto.getAddressDto().houseNum;
                        addressDto.sreet_area = dto.getAddressDto().sreet_area;
                        addressDto.community = dto.getAddressDto().community;
                        addressDto.village = dto.getAddressDto().village;
                        addressDto.otherDetails = dto.getAddressDto().otherDetails;
                        addressDto.city = dto.getAddressDto().city;
                        addressDto.district = dto.getAddressDto().district;
                        addressDto.tehsil = dto.getAddressDto().tehsil;
                        addressDto.city = dto.getAddressDto().city;
                        addressDto.uc = dto.getAddressDto().uc;
                        addressDto.addrSeq = dto.getAddressDto().addrSeq;
                        addressDto.cnicNum = clnt.getCnicNum();
                        addressDto.cycleNum = app.getLoanCyclNum();
                        mwAddrService.saveAddress(addressDto, currUser, "SchoolAppraisal");
                    }
                }
                exMwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(exMwSchAprsl.getSchAprslSeq(), true);
                if (exMwSchAtnd != null) {
                    exMwSchAtnd.setSchAtndSeq(exMwSchAtnd.getSchAtndSeq());
                    exMwSchAtnd.setSchAprslSeq(exMwSchAtnd.getSchAprslSeq());
                    exMwSchAtnd.setTotMaleTchrs(dto.getTotMaleTchrs());
                    exMwSchAtnd.setTotFemTchrs(dto.getTotFemTchrs());
                    exMwSchAtnd.setLastYrDrop(dto.getLastYrDrop());
                    exMwSchAtnd.setCrtdBy("w-" + currUser);
                    exMwSchAtnd.setCrtdDt(exMwSchAtnd.getCrtdDt());
                    exMwSchAtnd.setLastUpdBy("w-" + currUser);
                    exMwSchAtnd.setLastUpdDt(currIns);
                    exMwSchAtnd.setDelFlg(false);
                    exMwSchAtnd.setEffStartDt(exMwSchAtnd.getEffStartDt());
                    exMwSchAtnd.setCrntRecFlg(true);
                    exMwSchAtnd.setSyncFlg(true);
                    mwSchAtndRepository.save(exMwSchAtnd);
                } else {
                    if (dto.getLastYrDrop() != null || (dto.getTotMaleTchrs() != null && dto.getTotFemTchrs() != null)) {
                        MwSchAtnd mwSchAtnd = new MwSchAtnd();
                        long schoolAtndSeq = Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_SCH_ATND,
                                app.getLoanCyclNum());

                        mwSchAtnd.setSchAtndSeq(schoolAtndSeq);
                        mwSchAtnd.setSchAprslSeq(exMwSchAprsl.getSchAprslSeq());
                        mwSchAtnd.setTotMaleTchrs(dto.getTotMaleTchrs());
                        mwSchAtnd.setTotFemTchrs(dto.getTotFemTchrs());
                        mwSchAtnd.setLastYrDrop(dto.getLastYrDrop());
                        mwSchAtnd.setCrtdBy("w-" + currUser);
                        mwSchAtnd.setCrtdDt(currIns);
                        mwSchAtnd.setLastUpdBy("w-" + currUser);
                        mwSchAtnd.setLastUpdDt(currIns);
                        mwSchAtnd.setDelFlg(false);
                        mwSchAtnd.setEffStartDt(currIns);
                        mwSchAtnd.setCrntRecFlg(true);
                        mwSchAtnd.setSyncFlg(true);
                        mwSchAtndRepository.save(mwSchAtnd);
                    }
                }

                count = 1;
                List<MwSchGrd> exGrades = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(dto.getSchAprslSeq(), true);
                exGrades.forEach(grd -> {
                    grd.setCrntRecFlg(false);
                    grd.setDelFlg(true);
                    grd.setEffEndDt(Instant.now());
                    grd.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
                    grd.setLastUpdDt(Instant.now());
                    count++;
                });
                mwSchGrdRepository.save(exGrades);

                for (SchoolGradeDto schoolGradeDto : dto.getSchoolGradeDtos()) {
                    MwSchGrd mwSchGrd = new MwSchGrd();
                    long grdSeq = Long.parseLong(
                            (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_SCH_GRD, app.getLoanCyclNum())
                                    + "").concat(count + ""));

                    mwSchGrd.setSchGrdSeq(grdSeq);
                    mwSchGrd.setTotFemStdnt(schoolGradeDto.getTotFemStdnt());
                    mwSchGrd.setTotMaleStdnt(schoolGradeDto.getTotMaleStdnt());
                    mwSchGrd.setAvgGrdFee(schoolGradeDto.getAvgFee());
                    mwSchGrd.setNoFeeStdnt(schoolGradeDto.getNoFeeStdnt());
                    mwSchGrd.setFemStdntPrsnt(schoolGradeDto.getFemStdntPrsnt());
                    mwSchGrd.setMaleStdntPrsnt(schoolGradeDto.getMaleStdntPrsnt());
                    mwSchGrd.setGrdKey(schoolGradeDto.getGrdKey());
                    mwSchGrd.setSchAprslSeq(dto.getSchAprslSeq());
                    mwSchGrd.setCrtdBy("w-" + currUser);
                    mwSchGrd.setCrtdDt(currIns);
                    mwSchGrd.setLastUpdBy("w-" + currUser);
                    mwSchGrd.setLastUpdDt(currIns);
                    mwSchGrd.setDelFlg(false);
                    mwSchGrd.setEffStartDt(currIns);
                    mwSchGrd.setCrntRecFlg(true);
                    mwSchGrd.setSyncFlg(true);
                    mwSchGrds.add(mwSchGrd);
                    count++;
                }
                for (MwSchGrd mwSchGrd : mwSchGrds) {
                    mwSchGrdRepository.save(mwSchGrd);
                }
                mwSchAprslRepository.save(exMwSchAprsl);

                isSchoolAppraisalFormComplete(dto);
                if (dto.formComplete)
                    Common.updateFormComplFlag(dto.formSeq, dto.getLoanAppSeq(), currUser);
                else
                    Common.removeComplFlag(dto.formSeq, dto.getLoanAppSeq(), currUser);
            }
        }
        return dto;
    }
}
