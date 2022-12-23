package com.idev4.loans.repository;

import com.idev4.loans.domain.MwHmAprsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MwHmAprslRepository extends JpaRepository<MwHmAprsl, Long> {

    public List<MwHmAprsl> findAllByCrntRecFlg(boolean flag);

    public MwHmAprsl findOneByHmAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public MwHmAprsl findOneByBizAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public MwHmAprsl findOneByHmAprslSeqAndCrntRecFlgAndBizAprslSeq(long seq, boolean flag, long seq1);

    public MwHmAprsl findOneByHmAprslSeqAndBizAprslSeqAndCrntRecFlg(long hmseq, long bizseq, boolean flag);

    public List<MwHmAprsl> findAllByHmAprslSeq(long seq);
}
