package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwSchAsts;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class SchoolInfrastructureDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long sch_asts_seq;

    public Long loan_app_seq;

    public String eff_start_dt;

    public Long tot_rms;

    public Long tot_ofcs;

    public Long male_tlts;

    public Long fmale_tlts;

    public Long mxd_tlts;

    public Long tot_cmptrs;

    public Long tot_chrs;

    public Long tot_dsks;

    public Long scnc_labs;

    public Long tot_wclrs;

    public Long tot_fans;

    public Long tot_gnrtrs;

    public Long tot_flrs;

    public Long cmptr_labs;

    public String crtd_by;

    public String crtd_dt;

    public String last_upd_by;

    public Integer crnt_rec_flg;

    public Integer del_flg;


    public MwSchAsts DtoToDomain(DateFormat formatter, MwSchAsts asts) {
        asts.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        asts.setCrtdBy(crtd_by);
        try {
            asts.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            asts.setEffStartDt(Instant.now());
            asts.setLastUpdDt(Common.getZonedInstant(Instant.now()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        asts.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        asts.setLoanAppSeq(loan_app_seq);
        asts.setTotCmptrLabs(cmptr_labs);
        asts.setSchAstsSeq(sch_asts_seq);
        asts.setTotChrs(tot_chrs);
        asts.setTotCmptrs(tot_cmptrs);
        asts.setTotDsks(tot_dsks);
        asts.setTotFans(tot_fans);
        asts.setTotFmalTolts(fmale_tlts);
        asts.setTotGnrtrs(tot_gnrtrs);
        asts.setTotLabs(scnc_labs);
        asts.setTotMalTolts(male_tlts);
        asts.setTotRms(tot_rms);
        asts.setTotToilets(mxd_tlts);
        asts.setTotWclrs(tot_wclrs);
        asts.setLastUpdBy(last_upd_by);
        asts.setTotOfcs(0L);
        asts.setOthAsts("");
        asts.setTotFlrs(0L);

        return asts;
    }

    public void DomainToDto(MwSchAsts ast) {
        // cmptr_labs = ast.getOthAsts();
        cmptr_labs = ast.getTotCmptrLabs();
        crtd_by = ast.getCrtdBy();
        crtd_dt = (ast.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(ast.getCrtdDt(), false);
        eff_start_dt = (ast.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(ast.getEffStartDt(), false);
        // fmale_tlts = ast.getTotFlrs();
        // id = ast.get;
        loan_app_seq = ast.getLoanAppSeq();
        // male_tlts = ast.getTotOfcs();
        mxd_tlts = ast.getTotToilets();
        sch_asts_seq = ast.getSchAstsSeq();
        scnc_labs = ast.getTotLabs();
        tot_chrs = ast.getTotChrs();
        tot_cmptrs = ast.getTotCmptrs();
        tot_dsks = ast.getTotDsks();
        tot_fans = ast.getTotFans();
        tot_gnrtrs = ast.getTotGnrtrs();
        tot_rms = ast.getTotRms();
        tot_wclrs = ast.getTotWclrs();
        last_upd_by = ast.getLastUpdBy();
        crnt_rec_flg = (ast.getCrntRecFlg() == null) ? 0 : (ast.getCrntRecFlg() ? 1 : 0);
        del_flg = (ast.getDelFlg() == null) ? 0 : (ast.getDelFlg() ? 1 : 0);
        //Added By Rizwan on 25 February 2022
        male_tlts = ast.getTotMalTolts();
        fmale_tlts = ast.getTotFmalTolts();
        cmptr_labs = ast.getTotCmptrLabs();
        tot_ofcs = ast.getTotOfcs();
        tot_flrs = ast.getTotFlrs();
        //End
    }
}
