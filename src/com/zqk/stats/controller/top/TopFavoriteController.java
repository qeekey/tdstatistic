package com.zqk.stats.controller.top;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Encoder;

import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.controller.BaseController;
import com.zqk.stats.service.top.TopFavoriteService;
import com.zqk.stats.utils.top.ParametersUtil;
import com.zqk.stats.utils.top.SessionUtil;


@Controller
public class TopFavoriteController extends BaseController {
	private static final Log log = LogFactory.getLog(TopFavoriteController.class);
	
	@Resource TopFavoriteService topFavoriteService; 
	
	
	@Resource private SessionUtil sessionUtil ;
	@Resource private ParametersUtil parametersUtil ;
	
	
	/***
	 * 输出历史访问List结果
	 */
	@RequestMapping("/favorite/addfavorite.do")
	public void doAddFavorite(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		
		
		//取得数据更新Item
		session = request.getSession();
	
		String querystr = "" ; 
		
		if( session.getAttribute("querystr") != null ){
			querystr = (String)session.getAttribute("querystr") ;	
		}
		
		
		log.info("###### querystr=" + querystr);
		boolean top_sign = false;
		
		
		
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
					
					session.setAttribute("NICK",nick) ;
				}
			}
		}
		
		
		System.out.println("favorite/addfavorite.do .................start.....");
		
//		String sessionkey = (String)session.getAttribute("SESSIONKEY");
//		System.out.println("sessionkey=" + sessionkey);
//		
//		boolean shared = false;
//		long num_iid = 8513278440L;
//		String collect_type = "ITEMDD" ;
//		topFavoriteService.AddFavoritet(num_iid, collect_type, shared, sessionkey);
		
		System.out.println("favorite/addfavorite.do .................end.....");
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
