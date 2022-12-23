package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwLoanUtlPlan;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class ExpextedLoanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String crtd_dt;

    public Integer del_flg;

    public String eff_start_dt;

    public String last_upd_by;

    public Long loan_app_seq;

    public Double loan_utl_amt;

    public String loan_utl_dscr;

    public Long loan_utl_plan_seq;

    public Long loan_utl_typ;

    public String last_upd_dt;

    public Integer sync_flg;

    public Integer typ_flg;

    @Override
    public String toString() {
        return "ExpextedLoanDto [crnt_rec_flg=" + crnt_rec_flg + ", crtd_by=" + crtd_by + ", crtd_dt=" + crtd_dt + ", del_flg=" + del_flg
                + ", eff_start_dt=" + eff_start_dt + ", last_upd_by=" + last_upd_by + ", loan_app_seq=" + loan_app_seq + ", loan_utl_amt="
                + loan_utl_amt + ", loan_utl_dscr=" + loan_utl_dscr + ", loan_utl_plan_seq=" + loan_utl_plan_seq + ", loan_utl_typ="
                + loan_utl_typ + "]";
    }

    public MwLoanUtlPlan DtoToDomain(DateFormat formatter, MwLoanUtlPlan util) {
        util.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        util.setCrtdBy(crtd_by);
        try {
            util.setCrtdDt((crtd_dt) == null ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            util.setEffStartDt(
                    (eff_start_dt) == null ? Instant.now() : Common.getZonedInstant(formatter.parse(eff_start_dt).toInstant()));

            util.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        util.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        util.setLoanAppSeq(loan_app_seq);
        util.setLoanUtlAmt(loan_utl_amt);
        util.setLoanUtlDscr(loan_utl_dscr);
        util.setLoanUtlPlanSeq(loan_utl_plan_seq);
        util.setLoanUtlTyp(loan_utl_typ);
        util.setLastUpdBy(last_upd_by);
        util.setTypFlg(typ_flg);
        return util;
    }

    public void DomainToDto(MwLoanUtlPlan utl) {
        crnt_rec_flg = (utl.isCrntRecFlg() == null) ? 0 : (utl.isCrntRecFlg() ? 1 : 0);
        crtd_by = utl.getCrtdBy();
        crtd_dt = (utl.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(utl.getCrtdDt(), false);
        del_flg = (utl.isDelFlg() == null) ? 0 : (utl.isDelFlg() ? 1 : 0);
        eff_start_dt = (utl.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(utl.getEffStartDt(), false);
        last_upd_by = utl.getLastUpdBy();
        loan_app_seq = utl.getLoanAppSeq();
        loan_utl_amt = utl.getLoanUtlAmt();
        loan_utl_dscr = utl.getLoanUtlDscr();
        loan_utl_plan_seq = utl.getLoanUtlPlanSeq();
        loan_utl_typ = utl.getLoanUtlTyp();
        last_upd_dt = (utl.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(utl.getLastUpdDt(), false);
        sync_flg = (utl.getSyncFlg() == null) ? 0 : (utl.getSyncFlg() ? 1 : 0);
        typ_flg = utl.getTypFlg();
    }
}
