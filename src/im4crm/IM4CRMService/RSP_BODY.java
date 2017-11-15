package im4crm.IM4CRMService;

import javax.xml.bind.annotation.*;

/**
 * Created by cjay on 2017-11-12.
 */
@XmlRootElement(name = "UNI_BSS_BODY")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rspBody", propOrder = {
        "broadbandSelfQueryRsp"
})
public class RSP_BODY {
    @XmlElement(name = "BROADBAND_SELF_QUERY_RSP")
    protected BROADBAND_SELF_QUERY_RSP broadbandSelfQueryRsp;

    public BROADBAND_SELF_QUERY_RSP getBroadbandSelfQueryRsp() {
        return broadbandSelfQueryRsp;
    }

    public void setBroadbandSelfQueryRsp(BROADBAND_SELF_QUERY_RSP broadbandSelfQueryRsp) {
        this.broadbandSelfQueryRsp = broadbandSelfQueryRsp;
    }
}
