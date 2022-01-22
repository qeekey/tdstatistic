package com.zqk.stats.dao;

import java.util.List;

import com.zqk.stats.pojo.NotifyPojo;
import com.zqk.stats.pojo.Visit;


public interface NotifyDAO {

	public void insertNotyfy(NotifyPojo notify);
	
	public List<NotifyPojo> getNotifyList(NotifyPojo notifyPara);	
	public int getNotifyCount(NotifyPojo notifyPara);
	
	public int getValidateCount(NotifyPojo notifyPara);		//得到有效客户
	
	
}
