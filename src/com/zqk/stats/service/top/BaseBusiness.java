package com.zqk.stats.service.top;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.zqk.stats.comm.TaoBaoComm;



public class BaseBusiness {
	private static final Log log = LogFactory.getLog(BaseBusiness.class);
	
	public int total_results ; //搜索到符合条件的结果总数 
	
	public static TaobaoClient  client ; 
	public static int bbtime = 1; 
	
	public BaseBusiness(){}
	
	/**
	 * 得到淘宝API Client 实例
	 * @return
	 */
	public static TaobaoClient  getClient(){		
		if(client == null){
			if(TaoBaoComm.TAOBAO_URL.length() >0 && TaoBaoComm.APP_KEY.length() >0 ){
				log.info("TaoBaoComm.TAOBAO_URL=" + TaoBaoComm.TAOBAO_URL);
				client = new DefaultTaobaoClient (TaoBaoComm.TAOBAO_URL, TaoBaoComm.APP_KEY, TaoBaoComm.APP_SERCET,TaoBaoComm.format, TaoBaoComm.connectTimeout, TaoBaoComm.readTimeout); 
				return client ;
			}else{
				log.info("TaoBaoComm.TAOBAO_URL=" + TaoBaoComm.TAOBAO_URL) ;
				log.info("TaoBaoComm.APP_KEY=" +  TaoBaoComm.APP_KEY ) ;				
				log.info("client 暂时不做初始化工作.");
				//client = new TaobaoJsonRestClient("http://gw.api.taobao.com/router/rest", TaoBaoComm.APP_KEY, TaoBaoComm.APP_SERCET);
			}				
		}else{
			return client;
		}
		return client;
	}
	
}
