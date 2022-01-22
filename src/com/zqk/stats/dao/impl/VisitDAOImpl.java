package com.zqk.stats.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.zqk.stats.dao.VisitDAO;
import com.zqk.stats.pojo.Visit;




@Repository
public class  VisitDAOImpl  extends BaseDaoImpl implements VisitDAO{

	public void insertVisit(Visit visit) {
		getSqlMapClientTemplate().insert("VisitDaoImpl.insert", visit);
	}

	public List<Visit> nowVisitList(Visit  visitpara) {
		return (List<Visit>)getSqlMapClientTemplate().queryForList("VisitDaoImpl.nowVisitList", visitpara);
	}

	public List<Visit> getBlankTitleList() {
		return (List<Visit>)getSqlMapClientTemplate().queryForList("VisitDaoImpl.getBlankTitleList", null);
	}

	public void updateVisit(Visit visitpara) {
		getSqlMapClientTemplate().insert("VisitDaoImpl.update", visitpara);
	}
	
	public List<Visit> getHistoryVisitList(Visit visitpara) {
		return (List<Visit>)getSqlMapClientTemplate().queryForList("VisitDaoImpl.getHistoryVisitList", visitpara);
	}
	public int getHistoryVisitListCount(Visit visitpara) {
		return (Integer) getSqlMapClientTemplate().queryForObject("VisitDaoImpl.getHistoryVisitListCount", visitpara);
	}
	public List<Visit> getVisitCountryList(Visit visitpara) {
		return (List<Visit>)getSqlMapClientTemplate().queryForList("VisitDaoImpl.getVisitCountryList", visitpara);
	}

	public void deleteExpiredVisitData(Visit visitpara) {
		getSqlMapClientTemplate().delete("VisitDaoImpl.deleteExpiredData", visitpara);		
	}




}
