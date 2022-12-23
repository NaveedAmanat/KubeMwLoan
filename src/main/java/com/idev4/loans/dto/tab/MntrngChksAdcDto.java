package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwMntrngChksAdc;
import com.idev4.loans.web.rest.util.Common;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class MntrngChksAdcDto {

    public BigInteger mntrng_chks_seq;

    public Long brnch_seq;

    public Integer del_flg;

    public Integer crnt_rec_flg;

    public String crtd_dt;

    public String eff_start_dt;

    public String adc_agnt_id;

    public String adc_agnt_nm;

    public String adc_addr;

    public String rmrks;

    public String inst_nm;
    public Double latitude;

    public Double longitude;

    public MwMntrngChksAdc DtoToDomain(DateFormat formatter) {
        MwMntrngChksAdc chk = new MwMntrngChksAdc();
        chk.setAdcAddr(adc_addr);
        chk.setAdcAgntId(adc_agnt_id);
        chk.setAdcAgntNm(adc_agnt_nm);
        chk.setBrnchSeq(brnch_seq);
        chk.setInstNm(inst_nm);
        chk.setMntrngChksSeq(mntrng_chks_seq);
        chk.setRmrks(rmrks);
        chk.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        chk.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        // chk.setEffStartDt( Instant.now() );
        try {
            chk.setCrtdDt((crtd_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            chk.setEffStartDt(
                    (eff_start_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(eff_start_dt).toInstant()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        chk.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        chk.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        chk.setLastUpdDt(Instant.now());
        chk.setLatitude(latitude);
        chk.setLongitude(longitude);
        return chk;
    }

    public void DomainToDto(MwMntrngChksAdc chk) {
        mntrng_chks_seq = chk.getMntrngChksSeq();

        brnch_seq = chk.getBrnchSeq();

        del_flg = (chk.getDelFlg() == null) ? 0 : (chk.getDelFlg() ? 1 : 0);

        crnt_rec_flg = (chk.getCrntRecFlg() == null) ? 0 : (chk.getCrntRecFlg() ? 1 : 0);

        crtd_dt = (chk.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getCrtdDt(), false);

        eff_start_dt = (chk.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(chk.getEffStartDt(), false);

        adc_agnt_id = chk.getAdcAgntId();

        adc_agnt_nm = chk.getAdcAgntNm();

        adc_addr = chk.getAdcAddr();

        rmrks = chk.getRmrks();

        inst_nm = chk.getInstNm();

        latitude = chk.getLatitude();
        longitude = chk.getLongitude();
    }

}
