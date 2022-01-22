/**
 * 
 */
package com.zqk.stats.service.top;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Shop;
import com.taobao.api.request.SellercatsListAddRequest;
import com.taobao.api.request.SellercatsListUpdateRequest;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.response.SellercatsListAddResponse;
import com.taobao.api.response.SellercatsListUpdateResponse;
import com.taobao.api.response.ShopGetResponse;
import com.zqk.stats.pojo.ShopPojo;



/**
 * @author zqk
 *
 */
@Service
public class TopShopBusiness extends BaseBusiness {
	private static final Log log = LogFactory.getLog(TopShopBusiness.class);
	

	/***
	 * 通过店铺名称查询店铺
	 * @param nick
	 * @return
	 */
	public ShopPojo getShop(String nick){
		ShopPojo shopPojo = null ;		 
		try {
			ShopGetRequest req = new ShopGetRequest();
			req.setFields("sid,nick");
			req.setNick(nick);		 
			ShopGetResponse rsp= getClient().execute(req);
			Shop shop = rsp.getShop();
			if(shop != null){
				shopPojo =  new ShopPojo();
				shopPojo.setShopid(shop.getSid());
				shopPojo.setNick(shop.getNick());
				shopPojo.setAutosynflag(1) ;
				shopPojo.setVoiceflag(1) ;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return shopPojo;	
	}
	
	
	
	/***
	 * 插入自定义分类
	 * @param nick
	 * @return
	 * @throws TaobaoApiException 
	 */
	public String getInsertSelfCategoryResult(String nick,String name, String sessionkey,String pictUrl) {
		String result =	"";		
		
		SellercatsListAddRequest req = new SellercatsListAddRequest();		
		req.setParentCid(0L);
		req.setName(name);
		req.setPictUrl(pictUrl);
		req.setSortOrder(1L);
		SellercatsListAddResponse rsp;
		try {
			rsp = getClient().execute(req, sessionkey);
			result = rsp.getBody() ;
		} catch (ApiException e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	/***
	 * 更新自定义分类
	 * @param nick
	 * @return
	 * @throws TaobaoApiException 
	 */
	public String getUpdateSelfCategoryResult(String nick,String name, String sessionkey,String pictUrl){
		String result =	"";		
		SellercatsListUpdateRequest req = new SellercatsListUpdateRequest();
		req.setName(name);
		req.setPictUrl(pictUrl);
		req.setSortOrder(0L);
		SellercatsListUpdateResponse rsp;
		try {
			rsp = getClient().execute(req, sessionkey);
			result = rsp.getBody() ;
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return result;		
	}	



}
