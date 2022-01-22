package com.zqk.stats.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqk.stats.dao.TimePersonDAO;
import com.zqk.stats.pojo.TimePerson;
import com.zqk.stats.pojo.Visit;
import com.zqk.stats.service.TimePersonService;


@Service
public class TimePersonServiceImpl implements TimePersonService {

	@Autowired private TimePersonDAO timePersonDAO;
	
	public List<TimePerson> TimePersonList(Visit visit) {
		return timePersonDAO.TimePersonList(visit);
	}

	public TimePerson getTodayPV(Visit visitpara) {
		return  timePersonDAO.getTodayPV(visitpara);
	}

	public TimePerson getTodayUV(Visit visitpara) {
		return  timePersonDAO.getTodayUV(visitpara);
	}

	public List<TimePerson> TimePageList(Visit visit) {
		return timePersonDAO.TimePageList(visit);
	}

}
