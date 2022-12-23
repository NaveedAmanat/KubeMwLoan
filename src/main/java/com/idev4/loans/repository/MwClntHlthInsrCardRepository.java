package com.idev4.loans.repository;

import com.idev4.loans.domain.MwClntHlthInsrCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwHlthInsrPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwClntHlthInsrCardRepository extends JpaRepository<MwClntHlthInsrCard, Long> {

    public List<MwClntHlthInsrCard> findAllByCrntRecFlgOrderByClntHlthInsrCardSeqDesc(boolean flag);

    public MwClntHlthInsrCard findOneByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);
}
