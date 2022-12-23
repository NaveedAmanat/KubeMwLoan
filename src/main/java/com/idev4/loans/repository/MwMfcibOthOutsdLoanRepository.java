package com.idev4.loans.repository;

import com.idev4.loans.domain.MwMfcibOthOutsdLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwMfcibOthOutsdLoan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwMfcibOthOutsdLoanRepository extends JpaRepository<MwMfcibOthOutsdLoan, Long> {

    public List<MwMfcibOthOutsdLoan> findAllByLoanAppSeqAndCrntRecFlg(long loanAppSeq, boolean recFlag);

    public MwMfcibOthOutsdLoan findOneByOthOutsdLoanSeqAndCrntRecFlg(long seq, boolean recFlag);

    public MwMfcibOthOutsdLoan findOneByOthOutsdLoanSeqAndLoanAppSeqAndCrntRecFlg(long seq, long loanAppSeq, boolean recFlag);

    public List<MwMfcibOthOutsdLoan> findAllBySyncFlgAndCrntRecFlg(boolean sFlag, boolean recFlag);

    public List<MwMfcibOthOutsdLoan> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                                   boolean flag);

    @Query(value = "select oth.OTH_OUTSD_LOAN_SEQ, oth.EFF_START_DT, oth.LOAN_APP_SEQ, oth.INSTN_NM, oth.TOT_LOAN_AMT, oth.LOAN_PRPS, oth.CRNT_OUTSD_AMT, oth.CMPL_DT, oth.MNTH_EXP_FLG, oth.CRTD_BY, oth.CRTD_DT, oth.LAST_UPD_BY, oth.LAST_UPD_DT, oth.DEL_FLG, oth.MFCIB_FLG, oth.EFF_END_DT, oth.CRNT_REC_FLG, oth.SYNC_FLG "
            + " from mw_mfcib_oth_outsd_loan oth " + " join mw_loan_app app on app.loan_app_seq=oth.loan_app_seq and app.crnt_rec_flg=1 "
            + " where oth.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwMfcibOthOutsdLoan> findAllMFCIBForLoanForPorts(@Param("ports") List<Long> ports,
                                                                 @Param("sts") List<Long> sts);

    @Query(value = "select oth.OTH_OUTSD_LOAN_SEQ, oth.EFF_START_DT, oth.LOAN_APP_SEQ, oth.INSTN_NM, oth.TOT_LOAN_AMT, oth.LOAN_PRPS, oth.CRNT_OUTSD_AMT, oth.CMPL_DT, oth.MNTH_EXP_FLG, oth.CRTD_BY, oth.CRTD_DT, oth.LAST_UPD_BY, oth.LAST_UPD_DT, oth.DEL_FLG, oth.MFCIB_FLG, oth.EFF_END_DT, oth.CRNT_REC_FLG, oth.SYNC_FLG "
            + " from mw_mfcib_oth_outsd_loan oth " + " join mw_loan_app app on app.loan_app_seq=oth.loan_app_seq and app.crnt_rec_flg=1 "
            + " where (oth.crnt_rec_flg = 1 or oth.del_flg = 1)"
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and oth.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and oth.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts "
            + " order by oth.eff_start_dt", nativeQuery = true)
    public List<MwMfcibOthOutsdLoan> findUpdatedMFCIBForLoanForPortsLastUpdatedbyNot(@Param("ports") List<Long> ports,
                                                                                     @Param("syncDate") Instant syncDate, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
