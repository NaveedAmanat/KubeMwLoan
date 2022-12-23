package com.idev4.loans.domain;

import javax.persistence.*;

@Entity
@Table(name = "MW_MFCIB_CRED")
public class MwMfcibCred {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRED_ID")
    private Long credId;

    @Column(name = "CRED_TYP")
    private String credTyp;

    @Column(name = "COMPANY_NM")
    private String companyNm;

    @Column(name = "URL")
    private String url;

    @Column(name = "AUTH_KEY")
    private String authKey;

    @Column(name = "AUTH_USER")
    private String authUser;

    @Column(name = "AUTH_PASS")
    private String authPass;

    @Column(name = "AUTH_TKN")
    private String authTkn;

    @Column(name = "AUTH_TKN_EXPRY")
    private java.util.Date authTknExpry;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    @Column(name = "DEL_FLG")
    private Boolean delFlg;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private java.util.Date crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private java.util.Date lastUpdDt;

    public Long getCredId() {
        return this.credId;
    }

    public void setCredId(Long credId) {
        this.credId = credId;
    }

    public String getCredTyp() {
        return this.credTyp;
    }

    public void setCredTyp(String credTyp) {
        this.credTyp = credTyp;
    }

    public String getCompanyNm() {
        return this.companyNm;
    }

    public void setCompanyNm(String companyNm) {
        this.companyNm = companyNm;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getAuthUser() {
        return this.authUser;
    }

    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    public String getAuthPass() {
        return this.authPass;
    }

    public void setAuthPass(String authPass) {
        this.authPass = authPass;
    }

    public String getAuthTkn() {
        return this.authTkn;
    }

    public void setAuthTkn(String authTkn) {
        this.authTkn = authTkn;
    }

    public java.util.Date getAuthTknExpry() {
        return this.authTknExpry;
    }

    public void setAuthTknExpry(java.util.Date authTknExpry) {
        this.authTknExpry = authTknExpry;
    }

    public Boolean getCrntRecFlg() {
        return this.crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Boolean getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public java.util.Date getCrtdDt() {
        return this.crtdDt;
    }

    public void setCrtdDt(java.util.Date crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return this.lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public java.util.Date getLastUpdDt() {
        return this.lastUpdDt;
    }

    public void setLastUpdDt(java.util.Date lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }
}
