package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppCrdtScr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwLoanApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwLoanAppCrdtScrRepository extends JpaRepository<MwLoanAppCrdtScr, Long> {

    public MwLoanAppCrdtScr findOneByLoanAppCrdtScrSeqAndCrntRecFlg(long seq, boolean flg);

    public List<MwLoanAppCrdtScr> findAllByCrntRecFlgAndLoanAppSeqIn(boolean flg, List<Long> seq);

    public MwLoanAppCrdtScr findOneByCrntRecFlgAndLoanAppSeq(boolean flg, Long seq);

    // public List< MwLoanAppCrdtScr > findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg( Instant lastUpdDt, String lastUpdBy,
    // boolean flag );
    //
    @Query(value = "select c.* from MW_LOAN_APP_CRDT_SCR c\r\n"
            + "join mw_loan_app app on app.loan_app_seq=c.loan_app_seq and app.crnt_rec_flg = 1\r\n"
            + "join mw_prd prd on prd.PRD_SEQ=app.PRD_SEQ and prd.CRNT_REC_FLG=1\r\n"
            + "             join mw_ref_cd_val val on val.ref_cd_seq = prd.prd_typ_key and val.crnt_rec_flg=1 and val.ref_cd!='1165'\r\n"
            + "             where c.crnt_rec_flg = 1\r\n"
            + "             and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1)\r\n"
            + "             and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwLoanAppCrdtScr> findAllLoansCrdtScrForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select c.* from MW_LOAN_APP_CRDT_SCR c\r\n"
            + "join mw_loan_app app on app.loan_app_seq=c.loan_app_seq and app.crnt_rec_flg = 1\r\n"
            + "join mw_prd prd on prd.PRD_SEQ=app.PRD_SEQ and prd.CRNT_REC_FLG=1\r\n"
            + "             join mw_ref_cd_val val on val.ref_cd_seq = prd.prd_typ_key and val.crnt_rec_flg=1 and val.ref_cd!='1165'\r\n"
            + "             where c.crnt_rec_flg = 1\r\n"
            + "             and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1)\r\n"
            + "             and app.port_seq IN :ports and c.last_upd_dt > :syncDate "
            + "         and c.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwLoanAppCrdtScr> findUpdatedLoansCrdtScrForPorts(@Param("ports") List<Long> ports,
                                                                  @Param("syncDate") Instant syncDate, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    public MwLoanAppCrdtScr findOneByLoanAppSeqAndCrntRecFlg(long seq, boolean flg);

}
