package com.zqk.stats.comm;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zqk.stats.utils.ip.IPSeeker;


public class TaoBaoComm {
	private static final Log log = LogFactory.getLog(TaoBaoComm.class);
	
	public static String APP_KEY 			= "APP_KEY";
	public static String APP_SERCET 		= "APP_SERCET";
	
	public static String TAOBAO_URL       	= "http://gw.api.taobao.com/router/rest";			
	public static String GET_SESSION_URL 	= "http://container.open.taobao.com/container";			
	
	public static String ITEMLOGO 	= "_60x60.jpg";
	
	public static String   ImageTudunCateFile 		= "images/tds/TudunStats.jpg" ;
	
	public static  String  statscateUrl = "stats.do?sid=" ;
	public static  String  RootURL = "http://www.xxxxxx.com:8080"; 
	public static  String  StatsDomain = "www.xxxxxx.com:8080";
	
	public static  int     OnlineMinute = 3 ;	//设置用户在线时间为3分钟
	
	
	//新API的连接超时问题
	public static String format = "json";
	public static int connectTimeout = 0 ;
	public static int readTimeout    = 60000 ;
	
	
	public static int  V1_MaxItemCount = 20 ;
	public static int V2_MaxItemCount = 200 ;
	public static int V3_MaxItemCount = 5000 ;

	public static int TopMaxPageSize = 200;

	public  static  String  tuduncateName = "图盾访客提醒" ;
	
	public  static  int   PAGESIZE = 10;
	public  static  int   NotifyPAGESIZE = 50;

	public static String RootPath = "" ;						//应用根目录
	
	public static IPSeeker iPSeeker = null ; 

	
	public static String LogErrorMsg100 = "淘宝接口返回错误." ;
	public static String LogErrorMsg101 = "调用淘宝接口取宝贝详情返回超时错." ;
	
	
	public static boolean OutputDescDebug = true;		//输出宝贝内容的开关 
	
	public static boolean NotifySignFlag = true; 		//通知验证开关
	public static int	 NotifyDBVersion = 1; 			//通知入库的最低版本
	public static boolean NotifyJspFlag = true; 		//通知jsp输出开关
	
	public static ConcurrentMap StatsMap = new ConcurrentHashMap();

	//淘宝平台返回的固定字符串
	public final static String ERROR_RESPONSE = "error_response";
	
	public final static String SHOP_GET_RESPONSE = "shop_get_response";
	public final static String SHOP = "shop";
	
	
	
	public final static String ITEMS_INVENTORY_GET_RESPONSE = "items_inventory_get_response";
	public final static String ITEMS_ONSALE_GET_RESPONSE = "items_onsale_get_response";
	public final static String ITEMS_ALL_GET_RESPONSE = "items_all_get_response";
	
	
	public static int ITEMS_GET_RESPONSE_COUNT = 40;
	
	public final static String ITEMS_GET_RESPONSE = "items_get_response";		
	
	public final static String TOTAL_RESULTS = "total_results";
	public final static String ITEMS = "items";
	public final static String ITEM = "item";
	public final static String ITEM_GET_RESPONSE = "item_get_response";
	public final static String ITEM_UPDATE_RESPONSE = "item_update_response";
	
	public final static String APPSTORE_SUBSCRIBE_GET_RESPONSE = "appstore_subscribe_get_response";
	
	public final static String USER_SUBCRIBE = "user_subscribe";
	
	public final static String SHOP_DESC_TOO_SHORT = "SHOP_DESC_TOO_SHORT";
		
	
}
