package com.idev4.loans.service;

import com.idev4.loans.domain.MwDsbmtHdr;
import com.idev4.loans.repository.MwDsbmtHdrRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwDsbmtHdr.
 */
@Service
@Transactional
public class MwDsbmtHdrService {

    private final Logger log = LoggerFactory.getLogger(MwDsbmtHdrService.class);

    private final MwDsbmtHdrRepository mwDsbmtHdrRepository;

    public MwDsbmtHdrService(MwDsbmtHdrRepository mwDsbmtHdrRepository) {
        this.mwDsbmtHdrRepository = mwDsbmtHdrRepository;
    }

    /**
     * Save a mwDsbmtHdr.
     *
     * @param mwDsbmtHdr the entity to save
     * @return the persisted entity
     */
    public MwDsbmtHdr save(MwDsbmtHdr mwDsbmtHdr) {
        log.debug("Request to save MwDsbmtHdr : {}", mwDsbmtHdr);
        return mwDsbmtHdrRepository.save(mwDsbmtHdr);
    }

    /**
     * Get all the mwDsbmtHdrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwDsbmtHdr> findAll(Pageable pageable) {
        log.debug("Request to get all MwDsbmtHdrs");
        return mwDsbmtHdrRepository.findAll(pageable);
    }

    /**
     * Get one mwDsbmtHdr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwDsbmtHdr findOne(Long id) {
        log.debug("Request to get MwDsbmtHdr : {}", id);
        return mwDsbmtHdrRepository.findOne(id);
    }

}
