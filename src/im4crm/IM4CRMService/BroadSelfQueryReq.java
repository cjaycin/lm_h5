package im4crm.IM4CRMService;

/**
 * Created by cjay on 2017-11-12.
 */
public class BroadSelfQueryReq {
    private String funCode;
    private String head;
    private BroadbandSelfQueryReq broadbandSelfQueryReq;

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public BroadbandSelfQueryReq getBroadbandSelfQueryReq() {
        return broadbandSelfQueryReq;
    }

    public void setBroadbandSelfQueryReq(BroadbandSelfQueryReq broadbandSelfQueryReq) {
        this.broadbandSelfQueryReq = broadbandSelfQueryReq;
    }
}
