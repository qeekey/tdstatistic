package com.zqk.stats.dao;

import java.util.List;

import com.zqk.stats.pojo.ItemHot;
import com.zqk.stats.pojo.Visit;


public interface ItemHotDAO {
	
	public List<ItemHot> getItemHotList(Visit visitpara);
	

	
}
