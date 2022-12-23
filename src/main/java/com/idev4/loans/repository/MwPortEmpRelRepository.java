package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPortEmpRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwPortEmpRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPortEmpRelRepository extends JpaRepository<MwPortEmpRel, Long> {

    public List<MwPortEmpRel> findAllByPortSeqAndCrntRecFlg(long portSeq, boolean flag);

    public MwPortEmpRel findOneByPortEmpSeqAndCrntRecFlg(long seq, boolean flag);

    public MwPortEmpRel findOneByPortSeqAndCrntRecFlg(long portSeq, boolean flag);
}
