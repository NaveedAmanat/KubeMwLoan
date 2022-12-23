package com.idev4.loans.service;

import com.idev4.loans.domain.MwForm;
import com.idev4.loans.repository.MwFormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MwForm.
 */
@Service
@Transactional
public class MwFormService {

    private final Logger log = LoggerFactory.getLogger(MwFormService.class);

    private final MwFormRepository mwFormRepository;

    public MwFormService(MwFormRepository mwFormRepository) {
        this.mwFormRepository = mwFormRepository;
    }

    /**
     * Save a mwForm.
     *
     * @param mwForm the entity to save
     * @return the persisted entity
     */
    public MwForm save(MwForm mwForm) {
        log.debug("Request to save MwForm : {}", mwForm);
        return mwFormRepository.save(mwForm);
    }

    /**
     * Get all the mwForms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwForm> findAll(Pageable pageable) {
        log.debug("Request to get all MwForms");
        return mwFormRepository.findAll(pageable);
    }

    /**
     * Get one mwForm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MwForm> findOne(Long id) {
        log.debug("Request to get MwForm : {}", id);
        return Optional.of(mwFormRepository.findOne(id));
    }

}
