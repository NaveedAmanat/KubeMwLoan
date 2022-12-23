package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "mw_sch_grd")
// @IdClass(MwSchGrdId.class)
public class MwSchGrd {

    @Id
    @Column(name = "sch_grd_seq")
    private long schGrdSeq;

    @Column(name = "tot_fem_stdnt")
    private long totFemStdnt;

    @Column(name = "tot_male_stdnt")
    private long totMaleStdnt;

    @Column(name = "avg_grd_fee")
    private long avgGrdFee;

    @Column(name = "no_fee_stdnt")
    private long noFeeStdnt;

    @Column(name = "fem_stdnt_prsnt")
    private long femStdntPrsnt;

    @Column(name = "male_stdnt_prsnt")
    private long maleStdntPrsnt;

    @Column(name = "grd_key")
    private long grdKey;

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

    @Column(name = "sch_aprsl_seq")
    private long schAprslSeq;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public long getSchAprslSeq() {
        return schAprslSeq;
    }

    public void setSchAprslSeq(long schAprslSeq) {
        this.schAprslSeq = schAprslSeq;
    }

    public long getSchGrdSeq() {
        return schGrdSeq;
    }

    public void setSchGrdSeq(long schGrdSeq) {
        this.schGrdSeq = schGrdSeq;
    }

    public long getTotFemStdnt() {
        return totFemStdnt;
    }

    public void setTotFemStdnt(long totFemStdnt) {
        this.totFemStdnt = totFemStdnt;
    }

    public long getTotMaleStdnt() {
        return totMaleStdnt;
    }

    public void setTotMaleStdnt(long totMaleStdnt) {
        this.totMaleStdnt = totMaleStdnt;
    }

    public long getAvgGrdFee() {
        return avgGrdFee;
    }

    public void setAvgGrdFee(long avgGrdFee) {
        this.avgGrdFee = avgGrdFee;
    }

    public long getNoFeeStdnt() {
        return noFeeStdnt;
    }

    public void setNoFeeStdnt(long noFeeStdnt) {
        this.noFeeStdnt = noFeeStdnt;
    }

    public long getFemStdntPrsnt() {
        return femStdntPrsnt;
    }

    public void setFemStdntPrsnt(long femStdntPrsnt) {
        this.femStdntPrsnt = femStdntPrsnt;
    }

    public long getMaleStdntPrsnt() {
        return maleStdntPrsnt;
    }

    public void setMaleStdntPrsnt(long maleStdntPrsnt) {
        this.maleStdntPrsnt = maleStdntPrsnt;
    }

    public long getGrdKey() {
        return grdKey;
    }

    public void setGrdKey(long grdKey) {
        this.grdKey = grdKey;
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

}
