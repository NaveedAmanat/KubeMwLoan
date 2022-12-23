package com.idev4.loans.domain;


import com.idev4.loans.ids.MwClntHlthInsrCardId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwHlthInsrPlan.
 */
@Entity
@Table(name = "mw_clnt_hlth_insr_card")
@IdClass(MwClntHlthInsrCardId.class)
public class MwClntHlthInsrCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CLNT_HLTH_INSR_CARD_SEQ")
    private Long clntHlthInsrCardSeq;

    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "CARD_NUM")
    private String cardNum;

    @Column(name = "CARD_EXPIRY_DT")
    private Instant cardExpiryDt;

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

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getClntHlthInsrCardSeq() {
        return clntHlthInsrCardSeq;
    }

    public void setClntHlthInsrCardSeq(Long clntHlthInsrCardSeq) {
        this.clntHlthInsrCardSeq = clntHlthInsrCardSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Instant getCardExpiryDt() {
        return cardExpiryDt;
    }

    public void setCardExpiryDt(Instant cardExpiryDt) {
        this.cardExpiryDt = cardExpiryDt;
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
