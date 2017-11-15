package com.nmdemo.model;
import org.springframework.format.annotation.DateTimeFormat;

public class Broadband {
	  private String serviceType;
	  private String queType;
	  private String queTypeInfo;
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getQueType() {
		return queType;
	}
	public void setQueType(String queType) {
		this.queType = queType;
	}
	public String getQueTypeInfo() {
		return queTypeInfo;
	}
	public void setQueTypeInfo(String queTypeInfo) {
		this.queTypeInfo = queTypeInfo;
	}


}
