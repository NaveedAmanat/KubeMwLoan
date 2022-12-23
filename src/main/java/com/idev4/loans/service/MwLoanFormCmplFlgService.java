package com.idev4.loans.service;

import com.idev4.loans.domain.MwLoanFormCmplFlg;
import com.idev4.loans.repository.MwLoanFormCmplFlgRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MwLoanFormCmplFlg.
 */
@Service
@Transactional
public class MwLoanFormCmplFlgService {

    private final Logger log = LoggerFactory.getLogger(MwLoanFormCmplFlgService.class);

    private final MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository;

    public MwLoanFormCmplFlgService(MwLoanFormCmplFlgRepository mwLoanFormCmplFlgRepository) {
        this.mwLoanFormCmplFlgRepository = mwLoanFormCmplFlgRepository;
    }

    /**
     * Save a mwLoanFormCmplFlg.
     *
     * @param mwLoanFormCmplFlg the entity to save
     * @return the persisted entity
     */
    public MwLoanFormCmplFlg save(MwLoanFormCmplFlg mwLoanFormCmplFlg) {
        log.debug("Request to save MwLoanFormCmplFlg : {}", mwLoanFormCmplFlg);
        return mwLoanFormCmplFlgRepository.save(mwLoanFormCmplFlg);
    }

    /**
     * Get all the mwLoanFormCmplFlgs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwLoanFormCmplFlg> findAll(Pageable pageable) {
        log.debug("Request to get all MwLoanFormCmplFlgs");
        return mwLoanFormCmplFlgRepository.findAll(pageable);
    }

    /**
     * Get one mwLoanFormCmplFlg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MwLoanFormCmplFlg> findOne(Long id) {
        log.debug("Request to get MwLoanFormCmplFlg : {}", id);
        return Optional.of(mwLoanFormCmplFlgRepository.findOne(id));
    }

}
