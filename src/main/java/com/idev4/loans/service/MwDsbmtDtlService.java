package com.idev4.loans.service;

import com.idev4.loans.domain.MwDsbmtDtl;
import com.idev4.loans.repository.MwDsbmtDtlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwDsbmtDtl.
 */
@Service
@Transactional
public class MwDsbmtDtlService {

    private final Logger log = LoggerFactory.getLogger(MwDsbmtDtlService.class);

    private final MwDsbmtDtlRepository mwDsbmtDtlRepository;

    public MwDsbmtDtlService(MwDsbmtDtlRepository mwDsbmtDtlRepository) {
        this.mwDsbmtDtlRepository = mwDsbmtDtlRepository;
    }

    /**
     * Save a mwDsbmtDtl.
     *
     * @param mwDsbmtDtl the entity to save
     * @return the persisted entity
     */
    public MwDsbmtDtl save(MwDsbmtDtl mwDsbmtDtl) {
        log.debug("Request to save MwDsbmtDtl : {}", mwDsbmtDtl);
        return mwDsbmtDtlRepository.save(mwDsbmtDtl);
    }

    /**
     * Get all the mwDsbmtDtls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwDsbmtDtl> findAll(Pageable pageable) {
        log.debug("Request to get all MwDsbmtDtls");
        return mwDsbmtDtlRepository.findAll(pageable);
    }

    /**
     * Get one mwDsbmtDtl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwDsbmtDtl findOne(Long id) {
        log.debug("Request to get MwDsbmtDtl : {}", id);
        return mwDsbmtDtlRepository.findOne(id);
    }

}
