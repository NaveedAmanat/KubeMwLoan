package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwClntPermAddr.
 */
@Entity
@Table(name = "mw_clnt_perm_addr")
// @IdClass(MwClntPermAddrId.class)
public class MwClntPermAddr implements Serializable {

    private static final long serialVersionUID = 1L;

    // @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    // @SequenceGenerator(name = "sequenceGenerator")
    // private Long id;

    @Id
    @Column(name = "clnt_perm_addr_seq")
    private Long clntPermAddrSeq;

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "clnt_seq")
    private Long clntSeq;

    @Column(name = "perm_addr_str")
    private String permAddrStr;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    // public Long getId() {
    // return id;
    // }
    //
    // public void setId(Long id) {
    // this.id = id;
    // }

    public Long getClntPermAddrSeq() {
        return clntPermAddrSeq;
    }

    public void setClntPermAddrSeq(Long clntPermAddrSeq) {
        this.clntPermAddrSeq = clntPermAddrSeq;
    }

    public MwClntPermAddr clntPermAddrSeq(Long clntPermAddrSeq) {
        this.clntPermAddrSeq = clntPermAddrSeq;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwClntPermAddr effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public MwClntPermAddr clntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
        return this;
    }

    public String getPermAddrStr() {
        return permAddrStr;
    }

    public void setPermAddrStr(String permAddrStr) {
        this.permAddrStr = permAddrStr;
    }

    public MwClntPermAddr permAddrStr(String permAddrStr) {
        this.permAddrStr = permAddrStr;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwClntPermAddr crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwClntPermAddr crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwClntPermAddr lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwClntPermAddr lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwClntPermAddr delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwClntPermAddr effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwClntPermAddr crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    // @Override
    // public boolean equals(Object o) {
    // if (this == o) {
    // return true;
    // }
    // if (o == null || getClass() != o.getClass()) {
    // return false;
    // }
    // MwClntPermAddr mwClntPermAddr = (MwClntPermAddr) o;
    // if (mwClntPermAddr.getId() == null || getId() == null) {
    // return false;
    // }
    // return Objects.equals(getId(), mwClntPermAddr.getId());
    // }
    //
    // @Override
    // public int hashCode() {
    // return Objects.hashCode(getId());
    // }

    @Override
    public String toString() {
        return "MwClntPermAddr{" +
                // "id=" + getId() +
                ", clntPermAddrSeq=" + getClntPermAddrSeq() + ", effStartDt='" + getEffStartDt() + "'" + ", clntSeq=" + getClntSeq()
                + ", permAddrStr='" + getPermAddrStr() + "'" + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'"
                + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'"
                + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'" + "}";
    }
}
