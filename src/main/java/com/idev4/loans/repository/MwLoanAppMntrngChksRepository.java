package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppMntrngChks;
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
public interface MwLoanAppMntrngChksRepository extends JpaRepository<MwLoanAppMntrngChks, Long> {

    public MwLoanAppMntrngChks findOneByLoanAppMntrngChksSeqAndCrntRecFlg(long seq, boolean flag);

    public MwLoanAppMntrngChks findOneByLoanAppMntrngChksSeqAndCrtdByAndCrntRecFlg(long seq, String crtdBy, boolean flag);

    public MwLoanAppMntrngChks findOneByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public MwLoanAppMntrngChks findOneByLoanAppSeqAndCrtdByAndCrntRecFlg(long seq, String crtdBy, boolean flag);

    public MwLoanAppMntrngChks findOneByLoanAppSeqAndChkFlgAndCrntRecFlg(long seq, Integer chkFlg, boolean flag);

    public List<MwLoanAppMntrngChks> findAllByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwLoanAppMntrngChks> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                                   boolean flag);

    @Query(value = "select m.* from MW_LOAN_APP_MNTRNG_CHKS m "
            + "    join mw_loan_app app on app.loan_app_seq = m.loan_app_seq and app.crnt_rec_flg=1 "
            + " where (m.crnt_rec_flg = 1 OR m.del_flg = 1 )"
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwLoanAppMntrngChks> findAllMwLoanAppMntrngChksForLoanForPorts(@Param("ports") List<Long> ports,
                                                                               @Param("sts") List<Long> sts);

    @Query(value = "select m.* from MW_LOAN_APP_MNTRNG_CHKS m "
            + "    join mw_loan_app app on app.loan_app_seq = m.loan_app_seq and app.crnt_rec_flg=1 "
            + " where (m.crnt_rec_flg = 1 OR m.del_flg = 1 )"
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and m.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and m.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts "
            + " order by m.eff_start_dt", nativeQuery = true)
    public List<MwLoanAppMntrngChks> findUpdatedMwLoanAppMntrngChksForLoanForPortsLastUpdatedbyNot(
            @Param("syncDate") Instant syncDate, @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy,
            @Param("sts") List<Long> sts);

//
//    @Query ( value = "select m.* from MW_LOAN_APP_MNTRNG_CHKS m where m.LOAN_APP_SEQ = :loan_app_seq " +
//            "and m.chk_flg = :chk_flg and crnt_rec_flg=:crnt_rec_flg", nativeQuery = true )
//    public MwLoanAppMntrngChks findOneByLoanAppSeqAndChkFlgAndCrntRecFlg(
//            @Param ( "loan_app_seq" ) long loan_app_seq, @Param ( "chk_flg" ) Long  chk_flg,
//            @Param ( "crnt_rec_flg" ) boolean crnt_rec_flg);


}
