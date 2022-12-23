package com.idev4.loans.repository;

import com.idev4.loans.domain.MwBizExpDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwBizExpDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBizExpDtlRepository extends JpaRepository<MwBizExpDtl, Long> {

    public List<MwBizExpDtl> findAllByCrntRecFlg(boolean flag);

    public MwBizExpDtl findOneByExpDtlSeqAndCrntRecFlg(long seq, boolean flag);

    public MwBizExpDtl findOneByExpDtlSeqAndExpCtgryKeyAndCrntRecFlg(long seq, long catKey, boolean flag);

    public MwBizExpDtl findOneByExpDtlSeqAndExpCtgryKeyAndMwBizAprslAndExpTypKeyAndCrntRecFlg(long seq, long catKey, long bizKey, long bizTypeKey, boolean flag);

    public List<MwBizExpDtl> findAllByMwBizAprslAndCrntRecFlgAndEntyTypFlg(long businssAprsl, boolean flag, int typFlg);

    @Query(value = "select * from mw_biz_exp_dtl e where e.biz_aprsl_seq = :businssAprsl and e.crnt_rec_flg=1 and e.enty_typ_flg=1", nativeQuery = true)
    public List<MwBizExpDtl> findAllByMwBizAprslAndCrntRecFlgAndEntyTypFlgQry(@Param("businssAprsl") long businssAprsl);

    public List<MwBizExpDtl> findAllByMwBizAprslAndEntyTypFlg(long businssAprsl, int typFlg);

    // public List<MwBizExpDtl> findAllByLoanAppSeqAndBizAprslSeq(long loanAppSeq, long bizAppSeq);

    public List<MwBizExpDtl> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwBizExpDtl> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                           boolean flag);

    @Query(value = "select dtl.EXP_DTL_SEQ, dtl.EFF_START_DT, dtl.EXP_AMT, dtl.BIZ_APRSL_SEQ, dtl.EXP_CTGRY_KEY, dtl.EXP_TYP_KEY, dtl.CRTD_BY, dtl.CRTD_DT, dtl.LAST_UPD_BY, dtl.LAST_UPD_DT, dtl.DEL_FLG, dtl.EFF_END_DT, dtl.CRNT_REC_FLG, dtl.ENTY_TYP_FLG, dtl.SYNC_FLG "
            + " from mw_biz_aprsl ba " + " join mw_biz_exp_dtl dtl on dtl.biz_aprsl_seq=ba.biz_aprsl_seq and dtl.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and dtl.enty_typ_flg=1" + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizExpDtl> findAllMwBizExpDtlForBusinessForPorts(@Param("ports") List<Long> ports,
                                                                   @Param("sts") List<Long> sts);

    @Query(value = "select dtl.EXP_DTL_SEQ, dtl.EFF_START_DT, dtl.EXP_AMT, dtl.BIZ_APRSL_SEQ, dtl.EXP_CTGRY_KEY, dtl.EXP_TYP_KEY, dtl.CRTD_BY, dtl.CRTD_DT, dtl.LAST_UPD_BY, dtl.LAST_UPD_DT, dtl.DEL_FLG, dtl.EFF_END_DT, dtl.CRNT_REC_FLG, dtl.ENTY_TYP_FLG, dtl.SYNC_FLG "
            + " from mw_biz_aprsl ba "
            + " join mw_biz_exp_dtl dtl on dtl.biz_aprsl_seq=ba.biz_aprsl_seq and (dtl.crnt_rec_flg=1 or dtl.del_flg=1 ) "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and dtl.enty_typ_flg=1" + "  and dtl.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and dtl.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts ", nativeQuery = true)
    public List<MwBizExpDtl> findUpdatedMwBizExpDtlForBusinessForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                       @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    @Query(value = "select dtl.EXP_DTL_SEQ, dtl.EFF_START_DT, dtl.EXP_AMT, dtl.BIZ_APRSL_SEQ, dtl.EXP_CTGRY_KEY, dtl.EXP_TYP_KEY, dtl.CRTD_BY, dtl.CRTD_DT, dtl.LAST_UPD_BY, dtl.LAST_UPD_DT, dtl.DEL_FLG, dtl.EFF_END_DT, dtl.CRNT_REC_FLG, dtl.ENTY_TYP_FLG, dtl.SYNC_FLG "
            + " from mw_sch_aprsl ba " + " join mw_biz_exp_dtl dtl on dtl.biz_aprsl_seq=ba.sch_aprsl_seq and dtl.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and dtl.enty_typ_flg=2 " + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizExpDtl> findAllMwBizExpDtlForSchoolForPorts(@Param("ports") List<Long> ports,
                                                                 @Param("sts") List<Long> sts);

    @Query(value = "select dtl.EXP_DTL_SEQ, dtl.EFF_START_DT, dtl.EXP_AMT, dtl.BIZ_APRSL_SEQ, dtl.EXP_CTGRY_KEY, dtl.EXP_TYP_KEY, dtl.CRTD_BY, dtl.CRTD_DT, dtl.LAST_UPD_BY, dtl.LAST_UPD_DT, dtl.DEL_FLG, dtl.EFF_END_DT, dtl.CRNT_REC_FLG, dtl.ENTY_TYP_FLG, dtl.SYNC_FLG "
            + " from mw_sch_aprsl ba "
            + " join mw_biz_exp_dtl dtl on dtl.biz_aprsl_seq=ba.sch_aprsl_seq and (dtl.crnt_rec_flg=1 or dtl.del_flg=1 )  "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and dtl.enty_typ_flg=2 " + "  and dtl.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and dtl.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts ", nativeQuery = true)
    public List<MwBizExpDtl> findUpdatedMwBizExpDtlForSchoolForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                     @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    public List<MwBizExpDtl> findAllByMwBizAprslAndCrntRecFlg(Long bizAprslSeq, boolean b);

    public List<MwBizExpDtl> findAllByMwBizAprslAndEntyTypFlgAndCrntRecFlg(Long schAprslSeq, int i, boolean b);
}
