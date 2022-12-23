package com.idev4.loans.domain;

import com.idev4.loans.ids.MwWrtOfClntId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwClnt.
 */
@Entity
@Table(name = "MW_WRT_OF_CLNT")
@IdClass(MwWrtOfClntId.class)
public class MwWrtOfClnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "WRT_OF_CLNT_SEQ")
    private Long wrtOfClntSeq;

    @Id
    @Column(name = "EFF_START_DT")
    private Instant effStartDt;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    @Column(name = "CLNT_SEQ")
    private Long clntSeq;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "CNIC_NUM")
    private Long cnicNum;

    @Column(name = "CLNT_NM")
    private String clntNm;

    @Column(name = "CYCL_NUM")
    private Long cyclNum;

    @Column(name = "DSBMT_DT")
    private Instant dsbmtDt;

    @Column(name = "DSBMT_PPAL_AMT")
    private Double dsbmtPpalAmt;

    @Column(name = "DSBMT_SRVC_CHRG_AMT")
    private Double dsmtSrvcChrgAmt;

    @Column(name = "OUTSD_PPAL_AMT")
    private Double outSdPpalAmt;

    @Column(name = "OUTSD_SRVC_CHRG_AMT")
    private Double outsdSrvcChrgAmt;

    @Column(name = "OD_PPAL_AMT")
    private Double odPpalAmt;

    @Column(name = "OD_SRVC_CHRG_AMT")
    private Double odSrvcChrgAmt;

    @Column(name = "OD_DYS")
    private Long odDys;

    @Column(name = "PRD_GRP_SEQ")
    private Long prdGrpSeq;

    @Column(name = "LOAN_APP_STS")
    private Long loanAppSts;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NO")
    private String phoneNo;

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

    public Long getWrtOfClntSeq() {
        return wrtOfClntSeq;
    }

    public void setWrtOfClntSeq(Long wrtOfClntSeq) {
        this.wrtOfClntSeq = wrtOfClntSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Long getCnicNum() {
        return cnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
    }

    public String getClntNm() {
        return clntNm;
    }

    public void setClntNm(String clntNm) {
        this.clntNm = clntNm;
    }

    public Long getCyclNum() {
        return cyclNum;
    }

    public void setCyclNum(Long cyclNum) {
        this.cyclNum = cyclNum;
    }

    public Instant getDsbmtDt() {
        return dsbmtDt;
    }

    public void setDsbmtDt(Instant dsbmtDt) {
        this.dsbmtDt = dsbmtDt;
    }

    public Double getDsbmtPpalAmt() {
        return dsbmtPpalAmt;
    }

    public void setDsbmtPpalAmt(Double dsbmtPpalAmt) {
        this.dsbmtPpalAmt = dsbmtPpalAmt;
    }

    public Double getDsmtSrvcChrgAmt() {
        return dsmtSrvcChrgAmt;
    }

    public void setDsmtSrvcChrgAmt(Double dsmtSrvcChrgAmt) {
        this.dsmtSrvcChrgAmt = dsmtSrvcChrgAmt;
    }

    public Double getOutSdPpalAmt() {
        return outSdPpalAmt;
    }

    public void setOutSdPpalAmt(Double outSdPpalAmt) {
        this.outSdPpalAmt = outSdPpalAmt;
    }

    public Double getOutsdSrvcChrgAmt() {
        return outsdSrvcChrgAmt;
    }

    public void setOutsdSrvcChrgAmt(Double outsdSrvcChrgAmt) {
        this.outsdSrvcChrgAmt = outsdSrvcChrgAmt;
    }

    public Double getOdPpalAmt() {
        return odPpalAmt;
    }

    public void setOdPpalAmt(Double odPpalAmt) {
        this.odPpalAmt = odPpalAmt;
    }

    public Double getOdSrvcChrgAmt() {
        return odSrvcChrgAmt;
    }

    public void setOdSrvcChrgAmt(Double odSrvcChrgAmt) {
        this.odSrvcChrgAmt = odSrvcChrgAmt;
    }

    public Long getOdDys() {
        return odDys;
    }

    public void setOdDys(Long odDys) {
        this.odDys = odDys;
    }

    public Long getPrdGrpSeq() {
        return prdGrpSeq;
    }

    public void setPrdGrpSeq(Long prdGrpSeq) {
        this.prdGrpSeq = prdGrpSeq;
    }

    public Long getLoanAppSts() {
        return loanAppSts;
    }

    public void setLoanAppSts(Long loanAppSts) {
        this.loanAppSts = loanAppSts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

}
