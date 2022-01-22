package com.zqk.stats.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zqk.stats.dao.NotifyDAO;
import com.zqk.stats.pojo.NotifyPojo;
import com.zqk.stats.service.NotifyService;

@Service
public class NotifyServiceImpl implements NotifyService {

	@Resource private NotifyDAO notifyDAO;
	
	public void insertNotify(NotifyPojo notifypara) {
		notifyDAO.insertNotyfy(notifypara) ; 
	}

	public int getNotifyCount(NotifyPojo notifyPara) {
		return notifyDAO.getNotifyCount(notifyPara);
	}

	public List<NotifyPojo> getNotifyList(NotifyPojo notifyPara) {
		return notifyDAO.getNotifyList(notifyPara);
	}

	public int getValidateCount(NotifyPojo notifyPara) {
		return notifyDAO.getValidateCount(notifyPara);
	}

}
