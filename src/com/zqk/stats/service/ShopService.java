package com.zqk.stats.service;

import com.zqk.stats.pojo.ShopPojo;


public interface ShopService {

	public void insertShop(ShopPojo shop);
	
	public ShopPojo getShopById(long shopid);
	public ShopPojo getShopByNick(String nick);
	
	public void updateShop(ShopPojo shoppara);
}
