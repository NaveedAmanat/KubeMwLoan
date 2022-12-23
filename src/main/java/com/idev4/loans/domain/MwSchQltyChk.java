package com.idev4.loans.domain;

import com.idev4.loans.ids.MwSchQltyChkId;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "mw_sch_qlty_chk")
@IdClass(MwSchQltyChkId.class)
public class MwSchQltyChk {

    //(sch_qlty_chk_SEQ, QST_SEQ, ANSWR_SEQ);
    @Id
    @Column(name = "sch_qlty_chk_seq")
    private long schQltyChkSeq;

    @Id
    @Column(name = "qst_seq")
    private long qstSeq;

    @Id
    @Column(name = "answr_seq")
    private Long answrSeq;

    @Column(name = "sch_aprsl_seq")
    private long schAprslSeq;

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

    public long getSchQltyChkSeq() {
        return schQltyChkSeq;
    }

    public void setSchQltyChkSeq(long schQltyChkSeq) {
        this.schQltyChkSeq = schQltyChkSeq;
    }

    public long getSchAprslSeq() {
        return schAprslSeq;
    }

    public void setSchAprslSeq(long schAprslSeq) {
        this.schAprslSeq = schAprslSeq;
    }

    public long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(long qstSeq) {
        this.qstSeq = qstSeq;
    }

    public Long getAnswrSeq() {
        return answrSeq;
    }

    public void setAnswrSeq(Long answrSeq) {
        this.answrSeq = answrSeq;
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
