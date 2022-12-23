package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwClient.
 */
@Entity
@Table(name = "mw_clnt")
public class MwClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "clnt_seq")
    private Long clntSeq;

    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "clnt_id")
    private String clntId;

    @Column(name = "cnic_num")
    private Long cnicNum;

    @Column(name = "cnic_expry_dt")
    private Instant cnicExpryDt;

    @Column(name = "cnic_issue_dt")
    private Instant cnicIssueDt;

    @Column(name = "frst_nm")
    private String frstNm;

    @Column(name = "last_nm")
    private String lastNm;

    @Column(name = "nick_nm")
    private String nickNm;

    @Column(name = "dob")
    private Instant dob;

    @Column(name = "gndr_key")
    private Long gndrKey;

    @Column(name = "mrtl_sts_key")
    private Long mrtlStsKey;

    @Column(name = "edu_lvl_key")
    private Long eduLvlKey;

    @Column(name = "natr_of_dis_key")
    private Long natrOfDisKey;

    @Column(name = "occ_key")
    private Long occKey;

    @Column(name = "hse_hld_memb")
    private Long hseHldMemb;

    @Column(name = "num_of_dpnd")
    private Long numOfDpnd;

    @Column(name = "num_of_chldrn")
    private Long numOfChldrn;

    @Column(name = "dis_flg")
    private Boolean disFlg;

    @Column(name = "slf_pdc_flg")
    private Boolean slfPdcFlg;

    @Column(name = "ph_num")
    private String phNum;

    @Column(name = "mthr_madn_nm")
    private String mthrMadnNm;

    @Column(name = "erng_memb")
    private Long erngMemb;

    @Column(name = "crnt_addr_perm_flg")
    private Boolean crntAddrPermFlg;

    @Column(name = "num_of_erng_memb")
    private Long numOfErngMemb;

    @Column(name = "tot_incm_of_erng_memb")
    private Long totIncmOfErngMemb;

    @Column(name = "nom_dtl_available_flg")
    private Boolean nomDtlAvailableFlg;

    @Column(name = "co_bwr_san_flg")
    private Boolean coBwrSanFlg;

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

    @Column(name = "port_key")
    private Long portKey;

    @Column(name = "clnt_sts_key")
    private Long clntStsKey;

    @Column(name = "res_typ_key")
    private Long resTypKey;

    @Column(name = "fthr_frst_nm")
    private String fthrFrstNm;

    @Column(name = "fthr_last_nm")
    private String fthrLastNm;

    @Column(name = "spz_frst_nm")
    private String spzFrstNm;

    @Column(name = "spz_last_nm")
    private String spzLastNm;

    @Column(name = "yrs_res")
    private Long yrsRes;

    @Column(name = "mnths_res")
    private Long mnthsRes;

    @Column(name = "biz_dtl")
    private String bizDtl;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    @Column(name = "SM_HSLD_FLG")
    private Boolean smHsldFlg;

    @Column(name = "CLNT_STS_DT")
    private Instant clntStsDt;

    @Column(name = "PFT_FLG")
    private Boolean pftFlg;

    @Column(name = "MEMBRSHP_DT")
    private Instant membrshpDt;

    @Column(name = "REF_CD_LEAD_TYP_SEQ")
    private Long refCdLeadTypSeq;

    @Column(name = "WHATSAPP_NUM")
    private String WhatsappNum;

    @Column(name = "REF_CD_PH_TYP_SEQ")
    private Long refCdPhTypSeq;

    @Column(name = "SIM_RGSTR_BY_CLNT")
    private Long simRgstrByClnt;

    @Column(name = "REF_CD_LOAN_USR")
    private Long refCdLoanUsr;

    public Instant getCnicIssueDt() {
        return cnicIssueDt;
    }

    public void setCnicIssueDt(Instant cnicIssueDt) {
        this.cnicIssueDt = cnicIssueDt;
    }

    public Boolean isPftFlg() {
        return pftFlg;
    }

    public Boolean getSmHsldFlg() {
        return smHsldFlg;
    }

    public void setSmHsldFlg(Boolean smHsldFlg) {
        this.smHsldFlg = smHsldFlg;
    }

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Instant getClntStsDt() {
        return clntStsDt;
    }

    public void setClntStsDt(Instant clntStsDt) {
        this.clntStsDt = clntStsDt;
    }

    public String getBizDtl() {
        return bizDtl;
    }

    public void setBizDtl(String bizDtl) {
        this.bizDtl = bizDtl;
    }

    public Boolean getDisFlg() {
        return disFlg;
    }

    public void setDisFlg(Boolean disFlg) {
        this.disFlg = disFlg;
    }

    public Boolean getSlfPdcFlg() {
        return slfPdcFlg;
    }

    public void setSlfPdcFlg(Boolean slfPdcFlg) {
        this.slfPdcFlg = slfPdcFlg;
    }

    public Boolean getCrntAddrPermFlg() {
        return crntAddrPermFlg;
    }

    public void setCrntAddrPermFlg(Boolean crntAddrPermFlg) {
        this.crntAddrPermFlg = crntAddrPermFlg;
    }

    public Boolean getNomDtlAvailableFlg() {
        return nomDtlAvailableFlg;
    }

    public void setNomDtlAvailableFlg(Boolean nomDtlAvailableFlg) {
        this.nomDtlAvailableFlg = nomDtlAvailableFlg;
    }

    public Boolean getCoBwrSanFlg() {
        return coBwrSanFlg;
    }

    public void setCoBwrSanFlg(Boolean coBwrSanFlg) {
        this.coBwrSanFlg = coBwrSanFlg;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public MwClient clntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
        return this;
    }

    public String getClntId() {
        return clntId;
    }

    public void setClntId(String clntId) {
        this.clntId = clntId;
    }

    public MwClient clntId(String clntId) {
        this.clntId = clntId;
        return this;
    }

    public Long getCnicNum() {
        return cnicNum;
    }

    public void setCnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
    }

    public MwClient cnicNum(Long cnicNum) {
        this.cnicNum = cnicNum;
        return this;
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

    public MwClient frstNm(String frstNm) {
        this.frstNm = frstNm;
        return this;
    }

    public String getLastNm() {
        return lastNm;
    }

    public void setLastNm(String lastNm) {
        this.lastNm = lastNm;
    }

    public MwClient lastNm(String lastNm) {
        this.lastNm = lastNm;
        return this;
    }

    public String getNickNm() {
        return nickNm;
    }

    public void setNickNm(String nickNm) {
        this.nickNm = nickNm;
    }

    public MwClient nickNm(String nickNm) {
        this.nickNm = nickNm;
        return this;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public MwClient dob(Instant dob) {
        this.dob = dob;
        return this;
    }

    public Long getGndrKey() {
        return gndrKey;
    }

    public void setGndrKey(Long gndrKey) {
        this.gndrKey = gndrKey;
    }

    public MwClient gndrKey(Long gndrKey) {
        this.gndrKey = gndrKey;
        return this;
    }

    public Long getMrtlStsKey() {
        return mrtlStsKey;
    }

    public void setMrtlStsKey(Long mrtlStsKey) {
        this.mrtlStsKey = mrtlStsKey;
    }

    public MwClient mrtlStsKey(Long mrtlStsKey) {
        this.mrtlStsKey = mrtlStsKey;
        return this;
    }

    public Long getEduLvlKey() {
        return eduLvlKey;
    }

    public void setEduLvlKey(Long eduLvlKey) {
        this.eduLvlKey = eduLvlKey;
    }

    public MwClient eduLvlKey(Long eduLvlKey) {
        this.eduLvlKey = eduLvlKey;
        return this;
    }

    public Long getNatrOfDisKey() {
        return natrOfDisKey;
    }

    public void setNatrOfDisKey(Long natrOfDisKey) {
        this.natrOfDisKey = natrOfDisKey;
    }

    public MwClient natrOfDisKey(Long natrOfDisKey) {
        this.natrOfDisKey = natrOfDisKey;
        return this;
    }

    public Long getOccKey() {
        return occKey;
    }

    public void setOccKey(Long occKey) {
        this.occKey = occKey;
    }

    public MwClient occKey(Long occKey) {
        this.occKey = occKey;
        return this;
    }

    public Long getHseHldMemb() {
        return hseHldMemb;
    }

    public void setHseHldMemb(Long hseHldMemb) {
        this.hseHldMemb = hseHldMemb;
    }

    public MwClient hseHldMemb(Long hseHldMemb) {
        this.hseHldMemb = hseHldMemb;
        return this;
    }

    public Long getNumOfDpnd() {
        return numOfDpnd;
    }

    public void setNumOfDpnd(Long numOfDpnd) {
        this.numOfDpnd = numOfDpnd;
    }

    public MwClient numOfDpnd(Long numOfDpnd) {
        this.numOfDpnd = numOfDpnd;
        return this;
    }

    public Long getNumOfChldrn() {
        return numOfChldrn;
    }

    public void setNumOfChldrn(Long numOfChldrn) {
        this.numOfChldrn = numOfChldrn;
    }

    public MwClient numOfChldrn(Long numOfChldrn) {
        this.numOfChldrn = numOfChldrn;
        return this;
    }

    public Boolean isDisFlg() {
        return disFlg;
    }

    public MwClient disFlg(Boolean disFlg) {
        this.disFlg = disFlg;
        return this;
    }

    public Boolean isSlfPdcFlg() {
        return slfPdcFlg;
    }

    public MwClient slfPdcFlg(Boolean slfPdcFlg) {
        this.slfPdcFlg = slfPdcFlg;
        return this;
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum;
    }

    public MwClient phNum(String phNum) {
        this.phNum = phNum;
        return this;
    }

    public String getMthrMadnNm() {
        return mthrMadnNm;
    }

    public void setMthrMadnNm(String mthrMadnNm) {
        this.mthrMadnNm = mthrMadnNm;
    }

    public MwClient mthrMadnNm(String mthrMadnNm) {
        this.mthrMadnNm = mthrMadnNm;
        return this;
    }

    public Long getErngMemb() {
        return erngMemb;
    }

    public void setErngMemb(Long erngMemb) {
        this.erngMemb = erngMemb;
    }

    public MwClient erngMemb(Long erngMemb) {
        this.erngMemb = erngMemb;
        return this;
    }

    public Boolean isCrntAddrPermFlg() {
        return crntAddrPermFlg;
    }

    public MwClient crntAddrPermFlg(Boolean crntAddrPermFlg) {
        this.crntAddrPermFlg = crntAddrPermFlg;
        return this;
    }

    public Long getNumOfErngMemb() {
        return numOfErngMemb;
    }

    public void setNumOfErngMemb(Long numOfErngMemb) {
        this.numOfErngMemb = numOfErngMemb;
    }

    public MwClient numOfErngMemb(Long numOfErngMemb) {
        this.numOfErngMemb = numOfErngMemb;
        return this;
    }

    public Long getTotIncmOfErngMemb() {
        return totIncmOfErngMemb;
    }

    public void setTotIncmOfErngMemb(Long totIncmOfErngMemb) {
        this.totIncmOfErngMemb = totIncmOfErngMemb;
    }

    public MwClient totIncmOfErngMemb(Long totIncmOfErngMemb) {
        this.totIncmOfErngMemb = totIncmOfErngMemb;
        return this;
    }

    public Boolean isNomDtlAvailableFlg() {
        return nomDtlAvailableFlg;
    }

    public MwClient nomDtlAvailableFlg(Boolean nomDtlAvailableFlg) {
        this.nomDtlAvailableFlg = nomDtlAvailableFlg;
        return this;
    }

    public Boolean isCoBwrSanFlg() {
        return coBwrSanFlg;
    }

    public MwClient coBwrSanFlg(Boolean coBwrSanFlg) {
        this.coBwrSanFlg = coBwrSanFlg;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwClient crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwClient crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwClient lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwClient lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwClient delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwClient effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwClient effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwClient crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public Long getPortKey() {
        return portKey;
    }

    public void setPortKey(Long portKey) {
        this.portKey = portKey;
    }

    public MwClient portKey(Long portKey) {
        this.portKey = portKey;
        return this;
    }

    public Long getClntStsKey() {
        return clntStsKey;
    }

    public void setClntStsKey(Long clntStsKey) {
        this.clntStsKey = clntStsKey;
    }

    public MwClient clntStsKey(Long clntStsKey) {
        this.clntStsKey = clntStsKey;
        return this;
    }

    public Long getResTypKey() {
        return resTypKey;
    }

    public void setResTypKey(Long resTypKey) {
        this.resTypKey = resTypKey;
    }

    public String getFthrFrstNm() {
        return fthrFrstNm;
    }

    public void setFthrFrstNm(String fthrFrstNm) {
        this.fthrFrstNm = fthrFrstNm;
    }

    public MwClient fthrFrstNm(String fthrFrstNm) {
        this.fthrFrstNm = fthrFrstNm;
        return this;
    }

    public String getFthrLastNm() {
        return fthrLastNm;
    }

    public void setFthrLastNm(String fthrLastNm) {
        this.fthrLastNm = fthrLastNm;
    }

    public MwClient fthrLastNm(String fthrLastNm) {
        this.fthrLastNm = fthrLastNm;
        return this;
    }

    public String getSpzFrstNm() {
        return spzFrstNm;
    }

    public void setSpzFrstNm(String spzFrstNm) {
        this.spzFrstNm = spzFrstNm;
    }

    public MwClient spzFrstNm(String spzFrstNm) {
        this.spzFrstNm = spzFrstNm;
        return this;
    }

    public String getSpzLastNm() {
        return spzLastNm;
    }

    public void setSpzLastNm(String spzLastNm) {
        this.spzLastNm = spzLastNm;
    }

    public MwClient spzLastNm(String spzLastNm) {
        this.spzLastNm = spzLastNm;
        return this;
    }

    public Long getYrsRes() {
        return yrsRes;
    }

    public void setYrsRes(Long yrsRes) {
        this.yrsRes = yrsRes;
    }

    public MwClient yrsRes(Long yrsRes) {
        this.yrsRes = yrsRes;
        return this;
    }

    public Long getMnthsRes() {
        return mnthsRes;
    }

    public void setMnthsRes(Long mnthsRes) {
        this.mnthsRes = mnthsRes;
    }

    public MwClient mnthsRes(Long mnthsRes) {
        this.mnthsRes = mnthsRes;
        return this;
    }

    public Boolean getPftFlg() {
        return pftFlg;
    }

    public void setPftFlg(Boolean pftFlg) {
        this.pftFlg = pftFlg;
    }

    public Instant getMembrshpDt() {
        return membrshpDt;
    }

    public void setMembrshpDt(Instant membrshpDt) {
        this.membrshpDt = membrshpDt;
    }

    public Long getRefCdLeadTypSeq() {
        return refCdLeadTypSeq;
    }

    public void setRefCdLeadTypSeq(Long refCdLeadTypSeq) {
        this.refCdLeadTypSeq = refCdLeadTypSeq;
    }

    public String getWhatsappNum() {
        return WhatsappNum;
    }

    public void setWhatsappNum(String whatsappNum) {
        WhatsappNum = whatsappNum;
    }

    public Long getRefCdPhTypSeq() {
        return refCdPhTypSeq;
    }

    public void setRefCdPhTypSeq(Long refCdPhTypSeq) {
        this.refCdPhTypSeq = refCdPhTypSeq;
    }

    public Long getSimRgstrByClnt() {
        return simRgstrByClnt;
    }

    public void setSimRgstrByClnt(Long simRgstrByClnt) {
        this.simRgstrByClnt = simRgstrByClnt;
    }

    public Long getRefCdLoanUsr() {
        return refCdLoanUsr;
    }

    public void setRefCdLoanUsr(Long refCdLoanUsr) {
        this.refCdLoanUsr = refCdLoanUsr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwClient MwClient = (MwClient) o;
        if (MwClient.getClntSeq() == null || getClntSeq() == null) {
            return false;
        }
        return Objects.equals(getClntSeq(), MwClient.getClntSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClntSeq());
    }

    @Override
    public String toString() {
        return "MwClient{" + "clntSeq=" + clntSeq + ", effStartDt=" + effStartDt + ", clntId='" + clntId + '\'' + ", cnicNum=" + cnicNum
                + ", cnicExpryDt=" + cnicExpryDt + ", frstNm='" + frstNm + '\'' + ", lastNm='" + lastNm + '\'' + ", nickNm='" + nickNm
                + '\'' + ", dob=" + dob + ", gndrKey=" + gndrKey + ", mrtlStsKey=" + mrtlStsKey + ", eduLvlKey=" + eduLvlKey
                + ", natrOfDisKey=" + natrOfDisKey + ", occKey=" + occKey + ", hseHldMemb=" + hseHldMemb + ", numOfDpnd=" + numOfDpnd
                + ", numOfChldrn=" + numOfChldrn + ", disFlg=" + disFlg + ", slfPdcFlg=" + slfPdcFlg + ", phNum='" + phNum + '\''
                + ", mthrMadnNm='" + mthrMadnNm + '\'' + ", erngMemb=" + erngMemb + ", crntAddrPermFlg=" + crntAddrPermFlg
                + ", numOfErngMemb=" + numOfErngMemb + ", totIncmOfErngMemb=" + totIncmOfErngMemb + ", nomDtlAvailableFlg="
                + nomDtlAvailableFlg + ", coBwrSanFlg=" + coBwrSanFlg + ", crtdBy='" + crtdBy + '\'' + ", crtdDt=" + crtdDt
                + ", lastUpdBy='" + lastUpdBy + '\'' + ", lastUpdDt=" + lastUpdDt + ", delFlg=" + delFlg + ", effEndDt=" + effEndDt
                + ", crntRecFlg=" + crntRecFlg + ", portKey=" + portKey + ", clntStsKey=" + clntStsKey + ", resTypKey=" + resTypKey
                + ", fthrFrstNm='" + fthrFrstNm + '\'' + ", fthrLastNm='" + fthrLastNm + '\'' + ", spzFrstNm='" + spzFrstNm + '\''
                + ", spzLastNm='" + spzLastNm + '\'' + ", yrsRes=" + yrsRes + ", mnthsRes=" + mnthsRes + ", bizDtl='" + bizDtl + '\''
                + ", syncFlg=" + syncFlg + ", smHsldFlg=" + smHsldFlg + ", clntStsDt=" + clntStsDt + ", pftFlg=" + pftFlg + ", refCdPhTypSeq=" + refCdPhTypSeq + ", simRgstrByClnt=" + simRgstrByClnt + ", refCdLoanUsr=" + refCdLoanUsr + '}';
    }
}
