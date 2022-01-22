package com.zqk.stats.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zqk.stats.dao.ItemHotDAO;
import com.zqk.stats.pojo.ItemHot;
import com.zqk.stats.pojo.Visit;
import com.zqk.stats.service.ItemHotService;


@Service
public class ItemHotServiceImpl implements ItemHotService {

	

	@Resource private ItemHotDAO itemHotDAO;
	
	
	public List<ItemHot> TimePersonList(Visit visitpara) {
		return itemHotDAO.getItemHotList(visitpara);
	}

}
