package com.idev4.loans.repository;

import com.idev4.loans.domain.MwClntRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MwClntRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwClntRelRepository extends JpaRepository<MwClntRel, Long> {

    public MwClntRel findOneByClntRelSeq(long seq);

    public MwClntRel findOneByClntRelSeqAndCrntRecFlg(long seq, boolean flag);

    public MwClntRel findOneByClntRelSeqAndRelTypFlgAndCrntRecFlg(long seq, long relTyp, boolean flag);

    //public MwClntRel findOneByLoanAppSeqClntAndRelSeqAndRelTypFlgAndCrntRecFlg(long loanAppSeq, long seq, long relTyp, boolean flag );

    public MwClntRel findOneByLoanAppSeqAndRelWthClntKeyAndCrntRecFlg(long seq, long relKey, boolean flag);

    public MwClntRel findOneByLoanAppSeqAndRelTypFlgAndCrntRecFlg(long seq, long relTyp, boolean flag);

    public MwClntRel findOneByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwClntRel> findAllByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwClntRel> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwClntRel> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                         boolean flag);

    @Query(value = "select rl.CLNT_REL_SEQ, rl.REL_TYP_FLG, rl.LOAN_APP_SEQ, rl.GNDR_KEY, rl.OCC_KEY, rl.REL_WTH_CLNT_KEY, rl.MRTL_STS_KEY, rl.RES_TYP_KEY, rl.EFF_START_DT, rl.ID, rl.CNIC_NUM, rl.CNIC_EXPRY_DT, rl.cnic_issue_dt, rl.FRST_NM, rl.LAST_NM, rl.CO_BWR_SAN_FLG, rl.NOM_FTHR_SPZ_FLG, rl.DOB, rl.PH_NUM, rl.FTHR_FRST_NM, rl.FTHR_LAST_NM, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + "  from mw_clnt_rel rl " + " join mw_loan_app app on app.loan_app_seq=rl.loan_app_seq and app.crnt_rec_flg=1 "
            + " where rl.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwClntRel> findAllMwClntRelForLoanForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select rl.CLNT_REL_SEQ, rl.REL_TYP_FLG, rl.LOAN_APP_SEQ, rl.GNDR_KEY, rl.OCC_KEY, rl.REL_WTH_CLNT_KEY, rl.MRTL_STS_KEY, rl.RES_TYP_KEY, rl.EFF_START_DT, rl.ID, rl.CNIC_NUM, rl.CNIC_EXPRY_DT, rl.cnic_issue_dt, rl.FRST_NM, rl.LAST_NM, rl.CO_BWR_SAN_FLG, rl.NOM_FTHR_SPZ_FLG, rl.DOB, rl.PH_NUM, rl.FTHR_FRST_NM, rl.FTHR_LAST_NM, rl.CRTD_BY, rl.CRTD_DT, rl.LAST_UPD_BY, rl.LAST_UPD_DT, rl.DEL_FLG, rl.EFF_END_DT, rl.CRNT_REC_FLG, rl.SYNC_FLG "
            + "  from mw_clnt_rel rl " + " join mw_loan_app app on app.loan_app_seq=rl.loan_app_seq and app.crnt_rec_flg=1 "
            + " where (rl.crnt_rec_flg = 1 or rl.del_flg=1)"
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and rl.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and rl.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts"
            + " order by rl.eff_start_dt", nativeQuery = true)
    public List<MwClntRel> findUpdatedMwClntRelForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                               @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    public MwClntRel findOneByLoanAppSeqAndRelTypFlgAndCnicNumAndCrntRecFlg(long loanAppSeq, long relType, long cnicNum, boolean flag);
}
