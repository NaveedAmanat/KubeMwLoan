package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppChrgStngs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface MwLoanAppChrgStngsRepository extends JpaRepository<MwLoanAppChrgStngs, Long> {

    public MwLoanAppChrgStngs findOneByLoanAppChrgStngsSeqAndPrdSeq(Long prdChrgSeq, long prdSeq);

    public MwLoanAppChrgStngs findOneByLoanAppChrgStngsSeq(Long prdChrgSeq);

    public List<MwLoanAppChrgStngs> findAllByPrdSeq(Long prdSeq, boolean flag);

    public List<MwLoanAppChrgStngs> findAllByLoanAppSeq(Long loanAppSeq);

    public MwLoanAppChrgStngs findOneByLoanAppSeqAndTypSeq(long loanAppSeq, long typSeq);

    public MwLoanAppChrgStngs findOneByLoanAppSeqAndPrdChrgSeq(long loanAppSeq, long prdChrgSeq);
}

