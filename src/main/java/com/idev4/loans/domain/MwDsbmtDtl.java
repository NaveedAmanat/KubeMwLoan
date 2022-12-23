package com.idev4.loans.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwDsbmtDtl.
 */
@Entity
@Table(name = "mw_dsbmt_dtl")
public class MwDsbmtDtl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dsbmt_dtl_seq")
    private Long dsbmtDtlSeq;

    @Column(name = "instr_num")
    private String instrNum;

    @Column(name = "amt")
    private Long amt;

    @Column(name = "pymt_mod_key")
    private Long pymtModKey;

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
    private MwDsbmtHdr mwDsbmtHdr;

    public Long getDsbmtDtlSeq() {
        return dsbmtDtlSeq;
    }

    public void setDsbmtDtlSeq(Long dsbmtDtlSeq) {
        this.dsbmtDtlSeq = dsbmtDtlSeq;
    }

    public MwDsbmtDtl dsbmtDtlSeq(Long dsbmtDtlSeq) {
        this.dsbmtDtlSeq = dsbmtDtlSeq;
        return this;
    }

    public String getInstrNum() {
        return instrNum;
    }

    public void setInstrNum(String instrNum) {
        this.instrNum = instrNum;
    }

    public MwDsbmtDtl instrNum(String instrNum) {
        this.instrNum = instrNum;
        return this;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public MwDsbmtDtl amt(Long amt) {
        this.amt = amt;
        return this;
    }

    public Long getPymtModKey() {
        return pymtModKey;
    }

    public void setPymtModKey(Long pymtModKey) {
        this.pymtModKey = pymtModKey;
    }

    public MwDsbmtDtl pymtModKey(Long pymtModKey) {
        this.pymtModKey = pymtModKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwDsbmtDtl crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwDsbmtDtl crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwDsbmtDtl lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwDsbmtDtl lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwDsbmtDtl delFlg(Boolean delFlg) {
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

    public MwDsbmtDtl effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwDsbmtDtl effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwDsbmtDtl crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public MwDsbmtHdr getMwDsbmtHdr() {
        return mwDsbmtHdr;
    }

    public void setMwDsbmtHdr(MwDsbmtHdr mwDsbmtHdr) {
        this.mwDsbmtHdr = mwDsbmtHdr;
    }

    public MwDsbmtDtl mwDsbmtHdr(MwDsbmtHdr mwDsbmtHdr) {
        this.mwDsbmtHdr = mwDsbmtHdr;
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
        MwDsbmtDtl mwDsbmtDtl = (MwDsbmtDtl) o;
        if (mwDsbmtDtl.getDsbmtDtlSeq() == null || getDsbmtDtlSeq() == null) {
            return false;
        }
        return Objects.equals(getDsbmtDtlSeq(), mwDsbmtDtl.getDsbmtDtlSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDsbmtDtlSeq());
    }

    @Override
    public String toString() {
        return "MwDsbmtDtl{" +
                "id=" + getDsbmtDtlSeq() +
                ", dsbmtDtlSeq=" + getDsbmtDtlSeq() +
                ", instrNum='" + getInstrNum() + "'" +
                ", amt=" + getAmt() +
                ", pymtModKey=" + getPymtModKey() +
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
