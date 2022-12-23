package com.idev4.loans.domain;

import com.idev4.loans.ids.MwLoanAppMntrngChksId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwLoanAppMntrngChks.
 */
@Entity
@Table(name = "MW_LOAN_APP_MNTRNG_CHKS")
@IdClass(MwLoanAppMntrngChksId.class)
public class MwLoanAppMntrngChks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LOAN_APP_MNTRNG_CHKS_SEQ")
    private Long loanAppMntrngChksSeq;

    @Id
    @Column(name = "CHK_FLG")
    private Integer chkFlg;

    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "loan_app_seq")
    private Long loanAppSeq;

    @Column(name = "RSN")
    private Long rsn;

    @Column(name = "ACTN_TKN")
    private Long actnTkn;

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

    @Column(name = "CMNT")
    private String cmnt;


    public Integer getChkFlg() {
        return chkFlg;
    }

    public void setChkFlg(Integer chkFlg) {
        this.chkFlg = chkFlg;
    }

    public Long getLoanAppMntrngChksSeq() {
        return loanAppMntrngChksSeq;
    }

    public void setLoanAppMntrngChksSeq(Long loanAppMntrngChksSeq) {
        this.loanAppMntrngChksSeq = loanAppMntrngChksSeq;
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

    public Long getRsn() {
        return rsn;
    }

    public void setRsn(Long rsn) {
        this.rsn = rsn;
    }

    public Long getActnTkn() {
        return actnTkn;
    }

    public void setActnTkn(Long actnTkn) {
        this.actnTkn = actnTkn;
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

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

}
