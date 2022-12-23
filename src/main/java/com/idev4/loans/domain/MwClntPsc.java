package com.idev4.loans.domain;

import com.idev4.loans.ids.MwClntPscId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwClntPsc.
 */
@Entity
@Table(name = "mw_clnt_psc")
@IdClass(MwClntPscId.class)
public class MwClntPsc implements Serializable {

    //(PSC_SEQ, qst_seq, ANSWR_SEQ, loan_app_Seq);
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "psc_seq")
    private Long pscSeq;

    @Id
    @Column(name = "qst_seq")
    private Long qstSeq;

    @Id
    @Column(name = "answr_seq")
    private Long answrSeq;

    @Id
    @Column(name = "loan_app_seq")
    private Long loanAppSeq;

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

    public Long getPscSeq() {
        return pscSeq;
    }

    public void setPscSeq(Long pscSeq) {
        this.pscSeq = pscSeq;
    }

    public MwClntPsc pscSeq(Long pscSeq) {
        this.pscSeq = pscSeq;
        return this;
    }

    public Long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(Long qstSeq) {
        this.qstSeq = qstSeq;
    }

    public MwClntPsc qstSeq(Long qstSeq) {
        this.qstSeq = qstSeq;
        return this;
    }

    public Long getAnswrSeq() {
        return answrSeq;
    }

    public void setAnswrSeq(Long answrSeq) {
        this.answrSeq = answrSeq;
    }

    public MwClntPsc answrSeq(Long answrSeq) {
        this.answrSeq = answrSeq;
        return this;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public MwClntPsc loanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwClntPsc crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwClntPsc crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwClntPsc lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwClntPsc lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwClntPsc delFlg(Boolean delFlg) {
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

    public MwClntPsc effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwClntPsc effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwClntPsc crntRecFlg(Boolean crntRecFlg) {
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
        MwClntPsc mwClntPsc = (MwClntPsc) o;
        if (mwClntPsc.getPscSeq() == null || getPscSeq() == null) {
            return false;
        }
        return Objects.equals(getPscSeq(), mwClntPsc.getPscSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPscSeq());
    }

    @Override
    public String toString() {
        return "MwClntPsc{" + "id=" + getPscSeq() + ", pscSeq=" + getPscSeq() + ", qstSeq=" + getQstSeq() + ", answrSeq=" + getAnswrSeq()
                + ", loanAppSeq=" + getLoanAppSeq() + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'" + ", lastUpdBy='"
                + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'" + ", effStartDt='"
                + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'" + "}";
    }
}
