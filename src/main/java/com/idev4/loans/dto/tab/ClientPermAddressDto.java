package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwClntPermAddr;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class ClientPermAddressDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long clnt_seq;

    public String crtd_by;

    public String crtd_dt;

    public String eff_start_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public String perm_addr_str;

    public String sync_flg;

    public Long clnt_perm_addr_seq;

    public Integer crnt_rec_flg;

    public Integer del_flg;

    public MwClntPermAddr DtoToDomain(DateFormat formatter, MwClntPermAddr perm) {
        perm.setClntSeq(clnt_seq);
        perm.setCrtdBy(crtd_by);
        try {
            perm.setCrtdDt(Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            perm.setEffStartDt(Instant.now());
            perm.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        perm.setLastUpdBy(last_upd_by);
        perm.setPermAddrStr(perm_addr_str);
        perm.setClntPermAddrSeq(clnt_perm_addr_seq);
        perm.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        perm.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        return perm;
    }

    public void DomainToDto(MwClntPermAddr adr) {
        clnt_seq = adr.getClntSeq();
        crtd_by = adr.getCrtdBy();
        crtd_dt = Common.GetFormattedDateForTab(adr.getCrtdDt(), false);
        eff_start_dt = Common.GetFormattedDateForTab(adr.getEffStartDt(), false);
        last_upd_dt = Common.GetFormattedDateForTab(adr.getLastUpdDt(), false);
        last_upd_by = adr.getLastUpdBy();
        perm_addr_str = adr.getPermAddrStr();
        clnt_perm_addr_seq = adr.getClntPermAddrSeq();
        crnt_rec_flg = (adr.isCrntRecFlg() == null) ? 0 : (adr.isCrntRecFlg() ? 1 : 0);
        del_flg = (adr.isDelFlg() == null) ? 0 : (adr.isDelFlg() ? 1 : 0);
    }
}
