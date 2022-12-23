package com.idev4.loans.repository;

import com.idev4.loans.domain.MwRul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface MwRulRepository extends JpaRepository<MwRul, Long> {

    public MwRul findOneByRulSeqAndCrntRecFlg(Long rulSeq, boolean flag);

    public List<MwRul> findAllByRulSeq(Long rulSeq);

    public List<MwRul> findAllByCrntRecFlg(boolean flag);

    public List<MwRul> findAllByRulCtgryKeyAndCrntRecFlg(Long ctgrySeq, boolean flag);

}

