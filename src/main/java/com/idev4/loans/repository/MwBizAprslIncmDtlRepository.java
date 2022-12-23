package com.idev4.loans.repository;

import com.idev4.loans.domain.MwBizAprslIncmDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwBizAprslIncmDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBizAprslIncmDtlRepository extends JpaRepository<MwBizAprslIncmDtl, Long> {

    public List<MwBizAprslIncmDtl> findAllByCrntRecFlg(boolean flag);

    // public List<MwBizAprslIncmDtl> findAllByMwBizAprslIncmHdrAndCrntRecFlg(long hdrSeq,boolean flag);

    public List<MwBizAprslIncmDtl> findAllByMwBizAprslIncmHdrAndCrntRecFlgAndEntyTypFlg(long hdrSeq, boolean flag, int typFlg);

    public List<MwBizAprslIncmDtl> findAllByMwBizAprslIncmHdrAndEntyTypFlg(long hdrSeq, int typFlg);

    public List<MwBizAprslIncmDtl> findAllByMwBizAprslIncmHdrAndCrntRecFlg(long hdrSeq, boolean flag);

    public MwBizAprslIncmDtl findOneByIncmDtlSeqAndCrntRecFlg(long dtlSeq, boolean flag);

    public MwBizAprslIncmDtl findOneByIncmDtlSeqAndIncmCtgryKeyAndCrntRecFlg(long dtlSeq, long catkey, boolean flag);

    public List<MwBizAprslIncmDtl> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwBizAprslIncmDtl> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                                 boolean flag);

    @Query(value = "select dtl.INCM_DTL_SEQ, dtl.EFF_START_DT, dtl.INCM_AMT, dtl.INCM_HDR_SEQ, dtl.INCM_CTGRY_KEY, dtl.INCM_TYP_KEY, dtl.CRTD_BY, dtl.CRTD_DT, dtl.LAST_UPD_BY, dtl.LAST_UPD_DT, dtl.DEL_FLG, dtl.EFF_END_DT, dtl.CRNT_REC_FLG, dtl.ENTY_TYP_FLG, dtl.SYNC_FLG "
            + " from mw_biz_aprsl ba " + " join mw_biz_aprsl_incm_hdr hdr on hdr.biz_aprsl_seq=ba.biz_aprsl_seq and hdr.crnt_rec_flg=1 "
            + " join mw_biz_aprsl_incm_dtl dtl on dtl.incm_hdr_seq=hdr.incm_hdr_seq and dtl.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and dtl.enty_typ_flg=1 " + "  and app.port_seq IN :ports  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizAprslIncmDtl> findAllMwBizAprslIncmHdrForBusinessForPorts(@Param("ports") List<Long> ports,
                                                                               @Param("sts") List<Long> sts);

    @Query(value = "select dtl.INCM_DTL_SEQ, dtl.EFF_START_DT, dtl.INCM_AMT, dtl.INCM_HDR_SEQ, dtl.INCM_CTGRY_KEY, dtl.INCM_TYP_KEY, dtl.CRTD_BY, dtl.CRTD_DT, dtl.LAST_UPD_BY, dtl.LAST_UPD_DT, dtl.DEL_FLG, dtl.EFF_END_DT, dtl.CRNT_REC_FLG, dtl.ENTY_TYP_FLG, dtl.SYNC_FLG "
            + " from mw_biz_aprsl ba " + " join mw_biz_aprsl_incm_hdr hdr on hdr.biz_aprsl_seq=ba.biz_aprsl_seq and hdr.crnt_rec_flg=1 "
            + " join mw_biz_aprsl_incm_dtl dtl on dtl.incm_hdr_seq=hdr.incm_hdr_seq and (dtl.crnt_rec_flg=1 or dtl.del_flg=1) "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and dtl.enty_typ_flg=1 " + "  and dtl.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and dtl.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts"
            + " order by dtl.eff_start_dt", nativeQuery = true)
    public List<MwBizAprslIncmDtl> findUpdatedMwBizAprslIncmHdrForBusinessForPortsLastUpdatedbyNot(
            @Param("syncDate") Instant syncDate, @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy,
            @Param("sts") List<Long> sts);

    @Query(value = "select dtl.INCM_DTL_SEQ, dtl.EFF_START_DT, dtl.INCM_AMT, dtl.INCM_HDR_SEQ, dtl.INCM_CTGRY_KEY, dtl.INCM_TYP_KEY, dtl.CRTD_BY, dtl.CRTD_DT, dtl.LAST_UPD_BY, dtl.LAST_UPD_DT, dtl.DEL_FLG, dtl.EFF_END_DT, dtl.CRNT_REC_FLG, dtl.ENTY_TYP_FLG, dtl.SYNC_FLG "
            + "   from mw_sch_aprsl ba " + " join mw_biz_aprsl_incm_dtl dtl on dtl.incm_hdr_seq=ba.sch_aprsl_seq and dtl.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and dtl.enty_typ_flg=2 " + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizAprslIncmDtl> findAllMwBizAprslIncmHdrForSchoolForPorts(@Param("ports") List<Long> ports,
                                                                             @Param("sts") List<Long> sts);

    @Query(value = "select dtl.INCM_DTL_SEQ, dtl.EFF_START_DT, dtl.INCM_AMT, dtl.INCM_HDR_SEQ, dtl.INCM_CTGRY_KEY, dtl.INCM_TYP_KEY, dtl.CRTD_BY, dtl.CRTD_DT, dtl.LAST_UPD_BY, dtl.LAST_UPD_DT, dtl.DEL_FLG, dtl.EFF_END_DT, dtl.CRNT_REC_FLG, dtl.ENTY_TYP_FLG, dtl.SYNC_FLG "
            + "   from mw_sch_aprsl ba "
            + " join mw_biz_aprsl_incm_dtl dtl on dtl.incm_hdr_seq=ba.sch_aprsl_seq and (dtl.crnt_rec_flg=1 or dtl.del_flg=1) "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and dtl.enty_typ_flg=2 " + "  and dtl.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and dtl.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts"
            + "  order by dtl.eff_start_dt", nativeQuery = true)
    public List<MwBizAprslIncmDtl> findUpdatedMwBizAprslIncmHdrForSchoolForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                                 @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    public List<MwBizAprslIncmDtl> findAllByMwBizAprslIncmHdrAndEntyTypFlgAndCrntRecFlg(Long schAprslSeq, int i, boolean b);

}
