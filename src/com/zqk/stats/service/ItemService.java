package com.zqk.stats.service;

import java.util.List;

import com.zqk.stats.pojo.ItemPojo;


public interface ItemService {

	public ItemPojo getItem(long num_iid);
	
	public int  getItemCount(long shopid);
	public ItemPojo getItemByShopId(long shopid);
	
	public void insertItem(ItemPojo item);
	
	public List<ItemPojo> getNumIidList(long shopid);
	public List<ItemPojo> getItemList(long shopid);
	
	public void deleteItem(long num_iid);
	
	public void insertBatchItemData(final List varList);
	public void updateBatchItemData(final List varList); 
	public void deleteBatchItemData(final List varList); 
	
}
