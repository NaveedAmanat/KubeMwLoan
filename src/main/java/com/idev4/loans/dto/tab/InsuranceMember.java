package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwHlthInsrMemb;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class InsuranceMember implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer crnt_rec_flg;

    public String crtd_dt;

    public String crtd_by;

    public String dob;

    public String eff_start_dt;

    public Long gndr_key;

    public Long hlth_insr_memb_seq;

    public Long loan_app_seq;

    public Long member_cnic_num;

    public String member_nm;

    public Long member_id;

    public Long mrtl_sts_key;

    public Long rel_key;

    public Integer sync_flg;

    public String last_upd_by;

    public Integer del_flg;

    @Override
    public String toString() {
        return "InsuranceMember [crnt_rec_flg=" + crnt_rec_flg + ", crtd_dt=" + crtd_dt + ", crtd_by=" + crtd_by + ", dob=" + dob + ", eff_start_dt=" + eff_start_dt
                + ", gndr_key=" + gndr_key + ", hlth_insr_memb_seq=" + hlth_insr_memb_seq + ", loan_app_seq=" + loan_app_seq
                + ", member_cnic_num=" + member_cnic_num + ", member_nm=" + member_nm + ", member_id=" + member_id + ", mrtl_sts_key=" + mrtl_sts_key + ", rel_key="
                + rel_key + "]";
    }

    public MwHlthInsrMemb DtoToDomain(DateFormat formatter, DateFormat simplerFormatter) {
        MwHlthInsrMemb mem = new MwHlthInsrMemb();
        mem.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        mem.setCrtdBy(crtd_by);


        mem.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        try {
            mem.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            mem.setDob(Common.getZonedInstant(simplerFormatter.parse(dob).toInstant()));
            mem.setEffStartDt(Instant.now());
            mem.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mem.setGndrKey(gndr_key);
        mem.setHlthInsrMembSeq(hlth_insr_memb_seq);
        mem.setLoanAppSeq(loan_app_seq);
        mem.setMembCnicNum(member_cnic_num);
        mem.setMembNm(member_nm);
        mem.setMrtlStsKey(mrtl_sts_key);
        mem.setRelKey(rel_key);
        mem.setLastUpdBy(last_upd_by);
        mem.setMemberId(member_id);
        return mem;
    }

    public InsuranceMember DomainToDto(MwHlthInsrMemb mem) {
        InsuranceMember dto = new InsuranceMember();
        crnt_rec_flg = (mem.getCrntRecFlg() == null) ? 0 : (mem.getCrntRecFlg() ? 1 : 0);
        crtd_dt = (mem.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(mem.getCrtdDt(), false);
        dob = (mem.getDob() == null) ? "" : Common.GetFormattedDateForTab(mem.getDob(), true);
        eff_start_dt = (mem.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(mem.getEffStartDt(), false);
        crtd_by = mem.getCrtdBy();
        gndr_key = mem.getGndrKey();
        hlth_insr_memb_seq = mem.getHlthInsrMembSeq();
        loan_app_seq = mem.getLoanAppSeq();
        member_cnic_num = mem.getMembCnicNum();
        member_nm = mem.getMembNm();
        member_id = mem.getMemberId();
        mrtl_sts_key = mem.getMrtlStsKey();
        rel_key = mem.getRelKey();
        sync_flg = (mem.getSyncFlg() == null) ? 0 : (mem.getSyncFlg() ? 1 : 0);
        last_upd_by = mem.getLastUpdBy();
        del_flg = (mem.isDelFlg() == null) ? 0 : (mem.isDelFlg() ? 1 : 0);
        return dto;
    }
}
