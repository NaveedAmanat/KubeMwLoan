package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwBizExpDtl;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class ExpenseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long biz_aprsl_seq;

    public Integer crnt_rec_flg;

    public String crtd_dt;

    public Integer del_flg;

    public String eff_start_dt;

    public Double exp_amt;

    public Long exp_ctgry_key;

    public Long exp_dtl_seq;

    public Long exp_typ_key;

    public Integer enty_typ_flg;

    public String crtd_by;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer sync_flg;

    @Override
    public String toString() {
        return "ExpenseDto [biz_aprsl_seq=" + biz_aprsl_seq + ", crnt_rec_flg=" + crnt_rec_flg + ", crtd_dt=" + crtd_dt + ", del_flg="
                + del_flg + ", eff_start_dt=" + eff_start_dt + ", exp_amt=" + exp_amt + ", exp_ctgry_key=" + exp_ctgry_key
                + ", exp_dtl_seq=" + exp_dtl_seq + ", exp_typ_key=" + exp_typ_key + ", enty_typ_flg=" + enty_typ_flg + "]";
    }

    public MwBizExpDtl DtoToDomain(DateFormat formatter, MwBizExpDtl expense) {
        expense.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        try {
            expense.setCrtdDt((crtd_dt) == null ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            expense.setEffStartDt(
                    (eff_start_dt) == null ? Instant.now() : Common.getZonedInstant(formatter.parse(eff_start_dt).toInstant()));

            expense.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        expense.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        expense.setEntyTypFlg(enty_typ_flg);
        expense.setExpAmt(exp_amt);
        expense.setExpCtgryKey(exp_ctgry_key);
        expense.setExpDtlSeq(exp_dtl_seq);
        expense.setExpTypKey(exp_typ_key);
        expense.setMwBizAprsl(biz_aprsl_seq);
        expense.setCrtdBy(crtd_by);
        expense.setLastUpdBy(last_upd_by);
        return expense;
    }

    public void DomainToDto(MwBizExpDtl ex) {
        biz_aprsl_seq = ex.getMwBizAprsl();
        crnt_rec_flg = (ex.isCrntRecFlg() == null) ? 0 : (ex.isCrntRecFlg() ? 1 : 0);
        crtd_dt = (ex.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(ex.getCrtdDt(), false);
        del_flg = (ex.isDelFlg() == null) ? 0 : (ex.isDelFlg() ? 1 : 0);
        eff_start_dt = (ex.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(ex.getEffStartDt(), false);
        enty_typ_flg = ex.getEntyTypFlg();
        exp_amt = ex.getExpAmt();
        exp_ctgry_key = ex.getExpCtgryKey();
        exp_dtl_seq = ex.getExpDtlSeq();
        exp_typ_key = ex.getExpTypKey();
        crtd_by = ex.getCrtdBy();
        last_upd_by = ex.getLastUpdBy();
        last_upd_dt = (ex.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(ex.getLastUpdDt(), false);
        sync_flg = (ex.getSyncFlg() == null) ? 0 : (ex.getSyncFlg() ? 1 : 0);
    }
}
