package com.idev4.loans.domain;

import com.idev4.loans.ids.MwLoanAppTrnsId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwLoanAppTrns.
 */
@Entity
@Table(name = "MW_LOAN_APP_TRNS")
@IdClass(MwLoanAppTrnsId.class)
public class MwLoanAppTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LOAN_APP_TRNS_SEQ")
    private Long loanAppTrnsSeq;

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "TRNS_DT")
    private Instant trnsDt;

    @Column(name = "FROM_PORT")
    private Long fromPort;

    @Column(name = "TO_PORT")
    private Long toPort;

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

    public Long getLoanAppTrnsSeq() {
        return loanAppTrnsSeq;
    }

    public void setLoanAppTrnsSeq(Long loanAppTrnsSeq) {
        this.loanAppTrnsSeq = loanAppTrnsSeq;
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

    public Instant getTrnsDt() {
        return trnsDt;
    }

    public void setTrnsDt(Instant trnsDt) {
        this.trnsDt = trnsDt;
    }

    public Long getFromPort() {
        return fromPort;
    }

    public void setFromPort(Long fromPort) {
        this.fromPort = fromPort;
    }

    public Long getToPort() {
        return toPort;
    }

    public void setToPort(Long toPort) {
        this.toPort = toPort;
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
