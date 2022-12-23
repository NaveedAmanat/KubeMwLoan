package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwBizAprslEstLvstkFin;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class BizAprslEstLvstkFinDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long biz_aprsl_est_lvstk_fin_seq;

    public String eff_start_dt;

    public Long biz_aprsl_seq;

    public Long sr_num;

    public Long anml_hc;

    public Long est_val;

    public Long anml_knd;

    public Long anml_typ;

    public String anml_brd;

    public Long amt_by_client;

    public Long amt_fin;

    public String crtd_by;

    public String crtd_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer del_flg;

    public String eff_end_dt;

    public Integer crnt_rec_flg;

    public MwBizAprslEstLvstkFin DtoToDomain(DateFormat formatter, MwBizAprslEstLvstkFin fin) {
        fin.setAmtByClient(amt_by_client);
        fin.setAmtFin(amt_fin);
        fin.setAnmlBrd(anml_brd);
        fin.setAnmlHc(anml_hc);
        fin.setAnmlKnd(anml_knd);
        fin.setAnmlTyp(anml_typ);
        fin.setBizAprslEstLvstkFinSeq(biz_aprsl_est_lvstk_fin_seq);
        fin.setBizAprslSeq(biz_aprsl_seq);
        fin.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        try {
            fin.setLastUpdDt(Instant.now());
            fin.setEffStartDt(Instant.now());
            fin.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fin.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        fin.setCrtdBy(crtd_by);
        fin.setEstVal(est_val);
        fin.setLastUpdBy(last_upd_by);
        fin.setSrNum(sr_num);
        return fin;
    }

    public void DomainToDto(MwBizAprslEstLvstkFin fin) {
        biz_aprsl_est_lvstk_fin_seq = fin.getBizAprslEstLvstkFinSeq();
        eff_start_dt = (fin.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(fin.getEffStartDt(), false);
        biz_aprsl_seq = fin.getBizAprslSeq();
        sr_num = fin.getSrNum();
        anml_hc = fin.getAnmlHc();
        est_val = fin.getEstVal();
        anml_knd = fin.getAnmlKnd();
        anml_typ = fin.getAnmlTyp();
        anml_brd = fin.getAnmlBrd();
        amt_by_client = fin.getAmtByClient();
        amt_fin = fin.getAmtFin();
        crtd_by = fin.getCrtdBy();
        crtd_dt = (fin.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(fin.getCrtdDt(), false);
        last_upd_by = fin.getLastUpdBy();
        last_upd_dt = (fin.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(fin.getLastUpdDt(), false);
        del_flg = (fin.getDelFlg() == null) ? 0 : (fin.getDelFlg() ? 1 : 0);
        crnt_rec_flg = (fin.getCrntRecFlg() == null) ? 0 : (fin.getCrntRecFlg() ? 1 : 0);
    }
}
