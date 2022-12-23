package com.idev4.loans.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MWTags.
 */
@Entity
@Table(name = "mw_tags")
public class MWTags implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "tags_seq")
    private Long tagsSeq;

    @Column(name = "tag_id")
    private String tagId;

    @Column(name = "tag_nm")
    private String tagName;

    @Column(name = "tag_dscr")
    private String tagDscr;

    @Column(name = "svrty_flg_key")
    private Long svrtyFlgKey;

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


    public Long getTagsSeq() {
        return tagsSeq;
    }

    public void setTagsSeq(Long tagsSeq) {
        this.tagsSeq = tagsSeq;
    }

    public MWTags tagsSeq(Long tagsSeq) {
        this.tagsSeq = tagsSeq;
        return this;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public MWTags tagId(String tagId) {
        this.tagId = tagId;
        return this;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public MWTags tagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    public String getTagDscr() {
        return tagDscr;
    }

    public void setTagDscr(String tagDscr) {
        this.tagDscr = tagDscr;
    }

    public MWTags tagDscr(String tagDscr) {
        this.tagDscr = tagDscr;
        return this;
    }

    public Long getSvrtyFlgKey() {
        return svrtyFlgKey;
    }

    public void setSvrtyFlgKey(Long svrtyFlgKey) {
        this.svrtyFlgKey = svrtyFlgKey;
    }

    public MWTags svrtyFlgKey(Long svrtyFlgKey) {
        this.svrtyFlgKey = svrtyFlgKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MWTags crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MWTags crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MWTags lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MWTags lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MWTags delFlg(Boolean delFlg) {
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

    public MWTags effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MWTags effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MWTags crntRecFlg(Boolean crntRecFlg) {
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
        MWTags mWTags = (MWTags) o;
        if (mWTags.getTagsSeq() == null || getTagsSeq() == null) {
            return false;
        }
        return Objects.equals(getTagsSeq(), mWTags.getTagsSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTagsSeq());
    }

    @Override
    public String toString() {
        return "MWTags{" +
                "id=" + getTagsSeq() +
                ", tagsSeq=" + getTagsSeq() +
                ", tagId='" + getTagId() + "'" +
                ", tagName='" + getTagName() + "'" +
                ", tagDscr='" + getTagDscr() + "'" +
                ", svrtyFlgKey=" + getSvrtyFlgKey() +
                ", crtdBy='" + getCrtdBy() + "'" +
                ", crtdDt='" + getCrtdDt() + "'" +
                ", lastUpdBy='" + getLastUpdBy() + "'" +
                ", lastUpdDt='" + getLastUpdDt() + "'" +
                ", delFlg='" + isDelFlg() + "'" +
                ", effStartDt='" + getEffStartDt() + "'" +
                ", effEndDt='" + getEffEndDt() + "'" +
                ", crntRecFlg='" + isCrntRecFlg() + "'" +
                "}";
    }
}
