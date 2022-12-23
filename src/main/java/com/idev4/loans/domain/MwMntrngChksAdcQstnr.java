package com.idev4.loans.domain;

import com.idev4.loans.ids.MwMntrngChksAdcQstnrId;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;

/**
 * A MwAddr.
 */
@Entity
@Table(name = "MW_MNTRNG_CHKS_ADC_QSTNR")
@IdClass(MwMntrngChksAdcQstnrId.class)
public class MwMntrngChksAdcQstnr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MNTRNG_CHKS_ADC_QSTNR_SEQ")
    private BigInteger mntrngChksAdcQstnrSeq;

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Id
    @Column(name = "MNTRNG_CHKS_SEQ")
    private BigInteger mntrngChksSeq;

    @Column(name = "QST_SEQ")
    private Long qstSeq;

    @Column(name = "ANSWR_SEQ")
    private Long answrSeq;

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

    @Column(name = "ANSWR_VAL")
    private String answrVal;

    public String getAnswrVal() {
        return answrVal;
    }

    public void setAnswrVal(String answrVal) {
        this.answrVal = answrVal;
    }

    public BigInteger getMntrngChksAdcQstnrSeq() {
        return mntrngChksAdcQstnrSeq;
    }

    public void setMntrngChksAdcQstnrSeq(BigInteger mntrngChksAdcQstnrSeq) {
        this.mntrngChksAdcQstnrSeq = mntrngChksAdcQstnrSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public BigInteger getMntrngChksSeq() {
        return mntrngChksSeq;
    }

    public void setMntrngChksSeq(BigInteger mntrngChksSeq) {
        this.mntrngChksSeq = mntrngChksSeq;
    }

    public Long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(Long qstSeq) {
        this.qstSeq = qstSeq;
    }

    public Long getAnswrSeq() {
        return answrSeq;
    }

    public void setAnswrSeq(Long answrSeq) {
        this.answrSeq = answrSeq;
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
