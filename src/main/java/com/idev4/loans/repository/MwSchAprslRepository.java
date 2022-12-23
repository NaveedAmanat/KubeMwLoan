package com.idev4.loans.repository;

import com.idev4.loans.domain.MwSchAprsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwSchAprslRepository extends JpaRepository<MwSchAprsl, Long> {

    public List<MwSchAprsl> findAllByCrntRecFlg(boolean flag);

    public MwSchAprsl findOneBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchAprsl findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(long seq, boolean delFlag, boolean crntFlag);

    public List<MwSchAprsl> findAllBySchAprslSeq(long seq);

    public List<MwSchAprsl> findAllBySyncFlgAndCrntRecFlg(boolean sFlag, boolean flag);

    public List<MwSchAprsl> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                          boolean flag);

    @Query(value = "select sa.*" + " from mw_sch_aprsl sa "
            + " join mw_loan_app app on app.loan_app_seq=sa.loan_app_seq and app.crnt_rec_flg=1 " + " where sa.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwSchAprsl> findAllMwSchAprslForLoanForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> st);

    @Query(value = "select sa.*" + " from mw_sch_aprsl sa "
            + " join mw_loan_app app on app.loan_app_seq=sa.loan_app_seq and app.crnt_rec_flg=1 " + " where sa.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and sa.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and sa.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwSchAprsl> findUpdatedMwSchAprslForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                 @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> st);
}
