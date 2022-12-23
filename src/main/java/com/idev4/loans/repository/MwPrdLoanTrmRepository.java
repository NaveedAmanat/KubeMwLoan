package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPrdLoanTrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface MwPrdLoanTrmRepository extends JpaRepository<MwPrdLoanTrm, Long> {

    public MwPrdLoanTrm findOneByPrdTrmSeqAndCrntRecFlg(Long prdTrmSeq, boolean flag);

    public MwPrdLoanTrm findOneByPrdTrmSeq(Long prdTrmSeq);

    public List<MwPrdLoanTrm> findAllByPrdTrmSeq(Long prdTrmSeq);

    public MwPrdLoanTrm findOneByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public MwPrdLoanTrm findOneByPrdSeqAndRulSeqAndCrntRecFlg(Long prdSeq, Long seq, boolean flag);

    public List<MwPrdLoanTrm> findAllByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwPrdLoanTrm> findAllByCrntRecFlg(boolean flag);


}

