package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwSchQltyChk;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class SchoolQuestionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long answr_seq;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String crtd_dt;

    public String eff_start_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Long qst_seq;

    public Long sch_aprsl_seq;

    public Long sch_qlty_chk_seq;

    public Integer sync_flg;

    public Integer del_flg;

    public MwSchQltyChk DtoToDomain(DateFormat formatter) {
        MwSchQltyChk chk = new MwSchQltyChk();
        chk.setAnswrSeq(answr_seq);
        chk.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        chk.setCrtdBy(crtd_by);
        try {
            chk.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            chk.setEffStartDt(Instant.now());
            chk.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        chk.setQstSeq(qst_seq);
        chk.setSchAprslSeq(sch_aprsl_seq);
        chk.setSchQltyChkSeq(sch_qlty_chk_seq);
        chk.setLastUpdBy(last_upd_by);
        chk.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        return chk;
    }

    public void DomainToDto(MwSchQltyChk chk) {
        answr_seq = chk.getAnswrSeq();
        crnt_rec_flg = (chk.getCrntRecFlg() == null) ? 0 : (chk.getCrntRecFlg() ? 1 : 0);
        crtd_by = chk.getCrtdBy();
        crtd_dt = (chk.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getCrtdDt(), false);
        eff_start_dt = (chk.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getEffStartDt(), false);
        // id = chk.get;
        last_upd_by = chk.getLastUpdBy();
        // last_upd_dt = chk.getLastUpdDt().;
        qst_seq = chk.getQstSeq();
        sch_aprsl_seq = chk.getSchAprslSeq();
        sch_qlty_chk_seq = chk.getSchQltyChkSeq();
        sync_flg = (chk.getSyncFlg() == null) ? 0 : (chk.getSyncFlg() ? 1 : 0);
        last_upd_by = chk.getLastUpdBy();

        del_flg = (chk.getDelFlg() == null) ? 0 : (chk.getDelFlg() ? 1 : 0);
    }
}
