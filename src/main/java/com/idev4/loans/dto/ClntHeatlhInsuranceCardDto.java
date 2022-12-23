package com.idev4.loans.dto;

import java.time.Instant;

public class ClntHeatlhInsuranceCardDto {

    public Long loanAppSeq;

    public Instant cardExpiryDate;

    public String cardNum;

    // Added by Zohaib Asim - Dated 19-12-2020
    // CR: KSZB Claims
    public String loanAppSts;
    public long prdSeq;
}
