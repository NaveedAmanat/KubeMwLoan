package com.idev4.loans.service;

import com.idev4.loans.domain.MwAddrRel;
import com.idev4.loans.repository.MwAddrRelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing MwAddrRel.
 */
@Service
@Transactional
public class MwAddrRelService {

    private final Logger log = LoggerFactory.getLogger(MwAddrRelService.class);

    private final MwAddrRelRepository mwAddrRelRepository;

    public MwAddrRelService(MwAddrRelRepository mwAddrRelRepository) {
        this.mwAddrRelRepository = mwAddrRelRepository;
    }

    /**
     * Save a mwAddrRel.
     *
     * @param mwAddrRel the entity to save
     * @return the persisted entity
     */
    public MwAddrRel save(MwAddrRel mwAddrRel) {
        log.debug("Request to save MwAddrRel : {}", mwAddrRel);
        return mwAddrRelRepository.save(mwAddrRel);
    }

    /**
     * Get all the mwAddrRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwAddrRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwAddrRels");
        return mwAddrRelRepository.findAll(pageable);
    }

    /**
     * Get one mwAddrRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwAddrRel findOne(Long id) {
        log.debug("Request to get MwAddrRel : {}", id);
        return mwAddrRelRepository.findOne(id);
    }

    /**
     * Delete the mwAddrRel by id.
     *
     * @param id the id of the entity
     */
    public void deleteByEntity(MwAddrRel mwAddrRel) {
        log.debug("Request to delete MwAddrRel : {}", mwAddrRel.getAddrRelSeq());
        mwAddrRel.setCrntRecFlg(false);
        mwAddrRel.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        mwAddrRel.setDelFlg(true);
        mwAddrRel.setLastUpdDt(Instant.now());
        mwAddrRel.setEffEndDt(Instant.now());
        mwAddrRelRepository.save(mwAddrRel);
    }

//    public MwAddrRel getAddressRelationByEntity( long entySeq ) {
//        return mwAddrRelRepository.findOneByEntySeq( entySeq );
//    }

    public MwAddrRel getAddressRelationByEntityKeyAndEntyTyp(long entySeq, String entyTyp) {
        return mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(entySeq, entyTyp, true);
    }
}
