package com.idev4.loans.repository;

import com.idev4.loans.domain.MwWrtOfClnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwClnt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwWrtOfClntRepository extends JpaRepository<MwWrtOfClnt, Long> {

    public MwWrtOfClnt findOneByClntSeqAndCrntRecFlg(Long clntSeq, boolean flg);

    public MwWrtOfClnt findOneByCnicNumAndCrntRecFlg(Long cnicNum, boolean flg);

    public List<MwWrtOfClnt> findAllByCnicNumAndCrntRecFlg(Long cnicNum, boolean flg);

}
