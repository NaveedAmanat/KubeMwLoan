package com.idev4.loans.domain;

import com.idev4.loans.ids.MwRulId;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "mw_rul")
@IdClass(MwRulId.class)
public class MwRul {

    @Id
    @Column(name = "rul_seq")
    private Long rulSeq;

    @Column(name = "rul_id")
    private String rulId;

    @Column(name = "rul_ctgry_key")
    private Long rulCtgryKey;

    @Column(name = "rul_nm")
    private String rulNm;

    @Column(name = "rul_cmnt")
    private String rulCmnt;

    @Column(name = "rul_crtra_str")
    private String rulCrtraStr;

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

    public Long getRulSeq() {
        return rulSeq;
    }

    public void setRulSeq(Long rulSeq) {
        this.rulSeq = rulSeq;
    }

    public String getRulId() {
        return rulId;
    }

    public void setRulId(String rulId) {
        this.rulId = rulId;
    }

    public Long getRulCtgryKey() {
        return rulCtgryKey;
    }

    public void setRulCtgryKey(Long rulCtgryKey) {
        this.rulCtgryKey = rulCtgryKey;
    }

    public String getRulNm() {
        return rulNm;
    }

    public void setRulNm(String rulNm) {
        this.rulNm = rulNm;
    }

    public String getRulCmnt() {
        return rulCmnt;
    }

    public void setRulCmnt(String rulCmnt) {
        this.rulCmnt = rulCmnt;
    }

    public String getRulCrtraStr() {
        return rulCrtraStr;
    }

    public void setRulCrtraStr(String rulCrtraStr) {
        this.rulCrtraStr = rulCrtraStr;
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
