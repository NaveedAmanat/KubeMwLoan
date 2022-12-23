package com.idev4.loans.service;

import com.idev4.loans.domain.MwBizExpDtl;
import com.idev4.loans.dto.BizExpDto;
import com.idev4.loans.repository.MwBizExpDtlRepository;
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
 * Service Implementation for managing MwBizExpDtl.
 */
@Service
@Transactional
public class MwBizExpDtlService {

    private final Logger log = LoggerFactory.getLogger(MwBizExpDtlService.class);

    private final MwBizExpDtlRepository mwBizExpDtlRepository;

    public MwBizExpDtlService(MwBizExpDtlRepository mwBizExpDtlRepository) {
        this.mwBizExpDtlRepository = mwBizExpDtlRepository;
    }

    /**
     * Save a mwBizExpDtl.
     *
     * @param mwBizExpDtl the entity to save
     * @return the persisted entity
     */
    public MwBizExpDtl save(MwBizExpDtl mwBizExpDtl) {
        log.debug("Request to save MwBizExpDtl : {}", mwBizExpDtl);
        return mwBizExpDtlRepository.save(mwBizExpDtl);
    }

    /**
     * Get all the mwBizExpDtls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBizExpDtl> findAll(Pageable pageable) {
        log.debug("Request to get all MwBizExpDtls");
        return mwBizExpDtlRepository.findAll(pageable);
    }

    /**
     * Get one mwBizExpDtl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwBizExpDtl findOne(Long id) {
        log.debug("Request to get MwBizExpDtl : {}", id);
        return mwBizExpDtlRepository.findOne(id);
    }

    /**
     * Delete the mwBizExpDtl by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        MwBizExpDtl dtl = mwBizExpDtlRepository.findOneByExpDtlSeqAndCrntRecFlg(id, true);
        if (dtl != null) {
            dtl.setCrntRecFlg(false);
            dtl.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            dtl.setDelFlg(true);
            dtl.setLastUpdDt(Instant.now());
            dtl.setEffEndDt(Instant.now());
            mwBizExpDtlRepository.save(dtl);
        }
    }

    public long addClientExpenses(BizExpDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.BIZ_APRSL_EXP_SEQ);
        MwBizExpDtl bizExp = new MwBizExpDtl();
        Instant currIns = Instant.now();
        bizExp.setExpDtlSeq(seq);
        bizExp.setCrntRecFlg(true);
        bizExp.setCrtdBy("w-" + currUser);
        bizExp.setCrtdDt(currIns);
        bizExp.setDelFlg(false);
        bizExp.setEffStartDt(currIns);
        bizExp.setExpAmt(dto.expAmount);
        bizExp.setExpCtgryKey(dto.expCategoryKey);
        bizExp.setExpTypKey(dto.expTypKey);
        bizExp.setLastUpdBy("w-" + currUser);
        bizExp.setLastUpdDt(currIns);
        bizExp.mwBizAprsl = dto.bizAprslSeq;
        bizExp.setSyncFlg(true);
        return mwBizExpDtlRepository.save(bizExp).getExpDtlSeq();
    }

    @Transactional
    public long updateClientExpenses(BizExpDto dto, String currUser) {

        MwBizExpDtl exBizExp = mwBizExpDtlRepository.findOneByExpDtlSeqAndExpCtgryKeyAndMwBizAprslAndExpTypKeyAndCrntRecFlg
                (dto.bizExpSeq, dto.expCategoryKey, dto.bizAprslSeq, dto.expTypKey, true);
        Instant currIns = Instant.now();
        if (exBizExp == null)
            return 0;

        exBizExp.setLastUpdBy("w-" + currUser);
        exBizExp.setLastUpdDt(currIns);
        exBizExp.setExpAmt(dto.expAmount);
        exBizExp.setExpTypKey(dto.expTypKey);
        exBizExp.mwBizAprsl = dto.bizAprslSeq;
        return mwBizExpDtlRepository.save(exBizExp).getExpDtlSeq();

        // exBizExp.setLastUpdBy( "w-" + currUser );
        // exBizExp.setLastUpdDt( currIns );
        // exBizExp.setCrntRecFlg( false );
        // exBizExp.setEffEndDt( currIns );
        // mwBizExpDtlRepository.save( exBizExp );
        //
        // MwBizExpDtl bizExp = new MwBizExpDtl();
        //
        // bizExp.setExpDtlSeq( dto.bizExpSeq );
        // bizExp.setCrntRecFlg( true );
        // bizExp.setCrtdBy( "w-" + currUser );
        // bizExp.setCrtdDt( currIns );
        // bizExp.setDelFlg( false );
        // bizExp.setEffStartDt( currIns );
        // bizExp.setExpAmt( dto.expAmount );
        // /// bizExp.setExpCtgryKey(dto.expCategoryKey);
        // bizExp.setExpTypKey( dto.expTypKey );
        // bizExp.setLastUpdBy( "w-" + currUser );
        // bizExp.setLastUpdDt( currIns );
        // bizExp.mwBizAprsl = dto.bizAprslSeq;
        // bizExp.setSyncFlg( true );
        // return mwBizExpDtlRepository.save( bizExp ).getExpDtlSeq();
    }

    @Transactional
    public void deleteAllByLoanAppSeq(long loanAppSeq, long bizAppSeq) {

        // List<MwBizExpDtl> items = mwBizExpDtlRepository.findAllByLoanAppSequenceAndBizAprslSeq(loanAppSeq, bizAppSeq);

        // mwBizExpDtlRepository.deleteInBatch(items);
    }
}
