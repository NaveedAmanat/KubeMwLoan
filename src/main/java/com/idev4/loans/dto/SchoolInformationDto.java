package com.idev4.loans.dto;

import com.idev4.loans.domain.MwSchQltyChk;

import java.util.List;

public class SchoolInformationDto {

    public Long schAstsSeq;

    public Long totRms;
    public Long totOfcs;
    public Long totToilets;
    public Long totCmptrs;
    public Long totChrs;
    public Long totDsks;
    public Long totLabs;
    public Long totWclrs;
    public Long totFans;
    public Long totGnrtrs;
    public Long totFlrs;
    public String othAsts;

    public Long loanAppSeq;
    public Long formSeq;

    // Added by Zohaib Asim - Dated 14-4-2022 - Fields information missing
    public Long totMalTolts;
    public Long totFmalTolts;
    public Long totCmptrLabs;
    // End


    public List<SchoolQualityCheckDto> schoolQualityCheckDtos;

    public List<SchoolQuestionsDto> SchoolQAArray;

    public List<SchoolQuestionsDto> documentChecklist;

    public List<MwSchQltyChk> mwAnswers;


    public boolean hasAssets;
    public boolean hasDocChck;
    public boolean hasQltyChck;
    public boolean formComplete;

}
