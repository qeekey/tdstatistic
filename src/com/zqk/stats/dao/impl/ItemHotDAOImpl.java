package com.zqk.stats.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zqk.stats.dao.ItemDAO;
import com.zqk.stats.dao.ItemHotDAO;
import com.zqk.stats.pojo.ItemHot;
import com.zqk.stats.pojo.TimePerson;
import com.zqk.stats.pojo.Visit;




@Repository
public class ItemHotDAOImpl  extends BaseDaoImpl  implements ItemHotDAO {

	public List<ItemHot> getItemHotList(Visit visitpara) {
		return (List<ItemHot>)getSqlMapClientTemplate().queryForList("ItemHotImpl.ItemHotList", visitpara);
	}

}
