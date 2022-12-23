package com.idev4.loans.service;

import com.idev4.loans.domain.MwBizAprslIncmHdr;
import com.idev4.loans.dto.IncomeHdrDto;
import com.idev4.loans.repository.MwBizAprslIncmHdrRepository;
import com.idev4.loans.repository.MwBizAprslRepository;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing MwBizAprslIncmHdr.
 */
@Service
@Transactional
public class MwBizAprslIncmHdrService {

    private final Logger log = LoggerFactory.getLogger(MwBizAprslIncmHdrService.class);

    private final MwBizAprslIncmHdrRepository mwBizAprslIncmHdrRepository;

    private final MwBizAprslRepository mwBizAprslRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    public MwBizAprslIncmHdrService(MwBizAprslIncmHdrRepository mwBizAprslIncmHdrRepository, MwLoanAppRepository mwLoanAppRepository,
                                    MwClntRepository mwClntRepository, MwBizAprslRepository mwBizAprslRepository) {
        this.mwBizAprslIncmHdrRepository = mwBizAprslIncmHdrRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
        this.mwBizAprslRepository = mwBizAprslRepository;
    }

    /**
     * Save a mwBizAprslIncmHdr.
     *
     * @param mwBizAprslIncmHdr the entity to save
     * @return the persisted entity
     */
    public MwBizAprslIncmHdr save(MwBizAprslIncmHdr mwBizAprslIncmHdr) {
        log.debug("Request to save MwBizAprslIncmHdr : {}", mwBizAprslIncmHdr);
        return mwBizAprslIncmHdrRepository.save(mwBizAprslIncmHdr);
    }

    /**
     * Get all the mwBizAprslIncmHdrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBizAprslIncmHdr> findAll(Pageable pageable) {
        log.debug("Request to get all MwBizAprslIncmHdrs");
        return mwBizAprslIncmHdrRepository.findAll(pageable);
    }

    /**
     * Get one mwBizAprslIncmHdr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwBizAprslIncmHdr findOne(Long id) {
        log.debug("Request to get MwBizAprslIncmHdr : {}", id);
        return mwBizAprslIncmHdrRepository.findOneByIncmHdrSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwBizAprslIncmHdr by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        MwBizAprslIncmHdr hdr = mwBizAprslIncmHdrRepository.findOneByIncmHdrSeqAndCrntRecFlg(id, true);
        if (hdr != null) {
            hdr.setCrntRecFlg(false);
            hdr.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            hdr.setDelFlg(true);
            hdr.setLastUpdDt(Instant.now());
            hdr.setEffEndDt(Instant.now());
            mwBizAprslIncmHdrRepository.save(hdr);
        }
    }

    public long saveBusinessIncomeHdr(IncomeHdrDto dto, String currUser) {
        MwBizAprslIncmHdr hdr = mwBizAprslIncmHdrRepository.findOneByMwBizAprslAndCrntRecFlg(dto.bizAprslSeq, true);

        if (hdr != null)
            return hdr.getIncmHdrSeq();
        long seq = Common.GenerateTableSequence(dto.cnicNum.toString(), TableNames.MW_BIZ_APRSL_INCM_HDR, dto.cycleNum);

        MwBizAprslIncmHdr bizIncome = new MwBizAprslIncmHdr();
        Instant currIns = Instant.now();
        bizIncome.setIncmHdrSeq(seq);
        bizIncome.setCrntRecFlg(true);
        bizIncome.setCrtdBy(currUser);
        bizIncome.setCrtdDt(currIns);
        bizIncome.setDelFlg(false);
        bizIncome.setEffStartDt(currIns);
        bizIncome.setLastUpdBy("w-" + currUser);
        bizIncome.setLastUpdDt(currIns);
        bizIncome.setMaxMnthSalAmt(dto.maxMonthSale);
        bizIncome.setMaxSalNumOfMnth(dto.maxSaleMonth);
        bizIncome.setMinMnthSalAmt(dto.minMonthSale);
        bizIncome.setMinSalNumOfMnth(dto.minSaleMonth);
        bizIncome.mwBizAprsl = dto.bizAprslSeq;
        bizIncome.setSyncFlg(true);
        return mwBizAprslIncmHdrRepository.save(bizIncome).getIncmHdrSeq();
    }

    public long updateBusinessIncomeHdr(IncomeHdrDto dto, String currUser) {
        MwBizAprslIncmHdr mwBizIncome = mwBizAprslIncmHdrRepository.findOneByIncmHdrSeqAndCrntRecFlg(dto.incomeHdrSeq, true);
        Instant currIns = Instant.now();
        if (mwBizIncome == null)
            return saveBusinessIncomeHdr(dto, currUser);

        mwBizIncome.setLastUpdBy(currUser);
        mwBizIncome.setLastUpdDt(currIns);
        mwBizIncome.setMaxMnthSalAmt(dto.maxMonthSale);
        mwBizIncome.setMaxSalNumOfMnth(dto.maxSaleMonth);
        mwBizIncome.setMinMnthSalAmt(dto.minMonthSale);
        mwBizIncome.setMinSalNumOfMnth(dto.minSaleMonth);
        mwBizIncome.mwBizAprsl = dto.bizAprslSeq;
        mwBizIncome.setSyncFlg(true);

        // exBizIncome.setLastUpdBy( "w-" + currUser );
        // exBizIncome.setLastUpdDt( currIns );
        // exBizIncome.setCrntRecFlg( false );
        // exBizIncome.setEffEndDt( currIns );
        // mwBizAprslIncmHdrRepository.save( exBizIncome );
        // MwBizAprslIncmHdr bizIncome = new MwBizAprslIncmHdr();
        //
        // bizIncome.setIncmHdrSeq( dto.incomeHdrSeq );
        // bizIncome.setCrntRecFlg( true );
        // bizIncome.setCrtdBy( "w-" + currUser );
        // bizIncome.setCrtdDt( currIns );
        // bizIncome.setDelFlg( false );
        // bizIncome.setEffStartDt( currIns );
        // bizIncome.setLastUpdBy( "w-" + currUser );
        // bizIncome.setLastUpdDt( currIns );
        // bizIncome.setMaxMnthSalAmt( dto.maxMonthSale );
        // bizIncome.setMaxSalNumOfMnth( dto.maxSaleMonth );
        // bizIncome.setMinMnthSalAmt( dto.minMonthSale );
        // bizIncome.setMinSalNumOfMnth( dto.minSaleMonth );
        // bizIncome.mwBizAprsl = dto.bizAprslSeq;
        // bizIncome.setSyncFlg( true );
        return mwBizAprslIncmHdrRepository.save(mwBizIncome).getIncmHdrSeq();
    }
}
