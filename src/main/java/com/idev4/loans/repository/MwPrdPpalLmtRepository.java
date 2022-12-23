package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPrdPpalLmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface MwPrdPpalLmtRepository extends JpaRepository<MwPrdPpalLmt, Long> {

    public MwPrdPpalLmt findOneByPrdPpalLmtSeqAndCrntRecFlg(Long prdPpalLmtSeq, boolean flag);

    public MwPrdPpalLmt findOneByPrdSeqAndRulSeqAndCrntRecFlg(Long prdSeq, Long rulSeq, boolean flag);

    public MwPrdPpalLmt findOneByPrdPpalLmtSeq(Long prdPpalLmtSeq);

    public List<MwPrdPpalLmt> findAllByPrdPpalLmtSeq(Long prdTrmSeq);

    public List<MwPrdPpalLmt> findAllByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwPrdPpalLmt> findAllByPrdSeqAndCrntRecFlgOrderByPrdPpalLmtSeq(Long prdSeq, boolean flag);

    public List<MwPrdPpalLmt> findAllByCrntRecFlg(boolean flag);

}

