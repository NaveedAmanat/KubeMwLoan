package com.idev4.loans.domain;


import com.idev4.loans.ids.MwCbwrId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwCbwr.
 */
@Entity
@Table(name = "mw_cbwr")
@IdClass(MwCbwrId.class)
public class MwCbwr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cbwr_seq")
    private Long cbwrSeq;

    @Column(name = "cnic_num")
    private Long cnicNum;

    @Column(name = "cnic_expry_dt")
    private Instant cnicEpryDt;

    @Column(name = "frst_nm")
    private String frstNm;

    @Column(name = "last_nm")
    private String lastNm;

    @Column(name = "dob")
    private Instant dob;

    @Column(name = "gndr_key")
    private Long gndrKey;

    @Column(name = "occ_key")
    private Long occKey;

    @Column(name = "rel_wth_clnt_key")
    private Long relWthClntKey;

    @Column(name = "ph_num")
    private String phNum;

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

    @Column(name = "res_typ_key")
    private Long resTypKey;

    @Column(name = "cbwr_id")
    private String cbwrId;

    @Column(name = "loan_app_seq")
    private Long loanAppSeq;

    public Long getCbwrSeq() {
        return cbwrSeq;
    }

    public void setCbwrSeq(Long cbwrSeq) {
        this.cbwrSeq = cbwrSeq;
    }

    public MwCbwr cbwrSeq(Long cbwrSeq) {
        this.cbwrSeq = cbwrSeq;
        return this;
    }

    public Long getCnicNum() {
        return cnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
    }

    public MwCbwr cnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
        return this;
    }

    public Instant getCnicEpryDt() {
        return cnicEpryDt;
    }

    public void setCnicEpryDt(Instant cnicEpryDt) {
        this.cnicEpryDt = cnicEpryDt;
    }

    public MwCbwr cnicEpryDt(Instant cnicEpryDt) {
        this.cnicEpryDt = cnicEpryDt;
        return this;
    }

    public String getFrstNm() {
        return frstNm;
    }

    public void setFrstNm(String frstNm) {
        this.frstNm = frstNm;
    }

    public MwCbwr frstNm(String frstNm) {
        this.frstNm = frstNm;
        return this;
    }

    public String getLastNm() {
        return lastNm;
    }

    public void setLastNm(String lastNm) {
        this.lastNm = lastNm;
    }

    public MwCbwr lastNm(String lastNm) {
        this.lastNm = lastNm;
        return this;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public MwCbwr dob(Instant dob) {
        this.dob = dob;
        return this;
    }

    public Long getGndrKey() {
        return gndrKey;
    }

    public void setGndrKey(Long gndrKey) {
        this.gndrKey = gndrKey;
    }

    public MwCbwr gndrKey(Long gndrKey) {
        this.gndrKey = gndrKey;
        return this;
    }

    public Long getOccKey() {
        return occKey;
    }

    public void setOccKey(Long occKey) {
        this.occKey = occKey;
    }

    public MwCbwr occKey(Long occKey) {
        this.occKey = occKey;
        return this;
    }

    public Long getRelWthClntKey() {
        return relWthClntKey;
    }

    public void setRelWthClntKey(Long relWthClntKey) {
        this.relWthClntKey = relWthClntKey;
    }

    public MwCbwr relWthClntKey(Long relWthClntKey) {
        this.relWthClntKey = relWthClntKey;
        return this;
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum;
    }

    public MwCbwr phNum(String phNum) {
        this.phNum = phNum;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwCbwr crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwCbwr crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwCbwr lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwCbwr lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwCbwr delFlg(Boolean delFlg) {
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

    public MwCbwr effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwCbwr effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwCbwr crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getResTypKey() {
        return resTypKey;
    }

    public void setResTypKey(Long resTypKey) {
        this.resTypKey = resTypKey;
    }

    public MwCbwr resTypKey(Long resTypKey) {
        this.resTypKey = resTypKey;
        return this;
    }

    public String getCbwrId() {
        return cbwrId;
    }

    public void setCbwrId(String cbwrId) {
        this.cbwrId = cbwrId;
    }

    public MwCbwr cbwrId(String cbwrId) {
        this.cbwrId = cbwrId;
        return this;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public MwCbwr loanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
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
        MwCbwr mwCbwr = (MwCbwr) o;
        if (mwCbwr.getCbwrSeq() == null || getCbwrSeq() == null) {
            return false;
        }
        return Objects.equals(getCbwrSeq(), mwCbwr.getCbwrSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCbwrSeq());
    }

    @Override
    public String toString() {
        return "MwCbwr{" +
                "id=" + getCbwrSeq() +
                ", cbwrSeq=" + getCbwrSeq() +
                ", cnicNum=" + getCnicNum() +
                ", cnicEpryDt='" + getCnicEpryDt() + "'" +
                ", frstNm='" + getFrstNm() + "'" +
                ", lastNm='" + getLastNm() + "'" +
                ", dob='" + getDob() + "'" +
                ", gndrKey=" + getGndrKey() +
                ", occKey=" + getOccKey() +
                ", relWthClntKey=" + getRelWthClntKey() +
                ", phNum='" + getPhNum() + "'" +
                ", crtdBy='" + getCrtdBy() + "'" +
                ", crtdDt='" + getCrtdDt() + "'" +
                ", lastUpdBy='" + getLastUpdBy() + "'" +
                ", lastUpdDt='" + getLastUpdDt() + "'" +
                ", delFlg='" + isDelFlg() + "'" +
                ", effStartDt='" + getEffStartDt() + "'" +
                ", effEndDt='" + getEffEndDt() + "'" +
                ", crntRecFlg='" + isCrntRecFlg() + "'" +
                ", resTypKey=" + getResTypKey() +
                ", cbwrId='" + getCbwrId() + "'" +
                ", loanAppSeq=" + getLoanAppSeq() +
                "}";
    }
}
