package com.idev4.loans.service;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwClntHlthInsr;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.ClntHlthInsrDto;
import com.idev4.loans.dto.HlthInsrMemberDto;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwClntHlthInsr.
 */
@Service
@Transactional
public class MwClntHlthInsrService {

    private final Logger log = LoggerFactory.getLogger(MwClntHlthInsrService.class);

    private final MwClntHlthInsrRepository mwClntHlthInsrRepository;

    private final MwHlthInsrMembService mwHlthInsrMembService;

    private final MwLoanAppChrgStngsRepository mwLoanAppChrgStngsRepository;

    private final MwHlthInsrPlanRepository mwHlthInsrPlanRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    private final EntityManager em;

    public MwClntHlthInsrService(MwClntHlthInsrRepository mwClntHlthInsrRepository, MwHlthInsrMembService mwHlthInsrMembService,
                                 EntityManager em, MwLoanAppChrgStngsRepository mwLoanAppChrgStngsRepository, MwHlthInsrPlanRepository mwHlthInsrPlanRepository,
                                 MwLoanAppRepository mwLoanAppRepository, MwClntRepository mwClntRepository) {
        this.mwClntHlthInsrRepository = mwClntHlthInsrRepository;
        this.mwHlthInsrMembService = mwHlthInsrMembService;
        this.em = em;
        this.mwLoanAppChrgStngsRepository = mwLoanAppChrgStngsRepository;
        this.mwHlthInsrPlanRepository = mwHlthInsrPlanRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * Save a mwClntHlthInsr.
     *
     * @param mwClntHlthInsr the entity to save
     * @return the persisted entity
     */
    public MwClntHlthInsr save(MwClntHlthInsr mwClntHlthInsr) {
        log.debug("Request to save MwClntHlthInsr : {}", mwClntHlthInsr);
        return mwClntHlthInsrRepository.save(mwClntHlthInsr);
    }

    /**
     * Get all the mwClntHlthInsrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwClntHlthInsr> findAll(Pageable pageable) {
        log.debug("Request to get all MwClntHlthInsrs");
        return mwClntHlthInsrRepository.findAll(pageable);
    }

    /**
     * Get one mwClntHlthInsr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwClntHlthInsr findOne(Long id) {
        log.debug("Request to get MwClntHlthInsr : {}", id);
        return mwClntHlthInsrRepository.findOne(id);
    }

    @Transactional
    public long updateClientHealthInsr(ClntHlthInsrDto dto, String currUser) {
        MwClntHlthInsr ins = mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (ins != null) {
            ins.setExclCtgryKey(dto.exclusionCategoryKey == null ? -1L : dto.exclusionCategoryKey);
            ins.setHlthInsrFlg(dto.hlthInsrFlag);
            ins.setLastUpdBy("w-" + currUser);
            ins.setLastUpdDt(Instant.now());
            ins.setMwHlthInsrPlan(dto.healthInsrPlanSeq);
            ins.setLoanAppSeq(dto.loanAppSeq);
            ins.setRelWthBreadEarnerKey(dto.relWithBreadEarnerKey);
            ins.setMainBreadEarnerNm(dto.mainBreadEarnerName);
            ins.setSyncFlg(true);
            Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);

            // MwLoanAppChrgStngs newChrg = new MwLoanAppChrgStngs();
            // newChrg.setLoanAppChrgStngsSeq(0L);
            // MwLoanAppChrgStngs chrg = mwLoanAppChrgStngsRepository.findOneByLoanAppSeqAndPrdChrgSeq(dto.loanAppSeq, -2L);
            //
            // if(chrg!=null) {
            // if(dto.hlthInsrFlag) {
            // mwLoanAppChrgStngsRepository.delete(chrg);
            // }else {
            // MwHlthInsrPlan healthInsurance = mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(dto.healthInsrPlanSeq,true);
            // chrg.setTypSeq(healthInsurance.getHlthInsrPlanSeq());
            // mwLoanAppChrgStngsRepository.save(chrg);
            // }
            // }else if(!dto.hlthInsrFlag) {
            // MwHlthInsrPlan healthInsurance = mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(dto.healthInsrPlanSeq,true);
            // chrg = new MwLoanAppChrgStngs();
            // chrg.setLoanAppChrgStngsSeq(SequenceFinder.findNextVal(Sequences.LOAN_APP_CHRG_STNGS_SEQ));
            // chrg.setPrdChrgSeq(-2L);
            // chrg.setPrdSeq(dto.loanPrd);
            // chrg.setTypSeq(healthInsurance.getHlthInsrPlanSeq());
            // mwLoanAppChrgStngsRepository.save(chrg);
            // }
            Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
            return mwClntHlthInsrRepository.save(ins).getClntHlthInsrSeq();
        } else {
            MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
            if (app != null) {
                MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
                if (clnt != null) {
                    long seq = Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_CLNT_HLTH_INSR,
                            app.getLoanCyclNum());
                    MwClntHlthInsr clntHealthInsr = new MwClntHlthInsr();
                    Instant currIns = Instant.now();
                    clntHealthInsr.setClntHlthInsrSeq(seq);
                    clntHealthInsr.setCrntRecFlg(true);
                    clntHealthInsr.setCrtdBy("w-" + currUser);
                    clntHealthInsr.setCrtdDt(currIns);
                    clntHealthInsr.setDelFlg(false);
                    clntHealthInsr.setEffStartDt(currIns);
                    clntHealthInsr.setExclCtgryKey(dto.exclusionCategoryKey == null ? -1L : dto.exclusionCategoryKey);
                    clntHealthInsr.setHlthInsrFlg(dto.hlthInsrFlag);
                    clntHealthInsr.setLastUpdBy("w-" + currUser);
                    clntHealthInsr.setLastUpdDt(currIns);
                    clntHealthInsr.setMwHlthInsrPlan(dto.healthInsrPlanSeq);
                    clntHealthInsr.setLoanAppSeq(dto.loanAppSeq);
                    clntHealthInsr.setRelWthBreadEarnerKey(dto.relWithBreadEarnerKey);
                    clntHealthInsr.setMainBreadEarnerNm(dto.mainBreadEarnerName);
                    clntHealthInsr.setSyncFlg(true);
                    Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);

                    // MwLoanAppChrgStngs newChrg = new MwLoanAppChrgStngs();
                    // newChrg.setLoanAppChrgStngsSeq(0L);
                    // MwLoanAppChrgStngs chrg = mwLoanAppChrgStngsRepository.findOneByLoanAppSeqAndPrdChrgSeq(dto.loanAppSeq, -2L);
                    //
                    // if(chrg!=null) {
                    // if(dto.hlthInsrFlag) {
                    // mwLoanAppChrgStngsRepository.delete(chrg);
                    // }else {
                    // MwHlthInsrPlan healthInsurance =
                    // mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(dto.healthInsrPlanSeq,true);
                    // chrg.setTypSeq(healthInsurance.getHlthInsrPlanSeq());
                    // mwLoanAppChrgStngsRepository.save(chrg);
                    // }
                    // }else if(!dto.hlthInsrFlag) {
                    // MwHlthInsrPlan healthInsurance =
                    // mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(dto.healthInsrPlanSeq,true);
                    // chrg = new MwLoanAppChrgStngs();
                    // chrg.setLoanAppChrgStngsSeq(SequenceFinder.findNextVal(Sequences.LOAN_APP_CHRG_STNGS_SEQ));
                    // chrg.setPrdChrgSeq(-2L);
                    // chrg.setPrdSeq(dto.loanPrd);
                    // chrg.setTypSeq(healthInsurance.getHlthInsrPlanSeq());
                    // mwLoanAppChrgStngsRepository.save(chrg);
                    // }
                    Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
                    return mwClntHlthInsrRepository.save(clntHealthInsr).getClntHlthInsrSeq();
                }
            }

        }
        return 0;
    }

    @Transactional
    public long updateClientHealthInsrOnUpdate(ClntHlthInsrDto dto, String currUser) {
        MwClntHlthInsr mwInsr = mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        Instant currIns = Instant.now();
        if (mwInsr == null)
            return 0;

        mwInsr.setLastUpdDt(currIns);
        mwInsr.setLastUpdBy(currUser);
        mwInsr.setExclCtgryKey(dto.exclusionCategoryKey);
        mwInsr.setHlthInsrFlg(dto.hlthInsrFlag);
        mwInsr.setMwHlthInsrPlan(dto.healthInsrPlanSeq);
        mwInsr.setLoanAppSeq(dto.loanAppSeq);
        mwInsr.setRelWthBreadEarnerKey(dto.relWithBreadEarnerKey);
        mwInsr.setMainBreadEarnerNm(dto.mainBreadEarnerName);

        // exInsr.setLastUpdBy( "w-" + currUser );
        // exInsr.setLastUpdDt( currIns );
        // exInsr.setCrntRecFlg( false );
        // exInsr.setEffEndDt( currIns );
        // mwClntHlthInsrRepository.save( exInsr );

        // MwClntHlthInsr clntHealthInsr = new MwClntHlthInsr();
        // clntHealthInsr.setClntHlthInsrSeq( exInsr.getClntHlthInsrSeq() );
        // clntHealthInsr.setCrntRecFlg( true );
        // clntHealthInsr.setCrtdBy( "w-" + currUser );
        // clntHealthInsr.setCrtdDt( currIns );
        // clntHealthInsr.setDelFlg( false );
        // clntHealthInsr.setEffStartDt( currIns );
        // clntHealthInsr.setExclCtgryKey( dto.exclusionCategoryKey );
        // clntHealthInsr.setHlthInsrFlg( dto.hlthInsrFlag );
        // clntHealthInsr.setLastUpdBy( "w-" + currUser );
        // clntHealthInsr.setLastUpdDt( currIns );
        // clntHealthInsr.setMwHlthInsrPlan( dto.healthInsrPlanSeq );
        // clntHealthInsr.setLoanAppSeq( dto.loanAppSeq );
        // clntHealthInsr.setRelWthBreadEarnerKey( dto.relWithBreadEarnerKey );
        // clntHealthInsr.setMainBreadEarnerNm( dto.mainBreadEarnerName );
        // clntHealthInsr.setSyncFlg( true );

        ///////////////////////
        // MwLoanAppChrgStngs newChrg = new MwLoanAppChrgStngs();
        // newChrg.setLoanAppChrgStngsSeq(0L);
        // MwLoanAppChrgStngs chrg = mwLoanAppChrgStngsRepository.findOneByLoanAppSeqAndPrdChrgSeq(dto.loanAppSeq, -2L);
        //
        // if(chrg!=null) {
        // if(dto.hlthInsrFlag) {
        // mwLoanAppChrgStngsRepository.delete(chrg);
        // }else {
        // MwHlthInsrPlan healthInsurance = mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(dto.healthInsrPlanSeq,true);
        // chrg.setTypSeq(healthInsurance.getHlthInsrPlanSeq());
        // mwLoanAppChrgStngsRepository.save(chrg);
        // }
        // }else if(!dto.hlthInsrFlag) {
        // MwHlthInsrPlan healthInsurance = mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(dto.healthInsrPlanSeq,true);
        // chrg = new MwLoanAppChrgStngs();
        // chrg.setLoanAppChrgStngsSeq(SequenceFinder.findNextVal(Sequences.LOAN_APP_CHRG_STNGS_SEQ));
        // chrg.setPrdChrgSeq(-2L);
        // chrg.setPrdSeq(dto.loanPrd);
        // chrg.setTypSeq(healthInsurance.getHlthInsrPlanSeq());
        // mwLoanAppChrgStngsRepository.save(chrg);
        // }
        //
        Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
        return mwClntHlthInsrRepository.save(mwInsr).getClntHlthInsrSeq();
    }

    public List<HlthInsrMemberDto> getClientHealthInsuranceMembers(Long loanAppSeq) {
        return mwHlthInsrMembService.findAllByLoanAppSeq(loanAppSeq);
    }

    public MwClntHlthInsr getOneByLoanAppSeq(long loanAppSeq) {
        return mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
    }

    public ClntHlthInsrDto getClientHealthInsrByLoanApp(long loanAppSeq) {
        MwClntHlthInsr ins = mwClntHlthInsrRepository.findOneByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        ClntHlthInsrDto dto = new ClntHlthInsrDto();
        if (ins == null)
            return dto;
        dto.loanAppSeq = ins.getLoanAppSeq();
        dto.clntHlthInsrSeq = ins.getClntHlthInsrSeq();
        dto.healthInsrPlanSeq = ins.getMwHlthInsrPlan();
        dto.exclusionCategoryKey = ins.getExclCtgryKey();
        dto.hlthInsrFlag = ins.isHlthInsrFlg();
        dto.relWithBreadEarnerKey = ins.getRelWthBreadEarnerKey();
        dto.mainBreadEarnerName = ins.getMainBreadEarnerNm();
        return dto;
    }
}
