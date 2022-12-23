package com.idev4.loans.domain;

import com.idev4.loans.ids.MwLoanAppPpalStngsId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MW_LOAN_APP_PPAL_STNGS")
@IdClass(MwLoanAppPpalStngsId.class)
public class MwLoanAppPpalStngs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LOAN_APP_PPAL_STNGS_SEQ")
    private Long loanAppPpalStngsSeq;

    @Id
    @Column(name = "PRD_SEQ")
    private Long prdSeq;

    @Column(name = "NUM_OF_INST")
    private Long numOfInst;

    @Column(name = "PYMT_FREQ")
    private Long pymtFreq;

    @Column(name = "NUM_OF_INST_SGRT")
    private Long numOfInstSgrt;

    @Column(name = "SGRT_INST")
    private String sgrtInst;

    @Column(name = "IRR_FLG")
    private Boolean irrFlg;

    @Column(name = "IRR_RATE")
    private Double irrRate;

    @Column(name = "RNDNG_SCL")
    private Long rndngScl;

    @Column(name = "RNDNG_ADJ_INST")
    private Double rndngAdjInst;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    public Long getLoanAppPpalStngsSeq() {
        return loanAppPpalStngsSeq;
    }

    public void setLoanAppPpalStngsSeq(Long loanAppPpalStngsSeq) {
        this.loanAppPpalStngsSeq = loanAppPpalStngsSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Long getNumOfInst() {
        return numOfInst;
    }

    public void setNumOfInst(Long numOfInst) {
        this.numOfInst = numOfInst;
    }

    public Long getPymtFreq() {
        return pymtFreq;
    }

    public void setPymtFreq(Long pymtFreq) {
        this.pymtFreq = pymtFreq;
    }

    public Long getNumOfInstSgrt() {
        return numOfInstSgrt;
    }

    public void setNumOfInstSgrt(Long numOfInstSgrt) {
        this.numOfInstSgrt = numOfInstSgrt;
    }

    public String getSgrtInst() {
        return sgrtInst;
    }

    public void setSgrtInst(String sgrtInst) {
        this.sgrtInst = sgrtInst;
    }

    public Boolean getIrrFlg() {
        return irrFlg;
    }

    public void setIrrFlg(Boolean irrFlg) {
        this.irrFlg = irrFlg;
    }

    public Double getIrrRate() {
        return irrRate;
    }

    public void setIrrRate(Double irrRate) {
        this.irrRate = irrRate;
    }

    public Long getRndngScl() {
        return rndngScl;
    }

    public void setRndngScl(Long rndngScl) {
        this.rndngScl = rndngScl;
    }

    public Double getRndngAdjInst() {
        return rndngAdjInst;
    }

    public void setRndngAdjInst(Double rndngAdjInst) {
        this.rndngAdjInst = rndngAdjInst;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

}
