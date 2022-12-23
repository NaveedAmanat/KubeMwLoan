package com.idev4.loans.domain;

import com.idev4.loans.ids.MwPrdId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwPrd.
 */
@Entity
@Table(name = "mw_prd")
@IdClass(MwPrdId.class)
public class MwPrd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "prd_seq")
    private Long prdSeq;

    @Column(name = "prd_grp_seq")
    private Long prdGrpSeq;

    @Column(name = "prd_id")
    private String prdId;

    @Column(name = "prd_nm")
    private String prdNm;

    @Column(name = "prd_cmnt")
    private String prdCmnt;

    @Column(name = "fnd_by_key")
    private Long fndByKey;

    // @Column(name = "biz_sect_key")
    // private Long bizSectKey;
    //
    // @Column(name = "acty_key")
    // private Long actyKey;

    @Column(name = "irr_val")
    private Double irrVal;

    @Column(name = "mlt_loan_flg")
    private Boolean multiLoanFlg;

    @Column(name = "IRR_FLG")
    private Boolean irrFlg;

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

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "RNDNG_SCL")
    private Long rndngScl;

    @Column(name = "RNDNG_ADJ")
    private Long rndngAdj;

    @Column(name = "PRD_TYP_KEY")
    private Long prdTypKey;


    public Long getPrdGrpSeq() {
        return prdGrpSeq;
    }

    public void setPrdGrpSeq(Long prdGrpSeq) {
        this.prdGrpSeq = prdGrpSeq;
    }


    public Long getPrdTypKey() {
        return prdTypKey;
    }

    public void setPrdTypKey(Long prdTypKey) {
        this.prdTypKey = prdTypKey;
    }

    public Long getRndngScl() {
        return rndngScl;
    }

    public void setRndngScl(Long rndngScl) {
        this.rndngScl = rndngScl;
    }

    public Long getRndngAdj() {
        return rndngAdj;
    }

    public void setRndngAdj(Long rndngAdj) {
        this.rndngAdj = rndngAdj;
    }

    public Boolean getIrrFlg() {
        return irrFlg;
    }

    public void setIrrFlg(Boolean irrFlg) {
        this.irrFlg = irrFlg;
    }

    public Boolean getMultiLoanFlg() {
        return multiLoanFlg;
    }

    public void setMultiLoanFlg(Boolean multiLoanFlg) {
        this.multiLoanFlg = multiLoanFlg;
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

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public MwPrd prdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
        return this;
    }

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public MwPrd prdId(String prdId) {
        this.prdId = prdId;
        return this;
    }

    public String getPrdNm() {
        return prdNm;
    }

    public void setPrdNm(String prdNm) {
        this.prdNm = prdNm;
    }

    public MwPrd prdNm(String prdNm) {
        this.prdNm = prdNm;
        return this;
    }

    public String getPrdCmnt() {
        return prdCmnt;
    }

    public void setPrdCmnt(String prdCmnt) {
        this.prdCmnt = prdCmnt;
    }

    public MwPrd prdCmnt(String prdCmnt) {
        this.prdCmnt = prdCmnt;
        return this;
    }

    // public Long getBizSectKey() {
    // return bizSectKey;
    // }
    //
    // public MwPrd bizSectKey(Long bizSectKey) {
    // this.bizSectKey = bizSectKey;
    // return this;
    // }
    //
    // public void setBizSectKey(Long bizSectKey) {
    // this.bizSectKey = bizSectKey;
    // }

    // public Long getActyKey() {
    // return actyKey;
    // }
    //
    // public MwPrd actyKey(Long actyKey) {
    // this.actyKey = actyKey;
    // return this;
    // }
    //
    // public void setActyKey(Long actyKey) {
    // this.actyKey = actyKey;
    // }

    public Long getFndByKey() {
        return fndByKey;
    }

    public void setFndByKey(Long fndByKey) {
        this.fndByKey = fndByKey;
    }

    public MwPrd fndByKey(Long fndByKey) {
        this.fndByKey = fndByKey;
        return this;
    }

    public Double getIrrVal() {
        return irrVal;
    }

    public void setIrrVal(Double irrVal) {
        this.irrVal = irrVal;
    }

    public MwPrd irrVal(Double irrVal) {
        this.irrVal = irrVal;
        return this;
    }

    public Boolean isMultiLoanFlg() {
        return multiLoanFlg;
    }

    public MwPrd multiLoanFlg(Boolean multiLoanFlg) {
        this.multiLoanFlg = multiLoanFlg;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwPrd crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwPrd crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwPrd lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwPrd lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwPrd delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwPrd effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwPrd effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwPrd crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
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
        MwPrd mwPrd = (MwPrd) o;
        if (mwPrd.getPrdSeq() == null || getPrdSeq() == null) {
            return false;
        }
        return Objects.equals(getPrdSeq(), mwPrd.getPrdSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPrdSeq());
    }

    @Override
    public String toString() {
        return "MwPrd{" + "id=" + getPrdSeq() + ", prdSeq=" + getPrdSeq() + ", prdId='" + getPrdId() + "'" + ", prdNm='" + getPrdNm() + "'"
                + ", prdCmnt='" + getPrdCmnt() + "'" + ", fndByKey=" + getFndByKey() +
                // ", bizSectKey=" + getBizSectKey() +
                // ", actyKey=" + getActyKey() +
                ", irrVal=" + getIrrVal() + ", multiLoanFlg='" + isMultiLoanFlg() + "'" + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='"
                + getCrtdDt() + "'" + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='"
                + isDelFlg() + "'" + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='"
                + isCrntRecFlg() + "'" + "}";
    }
}
