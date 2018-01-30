package im4crm.IM4CRMService;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by cjay on 2017-11-12.
 * 受理单信息
 */
@XmlRootElement(name = "ORDER_INFO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderInfo", propOrder = {
        "acceptNo",
        "userName",
        "eparchyCode",
        "productTypeCode",
        "staffSet",
        "progressInfo"
})

public class ORDER_INFO {
    @XmlElement(name = "ACCEPT_NO")
    protected String acceptNo;

    @XmlElement(name = "USER_NAME")
    protected String userName;

    @XmlElement(name = "EPARCHY_CODE")
    protected String eparchyCode;

    @XmlElement(name = "PRODUCT_TYPE_CODE")
    protected String productTypeCode;

    @XmlElement(name = "STAFF_SET")
    protected String staffSet;

    @XmlElement(name = "PROGRESS_INFO")
    protected List<PROGRESS_INFO> progressInfo;

    public String getAcceptNo() {
        return acceptNo;
    }

    public void setAcceptNo(String acceptNo) {
        this.acceptNo = acceptNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEparchyCode() {
        return eparchyCode;
    }

    public void setEparchyCode(String eparchyCode) {
        this.eparchyCode = eparchyCode;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getStaffSet() {
        return staffSet;
    }

    public void setStaffSet(String staffSet) {
        this.staffSet = staffSet;
    }

    public List<PROGRESS_INFO> getProgressInfo() {
        return progressInfo;
    }

    public void setProgressInfo(List<PROGRESS_INFO> progressInfo) {
        this.progressInfo = progressInfo;
    }
}
