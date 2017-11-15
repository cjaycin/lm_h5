package im4crm.IM4CRMService;

import javax.xml.bind.annotation.*;

/**
 * Created by cjay on 2017-11-12.
 * 进度信息
 */
@XmlRootElement(name = "PROGRESS_INFO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "progressInfo", propOrder = {
        "progressNo",
        "progressStageName",
        "progressDesc",
        "progressState",
        "state_start_time",
        "staff_start_time",
        "staff_name",
        "staff_mode"
})
public class PROGRESS_INFO {
    @XmlElement(name = "PROGRESS_NO")
    protected String progressNo;
    @XmlElement(name = "PROGRESS_STAGE_NAME")
    protected String progressStageName;
    @XmlElement(name = "PROGRESS_DESC")
    protected String progressDesc;
    @XmlElement(name = "PROGRESS_STATE")
    protected String progressState;
    @XmlElement(name = "STAGE_START_TIME")
    protected String state_start_time;
    @XmlElement(name = "STAFF_START_TIME")
    protected String staff_start_time;
    @XmlElement(name = "STAFF_NAME")
    protected String staff_name;
    @XmlElement(name = "STAFF_MODE")
    protected String staff_mode;

    public String getProgressNo() {
        return progressNo;
    }

    public void setProgressNo(String progressNo) {
        this.progressNo = progressNo;
    }

    public String getProgressStageName() {
        return progressStageName;
    }

    public void setProgressStageName(String progressStageName) {
        this.progressStageName = progressStageName;
    }

    public String getProgressDesc() {
        return progressDesc;
    }

    public void setProgressDesc(String progressDesc) {
        this.progressDesc = progressDesc;
    }

    public String getProgressState() {
        return progressState;
    }

    public void setProgressState(String progressState) {
        this.progressState = progressState;
    }

    public String getState_start_time() {
        return state_start_time;
    }

    public void setState_start_time(String state_start_time) {
        this.state_start_time = state_start_time;
    }

    public String getStaff_start_time() {
        return staff_start_time;
    }

    public void setStaff_start_time(String staff_start_time) {
        this.staff_start_time = staff_start_time;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_mode() {
        return staff_mode;
    }

    public void setStaff_mode(String staff_mode) {
        this.staff_mode = staff_mode;
    }
}
