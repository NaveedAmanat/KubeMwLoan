package com.idev4.loans.dto.tab;

import java.util.List;

public class TabClientAppDto {

    public TabClientDto client;

    public LoanAppDto loan_info;

    public ClntRelDto nominee;

    public LoanAppCrdtScrDto credit_scoring;

    public List<OtherLoanDto> other_loans;

    public InsuranceInfo insurance_info;

    public CobDto coborrower;

    public ClntRelDto next_of_kin;
    ;

    public CobDto client_relative;

    public List<ExpextedLoanDto> expected_loan_utilization;

    public BusinessAprslDto business_appraisal;

    public List<PSC> psc;

    public SchoolAppraisalDto school_appraisal;

    public SchoolInformationDto school_information;

    public List<DocDto> documents;

    public List<AnmlRgstrDto> anml_rgstr;

    public String user_role;

    public HmAprslDto home_imp_aprsl;

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
