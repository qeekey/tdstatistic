package com.zqk.stats.dao;

import java.util.List;

import com.zqk.stats.pojo.ItemPojo;


public interface ItemDAO {
	
	public ItemPojo getItem(long num_iid);
	
	public int  getItemCount(long shopid);
	
	public ItemPojo getItemByShopId(long shopid);	//用于查找是否存在对应店铺的宝贝数据,如果没有存在则调用初始化数据功能
	
	public void insertItem(ItemPojo item);
	
	public void insertBatchItemData(final List varList);
	public void updateBatchItemData(final List varList);
	public void deleteBatchItemData(final List varList); 
	
	public List<ItemPojo> getNumIidList(long shopid);
	
	public List<ItemPojo> getItemList(long shopid);
	
	
	public void deleteItem(long num_iid);
}
