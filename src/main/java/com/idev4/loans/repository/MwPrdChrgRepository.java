package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPrdChrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwPrdChrgRepository extends JpaRepository<MwPrdChrg, Long> {

    public MwPrdChrg findOneByPrdChrgSeqAndCrntRecFlgAndDelFlg(Long prdChrgSeq, boolean flag, boolean delFlag);

    public MwPrdChrg findOneByPrdChrgSeq(Long prdChrgSeq);

    public List<MwPrdChrg> findAllByPrdChrgSeq(Long prdChrgSeq);

    public List<MwPrdChrg> findAllByPrdSeqAndCrntRecFlgAndDelFlg(Long prdSeq, boolean flag, boolean delFlag);

    public List<MwPrdChrg> findAllByCrntRecFlg(boolean flag);

}
