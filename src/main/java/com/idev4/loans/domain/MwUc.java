package com.idev4.loans.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwUc.
 */
@Entity
@Table(name = "mw_uc")
public class MwUc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "uc_seq")
    private Long ucSeq;

    @Column(name = "uc_cd")
    private String ucCd;

    @Column(name = "uc_nm")
    private String ucNm;

    @Column(name = "uc_cmnt")
    private String ucCmnt;

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
    private MwThsl mwThsl;

    public Long getUcSeq() {
        return ucSeq;
    }

    public void setUcSeq(Long ucSeq) {
        this.ucSeq = ucSeq;
    }

    public MwUc ucSeq(Long ucSeq) {
        this.ucSeq = ucSeq;
        return this;
    }

    public String getUcCd() {
        return ucCd;
    }

    public void setUcCd(String ucCd) {
        this.ucCd = ucCd;
    }

    public MwUc ucCd(String ucCd) {
        this.ucCd = ucCd;
        return this;
    }

    public String getUcNm() {
        return ucNm;
    }

    public void setUcNm(String ucNm) {
        this.ucNm = ucNm;
    }

    public MwUc ucNm(String ucNm) {
        this.ucNm = ucNm;
        return this;
    }

    public String getUcCmnt() {
        return ucCmnt;
    }

    public void setUcCmnt(String ucCmnt) {
        this.ucCmnt = ucCmnt;
    }

    public MwUc ucCmnt(String ucCmnt) {
        this.ucCmnt = ucCmnt;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwUc crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwUc crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwUc lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwUc lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwUc delFlg(Boolean delFlg) {
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

    public MwUc effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwUc effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwUc crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public MwThsl getMwThsl() {
        return mwThsl;
    }

    public void setMwThsl(MwThsl mwThsl) {
        this.mwThsl = mwThsl;
    }

    public MwUc mwThsl(MwThsl mwThsl) {
        this.mwThsl = mwThsl;
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
        MwUc mwUc = (MwUc) o;
        if (mwUc.getUcSeq() == null || getUcSeq() == null) {
            return false;
        }
        return Objects.equals(getUcSeq(), mwUc.getUcSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUcSeq());
    }

    @Override
    public String toString() {
        return "MwUc{" +
                "id=" + getUcSeq() +
                ", ucSeq=" + getUcSeq() +
                ", ucCd='" + getUcCd() + "'" +
                ", ucNm='" + getUcNm() + "'" +
                ", ucCmnt='" + getUcCmnt() + "'" +
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
