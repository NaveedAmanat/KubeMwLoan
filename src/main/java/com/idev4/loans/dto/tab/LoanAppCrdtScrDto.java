package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwLoanAppCrdtScr;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class LoanAppCrdtScrDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long loan_app_crdt_scr_seq;

    public Long loan_app_seq;

    public String crtd_dt;

    public String crtd_by;

    public String eff_start_dt;

    public String eff_end_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer crnt_rec_flg;

    public Integer del_flg;

    public Long crdt_scr;

    public String crdt_rsk_ctgry;

    public String crdt_json;

    //Added by Rizwan Mahfooz on 18 JULY 2022
    public String rqstd_json;
    //End

    public MwLoanAppCrdtScr DtoToDomain(DateFormat formatter, MwLoanAppCrdtScr scr) {
        scr.setCrdtJson(crdt_json);
        //Added by Rizwan Mahfooz on 18 JULY 2022
        scr.setRqstdJson(rqstd_json);
        //End
        scr.setCrdtRskCtgry(crdt_rsk_ctgry);
        scr.setCrdtScr(crdt_scr);
        scr.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        scr.setCrtdBy(crtd_by);
        scr.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        scr.setLastUpdBy(last_upd_by);
        scr.setLoanAppCrdtScrSeq(loan_app_crdt_scr_seq);
        scr.setLoanAppSeq(loan_app_seq);
        try {
            scr.setLastUpdDt(Instant.now());
            scr.setEffStartDt(Instant.now());
            scr.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return scr;
    }

    public void DomainToDto(MwLoanAppCrdtScr scr) {
        loan_app_crdt_scr_seq = scr.getLoanAppCrdtScrSeq();
        loan_app_seq = scr.getLoanAppSeq();
        crdt_scr = scr.getCrdtScr();
        crdt_rsk_ctgry = scr.getCrdtRskCtgry();
        crdt_json = scr.getCrdtJson();
        //Added by Rizwan Mahfooz on 18 JULY 2022
        rqstd_json = scr.getRqstdJson();
        //End
        crnt_rec_flg = (scr.getCrntRecFlg() == null) ? 0 : (scr.getCrntRecFlg() ? 1 : 0);
        crtd_dt = (scr.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(scr.getCrtdDt(), false);
        eff_start_dt = (scr.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(scr.getEffStartDt(), false);
        del_flg = (scr.getDelFlg() == null) ? 0 : (scr.getDelFlg() ? 1 : 0);
        crtd_by = scr.getCrtdBy();
        last_upd_by = scr.getLastUpdBy();
        last_upd_dt = (scr.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(scr.getLastUpdDt(), false);
    }
}
