package com.linkage.test;

import java.net.URL;

import org.apache.axis.client.Service;

import com.linkage.util.Constant;

import _134._8._10._130.web.services.IM4CRMService.BROADBANDSELFQUERYREQ;
import _134._8._10._130.web.services.IM4CRMService.BROADBANDSELFQUERYRSP;
import _134._8._10._130.web.services.IM4CRMService.IM4CRMServiceSoapBindingStub;



public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BROADBANDSELFQUERYREQ Broadbandselfqueryreq = new BROADBANDSELFQUERYREQ();
		BROADBANDSELFQUERYRSP Broadbandselfqueryrsp = null;
		String service_type ="1";
		String que_type ="1";
		String que_type_info ="";
		
		Broadbandselfqueryreq.setServiceType(service_type);
		Broadbandselfqueryreq.setQueType(que_type);
		Broadbandselfqueryreq.setQueTypeInfo(que_type_info);
		
		String OssURL = "http://130.10.8.134:7017/web/services/IM4CRMService";
		
		String requestXml = Constant.beanToXml(Broadbandselfqueryreq,BROADBANDSELFQUERYREQ.class);
		String responseXml = null;
		//���öԶ�WS
		try {
			IM4CRMServiceSoapBindingStub stub = new IM4CRMServiceSoapBindingStub(new URL(OssURL),new Service());
			System.out.println("requestXml:"+requestXml);
			responseXml = stub.svcCallIOMByCRM("installRequireDesireReceive",null, requestXml);  
			System.out.println("responseXml:"+responseXml);
			Broadbandselfqueryrsp = (BROADBANDSELFQUERYRSP) Constant.xmlToBean(responseXml,BROADBANDSELFQUERYRSP.class); 
		} catch (Exception e) {
            e.printStackTrace();
        }
		System.out.println("over");		
	}
}
