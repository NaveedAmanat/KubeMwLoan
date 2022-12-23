package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanAppDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MwLoanAppDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwLoanAppDocRepository extends JpaRepository<MwLoanAppDoc, Long> {

    public List<MwLoanAppDoc> findAllByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwLoanAppDoc> findAllByLoanAppSeqAndLoanAppDocSeqAndDocSeqAndCrntRecFlg(long seq, long aseq, long qseq, boolean flag);

    public MwLoanAppDoc findOneByLoanAppDocSeqAndDocSeqAndCrntRecFlg(long seq, long dSeq, boolean flag);

    public MwLoanAppDoc findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(long seq, long dSeq, boolean flag);

    public MwLoanAppDoc findOneByLoanAppSeqAndDocSeqAndLoanAppDocSeqAndCrntRecFlg(long seq, long dSeq, long aSeq, boolean flag);

    public MwLoanAppDoc findOneByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

//    public MwLoanAppDoc findOneByLoanAppSeqAndDocSeqAndCrntRecFlg(long loanAppSeq,long loanAppDocSeq, boolean flag);

    public List<MwLoanAppDoc> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                            boolean flag);

    @Query(value = "select doc.* " + " from  mw_loan_app_doc doc "
            + " join mw_loan_app app on app.loan_app_seq=doc.loan_app_seq and app.crnt_rec_flg=1 " + " where doc.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and app.port_seq IN :ports and app.loan_app_sts in :sts", nativeQuery = true)
    public List<MwLoanAppDoc> findAllMwLoanAppDocForLoanForPorts(@Param("ports") List<Long> ports,
                                                                 @Param("sts") List<Long> sts);

    @Query(value = "select doc.* " + " from  mw_loan_app_doc doc "
            + " join mw_loan_app app on app.loan_app_seq=doc.loan_app_seq and app.crnt_rec_flg=1 " + " where doc.crnt_rec_flg = 1 "
            + "  and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1) "
            + "  and doc.last_upd_dt > :syncDate"
            + "  and app.port_seq IN :ports and doc.last_upd_by != :lastUpdBy  and app.loan_app_sts in :sts "
            + " order by doc.eff_start_dt", nativeQuery = true)
    public List<MwLoanAppDoc> findUpdatedMwLoanAppDocForLoanForPortsLastUpdatedbyNot(@Param("syncDate") Instant syncDate,
                                                                                     @Param("ports") List<Long> ports, @Param("lastUpdBy") String lastUpdBy, @Param("sts") List<Long> sts);

    // Added by Zohaib Asim - Dated 23-08-2021 - MFCIB - Client Last 30 days
    public List<MwLoanAppDoc> findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(Long cnicNo,
                                                                                                                                 Long docSeq,
                                                                                                                                 Boolean crntRecFlg,
                                                                                                                                 Instant effStrtDt, String cmpnyNm);

    public List<MwLoanAppDoc> findMwLoanAppDocByCnicNumAndNomCnicNumIsNullAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(Long cnicNo,
                                                                                                                                                    Long docSeq,
                                                                                                                                                    Boolean crntRecFlg,
                                                                                                                                                    Instant effStrtDt, String cmpnyNm);


    public List<MwLoanAppDoc> findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(Long cnicNo,
                                                                                                                    Long docSeq,
                                                                                                                    Instant effStrtDt, String cmpnyNm);

    public List<MwLoanAppDoc> findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(Long cnicNo,
                                                                                                                                              Long nomCnicNo,
                                                                                                                                              Long docSeq,
                                                                                                                                              Boolean crntRecFlg,
                                                                                                                                              Instant effStrtDt,
                                                                                                                                              String cmpnyNm);

    public List<MwLoanAppDoc> findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(Long cnicNo,
                                                                                                                                 Long nomCnicNo,
                                                                                                                                 Long docSeq,
                                                                                                                                 Instant effStrtDt,
                                                                                                                                 String cmpnyNm);
    // End by Zohaib Asim
}
