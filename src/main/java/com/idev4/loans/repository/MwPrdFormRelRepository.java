package com.idev4.loans.repository;

import com.idev4.loans.domain.MwPrdFormRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the MwPrdFormRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPrdFormRelRepository extends JpaRepository<MwPrdFormRel, Long> {

    public List<MwPrdFormRel> findAllByPrdSeqAndCrntRecFlg(long seq, boolean flag);
}
