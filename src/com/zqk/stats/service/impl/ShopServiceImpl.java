package com.zqk.stats.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zqk.stats.pojo.ShopPojo;
import com.zqk.stats.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService{

	@Resource private ShopDAO shopDAO;


	public void insertShop(ShopPojo shop) {
		shopDAO.insertShop(shop);
	}

	public ShopPojo getShopById(long shopid) {
		return shopDAO.getShopById(shopid);
	}

	public ShopPojo getShopByNick(String nick) {
		return shopDAO.getShopByNick(nick);
	}

	public void updateShop(ShopPojo shoppara) {
		shopDAO.updateShop(shoppara);
	}
	
}
