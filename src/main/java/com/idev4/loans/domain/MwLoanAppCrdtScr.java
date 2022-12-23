package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwLoanAppCrdtScr.
 */
@Entity
@Table(name = "MW_LOAN_APP_CRDT_SCR")
//@IdClass ( MwLoanAppCrdtScrId.class )
public class MwLoanAppCrdtScr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LOAN_APP_CRDT_SCR_SEQ")
    private Long loanAppCrdtScrSeq;

    // @Id
    @Column(name = "EFF_START_DT")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "CRDT_SCR")
    private Long crdtScr;

    @Column(name = "CRDT_RSK_CTGRY")
    private String crdtRskCtgry;

    @Column(name = "CRDT_JSON")
    private String crdtJson;

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

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    //Added by Rizwan Mahfooz on 18 JULY 2022
    @Column(name = "RQSTD_JSON")
    private String rqstdJson;
    //End

    public Long getLoanAppCrdtScrSeq() {
        return loanAppCrdtScrSeq;
    }

    public void setLoanAppCrdtScrSeq(Long loanAppCrdtScrSeq) {
        this.loanAppCrdtScrSeq = loanAppCrdtScrSeq;
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

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Long getCrdtScr() {
        return crdtScr;
    }

    public void setCrdtScr(Long crdtScr) {
        this.crdtScr = crdtScr;
    }

    public String getCrdtRskCtgry() {
        return crdtRskCtgry;
    }

    public void setCrdtRskCtgry(String crdtRskCtgry) {
        this.crdtRskCtgry = crdtRskCtgry;
    }

    public String getCrdtJson() {
        return crdtJson;
    }

    public void setCrdtJson(String crdtJson) {
        this.crdtJson = crdtJson;
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

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public String getRqstdJson() {
        return rqstdJson;
    }

    public void setRqstdJson(String rqstdJson) {
        this.rqstdJson = rqstdJson;
    }
}
