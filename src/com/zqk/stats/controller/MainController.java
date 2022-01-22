package com.zqk.stats.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Encoder;

import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.pojo.ItemComparator;
import com.zqk.stats.pojo.ItemPojo;
import com.zqk.stats.pojo.ItemRspResult;
import com.zqk.stats.pojo.ShopPojo;
import com.zqk.stats.pojo.Visit;
import com.zqk.stats.service.ItemService;
import com.zqk.stats.service.ShopService;
import com.zqk.stats.service.VisitService;
import com.zqk.stats.service.top.TopItemBusiness;
import com.zqk.stats.service.top.TopItemDetailBusiness;
import com.zqk.stats.service.top.TopShopBusiness;
import com.zqk.stats.utils.DateUtil;
import com.zqk.stats.utils.ListUtil;
import com.zqk.stats.utils.StringUtil;
import com.zqk.stats.utils.top.ParametersUtil;
import com.zqk.stats.utils.top.SessionUtil;


@Controller
public class MainController extends BaseController {
	
	private static final Log log = LogFactory.getLog(MainController.class);
	
	@Resource private ItemService 	itemService;
	@Resource  private ShopService 	shopService; 
	@Resource  private VisitService visitService;
	
	
	@Resource private SessionUtil sessionUtil ;
	@Resource private ParametersUtil parametersUtil ;
	
	@Resource private TopItemBusiness topItemBusiness;	
	@Resource private TopShopBusiness topShopBusiness;
	@Resource private TopItemDetailBusiness topItemDetailBusiness;
	
	
	private ListUtil listUtil = new ListUtil();
	private DateUtil du = new DateUtil();	
	private StringUtil su = new StringUtil();
	
	@RequestMapping("/main/main.do")
	public String doMain(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//取得数据更新Item
		session = request.getSession();
	
		String querystr = "" ; 
		
		if( session.getAttribute("querystr") != null ){
			querystr = (String)session.getAttribute("querystr") ;	
		}
		
		
		log.info("###### querystr=" + querystr);
		boolean top_sign = false;
		
		ShopPojo shop = null ; 
		
		if(querystr != null ){
			sessionUtil.setSessionKey( sessionUtil.getSessionKey(querystr) );
			log.info("######SessionUtil.SessionKey=" + sessionUtil.getSessionKey() );
			
			System.out.println("querystr=" + querystr );
			
			String topParams = sessionUtil.getTop_parameters(querystr);
		
			System.out.println("topParams =" + topParams );
			
		
			topParams = java.net.URLDecoder.decode(topParams);
			
			String topSign = sessionUtil.getTop_sign(querystr);
			topSign = java.net.URLDecoder.decode(topSign);
		
			try {
				top_sign = verifyTopResponse(topParams, sessionUtil.getSessionKey() , topSign, TaoBaoComm.APP_KEY, TaoBaoComm.APP_SERCET );
				log.info("==========top_sign=" + top_sign );				
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
			if(querystr.length() >0 ){
				if(top_sign){	//通过top_sign验证				
					session.setAttribute("SESSIONKEY" , sessionUtil.getSessionKey() ) ;
					
					String[] params = querystr.split("&");
					String top_parameters = null;
					for (String string : params) {
						if(string.startsWith("top_parameters")){
							top_parameters = string.split("=")[1];
							break;
						}
					}			
					log.info("top_parameters =" + top_parameters);			
					//在这里，必须进行decode 才能正确的取到Nick名称
					top_parameters =  java.net.URLDecoder.decode(top_parameters);
					log.info("URLdecode top_parameters =" + top_parameters);			
					
					String nick = parametersUtil.getCurrentNick(top_parameters);
					//从查询字符串中得到nick
					
					log.info("##从查询字符串中得到nick=" + nick );
					
					
					//如果nick 从 top_parameters里面的得到的，应该是gbk格式的，调用API的时候，要把gbk转化成utf8的
					try {
						nick = new String(nick.getBytes(),"gbk");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}			
					log.info("##从查询字符串中得到nick转成gbk 的nick =" + nick );			
					
					parametersUtil.setNick(nick);
					log.info("######parametersUtil.nick=" + nick );
					session.setAttribute("NICK",nick) ;
					
					
					//查询此店铺是否已经存在
					shop = shopService.getShopByNick(nick);
					
					if(shop == null){	//证明DB中无此店铺，需要入DB
						shop = topShopBusiness.getShop(nick);
						if(shop != null){
							shopService.insertShop(shop);
							log.info("shop 不存在，insert to DB");
							
							//调用插入定义分类开发接口 start							
							String encodesid = su.encodeShopId(shop.getShopid());
							String pictUrl = TaoBaoComm.RootURL + "/" + TaoBaoComm.statscateUrl + encodesid ;
							String name = 	 TaoBaoComm.tuduncateName ;	
							String cateresult ="" ;
							cateresult = topShopBusiness.getInsertSelfCategoryResult(nick, name, (String)session.getAttribute("SESSIONKEY"), pictUrl);
							log.info("cateresult=" + cateresult) ;
							//调用插入定义分类开发接口 end
						}						
					}else{
						log.info("shop 存在，不入DB");
					}
				}else{
					System.out.println("top_sign验证 没有通过啊");
				}
			}
		}else{
			log.info("sessionUtil is null");
		}
		
		if(shop != null ){
			session.setAttribute("SHOPID",shop.getShopid() ) ;
			session.setAttribute("SHOP",shop ) ;			
			int version = 1 ; 
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"  + shop.getShopid()  );
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"  + (Long)session.getAttribute("SHOPID") );
			
			//更新商品名称为默认时的商品名称start
			List<Visit> blankList = visitService.getBlankTitleList();					
			Iterator it = blankList.iterator() ;
			while(it.hasNext()){
				Visit visit = (Visit)it.next();
				//从商品表中找到商品信息，根据此信息，更新visit表的title , 和pic_url
				ItemPojo item = itemService.getItem( visit.getNum_iid() );
				if(item != null){
					if(item.getTitle() != null){
						visit.setTitle(item.getTitle());	
					}
					if(item.getPic_url() != null ){
						visit.setPic_url( item.getPic_url() );
					}	
					if(item.getShopid() >0 ){
						visit.setShopid(item.getShopid());
					}
					log.info("更新访问标题 start.");
					visitService.updateVisit(visit);
					log.info("更新访问标题 end.");
				}
			}
			//更新商品名称为默认时的商品名称 end 
			
			return "/jsp/main/Main";	
		}else{
			return "/ShopError";
		}
		
	}
	
	/***
	 * Json后台同步店铺数据
	 */
	@RequestMapping("/synItemDataJson.do")	
	public void doSynItemDataJson(){
		int version = 1 ; 
		ShopPojo shoppojo = (ShopPojo) session.getAttribute("SHOP");
		if( shoppojo.getAutosynflag()  == 1 ){
			log.info("自动进行同步功能");
			boolean exist = ExistItemByShopId();
			if( !exist ){
				log.error("没有存在数据，initShopItem==============");
				initShopItem(version);
			}else{
				log.error("存在数据，synShopItem==============");
				//进行数据同步， 增  删  改
				synShopItem(version);
			}
		}else{
			log.info("不用自动进行同步功能");
		}		
	}
	
	
	/***
	 * 手工同步店铺数据
	 */
	@RequestMapping("/main/synItemDataHand.do")
	public String doSynItemDataHand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int version = 1 ; 
//		进行数据同步， 增  删  改
		synShopItem(version);		
		return "/synItemDataHand";
	}
	
	/***
	 * DB是否已经存在宝贝数据
	 */
	public boolean ExistItemByShopId(){		
		ItemPojo itempojo = itemService.getItemByShopId( (Long)session.getAttribute("SHOPID") );
		if(itempojo == null ){
			return false ;
		}else{
			return true;
		}	
	}
	
	/***
	 * 得到DB中店铺宝贝数据
	 */
	public int getShopItemCountXXX(){		
		int count = itemService.getItemCount( (Long)session.getAttribute("SHOPID") );
		if(count == 0 ){
			return 0 ;
		}else{
			return count;
		}	
	}
	
	
	/***
	 * 初始化店铺宝贝数据，一条条的插入DB
	 */
	public void initShopItem(int version){
		List<ItemPojo> alllist_top = new ArrayList<ItemPojo>();
		
		String nick = (String)session.getAttribute("NICK");
		String sessionkey = (String)session.getAttribute("SESSIONKEY");
		ShopPojo shoppojo = (ShopPojo) session.getAttribute("SHOP");
		String title ="" ;
		String SellerCid= "" ;
		int curpage =1 ; 
		
		long total_results = 1 ;
		
		
		int status =1 ;	//所有出售中商品列表
				
		while (((curpage - 1) * TaoBaoComm.TopMaxPageSize) <  total_results ) {
			log.info("##调用所有出售中商品列表 curpage =" + curpage);
			ItemRspResult irr = topItemBusiness.getUserItemResult(status, nick, shoppojo, sessionkey, title ,SellerCid, TaoBaoComm.TopMaxPageSize , curpage);
			if(irr != null){
				
				total_results = irr.getTotalResults()  ;
				log.info("onsale  total_results" + total_results);
				
				if(total_results > 0 ){
					alllist_top.addAll(irr.getItemlist());	
				}
			}else{
				log.info("irr == null");
			}
			curpage ++ ;
			log.info("##调用===================================" + curpage);
			ThreadSleep(1000);
		}
		
		total_results = 1 ;
		
		curpage = 1 ;
		status = 2 ;	//所有库存中商品列表		
		while (((curpage - 1) * TaoBaoComm.TopMaxPageSize) <  total_results ) {
			log.info("##调用所有库存中商品列表 curpage =" + curpage);
			ItemRspResult irr = topItemBusiness.getUserItemResult(status, nick, shoppojo, sessionkey, title ,SellerCid, TaoBaoComm.TopMaxPageSize , curpage);
			if(irr != null){
				total_results = irr.getTotalResults()  ;
				log.info("stock  total_results" + total_results);
				if(total_results > 0 ){
					alllist_top.addAll(irr.getItemlist());	
				}	
			}
			curpage ++ ;
			log.info("##调用===================================" + curpage);
			ThreadSleep(1000);
		}	
		
		//采用批量方案 200条数据用了4秒
		long start = System.currentTimeMillis() ; 
		itemService.insertBatchItemData(alllist_top) ; 
		long end = System.currentTimeMillis() ;
		long wastetime = end - start;
		System.out.println("插入数据用时 " + wastetime);
		
		//采用一条一条插入方案用7.8秒
//		long start = System.currentTimeMillis() ; 
//		Iterator it = alllist_top.iterator();
//		while(it.hasNext()){
//			ItemPojo itempojo = (ItemPojo)it.next();
//			if(itempojo != null ){
//				itemService.insertItem(itempojo);
//			}
//		}	
//		long end = System.currentTimeMillis() ;
//		long wastetime = end - start;
//		System.out.println("wastetime=" + wastetime);
		
	
	}
	
	/***
	 * 同步数据
	 * @param version
	 */
	public void synShopItem(int version){
		List<ItemPojo> alllist_top = new ArrayList<ItemPojo>();
		long total_results = 1 ;
		
		String nick = (String)session.getAttribute("NICK");
		String sessionkey = (String)session.getAttribute("SESSIONKEY");
		ShopPojo shoppojo = (ShopPojo) session.getAttribute("SHOP");
		
		int thisVersionMax = 1 ; 
		
		switch (version)
		{
			case 1:
				thisVersionMax = TaoBaoComm.V1_MaxItemCount ;
				break;
			case 2:
				thisVersionMax = TaoBaoComm.V2_MaxItemCount ;
				break;
			case 3:
				thisVersionMax = TaoBaoComm.V3_MaxItemCount ;
				break;			
		}
		
		int curpage =1 ;
				
		int status =1 ;	//所有出售中商品列表
		
		while (((curpage - 1) * TaoBaoComm.TopMaxPageSize) <  total_results ) {
			log.info("##调用所有出售中商品列表 curpage =" + curpage);
			ItemRspResult irr = topItemBusiness.getUserItemResult(status, nick, shoppojo, sessionkey,"","", TaoBaoComm.TopMaxPageSize , curpage);
			if(irr != null){
				total_results = irr.getTotalResults()  ;
				if(total_results > 0 ){
					alllist_top.addAll(irr.getItemlist());	
				}	
			}
			curpage ++ ;
			log.info("##调用===================================" + curpage);
			ThreadSleep(1000);
		}
		
		total_results = 1 ;
		curpage =1 ; 
		status = 2 ;	//所有库存中商品列表		
		while (((curpage - 1) * TaoBaoComm.TopMaxPageSize) <  total_results ) {
			log.info("##调用所有库存中商品列表 curpage =" + curpage);
			ItemRspResult irr = topItemBusiness.getUserItemResult(status, nick, shoppojo, sessionkey,"","", TaoBaoComm.TopMaxPageSize , curpage);
			if(irr != null){
				total_results = irr.getTotalResults()  ;
				if(total_results > 0 ){
					alllist_top.addAll(irr.getItemlist());	
				}	
			}
			curpage ++ ;
			log.info("##调用===================================" + curpage);
			ThreadSleep(1000);
		}
		
		//终于得到了淘宝店铺中的宝贝NumIid列表 alllist_top
		log.error("alllist_top_db size=" + alllist_top.size());
		
		//得到DB中的宝贝 的 NumIid列表
		List<ItemPojo> alllist_db = itemService.getNumIidList( shoppojo.getShopid() );
		log.error("dballlist_db size=" + alllist_db.size());
		
		List<ItemPojo> newItemList = listUtil.AsubB(alllist_top, alllist_db);				//新增DB中不存在的宝贝
		log.error("newItemList size=" + newItemList.size());
		
		//新增的宝贝列表
		List<ItemPojo> newlist = new ArrayList<ItemPojo>();
		
		Iterator it = newItemList.iterator();
		while(it.hasNext()){
			ItemPojo itempojo = (ItemPojo)it.next();
			itempojo = topItemDetailBusiness.getDetailItem(nick, shoppojo , sessionkey, itempojo.getNum_iid() ) ;
			if(itempojo != null ){
				newlist.add(itempojo) ;
			}
		}
		//itemService.insertItem(itempojo);
		
		if(newlist != null && newlist.size()>0){
			//采用批量方案 200条数据用了4秒
			long start = System.currentTimeMillis() ; 
			itemService.insertBatchItemData(newlist) ; 
			long end = System.currentTimeMillis() ;
			long wastetime = end - start;
			System.out.println(nick + " 采用批量插入数据,size=" + newlist.size() + "用时 " + wastetime);
		}
		
		
		List<ItemPojo> deletedItemList = listUtil.AsubB(alllist_db , alllist_top );			//删除已经不存在的宝贝
		log.error("deletedItemList size=" + deletedItemList.size());
		
		long startDelete = System.currentTimeMillis() ; 
		itemService.deleteBatchItemData( deletedItemList ) ; 
		long endDelete = System.currentTimeMillis() ;
		long wastetimeDelete = endDelete - startDelete;
		System.out.println(nick + " 采用批量删除数据,size=" + deletedItemList.size() + "用时 " + wastetimeDelete);
		
//		Iterator it2 = deletedItemList.iterator();
//		while(it2.hasNext()){
//			ItemPojo itempojo = (ItemPojo)it2.next();
//			itemService.deleteItem(itempojo.getNum_iid());
//			log.info("删除已经不存在的宝贝 itempojo.numiid =" + itempojo.getNum_iid() ) ;
//		}
		
		
		//重新得到数据库中的宝贝列表
		List<ItemPojo> reallist_db = itemService.getItemList( shoppojo.getShopid() );
		log.error("reallist_db size=" + reallist_db.size());
		
		// 排序
		ItemComparator comp = new ItemComparator();
		Collections.sort(alllist_top , comp);
		Collections.sort(reallist_db , comp); 
		
		int len = reallist_db.size(); 
		ItemPojo itemtop = new ItemPojo();
		ItemPojo itemdb  = new ItemPojo();
		
		List<ItemPojo> modifylist_db = new ArrayList<ItemPojo>();
		
		//更新宝贝的标题
		for(int i=0; i<len; i++){			
			itemtop = (ItemPojo)alllist_top.get(i);
			itemdb =  (ItemPojo)reallist_db.get(i);
			if(itemtop.getNum_iid() == itemdb.getNum_iid() ){		// 如果是同一个Num_iid的宝贝							
				if( !itemtop.getTitle().equalsIgnoreCase(itemdb.getTitle()) || !itemtop.getPic_url().equalsIgnoreCase(itemdb.getPic_url() )  ){
					ItemPojo modifyitem = reallist_db.get(i) ;
					int uflag = 0 ; 
					if( !itemtop.getTitle().equalsIgnoreCase(itemdb.getTitle())   ){
						modifyitem.setTitle( itemtop.getTitle());
						uflag = 1 ;
					}
					if( !itemtop.getPic_url().equalsIgnoreCase(itemdb.getPic_url()) ){
						modifyitem.setPic_url( itemtop.getPic_url());	
						uflag = 1 ;
					}
					if(uflag == 1 ){
						modifylist_db.add(modifyitem) ;
					}
				}
			}
		}
		//批量更新
		if(modifylist_db != null && modifylist_db.size()>0){
			long start = System.currentTimeMillis() ; 
			itemService.updateBatchItemData(modifylist_db) ; 
			long end = System.currentTimeMillis() ;
			long wastetime = end - start;
			System.out.println(nick + " 采用批量更新数据,size=" + modifylist_db.size() + "用时 " + wastetime);
		}
		
	}
	
	
	/***
	 * 线程休息毫秒数
	 * @param MilliSeconds
	 */
	public void ThreadSleep(int MilliSeconds){
		try {
			Thread.sleep(MilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * 验证TOP回调地址的签名是否合法。要求所有参数均为已URL反编码的。 
     *  
     * @param topParams TOP私有参数（未经BASE64解密） 
     * @param topSession TOP私有会话码 
     * @param topSign TOP回调签名 
     * @param appKey 应用公钥 
     * @param appSecret 应用密钥 
     * @return 验证成功返回true，否则返回false 
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     */ 
    public static boolean verifyTopResponse(String topParams, String topSession, String topSign,String appKey, String appSecret) throws NoSuchAlgorithmException, IOException { 
            StringBuilder result = new StringBuilder(); 
            MessageDigest md5 = MessageDigest.getInstance("MD5"); 
            result.append(appKey).append(topParams).append(topSession).append(appSecret); 
            byte[] bytes = md5.digest(result.toString().getBytes("UTF-8")); 
            BASE64Encoder encoder = new BASE64Encoder(); 
            return encoder.encode(bytes).equals(topSign); 
    }
	

}
