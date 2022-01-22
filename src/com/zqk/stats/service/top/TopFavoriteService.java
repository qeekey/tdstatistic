/**
 * 
 */
package com.zqk.stats.service.top;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.FavoriteAddRequest;
import com.taobao.api.response.FavoriteAddResponse;

/**
 * @author zqk
 *
 */

@Service
public class TopFavoriteService extends BaseBusiness {	
	private static final Log log = LogFactory.getLog(TopFavoriteService.class);
	
	private int total_result = 0;	//返回满足条件的总记录数
	
	
	public boolean AddFavoritet(long item_numid, String collect_type,boolean shared ,String sessionkey){
		boolean t = true; 

		try {
			FavoriteAddRequest req=new FavoriteAddRequest();
			req.setItemNumid(item_numid);
			req.setCollectType(collect_type);
			req.setShared(shared);
			
			FavoriteAddResponse rsp = getClient().execute(req, sessionkey);
			
			System.out.println(rsp.getBody() );
			System.out.println(rsp.getMsg() );
			System.out.println(rsp.getErrorCode() );
			
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return t ;
	}
	
		
	
}
