package im4crm.IM4CRMService;

import javax.xml.rpc.Stub;
import java.rmi.RemoteException;

public class IM4CRMServiceProxy implements IM4CRMService {
  private String _endpoint = null;
  private IM4CRMService iM4CRMService = null;
  
  public IM4CRMServiceProxy() {
    _initIM4CRMServiceProxy();
  }
  
  public IM4CRMServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIM4CRMServiceProxy();
  }
  
  private void _initIM4CRMServiceProxy() {
    try {
      iM4CRMService = (new IM4CRMServiceServiceLocator()).getIM4CRMService();
      if (iM4CRMService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iM4CRMService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((Stub)iM4CRMService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iM4CRMService != null)
      ((javax.xml.rpc.Stub)iM4CRMService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public IM4CRMService getIM4CRMService() {
    if (iM4CRMService == null)
      _initIM4CRMServiceProxy();
    return iM4CRMService;
  }
  
  public String svcCallIOMByCRM(String in0, String in1, String in2) throws RemoteException {
    if (iM4CRMService == null)
      _initIM4CRMServiceProxy();
    return iM4CRMService.svcCallIOMByCRM(in0, in1, in2);
  }
  
  
}