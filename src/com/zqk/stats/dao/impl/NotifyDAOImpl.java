package com.zqk.stats.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zqk.stats.dao.NotifyDAO;
import com.zqk.stats.pojo.ItemPojo;
import com.zqk.stats.pojo.NotifyPojo;
import com.zqk.stats.pojo.Visit;


@Repository
public class NotifyDAOImpl  extends BaseDaoImpl  implements NotifyDAO {

	public void insertNotyfy(NotifyPojo notify) {
		getSqlMapClientTemplate().insert("NotifyImpl.insert", notify);
	}

	public List<NotifyPojo> getNotifyList(NotifyPojo notifyPara) {
		return (List<NotifyPojo>)getSqlMapClientTemplate().queryForList("NotifyImpl.getNotifyList", notifyPara);
	}

	public int getNotifyCount(NotifyPojo notifyPara) {
		return (Integer) getSqlMapClientTemplate().queryForObject("NotifyImpl.getNotifyCount", notifyPara);
	}

	public int getValidateCount(NotifyPojo notifyPara) {
		return (Integer) getSqlMapClientTemplate().queryForObject("NotifyImpl.getValidateCount", notifyPara);
	}

}
