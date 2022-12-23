package com.idev4.loans.repository;

import com.idev4.loans.domain.MwClntHlthInsr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwClntHlthInsr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwClntHlthInsrRepository extends JpaRepository<MwClntHlthInsr, Long> {

    public List<MwClntHlthInsr> findAllByCrntRecFlg(boolean flag);

    public MwClntHlthInsr findOneByLoanAppSeqAndCrntRecFlg(long clientSeq, boolean flag);

    public List<MwClntHlthInsr> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public MwClntHlthInsr findOneByClntHlthInsrSeqAndCrntRecFlg(long insrSeq, boolean flag);

    public List<MwClntHlthInsr> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                              boolean flag);

    @Query(value = "select insr.* " + " from  mw_clnt_hlth_insr insr "
            + " join mw_loan_app app on app.loan_app_seq=insr.loan_app_seq and app.crnt_rec_flg=1 " + " where insr.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwClntHlthInsr> findAllClntHlthInsrForLoanForPorts(@Param("ports") List<Long> ports,
                                                                   @Param("sts") List<Long> sts);

    @Query(value = "select insr.* " + " from  mw_clnt_hlth_insr insr "
            + " join mw_loan_app app on app.loan_app_seq=insr.loan_app_seq and app.crnt_rec_flg=1 " + " where insr.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and insr.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and insr.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwClntHlthInsr> findUpdatedClntHlthInsrForClntForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                       @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
