package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwBizAprslIncmDtl;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class BizAprslIncmDtlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer crnt_rec_flg;

    public String crtd_dt;

    public String crtd_by;

    public Integer del_flg;

    public String eff_start_dt;

    public Double incm_amt;

    public Long incm_ctgry_key;

    public Long incm_dtl_seq;

    public Long incm_hdr_seq;

    public Long incm_typ_key;

    public Integer enty_typ_flg;

    public Integer sync_flg;

    public String last_upd_by;

    @Override
    public String toString() {
        return "BizAprslIncmDtlDto [crnt_rec_flg=" + crnt_rec_flg + ", crtd_dt=" + crtd_dt + ", del_flg=" + del_flg + ", eff_start_dt="
                + eff_start_dt + ", incm_amt=" + incm_amt + ", incm_ctgry_key=" + incm_ctgry_key + ", incm_dtl_seq=" + incm_dtl_seq
                + ", incm_hdr_seq=" + incm_hdr_seq + ", incm_typ_key=" + incm_typ_key + ", enty_typ_flg=" + enty_typ_flg + "]";
    }

    public MwBizAprslIncmDtl DtoToDomain(DateFormat formatter, MwBizAprslIncmDtl dtl) {
        dtl.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        dtl.setCrtdBy(crtd_by);
        try {
            dtl.setCrtdDt((crtd_dt) == null ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            dtl.setEffStartDt(
                    (eff_start_dt) == null ? Instant.now() : Common.getZonedInstant(formatter.parse(eff_start_dt).toInstant()));
            dtl.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dtl.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        dtl.setEntyTypFlg(enty_typ_flg);
        dtl.setIncmAmt(incm_amt);
        dtl.setIncmCtgryKey(incm_ctgry_key);
        dtl.setIncmDtlSeq(incm_dtl_seq);
        dtl.setIncmTypKey(incm_typ_key);
        dtl.setMwBizAprslIncmHdr(incm_hdr_seq);
        dtl.setLastUpdBy(last_upd_by);
        return dtl;
    }

    public void DomainToDto(MwBizAprslIncmDtl dtl) {
        BizAprslIncmDtlDto dto = new BizAprslIncmDtlDto();
        crnt_rec_flg = (dtl.isCrntRecFlg() == null) ? 0 : (dtl.isCrntRecFlg() ? 1 : 0);
        crtd_dt = (dtl.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(dtl.getCrtdDt(), false);
        del_flg = (dtl.isDelFlg() == null) ? 0 : (dtl.isDelFlg() ? 1 : 0);
        eff_start_dt = (dtl.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(dtl.getEffStartDt(), false);
        enty_typ_flg = dtl.getEntyTypFlg();
        incm_amt = dtl.getIncmAmt();
        incm_ctgry_key = dtl.getIncmCtgryKey();
        incm_dtl_seq = dtl.getIncmDtlSeq();
        incm_hdr_seq = dtl.getMwBizAprslIncmHdr();
        incm_typ_key = dtl.getIncmTypKey();
        sync_flg = (dtl.getSyncFlg() == null) ? 0 : (dtl.getSyncFlg() ? 1 : 0);
        crtd_by = dtl.getCrtdBy();
        last_upd_by = dtl.getLastUpdBy();
    }
}