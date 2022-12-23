package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwBizAprslIncmHdr;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class BizAppIncmHdrDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long biz_aprsl_seq;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String crtd_dt;

    public Integer del_flg;

    public String eff_start_dt;

    public Long incm_hdr_seq;

    public Double max_mnth_sal_amt;

    public Integer max_sal_num_of_mnth;

    public Double min_mnth_sal_amt;

    public Integer min_sal_num_of_mnth;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer sync_flg;

    @Override
    public String toString() {
        return "BizAppIncmHdrDto [biz_aprsl_seq=" + biz_aprsl_seq + ", crnt_rec_flg=" + crnt_rec_flg + ", crtd_by=" + crtd_by + ", crtd_dt="
                + crtd_dt + ", del_flg=" + del_flg + ", eff_start_dt=" + eff_start_dt + ", incm_hdr_seq=" + incm_hdr_seq
                + ", max_mnth_sal_amt=" + max_mnth_sal_amt + ", max_sal_num_of_mnth=" + max_sal_num_of_mnth + ", min_mnth_sal_amt="
                + min_mnth_sal_amt + ", min_sal_num_of_mnth=" + min_sal_num_of_mnth + "]";
    }

    public MwBizAprslIncmHdr DtotoDomain(DateFormat formatter, MwBizAprslIncmHdr hdr) {

        hdr.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        hdr.setCrtdBy(crtd_by);
        try {
            hdr.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            hdr.setEffStartDt(Instant.now());
            hdr.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        hdr.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        hdr.setIncmHdrSeq(incm_hdr_seq);
        hdr.setMaxMnthSalAmt(max_mnth_sal_amt);
        hdr.setMaxSalNumOfMnth(max_sal_num_of_mnth);
        hdr.setMinMnthSalAmt(min_mnth_sal_amt);
        hdr.setMinSalNumOfMnth(min_sal_num_of_mnth);
        hdr.setMwBizAprsl(biz_aprsl_seq);
        hdr.setLastUpdBy(last_upd_by);
        return hdr;
    }

    public BizAppIncmHdrDto DomainToDto(MwBizAprslIncmHdr hdr) {
        BizAppIncmHdrDto dto = new BizAppIncmHdrDto();
        biz_aprsl_seq = hdr.getMwBizAprsl();
        crnt_rec_flg = (hdr.isCrntRecFlg() == null) ? 0 : (hdr.isCrntRecFlg() ? 1 : 0);
        crtd_by = hdr.getCrtdBy();
        crtd_dt = (hdr.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(hdr.getCrtdDt(), false);
        del_flg = (hdr.isDelFlg() == null) ? 0 : (hdr.isDelFlg() ? 1 : 0);
        eff_start_dt = (hdr.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(hdr.getEffStartDt(), false);
        incm_hdr_seq = hdr.getIncmHdrSeq();
        max_mnth_sal_amt = hdr.getMaxMnthSalAmt();
        max_sal_num_of_mnth = hdr.getMaxSalNumOfMnth();
        min_mnth_sal_amt = hdr.getMinMnthSalAmt();
        min_sal_num_of_mnth = hdr.getMinSalNumOfMnth();
        last_upd_by = hdr.getLastUpdBy();
        last_upd_dt = (hdr.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(hdr.getLastUpdDt(), false);
        sync_flg = (hdr.getSyncFlg() == null) ? 0 : (hdr.getSyncFlg() ? 1 : 0);
        del_flg = (hdr.isDelFlg() == null) ? 0 : (hdr.isDelFlg() ? 1 : 0);
        return dto;
    }
}