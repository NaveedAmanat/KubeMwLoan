package com.idev4.loans.repository;

import com.idev4.loans.domain.MwAsocPrdRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the MwPrd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAsocPrdRelRepository extends JpaRepository<MwAsocPrdRel, Long> {

    public List<MwAsocPrdRel> findAllByPrdSeqAndCrntRecFlg(long seq, boolean flag);

    public MwAsocPrdRel findOneByAsocPrdRelSeqAndCrntRecFlg(long seq, boolean flag);

    public MwAsocPrdRel findOneByAsocPrdSeqAndPrdSeqAndCrntRecFlg(long seq, long prdSeq, boolean flag);

}
