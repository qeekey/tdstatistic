package com.zqk.stats.service.top;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.request.AppstoreSubscribeGetRequest;
import com.taobao.api.response.AppstoreSubscribeGetResponse;

@Service
public class TopSubscribeService extends BaseBusiness {	
	private static final Log log = LogFactory.getLog(TopItemBusiness.class);
	
	
	/***
	 * 用户API
	 * taobao.appstore.subscribe.get 查询appstore应用订购关系
	 * @param nick
	 * @return
	 */
	public String getUserSubscribeResult(String nick,String sessionkey ){
		String SubscribeResult ="";
		try {   
			AppstoreSubscribeGetRequest req = new AppstoreSubscribeGetRequest();
			req.setNick(nick);
			AppstoreSubscribeGetResponse  rsp = super.getClient().execute(req, sessionkey);
			SubscribeResult = rsp.getBody() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("SubscribeResult=" + SubscribeResult);
		return SubscribeResult;
	}
	

}
