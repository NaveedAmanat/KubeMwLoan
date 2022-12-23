package com.idev4.loans.service;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.domain.MwMfcibOthOutsdLoan;
import com.idev4.loans.dto.MfcibDto;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.repository.MwMfcibOthOutsdLoanRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing MwMfcibOthOutsdLoan.
 */
@Service
@Transactional
public class MwMfcibOthOutsdLoanService {

    private final Logger log = LoggerFactory.getLogger(MwMfcibOthOutsdLoanService.class);

    private final MwMfcibOthOutsdLoanRepository mwMfcibOthOutsdLoanRepository;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    public MwMfcibOthOutsdLoanService(MwMfcibOthOutsdLoanRepository mwMfcibOthOutsdLoanRepository, MwLoanAppRepository mwLoanAppRepository,
                                      MwClntRepository mwClntRepository) {
        this.mwMfcibOthOutsdLoanRepository = mwMfcibOthOutsdLoanRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * Save a mwMfcibOthOutsdLoan.
     *
     * @param mwMfcibOthOutsdLoan the entity to save
     * @return the persisted entity
     */
    public MwMfcibOthOutsdLoan save(MwMfcibOthOutsdLoan mwMfcibOthOutsdLoan) {
        log.debug("Request to save MwMfcibOthOutsdLoan : {}", mwMfcibOthOutsdLoan);
        return mwMfcibOthOutsdLoanRepository.save(mwMfcibOthOutsdLoan);
    }

    /**
     * Get all the mwMfcibOthOutsdLoans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwMfcibOthOutsdLoan> findAll(Pageable pageable) {
        log.debug("Request to get all MwMfcibOthOutsdLoans");
        return mwMfcibOthOutsdLoanRepository.findAll(pageable);
    }

    /**
     * Get one mwMfcibOthOutsdLoan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwMfcibOthOutsdLoan findOne(Long id) {
        log.debug("Request to get MwMfcibOthOutsdLoan : {}", id);
        return mwMfcibOthOutsdLoanRepository.findOne(id);
    }

    /**
     * Delete the mwMfcibOthOutsdLoan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwMfcibOthOutsdLoan : {}", id);
        MwMfcibOthOutsdLoan loan = mwMfcibOthOutsdLoanRepository.findOneByOthOutsdLoanSeqAndCrntRecFlg(id, true);
        if (loan == null)
            return;
        loan.setCrntRecFlg(false);
        loan.setDelFlg(true);
        loan.setEffEndDt(Instant.now());
        loan.setLastUpdBy("w-" + SecurityContextHolder.getContext().getAuthentication().getName());
        loan.setLastUpdDt(Instant.now());
        mwMfcibOthOutsdLoanRepository.save(loan);
    }

    @Transactional
    public long addNewUserToldMfcibLoan(MfcibDto dto, String currUser) {
        List<MwMfcibOthOutsdLoan> apps = mwMfcibOthOutsdLoanRepository.findAllByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        int length = 0;
        if (apps != null && apps.size() > 0)
            length = apps.size();
        MwLoanApp app = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);
        if (app != null) {
            MwClnt clnt = mwClntRepository.findOneByClntSeqAndCrntRecFlg(app.getClntSeq(), true);
            if (clnt != null) {
                long seq = Long.parseLong((Common.GenerateTableSequence(clnt.getCnicNum().toString(), TableNames.MW_MFCIB_OTH_OUTSD_LOAN,
                        app.getLoanCyclNum()) + "").concat(length + ""));
                MwMfcibOthOutsdLoan mfcib = new MwMfcibOthOutsdLoan();
                Instant currIns = Instant.now();
                mfcib.setOthOutsdLoanSeq(seq);
                mfcib.setCmplDt(dto.loanCompletionDate.toInstant());
                mfcib.setCrntOutsdAmt(dto.currentOutStandingAmount);
                mfcib.setCrntRecFlg(true);
                mfcib.setCrtdBy("w-" + currUser);
                mfcib.setCrtdDt(currIns);
                mfcib.setDelFlg(false);
                mfcib.setEffStartDt(currIns);
                mfcib.setInstnNm(dto.instituteName);
                mfcib.setLastUpdBy("w-" + currUser);
                mfcib.setLastUpdDt(currIns);
                mfcib.setLoanPrps(dto.loanPurpose);
                // mfcib.setMfcibFlg(true);
                mfcib.setMnthExpFlg(dto.isExpense);
                mfcib.setLoanAppSeq(dto.loanAppSeq);
                mfcib.setTotLoanAmt(dto.totalAmount);
                mfcib.setSyncFlg(true);
                Common.updateFormComplFlag(dto.formSeq, dto.loanAppSeq, currUser);

                return mwMfcibOthOutsdLoanRepository.save(mfcib).getOthOutsdLoanSeq();
            }
        }
        return 0;
    }

    public List<MfcibDto> getLoanAppMfcibs(long loanAppSeq) {

        List<MwMfcibOthOutsdLoan> mfcib = mwMfcibOthOutsdLoanRepository.findAllByLoanAppSeqAndCrntRecFlg(loanAppSeq, true);
        List<MfcibDto> dtos = new ArrayList<MfcibDto>();
        if (mfcib == null)
            return dtos;
        mfcib.forEach(fcib -> {
            MfcibDto dto = new MfcibDto();
            dto.loanAppSeq = fcib.getLoanAppSeq();
            dto.currentOutStandingAmount = fcib.getCrntOutsdAmt();
            dto.instituteName = fcib.getInstnNm();
            dto.loanPurpose = fcib.getLoanPrps();
            dto.isExpense = fcib.isMnthExpFlg();
            dto.totalAmount = fcib.getTotLoanAmt();
            dto.loanCompletionDate = Date.from(fcib.getCmplDt());
            dto.mfcibSeq = fcib.getOthOutsdLoanSeq();
            dtos.add(dto);
        });

        return dtos;

    }

    public long updateUserToldMfcibLoan(MfcibDto dto, String currUser) {
        MwMfcibOthOutsdLoan mwMfcib = mwMfcibOthOutsdLoanRepository.findOneByOthOutsdLoanSeqAndCrntRecFlg(dto.mfcibSeq, true);
        Instant currIns = Instant.now();
        if (mwMfcib == null)
            return 0;

        mwMfcib.setLastUpdDt(currIns);
        mwMfcib.setLastUpdBy(currUser);
        mwMfcib.setMnthExpFlg(dto.isExpense);
        mwMfcib.setTotLoanAmt(dto.totalAmount);
        mwMfcib.setLoanPrps(dto.loanPurpose);
        mwMfcib.setInstnNm(dto.instituteName);
        mwMfcib.setCmplDt(dto.loanCompletionDate.toInstant());
        mwMfcib.setCrntOutsdAmt(dto.currentOutStandingAmount);

        return mwMfcibOthOutsdLoanRepository.save(mwMfcib).getOthOutsdLoanSeq();

        // exMfcib.setCrntRecFlg( false );
        // exMfcib.setLastUpdBy( "w-" + currUser );
        // exMfcib.setLastUpdDt( currIns );
        // exMfcib.setEffEndDt( currIns );
        // mwMfcibOthOutsdLoanRepository.save( exMfcib );
        //
        // MwMfcibOthOutsdLoan mfcib = new MwMfcibOthOutsdLoan();
        //
        // mfcib.setOthOutsdLoanSeq( dto.mfcibSeq );
        // mfcib.setCmplDt( dto.loanCompletionDate.toInstant() );
        // mfcib.setCrntOutsdAmt( dto.currentOutStandingAmount );
        // mfcib.setCrntRecFlg( true );
        // mfcib.setCrtdBy( "w-" + currUser );
        // mfcib.setCrtdDt( currIns );
        // mfcib.setDelFlg( false );
        // mfcib.setEffStartDt( currIns );
        // mfcib.setInstnNm( dto.instituteName );
        // mfcib.setLastUpdBy( "w-" + currUser );
        // mfcib.setLastUpdDt( currIns );
        // mfcib.setLoanPrps( dto.loanPurpose );
        // // mfcib.setMfcibFlg(true);
        // mfcib.setMnthExpFlg( dto.isExpense );
        // mfcib.setLoanAppSeq( dto.loanAppSeq );
        // mfcib.setTotLoanAmt( dto.totalAmount );
        // mfcib.setCmplDt( dto.loanCompletionDate.toInstant() );
        // mfcib.setSyncFlg( true );
        // return mwMfcibOthOutsdLoanRepository.save( mfcib ).getOthOutsdLoanSeq();
    }
}
