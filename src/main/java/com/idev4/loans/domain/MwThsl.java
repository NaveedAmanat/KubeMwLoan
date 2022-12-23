package com.idev4.loans.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwThsl.
 */
@Entity
@Table(name = "mw_thsl")
public class MwThsl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "thsl_seq")
    private Long thslSeq;

    @Column(name = "thsl_cd")
    private String thslCd;

    @Column(name = "thsl_nm")
    private String thslNm;

    @Column(name = "thsl_cmnt")
    private String thslCmnt;

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
    private MwDist mwDist;

    public Long getThslSeq() {
        return thslSeq;
    }

    public void setThslSeq(Long thslSeq) {
        this.thslSeq = thslSeq;
    }

    public MwThsl thslSeq(Long thslSeq) {
        this.thslSeq = thslSeq;
        return this;
    }

    public String getThslCd() {
        return thslCd;
    }

    public void setThslCd(String thslCd) {
        this.thslCd = thslCd;
    }

    public MwThsl thslCd(String thslCd) {
        this.thslCd = thslCd;
        return this;
    }

    public String getThslNm() {
        return thslNm;
    }

    public void setThslNm(String thslNm) {
        this.thslNm = thslNm;
    }

    public MwThsl thslNm(String thslNm) {
        this.thslNm = thslNm;
        return this;
    }

    public String getThslCmnt() {
        return thslCmnt;
    }

    public void setThslCmnt(String thslCmnt) {
        this.thslCmnt = thslCmnt;
    }

    public MwThsl thslCmnt(String thslCmnt) {
        this.thslCmnt = thslCmnt;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwThsl crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwThsl crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwThsl lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwThsl lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwThsl delFlg(Boolean delFlg) {
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

    public MwThsl effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwThsl effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwThsl crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public MwDist getMwDist() {
        return mwDist;
    }

    public void setMwDist(MwDist mwDist) {
        this.mwDist = mwDist;
    }

    public MwThsl mwDist(MwDist mwDist) {
        this.mwDist = mwDist;
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
        MwThsl mwThsl = (MwThsl) o;
        if (mwThsl.getThslSeq() == null || getThslSeq() == null) {
            return false;
        }
        return Objects.equals(getThslSeq(), mwThsl.getThslSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getThslSeq());
    }

    @Override
    public String toString() {
        return "MwThsl{" +
                "id=" + getThslSeq() +
                ", thslSeq=" + getThslSeq() +
                ", thslCd='" + getThslCd() + "'" +
                ", thslNm='" + getThslNm() + "'" +
                ", thslCmnt='" + getThslCmnt() + "'" +
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
