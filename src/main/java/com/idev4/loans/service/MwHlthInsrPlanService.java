package com.idev4.loans.service;

import com.idev4.loans.domain.MwHlthInsrPlan;
import com.idev4.loans.repository.MwHlthInsrPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing MwHlthInsrPlan.
 */
@Service
@Transactional
public class MwHlthInsrPlanService {

    private final Logger log = LoggerFactory.getLogger(MwHlthInsrPlanService.class);

    private final MwHlthInsrPlanRepository mwHlthInsrPlanRepository;

    public MwHlthInsrPlanService(MwHlthInsrPlanRepository mwHlthInsrPlanRepository) {
        this.mwHlthInsrPlanRepository = mwHlthInsrPlanRepository;
    }

    /**
     * Save a mwHlthInsrPlan.
     *
     * @param mwHlthInsrPlan the entity to save
     * @return the persisted entity
     */
    public MwHlthInsrPlan save(MwHlthInsrPlan mwHlthInsrPlan) {
        log.debug("Request to save MwHlthInsrPlan : {}", mwHlthInsrPlan);
        return mwHlthInsrPlanRepository.save(mwHlthInsrPlan);
    }

    /**
     * Get all the mwHlthInsrPlans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwHlthInsrPlan> findAll(Pageable pageable) {
        log.debug("Request to get all MwHlthInsrPlans");
        return mwHlthInsrPlanRepository.findAll(pageable);
    }

    /**
     * Get one mwHlthInsrPlan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwHlthInsrPlan findOne(Long id) {
        log.debug("Request to get MwHlthInsrPlan : {}", id);
        return mwHlthInsrPlanRepository.findOne(id);
    }

    public List<MwHlthInsrPlan> getAllHlthInsurancePlans() {
        return mwHlthInsrPlanRepository.findAllByCrntRecFlg(true);
    }
}
