package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwSchAtnd;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class SchoolAttendanceDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String crtd_dt;

    public String eff_start_dt;

    public Long last_yr_drop;

    public Long sch_aprsl_seq;

    public Long sch_atnd_seq;

    public Long tot_fem_tchrs;

    public Long tot_male_tchrs;

    public String last_upd_by;

    public Integer del_flg;

    //Made by Rizwan on 3 February 2022
    public Long tchrs_attritn;

    public Long tot_suprt_staf;
    //End

    public MwSchAtnd DtoToDomain(SchoolAttendanceDto dto, DateFormat formatter, MwSchAtnd atnd) {
        atnd.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        atnd.setCrtdBy(dto.crtd_by);
        try {
            atnd.setCrtdDt(Common.getZonedInstant(formatter.parse(dto.crtd_dt).toInstant()));
            atnd.setLastUpdDt(Instant.now());
            atnd.setEffStartDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        atnd.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        atnd.setEffStartDt(Instant.now());
        atnd.setLastYrDrop(dto.last_yr_drop);
        atnd.setSchAprslSeq(dto.sch_aprsl_seq);
        atnd.setSchAtndSeq(dto.sch_atnd_seq);
        atnd.setTotFemTchrs(dto.tot_fem_tchrs);
        atnd.setTotMaleTchrs(dto.tot_male_tchrs);
        atnd.setLastUpdBy(last_upd_by);
        //Made by Rizwan on 3 February 2022
        atnd.setTchrs_attritn(tchrs_attritn);
        atnd.setTot_suprt_staf(tot_suprt_staf);
        // End
        return atnd;
    }

    public void DomainToDto(MwSchAtnd atnd) {
        crnt_rec_flg = (atnd.getCrntRecFlg() == null) ? 0 : (atnd.getCrntRecFlg() ? 1 : 0);
        crtd_by = atnd.getCrtdBy();
        crtd_dt = (atnd.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(atnd.getCrtdDt(), false);
        eff_start_dt = (atnd.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(atnd.getEffStartDt(), false);
        last_yr_drop = atnd.getLastYrDrop();
        sch_aprsl_seq = atnd.getSchAprslSeq();
        sch_atnd_seq = atnd.getSchAtndSeq();
        tot_fem_tchrs = atnd.getTotFemTchrs();
        tot_male_tchrs = atnd.getTotMaleTchrs();
        last_upd_by = atnd.getLastUpdBy();
        //Made by Rizwan on 3 February 2022
        tchrs_attritn = atnd.getTchrs_attritn();
        tot_suprt_staf = atnd.getTot_suprt_staf();
        //ENd
        del_flg = (atnd.getDelFlg() == null) ? 0 : (atnd.getDelFlg() ? 1 : 0);
    }
}
