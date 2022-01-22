package com.zqk.stats.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.pojo.ItemHot;
import com.zqk.stats.pojo.Visit;
import com.zqk.stats.service.ItemHotService;
import com.zqk.stats.utils.DateUtil;


@Controller
public class ItemHotController extends BaseController {
	private static final Log log = LogFactory.getLog(ItemHotController.class);
	
	@Resource private ItemHotService itemHotService;
	
	private DateUtil du = new DateUtil();
	
	private int MAXLEN = 24 ;
	
	
	@RequestMapping("/item/itemhot.do")
	public String doTimePerson(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		
		long  shopid = 0 ;
		session = req.getSession() ;
		if( session.getAttribute("SHOPID") != null ){
			shopid  = (Long)session.getAttribute("SHOPID") ;
		}else{
			System.out.println("session shopid is null");
		}
		
		Visit visitpara = new Visit();
		visitpara.setShopid(shopid);
		
		String day = request.getParameter("day");
		if(day != null){
			int days = Integer.parseInt(day);
			log.info("days =" + days );
			
 
			Date today = new Date() ;
			Date daystarttime = null ;
			if(days == 1 ){
				String startday =  du.DateToStr( today ) ; 
				startday = startday.substring(0, 10) ;				
				daystarttime = du.StrToDate(startday + " 00:00:00" );
			}else{
				daystarttime = du.getBeforeDate(today, days);
			}
			visitpara.setEntertime(daystarttime);
			visitpara.setOuttime(today);
			
			List<ItemHot> itemhotlist = itemHotService.TimePersonList(visitpara);
			
			
			int i = 0 ;
			Iterator it = itemhotlist.iterator();
			while(it.hasNext()){
				ItemHot itemHot = (ItemHot)it.next() ;
				itemHot.setPic_url("<img src=" + itemHot.getPic_url()+ TaoBaoComm.ITEMLOGO + ">"); //TaoBaoComm.ITEMLOGO 
				String titlehref= "<a href=\"http://item.taobao.com/item.htm?id=" + itemHot.getNum_iid() +  "&f=tdstats\" target=\"_blank\" >" + itemHot.getTitle() + "</a>" ;
				itemHot.setTitle(titlehref) ;
				itemhotlist.set(i, itemHot);
				i++ ;
			}
			
			req.setAttribute("ItemHotList", itemhotlist );
			
			if( days == 1 ){
				req.setAttribute("ItemHotTitle", "今天热门宝贝") ;
			}if( days == 7 ){
				req.setAttribute("ItemHotTitle", "一周热门宝贝") ;
			}if( days == 30 ){
				req.setAttribute("ItemHotTitle", "近一月热门宝贝") ;
			}
			
		}
		
		
		
		return "/jsp/item/ItemHot";
	}
	
	
	
	   

}
