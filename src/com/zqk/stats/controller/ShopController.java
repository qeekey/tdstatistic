package com.zqk.stats.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqk.stats.pojo.ShopPojo;
import com.zqk.stats.service.ShopService;


@Controller
public class ShopController extends BaseController {
	
	private static final Log log = LogFactory.getLog(ShopController.class);
	
	@Resource private ShopService shopService ;
	
	
	@RequestMapping("/setting/editshop.do")
	public String doEditShop(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		session = req.getSession();
		
		if( session.getAttribute("SHOP") != null ){
			req.setAttribute("SHOP" , session.getAttribute("SHOP") ) ;
		}else{
			log.error("session shopid is null");
		}
		
		String func = req.getParameter("func");
		if(func.equalsIgnoreCase("11")){
			req.setAttribute("func", 11);
		}else if(func.equalsIgnoreCase("12")){
			req.setAttribute("func", 12);
		}
		
		return "/jsp/setting/EditShop";
	}
	
	
	@RequestMapping("/setting/updateshop.do")
	public String doUpdateShop(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		session = req.getSession();
		
		long shopid = 0 ; 
		
		if( session.getAttribute("SHOPID") != null ){
			shopid  = (Long)session.getAttribute("SHOPID") ;
		}else{
			log.error("session shopid is null");
		}
		
		ShopPojo shoppojo = new  ShopPojo() ; 
		
		String func = req.getParameter("func");
		if(func.equalsIgnoreCase("11")){
			int autosynflag = Integer.parseInt( req.getParameter("autosynflag") ) ;
			if( session.getAttribute("SHOP") != null ){
				shoppojo = shopService.getShopById(shopid);
				shoppojo.setAutosynflag(autosynflag);
				shopService.updateShop(shoppojo);
			}
			req.setAttribute("func", 11);
		}else if(func.equalsIgnoreCase("12")){
			int voiceflag = Integer.parseInt( req.getParameter("voiceflag") ) ;
			if( session.getAttribute("SHOP") != null ){
				shoppojo = shopService.getShopById(shopid);
				shoppojo.setVoiceflag(voiceflag);
				shopService.updateShop(shoppojo);
			}
			req.setAttribute("func", 12);
		}
		
		session.setAttribute("SHOP",shoppojo) ;
		req.setAttribute("SHOP" ,  shoppojo ) ;
		
		return "/jsp/setting/EditShop";
	}
	   

}
