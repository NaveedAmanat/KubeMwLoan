package com.idev4.loans.domain;

import com.idev4.loans.ids.MwDvcRgstrId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwAddr.
 */
@Entity
@Table(name = "MW_DVC_RGSTR")
@IdClass(MwDvcRgstrId.class)
public class MwDvcRgstr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DVC_RGSTR_SEQ")
    private Long dvcRgstrSeq;

    @Column(name = "DVC_ADDR")
    private String dvcAddr;

    @Column(name = "ENTY_TYP_FLG")
    private Long entyTypFlg;

    @Column(name = "ENTY_TYP_SEQ")
    private Long entyTypSeq;

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

    @Column(name = "SYNC_DT")
    private Instant syncDt;

    @Column(name = "STP_SYNC_DT")
    private Instant stpSyncDt;

    @Column(name = "SYNC_TMP_DT")
    private Instant syncTmpDt;

    @Column(name = "STP_SYNC_TMP_DT")
    private Instant stpSyncTmpDt;

    public Instant getStpSyncDt() {
        return stpSyncDt;
    }

    public void setStpSyncDt(Instant stpSyncDt) {
        this.stpSyncDt = stpSyncDt;
    }

    public Instant getSyncTmpDt() {
        return syncTmpDt;
    }

    public void setSyncTmpDt(Instant syncTmpDt) {
        this.syncTmpDt = syncTmpDt;
    }

    public Instant getStpSyncTmpDt() {
        return stpSyncTmpDt;
    }

    public void setStpSyncTmpDt(Instant stpSyncTmpDt) {
        this.stpSyncTmpDt = stpSyncTmpDt;
    }

    public Instant getSyncDt() {
        return syncDt;
    }

    public void setSyncDt(Instant syncDt) {
        this.syncDt = syncDt;
    }

    public Long getDvcRgstrSeq() {
        return dvcRgstrSeq;
    }

    public void setDvcRgstrSeq(Long dvcRgstrSeq) {
        this.dvcRgstrSeq = dvcRgstrSeq;
    }

    public String getDvcAddr() {
        return dvcAddr;
    }

    public void setDvcAddr(String dvcAddr) {
        this.dvcAddr = dvcAddr;
    }

    public Long getEntyTypFlg() {
        return entyTypFlg;
    }

    public void setEntyTypFlg(Long entyTypFlg) {
        this.entyTypFlg = entyTypFlg;
    }

    public Long getEntyTypSeq() {
        return entyTypSeq;
    }

    public void setEntyTypSeq(Long entyTypSeq) {
        this.entyTypSeq = entyTypSeq;
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
