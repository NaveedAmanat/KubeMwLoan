package com.idev4.loans.repository;

import com.idev4.loans.domain.MwClntPsc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MwClntPsc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwClntPscRepository extends JpaRepository<MwClntPsc, Long> {

    public List<MwClntPsc> findAllByLoanAppSeqAndCrntRecFlg(long loanAppSeq, boolean flag);

    public MwClntPsc findOneByLoanAppSeqAndQstSeqAndCrntRecFlg(long loanAppSeq, long qstSeq, boolean flag);

    public MwClntPsc findOneByPscSeqAndCrntRecFlg(long seq, boolean flag);

    public MwClntPsc findOneByPscSeqAndQstSeqAndCrntRecFlg(long seq, long cSeq, boolean flag);

    //PSC_SEQ, QST_SEQ, ANSWR_SEQ, LOAN_APP_SEQ
    public MwClntPsc findOneByPscSeqAndQstSeqAndAnswrSeqAndLoanAppSeqAndCrntRecFlg(long seq, long cSeq, long aSeq, long lSeq, boolean flag);

    public List<MwClntPsc> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwClntPsc> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                         boolean flag);

    @Query(value = "select psc.PSC_SEQ, psc.EFF_START_DT, psc.QST_SEQ, psc.ANSWR_SEQ, psc.LOAN_APP_SEQ, psc.CRTD_BY, psc.CRTD_DT, psc.LAST_UPD_BY, psc.LAST_UPD_DT, psc.DEL_FLG, psc.EFF_END_DT, psc.CRNT_REC_FLG, psc.SYNC_FLG"
            + " from  mw_clnt_psc psc " + " join mw_loan_app app on app.loan_app_seq=psc.loan_app_seq and app.crnt_rec_flg=1 "
            + " where psc.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwClntPsc> findAllMwClntPscForLoanForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select psc.PSC_SEQ, psc.EFF_START_DT, psc.QST_SEQ, psc.ANSWR_SEQ, psc.LOAN_APP_SEQ, psc.CRTD_BY, psc.CRTD_DT, psc.LAST_UPD_BY, psc.LAST_UPD_DT, psc.DEL_FLG, psc.EFF_END_DT, psc.CRNT_REC_FLG, psc.SYNC_FLG"
            + " from  mw_clnt_psc psc " + " join mw_loan_app app on app.loan_app_seq=psc.loan_app_seq and app.crnt_rec_flg=1 "
            + " where psc.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and psc.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and psc.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwClntPsc> findUpdatedMwClntPscForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                               @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
