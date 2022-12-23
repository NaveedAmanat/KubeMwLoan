package com.idev4.loans.domain;

import com.idev4.loans.ids.MwPrdSgrtInstId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "mw_prd_sgrt_inst")
@IdClass(MwPrdSgrtInstId.class)
public class MwPrdSgrtInst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "prd_sgrt_inst_seq")
    private Long prdSgrtInstSeq;

    @Column(name = "sgrt_enty_SEQ")
    private Long sgrtEntySeq;

    @Column(name = "enty_typ_str")
    private String entyTypStr;


    @Column(name = "inst_num")
    private Long instNum;


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

    public Long getPrdSgrtInstSeq() {
        return prdSgrtInstSeq;
    }

    public void setPrdSgrtInstSeq(Long prdSgrtInstSeq) {
        this.prdSgrtInstSeq = prdSgrtInstSeq;
    }

    public Long getSgrtEntySeq() {
        return sgrtEntySeq;
    }

    public void setSgrtEntySeq(Long sgrtEntySeq) {
        this.sgrtEntySeq = sgrtEntySeq;
    }

    public String getEntyTypStr() {
        return entyTypStr;
    }

    public void setEntyTypStr(String entyTypStr) {
        this.entyTypStr = entyTypStr;
    }

    public Long getInstNum() {
        return instNum;
    }

    public void setInstNum(Long instNum) {
        this.instNum = instNum;
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
