package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwDoc.
 */
@Entity
@Table(name = "mw_doc")
public class MwDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "doc_seq")
    private Long docSeq;

    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_ctgry_key")
    private Long docCtgryKey;

    @Column(name = "doc_typ_key")
    private Long docTypKey;

    @Column(name = "doc_nm")
    private String docNm;

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

    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    public Long getDocSeq() {
        return docSeq;
    }

    public void setDocSeq(Long docSeq) {
        this.docSeq = docSeq;
    }

    public MwDoc docSeq(Long docSeq) {
        this.docSeq = docSeq;
        return this;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public MwDoc docId(String docId) {
        this.docId = docId;
        return this;
    }

    public Long getDocCtgryKey() {
        return docCtgryKey;
    }

    public void setDocCtgryKey(Long docCtgryKey) {
        this.docCtgryKey = docCtgryKey;
    }

    public MwDoc docCtgryKey(Long docCtgryKey) {
        this.docCtgryKey = docCtgryKey;
        return this;
    }

    public Long getDocTypKey() {
        return docTypKey;
    }

    public void setDocTypKey(Long docTypKey) {
        this.docTypKey = docTypKey;
    }

    public MwDoc docTypKey(Long docTypKey) {
        this.docTypKey = docTypKey;
        return this;
    }

    public String getDocNm() {
        return docNm;
    }

    public void setDocNm(String docNm) {
        this.docNm = docNm;
    }

    public MwDoc docNm(String docNm) {
        this.docNm = docNm;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwDoc crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwDoc crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwDoc lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwDoc lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwDoc delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
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

    public MwDoc effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwDoc effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwDoc crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwDoc mwDoc = (MwDoc) o;
        if (mwDoc.getDocSeq() == null || getDocSeq() == null) {
            return false;
        }
        return Objects.equals(getDocSeq(), mwDoc.getDocSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDocSeq());
    }

    @Override
    public String toString() {
        return "MwDoc{" + "id=" + getDocSeq() + ", docSeq=" + getDocSeq() + ", docId='" + getDocId() + "'"
                + ", docCtgryKey=" + getDocCtgryKey() + ", docTypKey=" + getDocTypKey() + ", docNm='" + getDocNm() + "'"
                + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'" + ", lastUpdBy='" + getLastUpdBy()
                + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", delFlg='" + isDelFlg() + "'" + ", effStartDt='"
                + getEffStartDt() + "'" + ", effEndDt='" + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'"
                + "}";
    }
}
