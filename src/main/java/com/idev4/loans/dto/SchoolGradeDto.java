package com.idev4.loans.dto;

public class SchoolGradeDto {

    private long schGrdSeq;
    private long totFemStdnt;
    private long totMaleStdnt;
    private long avgFee;
    private long noFeeStdnt;
    private long femStdntPrsnt;
    private long maleStdntPrsnt;
    private long grdKey;
    private long schAprslSeq;
    private String gradeFlag;

    public String getGradeFlag() {
        return gradeFlag;
    }

    public void setGradeFlag(String gradeFlag) {
        this.gradeFlag = gradeFlag;
    }

    public long getSchAprslSeq() {
        return schAprslSeq;
    }

    public void setSchAprslSeq(long schAprslSeq) {
        this.schAprslSeq = schAprslSeq;
    }

    public long getSchGrdSeq() {
        return schGrdSeq;
    }

    public void setSchGrdSeq(long schGrdSeq) {
        this.schGrdSeq = schGrdSeq;
    }

    public long getTotFemStdnt() {
        return totFemStdnt;
    }

    public void setTotFemStdnt(long totFemStdnt) {
        this.totFemStdnt = totFemStdnt;
    }

    public long getTotMaleStdnt() {
        return totMaleStdnt;
    }

    public void setTotMaleStdnt(long totMaleStdnt) {
        this.totMaleStdnt = totMaleStdnt;
    }

    public long getAvgFee() {
        return avgFee;
    }

    public void setAvgFee(long avgFee) {
        this.avgFee = avgFee;
    }

    public long getNoFeeStdnt() {
        return noFeeStdnt;
    }

    public void setNoFeeStdnt(long noFeeStdnt) {
        this.noFeeStdnt = noFeeStdnt;
    }

    public long getFemStdntPrsnt() {
        return femStdntPrsnt;
    }

    public void setFemStdntPrsnt(long femStdntPrsnt) {
        this.femStdntPrsnt = femStdntPrsnt;
    }

    public long getMaleStdntPrsnt() {
        return maleStdntPrsnt;
    }

    public void setMaleStdntPrsnt(long maleStdntPrsnt) {
        this.maleStdntPrsnt = maleStdntPrsnt;
    }

    public long getGrdKey() {
        return grdKey;
    }

    public void setGrdKey(long grdKey) {
        this.grdKey = grdKey;
    }
}

