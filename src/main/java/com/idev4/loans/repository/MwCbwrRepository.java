package com.idev4.loans.repository;

import com.idev4.loans.domain.MwCbwr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MwCbwr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCbwrRepository extends JpaRepository<MwCbwr, Long> {


    public MwCbwr findOneByCbwrSeqAndCrntRecFlg(long seq, boolean flag);

    public MwCbwr findOneByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);
}
