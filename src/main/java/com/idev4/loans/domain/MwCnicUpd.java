package com.idev4.loans.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwAddr.
 */
@Entity
@Table(name = "mw_cnic_upd")
public class MwCnicUpd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CNIC_UPD_SEQ")
    private Long cnicUpdSeq;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "CNIC_EXPRY_DT")
    private Instant cnicExpryDt;

    @Column(name = "CNIC_FRNT_PIC")
    @Lob
    private String cnicFrntPic;

    @Column(name = "CNIC_BCK_PIC")
    @Lob
    private String cnicBckPic;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpdDt;

    @Column(name = "UPD_STS")
    private Long updSts;

    @Column(name = "CMNT")
    private String cmnt;

    @Column(name = "cnic_num")
    private Long CnicNum;

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }

    public Long getUpdSts() {
        return updSts;
    }

    public void setUpdSts(Long updSts) {
        this.updSts = updSts;
    }

    public Long getCnicUpdSeq() {
        return cnicUpdSeq;
    }

    public void setCnicUpdSeq(Long cnicUpdSeq) {
        this.cnicUpdSeq = cnicUpdSeq;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Instant getCnicExpryDt() {
        return cnicExpryDt;
    }

    public void setCnicExpryDt(Instant cnicExpryDt) {
        this.cnicExpryDt = cnicExpryDt;
    }

    public String getCnicFrntPic() {
        return cnicFrntPic;
    }

    public void setCnicFrntPic(String cnicFrntPic) {
        this.cnicFrntPic = cnicFrntPic;
    }

    public String getCnicBckPic() {
        return cnicBckPic;
    }

    public void setCnicBckPic(String cnicBckPic) {
        this.cnicBckPic = cnicBckPic;
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

    public Long getCnicNum() {
        return CnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        CnicNum = cnicNum;
    }
}
