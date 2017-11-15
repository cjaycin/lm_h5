package com.nmdemo.model;
import org.springframework.format.annotation.DateTimeFormat;

public class Icpn {
	  private String localNetId;
	  private String areaId;
	  private String addrName;
	  private String queryFlag;
	public String getLocalNetId() {
		return localNetId;
	}
	public void setLocalNetId(String localNetId) {
		this.localNetId = localNetId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAddrName() {
		return addrName;
	}
	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	


}
