package com.idev4.loans.dto;

/*
 * Changed by Zohaib Asim
 * Version: 3.1
 * Reason: New Parameter addition
 * Dated: 03-05-2021
 * */
public class CreditBureauDto {

    // Mandatory
    public String memberCode;

    // Mandatory
    public String controlBranchCode;

    public String subBranchCode;

    // Mandatory
    public String makerUserName;

    // Mandatory
    public String makerPassword;

    public String checkerUserName;

    public String checkerPassword;

    public String nicNoOrPassportNo;

    // Mandatory
    public String cnicNo;

    // New Parm
    public String title;
    // -

    // Mandatory
    public String firstName;

    // Mandatory
    public String middleName;

    // Mandatory
    public String lastName;

    // Mandatory
    public String dateOfBirth;

    // Mandatory
    public String gender;

    public String profession;

    // New Parm
    public String ntnNo;

    public String nationalityType;

    public String nationality;

    public String dependants;

    public String maritialStatus;

    public String qualification;
    // -

    // Mandatory
    public String fatherOrHusbandFirstName;

    // Mandatory
    public String fatherOrHusbandMiddleName;

    // Mandatory
    public String fatherOrHusbandLastName;

    // Mandatory
    public String address;

    // Mandatory
    public String cityOrDistrict;

    // Mandatory
    public String phoneNo;

    public String cellNo;

    // New Parm
    public String permanentAddress;

    public String permanentCity;
    // -

    public String employerCompanyName;

    // New Parm
    public String designation;

    public String selfEmployed;
    // -

    public String employerAddress;

    // New Parm
    public String employerDistrict;
    // -

    public String employerPhoneNo;

    public String employerCellNo;

    // Mandatory
    public String accountType;

    // Mandatory
    public String applicationId;

    // Mandatory
    public String amount;

    // Mandatory
    public String associationType;

    // New Parm
    public String enquiryStatus;

    public String dateOfApplication;
    // -

    public String groupId;

    // New Parm
    public String co_nic_1;

    public String co_cnic_1;

    public String co_first_name_1;

    public String co_mid_name_1;

    public String co_last_name_1;

    public String co_association_1;


    public String co_nic_2;

    public String co_cnic_2;

    public String co_first_name_2;

    public String co_mid_name_2;

    public String co_last_name_2;

    public String co_association_2;


    public String co_nic_3;

    public String co_cnic_3;

    public String co_first_name_3;

    public String co_mid_name_3;

    public String co_last_name_3;

    public String co_association_3;


    public String co_nic_4;

    public String co_cnic_4;

    public String co_first_name_4;

    public String co_mid_name_4;

    public String co_last_name_4;

    public String co_association_4;


    public String co_nic_5;

    public String co_cnic_5;

    public String co_first_name_5;

    public String co_mid_name_5;

    public String co_last_name_5;

    public String co_association_5;
    // -


    public String newApplicationId;

    public String emailAddress;

    public String employerBusinessCategory;

    public String employerCityOrDistrict;

    public String employerEmailAddress;

    public String employerOwnershipStatus;


    // Added by Zohaib Asim - Dated 09-03-2021
    public String reqFor;

    public String companyNm;
    // End by Zohaib Asim

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
