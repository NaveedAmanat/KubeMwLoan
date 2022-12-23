package com.idev4.loans.repository;

import com.idev4.loans.domain.MwSchQltyChk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwSchQltyChkRepository extends JpaRepository<MwSchQltyChk, Long> {

    public List<MwSchQltyChk> findAllByCrntRecFlg(boolean flag);

    public MwSchQltyChk findOneBySchQltyChkSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchQltyChk findOneBySchQltyChkSeqAndQstSeqAndCrntRecFlg(long seq, long qseq, boolean flag);

    // Added by M. Naveed - Dated 08-04-2022 - Duplicate School Qualities
    public MwSchQltyChk findOneBySchQltyChkSeqAndQstSeqAndAnswrSeqAndCrntRecFlg(long seq, long qseq, long answerSeq, boolean flag);
    // End

    public List<MwSchQltyChk> findAllBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchQltyChk findOneBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchQltyChk> findAllBySchQltyChkSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwSchQltyChk> findAllBySyncFlgAndCrntRecFlg(boolean sFlag, boolean flag);

    public List<MwSchQltyChk> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                            boolean flag);

    @Query(value = "select chk.* " + " from  mw_sch_qlty_chk chk "
            + " join mw_sch_aprsl aprsl on aprsl.sch_aprsl_seq=chk.sch_aprsl_seq and aprsl.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=aprsl.loan_app_seq and app.crnt_rec_flg=1 " + " where chk.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwSchQltyChk> findAllMwSchQltyChkForLoanForPorts(@Param("ports") List<Long> ports,
                                                                 @Param("sts") List<Long> sts);

    @Query(value = "select chk.* " + " from  mw_sch_qlty_chk chk "
            + " join mw_sch_aprsl aprsl on aprsl.sch_aprsl_seq=chk.sch_aprsl_seq and aprsl.crnt_rec_flg=1 "
            + " join mw_loan_app app on app.loan_app_seq=aprsl.loan_app_seq and app.crnt_rec_flg=1 " + " where chk.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and chk.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and chk.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwSchQltyChk> findUpdatedMwSchQltyChkForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                     @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

}
