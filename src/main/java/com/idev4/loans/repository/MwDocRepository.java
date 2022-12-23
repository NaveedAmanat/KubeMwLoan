package com.idev4.loans.repository;

import com.idev4.loans.domain.MwDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the MwDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwDocRepository extends JpaRepository<MwDoc, Long> {

    public MwDoc findOneByDocSeqAndCrntRecFlg(long seq, boolean flag);
}
