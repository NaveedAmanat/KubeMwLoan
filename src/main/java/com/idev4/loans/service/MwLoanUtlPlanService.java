package com.idev4.loans.service;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.domain.MwLoanUtlPlan;
import com.idev4.loans.dto.LoanUtilDto;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.repository.MwLoanUtlPlanRepository;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MwLoanUtlPlan.
 */
@Service
@Transactional
public class MwLoanUtlPlanService {

    private final Logger log = LoggerFactory.getLogger(MwLoanUtlPlanService.class);

    private final MwLoanUtlPlanRepository mwLoanUtlPlanRepository;

    private final MwLoanAppService mwLoanAppService;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    public MwLoanUtlPlanService(MwLoanUtlPlanRepository mwLoanUtlPlanRepository, MwLoanAppService mwLoanAppService,
                                MwLoanAppRepository mwLoanAppRepository, MwClntRepository mwClntRepository) {
        this.mwLoanUtlPlanRepository = mwLoanUtlPlanRepository;
        this.mwLoanAppService = mwLoanAppService;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * Save a mwLoanUtlPlan.
     *
     * @param mwLoanUtlPlan the entity to save
     * @return the persisted entity
     */
    public MwLoanUtlPlan save(MwLoanUtlPlan mwLoanUtlPlan) {
        log.debug("Request to save MwLoanUtlPlan : {}", mwLoanUtlPlan);
        return mwLoanUtlPlanRepository.save(mwLoanUtlPlan);
    }

    /**
     * Get all the mwLoanUtlPlans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwLoanUtlPlan> findAll(Pageable pageable) {
        log.debug("Request to get all MwLoanUtlPlans");
        return mwLoanUtlPlanRepository.findAll(pageable);
    }

    /**
     * Get one mwLoanUtlPlan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwLoanUtlPlan findOne(Long id) {
        log.debug("Request to get MwLoanUtlPlan : {}", id);
        return mwLoanUtlPlanRepository.findOne(id);
    }

    /**
     * Delete the mwLoanUtlPlan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwLoanUtlPlan : {}", id);
        MwLoanUtlPlan plan = mwLoanUtlPlanRepository.findOneByLoanUtlPlanSeqAndCrntRecFlg(id, true);
        if (plan == null)
            return;
        plan.setCrntRecFlg(false);
        plan.setDelFlg(true);
        plan.setEffEndDt(Instant.now());
        plan.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        plan.setLastUpdDt(Instant.now());
        mwLoanUtlPlanRepository.save(plan);
    }

    public long createLoanUtilPlan(LoanUtilDto dto, String currUser) {
        int length = 0;
        double amt = 0;
        List<MwLoanUtlPlan> plans = mwLoanUtlPlanRepository.findAllByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (plans != null) {
            length = plans.size();
            for (MwLoanUtlPlan plan : plans) {
                amt = amt + plan.getLoanUtlAmt().doubleValue();
            }
            amt = amt + dto.loanUtilAmount;
            if (amt == app.getRcmndLoanAmt().doubleValue()) {
                Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
            } else {
                Common.removeComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
            }
        }
        if (app != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                long seq = Long.parseLong(
                        (Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_LOAN_UTL_PLAN, app.getLoanCyclNum())
                                + "").concat(length + ""));

                MwLoanUtlPlan utilPlan = new MwLoanUtlPlan();
                Instant currIns = Instant.now();
                utilPlan.setLoanUtlPlanSeq(seq);
                utilPlan.setCrntRecFlg(true);
                utilPlan.setCrtdBy("w-" + currUser);
                utilPlan.setCrtdDt(currIns);
                utilPlan.setDelFlg(false);
                utilPlan.setEffStartDt(currIns);
                utilPlan.setLastUpdBy("w-" + currUser);
                utilPlan.setLastUpdDt(currIns);
                utilPlan.setLoanAppSeq(dto.loanAppSeq);
                utilPlan.setLoanUtlAmt(dto.loanUtilAmount);
                utilPlan.setLoanUtlDscr(dto.loanUtilDesc);
                utilPlan.setLoanUtlTyp(dto.loanUtilType);
                utilPlan.setSyncFlg(true);
                return mwLoanUtlPlanRepository.save(utilPlan).getLoanUtlPlanSeq();
            }
        }
        return 0;
    }

    @Transactional
    public long updateLoanUtilPlan(LoanUtilDto dto, String currUser) {
        MwLoanUtlPlan mwUtilPlan = mwLoanUtlPlanRepository.findOneByLoanUtlPlanSeqAndCrntRecFlg(dto.utilPlanSeq, true);
        Instant currIns = Instant.now();
        if (mwUtilPlan == null)
            return 0;

        mwUtilPlan.setLastUpdDt(currIns);
        mwUtilPlan.setLastUpdBy("w-" + currUser);
        mwUtilPlan.setLoanUtlAmt(dto.loanUtilAmount);
        mwUtilPlan.setLoanUtlDscr(dto.loanUtilDesc);
        mwUtilPlan.setLoanUtlTyp(dto.loanUtilType);
        mwUtilPlan.setSyncFlg(true);
        // exUtilPlan.setLastUpdBy( "w-" + currUser );
        // exUtilPlan.setLastUpdDt( currIns );
        // exUtilPlan.setCrntRecFlg( false );
        // exUtilPlan.setEffEndDt( currIns );
        //
        // mwLoanUtlPlanRepository.save( exUtilPlan );
        // MwLoanUtlPlan utilPlan = new MwLoanUtlPlan();
        //
        // utilPlan.setLoanUtlPlanSeq( dto.utilPlanSeq );
        // utilPlan.setCrntRecFlg( true );
        // utilPlan.setCrtdBy( "w-" + currUser );
        // utilPlan.setCrtdDt( currIns );
        // utilPlan.setDelFlg( false );
        // utilPlan.setEffStartDt( currIns );
        // utilPlan.setLastUpdBy( "w-" + currUser );
        // utilPlan.setLastUpdDt( currIns );
        // utilPlan.setLoanAppSeq( dto.loanAppSeq );
        // utilPlan.setLoanUtlAmt( dto.loanUtilAmount );
        // utilPlan.setLoanUtlDscr( dto.loanUtilDesc );
        // utilPlan.setLoanUtlTyp( dto.loanUtilType );
        // utilPlan.setSyncFlg( true );
        double amt = 0;
        List<MwLoanUtlPlan> plans = mwLoanUtlPlanRepository.findAllByLoanAppSeq(dto.loanAppSeq);
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (plans != null) {
            for (MwLoanUtlPlan plan : plans) {
                amt = amt + plan.getLoanUtlAmt().doubleValue();
            }
            amt = amt + dto.loanUtilAmount;
            if (amt == app.getRcmndLoanAmt().doubleValue()) {
                Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
            } else {
                Common.removeComplFlag(dto.formSeq, dto.loanAppSeq, currUser);
            }
        }

        return mwLoanUtlPlanRepository.save(mwUtilPlan).getLoanAppSeq();
    }

    public Map getUtilPlanOfLoanApplication(long loanAppSeq) {
        MwLoanApp app = mwLoanAppService.findOne(loanAppSeq);
        List<MwLoanUtlPlan> plan = mwLoanUtlPlanRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        List<LoanUtilDto> dtosList = new ArrayList<>();
        if (plan != null && plan.size() > 0) {
            plan.forEach((pl) -> {
                LoanUtilDto dto = new LoanUtilDto();
                dto.loanAppSeq = pl.getLoanAppSeq();
                dto.loanUtilAmount = pl.getLoanUtlAmt();
                dto.loanUtilDesc = pl.getLoanUtlDscr();
                dto.utilPlanSeq = pl.getLoanUtlPlanSeq();
                dto.loanUtilType = pl.getLoanUtlTyp();
                dtosList.add(dto);
            });
        }

        Map<String, Object> data = new HashMap<String, Object>();
        if (app != null) {
            data.put("recAmount", app.getRcmndLoanAmt());
            data.put("loanUtilization", dtosList);
            return data;
        } else
            return null;
    }
}
