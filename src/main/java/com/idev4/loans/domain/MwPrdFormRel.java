package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwPrdFormRel.
 */
@Entity
@Table(name = "mw_prd_form_rel")
// @IdClass(MwPrdFormRelId.class)
public class MwPrdFormRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "prd_form_rel_seq")
    private Long prdFormSeq;

    @Column(name = "form_seq")
    private Long formSeq;

    @Column(name = "prd_seq")
    private Long prdSeq;

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "FORM_SORT_ORDR")
    private Long formSortOrdr;

    @Column(name = "FORM_MNDTRY_FLG")
    private Boolean formMndtryFlg;

    public Long getFormSortOrdr() {
        return formSortOrdr;
    }

    public void setFormSortOrdr(Long formSortOrdr) {
        this.formSortOrdr = formSortOrdr;
    }

    public Boolean getFormMndtryFlg() {
        return formMndtryFlg;
    }

    public void setFormMndtryFlg(Boolean formMndtryFlg) {
        this.formMndtryFlg = formMndtryFlg;
    }

    public Long getPrdFormSeq() {
        return prdFormSeq;
    }

    public void setPrdFormSeq(Long prdFormSeq) {
        this.prdFormSeq = prdFormSeq;
    }

    public MwPrdFormRel prdFormSeq(Long prdFormSeq) {
        this.prdFormSeq = prdFormSeq;
        return this;
    }

    public Long getFormSeq() {
        return formSeq;
    }

    public void setFormSeq(Long formSeq) {
        this.formSeq = formSeq;
    }

    public MwPrdFormRel formSeq(Long formSeq) {
        this.formSeq = formSeq;
        return this;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public MwPrdFormRel prdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwPrdFormRel effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwPrdFormRel effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwPrdFormRel crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwPrdFormRel mwPrdFormRel = (MwPrdFormRel) o;
        if (mwPrdFormRel.getPrdFormSeq() == null || getPrdFormSeq() == null) {
            return false;
        }
        return Objects.equals(getPrdFormSeq(), mwPrdFormRel.getPrdFormSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPrdFormSeq());
    }

    @Override
    public String toString() {
        return "MwPrdFormRel{" + "id=" + getPrdFormSeq() + ", prdFormSeq=" + getPrdFormSeq() + ", formSeq=" + getFormSeq() + ", prdSeq="
                + getPrdSeq() + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='"
                + isCrntRecFlg() + "'" + "}";
    }
}
