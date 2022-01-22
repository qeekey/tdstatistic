/**
 * 
 */
package com.zqk.stats.controller.top;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.controller.BaseController;
import com.zqk.stats.pojo.ItemRspResult;
import com.zqk.stats.pojo.ShopPojo;
import com.zqk.stats.service.top.TopItemBusiness;



/**
 * @author zqk
 */

@Controller
public class TopItemController  extends BaseController {

	@Resource private TopItemBusiness topItemBusiness;	
	
	/***
	 * Ajax 自动调用以便延长淘宝授权Session的功能
	 */
	@RequestMapping("/autoExtendSessionTopItem.do")
	public void doAutoExtendSessionTopItem(HttpServletRequest request,HttpServletResponse response) throws IOException{
		req = request;
		resp = response;
		session = request.getSession();
		
		System.out.println("##########自动调用以便延长淘宝授权 by " +  (String)session.getAttribute("NICK") + " start ...... ");
		
		String nick = (String)session.getAttribute("NICK");
		String sessionkey = (String)session.getAttribute("SESSIONKEY");
		ShopPojo shoppojo = (ShopPojo) session.getAttribute("SHOP");
		
		int status = 1; 
		String title = "自动调用以便延长淘宝授权";
		String SellerCid = "" ;
		
		ItemRspResult irr = topItemBusiness.getUserItemResult(status, nick, shoppojo, sessionkey, title ,SellerCid, 1 , 1 );
		
		//System.out.println("##itemQueryResult=" + itemQueryResult );		
		System.out.println("##########自动调用以便延长淘宝授权 by " +  (String)session.getAttribute("NICK") + " end ...... ");
	}
	
}
