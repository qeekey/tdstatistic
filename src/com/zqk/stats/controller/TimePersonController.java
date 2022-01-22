package com.zqk.stats.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqk.stats.pojo.TimePerson;
import com.zqk.stats.pojo.Visit;
import com.zqk.stats.service.TimePersonService;
import com.zqk.stats.utils.DateUtil;


@Controller
public class TimePersonController extends BaseController {
	private static final Log log = LogFactory.getLog(TimePersonController.class);
	
	@Resource private TimePersonService timePersonService;
	
	private DateUtil du = new DateUtil();
	
	private int MAXLEN = 24 ;
	
	
	
	@RequestMapping("/timeperson/timeperson.do")
	public String doTimePerson(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		
		long  shopid = 0 ;
		session = req.getSession() ;
		if( session.getAttribute("SHOPID") != null ){
			shopid  = (Long)session.getAttribute("SHOPID") ;
		}else{
			log.error("session shopid is null");
		}
		
		
		Visit visitpara = new Visit();
		visitpara.setShopid(shopid);
		
		//今天的PV UV
		String curday = du.DateToStr( du.getTimeStamp() ) ; 
		curday = curday.substring(0, 10) ;
		
		Date daystarttime = du.StrToDate(curday + " 00:00:00" );
		Date dayendtime = du.StrToDate(curday + " 23:59:59" );
		
		visitpara.setEntertime(daystarttime);
		visitpara.setOuttime(dayendtime);
		
		TimePerson tppv = (TimePerson)timePersonService.getTodayPV(visitpara);
		TimePerson tpuv = (TimePerson)timePersonService.getTodayUV(visitpara);
		
		if(tppv!=null){
			req.setAttribute("PV", tppv.getPagecount() );
		}
		if(tpuv!=null){
			//System.out.println(tpuv.getPersoncount() );
			req.setAttribute("UV",tpuv.getPersoncount() );
		}
		
		//昨天的PV UV
		String yesterday = du.DateToStr( du.getBeforeDate(new Date() , 1 ) ) ; 
		yesterday = yesterday.substring(0, 10) ;
		
		System.out.println("-----------------yesterday" + yesterday );
		
		Date yesterdaystarttime = du.StrToDate(yesterday + " 00:00:00" );
		Date yesterdayendtime = du.StrToDate(yesterday + " 23:59:59" );
		
		visitpara.setEntertime(yesterdaystarttime);
		visitpara.setOuttime(yesterdayendtime);
		
		TimePerson Yesterdaytppv = (TimePerson)timePersonService.getTodayPV(visitpara);
		TimePerson Yesterdaytpuv = (TimePerson)timePersonService.getTodayUV(visitpara);
		
		if(Yesterdaytppv!=null){
			req.setAttribute("YesPV", Yesterdaytppv.getPagecount() );
		}
		if(Yesterdaytpuv!=null){
			//System.out.println(tpuv.getPersoncount() );
			req.setAttribute("YesUV",Yesterdaytpuv.getPersoncount() );
		}
		
		
		
		return "/jsp/timeperson/TimePerson";
	}
	   
	/***
	 * 输出时段人数的Json结果
	 */
	@RequestMapping("/timeperson/timePersonJson.do")
	public void doTimePersonJson(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
	
		session = req.getSession();
	
		long shopid = 0;
		if(session.getAttribute("SHOPID") != null ){
			shopid = (Long)session.getAttribute("SHOPID");
		}else{
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> session shopid is null ");
		}	
		
		log.info("#############输出时段人数Json字符 at " + System.currentTimeMillis() );
		
		if(true){
			Visit visit = new Visit();
			visit.setShopid(shopid);
			
			String curday = du.DateToStr( du.getTimeStamp() ) ; 
			curday = curday.substring(0, 10) ;
			
			Date daystarttime = du.StrToDate(curday + " 00:00:00" );
			
			Date dayendtime = du.StrToDate(curday + " 23:59:59" );
			
			
			visit.setEntertime(daystarttime);
			visit.setOuttime(dayendtime);
			
			
			List<TimePerson>  timepersonlist =  timePersonService.TimePersonList(visit);

			
			//格式化为0点到24点都有值的列表
			List<TimePerson> format_timepersonlist = new ArrayList<TimePerson>();
			
			Iterator it = timepersonlist.iterator() ;
			
			int time = 0 ; 
			while(it.hasNext()){
				TimePerson tp = (TimePerson)it.next();
				if(time != tp.getTimegroup()){
					for(int i=time ;i<tp.getTimegroup(); i++ ){
						TimePerson ntp = new TimePerson();
						ntp.setPersoncount(0);
						ntp.setTimegroup(i);
						format_timepersonlist.add(ntp) ;
						time ++ ;
					}
				}
				format_timepersonlist.add(tp) ;
				time ++ ;
			}
			
			for(int i=time ;i<24; i++ ){
				TimePerson ntp = new TimePerson();
				ntp.setPersoncount(0);
				ntp.setTimegroup(i);
				format_timepersonlist.add(ntp) ;
			}
			

			
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
	        PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.print(getJsonPersonString(format_timepersonlist));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	public String getJsonPersonString(List<TimePerson> list){
		String data1 = "[";   
		Iterator it = list.iterator();
		int i = 0 ;
		while(it.hasNext()){
			TimePerson tp = (TimePerson)it.next();
			String time =tp.getTimegroup() + "";
			String personcount = tp.getPersoncount() + "";
			
			data1 += "[" +  time  + "," + personcount + "]";   
	            if (i != MAXLEN - 1) data1 += ",";  //最后一个数据不加","
			i++ ;
		}
		data1 += "]";  
		
//		String dataString = "{label:\"Time-Person\"" + ",data:" + data1 + "}";   
		String dataString = "{data:" + data1 + "}";
	    
        //System.out.println("##dataString=" + dataString);
        
        return dataString ;

	}
	
	
	
	@RequestMapping("/timeperson/timepage.do")
	public String doTimePage(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		
		long  shopid = 0 ;
		session = req.getSession() ;
		if( session.getAttribute("SHOPID") != null ){
			shopid  = (Long)session.getAttribute("SHOPID") ;
		}else{
			log.error("session shopid is null");
		}
		
		
		Visit visitpara = new Visit();
		visitpara.setShopid(shopid);
		
		String curday = du.DateToStr( du.getTimeStamp() ) ; 
		curday = curday.substring(0, 10) ;
		
		Date daystarttime = du.StrToDate(curday + " 00:00:00" );
		Date dayendtime = du.StrToDate(curday + " 23:59:59" );
		
		visitpara.setEntertime(daystarttime);
		visitpara.setOuttime(dayendtime);
		
		TimePerson tppv = (TimePerson)timePersonService.getTodayPV(visitpara);
		TimePerson tpuv = (TimePerson)timePersonService.getTodayUV(visitpara);
		
		if(tppv!=null){
			req.setAttribute("PV", tppv.getPagecount() );
		}
		if(tpuv!=null){
			req.setAttribute("UV",tpuv.getPersoncount() );
		}
		
		//昨天的PV UV
		String yesterday = du.DateToStr( du.getBeforeDate(new Date() , 1 ) ) ; 
		yesterday = yesterday.substring(0, 10) ;
		
		System.out.println("-----------------yesterday" + yesterday );
		
		Date yesterdaystarttime = du.StrToDate(yesterday + " 00:00:00" );
		Date yesterdayendtime = du.StrToDate(yesterday + " 23:59:59" );
		
		visitpara.setEntertime(yesterdaystarttime);
		visitpara.setOuttime(yesterdayendtime);
		
		TimePerson Yesterdaytppv = (TimePerson)timePersonService.getTodayPV(visitpara);
		TimePerson Yesterdaytpuv = (TimePerson)timePersonService.getTodayUV(visitpara);
		
		if(Yesterdaytppv!=null){
			req.setAttribute("YesPV", Yesterdaytppv.getPagecount() );
		}
		if(Yesterdaytpuv!=null){
			//System.out.println(tpuv.getPersoncount() );
			req.setAttribute("YesUV",Yesterdaytpuv.getPersoncount() );
		}
		
		return "/jsp/timeperson/TimePage";
	}
	
	
	
	/***
	 * 输出时段页面的Json结果
	 */
	@RequestMapping("/timeperson/timePageJson.do")
	public void doTimePageJson(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
	
		session = req.getSession();
	
		long shopid = 0 ;
		if(session.getAttribute("SHOPID") != null ){
			shopid = (Long)session.getAttribute("SHOPID");
		}else{
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> session shopid is null ");
		}	
		
		log.info("#############输出时段页面的Json字符 at " + System.currentTimeMillis() );
		
		if(true){
			Visit visit = new Visit();
			visit.setShopid(shopid);
			
			String curday = du.DateToStr( du.getTimeStamp() ) ; 
			curday = curday.substring(0, 10) ;
			
			Date daystarttime = du.StrToDate(curday + " 00:00:00" );
			Date dayendtime = du.StrToDate(curday + " 23:59:59" );
			
			visit.setEntertime(daystarttime);
			visit.setOuttime(dayendtime);
			
			
			List<TimePerson>  timepagelist =  timePersonService.TimePageList(visit);
			
			//格式化为0点到24点都有值的列表
			List<TimePerson> format_timepersonlist = new ArrayList<TimePerson>();
			
			Iterator it = timepagelist.iterator() ;
			
			int time = 0 ; 
			while(it.hasNext()){
				TimePerson tp = (TimePerson)it.next();
				if(time != tp.getTimegroup()){
					for(int i=time ;i<tp.getTimegroup(); i++ ){
						TimePerson ntp = new TimePerson();
						ntp.setPagecount(0);
						ntp.setTimegroup(i);
						format_timepersonlist.add(ntp) ;
						time ++ ;
					}
				}
				format_timepersonlist.add(tp) ;
				time ++ ;
			}
			
			for(int i=time ;i<24; i++ ){
				TimePerson ntp = new TimePerson();
				ntp.setPagecount(0);
				ntp.setTimegroup(i);
				format_timepersonlist.add(ntp) ;
			}
			
			
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
	        PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.print(getJsonPageString(format_timepersonlist));
			} catch (IOException e) {
				e.printStackTrace();
			}   

		}
	}
	
	public String getJsonPageString(List<TimePerson> list){
		String data1 = "[";   
		Iterator it = list.iterator();
		int i = 0 ;
		while(it.hasNext()){
			TimePerson tp = (TimePerson)it.next();
			String time =tp.getTimegroup() + "";
			String pagecount = tp.getPagecount() + "";
			data1 += "[" +  time  + "," + pagecount + "]";   
	            if (i != MAXLEN - 1) data1 += ",";  //最后一个数据不加","
			i++ ;
		}
		data1 += "]";  
		//String dataString = "{label:\"Time-Person\"" + ",data:" + data1 + "}";   
		String dataString = "{data:" + data1 + "}";
	    
        //System.out.println("##dataString=" + dataString);
        
        return dataString ;

	}	
	

}
