package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwSchGrd;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class SchoolGradesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long avg_grd_fee;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String crtd_dt;

    public Integer del_flg;

    public String eff_start_dt;

    public Long fem_stdnt_prsnt;

    public Long grd_key;

    public Long male_stdnt_prsnt;

    public Long no_fee_stdnt;

    public Long sch_aprsl_seq;

    public Long sch_grd_seq;

    public Long tot_fem_stdnt;

    public Long tot_male_stdnt;

    public String last_upd_by;

    public MwSchGrd DtoToDomain(DateFormat formatter, MwSchGrd grade) {
        grade.setAvgGrdFee(avg_grd_fee);
        grade.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        grade.setCrtdBy(crtd_by);
        try {
            grade.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            grade.setEffStartDt(Instant.now());
            grade.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        grade.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        grade.setFemStdntPrsnt(fem_stdnt_prsnt);
        grade.setGrdKey(grd_key);
        grade.setMaleStdntPrsnt(male_stdnt_prsnt);
        grade.setNoFeeStdnt(no_fee_stdnt);
        grade.setSchAprslSeq(sch_aprsl_seq);
        grade.setSchGrdSeq(sch_grd_seq);
        grade.setTotFemStdnt(tot_fem_stdnt);
        grade.setTotMaleStdnt(tot_male_stdnt);
        grade.setLastUpdBy(last_upd_by);
        return grade;
    }

    public void DomainToDto(MwSchGrd grd) {
        avg_grd_fee = grd.getAvgGrdFee();
        crnt_rec_flg = (grd.getCrntRecFlg() == null) ? 0 : (grd.getCrntRecFlg() ? 1 : 0);
        crtd_by = grd.getCrtdBy();
        crtd_dt = (grd.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(grd.getCrtdDt(), false);
        del_flg = (grd.getDelFlg() == null) ? 0 : (grd.getDelFlg() ? 1 : 0);
        eff_start_dt = (grd.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(grd.getEffStartDt(), false);
        fem_stdnt_prsnt = grd.getFemStdntPrsnt();
        grd_key = grd.getGrdKey();
        male_stdnt_prsnt = grd.getMaleStdntPrsnt();
        no_fee_stdnt = grd.getNoFeeStdnt();
        sch_aprsl_seq = grd.getSchAprslSeq();
        sch_grd_seq = grd.getSchGrdSeq();
        tot_fem_stdnt = grd.getTotFemStdnt();
        tot_male_stdnt = grd.getTotMaleStdnt();
        last_upd_by = grd.getLastUpdBy();
        del_flg = (grd.getDelFlg() == null) ? 0 : (grd.getDelFlg() ? 1 : 0);

    }
}
