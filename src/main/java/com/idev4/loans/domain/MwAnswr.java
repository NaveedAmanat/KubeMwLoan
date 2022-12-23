package com.idev4.loans.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwAnswr.
 */
@Entity
@Table(name = "mw_answr")
public class MwAnswr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "answr_seq")
    private Long answrSeq;

    @Column(name = "answr_id")
    private String answrId;

    @Column(name = "answr_str")
    private String answrStr;

    @Column(name = "answr_sts_key")
    private Long answrStsKey;

    @Column(name = "answr_score")
    private Long answrScore;

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

    @Column(name = "qst_seq")
    private Long qstSeq;

    public Long getAnswrSeq() {
        return answrSeq;
    }

    public void setAnswrSeq(Long answrSeq) {
        this.answrSeq = answrSeq;
    }

    public MwAnswr answrSeq(Long answrSeq) {
        this.answrSeq = answrSeq;
        return this;
    }

    public String getAnswrId() {
        return answrId;
    }

    public void setAnswrId(String answrId) {
        this.answrId = answrId;
    }

    public MwAnswr answrId(String answrId) {
        this.answrId = answrId;
        return this;
    }

    public String getAnswrStr() {
        return answrStr;
    }

    public void setAnswrStr(String answrStr) {
        this.answrStr = answrStr;
    }

    public MwAnswr answrStr(String answrStr) {
        this.answrStr = answrStr;
        return this;
    }

    public Long getAnswrStsKey() {
        return answrStsKey;
    }

    public void setAnswrStsKey(Long answrStsKey) {
        this.answrStsKey = answrStsKey;
    }

    public MwAnswr answrStsKey(Long answrStsKey) {
        this.answrStsKey = answrStsKey;
        return this;
    }

    public Long getAnswrScore() {
        return answrScore;
    }

    public void setAnswrScore(Long answrScore) {
        this.answrScore = answrScore;
    }

    public MwAnswr answrScore(Long answrScore) {
        this.answrScore = answrScore;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwAnswr crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwAnswr crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwAnswr lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwAnswr lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwAnswr delFlg(Boolean delFlg) {
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

    public MwAnswr effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwAnswr effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwAnswr crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(Long qstSeq) {
        this.qstSeq = qstSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwAnswr mwAnswr = (MwAnswr) o;
        if (mwAnswr.getAnswrSeq() == null || getAnswrSeq() == null) {
            return false;
        }
        return Objects.equals(getAnswrSeq(), mwAnswr.getAnswrSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAnswrSeq());
    }

    @Override
    public String toString() {
        return "MwAnswr{" +
                "id=" + getAnswrSeq() +
                ", answrSeq=" + getAnswrSeq() +
                ", answrId='" + getAnswrId() + "'" +
                ", answrStr='" + getAnswrStr() + "'" +
                ", answrStsKey=" + getAnswrStsKey() +
                ", answrScore=" + getAnswrScore() +
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
