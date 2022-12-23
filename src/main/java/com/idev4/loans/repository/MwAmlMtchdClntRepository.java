package com.idev4.loans.repository;

import com.idev4.loans.domain.MwAmlMtchdClnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*** Spring Data JPA repository for the MwAmlMtchdClnt entity. */

@SuppressWarnings("unused")
@Repository
public interface MwAmlMtchdClntRepository extends JpaRepository<MwAmlMtchdClnt, Long> {

    public MwAmlMtchdClnt findOneByLoanAppSeq(long loanAppSeq);

    public List<MwAmlMtchdClnt> findAllByLoanAppSeq(long loanAppSeq);

    public List<MwAmlMtchdClnt> findAllByLoanAppSeqAndClntSrcFlgNot(long loanAppSeq, int clntSrcFlg);

    public List<MwAmlMtchdClnt> findAllByLoanAppSeqAndStpFlg(long loanAppSeq, boolean stpFlag);
}
