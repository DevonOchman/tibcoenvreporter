//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.08.22 at 11:48:35 AM EDT 
//


package com.logilabs.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{}profiles"/>
 *         &lt;element ref="{}productsInitialized"/>
 *         &lt;element ref="{}productsAlreadyInstalled" minOccurs="0"/>
 *         &lt;element ref="{}productsSelected"/>
 *       &lt;/sequence>
 *       &lt;attribute name="logsDirectory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tibcoHome" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "profiles",
    "productsInitialized",
    "productsAlreadyInstalled",
    "productsSelected"
})
@XmlRootElement(name = "install")
public class Install {

    @XmlElement(required = true)
    protected Profiles profiles;
    @XmlElement(required = true)
    protected ProductsInitialized productsInitialized;
    protected ProductsAlreadyInstalled productsAlreadyInstalled;
    @XmlElement(required = true)
    protected ProductsSelected productsSelected;
    @XmlAttribute(name = "logsDirectory")
    protected String logsDirectory;
    @XmlAttribute(name = "mode")
    protected String mode;
    @XmlAttribute(name = "tibcoHome")
    protected String tibcoHome;
    @XmlAttribute(name = "timestamp")
    protected String timestamp;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the profiles property.
     * 
     * @return
     *     possible object is
     *     {@link Profiles }
     *     
     */
    public Profiles getProfiles() {
        return profiles;
    }

    /**
     * Sets the value of the profiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Profiles }
     *     
     */
    public void setProfiles(Profiles value) {
        this.profiles = value;
    }

    /**
     * Gets the value of the productsInitialized property.
     * 
     * @return
     *     possible object is
     *     {@link ProductsInitialized }
     *     
     */
    public ProductsInitialized getProductsInitialized() {
        return productsInitialized;
    }

    /**
     * Sets the value of the productsInitialized property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductsInitialized }
     *     
     */
    public void setProductsInitialized(ProductsInitialized value) {
        this.productsInitialized = value;
    }

    /**
     * Gets the value of the productsAlreadyInstalled property.
     * 
     * @return
     *     possible object is
     *     {@link ProductsAlreadyInstalled }
     *     
     */
    public ProductsAlreadyInstalled getProductsAlreadyInstalled() {
        return productsAlreadyInstalled;
    }

    /**
     * Sets the value of the productsAlreadyInstalled property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductsAlreadyInstalled }
     *     
     */
    public void setProductsAlreadyInstalled(ProductsAlreadyInstalled value) {
        this.productsAlreadyInstalled = value;
    }

    /**
     * Gets the value of the productsSelected property.
     * 
     * @return
     *     possible object is
     *     {@link ProductsSelected }
     *     
     */
    public ProductsSelected getProductsSelected() {
        return productsSelected;
    }

    /**
     * Sets the value of the productsSelected property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductsSelected }
     *     
     */
    public void setProductsSelected(ProductsSelected value) {
        this.productsSelected = value;
    }

    /**
     * Gets the value of the logsDirectory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogsDirectory() {
        return logsDirectory;
    }

    /**
     * Sets the value of the logsDirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogsDirectory(String value) {
        this.logsDirectory = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

    /**
     * Gets the value of the tibcoHome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTibcoHome() {
        return tibcoHome;
    }

    /**
     * Sets the value of the tibcoHome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTibcoHome(String value) {
        this.tibcoHome = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
