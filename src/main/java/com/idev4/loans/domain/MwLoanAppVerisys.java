package com.idev4.loans.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A MwLoanAppDoc.
 */
@Entity
@Table(name = "mw_cnic_verisys")
public class MwLoanAppVerisys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ver_seq")
    public Long verSeq;

    @Column(name = "loan_app_seq")
    public Long loanAppSeq;

    @Column(name = "cnic_category")
    public String cnicCat;

    @Column(name = "STATUS")
    public String status;

    @Column(name = "doc")
    @Lob
    public byte[] doc;

    @Column(name = "VREMARKS")
    public String vremarks;

    @Column(name = "kashf_status")
    public String kStatus;

    public String getkStatus() {
        return kStatus;
    }

    public void setkStatus(String kStatus) {
        this.kStatus = kStatus;
    }

    public String getVremarks() {
        return vremarks;
    }

    public void setVremarks(String vremarks) {
        this.vremarks = vremarks;
    }

    public Long getVerSeq() {
        return verSeq;
    }

    public void setVerSeq(Long verSeq) {
        this.verSeq = verSeq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCnicCat() {
        return cnicCat;
    }

    public void setCnicCat(String cnicCat) {
        this.cnicCat = cnicCat;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public byte[] getDoc() {
        return doc;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

}
