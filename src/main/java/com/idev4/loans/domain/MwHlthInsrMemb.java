package com.idev4.loans.domain;

import com.idev4.loans.ids.MwHlthInsrMembId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwHlthInsrMemb.
 */
@Entity
@Table(name = "mw_hlth_insr_memb")
@IdClass(MwHlthInsrMembId.class)
public class MwHlthInsrMemb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "MEMBER_ID")
    public Long memberId;
    @Id
    @Column(name = "HLTH_INSR_MEMB_SEQ")
    private Long hlthInsrMembSeq;
    @Id
    @Column(name = "loan_app_seq")
    private Long loanAppSeq;
    @Column(name = "member_cnic_num")
    private Long membCnicNum;
    @Column(name = "member_nm")
    private String membNm;
    @Column(name = "dob")
    private Instant dob;
    @Column(name = "gndr_key")
    private Long gndrKey;
    @Column(name = "rel_key")
    private Long relKey;
    @Column(name = "mrtl_sts_key")
    private Long mrtlStsKey;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    /*@ManyToOne
    private MwClntHlthInsr mwClntHlthInsr;*/

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

    public Long getHlthInsrMembSeq() {
        return hlthInsrMembSeq;
    }

    public void setHlthInsrMembSeq(Long hlthInsrMembSeq) {
        this.hlthInsrMembSeq = hlthInsrMembSeq;
    }

    public MwHlthInsrMemb hlthInsrMembSeq(Long hlthInsrMembSeq) {
        this.hlthInsrMembSeq = hlthInsrMembSeq;
        return this;
    }

    public Long getMembCnicNum() {
        return membCnicNum;
    }

    public void setMembCnicNum(Long membCnicNum) {
        this.membCnicNum = membCnicNum;
    }

    public MwHlthInsrMemb membCnicNum(Long membCnicNum) {
        this.membCnicNum = membCnicNum;
        return this;
    }

    public String getMembNm() {
        return membNm;
    }

    public void setMembNm(String membNm) {
        this.membNm = membNm;
    }

    public MwHlthInsrMemb membNm(String membNm) {
        this.membNm = membNm;
        return this;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public MwHlthInsrMemb dob(Instant dob) {
        this.dob = dob;
        return this;
    }

    public Long getGndrKey() {
        return gndrKey;
    }

    public void setGndrKey(Long gndrKey) {
        this.gndrKey = gndrKey;
    }

    public MwHlthInsrMemb gndrKey(Long gndrKey) {
        this.gndrKey = gndrKey;
        return this;
    }

    public Long getRelKey() {
        return relKey;
    }

    public void setRelKey(Long relKey) {
        this.relKey = relKey;
    }

    public MwHlthInsrMemb relKey(Long relKey) {
        this.relKey = relKey;
        return this;
    }

    public Long getMrtlStsKey() {
        return mrtlStsKey;
    }

    public void setMrtlStsKey(Long mrtlStsKey) {
        this.mrtlStsKey = mrtlStsKey;
    }

    public MwHlthInsrMemb mrtlStsKey(Long mrtlStsKey) {
        this.mrtlStsKey = mrtlStsKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwHlthInsrMemb crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwHlthInsrMemb crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwHlthInsrMemb lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwHlthInsrMemb lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwHlthInsrMemb delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwHlthInsrMemb effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwHlthInsrMemb effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwHlthInsrMemb crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    /**
     * @return the loanAppSeq
     */
    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    /**
     * @param loanAppSeq the loanAppSeq to set
     */
    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwHlthInsrMemb mwHlthInsrMemb = (MwHlthInsrMemb) o;
        if (mwHlthInsrMemb.getHlthInsrMembSeq() == null || getHlthInsrMembSeq() == null) {
            return false;
        }
        return Objects.equals(getHlthInsrMembSeq(), mwHlthInsrMemb.getHlthInsrMembSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getHlthInsrMembSeq());
    }

    @Override
    public String toString() {
        return "MwHlthInsrMemb{" + "id=" + getHlthInsrMembSeq() + ", hlthInsrMembSeq=" + getHlthInsrMembSeq() + ", membCnicNum="
                + getMembCnicNum() + ", membNm='" + getMembNm() + "'" + ", dob='" + getDob() + "'" + ", gndrKey=" + getGndrKey()
                + ", relKey=" + getRelKey() + ", mrtlStsKey=" + getMrtlStsKey() + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='"
                + getCrtdDt() + "'" + ", lastUpdBy='" + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='"
                + isDelFlg() + "'" + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='"
                + isCrntRecFlg() + "'" + "}";
    }
}
