package com.idev4.loans.service;

import com.idev4.loans.domain.MwCmnty;
import com.idev4.loans.repository.MwCmntyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwCmnty.
 */
@Service
@Transactional
public class MwCmntyService {

    private final Logger log = LoggerFactory.getLogger(MwCmntyService.class);

    private final MwCmntyRepository mwCmntyRepository;

    public MwCmntyService(MwCmntyRepository mwCmntyRepository) {
        this.mwCmntyRepository = mwCmntyRepository;
    }

    /**
     * Save a mwCmnty.
     *
     * @param mwCmnty the entity to save
     * @return the persisted entity
     */
    public MwCmnty save(MwCmnty mwCmnty) {
        log.debug("Request to save MwCmnty : {}", mwCmnty);
        return mwCmntyRepository.save(mwCmnty);
    }

    /**
     * Get all the mwCmnties.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCmnty> findAll(Pageable pageable) {
        log.debug("Request to get all MwCmnties");
        return mwCmntyRepository.findAll(pageable);
    }

    /**
     * Get one mwCmnty by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCmnty findOne(Long id) {
        log.debug("Request to get MwCmnty : {}", id);
        return mwCmntyRepository.findOne(id);
    }

}
