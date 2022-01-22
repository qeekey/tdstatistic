package com.zqk.stats.dao;

import java.util.List;

import com.zqk.stats.pojo.TimePerson;
import com.zqk.stats.pojo.Visit;


public interface TimePersonDAO {
	//时间段访问人数
	public List<TimePerson> TimePersonList(Visit visit);
	
	
	public TimePerson getTodayPV(Visit visitpara);
	
	public TimePerson getTodayUV(Visit visitpara);
	
	//时间段而面浏览
	public List<TimePerson> TimePageList(Visit visit);
	
}
