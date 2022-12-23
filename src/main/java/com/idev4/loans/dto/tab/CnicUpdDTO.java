package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwCnicUpd;
import com.idev4.loans.web.rest.util.Common;

public class CnicUpdDTO {

    public Long cnic_upd_seq;

    public Long loan_app_seq;

    public String cnic_expry_dt;

    public String cnic_frnt_pic;

    public String cnic_bck_pic;

    public String crtd_by;

    public String crtd_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Long upd_sts;

    public String cmnt;

    //Updated by Areeba - Dated - 24-3-2022
    public Long cnic_num;


    public void DomainToDto(MwCnicUpd upd) {

        cnic_upd_seq = upd.getCnicUpdSeq();

        cnic_expry_dt = (upd.getCnicExpryDt() == null) ? "" : Common.GetFormattedDateForTab(upd.getCnicExpryDt(), true);

        loan_app_seq = upd.getLoanAppSeq();

        cnic_frnt_pic = upd.getCnicFrntPic();

        cnic_bck_pic = upd.getCnicBckPic();

        crtd_by = upd.getCrtdBy();

        crtd_dt = (upd.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(upd.getCrtdDt(), false);

        last_upd_by = upd.getLastUpdBy();

        last_upd_dt = (upd.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(upd.getLastUpdDt(), false);

        upd_sts = upd.getUpdSts();

        cmnt = upd.getCmnt();

        //Updated by Areeba - Dated - 24-3-2022
        cnic_num = upd.getCnicNum();

    }

}
