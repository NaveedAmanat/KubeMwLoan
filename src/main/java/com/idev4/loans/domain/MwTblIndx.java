package com.idev4.loans.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "MW_TBL_INDX")
public class MwTblIndx implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TBL_INDX_SEQ")
    private Long tblIndxSeq;

    @Column(name = "TBL_NM")
    private String tblNm;

    @Column(name = "INDX")
    private Long indx;

    public Long getTblIndxSeq() {
        return tblIndxSeq;
    }

    public void setTblIndxSeq(Long tblIndxSeq) {
        this.tblIndxSeq = tblIndxSeq;
    }

    public String getTblNm() {
        return tblNm;
    }

    public void setTblNm(String tblNm) {
        this.tblNm = tblNm;
    }

    public Long getIndx() {
        return indx;
    }

    public void setIndx(Long indx) {
        this.indx = indx;
    }

}
