package im4crm.IM4CRMService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BroadbandSelfQueryReq")
@XmlAccessorType(XmlAccessType.FIELD)
public class BroadbandSelfQueryReq {
	private String que_type_info;
	private String que_type;
	private String service_type;

	public String getQue_type_info() {
		return que_type_info;
	}

	public void setQue_type_info(String que_type_info) {
		this.que_type_info = que_type_info;
	}

	public String getQue_type() {
		return que_type;
	}

	public void setQue_type(String que_type) {
		this.que_type = que_type;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
}
