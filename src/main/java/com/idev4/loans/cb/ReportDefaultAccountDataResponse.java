//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.29 at 02:03:19 PM PKT 
//


package com.idev4.loans.cb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReportDefaultAccountDataResult" type="{http://schemas.datacontract.org/2004/07/DataCheckEnquiry}ArrayOfRDAResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "reportDefaultAccountDataResult"
})
@XmlRootElement(name = "ReportDefaultAccountDataResponse", namespace = "http://tempuri.org/")
public class ReportDefaultAccountDataResponse {

    @XmlElementRef(name = "ReportDefaultAccountDataResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfRDAResponse> reportDefaultAccountDataResult;

    /**
     * Gets the value of the reportDefaultAccountDataResult property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link ArrayOfRDAResponse }{@code >}
     */
    public JAXBElement<ArrayOfRDAResponse> getReportDefaultAccountDataResult() {
        return reportDefaultAccountDataResult;
    }

    /**
     * Sets the value of the reportDefaultAccountDataResult property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link ArrayOfRDAResponse }{@code >}
     */
    public void setReportDefaultAccountDataResult(JAXBElement<ArrayOfRDAResponse> value) {
        this.reportDefaultAccountDataResult = value;
    }

}
