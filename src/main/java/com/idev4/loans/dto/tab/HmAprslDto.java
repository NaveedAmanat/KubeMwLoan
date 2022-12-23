package com.idev4.loans.dto.tab;

import com.idev4.loans.domain.MwHmAprsl;
import com.idev4.loans.web.rest.util.Common;

import java.io.Serializable;
import java.text.DateFormat;
import java.time.Instant;

public class HmAprslDto implements Serializable {

    public long hm_aprsl_seq;
    public long biz_aprsl_seq;
    public long yr_of_cnstrctn;
    public long no_of_flrs;
    public double plot_area_in_mrla;
    public long no_of_rooms;
    public long no_of_bdroom;
    public long no_of_wshroom;
    public long is_ktchn_seprt;
    public Integer crnt_rec_flg;
    public String last_upd_by;
    public String last_upd_dt;
    public String crtd_by;
    public String crtd_dt;

    @Override
    public String toString() {
        return "HmAprslDto{" +
                "hm_aprsl_seq=" + hm_aprsl_seq +
                ", biz_aprsl_seq=" + biz_aprsl_seq +
                ", yr_of_cnstrctn=" + yr_of_cnstrctn +
                ", no_of_flrs=" + no_of_flrs +
                ", plot_area_in_mrla=" + plot_area_in_mrla +
                ", no_of_rooms=" + no_of_rooms +
                ", no_of_bdroom=" + no_of_bdroom +
                ", no_of_wshroom=" + no_of_wshroom +
                ", is_ktchn_seprt=" + is_ktchn_seprt +
                ", crnt_rec_flg=" + crnt_rec_flg +
                ", last_upd_by='" + last_upd_by + '\'' +
                ", last_upd_dt='" + last_upd_dt + '\'' +
                ", crtd_by='" + crtd_by + '\'' +
                ", crtd_dt='" + crtd_dt + '\'' +
                '}';
    }

    public MwHmAprsl DtoToDomain(DateFormat formatter, DateFormat simpleFormatter, String curUser,
                                 MwHmAprsl Aprsl) {

        Aprsl.setHmAprslSeq(hm_aprsl_seq);
        Aprsl.setBizAprslSeq(biz_aprsl_seq);
        Aprsl.setYrOfCnstrctn(yr_of_cnstrctn);
        Aprsl.setNoOfFlrs(no_of_flrs);
        Aprsl.setPlotAreaInMrla(plot_area_in_mrla);
        Aprsl.setNoOfRooms(no_of_rooms);
        Aprsl.setNoOfBdroom(no_of_bdroom);
        Aprsl.setNoOfWshroom(no_of_wshroom);
        Aprsl.setIsKtchnSeprt(is_ktchn_seprt);
        Aprsl.setCrtdBy(curUser);
        Aprsl.setCrtdDt(Instant.now());
        Aprsl.setLastUpdBy(curUser);
        Aprsl.setLastUpdDt(Instant.now());
        Aprsl.setCrntRecFlg((crnt_rec_flg == null) ? true : (crnt_rec_flg == 1) ? true : false);

        return Aprsl;
    }

    public void DomainToDto(MwHmAprsl mwHmAprsl) {
        hm_aprsl_seq = mwHmAprsl.getBizAprslSeq();
        biz_aprsl_seq = mwHmAprsl.getBizAprslSeq();
        yr_of_cnstrctn = mwHmAprsl.getYrOfCnstrctn();
        no_of_flrs = mwHmAprsl.getNoOfFlrs();
        plot_area_in_mrla = mwHmAprsl.getPlotAreaInMrla();
        no_of_rooms = mwHmAprsl.getNoOfRooms();
        no_of_bdroom = mwHmAprsl.getNoOfBdroom();
        no_of_wshroom = mwHmAprsl.getNoOfWshroom();
        is_ktchn_seprt = mwHmAprsl.getIsKtchnSeprt();
        crtd_by = mwHmAprsl.getCrtdBy();
        last_upd_by = mwHmAprsl.getLastUpdBy();
        last_upd_dt = (mwHmAprsl.getLastUpdDt() == null) ? "" : Common.GetFormattedDateForTab(mwHmAprsl.getLastUpdDt(), false);
        crnt_rec_flg = (mwHmAprsl.getCrntRecFlg() == null) ? 0 : (mwHmAprsl.getCrntRecFlg() ? 1 : 0);
    }
}