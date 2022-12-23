package com.idev4.loans.service;

import com.idev4.loans.domain.MwBizAprslIncmDtl;
import com.idev4.loans.dto.IncomeDtlDto;
import com.idev4.loans.repository.MwBizAprslIncmDtlRepository;
import com.idev4.loans.web.rest.util.SequenceFinder;
import com.idev4.loans.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing MwBizAprslIncmDtl.
 */
@Service
@Transactional
public class MwBizAprslIncmDtlService {

    private final Logger log = LoggerFactory.getLogger(MwBizAprslIncmDtlService.class);

    private final MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository;

    public MwBizAprslIncmDtlService(MwBizAprslIncmDtlRepository mwBizAprslIncmDtlRepository) {
        this.mwBizAprslIncmDtlRepository = mwBizAprslIncmDtlRepository;
    }

    /**
     * Save a mwBizAprslIncmDtl.
     *
     * @param mwBizAprslIncmDtl the entity to save
     * @return the persisted entity
     */
    public MwBizAprslIncmDtl save(MwBizAprslIncmDtl mwBizAprslIncmDtl) {
        log.debug("Request to save MwBizAprslIncmDtl : {}", mwBizAprslIncmDtl);
        return mwBizAprslIncmDtlRepository.save(mwBizAprslIncmDtl);
    }

    /**
     * Get all the mwBizAprslIncmDtls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBizAprslIncmDtl> findAll(Pageable pageable) {
        log.debug("Request to get all MwBizAprslIncmDtls");
        return mwBizAprslIncmDtlRepository.findAll(pageable);
    }

    /**
     * Get one mwBizAprslIncmDtl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwBizAprslIncmDtl findOne(Long id) {
        log.debug("Request to get MwBizAprslIncmDtl : {}", id);
        return mwBizAprslIncmDtlRepository.findOne(id);
    }

    /**
     * Delete the mwBizAprslIncmDtl by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        MwBizAprslIncmDtl dtl = mwBizAprslIncmDtlRepository.findOneByIncmDtlSeqAndCrntRecFlg(id, true);
        if (dtl != null) {
            dtl.setCrntRecFlg(false);
            dtl.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dtl.setDelFlg(true);
            dtl.setLastUpdDt(Instant.now());
            dtl.setEffEndDt(Instant.now());
            mwBizAprslIncmDtlRepository.save(dtl);
        }
    }

    public long addBusinessIncomeDetails(IncomeDtlDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.BIZ_APRSL_INCM_DTL_SEQ);

        MwBizAprslIncmDtl incomeDtl = new MwBizAprslIncmDtl();
        Instant currIns = Instant.now();
        incomeDtl.setIncmDtlSeq(seq);
        incomeDtl.setCrntRecFlg(true);
        incomeDtl.setCrtdBy("w-" + currUser);
        incomeDtl.setCrtdDt(currIns);
        incomeDtl.setDelFlg(false);
        incomeDtl.setEffStartDt(currIns);
        incomeDtl.setIncmAmt(dto.incomeAmount);
        incomeDtl.setIncmCtgryKey(dto.incomeCategoryKey);
        incomeDtl.setIncmTypKey(dto.incomeTypeKey);
        incomeDtl.setLastUpdBy("w-" + currUser);
        incomeDtl.setLastUpdDt(currIns);
        incomeDtl.mwBizAprslIncmHdr = Long.parseLong(dto.IncomeHdrSeq);
        incomeDtl.setSyncFlg(true);
        return mwBizAprslIncmDtlRepository.save(incomeDtl).getIncmDtlSeq();
    }

    @Transactional
    public long updateBusinessIncomeDetails(IncomeDtlDto dto, String currUser) {
        log.info("Update Business Appraisal Income Detail. IncomeDtlSeq:" + dto.incomeDtlSeq + ", dto.IncomeHdrSeq:" + dto.IncomeHdrSeq);
        MwBizAprslIncmDtl exIncomeDtl = mwBizAprslIncmDtlRepository.findOneByIncmDtlSeqAndCrntRecFlg(Long.parseLong(dto.incomeDtlSeq), true);
        Instant currIns = Instant.now();
        if (exIncomeDtl == null)
            return 0;

        exIncomeDtl.setLastUpdDt(currIns);
        exIncomeDtl.setLastUpdBy("w-" + currUser);
        exIncomeDtl.setIncmTypKey(dto.incomeTypeKey);
        exIncomeDtl.setIncmAmt(dto.incomeAmount);
        exIncomeDtl.mwBizAprslIncmHdr = Long.parseLong(dto.IncomeHdrSeq);
        return mwBizAprslIncmDtlRepository.save(exIncomeDtl).getIncmDtlSeq();

    }
}
