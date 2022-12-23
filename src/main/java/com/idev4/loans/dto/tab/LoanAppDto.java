package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class LoanAppDto implements Serializable {


    private static final long serialVersionUID = 1L;

    public Double aprvd_loan_amt;

    public Long clnt_seq;

    public Integer crnt_rec_flg;

    public String crtd_dt;

    public String eff_start_dt;

    public Long loan_app_seq;

    public Long loan_app_sts;

    public Long loan_cycl_num;

    public Long port_seq;

    public Long prd_seq;

    public Double rcmnd_loan_amt;

    public Double rqstd_loan_amt;

    public String crtd_by;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer sync_flg;

    public String cmnt;

    public Integer del_flg;

    public Integer tbl_scrn_flg;

    public Integer rel_addr_as_clnt_flg;

    public Integer co_bwr_addr_as_clnt_flg;

    public String loan_app_sts_dt;

    public Long loan_utl_sts_seq;

    public String loan_utl_cmnt;

    public String app_vrsn, verisys_status;

    public Integer is_krk_valid;

    public Integer is_hil_valid;

    //ADDED BY YOUSAF DATED: 03-OCT-2022
    public Integer is_ktk_valid;
    // ENDED

    public Integer loan_app_sts_sync;

    @Override
    public String toString() {
        return "LoanAppDto{" +
                "aprvd_loan_amt=" + aprvd_loan_amt +
                ", clnt_seq=" + clnt_seq +
                ", crnt_rec_flg=" + crnt_rec_flg +
                ", crtd_dt='" + crtd_dt + '\'' +
                ", eff_start_dt='" + eff_start_dt + '\'' +
                ", loan_app_seq=" + loan_app_seq +
                ", loan_app_sts=" + loan_app_sts +
                ", loan_cycl_num=" + loan_cycl_num +
                ", port_seq=" + port_seq +
                ", prd_seq=" + prd_seq +
                ", rcmnd_loan_amt=" + rcmnd_loan_amt +
                ", rqstd_loan_amt=" + rqstd_loan_amt +
                ", crtd_by='" + crtd_by + '\'' +
                ", last_upd_by='" + last_upd_by + '\'' +
                ", last_upd_dt='" + last_upd_dt + '\'' +
                ", sync_flg=" + sync_flg +
                ", cmnt='" + cmnt + '\'' +
                ", del_flg=" + del_flg +
                ", tbl_scrn_flg=" + tbl_scrn_flg +
                ", rel_addr_as_clnt_flg=" + rel_addr_as_clnt_flg +
                ", co_bwr_addr_as_clnt_flg=" + co_bwr_addr_as_clnt_flg +
                ", loan_app_sts_dt='" + loan_app_sts_dt + '\'' +
                ", loan_utl_sts_seq=" + loan_utl_sts_seq +
                ", loan_utl_cmnt='" + loan_utl_cmnt + '\'' +
                ", app_vrsn='" + app_vrsn + '\'' +
                ", verisys_status='" + verisys_status + '\'' +
                ", is_krk_valid=" + is_krk_valid +
                ", is_hil_valid=" + is_hil_valid +
                ", is_ktk_valid=" + is_ktk_valid + //ADDED BY YOUSAF DATED: 03-OCT-2022
                ", loan_app_sts_sync=" + loan_app_sts_sync + //Added by Zohaib Asim - Dated 19-Nov-2022
                '}';
    }

    public MwLoanApp DtoToDomain(DateFormat formatter, MwLoanApp loan) {
        loan.setAprvdLoanAmt(aprvd_loan_amt);
        loan.setClntSeq(clnt_seq);
        // loan.setCmnt();
        loan.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        try {
            loan.setLastUpdDt(Instant.now());
            loan.setEffStartDt(Instant.now());
            loan.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        loan.setCrtdBy(crtd_by);
        loan.setLastUpdBy(last_upd_by);
        loan.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        loan.setLoanAppId(loan_app_seq);
        loan.setLoanAppSeq(loan_app_seq);
        loan.setLoanAppSts(loan_app_sts);
        loan.setLoanCyclNum(loan_cycl_num);
        loan.setLoanId(loan_app_seq + "");
        loan.setPortSeq(port_seq);
        loan.setPrdSeq(prd_seq);
        // loan.setPscScore(ps);
        loan.setRcmndLoanAmt(rcmnd_loan_amt);
        // loan.setRejectionReasonCd(r);
        loan.setRqstdLoanAmt(rqstd_loan_amt);
        loan.setTblScrn((tbl_scrn_flg == null) ? false : (tbl_scrn_flg == 1) ? true : false);

        loan.setCmnt(cmnt);
        loan.setLoanAppStsDt(Instant.now());
        loan.setPrntLoanAppSeq(loan_app_seq);
        loan.setRelAddrAsClntFLg((rel_addr_as_clnt_flg == null) ? false : (rel_addr_as_clnt_flg == 1) ? true : false);
        loan.setCoBwrAddrAsClntFlg((co_bwr_addr_as_clnt_flg == null) ? false : (co_bwr_addr_as_clnt_flg == 1) ? true : false);
        loan.setAppVrsn(app_vrsn);
        loan.setIsKrkValid(is_krk_valid);
        loan.setIsHilValid(is_hil_valid);
        //ADDED BY YOUSAF DATED: 03-OCT-2022
        loan.setIsKTKValid(is_ktk_valid);
        loan.setLoanAppStsSync(loan_app_sts_sync);
        return loan;
    }

    /*
     * @auther: Zohaib Asim
     * @date: 25-11-2021
     * @purpose: System Controls Discard Application after 30Days
     * */
    public MwLoanApp DtoToDomain(DateFormat formatter, MwLoanApp loan, LoanAppDto tabletLoanApp) {
        loan.setAprvdLoanAmt(aprvd_loan_amt);
        loan.setClntSeq(clnt_seq);
        // loan.setCmnt();
        loan.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        try {
            loan.setLastUpdDt(Instant.now());

            // Date
            Date effStartDt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(tabletLoanApp.eff_start_dt);
            loan.setEffStartDt(effStartDt.toInstant());

            loan.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        loan.setCrtdBy(crtd_by);
        loan.setLastUpdBy(last_upd_by);
        loan.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        loan.setLoanAppId(loan_app_seq);
        loan.setLoanAppSeq(loan_app_seq);
        loan.setLoanAppSts(loan_app_sts);
        loan.setLoanCyclNum(loan_cycl_num);
        loan.setLoanId(loan_app_seq + "");
        loan.setPortSeq(port_seq);
        loan.setPrdSeq(prd_seq);
        // loan.setPscScore(ps);
        loan.setRcmndLoanAmt(rcmnd_loan_amt);
        // loan.setRejectionReasonCd(r);
        loan.setRqstdLoanAmt(rqstd_loan_amt);
        loan.setTblScrn((tbl_scrn_flg == null) ? false : (tbl_scrn_flg == 1) ? true : false);

        loan.setCmnt(cmnt);
        loan.setLoanAppStsDt(Instant.now());
        loan.setPrntLoanAppSeq(loan_app_seq);
        loan.setRelAddrAsClntFLg((rel_addr_as_clnt_flg == null) ? false : (rel_addr_as_clnt_flg == 1) ? true : false);
        loan.setCoBwrAddrAsClntFlg((co_bwr_addr_as_clnt_flg == null) ? false : (co_bwr_addr_as_clnt_flg == 1) ? true : false);
        loan.setAppVrsn(app_vrsn);
        loan.setIsKrkValid(is_krk_valid);
        loan.setIsHilValid(is_hil_valid);
        loan.setIsKTKValid(is_ktk_valid); //ADDED BY YOUSAF DATED: 03-OCT-2022
        loan.setLoanAppStsSync(loan_app_sts_sync); // Zohaib Asim - Dated 19-Nov-2022
        return loan;
    }
    // End

    public void DomainToDto(MwLoanApp loan) {
        aprvd_loan_amt = loan.getAprvdLoanAmt();
        clnt_seq = loan.getClntSeq();
        crnt_rec_flg = (loan.getCrntRecFlg() == null) ? 0 : (loan.getCrntRecFlg() ? 1 : 0);
        crtd_dt = (loan.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(loan.getCrtdDt(), false);
        eff_start_dt = (loan.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(loan.getEffStartDt(), false);
        loan_app_seq = loan.getLoanAppSeq();
        loan_app_sts = loan.getLoanAppSts();
        loan_cycl_num = loan.getLoanCyclNum();
        port_seq = loan.getPortSeq();
        prd_seq = loan.getPrdSeq();
        rcmnd_loan_amt = loan.getRcmndLoanAmt();
        rqstd_loan_amt = loan.getRqstdLoanAmt();

        crtd_by = loan.getCrtdBy();
        last_upd_by = loan.getLastUpdBy();
        last_upd_dt = (loan.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(loan.getLastUpdDt(), false);
        sync_flg = (loan.getSyncFlg() == null) ? 0 : (loan.getSyncFlg() ? 1 : 0);
        cmnt = loan.getCmnt();
        del_flg = (loan.isDelFlg() == null) ? 0 : (loan.isDelFlg() ? 1 : 0);

        tbl_scrn_flg = (loan.getTblScrn() == null) ? 0 : (loan.getTblScrn() ? 1 : 0);
        rel_addr_as_clnt_flg = (loan.getRelAddrAsClntFLg() == null) ? 0 : (loan.getRelAddrAsClntFLg() ? 1 : 0);
        co_bwr_addr_as_clnt_flg = (loan.getCoBwrAddrAsClntFlg() == null) ? 0 : (loan.getCoBwrAddrAsClntFlg() ? 1 : 0);

        loan_app_sts_dt = (loan.getLoanAppStsDt() == null) ? "" : Common.GetFormattedDateForTab(loan.getLoanAppStsDt(), false);
        loan_utl_sts_seq = loan.getLoanUtlStsSeq();
        loan_utl_cmnt = loan.getLoanUtlCmnt();
        app_vrsn = loan.getAppVrsn();
        is_krk_valid = loan.getIsKrkValid();
        loan.setIsHilValid(is_hil_valid);
        //ADDED BY YOUSAF DATED: 03-OCT-2022
        loan.setIsKTKValid(is_ktk_valid);
        //is_ktk_valid = loan.getIsKTKValid();

        loan_app_sts_sync = loan.getLoanAppStsSync(); // Zohaib Asim - Dated 19-Nov-2022
    }
}
