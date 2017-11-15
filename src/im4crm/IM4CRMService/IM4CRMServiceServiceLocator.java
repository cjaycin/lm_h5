/**
 * IM4CRMServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package im4crm.IM4CRMService;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

public class IM4CRMServiceServiceLocator extends Service implements IM4CRMServiceService {

    public IM4CRMServiceServiceLocator() {
    }


    public IM4CRMServiceServiceLocator(EngineConfiguration config) {
        super(config);
    }

    public IM4CRMServiceServiceLocator(String wsdlLoc, QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IM4CRMService
    private String IM4CRMService_address = "http://130.10.8.134:7017/web/services/IM4CRMService";

    public String getIM4CRMServiceAddress() {
        return IM4CRMService_address;
    }

    // The WSDD service name defaults to the port name.
    private String IM4CRMServiceWSDDServiceName = "IM4CRMService";

    public String getIM4CRMServiceWSDDServiceName() {
        return IM4CRMServiceWSDDServiceName;
    }

    public void setIM4CRMServiceWSDDServiceName(String name) {
        IM4CRMServiceWSDDServiceName = name;
    }

    public IM4CRMService getIM4CRMService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IM4CRMService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIM4CRMService(endpoint);
    }

    public IM4CRMService getIM4CRMService(java.net.URL portAddress) throws ServiceException {
        try {
            IM4CRMServiceSoapBindingStub _stub = new IM4CRMServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getIM4CRMServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIM4CRMServiceEndpointAddress(String address) {
        IM4CRMService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
        try {
            if (IM4CRMService.class.isAssignableFrom(serviceEndpointInterface)) {
                IM4CRMServiceSoapBindingStub _stub = new IM4CRMServiceSoapBindingStub(new URL(IM4CRMService_address), this);
                _stub.setPortName(getIM4CRMServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("IM4CRMService".equals(inputPortName)) {
            return getIM4CRMService();
        }
        else  {
            Remote _stub = getPort(serviceEndpointInterface);
            ((Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public QName getServiceName() {
        return new QName("http://130.10.8.134:7017/web/services/IM4CRMService", "IM4CRMServiceService");
    }

    private HashSet ports = null;

    public Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName("http://130.10.8.134:7017/web/services/IM4CRMService", "IM4CRMService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws ServiceException {
        
if ("IM4CRMService".equals(portName)) {
            setIM4CRMServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(QName portName, String address) throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
