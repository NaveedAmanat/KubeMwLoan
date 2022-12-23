package com.idev4.loans.service;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwLoanAppCrdtSumry;
import com.idev4.loans.domain.MwLoanAppDoc;
import com.idev4.loans.domain.MwLoanAppMfcibData;
import com.idev4.loans.dto.CreditBureauDto;
import com.idev4.loans.repository.*;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.Queries;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class CreditBureau {// extends WebServiceGatewaySupport {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Logger log = LoggerFactory.getLogger(DataLift.class);
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    static SecureRandom rnd = new SecureRandom();
    private static String url = "";
    private static String authKey = "";
    private static String maker = "";
    private static String makerPassword = "";
    private static String checker = "";
    private static String checkerPassword = "";
    private static String cbReq = "getBureauCreditReportV5";
    private static String cbResp = "getBureauCreditReportV5Response";
    private static String cbRslt = "getBureauCreditReportV5Result";
    @Autowired
    ServletContext context;
    @Autowired
    MwLoanAppDocRepository mwLoanAppDocRepository;
    @Autowired
    MwClntRepository mwClntRepository;
    @Autowired
    MwLoanAppRepository mwLoanAppRepository;
    @Autowired
    EntityManager em;
    @Autowired
    MwLoanAppCrdtSumryRepository mwLoanAppCrdtSumryRepository;
    // End
    // Added by Zohaib Asim - Dated 17-06-2022
    @Autowired
    MwLoanAppMfcibDataRepository mwLoanAppMfcibDataRepository;
    // End

    private static void createSoapEnvelope(SOAPMessage soapMessage, CreditBureauDto dto) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "tem";
        String myNamespaceURI = "http://tempuri.org/";
        String myNamespace2 = "dat";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        envelope.addNamespaceDeclaration(myNamespace2, "http://schemas.datacontract.org/2004/07/DataCheckEnquiry");

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="https://www.w3schools.com/xml/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <myNamespace:CelsiusToFahrenheit>
                    <myNamespace:Celsius>100</myNamespace:Celsius>
                </myNamespace:CelsiusToFahrenheit>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();

        // Modified by Zohaib Asim - Dated 04-05-2021 - Version 3.1
        SOAPElement soapBodyElem = soapBody.addChildElement("getBureauCreditReportV3_1", myNamespace);
        // SOAPElement soapBodyElem = soapBody.addChildElement(cbReq, myNamespace);

        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("authKey", myNamespace);
        soapBodyElem1.addTextNode(authKey);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("cr", myNamespace);
        SOAPElement soapBodyElemCr1 = soapBodyElem2.addChildElement("accountType", myNamespace2);
        soapBodyElemCr1.addTextNode(dto.accountType);
        SOAPElement soapBodyElemCr2 = soapBodyElem2.addChildElement("address", myNamespace2);
        soapBodyElemCr2.addTextNode(dto.address);
        SOAPElement soapBodyElemCr3 = soapBodyElem2.addChildElement("amount", myNamespace2);
        soapBodyElemCr3.addTextNode(dto.amount);
        SOAPElement soapBodyElemCr4 = soapBodyElem2.addChildElement("applicationId", myNamespace2);
        soapBodyElemCr4.addTextNode(dto.newApplicationId);
        SOAPElement soapBodyElemCr5 = soapBodyElem2.addChildElement("associationType", myNamespace2);
        soapBodyElemCr5.addTextNode(dto.associationType);
        SOAPElement soapBodyElemCr6 = soapBodyElem2.addChildElement("cellNo", myNamespace2);
        soapBodyElemCr6.addTextNode(dto.cellNo);
        SOAPElement soapBodyElemCr7 = soapBodyElem2.addChildElement("checkerPassword", myNamespace2);
        soapBodyElemCr7.addTextNode(checkerPassword);
        SOAPElement soapBodyElemCr8 = soapBodyElem2.addChildElement("checkerUserName", myNamespace2);
        soapBodyElemCr8.addTextNode(checker);
        SOAPElement soapBodyElemCr9 = soapBodyElem2.addChildElement("cityOrDistrict", myNamespace2);
        soapBodyElemCr9.addTextNode(dto.cityOrDistrict);
        SOAPElement soapBodyElemCr10 = soapBodyElem2.addChildElement("cnicNo", myNamespace2);
        soapBodyElemCr10.addTextNode(dto.cnicNo);
        SOAPElement soapBodyElemCr11 = soapBodyElem2.addChildElement("controlBranchCode", myNamespace2);
        soapBodyElemCr11.addTextNode("7001");
        SOAPElement soapBodyElemCr12 = soapBodyElem2.addChildElement("dateOfBirth", myNamespace2);
        soapBodyElemCr12.addTextNode(dto.dateOfBirth);
        SOAPElement soapBodyElemCr13 = soapBodyElem2.addChildElement("emailAddress", myNamespace2);
        soapBodyElemCr13.addTextNode(dto.emailAddress);
        SOAPElement soapBodyElemCr14 = soapBodyElem2.addChildElement("employerAddress", myNamespace2);
        soapBodyElemCr14.addTextNode(dto.employerAddress);
        SOAPElement soapBodyElemCr15 = soapBodyElem2.addChildElement("employerBusinessCategory", myNamespace2);
        soapBodyElemCr15.addTextNode(dto.employerBusinessCategory);
        SOAPElement soapBodyElemCr16 = soapBodyElem2.addChildElement("employerCellNo", myNamespace2);
        soapBodyElemCr16.addTextNode(dto.employerCellNo);
        SOAPElement soapBodyElemCr17 = soapBodyElem2.addChildElement("employerCityOrDistrict", myNamespace2);
        soapBodyElemCr17.addTextNode(dto.employerCityOrDistrict);
        SOAPElement soapBodyElemCr18 = soapBodyElem2.addChildElement("employerCompanyName", myNamespace2);
        soapBodyElemCr18.addTextNode(dto.employerCompanyName);
        SOAPElement soapBodyElemCr19 = soapBodyElem2.addChildElement("employerEmailAddress", myNamespace2);
        soapBodyElemCr19.addTextNode(dto.employerEmailAddress);
        SOAPElement soapBodyElemCr20 = soapBodyElem2.addChildElement("employerOwnershipStatus", myNamespace2);
        soapBodyElemCr20.addTextNode(dto.employerOwnershipStatus);
        SOAPElement soapBodyElemCr21 = soapBodyElem2.addChildElement("employerPhoneNo", myNamespace2);
        soapBodyElemCr21.addTextNode(dto.employerPhoneNo);
        SOAPElement soapBodyElemCr22 = soapBodyElem2.addChildElement("fatherOrHusbandFirstName", myNamespace2);
        soapBodyElemCr22.addTextNode(dto.fatherOrHusbandFirstName);
        SOAPElement soapBodyElemCr23 = soapBodyElem2.addChildElement("fatherOrHusbandLastName", myNamespace2);
        soapBodyElemCr23.addTextNode(dto.fatherOrHusbandLastName);
        SOAPElement soapBodyElemCr24 = soapBodyElem2.addChildElement("fatherOrHusbandMiddleName", myNamespace2);
        soapBodyElemCr24.addTextNode(dto.fatherOrHusbandMiddleName);
        SOAPElement soapBodyElemCr25 = soapBodyElem2.addChildElement("firstName", myNamespace2);
        soapBodyElemCr25.addTextNode(dto.firstName);
        SOAPElement soapBodyElemCr26 = soapBodyElem2.addChildElement("gender", myNamespace2);
        soapBodyElemCr26.addTextNode(dto.gender);
        SOAPElement soapBodyElemCr27 = soapBodyElem2.addChildElement("groupId", myNamespace2);
        soapBodyElemCr27.addTextNode(dto.groupId);
        SOAPElement soapBodyElemCr28 = soapBodyElem2.addChildElement("lastName", myNamespace2);
        soapBodyElemCr28.addTextNode(dto.lastName);
        SOAPElement soapBodyElemCr29 = soapBodyElem2.addChildElement("makerPassword", myNamespace2);
        soapBodyElemCr29.addTextNode(makerPassword);
        SOAPElement soapBodyElemCr30 = soapBodyElem2.addChildElement("makerUserName", myNamespace2);
        soapBodyElemCr30.addTextNode(maker);
        SOAPElement soapBodyElemCr31 = soapBodyElem2.addChildElement("memberCode", myNamespace2);
        soapBodyElemCr31.addTextNode("706");
        SOAPElement soapBodyElemCr32 = soapBodyElem2.addChildElement("middleName", myNamespace2);
        soapBodyElemCr32.addTextNode(dto.middleName);
        SOAPElement soapBodyElemCr33 = soapBodyElem2.addChildElement("nicNoOrPassportNo", myNamespace2);
        soapBodyElemCr33.addTextNode(dto.nicNoOrPassportNo);
        SOAPElement soapBodyElemCr34 = soapBodyElem2.addChildElement("phoneNo", myNamespace2);
        soapBodyElemCr34.addTextNode(dto.phoneNo);
        SOAPElement soapBodyElemCr35 = soapBodyElem2.addChildElement("profession", myNamespace2);
        soapBodyElemCr35.addTextNode(dto.profession);
        SOAPElement soapBodyElemCr36 = soapBodyElem2.addChildElement("subBranchCode", myNamespace2);
        soapBodyElemCr36.addTextNode(dto.subBranchCode);

        // New Fields added by Zohaib Asim - Dated 03-05-2021 - Version 3.1
        SOAPElement soapBodyElemCr37 = soapBodyElem2.addChildElement("title", myNamespace2);
        soapBodyElemCr37.addTextNode(dto.title == null ? "" : dto.title);

        SOAPElement soapBodyElemCr38 = soapBodyElem2.addChildElement("ntnno", myNamespace2);
        soapBodyElemCr38.addTextNode(dto.ntnNo == null ? "" : dto.ntnNo);

        SOAPElement soapBodyElemCr39 = soapBodyElem2.addChildElement("nationalitytype", myNamespace2);
        soapBodyElemCr39.addTextNode(dto.nationalityType == null ? "" : dto.nationalityType);

        SOAPElement soapBodyElemCr40 = soapBodyElem2.addChildElement("nationality", myNamespace2);
        soapBodyElemCr40.addTextNode(dto.nationality == null ? "" : dto.nationality);

        SOAPElement soapBodyElemCr41 = soapBodyElem2.addChildElement("dependants", myNamespace2);
        soapBodyElemCr41.addTextNode(dto.dependants == null ? "" : dto.dependants);
        soapBodyElemCr41.addTextNode(dto.dependants == null ? "" : dto.dependants);

        SOAPElement soapBodyElemCr42 = soapBodyElem2.addChildElement("maritialstatus", myNamespace2);
        soapBodyElemCr42.addTextNode(dto.maritialStatus == null ? "" : dto.maritialStatus);

        SOAPElement soapBodyElemCr43 = soapBodyElem2.addChildElement("qualification", myNamespace2);
        soapBodyElemCr43.addTextNode(dto.qualification == null ? "" : dto.qualification);

        SOAPElement soapBodyElemCr44 = soapBodyElem2.addChildElement("permanentaddress", myNamespace2);
        soapBodyElemCr44.addTextNode(dto.permanentAddress == null ? "" : dto.permanentAddress);

        SOAPElement soapBodyElemCr45 = soapBodyElem2.addChildElement("permanentcity", myNamespace2);
        soapBodyElemCr45.addTextNode(dto.permanentCity == null ? "" : dto.permanentCity);

        SOAPElement soapBodyElemCr46 = soapBodyElem2.addChildElement("Designation", myNamespace2);
        soapBodyElemCr46.addTextNode(dto.designation == null ? "" : dto.designation);

        SOAPElement soapBodyElemCr47 = soapBodyElem2.addChildElement("SelfEmployed", myNamespace2);
        soapBodyElemCr47.addTextNode(dto.selfEmployed == null ? "" : dto.selfEmployed);

        SOAPElement soapBodyElemCr48 = soapBodyElem2.addChildElement("employerDistrict", myNamespace2);
        soapBodyElemCr48.addTextNode(dto.employerDistrict == null ? "" : dto.employerDistrict);

        SOAPElement soapBodyElemCr49 = soapBodyElem2.addChildElement("enquirystatus", myNamespace2);
        soapBodyElemCr49.addTextNode(dto.enquiryStatus == null ? "" : dto.enquiryStatus);

        SOAPElement soapBodyElemCr50 = soapBodyElem2.addChildElement("dateofapplication", myNamespace2);
        soapBodyElemCr50.addTextNode(dto.dateOfApplication == null ? "" : dto.dateOfApplication);

        // 1
        SOAPElement soapBodyElemCr51 = soapBodyElem2.addChildElement("CO_NIC_1", myNamespace2);
        soapBodyElemCr51.addTextNode(dto.co_nic_1 == null ? "" : dto.co_nic_1);

        SOAPElement soapBodyElemCr52 = soapBodyElem2.addChildElement("CO_CNIC_1", myNamespace2);
        soapBodyElemCr52.addTextNode(dto.co_cnic_1 == null ? "" : dto.co_cnic_1);

        SOAPElement soapBodyElemCr53 = soapBodyElem2.addChildElement("CO_FIRST_NAME_1", myNamespace2);
        soapBodyElemCr53.addTextNode(dto.co_first_name_1 == null ? "" : dto.co_first_name_1);

        SOAPElement soapBodyElemCr54 = soapBodyElem2.addChildElement("CO_MID_NAME_1", myNamespace2);
        soapBodyElemCr54.addTextNode(dto.co_mid_name_1 == null ? "" : dto.co_mid_name_1);

        SOAPElement soapBodyElemCr55 = soapBodyElem2.addChildElement("CO_LAST_NAME_1", myNamespace2);
        soapBodyElemCr55.addTextNode(dto.co_last_name_1 == null ? "" : dto.co_last_name_1);

        SOAPElement soapBodyElemCr56 = soapBodyElem2.addChildElement("CO_ASSOCIATION_1", myNamespace2);
        soapBodyElemCr56.addTextNode(dto.co_association_1 == null ? "" : dto.co_association_1);

        // 2
        SOAPElement soapBodyElemCr57 = soapBodyElem2.addChildElement("CO_NIC_2", myNamespace2);
        soapBodyElemCr57.addTextNode(dto.co_nic_2 == null ? "" : dto.co_nic_2);

        SOAPElement soapBodyElemCr58 = soapBodyElem2.addChildElement("CO_CNIC_2", myNamespace2);
        soapBodyElemCr58.addTextNode(dto.co_cnic_2 == null ? "" : dto.co_cnic_2);

        SOAPElement soapBodyElemCr59 = soapBodyElem2.addChildElement("CO_FIRST_NAME_2", myNamespace2);
        soapBodyElemCr59.addTextNode(dto.co_first_name_2 == null ? "" : dto.co_first_name_2);

        SOAPElement soapBodyElemCr60 = soapBodyElem2.addChildElement("CO_MID_NAME_2", myNamespace2);
        soapBodyElemCr60.addTextNode(dto.co_mid_name_2 == null ? "" : dto.co_mid_name_2);

        SOAPElement soapBodyElemCr61 = soapBodyElem2.addChildElement("CO_LAST_NAME_2", myNamespace2);
        soapBodyElemCr61.addTextNode(dto.co_last_name_2 == null ? "" : dto.co_last_name_2);

        SOAPElement soapBodyElemCr62 = soapBodyElem2.addChildElement("CO_ASSOCIATION_2", myNamespace2);
        soapBodyElemCr62.addTextNode(dto.co_association_2 == null ? "" : dto.co_association_2);

        // 3
        SOAPElement soapBodyElemCr63 = soapBodyElem2.addChildElement("CO_NIC_3", myNamespace2);
        soapBodyElemCr63.addTextNode(dto.co_nic_3 == null ? "" : dto.co_nic_3);

        SOAPElement soapBodyElemCr64 = soapBodyElem2.addChildElement("CO_CNIC_3", myNamespace2);
        soapBodyElemCr64.addTextNode(dto.co_cnic_3 == null ? "" : dto.co_cnic_3);

        SOAPElement soapBodyElemCr65 = soapBodyElem2.addChildElement("CO_FIRST_NAME_3", myNamespace2);
        soapBodyElemCr65.addTextNode(dto.co_first_name_3 == null ? "" : dto.co_first_name_3);

        SOAPElement soapBodyElemCr66 = soapBodyElem2.addChildElement("CO_MID_NAME_3", myNamespace2);
        soapBodyElemCr66.addTextNode(dto.co_mid_name_3 == null ? "" : dto.co_mid_name_3);

        SOAPElement soapBodyElemCr67 = soapBodyElem2.addChildElement("CO_LAST_NAME_3", myNamespace2);
        soapBodyElemCr67.addTextNode(dto.co_last_name_3 == null ? "" : dto.co_last_name_3);

        SOAPElement soapBodyElemCr68 = soapBodyElem2.addChildElement("CO_ASSOCIATION_3", myNamespace2);
        soapBodyElemCr68.addTextNode(dto.co_association_3 == null ? "" : dto.co_association_3);

        // 4
        SOAPElement soapBodyElemCr69 = soapBodyElem2.addChildElement("CO_NIC_4", myNamespace2);
        soapBodyElemCr69.addTextNode(dto.co_nic_4 == null ? "" : dto.co_nic_4);

        SOAPElement soapBodyElemCr70 = soapBodyElem2.addChildElement("CO_CNIC_4", myNamespace2);
        soapBodyElemCr70.addTextNode(dto.co_cnic_4 == null ? "" : dto.co_cnic_4);

        SOAPElement soapBodyElemCr71 = soapBodyElem2.addChildElement("CO_FIRST_NAME_4", myNamespace2);
        soapBodyElemCr71.addTextNode(dto.co_first_name_4 == null ? "" : dto.co_first_name_4);

        SOAPElement soapBodyElemCr72 = soapBodyElem2.addChildElement("CO_MID_NAME_4", myNamespace2);
        soapBodyElemCr72.addTextNode(dto.co_mid_name_4 == null ? "" : dto.co_mid_name_4);

        SOAPElement soapBodyElemCr73 = soapBodyElem2.addChildElement("CO_LAST_NAME_4", myNamespace2);
        soapBodyElemCr73.addTextNode(dto.co_last_name_4 == null ? "" : dto.co_last_name_4);

        SOAPElement soapBodyElemCr74 = soapBodyElem2.addChildElement("CO_ASSOCIATION_4", myNamespace2);
        soapBodyElemCr74.addTextNode(dto.co_association_4 == null ? "" : dto.co_association_4);

        // 5
        SOAPElement soapBodyElemCr75 = soapBodyElem2.addChildElement("CO_NIC_5", myNamespace2);
        soapBodyElemCr75.addTextNode(dto.co_nic_5 == null ? "" : dto.co_nic_5);

        SOAPElement soapBodyElemCr76 = soapBodyElem2.addChildElement("CO_CNIC_5", myNamespace2);
        soapBodyElemCr76.addTextNode(dto.co_cnic_5 == null ? "" : dto.co_cnic_5);

        SOAPElement soapBodyElemCr77 = soapBodyElem2.addChildElement("CO_FIRST_NAME_5", myNamespace2);
        soapBodyElemCr77.addTextNode(dto.co_first_name_5 == null ? "" : dto.co_first_name_5);

        SOAPElement soapBodyElemCr78 = soapBodyElem2.addChildElement("CO_MID_NAME_5", myNamespace2);
        soapBodyElemCr78.addTextNode(dto.co_mid_name_5 == null ? "" : dto.co_mid_name_5);

        SOAPElement soapBodyElemCr79 = soapBodyElem2.addChildElement("CO_LAST_NAME_5", myNamespace2);
        soapBodyElemCr79.addTextNode(dto.co_last_name_5 == null ? "" : dto.co_last_name_5);

        SOAPElement soapBodyElemCr80 = soapBodyElem2.addChildElement("CO_ASSOCIATION_5", myNamespace2);
        soapBodyElemCr80.addTextNode(dto.co_association_5 == null ? "" : dto.co_association_5);
        // End by Zohaib Asim
    }
    // End

    // Replicated Function - Dated 17-03-2022 - Re-Ordering of Elements
    private static void createSoapEnvelopeV5(SOAPMessage soapMessage, CreditBureauDto dto) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "tem";
        String myNamespaceURI = "http://tempuri.org/";
        String myNamespace2 = "dat";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        envelope.addNamespaceDeclaration(myNamespace2, "http://schemas.datacontract.org/2004/07/DataCheckEnquiry");

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="https://www.w3schools.com/xml/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <myNamespace:CelsiusToFahrenheit>
                    <myNamespace:Celsius>100</myNamespace:Celsius>
                </myNamespace:CelsiusToFahrenheit>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(cbReq, myNamespace);

        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("authKey", myNamespace);
        soapBodyElem1.addTextNode(authKey);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("cr", myNamespace);

        // Mandatory CR positioning as below (DataCheck)
        SOAPElement soapBodyElemCr56 = soapBodyElem2.addChildElement("CO_ASSOCIATION_1", myNamespace2);
        soapBodyElemCr56.addTextNode(dto.co_association_1 == null ? "" : dto.co_association_1);
        SOAPElement soapBodyElemCr62 = soapBodyElem2.addChildElement("CO_ASSOCIATION_2", myNamespace2);
        soapBodyElemCr62.addTextNode(dto.co_association_2 == null ? "" : dto.co_association_2);
        SOAPElement soapBodyElemCr68 = soapBodyElem2.addChildElement("CO_ASSOCIATION_3", myNamespace2);
        soapBodyElemCr68.addTextNode(dto.co_association_3 == null ? "" : dto.co_association_3);
        SOAPElement soapBodyElemCr74 = soapBodyElem2.addChildElement("CO_ASSOCIATION_4", myNamespace2);
        soapBodyElemCr74.addTextNode(dto.co_association_4 == null ? "" : dto.co_association_4);
        SOAPElement soapBodyElemCr80 = soapBodyElem2.addChildElement("CO_ASSOCIATION_5", myNamespace2);
        soapBodyElemCr80.addTextNode(dto.co_association_5 == null ? "" : dto.co_association_5);

        //
        SOAPElement soapBodyElemCr52 = soapBodyElem2.addChildElement("CO_CNIC_1", myNamespace2);
        soapBodyElemCr52.addTextNode(dto.co_cnic_1 == null ? "" : dto.co_cnic_1);
        SOAPElement soapBodyElemCr58 = soapBodyElem2.addChildElement("CO_CNIC_2", myNamespace2);
        soapBodyElemCr58.addTextNode(dto.co_cnic_2 == null ? "" : dto.co_cnic_2);
        SOAPElement soapBodyElemCr64 = soapBodyElem2.addChildElement("CO_CNIC_3", myNamespace2);
        soapBodyElemCr64.addTextNode(dto.co_cnic_3 == null ? "" : dto.co_cnic_3);
        SOAPElement soapBodyElemCr70 = soapBodyElem2.addChildElement("CO_CNIC_4", myNamespace2);
        soapBodyElemCr70.addTextNode(dto.co_cnic_4 == null ? "" : dto.co_cnic_4);
        SOAPElement soapBodyElemCr76 = soapBodyElem2.addChildElement("CO_CNIC_5", myNamespace2);
        soapBodyElemCr76.addTextNode(dto.co_cnic_5 == null ? "" : dto.co_cnic_5);

        //
        SOAPElement soapBodyElemCr53 = soapBodyElem2.addChildElement("CO_FIRST_NAME_1", myNamespace2);
        soapBodyElemCr53.addTextNode(dto.co_first_name_1 == null ? "" : dto.co_first_name_1);
        SOAPElement soapBodyElemCr59 = soapBodyElem2.addChildElement("CO_FIRST_NAME_2", myNamespace2);
        soapBodyElemCr59.addTextNode(dto.co_first_name_2 == null ? "" : dto.co_first_name_2);
        SOAPElement soapBodyElemCr65 = soapBodyElem2.addChildElement("CO_FIRST_NAME_3", myNamespace2);
        soapBodyElemCr65.addTextNode(dto.co_first_name_3 == null ? "" : dto.co_first_name_3);
        SOAPElement soapBodyElemCr71 = soapBodyElem2.addChildElement("CO_FIRST_NAME_4", myNamespace2);
        soapBodyElemCr71.addTextNode(dto.co_first_name_4 == null ? "" : dto.co_first_name_4);
        SOAPElement soapBodyElemCr77 = soapBodyElem2.addChildElement("CO_FIRST_NAME_5", myNamespace2);
        soapBodyElemCr77.addTextNode(dto.co_first_name_5 == null ? "" : dto.co_first_name_5);

        //
        SOAPElement soapBodyElemCr55 = soapBodyElem2.addChildElement("CO_LAST_NAME_1", myNamespace2);
        soapBodyElemCr55.addTextNode(dto.co_last_name_1 == null ? "" : dto.co_last_name_1);
        SOAPElement soapBodyElemCr61 = soapBodyElem2.addChildElement("CO_LAST_NAME_2", myNamespace2);
        soapBodyElemCr61.addTextNode(dto.co_last_name_2 == null ? "" : dto.co_last_name_2);
        SOAPElement soapBodyElemCr67 = soapBodyElem2.addChildElement("CO_LAST_NAME_3", myNamespace2);
        soapBodyElemCr67.addTextNode(dto.co_last_name_3 == null ? "" : dto.co_last_name_3);
        SOAPElement soapBodyElemCr73 = soapBodyElem2.addChildElement("CO_LAST_NAME_4", myNamespace2);
        soapBodyElemCr73.addTextNode(dto.co_last_name_4 == null ? "" : dto.co_last_name_4);
        SOAPElement soapBodyElemCr79 = soapBodyElem2.addChildElement("CO_LAST_NAME_5", myNamespace2);
        soapBodyElemCr79.addTextNode(dto.co_last_name_5 == null ? "" : dto.co_last_name_5);

        //
        SOAPElement soapBodyElemCr54 = soapBodyElem2.addChildElement("CO_MID_NAME_1", myNamespace2);
        soapBodyElemCr54.addTextNode(dto.co_mid_name_1 == null ? "" : dto.co_mid_name_1);
        SOAPElement soapBodyElemCr60 = soapBodyElem2.addChildElement("CO_MID_NAME_2", myNamespace2);
        soapBodyElemCr60.addTextNode(dto.co_mid_name_2 == null ? "" : dto.co_mid_name_2);
        SOAPElement soapBodyElemCr66 = soapBodyElem2.addChildElement("CO_MID_NAME_3", myNamespace2);
        soapBodyElemCr66.addTextNode(dto.co_mid_name_3 == null ? "" : dto.co_mid_name_3);
        SOAPElement soapBodyElemCr72 = soapBodyElem2.addChildElement("CO_MID_NAME_4", myNamespace2);
        soapBodyElemCr72.addTextNode(dto.co_mid_name_4 == null ? "" : dto.co_mid_name_4);
        SOAPElement soapBodyElemCr78 = soapBodyElem2.addChildElement("CO_MID_NAME_5", myNamespace2);
        soapBodyElemCr78.addTextNode(dto.co_mid_name_5 == null ? "" : dto.co_mid_name_5);

        //
        SOAPElement soapBodyElemCr51 = soapBodyElem2.addChildElement("CO_NIC_1", myNamespace2);
        soapBodyElemCr51.addTextNode(dto.co_nic_1 == null ? "" : dto.co_nic_1);
        SOAPElement soapBodyElemCr57 = soapBodyElem2.addChildElement("CO_NIC_2", myNamespace2);
        soapBodyElemCr57.addTextNode(dto.co_nic_2 == null ? "" : dto.co_nic_2);
        SOAPElement soapBodyElemCr63 = soapBodyElem2.addChildElement("CO_NIC_3", myNamespace2);
        soapBodyElemCr63.addTextNode(dto.co_nic_3 == null ? "" : dto.co_nic_3);
        SOAPElement soapBodyElemCr69 = soapBodyElem2.addChildElement("CO_NIC_4", myNamespace2);
        soapBodyElemCr69.addTextNode(dto.co_nic_4 == null ? "" : dto.co_nic_4);
        SOAPElement soapBodyElemCr75 = soapBodyElem2.addChildElement("CO_NIC_5", myNamespace2);
        soapBodyElemCr75.addTextNode(dto.co_nic_5 == null ? "" : dto.co_nic_5);

        //
        SOAPElement soapBodyElemCr1 = soapBodyElem2.addChildElement("accountType", myNamespace2);
        soapBodyElemCr1.addTextNode(dto.accountType);
        SOAPElement soapBodyElemCr2 = soapBodyElem2.addChildElement("address", myNamespace2);
        soapBodyElemCr2.addTextNode(dto.address);
        SOAPElement soapBodyElemCr3 = soapBodyElem2.addChildElement("amount", myNamespace2);
        soapBodyElemCr3.addTextNode(dto.amount);
        SOAPElement soapBodyElemCr4 = soapBodyElem2.addChildElement("applicationId", myNamespace2);
        soapBodyElemCr4.addTextNode(dto.newApplicationId);
        SOAPElement soapBodyElemCr5 = soapBodyElem2.addChildElement("associationType", myNamespace2);
        soapBodyElemCr5.addTextNode(dto.associationType);
        SOAPElement soapBodyElemCr6 = soapBodyElem2.addChildElement("cellNo", myNamespace2);
        soapBodyElemCr6.addTextNode(dto.cellNo);
        SOAPElement soapBodyElemCr7 = soapBodyElem2.addChildElement("checkerPassword", myNamespace2);
        soapBodyElemCr7.addTextNode(checkerPassword);
        SOAPElement soapBodyElemCr8 = soapBodyElem2.addChildElement("checkerUserName", myNamespace2);
        soapBodyElemCr8.addTextNode(checker);
        SOAPElement soapBodyElemCr9 = soapBodyElem2.addChildElement("cityOrDistrict", myNamespace2);
        soapBodyElemCr9.addTextNode(dto.cityOrDistrict);
        SOAPElement soapBodyElemCr10 = soapBodyElem2.addChildElement("cnicNo", myNamespace2);
        soapBodyElemCr10.addTextNode(dto.cnicNo);
        SOAPElement soapBodyElemCr11 = soapBodyElem2.addChildElement("controlBranchCode", myNamespace2);
        soapBodyElemCr11.addTextNode("7001");
        SOAPElement soapBodyElemCr12 = soapBodyElem2.addChildElement("dateOfBirth", myNamespace2);
        soapBodyElemCr12.addTextNode(dto.dateOfBirth);
        /*SOAPElement soapBodyElemCr13 = soapBodyElem2.addChildElement("emailAddress", myNamespace2);
        soapBodyElemCr13.addTextNode(dto.emailAddress);*/
        SOAPElement soapBodyElemCr14 = soapBodyElem2.addChildElement("employerAddress", myNamespace2);
        soapBodyElemCr14.addTextNode(dto.employerAddress);
        /*SOAPElement soapBodyElemCr15 = soapBodyElem2.addChildElement("employerBusinessCategory", myNamespace2);
        soapBodyElemCr15.addTextNode(dto.employerBusinessCategory);*/
        SOAPElement soapBodyElemCr16 = soapBodyElem2.addChildElement("employerCellNo", myNamespace2);
        soapBodyElemCr16.addTextNode(dto.employerCellNo);
        SOAPElement soapBodyElemCr17 = soapBodyElem2.addChildElement("employerCityOrDistrict", myNamespace2);
        soapBodyElemCr17.addTextNode(dto.employerCityOrDistrict);
        SOAPElement soapBodyElemCr18 = soapBodyElem2.addChildElement("employerCompanyName", myNamespace2);
        soapBodyElemCr18.addTextNode(dto.employerCompanyName);
        /*SOAPElement soapBodyElemCr19 = soapBodyElem2.addChildElement("employerEmailAddress", myNamespace2);
        soapBodyElemCr19.addTextNode(dto.employerEmailAddress);
        SOAPElement soapBodyElemCr20 = soapBodyElem2.addChildElement("employerOwnershipStatus", myNamespace2);
        soapBodyElemCr20.addTextNode(dto.employerOwnershipStatus);*/
        SOAPElement soapBodyElemCr21 = soapBodyElem2.addChildElement("employerPhoneNo", myNamespace2);
        soapBodyElemCr21.addTextNode(dto.employerPhoneNo);
        SOAPElement soapBodyElemCr22 = soapBodyElem2.addChildElement("fatherOrHusbandFirstName", myNamespace2);
        soapBodyElemCr22.addTextNode(dto.fatherOrHusbandFirstName);
        SOAPElement soapBodyElemCr23 = soapBodyElem2.addChildElement("fatherOrHusbandLastName", myNamespace2);
        soapBodyElemCr23.addTextNode(dto.fatherOrHusbandLastName);
        SOAPElement soapBodyElemCr24 = soapBodyElem2.addChildElement("fatherOrHusbandMiddleName", myNamespace2);
        soapBodyElemCr24.addTextNode(dto.fatherOrHusbandMiddleName);
        SOAPElement soapBodyElemCr25 = soapBodyElem2.addChildElement("firstName", myNamespace2);
        soapBodyElemCr25.addTextNode(dto.firstName);
        SOAPElement soapBodyElemCr26 = soapBodyElem2.addChildElement("gender", myNamespace2);
        soapBodyElemCr26.addTextNode(dto.gender);
        SOAPElement soapBodyElemCr27 = soapBodyElem2.addChildElement("groupId", myNamespace2);
        soapBodyElemCr27.addTextNode(dto.groupId);
        SOAPElement soapBodyElemCr28 = soapBodyElem2.addChildElement("lastName", myNamespace2);
        soapBodyElemCr28.addTextNode(dto.lastName);
        SOAPElement soapBodyElemCr29 = soapBodyElem2.addChildElement("makerPassword", myNamespace2);
        soapBodyElemCr29.addTextNode(makerPassword);
        SOAPElement soapBodyElemCr30 = soapBodyElem2.addChildElement("makerUserName", myNamespace2);
        soapBodyElemCr30.addTextNode(maker);
        SOAPElement soapBodyElemCr31 = soapBodyElem2.addChildElement("memberCode", myNamespace2);
        soapBodyElemCr31.addTextNode("706");
        SOAPElement soapBodyElemCr32 = soapBodyElem2.addChildElement("middleName", myNamespace2);
        soapBodyElemCr32.addTextNode(dto.middleName);
        SOAPElement soapBodyElemCr33 = soapBodyElem2.addChildElement("nicNoOrPassportNo", myNamespace2);
        soapBodyElemCr33.addTextNode(dto.nicNoOrPassportNo);
        SOAPElement soapBodyElemCr34 = soapBodyElem2.addChildElement("phoneNo", myNamespace2);
        soapBodyElemCr34.addTextNode(dto.phoneNo);
        SOAPElement soapBodyElemCr35 = soapBodyElem2.addChildElement("profession", myNamespace2);
        soapBodyElemCr35.addTextNode(dto.profession);
        SOAPElement soapBodyElemCr36 = soapBodyElem2.addChildElement("subBranchCode", myNamespace2);
        soapBodyElemCr36.addTextNode(dto.subBranchCode);
        SOAPElement soapBodyElemCr37 = soapBodyElem2.addChildElement("title", myNamespace2);
        soapBodyElemCr37.addTextNode(dto.title == null ? "" : dto.title);
        SOAPElement soapBodyElemCr38 = soapBodyElem2.addChildElement("ntnno", myNamespace2);
        soapBodyElemCr38.addTextNode(dto.ntnNo == null ? "" : dto.ntnNo);
        SOAPElement soapBodyElemCr39 = soapBodyElem2.addChildElement("nationalitytype", myNamespace2);
        soapBodyElemCr39.addTextNode(dto.nationalityType == null ? "" : dto.nationalityType);
        SOAPElement soapBodyElemCr40 = soapBodyElem2.addChildElement("nationality", myNamespace2);
        soapBodyElemCr40.addTextNode(dto.nationality == null ? "" : dto.nationality);
        SOAPElement soapBodyElemCr41 = soapBodyElem2.addChildElement("dependants", myNamespace2);
        soapBodyElemCr41.addTextNode(dto.dependants == null ? "" : dto.dependants);
        SOAPElement soapBodyElemCr42 = soapBodyElem2.addChildElement("maritialstatus", myNamespace2);
        soapBodyElemCr42.addTextNode(dto.maritialStatus == null ? "" : dto.maritialStatus);
        SOAPElement soapBodyElemCr43 = soapBodyElem2.addChildElement("qualification", myNamespace2);
        soapBodyElemCr43.addTextNode(dto.qualification == null ? "" : dto.qualification);
        SOAPElement soapBodyElemCr44 = soapBodyElem2.addChildElement("permanentaddress", myNamespace2);
        soapBodyElemCr44.addTextNode(dto.permanentAddress == null ? "" : dto.permanentAddress);
        SOAPElement soapBodyElemCr45 = soapBodyElem2.addChildElement("permanentcity", myNamespace2);
        soapBodyElemCr45.addTextNode(dto.permanentCity == null ? "" : dto.permanentCity);
        SOAPElement soapBodyElemCr46 = soapBodyElem2.addChildElement("Designation", myNamespace2);
        soapBodyElemCr46.addTextNode(dto.designation == null ? "" : dto.designation);
        SOAPElement soapBodyElemCr47 = soapBodyElem2.addChildElement("SelfEmployed", myNamespace2);
        soapBodyElemCr47.addTextNode(dto.selfEmployed == null ? "" : dto.selfEmployed);
        SOAPElement soapBodyElemCr48 = soapBodyElem2.addChildElement("employerDistrict", myNamespace2);
        soapBodyElemCr48.addTextNode(dto.employerDistrict == null ? "" : dto.employerDistrict);
        SOAPElement soapBodyElemCr49 = soapBodyElem2.addChildElement("enquirystatus", myNamespace2);
        soapBodyElemCr49.addTextNode(dto.enquiryStatus == null ? "" : dto.enquiryStatus);
        SOAPElement soapBodyElemCr50 = soapBodyElem2.addChildElement("dateofapplication", myNamespace2);
        soapBodyElemCr50.addTextNode(dto.dateOfApplication == null ? "" : dto.dateOfApplication);
    }

    private static SOAPMessage callSoapWebService(String soapEndpointUrl, String soapAction, CreditBureauDto dto) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, dto), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            //System.out.println();

            soapConnection.close();
            return soapResponse;
        } catch (Exception e) {
            System.err.println(
                    "\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
        return null;
    }

    private static SOAPMessage createSOAPRequest(String soapAction, CreditBureauDto dto) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // createSoapEnvelope(soapMessage, dto);
        createSoapEnvelopeV5(soapMessage, dto);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    private static void createSoapEnvelope2(SOAPMessage soapMessage, CreditBureauDto dto) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "tem";
        String myNamespaceURI = "http://tempuri.org/";
        String myNamespace2 = "dat";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        envelope.addNamespaceDeclaration(myNamespace2, "http://schemas.datacontract.org/2004/07/DataCheckEnquiry");

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="https://www.w3schools.com/xml/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <myNamespace:CelsiusToFahrenheit>
                    <myNamespace:Celsius>100</myNamespace:Celsius>
                </myNamespace:CelsiusToFahrenheit>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();

        // Modified by Zohaib Asim - Dated 04-05-2021 - Version 3.1
        SOAPElement soapBodyElem = soapBody.addChildElement("getBureauCreditReportV3_1", myNamespace);
        // SOAPElement soapBodyElem = soapBody.addChildElement(cbReq, myNamespace);

        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("authKey", myNamespace);
        soapBodyElem1.addTextNode(authKey);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("cr", myNamespace);
        SOAPElement soapBodyElemCr1 = soapBodyElem2.addChildElement("accountType", myNamespace2);
        soapBodyElemCr1.addTextNode(dto.accountType);
        SOAPElement soapBodyElemCr2 = soapBodyElem2.addChildElement("address", myNamespace2);
        soapBodyElemCr2.addTextNode(dto.address);
        SOAPElement soapBodyElemCr3 = soapBodyElem2.addChildElement("amount", myNamespace2);
        soapBodyElemCr3.addTextNode(dto.amount);
        SOAPElement soapBodyElemCr4 = soapBodyElem2.addChildElement("applicationId", myNamespace2);
        soapBodyElemCr4.addTextNode(dto.newApplicationId);
        SOAPElement soapBodyElemCr5 = soapBodyElem2.addChildElement("associationType", myNamespace2);
        soapBodyElemCr5.addTextNode(dto.associationType);
        SOAPElement soapBodyElemCr6 = soapBodyElem2.addChildElement("cellNo", myNamespace2);
        soapBodyElemCr6.addTextNode(dto.cellNo);
        SOAPElement soapBodyElemCr7 = soapBodyElem2.addChildElement("checkerPassword", myNamespace2);
        soapBodyElemCr7.addTextNode(checkerPassword);
        SOAPElement soapBodyElemCr8 = soapBodyElem2.addChildElement("checkerUserName", myNamespace2);
        soapBodyElemCr8.addTextNode(checker);
        SOAPElement soapBodyElemCr9 = soapBodyElem2.addChildElement("cityOrDistrict", myNamespace2);
        soapBodyElemCr9.addTextNode(dto.cityOrDistrict);
        SOAPElement soapBodyElemCr10 = soapBodyElem2.addChildElement("cnicNo", myNamespace2);
        soapBodyElemCr10.addTextNode(dto.cnicNo);
        SOAPElement soapBodyElemCr11 = soapBodyElem2.addChildElement("controlBranchCode", myNamespace2);
        soapBodyElemCr11.addTextNode("7001");
        SOAPElement soapBodyElemCr12 = soapBodyElem2.addChildElement("dateOfBirth", myNamespace2);
        soapBodyElemCr12.addTextNode(dto.dateOfBirth);
        SOAPElement soapBodyElemCr13 = soapBodyElem2.addChildElement("emailAddress", myNamespace2);
        soapBodyElemCr13.addTextNode(dto.emailAddress);
        SOAPElement soapBodyElemCr14 = soapBodyElem2.addChildElement("employerAddress", myNamespace2);
        soapBodyElemCr14.addTextNode(dto.employerAddress);
        SOAPElement soapBodyElemCr15 = soapBodyElem2.addChildElement("employerBusinessCategory", myNamespace2);
        soapBodyElemCr15.addTextNode(dto.employerBusinessCategory);
        SOAPElement soapBodyElemCr16 = soapBodyElem2.addChildElement("employerCellNo", myNamespace2);
        soapBodyElemCr16.addTextNode(dto.employerCellNo);
        SOAPElement soapBodyElemCr17 = soapBodyElem2.addChildElement("employerCityOrDistrict", myNamespace2);
        soapBodyElemCr17.addTextNode(dto.employerCityOrDistrict);
        SOAPElement soapBodyElemCr18 = soapBodyElem2.addChildElement("employerCompanyName", myNamespace2);
        soapBodyElemCr18.addTextNode(dto.employerCompanyName);
        SOAPElement soapBodyElemCr19 = soapBodyElem2.addChildElement("employerEmailAddress", myNamespace2);
        soapBodyElemCr19.addTextNode(dto.employerEmailAddress);
        SOAPElement soapBodyElemCr20 = soapBodyElem2.addChildElement("employerOwnershipStatus", myNamespace2);
        soapBodyElemCr20.addTextNode(dto.employerOwnershipStatus);
        SOAPElement soapBodyElemCr21 = soapBodyElem2.addChildElement("employerPhoneNo", myNamespace2);
        soapBodyElemCr21.addTextNode(dto.employerPhoneNo);
        SOAPElement soapBodyElemCr22 = soapBodyElem2.addChildElement("fatherOrHusbandFirstName", myNamespace2);
        soapBodyElemCr22.addTextNode(dto.fatherOrHusbandFirstName);
        SOAPElement soapBodyElemCr23 = soapBodyElem2.addChildElement("fatherOrHusbandLastName", myNamespace2);
        soapBodyElemCr23.addTextNode(dto.fatherOrHusbandLastName);
        SOAPElement soapBodyElemCr24 = soapBodyElem2.addChildElement("fatherOrHusbandMiddleName", myNamespace2);
        soapBodyElemCr24.addTextNode(dto.fatherOrHusbandMiddleName);
        SOAPElement soapBodyElemCr25 = soapBodyElem2.addChildElement("firstName", myNamespace2);
        soapBodyElemCr25.addTextNode(dto.firstName);
        SOAPElement soapBodyElemCr26 = soapBodyElem2.addChildElement("gender", myNamespace2);
        soapBodyElemCr26.addTextNode(dto.gender);
        SOAPElement soapBodyElemCr27 = soapBodyElem2.addChildElement("groupId", myNamespace2);
        soapBodyElemCr27.addTextNode(dto.groupId);
        SOAPElement soapBodyElemCr28 = soapBodyElem2.addChildElement("lastName", myNamespace2);
        soapBodyElemCr28.addTextNode(dto.lastName);
        SOAPElement soapBodyElemCr29 = soapBodyElem2.addChildElement("makerPassword", myNamespace2);
        soapBodyElemCr29.addTextNode(makerPassword);
        SOAPElement soapBodyElemCr30 = soapBodyElem2.addChildElement("makerUserName", myNamespace2);
        soapBodyElemCr30.addTextNode(maker);
        SOAPElement soapBodyElemCr31 = soapBodyElem2.addChildElement("memberCode", myNamespace2);
        soapBodyElemCr31.addTextNode("706");
        SOAPElement soapBodyElemCr32 = soapBodyElem2.addChildElement("middleName", myNamespace2);
        soapBodyElemCr32.addTextNode(dto.middleName);
        SOAPElement soapBodyElemCr33 = soapBodyElem2.addChildElement("nicNoOrPassportNo", myNamespace2);
        soapBodyElemCr33.addTextNode(dto.nicNoOrPassportNo);
        SOAPElement soapBodyElemCr34 = soapBodyElem2.addChildElement("phoneNo", myNamespace2);
        soapBodyElemCr34.addTextNode(dto.phoneNo);
        SOAPElement soapBodyElemCr35 = soapBodyElem2.addChildElement("profession", myNamespace2);
        soapBodyElemCr35.addTextNode(dto.profession);
        SOAPElement soapBodyElemCr36 = soapBodyElem2.addChildElement("subBranchCode", myNamespace2);
        soapBodyElemCr36.addTextNode(dto.subBranchCode);

        // New Fields added by Zohaib Asim - Dated 03-05-2021 - Version 3.1
        SOAPElement soapBodyElemCr37 = soapBodyElem2.addChildElement("title", myNamespace2);
        soapBodyElemCr37.addTextNode(dto.title == null ? "" : dto.title);

        SOAPElement soapBodyElemCr38 = soapBodyElem2.addChildElement("ntnno", myNamespace2);
        soapBodyElemCr38.addTextNode(dto.ntnNo == null ? "" : dto.ntnNo);

        SOAPElement soapBodyElemCr39 = soapBodyElem2.addChildElement("nationalitytype", myNamespace2);
        soapBodyElemCr39.addTextNode(dto.nationalityType == null ? "" : dto.nationalityType);

        SOAPElement soapBodyElemCr40 = soapBodyElem2.addChildElement("nationality", myNamespace2);
        soapBodyElemCr40.addTextNode(dto.nationality == null ? "" : dto.nationality);

        SOAPElement soapBodyElemCr41 = soapBodyElem2.addChildElement("dependants", myNamespace2);
        soapBodyElemCr41.addTextNode(dto.dependants == null ? "" : dto.dependants);
        soapBodyElemCr41.addTextNode(dto.dependants == null ? "" : dto.dependants);

        SOAPElement soapBodyElemCr42 = soapBodyElem2.addChildElement("maritialstatus", myNamespace2);
        soapBodyElemCr42.addTextNode(dto.maritialStatus == null ? "" : dto.maritialStatus);

        SOAPElement soapBodyElemCr43 = soapBodyElem2.addChildElement("qualification", myNamespace2);
        soapBodyElemCr43.addTextNode(dto.qualification == null ? "" : dto.qualification);

        SOAPElement soapBodyElemCr44 = soapBodyElem2.addChildElement("permanentaddress", myNamespace2);
        soapBodyElemCr44.addTextNode(dto.permanentAddress == null ? "" : dto.permanentAddress);

        SOAPElement soapBodyElemCr45 = soapBodyElem2.addChildElement("permanentcity", myNamespace2);
        soapBodyElemCr45.addTextNode(dto.permanentCity == null ? "" : dto.permanentCity);

        SOAPElement soapBodyElemCr46 = soapBodyElem2.addChildElement("Designation", myNamespace2);
        soapBodyElemCr46.addTextNode(dto.designation == null ? "" : dto.designation);

        SOAPElement soapBodyElemCr47 = soapBodyElem2.addChildElement("SelfEmployed", myNamespace2);
        soapBodyElemCr47.addTextNode(dto.selfEmployed == null ? "" : dto.selfEmployed);

        SOAPElement soapBodyElemCr48 = soapBodyElem2.addChildElement("employerDistrict", myNamespace2);
        soapBodyElemCr48.addTextNode(dto.employerDistrict == null ? "" : dto.employerDistrict);

        SOAPElement soapBodyElemCr49 = soapBodyElem2.addChildElement("enquirystatus", myNamespace2);
        soapBodyElemCr49.addTextNode(dto.enquiryStatus == null ? "" : dto.enquiryStatus);

        SOAPElement soapBodyElemCr50 = soapBodyElem2.addChildElement("dateofapplication", myNamespace2);
        soapBodyElemCr50.addTextNode(dto.dateOfApplication == null ? "" : dto.dateOfApplication);

        // 1
        SOAPElement soapBodyElemCr51 = soapBodyElem2.addChildElement("CO_NIC_1", myNamespace2);
        soapBodyElemCr51.addTextNode(dto.co_nic_1 == null ? "" : dto.co_nic_1);

        SOAPElement soapBodyElemCr52 = soapBodyElem2.addChildElement("CO_CNIC_1", myNamespace2);
        soapBodyElemCr52.addTextNode(dto.co_cnic_1 == null ? "" : dto.co_cnic_1);

        SOAPElement soapBodyElemCr53 = soapBodyElem2.addChildElement("CO_FIRST_NAME_1", myNamespace2);
        soapBodyElemCr53.addTextNode(dto.co_first_name_1 == null ? "" : dto.co_first_name_1);

        SOAPElement soapBodyElemCr54 = soapBodyElem2.addChildElement("CO_MID_NAME_1", myNamespace2);
        soapBodyElemCr54.addTextNode(dto.co_mid_name_1 == null ? "" : dto.co_mid_name_1);

        SOAPElement soapBodyElemCr55 = soapBodyElem2.addChildElement("CO_LAST_NAME_1", myNamespace2);
        soapBodyElemCr55.addTextNode(dto.co_last_name_1 == null ? "" : dto.co_last_name_1);

        SOAPElement soapBodyElemCr56 = soapBodyElem2.addChildElement("CO_ASSOCIATION_1", myNamespace2);
        soapBodyElemCr56.addTextNode(dto.co_association_1 == null ? "" : dto.co_association_1);

        // 2
        SOAPElement soapBodyElemCr57 = soapBodyElem2.addChildElement("CO_NIC_2", myNamespace2);
        soapBodyElemCr57.addTextNode(dto.co_nic_2 == null ? "" : dto.co_nic_2);

        SOAPElement soapBodyElemCr58 = soapBodyElem2.addChildElement("CO_CNIC_2", myNamespace2);
        soapBodyElemCr58.addTextNode(dto.co_cnic_2 == null ? "" : dto.co_cnic_2);

        SOAPElement soapBodyElemCr59 = soapBodyElem2.addChildElement("CO_FIRST_NAME_2", myNamespace2);
        soapBodyElemCr59.addTextNode(dto.co_first_name_2 == null ? "" : dto.co_first_name_2);

        SOAPElement soapBodyElemCr60 = soapBodyElem2.addChildElement("CO_MID_NAME_2", myNamespace2);
        soapBodyElemCr60.addTextNode(dto.co_mid_name_2 == null ? "" : dto.co_mid_name_2);

        SOAPElement soapBodyElemCr61 = soapBodyElem2.addChildElement("CO_LAST_NAME_2", myNamespace2);
        soapBodyElemCr61.addTextNode(dto.co_last_name_2 == null ? "" : dto.co_last_name_2);

        SOAPElement soapBodyElemCr62 = soapBodyElem2.addChildElement("CO_ASSOCIATION_2", myNamespace2);
        soapBodyElemCr62.addTextNode(dto.co_association_2 == null ? "" : dto.co_association_2);

        // 3
        SOAPElement soapBodyElemCr63 = soapBodyElem2.addChildElement("CO_NIC_3", myNamespace2);
        soapBodyElemCr63.addTextNode(dto.co_nic_3 == null ? "" : dto.co_nic_3);

        SOAPElement soapBodyElemCr64 = soapBodyElem2.addChildElement("CO_CNIC_3", myNamespace2);
        soapBodyElemCr64.addTextNode(dto.co_cnic_3 == null ? "" : dto.co_cnic_3);

        SOAPElement soapBodyElemCr65 = soapBodyElem2.addChildElement("CO_FIRST_NAME_3", myNamespace2);
        soapBodyElemCr65.addTextNode(dto.co_first_name_3 == null ? "" : dto.co_first_name_3);

        SOAPElement soapBodyElemCr66 = soapBodyElem2.addChildElement("CO_MID_NAME_3", myNamespace2);
        soapBodyElemCr66.addTextNode(dto.co_mid_name_3 == null ? "" : dto.co_mid_name_3);

        SOAPElement soapBodyElemCr67 = soapBodyElem2.addChildElement("CO_LAST_NAME_3", myNamespace2);
        soapBodyElemCr67.addTextNode(dto.co_last_name_3 == null ? "" : dto.co_last_name_3);

        SOAPElement soapBodyElemCr68 = soapBodyElem2.addChildElement("CO_ASSOCIATION_3", myNamespace2);
        soapBodyElemCr68.addTextNode(dto.co_association_3 == null ? "" : dto.co_association_3);

        // 4
        SOAPElement soapBodyElemCr69 = soapBodyElem2.addChildElement("CO_NIC_4", myNamespace2);
        soapBodyElemCr69.addTextNode(dto.co_nic_4 == null ? "" : dto.co_nic_4);

        SOAPElement soapBodyElemCr70 = soapBodyElem2.addChildElement("CO_CNIC_4", myNamespace2);
        soapBodyElemCr70.addTextNode(dto.co_cnic_4 == null ? "" : dto.co_cnic_4);

        SOAPElement soapBodyElemCr71 = soapBodyElem2.addChildElement("CO_FIRST_NAME_4", myNamespace2);
        soapBodyElemCr71.addTextNode(dto.co_first_name_4 == null ? "" : dto.co_first_name_4);

        SOAPElement soapBodyElemCr72 = soapBodyElem2.addChildElement("CO_MID_NAME_4", myNamespace2);
        soapBodyElemCr72.addTextNode(dto.co_mid_name_4 == null ? "" : dto.co_mid_name_4);

        SOAPElement soapBodyElemCr73 = soapBodyElem2.addChildElement("CO_LAST_NAME_4", myNamespace2);
        soapBodyElemCr73.addTextNode(dto.co_last_name_4 == null ? "" : dto.co_last_name_4);

        SOAPElement soapBodyElemCr74 = soapBodyElem2.addChildElement("CO_ASSOCIATION_4", myNamespace2);
        soapBodyElemCr74.addTextNode(dto.co_association_4 == null ? "" : dto.co_association_4);

        // 5
        SOAPElement soapBodyElemCr75 = soapBodyElem2.addChildElement("CO_NIC_5", myNamespace2);
        soapBodyElemCr75.addTextNode(dto.co_nic_5 == null ? "" : dto.co_nic_5);

        SOAPElement soapBodyElemCr76 = soapBodyElem2.addChildElement("CO_CNIC_5", myNamespace2);
        soapBodyElemCr76.addTextNode(dto.co_cnic_5 == null ? "" : dto.co_cnic_5);

        SOAPElement soapBodyElemCr77 = soapBodyElem2.addChildElement("CO_FIRST_NAME_5", myNamespace2);
        soapBodyElemCr77.addTextNode(dto.co_first_name_5 == null ? "" : dto.co_first_name_5);

        SOAPElement soapBodyElemCr78 = soapBodyElem2.addChildElement("CO_MID_NAME_5", myNamespace2);
        soapBodyElemCr78.addTextNode(dto.co_mid_name_5 == null ? "" : dto.co_mid_name_5);

        SOAPElement soapBodyElemCr79 = soapBodyElem2.addChildElement("CO_LAST_NAME_5", myNamespace2);
        soapBodyElemCr79.addTextNode(dto.co_last_name_5 == null ? "" : dto.co_last_name_5);

        SOAPElement soapBodyElemCr80 = soapBodyElem2.addChildElement("CO_ASSOCIATION_5", myNamespace2);
        soapBodyElemCr80.addTextNode(dto.co_association_5 == null ? "" : dto.co_association_5);
        // End by Zohaib Asim
    }

    // Replicated Function - Dated 17-03-2022 - Re-Ordering of Elements
    private static void createSoapEnvelopeV52(SOAPMessage soapMessage, CreditBureauDto dto) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "tem";
        String myNamespaceURI = "http://tempuri.org/";
        String myNamespace2 = "dat";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        envelope.addNamespaceDeclaration(myNamespace2, "http://schemas.datacontract.org/2004/07/DataCheckEnquiry");

        /*
        Constructed SOAP Request Message:
        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="https://www.w3schools.com/xml/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <myNamespace:CelsiusToFahrenheit>
                    <myNamespace:Celsius>100</myNamespace:Celsius>
                </myNamespace:CelsiusToFahrenheit>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>
        */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(cbReq, myNamespace);

        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("authKey", myNamespace);
        soapBodyElem1.addTextNode(authKey);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("cr", myNamespace);

        // Mandatory CR positioning as below (DataCheck)
        SOAPElement soapBodyElemCr56 = soapBodyElem2.addChildElement("CO_ASSOCIATION_1", myNamespace2);
        soapBodyElemCr56.addTextNode(dto.co_association_1 == null ? "" : dto.co_association_1);
        SOAPElement soapBodyElemCr62 = soapBodyElem2.addChildElement("CO_ASSOCIATION_2", myNamespace2);
        soapBodyElemCr62.addTextNode(dto.co_association_2 == null ? "" : dto.co_association_2);
        SOAPElement soapBodyElemCr68 = soapBodyElem2.addChildElement("CO_ASSOCIATION_3", myNamespace2);
        soapBodyElemCr68.addTextNode(dto.co_association_3 == null ? "" : dto.co_association_3);
        SOAPElement soapBodyElemCr74 = soapBodyElem2.addChildElement("CO_ASSOCIATION_4", myNamespace2);
        soapBodyElemCr74.addTextNode(dto.co_association_4 == null ? "" : dto.co_association_4);
        SOAPElement soapBodyElemCr80 = soapBodyElem2.addChildElement("CO_ASSOCIATION_5", myNamespace2);
        soapBodyElemCr80.addTextNode(dto.co_association_5 == null ? "" : dto.co_association_5);

        //
        SOAPElement soapBodyElemCr52 = soapBodyElem2.addChildElement("CO_CNIC_1", myNamespace2);
        soapBodyElemCr52.addTextNode(dto.co_cnic_1 == null ? "" : dto.co_cnic_1);
        SOAPElement soapBodyElemCr58 = soapBodyElem2.addChildElement("CO_CNIC_2", myNamespace2);
        soapBodyElemCr58.addTextNode(dto.co_cnic_2 == null ? "" : dto.co_cnic_2);
        SOAPElement soapBodyElemCr64 = soapBodyElem2.addChildElement("CO_CNIC_3", myNamespace2);
        soapBodyElemCr64.addTextNode(dto.co_cnic_3 == null ? "" : dto.co_cnic_3);
        SOAPElement soapBodyElemCr70 = soapBodyElem2.addChildElement("CO_CNIC_4", myNamespace2);
        soapBodyElemCr70.addTextNode(dto.co_cnic_4 == null ? "" : dto.co_cnic_4);
        SOAPElement soapBodyElemCr76 = soapBodyElem2.addChildElement("CO_CNIC_5", myNamespace2);
        soapBodyElemCr76.addTextNode(dto.co_cnic_5 == null ? "" : dto.co_cnic_5);

        //
        SOAPElement soapBodyElemCr53 = soapBodyElem2.addChildElement("CO_FIRST_NAME_1", myNamespace2);
        soapBodyElemCr53.addTextNode(dto.co_first_name_1 == null ? "" : dto.co_first_name_1);
        SOAPElement soapBodyElemCr59 = soapBodyElem2.addChildElement("CO_FIRST_NAME_2", myNamespace2);
        soapBodyElemCr59.addTextNode(dto.co_first_name_2 == null ? "" : dto.co_first_name_2);
        SOAPElement soapBodyElemCr65 = soapBodyElem2.addChildElement("CO_FIRST_NAME_3", myNamespace2);
        soapBodyElemCr65.addTextNode(dto.co_first_name_3 == null ? "" : dto.co_first_name_3);
        SOAPElement soapBodyElemCr71 = soapBodyElem2.addChildElement("CO_FIRST_NAME_4", myNamespace2);
        soapBodyElemCr71.addTextNode(dto.co_first_name_4 == null ? "" : dto.co_first_name_4);
        SOAPElement soapBodyElemCr77 = soapBodyElem2.addChildElement("CO_FIRST_NAME_5", myNamespace2);
        soapBodyElemCr77.addTextNode(dto.co_first_name_5 == null ? "" : dto.co_first_name_5);

        //
        SOAPElement soapBodyElemCr55 = soapBodyElem2.addChildElement("CO_LAST_NAME_1", myNamespace2);
        soapBodyElemCr55.addTextNode(dto.co_last_name_1 == null ? "" : dto.co_last_name_1);
        SOAPElement soapBodyElemCr61 = soapBodyElem2.addChildElement("CO_LAST_NAME_2", myNamespace2);
        soapBodyElemCr61.addTextNode(dto.co_last_name_2 == null ? "" : dto.co_last_name_2);
        SOAPElement soapBodyElemCr67 = soapBodyElem2.addChildElement("CO_LAST_NAME_3", myNamespace2);
        soapBodyElemCr67.addTextNode(dto.co_last_name_3 == null ? "" : dto.co_last_name_3);
        SOAPElement soapBodyElemCr73 = soapBodyElem2.addChildElement("CO_LAST_NAME_4", myNamespace2);
        soapBodyElemCr73.addTextNode(dto.co_last_name_4 == null ? "" : dto.co_last_name_4);
        SOAPElement soapBodyElemCr79 = soapBodyElem2.addChildElement("CO_LAST_NAME_5", myNamespace2);
        soapBodyElemCr79.addTextNode(dto.co_last_name_5 == null ? "" : dto.co_last_name_5);

        //
        SOAPElement soapBodyElemCr54 = soapBodyElem2.addChildElement("CO_MID_NAME_1", myNamespace2);
        soapBodyElemCr54.addTextNode(dto.co_mid_name_1 == null ? "" : dto.co_mid_name_1);
        SOAPElement soapBodyElemCr60 = soapBodyElem2.addChildElement("CO_MID_NAME_2", myNamespace2);
        soapBodyElemCr60.addTextNode(dto.co_mid_name_2 == null ? "" : dto.co_mid_name_2);
        SOAPElement soapBodyElemCr66 = soapBodyElem2.addChildElement("CO_MID_NAME_3", myNamespace2);
        soapBodyElemCr66.addTextNode(dto.co_mid_name_3 == null ? "" : dto.co_mid_name_3);
        SOAPElement soapBodyElemCr72 = soapBodyElem2.addChildElement("CO_MID_NAME_4", myNamespace2);
        soapBodyElemCr72.addTextNode(dto.co_mid_name_4 == null ? "" : dto.co_mid_name_4);
        SOAPElement soapBodyElemCr78 = soapBodyElem2.addChildElement("CO_MID_NAME_5", myNamespace2);
        soapBodyElemCr78.addTextNode(dto.co_mid_name_5 == null ? "" : dto.co_mid_name_5);

        //
        SOAPElement soapBodyElemCr51 = soapBodyElem2.addChildElement("CO_NIC_1", myNamespace2);
        soapBodyElemCr51.addTextNode(dto.co_nic_1 == null ? "" : dto.co_nic_1);
        SOAPElement soapBodyElemCr57 = soapBodyElem2.addChildElement("CO_NIC_2", myNamespace2);
        soapBodyElemCr57.addTextNode(dto.co_nic_2 == null ? "" : dto.co_nic_2);
        SOAPElement soapBodyElemCr63 = soapBodyElem2.addChildElement("CO_NIC_3", myNamespace2);
        soapBodyElemCr63.addTextNode(dto.co_nic_3 == null ? "" : dto.co_nic_3);
        SOAPElement soapBodyElemCr69 = soapBodyElem2.addChildElement("CO_NIC_4", myNamespace2);
        soapBodyElemCr69.addTextNode(dto.co_nic_4 == null ? "" : dto.co_nic_4);
        SOAPElement soapBodyElemCr75 = soapBodyElem2.addChildElement("CO_NIC_5", myNamespace2);
        soapBodyElemCr75.addTextNode(dto.co_nic_5 == null ? "" : dto.co_nic_5);

        //
        SOAPElement soapBodyElemCr1 = soapBodyElem2.addChildElement("accountType", myNamespace2);
        soapBodyElemCr1.addTextNode(dto.accountType);
        SOAPElement soapBodyElemCr2 = soapBodyElem2.addChildElement("address", myNamespace2);
        soapBodyElemCr2.addTextNode(dto.address);
        SOAPElement soapBodyElemCr3 = soapBodyElem2.addChildElement("amount", myNamespace2);
        soapBodyElemCr3.addTextNode(dto.amount);
        SOAPElement soapBodyElemCr4 = soapBodyElem2.addChildElement("applicationId", myNamespace2);
        soapBodyElemCr4.addTextNode(dto.newApplicationId);
        SOAPElement soapBodyElemCr5 = soapBodyElem2.addChildElement("associationType", myNamespace2);
        soapBodyElemCr5.addTextNode(dto.associationType);
        SOAPElement soapBodyElemCr6 = soapBodyElem2.addChildElement("cellNo", myNamespace2);
        soapBodyElemCr6.addTextNode(dto.cellNo);
        SOAPElement soapBodyElemCr7 = soapBodyElem2.addChildElement("checkerPassword", myNamespace2);
        soapBodyElemCr7.addTextNode(checkerPassword);
        SOAPElement soapBodyElemCr8 = soapBodyElem2.addChildElement("checkerUserName", myNamespace2);
        soapBodyElemCr8.addTextNode(checker);
        SOAPElement soapBodyElemCr9 = soapBodyElem2.addChildElement("cityOrDistrict", myNamespace2);
        soapBodyElemCr9.addTextNode(dto.cityOrDistrict);
        SOAPElement soapBodyElemCr10 = soapBodyElem2.addChildElement("cnicNo", myNamespace2);
        soapBodyElemCr10.addTextNode(dto.cnicNo);
        SOAPElement soapBodyElemCr11 = soapBodyElem2.addChildElement("controlBranchCode", myNamespace2);
        soapBodyElemCr11.addTextNode("7001");
        SOAPElement soapBodyElemCr12 = soapBodyElem2.addChildElement("dateOfBirth", myNamespace2);
        soapBodyElemCr12.addTextNode(dto.dateOfBirth);
        /*SOAPElement soapBodyElemCr13 = soapBodyElem2.addChildElement("emailAddress", myNamespace2);
        soapBodyElemCr13.addTextNode(dto.emailAddress);*/
        SOAPElement soapBodyElemCr14 = soapBodyElem2.addChildElement("employerAddress", myNamespace2);
        soapBodyElemCr14.addTextNode(dto.employerAddress);
        /*SOAPElement soapBodyElemCr15 = soapBodyElem2.addChildElement("employerBusinessCategory", myNamespace2);
        soapBodyElemCr15.addTextNode(dto.employerBusinessCategory);*/
        SOAPElement soapBodyElemCr16 = soapBodyElem2.addChildElement("employerCellNo", myNamespace2);
        soapBodyElemCr16.addTextNode(dto.employerCellNo);
        SOAPElement soapBodyElemCr17 = soapBodyElem2.addChildElement("employerCityOrDistrict", myNamespace2);
        soapBodyElemCr17.addTextNode(dto.employerCityOrDistrict);
        SOAPElement soapBodyElemCr18 = soapBodyElem2.addChildElement("employerCompanyName", myNamespace2);
        soapBodyElemCr18.addTextNode(dto.employerCompanyName);
        /*SOAPElement soapBodyElemCr19 = soapBodyElem2.addChildElement("employerEmailAddress", myNamespace2);
        soapBodyElemCr19.addTextNode(dto.employerEmailAddress);
        SOAPElement soapBodyElemCr20 = soapBodyElem2.addChildElement("employerOwnershipStatus", myNamespace2);
        soapBodyElemCr20.addTextNode(dto.employerOwnershipStatus);*/
        SOAPElement soapBodyElemCr21 = soapBodyElem2.addChildElement("employerPhoneNo", myNamespace2);
        soapBodyElemCr21.addTextNode(dto.employerPhoneNo);
        SOAPElement soapBodyElemCr22 = soapBodyElem2.addChildElement("fatherOrHusbandFirstName", myNamespace2);
        soapBodyElemCr22.addTextNode(dto.fatherOrHusbandFirstName);
        SOAPElement soapBodyElemCr23 = soapBodyElem2.addChildElement("fatherOrHusbandLastName", myNamespace2);
        soapBodyElemCr23.addTextNode(dto.fatherOrHusbandLastName);
        SOAPElement soapBodyElemCr24 = soapBodyElem2.addChildElement("fatherOrHusbandMiddleName", myNamespace2);
        soapBodyElemCr24.addTextNode(dto.fatherOrHusbandMiddleName);
        SOAPElement soapBodyElemCr25 = soapBodyElem2.addChildElement("firstName", myNamespace2);
        soapBodyElemCr25.addTextNode(dto.firstName);
        SOAPElement soapBodyElemCr26 = soapBodyElem2.addChildElement("gender", myNamespace2);
        soapBodyElemCr26.addTextNode(dto.gender);
        SOAPElement soapBodyElemCr27 = soapBodyElem2.addChildElement("groupId", myNamespace2);
        soapBodyElemCr27.addTextNode(dto.groupId);
        SOAPElement soapBodyElemCr28 = soapBodyElem2.addChildElement("lastName", myNamespace2);
        soapBodyElemCr28.addTextNode(dto.lastName);
        SOAPElement soapBodyElemCr29 = soapBodyElem2.addChildElement("makerPassword", myNamespace2);
        soapBodyElemCr29.addTextNode(makerPassword);
        SOAPElement soapBodyElemCr30 = soapBodyElem2.addChildElement("makerUserName", myNamespace2);
        soapBodyElemCr30.addTextNode(maker);
        SOAPElement soapBodyElemCr31 = soapBodyElem2.addChildElement("memberCode", myNamespace2);
        soapBodyElemCr31.addTextNode("706");
        SOAPElement soapBodyElemCr32 = soapBodyElem2.addChildElement("middleName", myNamespace2);
        soapBodyElemCr32.addTextNode(dto.middleName);
        SOAPElement soapBodyElemCr33 = soapBodyElem2.addChildElement("nicNoOrPassportNo", myNamespace2);
        soapBodyElemCr33.addTextNode(dto.nicNoOrPassportNo);
        SOAPElement soapBodyElemCr34 = soapBodyElem2.addChildElement("phoneNo", myNamespace2);
        soapBodyElemCr34.addTextNode(dto.phoneNo);
        SOAPElement soapBodyElemCr35 = soapBodyElem2.addChildElement("profession", myNamespace2);
        soapBodyElemCr35.addTextNode(dto.profession);
        SOAPElement soapBodyElemCr36 = soapBodyElem2.addChildElement("subBranchCode", myNamespace2);
        soapBodyElemCr36.addTextNode(dto.subBranchCode);
        SOAPElement soapBodyElemCr37 = soapBodyElem2.addChildElement("title", myNamespace2);
        soapBodyElemCr37.addTextNode(dto.title == null ? "" : dto.title);
        SOAPElement soapBodyElemCr38 = soapBodyElem2.addChildElement("ntnno", myNamespace2);
        soapBodyElemCr38.addTextNode(dto.ntnNo == null ? "" : dto.ntnNo);
        SOAPElement soapBodyElemCr39 = soapBodyElem2.addChildElement("nationalitytype", myNamespace2);
        soapBodyElemCr39.addTextNode(dto.nationalityType == null ? "" : dto.nationalityType);
        SOAPElement soapBodyElemCr40 = soapBodyElem2.addChildElement("nationality", myNamespace2);
        soapBodyElemCr40.addTextNode(dto.nationality == null ? "" : dto.nationality);
        SOAPElement soapBodyElemCr41 = soapBodyElem2.addChildElement("dependants", myNamespace2);
        soapBodyElemCr41.addTextNode(dto.dependants == null ? "" : dto.dependants);
        SOAPElement soapBodyElemCr42 = soapBodyElem2.addChildElement("maritialstatus", myNamespace2);
        soapBodyElemCr42.addTextNode(dto.maritialStatus == null ? "" : dto.maritialStatus);
        SOAPElement soapBodyElemCr43 = soapBodyElem2.addChildElement("qualification", myNamespace2);
        soapBodyElemCr43.addTextNode(dto.qualification == null ? "" : dto.qualification);
        SOAPElement soapBodyElemCr44 = soapBodyElem2.addChildElement("permanentaddress", myNamespace2);
        soapBodyElemCr44.addTextNode(dto.permanentAddress == null ? "" : dto.permanentAddress);
        SOAPElement soapBodyElemCr45 = soapBodyElem2.addChildElement("permanentcity", myNamespace2);
        soapBodyElemCr45.addTextNode(dto.permanentCity == null ? "" : dto.permanentCity);
        SOAPElement soapBodyElemCr46 = soapBodyElem2.addChildElement("Designation", myNamespace2);
        soapBodyElemCr46.addTextNode(dto.designation == null ? "" : dto.designation);
        SOAPElement soapBodyElemCr47 = soapBodyElem2.addChildElement("SelfEmployed", myNamespace2);
        soapBodyElemCr47.addTextNode(dto.selfEmployed == null ? "" : dto.selfEmployed);
        SOAPElement soapBodyElemCr48 = soapBodyElem2.addChildElement("employerDistrict", myNamespace2);
        soapBodyElemCr48.addTextNode(dto.employerDistrict == null ? "" : dto.employerDistrict);
        SOAPElement soapBodyElemCr49 = soapBodyElem2.addChildElement("enquirystatus", myNamespace2);
        soapBodyElemCr49.addTextNode(dto.enquiryStatus == null ? "" : dto.enquiryStatus);
        SOAPElement soapBodyElemCr50 = soapBodyElem2.addChildElement("dateofapplication", myNamespace2);
        soapBodyElemCr50.addTextNode(dto.dateOfApplication == null ? "" : dto.dateOfApplication);
    }

    private static SOAPMessage callSoapWebService2(String soapEndpointUrl, String soapAction, CreditBureauDto dto) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, dto), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            //System.out.println();

            soapConnection.close();
            return soapResponse;
        } catch (Exception e) {
            System.err.println(
                    "\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
        return null;
    }

    /* Added by Zohaib Asim
       Dated:   15-03-2022
       Reason:  Integrating DataCheck - V5 API */
    @Transactional
    @Timed
    public String getBureauCreditReport(CreditBureauDto dto) {
        //
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        //
        List<MwLoanAppCrdtSumry> loanAppCrdtSumryList = new ArrayList<>();

        //
        String query = "select * from data_check_cred where active=1";
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        if (result.size() > 0) {
            Object[] st = result.get(0);
            this.url = st[0].toString();
            this.authKey = st[1].toString();
            this.maker = st[2].toString();
            this.makerPassword = st[3].toString();
            this.checker = st[4].toString();
            this.checkerPassword = st[5].toString();
        } else {
            return "";
        }

        //
        log.info("DataCheck : CNIC/Application -> " + dto.cnicNo
                + "/" + dto.applicationId);
        List<MwLoanAppDoc> loanAppDocList = null;
        Instant dateForComparison = Instant.now().minus(30, ChronoUnit.DAYS);
        if (dto.reqFor.equals("Client")) {
            log.info("MFCIBOfLast30Days Comparison - Client -> " + dateForComparison);
            // For Single Call - Nominee Information Available
            if (dto.co_cnic_1 != null && !dto.co_cnic_1.isEmpty()) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(dto.cnicNo), Long.parseLong(dto.co_cnic_1), 0L, true, dateForComparison, "DATACHECK");
                // Verify - In-Active Records, if there is any inquiry took place
                if (loanAppDocList.size() == 0) {
                    loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                            Long.parseLong(dto.cnicNo), Long.parseLong(dto.co_cnic_1), 0L, dateForComparison, "DATACHECK");
                }
            } else {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumIsNullAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(dto.cnicNo), 0L, true, dateForComparison, "DATACHECK");
                // Verify - In-Active Records, if there is any inquiry took place
                if (loanAppDocList.size() == 0) {
                    loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                            Long.parseLong(dto.cnicNo), 0L, dateForComparison, "DATACHECK");
                }
            }
        } else if (dto.reqFor.equals("Nominee")) {
            log.info("MFCIBOfLast30Days  Comparison - Nominee -> " + dateForComparison);
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumIsNullAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(dto.cnicNo), -1L, true, dateForComparison, "DATACHECK");
            // Verify - In-Active Records, if there is any inquiry took place
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(dto.cnicNo), -1L, dateForComparison, "DATACHECK");
            }
        } else if (dto.reqFor.equals("Pdc")) {
            log.info("MFCIBOfLast30Days  Comparison - Co-Borrower -> " + dateForComparison);
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumIsNullAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(dto.cnicNo), -2L, true, dateForComparison, "DATACHECK");
            // Verify - In-Active Records, if there is any inquiry took place
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(dto.cnicNo), -2L, dateForComparison, "DATACHECK");
            }
        }

        // Data Exists
        if (loanAppDocList != null && loanAppDocList.size() > 0) {
            log.info("DataCheck: Database Object Returned");
            // In Case Loan App Seq is changed but Doc Image
            if (!dto.applicationId.equals(loanAppDocList.get(0).getLoanAppSeq())) {
                // Get Detail of Credit Summary
                List<MwLoanAppCrdtSumry> loanAppCrdtSumries =
                        mwLoanAppCrdtSumryRepository.findAllByLoanAppDocSeqAndCrntRecFlg(loanAppDocList.get(0).getLoanAppDocSeq(), true);

                //
                /***if (exLoanAppDoc.getCrntRecFlg() == true) {
                 // Existing Loan Detail
                 exLoanAppDoc.setCrntRecFlg(false);
                 exLoanAppDoc.setDelFlg(true);
                 exLoanAppDoc.setLastUpdDt(Instant.now());
                 exLoanAppDoc.setLastUpdBy(user);

                 mwLoanAppDocRepository.save(exLoanAppDoc);

                 // Summary Detail to Update
                 if (loanAppCrdtSumries.size() > 0) {
                 for (MwLoanAppCrdtSumry loanAppCrdtSumry : loanAppCrdtSumries) {
                 loanAppCrdtSumry.setCrntRecFlg(false);
                 loanAppCrdtSumry.setLastUpdDt(Instant.now());
                 loanAppCrdtSumry.setLastUpdBy(user);

                 loanAppCrdtSumryList.add(loanAppCrdtSumry);
                 }
                 mwLoanAppCrdtSumryRepository.save(loanAppCrdtSumryList);
                 loanAppCrdtSumryList = null;
                 }
                 }***/

                // Loan App Doc Sequence
                Long loanAppDocSeq = 0L;
                Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_LOAN_APP_DOC")
                        .setParameter("userId", user);
                Object tblSeqRes = qry.getSingleResult();

                if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                    loanAppDocSeq = Long.parseLong(tblSeqRes.toString());
                }

                // Loan App Doc Details to Update
                MwLoanAppDoc mwLoanAppDoc = new MwLoanAppDoc();
                mwLoanAppDoc.setLoanAppDocSeq(loanAppDocSeq);
                mwLoanAppDoc.setEffStartDt(loanAppDocList.get(0).getEffStartDt());
                mwLoanAppDoc.setDocSeq(loanAppDocList.get(0).getDocSeq());

                // Added by Zohaib Asim - Dated 16-07-2022 - Temporary Fix for Empty Value
                if (loanAppDocList.get(0).getDocImg().contains("\"LOAN_ABOVE_10K\":\"\"")) {
                    loanAppDocList.get(0).setDocImg(loanAppDocList.get(0).getDocImg().replace("\"LOAN_ABOVE_10K\":\"\"", "\"LOAN_ABOVE_10K\":\"0\""));
                }

                if (loanAppDocList.get(0).getDocImg().contains("\"DEFAULT_COUNT\":\"\"")) {
                    loanAppDocList.get(0).setDocImg(loanAppDocList.get(0).getDocImg().replace("\"DEFAULT_COUNT\":\"\"", "\"DEFAULT_COUNT\":\"0\""));
                }
                // End

                mwLoanAppDoc.setDocImg(loanAppDocList.get(0).getDocImg());
                mwLoanAppDoc.setCnicNum(loanAppDocList.get(0).getCnicNum());
                mwLoanAppDoc.setNomCnicNum(loanAppDocList.get(0).getNomCnicNum());
                mwLoanAppDoc.setCompanyNm(loanAppDocList.get(0).getCompanyNm());
                mwLoanAppDoc.setLoanAppSeq(Long.parseLong(dto.applicationId));
                mwLoanAppDoc.setCrntRecFlg(true);
                mwLoanAppDoc.setDelFlg(false);
                mwLoanAppDoc.setCrtdDt(Instant.now());
                mwLoanAppDoc.setCrtdBy(user);
                mwLoanAppDocRepository.save(mwLoanAppDoc);

                //
                if (loanAppCrdtSumries.size() > 0) {
                    loanAppCrdtSumryList = new ArrayList<>();

                    for (MwLoanAppCrdtSumry loanAppCrdtSumry : loanAppCrdtSumries) {
                        MwLoanAppCrdtSumry mwLoanAppCrdtSumry = new MwLoanAppCrdtSumry();

                        mwLoanAppCrdtSumry.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppCrdtSumry.setCsCtgry(loanAppCrdtSumry.getCsCtgry());
                        mwLoanAppCrdtSumry.setCsNm(loanAppCrdtSumry.getCsNm());
                        mwLoanAppCrdtSumry.setCsCnicNum(loanAppCrdtSumry.getCsCnicNum());
                        mwLoanAppCrdtSumry.setLoanCount(loanAppCrdtSumry.getLoanCount());
                        mwLoanAppCrdtSumry.setLoanLimit(loanAppCrdtSumry.getLoanLimit());
                        mwLoanAppCrdtSumry.setLoanOst(loanAppCrdtSumry.getLoanOst());
                        mwLoanAppCrdtSumry.setLoanLess10K(loanAppCrdtSumry.getLoanLess10K());
                        mwLoanAppCrdtSumry.setLoanAbove10K(loanAppCrdtSumry.getLoanAbove10K());
                        mwLoanAppCrdtSumry.setCurrent30Plus(loanAppCrdtSumry.getCurrent30Plus());
                        mwLoanAppCrdtSumry.setCurrent60Plus(loanAppCrdtSumry.getCurrent60Plus());
                        mwLoanAppCrdtSumry.setCurrent90Plus(loanAppCrdtSumry.getCurrent90Plus());
                        mwLoanAppCrdtSumry.setCloseWithinMaturity(loanAppCrdtSumry.getCloseWithinMaturity());
                        mwLoanAppCrdtSumry.setCloseAfterMaturity(loanAppCrdtSumry.getCloseAfterMaturity());
                        mwLoanAppCrdtSumry.setDefaultCount(loanAppCrdtSumry.getDefaultCount());
                        mwLoanAppCrdtSumry.setDefaultOst(loanAppCrdtSumry.getDefaultOst());
                        mwLoanAppCrdtSumry.setMonth2430Plus(loanAppCrdtSumry.getMonth2430Plus());
                        mwLoanAppCrdtSumry.setMonth2460Plus(loanAppCrdtSumry.getMonth2460Plus());
                        mwLoanAppCrdtSumry.setMonth2490Plus(loanAppCrdtSumry.getMonth2490Plus());
                        mwLoanAppCrdtSumry.setEnquiryCount(loanAppCrdtSumry.getEnquiryCount());
                        mwLoanAppCrdtSumry.setCrntRecFlg(true);
                        mwLoanAppCrdtSumry.setCrtdDt(Instant.now());
                        mwLoanAppCrdtSumry.setCrtdBy(user);

                        loanAppCrdtSumryList.add(mwLoanAppCrdtSumry);
                    }
                    mwLoanAppCrdtSumryRepository.save(loanAppCrdtSumryList);
                    loanAppCrdtSumryList = null;
                }
            }
            // End
            return loanAppDocList.get(0).getDocImg().toString();
        }
        String soapEndpointUrl = url;
        String soapAction = "http://tempuri.org/IService1/" + cbReq;

        String jsonPrettyPrintString = "";

        Date startDt = new Date();
        SOAPMessage msg = callSoapWebService(soapEndpointUrl, soapAction, dto);
        log.info("DataCheckSoap: Call started at " + startDt + " ended at " + new Date() + ", CNIC: " + dto.cnicNo);


        JSONObject report = new JSONObject();
        Map<String, Map> resp = new HashMap<String, Map>();
        JSONObject respObject = new JSONObject();
        try {
            // log.debug( "===============================1==============================" );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            msg.writeTo(out);
            String strMsg = new String(out.toByteArray());
            // log.debug( dto.toString() );
            // log.debug( "===============================2==============================" );
            JSONObject xmlJSONObj = XML.toJSONObject(strMsg);
            jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

            // System.out.println( jsonPrettyPrintString );
            // log.debug( "===============================3==============================" );
            JSONObject env = xmlJSONObj.getJSONObject("s:Envelope");
            JSONObject body = env.getJSONObject("s:Body");

            JSONObject getBureauCreditReportResponse = body.getJSONObject(cbResp);
            JSONObject getBureauCreditReportResult = getBureauCreditReportResponse.getJSONObject(cbRslt);
            JSONObject cr = getBureauCreditReportResult.getJSONObject("a:CRNewResponse");
            // End

            String reportStr = cr.getString("a:Report");
            String status = cr.getString("a:Status");
            /*log.debug(reportStr);
            log.debug("===============================4==============================");
            log.debug(status);
            log.debug("===============================5==============================");*/

            report = XML.toJSONObject(reportStr);
            String reportJSONStr = report.toString(PRETTY_PRINT_INDENT_FACTOR);
            log.debug(reportJSONStr);
            /*log.debug("===============================6==============================");*/

            // Loan App Doc Sequence
            Long seq = 0L;
            Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_LOAN_APP_DOC")
                    .setParameter("userId", user);
            Object tblSeqRes = qry.getSingleResult();

            if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                seq = Long.parseLong(tblSeqRes.toString());
            }

            if (status.equals("CR")) {
                JSONObject root = report.getJSONObject("ROOT");
                if (root != null) {
                    JSONObject individualDetail = new JSONObject();
                    Object individualDetailObj = root.get("INDIVIDUAL_DETAIL");
                    if (individualDetailObj instanceof JSONArray) {
                        // JSON Array
                        JSONArray arr = (JSONArray) individualDetailObj;
                        for (int y = 0; y < arr.length(); y++) {
                            JSONObject dtl = arr.getJSONObject(y);
                            String isSelf = dtl.getString("IS_SELF");
                            if (isSelf.trim().toLowerCase().equals("y")) {
                                individualDetail = dtl;
                            }
                        }
                    } else if (individualDetailObj instanceof JSONObject) {
                        // JSON Object
                        individualDetail = (JSONObject) individualDetailObj;
                    }
                    root.remove("INDIVIDUAL_DETAIL");
                    root.put("INDIVIDUAL_DETAIL", individualDetail);
                    JSONArray cpp_master = new JSONArray();
                    Object cppMaster = root.get("CCP_MASTER");
                    if (cppMaster instanceof JSONArray) {
                        // JSON Array
                        cpp_master = (JSONArray) cppMaster;
                    } else if (cppMaster instanceof JSONObject) {
                        // JSON Object
                        JSONObject obj = (JSONObject) cppMaster;
                        cpp_master.put(obj);
                    }

                    //
                    JSONArray cpp_detail = new JSONArray();
                    Object cppDetail = root.get("CCP_DETAIL");
                    if (cppDetail instanceof JSONArray) {
                        // JSON Array
                        cpp_detail = (JSONArray) cppDetail;
                    } else if (cppDetail instanceof JSONObject) {
                        // JSON Object
                        JSONObject obj = (JSONObject) cppDetail;
                        cpp_detail.put(obj);
                    }

                    //
                    JSONArray summary = new JSONArray();
                    Object sumObj = root.get("CCP_SUMMARY");
                    if (sumObj instanceof JSONArray) {
                        // JSON Array
                        summary = (JSONArray) sumObj;
                    } else if (sumObj instanceof JSONObject) {
                        // JSON Object
                        JSONObject obj = (JSONObject) sumObj;
                        summary.put(obj);
                    }
                    if (cpp_master != null) {
                        for (int i = 0; i < cpp_master.length(); i++) {
                            JSONObject master = cpp_master.getJSONObject(i);
                            JSONArray details = new JSONArray();
                            Long seq_no = (master.get("SEQ_NO").toString().length() > 0)
                                    ? Long.parseLong(master.get("SEQ_NO").toString())
                                    : 0L;
                            for (int y = 0; y < cpp_detail.length(); y++) {
                                JSONObject detail = cpp_detail.getJSONObject(y);
                                Long dseq_no = (detail.get("SEQ_NO").toString().length() > 0)
                                        ? Long.parseLong(detail.get("SEQ_NO").toString())
                                        : 0L;
                                if (dseq_no.longValue() == seq_no.longValue()) {
                                    details.put(detail);
                                }
                            }
                            master.put("CCP_DETAIL", details);
                            for (int z = 0; z < summary.length(); z++) {
                                JSONObject smry = summary.getJSONObject(z);
                                Long sseq_no = (smry.get("SEQ_NO").toString().length() > 0)
                                        ? Long.parseLong(smry.get("SEQ_NO").toString())
                                        : 0L;
                                if (sseq_no.longValue() == seq_no.longValue()) {
                                    master.put("CCP_SUMMARY", smry);
                                }
                            }
                        }
                        root.remove("CCP_MASTER");
                        root.put("CCP_MASTER", cpp_master);

                        //
                        JSONArray enquiries = new JSONArray();
                        Object enquiriesObj = root.get("ENQUIRIES");
                        if (enquiriesObj instanceof JSONArray) {
                            // JSON Array
                            enquiries = (JSONArray) enquiriesObj;
                        } else if (enquiriesObj instanceof JSONObject) {
                            // JSON Object
                            JSONObject obj = (JSONObject) enquiriesObj;
                            enquiries.put(obj);
                        }
                        root.remove("ENQUIRIES");
                        root.put("ENQUIRIES", enquiries);
                    }

                    //
                    JSONArray defaults = new JSONArray();
                    Object defaultObj = root.get("DEFAULTS");
                    if (defaultObj instanceof JSONArray) {
                        // JSON Array
                        defaults = (JSONArray) defaultObj;
                    } else if (defaultObj instanceof JSONObject) {
                        // JSON Object
                        JSONObject obj = (JSONObject) defaultObj;
                        defaults.put(obj);
                    }
                    root.remove("DEFAULTS");
                    root.put("DEFAULTS", defaults);

                    //
                    JSONArray home = new JSONArray();
                    Object homeObj = root.get("HOME_INFORMATION");
                    if (homeObj instanceof JSONArray) {
                        // JSON Array
                        home = (JSONArray) homeObj;
                    } else if (homeObj instanceof JSONObject) {
                        // JSON Object
                        JSONObject obj = (JSONObject) homeObj;
                        home.put(obj);
                    }
                    root.remove("HOME_INFORMATION");
                    root.put("HOME_INFORMATION", home);

                    //
                    JSONArray cppSummaryTotal = new JSONArray();
                    Object cppSummaryTotalObj = root.get("CCP_SUMMARY_TOTAL");
                    if (cppSummaryTotalObj instanceof JSONArray) {
                        // JSON Array
                        cppSummaryTotal = (JSONArray) cppSummaryTotalObj;
                        root.remove("CCP_SUMMARY_TOTAL");
                        root.put("CCP_SUMMARY_TOTAL", cppSummaryTotal.getJSONObject(0));
                    }

                    //
                    JSONArray creditSummary = new JSONArray();
                    Object creditSummaryObj = root.get("CREDIT_SUMMARY");
                    if (creditSummaryObj instanceof JSONArray) {
                        // JSON Array
                        creditSummary = (JSONArray) creditSummaryObj;
                    } else if (creditSummaryObj instanceof JSONObject) {
                        // JSON Object
                        JSONObject obj = (JSONObject) creditSummaryObj;
                        creditSummary.put(obj);
                    }
                    /*
                        Added by Yousaf (store Credit Summary in DB)
                        Dated: 24-Mar-2022
                     */

                    for (int i = 0; i < creditSummary.length(); i++) {
                        MwLoanAppCrdtSumry mwLoanAppCrdtSumry = new MwLoanAppCrdtSumry();
                        mwLoanAppCrdtSumry.setLoanAppDocSeq(seq);
                        mwLoanAppCrdtSumry.setCsCtgry(creditSummary.getJSONObject(i).getString("CATEGORY"));
                        mwLoanAppCrdtSumry.setCsNm(creditSummary.getJSONObject(i).getString("NAME"));
                        mwLoanAppCrdtSumry.setCsCnicNum(creditSummary.getJSONObject(i).getLong("CNIC"));
                        mwLoanAppCrdtSumry.setLoanCount(creditSummary.getJSONObject(i).has("LOAN_COUNT") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("LOAN_COUNT")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("LOAN_COUNT")) : "0");
                        mwLoanAppCrdtSumry.setLoanLimit(creditSummary.getJSONObject(i).has("LOAN_LIMIT") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("LOAN_LIMIT")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("LOAN_LIMIT")) : "0");
                        mwLoanAppCrdtSumry.setLoanOst(creditSummary.getJSONObject(i).has("LOAN_OS") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("LOAN_OS")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("LOAN_OS")) : "0");
                        mwLoanAppCrdtSumry.setLoanLess10K(creditSummary.getJSONObject(i).has("LOAN_LESS_10K") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("LOAN_LESS_10K")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("LOAN_LESS_10K")) : "0");
                        mwLoanAppCrdtSumry.setLoanAbove10K(creditSummary.getJSONObject(i).has("LOAN_ABOVE_10K") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("LOAN_ABOVE_10K")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("LOAN_ABOVE_10K")) : "0");

                        // Added by Zohaib Asim - Dated 16-07-2022 - Temporary Fix for Empty Value
                        if (creditSummary.getJSONObject(i).has("LOAN_ABOVE_10K") &&
                                String.valueOf(creditSummary.getJSONObject(i).get("LOAN_ABOVE_10K")).isEmpty()) {
                            creditSummary.getJSONObject(i).put("LOAN_ABOVE_10K", "0");
                        }
                        // End

                        mwLoanAppCrdtSumry.setCurrent30Plus(creditSummary.getJSONObject(i).has("CURRENT_30PLUS") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("CURRENT_30PLUS")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("CURRENT_30PLUS")) : "0");
                        mwLoanAppCrdtSumry.setCurrent60Plus(creditSummary.getJSONObject(i).has("CURRENT_60PLUS") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("CURRENT_60PLUS")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("CURRENT_60PLUS")) : "0");
                        mwLoanAppCrdtSumry.setCurrent90Plus(creditSummary.getJSONObject(i).has("CURRENT_90PLUS") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("CURRENT_90PLUS")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("CURRENT_90PLUS")) : "0");
                        mwLoanAppCrdtSumry.setCloseWithinMaturity(creditSummary.getJSONObject(i).has("CLOSE_WITHIN_MATURITY") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("CLOSE_WITHIN_MATURITY")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("CLOSE_WITHIN_MATURITY")) : "0");
                        mwLoanAppCrdtSumry.setCloseAfterMaturity(creditSummary.getJSONObject(i).has("CLOSE_AFTER_MATURITY") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("CLOSE_AFTER_MATURITY")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("CLOSE_AFTER_MATURITY")) : "0");
                        mwLoanAppCrdtSumry.setDefaultCount(creditSummary.getJSONObject(i).has("DEFAULT_COUNT") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("DEFAULT_COUNT")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("DEFAULT_COUNT")) : "0");

                        // Added by Zohaib Asim - Dated 16-07-2022 - Temporary Fix for Empty Value
                        if (creditSummary.getJSONObject(i).has("DEFAULT_COUNT") &&
                                String.valueOf(creditSummary.getJSONObject(i).get("DEFAULT_COUNT")).isEmpty()) {
                            creditSummary.getJSONObject(i).put("DEFAULT_COUNT", "0");
                        }
                        // End

                        mwLoanAppCrdtSumry.setDefaultOst(creditSummary.getJSONObject(i).has("DEFAULT_OS") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("DEFAULT_OS")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("DEFAULT_OS")) : "0");
                        mwLoanAppCrdtSumry.setMonth2430Plus(creditSummary.getJSONObject(i).has("MONTH24_30PLUS") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("MONTH24_30PLUS")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("MONTH24_30PLUS")) : "0");
                        mwLoanAppCrdtSumry.setMonth2460Plus(creditSummary.getJSONObject(i).has("MONTH24_60PLUS") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("MONTH24_60PLUS")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("MONTH24_60PLUS")) : "0");
                        mwLoanAppCrdtSumry.setMonth2490Plus(creditSummary.getJSONObject(i).has("MONTH24_90PLUS") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("MONTH24_90PLUS")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("MONTH24_90PLUS")) : "0");
                        mwLoanAppCrdtSumry.setEnquiryCount(creditSummary.getJSONObject(i).has("ENQUIRY_COUNT") &&
                                !String.valueOf(creditSummary.getJSONObject(i).get("ENQUIRY_COUNT")).isEmpty() ?
                                String.valueOf(creditSummary.getJSONObject(i).get("ENQUIRY_COUNT")) : "0");
                        mwLoanAppCrdtSumry.setCrntRecFlg(true);
                        mwLoanAppCrdtSumry.setCrtdDt(Instant.now());
                        mwLoanAppCrdtSumry.setCrtdBy(user);
                        mwLoanAppCrdtSumry.setLastUpdDt(Instant.now());
                        mwLoanAppCrdtSumry.setLastUpdBy(user);
                        loanAppCrdtSumryList.add(mwLoanAppCrdtSumry);
                    }
                    root.remove("CREDIT_SUMMARY");
                    root.put("CREDIT_SUMMARY", creditSummary);

                    report.remove("ROOT");
                    report.put("ROOT", root);
                }

                Map<String, Map> resp1 = new HashMap<String, Map>();
                report.put("status", status);
                respObject.put("status", report);
                Map<String, Object> reportMap1 = Common.jsonToMap(report);
                resp1.put("report", reportMap1);
                respObject.put("report", reportMap1);
                MwLoanAppDoc doc = new MwLoanAppDoc();
                // String cnicNum = dto.cnicNo;

                doc.setCrntRecFlg(true);
                doc.setCrtdBy("CB");
                doc.setCrtdDt(Instant.now());
                doc.setDelFlg(false);
                doc.setDocImg(respObject.toString());

                if (dto.reqFor.equals("Client"))
                    doc.setDocSeq(0L);
                else if (dto.reqFor.equals("Nominee"))
                    doc.setDocSeq(-1L);
                else if (dto.reqFor.equals("Pdc"))
                    doc.setDocSeq(-2L);

                doc.setEffStartDt(Instant.now());
                doc.setLoanAppDocSeq(seq);
                doc.setLoanAppSeq(Long.parseLong(dto.applicationId));

                // Condition added by Zohaib Asim - Dated 09-03-2021
                doc.setCnicNum(Long.parseLong(dto.cnicNo));

                if (dto.co_cnic_1 != null && !dto.co_cnic_1.isEmpty()) {
                    doc.setNomCnicNum(Long.parseLong(dto.co_cnic_1));
                }

                if (dto.reqFor.equals("Client")) {
                    log.info("MFCIBOfLast30Days Comparison - Client -> " + dateForComparison);

                    // For Single Call - Nominee Information Available
                    if (dto.co_cnic_1 != null && !dto.co_cnic_1.isEmpty()) {
                        loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                Long.parseLong(dto.cnicNo), Long.parseLong(dto.co_cnic_1), 0L, true, dateForComparison, "DATACHECK");
                        // Verify - In-Active Records, if there is any inquiry took place
                        if (loanAppDocList.size() == 0) {
                            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                    Long.parseLong(dto.cnicNo), Long.parseLong(dto.co_cnic_1), 0L, dateForComparison, "DATACHECK");
                        }
                    } else {
                        loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                Long.parseLong(dto.cnicNo), 0L, true, dateForComparison, "DATACHECK");
                        // Verify - In-Active Records, if there is any inquiry took place
                        if (loanAppDocList.size() == 0) {
                            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                    Long.parseLong(dto.cnicNo), 0L, dateForComparison, "DATACHECK");
                        }
                    }
                } else if (dto.reqFor.equals("Nominee")) {
                    log.info("MFCIBOfLast30Days  Comparison - Nominee -> " + dateForComparison);
                    loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                            Long.parseLong(dto.cnicNo), -1L, true, dateForComparison, "DATACHECK");
                    // Verify - In-Active Records, if there is any inquiry took place
                    if (loanAppDocList.size() == 0) {
                        loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                Long.parseLong(dto.cnicNo), -1L, dateForComparison, "DATACHECK");
                    }
                } else if (dto.reqFor.equals("Pdc")) {
                    log.info("MFCIBOfLast30Days  Comparison - Co-Borrower -> " + dateForComparison);
                    loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                            Long.parseLong(dto.cnicNo), -2L, true, dateForComparison, "DATACHECK");
                    // Verify - In-Active Records, if there is any inquiry took place
                    if (loanAppDocList.size() == 0) {
                        loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                Long.parseLong(dto.cnicNo), -2L, dateForComparison, "DATACHECK");
                    }
                }

                if (loanAppDocList != null && loanAppDocList.size() > 0) {
                    return loanAppDocList.get(0).getDocImg().toString();
                }
                doc.setCompanyNm("DATACHECK");
                mwLoanAppDocRepository.save(doc);
                mwLoanAppCrdtSumryRepository.save(loanAppCrdtSumryList);

            } else if (status.equals("DBRF-TR1")) {
                String randomCh = randomString(5);
                dto.newApplicationId = randomCh + dto.applicationId;
                return getBureauCreditReport(dto);
            }

            report.put("status", status);
            Map<String, Object> reportMap = Common.jsonToMap(report);

            resp.put("report", reportMap);
            respObject.put("status", report);
            respObject.put("report", report);

        } catch (SOAPException | IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return respObject.toString();
    }

    /*
     *
     * */
    @Transactional
    public String getBureauCreditReportWithParsing(CreditBureauDto dto) throws ParseException, JSONException, Exception {
        log.info("Datacheck - Process Started at " + Instant.now());
        log.info("DataCheck : CNIC/Application -> " + dto.cnicNo
                + "/" + dto.applicationId);

        //
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        //
        List<MwLoanAppCrdtSumry> loanAppCrdtSumryList = new ArrayList<>();
        List<MwLoanAppMfcibData> loanAppMfcibDataList = new ArrayList<>();

        //
        String query = "select * from data_check_cred where active=1";
        Query q = em.createNativeQuery(query);
        List<Object[]> result = q.getResultList();
        if (result.size() > 0) {
            Object[] st = result.get(0);
            this.url = st[0].toString();
            this.authKey = st[1].toString();
            this.maker = st[2].toString();
            this.makerPassword = st[3].toString();
            this.checker = st[4].toString();
            this.checkerPassword = st[5].toString();
        } else {
            return "";
        }

        //
        List<MwLoanAppDoc> loanAppDocList = null;
        Instant dateForComparison = Instant.now().minus(30, ChronoUnit.DAYS);
        if (dto.reqFor.equals("Client")) {
            log.info("MFCIBOfLast30Days Comparison - Client -> " + dateForComparison);
            // For Single Call - Nominee Information Available
            if (dto.co_cnic_1 != null && !dto.co_cnic_1.isEmpty()) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(dto.cnicNo), Long.parseLong(dto.co_cnic_1), 0L, true, dateForComparison, "DATACHECK");
                // Verify - In-Active Records, if there is any inquiry took place
                if (loanAppDocList.size() == 0) {
                    loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                            Long.parseLong(dto.cnicNo), Long.parseLong(dto.co_cnic_1), 0L, dateForComparison, "DATACHECK");
                }
            } else {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(dto.cnicNo), 0L, true, dateForComparison, "DATACHECK");
                // Verify - In-Active Records, if there is any inquiry took place
                if (loanAppDocList.size() == 0) {
                    loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                            Long.parseLong(dto.cnicNo), 0L, dateForComparison, "DATACHECK");
                }
            }
        } else if (dto.reqFor.equals("Nominee")) {
            log.info("MFCIBOfLast30Days  Comparison - Nominee -> " + dateForComparison);
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(dto.cnicNo), -1L, true, dateForComparison, "DATACHECK");
            // Verify - In-Active Records, if there is any inquiry took place
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(dto.cnicNo), -1L, dateForComparison, "DATACHECK");
            }
        } else if (dto.reqFor.equals("Pdc")) {
            log.info("MFCIBOfLast30Days  Comparison - Co-Borrower -> " + dateForComparison);
            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                    Long.parseLong(dto.cnicNo), -2L, true, dateForComparison, "DATACHECK");
            // Verify - In-Active Records, if there is any inquiry took place
            if (loanAppDocList.size() == 0) {
                loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                        Long.parseLong(dto.cnicNo), -2L, dateForComparison, "DATACHECK");
            }
        }

        // Data Exists
        if (loanAppDocList != null && loanAppDocList.size() > 0) {
            log.info("DataCheck: Database Object Returned " + loanAppDocList.get(0).getLoanAppSeq()
                    + ", CNIC: " + loanAppDocList.get(0).getCnicNum());
            // In Case Loan App Seq is changed but Doc Image
            /***if (!dto.applicationId.equals(loanAppDocList.get(0).getLoanAppSeq())) {
             // Existing Loan Detail
             MwLoanAppDoc exLoanAppDoc = loanAppDocList.get(0);
             // Get Detail of Credit Summary
             List<MwLoanAppCrdtSumry> loanAppCrdtSumries =
             mwLoanAppCrdtSumryRepository.findAllByLoanAppDocSeqAndCrntRecFlg(exLoanAppDoc.getLoanAppDocSeq(), true);

             // MFCIB Parsed Data
             List<MwLoanAppMfcibData> exLoanAppMfcibDataList = mwLoanAppMfcibDataRepository.findAllByLoanAppDocSeqAndCrntRecFlg(exLoanAppDoc.getLoanAppDocSeq(), true);

             //
             if (exLoanAppDoc.getCrntRecFlg() == true) {
             // Existing Loan Detail
             exLoanAppDoc.setCrntRecFlg(false);
             exLoanAppDoc.setDelFlg(true);
             exLoanAppDoc.setLastUpdDt(Instant.now());
             exLoanAppDoc.setLastUpdBy(user);

             mwLoanAppDocRepository.save(exLoanAppDoc);

             // Summary Detail to Update
             if (loanAppCrdtSumries.size() > 0) {
             for (MwLoanAppCrdtSumry loanAppCrdtSumry : loanAppCrdtSumries) {
             loanAppCrdtSumry.setCrntRecFlg(false);
             loanAppCrdtSumry.setLastUpdDt(Instant.now());
             loanAppCrdtSumry.setLastUpdBy(user);

             loanAppCrdtSumryList.add(loanAppCrdtSumry);
             }
             mwLoanAppCrdtSumryRepository.save(loanAppCrdtSumryList);
             loanAppCrdtSumryList.clear();
             }

             // Added by Zohaib Asim - Dated 21-06-2022
             // MFCIB Parsed Data to Update
             if (exLoanAppMfcibDataList.size() > 0) {
             for (MwLoanAppMfcibData loanAppMfcibData : exLoanAppMfcibDataList) {
             loanAppMfcibData.setCrntRecFlg(false);
             loanAppMfcibData.setLastUpdDt(Instant.now());
             loanAppMfcibData.setLastUpdBy(user);

             loanAppMfcibDataList.add(loanAppMfcibData);
             }
             mwLoanAppMfcibDataRepository.save(loanAppMfcibDataList);
             loanAppMfcibDataList.clear();
             }
             }

             // Loan App Doc Sequence
             Long loanAppDocSeq = 0L;
             Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_LOAN_APP_DOC");
             Object tblSeqRes = qry.getSingleResult();

             if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
             loanAppDocSeq = Long.parseLong(tblSeqRes.toString());
             }

             // Loan App Doc Details to Update
             MwLoanAppDoc mwLoanAppDoc = new MwLoanAppDoc();
             mwLoanAppDoc.setLoanAppDocSeq(loanAppDocSeq);
             mwLoanAppDoc.setEffStartDt(loanAppDocList.get(0).getEffStartDt());
             mwLoanAppDoc.setDocSeq(loanAppDocList.get(0).getDocSeq());
             mwLoanAppDoc.setDocImg(loanAppDocList.get(0).getDocImg());
             mwLoanAppDoc.setCnicNum(loanAppDocList.get(0).getCnicNum());
             mwLoanAppDoc.setNomCnicNum(loanAppDocList.get(0).getNomCnicNum());
             mwLoanAppDoc.setCompanyNm(loanAppDocList.get(0).getCompanyNm());
             mwLoanAppDoc.setLoanAppSeq(Long.parseLong(dto.applicationId));
             mwLoanAppDoc.setCrntRecFlg(true);
             mwLoanAppDoc.setDelFlg(false);
             mwLoanAppDoc.setCrtdDt(Instant.now());
             mwLoanAppDoc.setCrtdBy(user);
             mwLoanAppDocRepository.save(mwLoanAppDoc);

             // DataCheck - Credit Summary
             if (loanAppCrdtSumries.size() > 0) {
             loanAppCrdtSumryList = new ArrayList<>();

             for (MwLoanAppCrdtSumry loanAppCrdtSumry : loanAppCrdtSumries) {
             MwLoanAppCrdtSumry mwLoanAppCrdtSumry = new MwLoanAppCrdtSumry();

             mwLoanAppCrdtSumry.setLoanAppDocSeq(loanAppDocSeq);
             mwLoanAppCrdtSumry.setCsCtgry(loanAppCrdtSumry.getCsCtgry());
             mwLoanAppCrdtSumry.setCsNm(loanAppCrdtSumry.getCsNm());
             mwLoanAppCrdtSumry.setCsCnicNum(loanAppCrdtSumry.getCsCnicNum());
             mwLoanAppCrdtSumry.setLoanCount(loanAppCrdtSumry.getLoanCount());
             mwLoanAppCrdtSumry.setLoanLimit(loanAppCrdtSumry.getLoanLimit());
             mwLoanAppCrdtSumry.setLoanOst(loanAppCrdtSumry.getLoanOst());
             mwLoanAppCrdtSumry.setLoanLess10K(loanAppCrdtSumry.getLoanLess10K());
             mwLoanAppCrdtSumry.setLoanAbove10K(loanAppCrdtSumry.getLoanAbove10K());
             mwLoanAppCrdtSumry.setCurrent30Plus(loanAppCrdtSumry.getCurrent30Plus());
             mwLoanAppCrdtSumry.setCurrent60Plus(loanAppCrdtSumry.getCurrent60Plus());
             mwLoanAppCrdtSumry.setCurrent90Plus(loanAppCrdtSumry.getCurrent90Plus());
             mwLoanAppCrdtSumry.setCloseWithinMaturity(loanAppCrdtSumry.getCloseWithinMaturity());
             mwLoanAppCrdtSumry.setCloseAfterMaturity(loanAppCrdtSumry.getCloseAfterMaturity());
             mwLoanAppCrdtSumry.setDefaultCount(loanAppCrdtSumry.getDefaultCount());
             mwLoanAppCrdtSumry.setDefaultOst(loanAppCrdtSumry.getDefaultOst());
             mwLoanAppCrdtSumry.setMonth2430Plus(loanAppCrdtSumry.getMonth2430Plus());
             mwLoanAppCrdtSumry.setMonth2460Plus(loanAppCrdtSumry.getMonth2460Plus());
             mwLoanAppCrdtSumry.setMonth2490Plus(loanAppCrdtSumry.getMonth2490Plus());
             mwLoanAppCrdtSumry.setEnquiryCount(loanAppCrdtSumry.getEnquiryCount());
             mwLoanAppCrdtSumry.setCrntRecFlg(true);
             mwLoanAppCrdtSumry.setCrtdDt(Instant.now());
             mwLoanAppCrdtSumry.setCrtdBy(user);

             loanAppCrdtSumryList.add(mwLoanAppCrdtSumry);
             }
             mwLoanAppCrdtSumryRepository.save(loanAppCrdtSumryList);
             loanAppCrdtSumryList.clear();
             }

             // DataCheck - MFCIB Parsing
             if (exLoanAppMfcibDataList.size() > 0) {
             for (MwLoanAppMfcibData loanAppMfcibData : exLoanAppMfcibDataList) {
             MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();

             mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
             mwLoanAppMfcibData.setLevel1(loanAppMfcibData.getLevel1());
             mwLoanAppMfcibData.setLevel2(loanAppMfcibData.getLevel2());
             mwLoanAppMfcibData.setLevel3(loanAppMfcibData.getLevel3());
             mwLoanAppMfcibData.setLevel4(loanAppMfcibData.getLevel4());

             mwLoanAppMfcibData.setTagNm(loanAppMfcibData.getTagNm());
             mwLoanAppMfcibData.setTagVal(loanAppMfcibData.getTagVal());
             //
             mwLoanAppMfcibData.setFileNo(loanAppMfcibData.getFileNo());
             mwLoanAppMfcibData.setTrnxNo(loanAppMfcibData.getTrnxNo());
             mwLoanAppMfcibData.setLoanNo(loanAppMfcibData.getLoanNo());
             mwLoanAppMfcibData.setSeqNo(loanAppMfcibData.getSeqNo());
             mwLoanAppMfcibData.setP90(loanAppMfcibData.getP90());
             mwLoanAppMfcibData.setP60(loanAppMfcibData.getP60());
             mwLoanAppMfcibData.setP30(loanAppMfcibData.getP30());
             mwLoanAppMfcibData.setX(loanAppMfcibData.getX());
             mwLoanAppMfcibData.setP150(loanAppMfcibData.getP150());
             mwLoanAppMfcibData.setP120(loanAppMfcibData.getP120());
             mwLoanAppMfcibData.setOk(loanAppMfcibData.getOk());
             mwLoanAppMfcibData.setP180(loanAppMfcibData.getP180());
             mwLoanAppMfcibData.setLoss(loanAppMfcibData.getLoss());
             //
             mwLoanAppMfcibData.setMfiDefault(loanAppMfcibData.getMfiDefault());
             mwLoanAppMfcibData.setLatePmtDays(loanAppMfcibData.getLatePmtDays());
             mwLoanAppMfcibData.setLatePayment1To15(loanAppMfcibData.getLatePayment1To15());
             mwLoanAppMfcibData.setLatePayment16To20(loanAppMfcibData.getLatePayment16To20());
             mwLoanAppMfcibData.setLatePayment21To29(loanAppMfcibData.getLatePayment21To29());
             mwLoanAppMfcibData.setLatePayment30(loanAppMfcibData.getLatePayment30());
             //
             mwLoanAppMfcibData.setCollateralAmt(loanAppMfcibData.getCollateralAmt());
             mwLoanAppMfcibData.setAcctSeqNo(loanAppMfcibData.getAcctSeqNo());
             //
             mwLoanAppMfcibData.setCollTyp(loanAppMfcibData.getCollTyp());
             //
             mwLoanAppMfcibData.setCbrwrGrntrCnic(loanAppMfcibData.getCbrwrGrntrCnic());
             mwLoanAppMfcibData.setGrntrFileNo(loanAppMfcibData.getGrntrFileNo());
             mwLoanAppMfcibData.setGrntrInFavr(loanAppMfcibData.getGrntrInFavr());
             //
             mwLoanAppMfcibData.setNationality(loanAppMfcibData.getNationality());
             mwLoanAppMfcibData.setNtn(loanAppMfcibData.getNtn());
             mwLoanAppMfcibData.setFthrHsbndMNm(loanAppMfcibData.getFthrHsbndMNm());
             mwLoanAppMfcibData.setLastNm(loanAppMfcibData.getLastNm());
             mwLoanAppMfcibData.setFirstNm(loanAppMfcibData.getFirstNm());
             mwLoanAppMfcibData.setDependants(loanAppMfcibData.getDependants());
             mwLoanAppMfcibData.setQualification(loanAppMfcibData.getQualification());
             mwLoanAppMfcibData.setDob(loanAppMfcibData.getDob());
             mwLoanAppMfcibData.setTrnxResult(loanAppMfcibData.getTrnxResult());
             mwLoanAppMfcibData.setGender(loanAppMfcibData.getGender());
             mwLoanAppMfcibData.setMiddleNm(loanAppMfcibData.getMiddleNm());
             mwLoanAppMfcibData.setFthrHsbndLNm(loanAppMfcibData.getFthrHsbndLNm());
             mwLoanAppMfcibData.setChecker(loanAppMfcibData.getChecker());
             mwLoanAppMfcibData.setReportDt(loanAppMfcibData.getReportDt());
             mwLoanAppMfcibData.setNic(loanAppMfcibData.getNic());
             mwLoanAppMfcibData.setNatlTyp(loanAppMfcibData.getNatlTyp());
             mwLoanAppMfcibData.setIsSelf(loanAppMfcibData.getIsSelf());
             mwLoanAppMfcibData.setFthrHsbndFNm(loanAppMfcibData.getFthrHsbndFNm());
             mwLoanAppMfcibData.setMaritalSts(loanAppMfcibData.getMaritalSts());
             mwLoanAppMfcibData.setTitle(loanAppMfcibData.getTitle());
             mwLoanAppMfcibData.setProfession(loanAppMfcibData.getProfession());
             mwLoanAppMfcibData.setTrnxDt(loanAppMfcibData.getTrnxDt());
             mwLoanAppMfcibData.setRefNo(loanAppMfcibData.getRefNo());
             mwLoanAppMfcibData.setMaker(loanAppMfcibData.getMaker());
             mwLoanAppMfcibData.setCnic(loanAppMfcibData.getCnic());
             //
             mwLoanAppMfcibData.setPassport(loanAppMfcibData.getPassport());
             mwLoanAppMfcibData.setBrwrTyp(loanAppMfcibData.getBrwrTyp());
             //
             mwLoanAppMfcibData.setEmployer(loanAppMfcibData.getEmployer());
             mwLoanAppMfcibData.setReportedOn(loanAppMfcibData.getReportedOn());
             mwLoanAppMfcibData.setSelfEmployed(loanAppMfcibData.getSelfEmployed());
             mwLoanAppMfcibData.setCity(loanAppMfcibData.getCity());
             mwLoanAppMfcibData.setDesignation(loanAppMfcibData.getDesignation());
             mwLoanAppMfcibData.setRsdntlAddrs(loanAppMfcibData.getRsdntlAddrs());
             mwLoanAppMfcibData.setPhone1(loanAppMfcibData.getPhone1());
             mwLoanAppMfcibData.setPhone2(loanAppMfcibData.getPhone2());
             //
             mwLoanAppMfcibData.setCurRsdntalAddrsDt(loanAppMfcibData.getCurRsdntalAddrsDt());
             mwLoanAppMfcibData.setPrmntAddrsDt(loanAppMfcibData.getPrmntAddrsDt());
             mwLoanAppMfcibData.setPrvRsdntalAddrs(loanAppMfcibData.getPrvRsdntalAddrs());
             mwLoanAppMfcibData.setPrvRsdntalAddrsDt(loanAppMfcibData.getPrvRsdntalAddrsDt());
             mwLoanAppMfcibData.setEmployerBusinessDt(loanAppMfcibData.getEmployerBusinessDt());
             //
             mwLoanAppMfcibData.setOdds(loanAppMfcibData.getOdds());
             mwLoanAppMfcibData.setScore(loanAppMfcibData.getScore());
             mwLoanAppMfcibData.setProbOfDefalut(loanAppMfcibData.getProbOfDefalut());
             mwLoanAppMfcibData.setPercentileRisk(loanAppMfcibData.getPercentileRisk());
             mwLoanAppMfcibData.setSbpRiskLevel(loanAppMfcibData.getSbpRiskLevel());
             //
             mwLoanAppMfcibData.setPymntSts(loanAppMfcibData.getPymntSts());
             mwLoanAppMfcibData.setStsMnth(loanAppMfcibData.getStsMnth());
             mwLoanAppMfcibData.setOverdueAmt(loanAppMfcibData.getOverdueAmt());
             //
             mwLoanAppMfcibData.setLoanLess10K(loanAppMfcibData.getLoanLess10K());
             mwLoanAppMfcibData.setCurrent30Plus(loanAppMfcibData.getCurrent30Plus());
             mwLoanAppMfcibData.setEnquiryCount(loanAppMfcibData.getEnquiryCount());
             mwLoanAppMfcibData.setCloseWithinMaturity(loanAppMfcibData.getCloseWithinMaturity());
             mwLoanAppMfcibData.setCloseAfterMaturity(loanAppMfcibData.getCloseAfterMaturity());
             mwLoanAppMfcibData.setLoanLimit(loanAppMfcibData.getLoanLimit());
             mwLoanAppMfcibData.setMonth2430Plus(loanAppMfcibData.getMonth2430Plus());
             mwLoanAppMfcibData.setFileCreationDt(loanAppMfcibData.getFileCreationDt());
             mwLoanAppMfcibData.setCsName(loanAppMfcibData.getCsName());
             mwLoanAppMfcibData.setLoanCount(loanAppMfcibData.getLoanCount());
             mwLoanAppMfcibData.setLoanOs(loanAppMfcibData.getLoanOs());
             mwLoanAppMfcibData.setCsCtgry(loanAppMfcibData.getCsCtgry());
             mwLoanAppMfcibData.setDefaultCount(loanAppMfcibData.getDefaultCount());
             mwLoanAppMfcibData.setMonth2490Plus(loanAppMfcibData.getMonth2490Plus());
             mwLoanAppMfcibData.setLoanAbove10K(loanAppMfcibData.getLoanAbove10K());
             mwLoanAppMfcibData.setDefaultOs(loanAppMfcibData.getDefaultOs());
             //
             mwLoanAppMfcibData.setMessage(loanAppMfcibData.getMessage());
             //
             mwLoanAppMfcibData.setComments(loanAppMfcibData.getComments());
             mwLoanAppMfcibData.setAcctNo(loanAppMfcibData.getAcctNo());
             //
             mwLoanAppMfcibData.setCbrFileNo(loanAppMfcibData.getCbrFileNo());
             mwLoanAppMfcibData.setOthrBwr(loanAppMfcibData.getOthrBwr());
             //
             mwLoanAppMfcibData.setCoutName(loanAppMfcibData.getCoutName());
             mwLoanAppMfcibData.setDclrtnDt(loanAppMfcibData.getDclrtnDt());
             //
             mwLoanAppMfcibData.setPrmntCity(loanAppMfcibData.getPrmntCity());
             mwLoanAppMfcibData.setPrmntAddrs(loanAppMfcibData.getPrmntAddrs());
             //
             mwLoanAppMfcibData.setReviews(loanAppMfcibData.getReviews());
             //
             mwLoanAppMfcibData.setCurrency(loanAppMfcibData.getCurrency());
             mwLoanAppMfcibData.setRefDt(loanAppMfcibData.getRefDt());
             mwLoanAppMfcibData.setAssocTy(loanAppMfcibData.getAssocTy());
             mwLoanAppMfcibData.setEnqGroupId(loanAppMfcibData.getEnqGroupId());
             mwLoanAppMfcibData.setAcctTy(loanAppMfcibData.getAcctTy());
             mwLoanAppMfcibData.setEnqSts(loanAppMfcibData.getEnqSts());
             mwLoanAppMfcibData.setDispute(loanAppMfcibData.getDispute());
             mwLoanAppMfcibData.setMemNm(loanAppMfcibData.getMemNm());
             mwLoanAppMfcibData.setMappedAcctTy(loanAppMfcibData.getMappedAcctTy());
             mwLoanAppMfcibData.setSeprteDt(loanAppMfcibData.getSeprteDt());
             mwLoanAppMfcibData.setSubbrnName(loanAppMfcibData.getSubbrnName());
             mwLoanAppMfcibData.setApplicationDt(loanAppMfcibData.getApplicationDt());
             //
             mwLoanAppMfcibData.setCoBrwrNm(loanAppMfcibData.getCoBrwrNm());
             mwLoanAppMfcibData.setGrntrDt(loanAppMfcibData.getGrntrDt());
             mwLoanAppMfcibData.setAssoc(loanAppMfcibData.getAssoc());
             mwLoanAppMfcibData.setGrnteAmt(loanAppMfcibData.getGrnteAmt());
             mwLoanAppMfcibData.setInvocationDt(loanAppMfcibData.getInvocationDt());
             mwLoanAppMfcibData.setSection(loanAppMfcibData.getSection());
             //
             mwLoanAppMfcibData.setSubObj(loanAppMfcibData.getSubObj());
             mwLoanAppMfcibData.setCcpMstrLimit(loanAppMfcibData.getCcpMstrLimit());
             mwLoanAppMfcibData.setRschedlFlg(loanAppMfcibData.getRschedlFlg());
             mwLoanAppMfcibData.setTerm(loanAppMfcibData.getTerm());
             mwLoanAppMfcibData.setMaturityDt(loanAppMfcibData.getMaturityDt());
             mwLoanAppMfcibData.setHighCredit(loanAppMfcibData.getHighCredit());
             mwLoanAppMfcibData.setRescheduleDt(loanAppMfcibData.getRescheduleDt());
             mwLoanAppMfcibData.setClassCatg(loanAppMfcibData.getClassCatg());
             mwLoanAppMfcibData.setBncChq(loanAppMfcibData.getBncChq());
             mwLoanAppMfcibData.setNote(loanAppMfcibData.getNote());
             //
             mwLoanAppMfcibData.setCcpMstrCurrency(loanAppMfcibData.getCcpMstrCurrency());
             //
             mwLoanAppMfcibData.setStatusDt(loanAppMfcibData.getStatusDt());
             mwLoanAppMfcibData.setBalance(loanAppMfcibData.getBalance());
             mwLoanAppMfcibData.setLastPymt(loanAppMfcibData.getLastPymt());
             mwLoanAppMfcibData.setLoanClassDesc(loanAppMfcibData.getLoanClassDesc());
             mwLoanAppMfcibData.setAcctSts(loanAppMfcibData.getAcctSts());
             mwLoanAppMfcibData.setSecure(loanAppMfcibData.getSecure());
             mwLoanAppMfcibData.setMinAmtDue(loanAppMfcibData.getMinAmtDue());
             mwLoanAppMfcibData.setRepaymentFreq(loanAppMfcibData.getRepaymentFreq());
             mwLoanAppMfcibData.setOpenDt(loanAppMfcibData.getOpenDt());
             //
             mwLoanAppMfcibData.setLastPymntDt(loanAppMfcibData.getLastPymntDt());
             mwLoanAppMfcibData.setClassificationNature(loanAppMfcibData.getClassificationNature());
             mwLoanAppMfcibData.setLitigationAmt(loanAppMfcibData.getLitigationAmt());
             mwLoanAppMfcibData.setBouncedRepaymentCheques(loanAppMfcibData.getBouncedRepaymentCheques());
             mwLoanAppMfcibData.setSecurityCollateral(loanAppMfcibData.getSecurityCollateral());
             mwLoanAppMfcibData.setRestructuringAmt(loanAppMfcibData.getRestructuringAmt());
             mwLoanAppMfcibData.setWriteoffTyp(loanAppMfcibData.getWriteoffTyp());
             mwLoanAppMfcibData.setWriteOffAmt(loanAppMfcibData.getWriteOffAmt());
             mwLoanAppMfcibData.setWriteoffDt(loanAppMfcibData.getWriteoffDt());
             //
             mwLoanAppMfcibData.setRelDt(loanAppMfcibData.getRelDt());
             mwLoanAppMfcibData.setUpdMappedAcctTy(loanAppMfcibData.getUpdMappedAcctTy());
             mwLoanAppMfcibData.setUpdCurrency(loanAppMfcibData.getUpdCurrency());
             mwLoanAppMfcibData.setUpdSts(loanAppMfcibData.getUpdSts());
             mwLoanAppMfcibData.setOrgStsDt(loanAppMfcibData.getOrgStsDt());
             mwLoanAppMfcibData.setOrgSts(loanAppMfcibData.getOrgSts());
             mwLoanAppMfcibData.setOrgRtr(loanAppMfcibData.getOrgRtr());
             mwLoanAppMfcibData.setUpdAmt(loanAppMfcibData.getUpdAmt());
             mwLoanAppMfcibData.setRecoveryDate(loanAppMfcibData.getRecoveryDate());
             mwLoanAppMfcibData.setOrgCurrency(loanAppMfcibData.getOrgCurrency());
             mwLoanAppMfcibData.setOrgMappedAcctTy(loanAppMfcibData.getOrgMappedAcctTy());
             mwLoanAppMfcibData.setOrgAcctTy(loanAppMfcibData.getOrgAcctTy());
             mwLoanAppMfcibData.setOrgAmount(loanAppMfcibData.getOrgAmount());
             mwLoanAppMfcibData.setUpdStatusDate(loanAppMfcibData.getUpdStatusDate());
             mwLoanAppMfcibData.setUpdAcctNo(loanAppMfcibData.getUpdAcctNo());
             mwLoanAppMfcibData.setOrgAcctNo(loanAppMfcibData.getOrgAcctNo());
             mwLoanAppMfcibData.setUpdAcctTy(loanAppMfcibData.getUpdAcctTy());
             mwLoanAppMfcibData.setUpdRtr(loanAppMfcibData.getUpdRtr());
             mwLoanAppMfcibData.setRecoveryAmount(loanAppMfcibData.getRecoveryAmount());

             //log.info(loanAppMfcibData.getDateOfLastPaymentMade());
             //                        mwLoanAppMfcibData.setDateOfLastPaymentMade(loanAppMfcibData.getDateOfLastPaymentMade());
             //                        mwLoanAppMfcibData.setCourtNm(loanAppMfcibData.getCourtNm());
             //                        mwLoanAppMfcibData.setProduct((loanAppMfcibData.getProduct()));
             //                        mwLoanAppMfcibData.setFinancialInstitution(loanAppMfcibData.getFinancialInstitution());
             //                        mwLoanAppMfcibData.setAmountOfFacility(loanAppMfcibData.getAmountOfFacility());
             //                        mwLoanAppMfcibData.setTotalLimit(loanAppMfcibData.getTotalLimit());
             //
             mwLoanAppMfcibData.setCrntRecFlg(true);
             mwLoanAppMfcibData.setCrtdBy(user);
             mwLoanAppMfcibData.setCrtdDt(Instant.now());

             loanAppMfcibDataList.add(mwLoanAppMfcibData);
             }
             mwLoanAppMfcibDataRepository.save(loanAppMfcibDataList);
             loanAppMfcibDataList.clear();
             }

             // For Testing Purpose
             //                try{
             //                    JSONObject jsonObject = new JSONObject(loanAppDocList.get(0).getDocImg().toString());
             //                    JSONObject root = jsonObject.getJSONObject("report");
             //                    parseCreditBureauData(root, loanAppDocSeq);
             //                }catch(JSONException ex){
             //                    ex.printStackTrace();
             //                }catch(Exception ex){
             //                    ex.printStackTrace();
             //                }
             }***/
            // End

            return loanAppDocList.get(0).getDocImg().toString();
        }

        String soapEndpointUrl = url;
        String soapAction = "http://tempuri.org/IService1/" + cbReq;

        String jsonPrettyPrintString = "";

        log.info("Datacheck - Soap Request started at " + Instant.now());

        SOAPMessage msg = callSoapWebService(soapEndpointUrl, soapAction, dto);

        log.info("Datacheck - Soap Request ended at " + Instant.now());

        JSONObject report = new JSONObject();
        Map<String, Map> resp = new HashMap<String, Map>();
        JSONObject respObject = new JSONObject();
        try {
            // log.debug( "===============================1==============================" );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            msg.writeTo(out);
            String strMsg = new String(out.toByteArray());
            // log.debug( dto.toString() );
            // log.debug( "===============================2==============================" );
            JSONObject xmlJSONObj = XML.toJSONObject(strMsg);
            jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

            // System.out.println( jsonPrettyPrintString );
            // log.debug( "===============================3==============================" );

            JSONObject env = xmlJSONObj.getJSONObject("s:Envelope");
            JSONObject body = env.getJSONObject("s:Body");

            JSONObject getBureauCreditReportResponse = body.getJSONObject(cbResp);
            JSONObject getBureauCreditReportResult = getBureauCreditReportResponse.getJSONObject(cbRslt);
            JSONObject cr = getBureauCreditReportResult.getJSONObject("a:CRNewResponse");
            // End

            String reportStr = cr.getString("a:Report");
            String status = cr.getString("a:Status");
            /*log.debug(reportStr);
            log.debug("===============================4==============================");
            log.debug(status);
            log.debug("===============================5==============================");*/

            report = XML.toJSONObject(reportStr);
            String reportJSONStr = report.toString(PRETTY_PRINT_INDENT_FACTOR);
            log.debug(reportJSONStr);
            /*log.debug("===============================6==============================");*/

            // Loan App Doc Sequence
            Long seq = 0L;
            Query qry = em.createNativeQuery(Queries.getTableSeq).setParameter("tblNm", "MW_LOAN_APP_DOC")
                    .setParameter("userId", user);
            Object tblSeqRes = qry.getSingleResult();

            if (tblSeqRes != null && !tblSeqRes.toString().equals("")) {
                seq = Long.parseLong(tblSeqRes.toString());
            }

            if (status.equals("CR")) {
                JSONObject root = report.getJSONObject("ROOT");
                Map<String, Map> resp1 = new HashMap<String, Map>();
                report.put("status", status);
                respObject.put("status", report);
                Map<String, Object> reportMap1 = Common.jsonToMap(report);
                resp1.put("report", reportMap1);
                respObject.put("report", reportMap1);
                MwLoanAppDoc doc = new MwLoanAppDoc();
                // String cnicNum = dto.cnicNo;

                doc.setCrntRecFlg(true);
                doc.setCrtdBy("CB");
                doc.setCrtdDt(Instant.now());
                doc.setDelFlg(false);
                doc.setDocImg(respObject.toString());

                if (dto.reqFor.equals("Client"))
                    doc.setDocSeq(0L);
                else if (dto.reqFor.equals("Nominee"))
                    doc.setDocSeq(-1L);
                else if (dto.reqFor.equals("Pdc"))
                    doc.setDocSeq(-2L);

                doc.setEffStartDt(Instant.now());
                doc.setLoanAppDocSeq(seq);
                doc.setLoanAppSeq(Long.parseLong(dto.applicationId));

                // Condition added by Zohaib Asim - Dated 09-03-2021
                doc.setCnicNum(Long.parseLong(dto.cnicNo));

                if (dto.co_cnic_1 != null && !dto.co_cnic_1.isEmpty()) {
                    doc.setNomCnicNum(Long.parseLong(dto.co_cnic_1));
                }

                if (dto.reqFor.equals("Client")) {
                    log.info("MFCIBOfLast30Days Comparison - Client -> " + dateForComparison);

                    // For Single Call - Nominee Information Available
                    if (dto.co_cnic_1 != null && !dto.co_cnic_1.isEmpty()) {
                        loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                Long.parseLong(dto.cnicNo), Long.parseLong(dto.co_cnic_1), 0L, true, dateForComparison, "DATACHECK");
                        // Verify - In-Active Records, if there is any inquiry took place
                        if (loanAppDocList.size() == 0) {
                            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndNomCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                    Long.parseLong(dto.cnicNo), Long.parseLong(dto.co_cnic_1), 0L, dateForComparison, "DATACHECK");
                        }
                    } else {
                        loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                Long.parseLong(dto.cnicNo), 0L, true, dateForComparison, "DATACHECK");
                        // Verify - In-Active Records, if there is any inquiry took place
                        if (loanAppDocList.size() == 0) {
                            loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                    Long.parseLong(dto.cnicNo), 0L, dateForComparison, "DATACHECK");
                        }
                    }
                } else if (dto.reqFor.equals("Nominee")) {
                    log.info("MFCIBOfLast30Days  Comparison - Nominee -> " + dateForComparison);
                    loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                            Long.parseLong(dto.cnicNo), -1L, true, dateForComparison, "DATACHECK");
                    // Verify - In-Active Records, if there is any inquiry took place
                    if (loanAppDocList.size() == 0) {
                        loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                Long.parseLong(dto.cnicNo), -1L, dateForComparison, "DATACHECK");
                    }
                } else if (dto.reqFor.equals("Pdc")) {
                    log.info("MFCIBOfLast30Days  Comparison - Co-Borrower -> " + dateForComparison);
                    loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndCrntRecFlgAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                            Long.parseLong(dto.cnicNo), -2L, true, dateForComparison, "DATACHECK");
                    // Verify - In-Active Records, if there is any inquiry took place
                    if (loanAppDocList.size() == 0) {
                        loanAppDocList = mwLoanAppDocRepository.findMwLoanAppDocByCnicNumAndDocSeqAndEffStartDtAfterAndCompanyNmOrderByEffStartDtDesc(
                                Long.parseLong(dto.cnicNo), -2L, dateForComparison, "DATACHECK");
                    }
                }

                if (loanAppDocList != null && loanAppDocList.size() > 0) {
                    return loanAppDocList.get(0).getDocImg().toString();
                }
                doc.setCompanyNm("DATACHECK");
                mwLoanAppDocRepository.save(doc);
                // mwLoanAppCrdtSumryRepository.save(loanAppCrdtSumryList);

                log.info("Datacheck - Soap response parsing started at " + Instant.now());

                // Added by Zohaib Asim - Dated 21-06-2022 - To Parse Data
                String parsingSts = parseCreditBureauData(report, seq, "report");
                // log.info("Datacheck - Credit Bureau Data Parsing Status: " + parsingSts);
                log.info("Datacheck - Soap response parsing ended at " + Instant.now());


            } else if (status.equals("DBRF-TR1")) {
                String randomCh = randomString(5);
                dto.newApplicationId = randomCh + dto.applicationId;
                // return getBureauCreditReport(dto);
                return getBureauCreditReportWithParsing(dto);
            }

            report.put("status", status);

            Map<String, Object> reportMap = Common.jsonToMap(report);

            //report = XML.toJSONObject(reportStr);
            resp.put("report", reportMap);
            respObject.put("status", report);
            respObject.put("report", report);

        } catch (SOAPException | IOException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        log.info("Datacheck - Process Ended at " + Instant.now());

        return respObject.toString();
    }

    /* Added by Zohaib Asim
       Dated:   17-06-2022
       Reason:  Parsing DataCheck - Response */
    @Transactional
    @Timed
    public String parseCreditBureauData(JSONObject rptJsonObj, Long loanAppDocSeq, String parent) {
        //
        List<MwLoanAppCrdtSumry> loanAppCrdtSumryList = new ArrayList<>();
        List<MwLoanAppMfcibData> loanAppMfcibDataList = new ArrayList<>();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        //
        try {

            //---------------------------------------------------------------------------------------------------------
            // ROOT
            //---------------------------------------------------------------------------------------------------------

            JSONObject root = rptJsonObj.getJSONObject("ROOT");
            if (root != null) {
                //---------------------------------------------------------------------------------------------------------
                // 1. CCP_SUMMARY
                //---------------------------------------------------------------------------------------------------------
                JSONArray summaries = new JSONArray();
                Object sumObj = root.has("CCP_SUMMARY") ? root.get("CCP_SUMMARY") : null;
                if (sumObj instanceof JSONArray) {
                    // JSON Array
                    summaries = (JSONArray) sumObj;
                    for (int index = 0; index < summaries.length(); index++) {
                        JSONObject jsonObject = summaries.getJSONObject(index);
                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("CCP_SUMMARY");
                        mwLoanAppMfcibData.setLoss(jsonObject.has("LOSS") ? String.valueOf(jsonObject.get("LOSS")) : "");
                        mwLoanAppMfcibData.setP90(jsonObject.has("P90") ? String.valueOf(jsonObject.get("P90")) : "");
                        mwLoanAppMfcibData.setP60(jsonObject.has("P60") ? String.valueOf(jsonObject.get("P60")) : "");
                        mwLoanAppMfcibData.setP30(jsonObject.has("P30") ? String.valueOf(jsonObject.get("P30")) : "");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setLoanNo(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                        mwLoanAppMfcibData.setX(jsonObject.has("X") ? String.valueOf(jsonObject.get("X")) : "");
                        mwLoanAppMfcibData.setP150(jsonObject.has("P150") ? String.valueOf(jsonObject.get("P150")) : "");
                        mwLoanAppMfcibData.setP120(jsonObject.has("P120") ? String.valueOf(jsonObject.get("P120")) : "");
                        mwLoanAppMfcibData.setOk(jsonObject.has("OK") ? String.valueOf(jsonObject.get("OK")) : "");
                        mwLoanAppMfcibData.setP180(jsonObject.has("P180") ? String.valueOf(jsonObject.get("P180")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (sumObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) sumObj;
                    // summaryArr.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("CCP_SUMMARY");
                    mwLoanAppMfcibData.setLoss(jsonObject.has("LOSS") ? String.valueOf(jsonObject.get("LOSS")) : "");
                    mwLoanAppMfcibData.setP90(jsonObject.has("P90") ? String.valueOf(jsonObject.get("P90")) : "");
                    mwLoanAppMfcibData.setP60(jsonObject.has("P60") ? String.valueOf(jsonObject.get("P60")) : "");
                    mwLoanAppMfcibData.setP30(jsonObject.has("P30") ? String.valueOf(jsonObject.get("P30")) : "");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setLoanNo(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                    mwLoanAppMfcibData.setX(jsonObject.has("X") ? String.valueOf(jsonObject.get("X")) : "");
                    mwLoanAppMfcibData.setP150(jsonObject.has("P150") ? String.valueOf(jsonObject.get("P150")) : "");
                    mwLoanAppMfcibData.setP120(jsonObject.has("P120") ? String.valueOf(jsonObject.get("P120")) : "");
                    mwLoanAppMfcibData.setOk(jsonObject.has("OK") ? String.valueOf(jsonObject.get("OK")) : "");
                    mwLoanAppMfcibData.setP180(jsonObject.has("P180") ? String.valueOf(jsonObject.get("P180")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("CCP_SUMMARY");
                //root.put("CCP_SUMMARY", summaries);

                //---------------------------------------------------------------------------------------------------------
                // 2. COLLATERAL
                //---------------------------------------------------------------------------------------------------------
                JSONArray collaterals = new JSONArray();
                Object collateralObj = root.has("COLLATERAL") ? root.get("COLLATERAL") : null;
                if (collateralObj instanceof JSONArray) {
                    // JSON Array
                    collaterals = (JSONArray) collateralObj;
                    for (int index = 0; index < collaterals.length(); index++) {
                        JSONObject jsonObject = collaterals.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("COLLATERAL");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setCollateralAmt(jsonObject.has("AMOUNT") ? String.valueOf(jsonObject.get("AMOUNT")) : "");
                        mwLoanAppMfcibData.setAcctSeqNo(jsonObject.has("ACCT_SEQ_NO") ? String.valueOf(jsonObject.get("ACCT_SEQ_NO")) : "");
                        mwLoanAppMfcibData.setSection(jsonObject.has("SECTION") ? String.valueOf(jsonObject.get("SECTION")) : "");
                        mwLoanAppMfcibData.setCollTyp(jsonObject.has("COLL_TYPE") ? String.valueOf(jsonObject.get("COLL_TYPE")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (collateralObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) collateralObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("COLLATERAL");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setCollateralAmt(jsonObject.has("AMOUNT") ? String.valueOf(jsonObject.get("AMOUNT")) : "");
                    mwLoanAppMfcibData.setAcctSeqNo(jsonObject.has("ACCT_SEQ_NO") ? String.valueOf(jsonObject.get("ACCT_SEQ_NO")) : "");
                    mwLoanAppMfcibData.setSection(jsonObject.has("SECTION") ? String.valueOf(jsonObject.get("SECTION")) : "");
                    mwLoanAppMfcibData.setCollTyp(jsonObject.has("COLL_TYPE") ? String.valueOf(jsonObject.get("COLL_TYPE")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("COLLATERAL");
                //root.put("COLLATERAL", collaterals);

                //---------------------------------------------------------------------------------------------------------
                // 3. GUARANTEES_DETAILS
                //---------------------------------------------------------------------------------------------------------
                JSONArray guarantees = new JSONArray();
                Object guaranteeObj = root.has("GUARANTEES_DETAILS") ? root.get("GUARANTEES_DETAILS") : null;
                if (guaranteeObj instanceof JSONArray) {
                    // JSON Array
                    guarantees = (JSONArray) guaranteeObj;
                    for (int index = 0; index < guarantees.length(); index++) {
                        JSONObject jsonObject = guarantees.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("GUARANTEES_DETAILS");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setCollateralAmt(jsonObject.has("AMOUNT") ? String.valueOf(jsonObject.get("AMOUNT")) : "");
                        mwLoanAppMfcibData.setCbrwrGrntrCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                        mwLoanAppMfcibData.setGrntrFileNo(jsonObject.has("GRN_FILE_NO") ? String.valueOf(jsonObject.get("GRN_FILE_NO")) : "");
                        mwLoanAppMfcibData.setGrntrInFavr(jsonObject.has("GRN_IN_FAVR") ? String.valueOf(jsonObject.get("GRN_IN_FAVR")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (guaranteeObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) guaranteeObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("GUARANTEES_DETAILS");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setCollateralAmt(jsonObject.has("AMOUNT") ? String.valueOf(jsonObject.get("AMOUNT")) : "");
                    mwLoanAppMfcibData.setCbrwrGrntrCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                    mwLoanAppMfcibData.setGrntrFileNo(jsonObject.has("GRN_FILE_NO") ? String.valueOf(jsonObject.get("GRN_FILE_NO")) : "");
                    mwLoanAppMfcibData.setGrntrInFavr(jsonObject.has("GRN_IN_FAVR") ? String.valueOf(jsonObject.get("GRN_IN_FAVR")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("GUARANTEES_DETAILS");
                //root.put("GUARANTEES_DETAILS", guarantees);

                //---------------------------------------------------------------------------------------------------------
                // 4. Individual Detail
                //---------------------------------------------------------------------------------------------------------
                JSONObject individualDetails = new JSONObject();
                Object individualDetailObj = root.has("INDIVIDUAL_DETAIL") ? root.get("INDIVIDUAL_DETAIL") : null;
                if (individualDetailObj instanceof JSONArray) {
                    // JSON Array
                    JSONArray arr = (JSONArray) individualDetailObj;
                    for (int y = 0; y < arr.length(); y++) {
                        JSONObject jsonObject = arr.getJSONObject(y);
                        String isSelf = jsonObject.getString("IS_SELF");
                        if (isSelf.trim().toLowerCase().equals("y")) {
                            //individualDetail = dtl;

                            // MwLoanAppMfcibData Object Map
                            MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                            mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                            mwLoanAppMfcibData.setLevel1(parent);
                            mwLoanAppMfcibData.setLevel2("ROOT");
                            mwLoanAppMfcibData.setTagNm("INDIVIDUAL_DETAIL");
                            mwLoanAppMfcibData.setNationality(jsonObject.has("NATIONALITY") ? String.valueOf(jsonObject.get("NATIONALITY")) : "");
                            mwLoanAppMfcibData.setNtn(jsonObject.has("NTN") ? String.valueOf(jsonObject.get("NTN")) : "");
                            mwLoanAppMfcibData.setFthrHsbndMNm(jsonObject.has("FATHER_HUSBAND_MNAME") ? String.valueOf(jsonObject.get("FATHER_HUSBAND_MNAME")) : "");
                            mwLoanAppMfcibData.setLastNm(jsonObject.has("LAST_NAME") ? String.valueOf(jsonObject.get("LAST_NAME")) : "");
                            mwLoanAppMfcibData.setFirstNm(jsonObject.has("FIRST_NAME") ? String.valueOf(jsonObject.get("FIRST_NAME")) : "");
                            mwLoanAppMfcibData.setDependants(jsonObject.has("DEPENDANTS") ? String.valueOf(jsonObject.get("DEPENDANTS")) : "");
                            mwLoanAppMfcibData.setQualification(jsonObject.has("QUALIFICATION") ? String.valueOf(jsonObject.get("QUALIFICATION")) : "");
                            mwLoanAppMfcibData.setDob(jsonObject.has("DOB") ? String.valueOf(jsonObject.get("DOB")) : "");
                            mwLoanAppMfcibData.setTrnxResult(jsonObject.has("TRNX_RESULT") ? String.valueOf(jsonObject.get("TRNX_RESULT")) : "");
                            mwLoanAppMfcibData.setGender(jsonObject.has("GENDER") ? String.valueOf(jsonObject.get("GENDER")) : "");
                            mwLoanAppMfcibData.setMiddleNm(jsonObject.has("MIDDLE_NAME") ? String.valueOf(jsonObject.get("MIDDLE_NAME")) : "");
                            mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                            mwLoanAppMfcibData.setFthrHsbndLNm(jsonObject.has("FATHER_HUSBAND_LNAME") ? String.valueOf(jsonObject.get("FATHER_HUSBAND_LNAME")) : "");
                            mwLoanAppMfcibData.setChecker(jsonObject.has("CHECKER") ? String.valueOf(jsonObject.get("CHECKER")) : "");
                            mwLoanAppMfcibData.setReportDt(jsonObject.has("CREATION_DATE") ? String.valueOf(jsonObject.get("CREATION_DATE")) : "");
                            mwLoanAppMfcibData.setNic(jsonObject.has("NIC") ? String.valueOf(jsonObject.get("NIC")) : "");
                            mwLoanAppMfcibData.setNatlTyp(jsonObject.has("NATL_TYPE") ? String.valueOf(jsonObject.get("NATL_TYPE")) : "");
                            mwLoanAppMfcibData.setIsSelf(jsonObject.has("IS_SELF") ? String.valueOf(jsonObject.get("IS_SELF")) : "");
                            mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                            mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                            mwLoanAppMfcibData.setFthrHsbndFNm(jsonObject.has("FATHER_HUSBAND_FNAME") ? String.valueOf(jsonObject.get("FATHER_HUSBAND_FNAME")) : "");
                            mwLoanAppMfcibData.setMaritalSts(jsonObject.has("MARITIAL_STATUS") ? String.valueOf(jsonObject.get("MARITIAL_STATUS")) : "");
                            mwLoanAppMfcibData.setTitle(jsonObject.has("TITLE") ? String.valueOf(jsonObject.get("TITLE")) : "");
                            mwLoanAppMfcibData.setProfession(jsonObject.has("PROFESSION") ? String.valueOf(jsonObject.get("PROFESSION")) : "");
                            mwLoanAppMfcibData.setTrnxDt(jsonObject.has("TRANX_DATE") ? String.valueOf(jsonObject.get("TRANX_DATE")) : "");
                            mwLoanAppMfcibData.setRefNo(jsonObject.has("REFERENCE_NO") ? String.valueOf(jsonObject.get("REFERENCE_NO")) : "");
                            mwLoanAppMfcibData.setMaker(jsonObject.has("MAKER") ? String.valueOf(jsonObject.get("MAKER")) : "");
                            mwLoanAppMfcibData.setCrntRecFlg(true);
                            mwLoanAppMfcibData.setCrtdDt(Instant.now());
                            mwLoanAppMfcibData.setCrtdBy(user);
                            // List
                            loanAppMfcibDataList.add(mwLoanAppMfcibData);
                        }
                    }
                } else if (individualDetailObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) individualDetailObj;

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("INDIVIDUAL_DETAIL");
                    mwLoanAppMfcibData.setNationality(jsonObject.has("NATIONALITY") ? String.valueOf(jsonObject.get("NATIONALITY")) : "");
                    mwLoanAppMfcibData.setNtn(jsonObject.has("NTN") ? String.valueOf(jsonObject.get("NTN")) : "");
                    mwLoanAppMfcibData.setFthrHsbndMNm(jsonObject.has("FATHER_HUSBAND_MNAME") ? String.valueOf(jsonObject.get("FATHER_HUSBAND_MNAME")) : "");
                    mwLoanAppMfcibData.setLastNm(jsonObject.has("LAST_NAME") ? String.valueOf(jsonObject.get("LAST_NAME")) : "");
                    mwLoanAppMfcibData.setFirstNm(jsonObject.has("FIRST_NAME") ? String.valueOf(jsonObject.get("FIRST_NAME")) : "");
                    mwLoanAppMfcibData.setDependants(jsonObject.has("DEPENDANTS") ? String.valueOf(jsonObject.get("DEPENDANTS")) : "");
                    mwLoanAppMfcibData.setQualification(jsonObject.has("QUALIFICATION") ? String.valueOf(jsonObject.get("QUALIFICATION")) : "");
                    mwLoanAppMfcibData.setDob(jsonObject.has("DOB") ? String.valueOf(jsonObject.get("DOB")) : "");
                    mwLoanAppMfcibData.setTrnxResult(jsonObject.has("TRNX_RESULT") ? String.valueOf(jsonObject.get("TRNX_RESULT")) : "");
                    mwLoanAppMfcibData.setGender(jsonObject.has("GENDER") ? String.valueOf(jsonObject.get("GENDER")) : "");
                    mwLoanAppMfcibData.setMiddleNm(jsonObject.has("MIDDLE_NAME") ? String.valueOf(jsonObject.get("MIDDLE_NAME")) : "");
                    mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                    mwLoanAppMfcibData.setFthrHsbndLNm(jsonObject.has("FATHER_HUSBAND_LNAME") ? String.valueOf(jsonObject.get("FATHER_HUSBAND_LNAME")) : "");
                    mwLoanAppMfcibData.setChecker(jsonObject.has("CHECKER") ? String.valueOf(jsonObject.get("CHECKER")) : "");
                    mwLoanAppMfcibData.setReportDt(jsonObject.has("CREATION_DATE") ? String.valueOf(jsonObject.get("CREATION_DATE")) : "");
                    mwLoanAppMfcibData.setNic(jsonObject.has("NIC") ? String.valueOf(jsonObject.get("NIC")) : "");
                    mwLoanAppMfcibData.setNatlTyp(jsonObject.has("NATL_TYPE") ? String.valueOf(jsonObject.get("NATL_TYPE")) : "");
                    mwLoanAppMfcibData.setIsSelf(jsonObject.has("IS_SELF") ? String.valueOf(jsonObject.get("IS_SELF")) : "");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setFthrHsbndFNm(jsonObject.has("FATHER_HUSBAND_FNAME") ? String.valueOf(jsonObject.get("FATHER_HUSBAND_FNAME")) : "");
                    mwLoanAppMfcibData.setMaritalSts(jsonObject.has("MARITIAL_STATUS") ? String.valueOf(jsonObject.get("MARITIAL_STATUS")) : "");
                    mwLoanAppMfcibData.setTitle(jsonObject.has("TITLE") ? String.valueOf(jsonObject.get("TITLE")) : "");
                    mwLoanAppMfcibData.setProfession(jsonObject.has("PROFESSION") ? String.valueOf(jsonObject.get("PROFESSION")) : "");
                    mwLoanAppMfcibData.setTrnxDt(jsonObject.has("TRANX_DATE") ? String.valueOf(jsonObject.get("TRANX_DATE")) : "");
                    mwLoanAppMfcibData.setRefNo(jsonObject.has("REFERENCE_NO") ? String.valueOf(jsonObject.get("REFERENCE_NO")) : "");
                    mwLoanAppMfcibData.setMaker(jsonObject.has("MAKER") ? String.valueOf(jsonObject.get("MAKER")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("INDIVIDUAL_DETAIL");
                //root.put("INDIVIDUAL_DETAIL", individualDetails);

                //---------------------------------------------------------------------------------------------------------
                // 5. EMPLOYER_INFORMATION
                //---------------------------------------------------------------------------------------------------------
                JSONArray employers = new JSONArray();
                Object employerObj = root.has("EMPLOYER_INFORMATION") ? root.get("EMPLOYER_INFORMATION") : null;
                if (employerObj instanceof JSONArray) {
                    // JSON Array
                    employers = (JSONArray) employerObj;
                    for (int index = 0; index < employers.length(); index++) {
                        JSONObject jsonObject = employers.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("EMPLOYER_INFORMATION");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setEmployer(jsonObject.has("EMPLOYER") ? String.valueOf(jsonObject.get("EMPLOYER")) : "");
                        mwLoanAppMfcibData.setReportedOn(jsonObject.has("REPORTED_ON") ? String.valueOf(jsonObject.get("REPORTED_ON")) : "");
                        mwLoanAppMfcibData.setSelfEmployed(jsonObject.has("SELF_EMPLOYED") ? String.valueOf(jsonObject.get("SELF_EMPLOYED")) : "");
                        mwLoanAppMfcibData.setCity(jsonObject.has("CITY") ? String.valueOf(jsonObject.get("CITY")) : "");
                        mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                        mwLoanAppMfcibData.setDesignation(jsonObject.has("DESIGNATION") ? String.valueOf(jsonObject.get("DESIGNATION")) : "");
                        mwLoanAppMfcibData.setRsdntlAddrs(jsonObject.has("ADDRESS") ? String.valueOf(jsonObject.get("ADDRESS")) : "");
                        mwLoanAppMfcibData.setPhone2(jsonObject.has("PHONE2") ? String.valueOf(jsonObject.get("PHONE2")) : "");
                        mwLoanAppMfcibData.setPhone1(jsonObject.has("PHONE1") ? String.valueOf(jsonObject.get("PHONE1")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (employerObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) employerObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("EMPLOYER_INFORMATION");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setEmployer(jsonObject.has("EMPLOYER") ? String.valueOf(jsonObject.get("EMPLOYER")) : "");
                    mwLoanAppMfcibData.setReportedOn(jsonObject.has("REPORTED_ON") ? String.valueOf(jsonObject.get("REPORTED_ON")) : "");
                    mwLoanAppMfcibData.setSelfEmployed(jsonObject.has("SELF_EMPLOYED") ? String.valueOf(jsonObject.get("SELF_EMPLOYED")) : "");
                    mwLoanAppMfcibData.setCity(jsonObject.has("CITY") ? String.valueOf(jsonObject.get("CITY")) : "");
                    mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                    mwLoanAppMfcibData.setDesignation(jsonObject.has("DESIGNATION") ? String.valueOf(jsonObject.get("DESIGNATION")) : "");
                    mwLoanAppMfcibData.setRsdntlAddrs(jsonObject.has("ADDRESS") ? String.valueOf(jsonObject.get("ADDRESS")) : "");
                    mwLoanAppMfcibData.setPhone2(jsonObject.has("PHONE2") ? String.valueOf(jsonObject.get("PHONE2")) : "");
                    mwLoanAppMfcibData.setPhone1(jsonObject.has("PHONE1") ? String.valueOf(jsonObject.get("PHONE1")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("EMPLOYER_INFORMATION");
                //root.put("EMPLOYER_INFORMATION", employers);

                //---------------------------------------------------------------------------------------------------------
                // 6. CREDIT_SCORE
                //---------------------------------------------------------------------------------------------------------
                JSONArray creditScores = new JSONArray();
                Object creditScoreObj = root.has("CREDIT_SCORE") ? root.get("CREDIT_SCORE") : null;
                if (creditScoreObj instanceof JSONArray) {
                    // JSON Array
                    creditScores = (JSONArray) creditScoreObj;
                    for (int index = 0; index < creditScores.length(); index++) {
                        JSONObject jsonObject = creditScores.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("CREDIT_SCORE");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setOdds(jsonObject.has("ODDS") ? String.valueOf(jsonObject.get("ODDS")) : "");
                        mwLoanAppMfcibData.setScore(jsonObject.has("SCORE") ? String.valueOf(jsonObject.get("SCORE")) : "");
                        mwLoanAppMfcibData.setProbOfDefalut(jsonObject.has("PROB_OF_DEFALUT") ? String.valueOf(jsonObject.get("PROB_OF_DEFALUT")) : "");
                        mwLoanAppMfcibData.setPercentileRisk(jsonObject.has("PERCENTILE_RISK") ? String.valueOf(jsonObject.get("PERCENTILE_RISK")) : "");
                        mwLoanAppMfcibData.setSbpRiskLevel(jsonObject.has("SBP_RISK_LEVEL") ? String.valueOf(jsonObject.get("SBP_RISK_LEVEL")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (creditScoreObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) creditScoreObj;
                    // collaterals.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("CREDIT_SCORE");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setOdds(jsonObject.has("ODDS") ? String.valueOf(jsonObject.get("ODDS")) : "");
                    mwLoanAppMfcibData.setScore(jsonObject.has("SCORE") ? String.valueOf(jsonObject.get("SCORE")) : "");
                    mwLoanAppMfcibData.setProbOfDefalut(jsonObject.has("PROB_OF_DEFALUT") ? String.valueOf(jsonObject.get("PROB_OF_DEFALUT")) : "");
                    mwLoanAppMfcibData.setPercentileRisk(jsonObject.has("PERCENTILE_RISK") ? String.valueOf(jsonObject.get("PERCENTILE_RISK")) : "");
                    mwLoanAppMfcibData.setSbpRiskLevel(jsonObject.has("SBP_RISK_LEVEL") ? String.valueOf(jsonObject.get("SBP_RISK_LEVEL")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("CREDIT_SCORE");
                //root.put("CREDIT_SCORE", creditScores);

                //---------------------------------------------------------------------------------------------------------
                // 7. CCP_DETAIL
                //---------------------------------------------------------------------------------------------------------
                JSONArray cpp_details = new JSONArray();
                Object cppDetailObj = root.has("CCP_DETAIL") ? root.get("CCP_DETAIL") : null;
                if (cppDetailObj instanceof JSONArray) {
                    // JSON Array
                    cpp_details = (JSONArray) cppDetailObj;

                    for (int index = 0; index < cpp_details.length(); index++) {
                        JSONObject jsonObject = cpp_details.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("CCP_DETAIL");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setPymntSts(jsonObject.has("PAYMENT_STATUS") ? String.valueOf(jsonObject.get("PAYMENT_STATUS")) : "");
                        mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                        mwLoanAppMfcibData.setStsMnth(jsonObject.has("STATUS_MONTH") ? String.valueOf(jsonObject.get("STATUS_MONTH")) : "");
                        mwLoanAppMfcibData.setOverdueAmt(jsonObject.has("OVERDUEAMOUNT") ? String.valueOf(jsonObject.get("OVERDUEAMOUNT")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (cppDetailObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) cppDetailObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("CCP_DETAIL");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setPymntSts(jsonObject.has("PAYMENT_STATUS") ? String.valueOf(jsonObject.get("PAYMENT_STATUS")) : "");
                    mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                    mwLoanAppMfcibData.setStsMnth(jsonObject.has("STATUS_MONTH") ? String.valueOf(jsonObject.get("STATUS_MONTH")) : "");
                    mwLoanAppMfcibData.setOverdueAmt(jsonObject.has("OVERDUEAMOUNT") ? String.valueOf(jsonObject.get("OVERDUEAMOUNT")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("CCP_DETAIL");
                //root.put("CCP_DETAIL", cpp_details);

                //---------------------------------------------------------------------------------------------------------
                // 8. CREDIT_SUMMARY
                //---------------------------------------------------------------------------------------------------------
                JSONArray creditSummaries = new JSONArray();
                Object creditSummaryObj = root.has("CREDIT_SUMMARY") ? root.get("CREDIT_SUMMARY") : null;
                if (creditSummaryObj instanceof JSONArray) {
                    // JSON Array
                    creditSummaries = (JSONArray) creditSummaryObj;

                    for (int index = 0; index < creditSummaries.length(); index++) {
                        JSONObject jsonObject = creditSummaries.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("CREDIT_SUMMARY");
                        mwLoanAppMfcibData.setLoanLess10K(jsonObject.has("LOAN_LESS_10K") ? String.valueOf(jsonObject.get("LOAN_LESS_10K")) : "");
                        mwLoanAppMfcibData.setCurrent30Plus(jsonObject.has("CURRENT_30PLUS") ? String.valueOf(jsonObject.get("CURRENT_30PLUS")) : "");
                        mwLoanAppMfcibData.setCurrent60Plus(jsonObject.has("CURRENT_60PLUS") ? String.valueOf(jsonObject.get("CURRENT_60PLUS")) : "");
                        mwLoanAppMfcibData.setCurrent90Plus(jsonObject.has("CURRENT_90PLUS") ? String.valueOf(jsonObject.get("CURRENT_90PLUS")) : "");
                        mwLoanAppMfcibData.setEnquiryCount(jsonObject.has("ENQUIRY_COUNT") ? String.valueOf(jsonObject.get("ENQUIRY_COUNT")) : "");
                        mwLoanAppMfcibData.setCloseWithinMaturity(jsonObject.has("CLOSE_WITHIN_MATURITY") ? String.valueOf(jsonObject.get("CLOSE_WITHIN_MATURITY")) : "");
                        mwLoanAppMfcibData.setCloseAfterMaturity(jsonObject.has("CLOSE_AFTER_MATURITY") ? String.valueOf(jsonObject.get("CLOSE_AFTER_MATURITY")) : "");
                        mwLoanAppMfcibData.setLoanLimit(jsonObject.has("LOAN_LIMIT") ? String.valueOf(jsonObject.get("LOAN_LIMIT")) : "");
                        mwLoanAppMfcibData.setMonth2430Plus(jsonObject.has("MONTH24_30PLUS") ? String.valueOf(jsonObject.get("MONTH24_30PLUS")) : "");
                        mwLoanAppMfcibData.setMonth2460Plus(jsonObject.has("MONTH24_60PLUS") ? String.valueOf(jsonObject.get("MONTH24_60PLUS")) : "");
                        mwLoanAppMfcibData.setFileCreationDt(jsonObject.has("FILE_CREATION_DT") ? String.valueOf(jsonObject.get("FILE_CREATION_DT")) : "");
                        mwLoanAppMfcibData.setCsName(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                        mwLoanAppMfcibData.setLoanCount(jsonObject.has("LOAN_COUNT") ? String.valueOf(jsonObject.get("LOAN_COUNT")) : "");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setLoanOs(jsonObject.has("LOAN_OS") ? String.valueOf(jsonObject.get("LOAN_OS")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRNX_NO") ? String.valueOf(jsonObject.get("TRNX_NO")) : "");
                        mwLoanAppMfcibData.setCsCtgry(jsonObject.has("CATEGORY") ? String.valueOf(jsonObject.get("CATEGORY")) : "");
                        mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                        mwLoanAppMfcibData.setDefaultCount(jsonObject.has("DEFAULT_COUNT") ? String.valueOf(jsonObject.get("DEFAULT_COUNT")) : "");
                        mwLoanAppMfcibData.setAssoc(jsonObject.has("ASSOCIATION") ? String.valueOf(jsonObject.get("ASSOCIATION")) : "");
                        mwLoanAppMfcibData.setMonth2490Plus(jsonObject.has("MONTH24_90PLUS") ? String.valueOf(jsonObject.get("MONTH24_90PLUS")) : "");
                        mwLoanAppMfcibData.setLoanAbove10K(jsonObject.has("LOAN_ABOVE_10K") ? String.valueOf(jsonObject.get("LOAN_ABOVE_10K")) : "");
                        mwLoanAppMfcibData.setDefaultOs(jsonObject.has("DEFAULT_OS") ? String.valueOf(jsonObject.get("DEFAULT_OS")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);

                        // Table - Credit Summary in MWX
                        MwLoanAppCrdtSumry mwLoanAppCrdtSumry = new MwLoanAppCrdtSumry();
                        mwLoanAppCrdtSumry.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppCrdtSumry.setLoanLess10K(jsonObject.has("LOAN_LESS_10K") ? String.valueOf(jsonObject.get("LOAN_LESS_10K")) : "");
                        mwLoanAppCrdtSumry.setCurrent30Plus(jsonObject.has("CURRENT_30PLUS") ? String.valueOf(jsonObject.get("CURRENT_30PLUS")) : "");
                        mwLoanAppCrdtSumry.setCurrent60Plus(jsonObject.has("CURRENT_60PLUS") ? String.valueOf(jsonObject.get("CURRENT_60PLUS")) : "");
                        mwLoanAppCrdtSumry.setCurrent90Plus(jsonObject.has("CURRENT_90PLUS") ? String.valueOf(jsonObject.get("CURRENT_90PLUS")) : "");
                        mwLoanAppCrdtSumry.setEnquiryCount(jsonObject.has("ENQUIRY_COUNT") ? String.valueOf(jsonObject.get("ENQUIRY_COUNT")) : "");
                        mwLoanAppCrdtSumry.setCloseWithinMaturity(jsonObject.has("CLOSE_WITHIN_MATURITY") ? String.valueOf(jsonObject.get("CLOSE_WITHIN_MATURITY")) : "");
                        mwLoanAppCrdtSumry.setCloseAfterMaturity(jsonObject.has("CLOSE_AFTER_MATURITY") ? String.valueOf(jsonObject.get("CLOSE_AFTER_MATURITY")) : "");
                        mwLoanAppCrdtSumry.setLoanLimit(jsonObject.has("LOAN_LIMIT") ? String.valueOf(jsonObject.get("LOAN_LIMIT")) : "");
                        mwLoanAppCrdtSumry.setMonth2430Plus(jsonObject.has("MONTH24_30PLUS") ? String.valueOf(jsonObject.get("MONTH24_30PLUS")) : "");
                        mwLoanAppCrdtSumry.setCsNm(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                        mwLoanAppCrdtSumry.setLoanCount(jsonObject.has("LOAN_COUNT") ? String.valueOf(jsonObject.get("LOAN_COUNT")) : "");
                        mwLoanAppCrdtSumry.setLoanOst(jsonObject.has("LOAN_OS") ? String.valueOf(jsonObject.get("LOAN_OS")) : "");
                        mwLoanAppCrdtSumry.setCsCtgry(jsonObject.has("CATEGORY") ? String.valueOf(jsonObject.get("CATEGORY")) : "");
                        mwLoanAppCrdtSumry.setCsCnicNum(jsonObject.has("CNIC") ? jsonObject.getLong("CNIC") : 0);
                        mwLoanAppCrdtSumry.setDefaultCount(jsonObject.has("DEFAULT_COUNT") ? String.valueOf(jsonObject.get("DEFAULT_COUNT")) : "");
                        mwLoanAppCrdtSumry.setMonth2490Plus(jsonObject.has("MONTH24_90PLUS") ? String.valueOf(jsonObject.get("MONTH24_90PLUS")) : "");
                        mwLoanAppCrdtSumry.setLoanAbove10K(jsonObject.has("LOAN_ABOVE_10K") ? String.valueOf(jsonObject.get("LOAN_ABOVE_10K")) : "");
                        mwLoanAppCrdtSumry.setDefaultOst(jsonObject.has("DEFAULT_OS") ? String.valueOf(jsonObject.get("DEFAULT_OS")) : "");
                        mwLoanAppCrdtSumry.setCrntRecFlg(true);
                        mwLoanAppCrdtSumry.setCrtdDt(Instant.now());
                        mwLoanAppCrdtSumry.setCrtdBy(user);
                        mwLoanAppCrdtSumry.setLastUpdDt(Instant.now());
                        mwLoanAppCrdtSumry.setLastUpdBy(user);
                        //
                        loanAppCrdtSumryList.add(mwLoanAppCrdtSumry);

                    }
                } else if (creditSummaryObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) creditSummaryObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("CREDIT_SUMMARY");
                    mwLoanAppMfcibData.setLoanLess10K(jsonObject.has("LOAN_LESS_10K") ? String.valueOf(jsonObject.get("LOAN_LESS_10K")) : "");
                    mwLoanAppMfcibData.setCurrent30Plus(jsonObject.has("CURRENT_30PLUS") ? String.valueOf(jsonObject.get("CURRENT_30PLUS")) : "");
                    mwLoanAppMfcibData.setEnquiryCount(jsonObject.has("ENQUIRY_COUNT") ? String.valueOf(jsonObject.get("ENQUIRY_COUNT")) : "");
                    mwLoanAppMfcibData.setCloseWithinMaturity(jsonObject.has("CLOSE_WITHIN_MATURITY") ? String.valueOf(jsonObject.get("CLOSE_WITHIN_MATURITY")) : "");
                    mwLoanAppMfcibData.setCloseAfterMaturity(jsonObject.has("CLOSE_AFTER_MATURITY") ? String.valueOf(jsonObject.get("CLOSE_AFTER_MATURITY")) : "");
                    mwLoanAppMfcibData.setLoanLimit(jsonObject.has("LOAN_LIMIT") ? String.valueOf(jsonObject.get("LOAN_LIMIT")) : "");
                    mwLoanAppMfcibData.setMonth2430Plus(jsonObject.has("MONTH24_30PLUS") ? String.valueOf(jsonObject.get("MONTH24_30PLUS")) : "");
                    mwLoanAppMfcibData.setFileCreationDt(jsonObject.has("FILE_CREATION_DT") ? String.valueOf(jsonObject.get("FILE_CREATION_DT")) : "");
                    mwLoanAppMfcibData.setCsName(jsonObject.has("NAME") ? String.valueOf(jsonObject.get("NAME")) : "");
                    mwLoanAppMfcibData.setLoanCount(jsonObject.has("LOAN_COUNT") ? String.valueOf(jsonObject.get("LOAN_COUNT")) : "");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setLoanOs(jsonObject.has("LOAN_OS") ? String.valueOf(jsonObject.get("LOAN_OS")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRNX_NO") ? String.valueOf(jsonObject.get("TRNX_NO")) : "");
                    mwLoanAppMfcibData.setCsCtgry(jsonObject.has("CATEGORY") ? String.valueOf(jsonObject.get("CATEGORY")) : "");
                    mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                    mwLoanAppMfcibData.setDefaultCount(jsonObject.has("DEFAULT_COUNT") ? String.valueOf(jsonObject.get("DEFAULT_COUNT")) : "");
                    mwLoanAppMfcibData.setAssoc(jsonObject.has("ASSOCIATION") ? String.valueOf(jsonObject.get("ASSOCIATION")) : "");
                    mwLoanAppMfcibData.setMonth2490Plus(jsonObject.has("MONTH24_90PLUS") ? String.valueOf(jsonObject.get("MONTH24_90PLUS")) : "");
                    mwLoanAppMfcibData.setLoanAbove10K(jsonObject.has("LOAN_ABOVE_10K") ? String.valueOf(jsonObject.get("LOAN_ABOVE_10K")) : "");
                    mwLoanAppMfcibData.setDefaultOs(jsonObject.has("DEFAULT_OS") ? String.valueOf(jsonObject.get("DEFAULT_OS")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("CREDIT_SUMMARY");
                //root.put("CREDIT_SUMMARY", creditSummaries);

                //---------------------------------------------------------------------------------------------------------
                // 9. REPORT_MESSAGE
                //---------------------------------------------------------------------------------------------------------
                JSONArray rptMsgs = new JSONArray();
                Object rptMsgObj = root.has("REPORT_MESSAGE") ? root.get("REPORT_MESSAGE") : null;
                if (rptMsgObj instanceof JSONArray) {
                    // JSON Array
                    rptMsgs = (JSONArray) rptMsgObj;

                    for (int index = 0; index < rptMsgs.length(); index++) {
                        JSONObject jsonObject = rptMsgs.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("REPORT_MESSAGE");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setMessage(jsonObject.has("MESSAGE") ? String.valueOf(jsonObject.get("MESSAGE")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (rptMsgObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) rptMsgObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("REPORT_MESSAGE");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setMessage(jsonObject.has("MESSAGE") ? String.valueOf(jsonObject.get("MESSAGE")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("REPORT_MESSAGE");
                //root.put("REPORT_MESSAGE", rptMsgs);

                //---------------------------------------------------------------------------------------------------------
                // 10. FILE_NOTES
                //---------------------------------------------------------------------------------------------------------
                JSONArray fileNotes = new JSONArray();
                Object fileNoteObj = root.has("FILE_NOTES") ? root.get("FILE_NOTES") : null;
                if (fileNoteObj instanceof JSONArray) {
                    // JSON Array
                    fileNotes = (JSONArray) fileNoteObj;

                    for (int index = 0; index < fileNotes.length(); index++) {
                        JSONObject jsonObject = fileNotes.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("FILE_NOTES");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setRefDt(jsonObject.has("REF_DATE") ? String.valueOf(jsonObject.get("REF_DATE")) : "");
                        mwLoanAppMfcibData.setComments(jsonObject.has("COMMENTS") ? String.valueOf(jsonObject.get("COMMENTS")) : "");
                        mwLoanAppMfcibData.setAcctNo(jsonObject.has("ACCT_NO") ? String.valueOf(jsonObject.get("ACCT_NO")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (fileNoteObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) fileNoteObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("FILE_NOTES");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setRefDt(jsonObject.has("REF_DATE") ? String.valueOf(jsonObject.get("REF_DATE")) : "");
                    mwLoanAppMfcibData.setComments(jsonObject.has("COMMENTS") ? String.valueOf(jsonObject.get("COMMENTS")) : "");
                    mwLoanAppMfcibData.setAcctNo(jsonObject.has("ACCT_NO") ? String.valueOf(jsonObject.get("ACCT_NO")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("FILE_NOTES");
                //root.put("FILE_NOTES", fileNotes);

                //---------------------------------------------------------------------------------------------------------
                // 11. COBORROWER_DETAILS
                //---------------------------------------------------------------------------------------------------------
                JSONArray cbrwrDtls = new JSONArray();
                Object cbrwrDtlObj = root.has("COBORROWER_DETAILS") ? root.get("COBORROWER_DETAILS") : null;
                if (cbrwrDtlObj instanceof JSONArray) {
                    // JSON Array
                    cbrwrDtls = (JSONArray) cbrwrDtlObj;

                    for (int index = 0; index < cbrwrDtls.length(); index++) {
                        JSONObject jsonObject = cbrwrDtls.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("COBORROWER_DETAILS");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setCbrFileNo(jsonObject.has("CBR_FILE_NO") ? String.valueOf(jsonObject.get("CBR_FILE_NO")) : "");
                        mwLoanAppMfcibData.setOthrBwr(jsonObject.has("OTHR_BWR") ? String.valueOf(jsonObject.get("OTHR_BWR")) : "");
                        mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (cbrwrDtlObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) cbrwrDtlObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("COBORROWER_DETAILS");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setCbrFileNo(jsonObject.has("CBR_FILE_NO") ? String.valueOf(jsonObject.get("CBR_FILE_NO")) : "");
                    mwLoanAppMfcibData.setOthrBwr(jsonObject.has("OTHR_BWR") ? String.valueOf(jsonObject.get("OTHR_BWR")) : "");
                    mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC") ? String.valueOf(jsonObject.get("CNIC")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("COBORROWER_DETAILS");
                //root.put("COBORROWER_DETAILS", cbrwrDtls);

                //---------------------------------------------------------------------------------------------------------
                // 12. BANKRUPTCY_DETAILS
                //---------------------------------------------------------------------------------------------------------
                JSONArray bnkrpt = new JSONArray();
                Object bnkrptObj = root.has("BANKRUPTCY_DETAILS") ? root.get("BANKRUPTCY_DETAILS") : null;
                if (bnkrptObj instanceof JSONArray) {
                    // JSON Array
                    bnkrpt = (JSONArray) bnkrptObj;

                    for (int index = 0; index < bnkrpt.length(); index++) {
                        JSONObject jsonObject = bnkrpt.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("BANKRUPTCY_DETAILS");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setCoutName(jsonObject.has("COUT_NAME") ? String.valueOf(jsonObject.get("COUT_NAME")) : "");
                        mwLoanAppMfcibData.setDclrtnDt(jsonObject.has("DECL_DT") ? String.valueOf(jsonObject.get("DECL_DT")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (bnkrptObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) bnkrptObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("BANKRUPTCY_DETAILS");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setCoutName(jsonObject.has("COUT_NAME") ? String.valueOf(jsonObject.get("COUT_NAME")) : "");
                    mwLoanAppMfcibData.setDclrtnDt(jsonObject.has("DECL_DT") ? String.valueOf(jsonObject.get("DECL_DT")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("BANKRUPTCY_DETAILS");
                //root.put("BANKRUPTCY_DETAILS", bnkrpt);

                //---------------------------------------------------------------------------------------------------------
                // 13. HOME_INFORMATION
                //---------------------------------------------------------------------------------------------------------
                JSONArray homes = new JSONArray();
                Object homeObj = root.has("HOME_INFORMATION") ? root.get("HOME_INFORMATION") : null;
                if (homeObj instanceof JSONArray) {
                    // JSON Array
                    homes = (JSONArray) homeObj;

                    for (int index = 0; index < homes.length(); index++) {
                        JSONObject jsonObject = homes.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("HOME_INFORMATION");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setReportedOn(jsonObject.has("REPORTED_ON") ? String.valueOf(jsonObject.get("REPORTED_ON")) : "");
                        mwLoanAppMfcibData.setCity(jsonObject.has("CITY") ? String.valueOf(jsonObject.get("CITY")) : "");
                        mwLoanAppMfcibData.setPrmntCity(jsonObject.has("PERMANENT_CITY") ? String.valueOf(jsonObject.get("PERMANENT_CITY")) : "");
                        mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                        mwLoanAppMfcibData.setRsdntlAddrs(jsonObject.has("ADDRESS") ? String.valueOf(jsonObject.get("ADDRESS")) : "");
                        mwLoanAppMfcibData.setPrmntAddrs(jsonObject.has("PERMANENT_ADDRESS") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS")) : "");
                        mwLoanAppMfcibData.setPhone2(jsonObject.has("PHONE2") ? String.valueOf(jsonObject.get("PHONE2")) : "");
                        mwLoanAppMfcibData.setPhone1(jsonObject.has("PHONE1") ? String.valueOf(jsonObject.get("PHONE1")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (homeObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) homeObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("HOME_INFORMATION");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setReportedOn(jsonObject.has("REPORTED_ON") ? String.valueOf(jsonObject.get("REPORTED_ON")) : "");
                    mwLoanAppMfcibData.setCity(jsonObject.has("CITY") ? String.valueOf(jsonObject.get("CITY")) : "");
                    mwLoanAppMfcibData.setPrmntCity(jsonObject.has("PERMANENT_CITY") ? String.valueOf(jsonObject.get("PERMANENT_CITY")) : "");
                    mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                    mwLoanAppMfcibData.setRsdntlAddrs(jsonObject.has("ADDRESS") ? String.valueOf(jsonObject.get("ADDRESS")) : "");
                    mwLoanAppMfcibData.setPrmntAddrs(jsonObject.has("PERMANENT_ADDRESS") ? String.valueOf(jsonObject.get("PERMANENT_ADDRESS")) : "");
                    mwLoanAppMfcibData.setPhone2(jsonObject.has("PHONE2") ? String.valueOf(jsonObject.get("PHONE2")) : "");
                    mwLoanAppMfcibData.setPhone1(jsonObject.has("PHONE1") ? String.valueOf(jsonObject.get("PHONE1")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("HOME_INFORMATION");
                //root.put("HOME_INFORMATION", homes);

                //---------------------------------------------------------------------------------------------------------
                // 14. CCP_SUMMARY_TOTAL
                //---------------------------------------------------------------------------------------------------------
                JSONArray ccpSmryTtls = new JSONArray();
                Object ccpSmryTtlObj = root.has("CCP_SUMMARY_TOTAL") ? root.get("CCP_SUMMARY_TOTAL") : null;
                if (ccpSmryTtlObj instanceof JSONArray) {
                    // JSON Array
                    ccpSmryTtls = (JSONArray) ccpSmryTtlObj;

                    for (int index = 0; index < ccpSmryTtls.length(); index++) {
                        JSONObject jsonObject = ccpSmryTtls.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("CCP_SUMMARY_TOTAL");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setLoss(jsonObject.has("LOSS") ? String.valueOf(jsonObject.get("LOSS")) : "");
                        mwLoanAppMfcibData.setP60(jsonObject.has("P90") ? String.valueOf(jsonObject.get("P90")) : "");
                        mwLoanAppMfcibData.setX(jsonObject.has("X") ? String.valueOf(jsonObject.get("X")) : "");
                        mwLoanAppMfcibData.setP150(jsonObject.has("P150") ? String.valueOf(jsonObject.get("P150")) : "");
                        mwLoanAppMfcibData.setP120(jsonObject.has("P120") ? String.valueOf(jsonObject.get("P120")) : "");
                        mwLoanAppMfcibData.setP60(jsonObject.has("P60") ? String.valueOf(jsonObject.get("P60")) : "");
                        mwLoanAppMfcibData.setOk(jsonObject.has("OK") ? String.valueOf(jsonObject.get("OK")) : "");
                        mwLoanAppMfcibData.setP30(jsonObject.has("P30") ? String.valueOf(jsonObject.get("P30")) : "");
                        mwLoanAppMfcibData.setP180(jsonObject.has("P180") ? String.valueOf(jsonObject.get("P180")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (ccpSmryTtlObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) ccpSmryTtlObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("CCP_SUMMARY_TOTAL");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setLoss(jsonObject.has("LOSS") ? String.valueOf(jsonObject.get("LOSS")) : "");
                    mwLoanAppMfcibData.setP60(jsonObject.has("P90") ? String.valueOf(jsonObject.get("P90")) : "");
                    mwLoanAppMfcibData.setX(jsonObject.has("X") ? String.valueOf(jsonObject.get("X")) : "");
                    mwLoanAppMfcibData.setP150(jsonObject.has("P150") ? String.valueOf(jsonObject.get("P150")) : "");
                    mwLoanAppMfcibData.setP120(jsonObject.has("P120") ? String.valueOf(jsonObject.get("P120")) : "");
                    mwLoanAppMfcibData.setP60(jsonObject.has("P60") ? String.valueOf(jsonObject.get("P60")) : "");
                    mwLoanAppMfcibData.setOk(jsonObject.has("OK") ? String.valueOf(jsonObject.get("OK")) : "");
                    mwLoanAppMfcibData.setP30(jsonObject.has("P30") ? String.valueOf(jsonObject.get("P30")) : "");
                    mwLoanAppMfcibData.setP180(jsonObject.has("P180") ? String.valueOf(jsonObject.get("P180")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("CCP_SUMMARY_TOTAL");
                //root.put("CCP_SUMMARY_TOTAL", ccpSmryTtls);

                //---------------------------------------------------------------------------------------------------------
                // 15. REVIEW
                //---------------------------------------------------------------------------------------------------------
                JSONArray reviews = new JSONArray();
                Object reviewObj = root.has("REVIEW") ? root.get("REVIEW") : null;
                if (reviewObj instanceof JSONArray) {
                    // JSON Array
                    reviews = (JSONArray) reviewObj;

                    for (int index = 0; index < reviews.length(); index++) {
                        JSONObject jsonObject = reviews.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("REVIEW");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setReviews(jsonObject.has("REVIEWS") ? String.valueOf(jsonObject.get("REVIEWS")) : "");
                        mwLoanAppMfcibData.setMemNm(jsonObject.has("MEMBER") ? String.valueOf(jsonObject.get("MEMBER")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (reviewObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) reviewObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("REVIEW");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setReviews(jsonObject.has("REVIEWS") ? String.valueOf(jsonObject.get("REVIEWS")) : "");
                    mwLoanAppMfcibData.setMemNm(jsonObject.has("MEMBER") ? String.valueOf(jsonObject.get("MEMBER")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("REVIEW");
                //root.put("REVIEW", reviews);

                //---------------------------------------------------------------------------------------------------------
                // 16. ENQUIRIES
                //---------------------------------------------------------------------------------------------------------
                JSONArray enquiries = new JSONArray();
                Object enquiryObj = root.has("ENQUIRIES") ? root.get("ENQUIRIES") : null;
                if (enquiryObj instanceof JSONArray) {
                    // JSON Array
                    enquiries = (JSONArray) enquiryObj;

                    for (int index = 0; index < enquiries.length(); index++) {
                        JSONObject jsonObject = enquiries.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("ENQUIRIES");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setCurrency(jsonObject.has("CURRENCY") ? String.valueOf(jsonObject.get("CURRENCY")) : "");
                        mwLoanAppMfcibData.setRefDt(jsonObject.has("REFERENCE_DATE") ? String.valueOf(jsonObject.get("REFERENCE_DATE")) : "");
                        mwLoanAppMfcibData.setAssocTy(jsonObject.has("ASSOC_TY") ? String.valueOf(jsonObject.get("ASSOC_TY")) : "");
                        mwLoanAppMfcibData.setAcctTy(jsonObject.has("ACCT_TY") ? String.valueOf(jsonObject.get("ACCT_TY")) : "");
                        mwLoanAppMfcibData.setEnqSts(jsonObject.has("ENQ_STS") ? String.valueOf(jsonObject.get("ENQ_STS")) : "");
                        mwLoanAppMfcibData.setDispute(jsonObject.has("DISPUTE") ? String.valueOf(jsonObject.get("DISPUTE")) : "");
                        mwLoanAppMfcibData.setUpdAmt(jsonObject.has("AMOUNT") ? String.valueOf(jsonObject.get("AMOUNT")) : "");
                        mwLoanAppMfcibData.setMemNm(jsonObject.has("MEM_NAME") ? String.valueOf(jsonObject.get("MEM_NAME")) : "");
                        mwLoanAppMfcibData.setMappedAcctTy(jsonObject.has("MAPPED_ACCT_TY") ? String.valueOf(jsonObject.get("MAPPED_ACCT_TY")) : "");
                        mwLoanAppMfcibData.setSeprteDt(jsonObject.has("SEPARATE_DATE") ? String.valueOf(jsonObject.get("SEPARATE_DATE")) : "");
                        mwLoanAppMfcibData.setSubbrnName(jsonObject.has("SUBBRN_NAME") ? String.valueOf(jsonObject.get("SUBBRN_NAME")) : "");
                        mwLoanAppMfcibData.setRefNo(jsonObject.has("REFERENCE_NO") ? String.valueOf(jsonObject.get("REFERENCE_NO")) : "");
                        mwLoanAppMfcibData.setApplicationDt(jsonObject.has("APPLICATION_DATE") ? String.valueOf(jsonObject.get("APPLICATION_DATE")) : "");
                        mwLoanAppMfcibData.setEnqGroupId(jsonObject.has("GROUP_ID") ? String.valueOf(jsonObject.get("GROUP_ID")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (enquiryObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) enquiryObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("ENQUIRIES");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setCurrency(jsonObject.has("CURRENCY") ? String.valueOf(jsonObject.get("CURRENCY")) : "");
                    mwLoanAppMfcibData.setRefDt(jsonObject.has("REFERENCE_DATE") ? String.valueOf(jsonObject.get("REFERENCE_DATE")) : "");
                    mwLoanAppMfcibData.setAssocTy(jsonObject.has("ASSOC_TY") ? String.valueOf(jsonObject.get("ASSOC_TY")) : "");
                    mwLoanAppMfcibData.setAcctTy(jsonObject.has("ACCT_TY") ? String.valueOf(jsonObject.get("ACCT_TY")) : "");
                    mwLoanAppMfcibData.setEnqSts(jsonObject.has("ENQ_STS") ? String.valueOf(jsonObject.get("ENQ_STS")) : "");
                    mwLoanAppMfcibData.setDispute(jsonObject.has("DISPUTE") ? String.valueOf(jsonObject.get("DISPUTE")) : "");
                    mwLoanAppMfcibData.setUpdAmt(jsonObject.has("AMOUNT") ? String.valueOf(jsonObject.get("AMOUNT")) : "");
                    mwLoanAppMfcibData.setMemNm(jsonObject.has("MEM_NAME") ? String.valueOf(jsonObject.get("MEM_NAME")) : "");
                    mwLoanAppMfcibData.setMappedAcctTy(jsonObject.has("MAPPED_ACCT_TY") ? String.valueOf(jsonObject.get("MAPPED_ACCT_TY")) : "");
                    mwLoanAppMfcibData.setSeprteDt(jsonObject.has("SEPARATE_DATE") ? String.valueOf(jsonObject.get("SEPARATE_DATE")) : "");
                    mwLoanAppMfcibData.setSubbrnName(jsonObject.has("SUBBRN_NAME") ? String.valueOf(jsonObject.get("SUBBRN_NAME")) : "");
                    mwLoanAppMfcibData.setRefNo(jsonObject.has("REFERENCE_NO") ? String.valueOf(jsonObject.get("REFERENCE_NO")) : "");
                    mwLoanAppMfcibData.setApplicationDt(jsonObject.has("APPLICATION_DATE") ? String.valueOf(jsonObject.get("APPLICATION_DATE")) : "");
                    mwLoanAppMfcibData.setEnqGroupId(jsonObject.has("GROUP_ID") ? String.valueOf(jsonObject.get("GROUP_ID")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("ENQUIRIES");
                //root.put("ENQUIRIES", enquiries);


                //---------------------------------------------------------------------------------------------------------
                // 17. ASSOCIATION
                //---------------------------------------------------------------------------------------------------------
                JSONArray assocs = new JSONArray();
                Object assocObj = root.has("ASSOCIATION") ? root.get("ASSOCIATION") : null;
                if (assocObj instanceof JSONArray) {
                    // JSON Array
                    assocs = (JSONArray) assocObj;

                    for (int index = 0; index < assocs.length(); index++) {
                        JSONObject jsonObject = assocs.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("ASSOCIATION");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setNic(jsonObject.has("NIC_NO") ? String.valueOf(jsonObject.get("NIC_NO")) : "");
                        mwLoanAppMfcibData.setLastNm(jsonObject.has("LAST_NAME") ? String.valueOf(jsonObject.get("LAST_NAME")) : "");
                        mwLoanAppMfcibData.setGrntrDt(jsonObject.has("GUARANTEE_DATE") ? String.valueOf(jsonObject.get("GUARANTEE_DATE")) : "");
                        mwLoanAppMfcibData.setGrnteAmt(jsonObject.has("GUARANTEE_AMOUNT") ? String.valueOf(jsonObject.get("GUARANTEE_AMOUNT")) : "");
                        mwLoanAppMfcibData.setMiddleNm(jsonObject.has("MIDDLE_NAME") ? String.valueOf(jsonObject.get("MIDDLE_NAME")) : "");
                        mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC_NO") ? String.valueOf(jsonObject.get("CNIC_NO")) : "");
                        mwLoanAppMfcibData.setInvocationDt(jsonObject.has("INVOCATION_DATE") ? String.valueOf(jsonObject.get("INVOCATION_DATE")) : "");
                        mwLoanAppMfcibData.setSection(jsonObject.has("SECTION") ? String.valueOf(jsonObject.get("SECTION")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (assocObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) assocObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("ASSOCIATION");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setNic(jsonObject.has("NIC_NO") ? String.valueOf(jsonObject.get("NIC_NO")) : "");
                    mwLoanAppMfcibData.setLastNm(jsonObject.has("LAST_NAME") ? String.valueOf(jsonObject.get("LAST_NAME")) : "");
                    mwLoanAppMfcibData.setGrntrDt(jsonObject.has("GUARANTEE_DATE") ? String.valueOf(jsonObject.get("GUARANTEE_DATE")) : "");
                    mwLoanAppMfcibData.setGrnteAmt(jsonObject.has("GUARANTEE_AMOUNT") ? String.valueOf(jsonObject.get("GUARANTEE_AMOUNT")) : "");
                    mwLoanAppMfcibData.setMiddleNm(jsonObject.has("MIDDLE_NAME") ? String.valueOf(jsonObject.get("MIDDLE_NAME")) : "");
                    mwLoanAppMfcibData.setCnic(jsonObject.has("CNIC_NO") ? String.valueOf(jsonObject.get("CNIC_NO")) : "");
                    mwLoanAppMfcibData.setInvocationDt(jsonObject.has("INVOCATION_DATE") ? String.valueOf(jsonObject.get("INVOCATION_DATE")) : "");
                    mwLoanAppMfcibData.setSection(jsonObject.has("SECTION") ? String.valueOf(jsonObject.get("SECTION")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("ASSOCIATION");
                //root.put("ASSOCIATION", assocs);


                //---------------------------------------------------------------------------------------------------------
                // 18. CCP_MASTER
                //---------------------------------------------------------------------------------------------------------
                JSONArray ccpMasters = new JSONArray();
                Object ccpMasterObj = root.has("CCP_MASTER") ? root.get("CCP_MASTER") : null;
                if (ccpMasterObj instanceof JSONArray) {
                    // JSON Array
                    ccpMasters = (JSONArray) ccpMasterObj;

                    for (int index = 0; index < ccpMasters.length(); index++) {
                        JSONObject jsonObject = ccpMasters.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("CCP_MASTER");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setSubObj(jsonObject.has("SUB_OBJ") ? String.valueOf(jsonObject.get("SUB_OBJ")) : "");
                        mwLoanAppMfcibData.setLoanLimit(jsonObject.has("LIMIT") ? String.valueOf(jsonObject.get("LIMIT")) : "");
                        mwLoanAppMfcibData.setRschedlFlg(jsonObject.has("RESCHEDULE_FLAG") ? String.valueOf(jsonObject.get("RESCHEDULE_FLAG")) : "");
                        mwLoanAppMfcibData.setTerm(jsonObject.has("TERM") ? String.valueOf(jsonObject.get("TERM")) : "");
                        mwLoanAppMfcibData.setMaturityDt(jsonObject.has("MATURITY_DATE") ? String.valueOf(jsonObject.get("MATURITY_DATE")) : "");
                        mwLoanAppMfcibData.setOverdueAmt(jsonObject.has("OVERDUEAMOUNT") ? String.valueOf(jsonObject.get("OVERDUEAMOUNT")) : "");
                        mwLoanAppMfcibData.setAcctNo(jsonObject.has("ACCT_NO") ? String.valueOf(jsonObject.get("ACCT_NO")) : "");
                        mwLoanAppMfcibData.setHighCredit(jsonObject.has("HIGH_CREDIT") ? String.valueOf(jsonObject.get("HIGH_CREDIT")) : "");
                        mwLoanAppMfcibData.setRescheduleDt(jsonObject.has("RESCHEDULE_DT") ? String.valueOf(jsonObject.get("RESCHEDULE_DT")) : "");
                        mwLoanAppMfcibData.setClassCatg(jsonObject.has("CLASS_CATG") ? String.valueOf(jsonObject.get("CLASS_CATG")) : "");
                        mwLoanAppMfcibData.setAcctTy(jsonObject.has("ACCT_TY") ? String.valueOf(jsonObject.get("ACCT_TY")) : "");
                        mwLoanAppMfcibData.setLoanNo(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                        mwLoanAppMfcibData.setPymntSts(jsonObject.has("PAYMENT_STATUS") ? String.valueOf(jsonObject.get("PAYMENT_STATUS")) : "");
                        mwLoanAppMfcibData.setDispute(jsonObject.has("DISPUTE") ? String.valueOf(jsonObject.get("DISPUTE")) : "");
                        mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                        mwLoanAppMfcibData.setBncChq(jsonObject.has("BNC_CHQ") ? String.valueOf(jsonObject.get("BNC_CHQ")) : "");
                        mwLoanAppMfcibData.setNote(jsonObject.has("NOTE") ? String.valueOf(jsonObject.get("NOTE")) : "");
                        mwLoanAppMfcibData.setRelDt(jsonObject.has("RELATION_DT") ? String.valueOf(jsonObject.get("RELATION_DT")) : "");
                        mwLoanAppMfcibData.setMemNm(jsonObject.has("MEM_NAME") ? String.valueOf(jsonObject.get("MEM_NAME")) : "");
                        mwLoanAppMfcibData.setCurrency(jsonObject.has("CURRENCY") ? String.valueOf(jsonObject.get("CURRENCY")) : "");
                        mwLoanAppMfcibData.setAssocTy(jsonObject.has("ASSOC_TY") ? String.valueOf(jsonObject.get("ASSOC_TY")) : "");
                        mwLoanAppMfcibData.setStatusDt(jsonObject.has("STATUS_DATE") ? String.valueOf(jsonObject.get("STATUS_DATE")) : "");
                        mwLoanAppMfcibData.setEnqGroupId(jsonObject.has("GROUP_ID") ? String.valueOf(jsonObject.get("GROUP_ID")) : "");
                        mwLoanAppMfcibData.setBalance(jsonObject.has("BALANCE") ? String.valueOf(jsonObject.get("BALANCE")) : "");
                        mwLoanAppMfcibData.setLastPymt(jsonObject.has("LAST_PAYMENT") ? String.valueOf(jsonObject.get("LAST_PAYMENT")) : "");
                        mwLoanAppMfcibData.setLoanClassDesc(jsonObject.has("LOAN_CLASS_DESC") ? String.valueOf(jsonObject.get("LOAN_CLASS_DESC")) : "");
                        mwLoanAppMfcibData.setAcctSts(jsonObject.has("ACCT_STATUS") ? String.valueOf(jsonObject.get("ACCT_STATUS")) : "");
                        mwLoanAppMfcibData.setSecure(jsonObject.has("SECURE") ? String.valueOf(jsonObject.get("SECURE")) : "");
                        mwLoanAppMfcibData.setMappedAcctTy(jsonObject.has("MAPPED_ACCT_TY") ? String.valueOf(jsonObject.get("MAPPED_ACCT_TY")) : "");
                        mwLoanAppMfcibData.setMinAmtDue(jsonObject.has("MIN_AMT_DUE") ? String.valueOf(jsonObject.get("MIN_AMT_DUE")) : "");
                        mwLoanAppMfcibData.setRepaymentFreq(jsonObject.has("REPAYMENT_FREQ") ? String.valueOf(jsonObject.get("REPAYMENT_FREQ")) : "");
                        mwLoanAppMfcibData.setOpenDt(jsonObject.has("OPEN_DATE") ? String.valueOf(jsonObject.get("OPEN_DATE")) : "");
                        mwLoanAppMfcibData.setSubbrnName(jsonObject.has("SUBBRN_NAME") ? String.valueOf(jsonObject.get("SUBBRN_NAME")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);

                        //---------------------------------------------------------------------------------------------------------
                        // 18.1 CCP_SUMMARY
                        //---------------------------------------------------------------------------------------------------------
                        JSONArray ccpSmries = new JSONArray();
                        if (jsonObject.has("CCP_SUMMARY")) {
                            Object ccpSmryObj = jsonObject.get("CCP_SUMMARY");
                            if (ccpSmryObj instanceof JSONArray) {
                                // JSON Array
                                ccpSmries = (JSONArray) ccpSmryObj;

                                for (int i = 0; i < ccpSmries.length(); i++) {
                                    JSONObject jsonCcpSmryObject = ccpSmries.getJSONObject(i);

                                    // MwLoanAppMfcibData Object Map
                                    MwLoanAppMfcibData mwLoanAppMfcibData_CcpSmry = new MwLoanAppMfcibData();
                                    mwLoanAppMfcibData_CcpSmry.setLoanAppDocSeq(loanAppDocSeq);
                                    mwLoanAppMfcibData_CcpSmry.setLevel1(parent);
                                    mwLoanAppMfcibData_CcpSmry.setLevel2("ROOT");
                                    mwLoanAppMfcibData_CcpSmry.setLevel3("CCP_MASTER");
                                    mwLoanAppMfcibData_CcpSmry.setTagNm("CCP_SUMMARY");
                                    mwLoanAppMfcibData_CcpSmry.setLoanLess10K(jsonObject.has("LOSS") ? String.valueOf(jsonObject.get("LOSS")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setCurrent30Plus(jsonObject.has("P90") ? String.valueOf(jsonObject.get("P90")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setEnquiryCount(jsonObject.has("P60") ? String.valueOf(jsonObject.get("P60")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setCloseWithinMaturity(jsonObject.has("P30") ? String.valueOf(jsonObject.get("P30")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setCloseAfterMaturity(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setLoanLimit(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setMonth2430Plus(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setFileCreationDt(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setCsName(jsonObject.has("X") ? String.valueOf(jsonObject.get("X")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setLoanCount(jsonObject.has("P150") ? String.valueOf(jsonObject.get("P150")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setFileNo(jsonObject.has("P120") ? String.valueOf(jsonObject.get("P120")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setLoanOs(jsonObject.has("P180") ? String.valueOf(jsonObject.get("P180")) : "");
                                    mwLoanAppMfcibData_CcpSmry.setCrntRecFlg(true);
                                    mwLoanAppMfcibData_CcpSmry.setCrtdDt(Instant.now());
                                    mwLoanAppMfcibData_CcpSmry.setCrtdBy(user);
                                    // List
                                    loanAppMfcibDataList.add(mwLoanAppMfcibData_CcpSmry);
                                }
                            } else if (creditSummaryObj instanceof JSONObject) {
                                // JSON Object
                                JSONObject jsonCcpSmryObject = (JSONObject) creditSummaryObj;
                                // cpp_details.put(obj);

                                // MwLoanAppMfcibData Object Map
                                MwLoanAppMfcibData mwLoanAppMfcibData_CcpSmry = new MwLoanAppMfcibData();
                                mwLoanAppMfcibData_CcpSmry.setLoanAppDocSeq(loanAppDocSeq);
                                mwLoanAppMfcibData_CcpSmry.setLevel1(parent);
                                mwLoanAppMfcibData_CcpSmry.setLevel2("ROOT");
                                mwLoanAppMfcibData_CcpSmry.setLevel3("CCP_MASTER");
                                mwLoanAppMfcibData_CcpSmry.setTagNm("CCP_SUMMARY");
                                mwLoanAppMfcibData_CcpSmry.setLoanLess10K(jsonObject.has("LOSS") ? String.valueOf(jsonObject.get("LOSS")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCurrent30Plus(jsonObject.has("P90") ? String.valueOf(jsonObject.get("P90")) : "");
                                mwLoanAppMfcibData_CcpSmry.setEnquiryCount(jsonObject.has("P60") ? String.valueOf(jsonObject.get("P60")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCloseWithinMaturity(jsonObject.has("P30") ? String.valueOf(jsonObject.get("P30")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCloseAfterMaturity(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                                mwLoanAppMfcibData_CcpSmry.setLoanLimit(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                                mwLoanAppMfcibData_CcpSmry.setMonth2430Plus(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                                mwLoanAppMfcibData_CcpSmry.setFileCreationDt(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCsName(jsonObject.has("X") ? String.valueOf(jsonObject.get("X")) : "");
                                mwLoanAppMfcibData_CcpSmry.setLoanCount(jsonObject.has("P150") ? String.valueOf(jsonObject.get("P150")) : "");
                                mwLoanAppMfcibData_CcpSmry.setFileNo(jsonObject.has("P120") ? String.valueOf(jsonObject.get("P120")) : "");
                                mwLoanAppMfcibData_CcpSmry.setLoanOs(jsonObject.has("P180") ? String.valueOf(jsonObject.get("P180")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCrntRecFlg(true);
                                mwLoanAppMfcibData_CcpSmry.setCrtdDt(Instant.now());
                                mwLoanAppMfcibData_CcpSmry.setCrtdBy(user);
                                // List
                                loanAppMfcibDataList.add(mwLoanAppMfcibData_CcpSmry);
                            }
                            jsonObject.remove("CCP_SUMMARY");
                            jsonObject.put("CCP_SUMMARY", creditSummaries);
                        }

                        //---------------------------------------------------------------------------------------------------------
                        // 18.2 CCP_DETAIL
                        //---------------------------------------------------------------------------------------------------------
                        JSONArray ccpDtls = new JSONArray();
                        if (jsonObject.has("CCP_DETAIL")) {
                            Object ccpDtlObj = jsonObject.get("CCP_DETAIL");
                            if (ccpDtlObj instanceof JSONArray) {
                                // JSON Array
                                ccpDtls = (JSONArray) ccpDtlObj;

                                for (int i = 0; i < ccpDtls.length(); i++) {
                                    JSONObject jsonCcpDtlObject = ccpDtls.getJSONObject(i);

                                    // MwLoanAppMfcibData Object Map
                                    MwLoanAppMfcibData mwLoanAppMfcibData_CcpDtl = new MwLoanAppMfcibData();
                                    mwLoanAppMfcibData_CcpDtl.setLoanAppDocSeq(loanAppDocSeq);
                                    mwLoanAppMfcibData_CcpDtl.setLevel1(parent);
                                    mwLoanAppMfcibData_CcpDtl.setLevel2("ROOT");
                                    mwLoanAppMfcibData_CcpDtl.setLevel3("CCP_MASTER");
                                    mwLoanAppMfcibData_CcpDtl.setTagNm("CCP_DETAIL");
                                    mwLoanAppMfcibData_CcpDtl.setFileNo(jsonCcpDtlObject.has("FILE_NO") ? String.valueOf(jsonCcpDtlObject.get("FILE_NO")) : "");
                                    mwLoanAppMfcibData_CcpDtl.setTrnxNo(jsonCcpDtlObject.has("TRANX_NO") ? String.valueOf(jsonCcpDtlObject.get("TRANX_NO")) : "");
                                    mwLoanAppMfcibData_CcpDtl.setPymntSts(jsonCcpDtlObject.has("PAYMENT_STATUS") ? String.valueOf(jsonCcpDtlObject.get("PAYMENT_STATUS")) : "");
                                    mwLoanAppMfcibData_CcpDtl.setSeqNo(jsonCcpDtlObject.has("SEQ_NO") ? String.valueOf(jsonCcpDtlObject.get("SEQ_NO")) : "");
                                    mwLoanAppMfcibData_CcpDtl.setStsMnth(jsonCcpDtlObject.has("STATUS_MONTH") ? String.valueOf(jsonCcpDtlObject.get("STATUS_MONTH")) : "");
                                    mwLoanAppMfcibData_CcpDtl.setOverdueAmt(jsonCcpDtlObject.has("OVERDUEAMOUNT") ? String.valueOf(jsonCcpDtlObject.get("OVERDUEAMOUNT")) : "");
                                    mwLoanAppMfcibData_CcpDtl.setCrntRecFlg(true);
                                    mwLoanAppMfcibData_CcpDtl.setCrtdDt(Instant.now());
                                    mwLoanAppMfcibData_CcpDtl.setCrtdBy(user);
                                    // List
                                    loanAppMfcibDataList.add(mwLoanAppMfcibData_CcpDtl);
                                }
                            } else if (ccpDtlObj instanceof JSONObject) {
                                // JSON Object
                                JSONObject jsonCcpDtlObject = (JSONObject) ccpDtlObj;
                                // cpp_details.put(obj);

                                // MwLoanAppMfcibData Object Map
                                MwLoanAppMfcibData mwLoanAppMfcibData_CcpDtl = new MwLoanAppMfcibData();
                                mwLoanAppMfcibData_CcpDtl.setLoanAppDocSeq(loanAppDocSeq);
                                mwLoanAppMfcibData_CcpDtl.setLevel1(parent);
                                mwLoanAppMfcibData_CcpDtl.setLevel2("ROOT");
                                mwLoanAppMfcibData_CcpDtl.setLevel3("CCP_MASTER");
                                mwLoanAppMfcibData_CcpDtl.setTagNm("CCP_DETAIL");
                                mwLoanAppMfcibData_CcpDtl.setFileNo(jsonCcpDtlObject.has("FILE_NO") ? String.valueOf(jsonCcpDtlObject.get("FILE_NO")) : "");
                                mwLoanAppMfcibData_CcpDtl.setTrnxNo(jsonCcpDtlObject.has("TRANX_NO") ? String.valueOf(jsonCcpDtlObject.get("TRANX_NO")) : "");
                                mwLoanAppMfcibData_CcpDtl.setPymntSts(jsonCcpDtlObject.has("PAYMENT_STATUS") ? String.valueOf(jsonCcpDtlObject.get("PAYMENT_STATUS")) : "");
                                mwLoanAppMfcibData_CcpDtl.setSeqNo(jsonCcpDtlObject.has("SEQ_NO") ? String.valueOf(jsonCcpDtlObject.get("SEQ_NO")) : "");
                                mwLoanAppMfcibData_CcpDtl.setStsMnth(jsonCcpDtlObject.has("STATUS_MONTH") ? String.valueOf(jsonCcpDtlObject.get("STATUS_MONTH")) : "");
                                mwLoanAppMfcibData_CcpDtl.setOverdueAmt(jsonCcpDtlObject.has("OVERDUEAMOUNT") ? String.valueOf(jsonCcpDtlObject.get("OVERDUEAMOUNT")) : "");
                                mwLoanAppMfcibData_CcpDtl.setCrntRecFlg(true);
                                mwLoanAppMfcibData_CcpDtl.setCrtdDt(Instant.now());
                                mwLoanAppMfcibData_CcpDtl.setCrtdBy(user);
                                // List
                                loanAppMfcibDataList.add(mwLoanAppMfcibData_CcpDtl);
                            }
                            jsonObject.remove("CCP_DETAIL");
                            jsonObject.put("CCP_DETAIL", ccpDtls);
                        }
                    }

                } else if (ccpMasterObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) ccpMasterObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("CCP_MASTER");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setSubObj(jsonObject.has("SUB_OBJ") ? String.valueOf(jsonObject.get("SUB_OBJ")) : "");
                    mwLoanAppMfcibData.setLoanLimit(jsonObject.has("LIMIT") ? String.valueOf(jsonObject.get("LIMIT")) : "");
                    mwLoanAppMfcibData.setRschedlFlg(jsonObject.has("RESCHEDULE_FLAG") ? String.valueOf(jsonObject.get("RESCHEDULE_FLAG")) : "");
                    mwLoanAppMfcibData.setTerm(jsonObject.has("TERM") ? String.valueOf(jsonObject.get("TERM")) : "");
                    mwLoanAppMfcibData.setMaturityDt(jsonObject.has("MATURITY_DATE") ? String.valueOf(jsonObject.get("MATURITY_DATE")) : "");
                    mwLoanAppMfcibData.setOverdueAmt(jsonObject.has("OVERDUEAMOUNT") ? String.valueOf(jsonObject.get("OVERDUEAMOUNT")) : "");
                    mwLoanAppMfcibData.setAcctNo(jsonObject.has("ACCT_NO") ? String.valueOf(jsonObject.get("ACCT_NO")) : "");
                    mwLoanAppMfcibData.setHighCredit(jsonObject.has("HIGH_CREDIT") ? String.valueOf(jsonObject.get("HIGH_CREDIT")) : "");
                    mwLoanAppMfcibData.setRescheduleDt(jsonObject.has("RESCHEDULE_DT") ? String.valueOf(jsonObject.get("RESCHEDULE_DT")) : "");
                    mwLoanAppMfcibData.setClassCatg(jsonObject.has("CLASS_CATG") ? String.valueOf(jsonObject.get("CLASS_CATG")) : "");
                    mwLoanAppMfcibData.setAcctTy(jsonObject.has("ACCT_TY") ? String.valueOf(jsonObject.get("ACCT_TY")) : "");
                    mwLoanAppMfcibData.setLoanNo(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                    mwLoanAppMfcibData.setPymntSts(jsonObject.has("PAYMENT_STATUS") ? String.valueOf(jsonObject.get("PAYMENT_STATUS")) : "");
                    mwLoanAppMfcibData.setDispute(jsonObject.has("DISPUTE") ? String.valueOf(jsonObject.get("DISPUTE")) : "");
                    mwLoanAppMfcibData.setSeqNo(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                    mwLoanAppMfcibData.setBncChq(jsonObject.has("BNC_CHQ") ? String.valueOf(jsonObject.get("BNC_CHQ")) : "");
                    mwLoanAppMfcibData.setNote(jsonObject.has("NOTE") ? String.valueOf(jsonObject.get("NOTE")) : "");
                    mwLoanAppMfcibData.setRelDt(jsonObject.has("RELATION_DT") ? String.valueOf(jsonObject.get("RELATION_DT")) : "");
                    mwLoanAppMfcibData.setMemNm(jsonObject.has("MEM_NAME") ? String.valueOf(jsonObject.get("MEM_NAME")) : "");
                    mwLoanAppMfcibData.setCurrency(jsonObject.has("CURRENCY") ? String.valueOf(jsonObject.get("CURRENCY")) : "");
                    mwLoanAppMfcibData.setAssocTy(jsonObject.has("ASSOC_TY") ? String.valueOf(jsonObject.get("ASSOC_TY")) : "");
                    mwLoanAppMfcibData.setStatusDt(jsonObject.has("STATUS_DATE") ? String.valueOf(jsonObject.get("STATUS_DATE")) : "");
                    mwLoanAppMfcibData.setEnqGroupId(jsonObject.has("GROUP_ID") ? String.valueOf(jsonObject.get("GROUP_ID")) : "");
                    mwLoanAppMfcibData.setBalance(jsonObject.has("BALANCE") ? String.valueOf(jsonObject.get("BALANCE")) : "");
                    mwLoanAppMfcibData.setLastPymt(jsonObject.has("LAST_PAYMENT") ? String.valueOf(jsonObject.get("LAST_PAYMENT")) : "");
                    mwLoanAppMfcibData.setLoanClassDesc(jsonObject.has("LOAN_CLASS_DESC") ? String.valueOf(jsonObject.get("LOAN_CLASS_DESC")) : "");
                    mwLoanAppMfcibData.setAcctSts(jsonObject.has("ACCT_STATUS") ? String.valueOf(jsonObject.get("ACCT_STATUS")) : "");
                    mwLoanAppMfcibData.setSecure(jsonObject.has("SECURE") ? String.valueOf(jsonObject.get("SECURE")) : "");
                    mwLoanAppMfcibData.setMappedAcctTy(jsonObject.has("MAPPED_ACCT_TY") ? String.valueOf(jsonObject.get("MAPPED_ACCT_TY")) : "");
                    mwLoanAppMfcibData.setMinAmtDue(jsonObject.has("MIN_AMT_DUE") ? String.valueOf(jsonObject.get("MIN_AMT_DUE")) : "");
                    mwLoanAppMfcibData.setRepaymentFreq(jsonObject.has("REPAYMENT_FREQ") ? String.valueOf(jsonObject.get("REPAYMENT_FREQ")) : "");
                    mwLoanAppMfcibData.setOpenDt(jsonObject.has("OPEN_DATE") ? String.valueOf(jsonObject.get("OPEN_DATE")) : "");
                    mwLoanAppMfcibData.setSubbrnName(jsonObject.has("SUBBRN_NAME") ? String.valueOf(jsonObject.get("SUBBRN_NAME")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);

                    //---------------------------------------------------------------------------------------------------------
                    // 18.1 CCP_SUMMARY
                    //---------------------------------------------------------------------------------------------------------
                    JSONArray ccpSmries = new JSONArray();
                    if (jsonObject.has("CCP_SUMMARY")) {
                        Object ccpSmryObj = jsonObject.get("CCP_SUMMARY");
                        if (ccpSmryObj instanceof JSONArray) {
                            // JSON Array
                            ccpSmries = (JSONArray) ccpSmryObj;

                            for (int i = 0; i < ccpSmries.length(); i++) {
                                JSONObject jsonCcpSmryObject = ccpSmries.getJSONObject(i);

                                // MwLoanAppMfcibData Object Map
                                MwLoanAppMfcibData mwLoanAppMfcibData_CcpSmry = new MwLoanAppMfcibData();
                                mwLoanAppMfcibData_CcpSmry.setLoanAppDocSeq(loanAppDocSeq);
                                mwLoanAppMfcibData_CcpSmry.setLevel1(parent);
                                mwLoanAppMfcibData_CcpSmry.setLevel2("ROOT");
                                mwLoanAppMfcibData_CcpSmry.setLevel3("CCP_MASTER");
                                mwLoanAppMfcibData_CcpSmry.setTagNm("CCP_SUMMARY");
                                mwLoanAppMfcibData_CcpSmry.setLoanLess10K(jsonObject.has("LOSS") ? String.valueOf(jsonObject.get("LOSS")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCurrent30Plus(jsonObject.has("P90") ? String.valueOf(jsonObject.get("P90")) : "");
                                mwLoanAppMfcibData_CcpSmry.setEnquiryCount(jsonObject.has("P60") ? String.valueOf(jsonObject.get("P60")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCloseWithinMaturity(jsonObject.has("P30") ? String.valueOf(jsonObject.get("P30")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCloseAfterMaturity(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                                mwLoanAppMfcibData_CcpSmry.setLoanLimit(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                                mwLoanAppMfcibData_CcpSmry.setMonth2430Plus(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                                mwLoanAppMfcibData_CcpSmry.setFileCreationDt(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCsName(jsonObject.has("X") ? String.valueOf(jsonObject.get("X")) : "");
                                mwLoanAppMfcibData_CcpSmry.setLoanCount(jsonObject.has("P150") ? String.valueOf(jsonObject.get("P150")) : "");
                                mwLoanAppMfcibData_CcpSmry.setFileNo(jsonObject.has("P120") ? String.valueOf(jsonObject.get("P120")) : "");
                                mwLoanAppMfcibData_CcpSmry.setLoanOs(jsonObject.has("P180") ? String.valueOf(jsonObject.get("P180")) : "");
                                mwLoanAppMfcibData_CcpSmry.setCrntRecFlg(true);
                                mwLoanAppMfcibData_CcpSmry.setCrtdDt(Instant.now());
                                mwLoanAppMfcibData_CcpSmry.setCrtdBy(user);
                                // List
                                loanAppMfcibDataList.add(mwLoanAppMfcibData_CcpSmry);
                            }
                        } else if (creditSummaryObj instanceof JSONObject) {
                            // JSON Object
                            JSONObject jsonCcpSmryObject = (JSONObject) creditSummaryObj;
                            // cpp_details.put(obj);

                            // MwLoanAppMfcibData Object Map
                            MwLoanAppMfcibData mwLoanAppMfcibData_CcpSmry = new MwLoanAppMfcibData();
                            mwLoanAppMfcibData_CcpSmry.setLoanAppDocSeq(loanAppDocSeq);
                            mwLoanAppMfcibData_CcpSmry.setLevel1(parent);
                            mwLoanAppMfcibData_CcpSmry.setLevel2("ROOT");
                            mwLoanAppMfcibData_CcpSmry.setLevel3("CCP_MASTER");
                            mwLoanAppMfcibData_CcpSmry.setTagNm("CCP_SUMMARY");
                            mwLoanAppMfcibData_CcpSmry.setLoanLess10K(jsonObject.has("LOSS") ? String.valueOf(jsonObject.get("LOSS")) : "");
                            mwLoanAppMfcibData_CcpSmry.setCurrent30Plus(jsonObject.has("P90") ? String.valueOf(jsonObject.get("P90")) : "");
                            mwLoanAppMfcibData_CcpSmry.setEnquiryCount(jsonObject.has("P60") ? String.valueOf(jsonObject.get("P60")) : "");
                            mwLoanAppMfcibData_CcpSmry.setCloseWithinMaturity(jsonObject.has("P30") ? String.valueOf(jsonObject.get("P30")) : "");
                            mwLoanAppMfcibData_CcpSmry.setCloseAfterMaturity(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                            mwLoanAppMfcibData_CcpSmry.setLoanLimit(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                            mwLoanAppMfcibData_CcpSmry.setMonth2430Plus(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                            mwLoanAppMfcibData_CcpSmry.setFileCreationDt(jsonObject.has("SEQ_NO") ? String.valueOf(jsonObject.get("SEQ_NO")) : "");
                            mwLoanAppMfcibData_CcpSmry.setCsName(jsonObject.has("X") ? String.valueOf(jsonObject.get("X")) : "");
                            mwLoanAppMfcibData_CcpSmry.setLoanCount(jsonObject.has("P150") ? String.valueOf(jsonObject.get("P150")) : "");
                            mwLoanAppMfcibData_CcpSmry.setFileNo(jsonObject.has("P120") ? String.valueOf(jsonObject.get("P120")) : "");
                            mwLoanAppMfcibData_CcpSmry.setLoanOs(jsonObject.has("P180") ? String.valueOf(jsonObject.get("P180")) : "");
                            mwLoanAppMfcibData_CcpSmry.setCrntRecFlg(true);
                            mwLoanAppMfcibData_CcpSmry.setCrtdDt(Instant.now());
                            mwLoanAppMfcibData_CcpSmry.setCrtdBy(user);
                            // List
                            loanAppMfcibDataList.add(mwLoanAppMfcibData_CcpSmry);
                        }
                        jsonObject.remove("CCP_SUMMARY");
                        jsonObject.put("CCP_SUMMARY", creditSummaries);
                    }

                    //---------------------------------------------------------------------------------------------------------
                    // 18.2 CCP_DETAIL
                    //---------------------------------------------------------------------------------------------------------
                    JSONArray ccpDtls = new JSONArray();
                    if (jsonObject.has("CCP_DETAIL")) {
                        Object ccpDtlObj = jsonObject.get("CCP_DETAIL");
                        if (ccpDtlObj instanceof JSONArray) {
                            // JSON Array
                            ccpDtls = (JSONArray) ccpDtlObj;

                            for (int i = 0; i < ccpDtls.length(); i++) {
                                JSONObject jsonCcpDtlObject = ccpDtls.getJSONObject(i);

                                // MwLoanAppMfcibData Object Map
                                MwLoanAppMfcibData mwLoanAppMfcibData_CcpDtl = new MwLoanAppMfcibData();
                                mwLoanAppMfcibData_CcpDtl.setLoanAppDocSeq(loanAppDocSeq);
                                mwLoanAppMfcibData_CcpDtl.setLevel1(parent);
                                mwLoanAppMfcibData_CcpDtl.setLevel2("ROOT");
                                mwLoanAppMfcibData_CcpDtl.setLevel3("CCP_MASTER");
                                mwLoanAppMfcibData_CcpDtl.setTagNm("CCP_DETAIL");
                                mwLoanAppMfcibData_CcpDtl.setFileNo(jsonCcpDtlObject.has("FILE_NO") ? String.valueOf(jsonCcpDtlObject.get("FILE_NO")) : "");
                                mwLoanAppMfcibData_CcpDtl.setTrnxNo(jsonCcpDtlObject.has("TRANX_NO") ? String.valueOf(jsonCcpDtlObject.get("TRANX_NO")) : "");
                                mwLoanAppMfcibData_CcpDtl.setPymntSts(jsonCcpDtlObject.has("PAYMENT_STATUS") ? String.valueOf(jsonCcpDtlObject.get("PAYMENT_STATUS")) : "");
                                mwLoanAppMfcibData_CcpDtl.setSeqNo(jsonCcpDtlObject.has("SEQ_NO") ? String.valueOf(jsonCcpDtlObject.get("SEQ_NO")) : "");
                                mwLoanAppMfcibData_CcpDtl.setStsMnth(jsonCcpDtlObject.has("STATUS_MONTH") ? String.valueOf(jsonCcpDtlObject.get("STATUS_MONTH")) : "");
                                mwLoanAppMfcibData_CcpDtl.setOverdueAmt(jsonCcpDtlObject.has("OVERDUEAMOUNT") ? String.valueOf(jsonCcpDtlObject.get("OVERDUEAMOUNT")) : "");
                                mwLoanAppMfcibData_CcpDtl.setCrntRecFlg(true);
                                mwLoanAppMfcibData_CcpDtl.setCrtdDt(Instant.now());
                                mwLoanAppMfcibData_CcpDtl.setCrtdBy(user);
                                // List
                                loanAppMfcibDataList.add(mwLoanAppMfcibData_CcpDtl);
                            }
                        } else if (ccpDtlObj instanceof JSONObject) {
                            // JSON Object
                            JSONObject jsonCcpDtlObject = (JSONObject) ccpDtlObj;
                            // cpp_details.put(obj);

                            // MwLoanAppMfcibData Object Map
                            MwLoanAppMfcibData mwLoanAppMfcibData_CcpDtl = new MwLoanAppMfcibData();
                            mwLoanAppMfcibData_CcpDtl.setLoanAppDocSeq(loanAppDocSeq);
                            mwLoanAppMfcibData_CcpDtl.setLevel1(parent);
                            mwLoanAppMfcibData_CcpDtl.setLevel2("ROOT");
                            mwLoanAppMfcibData_CcpDtl.setLevel3("CCP_MASTER");
                            mwLoanAppMfcibData_CcpDtl.setTagNm("CCP_DETAIL");
                            mwLoanAppMfcibData_CcpDtl.setFileNo(jsonCcpDtlObject.has("FILE_NO") ? String.valueOf(jsonCcpDtlObject.get("FILE_NO")) : "");
                            mwLoanAppMfcibData_CcpDtl.setTrnxNo(jsonCcpDtlObject.has("TRANX_NO") ? String.valueOf(jsonCcpDtlObject.get("TRANX_NO")) : "");
                            mwLoanAppMfcibData_CcpDtl.setPymntSts(jsonCcpDtlObject.has("PAYMENT_STATUS") ? String.valueOf(jsonCcpDtlObject.get("PAYMENT_STATUS")) : "");
                            mwLoanAppMfcibData_CcpDtl.setSeqNo(jsonCcpDtlObject.has("SEQ_NO") ? String.valueOf(jsonCcpDtlObject.get("SEQ_NO")) : "");
                            mwLoanAppMfcibData_CcpDtl.setStsMnth(jsonCcpDtlObject.has("STATUS_MONTH") ? String.valueOf(jsonCcpDtlObject.get("STATUS_MONTH")) : "");
                            mwLoanAppMfcibData_CcpDtl.setOverdueAmt(jsonCcpDtlObject.has("OVERDUEAMOUNT") ? String.valueOf(jsonCcpDtlObject.get("OVERDUEAMOUNT")) : "");
                            mwLoanAppMfcibData_CcpDtl.setCrntRecFlg(true);
                            mwLoanAppMfcibData_CcpDtl.setCrtdDt(Instant.now());
                            mwLoanAppMfcibData_CcpDtl.setCrtdBy(user);
                            // List
                            loanAppMfcibDataList.add(mwLoanAppMfcibData_CcpDtl);
                        }
                        jsonObject.remove("CCP_DETAIL");
                        jsonObject.put("CCP_DETAIL", ccpDtls);
                    }
                }
                //root.remove("CCP_MASTER");
                //root.put("CCP_MASTER", ccpMasters);

                //---------------------------------------------------------------------------------------------------------
                // 19. DEFAULTS
                //---------------------------------------------------------------------------------------------------------
                JSONArray defaults = new JSONArray();
                Object defaultObj = root.has("DEFAULTS") ? root.get("DEFAULTS") : null;
                if (defaultObj instanceof JSONArray) {
                    // JSON Array
                    defaults = (JSONArray) defaultObj;

                    for (int index = 0; index < defaults.length(); index++) {
                        JSONObject jsonObject = defaults.getJSONObject(index);

                        // MwLoanAppMfcibData Object Map
                        MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                        mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                        mwLoanAppMfcibData.setLevel1(parent);
                        mwLoanAppMfcibData.setLevel2("ROOT");
                        mwLoanAppMfcibData.setTagNm("DEFAULTS");
                        mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                        mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                        mwLoanAppMfcibData.setNic(jsonObject.has("SUB_OBJ") ? String.valueOf(jsonObject.get("SUB_OBJ")) : "");
                        mwLoanAppMfcibData.setLastNm(jsonObject.has("REL_DT") ? String.valueOf(jsonObject.get("REL_DT")) : "");
                        mwLoanAppMfcibData.setGrntrDt(jsonObject.has("UPD_MAPPED_ACCT_TY") ? String.valueOf(jsonObject.get("UPD_MAPPED_ACCT_TY")) : "");
                        mwLoanAppMfcibData.setGrnteAmt(jsonObject.has("UPD_CURRENCY") ? String.valueOf(jsonObject.get("UPD_CURRENCY")) : "");
                        mwLoanAppMfcibData.setMiddleNm(jsonObject.has("UPD_STATUS") ? String.valueOf(jsonObject.get("UPD_STATUS")) : "");
                        mwLoanAppMfcibData.setCnic(jsonObject.has("CLASS_CATG") ? String.valueOf(jsonObject.get("CLASS_CATG")) : "");
                        mwLoanAppMfcibData.setInvocationDt(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                        mwLoanAppMfcibData.setOrgStsDt(jsonObject.has("ORG_STATUS_DATE") ? String.valueOf(jsonObject.get("ORG_STATUS_DATE")) : "");
                        mwLoanAppMfcibData.setOrgSts(jsonObject.has("ORG_STATUS") ? String.valueOf(jsonObject.get("ORG_STATUS")) : "");
                        mwLoanAppMfcibData.setDispute(jsonObject.has("DISPUTE") ? String.valueOf(jsonObject.get("DISPUTE")) : "");
                        mwLoanAppMfcibData.setNote(jsonObject.has("NOTE") ? String.valueOf(jsonObject.get("NOTE")) : "");
                        mwLoanAppMfcibData.setMemNm(jsonObject.has("MEM_NAME") ? String.valueOf(jsonObject.get("MEM_NAME")) : "");
                        mwLoanAppMfcibData.setOrgRtr(jsonObject.has("ORG_RTR") ? String.valueOf(jsonObject.get("ORG_RTR")) : "");
                        mwLoanAppMfcibData.setUpdAmt(jsonObject.has("UPD_AMOUNT") ? String.valueOf(jsonObject.get("UPD_AMOUNT")) : "");
                        mwLoanAppMfcibData.setRecoveryDate(jsonObject.has("RECOVERY_DATE") ? String.valueOf(jsonObject.get("RECOVERY_DATE")) : "");
                        mwLoanAppMfcibData.setAssocTy(jsonObject.has("ASSOC_TY") ? String.valueOf(jsonObject.get("ASSOC_TY")) : "");
                        mwLoanAppMfcibData.setOrgCurrency(jsonObject.has("ORG_CURRENCY") ? String.valueOf(jsonObject.get("ORG_CURRENCY")) : "");
                        mwLoanAppMfcibData.setOrgMappedAcctTy(jsonObject.has("ORG_MAPPED_ACCT_TY") ? String.valueOf(jsonObject.get("ORG_MAPPED_ACCT_TY")) : "");
                        mwLoanAppMfcibData.setEnqGroupId(jsonObject.has("GROUP_ID") ? String.valueOf(jsonObject.get("GROUP_ID")) : "");
                        mwLoanAppMfcibData.setOrgAcctTy(jsonObject.has("ORG_ACCT_TY") ? String.valueOf(jsonObject.get("ORG_ACCT_TY")) : "");
                        mwLoanAppMfcibData.setOrgAmount(jsonObject.has("ORG_AMOUNT") ? String.valueOf(jsonObject.get("ORG_AMOUNT")) : "");
                        mwLoanAppMfcibData.setUpdStatusDate(jsonObject.has("UPD_STATUS_DATE") ? String.valueOf(jsonObject.get("UPD_STATUS_DATE")) : "");
                        mwLoanAppMfcibData.setUpdAcctNo(jsonObject.has("UPD_ACCT_NO") ? String.valueOf(jsonObject.get("UPD_ACCT_NO")) : "");
                        mwLoanAppMfcibData.setLoanClassDesc(jsonObject.has("LOAN_CLASS_DESC") ? String.valueOf(jsonObject.get("LOAN_CLASS_DESC")) : "");
                        mwLoanAppMfcibData.setSecure(jsonObject.has("SECURE") ? String.valueOf(jsonObject.get("SECURE")) : "");
                        mwLoanAppMfcibData.setUpdAcctTy(jsonObject.has("UPD_ACCT_TY") ? String.valueOf(jsonObject.get("UPD_ACCT_TY")) : "");
                        mwLoanAppMfcibData.setUpdRtr(jsonObject.has("UPD_RTR") ? String.valueOf(jsonObject.get("UPD_RTR")) : "");
                        mwLoanAppMfcibData.setSubbrnName(jsonObject.has("SUBBRN_NAME") ? String.valueOf(jsonObject.get("SUBBRN_NAME")) : "");
                        mwLoanAppMfcibData.setRecoveryAmount(jsonObject.has("RECOVERY_AMOUNT") ? String.valueOf(jsonObject.get("RECOVERY_AMOUNT")) : "");
                        mwLoanAppMfcibData.setCrntRecFlg(true);
                        mwLoanAppMfcibData.setCrtdDt(Instant.now());
                        mwLoanAppMfcibData.setCrtdBy(user);
                        // List
                        loanAppMfcibDataList.add(mwLoanAppMfcibData);
                    }
                } else if (defaultObj instanceof JSONObject) {
                    // JSON Object
                    JSONObject jsonObject = (JSONObject) defaultObj;
                    // cpp_details.put(obj);

                    // MwLoanAppMfcibData Object Map
                    MwLoanAppMfcibData mwLoanAppMfcibData = new MwLoanAppMfcibData();
                    mwLoanAppMfcibData.setLoanAppDocSeq(loanAppDocSeq);
                    mwLoanAppMfcibData.setLevel1(parent);
                    mwLoanAppMfcibData.setLevel2("ROOT");
                    mwLoanAppMfcibData.setTagNm("DEFAULTS");
                    mwLoanAppMfcibData.setFileNo(jsonObject.has("FILE_NO") ? String.valueOf(jsonObject.get("FILE_NO")) : "");
                    mwLoanAppMfcibData.setTrnxNo(jsonObject.has("TRANX_NO") ? String.valueOf(jsonObject.get("TRANX_NO")) : "");
                    mwLoanAppMfcibData.setNic(jsonObject.has("SUB_OBJ") ? String.valueOf(jsonObject.get("SUB_OBJ")) : "");
                    mwLoanAppMfcibData.setLastNm(jsonObject.has("REL_DT") ? String.valueOf(jsonObject.get("REL_DT")) : "");
                    mwLoanAppMfcibData.setGrntrDt(jsonObject.has("UPD_MAPPED_ACCT_TY") ? String.valueOf(jsonObject.get("UPD_MAPPED_ACCT_TY")) : "");
                    mwLoanAppMfcibData.setGrnteAmt(jsonObject.has("UPD_CURRENCY") ? String.valueOf(jsonObject.get("UPD_CURRENCY")) : "");
                    mwLoanAppMfcibData.setMiddleNm(jsonObject.has("UPD_STATUS") ? String.valueOf(jsonObject.get("UPD_STATUS")) : "");
                    mwLoanAppMfcibData.setCnic(jsonObject.has("CLASS_CATG") ? String.valueOf(jsonObject.get("CLASS_CATG")) : "");
                    mwLoanAppMfcibData.setInvocationDt(jsonObject.has("LOAN_NO") ? String.valueOf(jsonObject.get("LOAN_NO")) : "");
                    mwLoanAppMfcibData.setOrgStsDt(jsonObject.has("ORG_STATUS_DATE") ? String.valueOf(jsonObject.get("ORG_STATUS_DATE")) : "");
                    mwLoanAppMfcibData.setOrgSts(jsonObject.has("ORG_STATUS") ? String.valueOf(jsonObject.get("ORG_STATUS")) : "");
                    mwLoanAppMfcibData.setDispute(jsonObject.has("DISPUTE") ? String.valueOf(jsonObject.get("DISPUTE")) : "");
                    mwLoanAppMfcibData.setNote(jsonObject.has("NOTE") ? String.valueOf(jsonObject.get("NOTE")) : "");
                    mwLoanAppMfcibData.setMemNm(jsonObject.has("MEM_NAME") ? String.valueOf(jsonObject.get("MEM_NAME")) : "");
                    mwLoanAppMfcibData.setOrgRtr(jsonObject.has("ORG_RTR") ? String.valueOf(jsonObject.get("ORG_RTR")) : "");
                    mwLoanAppMfcibData.setUpdAmt(jsonObject.has("UPD_AMOUNT") ? String.valueOf(jsonObject.get("UPD_AMOUNT")) : "");
                    mwLoanAppMfcibData.setRecoveryDate(jsonObject.has("RECOVERY_DATE") ? String.valueOf(jsonObject.get("RECOVERY_DATE")) : "");
                    mwLoanAppMfcibData.setAssocTy(jsonObject.has("ASSOC_TY") ? String.valueOf(jsonObject.get("ASSOC_TY")) : "");
                    mwLoanAppMfcibData.setOrgCurrency(jsonObject.has("ORG_CURRENCY") ? String.valueOf(jsonObject.get("ORG_CURRENCY")) : "");
                    mwLoanAppMfcibData.setOrgMappedAcctTy(jsonObject.has("ORG_MAPPED_ACCT_TY") ? String.valueOf(jsonObject.get("ORG_MAPPED_ACCT_TY")) : "");
                    mwLoanAppMfcibData.setEnqGroupId(jsonObject.has("GROUP_ID") ? String.valueOf(jsonObject.get("GROUP_ID")) : "");
                    mwLoanAppMfcibData.setOrgAcctTy(jsonObject.has("ORG_ACCT_TY") ? String.valueOf(jsonObject.get("ORG_ACCT_TY")) : "");
                    mwLoanAppMfcibData.setOrgAmount(jsonObject.has("ORG_AMOUNT") ? String.valueOf(jsonObject.get("ORG_AMOUNT")) : "");
                    mwLoanAppMfcibData.setUpdStatusDate(jsonObject.has("UPD_STATUS_DATE") ? String.valueOf(jsonObject.get("UPD_STATUS_DATE")) : "");
                    mwLoanAppMfcibData.setUpdAcctNo(jsonObject.has("UPD_ACCT_NO") ? String.valueOf(jsonObject.get("UPD_ACCT_NO")) : "");
                    mwLoanAppMfcibData.setLoanClassDesc(jsonObject.has("LOAN_CLASS_DESC") ? String.valueOf(jsonObject.get("LOAN_CLASS_DESC")) : "");
                    mwLoanAppMfcibData.setSecure(jsonObject.has("SECURE") ? String.valueOf(jsonObject.get("SECURE")) : "");
                    mwLoanAppMfcibData.setUpdAcctTy(jsonObject.has("UPD_ACCT_TY") ? String.valueOf(jsonObject.get("UPD_ACCT_TY")) : "");
                    mwLoanAppMfcibData.setUpdRtr(jsonObject.has("UPD_RTR") ? String.valueOf(jsonObject.get("UPD_RTR")) : "");
                    mwLoanAppMfcibData.setSubbrnName(jsonObject.has("SUBBRN_NAME") ? String.valueOf(jsonObject.get("SUBBRN_NAME")) : "");
                    mwLoanAppMfcibData.setRecoveryAmount(jsonObject.has("RECOVERY_AMOUNT") ? String.valueOf(jsonObject.get("RECOVERY_AMOUNT")) : "");
                    mwLoanAppMfcibData.setCrntRecFlg(true);
                    mwLoanAppMfcibData.setCrtdDt(Instant.now());
                    mwLoanAppMfcibData.setCrtdBy(user);
                    // List
                    loanAppMfcibDataList.add(mwLoanAppMfcibData);
                }
                //root.remove("DEFAULTS");
                //root.put("DEFAULTS", defaults);
            }

            // Save Complete Object
            mwLoanAppCrdtSumryRepository.save(loanAppCrdtSumryList);
            mwLoanAppMfcibDataRepository.save(loanAppMfcibDataList);

            log.info("Credit Summary -> Data:" + loanAppCrdtSumryList.size());
            log.info("MFCIB Parsed -> Data:" + loanAppMfcibDataList.size());

            loanAppCrdtSumryList.clear();
            loanAppMfcibDataList.clear();
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "Failure";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Failure";
        }
        return "Success";
    }

    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
