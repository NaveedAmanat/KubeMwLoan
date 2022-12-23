package com.idev4.loans.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "MW_LOAN_APP_CRDT_SUMRY")
public class MwLoanAppCrdtSumry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAN_DOC_SEQ")
    @SequenceGenerator(sequenceName = "LOAN_DOC_SEQ", allocationSize = 1, name = "LOAN_DOC_SEQ")
    @Column(name = "LOAN_APP_CS_SEQ")
    private Long loanAppCsSeq;

    @Column(name = "LOAN_APP_DOC_SEQ")
    private Long loanAppDocSeq;

    @Column(name = "CS_CTGRY")
    private String csCtgry;

    @Column(name = "CS_NM")
    private String csNm;

    @Column(name = "CS_CNIC_NUM")
    private Long csCnicNum;

    @Column(name = "LOAN_COUNT")
    private String loanCount;

    @Column(name = "LOAN_LIMIT")
    private String loanLimit;

    @Column(name = "LOAN_OST")
    private String loanOst;

    @Column(name = "LOAN_LESS_10K")
    private String loanLess10K;

    @Column(name = "LOAN_ABOVE_10K")
    private String loanAbove10K;

    @Column(name = "CURRENT_30PLUS")
    private String current30Plus;

    @Column(name = "CURRENT_60PLUS")
    private String current60Plus;

    @Column(name = "CURRENT_90PLUS")
    private String current90Plus;

    @Column(name = "CLOSE_WITHIN_MATURITY")
    private String closeWithinMaturity;

    @Column(name = "CLOSE_AFTER_MATURITY")
    private String closeAfterMaturity;

    @Column(name = "DEFAULT_COUNT")
    private String defaultCount;

    @Column(name = "DEFAULT_OST")
    private String defaultOst;

    @Column(name = "MONTH24_30PLUS")
    private String month2430Plus;

    @Column(name = "MONTH24_60PLUS")
    private String month2460Plus;

    @Column(name = "MONTH24_90PLUS")
    private String month2490Plus;

    @Column(name = "ENQUIRY_COUNT")
    private String enquiryCount;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    public MwLoanAppCrdtSumry() {
    }

    public Long getLoanAppCsSeq() {
        return this.loanAppCsSeq;
    }

    public void setLoanAppCsSeq(Long loanAppCsSeq) {
        this.loanAppCsSeq = loanAppCsSeq;
    }

    public Long getLoanAppDocSeq() {
        return this.loanAppDocSeq;
    }

    public void setLoanAppDocSeq(Long loanAppDocSeq) {
        this.loanAppDocSeq = loanAppDocSeq;
    }

    public String getCsCtgry() {
        return this.csCtgry;
    }

    public void setCsCtgry(String csCtgry) {
        this.csCtgry = csCtgry;
    }

    public String getCsNm() {
        return this.csNm;
    }

    public void setCsNm(String csNm) {
        this.csNm = csNm;
    }

    public Long getCsCnicNum() {
        return this.csCnicNum;
    }

    public void setCsCnicNum(Long csCnicNum) {
        this.csCnicNum = csCnicNum;
    }

    public String getLoanCount() {
        return this.loanCount;
    }

    public void setLoanCount(String loanCount) {
        this.loanCount = loanCount;
    }

    public String getLoanLimit() {
        return this.loanLimit;
    }

    public void setLoanLimit(String loanLimit) {
        this.loanLimit = loanLimit;
    }

    public String getLoanOst() {
        return this.loanOst;
    }

    public void setLoanOst(String loanOst) {
        this.loanOst = loanOst;
    }

    public String getLoanLess10K() {
        return this.loanLess10K;
    }

    public void setLoanLess10K(String loanLess10K) {
        this.loanLess10K = loanLess10K;
    }

    public String getLoanAbove10K() {
        return this.loanAbove10K;
    }

    public void setLoanAbove10K(String loanAbove10K) {
        this.loanAbove10K = loanAbove10K;
    }

    public String getCurrent30Plus() {
        return this.current30Plus;
    }

    public void setCurrent30Plus(String current30Plus) {
        this.current30Plus = current30Plus;
    }

    public String getCurrent60Plus() {
        return this.current60Plus;
    }

    public void setCurrent60Plus(String current60Plus) {
        this.current60Plus = current60Plus;
    }

    public String getCurrent90Plus() {
        return this.current90Plus;
    }

    public void setCurrent90Plus(String current90Plus) {
        this.current90Plus = current90Plus;
    }

    public String getCloseWithinMaturity() {
        return this.closeWithinMaturity;
    }

    public void setCloseWithinMaturity(String closeWithinMaturity) {
        this.closeWithinMaturity = closeWithinMaturity;
    }

    public String getCloseAfterMaturity() {
        return this.closeAfterMaturity;
    }

    public void setCloseAfterMaturity(String closeAfterMaturity) {
        this.closeAfterMaturity = closeAfterMaturity;
    }

    public String getDefaultCount() {
        return this.defaultCount;
    }

    public void setDefaultCount(String defaultCount) {
        this.defaultCount = defaultCount;
    }

    public String getDefaultOst() {
        return this.defaultOst;
    }

    public void setDefaultOst(String defaultOst) {
        this.defaultOst = defaultOst;
    }

    public String getMonth2430Plus() {
        return this.month2430Plus;
    }

    public void setMonth2430Plus(String month2430Plus) {
        this.month2430Plus = month2430Plus;
    }

    public String getMonth2460Plus() {
        return this.month2460Plus;
    }

    public void setMonth2460Plus(String month2460Plus) {
        this.month2460Plus = month2460Plus;
    }

    public String getMonth2490Plus() {
        return this.month2490Plus;
    }

    public void setMonth2490Plus(String month2490Plus) {
        this.month2490Plus = month2490Plus;
    }

    public String getEnquiryCount() {
        return this.enquiryCount;
    }

    public void setEnquiryCount(String enquiryCount) {
        this.enquiryCount = enquiryCount;
    }

    public Boolean getCrntRecFlg() {
        return this.crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Instant getCrtdDt() {
        return this.crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getLastUpdDt() {
        return this.lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public String getLastUpdBy() {
        return this.lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }
}
