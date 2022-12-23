package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwMntrngChksAdcQstnr;
import com.idev4.loans.web.rest.util.Common;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class MntrngChksAdcQstnrDto {

    public BigInteger mntrng_chks_adc_qstnr_seq;

    public BigInteger mntrng_chks_seq;

    public String eff_start_dt;

    public Long qst_seq;

    public Long answr_seq;

    public Integer del_flg;

    public Integer crnt_rec_flg;

    public String crtd_dt;

    public String answr_val;

    public MwMntrngChksAdcQstnr DtoToDomain(DateFormat formatter) {
        MwMntrngChksAdcQstnr chk = new MwMntrngChksAdcQstnr();
        chk.setAnswrSeq(answr_seq);
        chk.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        chk.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            chk.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            chk.setEffStartDt(
                    (eff_start_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(eff_start_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        chk.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        chk.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        chk.setLastUpdDt(Instant.now());
        chk.setMntrngChksAdcQstnrSeq(mntrng_chks_adc_qstnr_seq);
        chk.setMntrngChksSeq(mntrng_chks_seq);
        chk.setQstSeq(qst_seq);
        chk.setAnswrVal(answr_val);
        return chk;
    }

    public void DomainToDto(MwMntrngChksAdcQstnr chk) {
        mntrng_chks_adc_qstnr_seq = chk.getMntrngChksAdcQstnrSeq();

        mntrng_chks_seq = chk.getMntrngChksSeq();

        eff_start_dt = (chk.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getEffStartDt(), false);

        qst_seq = chk.getQstSeq();

        answr_seq = chk.getAnswrSeq();

        del_flg = (chk.getDelFlg() == null) ? 0 : (chk.getDelFlg() ? 1 : 0);

        crnt_rec_flg = (chk.getCrntRecFlg() == null) ? 0 : (chk.getCrntRecFlg() ? 1 : 0);

        crtd_dt = (chk.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getCrtdDt(), false);

        answr_val = chk.getAnswrVal();
    }
}
