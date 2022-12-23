package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "MW_CNIC_TKN")
// @IdClass ( MwCnicTknId.class )
public class MwCnicTkn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CNIC_TKN_SEQ")
    private Long cnicTknSeq;

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "CNIC_TKN_NUM")
    private Long cnicTknNum;

    @Column(name = "CNIC_TKN_EXPRY_DT")
    private Instant cnicTknExpryDt;

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

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getCnicTknSeq() {
        return cnicTknSeq;
    }

    public void setCnicTknSeq(Long cnicTknSeq) {
        this.cnicTknSeq = cnicTknSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Long getCnicTknNum() {
        return cnicTknNum;
    }

    public void setCnicTknNum(Long cnicTknNum) {
        this.cnicTknNum = cnicTknNum;
    }

    public Instant getCnicTknExpryDt() {
        return cnicTknExpryDt;
    }

    public void setCnicTknExpryDt(Instant cnicTknExpryDt) {
        this.cnicTknExpryDt = cnicTknExpryDt;
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

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

}
