package im4crm.IM4CRMService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cjay on 2017-11-12.
 */
@XmlRootElement(name = "UNI_BSS_BODY")
@XmlAccessorType(XmlAccessType.FIELD)
public class UNI_BSS_BODY {

    private BROADBAND_SELF_QUERY_REQ broadband_self_query_req;

    public BROADBAND_SELF_QUERY_REQ getBroadband_self_query_req() {
        return broadband_self_query_req;
    }

    public void setBroadband_self_query_req(BROADBAND_SELF_QUERY_REQ broadband_self_query_req) {
        this.broadband_self_query_req = broadband_self_query_req;
    }
}
