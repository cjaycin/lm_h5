package im4crm.IM4CRMService;

import javax.xml.bind.annotation.*;

/**
 * Created by cjay on 2017-11-12.
 */

@XmlRootElement(name = "BROADBAND_SELF_QUERY_REQ", namespace = "http://ws.chinaunicom.cn/FixedOrderSer/unibssBody/broadbandSelfQueryReq")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "broadbandSelfQueryReq", propOrder = {
        "servicetype",
        "quetype",
        "quetypeinfo"
})
public class BROADBAND_SELF_QUERY_REQ {
    @XmlElement(name = "QUE_TYPE_INFO", required = true)
    protected String quetypeinfo;
    @XmlElement(name = "QUE_TYPE", required = true)
    protected String quetype;
    @XmlElement(name = "SERVICE_TYPE", required = true)
    protected String servicetype;

    public String getQuetypeinfo() {
        return quetypeinfo;
    }

    public void setQuetypeinfo(String quetypeinfo) {
        this.quetypeinfo = quetypeinfo;
    }

    public String getQuetype() {
        return quetype;
    }

    public void setQuetype(String quetype) {
        this.quetype = quetype;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }
}
