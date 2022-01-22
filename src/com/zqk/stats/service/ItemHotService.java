package com.zqk.stats.service;

import java.util.List;

import com.zqk.stats.pojo.ItemHot;
import com.zqk.stats.pojo.ItemPojo;
import com.zqk.stats.pojo.Visit;


public interface ItemHotService {

	public List<ItemHot> TimePersonList(Visit visitpara);
	
}
