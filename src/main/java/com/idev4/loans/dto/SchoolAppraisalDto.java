package com.idev4.loans.dto;

import com.idev4.loans.domain.MwSchQltyChk;

import java.util.List;

public class SchoolAppraisalDto {

    public long schyr;
    public long formSeq;
    public long schoolAge;
    public List<SchoolQuestionsDto> SchoolQAArray;
    public List<MwSchQltyChk> mwAnswers;
    public List<IncomeDtlDto> primaryIncome;
    public List<IncomeDtlDto> secondaryIncome;
    public List<BizExpDto> businessExpense;
    public List<BizExpDto> householdExpense;
    public boolean hasBasic;
    public boolean hasAddress;
    public boolean hasGrade;
    public boolean hasIncome;
    public boolean hasExpense;
    public boolean hasQltyChck;
    public boolean formComplete;
    public boolean hasAttend;
    public String schRegdAgy;
    // school_appraisal
    private long schAprslSeq;
    private String schNm;
    private long schRegdFlg;
    private long pefSptFlg;
    private long schArea;
    private long schAge;
    private long schOwnTypKey;
    private long relWthOwnKey;
    private long schPpalKey;
    private long bldngOwnKey;
    private long schTypKey;
    private long schLvlKey;
    private long schMedmKey;
    private long schAreaUntKey;
    private long loanAppSeq;
    // address
    private AddressDto addressDto;
    // attendance
    private Long schAtndSeq;
    private Long totMaleTchrs;
    private Long totFemTchrs;
    private Long lastYrDrop;
    // grades
    private List<SchoolGradeDto> schoolGradeDtos;
    // school_quality_check
    private List<SchoolQualityCheckDto> schoolQualityCheckDtos;

    public long getSchAprslSeq() {
        return schAprslSeq;
    }

    public void setSchAprslSeq(long schAprslSeq) {
        this.schAprslSeq = schAprslSeq;
    }

    public String getSchNm() {
        return schNm;
    }

    public void setSchNm(String schNm) {
        this.schNm = schNm;
    }

    public long getSchRegdFlg() {
        return schRegdFlg;
    }

    public void setSchRegdFlg(long schRegdFlg) {
        this.schRegdFlg = schRegdFlg;
    }

    public long getPefSptFlg() {
        return pefSptFlg;
    }

    public void setPefSptFlg(long pefSptFlg) {
        this.pefSptFlg = pefSptFlg;
    }

    public long getSchArea() {
        return schArea;
    }

    public void setSchArea(long schArea) {
        this.schArea = schArea;
    }

    public long getSchAge() {
        return schAge;
    }

    public void setSchAge(long schAge) {
        this.schAge = schAge;
    }

    public long getSchOwnTypKey() {
        return schOwnTypKey;
    }

    public void setSchOwnTypKey(long schOwnTypKey) {
        this.schOwnTypKey = schOwnTypKey;
    }

    public long getRelWthOwnKey() {
        return relWthOwnKey;
    }

    public void setRelWthOwnKey(long relWthOwnKey) {
        this.relWthOwnKey = relWthOwnKey;
    }

    public long getSchPpalKey() {
        return schPpalKey;
    }

    public void setSchPpalKey(long schPpalKey) {
        this.schPpalKey = schPpalKey;
    }

    public long getBldngOwnKey() {
        return bldngOwnKey;
    }

    public void setBldngOwnKey(long bldngOwnKey) {
        this.bldngOwnKey = bldngOwnKey;
    }

    public long getSchTypKey() {
        return schTypKey;
    }

    public void setSchTypKey(long schTypKey) {
        this.schTypKey = schTypKey;
    }

    public long getSchLvlKey() {
        return schLvlKey;
    }

    public void setSchLvlKey(long schLvlKey) {
        this.schLvlKey = schLvlKey;
    }

    public long getSchMedmKey() {
        return schMedmKey;
    }

    public void setSchMedmKey(long schMedmKey) {
        this.schMedmKey = schMedmKey;
    }

    public long getSchAreaUntKey() {
        return schAreaUntKey;
    }

    public void setSchAreaUntKey(long schAreaUntKey) {
        this.schAreaUntKey = schAreaUntKey;
    }

    public long getLoanAppSeq() {
        return loanAppSeq;
    }

    public void setLoanAppSeq(long loanAppSeq) {
        this.loanAppSeq = loanAppSeq;
    }

    public AddressDto getAddressDto() {
        if (this.addressDto == null) {
            addressDto = new AddressDto();
        }
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public Long getSchAtndSeq() {
        return schAtndSeq;
    }

    public void setSchAtndSeq(Long schAtndSeq) {
        this.schAtndSeq = schAtndSeq;
    }

    public Long getTotMaleTchrs() {
        return totMaleTchrs;
    }

    public void setTotMaleTchrs(Long totMaleTchrs) {
        this.totMaleTchrs = totMaleTchrs;
    }

    public Long getTotFemTchrs() {
        return totFemTchrs;
    }

    public void setTotFemTchrs(Long totFemTchrs) {
        this.totFemTchrs = totFemTchrs;
    }

    public Long getLastYrDrop() {
        return lastYrDrop;
    }

    public void setLastYrDrop(Long lastYrDrop) {
        this.lastYrDrop = lastYrDrop;
    }

    public List<SchoolGradeDto> getSchoolGradeDtos() {
        return schoolGradeDtos;
    }

    public void setSchoolGradeDtos(List<SchoolGradeDto> schoolGradeDtos) {
        this.schoolGradeDtos = schoolGradeDtos;
    }

    public List<SchoolQualityCheckDto> getSchoolQualityCheckDtos() {
        return schoolQualityCheckDtos;
    }

    public void setSchoolQualityCheckDtos(List<SchoolQualityCheckDto> schoolQualityCheckDtos) {
        this.schoolQualityCheckDtos = schoolQualityCheckDtos;
    }
}
