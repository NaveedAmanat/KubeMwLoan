package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPrdAdvRul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwPrdAdvRul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPrdAdvRulRepository extends JpaRepository<MwPrdAdvRul, Long> {

    public List<MwPrdAdvRul> findAllByCrntRecFlg(boolean flag);

    public MwPrdAdvRul findOneByPrdAdvRulSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwPrdAdvRul> findAllByPrdAdvRulSeq(long seq);

    public List<MwPrdAdvRul> findAllByPrdSeqAndCrntRecFlg(long prdSeq, boolean falg);

}

