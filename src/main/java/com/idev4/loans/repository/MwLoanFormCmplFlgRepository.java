package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanFormCmplFlg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the MwLoanFormCmplFlg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwLoanFormCmplFlgRepository extends JpaRepository<MwLoanFormCmplFlg, Long> {

    public List<MwLoanFormCmplFlg> findAllByLoanAppSeqAndCrntRecFlg(long appSeq, boolean flag);

    public MwLoanFormCmplFlg findOneByFormSeqAndLoanAppSeqAndCrntRecFlg(long formSeq, long loanSeq, boolean flag);
}
