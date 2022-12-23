package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwMfcibOthOutsdLoan;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class OtherLoanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public String cmpl_dt;

    public Long crnt_outsd_amt;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String eff_start_dt;

    public String instn_nm;

    public Long loan_app_seq;

    public String loan_prps;

    public Integer mnth_exp_flg;

    public Long oth_outsd_loan_seq;

    public Long tot_loan_amt;

    public String crtd_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer sync_flg;

    public Integer del_flg;

    @Override
    public String toString() {
        return "OtherLoanDto [cmpl_dt=" + cmpl_dt + ", crnt_outsd_amt=" + crnt_outsd_amt + ", crnt_rec_flg=" + crnt_rec_flg + ", crtd_by="
                + crtd_by + ", eff_start_dt=" + eff_start_dt + ", instn_nm=" + instn_nm + ", loan_app_seq=" + loan_app_seq + ", loan_prps="
                + loan_prps + ", mnth_exp_flg=" + mnth_exp_flg + ", oth_outsd_loan_seq=" + oth_outsd_loan_seq + ", tot_loan_amt="
                + tot_loan_amt + "]";
    }

    public MwMfcibOthOutsdLoan DtoToDomain(DateFormat formatter, DateFormat simpleFormatter, String curUser,
                                           MwMfcibOthOutsdLoan mwOtherLoan) {
        try {
            mwOtherLoan.setCmplDt(Common.getZonedInstant(simpleFormatter.parse(cmpl_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mwOtherLoan.setCrntOutsdAmt(crnt_outsd_amt);
        mwOtherLoan.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        mwOtherLoan.setCrtdBy(curUser);
        mwOtherLoan.setCrtdDt(Instant.now());
        mwOtherLoan.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        try {
            mwOtherLoan.setEffStartDt(
                    (eff_start_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(eff_start_dt).toInstant()));
            mwOtherLoan.setCrtdDt((crtd_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            mwOtherLoan.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mwOtherLoan.setInstnNm(instn_nm);
        mwOtherLoan.setLoanAppSeq(loan_app_seq);
        mwOtherLoan.setLoanPrps(loan_prps);
        // mwOtherLoan.setMfcibFlg(mnth_exp_flg);
        mwOtherLoan.setMnthExpFlg((mnth_exp_flg == null) ? false : (mnth_exp_flg == 1) ? true : false);
        mwOtherLoan.setOthOutsdLoanSeq(oth_outsd_loan_seq);
        mwOtherLoan.setTotLoanAmt(tot_loan_amt);
        mwOtherLoan.setLastUpdBy(last_upd_by);
        mwOtherLoan.setCrtdBy(crtd_by);
        return mwOtherLoan;
    }

    public void DomainToDto(MwMfcibOthOutsdLoan mwOtherLoan) {
        cmpl_dt = (mwOtherLoan.getCmplDt() == null) ? "" : Common.GetFormattedDateForTab(mwOtherLoan.getCmplDt(), true);
        crnt_outsd_amt = mwOtherLoan.getCrntOutsdAmt();
        crnt_rec_flg = (mwOtherLoan.isCrntRecFlg() == null) ? 0 : (mwOtherLoan.isCrntRecFlg() ? 1 : 0);
        crtd_by = mwOtherLoan.getCrtdBy();
        eff_start_dt = (mwOtherLoan.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(mwOtherLoan.getEffStartDt(), false);
        instn_nm = mwOtherLoan.getInstnNm();
        loan_app_seq = mwOtherLoan.getLoanAppSeq();
        loan_prps = mwOtherLoan.getLoanPrps();
        mnth_exp_flg = (mwOtherLoan.isMnthExpFlg() == null) ? 0 : (mwOtherLoan.isMnthExpFlg() ? 1 : 0);
        oth_outsd_loan_seq = mwOtherLoan.getOthOutsdLoanSeq();
        tot_loan_amt = mwOtherLoan.getTotLoanAmt();

        crtd_by = mwOtherLoan.getCrtdBy();
        last_upd_by = mwOtherLoan.getLastUpdBy();
        last_upd_dt = (mwOtherLoan.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(mwOtherLoan.getLastUpdDt(), false);
        sync_flg = (mwOtherLoan.getSyncFlg() == null) ? 0 : (mwOtherLoan.getSyncFlg() ? 1 : 0);
        del_flg = (mwOtherLoan.isDelFlg() == null) ? 0 : (mwOtherLoan.isDelFlg() ? 1 : 0);
    }
}