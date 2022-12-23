package com.idev4.loans.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwDsbmtHdr.
 */
@Entity
@Table(name = "mw_dsbmt_hdr")
public class MwDsbmtHdr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dsbmt_hdr_seq")
    private Long dsbmtHdrSeq;

    @Column(name = "dsbmt_hdr_id")
    private String dsbmtHdrId;

    @Column(name = "dsbmt_hdr_dt")
    private Instant dsbmtHdrDt;

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

    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @ManyToOne
    private MwLoanApp mwLoanApp;

    public Long getDsbmtHdrSeq() {
        return dsbmtHdrSeq;
    }

    public void setDsbmtHdrSeq(Long dsbmtHdrSeq) {
        this.dsbmtHdrSeq = dsbmtHdrSeq;
    }

    public MwDsbmtHdr dsbmtHdrSeq(Long dsbmtHdrSeq) {
        this.dsbmtHdrSeq = dsbmtHdrSeq;
        return this;
    }

    public String getDsbmtHdrId() {
        return dsbmtHdrId;
    }

    public void setDsbmtHdrId(String dsbmtHdrId) {
        this.dsbmtHdrId = dsbmtHdrId;
    }

    public MwDsbmtHdr dsbmtHdrId(String dsbmtHdrId) {
        this.dsbmtHdrId = dsbmtHdrId;
        return this;
    }

    public Instant getDsbmtHdrDt() {
        return dsbmtHdrDt;
    }

    public void setDsbmtHdrDt(Instant dsbmtHdrDt) {
        this.dsbmtHdrDt = dsbmtHdrDt;
    }

    public MwDsbmtHdr dsbmtHdrDt(Instant dsbmtHdrDt) {
        this.dsbmtHdrDt = dsbmtHdrDt;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwDsbmtHdr crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwDsbmtHdr crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwDsbmtHdr lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwDsbmtHdr lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwDsbmtHdr delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
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

    public MwDsbmtHdr effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwDsbmtHdr effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwDsbmtHdr crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public MwLoanApp getMwLoanApp() {
        return mwLoanApp;
    }

    public void setMwLoanApp(MwLoanApp mwLoanApp) {
        this.mwLoanApp = mwLoanApp;
    }

    public MwDsbmtHdr mwLoanApp(MwLoanApp mwLoanApp) {
        this.mwLoanApp = mwLoanApp;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwDsbmtHdr mwDsbmtHdr = (MwDsbmtHdr) o;
        if (mwDsbmtHdr.getDsbmtHdrSeq() == null || getDsbmtHdrSeq() == null) {
            return false;
        }
        return Objects.equals(getDsbmtHdrSeq(), mwDsbmtHdr.getDsbmtHdrSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDsbmtHdrSeq());
    }

    @Override
    public String toString() {
        return "MwDsbmtHdr{" +
                "id=" + getDsbmtHdrSeq() +
                ", dsbmtHdrSeq=" + getDsbmtHdrSeq() +
                ", dsbmtHdrId='" + getDsbmtHdrId() + "'" +
                ", dsbmtHdrDt='" + getDsbmtHdrDt() + "'" +
                ", crtdBy='" + getCrtdBy() + "'" +
                ", crtdDt='" + getCrtdDt() + "'" +
                ", lastUpdBy='" + getLastUpdBy() + "'" +
                ", lastUpdDt='" + getLastUpdDt() + "'" +
                ", delFlg='" + isDelFlg() + "'" +
                ", effStartDt='" + getEffStartDt() + "'" +
                ", effEndDt='" + getEffEndDt() + "'" +
                ", crntRecFlg='" + isCrntRecFlg() + "'" +
                "}";
    }
}
