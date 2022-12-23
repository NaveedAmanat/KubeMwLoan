package com.idev4.loans.service;

import com.idev4.loans.domain.MwThsl;
import com.idev4.loans.repository.MwThslRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwThsl.
 */
@Service
@Transactional
public class MwThslService {

    private final Logger log = LoggerFactory.getLogger(MwThslService.class);

    private final MwThslRepository mwThslRepository;

    public MwThslService(MwThslRepository mwThslRepository) {
        this.mwThslRepository = mwThslRepository;
    }

    /**
     * Save a mwThsl.
     *
     * @param mwThsl the entity to save
     * @return the persisted entity
     */
    public MwThsl save(MwThsl mwThsl) {
        log.debug("Request to save MwThsl : {}", mwThsl);
        return mwThslRepository.save(mwThsl);
    }

    /**
     * Get all the mwThsls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwThsl> findAll(Pageable pageable) {
        log.debug("Request to get all MwThsls");
        return mwThslRepository.findAll(pageable);
    }

    /**
     * Get one mwThsl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwThsl findOne(Long id) {
        log.debug("Request to get MwThsl : {}", id);
        return mwThslRepository.findOne(id);
    }

}
