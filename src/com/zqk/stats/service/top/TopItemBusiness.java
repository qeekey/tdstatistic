/**
 * 
 */
package com.zqk.stats.service.top;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.zqk.stats.pojo.ItemPojo;
import com.zqk.stats.pojo.ItemRspResult;
import com.zqk.stats.pojo.ShopPojo;

/**
 * @author zqk
 *
 */

@Service
public class TopItemBusiness extends BaseBusiness {	
	private static final Log log = LogFactory.getLog(TopItemBusiness.class);
	
	private int total_result = 0;	//返回满足条件的总记录数
	
	/***
	 * 根据商品状态　得到用户的商品列表　，status ==1 代表出售中，status ==2 代表仓库中
	 * @param status
	 * @param nick
	 * @param sessionkey
	 * @param title
	 * @param SellerCid
	 * @param pagesize
	 * @param curpage
	 * @return
	 */
	public ItemRspResult getUserItemResult(int status, String nick, ShopPojo shop , String sessionkey, String title, String SellerCid, long pagesize,long curpage){
		ItemRspResult irr = null ;
		
		List<ItemPojo> itemlist = new ArrayList<ItemPojo>();
		
		long shopid = shop.getShopid() ; 
		
		switch(status){		
		case 1:			//出售中商品
			try {
				ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();				
				req.setFields("num_iid,title,approve_status,pic_url");
				
				if(title.trim().length() > 0 ){
					req.setQ( title.trim() );
				}
				if(SellerCid.trim().length() >0  && !SellerCid.trim().equalsIgnoreCase("-1") ){
					req.setSellerCids(SellerCid.trim() ) ;
				}				
				req.setPageNo(curpage);
				req.setPageSize(pagesize);				
				ItemsOnsaleGetResponse rsp = getClient().execute(req,sessionkey);
								
			
				if(rsp.getErrorCode() == null ){
					irr = new ItemRspResult(); 
			
					List<Item> topitemlist = rsp.getItems();
					Iterator it = topitemlist.iterator();
					while(it.hasNext()){
						Item item = (Item)it.next();
						ItemPojo itempojo = new ItemPojo();
						itempojo.setNum_iid(item.getNumIid());
						itempojo.setShopid(shopid);
						itempojo.setTitle(item.getTitle() );
						itempojo.setPic_url(item.getPicUrl());
						
						itemlist.add(itempojo) ; 
					}
					
					irr.setTotalResults(rsp.getTotalResults() );
					irr.setItemlist(itemlist);
				}else{
					log.error("ERROR CODE ...........");
				}
				

			}catch (Exception e) {
				e.printStackTrace();
				log.error("#####getUserItemResult ItemsOnsaleGetResponse Error");
			}	
			break;
		case 2:			//仓库商品
			try {
				ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();				
				req.setFields("num_iid,title,approve_status,pic_url");	
				
				if(title.trim().length() > 0 ){
					req.setQ( title.trim() );
				}
				if(SellerCid.trim().length() >0  && !SellerCid.trim().equalsIgnoreCase("-1") ){
					req.setSellerCids(SellerCid.trim() ) ;
				}
				req.setPageNo(curpage);
				req.setPageSize(pagesize);
				ItemsInventoryGetResponse rsp = getClient().execute(req, sessionkey);
				
				System.out.println(rsp.getBody() );
				System.out.println(rsp.getTotalResults());
				
				if(rsp.getErrorCode() == null ){
					irr = new ItemRspResult(); 
	
					List<Item> topitemlist = rsp.getItems();
					Iterator it = topitemlist.iterator();
					while(it.hasNext()){
						Item item = (Item)it.next();
						ItemPojo itempojo = new ItemPojo();
						itempojo.setNum_iid(item.getNumIid());
						itempojo.setShopid(shopid);
						itempojo.setTitle(item.getTitle() );
						itempojo.setPic_url(item.getPicUrl());
						
						itemlist.add(itempojo) ; 
					}					
					irr.setTotalResults(rsp.getTotalResults() );
					irr.setItemlist(itemlist);
				}else{
					System.out.println(rsp.getErrorCode());
				}

			}catch (Exception e) {
				e.printStackTrace();
				log.error("#####getUserItemResult ItemsInventoryGetResponse Error");
			}	
			break;
		}
		
		return irr ;
	}
	
	/***
	 * 根据商品状态　得到用户的商品NumIid列表　，status ==1 代表仓库中，status ==2 代表出售中
	 * @param status
	 * @param nick
	 * @param sessionkey
	 * @param pagesize
	 * @param curpage
	 * @return
	 */
	public ItemRspResult getUserItemNumIidListXXX(int status, String nick, ShopPojo shop , String sessionkey, long pagesize,long curpage){
		String UsertemResult ="" ;
		ItemRspResult irr = null ;
		
		List<ItemPojo> itemlist = new ArrayList<ItemPojo>();
		
		String s ; 
		
		long shopid = shop.getShopid() ; 
		
		switch(status){		
		case 1:			//出售中商品
			try {
				ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();				
				req.setFields("num_iid");
				
				req.setPageNo(curpage);
				req.setPageSize(pagesize);				
				ItemsOnsaleGetResponse rsp = getClient().execute(req,sessionkey);
					
				if(rsp.getErrorCode() == null ){
					irr = new ItemRspResult(); 
					
					List<Item> topitemlist = rsp.getItems();
					Iterator it = topitemlist.iterator();
					while(it.hasNext()){
						Item item = (Item)it.next();
						ItemPojo itempojo = new ItemPojo();
						itempojo.setNum_iid(item.getNumIid());
						itemlist.add(itempojo) ;	
					}	
					irr.setTotalResults(rsp.getTotalResults() );
					irr.setItemlist(itemlist);
				}				
			}catch (Exception e) {
				e.printStackTrace();
				log.error("#####getUserItemResult ItemsOnsaleGetResponse Error");
			}	
			break;
		case 2:			//仓库商品
			try {
				ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();				
				req.setFields("num_iid");		
				req.setPageNo(curpage);
				req.setPageSize(pagesize);
				ItemsInventoryGetResponse rsp = getClient().execute(req, sessionkey);
				
				if(rsp.getErrorCode() == null ){
					irr = new ItemRspResult();
					
					List<Item> topitemlist = rsp.getItems();
					Iterator it = topitemlist.iterator();
					while(it.hasNext()){
						Item item = (Item)it.next();
						ItemPojo itempojo = new ItemPojo();
						itempojo.setNum_iid(item.getNumIid());
						itemlist.add(itempojo) ; 
					}
					irr.setTotalResults(rsp.getTotalResults() );
					irr.setItemlist(itemlist);
				}
			}catch (Exception e) {
				e.printStackTrace();
				log.error("#####getUserItemResult ItemsInventoryGetResponse Error");
			}	
			break;
		}
		return irr ;
	}
	
	
	
	
	
}
