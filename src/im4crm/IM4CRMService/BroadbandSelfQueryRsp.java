package im4crm.IM4CRMService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BroadbandSelfQueryRsp")
@XmlAccessorType(XmlAccessType.FIELD)
public class BroadbandSelfQueryRsp {
	String resp_code;
	String resp_desc;
	String order_info;
	String accept_no;
	String user_name;
	String eparchy_code;
	String product_type_code;
	String progress_info;
	String progress_no;
	String progress_stage_name;
	String progress_desc;
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getResp_desc() {
		return resp_desc;
	}
	public void setResp_desc(String resp_desc) {
		this.resp_desc = resp_desc;
	}
	public String getOrder_info() {
		return order_info;
	}
	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}
	public String getAccept_no() {
		return accept_no;
	}
	public void setAccept_no(String accept_no) {
		this.accept_no = accept_no;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEparchy_code() {
		return eparchy_code;
	}
	public void setEparchy_code(String eparchy_code) {
		this.eparchy_code = eparchy_code;
	}
	public String getProduct_type_code() {
		return product_type_code;
	}
	public void setProduct_type_code(String product_type_code) {
		this.product_type_code = product_type_code;
	}
	public String getProgress_info() {
		return progress_info;
	}
	public void setProgress_info(String progress_info) {
		this.progress_info = progress_info;
	}
	public String getProgress_no() {
		return progress_no;
	}
	public void setProgress_no(String progress_no) {
		this.progress_no = progress_no;
	}
	public String getProgress_stage_name() {
		return progress_stage_name;
	}
	public void setProgress_stage_name(String progress_stage_name) {
		this.progress_stage_name = progress_stage_name;
	}
	public String getProgress_desc() {
		return progress_desc;
	}
	public void setProgress_desc(String progress_desc) {
		this.progress_desc = progress_desc;
	}
	public String getProgress_state() {
		return progress_state;
	}
	public void setProgress_state(String progress_state) {
		this.progress_state = progress_state;
	}
	public String getStage_start_time() {
		return stage_start_time;
	}
	public void setStage_start_time(String stage_start_time) {
		this.stage_start_time = stage_start_time;
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
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}
	public String getPara_id() {
		return para_id;
	}
	public void setPara_id(String para_id) {
		this.para_id = para_id;
	}
	public String getPara_value() {
		return para_value;
	}
	public void setPara_value(String para_value) {
		this.para_value = para_value;
	}
	String progress_state;
	String stage_start_time;
	String staff_start_time;
	String staff_name;
	String staff_mode;
	String para;
	String para_id;
	String para_value;
}
