package com.idev4.loans.service;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.domain.MwSchAsts;
import com.idev4.loans.domain.MwSchQltyChk;
import com.idev4.loans.dto.QuestionDto;
import com.idev4.loans.dto.SchoolInformationDto;
import com.idev4.loans.dto.SchoolQualityCheckDto;
import com.idev4.loans.dto.SchoolQuestionsDto;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.SequenceFinder;
import com.idev4.loans.web.rest.util.Sequences;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MwSchAstsService {

    private final Logger log = LoggerFactory.getLogger(MwSchAstsService.class);

    private final MwSchAprslRepository mwSchAprslRepository;

    private final MwSchAstsRepository mwSchAstsRepository;

    private final MwSchQltyChkRepository mwSchQltyChkRepository;

    private final MwQstService mwQstService;

    private final MwRefCdValService mwRefCdValService;

    private final MwAnswrService mwAnswrService;

    private final EntityManager em;

    private final MwClntRepository mwClntRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    public MwSchAstsService(MwSchAprslRepository mwSchAprslRepository, MwSchAstsRepository mwSchAstsRepository,
                            MwSchQltyChkRepository mwSchQltyChkRepository, MwAnswrService mwAnswrService, MwQstService mwQstService,
                            MwRefCdValService mwRefCdValService, EntityManager em, MwClntRepository mwClntRepository,
                            MwLoanAppRepository mwLoanAppRepository) {

        this.mwSchAprslRepository = mwSchAprslRepository;
        this.mwSchAstsRepository = mwSchAstsRepository;
        this.mwSchQltyChkRepository = mwSchQltyChkRepository;
        this.mwQstService = mwQstService;
        this.mwRefCdValService = mwRefCdValService;
        this.mwAnswrService = mwAnswrService;
        this.em = em;
        this.mwClntRepository = mwClntRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
    }

    @Transactional
    public SchoolInformationDto addNewSchAsts(SchoolInformationDto dto, String currUser) {

        MwSchAsts exMwSchAsts = mwSchAstsRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(dto.loanAppSeq, false, true);

        if (exMwSchAsts != null) {
            dto.schAstsSeq = exMwSchAsts.getSchAstsSeq();
            dto = updateSchoolAsts(dto, currUser);
            return dto;
        }
        Instant currIns = Instant.now();

        MwSchAsts mwSchAsts = saveAssets(currIns, dto, currUser);
        dto.schAstsSeq = mwSchAsts.getSchAstsSeq();
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
        Long schoolAppraislSeq = Common.GenerateTableSequence(clnt.getCnicNum() + "", TableNames.MW_SCH_APRSL, app.getLoanCyclNum());
        List<MwSchQltyChk> exQltChk = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(schoolAppraislSeq, true);
        exQltChk.forEach(chk -> {
            chk.setCrntRecFlg(false);
            chk.setDelFlg(true);
            chk.setEffEndDt(Instant.now());
            chk.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            chk.setLastUpdDt(Instant.now());
        });
        mwSchQltyChkRepository.save(exQltChk);
        for (SchoolQuestionsDto questionDto : dto.SchoolQAArray) {
            for (QuestionDto question : questionDto.questions) {
                MwSchQltyChk chk = new MwSchQltyChk();
                Long seq = Common.GenerateTableSequence(clnt.getCnicNum() + "", TableNames.MW_SCH_QLTY_CHK, app.getLoanCyclNum());
                chk.setSchQltyChkSeq(seq);
                chk.setSchAprslSeq(schoolAppraislSeq);
                chk.setQstSeq(question.questionKey);
                chk.setAnswrSeq(question.answerSeq);
                chk.setCrtdBy("w-" + currUser);
                chk.setCrtdDt(currIns);
                chk.setLastUpdBy("w-" + currUser);
                chk.setLastUpdDt(currIns);
                chk.setDelFlg(false);
                chk.setEffStartDt(currIns);
                chk.setCrntRecFlg(true);
                chk.setSyncFlg(true);
                mwSchQltyChkRepository.save(chk);
            }
        }

        for (SchoolQuestionsDto questionDto : dto.documentChecklist) {
            for (QuestionDto question : questionDto.questions) {
                MwSchQltyChk chk = new MwSchQltyChk();
                Long seq = Common.GenerateTableSequence(clnt.getCnicNum() + "", TableNames.MW_SCH_QLTY_CHK, app.getLoanCyclNum());
                chk.setSchQltyChkSeq(seq);
                chk.setSchAprslSeq(schoolAppraislSeq);
                chk.setQstSeq(question.questionKey);
                chk.setAnswrSeq(question.answerSeq);
                chk.setCrtdBy("w-" + currUser);
                chk.setCrtdDt(currIns);
                chk.setLastUpdBy("w-" + currUser);
                chk.setLastUpdDt(currIns);
                chk.setDelFlg(false);
                chk.setEffStartDt(currIns);
                chk.setCrntRecFlg(true);
                chk.setSyncFlg(true);
                mwSchQltyChkRepository.save(chk);
            }
        }

        isSchoolInfotmationFormComplete(dto);
        if (dto.formComplete)
            Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
        return dto;
    }

    public MwSchAsts saveAssets(Instant currIns, SchoolInformationDto dto, String currUser) {
        MwSchAsts mwSchAsts = new MwSchAsts();
        long schoolAstsSeq = SequenceFinder.findNextVal(Sequences.SCH_ASTS_SEQ);
        mwSchAsts.setSchAstsSeq(schoolAstsSeq);
        mwSchAsts.setTotRms(dto.totRms);
        mwSchAsts.setTotOfcs(dto.totOfcs);
        mwSchAsts.setTotToilets(dto.totToilets);
        mwSchAsts.setTotCmptrs(dto.totCmptrs);
        mwSchAsts.setTotChrs(dto.totChrs);
        mwSchAsts.setTotDsks(dto.totDsks);
        mwSchAsts.setTotLabs(dto.totLabs);
        mwSchAsts.setTotWclrs(dto.totWclrs);
        mwSchAsts.setTotFans(dto.totFans);
        mwSchAsts.setTotGnrtrs(dto.totGnrtrs);
        mwSchAsts.setTotFlrs(dto.totFlrs);
        mwSchAsts.setOthAsts(dto.othAsts);
        mwSchAsts.setCrtdBy(currUser);
        mwSchAsts.setCrtdDt(currIns);
        mwSchAsts.setLastUpdBy("w-" + currUser);
        mwSchAsts.setLastUpdDt(currIns);
        mwSchAsts.setDelFlg(false);
        mwSchAsts.setEffStartDt(currIns);
        mwSchAsts.setCrntRecFlg(true);
        mwSchAsts.setLoanAppSeq(dto.loanAppSeq);
        mwSchAsts.setSyncFlg(true);
        mwSchAstsRepository.save(mwSchAsts);
        return mwSchAsts;
    }

    public SchoolInformationDto isSchoolInfotmationFormComplete(SchoolInformationDto dto) {
        MwSchAsts mwSchAsts = mwSchAstsRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(dto.loanAppSeq, false, true);
        if (mwSchAsts != null) {
            if (mwSchAsts.getTotChrs() != null && mwSchAsts.getTotCmptrs() != null && mwSchAsts.getTotDsks() != null
                    && mwSchAsts.getTotFans() != null && mwSchAsts.getTotFlrs() != null && mwSchAsts.getTotWclrs() != null
                    && mwSchAsts.getTotGnrtrs() != null && mwSchAsts.getTotLabs() != null && mwSchAsts.getTotOfcs() != null
                    && mwSchAsts.getTotRms() != null && mwSchAsts.getTotToilets() != null) {
                dto.hasAssets = true;
                dto.hasDocChck = true;
                dto.hasQltyChck = true;
            } else {
                dto.hasAssets = false;
                dto.hasDocChck = false;
                dto.hasQltyChck = false;
            }
        } else {
            dto.hasAssets = false;
            dto.hasDocChck = false;
            dto.hasQltyChck = false;
        }
//        int i = -1;
//        for ( SchoolQuestionsDto questionDto : dto.documentChecklist ) {
//            for ( QuestionDto question : questionDto.questions ) {
//                if ( question.answerSeq == 0 ) {
//                    i++;
//                }
//            }
//        }
//        if ( i == -1 ) {
//            dto.hasDocChck = true;
//        }
//
//        i = -1;
//        for ( SchoolQuestionsDto questionDto : dto.SchoolQAArray ) {
//            for ( QuestionDto question : questionDto.questions ) {
//                if ( question.answerSeq == 0 ) {
//                    i++;
//                }
//            }
//        }
//        if ( i == -1 ) {
//            dto.hasQltyChck = true;
//        }
        if (dto.hasAssets && dto.hasQltyChck && dto.hasDocChck)
            dto.formComplete = true;
        return dto;
    }

    @Transactional
    public SchoolInformationDto getSchoolAsts(long seq) {
        SchoolInformationDto dto = new SchoolInformationDto();
        dto.SchoolQAArray = new ArrayList<>();
        dto.documentChecklist = new ArrayList<>();
        MwSchAsts mwSchAsts = mwSchAstsRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(seq, false, true);
        if (mwSchAsts != null) {
            dto.schAstsSeq = mwSchAsts.getSchAstsSeq();
            dto.loanAppSeq = mwSchAsts.getLoanAppSeq();
            dto.othAsts = (mwSchAsts.getOthAsts() == null) ? "" : mwSchAsts.getOthAsts();
            dto.totChrs = mwSchAsts.getTotChrs();
            dto.totCmptrs = mwSchAsts.getTotCmptrs();
            dto.totDsks = mwSchAsts.getTotDsks();
            dto.totFans = mwSchAsts.getTotFans();
            dto.totFlrs = mwSchAsts.getTotFlrs();
            dto.totGnrtrs = mwSchAsts.getTotGnrtrs();
            dto.totLabs = mwSchAsts.getTotLabs();
            dto.totOfcs = mwSchAsts.getTotOfcs();
            dto.totRms = mwSchAsts.getTotRms();
            dto.totToilets = mwSchAsts.getTotToilets();
            dto.totWclrs = mwSchAsts.getTotWclrs();

            // Added by Zohaib Asim - Dated 14-4-2022 - Fields information missing
            dto.totMalTolts = mwSchAsts.getTotMalTolts();
            dto.totFmalTolts = mwSchAsts.getTotFmalTolts();
            dto.totCmptrLabs = mwSchAsts.getTotCmptrLabs();
            // End

            if (mwSchAsts != null) {
                if (mwSchAsts.getTotChrs() != null && mwSchAsts.getTotCmptrs() != null && mwSchAsts.getTotDsks() != null
                        && mwSchAsts.getTotFans() != null && mwSchAsts.getTotFlrs() != null && mwSchAsts.getTotWclrs() != null
                        && mwSchAsts.getTotGnrtrs() != null && mwSchAsts.getTotLabs() != null && mwSchAsts.getTotOfcs() != null
                        && mwSchAsts.getTotRms() != null && mwSchAsts.getTotToilets() != null) {
                    dto.hasAssets = true;
                } else {
                    dto.hasAssets = false;
                }
            }
        }
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
        Long schoolAppraislSeq = Common.GenerateTableSequence(clnt.getCnicNum() + "", TableNames.MW_SCH_APRSL, app.getLoanCyclNum());
        dto.mwAnswers = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(schoolAppraislSeq, true);
        List<MwSchQltyChk> mwSchQltyChks = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(schoolAppraislSeq, true);
        List<SchoolQualityCheckDto> schoolQualityCheckDtos = new ArrayList<SchoolQualityCheckDto>();
        if (mwSchQltyChks != null) {
            for (MwSchQltyChk mwSchQltyChk : mwSchQltyChks) {
                schoolQualityCheckDtos.add(schoolQualityCheckDomainToDto(mwSchQltyChk));
            }
            dto.schoolQualityCheckDtos = schoolQualityCheckDtos;
        }
        isSchoolInfotmationFormComplete(dto);
        return dto;
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

    @Transactional
    public SchoolInformationDto updateSchoolAsts(SchoolInformationDto dto, String currUser) {

        Instant currIns = Instant.now();
        List<MwSchQltyChk> mwSchQltyChks = new ArrayList<>();

        MwSchAsts exMwSchAsts = mwSchAstsRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(dto.loanAppSeq, false, true);
        if (exMwSchAsts == null)
            exMwSchAsts = saveAssets(currIns, dto, currUser);
        exMwSchAsts.setTotRms(dto.totRms);
        exMwSchAsts.setTotOfcs(dto.totOfcs);
        exMwSchAsts.setTotToilets(dto.totToilets);
        exMwSchAsts.setTotCmptrs(dto.totCmptrs);
        exMwSchAsts.setTotChrs(dto.totChrs);
        exMwSchAsts.setTotDsks(dto.totDsks);
        exMwSchAsts.setTotLabs(dto.totLabs);
        exMwSchAsts.setTotWclrs(dto.totWclrs);
        exMwSchAsts.setTotFans(dto.totFans);
        exMwSchAsts.setTotGnrtrs(dto.totGnrtrs);
        exMwSchAsts.setTotFlrs(dto.totFlrs);
        exMwSchAsts.setOthAsts(dto.othAsts);
        exMwSchAsts.setLastUpdBy("w-" + currUser);
        exMwSchAsts.setLastUpdDt(currIns);
        exMwSchAsts.setSyncFlg(true);
        mwSchAstsRepository.save(exMwSchAsts);

        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
        Long schoolAppraislSeq = Common.GenerateTableSequence(clnt.getCnicNum() + "", TableNames.MW_SCH_APRSL, app.getLoanCyclNum());

        List<MwSchQltyChk> exQltChk = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(schoolAppraislSeq, true);
        exQltChk.forEach(chk -> {
            chk.setCrntRecFlg(false);
            chk.setDelFlg(true);
            chk.setEffEndDt(Instant.now());
            chk.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
            chk.setLastUpdDt(Instant.now());
        });
        mwSchQltyChkRepository.save(exQltChk);
        for (SchoolQuestionsDto questionDto : dto.SchoolQAArray) {
            for (QuestionDto question : questionDto.questions) {
                MwSchQltyChk chk = new MwSchQltyChk();
                Long seq = Common.GenerateTableSequence(clnt.getCnicNum() + "", TableNames.MW_SCH_QLTY_CHK, app.getLoanCyclNum());
                chk.setSchQltyChkSeq(seq);
                chk.setSchAprslSeq(schoolAppraislSeq);
                chk.setQstSeq(question.questionKey);
                chk.setAnswrSeq(question.answerSeq);
                chk.setCrtdBy("w-" + currUser);
                chk.setCrtdDt(currIns);
                chk.setLastUpdBy("w-" + currUser);
                chk.setLastUpdDt(currIns);
                chk.setDelFlg(false);
                chk.setEffStartDt(currIns);
                chk.setCrntRecFlg(true);
                chk.setSyncFlg(true);
                mwSchQltyChkRepository.save(chk);
            }
        }

        for (SchoolQuestionsDto questionDto : dto.documentChecklist) {
            for (QuestionDto question : questionDto.questions) {
                MwSchQltyChk chk = new MwSchQltyChk();
                Long seq = Common.GenerateTableSequence(clnt.getCnicNum() + "", TableNames.MW_SCH_QLTY_CHK, app.getLoanCyclNum());
                chk.setSchQltyChkSeq(seq);
                chk.setSchAprslSeq(schoolAppraislSeq);
                chk.setQstSeq(question.questionKey);
                chk.setAnswrSeq(question.answerSeq);
                chk.setCrtdBy("w-" + currUser);
                chk.setCrtdDt(currIns);
                chk.setLastUpdBy("w-" + currUser);
                chk.setLastUpdDt(currIns);
                chk.setDelFlg(false);
                chk.setEffStartDt(currIns);
                chk.setCrntRecFlg(true);
                chk.setSyncFlg(true);
                mwSchQltyChkRepository.save(chk);
            }
        }

        isSchoolInfotmationFormComplete(dto);
        if (dto.formComplete)
            Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
        else
            Common.removeComplFlag(dto.formSeq, dto.loanAppSeq, currUser);

        return dto;
    }
}
