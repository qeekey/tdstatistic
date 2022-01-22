package com.zqk.stats.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.pojo.Visit;
import com.zqk.stats.service.NotifyService;
import com.zqk.stats.service.VisitService;
import com.zqk.stats.utils.DateUtil;
import com.zqk.stats.utils.Page;
import com.zqk.stats.utils.StringUtil;


@Controller
public class HistoryVisitController extends BaseController {
	
	private static final Log log = LogFactory.getLog(HistoryVisitController.class);
	
	@Resource private VisitService visitService;
	
	private DateUtil du = new DateUtil();
	private static StringUtil su = new StringUtil();



	
	/***
	 * 输出历史访问List结果
	 */
	@RequestMapping("/history/history.do")
	public String doHistoryVisitList(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		
		session = request.getSession();
		long shopid =110 ;
		
		if(session.getAttribute("SHOPID") != null ){
			shopid = (Long)session.getAttribute("SHOPID") ;
		}		
		
		
		//得到当天一个小时内的所有不同城市来源
		Visit vp = new Visit();
		vp.setShopid(shopid);
		vp.setEntertime(du.getBeforeMinuteTime(new Date() , 60 ));
		List<Visit> VisitCountryList = visitService.getVisitCountryList(vp);
		req.setAttribute("VisitCountryList", VisitCountryList);
		

		String country = ServletRequestUtils.getStringParameter(request, "country", "-1");	
		String ip = ServletRequestUtils.getStringParameter(request, "ip", "");				
		String title = ServletRequestUtils.getStringParameter(request, "title", "");
		String entertimeOption = ServletRequestUtils.getStringParameter(request, "entertime", "");

		String page = ServletRequestUtils.getStringParameter(request, "page", "1");
		
		System.out.println("==============page=" + page + " " + System.currentTimeMillis() );
		
		int curpage   = Integer.parseInt(page) ;
		int startNum  = ( curpage -1 ) * TaoBaoComm.PAGESIZE  ;
		System.out.println("==============startNum=" + startNum + " " + System.currentTimeMillis() );
		
		
		
		
		
		Visit visitpara = new Visit();
		visitpara.setShopid(shopid);
		
		System.out.println("===============================>>>>>>>>>>>country =" + country);		
		if(country.length() >0 ){
			visitpara.setCountry(country);
		}
		
		if(ip.length() >0 ){
			visitpara.setIp(ip);	
			System.out.println("===============================>>>>>>>>>>>ip =" + ip);
		}
		if(title.length() >0 ){
			visitpara.setTitle(title);
			System.out.println("===============================>>>>>>>>>>>title =" + title);
		}else{
			System.out.println("===============================>>>>>>>>>>>title is null");
		}
		if(entertimeOption.length() >0 ){
			int beforeMinute = Integer.parseInt(entertimeOption);
			visitpara.setEntertimeOption(beforeMinute+"");
			Date entertime = du.getBeforeMinuteTime(new Date() , beforeMinute ) ; 
			visitpara.setEntertime(entertime);	
			System.out.println("beforeMinute =" + beforeMinute);
		}else{//至少是当天的
			Date today = new Date() ;
			Date daystarttime = null ;
			String startday =  du.DateToStr( today ) ; 
			startday = startday.substring(0, 10) ;				
			daystarttime = du.StrToDate(startday + " 00:00:00" );
			visitpara.setEntertime(daystarttime);	
			System.out.println("daystarttime =" + daystarttime);
		}	
		visitpara.setStartNum(startNum);
		visitpara.setPageNum(TaoBaoComm.PAGESIZE );		
		
		if(request.getParameter("countryHidden") != null ){
			country =(String)request.getParameter("countryHidden") ;
			visitpara.setCountry(country);
			System.out.println("--------------------countryHidden" + country);
		}
		if(request.getParameter("ipHidden") != null && request.getParameter("ipHidden").length() >0){
			ip =(String)request.getParameter("ipHidden") ;
			visitpara.setIp(ip);	
			System.out.println("--------------------ipHidden" + ip );
		}
		if(request.getParameter("titleHidden") != null && request.getParameter("titleHidden").length() >0 ){
			title =(String)request.getParameter("titleHidden") ;
			visitpara.setTitle(title);	
			System.out.println("--------------------titleHidden" + title);
		}
		if(request.getParameter("entertimeOptionHidden") != null && request.getParameter("entertimeOptionHidden").length() >0 ){
			entertimeOption =(String)request.getParameter("entertimeOptionHidden") ;
			int beforeMinute = Integer.parseInt(entertimeOption);
			Date entertime = du.getBeforeMinuteTime(new Date() , beforeMinute ) ; 
			visitpara.setEntertime(entertime);	
			System.out.println("--------------------entertimeOptionHidden=" +  beforeMinute);
		}	
		req.setAttribute("visitpara", visitpara);
		
		List<Visit> historyVisitList = visitService.getHistoryVisitList(visitpara);
		System.out.println("^^^^^^^^^^^^^^historyVisitList.size=" + historyVisitList.size() );
		req.setAttribute("HistoryList", historyVisitList);

		
		//分页
		int totalcount = visitService.getHistoryVisitListCount(visitpara);
		Page pagepojo = new Page(curpage , TaoBaoComm.PAGESIZE);
		pagepojo.setRecordCount(totalcount);
		req.setAttribute("page", pagepojo);
		//
		
		return "/jsp/history/History" ;
	}
	
	
	
	

}
