package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppMfcibData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MwLoanAppMfcibDataRepository extends JpaRepository<MwLoanAppMfcibData, Long> {

    List<MwLoanAppMfcibData> findAllByLoanAppDocSeqAndCrntRecFlg(Long loanAppSeq, boolean crntRecFlg);
}
