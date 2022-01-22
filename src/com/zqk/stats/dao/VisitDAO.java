package com.zqk.stats.dao;

import java.util.List;

import com.zqk.stats.pojo.Visit;


public interface VisitDAO {
	
	public void insertVisit(Visit visit);
	
	public List<Visit> nowVisitList(Visit visitpara);	
	
	public List<Visit> getBlankTitleList();
	
	public void updateVisit(Visit visitpara);
	
	
	public List<Visit> getHistoryVisitList(Visit visitpara);		//得到历史访问记录
	public int getHistoryVisitListCount(Visit visitpara);			//得到历史访问记录总数
	
	public List<Visit> getVisitCountryList(Visit visitpara);		//得到历史访问城市列表
	
	
	public void deleteExpiredVisitData(Visit visitpara); 			//删除过期的访问数据

}
