package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwAddr;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;

public class AddressDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long addr_seq;

    public Long city_seq;

    public Long cmnty_seq;

    public Integer crnt_rec_flg;

    public String crtd_by;

    public String crtd_dt;

    public String eff_start_dt;

    public String hse_num;

    public String oth_dtl;

    public String strt;

    public String vlg;

    public String last_upd_by;

    public String last_upd_dt;

    public Double latitude;

    public Double longitude;

    public Integer sync_flg;

    public Integer del_flg;

    @Override
    public String toString() {
        return "AddressDto [addr_seq=" + addr_seq + ", city_seq=" + city_seq + ", cmnty_seq=" + cmnty_seq
                + ", crnt_rec_flg=" + crnt_rec_flg + ", crtd_by=" + crtd_by + ", crtd_dt=" + crtd_dt + ", eff_start_dt="
                + eff_start_dt + ", hse_num=" + hse_num + ", oth_dtl=" + oth_dtl + ", strt=" + strt + ", vlg=" + vlg
                + "]";
    }

    public MwAddr DtoToDomain(DateFormat formatter, String curUser, MwAddr address) {
//        address.setAddrSeq( addr_seq );
        address.setCitySeq(city_seq);
        address.setCmntySeq(cmnty_seq);
        address.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);
        address.setCrtdBy(crtd_by);
        try {
            address.setCrtdDt(
                    (crtd_dt == null) ? Instant.now() : Common.getZonedInstant(formatter.parse(crtd_dt).toInstant()));
            address.setEffStartDt(Instant.now());
            address.setLastUpdDt(Instant.now());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        address.setDelFlg((del_flg == null) ? false : (del_flg == 1) ? true : false);
        address.setLastUpdBy(last_upd_by);
        address.setLatitude(latitude);
        address.setLongitude(longitude);

        address.setHseNum(hse_num.length() > 40 ? hse_num.substring(0, 40) : hse_num);
        address.setOthDtl(oth_dtl.length() > 500 ? oth_dtl.substring(0, 500) : oth_dtl);
        address.setStrt(strt.length() > 100 ? strt.substring(0, 100) : strt);
        address.setVlg(vlg.length() > 100 ? vlg.substring(0, 100) : vlg);

        return address;
    }

    public void DomainToDto(MwAddr adr) {

        AddressDto dto = new AddressDto();
        addr_seq = adr.getAddrSeq();
        city_seq = adr.getCitySeq();
        cmnty_seq = adr.getCmntySeq();
        crnt_rec_flg = (adr.getCrntRecFlg() == null) ? 0 : (adr.getCrntRecFlg() ? 1 : 0);
        crtd_by = adr.getCrtdBy();
        crtd_dt = (adr.getCrtdDt() == null) ? "" : Common.GetFormattedDateForTab(adr.getCrtdDt(), false);
        eff_start_dt = (adr.getEffStartDt() == null) ? "" : Common.GetFormattedDateForTab(adr.getEffStartDt(), false);

        if (adr.getHseNum() != null)
            hse_num = adr.getHseNum().length() > 40 ? adr.getHseNum().substring(0, 40) : adr.getHseNum();
        else hse_num = "";
        if (adr.getOthDtl() != null)
            oth_dtl = adr.getOthDtl().length() > 500 ? adr.getOthDtl().substring(0, 500) : adr.getOthDtl();
        else oth_dtl = "";
        if (adr.getStrt() != null)
            strt = adr.getStrt().length() > 100 ? adr.getStrt().substring(0, 100) : adr.getStrt();
        else strt = "";
        if (adr.getVlg() != null) vlg = adr.getVlg().length() > 100 ? adr.getVlg().substring(0, 100) : adr.getVlg();
        else vlg = "";

        last_upd_by = adr.getLastUpdBy();
        last_upd_dt = (adr.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(adr.getLastUpdDt(), false);
        latitude = adr.getLatitude();
        longitude = adr.getLongitude();
        sync_flg = (adr.getSyncFlg() == null) ? 0 : (adr.getSyncFlg() ? 1 : 0);
        del_flg = (adr.isDelFlg() == null) ? 0 : (adr.isDelFlg() ? 1 : 0);
    }
}
