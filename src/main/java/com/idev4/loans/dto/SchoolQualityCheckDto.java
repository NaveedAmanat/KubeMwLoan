package com.idev4.loans.dto;

public class SchoolQualityCheckDto {

    private long schQltyChkSeq;
    private long schAprslSeq;
    private long qstSeq;
    private long qstCtgryKey;
    private String qstCtgry;
    private String qstStr;
    private long answrSeq;
    private String schQltyChkFlag;

    public String getSchQltyChkFlag() {
        return schQltyChkFlag;
    }

    public void setSchQltyChkFlag(String schQltyChkFlag) {
        this.schQltyChkFlag = schQltyChkFlag;
    }

    public long getQstCtgryKey() {
        return qstCtgryKey;
    }

    public void setQstCtgryKey(long qstCtgryKey) {
        this.qstCtgryKey = qstCtgryKey;
    }

    public String getQstCtgry() {
        return qstCtgry;
    }

    public void setQstCtgry(String qstCtgry) {
        this.qstCtgry = qstCtgry;
    }

    public long getSchQltyChkSeq() {
        return schQltyChkSeq;
    }

    public void setSchQltyChkSeq(long schQltyChkSeq) {
        this.schQltyChkSeq = schQltyChkSeq;
    }

    public long getSchAprslSeq() {
        return schAprslSeq;
    }

    public void setSchAprslSeq(long schAprslSeq) {
        this.schAprslSeq = schAprslSeq;
    }

    public long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(long qstSeq) {
        this.qstSeq = qstSeq;
    }

    public String getQstStr() {
        return qstStr;
    }

    public void setQstStr(String qstStr) {
        this.qstStr = qstStr;
    }

    public long getAnswrSeq() {
        return answrSeq;
    }

    public void setAnswrSeq(long answrSeq) {
        this.answrSeq = answrSeq;
    }
}

