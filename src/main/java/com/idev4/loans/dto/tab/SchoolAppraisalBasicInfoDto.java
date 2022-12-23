package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwSchAprsl;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class SchoolAppraisalBasicInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long bldng_own_key;

    public String crtd_by;

    public String crtd_dt;

    public String eff_start_dt;

    public Long loan_app_seq;

    public Long pef_spt_flg;

    public Long rel_wth_own_key;

    public Long sch_aprsl_seq;

    public Long sch_area;

    public Long sch_area_unt_key;

    public Long sch_lvl_key;

    public Long sch_medm_key;

    public String sch_nm;

    public Long sch_own_typ_key;

    public Long sch_ppal_key;

    public Long sch_regd_flg;

    public Long sch_typ_key;

    public Long sch_yr;

    public Integer crnt_rec_flg;

    public String last_upd_by;

    public Integer del_flg;

    public String sch_regd_agy;

    public MwSchAprsl DtoToDomain(DateFormat formatter, MwSchAprsl aprsl) {
        aprsl.setBldngOwnKey(bldng_own_key);
        aprsl.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        aprsl.setCrtdBy(crtd_by);
        try {
            aprsl.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            aprsl.setEffStartDt(Instant.now());
            aprsl.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        aprsl.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        aprsl.setLoanAppSeq(loan_app_seq);
        aprsl.setPefSptFlg(pef_spt_flg);
        aprsl.setRelWthOwnKey(rel_wth_own_key);
        aprsl.setSchAprslSeq(sch_aprsl_seq);
        aprsl.setSchArea(sch_area);
        aprsl.setSchAreaUntKey(sch_area_unt_key);
        aprsl.setSchLvlKey(sch_lvl_key);
        aprsl.setSchMedmKey(sch_medm_key);
        aprsl.setSchNm(sch_nm);
        aprsl.setSchOwnTypKey(sch_own_typ_key);
        aprsl.setSchPpalKey(sch_ppal_key);
        aprsl.setSchRegdFlg(sch_regd_flg);
        aprsl.setSchTypKey(sch_typ_key);
        aprsl.setSchYr(sch_yr);
        aprsl.setLastUpdBy(last_upd_by);
        aprsl.setSchRegdAgy(sch_regd_agy);
        return aprsl;
    }

    public void DomainToDto(MwSchAprsl aprsl) {
        bldng_own_key = aprsl.getBldngOwnKey();
        crtd_by = aprsl.getCrtdBy();
        crtd_dt = (aprsl.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(aprsl.getCrtdDt(), false);
        eff_start_dt = (aprsl.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(aprsl.getEffStartDt(), false);
        loan_app_seq = aprsl.getLoanAppSeq();
        pef_spt_flg = aprsl.getPefSptFlg();
        rel_wth_own_key = aprsl.getRelWthOwnKey();
        sch_aprsl_seq = aprsl.getSchAprslSeq();
        sch_area = aprsl.getSchArea();
        sch_area_unt_key = aprsl.getSchAreaUntKey();
        sch_lvl_key = aprsl.getSchLvlKey();
        sch_medm_key = aprsl.getSchMedmKey();
        sch_nm = aprsl.getSchNm();
        sch_own_typ_key = aprsl.getSchOwnTypKey();
        sch_ppal_key = aprsl.getSchPpalKey();
        sch_regd_flg = aprsl.getSchRegdFlg();
        sch_typ_key = aprsl.getSchTypKey();
        sch_yr = aprsl.getSchYr();
        last_upd_by = aprsl.getLastUpdBy();
        del_flg = (aprsl.getDelFlg() == null) ? 0 : (aprsl.getDelFlg() ? 1 : 0);
        crnt_rec_flg = (aprsl.getCrntRecFlg() == null) ? 0 : (aprsl.getCrntRecFlg() ? 1 : 0);
        sch_regd_agy = aprsl.getSchRegdAgy();
    }
}
