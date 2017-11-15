package _134._8._10._130.web.services.IM4CRMService;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BROADBANDSELFQUERYREQ {
	private String serviceType;
	private String queType;
	private String queTypeInfo;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getQueType() {
		return queType;
	}

	public void setQueType(String queType) {
		this.queType = queType;
	}

	public String getQueTypeInfo() {
		return queTypeInfo;
	}

	public void setQueTypeInfo(String queTypeInfo) {
		this.queTypeInfo = queTypeInfo;
	}
}
