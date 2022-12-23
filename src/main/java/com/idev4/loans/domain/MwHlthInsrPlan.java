package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwHlthInsrPlan.
 */
@Entity
@Table(name = "mw_hlth_insr_plan")
public class MwHlthInsrPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "hlth_insr_plan_seq")
    private Long hlthInsrPlanSeq;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "plan_nm")
    private String planNm;

    @Column(name = "anl_prem_amt")
    private Long anlPremAmt;

    @Column(name = "max_plcy_amt")
    private Long maxPlcyAmt;

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

    @Column(name = "HLTH_CARD_FLG")
    private Boolean hlthCardFlg;

    public Boolean getHlthCardFlg() {
        return hlthCardFlg;
    }

    public void setHlthCardFlg(Boolean hlthCardFlg) {
        this.hlthCardFlg = hlthCardFlg;
    }

    public Long getHlthInsrPlanSeq() {
        return hlthInsrPlanSeq;
    }

    public void setHlthInsrPlanSeq(Long hlthInsrPlanSeq) {
        this.hlthInsrPlanSeq = hlthInsrPlanSeq;
    }

    public MwHlthInsrPlan hlthInsrPlanSeq(Long hlthInsrPlanSeq) {
        this.hlthInsrPlanSeq = hlthInsrPlanSeq;
        return this;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public MwHlthInsrPlan planId(String planId) {
        this.planId = planId;
        return this;
    }

    public String getPlanNm() {
        return planNm;
    }

    public void setPlanNm(String planNm) {
        this.planNm = planNm;
    }

    public MwHlthInsrPlan planNm(String planNm) {
        this.planNm = planNm;
        return this;
    }

    public Long getAnlPremAmt() {
        return anlPremAmt;
    }

    public void setAnlPremAmt(Long anlPremAmt) {
        this.anlPremAmt = anlPremAmt;
    }

    public MwHlthInsrPlan anlPremAmt(Long anlPremAmt) {
        this.anlPremAmt = anlPremAmt;
        return this;
    }

    public Long getMaxPlcyAmt() {
        return maxPlcyAmt;
    }

    public void setMaxPlcyAmt(Long maxPlcyAmt) {
        this.maxPlcyAmt = maxPlcyAmt;
    }

    public MwHlthInsrPlan maxPlcyAmt(Long maxPlcyAmt) {
        this.maxPlcyAmt = maxPlcyAmt;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwHlthInsrPlan crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwHlthInsrPlan crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwHlthInsrPlan lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwHlthInsrPlan lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwHlthInsrPlan delFlg(Boolean delFlg) {
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

    public MwHlthInsrPlan effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwHlthInsrPlan effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwHlthInsrPlan crntRecFlg(Boolean crntRecFlg) {
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
        MwHlthInsrPlan mwHlthInsrPlan = (MwHlthInsrPlan) o;
        if (mwHlthInsrPlan.getHlthInsrPlanSeq() == null || getHlthInsrPlanSeq() == null) {
            return false;
        }
        return Objects.equals(getHlthInsrPlanSeq(), mwHlthInsrPlan.getHlthInsrPlanSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getHlthInsrPlanSeq());
    }

    @Override
    public String toString() {
        return "MwHlthInsrPlan{" + "id=" + getHlthInsrPlanSeq() + ", hlthInsrPlanSeq=" + getHlthInsrPlanSeq() + ", planId='" + getPlanId()
                + "'" + ", planNm='" + getPlanNm() + "'" + ", anlPremAmt=" + getAnlPremAmt() + ", maxPlcyAmt=" + getMaxPlcyAmt()
                + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'" + ", lastUpdBy='" + getLastUpdBy() + "'"
                + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'" + ", effStartDt='" + getEffStartDt() + "'"
                + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'" + "}";
    }
}
