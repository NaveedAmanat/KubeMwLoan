package com.idev4.loans.repository;

import com.idev4.loans.domain.MwNom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwNom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwNomRepository extends JpaRepository<MwNom, Long> {


    public MwNom findOneByNomSeqAndCrntRecFlg(long nomSeq, boolean flag);

    public List<MwNom> findAllByCrntRecFlg(boolean flag);

    public MwNom findOneByLoanAppSeqAndCrntRecFlg(long loanSeq, boolean flag);
}
