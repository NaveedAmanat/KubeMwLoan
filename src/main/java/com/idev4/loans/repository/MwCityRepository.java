package com.idev4.loans.repository;

import com.idev4.loans.domain.MwCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MwCity entity.
 */
@Repository
public interface MwCityRepository extends JpaRepository<MwCity, Long> {

    public MwCity findOneByCitySeqAndCrntRecFlg(long citySeq, boolean flag);

    @Query(value = "SELECT CITY.CITY_NM\n" +
            "  FROM MW_CITY CITY\n" +
            " WHERE CITY.CITY_SEQ = :P_CITY_SEQ AND CITY.CRNT_REC_FLG = 1", nativeQuery = true)
    Object findByCitySeqAndCrntRecFlg(@Param("P_CITY_SEQ") long citySeq);

    @Query(value = "SELECT CTY.*\n" +
            "  FROM MW_CLNT  CLNT\n" +
            "       JOIN MW_ADDR_REL ARL\n" +
            "           ON     ARL.ENTY_KEY = CLNT.CLNT_SEQ\n" +
            "              AND ARL.CRNT_REC_FLG = 1\n" +
            "              AND ARL.ENTY_TYP = 'Client'\n" +
            "       JOIN MW_ADDR ADR\n" +
            "           ON ADR.ADDR_SEQ = ARL.ADDR_SEQ AND ADR.CRNT_REC_FLG = 1\n" +
            "       JOIN MW_CITY_UC_REL CRL\n" +
            "           ON CRL.CITY_UC_REL_SEQ = ADR.CITY_SEQ AND CRL.CRNT_REC_FLG = 1\n" +
            "       JOIN MW_CITY CTY\n" +
            "           ON CTY.CITY_SEQ = CRL.CITY_SEQ AND CTY.CRNT_REC_FLG = 1\n" +
            " WHERE CLNT.CLNT_SEQ = :P_CLNT_SEQ AND CLNT.CRNT_REC_FLG = 1", nativeQuery = true)
    public MwCity findCityByClntSeq(@Param("P_CLNT_SEQ") long clntSeq);
}
