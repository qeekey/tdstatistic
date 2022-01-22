package com.zqk.stats.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zqk.stats.dao.TimePersonDAO;
import com.zqk.stats.pojo.TimePerson;
import com.zqk.stats.pojo.Visit;

@Repository
public class TimePersonDAOImpl  extends BaseDaoImpl implements TimePersonDAO {

	public List<TimePerson> TimePersonList(Visit visit) {
		return (List<TimePerson>)getSqlMapClientTemplate().queryForList("TimePersonDaoImpl.TimePersonList", visit);
	}

	public TimePerson getTodayPV(Visit visitpara) {
		return (TimePerson)getSqlMapClientTemplate().queryForObject("TimePersonDaoImpl.TodayPV", visitpara);
	}

	public TimePerson getTodayUV(Visit visitpara) {
		return (TimePerson)getSqlMapClientTemplate().queryForObject("TimePersonDaoImpl.TodayUV", visitpara);
	}

	public List<TimePerson> TimePageList(Visit visit) {
		return (List<TimePerson>)getSqlMapClientTemplate().queryForList("TimePersonDaoImpl.TimePageList", visit);
	}

}
