package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwClntHlthInsr;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class InsuranceInfoHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long clnt_hlth_insr_seq;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public Integer del_flg;

    public String eff_start_dt;

    public Long excl_ctgry_key;

    public Integer hlth_insr_flg;

    public Long hlth_insr_plan_seq;

    public Long loan_app_seq;

    public String main_bread_earner_nm;

    public Long rel_wth_bread_earner_key;

    public Integer sync_flg;

    public String last_upd_by;

    @Override
    public String toString() {
        return "InsuranceInfoHeader [clnt_hlth_insr_seq=" + clnt_hlth_insr_seq + ", crnt_rec_flg=" + crnt_rec_flg + ", crtd_by=" + crtd_by
                + ", del_flg=" + del_flg + ", eff_start_dt=" + eff_start_dt + ", excl_ctgry_key=" + excl_ctgry_key + ", hlth_insr_flg="
                + hlth_insr_flg + ", hlth_insr_plan_seq=" + hlth_insr_plan_seq + ", loan_app_seq=" + loan_app_seq
                + ", main_bread_earner_nm=" + main_bread_earner_nm + ", rel_wth_bread_earner_key=" + rel_wth_bread_earner_key + "]";
    }

    public MwClntHlthInsr DtoToDomain(DateFormat formatter, MwClntHlthInsr insr) {
        insr.setClntHlthInsrSeq(clnt_hlth_insr_seq);
        insr.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        insr.setCrtdBy(crtd_by);
        insr.setDelFlg((del_flg == null) ? true : (del_flg == 1) ? true : false);
        try {
            insr.setCrtdDt(Common.getZonedInstant(formatter.parse(eff_start_dt).toInstant()));
            insr.setEffStartDt(Instant.now());
            insr.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        insr.setExclCtgryKey(excl_ctgry_key);
        insr.setHlthInsrFlg((hlth_insr_flg == null) ? false : (hlth_insr_flg == 1) ? true : false);
        insr.setLoanAppSeq(loan_app_seq);
        insr.setMainBreadEarnerNm(main_bread_earner_nm);
        insr.setMwHlthInsrPlan(hlth_insr_plan_seq);
        insr.setRelWthBreadEarnerKey(rel_wth_bread_earner_key);
        insr.setLastUpdBy(last_upd_by);
        return insr;
    }

    public void DomainToDto(MwClntHlthInsr insr) {
        InsuranceInfoHeader dto = new InsuranceInfoHeader();
        clnt_hlth_insr_seq = insr.getClntHlthInsrSeq();
        crnt_rec_flg = (insr.getCrntRecFlg() == null) ? 0 : (insr.getCrntRecFlg() ? 1 : 0);
        crtd_by = insr.getCrtdBy();
        del_flg = (insr.getDelFlg() == null) ? 0 : (insr.getDelFlg() ? 1 : 0);
        eff_start_dt = (insr.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(insr.getEffStartDt(), false);
        excl_ctgry_key = insr.getExclCtgryKey();
        hlth_insr_flg = (insr.getHlthInsrFlg() == null) ? 0 : (insr.getHlthInsrFlg() ? 1 : 0);
        hlth_insr_plan_seq = insr.getMwHlthInsrPlan();
        loan_app_seq = insr.getLoanAppSeq();
        main_bread_earner_nm = insr.getMainBreadEarnerNm();
        rel_wth_bread_earner_key = insr.getRelWthBreadEarnerKey();
        sync_flg = (insr.getSyncFlg() == null) ? 0 : (insr.getSyncFlg() ? 1 : 0);
        last_upd_by = insr.getLastUpdBy();
    }
}
