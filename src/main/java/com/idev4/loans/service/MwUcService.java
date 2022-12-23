package com.idev4.loans.service;

import com.idev4.loans.domain.MwUc;
import com.idev4.loans.repository.MwUcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwUc.
 */
@Service
@Transactional
public class MwUcService {

    private final Logger log = LoggerFactory.getLogger(MwUcService.class);

    private final MwUcRepository mwUcRepository;

    public MwUcService(MwUcRepository mwUcRepository) {
        this.mwUcRepository = mwUcRepository;
    }

    /**
     * Save a mwUc.
     *
     * @param mwUc the entity to save
     * @return the persisted entity
     */
    public MwUc save(MwUc mwUc) {
        log.debug("Request to save MwUc : {}", mwUc);
        return mwUcRepository.save(mwUc);
    }

    /**
     * Get all the mwUcs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwUc> findAll(Pageable pageable) {
        log.debug("Request to get all MwUcs");
        return mwUcRepository.findAll(pageable);
    }

    /**
     * Get one mwUc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwUc findOne(Long id) {
        log.debug("Request to get MwUc : {}", id);
        return mwUcRepository.findOne(id);
    }

}
