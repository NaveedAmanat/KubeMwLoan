package com.idev4.loans.repository;

import com.idev4.loans.domain.MwBizAprslEstLvstkFin;
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
public interface MwBizAprslEstLvstkFinRepository extends JpaRepository<MwBizAprslEstLvstkFin, Long> {

    public List<MwBizAprslEstLvstkFin> findAllByCrntRecFlg(boolean flag);

    public List<MwBizAprslEstLvstkFin> findAllByBizAprslSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwBizAprslEstLvstkFin> findAllByBizAprslSeq(Long seq);

    public MwBizAprslEstLvstkFin findOneByBizAprslEstLvstkFinSeqAndCrntRecFlg(Long seq, boolean flag);


    public List<MwBizAprslEstLvstkFin> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                                     boolean flag);

    @Query(value = " select lv.* " + " from mw_biz_aprsl ba "
            + " join MW_BIZ_APRSL_EST_LVSTK_FIN lv on lv.BIZ_APRSL_SEQ=ba.BIZ_APRSL_SEQ and lv.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizAprslEstLvstkFin> findAllMwBizAprslEstLvstkFinForLoanForPorts(@Param("ports") List<Long> ports,
                                                                                   @Param("sts") List<Long> sts);

    @Query(value = " select lv.* " + " from mw_biz_aprsl ba "
            + " join MW_BIZ_APRSL_EST_LVSTK_FIN lv on lv.BIZ_APRSL_SEQ=ba.BIZ_APRSL_SEQ and lv.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and lv.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and lv.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts ", nativeQuery = true)
    public List<MwBizAprslEstLvstkFin> findUpdatedMwBizAprslEstLvstkFinForLoanForPortsLastUpdatedbyNot(
            @Param("syncDate") Instant syncDate, @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy,
            @Param("sts") List<Long> sts);

}
