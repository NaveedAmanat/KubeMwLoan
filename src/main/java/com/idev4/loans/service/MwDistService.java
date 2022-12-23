package com.idev4.loans.service;

import com.idev4.loans.domain.MwDist;
import com.idev4.loans.repository.MwDistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwDist.
 */
@Service
@Transactional
public class MwDistService {

    private final Logger log = LoggerFactory.getLogger(MwDistService.class);

    private final MwDistRepository mwDistRepository;

    public MwDistService(MwDistRepository mwDistRepository) {
        this.mwDistRepository = mwDistRepository;
    }

    /**
     * Save a mwDist.
     *
     * @param mwDist the entity to save
     * @return the persisted entity
     */
    public MwDist save(MwDist mwDist) {
        log.debug("Request to save MwDist : {}", mwDist);
        return mwDistRepository.save(mwDist);
    }

    /**
     * Get all the mwDists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwDist> findAll(Pageable pageable) {
        log.debug("Request to get all MwDists");
        return mwDistRepository.findAll(pageable);
    }

    /**
     * Get one mwDist by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwDist findOne(Long id) {
        log.debug("Request to get MwDist : {}", id);
        return mwDistRepository.findOne(id);
    }

}
