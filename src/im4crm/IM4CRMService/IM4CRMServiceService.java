/**
 * IM4CRMServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package im4crm.IM4CRMService;

import javax.xml.rpc.ServiceException;

public interface IM4CRMServiceService extends javax.xml.rpc.Service {
    public String getIM4CRMServiceAddress();

    public IM4CRMService getIM4CRMService() throws javax.xml.rpc.ServiceException;

    public IM4CRMService getIM4CRMService(java.net.URL portAddress) throws ServiceException;
}
