package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwClntRel;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class ClntRelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long clnt_rel_seq;

    public String cnic_issue_dt;

    public String cnic_expry_dt;

    public Long cnic_num;

    public Integer crnt_rec_flg;

    public String dob;

    public String eff_start_dt;

    public String frst_nm;

    public String fthr_frst_nm;

    public String fthr_last_nm;

    public Long gndr_key;

    public String last_nm;

    public Long loan_app_seq;

    public Long mrtl_sts_key;

    public Integer nom_fthr_spz_flg;

    public Long occ_key;

    public String ph_num;

    public Long rel_typ_flg;

    public Long rel_wth_clnt_key;

    public String crtd_by;

    public String crtd_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer sync_flg;

    public Integer del_flg;
    public Integer loan_user;

    @Override
    public String toString() {
        return "ClntRelDto{" +
                "clnt_rel_seq=" + clnt_rel_seq +
                ", cnic_issue_dt='" + cnic_issue_dt + '\'' +
                ", cnic_expry_dt='" + cnic_expry_dt + '\'' +
                ", cnic_num=" + cnic_num +
                ", crnt_rec_flg=" + crnt_rec_flg +
                ", dob='" + dob + '\'' +
                ", eff_start_dt='" + eff_start_dt + '\'' +
                ", frst_nm='" + frst_nm + '\'' +
                ", fthr_frst_nm='" + fthr_frst_nm + '\'' +
                ", fthr_last_nm='" + fthr_last_nm + '\'' +
                ", gndr_key=" + gndr_key +
                ", last_nm='" + last_nm + '\'' +
                ", loan_app_seq=" + loan_app_seq +
                ", mrtl_sts_key=" + mrtl_sts_key +
                ", nom_fthr_spz_flg=" + nom_fthr_spz_flg +
                ", occ_key=" + occ_key +
                ", ph_num='" + ph_num + '\'' +
                ", rel_typ_flg=" + rel_typ_flg +
                ", rel_wth_clnt_key=" + rel_wth_clnt_key +
                ", crtd_by='" + crtd_by + '\'' +
                ", crtd_dt='" + crtd_dt + '\'' +
                ", last_upd_by='" + last_upd_by + '\'' +
                ", last_upd_dt='" + last_upd_dt + '\'' +
                ", sync_flg=" + sync_flg +
                ", del_flg=" + del_flg +
                '}';
    }

    public MwClntRel DtoToDomain(DateFormat formatter, DateFormat simpleFormatter, MwClntRel rel) {
        rel.setClntRelSeq(clnt_rel_seq);
        try {
            rel.setLastUpdDt(Instant.now());
            rel.setEffStartDt(Instant.now());
            rel.setDob(Common.getZonedInstant(simpleFormatter.parse(dob).toInstant()));
            rel.setCnicIssueDt(Common.getZonedInstant(simpleFormatter.parse(cnic_issue_dt).toInstant()));
            rel.setCnicExpryDt(Common.getZonedInstant(simpleFormatter.parse(cnic_expry_dt).toInstant()));
            rel.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        rel.setCnicNum(cnic_num);
        // nominee.setCoBwrSanFlg(nominee.fl);
        rel.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        rel.setCrtdDt(Instant.now());
        rel.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        rel.setFrstNm(frst_nm);
        rel.setFthr_frst_nm(fthr_frst_nm);
        rel.setFthr_last_nm(fthr_last_nm);
        rel.setGndrKey(gndr_key);
        rel.setLastNm(last_nm);
        rel.setLoanAppSeq(loan_app_seq);
        rel.setMrtlStsKey(mrtl_sts_key);
        rel.setNomFthrSpzFlg((nom_fthr_spz_flg == null) ? false : (nom_fthr_spz_flg == 1) ? true : false);
        rel.setOccKey(occ_key);
        rel.setPhNum(ph_num);
        rel.setRelTypFlg(rel_typ_flg);
        rel.setRelWthClntKey(rel_wth_clnt_key);
        rel.setLastUpdBy(last_upd_by);
        rel.setCrtdBy(crtd_by);
        rel.setLoanUser(loan_user);
        return rel;
    }

    public ClntRelDto DomainToDto(MwClntRel rel) {

        ClntRelDto dto = new ClntRelDto();
        clnt_rel_seq = rel.getClntRelSeq();
        cnic_issue_dt = (rel.getCnicIssueDt() == null) ? "" : Common.GetFormattedDateForTab(rel.getCnicIssueDt(), true);
        cnic_expry_dt = (rel.getCnicExpryDt() == null) ? "" : Common.GetFormattedDateForTab(rel.getCnicExpryDt(), true);
        cnic_num = rel.getCnicNum();
        crnt_rec_flg = (rel.getCrntRecFlg() == null) ? 0 : (rel.getCrntRecFlg() ? 1 : 0);
        dob = (rel.getDob() == null) ? "" : Common.GetFormattedDateForTab(rel.getDob(), true);
        eff_start_dt = (rel.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(rel.getEffStartDt(), false);
        frst_nm = rel.getFrstNm();
        fthr_frst_nm = rel.getFthr_frst_nm();
        fthr_last_nm = rel.getFthr_last_nm();
        gndr_key = rel.getGndrKey();
        last_nm = rel.getLastNm();
        loan_app_seq = rel.getLoanAppSeq();
        mrtl_sts_key = rel.getMrtlStsKey();
        nom_fthr_spz_flg = (rel.getNomFthrSpzFlg() == null) ? 0 : (rel.getNomFthrSpzFlg() ? 1 : 0);
        occ_key = rel.getOccKey();
        ph_num = rel.getPhNum();
        rel_typ_flg = rel.getRelTypFlg();
        rel_wth_clnt_key = rel.getRelWthClntKey();
        crtd_by = rel.getCrtdBy();
        last_upd_by = rel.getLastUpdBy();
        last_upd_dt = (rel.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(rel.getLastUpdDt(), false);
        crtd_dt = (rel.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(rel.getCrtdDt(), false);
        sync_flg = (rel.getSyncFlg() == null) ? 0 : (rel.getSyncFlg() ? 1 : 0);
        del_flg = (rel.isDelFlg() == null) ? 0 : (rel.isDelFlg() ? 1 : 0);
        loan_user = rel.getLoanUser();
        return dto;
    }
}
