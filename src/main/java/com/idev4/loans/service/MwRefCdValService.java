package com.idev4.loans.service;

import com.idev4.loans.domain.MwRefCdVal;
import com.idev4.loans.repository.MwRefCdValRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwRefCdVal.
 */
@Service
@Transactional
public class MwRefCdValService {

    private final Logger log = LoggerFactory.getLogger(MwRefCdValService.class);

    private final MwRefCdValRepository mwRefCdValRepository;

    public MwRefCdValService(MwRefCdValRepository mwRefCdValRepository) {
        this.mwRefCdValRepository = mwRefCdValRepository;
    }

    /**
     * Save a mwRefCdVal.
     *
     * @param mwRefCdVal the entity to save
     * @return the persisted entity
     */
    public MwRefCdVal save(MwRefCdVal mwRefCdVal) {
        log.debug("Request to save MwRefCdVal : {}", mwRefCdVal);
        return mwRefCdValRepository.save(mwRefCdVal);
    }

    /**
     * Get all the mwRefCdVals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwRefCdVal> findAll(Pageable pageable) {
        log.debug("Request to get all MwRefCdVals");
        return mwRefCdValRepository.findAll(pageable);
    }

    /**
     * Get one mwRefCdVal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwRefCdVal findOne(Long id) {
        log.debug("Request to get MwRefCdVal : {}", id);
        return mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(id, true);
    }

}
