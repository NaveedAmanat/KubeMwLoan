package com.idev4.loans.repository;

import com.idev4.loans.domain.MwBrnchEmpRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwBrnchEmpRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBrnchEmpRelRepository extends JpaRepository<MwBrnchEmpRel, Long> {

    public List<MwBrnchEmpRel> findAllByBrnchSeqAndCrntRecFlg(long brnchSeq, boolean flag);

    public MwBrnchEmpRel findOneByBrnchEmpSeqAndCrntRecFlg(long seq, boolean flag);

    public MwBrnchEmpRel findOneByBrnchSeqAndCrntRecFlg(long brnchSeq, boolean flag);

    public MwBrnchEmpRel findOneByBrnchSeqAndCrntRecFlgAndDelFlg(long brnchSeq, boolean flag, boolean dflg);
}
