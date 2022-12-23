package com.idev4.loans.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwCity.
 */
@Entity
@Table(name = "mw_city")
public class MwCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "city_seq")
    private Long citySeq;

    @Column(name = "city_cd")
    private String cityCd;

    @Column(name = "city_nm")
    private String cityNm;

    @Column(name = "city_cmnt")
    private String cityCmnt;

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
    private MwUc mwUc;

    public Long getCitySeq() {
        return citySeq;
    }

    public void setCitySeq(Long citySeq) {
        this.citySeq = citySeq;
    }

    public MwCity citySeq(Long citySeq) {
        this.citySeq = citySeq;
        return this;
    }

    public String getCityCd() {
        return cityCd;
    }

    public void setCityCd(String cityCd) {
        this.cityCd = cityCd;
    }

    public MwCity cityCd(String cityCd) {
        this.cityCd = cityCd;
        return this;
    }

    public String getCityNm() {
        return cityNm;
    }

    public void setCityNm(String cityNm) {
        this.cityNm = cityNm;
    }

    public MwCity cityNm(String cityNm) {
        this.cityNm = cityNm;
        return this;
    }

    public String getCityCmnt() {
        return cityCmnt;
    }

    public void setCityCmnt(String cityCmnt) {
        this.cityCmnt = cityCmnt;
    }

    public MwCity cityCmnt(String cityCmnt) {
        this.cityCmnt = cityCmnt;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwCity crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwCity crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwCity lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwCity lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwCity delFlg(Boolean delFlg) {
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

    public MwCity effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwCity effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwCity crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public MwUc getMwUc() {
        return mwUc;
    }

    public void setMwUc(MwUc mwUc) {
        this.mwUc = mwUc;
    }

    public MwCity mwUc(MwUc mwUc) {
        this.mwUc = mwUc;
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
        MwCity mwCity = (MwCity) o;
        if (mwCity.getCitySeq() == null || getCitySeq() == null) {
            return false;
        }
        return Objects.equals(getCitySeq(), mwCity.getCitySeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCitySeq());
    }

    @Override
    public String toString() {
        return "MwCity{" +
                "id=" + getCitySeq() +
                ", citySeq=" + getCitySeq() +
                ", cityCd='" + getCityCd() + "'" +
                ", cityNm='" + getCityNm() + "'" +
                ", cityCmnt='" + getCityCmnt() + "'" +
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
