package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "MW_MFCIB_ALWD_PRD")
public class MwMfcibAlwdPrd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MFCIB_ALWD_PRD_SEQ")
    private Long mficb_alwd_prd_seq;

    @Column(name = "LOAN_APP_SEQ")
    private Long loan_app_seq;

    @Column(name = "ALWD_PRD_SEQ")
    private Long alwd_prd_seq;

    @Column(name = "CRTD_BY")
    private String crtd_by;

    @Column(name = "CRTD_DT")
    private Instant crtd_dt;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crnt_rec_flg;


    public Long getMficb_alwd_prd_seq() {
        return mficb_alwd_prd_seq;
    }

    public void setMficb_alwd_prd_seq(Long mficb_alwd_prd_seq) {
        this.mficb_alwd_prd_seq = mficb_alwd_prd_seq;
    }

    public Long getLoan_app_seq() {
        return loan_app_seq;
    }

    public void setLoan_app_seq(Long loan_app_seq) {
        this.loan_app_seq = loan_app_seq;
    }

    public Long getAlwd_prd_seq() {
        return alwd_prd_seq;
    }

    public void setAlwd_prd_seq(Long alwd_prd_seq) {
        this.alwd_prd_seq = alwd_prd_seq;
    }

    public String getCrtd_by() {
        return crtd_by;
    }

    public void setCrtd_by(String crtd_by) {
        this.crtd_by = crtd_by;
    }

    public Instant getCrtd_dt() {
        return crtd_dt;
    }

    public void setCrtd_dt(Instant crtd_dt) {
        this.crtd_dt = crtd_dt;
    }

    public Boolean getCrnt_rec_flg() {
        return crnt_rec_flg;
    }

    public void setCrnt_rec_flg(Boolean crnt_rec_flg) {
        this.crnt_rec_flg = crnt_rec_flg;
    }
}
