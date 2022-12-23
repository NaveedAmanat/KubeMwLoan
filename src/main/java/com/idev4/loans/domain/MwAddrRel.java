package com.idev4.loans.domain;

import com.idev4.loans.ids.MwAddrRelId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwAddrRel.
 */
@Entity
@Table(name = "mw_addr_rel")
@IdClass(MwAddrRelId.class)
public class MwAddrRel implements Serializable {

    //(Addr_rel_SEQ, ADDR_SEQ, ENTY_KEY);
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "addr_rel_seq")
    private Long addrRelSeq;

    @Id
    @Column(name = "addr_seq")
    private Long addrSeq;

    @Id
    @Column(name = "enty_key")
    private Long entySeq;

    @Column(name = "enty_typ")
    private String entyType;

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

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getAddrRelSeq() {
        return addrRelSeq;
    }

    public void setAddrRelSeq(Long addrRelSeq) {
        this.addrRelSeq = addrRelSeq;
    }

    public MwAddrRel addrRelSeq(Long addrRelSeq) {
        this.addrRelSeq = addrRelSeq;
        return this;
    }

    public Long getAddrSeq() {
        return addrSeq;
    }

    public void setAddrSeq(Long addrSeq) {
        this.addrSeq = addrSeq;
    }

    public MwAddrRel addrSeq(Long addrSeq) {
        this.addrSeq = addrSeq;
        return this;
    }

    public Long getEntySeq() {
        return entySeq;
    }

    public void setEntySeq(Long entySeq) {
        this.entySeq = entySeq;
    }

    public MwAddrRel entySeq(Long entySeq) {
        this.entySeq = entySeq;
        return this;
    }

    public String getEntyType() {
        return entyType;
    }

    public void setEntyType(String entyType) {
        this.entyType = entyType;
    }

    public MwAddrRel entyType(String entyType) {
        this.entyType = entyType;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwAddrRel crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwAddrRel crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwAddrRel lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwAddrRel lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwAddrRel delFlg(Boolean delFlg) {
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

    public MwAddrRel effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwAddrRel effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwAddrRel crntRecFlg(Boolean crntRecFlg) {
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
        MwAddrRel mwAddrRel = (MwAddrRel) o;
        if (mwAddrRel.getAddrRelSeq() == null || getAddrRelSeq() == null) {
            return false;
        }
        return Objects.equals(getAddrRelSeq(), mwAddrRel.getAddrRelSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAddrRelSeq());
    }

    @Override
    public String toString() {
        return "MwAddrRel{" + "id=" + getAddrRelSeq() + ", addrRelSeq=" + getAddrRelSeq() + ", addrSeq=" + getAddrSeq() + ", entySeq="
                + getEntySeq() + ", entyType='" + getEntyType() + "'" + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'"
                + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'"
                + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'"
                + "}";
    }
}
