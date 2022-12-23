package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwAnmlRgstr;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class AnmlRgstrDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private final DateFormat simpleFormatter = new SimpleDateFormat("dd-MM-yyyy");
    public Long anml_rgstr_seq;
    public String eff_start_dt;
    public Long loan_app_seq;
    public String rgstr_cd;
    public String tag_num;
    public Long anml_knd;
    public Long anml_typ;
    public Long anml_clr;
    public String anml_brd;
    public String prch_dt;
    public Long age_yr;
    public Long age_mnth;
    public Long prch_amt;
    public String pic_dt;
    public String anml_pic;
    public String tag_pic;
    public String crtd_by;
    public String crtd_dt;
    public String last_upd_by;
    public String last_upd_dt;
    public Integer del_flg;
    public String eff_end_dt;
    public Integer crnt_rec_flg;
    public Long anml_sts;

    public MwAnmlRgstr DtoToDomain(DateFormat formatter, MwAnmlRgstr rgstr) {
        rgstr.setAgeMnth(age_mnth);
        rgstr.setAgeYr(age_yr);
        rgstr.setAnmlBrd(anml_brd);
        rgstr.setAnmlClr(anml_clr);
        rgstr.setAnmlKnd(anml_knd);
        rgstr.setAnmlPic(anml_pic);
        rgstr.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        try {
            rgstr.setLastUpdDt(Instant.now());
            rgstr.setEffStartDt(Instant.now());
            rgstr.setPicDt(Common.getZonedInstant(simpleFormatter.parse(pic_dt).toInstant()));
            rgstr.setPrchDt(Common.getZonedInstant(simpleFormatter.parse(prch_dt).toInstant()));
            rgstr.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        rgstr.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        rgstr.setAnmlPic(anml_pic);
        rgstr.setAnmlRgstrSeq(anml_rgstr_seq);
        rgstr.setAnmlTyp(anml_typ);
        rgstr.setCrtdBy(crtd_by);
        rgstr.setLastUpdBy(last_upd_by);
        rgstr.setLoanAppSeq(loan_app_seq);
        rgstr.setPrchAmt(prch_amt);
        rgstr.setRgstrCd(rgstr_cd);
        rgstr.setTagNm(tag_num);
        rgstr.setTagPic(tag_pic);
        rgstr.setAnmlSts(anml_sts);
        return rgstr;
    }

    public void DomainToDto(MwAnmlRgstr apr) {
        anml_rgstr_seq = apr.getAnmlRgstrSeq();
        rgstr_cd = apr.getRgstrCd();
        tag_num = apr.getTagNm();
        anml_knd = apr.getAnmlKnd();
        anml_typ = apr.getAnmlTyp();
        anml_clr = apr.getAnmlClr();
        anml_brd = apr.getAnmlBrd();
        prch_dt = (apr.getPrchDt() == null) ? "" : Common.GetFormattedDateForTab(apr.getPrchDt(), true);
        age_yr = apr.getAgeYr();
        age_mnth = apr.getAgeMnth();
        prch_amt = apr.getPrchAmt();
        pic_dt = (apr.getPicDt() == null) ? "" : Common.GetFormattedDateForTab(apr.getPicDt(), false);
        // anml_pic = apr.getAnmlPic();
        // tag_pic = apr.getTagPic();
        crnt_rec_flg = (apr.getCrntRecFlg() == null) ? 0 : (apr.getCrntRecFlg() ? 1 : 0);
        crtd_dt = (apr.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(apr.getCrtdDt(), false);
        eff_start_dt = (apr.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(apr.getEffStartDt(), false);
        loan_app_seq = apr.getLoanAppSeq();
        crtd_by = apr.getCrtdBy();
        last_upd_by = apr.getLastUpdBy();
        last_upd_dt = (apr.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(apr.getLastUpdDt(), false);
        del_flg = (apr.getDelFlg() == null) ? 0 : (apr.getDelFlg() ? 1 : 0);
        anml_sts = apr.getAnmlSts();
    }

    public void DomainToDtoObj(Object[] apr) {
        anml_rgstr_seq = Long.valueOf((apr[0] == null) ? "0" : apr[0].toString());
        eff_start_dt = (apr[1] == null) ? Common.GetFormattedDateForTab(Instant.now(), true) : apr[1].toString();
        loan_app_seq = Long.valueOf((apr[2] == null) ? "0" : apr[2].toString());
        rgstr_cd = (apr[3] == null) ? "0" : apr[3].toString();
        tag_num = (apr[4] == null) ? "0" : apr[4].toString();
        anml_knd = Long.valueOf((apr[5] == null) ? "0" : apr[5].toString());
        anml_typ = Long.valueOf((apr[6] == null) ? "0" : apr[6].toString());
        anml_clr = Long.valueOf((apr[7] == null) ? "0" : apr[7].toString());
        anml_brd = (apr[8] == null) ? "" : apr[8].toString();
        prch_dt = (apr[9] == null) ? Common.GetFormattedDateForTab(Instant.now(), true) : apr[9].toString();
        age_yr = Long.valueOf((apr[10] == null) ? "0" : apr[10].toString());
        age_mnth = Long.valueOf((apr[11] == null) ? "0" : apr[11].toString());
        prch_amt = Long.valueOf((apr[12] == null) ? "0" : apr[12].toString());
        pic_dt = (apr[13] == null) ? Common.GetFormattedDateForTab(Instant.now(), true) : apr[13].toString();
        crtd_by = (apr[14] == null) ? "" : apr[14].toString();
        crtd_dt = (apr[15] == null) ? Common.GetFormattedDateForTab(Instant.now(), true) : apr[15].toString();
        last_upd_by = (apr[16] == null) ? "" : apr[16].toString();
        last_upd_dt = (apr[17] == null) ? Common.GetFormattedDateForTab(Instant.now(), true) : apr[17].toString();
        del_flg = Integer.valueOf((apr[18] == null) ? "0" : apr[18].toString());
        // anml_pic = apr.getAnmlPic();
        // tag_pic = apr.getTagPic();
        crnt_rec_flg = Integer.valueOf((apr[20] == null) ? "1" : apr[20].toString());
        anml_sts = Long.valueOf((apr[21] == null) ? "0" : apr[21].toString());
    }
}
