package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwBrnch.
 */
@Entity
@Table(name = "mw_brnch")
public class MwBrnch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "brnch_seq")
    private Long brnchSeq;

    @Column(name = "brnch_cd")
    private String brnchCd;

    @Column(name = "brnch_nm")
    private String brnchNm;

    // @Column(name = "brnch_cmnt")
    // private String brnchCmnt;

    @Column(name = "area_seq")
    private Long areaSeq;

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

    // Added By Naveed - Dated 24-11-2021
    // MFCIB Region wise Distribution
    @Column(name = "MFCIB_CMPNY_SEQ")
    private Long mfcibCmpnySeq;
    // ended By Naveed

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public MwBrnch brnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
        return this;
    }

    public String getBrnchCd() {
        return brnchCd;
    }

    public void setBrnchCd(String brnchCd) {
        this.brnchCd = brnchCd;
    }

    public MwBrnch brnchCd(String brnchCd) {
        this.brnchCd = brnchCd;
        return this;
    }

    public String getBrnchNm() {
        return brnchNm;
    }

    public void setBrnchNm(String brnchNm) {
        this.brnchNm = brnchNm;
    }

    public MwBrnch brnchNm(String brnchNm) {
        this.brnchNm = brnchNm;
        return this;
    }

    // public String getBrnchCmnt() {
    // return brnchCmnt;
    // }
    //
    // public MwBrnch brnchCmnt( String brnchCmnt ) {
    // this.brnchCmnt = brnchCmnt;
    // return this;
    // }
    //
    // public void setBrnchCmnt( String brnchCmnt ) {
    // this.brnchCmnt = brnchCmnt;
    // }

    public Long getAreaSeq() {
        return areaSeq;
    }

    public void setAreaSeq(Long areaSeq) {
        this.areaSeq = areaSeq;
    }

    public MwBrnch areaSeq(Long areaSeq) {
        this.areaSeq = areaSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwBrnch crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwBrnch crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwBrnch lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwBrnch lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwBrnch delFlg(Boolean delFlg) {
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

    public MwBrnch effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwBrnch effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwBrnch crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Long getMfcibCmpnySeq() {
        return mfcibCmpnySeq;
    }

    public void setMfcibCmpnySeq(Long mfcibCmpnySeq) {
        this.mfcibCmpnySeq = mfcibCmpnySeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwBrnch mwBrnch = (MwBrnch) o;
        if (mwBrnch.getBrnchSeq() == null || getBrnchSeq() == null) {
            return false;
        }
        return Objects.equals(getBrnchSeq(), mwBrnch.getBrnchSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBrnchSeq());
    }

    @Override
    public String toString() {
        return "MwBrnch{" + "id=" + getBrnchSeq() + ", brnchSeq=" + getBrnchSeq() + ", brnchCd='" + getBrnchCd() + "'" + ", brnchNm='"
                + getBrnchNm() + "'" + ", areaSeq=" + getAreaSeq() + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'"
                + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'"
                + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'"
                + "}";
    }
}
