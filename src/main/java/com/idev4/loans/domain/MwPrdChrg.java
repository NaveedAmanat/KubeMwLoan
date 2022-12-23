package com.idev4.loans.domain;


import com.idev4.loans.ids.MwPrdChrgId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "mw_prd_chrg")
@IdClass(MwPrdChrgId.class)
public class MwPrdChrg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "prd_chrg_seq")
    private Long prdChrgSeq;

    @Column(name = "prd_seq")
    private Long prdSeq;

    @Column(name = "rul_seq")
    private Long rulSeq;

    @Column(name = "chrg_calc_typ_key")
    private Long chrgCalcTypKey;

    @Column(name = "chrg_val")
    private Double chrgVal;

    @Column(name = "upfront_flg")
    private Boolean upfrontFlg;

    @Column(name = "sgrt_inst_num")
    private Long sgrtInstNum;

    @Column(name = "adjust_rounding_flg")
    private Boolean adjustRoundingFlg;

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

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "chrg_typ_SEQ")
    private long chrgTypSeq;

    public long getChrgTypSeq() {
        return chrgTypSeq;
    }

    public void setChrgTypSeq(long chrgTypSeq) {
        this.chrgTypSeq = chrgTypSeq;
    }


    public Long getPrdChrgSeq() {
        return prdChrgSeq;
    }

    public void setPrdChrgSeq(Long prdChrgSeq) {
        this.prdChrgSeq = prdChrgSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Long getRulSeq() {
        return rulSeq;
    }

    public void setRulSeq(Long rulSeq) {
        this.rulSeq = rulSeq;
    }

    public Long getChrgCalcTypKey() {
        return chrgCalcTypKey;
    }

    public void setChrgCalcTypKey(Long chrgCalcTypKey) {
        this.chrgCalcTypKey = chrgCalcTypKey;
    }

    public Double getChrgVal() {
        return chrgVal;
    }

    public void setChrgVal(Double chrgVal) {
        this.chrgVal = chrgVal;
    }

    public Boolean getUpfrontFlg() {
        return upfrontFlg;
    }

    public void setUpfrontFlg(Boolean upfrontFlg) {
        this.upfrontFlg = upfrontFlg;
    }

    public Long getSgrtInstNum() {
        return sgrtInstNum;
    }

    public void setSgrtInstNum(Long sgrtInstNum) {
        this.sgrtInstNum = sgrtInstNum;
    }

    public Boolean getAdjustRoundingFlg() {
        return adjustRoundingFlg;
    }

    public void setAdjustRoundingFlg(Boolean adjustRoundingFlg) {
        this.adjustRoundingFlg = adjustRoundingFlg;
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

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
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

}
