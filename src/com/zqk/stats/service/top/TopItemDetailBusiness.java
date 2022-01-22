package com.zqk.stats.service.top;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.zqk.stats.pojo.ItemPojo;
import com.zqk.stats.pojo.ShopPojo;



/**
 * @author zqk
 * 商品的描述信息
 */

@Service
public class TopItemDetailBusiness extends BaseBusiness {	
	private static final Log log = LogFactory.getLog(TopItemDetailBusiness.class);
	
	/***
	 * 商品API
	 * taobao.item.get (获取单个商品的标题等列表信息，用于新增商品的更新到DB操作)
	 * @param nick
	 * @return
	 */
	public ItemPojo getDetailItem(String nick, ShopPojo shop , String sessionkey,Long num_iid ){
		try {            
			ItemGetRequest req = new ItemGetRequest();
			req.setNumIid(num_iid);
			req.setNick(nick);
			req.setFields("num_iid, title ,approve_status, pic_url");
			ItemGetResponse rsp = getClient().execute(req, sessionkey);
			if(rsp.getErrorCode() == null ){
				Item item = rsp.getItem();			//淘宝返回Item模型
					
				if(item != null){
					System.out.println("top item is not null");
					ItemPojo itempojo = new ItemPojo();
					itempojo.setNum_iid(num_iid);
					itempojo.setShopid(shop.getShopid()) ;
					itempojo.setTitle(item.getTitle() ) ;
					itempojo.setPic_url( item.getPicUrl() ) ;
					
					return itempojo ; 
				}
			}else{
				System.out.println("rsp.getErrorCode() = " + rsp.getErrorCode() );
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("#####getItemTitleResult API ERROR");
		}
		return null;
	}
	
	
}
