package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "MW_BIZ_APRSL_EXTNG_LVSTK")
// @IdClass ( MwBizAprslExtngLvstkId.class )
public class MwBizAprslExtngLvstk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "BIZ_APRSL_EXTNG_LVSTK_SEQ")
    private Long bizAprslExtngLvstkSeq;

    // @Id
    @Column(name = "EFF_START_DT")
    private Instant effStartDt;

    @Column(name = "BIZ_APRSL_SEQ")
    private Long bizAprslSeq;

    @Column(name = "SR_NUM")
    private Long srNum;

    @Column(name = "ANML_HC")
    private Long anmlHc;

    @Column(name = "EST_VAL")
    private Long estVal;

    @Column(name = "ANML_KND")
    private Long anmlKnd;

    @Column(name = "ANML_TYP")
    private Long anmlTyp;

    @Column(name = "ANML_BRD")
    private String anmlBrd;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpdDt;

    @Column(name = "DEL_FLG")
    private Boolean delFlg;

    @Column(name = "EFF_END_DT")
    private Instant effEndDt;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    public Long getBizAprslExtngLvstkSeq() {
        return bizAprslExtngLvstkSeq;
    }

    public void setBizAprslExtngLvstkSeq(Long bizAprslExtngLvstkSeq) {
        this.bizAprslExtngLvstkSeq = bizAprslExtngLvstkSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getBizAprslSeq() {
        return bizAprslSeq;
    }

    public void setBizAprslSeq(Long bizAprslSeq) {
        this.bizAprslSeq = bizAprslSeq;
    }

    public Long getSrNum() {
        return srNum;
    }

    public void setSrNum(Long srNum) {
        this.srNum = srNum;
    }

    public Long getAnmlHc() {
        return anmlHc;
    }

    public void setAnmlHc(Long anmlHc) {
        this.anmlHc = anmlHc;
    }

    public Long getEstVal() {
        return estVal;
    }

    public void setEstVal(Long estVal) {
        this.estVal = estVal;
    }

    public Long getAnmlKnd() {
        return anmlKnd;
    }

    public void setAnmlKnd(Long anmlKnd) {
        this.anmlKnd = anmlKnd;
    }

    public Long getAnmlTyp() {
        return anmlTyp;
    }

    public void setAnmlTyp(Long anmlTyp) {
        this.anmlTyp = anmlTyp;
    }

    public String getAnmlBrd() {
        return anmlBrd;
    }

    public void setAnmlBrd(String anmlBrd) {
        this.anmlBrd = anmlBrd;
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

}
