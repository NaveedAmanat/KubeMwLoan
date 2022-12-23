package com.idev4.loans.repository;

import com.idev4.loans.domain.MwSchGrd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwSchGrdRepository extends JpaRepository<MwSchGrd, Long> {

    public List<MwSchGrd> findAllByCrntRecFlg(boolean flag);

    public MwSchGrd findOneBySchGrdSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchGrd findOneBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchGrd> findAllBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchGrd> findAllBySchGrdSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchGrd> findAllBySyncFlgAndCrntRecFlg(boolean sFlag, boolean flag);

    public List<MwSchGrd> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                        boolean flag);

    @Query(value = "select grd.SCH_GRD_SEQ, grd.EFF_START_DT, grd.EFF_END_DT, grd.TOT_FEM_STDNT, grd.TOT_MALE_STDNT, grd.AVG_GRD_FEE, grd.NO_FEE_STDNT, grd.FEM_STDNT_PRSNT, grd.MALE_STDNT_PRSNT, grd.GRD_KEY, grd.CRTD_BY, grd.CRTD_DT, grd.LAST_UPD_BY, grd.LAST_UPD_DT, grd.DEL_FLG, grd.CRNT_REC_FLG, grd.SCH_APRSL_SEQ, grd.SYNC_FLG "
            + " from mw_sch_aprsl sa " + " join mw_sch_grd grd on grd.sch_aprsl_seq=sa.sch_aprsl_seq and grd.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=sa.loan_app_seq and app.crnt_rec_flg=1 " + " where sa.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwSchGrd> findAllMwSchGrdForLoanForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> st);

    @Query(value = "select grd.SCH_GRD_SEQ, grd.EFF_START_DT, grd.EFF_END_DT, grd.TOT_FEM_STDNT, grd.TOT_MALE_STDNT, grd.AVG_GRD_FEE, grd.NO_FEE_STDNT, grd.FEM_STDNT_PRSNT, grd.MALE_STDNT_PRSNT, grd.GRD_KEY, grd.CRTD_BY, grd.CRTD_DT, grd.LAST_UPD_BY, grd.LAST_UPD_DT, grd.DEL_FLG, grd.CRNT_REC_FLG, grd.SCH_APRSL_SEQ, grd.SYNC_FLG "
            + " from mw_sch_aprsl sa " + " join mw_sch_grd grd on grd.sch_aprsl_seq=sa.sch_aprsl_seq and grd.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=sa.loan_app_seq and app.crnt_rec_flg=1 " + " where sa.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and grd.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and grd.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwSchGrd> findUpdatedMwSchGrdForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                             @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> st);
}
