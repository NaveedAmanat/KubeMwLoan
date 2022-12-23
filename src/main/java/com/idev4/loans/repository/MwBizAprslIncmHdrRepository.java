package com.idev4.loans.repository;

import com.idev4.loans.domain.MwBizAprslIncmHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwBizAprslIncmHdr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBizAprslIncmHdrRepository extends JpaRepository<MwBizAprslIncmHdr, Long> {

    public List<MwBizAprslIncmHdr> findAllByCrntRecFlg(boolean flag);

    public MwBizAprslIncmHdr findOneByIncmHdrSeqAndCrntRecFlg(long aprslSeq, boolean flag);

    public MwBizAprslIncmHdr findOneByMwBizAprslAndCrntRecFlg(long aprslSeq, boolean flag);

    public List<MwBizAprslIncmHdr> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwBizAprslIncmHdr> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                                 boolean flag);

    @Query(value = "select hdr.INCM_HDR_SEQ, hdr.EFF_START_DT, hdr.MAX_MNTH_SAL_AMT, hdr.MAX_SAL_NUM_OF_MNTH, hdr.MIN_MNTH_SAL_AMT, hdr.MIN_SAL_NUM_OF_MNTH, hdr.BIZ_APRSL_SEQ, hdr.CRTD_BY, hdr.CRTD_DT, hdr.LAST_UPD_BY, hdr.LAST_UPD_DT, hdr.DEL_FLG, hdr.EFF_END_DT, hdr.CRNT_REC_FLG, hdr.SYNC_FLG "
            + "  from mw_biz_aprsl ba " + " join mw_biz_aprsl_incm_hdr hdr on hdr.biz_aprsl_seq=ba.biz_aprsl_seq and hdr.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizAprslIncmHdr> findAllMwBizAprslIncmHdrForLoanForPorts(@Param("ports") List<Long> ports,
                                                                           @Param("sts") List<Long> sts);

    @Query(value = "select hdr.INCM_HDR_SEQ, hdr.EFF_START_DT, hdr.MAX_MNTH_SAL_AMT, hdr.MAX_SAL_NUM_OF_MNTH, hdr.MIN_MNTH_SAL_AMT, hdr.MIN_SAL_NUM_OF_MNTH, hdr.BIZ_APRSL_SEQ, hdr.CRTD_BY, hdr.CRTD_DT, hdr.LAST_UPD_BY, hdr.LAST_UPD_DT, hdr.DEL_FLG, hdr.EFF_END_DT, hdr.CRNT_REC_FLG, hdr.SYNC_FLG "
            + "  from mw_biz_aprsl ba " + " join mw_biz_aprsl_incm_hdr hdr on hdr.biz_aprsl_seq=ba.biz_aprsl_seq and hdr.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=ba.loan_app_seq and app.crnt_rec_flg=1 " + " where ba.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and ba.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and ba.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwBizAprslIncmHdr> findUpdatedMwBizAprslIncmHdrForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                               @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
