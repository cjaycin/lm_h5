package com.nmdemo.controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cattsoft.oss.rms.ws.service.CPNADDRQUERYNMREQ;
import com.cattsoft.oss.rms.ws.service.CPNADDRQUERYNMRSP;
import com.cattsoft.oss.rms.ws.service.CPNADDRQUERYNMRSP.ADDRLIST;
import com.linkage.service.IcpnCallService;
import com.nmdemo.model.Icpn;





@Controller
public class IcpnController {

	  @RequestMapping(value = "/ICpn", method = RequestMethod.GET)
	  public ModelAndView broadband(){
	    ModelAndView modelAndView = new ModelAndView("ICpn");
	    modelAndView.addObject("icpn", new Icpn());
	    return modelAndView;
	  }
	  @RequestMapping(value="/ICpnResult")
	  public String  ttResult(@ModelAttribute(value="icpn") Icpn i,ModelMap model){
		    //调用接口
		    CPNADDRQUERYNMREQ Cpnaddrquerynmreq = new CPNADDRQUERYNMREQ();
		    Cpnaddrquerynmreq.setLOCALNETID(i.getLocalNetId());
		    Cpnaddrquerynmreq.setAREAID(i.getAreaId());
		    Cpnaddrquerynmreq.setADDRNAME(i.getAddrName());
		    Cpnaddrquerynmreq.setQUERYFLAG(i.getQueryFlag());
		    try {
		    	IcpnCallService icpnCallService = new IcpnCallService();
		    	CPNADDRQUERYNMRSP Cpnaddrquerynmrsp = icpnCallService.callServiceImpl(Cpnaddrquerynmreq);
			    model.addAttribute("respCode",Cpnaddrquerynmrsp.getRESULTCODE());
			    model.addAttribute("respDesc",Cpnaddrquerynmrsp.getRESULTREMARKS());
			    String resultCode = Cpnaddrquerynmrsp.getRESULTCODE();
			    if (resultCode != null && "0".equals(resultCode.trim())) {
			    	List<ADDRLIST> addRlist = Cpnaddrquerynmrsp.getADDRLIST();
			    	model.addAttribute("ADDRLISTS",addRlist);
			    }
		    }catch (Exception e) {
	            e.printStackTrace();
	        }
	      
	    return "ICpnResult";
	  }  

}
