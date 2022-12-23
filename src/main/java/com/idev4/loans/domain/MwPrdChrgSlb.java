package com.idev4.loans.domain;

import com.idev4.loans.ids.MwPrdChrgSlbId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "MW_PRD_CHRG_SLB")
@IdClass(MwPrdChrgSlbId.class)
public class MwPrdChrgSlb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PRD_CHRG_SLB_SEQ")
    private Long prdChrgSlbSeq;

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "PRD_CHRG_SEQ")
    private Long prdChrgSeq;

    @Column(name = "START_LMT")
    private Double startLmt;

    @Column(name = "END_LMT")
    private Double endLmt;

    @Column(name = "VAL")
    private Double val;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

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

    public Long getPrdChrgSlbSeq() {
        return prdChrgSlbSeq;
    }

    public void setPrdChrgSlbSeq(Long prdChrgSlbSeq) {
        this.prdChrgSlbSeq = prdChrgSlbSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getPrdChrgSeq() {
        return prdChrgSeq;
    }

    public void setPrdChrgSeq(Long prdChrgSeq) {
        this.prdChrgSeq = prdChrgSeq;
    }

    public Double getStartLmt() {
        return startLmt;
    }

    public void setStartLmt(Double startLmt) {
        this.startLmt = startLmt;
    }

    public Double getEndLmt() {
        return endLmt;
    }

    public void setEndLmt(Double endLmt) {
        this.endLmt = endLmt;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
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

}
