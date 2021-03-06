//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.17 at 07:00:33 ���� CST 
//


package com.cattsoft.oss.rms.ws.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ADDR_LIST" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ADDR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ADDR_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ADDR_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DTAILEDADDR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ADDR6_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ADDR6_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RESULT_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RESULT_REMARKS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "addrlist",
    "resultcode",
    "resultremarks"
})
@XmlRootElement(name = "CPN_ADDR_QUERY_NM_RSP")
public class CPNADDRQUERYNMRSP {

    @XmlElement(name = "ADDR_LIST")
    protected List<ADDRLIST> addrlist;
    @XmlElement(name = "RESULT_CODE", required = true)
    protected String resultcode;
    @XmlElement(name = "RESULT_REMARKS", required = true)
    protected String resultremarks;

    /**
     * Gets the value of the addrlist property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addrlist property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getADDRLIST().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ADDRLIST }
     * 
     * 
     */
    public List<ADDRLIST> getADDRLIST() {
        if (addrlist == null) {
            addrlist = new ArrayList<ADDRLIST>();
        }
        return this.addrlist;
    }

    /**
     * Gets the value of the resultcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESULTCODE() {
        return resultcode;
    }

    /**
     * Sets the value of the resultcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESULTCODE(String value) {
        this.resultcode = value;
    }

    /**
     * Gets the value of the resultremarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRESULTREMARKS() {
        return resultremarks;
    }

    /**
     * Sets the value of the resultremarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRESULTREMARKS(String value) {
        this.resultremarks = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ADDR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ADDR_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ADDR_LEVEL" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DTAILEDADDR" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ADDR6_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ADDR6_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "addrid",
        "addrname",
        "addrlevel",
        "dtailedaddr",
        "addr6ID",
        "addr6NAME"
    })
    public static class ADDRLIST {

        @XmlElement(name = "ADDR_ID", required = true)
        protected String addrid;
        @XmlElement(name = "ADDR_NAME", required = true)
        protected String addrname;
        @XmlElement(name = "ADDR_LEVEL", required = true)
        protected String addrlevel;
        @XmlElement(name = "DTAILEDADDR", required = true)
        protected String dtailedaddr;
        @XmlElement(name = "ADDR6_ID", required = true)
        protected String addr6ID;
        @XmlElement(name = "ADDR6_NAME", required = true)
        protected String addr6NAME;

        /**
         * Gets the value of the addrid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getADDRID() {
            return addrid;
        }

        /**
         * Sets the value of the addrid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setADDRID(String value) {
            this.addrid = value;
        }

        /**
         * Gets the value of the addrname property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getADDRNAME() {
            return addrname;
        }

        /**
         * Sets the value of the addrname property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setADDRNAME(String value) {
            this.addrname = value;
        }

        /**
         * Gets the value of the addrlevel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getADDRLEVEL() {
            return addrlevel;
        }

        /**
         * Sets the value of the addrlevel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setADDRLEVEL(String value) {
            this.addrlevel = value;
        }

        /**
         * Gets the value of the dtailedaddr property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDTAILEDADDR() {
            return dtailedaddr;
        }

        /**
         * Sets the value of the dtailedaddr property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDTAILEDADDR(String value) {
            this.dtailedaddr = value;
        }

        /**
         * Gets the value of the addr6ID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getADDR6ID() {
            return addr6ID;
        }

        /**
         * Sets the value of the addr6ID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setADDR6ID(String value) {
            this.addr6ID = value;
        }

        /**
         * Gets the value of the addr6NAME property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getADDR6NAME() {
            return addr6NAME;
        }

        /**
         * Sets the value of the addr6NAME property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setADDR6NAME(String value) {
            this.addr6NAME = value;
        }

    }

}
