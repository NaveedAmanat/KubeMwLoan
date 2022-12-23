package com.idev4.loans.domain;

import com.idev4.loans.ids.MwClntTagListId;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "MW_CLNT_TAG_LIST")
@IdClass(MwClntTagListId.class)
public class MwClntTagList {

    @Id
    @Column(name = "CLNT_TAG_LIST_SEQ")
    private Long clntTagListSeq;

    @Column(name = "CNIC_NUM")
    private Long cnicNum;

    @Column(name = "TAGS_SEQ")
    private Long tagsSeq;

    @Column(name = "TAG_FROM_DT")
    private Instant tagFromDt;

    @Column(name = "TAG_TO_DT")
    private Instant tagToDt;

    @Column(name = "RMKS")
    private String rmks;

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

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    // Added by Zohaib Asim - Dated 14-09-2021 - Nacta-ClntRel-CNIC
    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;


    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Long getClntTagListSeq() {
        return clntTagListSeq;
    }

    public void setClntTagListSeq(Long clntTagListSeq) {
        this.clntTagListSeq = clntTagListSeq;
    }

    public Long getCnicNum() {
        return cnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
    }

    public Long getTagsSeq() {
        return tagsSeq;
    }

    public void setTagsSeq(Long tagsSeq) {
        this.tagsSeq = tagsSeq;
    }

    public Instant getTagFromDt() {
        return tagFromDt;
    }

    public void setTagFromDt(Instant tagFromDt) {
        this.tagFromDt = tagFromDt;
    }

    public Instant getTagToDt() {
        return tagToDt;
    }

    public void setTagToDt(Instant tagToDt) {
        this.tagToDt = tagToDt;
    }

    public String getRmks() {
        return rmks;
    }

    public void setRmks(String rmks) {
        this.rmks = rmks;
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
