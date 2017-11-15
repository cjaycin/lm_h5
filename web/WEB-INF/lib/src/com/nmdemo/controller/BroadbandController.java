package com.nmdemo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.linkage.service.CallService;
import com.nmdemo.model.Broadband;

import _134._8._10._130.web.services.IM4CRMService.BROADBANDSELFQUERYREQ;
import _134._8._10._130.web.services.IM4CRMService.BROADBANDSELFQUERYRSP;






@Controller
public class BroadbandController {

	  @RequestMapping(value = "/tt", method = RequestMethod.GET)
	  public ModelAndView broadband(){
	    ModelAndView modelAndView = new ModelAndView("tt");
	    modelAndView.addObject("broadband", new Broadband());
	    return modelAndView;
	  }
	  @RequestMapping(value="/ttResult")
	  public String  ttResult(@ModelAttribute(value="broadband") Broadband b,ModelMap model){
		    //调用接口
		    BROADBANDSELFQUERYREQ Broadbandselfqueryreq = new BROADBANDSELFQUERYREQ();
		    Broadbandselfqueryreq.setServiceType(b.getServiceType());
		    Broadbandselfqueryreq.setQueType(b.getQueType());
		    Broadbandselfqueryreq.setQueTypeInfo(b.getQueTypeInfo());
		    try {
		    	CallService callService = new CallService();
		    	BROADBANDSELFQUERYRSP Broadbandselfqueryrsp = callService.callServiceImpl(Broadbandselfqueryreq);
			    model.addAttribute("respCode",Broadbandselfqueryrsp.getResp_code());
			    model.addAttribute("respDesc",Broadbandselfqueryrsp.getResp_desc());
		    }catch (Exception e) {
	            e.printStackTrace();
	        }
	      
	    return "ttResult";
	  }  

}
