package com.idev4.loans.repository;

import com.idev4.loans.domain.MwMntrngChksAdc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Spring Data JPA repository for the MwAddrRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwMntrngChksAdcRepository extends JpaRepository<MwMntrngChksAdc, Long> {

    public MwMntrngChksAdc findOneByMntrngChksSeqAndCrntRecFlg(BigInteger seq, boolean flg);

    public List<MwMntrngChksAdc> findAllByBrnchSeqAndCrntRecFlg(long seq, boolean flg);

    public MwMntrngChksAdc findOneByMntrngChksSeqAndCrtdByAndCrntRecFlg(BigInteger seq, String crtdBy, boolean flg);

}
