package com.idev4.loans.service;

import com.idev4.loans.domain.MwCity;
import com.idev4.loans.repository.MwCityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MwCity.
 */
@Service
@Transactional
public class MwCityService {

    private final Logger log = LoggerFactory.getLogger(MwCityService.class);

    private final MwCityRepository mwCityRepository;

    public MwCityService(MwCityRepository mwCityRepository) {
        this.mwCityRepository = mwCityRepository;
    }

    /**
     * Save a mwCity.
     *
     * @param mwCity the entity to save
     * @return the persisted entity
     */
    public MwCity save(MwCity mwCity) {
        log.debug("Request to save MwCity : {}", mwCity);
        return mwCityRepository.save(mwCity);
    }

    /**
     * Get all the mwCities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCity> findAll(Pageable pageable) {
        log.debug("Request to get all MwCities");
        return mwCityRepository.findAll(pageable);
    }

    /**
     * Get one mwCity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCity findOne(Long id) {
        log.debug("Request to get MwCity : {}", id);
        return mwCityRepository.findOne(id);
    }

}
