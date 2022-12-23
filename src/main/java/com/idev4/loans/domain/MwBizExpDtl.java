package com.idev4.loans.domain;

import com.idev4.loans.ids.MwBizExpDtlId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwBizExpDtl.
 */
@Entity
@Table(name = "mw_biz_exp_dtl")
@IdClass(MwBizExpDtlId.class)
public class MwBizExpDtl implements Serializable {

    //(exp_dtl_SEQ, EXP_CTGRY_KEY, BIZ_APRSL, exp_type)
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "biz_aprsl_seq")
    public Long mwBizAprsl;
    @Id
    @Column(name = "exp_dtl_seq")
    private Long expDtlSeq;
    @Id
    @Column(name = "exp_ctgry_key")
    private Long expCtgryKey;
    @Id
    @Column(name = "exp_typ_key")
    private Long expTypKey;

    @Column(name = "exp_amt")
    private Double expAmt;

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
    @Column(name = "ENTY_TYP_FLG")
    private Integer entyTypFlg;

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    // @ManyToOne
    // private MwBizAprsl mwBizAprsl;

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Integer getEntyTypFlg() {
        return entyTypFlg;
    }

    public void setEntyTypFlg(Integer entyTypFlg) {
        this.entyTypFlg = entyTypFlg;
    }

    public Long getExpDtlSeq() {
        return expDtlSeq;
    }

    public void setExpDtlSeq(Long expDtlSeq) {
        this.expDtlSeq = expDtlSeq;
    }

    public MwBizExpDtl expDtlSeq(Long expDtlSeq) {
        this.expDtlSeq = expDtlSeq;
        return this;
    }

    public Double getExpAmt() {
        return expAmt;
    }

    public void setExpAmt(Double expAmt) {
        this.expAmt = expAmt;
    }

    public MwBizExpDtl expAmt(Double expAmt) {
        this.expAmt = expAmt;
        return this;
    }

    public Long getExpCtgryKey() {
        return expCtgryKey;
    }

    public void setExpCtgryKey(Long expCtgryKey) {
        this.expCtgryKey = expCtgryKey;
    }

    public MwBizExpDtl expCtgryKey(Long expCtgryKey) {
        this.expCtgryKey = expCtgryKey;
        return this;
    }

    public Long getExpTypKey() {
        return expTypKey;
    }

    public void setExpTypKey(Long expTypKey) {
        this.expTypKey = expTypKey;
    }

    public MwBizExpDtl expTypKey(Long expTypKey) {
        this.expTypKey = expTypKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwBizExpDtl crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwBizExpDtl crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwBizExpDtl lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwBizExpDtl lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwBizExpDtl delFlg(Boolean delFlg) {
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

    public MwBizExpDtl effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwBizExpDtl effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwBizExpDtl crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getMwBizAprsl() {
        return mwBizAprsl;
    }

    public void setMwBizAprsl(Long mwBizAprsl) {
        this.mwBizAprsl = mwBizAprsl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwBizExpDtl mwBizExpDtl = (MwBizExpDtl) o;
        if (mwBizExpDtl.getExpDtlSeq() == null || getExpDtlSeq() == null) {
            return false;
        }
        return Objects.equals(getExpDtlSeq(), mwBizExpDtl.getExpDtlSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getExpDtlSeq());
    }

    @Override
    public String toString() {
        return "MwBizExpDtl{" + "id=" + getExpDtlSeq() + ", expDtlSeq=" + getExpDtlSeq() + ", expAmt=" + getExpAmt() + ", expCtgryKey="
                + getExpCtgryKey() + ", expTypKey=" + getExpTypKey() + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'"
                + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'"
                + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'"
                + "}";
    }
}
