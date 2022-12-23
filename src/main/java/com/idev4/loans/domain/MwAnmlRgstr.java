package com.idev4.loans.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwAnswr.
 */
@Entity
@Table(name = "MW_ANML_RGSTR")
// @IdClass ( MwAnmlRgstrId.class )
public class MwAnmlRgstr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ANML_RGSTR_SEQ")
    private Long anmlRgstrSeq;

    @Column(name = "EFF_START_DT")
    private Instant effStartDt;

    @Column(name = "LOAN_APP_SEQ")
    private Long loanAppSeq;

    @Column(name = "RGSTR_CD")
    private String rgstrCd;

    @Column(name = "TAG_NUM")
    private String tagNm;

    @Column(name = "ANML_KND")
    private Long anmlKnd;

    @Column(name = "ANML_TYP")
    private Long anmlTyp;

    @Column(name = "ANML_CLR")
    private Long anmlClr;

    @Column(name = "ANML_BRD")
    private String anmlBrd;

    @Column(name = "PRCH_DT")
    private Instant prchDt;

    @Column(name = "AGE_YR")
    private Long ageYr;

    @Column(name = "AGE_MNTH")
    private Long ageMnth;

    @Column(name = "PRCH_AMT")
    private Long prchAmt;

    @Column(name = "PIC_DT")
    private Instant picDt;

    @Column(name = "ANML_PIC")
    @Lob
    private String anmlPic;

    @Column(name = "TAG_PIC")
    @Lob
    private String tagPic;

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

    @Column(name = "ANML_STS")
    private Long anmlSts;

    public Long getAnmlSts() {
        return anmlSts;
    }

    public void setAnmlSts(Long anmlSts) {
        this.anmlSts = anmlSts;
    }

    public Long getAnmlRgstrSeq() {
        return anmlRgstrSeq;
    }

    public void setAnmlRgstrSeq(Long anmlRgstrSeq) {
        this.anmlRgstrSeq = anmlRgstrSeq;
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

    public String getRgstrCd() {
        return rgstrCd;
    }

    public void setRgstrCd(String rgstrCd) {
        this.rgstrCd = rgstrCd;
    }

    public String getTagNm() {
        return tagNm;
    }

    public void setTagNm(String tagNm) {
        this.tagNm = tagNm;
    }

    public Long getAnmlKnd() {
        return anmlKnd;
    }

    public void setAnmlKnd(Long anmlKnd) {
        this.anmlKnd = anmlKnd;
    }

    public Long getAnmlTyp() {
        return anmlTyp;
    }

    public void setAnmlTyp(Long anmlTyp) {
        this.anmlTyp = anmlTyp;
    }

    public Long getAnmlClr() {
        return anmlClr;
    }

    public void setAnmlClr(Long anmlClr) {
        this.anmlClr = anmlClr;
    }

    public String getAnmlBrd() {
        return anmlBrd;
    }

    public void setAnmlBrd(String anmlBrd) {
        this.anmlBrd = anmlBrd;
    }

    public Instant getPrchDt() {
        return prchDt;
    }

    public void setPrchDt(Instant prchDt) {
        this.prchDt = prchDt;
    }

    public Long getAgeYr() {
        return ageYr;
    }

    public void setAgeYr(Long ageYr) {
        this.ageYr = ageYr;
    }

    public Long getAgeMnth() {
        return ageMnth;
    }

    public void setAgeMnth(Long ageMnth) {
        this.ageMnth = ageMnth;
    }

    public Long getPrchAmt() {
        return prchAmt;
    }

    public void setPrchAmt(Long prchAmt) {
        this.prchAmt = prchAmt;
    }

    public Instant getPicDt() {
        return picDt;
    }

    public void setPicDt(Instant picDt) {
        this.picDt = picDt;
    }

    public String getAnmlPic() {
        return anmlPic;
    }

    public void setAnmlPic(String anmlPic) {
        this.anmlPic = anmlPic;
    }

    public String getTagPic() {
        return tagPic;
    }

    public void setTagPic(String tagPic) {
        this.tagPic = tagPic;
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
