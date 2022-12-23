package com.idev4.loans.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwCmnty.
 */
@Entity
@Table(name = "mw_cmnty")
public class MwCmnty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cmnty_seq")
    private Long cmntySeq;

    @Column(name = "cmnty_nm")
    private String cmntyNm;

    @Column(name = "cmnty_cmnt")
    private String cmntyCmnt;

    @Column(name = "protfolio_seq")
    private Long protfolioSeq;

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

    public Long getCmntySeq() {
        return cmntySeq;
    }

    public void setCmntySeq(Long cmntySeq) {
        this.cmntySeq = cmntySeq;
    }

    public MwCmnty cmntySeq(Long cmntySeq) {
        this.cmntySeq = cmntySeq;
        return this;
    }

    public String getCmntyNm() {
        return cmntyNm;
    }

    public void setCmntyNm(String cmntyNm) {
        this.cmntyNm = cmntyNm;
    }

    public MwCmnty cmntyNm(String cmntyNm) {
        this.cmntyNm = cmntyNm;
        return this;
    }

    public String getCmntyCmnt() {
        return cmntyCmnt;
    }

    public void setCmntyCmnt(String cmntyCmnt) {
        this.cmntyCmnt = cmntyCmnt;
    }

    public MwCmnty cmntyCmnt(String cmntyCmnt) {
        this.cmntyCmnt = cmntyCmnt;
        return this;
    }

    public Long getProtfolioSeq() {
        return protfolioSeq;
    }

    public void setProtfolioSeq(Long protfolioSeq) {
        this.protfolioSeq = protfolioSeq;
    }

    public MwCmnty protfolioSeq(Long protfolioSeq) {
        this.protfolioSeq = protfolioSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwCmnty crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwCmnty crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwCmnty lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwCmnty lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwCmnty delFlg(Boolean delFlg) {
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

    public MwCmnty effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwCmnty effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwCmnty crntRecFlg(Boolean crntRecFlg) {
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
        MwCmnty mwCmnty = (MwCmnty) o;
        if (mwCmnty.getCmntySeq() == null || getCmntySeq() == null) {
            return false;
        }
        return Objects.equals(getCmntySeq(), mwCmnty.getCmntySeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCmntySeq());
    }

    @Override
    public String toString() {
        return "MwCmnty{" +
                "id=" + getCmntySeq() +
                ", cmntySeq=" + getCmntySeq() +
                ", cmntyNm='" + getCmntyNm() + "'" +
                ", cmntyCmnt='" + getCmntyCmnt() + "'" +
                ", protfolioSeq=" + getProtfolioSeq() +
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
