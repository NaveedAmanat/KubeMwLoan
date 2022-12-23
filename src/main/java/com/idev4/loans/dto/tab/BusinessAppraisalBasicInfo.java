package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwBizAprsl;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class BusinessAppraisalBasicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long biz_acty_seq;

    public Long biz_aprsl_seq;

    public String biz_dtl;

    public Long biz_own;

    public String biz_ph_num;

    public Long biz_prpty_own_key;

    public Integer crnt_rec_flg;

    public String crtd_dt;

    public String eff_start_dt;

    public Long loan_app_seq;

    public Integer mnth_in_biz;

    public Long prsn_run_the_biz;

    public Integer yrs_in_biz;

    public Integer biz_addr_same_as_home_flg;

    public String crtd_by;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer sync_flg;

    public Integer del_flg;

    public String remarks;

    @Override
    public String toString() {
        return "BusinessAppraisalBasicInfo{" +
                "biz_acty_seq=" + biz_acty_seq +
                ", biz_aprsl_seq=" + biz_aprsl_seq +
                ", biz_dtl='" + biz_dtl + '\'' +
                ", biz_own=" + biz_own +
                ", biz_ph_num='" + biz_ph_num + '\'' +
                ", biz_prpty_own_key=" + biz_prpty_own_key +
                ", crnt_rec_flg=" + crnt_rec_flg +
                ", crtd_dt='" + crtd_dt + '\'' +
                ", eff_start_dt='" + eff_start_dt + '\'' +
                ", loan_app_seq=" + loan_app_seq +
                ", mnth_in_biz=" + mnth_in_biz +
                ", prsn_run_the_biz=" + prsn_run_the_biz +
                ", yrs_in_biz=" + yrs_in_biz +
                ", biz_addr_same_as_home_flg=" + biz_addr_same_as_home_flg +
                ", crtd_by='" + crtd_by + '\'' +
                ", last_upd_by='" + last_upd_by + '\'' +
                ", last_upd_dt='" + last_upd_dt + '\'' +
                ", sync_flg=" + sync_flg +
                ", del_flg=" + del_flg +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public MwBizAprsl DtoToDomain(DateFormat formatter, MwBizAprsl aprsl) {
        aprsl.setActyKey(biz_acty_seq);
        aprsl.setBizAddrSameAsHomeFlg((biz_addr_same_as_home_flg == null) ? false : (biz_addr_same_as_home_flg == 1) ? true : false);
        aprsl.setBizAprslSeq(biz_aprsl_seq);
        // aprsl.setBizDtlStr(biz_);
        aprsl.setBizOwn(biz_own);
        aprsl.setBizPhNum(biz_ph_num);
        aprsl.setBizPropertyOwnKey(biz_prpty_own_key);
        aprsl.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        try {
            aprsl.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            aprsl.setEffStartDt(Instant.now());
            aprsl.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        aprsl.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        aprsl.setMnthInBiz(mnth_in_biz);
        aprsl.setMwLoanApp(loan_app_seq);
        aprsl.setPrsnRunTheBiz(prsn_run_the_biz);
        // aprsl.setSectKey();
        aprsl.setYrsInBiz(yrs_in_biz);
        aprsl.setCrtdBy(crtd_by);
        aprsl.setLastUpdBy(last_upd_by);
        aprsl.setBizDtlStr(biz_dtl);
        aprsl.setRemarks(remarks);
        return aprsl;
    }

    public void DomainToDto(MwBizAprsl apr) {
        BusinessAppraisalBasicInfo dto = new BusinessAppraisalBasicInfo();
        biz_acty_seq = apr.getActyKey();
        biz_aprsl_seq = apr.getBizAprslSeq();
        biz_dtl = apr.getBizDtlStr();
        biz_own = apr.getBizOwn();
        biz_ph_num = apr.getBizPhNum();
        biz_prpty_own_key = apr.getBizPropertyOwnKey();
        crnt_rec_flg = (apr.isCrntRecFlg() == null) ? 0 : (apr.isCrntRecFlg() ? 1 : 0);
        crtd_dt = (apr.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(apr.getCrtdDt(), false);
        eff_start_dt = (apr.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(apr.getEffStartDt(), false);
        loan_app_seq = apr.getMwLoanApp();
        mnth_in_biz = apr.getMnthInBiz();
        prsn_run_the_biz = apr.getPrsnRunTheBiz();
        yrs_in_biz = apr.getYrsInBiz();
        crtd_by = apr.getCrtdBy();
        last_upd_by = apr.getLastUpdBy();
        last_upd_dt = (apr.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(apr.getLastUpdDt(), false);
        sync_flg = (apr.getSyncFlg() == null) ? 0 : (apr.getSyncFlg() ? 1 : 0);
        biz_addr_same_as_home_flg = (apr.getBizAddrSameAsHomeFlg() == null) ? 0 : (apr.getBizAddrSameAsHomeFlg() ? 1 : 0);
        del_flg = (apr.isDelFlg() == null) ? 0 : (apr.isDelFlg() ? 1 : 0);
        remarks = apr.getRemarks();
    }

}
