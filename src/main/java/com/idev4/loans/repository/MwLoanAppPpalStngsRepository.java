package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppPpalStngs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface MwLoanAppPpalStngsRepository extends JpaRepository<MwLoanAppPpalStngs, Long> {

    public MwLoanAppPpalStngs findOneByLoanAppPpalStngsSeqAndPrdSeq(Long prdChrgSeq, long prdSeq);

    public MwLoanAppPpalStngs findOneByLoanAppPpalStngsSeq(Long prdChrgSeq);

    public MwLoanAppPpalStngs findOneByLoanAppSeq(Long loanAppSeq);

    public List<MwLoanAppPpalStngs> findAllByPrdSeq(Long prdSeq, boolean flag);

    public List<MwLoanAppPpalStngs> findAllByLoanAppSeq(Long loanAppSeq);
}
