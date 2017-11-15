package com.cattsoft.oss.rms.ws.service;

public class ICpnWsProxy implements com.cattsoft.oss.rms.ws.service.ICpnWs {
  private String _endpoint = null;
  private com.cattsoft.oss.rms.ws.service.ICpnWs iCpnWs = null;
  
  public ICpnWsProxy() {
    _initICpnWsProxy();
  }
  
  public ICpnWsProxy(String endpoint) {
    _endpoint = endpoint;
    _initICpnWsProxy();
  }
  
  private void _initICpnWsProxy() {
    try {
      iCpnWs = (new com.cattsoft.oss.rms.ws.service.ICpnWsServiceLocator()).getICpnWsPort();
      if (iCpnWs != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iCpnWs)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iCpnWs)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iCpnWs != null)
      ((javax.xml.rpc.Stub)iCpnWs)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.cattsoft.oss.rms.ws.service.ICpnWs getICpnWs() {
    if (iCpnWs == null)
      _initICpnWsProxy();
    return iCpnWs;
  }
  
  public String callCpnWs(String route, String xmlReq) throws java.rmi.RemoteException, com.cattsoft.oss.rms.ws.service.Exception{
    if (iCpnWs == null)
      _initICpnWsProxy();
    return iCpnWs.callCpnWs(route, xmlReq);
  }
  
  
}