package com.idev4.loans.domain;


import com.idev4.loans.ids.MwQstId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwQst.
 */
@Entity
@Table(name = "mw_qst")
@IdClass(MwQstId.class)
public class MwQst implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @Column(name = "qst_seq")
    private Long qstSeq;

    @Column(name = "QST_SORT_ORDR")
    private Long qstSortOrdr;

    @Column(name = "qst_str")
    private String qstStr;

    @Column(name = "qst_typ_key")
    private Long qstTypKey;

    @Column(name = "qst_sts_key")
    private Long qstStsKey;

    @Column(name = "qst_ctgry_key")
    private Long qstCtgryKey;

    @Column(name = "qstnr_seq")
    private Long qstnrSeq;

    @Column(name = "ph_num")
    private String phNum;

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

    public Long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(Long qstSeq) {
        this.qstSeq = qstSeq;
    }

//	public String getQstId() {
//		return qstId;
//	}
//
//	public void setQstId(String qstId) {
//		this.qstId = qstId;
//	}

    public Long getQstSortOrdr() {
        return qstSortOrdr;
    }

    public void setQstSortOrdr(Long qstSortOrdr) {
        this.qstSortOrdr = qstSortOrdr;
    }

    public String getQstStr() {
        return qstStr;
    }

    public void setQstStr(String qstStr) {
        this.qstStr = qstStr;
    }

    public Long getQstTypKey() {
        return qstTypKey;
    }

    public void setQstTypKey(Long qstTypKey) {
        this.qstTypKey = qstTypKey;
    }

    public Long getQstStsKey() {
        return qstStsKey;
    }

    public void setQstStsKey(Long qstStsKey) {
        this.qstStsKey = qstStsKey;
    }

    public Long getQstCtgryKey() {
        return qstCtgryKey;
    }

    public void setQstCtgryKey(Long qstCtgryKey) {
        this.qstCtgryKey = qstCtgryKey;
    }

    public Long getQstnrSeq() {
        return qstnrSeq;
    }

    public void setQstnrSeq(Long qstnrSeq) {
        this.qstnrSeq = qstnrSeq;
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum;
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