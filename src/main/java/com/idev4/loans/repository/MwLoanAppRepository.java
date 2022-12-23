package com.idev4.loans.repository;

import com.idev4.loans.domain.MwLoanApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * @modifier, Muhammad.Bassam
 * @date , 19-4-2021
 * @description, findOneByClntSeqAndLoanCyclNum modified for multiple inactive loan records,
 * Verisys status integrated for tablet
 */
@SuppressWarnings("unused")
@Repository
public interface MwLoanAppRepository extends JpaRepository<MwLoanApp, Long> {

    @Query(value = "select * from mw_loan_app la\n" + " join mw_prd prd on la.prd_seq=prd.prd_seq \n"
            + " join mw_ref_cd_val rf on rf.ref_cd_seq=prd.prd_typ_key\n" + " where la.clnt_seq = :clntSeq \n"
            + "   and la.crnt_rec_flg=1 \n" + "   and la.loan_cycl_num = :cycl\n" + "   and prd.crnt_rec_flg=1 \n"
            + "   and  prd.prd_typ_key != '1165' and la.loan_app_sts in (704, 703) and la.loan_cycl_num != -1 and la.prd_seq not in (2, 3, 5, 13, 14, 29)", nativeQuery = true)
    public MwLoanApp findOneByClntSeqAndLoanCyclNum(@Param("clntSeq") long clntSeq, @Param("cycl") long cycl);

    public List<MwLoanApp> findAllByClntSeq(Long clnt);

    public List<MwLoanApp> findAllByClntSeqAndCrntRecFlg(Long clnt, boolean flag);

    public List<MwLoanApp> findAllByCrntRecFlg(boolean flag);

    public MwLoanApp findOneByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public MwLoanApp findOneByClntSeqAndCrntRecFlg(long clientSeq, boolean flag);

    public MwLoanApp findOneByClntSeqAndLoanCyclNumAndCrntRecFlg(long clientSeq, long cyclNum, boolean flag);

    public List<MwLoanApp> findAllByLoanAppSeqAndLoanAppStsInAndCrntRecFlg(Long clntSeq, List<Long> sts, boolean flag);

    public List<MwLoanApp> findAllByClntSeqAndLoanAppSts(Long clnt, long status);

    public List<MwLoanApp> findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc(Long clnt, long status);

    public List<MwLoanApp> findAllByClntSeqAndLoanAppStsAndCrntRecFlgOrderByLoanCyclNumDesc(Long clnt, long status, boolean flag);

    // public List< MwLoanApp > findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc( Long clnt, long status );

    public List<MwLoanApp> findAllByClntSeqAndCrntRecFlgOrderByLoanCyclNumDesc(Long clnt, boolean flag);

    public List<MwLoanApp> findAllByClntSeqAndCrntRecFlgAndLoanAppStsNotOrderByLoanCyclNumDesc(Long clnt, boolean flag, long loanAppSts);

    public MwLoanApp findOneByClntSeqAndSyncFlgAndCrntRecFlg(long clientSeq, boolean sflag, boolean flag);

    public List<MwLoanApp> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwLoanApp> findAllByPrntLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwLoanApp> findAllByPrntLoanAppSeqAndClntSeqAndCrntRecFlg(long seq, long clntSeq, boolean flag);

    public List<MwLoanApp> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlgAndPortSeqInAndLoanAppStsInOrderByEffStartDt(
            Instant lastUpdDt, String lastUpdBy, boolean flag, List<Long> portKey, List<Long> sts);

    public List<MwLoanApp> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseAndCrntRecFlgAndPortSeqIn(Instant lastUpdDt, String lastUpdBy,
                                                                                                  boolean flag, List<Long> portKey);

    public List<MwLoanApp> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                         boolean flag);

    @Query(value = "select app.*, (select (SELECT v.cnic_category || ' - ' || case when v.vremarks is null then decode(v.kashf_status,'N','Verification Pending','Y','Verified','E', 'Verification Failed') else 'Verification Failed' end \r\n"
            + "          FROM mw_cnic_verisys v\r\n"
            + "         where v.cnic_category = 'CLIENT'\r\n"
            + "           and v.loan_app_seq = app.loan_app_seq\r\n"
            + "           and ver_seq = (select max(ver_seq)\r\n"
            + "                            FROM mw_cnic_verisys vv\r\n"
            + "                           where vv.loan_app_seq = v.loan_app_seq\r\n"
            + "                             and vv.cnic_category = 'CLIENT')\r\n"
            + "         GROUP BY v.cnic_category, kashf_status, VREMARKS) || ' / ' ||\r\n"
            + "       (SELECT v.cnic_category || ' - ' ||\r\n"
            + "               decode(v.kashf_status,\r\n"
            + "                      'N',\r\n"
            + "                      'Verification Pending',\r\n"
            + "                      'Y',\r\n"
            + "                      'Verified',\r\n"
            + "                      'E',\r\n"
            + "                      'Verification Failed')\r\n"
            + "          FROM mw_cnic_verisys v\r\n"
            + "         where v.cnic_category = 'NOMINEE'\r\n"
            + "           and v.loan_app_seq = app.loan_app_seq\r\n"
            + "           and ver_seq = (select max(ver_seq)\r\n"
            + "                            FROM mw_cnic_verisys vv\r\n"
            + "                           where vv.loan_app_seq = v.loan_app_seq\r\n"
            + "                             and vv.cnic_category = 'NOMINEE')\r\n"
            + "         GROUP BY v.cnic_category, kashf_status, VREMARKS)\r\n"
            + "  from dual) verisys_status  from  mw_loan_app app" + " join mw_prd prd on prd.PRD_SEQ=app.PRD_SEQ and prd.CRNT_REC_FLG=1"
            + " join mw_ref_cd_val val on val.ref_cd_seq = prd.prd_typ_key and val.crnt_rec_flg=1 and val.ref_cd!='1165'"
            + " where app.crnt_rec_flg = 1"
            + " and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1)"
            + " and app.port_seq IN :ports  and app.loan_app_sts in :sts order by app.eff_start_dt", nativeQuery = true)
    public List<MwLoanApp> findAllLoansForPorts(@Param("ports") List<Long> ports, @Param("sts") List<Long> sts);

    public List<MwLoanApp> findAllByClntSeqAndCrntRecFlgOrderByLoanAppStsDtDesc(Long clntSeq, boolean flag);

    public List<MwLoanApp> findAllByClntSeqAndLoanAppStsNotAndCrntRecFlg(Long clntSeq, Long stsSeq, boolean flag);

    // Modified by Rizwan Mahfooz - Dated 07-04-2022 - Loan App Seq
    @Query(value = "select app.*  from  mw_loan_app app" + " join mw_prd prd on prd.PRD_SEQ=app.PRD_SEQ and prd.CRNT_REC_FLG=1"
            + " join mw_ref_cd_val val on val.ref_cd_seq = prd.prd_typ_key and val.crnt_rec_flg=1 and val.ref_cd!='1165'"
            + " where app.crnt_rec_flg = 1" + " and app.loan_cycl_num =:cyclNum "
            + " and app.clnt_seq = :clientSeq and app.loan_app_seq = :loanAppSeq order by app.eff_start_dt", nativeQuery = true)
    public MwLoanApp findOneByClntSeqAndLoanCyclNumAndCrntRecFlgForConventionalProduct(@Param("clientSeq") long clientSeq,
                                                                                       @Param("cyclNum") long cyclNum, @Param("loanAppSeq") long loanAppSeq);

    @Query(value = "select grp.prd_grp_nm  from  mw_loan_app app"
            + " join mw_prd prd on prd.PRD_SEQ=app.PRD_SEQ and prd.CRNT_REC_FLG=1"
            + " join mw_prd_grp grp on grp.prd_grp_seq = prd.prd_grp_seq and grp.crnt_rec_flg=1 "
            + " where app.crnt_rec_flg = 1" + " and app.loan_cycl_num =:cyclNum "
            + " and app.loan_app_seq = :loanAppSeq order by app.eff_start_dt", nativeQuery = true)
    public String findProductOfPreviousLoan(@Param("loanAppSeq") long loanAppSeq, @Param("cyclNum") long cyclNum);

    @Query(value = "SELECT COUNT (1)\n" +
            "  FROM MW_PYMT_SCHED_HDR  psh\n" +
            "       JOIN MW_PYMT_SCHED_DTL psd\n" +
            "           ON     psd.PYMT_SCHED_HDR_SEQ = psh.PYMT_SCHED_HDR_SEQ\n" +
            "              AND psd.CRNT_REC_FLG = 1\n" +
            " WHERE     psh.CRNT_REC_FLG = 1\n" +
            "       AND psd.PYMT_STS_KEY IN (945, 1145)\n" +
            "       AND psh.LOAN_APP_SEQ = :loanAppSeq", nativeQuery = true)
    public String findRemainingInstallmentOfPreviousLoan(@Param("loanAppSeq") long loanAppSeq);

    @Query(value = "select LOAN_APP_OST(:loanAppSeq, SYSDATE, 'psc') from dual", nativeQuery = true)
    public Long findRemainingOutstandingOfPreviousLoan(@Param("loanAppSeq") long loanAppSeq);

    public List<MwLoanApp> findAllByClntSeqAndPortSeqAndLoanAppStsAndCrntRecFlg(long clntSeq, long portSeq, long loanAppSts, boolean flag);

    @Query(value = "SELECT max(app.APRVD_LOAN_AMT)\n" +
            "  FROM mw_loan_app app\n" +
            " WHERE     app.crnt_rec_flg = 1\n" +
            "       AND app.loan_cycl_num = :cyclNum and app.prd_seq != 29\n" +
            "       AND app.clnt_seq = :clientSeq", nativeQuery = true)
    public String findPreviousLoanAmount(@Param("clientSeq") long clientSeq, @Param("cyclNum") long cyclNum);


    @Query(value = "  SELECT APP.*\n" +
            "    FROM MW_LOAN_APP APP\n" +
            "   WHERE     APP.CLNT_SEQ = :P_CLNT_SEQ\n" +
            "         AND APP.LOAN_APP_STS IN (700, 702, 703, 704)\n" +
            "         AND APP.CRNT_REC_FLG = 1\n" +
            "         AND APP.LOAN_CYCL_NUM <\n" +
            "             (SELECT MAX (AP.LOAN_CYCL_NUM)\n" +
            "                FROM MW_LOAN_APP AP\n" +
            "               WHERE     AP.CLNT_SEQ = :P_CLNT_SEQ\n" +
            "                     AND AP.LOAN_APP_STS IN (700, 702, 703, 704)\n" +
            "                     AND AP.CRNT_REC_FLG = 1)\n" +
            "         AND APP.PRD_SEQ NOT IN (29) " +
            "ORDER BY APP.LOAN_CYCL_NUM DESC, APP.EFF_START_DT DESC", nativeQuery = true)
    public List<MwLoanApp> findLoansByClntSeq(@Param("P_CLNT_SEQ") long clientSeq);
}
