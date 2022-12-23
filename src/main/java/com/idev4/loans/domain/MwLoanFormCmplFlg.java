package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwLoanFormCmplFlg.
 */
@Entity
@Table(name = "MW_loan_form_cmpl_flg")
// @IdClass(MwLoanFormComplFlgId.class)
public class MwLoanFormCmplFlg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "form_seq")
    private Long formSeq;

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

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    // @Column(name = "loan_app_id")
    // private String loanAppId;

    @Id
    @Column(name = "loan_form_cmpl_flg_seq")
    private Long loan_form_cmpl_flg_SEQ;

    @Column(name = "PLAN_STS_KEY")
    private Long planStsKey;

    public void setPlanStsKey(Long key) {
        this.planStsKey = key;
    }

    public Long getPlanStsKey(Long key) {
        return this.planStsKey;
    }

    public Long getFormSeq() {
        return formSeq;
    }

    public void setFormSeq(Long formSeq) {
        this.formSeq = formSeq;
    }

    public MwLoanFormCmplFlg formSeq(Long formSeq) {
        this.formSeq = formSeq;
        return this;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public MwLoanFormCmplFlg loanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwLoanFormCmplFlg crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwLoanFormCmplFlg crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwLoanFormCmplFlg lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwLoanFormCmplFlg lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwLoanFormCmplFlg delFlg(Boolean delFlg) {
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

    public MwLoanFormCmplFlg effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwLoanFormCmplFlg crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    // public String getLoanAppId() {
    // return loanAppId;
    // }
    //
    // public MwLoanFormCmplFlg loanAppId(String loanAppId) {
    // this.loanAppId = loanAppId;
    // return this;
    // }
    //
    // public void setLoanAppId(String loanAppId) {
    // this.loanAppId = loanAppId;
    // }

    public Long getLoan_form_cmpl_flg_SEQ() {
        return loan_form_cmpl_flg_SEQ;
    }

    public void setLoan_form_cmpl_flg_SEQ(Long loan_form_cmpl_flg_SEQ) {
        this.loan_form_cmpl_flg_SEQ = loan_form_cmpl_flg_SEQ;
    }

    public MwLoanFormCmplFlg loan_form_cmpl_flg_SEQ(Long loan_form_cmpl_flg_SEQ) {
        this.loan_form_cmpl_flg_SEQ = loan_form_cmpl_flg_SEQ;
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
        MwLoanFormCmplFlg mwLoanFormCmplFlg = (MwLoanFormCmplFlg) o;
        if (mwLoanFormCmplFlg.getFormSeq() == null || getFormSeq() == null) {
            return false;
        }
        return Objects.equals(getFormSeq(), mwLoanFormCmplFlg.getFormSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFormSeq());
    }

    @Override
    public String toString() {
        return "MwLoanFormCmplFlg{" + "id=" + getFormSeq() + ", formSeq=" + getFormSeq() + ", loanAppSeq=" + getLoanAppSeq() + ", crtdBy='"
                + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'" + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='"
                + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'" + ", effStartDt='" + getEffStartDt() + "'" + ", crntRecFlg='"
                + isCrntRecFlg() + "'" +
                // ", loanAppId='" + getLoanAppId() + "'" +
                ", loan_form_cmpl_flg_SEQ=" + getLoan_form_cmpl_flg_SEQ() + "}";
    }
}
