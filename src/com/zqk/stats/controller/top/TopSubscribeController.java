package com.zqk.stats.controller.top;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqk.stats.controller.BaseController;
import com.zqk.stats.service.top.TopSubscribeService;

       

@Controller @Scope("prototype")
public class TopSubscribeController extends BaseController {
	
	private static final Log log = LogFactory.getLog(TopSubscribeController.class);
	
	@Resource private TopSubscribeService topSubscribeService ;

	
	@RequestMapping("/getUserSubscribe.do")
	public String doGetUserSubscribe(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		session = req.getSession() ; 
		
		String nick = ServletRequestUtils.getStringParameter(request, "nick" , "qeekey" );
		
		if(nick.length() > 1){
			String UserSubscribeResult = topSubscribeService.getUserSubscribeResult(nick, (String)session.getAttribute("SESSIONKEY") );
		}
		
		return "/jsp/subscribe/GetSubscribe";
	}
	
	

}
