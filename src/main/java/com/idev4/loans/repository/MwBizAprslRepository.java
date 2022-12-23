package com.idev4.loans.repository;

import com.idev4.loans.domain.MwBizAprsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwBizAprsl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBizAprslRepository extends JpaRepository<MwBizAprsl, Long> {

    public List<MwBizAprsl> findAllByCrntRecFlg(boolean flag);

    public MwBizAprsl findOneByBizAprslSeqAndCrntRecFlg(long loanAppSeq, boolean flag);

    public MwBizAprsl findOneByMwLoanAppAndCrntRecFlg(long loanAppSeq, boolean flag);

    public List<MwBizAprsl> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwBizAprsl> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                          boolean flag);

    @Query(value = "select ba.BIZ_APRSL_SEQ, ba.EFF_START_DT, ba.SECT_KEY, ba.ACTY_KEY, ba.BIZ_DTL_STR, ba.PRSN_RUN_THE_BIZ, ba.BIZ_OWN, ba.YRS_IN_BIZ, ba.MNTH_IN_BIZ, ba.LOAN_APP_SEQ, ba.CRTD_BY, ba.CRTD_DT, ba.LAST_UPD_BY, ba.LAST_UPD_DT, ba.DEL_FLG, ba.EFF_END_DT, ba.CRNT_REC_FLG, ba.BIZ_ADDR_SAME_AS_HOME_FLG, ba.BIZ_PROPERTY_OWN_KEY, ba.BIZ_PRPTY_OWN_KEY, ba.BIZ_PH_NUM, ba.SYNC_FLG "
            + "  from mw_biz_aprsl ba " + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 "
            + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizAprsl> findAllMwBizAprslForLoanForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select ba.BIZ_APRSL_SEQ, ba.EFF_START_DT, ba.SECT_KEY, ba.ACTY_KEY, ba.BIZ_DTL_STR, ba.PRSN_RUN_THE_BIZ, ba.BIZ_OWN, ba.YRS_IN_BIZ, ba.MNTH_IN_BIZ, ba.LOAN_APP_SEQ, ba.CRTD_BY, ba.CRTD_DT, ba.LAST_UPD_BY, ba.LAST_UPD_DT, ba.DEL_FLG, ba.EFF_END_DT, ba.CRNT_REC_FLG, ba.BIZ_ADDR_SAME_AS_HOME_FLG, ba.BIZ_PROPERTY_OWN_KEY, ba.BIZ_PRPTY_OWN_KEY, ba.BIZ_PH_NUM, ba.SYNC_FLG "
            + "  from mw_biz_aprsl ba " + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 "
            + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and ba.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and ba.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizAprsl> findUpdatedMwBizAprslForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                 @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
