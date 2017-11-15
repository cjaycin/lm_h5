package im4crm.IM4CRMService;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by cjay on 2017-11-12.
 * 处理结果
 */

@XmlRootElement(name = "BROADBAND_SELF_QUERY_RSP", namespace = "http://ws.chinaunicom.cn/FixedOrderSer/unibssBody/broadbandSelfQueryRsp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "broadbandSelfQueryRsp", propOrder = {
        "respCode",
        "respDesc",
        "orderInfo"
})
public class BROADBAND_SELF_QUERY_RSP {
    @XmlElement(name = "RESP_CODE", required = true)
    protected String respCode;
    @XmlElement(name = "RESP_DESC", required = true)
    protected String respDesc;
    @XmlElement(name = "ORDER_INFO", required = true)
    protected List<ORDER_INFO> orderInfo;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public List<ORDER_INFO> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(List<ORDER_INFO> orderInfo) {
        this.orderInfo = orderInfo;
    }
}
