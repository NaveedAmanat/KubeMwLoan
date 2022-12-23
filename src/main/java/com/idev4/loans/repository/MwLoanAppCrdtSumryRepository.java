package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppCrdtSumry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MwLoanAppCrdtSumryRepository extends JpaRepository<MwLoanAppCrdtSumry, Long> {

    public List<MwLoanAppCrdtSumry> findAllByLoanAppDocSeqAndCrntRecFlg(Long loanAppDocSeq, Boolean crntRecFlg);
}
