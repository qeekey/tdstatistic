package com.zqk.stats.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	
	public static String FORMAT_DAY="yyyy-MM-dd HH:mm:ss";
	
	public static int beforedays = 60 ;	//初始为60天．
	
	/***
	 * 返回当前的时间，　格式为 yyyy-MM-dd HH-mm-ss
	 * @return
	 */
	public static Date getTimeStamp() {
		Date nowTime = new Date();
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_DAY);		
		Date fdate=null ; 
		try {
			fdate=df.parse(df.format(nowTime));
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return fdate;
	}
	

	/***
	 *  返回当前的时间，　格式为 yyyy年MM月dd日 HH：mm
	 * @return
	 */
	public static String getNowTime(Date date) {
		StringBuffer stringbuffer = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_DAY);
		String timestring = df.format(date);
		stringbuffer.append(timestring.substring(0, 4) + "年"
				+ timestring.substring(5, 7).toString() + "月"
				+ timestring.substring(8, 10).toString() + "日"
				+timestring.substring(11,13)+":"+timestring.substring(14,16));
		timestring = stringbuffer.toString();
		return timestring;
	}

	/**
	 * 返回特定日期前n天的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getBeforeDate(Date date,int days)   
	{   
	    SimpleDateFormat df = new SimpleDateFormat(FORMAT_DAY);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);   
	    return calendar.getTime();   
	}	
	
	/**
	 * 返回特定日期后n天的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getAfterDate(Date date,int days)   
	{   
	    SimpleDateFormat df = new SimpleDateFormat(FORMAT_DAY);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + days);   
	    return calendar.getTime();   
	}		
	
	/**
	 * 返回指定分钟前n分钟的时间
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getBeforeMinuteTime(Date date,int minute)   
	{   
	    SimpleDateFormat df = new SimpleDateFormat(FORMAT_DAY);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) - minute);   
	    return calendar.getTime();   
	}	
	
	/**
	 * 返回指定分钟后n分钟的时间
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getAfterMinuteTime(Date date,int minute)   
	{   
	    SimpleDateFormat df = new SimpleDateFormat(FORMAT_DAY);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE) + minute);   
	    return calendar.getTime();   
	}	

	/***
	 * 将2010-1-2 这样的时间格式化为 2010-01-02形式
	 * @param datestr
	 * @return
	 */
	public static String FormatDateStr(String datestr){
		StringBuffer retbuf = new StringBuffer();
		String d[] = datestr.split("-");
		//2010-04-09 09:37:34
		retbuf.append(d[0]);		
		retbuf.append("-");		
		if(d[1].length()<2){
			retbuf.append("0").append(d[1]);
		}else{
			retbuf.append(d[1]);
		}
		retbuf.append("-");
		
		if(d[2].length()<2){
			retbuf.append("0").append(d[2]);
		}else{
			retbuf.append(d[2]);
		}
		return retbuf.toString();
	}
	/**
	* 日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String DateToStr(Date date) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(FORMAT_DAY);
	   String str = format.format(date);
	   return str;
	} 

	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(FORMAT_DAY);
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
 
	
}
