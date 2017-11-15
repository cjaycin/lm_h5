package com.linkage.service;

import java.net.URL;

import org.apache.axis.client.Service;

import com.cattsoft.oss.rms.ws.service.CPNADDRQUERYNMREQ;
import com.cattsoft.oss.rms.ws.service.CPNADDRQUERYNMRSP;
import com.cattsoft.oss.rms.ws.service.ICpnWsServiceSoapBindingStub;
import com.linkage.util.Constant;


public class IcpnCallService {
	public static CPNADDRQUERYNMRSP callServiceImpl(CPNADDRQUERYNMREQ Cpnaddrquerynmreq) {
		CPNADDRQUERYNMRSP Cpnaddrquerynmrsp = null;
		String OssURL = "http://134.0.77.4:7003/trms/services/CpnWs";
		String requestXml = Constant.beanToXml(Cpnaddrquerynmreq,CPNADDRQUERYNMREQ.class);
		String responseXml = null;
		try {
			ICpnWsServiceSoapBindingStub stub = new ICpnWsServiceSoapBindingStub(new URL(OssURL),new Service());
			System.out.println("requestXml:"+requestXml);
			responseXml = stub.callCpnWs("addrQueryForNM", requestXml);  
			System.out.println("responseXml:"+responseXml);
			Cpnaddrquerynmrsp = (CPNADDRQUERYNMRSP) Constant.xmlToBean(responseXml,CPNADDRQUERYNMRSP.class); 
		} catch (Exception e) {
            e.printStackTrace();
        }
		System.out.println("over");	
		return Cpnaddrquerynmrsp;
		
	}

}
