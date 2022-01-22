package com.zqk.stats.service;

import java.util.List;

import com.zqk.stats.pojo.NotifyPojo;


public interface NotifyService {

	public void insertNotify(NotifyPojo  notifypara);
	
	public List<NotifyPojo> getNotifyList(NotifyPojo notifyPara);	
	public int getNotifyCount(NotifyPojo notifyPara);

	public int getValidateCount(NotifyPojo notifyPara);		//得到有效客户
}
