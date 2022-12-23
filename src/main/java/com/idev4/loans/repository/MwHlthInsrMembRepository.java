package com.idev4.loans.repository;

import com.idev4.loans.domain.MwHlthInsrMemb;
import com.idev4.loans.ids.MwHlthInsrMembId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwHlthInsrMemb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwHlthInsrMembRepository extends JpaRepository<MwHlthInsrMemb, MwHlthInsrMembId> {

    public List<MwHlthInsrMemb> findAllByLoanAppSeqAndCrntRecFlg(Long loanAppSeq, boolean flag);

    public MwHlthInsrMemb findOneByHlthInsrMembSeqAndCrntRecFlg(Long mwClntHlthInsr, boolean flag);

    //Added by Areeba
    public MwHlthInsrMemb findOneByHlthInsrMembSeqAndLoanAppSeqAndCrntRecFlg(Long mwClntHlthInsr, Long loanAppSeq, boolean flag);
    //Ended by Areeba

    public List<MwHlthInsrMemb> findAllByCrntRecFlg(boolean flag);

    public List<MwHlthInsrMemb> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwHlthInsrMemb> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                              boolean flag);

    @Query(value = "select insr.* " + " from  mw_hlth_insr_memb insr "
            + " join mw_loan_app app on app.loan_app_seq=insr.loan_app_seq and app.crnt_rec_flg=1 " + " where insr.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwHlthInsrMemb> findAllMwHlthInsrMembForLoanForPorts(@Param("ports") List<Long> ports,
                                                                     @Param("sts") List<Long> sts);

    @Query(value = "select insr.* " + " from  mw_hlth_insr_memb insr "
            + " join mw_loan_app app on app.loan_app_seq=insr.loan_app_seq and app.crnt_rec_flg=1 "
            + " where (insr.crnt_rec_flg = 1 or insr.del_flg = 1)"
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and insr.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and insr.last_upd_by != :lastUpdBy and app.loan_app_sts in :sts"
            + " order by insr.eff_start_dt", nativeQuery = true)
    public List<MwHlthInsrMemb> findUpdatedMwHlthInsrMembForClntForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                         @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);
}
