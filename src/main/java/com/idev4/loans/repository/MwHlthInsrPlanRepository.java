package com.idev4.loans.repository;

import com.idev4.loans.domain.MwHlthInsrPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwHlthInsrPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwHlthInsrPlanRepository extends JpaRepository<MwHlthInsrPlan, Long> {

    public List<MwHlthInsrPlan> findAllByCrntRecFlg(boolean flag);

    public MwHlthInsrPlan findOneByHlthInsrPlanSeqAndCrntRecFlg(long seq, boolean flag);
}
