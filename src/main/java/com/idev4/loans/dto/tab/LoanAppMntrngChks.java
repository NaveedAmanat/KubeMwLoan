package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwLoanAppMntrngChks;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class LoanAppMntrngChks implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long loan_app_mntrng_chks_seq;

    public String eff_start_dt;

    public Long loan_app_seq;

    public Long rsn;

    public Long actn_tkn;

    public String crtd_by;

    public String crtd_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer del_flg;

    public String eff_end_dt;

    public Integer crnt_rec_flg;

    public String cmnt;

    public Integer chk_flg;

    public MwLoanAppMntrngChks DtoToDomain(DateFormat formatter) {
        MwLoanAppMntrngChks chk = new MwLoanAppMntrngChks();
        chk.setActnTkn(actn_tkn);
        chk.setCmnt(cmnt);
        chk.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        chk.setCrtdBy(crtd_by);
        chk.setDelFlg((del_flg == null) ? true : (del_flg == 1) ? true : false);
        chk.setLastUpdBy(last_upd_by);
        chk.setLoanAppMntrngChksSeq(loan_app_mntrng_chks_seq);
        chk.setLoanAppSeq(loan_app_seq);
        chk.setRsn(rsn);
        try {
            chk.setLastUpdDt(Instant.now());
            chk.setEffStartDt(
                    (eff_start_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(eff_start_dt).toInstant()));
            chk.setCrtdDt((crtd_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        chk.setChkFlg(chk_flg);
        return chk;
    }

    public void DomainToDto(MwLoanAppMntrngChks chk) {
        loan_app_mntrng_chks_seq = chk.getLoanAppMntrngChksSeq();
        loan_app_seq = chk.getLoanAppSeq();
        rsn = chk.getRsn();
        actn_tkn = chk.getActnTkn();
        crtd_by = chk.getCrtdBy();
        last_upd_by = chk.getLastUpdBy();
        cmnt = chk.getCmnt();
        del_flg = (chk.getDelFlg() == null) ? 0 : (chk.getDelFlg() ? 1 : 0);
        crnt_rec_flg = (chk.getCrntRecFlg() == null) ? 0 : (chk.getCrntRecFlg() ? 1 : 0);
        crtd_dt = (chk.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getCrtdDt(), false);
        eff_start_dt = (chk.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getEffStartDt(), false);
        last_upd_dt = (chk.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getLastUpdDt(), false);
        chk_flg = chk.getChkFlg();
    }

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
