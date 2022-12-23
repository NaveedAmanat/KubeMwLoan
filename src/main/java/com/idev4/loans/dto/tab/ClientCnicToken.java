package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwCnicTkn;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class ClientCnicToken implements Serializable {

    private static final long serialVersionUID = 1L;

    public String cnic_tkn_expry_dt;

    public Long cnic_tkn_num;

    public Long cnic_tkn_seq;

    public String crtd_by;

    public String crtd_dt;

    public String eff_start_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Long loan_app_seq;

    public Integer crnt_rec_flg;

    public Integer del_flg;

    public MwCnicTkn DtoToDomain(DateFormat formatter, DateFormat simpleFormatter, MwCnicTkn tkn) {
        try {
            tkn.setCnicTknExpryDt(Common.getZonedInstant(simpleFormatter.parse(cnic_tkn_expry_dt).toInstant()));
            tkn.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            tkn.setEffStartDt(Instant.now());
            tkn.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tkn.setCnicTknNum(cnic_tkn_num);
        tkn.setCrtdBy(crtd_by);
        tkn.setCnicTknSeq(cnic_tkn_seq);
        tkn.setLastUpdBy(last_upd_by);
        tkn.setLoanAppSeq(loan_app_seq);
        tkn.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        tkn.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        return tkn;
    }

    public void DomainToDto(MwCnicTkn tkn) {
        cnic_tkn_expry_dt = (tkn.getCnicTknExpryDt() == null) ? "" : Common.GetFormattedDateForTab(tkn.getCnicTknExpryDt(), true);
        cnic_tkn_num = tkn.getCnicTknNum();
        cnic_tkn_seq = tkn.getCnicTknSeq();
        crtd_by = tkn.getCrtdBy();
        crtd_dt = (tkn.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(tkn.getCrtdDt(), false);
        eff_start_dt = (tkn.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(tkn.getEffStartDt(), false);
        last_upd_by = tkn.getLastUpdBy();
        last_upd_dt = (tkn.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(tkn.getLastUpdDt(), false);
        loan_app_seq = tkn.getLoanAppSeq();
        crnt_rec_flg = (tkn.getCrntRecFlg() == null) ? 0 : (tkn.getCrntRecFlg() ? 1 : 0);
        del_flg = (tkn.getDelFlg() == null) ? 0 : (tkn.getDelFlg() ? 1 : 0);
    }
}
