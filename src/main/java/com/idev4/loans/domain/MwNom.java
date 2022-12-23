package com.idev4.loans.domain;


import com.idev4.loans.ids.MwNomId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwNom.
 */
@Entity
@Table(name = "mw_nom")
@IdClass(MwNomId.class)
public class MwNom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "nom_seq")
    private Long nomSeq;

    @Column(name = "cnic_num")
    private Long cnicNum;

    @Column(name = "cnic_expry_dt")
    private Instant cnicExpryDt;

    @Column(name = "frst_nm")
    private String frstNm;

    @Column(name = "last_nm")
    private String lastNm;

    @Column(name = "dob")
    private Instant dob;

    @Column(name = "rel_wth_clnt_key")
    private Long relWithClntKey;

    @Column(name = "ph_num")
    private String phNum;

    @Column(name = "Co_bwr_SAN_flg")
    private Boolean coBwrSanFlg;

    @Column(name = "gndr_Key")
    private Long gndrKey;

    @Column(name = "occ_Key")
    private Long occKey;

    @Column(name = "mrtl_sts_Key")
    private Long mrtlStsKey;

    @Column(name = "nom_id")
    private String nomId;

    @Column(name = "loan_App_SEQ")
    private Long loanAppSeq;

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

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    public Long getNomSeq() {
        return nomSeq;
    }

    public void setNomSeq(Long nomSeq) {
        this.nomSeq = nomSeq;
    }

    public Long getCnicNum() {
        return cnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
    }

    public Instant getCnicExpryDt() {
        return cnicExpryDt;
    }

    public void setCnicExpryDt(Instant cnicExpryDt) {
        this.cnicExpryDt = cnicExpryDt;
    }

    public String getFrstNm() {
        return frstNm;
    }

    public void setFrstNm(String frstNm) {
        this.frstNm = frstNm;
    }

    public String getLastNm() {
        return lastNm;
    }

    public void setLastNm(String lastNm) {
        this.lastNm = lastNm;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public Long getRelWithClntKey() {
        return relWithClntKey;
    }

    public void setRelWithClntKey(Long relWithClntKey) {
        this.relWithClntKey = relWithClntKey;
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum;
    }

    public Boolean getCoBwrSanFlg() {
        return coBwrSanFlg;
    }

    public void setCoBwrSanFlg(Boolean coBwrSanFlg) {
        this.coBwrSanFlg = coBwrSanFlg;
    }

    public Long getGndrKey() {
        return gndrKey;
    }

    public void setGndrKey(Long gndrKey) {
        this.gndrKey = gndrKey;
    }

    public Long getOccKey() {
        return occKey;
    }

    public void setOccKey(Long occKey) {
        this.occKey = occKey;
    }

    public Long getMrtlStsKey() {
        return mrtlStsKey;
    }

    public void setMrtlStsKey(Long mrtlStsKey) {
        this.mrtlStsKey = mrtlStsKey;
    }

    public String getNomId() {
        return nomId;
    }

    public void setNomId(String nomId) {
        this.nomId = nomId;
    }

    public Long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(Long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
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
