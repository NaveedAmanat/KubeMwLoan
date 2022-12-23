package com.idev4.loans.service;

import com.idev4.loans.domain.MwPrdFormRel;
import com.idev4.loans.repository.MwPrdFormRelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MwPrdFormRel.
 */
@Service
@Transactional
public class MwPrdFormRelService {

    private final Logger log = LoggerFactory.getLogger(MwPrdFormRelService.class);

    private final MwPrdFormRelRepository mwPrdFormRelRepository;

    public MwPrdFormRelService(MwPrdFormRelRepository mwPrdFormRelRepository) {
        this.mwPrdFormRelRepository = mwPrdFormRelRepository;
    }

    /**
     * Save a mwPrdFormRel.
     *
     * @param mwPrdFormRel the entity to save
     * @return the persisted entity
     */
    public MwPrdFormRel save(MwPrdFormRel mwPrdFormRel) {
        log.debug("Request to save MwPrdFormRel : {}", mwPrdFormRel);
        return mwPrdFormRelRepository.save(mwPrdFormRel);
    }

    /**
     * Get all the mwPrdFormRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwPrdFormRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwPrdFormRels");
        return mwPrdFormRelRepository.findAll(pageable);
    }

    /**
     * Get one mwPrdFormRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MwPrdFormRel> findOne(Long id) {
        log.debug("Request to get MwPrdFormRel : {}", id);
        return Optional.of(mwPrdFormRelRepository.findOne(id));
    }

}
