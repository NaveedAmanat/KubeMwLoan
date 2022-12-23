package com.idev4.loans.repository;

import com.idev4.loans.domain.MwClntPermAddr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MwClntPermAddr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwClntPermAddrRepository extends JpaRepository<MwClntPermAddr, Long> {

    public MwClntPermAddr findOneByClntSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwClntPermAddr> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwClntPermAddr> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                              boolean flag);

    @Query(value = "select pa.CLNT_PERM_ADDR_SEQ, pa.EFF_START_DT, pa.CLNT_SEQ, pa.PERM_ADDR_STR, pa.CRTD_BY, pa.CRTD_DT, pa.LAST_UPD_BY, pa.LAST_UPD_DT, pa.DEL_FLG, pa.EFF_END_DT, pa.CRNT_REC_FLG, pa.SYNC_FLG "
            + " from  MW_CLNT_PERM_ADDR pa " + " join mw_clnt clnt on clnt.clnt_seq=pa.clnt_seq and clnt.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1" + " where pa.crnt_rec_flg = 1"
            + "  and clnt.port_key IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwClntPermAddr> findAllClntPermAddrForClntForPorts(@Param("ports") List<Long> ports,
                                                                   @Param("sts") List<Long> sts);

    @Query(value = "select pa.CLNT_PERM_ADDR_SEQ, pa.EFF_START_DT, pa.CLNT_SEQ, pa.PERM_ADDR_STR, pa.CRTD_BY, pa.CRTD_DT, pa.LAST_UPD_BY, pa.LAST_UPD_DT, pa.DEL_FLG, pa.EFF_END_DT, pa.CRNT_REC_FLG, pa.SYNC_FLG "
            + " from  MW_CLNT_PERM_ADDR pa " + " join mw_clnt clnt on clnt.clnt_seq=pa.clnt_seq and clnt.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.clnt_seq=clnt.clnt_seq and app.crnt_rec_flg=1" + " where pa.crnt_rec_flg = 1"
            + "  and pa.last_upd_dt > :syncDate"
            + "  and clnt.port_key IN :ports and pa.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwClntPermAddr> findUpdatedClntPermAddrForClntForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                       @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    //Added by Rizwan Mahfooz on 22 AUGUST 2022
    @Query(value = "SELECT ADDR.*\n" +
            "  FROM MW_CLNT_PERM_ADDR  ADDR\n" +
            "       JOIN MW_LOAN_APP APP\n" +
            "           ON APP.CLNT_SEQ = ADDR.CLNT_SEQ AND APP.CRNT_REC_FLG = 1\n" +
            "       JOIN MW_BRNCH BR\n" +
            "           ON BR.BRNCH_SEQ = APP.BRNCH_SEQ AND BR.CRNT_REC_FLG = 1\n" +
            "WHERE     ADDR.CRNT_REC_FLG = 1\n" +
            "       AND ADDR.CLNT_SEQ = :P_CLNT_SEQ\n" +
            "       AND BR.BRNCH_SEQ = :P_BRNCH_SEQ", nativeQuery = true)
    public MwClntPermAddr findbyClntSeqAndBrnchSeq(@Param("P_CLNT_SEQ") Long clntSeq,
                                                   @Param("P_BRNCH_SEQ") Long brnchSeq);
    //End
}
