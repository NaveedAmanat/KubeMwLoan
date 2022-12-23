package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanUtlPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwLoanUtlPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwLoanUtlPlanRepository extends JpaRepository<MwLoanUtlPlan, Long> {

    public List<MwLoanUtlPlan> findAllByCrntRecFlg(boolean flag);

    public List<MwLoanUtlPlan> findAllByLoanAppSeq(long loanAppSeq);

    public MwLoanUtlPlan findOneByLoanUtlPlanSeqAndCrntRecFlg(long utilSeq, boolean flag);

    public List<MwLoanUtlPlan> findAllByLoanAppSeqAndCrntRecFlg(long loanAppSeq, boolean flag);

    public List<MwLoanUtlPlan> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwLoanUtlPlan> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                             boolean flag);

    @Query(value = "select pln.LOAN_UTL_PLAN_SEQ, pln.EFF_START_DT, pln.LOAN_UTL_DSCR, pln.LOAN_UTL_AMT, pln.CRTD_BY, pln.CRTD_DT, pln.LAST_UPD_BY, pln.LAST_UPD_DT, pln.DEL_FLG, pln.LOAN_APP_SEQ, pln.EFF_END_DT, pln.CRNT_REC_FLG, pln.LOAN_UTL_TYP, pln.SYNC_FLG, pln.TYP_FLG "
            + "  from mw_loan_utl_plan pln " + " join mw_loan_app app on app.loan_app_seq=pln.loan_app_seq and app.crnt_rec_flg=1 "
            + " where pln.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwLoanUtlPlan> findAllMwLoanUtlPlanForLoanForPorts(@Param("ports") List<Long> ports,
                                                                   @Param("sts") List<Long> sts);

    @Query(value = "select pln.LOAN_UTL_PLAN_SEQ, pln.EFF_START_DT, pln.LOAN_UTL_DSCR, pln.LOAN_UTL_AMT, pln.CRTD_BY, pln.CRTD_DT, pln.LAST_UPD_BY, pln.LAST_UPD_DT, pln.DEL_FLG, pln.LOAN_APP_SEQ, pln.EFF_END_DT, pln.CRNT_REC_FLG, pln.LOAN_UTL_TYP, pln.SYNC_FLG, pln.TYP_FLG "
            + "  from mw_loan_utl_plan pln " + " join mw_loan_app app on app.loan_app_seq=pln.loan_app_seq and app.crnt_rec_flg=1 "
            + " where (pln.crnt_rec_flg = 1 or pln.del_flg = 1) "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and pln.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and pln.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts"
            + " order by pln.eff_start_dt", nativeQuery = true)
    public List<MwLoanUtlPlan> findUpdatedMwLoanUtlPlanForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                       @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
