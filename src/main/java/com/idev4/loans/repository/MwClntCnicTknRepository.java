package com.idev4.loans.repository;

import com.idev4.loans.domain.MwCnicTkn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwCnicTkn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwClntCnicTknRepository extends JpaRepository<MwCnicTkn, Long> {

    public MwCnicTkn findOneByLoanAppSeqAndCrntRecFlg(Long loanAppSeq, boolean flag);

    public List<MwCnicTkn> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwCnicTkn> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                         boolean flag);

    @Query(value = "select tkn.CNIC_TKN_SEQ, tkn.EFF_START_DT, tkn.LOAN_APP_SEQ, tkn.CNIC_TKN_NUM, tkn.CNIC_TKN_EXPRY_DT, tkn.CRTD_BY, tkn.CRTD_DT, tkn.LAST_UPD_BY, tkn.LAST_UPD_DT, tkn.DEL_FLG, tkn.EFF_END_DT, tkn.CRNT_REC_FLG, tkn.SYNC_FLG "
            + " from  mw_cnic_tkn tkn " + " join mw_loan_app app on app.loan_app_seq=tkn.loan_app_seq and app.crnt_rec_flg=1 "
            + " where tkn.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1)"
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwCnicTkn> findAllTknForLoanForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    @Query(value = "select tkn.CNIC_TKN_SEQ, tkn.EFF_START_DT, tkn.LOAN_APP_SEQ, tkn.CNIC_TKN_NUM, tkn.CNIC_TKN_EXPRY_DT, tkn.CRTD_BY, tkn.CRTD_DT, tkn.LAST_UPD_BY, tkn.LAST_UPD_DT, tkn.DEL_FLG, tkn.EFF_END_DT, tkn.CRNT_REC_FLG, tkn.SYNC_FLG "
            + " from  mw_cnic_tkn tkn " + " join mw_loan_app app on app.loan_app_seq=tkn.loan_app_seq and app.crnt_rec_flg=1 "
            + " where tkn.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1)"
            + "  and tkn.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and tkn.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwCnicTkn> findUpdatedTknForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                         @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
