package com.idev4.loans.service;

import com.idev4.loans.domain.MwPrd;
import com.idev4.loans.repository.MwPrdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwPrd.
 */
@Service
@Transactional
public class MwPrdService {

    private final Logger log = LoggerFactory.getLogger(MwPrdService.class);

    private final MwPrdRepository mwPrdRepository;

    public MwPrdService(MwPrdRepository mwPrdRepository) {
        this.mwPrdRepository = mwPrdRepository;
    }

    /**
     * Save a mwPrd.
     *
     * @param mwPrd the entity to save
     * @return the persisted entity
     */
    public MwPrd save(MwPrd mwPrd) {
        log.debug("Request to save MwPrd : {}", mwPrd);
        return mwPrdRepository.save(mwPrd);
    }

    /**
     * Get all the mwPrds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwPrd> findAll(Pageable pageable) {
        log.debug("Request to get all MwPrds");
        return mwPrdRepository.findAll(pageable);
    }

    /**
     * Get one mwPrd by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwPrd findOne(Long id) {
        log.debug("Request to get MwPrd : {}", id);
        return mwPrdRepository.findOne(id);
    }

}
