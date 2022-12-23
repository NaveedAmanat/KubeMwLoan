package com.idev4.loans.domain;

import com.idev4.loans.ids.MwLoanAppChrgStngsId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MW_LOAN_APP_CHRG_STNGS")
@IdClass(MwLoanAppChrgStngsId.class)
public class MwLoanAppChrgStngs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LOAN_APP_CHRG_STNGS_SEQ")
    private Long loanAppChrgStngsSeq;

    @Id
    @Column(name = "PRD_SEQ")
    private Long prdSeq;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "PRD_CHRG_SEQ")
    private Long prdChrgSeq;

    @Column(name = "NUM_OF_INST_SGRT")
    private Long numOfInstSgrt;

    @Column(name = "SGRT_INST")
    private String sgrtInst;

    @Column(name = "RNDNG_FLG")
    private Boolean rndngFlg;

    @Column(name = "UPFRONT_FLG")
    private Boolean upfrontFlg;

    @Column(name = "TYP_SEQ")
    private Long typSeq;

    public Long getLoanAppChrgStngsSeq() {
        return loanAppChrgStngsSeq;
    }

    public void setLoanAppChrgStngsSeq(Long loanAppChrgStngsSeq) {
        this.loanAppChrgStngsSeq = loanAppChrgStngsSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Long getPrdChrgSeq() {
        return prdChrgSeq;
    }

    public void setPrdChrgSeq(Long prdChrgSeq) {
        this.prdChrgSeq = prdChrgSeq;
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

    public Boolean getRndngFlg() {
        return rndngFlg;
    }

    public void setRndngFlg(Boolean rndngFlg) {
        this.rndngFlg = rndngFlg;
    }

    public Boolean getUpfrontFlg() {
        return upfrontFlg;
    }

    public void setUpfrontFlg(Boolean upfrontFlg) {
        this.upfrontFlg = upfrontFlg;
    }

    public Long getTypSeq() {
        return typSeq;
    }

    public void setTypSeq(Long typSeq) {
        this.typSeq = typSeq;
    }

}
