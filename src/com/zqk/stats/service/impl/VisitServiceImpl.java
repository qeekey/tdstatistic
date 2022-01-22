package com.zqk.stats.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.dao.VisitDAO;
import com.zqk.stats.pojo.Visit;
import com.zqk.stats.service.VisitService;

@Service
public class VisitServiceImpl implements VisitService{


	@Autowired private VisitDAO visitDAO;

	public void insertVisit(Visit visit) {
		visitDAO.insertVisit(visit);
		
	}

	public List<Visit> nowVisitList(Visit visitpara) {
		List<Visit> listHtml = new ArrayList<Visit>();
		
		List<Visit> list = visitDAO.nowVisitList(visitpara);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Visit visit = (Visit)it.next() ;
			visit.setPic_url("<img src=" + visit.getPic_url()+ TaoBaoComm.ITEMLOGO + ">"); //TaoBaoComm.ITEMLOGO 
			String titlehref= "<a href=\"http://item.taobao.com/item.htm?id=" + visit.getNum_iid() +  "&f=tdstats\" target=\"_blank\" >" + visit.getTitle() + "</a>" ;
			visit.setTitle(titlehref) ;
			visit.setCountry(visit.getCountry() + "<br/>IP:" + visit.getIp() ) ;
			listHtml.add(visit);
		}
		return listHtml;  
	}

	public List<Visit> getBlankTitleList() {
		return visitDAO.getBlankTitleList();
	}

	public void updateVisit(Visit visitpara) {
		visitDAO.updateVisit(visitpara);
	}

	public List<Visit> getHistoryVisitList(Visit visitpara) {
		List<Visit> listHtml = new ArrayList<Visit>();
		List<Visit> list = visitDAO.getHistoryVisitList(visitpara);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Visit visit = (Visit)it.next() ;
			visit.setPic_url("<img src=" + visit.getPic_url()+ TaoBaoComm.ITEMLOGO + ">"); //TaoBaoComm.ITEMLOGO 
			String titlehref= "<a href=\"http://item.taobao.com/item.htm?id=" + visit.getNum_iid() +  "&f=tdstats\" target=\"_blank\" >" + visit.getTitle() + "</a>" ;
			visit.setTitle(titlehref) ;
			visit.setCountry(visit.getCountry() + "<br/>IP:" + visit.getIp() ) ;
			listHtml.add(visit);
		}
		return listHtml;  
	}
	public int getHistoryVisitListCount(Visit visitpara) {
		return visitDAO.getHistoryVisitListCount(visitpara) ;
	}
	
	
	public List<Visit> getVisitCountryList(Visit visitpara) {
		return visitDAO.getVisitCountryList(visitpara);
	}

	public void deleteExpiredVisitData(Visit visitpara) {
		visitDAO.deleteExpiredVisitData(visitpara);
	}


	
}
