package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPrdSgrtInst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface MwPrdSgrtInstRepository extends JpaRepository<MwPrdSgrtInst, Long> {

    public MwPrdSgrtInst findOneByPrdSgrtInstSeqAndCrntRecFlg(Long prdSgrtInstSeq, boolean flag);

    public MwPrdSgrtInst findOneByPrdSgrtInstSeq(Long prdSgrtInstSeq);

    public List<MwPrdSgrtInst> findAllByPrdSgrtInstSeq(Long prdSgrtInstSeq);

    public List<MwPrdSgrtInst> findAllBySgrtEntySeqAndEntyTypStrAndCrntRecFlg(Long sgrtEntySeq, String entyTypStr, boolean flag);

    public List<MwPrdSgrtInst> findAllByCrntRecFlg(boolean flag);

    public List<MwPrdSgrtInst> findAllBySgrtEntySeqAndCrntRecFlg(Long sgrtEntySeq, boolean flag);
}

