package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwAddrRel;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class AddrRelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long addr_rel_seq;

    public Long addr_seq;

    public String crtd_by;

    public String eff_start_dt;

    public Long enty_key;

    public String enty_typ;

    public String crtd_dt;

    public String last_upd_by;

    public String last_upd_dt;

    public Integer sync_flg;

    public Integer crnt_rec_flg;

    public Integer del_flg;

    @Override
    public String toString() {
        return "AddrRelDto [addr_rel_seq=" + addr_rel_seq + ", addr_seq=" + addr_seq + ", crtd_by=" + crtd_by + ", eff_start_dt="
                + eff_start_dt + ", enty_key=" + enty_key + ", enty_typ=" + enty_typ + "]";
    }

    public MwAddrRel DtoToDomain(DateFormat formatter, MwAddrRel addrRel) {
//        addrRel.setAddrRelSeq( addr_rel_seq );
//        addrRel.setAddrSeq( addr_seq );
        addrRel.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        addrRel.setCrtdBy(crtd_by);
        // addrRel.setCrtdDt(client.client_address.);
        addrRel.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        try {
            addrRel.setEffStartDt(Instant.now());
            addrRel.setCrtdDt((crtd_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            addrRel.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        addrRel.setEntySeq(enty_key);
        addrRel.setEntyType(enty_typ);
        addrRel.setLastUpdBy(last_upd_by);
        return addrRel;
    }

    public void DomainToDto(MwAddrRel rel) {
        AddrRelDto dto = new AddrRelDto();
        addr_rel_seq = rel.getAddrRelSeq();
        addr_seq = rel.getAddrSeq();
        crtd_by = rel.getCrtdBy();
        eff_start_dt = (rel.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(rel.getEffStartDt(), false);
        enty_key = rel.getEntySeq();
        enty_typ = rel.getEntyType();
        crtd_dt = (rel.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(rel.getCrtdDt(), false);
        last_upd_by = rel.getLastUpdBy();
        last_upd_dt = (rel.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(rel.getLastUpdDt(), false);
        sync_flg = (rel.getSyncFlg() == null) ? 0 : (rel.getSyncFlg() ? 1 : 0);
        crnt_rec_flg = (rel.isCrntRecFlg() == null) ? 0 : (rel.isCrntRecFlg() ? 1 : 0);
        del_flg = (rel.isDelFlg() == null) ? 0 : (rel.isDelFlg() ? 1 : 0);
    }
}
