package com.idev4.loans.repository;

import com.idev4.loans.domain.MwSchAsts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwSchAstsRepository extends JpaRepository<MwSchAsts, Long> {

    public List<MwSchAsts> findAllByCrntRecFlg(boolean flag);

    public MwSchAsts findOneBySchAstsSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchAsts findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(long seq, boolean delFlag, boolean crntFlag);

    public List<MwSchAsts> findAllBySchAstsSeq(long seq);

    public List<MwSchAsts> findAllBySyncFlgAndCrntRecFlg(boolean sFlag, boolean flag);

    public List<MwSchAsts> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                         boolean flag);

    @Query(value = "select  ast.*" + " from mw_sch_asts ast   "
            + " join mw_loan_app app on app.loan_app_seq=ast.loan_app_seq and app.crnt_rec_flg=1 " + "where ast.crnt_rec_flg=1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwSchAsts> findAllMwSchAstsForLoanForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> st);

    @Query(value = "select  ast.*" + " from mw_sch_asts ast   "
            + " join mw_loan_app app on app.loan_app_seq=ast.loan_app_seq and app.crnt_rec_flg=1 " + "where ast.crnt_rec_flg=1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and ast.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and ast.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwSchAsts> findUpdatedMwSchAstsForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                               @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> st);
}
