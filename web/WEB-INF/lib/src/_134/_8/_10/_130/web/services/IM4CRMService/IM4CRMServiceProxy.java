package _134._8._10._130.web.services.IM4CRMService;

public class IM4CRMServiceProxy implements _134._8._10._130.web.services.IM4CRMService.IM4CRMService {
  private String _endpoint = null;
  private _134._8._10._130.web.services.IM4CRMService.IM4CRMService iM4CRMService = null;
  
  public IM4CRMServiceProxy() {
    _initIM4CRMServiceProxy();
  }
  
  public IM4CRMServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIM4CRMServiceProxy();
  }
  
  private void _initIM4CRMServiceProxy() {
    try {
      iM4CRMService = (new _134._8._10._130.web.services.IM4CRMService.IM4CRMServiceServiceLocator()).getIM4CRMService();
      if (iM4CRMService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iM4CRMService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iM4CRMService)._getProperty("javax.xml.rpc.service.endpoint.address");
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
  
  public _134._8._10._130.web.services.IM4CRMService.IM4CRMService getIM4CRMService() {
    if (iM4CRMService == null)
      _initIM4CRMServiceProxy();
    return iM4CRMService;
  }
  
  public String svcCallIOMByCRM(String in0, String in1, String in2) throws java.rmi.RemoteException{
    if (iM4CRMService == null)
      _initIM4CRMServiceProxy();
    return iM4CRMService.svcCallIOMByCRM(in0, in1, in2);
  }
  
  
}