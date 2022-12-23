package com.idev4.loans.service;

import com.idev4.loans.domain.MwBrnch;
import com.idev4.loans.repository.MwBrnchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwBrnch.
 */
@Service
@Transactional
public class MwBrnchService {

    private final Logger log = LoggerFactory.getLogger(MwBrnchService.class);

    private final MwBrnchRepository mwBrnchRepository;

    public MwBrnchService(MwBrnchRepository mwBrnchRepository) {
        this.mwBrnchRepository = mwBrnchRepository;
    }

    /**
     * Save a mwBrnch.
     *
     * @param mwBrnch the entity to save
     * @return the persisted entity
     */
    public MwBrnch save(MwBrnch mwBrnch) {
        log.debug("Request to save MwBrnch : {}", mwBrnch);
        return mwBrnchRepository.save(mwBrnch);
    }

    /**
     * Get all the mwBrnches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBrnch> findAll(Pageable pageable) {
        log.debug("Request to get all MwBrnches");
        return mwBrnchRepository.findAll(pageable);
    }

    /**
     * Get one mwBrnch by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwBrnch findOne(Long id) {
        log.debug("Request to get MwBrnch : {}", id);
        return mwBrnchRepository.findOne(id);
    }

}
