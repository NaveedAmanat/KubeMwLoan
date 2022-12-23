//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.29 at 02:03:19 PM PKT 
//


package com.idev4.loans.cb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RDA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RDA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AccountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AssociationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CellNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CityOrDistrict" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CnicNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DefaultAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DefaultDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="GroupId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NicNoOrPassportNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReasontoReport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TransactionNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="checkerPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="checkerUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="controlBranchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="emailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="employerAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="employerBusinessCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="employerCellNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="employerCityOrDistrict" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="employerCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="employerEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="employerOwnershipStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="employerPhoneNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fatherOrHusbandFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fatherOrHusbandLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fatherOrHusbandMiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="makerPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="makerUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="memberCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="phoneNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="subBranchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RDA", propOrder = {
        "accountNumber",
        "accountType",
        "address",
        "associationType",
        "cellNo",
        "cityOrDistrict",
        "cnicNo",
        "currency",
        "defaultAmount",
        "defaultDate",
        "groupId",
        "nicNoOrPassportNo",
        "reasontoReport",
        "transactionNum",
        "checkerPassword",
        "checkerUserName",
        "controlBranchCode",
        "dateOfBirth",
        "emailAddress",
        "employerAddress",
        "employerBusinessCategory",
        "employerCellNo",
        "employerCityOrDistrict",
        "employerCompanyName",
        "employerEmailAddress",
        "employerOwnershipStatus",
        "employerPhoneNo",
        "fatherOrHusbandFirstName",
        "fatherOrHusbandLastName",
        "fatherOrHusbandMiddleName",
        "firstName",
        "gender",
        "lastName",
        "makerPassword",
        "makerUserName",
        "memberCode",
        "middleName",
        "phoneNo",
        "subBranchCode"
})
public class RDA {

    @XmlElementRef(name = "AccountNumber", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> accountNumber;
    @XmlElementRef(name = "AccountType", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> accountType;
    @XmlElementRef(name = "Address", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> address;
    @XmlElementRef(name = "AssociationType", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> associationType;
    @XmlElementRef(name = "CellNo", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cellNo;
    @XmlElementRef(name = "CityOrDistrict", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cityOrDistrict;
    @XmlElementRef(name = "CnicNo", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cnicNo;
    @XmlElementRef(name = "Currency", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> currency;
    @XmlElementRef(name = "DefaultAmount", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> defaultAmount;
    @XmlElementRef(name = "DefaultDate", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> defaultDate;
    @XmlElementRef(name = "GroupId", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> groupId;
    @XmlElementRef(name = "NicNoOrPassportNo", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nicNoOrPassportNo;
    @XmlElementRef(name = "ReasontoReport", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> reasontoReport;
    @XmlElementRef(name = "TransactionNum", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> transactionNum;
    @XmlElementRef(name = "checkerPassword", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> checkerPassword;
    @XmlElementRef(name = "checkerUserName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> checkerUserName;
    @XmlElementRef(name = "controlBranchCode", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> controlBranchCode;
    @XmlElementRef(name = "dateOfBirth", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dateOfBirth;
    @XmlElementRef(name = "emailAddress", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> emailAddress;
    @XmlElementRef(name = "employerAddress", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> employerAddress;
    @XmlElementRef(name = "employerBusinessCategory", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> employerBusinessCategory;
    @XmlElementRef(name = "employerCellNo", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> employerCellNo;
    @XmlElementRef(name = "employerCityOrDistrict", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> employerCityOrDistrict;
    @XmlElementRef(name = "employerCompanyName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> employerCompanyName;
    @XmlElementRef(name = "employerEmailAddress", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> employerEmailAddress;
    @XmlElementRef(name = "employerOwnershipStatus", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> employerOwnershipStatus;
    @XmlElementRef(name = "employerPhoneNo", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> employerPhoneNo;
    @XmlElementRef(name = "fatherOrHusbandFirstName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fatherOrHusbandFirstName;
    @XmlElementRef(name = "fatherOrHusbandLastName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fatherOrHusbandLastName;
    @XmlElementRef(name = "fatherOrHusbandMiddleName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fatherOrHusbandMiddleName;
    @XmlElementRef(name = "firstName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> firstName;
    @XmlElementRef(name = "gender", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> gender;
    @XmlElementRef(name = "lastName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastName;
    @XmlElementRef(name = "makerPassword", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> makerPassword;
    @XmlElementRef(name = "makerUserName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> makerUserName;
    @XmlElementRef(name = "memberCode", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> memberCode;
    @XmlElementRef(name = "middleName", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> middleName;
    @XmlElementRef(name = "phoneNo", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> phoneNo;
    @XmlElementRef(name = "subBranchCode", namespace = "http://schemas.datacontract.org/2004/07/DataCheckEnquiry", type = JAXBElement.class, required = false)
    protected JAXBElement<String> subBranchCode;

    /**
     * Gets the value of the accountNumber property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setAccountNumber(JAXBElement<String> value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the accountType property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getAccountType() {
        return accountType;
    }

    /**
     * Sets the value of the accountType property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setAccountType(JAXBElement<String> value) {
        this.accountType = value;
    }

    /**
     * Gets the value of the address property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setAddress(JAXBElement<String> value) {
        this.address = value;
    }

    /**
     * Gets the value of the associationType property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getAssociationType() {
        return associationType;
    }

    /**
     * Sets the value of the associationType property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setAssociationType(JAXBElement<String> value) {
        this.associationType = value;
    }

    /**
     * Gets the value of the cellNo property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getCellNo() {
        return cellNo;
    }

    /**
     * Sets the value of the cellNo property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setCellNo(JAXBElement<String> value) {
        this.cellNo = value;
    }

    /**
     * Gets the value of the cityOrDistrict property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getCityOrDistrict() {
        return cityOrDistrict;
    }

    /**
     * Sets the value of the cityOrDistrict property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setCityOrDistrict(JAXBElement<String> value) {
        this.cityOrDistrict = value;
    }

    /**
     * Gets the value of the cnicNo property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getCnicNo() {
        return cnicNo;
    }

    /**
     * Sets the value of the cnicNo property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setCnicNo(JAXBElement<String> value) {
        this.cnicNo = value;
    }

    /**
     * Gets the value of the currency property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setCurrency(JAXBElement<String> value) {
        this.currency = value;
    }

    /**
     * Gets the value of the defaultAmount property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getDefaultAmount() {
        return defaultAmount;
    }

    /**
     * Sets the value of the defaultAmount property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setDefaultAmount(JAXBElement<String> value) {
        this.defaultAmount = value;
    }

    /**
     * Gets the value of the defaultDate property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getDefaultDate() {
        return defaultDate;
    }

    /**
     * Sets the value of the defaultDate property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setDefaultDate(JAXBElement<String> value) {
        this.defaultDate = value;
    }

    /**
     * Gets the value of the groupId property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setGroupId(JAXBElement<String> value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the nicNoOrPassportNo property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getNicNoOrPassportNo() {
        return nicNoOrPassportNo;
    }

    /**
     * Sets the value of the nicNoOrPassportNo property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setNicNoOrPassportNo(JAXBElement<String> value) {
        this.nicNoOrPassportNo = value;
    }

    /**
     * Gets the value of the reasontoReport property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getReasontoReport() {
        return reasontoReport;
    }

    /**
     * Sets the value of the reasontoReport property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setReasontoReport(JAXBElement<String> value) {
        this.reasontoReport = value;
    }

    /**
     * Gets the value of the transactionNum property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getTransactionNum() {
        return transactionNum;
    }

    /**
     * Sets the value of the transactionNum property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setTransactionNum(JAXBElement<String> value) {
        this.transactionNum = value;
    }

    /**
     * Gets the value of the checkerPassword property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getCheckerPassword() {
        return checkerPassword;
    }

    /**
     * Sets the value of the checkerPassword property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setCheckerPassword(JAXBElement<String> value) {
        this.checkerPassword = value;
    }

    /**
     * Gets the value of the checkerUserName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getCheckerUserName() {
        return checkerUserName;
    }

    /**
     * Sets the value of the checkerUserName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setCheckerUserName(JAXBElement<String> value) {
        this.checkerUserName = value;
    }

    /**
     * Gets the value of the controlBranchCode property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getControlBranchCode() {
        return controlBranchCode;
    }

    /**
     * Sets the value of the controlBranchCode property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setControlBranchCode(JAXBElement<String> value) {
        this.controlBranchCode = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setDateOfBirth(JAXBElement<String> value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the emailAddress property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmailAddress(JAXBElement<String> value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the employerAddress property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmployerAddress() {
        return employerAddress;
    }

    /**
     * Sets the value of the employerAddress property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmployerAddress(JAXBElement<String> value) {
        this.employerAddress = value;
    }

    /**
     * Gets the value of the employerBusinessCategory property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmployerBusinessCategory() {
        return employerBusinessCategory;
    }

    /**
     * Sets the value of the employerBusinessCategory property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmployerBusinessCategory(JAXBElement<String> value) {
        this.employerBusinessCategory = value;
    }

    /**
     * Gets the value of the employerCellNo property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmployerCellNo() {
        return employerCellNo;
    }

    /**
     * Sets the value of the employerCellNo property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmployerCellNo(JAXBElement<String> value) {
        this.employerCellNo = value;
    }

    /**
     * Gets the value of the employerCityOrDistrict property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmployerCityOrDistrict() {
        return employerCityOrDistrict;
    }

    /**
     * Sets the value of the employerCityOrDistrict property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmployerCityOrDistrict(JAXBElement<String> value) {
        this.employerCityOrDistrict = value;
    }

    /**
     * Gets the value of the employerCompanyName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmployerCompanyName() {
        return employerCompanyName;
    }

    /**
     * Sets the value of the employerCompanyName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmployerCompanyName(JAXBElement<String> value) {
        this.employerCompanyName = value;
    }

    /**
     * Gets the value of the employerEmailAddress property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmployerEmailAddress() {
        return employerEmailAddress;
    }

    /**
     * Sets the value of the employerEmailAddress property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmployerEmailAddress(JAXBElement<String> value) {
        this.employerEmailAddress = value;
    }

    /**
     * Gets the value of the employerOwnershipStatus property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmployerOwnershipStatus() {
        return employerOwnershipStatus;
    }

    /**
     * Sets the value of the employerOwnershipStatus property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmployerOwnershipStatus(JAXBElement<String> value) {
        this.employerOwnershipStatus = value;
    }

    /**
     * Gets the value of the employerPhoneNo property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getEmployerPhoneNo() {
        return employerPhoneNo;
    }

    /**
     * Sets the value of the employerPhoneNo property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setEmployerPhoneNo(JAXBElement<String> value) {
        this.employerPhoneNo = value;
    }

    /**
     * Gets the value of the fatherOrHusbandFirstName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getFatherOrHusbandFirstName() {
        return fatherOrHusbandFirstName;
    }

    /**
     * Sets the value of the fatherOrHusbandFirstName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setFatherOrHusbandFirstName(JAXBElement<String> value) {
        this.fatherOrHusbandFirstName = value;
    }

    /**
     * Gets the value of the fatherOrHusbandLastName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getFatherOrHusbandLastName() {
        return fatherOrHusbandLastName;
    }

    /**
     * Sets the value of the fatherOrHusbandLastName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setFatherOrHusbandLastName(JAXBElement<String> value) {
        this.fatherOrHusbandLastName = value;
    }

    /**
     * Gets the value of the fatherOrHusbandMiddleName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getFatherOrHusbandMiddleName() {
        return fatherOrHusbandMiddleName;
    }

    /**
     * Sets the value of the fatherOrHusbandMiddleName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setFatherOrHusbandMiddleName(JAXBElement<String> value) {
        this.fatherOrHusbandMiddleName = value;
    }

    /**
     * Gets the value of the firstName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setFirstName(JAXBElement<String> value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the gender property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setGender(JAXBElement<String> value) {
        this.gender = value;
    }

    /**
     * Gets the value of the lastName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setLastName(JAXBElement<String> value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the makerPassword property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getMakerPassword() {
        return makerPassword;
    }

    /**
     * Sets the value of the makerPassword property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setMakerPassword(JAXBElement<String> value) {
        this.makerPassword = value;
    }

    /**
     * Gets the value of the makerUserName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getMakerUserName() {
        return makerUserName;
    }

    /**
     * Sets the value of the makerUserName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setMakerUserName(JAXBElement<String> value) {
        this.makerUserName = value;
    }

    /**
     * Gets the value of the memberCode property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getMemberCode() {
        return memberCode;
    }

    /**
     * Sets the value of the memberCode property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setMemberCode(JAXBElement<String> value) {
        this.memberCode = value;
    }

    /**
     * Gets the value of the middleName property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setMiddleName(JAXBElement<String> value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the phoneNo property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the value of the phoneNo property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setPhoneNo(JAXBElement<String> value) {
        this.phoneNo = value;
    }

    /**
     * Gets the value of the subBranchCode property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getSubBranchCode() {
        return subBranchCode;
    }

    /**
     * Sets the value of the subBranchCode property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setSubBranchCode(JAXBElement<String> value) {
        this.subBranchCode = value;
    }

}
