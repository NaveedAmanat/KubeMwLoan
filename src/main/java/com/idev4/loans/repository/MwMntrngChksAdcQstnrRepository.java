package com.idev4.loans.repository;

import com.idev4.loans.domain.MwMntrngChksAdcQstnr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Spring Data JPA repository for the MwAddrRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwMntrngChksAdcQstnrRepository extends JpaRepository<MwMntrngChksAdcQstnr, Long> {

    public MwMntrngChksAdcQstnr findOneByMntrngChksAdcQstnrSeqAndCrntRecFlg(BigInteger seq, boolean flag);

    public List<MwMntrngChksAdcQstnr> findAllByMntrngChksSeqInAndCrntRecFlg(List<BigInteger> seq, boolean flag);
}
