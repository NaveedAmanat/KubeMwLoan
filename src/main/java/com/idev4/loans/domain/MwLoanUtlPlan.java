package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwLoanUtlPlan.
 */
@Entity
@Table(name = "mw_loan_utl_plan")
// @IdClass ( MwLoanUtlPlanId.class )
public class MwLoanUtlPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "loan_utl_plan_seq")
    private Long loanUtlPlanSeq;

    @Column(name = "loan_utl_dscr")
    private String loanUtlDscr;

    @Column(name = "loan_utl_amt")
    private Double loanUtlAmt;

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

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "loan_utl_typ")
    private Long loanUtlTyp;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    @Column(name = "TYP_FLG")
    private Integer typFlg;

    public Integer getTypFlg() {
        return typFlg;
    }

    public void setTypFlg(Integer typFlg) {
        this.typFlg = typFlg;
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

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getLoanUtlPlanSeq() {
        return loanUtlPlanSeq;
    }

    public void setLoanUtlPlanSeq(Long loanUtlPlanSeq) {
        this.loanUtlPlanSeq = loanUtlPlanSeq;
    }

    public MwLoanUtlPlan loanUtlPlanSeq(Long loanUtlPlanSeq) {
        this.loanUtlPlanSeq = loanUtlPlanSeq;
        return this;
    }

    public String getLoanUtlDscr() {
        return loanUtlDscr;
    }

    public void setLoanUtlDscr(String loanUtlDscr) {
        this.loanUtlDscr = loanUtlDscr;
    }

    public MwLoanUtlPlan loanUtlDscr(String loanUtlDscr) {
        this.loanUtlDscr = loanUtlDscr;
        return this;
    }

    public Double getLoanUtlAmt() {
        return loanUtlAmt;
    }

    public void setLoanUtlAmt(Double loanUtlAmt) {
        this.loanUtlAmt = loanUtlAmt;
    }

    public MwLoanUtlPlan loanUtlAmt(Double loanUtlAmt) {
        this.loanUtlAmt = loanUtlAmt;
        return this;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public MwLoanUtlPlan loanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwLoanUtlPlan crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwLoanUtlPlan crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwLoanUtlPlan lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwLoanUtlPlan lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwLoanUtlPlan delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwLoanUtlPlan effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwLoanUtlPlan effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwLoanUtlPlan crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public Long getLoanUtlTyp() {
        return loanUtlTyp;
    }

    public void setLoanUtlTyp(Long loanUtlTyp) {
        this.loanUtlTyp = loanUtlTyp;
    }

    public MwLoanUtlPlan loanUtlTyp(Long loanUtlTyp) {
        this.loanUtlTyp = loanUtlTyp;
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
        MwLoanUtlPlan mwLoanUtlPlan = (MwLoanUtlPlan) o;
        if (mwLoanUtlPlan.getLoanUtlPlanSeq() == null || getLoanUtlPlanSeq() == null) {
            return false;
        }
        return Objects.equals(getLoanUtlPlanSeq(), mwLoanUtlPlan.getLoanUtlPlanSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLoanUtlPlanSeq());
    }

    @Override
    public String toString() {
        return "MwLoanUtlPlan{" + "id=" + getLoanUtlPlanSeq() + ", loanUtlPlanSeq=" + getLoanUtlPlanSeq() + ", loanUtlDscr='"
                + getLoanUtlDscr() + "'" + ", loanUtlAmt=" + getLoanUtlAmt() + ", loanAppSeq=" + getLoanAppSeq() + ", crtdBy='"
                + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'" + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='"
                + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'" + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='"
                + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'" + ", loanUtlTyp=" + getLoanUtlTyp() + "}";
    }
}
