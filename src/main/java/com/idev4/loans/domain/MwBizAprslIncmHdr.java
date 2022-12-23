package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwBizAprslIncmHdr.
 */
@Entity
@Table(name = "mw_biz_aprsl_incm_hdr")
// @IdClass(MwBizAprslIncmHdrId.class)
public class MwBizAprslIncmHdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "biz_aprsl_seq")
    public Long mwBizAprsl;
    @Id
    @Column(name = "incm_hdr_seq")
    private Long incmHdrSeq;
    @Column(name = "max_mnth_sal_amt")
    private Double maxMnthSalAmt;
    @Column(name = "max_sal_num_of_mnth")
    private int maxSalNumOfMnth;
    @Column(name = "min_mnth_sal_amt")
    private Double minMnthSalAmt;
    @Column(name = "min_sal_num_of_mnth")
    private int minSalNumOfMnth;
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
    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    // @ManyToOne
    // private MwBizAprsl mwBizAprsl;

    public Long getIncmHdrSeq() {
        return incmHdrSeq;
    }

    public void setIncmHdrSeq(Long incmHdrSeq) {
        this.incmHdrSeq = incmHdrSeq;
    }

    public Double getMaxMnthSalAmt() {
        return maxMnthSalAmt;
    }

    public void setMaxMnthSalAmt(Double maxMnthSalAmt) {
        this.maxMnthSalAmt = maxMnthSalAmt;
    }

    public int getMaxSalNumOfMnth() {
        return maxSalNumOfMnth;
    }

    public void setMaxSalNumOfMnth(int maxSalNumOfMnth) {
        this.maxSalNumOfMnth = maxSalNumOfMnth;
    }

    public Double getMinMnthSalAmt() {
        return minMnthSalAmt;
    }

    public void setMinMnthSalAmt(Double minMnthSalAmt) {
        this.minMnthSalAmt = minMnthSalAmt;
    }

    public int getMinSalNumOfMnth() {
        return minSalNumOfMnth;
    }

    public void setMinSalNumOfMnth(int minSalNumOfMnth) {
        this.minSalNumOfMnth = minSalNumOfMnth;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Boolean isDelFlg() {
        return delFlg;
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

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
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
        MwBizAprslIncmHdr mwBizAprslIncmHdr = (MwBizAprslIncmHdr) o;
        if (mwBizAprslIncmHdr.getIncmHdrSeq() == null || getIncmHdrSeq() == null) {
            return false;
        }
        return Objects.equals(getIncmHdrSeq(), mwBizAprslIncmHdr.getIncmHdrSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIncmHdrSeq());
    }

    @Override
    public String toString() {
        return "MwBizAprslIncmHdr{" + "id=" + getIncmHdrSeq() + ", incmHdrSeq=" + getIncmHdrSeq() + ", maxMnthSalAmt=" + getMaxMnthSalAmt()
                + ", maxSalNumOfMnth=" + getMaxSalNumOfMnth() + ", minMnthSalAmt=" + getMinMnthSalAmt() + ", minSalNumOfMnth="
                + getMinSalNumOfMnth() + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'" + ", lastUpdBy='"
                + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'" + ", effStartDt='"
                + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'" + "}";
    }
}
