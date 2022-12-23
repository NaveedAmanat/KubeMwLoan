package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwAddr.
 */
@Entity
@Table(name = "mw_addr")
//@IdClass(MwAddrId.class)
public class MwAddr implements Serializable {

    //(Addr_rel_SEQ, ADDR_SEQ, ENTY_KEY);
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "addr_seq")
    private Long addrSeq;

    @Column(name = "hse_num")
    private String hseNum;

    @Column(name = "strt")
    private String strt;

    @Column(name = "oth_dtl")
    private String othDtl;

    @Column(name = "addr_typ_key")
    private Long addrTypKey;

    @Column(name = "vlg")
    private String vlg;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

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

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "cmnty_seq")
    private Long cmntySeq;

    @Column(name = "city_seq")
    private Long citySeq;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    // @ManyToOne
    // private MwCity mwCity;

    // @ManyToOne
    // private MwCmnty mwCmnty;    

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getAddrSeq() {
        return addrSeq;
    }

    public void setAddrSeq(Long addrSeq) {
        this.addrSeq = addrSeq;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public MwAddr addrSeq(Long addrSeq) {
        this.addrSeq = addrSeq;
        return this;
    }

    public String getHseNum() {
        return hseNum;
    }

    public void setHseNum(String hseNum) {
        this.hseNum = hseNum;
    }

    public MwAddr hseNum(String hseNum) {
        this.hseNum = hseNum;
        return this;
    }

    public String getStrt() {
        return strt;
    }

    public void setStrt(String strt) {
        this.strt = strt;
    }

    public MwAddr strt(String strt) {
        this.strt = strt;
        return this;
    }

    public String getOthDtl() {
        return othDtl;
    }

    public void setOthDtl(String othDtl) {
        this.othDtl = othDtl;
    }

    public MwAddr othDtl(String othDtl) {
        this.othDtl = othDtl;
        return this;
    }

    public Long getAddrTypKey() {
        return addrTypKey;
    }

    public void setAddrTypKey(Long addrTypKey) {
        this.addrTypKey = addrTypKey;
    }

    public MwAddr addrTypKey(Long addrTypKey) {
        this.addrTypKey = addrTypKey;
        return this;
    }

    public String getVlg() {
        return vlg;
    }

    public void setVlg(String vlg) {
        this.vlg = vlg;
    }

    public MwAddr vlg(String vlg) {
        this.vlg = vlg;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public MwAddr longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public MwAddr latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwAddr crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwAddr crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwAddr lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwAddr lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwAddr delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwAddr effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwAddr effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwAddr crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public Long getCitySeq() {
        return citySeq;
    }

    public void setCitySeq(Long citySeq) {
        this.citySeq = citySeq;
    }

    public Long getCmntySeq() {
        return cmntySeq;
    }

    public void setCmntySeq(Long cmntySeq) {
        this.cmntySeq = cmntySeq;
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
        MwAddr mwAddr = (MwAddr) o;
        if (mwAddr.getAddrSeq() == null || getAddrSeq() == null) {
            return false;
        }
        return Objects.equals(getAddrSeq(), mwAddr.getAddrSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAddrSeq());
    }

    @Override
    public String toString() {
        return "MwAddr{" + "id=" + getAddrSeq() + ", addrSeq=" + getAddrSeq() + ", hseNum='" + getHseNum() + "'" + ", strt='" + getStrt()
                + "'" + ", othDtl='" + getOthDtl() + "'" + ", addrTypKey=" + getAddrTypKey() + ", vlg='" + getVlg() + "'" + ", longitude="
                + getLongitude() + ", latitude=" + getLatitude() + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'"
                + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'"
                + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'"
                + "}";
    }
}
