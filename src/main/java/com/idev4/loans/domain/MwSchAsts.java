package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "mw_sch_asts")
// @IdClass(MwSchAstsId.class)
public class MwSchAsts {

    @Id
    @Column(name = "sch_asts_seq")
    private Long schAstsSeq;

    @Column(name = "tot_rms")
    private Long totRms;

    @Column(name = "TOT_OFCS")
    private Long totOfcs;

    @Column(name = "TOT_TOILETS")
    private Long totToilets;

    @Column(name = "TOT_CMPTRS")
    private Long totCmptrs;

    @Column(name = "TOT_CHRS")
    private Long totChrs;

    @Column(name = "TOT_DSKS")
    private Long totDsks;

    //Mapped on Science Lab
    @Column(name = "TOT_LABS")
    private Long totLabs;

    @Column(name = "TOT_WCLRS")
    private Long totWclrs;

    @Column(name = "TOT_FANS")
    private Long totFans;

    @Column(name = "TOT_GNRTRS")
    private Long totGnrtrs;

    @Column(name = "TOT_FLRS")
    private Long totFlrs;

    @Column(name = "OTH_ASTS")
    private String othAsts;

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

    @Column(name = "loan_App_SEQ")
    private long loanAppSeq;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    //Added By Rizwan on 25 February 2022
    @Column(name = "TOT_MAL_TOLTS")
    private Long totMalTolts;

    @Column(name = "TOT_FMAL_TOLTS")
    private Long totFmalTolts;

    @Column(name = "TOT_CMPTR_LABS")
    private Long totCmptrLabs;

    public Long getTotCmptrLabs() {
        return totCmptrLabs;
    }

    public void setTotCmptrLabs(Long totCmptrLabs) {
        this.totCmptrLabs = totCmptrLabs;
    }

    public Long getTotMalTolts() {
        return totMalTolts;
    }

    public void setTotMalTolts(Long totMalTolts) {
        this.totMalTolts = totMalTolts;
    }

    public Long getTotFmalTolts() {
        return totFmalTolts;
    }

    public void setTotFmalTolts(Long totFmalTolts) {
        this.totFmalTolts = totFmalTolts;
    }

    //End

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getSchAstsSeq() {
        return schAstsSeq;
    }

    public void setSchAstsSeq(Long schAstsSeq) {
        this.schAstsSeq = schAstsSeq;
    }

    public Long getTotRms() {
        return totRms;
    }

    public void setTotRms(Long totRms) {
        this.totRms = totRms;
    }

    public Long getTotOfcs() {
        return totOfcs;
    }

    public void setTotOfcs(Long totOfcs) {
        this.totOfcs = totOfcs;
    }

    public Long getTotToilets() {
        return totToilets;
    }

    public void setTotToilets(Long totToilets) {
        this.totToilets = totToilets;
    }

    public Long getTotCmptrs() {
        return totCmptrs;
    }

    public void setTotCmptrs(Long totCmptrs) {
        this.totCmptrs = totCmptrs;
    }

    public Long getTotChrs() {
        return totChrs;
    }

    public void setTotChrs(Long totChrs) {
        this.totChrs = totChrs;
    }

    public Long getTotDsks() {
        return totDsks;
    }

    public void setTotDsks(Long totDsks) {
        this.totDsks = totDsks;
    }

    public Long getTotLabs() {
        return totLabs;
    }

    public void setTotLabs(Long totLabs) {
        this.totLabs = totLabs;
    }

    public Long getTotWclrs() {
        return totWclrs;
    }

    public void setTotWclrs(Long totWclrs) {
        this.totWclrs = totWclrs;
    }

    public Long getTotFans() {
        return totFans;
    }

    public void setTotFans(Long totFans) {
        this.totFans = totFans;
    }

    public Long getTotGnrtrs() {
        return totGnrtrs;
    }

    public void setTotGnrtrs(Long totgnrtrs) {
        this.totGnrtrs = totgnrtrs;
    }

    public Long getTotFlrs() {
        return totFlrs;
    }

    public void setTotFlrs(Long totFlrs) {
        this.totFlrs = totFlrs;
    }

    public String getOthAsts() {
        return othAsts;
    }

    public void setOthAsts(String othAsts) {
        this.othAsts = othAsts;
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

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

}
