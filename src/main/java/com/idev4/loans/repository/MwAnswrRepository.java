package com.idev4.loans.repository;

import com.idev4.loans.domain.MwAnswr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwAnswr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAnswrRepository extends JpaRepository<MwAnswr, Long> {

    public List<MwAnswr> findAllByDelFlgAndCrntRecFlg(boolean delflag, boolean recFlag);

    public List<MwAnswr> findByQstSeqInAndCrntRecFlg(List<Long> qstsSeq, boolean recFlag);

    public List<MwAnswr> findAllByQstSeqAndCrntRecFlg(long qstSeq, boolean recFlag);
}
