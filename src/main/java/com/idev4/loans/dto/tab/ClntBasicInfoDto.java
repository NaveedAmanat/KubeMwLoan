package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwClnt;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class ClntBasicInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public String clnt_id;

    public Long clnt_seq;

    public Long clnt_sts_key;

    public String cnic_issue_dt;

    public String cnic_expry_dt;

    public Long cnic_num;

    public Integer co_bwr_san_flg;

    public Integer crnt_addr_perm_flg;

    public Integer crnt_rec_flg;

    public String crtd_dt;

    public String crtd_by;

    public Integer dis_flg;

    public String dob;

    public Long edu_lvl_key;

    public String eff_start_dt;

    public Long erng_memb;

    public String frst_nm;

    public String fthr_frst_nm;

    public String fthr_last_nm;

    public Long gndr_key;

    public Long hse_hld_memb;

    public String last_nm;

    public Long mnths_res;

    public Long mrtl_sts_key;

    public String mthr_madn_nm;

    public String nick_nm;

    public Long num_of_chldrn;

    public Long num_of_dpnd;

    public Long num_of_erng_memb;

    public Long occ_key;

    public String ph_num;

    public Long port_key;

    public Long res_typ_key;

    public Long natr_of_dis_key;

    public Integer slf_pdc_flg;

    public String spz_frst_nm;

    public String spz_last_nm;

    public Long yrs_res;

    public String biz_dtl;

    public Long tot_incm_of_erng_memb;

    public String last_upd_by;

    public Integer del_flg;

    public Integer nom_dtl_available_flg;

    public Integer sm_hsld_flg;

    public String clnt_sts_dt;

    public Integer pft_flg;

    public Integer sync_flg;

    public String membrshp_dt;

    public Long ref_cd_lead_typ_seq;

    public String whatsapp_num;

    @Override
    public String toString() {
        return "ClntBasicInfoDto{" +
                "clnt_id='" + clnt_id + '\'' +
                ", clnt_seq=" + clnt_seq +
                ", clnt_sts_key=" + clnt_sts_key +
                ", cnic_issue_dt='" + cnic_issue_dt + '\'' +
                ", cnic_expry_dt='" + cnic_expry_dt + '\'' +
                ", cnic_num=" + cnic_num +
                ", co_bwr_san_flg=" + co_bwr_san_flg +
                ", crnt_addr_perm_flg=" + crnt_addr_perm_flg +
                ", crnt_rec_flg=" + crnt_rec_flg +
                ", crtd_dt='" + crtd_dt + '\'' +
                ", crtd_by='" + crtd_by + '\'' +
                ", dis_flg=" + dis_flg +
                ", dob='" + dob + '\'' +
                ", edu_lvl_key=" + edu_lvl_key +
                ", eff_start_dt='" + eff_start_dt + '\'' +
                ", erng_memb=" + erng_memb +
                ", frst_nm='" + frst_nm + '\'' +
                ", fthr_frst_nm='" + fthr_frst_nm + '\'' +
                ", fthr_last_nm='" + fthr_last_nm + '\'' +
                ", gndr_key=" + gndr_key +
                ", hse_hld_memb=" + hse_hld_memb +
                ", last_nm='" + last_nm + '\'' +
                ", mnths_res=" + mnths_res +
                ", mrtl_sts_key=" + mrtl_sts_key +
                ", mthr_madn_nm='" + mthr_madn_nm + '\'' +
                ", nick_nm='" + nick_nm + '\'' +
                ", num_of_chldrn=" + num_of_chldrn +
                ", num_of_dpnd=" + num_of_dpnd +
                ", num_of_erng_memb=" + num_of_erng_memb +
                ", occ_key=" + occ_key +
                ", ph_num='" + ph_num + '\'' +
                ", port_key=" + port_key +
                ", res_typ_key=" + res_typ_key +
                ", natr_of_dis_key=" + natr_of_dis_key +
                ", slf_pdc_flg=" + slf_pdc_flg +
                ", spz_frst_nm='" + spz_frst_nm + '\'' +
                ", spz_last_nm='" + spz_last_nm + '\'' +
                ", yrs_res=" + yrs_res +
                ", biz_dtl='" + biz_dtl + '\'' +
                ", tot_incm_of_erng_memb=" + tot_incm_of_erng_memb +
                ", last_upd_by='" + last_upd_by + '\'' +
                ", del_flg=" + del_flg +
                ", nom_dtl_available_flg=" + nom_dtl_available_flg +
                ", sm_hsld_flg=" + sm_hsld_flg +
                ", clnt_sts_dt='" + clnt_sts_dt + '\'' +
                ", pft_flg=" + pft_flg +
                ", sync_flg=" + sync_flg +
                ", membrshp_dt='" + membrshp_dt + '\'' +
                ", ref_cd_lead_typ_seq=" + ref_cd_lead_typ_seq +
                ", whatsapp_num='" + whatsapp_num + '\'' +
                '}';
    }

    // public ClntBasicInfoDto(MwClnt clnt) {
    // DomainToDto(clnt);
    // }

    public MwClnt DtoToDomain(DateFormat formatter, DateFormat simpleFormatter, MwClnt clnt) {
        clnt.setClntId(clnt_id);
        clnt.setClntSeq(clnt_seq);
        clnt.setClntStsKey(clnt_sts_key);
        try {
            clnt.setLastUpdDt(Instant.now());
            clnt.setEffStartDt(Instant.now());
            clnt.setCnicExpryDt(
                    (cnic_expry_dt == null) ? null : Common.getZonedInstant(simpleFormatter.parse(cnic_expry_dt).toInstant()));
            clnt.setCnicIssueDt(
                    (cnic_issue_dt == null) ? null : Common.getZonedInstant(simpleFormatter.parse(cnic_issue_dt).toInstant()));
            clnt.setCrtdDt((crtd_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));

            clnt.setMembrshpDt((membrshp_dt == null) ? Instant.now() : Common.getZonedInstant(simpleFormatter.parse(membrshp_dt).toInstant()));

            clnt.setDob(Common.getZonedInstant(simpleFormatter.parse(dob).toInstant()));
            clnt.setClntStsDt(
                    (clnt_sts_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(clnt_sts_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        clnt.setCnicNum(cnic_num);
        clnt.setCoBwrSanFlg((co_bwr_san_flg == null) ? false : (co_bwr_san_flg == 1) ? true : false);
        clnt.setCrntAddrPermFlg((crnt_addr_perm_flg == null) ? false : (crnt_addr_perm_flg == 1) ? true : false);
        clnt.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        clnt.setCrtdBy(crtd_by);

        clnt.setPftFlg((pft_flg == null) ? true : (pft_flg == 1) ? true : false);

        clnt.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        clnt.setDisFlg((dis_flg == null) ? false : (dis_flg == 1) ? true : false);

        clnt.setEduLvlKey(edu_lvl_key);

        clnt.setErngMemb(erng_memb);
        clnt.setFrstNm(frst_nm);
        clnt.setFthrFrstNm(fthr_frst_nm);
        clnt.setFthrLastNm(fthr_last_nm);
        clnt.setGndrKey(gndr_key);
        clnt.setHseHldMemb(hse_hld_memb);
        clnt.setLastNm(last_nm);
        clnt.setMnthsRes(mnths_res);
        clnt.setMrtlStsKey(mrtl_sts_key);
        clnt.setMthrMadnNm(mthr_madn_nm);
        clnt.setNomDtlAvailableFlg((nom_dtl_available_flg == null) ? false : (nom_dtl_available_flg == 1) ? true : false);
        clnt.setNickNm(nick_nm);
        clnt.setNatrOfDisKey(natr_of_dis_key);
        clnt.setNumOfChldrn(num_of_chldrn);
        clnt.setNumOfDpnd(num_of_dpnd);
        clnt.setNumOfErngMemb(num_of_erng_memb);
        clnt.setOccKey(occ_key);
        clnt.setPhNum(ph_num);
        clnt.setPortKey(port_key);
        clnt.setResTypKey(res_typ_key);
        clnt.setSlfPdcFlg((slf_pdc_flg == null) ? false : (slf_pdc_flg == 1) ? true : false);
        clnt.setSpzFrstNm(spz_frst_nm);
        clnt.setSpzLastNm(spz_last_nm);
        clnt.setYrsRes(yrs_res);
        clnt.setBizDtl(biz_dtl);
        clnt.setTotIncmOfErngMemb(tot_incm_of_erng_memb == null ? 0L : tot_incm_of_erng_memb);
        clnt.setLastUpdBy(last_upd_by);
        clnt.setSmHsldFlg((sm_hsld_flg == null) ? false : (sm_hsld_flg == 1) ? true : false);
        clnt.setRefCdLeadTypSeq(ref_cd_lead_typ_seq);
        clnt.setWhatsappNum(whatsapp_num);
        return clnt;
    }

    public void DomainToDto(MwClnt clnt) {
        ClntBasicInfoDto dto = new ClntBasicInfoDto();
        clnt_id = clnt.getClntId();
        clnt_seq = clnt.getClntSeq();
        clnt_sts_key = clnt.getClntStsKey();
        cnic_expry_dt = (clnt.getCnicExpryDt() == null) ? "" : Common.GetFormattedDateForTab(clnt.getCnicExpryDt(), true);
        cnic_issue_dt = (clnt.getCnicIssueDt() == null) ? "" : Common.GetFormattedDateForTab(clnt.getCnicIssueDt(), true);
        cnic_num = clnt.getCnicNum();
        crtd_dt = (clnt.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(clnt.getCrtdDt(), false);
        crtd_by = (clnt.getCrtdBy());
        clnt_sts_dt = (clnt.getClntStsDt() == null) ? "" : Common.GetFormattedDateForTab(clnt.getClntStsDt(), false);
        co_bwr_san_flg = (clnt.getCoBwrSanFlg() == null) ? 0 : (clnt.getCoBwrSanFlg() ? 1 : 0);
        crnt_addr_perm_flg = (clnt.getCrntAddrPermFlg() == null) ? 0 : (clnt.getCrntAddrPermFlg() ? 1 : 0);
        crnt_rec_flg = (clnt.getCrntRecFlg() == null) ? 0 : (clnt.getCrntRecFlg() ? 1 : 0);
        dob = (clnt.getDob() == null) ? "" : Common.GetFormattedDateForTab(clnt.getDob(), true);
        dis_flg = (clnt.getDisFlg() == null) ? 0 : (clnt.getDisFlg() ? 1 : 0);
        edu_lvl_key = clnt.getEduLvlKey();
        eff_start_dt = (clnt.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(clnt.getEffStartDt(), false);
        erng_memb = clnt.getErngMemb();
        frst_nm = clnt.getFrstNm();
        fthr_frst_nm = clnt.getFthrFrstNm();
        fthr_last_nm = clnt.getFthrLastNm();
        gndr_key = clnt.getGndrKey();
        hse_hld_memb = clnt.getHseHldMemb();
        last_nm = clnt.getLastNm();
        mnths_res = clnt.getMnthsRes();
        mrtl_sts_key = clnt.getMrtlStsKey();
        mthr_madn_nm = clnt.getMthrMadnNm();
        nick_nm = clnt.getNickNm();
        num_of_chldrn = clnt.getNumOfChldrn();
        num_of_dpnd = clnt.getNumOfDpnd();
        num_of_erng_memb = clnt.getNumOfErngMemb();
        natr_of_dis_key = clnt.getNatrOfDisKey();
        occ_key = clnt.getOccKey();
        ph_num = clnt.getPhNum();
        port_key = clnt.getPortKey();
        res_typ_key = clnt.getResTypKey();
        slf_pdc_flg = (clnt.getSlfPdcFlg() == null) ? 0 : (clnt.getSlfPdcFlg() ? 1 : 0);
        spz_frst_nm = clnt.getSpzFrstNm();
        spz_last_nm = clnt.getSpzLastNm();
        yrs_res = clnt.getYrsRes();
        biz_dtl = clnt.getBizDtl();
        tot_incm_of_erng_memb = clnt.getTotIncmOfErngMemb();
        last_upd_by = clnt.getLastUpdBy();
        crnt_rec_flg = (clnt.isCrntRecFlg() == null) ? 0 : (clnt.isCrntRecFlg() ? 1 : 0);
        del_flg = (clnt.isDelFlg() == null) ? 0 : (clnt.isDelFlg() ? 1 : 0);
        nom_dtl_available_flg = (clnt.getNomDtlAvailableFlg() == null) ? 0 : (clnt.getNomDtlAvailableFlg() ? 1 : 0);
        sm_hsld_flg = (clnt.getSmHsldFlg() == null) ? 0 : (clnt.getSmHsldFlg() ? 1 : 0);
        pft_flg = (clnt.isPftFlg() == null) ? 0 : (clnt.isPftFlg() ? 1 : 0);
        sync_flg = (clnt.getSyncFlg() == null) ? 0 : (clnt.getSyncFlg() ? 1 : 0);
        membrshp_dt = (clnt.getMembrshpDt() == null) ? "" : Common.GetFormattedDateForTab(clnt.getMembrshpDt(), false);
        ref_cd_lead_typ_seq = clnt.getRefCdLeadTypSeq();
        whatsapp_num = clnt.getWhatsappNum();
    }
}
