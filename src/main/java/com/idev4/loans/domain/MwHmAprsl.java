package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "MW_HM_APRSL")
public class MwHmAprsl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "HM_APRSL_SEQ")
    private Long hmAprslSeq;

    @Column(name = "BIZ_APRSL_SEQ")
    private Long bizAprslSeq;

    @Column(name = "YR_OF_CNSTRCTN")
    private Long yrOfCnstrctn;

    @Column(name = "NO_OF_FLRS")
    private Long noOfFlrs;

    @Column(name = "PLOT_AREA_IN_MRLA")
    private Double plotAreaInMrla;

    @Column(name = "NO_OF_ROOMS")
    private Long noOfRooms;

    @Column(name = "NO_OF_BDROOM")
    private Long noOfBdroom;

    @Column(name = "NO_OF_WSHROOM")
    private Long noOfWshroom;

    @Column(name = "IS_KTCHN_SEPRT")
    private Long isKtchnSeprt;

    @Column(name = "crtd_by")
    private String crtdBy;

    @Column(name = "crtd_dt")
    private Instant crtdDt;

    @Column(name = "last_upd_by")
    private String lastUpdBy;

    @Column(name = "last_upd_dt")
    private Instant lastUpdDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    public Long getHmAprslSeq() {
        return hmAprslSeq;
    }

    public void setHmAprslSeq(Long hmAprslSeq) {
        this.hmAprslSeq = hmAprslSeq;
    }

    public Long getBizAprslSeq() {
        return bizAprslSeq;
    }

    public void setBizAprslSeq(Long bizAprslSeq) {
        this.bizAprslSeq = bizAprslSeq;
    }

    public Long getYrOfCnstrctn() {
        return yrOfCnstrctn;
    }

    public void setYrOfCnstrctn(Long yrOfCnstrctn) {
        this.yrOfCnstrctn = yrOfCnstrctn;
    }

    public Long getNoOfFlrs() {
        return noOfFlrs;
    }

    public void setNoOfFlrs(Long noOfFlrs) {
        this.noOfFlrs = noOfFlrs;
    }

    public Double getPlotAreaInMrla() {
        return plotAreaInMrla;
    }

    public void setPlotAreaInMrla(Double plotAreaInMrla) {
        this.plotAreaInMrla = plotAreaInMrla;
    }

    public Long getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Long noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public Long getNoOfBdroom() {
        return noOfBdroom;
    }

    public void setNoOfBdroom(Long noOfBdroom) {
        this.noOfBdroom = noOfBdroom;
    }

    public Long getNoOfWshroom() {
        return noOfWshroom;
    }

    public void setNoOfWshroom(Long noOfWshroom) {
        this.noOfWshroom = noOfWshroom;
    }

    public Long getIsKtchnSeprt() {
        return isKtchnSeprt;
    }

    public void setIsKtchnSeprt(Long isKtchnSeprt) {
        this.isKtchnSeprt = isKtchnSeprt;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    @Override
    public String toString() {
        return "MwHmAprsl{" +
                "hmAprslSeq=" + hmAprslSeq +
                ", bizAprslSeq=" + bizAprslSeq +
                ", yrOfCnstrctn=" + yrOfCnstrctn +
                ", noOfFlrs=" + noOfFlrs +
                ", plotAreaInMrla=" + plotAreaInMrla +
                ", noOfRooms=" + noOfRooms +
                ", noOfBdroom=" + noOfBdroom +
                ", noOfWshroom=" + noOfWshroom +
                ", isKtchnSeprt=" + isKtchnSeprt +
                ", crtdBy='" + crtdBy + '\'' +
                ", crtdDt=" + crtdDt +
                ", lastUpdBy='" + lastUpdBy + '\'' +
                ", lastUpdDt=" + lastUpdDt +
                ", crntRecFlg=" + crntRecFlg +
                '}';
    }
}