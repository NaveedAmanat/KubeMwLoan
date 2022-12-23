package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "mw_sch_aprsl")
// @IdClass ( MwSchAprslId.class )
public class MwSchAprsl {

    @Id
    @Column(name = "sch_aprsl_seq")
    private Long schAprslSeq;

    @Column(name = "sch_nm")
    private String schNm;

    @Column(name = "sch_regd_flg")
    private Long schRegdFlg;

    @Column(name = "pef_spt_flg")
    private Long pefSptFlg;

    @Column(name = "sch_area")
    private Long schArea;

    @Column(name = "sch_own_typ_key")
    private Long schOwnTypKey;

    @Column(name = "rel_wth_own_key")
    private Long relWthOwnKey;

    @Column(name = "sch_ppal_key")
    private Long schPpalKey;

    @Column(name = "bldng_own_key")
    private Long bldngOwnKey;

    @Column(name = "sch_typ_key")
    private Long schTypKey;

    @Column(name = "sch_lvl_key")
    private Long schLvlKey;

    @Column(name = "sch_medm_key")
    private Long schMedmKey;

    @Column(name = "sch_area_unt_key")
    private Long schAreaUntKey;

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

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "loan_App_SEQ")
    private long loanAppSeq;

    @Column(name = "SCH_YR")
    private Long schYr;

    @Column(name = "SYNC_FLG")
    private Boolean syncFlg;

    @Column(name = "sch_regd_agy")
    private String schRegdAgy;

    public String getSchRegdAgy() {
        return schRegdAgy;
    }

    public void setSchRegdAgy(String schRegdAgy) {
        this.schRegdAgy = schRegdAgy;
    }

    public Boolean getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(Boolean syncFlg) {
        this.syncFlg = syncFlg;
    }

    public Long getSchYr() {
        return schYr;
    }

    public void setSchYr(Long schYr) {
        this.schYr = schYr;
    }

    public Long getSchAprslSeq() {
        return schAprslSeq;
    }

    public void setSchAprslSeq(Long schAprslSeq) {
        this.schAprslSeq = schAprslSeq;
    }

    public String getSchNm() {
        return schNm;
    }

    public void setSchNm(String schNm) {
        this.schNm = schNm;
    }

    public long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public Long getRelWthOwnKey() {
        return relWthOwnKey;
    }

    public void setRelWthOwnKey(Long relWthOwnKey) {
        this.relWthOwnKey = relWthOwnKey;
    }

    public Long getSchRegdFlg() {
        return schRegdFlg;
    }

    public void setSchRegdFlg(Long schRegdFlg) {
        this.schRegdFlg = schRegdFlg;
    }

    public Long getPefSptFlg() {
        return pefSptFlg;
    }

    public void setPefSptFlg(Long pefSptFlg) {
        this.pefSptFlg = pefSptFlg;
    }

    public Long getSchArea() {
        return schArea;
    }

    public void setSchArea(Long schArea) {
        this.schArea = schArea;
    }

    public Long getSchOwnTypKey() {
        return schOwnTypKey;
    }

    public void setSchOwnTypKey(Long schOwnTypKey) {
        this.schOwnTypKey = schOwnTypKey;
    }

    public Long getSchPpalKey() {
        return schPpalKey;
    }

    public void setSchPpalKey(Long schPpalKey) {
        this.schPpalKey = schPpalKey;
    }

    public Long getBldngOwnKey() {
        return bldngOwnKey;
    }

    public void setBldngOwnKey(Long bldngOwnKey) {
        this.bldngOwnKey = bldngOwnKey;
    }

    public Long getSchTypKey() {
        return schTypKey;
    }

    public void setSchTypKey(Long schTypKey) {
        this.schTypKey = schTypKey;
    }

    public Long getSchLvlKey() {
        return schLvlKey;
    }

    public void setSchLvlKey(Long schLvlKey) {
        this.schLvlKey = schLvlKey;
    }

    public Long getSchMedmKey() {
        return schMedmKey;
    }

    public void setSchMedmKey(Long schMedmKey) {
        this.schMedmKey = schMedmKey;
    }

    public Long getSchAreaUntKey() {
        return schAreaUntKey;
    }

    public void setSchAreaUntKey(Long schAreaUntKey) {
        this.schAreaUntKey = schAreaUntKey;
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
