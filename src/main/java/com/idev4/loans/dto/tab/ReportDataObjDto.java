package com.idev4.loans.dto.tab;

import java.io.Serializable;
import java.util.List;

public class ReportDataObjDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public String CNIC;
    public String fullName;
    public String dateOfBirth;
    public String city;
    public String applicationNumber;
    public String loanAmount;
    public String gender;
    public String currentAddress;
    public String fatherHusbandName;
    public String oldNIC;
    public String profession;
    public String selfEmployed;
    public String cellNo;
    public String productType;
    public String phoneNo;
    public String employerAddress;
    public String ntn;
    public String passport;
    public String designation;
    public String coBorrower;
    public String currentEmployer;
    // Added by Zohaib Asim - Dated 13-10-2021
    public String reqFor;
    public String companyNm;
    // End by Zohaib Asim

    public List<AdditionalInformation> AdditionalInformation;

//	@Override
//    public String toString() {
//        return "ReportDataObjDto [cnic=" + cnic + ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", city=" + city
//                + ", applicationNumber=" + applicationNumber + ", loanAmount=" + loanAmount + ", gender=" + gender + ", currentAddress="
//                + currentAddress + ", fatherHusbandName=" + fatherHusbandName + ", oldNIC=" + oldNIC + ", profession="
//                + profession +  ", selfEmployed=" + selfEmployed + ", cellNo=" + cellNo + ", productType=" + productType + ", phoneNo=" + phoneNo + ", employerAddress=" + employerAddress +
//                ", ntn=" + ntn + ", passport=" + passport + ", designation=" + designation + ", coBorrower=" + coBorrower + ", currentEmployer=" +currentEmployer +"]";
//    }


    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
//	
}
