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
 *         &lt;element name="authKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rda" type="{http://schemas.datacontract.org/2004/07/DataCheckEnquiry}RDA" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "authKey",
        "rda"
})
@XmlRootElement(name = "ReportDefaultAccountData", namespace = "http://tempuri.org/")
public class ReportDefaultAccountData {

    @XmlElementRef(name = "authKey", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> authKey;
    @XmlElementRef(name = "rda", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RDA> rda;

    /**
     * Gets the value of the authKey property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getAuthKey() {
        return authKey;
    }

    /**
     * Sets the value of the authKey property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setAuthKey(JAXBElement<String> value) {
        this.authKey = value;
    }

    /**
     * Gets the value of the rda property.
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link RDA }{@code >}
     */
    public JAXBElement<RDA> getRda() {
        return rda;
    }

    /**
     * Sets the value of the rda property.
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link RDA }{@code >}
     */
    public void setRda(JAXBElement<RDA> value) {
        this.rda = value;
    }

}
