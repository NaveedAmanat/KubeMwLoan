package com.idev4.loans.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwDist.
 */
@Entity
@Table(name = "mw_dist")
public class MwDist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dist_seq")
    private Long distSeq;

    @Column(name = "dist_cd")
    private String distCd;

    @Column(name = "dist_nm")
    private String distNm;

    @Column(name = "dist_cmnt")
    private String distCmnt;

    @Column(name = "st_seq")
    private Long stSeq;

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


    public Long getDistSeq() {
        return distSeq;
    }

    public void setDistSeq(Long distSeq) {
        this.distSeq = distSeq;
    }

    public MwDist distSeq(Long distSeq) {
        this.distSeq = distSeq;
        return this;
    }

    public String getDistCd() {
        return distCd;
    }

    public void setDistCd(String distCd) {
        this.distCd = distCd;
    }

    public MwDist distCd(String distCd) {
        this.distCd = distCd;
        return this;
    }

    public String getDistNm() {
        return distNm;
    }

    public void setDistNm(String distNm) {
        this.distNm = distNm;
    }

    public MwDist distNm(String distNm) {
        this.distNm = distNm;
        return this;
    }

    public String getDistCmnt() {
        return distCmnt;
    }

    public void setDistCmnt(String distCmnt) {
        this.distCmnt = distCmnt;
    }

    public MwDist distCmnt(String distCmnt) {
        this.distCmnt = distCmnt;
        return this;
    }

    public Long getStSeq() {
        return stSeq;
    }

    public void setStSeq(Long stSeq) {
        this.stSeq = stSeq;
    }

    public MwDist stSeq(Long stSeq) {
        this.stSeq = stSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwDist crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwDist crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwDist lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwDist lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwDist delFlg(Boolean delFlg) {
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

    public MwDist effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwDist effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwDist crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
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
        MwDist mwDist = (MwDist) o;
        if (mwDist.getDistSeq() == null || getDistSeq() == null) {
            return false;
        }
        return Objects.equals(getDistSeq(), mwDist.getDistSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDistSeq());
    }

    @Override
    public String toString() {
        return "MwDist{" +
                ", distSeq=" + getDistSeq() +
                ", distCd='" + getDistCd() + "'" +
                ", distNm='" + getDistNm() + "'" +
                ", distCmnt='" + getDistCmnt() + "'" +
                ", stSeq=" + getStSeq() +
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
