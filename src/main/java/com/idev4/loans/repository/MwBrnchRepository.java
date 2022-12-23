package com.idev4.loans.repository;

import com.idev4.loans.domain.MwBrnch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MwBrnch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBrnchRepository extends JpaRepository<MwBrnch, Long> {

    public MwBrnch findOneByBrnchSeqAndCrntRecFlg(Long brnchSeq, boolean flag);

    @Query(value = "SELECT BR.*\n" +
            "  FROM MW_PORT  PORT\n" +
            "       JOIN MW_BRNCH BR\n" +
            "           ON BR.BRNCH_SEQ = PORT.BRNCH_SEQ AND BR.CRNT_REC_FLG = 1\n" +
            " WHERE PORT.CRNT_REC_FLG = 1 AND PORT.PORT_SEQ = :P_PORT_KEY", nativeQuery = true)
    public MwBrnch findByBrnchByPortSeq(@Param("P_PORT_KEY") long portKey);
}
