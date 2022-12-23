package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "MW_AML_MTCHD_CLNT")
public class MwAmlMtchdClnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AML_MTCHD_CLNT_SEQ")
    private Long amlMtchdClntSeq;

    @Column(name = "PORT_SEQ")
    private Long portSeq;

    @Column(name = "FRST_NM")
    private String frstNm;

    @Column(name = "MID_NM")
    private String midNm;

    @Column(name = "LAST_NM")
    private String lastNm;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "CNIC_NUM")
    private Long cnicNum;

    @Column(name = "DOB")
    private Instant dob;

    @Column(name = "AML_SRC_AGY")
    private Long amlSrcAgy;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "CLNT_SRC_FLG")
    private Integer clntSrcFlg;

    @Column(name = "STP_FLG")
    private Boolean stpFlg;

    public Boolean getStpFlg() {
        return stpFlg;
    }

    public void setStpFlg(Boolean stpFlg) {
        this.stpFlg = stpFlg;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Integer getClntSrcFlg() {
        return clntSrcFlg;
    }

    public void setClntSrcFlg(Integer clntSrcFlg) {
        this.clntSrcFlg = clntSrcFlg;
    }

    public Long getAmlMtchdClntSeq() {
        return amlMtchdClntSeq;
    }

    public void setAmlMtchdClntSeq(Long amlMtchdClntSeq) {
        this.amlMtchdClntSeq = amlMtchdClntSeq;
    }

    public Long getPortSeq() {
        return portSeq;
    }

    public void setPortSeq(Long portSeq) {
        this.portSeq = portSeq;
    }

    public String getFrstNm() {
        return frstNm;
    }

    public void setFrstNm(String frstNm) {
        this.frstNm = frstNm;
    }

    public String getMidNm() {
        return midNm;
    }

    public void setMidNm(String midNm) {
        this.midNm = midNm;
    }

    public String getLastNm() {
        return lastNm;
    }

    public void setLastNm(String lastNm) {
        this.lastNm = lastNm;
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

    public Long getCnicNum() {
        return cnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public Long getAmlSrcAgy() {
        return amlSrcAgy;
    }

    public void setAmlSrcAgy(Long amlSrcAgy) {
        this.amlSrcAgy = amlSrcAgy;
    }

}
