package com.idev4.loans.domain;

import com.idev4.loans.ids.MwBizAprslIncmDtlId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwBizAprslIncmDtl.
 */
@Entity
@Table(name = "mw_biz_aprsl_incm_dtl")
@IdClass(MwBizAprslIncmDtlId.class)
public class MwBizAprslIncmDtl implements Serializable {

    //(incm_dtl_SEQ, INCM_CTGRY_KEY);
    private static final long serialVersionUID = 1L;
    @Column(name = "incm_hdr_seq")
    public Long mwBizAprslIncmHdr;
    @Id
    @Column(name = "incm_dtl_seq")
    private Long incmDtlSeq;
    @Id
    @Column(name = "incm_ctgry_key")
    private Long incmCtgryKey;
    @Column(name = "incm_amt")
    private Double incmAmt;
    @Column(name = "incm_typ_key")
    private Long incmTypKey;
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

    // @ManyToOne
    // private MwBizAprslIncmHdr mwBizAprslIncmHdr;
    @Column(name = "ENTY_TYP_FLG")
    private Integer entyTypFlg;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Integer getEntyTypFlg() {
        return entyTypFlg;
    }

    public void setEntyTypFlg(Integer entyTypFlg) {
        this.entyTypFlg = entyTypFlg;
    }

    public Long getIncmDtlSeq() {
        return incmDtlSeq;
    }

    public void setIncmDtlSeq(Long incmDtlSeq) {
        this.incmDtlSeq = incmDtlSeq;
    }

    public MwBizAprslIncmDtl incmDtlSeq(Long incmDtlSeq) {
        this.incmDtlSeq = incmDtlSeq;
        return this;
    }

    public Double getIncmAmt() {
        return incmAmt;
    }

    public void setIncmAmt(Double incmAmt) {
        this.incmAmt = incmAmt;
    }

    public MwBizAprslIncmDtl incmAmt(Double incmAmt) {
        this.incmAmt = incmAmt;
        return this;
    }

    public Long getIncmCtgryKey() {
        return incmCtgryKey;
    }

    public void setIncmCtgryKey(Long incmCtgryKey) {
        this.incmCtgryKey = incmCtgryKey;
    }

    public MwBizAprslIncmDtl incmCtgryKey(Long incmCtgryKey) {
        this.incmCtgryKey = incmCtgryKey;
        return this;
    }

    public Long getIncmTypKey() {
        return incmTypKey;
    }

    public void setIncmTypKey(Long incmTypKey) {
        this.incmTypKey = incmTypKey;
    }

    public MwBizAprslIncmDtl incmTypKey(Long incmTypKey) {
        this.incmTypKey = incmTypKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwBizAprslIncmDtl crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwBizAprslIncmDtl crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwBizAprslIncmDtl lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwBizAprslIncmDtl lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwBizAprslIncmDtl delFlg(Boolean delFlg) {
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

    public MwBizAprslIncmDtl effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwBizAprslIncmDtl effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwBizAprslIncmDtl crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getMwBizAprslIncmHdr() {
        return mwBizAprslIncmHdr;
    }

    public void setMwBizAprslIncmHdr(Long mwBizAprslIncmHdr) {
        this.mwBizAprslIncmHdr = mwBizAprslIncmHdr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwBizAprslIncmDtl mwBizAprslIncmDtl = (MwBizAprslIncmDtl) o;
        if (mwBizAprslIncmDtl.getIncmDtlSeq() == null || getIncmDtlSeq() == null) {
            return false;
        }
        return Objects.equals(getIncmDtlSeq(), mwBizAprslIncmDtl.getIncmDtlSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIncmDtlSeq());
    }

    @Override
    public String toString() {
        return "MwBizAprslIncmDtl{" + "id=" + getIncmDtlSeq() + ", incmDtlSeq=" + getIncmDtlSeq() + ", incmAmt=" + getIncmAmt()
                + ", incmCtgryKey=" + getIncmCtgryKey() + ", incmTypKey=" + getIncmTypKey() + ", crtdBy='" + getCrtdBy() + "'"
                + ", crtdDt='" + getCrtdDt() + "'" + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'"
                + ", delFlg='" + isDelFlg() + "'" + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'"
                + ", crntRecFlg='" + isCrntRecFlg() + "'" + "}";
    }
}
