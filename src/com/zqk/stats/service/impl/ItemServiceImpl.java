package com.zqk.stats.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zqk.stats.dao.ItemDAO;
import com.zqk.stats.pojo.ItemPojo;
import com.zqk.stats.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Resource private ItemDAO itemDAO;
	
	public ItemPojo getItem(long num_iid) {
		return itemDAO.getItem(num_iid);
	}

	public int getItemCount(long shopid) {
		return itemDAO.getItemCount(shopid);
	}

	public void insertItem(ItemPojo item) {
		itemDAO.insertItem(item);
	}

	public List<ItemPojo> getNumIidList(long shopid) {
		return itemDAO.getNumIidList(shopid);
	}

	public void deleteItem(long num_iid) {
		itemDAO.deleteItem(num_iid);
	}

	public void insertBatchItemData(List varList) {
		itemDAO.insertBatchItemData(varList);
	}

	public ItemPojo getItemByShopId(long shopid) {
		return itemDAO.getItemByShopId(shopid) ; 
	}

	public List<ItemPojo> getItemList(long shopid) {
		return itemDAO.getItemList(shopid);
	}

	public void updateBatchItemData(List varList) {
		itemDAO.updateBatchItemData(varList);
	}

	public void deleteBatchItemData(List varList) {
		itemDAO.deleteBatchItemData(varList);
	}


	
}
