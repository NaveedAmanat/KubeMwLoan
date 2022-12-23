package com.idev4.loans.domain;

import com.idev4.loans.ids.MwMntrngChksAdcId;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;

/**
 * A MwAddr.
 */
@Entity
@Table(name = "MW_MNTRNG_CHKS_ADC")
@IdClass(MwMntrngChksAdcId.class)
public class MwMntrngChksAdc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MNTRNG_CHKS_SEQ")
    private BigInteger mntrngChksSeq;

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    @Column(name = "INST_NM")
    private String instNm;

    @Column(name = "ADC_AGNT_ID")
    private String adcAgntId;

    @Column(name = "ADC_AGNT_NM")
    private String adcAgntNm;

    @Column(name = "ADC_ADDR")
    private String adcAddr;

    @Column(name = "RMRKS")
    private String rmrks;

    @Id
    @Column(name = "crtd_by")
    private String crtdBy;

    @Column(name = "crtd_dt")
    private Instant crtdDt;

    @Column(name = "last_upd_by")
    private String lastUpdBy;

    @Column(name = "last_upd_dt")
    private Instant lastUpdDt;

    @Column(name = "del_flg")
    private Boolean delFlg;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    public BigInteger getMntrngChksSeq() {
        return mntrngChksSeq;
    }

    public void setMntrngChksSeq(BigInteger mntrngChksSeq) {
        this.mntrngChksSeq = mntrngChksSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public String getInstNm() {
        return instNm;
    }

    public void setInstNm(String instNm) {
        this.instNm = instNm;
    }

    public String getAdcAgntId() {
        return adcAgntId;
    }

    public void setAdcAgntId(String adcAgntId) {
        this.adcAgntId = adcAgntId;
    }

    public String getAdcAgntNm() {
        return adcAgntNm;
    }

    public void setAdcAgntNm(String adcAgntNm) {
        this.adcAgntNm = adcAgntNm;
    }

    public String getAdcAddr() {
        return adcAddr;
    }

    public void setAdcAddr(String adcAddr) {
        this.adcAddr = adcAddr;
    }

    public String getRmrks() {
        return rmrks;
    }

    public void setRmrks(String rmrks) {
        this.rmrks = rmrks;
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

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}
