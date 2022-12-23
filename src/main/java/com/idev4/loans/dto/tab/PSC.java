package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwClntPsc;
import com.idev4.loans.web.rest.util.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class PSC implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(PSC.class);
    public Long answr_seq;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String crtd_dt;

    public String eff_start_dt;

    public Long loan_app_seq;

    public Long psc_seq;

    public Long qst_seq;

    public Integer sync_flg;

    public String last_upd_by;

    public Integer del_flg;

    @Override
    public String toString() {
        return "PSC [answr_seq=" + answr_seq + ", crnt_rec_flg=" + crnt_rec_flg + ", crtd_by=" + crtd_by + ", crtd_dt=" + crtd_dt
                + ", eff_start_dt=" + eff_start_dt + ", loan_app_seq=" + loan_app_seq + ", psc_seq=" + psc_seq + ", qst_seq=" + qst_seq
                + "]";
    }

    public MwClntPsc DtoToDomain(DateFormat formatter, MwClntPsc psc) {

        psc.setAnswrSeq(answr_seq);
        psc.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        psc.setCrtdBy(crtd_by);
        try {
            psc.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            psc.setEffStartDt(Instant.now());
            psc.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        psc.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        psc.setLoanAppSeq(loan_app_seq);
        psc.setPscSeq(psc_seq);
        psc.setQstSeq(qst_seq);
        psc.setLastUpdBy(last_upd_by);
        return psc;
    }

    public void DomainToDto(MwClntPsc psc) {
        answr_seq = psc.getAnswrSeq();
        crnt_rec_flg = (psc.isCrntRecFlg() == null) ? 0 : (psc.isCrntRecFlg() ? 1 : 0);
        crtd_by = psc.getCrtdBy();
        crtd_dt = (psc.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(psc.getCrtdDt(), false);
        eff_start_dt = (psc.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(psc.getEffStartDt(), false);
        loan_app_seq = psc.getLoanAppSeq();
        psc_seq = psc.getPscSeq();
        qst_seq = psc.getQstSeq();
        sync_flg = (psc.getSyncFlg() == null) ? 0 : (psc.getSyncFlg() ? 1 : 0);
        last_upd_by = psc.getLastUpdBy();
        del_flg = (psc.isDelFlg() == null) ? 0 : (psc.isDelFlg() ? 1 : 0);
    }
}