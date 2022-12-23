package com.idev4.loans.repository;

import com.idev4.loans.domain.MwCnicUpd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwCnicUpdRepository extends JpaRepository<MwCnicUpd, Long> {

    MwCnicUpd findOneByLoanAppSeq(long loanAppSeq);

    @Query(value = " select upd.* " + " from mw_cnic_upd upd "
            + " join mw_loan_app app on app.loan_app_seq=upd.loan_app_seq and app.crnt_rec_flg=1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  where app.port_seq IN :ports", nativeQuery = true)
    public List<MwCnicUpd> findAllMwCnicUpdForLoanForPorts(@Param("ports") List<Long> ports);

    @Query(value = " select upd.* " + " from mw_cnic_upd upd "
            + " join mw_loan_app app on app.loan_app_seq=upd.loan_app_seq and app.crnt_rec_flg=1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  where upd.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and upd.last_upd_by != :lastUpdBy ", nativeQuery = true)
    public List<MwCnicUpd> findUpdatedMwCnicUpdForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                               @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy);

}
