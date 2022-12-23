package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwBizAprslExtngLvstk;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class BizAprslExtngLvstkDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long biz_aprsl_extng_lvstk_seq;

    public String eff_start_dt;

    public Long biz_aprsl_seq;

    public Long sr_num;

    public Long anml_hc;

    public Long est_val;

    public Long anml_knd;

    public Long anml_typ;

    public String anml_brd;

    public String crtd_by;

    public String crtd_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer del_flg;

    public String eff_end_dt;

    public Integer crnt_rec_flg;

    public MwBizAprslExtngLvstk DtoToDomain(DateFormat formatter, MwBizAprslExtngLvstk lv) {
        lv.setAnmlBrd(anml_brd);
        lv.setAnmlHc(anml_hc);
        lv.setAnmlKnd(anml_knd);
        lv.setAnmlTyp(anml_typ);
        lv.setBizAprslExtngLvstkSeq(biz_aprsl_extng_lvstk_seq);
        lv.setBizAprslSeq(biz_aprsl_seq);
        lv.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        try {
            lv.setLastUpdDt(Instant.now());
            lv.setEffStartDt(Instant.now());
            lv.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        lv.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        lv.setCrtdBy(crtd_by);
        lv.setLastUpdBy(last_upd_by);
        lv.setEstVal(est_val);
        lv.setSrNum(sr_num);
        return lv;
    }

    public void DomainToDto(MwBizAprslExtngLvstk lv) {
        biz_aprsl_extng_lvstk_seq = lv.getBizAprslExtngLvstkSeq();
        eff_start_dt = (lv.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(lv.getEffStartDt(), false);
        biz_aprsl_seq = lv.getBizAprslSeq();
        sr_num = lv.getSrNum();
        anml_hc = lv.getAnmlHc();
        est_val = lv.getEstVal();
        anml_knd = lv.getAnmlKnd();
        anml_typ = lv.getAnmlTyp();
        anml_brd = lv.getAnmlBrd();
        crtd_by = lv.getCrtdBy();
        crtd_dt = (lv.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(lv.getCrtdDt(), false);
        last_upd_by = lv.getLastUpdBy();
        last_upd_dt = (lv.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(lv.getLastUpdDt(), false);
        del_flg = (lv.getDelFlg() == null) ? 0 : (lv.getDelFlg() ? 1 : 0);
        crnt_rec_flg = (lv.getCrntRecFlg() == null) ? 0 : (lv.getCrntRecFlg() ? 1 : 0);
    }
}
