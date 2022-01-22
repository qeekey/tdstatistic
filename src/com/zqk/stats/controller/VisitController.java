package com.zqk.stats.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Encoder;

import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.pojo.ItemPojo;
import com.zqk.stats.pojo.ShopPojo;
import com.zqk.stats.pojo.Visit;
import com.zqk.stats.service.ItemService;
import com.zqk.stats.service.VisitService;
import com.zqk.stats.utils.DateUtil;
import com.zqk.stats.utils.StringUtil;


@Controller
public class VisitController extends BaseController {
	
	private static final Log log = LogFactory.getLog(VisitController.class);
	
	@Resource private ItemService itemService;
	@Resource private VisitService visitService;
	
	private DateUtil du = new DateUtil();
	private static StringUtil su = new StringUtil();

	private static Image  tudun007Statistic   ; 
	public static BufferedImage bufferedImage ;
	
	/***
	 * 插入访问数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/stats.do")	
	public void doStats(HttpServletRequest request,HttpServletResponse response) throws IOException{
		req = request;
		resp = response;
		
		Visit visit = null ; 
		long shopid = 0 ;  
		
		String shopidStr = request.getParameter("sid");
		
		System.out.println("=============shopidStr=" + shopidStr);
		
		if(shopidStr != null){
			shopidStr = su.decodeShopId(shopidStr);
			boolean v = su.verfyShopId(shopidStr);
			if(v){
				shopid = Long.parseLong(shopidStr) ; 
			}
		}
		System.out.println("=============shopid=" + shopid);
		
		//http://item.taobao.com/item.htm?id=5782948371&ad_id=&am_id=&cm_id=&pm_id=
		//http://item.taobao.com/item.htm?id=5809183441
			
		if(shopid > 0){
			// 原始调用URL
			String enterUrl =request.getHeader("Referer");
			String ip = "" ;
			if(enterUrl != null ){
				int posself = enterUrl.indexOf("tdstats");		//不能是通过此软件的链接出去的
				
				int pos = enterUrl.indexOf("?id=");
				
				long num_iid = 0 ;
				
				if(pos != -1 && posself==-1 ){
					String querystr = enterUrl.substring(pos+4) ;
					int posend = querystr.indexOf("&");				
					if(posend != -1){
						querystr = querystr.substring(0, posend);
					}
					num_iid = Long.parseLong(querystr.trim());
				}
				
				System.out.println("=============num_iid=" + num_iid);
				
				if(num_iid > 0 ){
					visit = new Visit();
					//查找相应的记录信息
					ip = request.getRemoteAddr() ; 
					ItemPojo item = itemService.getItem(num_iid);
					String title = 	 "商品ID " + num_iid  ;
					String pic_url = "images/itemdefault.jpg" ;
					
					if(item != null ){
						title = item.getTitle() ; 
						pic_url = item.getPic_url() ; 
					}
					String country = TaoBaoComm.iPSeeker.getIPLocation(ip).getCountry();
					
					//生成新的visit record
					visit.setNum_iid(num_iid);	
					visit.setShopid(shopid);
					visit.setIp(ip);
					visit.setCountry(country);
					
					visit.setRefer(0) ;
					Date enterTime = du.getTimeStamp(); 
					visit.setEntertime(enterTime) ;
					visit.setOuttime(enterTime);
					
					visit.setTitle(title);
					visit.setPic_url(pic_url);
					
					int timegroup = getTimeGroup(enterTime); 
					visit.setTimegroup(timegroup);

					visitService.insertVisit(visit);
					
					TaoBaoComm.StatsMap.put(shopid,1);
					
				}
			}else{
				log.error("num_iid<0");
			}
		}
	
		
		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			log.info("#####ErrorCode=151. 没有得到response.getOutputStream()．输出图象肯定会出错.");
		}
		
		outputImgFile(os, TaoBaoComm.RootPath +  TaoBaoComm.ImageTudunCateFile);		//输出图盾的分类标
		
	}
	
	/**
	 * 根据当前时间，分时段
	 * @param enterTime
	 * @return
	 */
	public int getTimeGroup(Date enterTime){
		int ret = 1 ; 
		
		//2011-02-28 14:07:30
		String datestr =du.DateToStr(enterTime);
		
		ret = Integer.parseInt( datestr.substring(11,13) );
		
		return ret ;		
	}
	
	
	/***
	 * 输出正在访问的Json结果
	 */                
	@RequestMapping("/nowVisitItemJson.do")
	public void doNowVisitItemJson(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		
		session = request.getSession();
		
		long shopid =110 ;
		
		if(session.getAttribute("SHOPID") != null ){
			shopid = (Long)session.getAttribute("SHOPID") ;
		}				
		
		int newflag = ( (Integer)TaoBaoComm.StatsMap.get(shopid)).intValue() ;
		
		if(newflag == 1 ){
			//输出Json字符了
			log.info("#############有新访问,输出Json字符了 " + System.currentTimeMillis() );

			Visit visitpara = new Visit();
			visitpara.setShopid(shopid);
			
			log.info("=====shopid="+ shopid) ;
			
			visitpara.setEntertime(du.getBeforeMinuteTime(new Date() , TaoBaoComm.OnlineMinute ));
			List<Visit> nowVisitList = visitService.nowVisitList(visitpara);
			
			if(session.getAttribute("SHOP") != null  ){
				log.info("#############session SHOP is not null ") ; 
			}else{
				log.info("#############session SHOP is null ") ;
			}
			
			if(session.getAttribute("SHOP") != null  ){
				ShopPojo shoppojo = (ShopPojo) session.getAttribute("SHOP") ;
				log.info("#############11111111") ; 
				int voiceflag = shoppojo.getVoiceflag() ; 
				if(voiceflag ==1 ){
					log.info("#############22222222") ; 
					//设置提示声音功能
					if(session.getAttribute("LatesVisit") == null ){
						log.info("#############333333") ; 
						if(nowVisitList.size() >0 ){
							session.setAttribute("LatesVisit", nowVisitList.get(0)) ;	
							
							Iterator it = nowVisitList.iterator() ;
							int i = 0 ;
							while(it.hasNext()){
								Visit vs = (Visit)it.next() ;
								String expireCookieDate = du.DateToStr( du.getAfterMinuteTime( vs.getEntertime() , TaoBaoComm.OnlineMinute ) );
								vs.setExpireCookieDate( expireCookieDate);
								nowVisitList.set(i, vs ) ;
								i++;
							}
							log.info("#############44444") ; 
						}else{
						}
					}else{
						log.info("#############55555555555") ; 
						if(nowVisitList.size() >0 ){
							log.info("#############66666666") ; 
//							log.info("session id =" + ( (Visit)session.getAttribute("LatesVisit") ).getId() ) ;
//							log.info(( (Visit)nowVisitList.get(0)).getId()  ) ;
//							log.info(( (Visit)nowVisitList.get(0)).getNum_iid() ) ;
							if( ( (Visit)session.getAttribute("LatesVisit") ).getId() != ( (Visit)nowVisitList.get(0)).getId()  ){
								log.info("#############7777777777777777") ; 
								session.setAttribute("LatesVisit", nowVisitList.get(0)) ;
								req.setAttribute("NewVisit", 1);
								log.info("有人正在访问产品");
								Visit v = (Visit)nowVisitList.get(0) ;
								v.setTimegroup(99); 	//用做新访问标志
								
								Iterator it = nowVisitList.iterator() ;
								int i = 0 ;
								while(it.hasNext()){
									Visit vs = (Visit)it.next() ;									
									vs.setTimegroup(99); 	//用做新访问标志									
									String expireCookieDate = du.DateToStr( du.getAfterMinuteTime( vs.getEntertime() , TaoBaoComm.OnlineMinute ) );
									vs.setExpireCookieDate( expireCookieDate);
									nowVisitList.set(i, vs ) ;
									i++ ; 
								}
							}else{
								log.info("#############88888888") ; 
							}
							log.info("#############9999999999999") ; 
						}else{
							log.info("999999999999999999999999999999999998888");
						}
					}
				}
			}
			
			//输出最新访问列表Json串
			outJsonArray(nowVisitList);
			
			TaoBaoComm.StatsMap.put(shopid,0);
			
		}else{
			log.info("#############无新访问,不输出Json字符了 " + System.currentTimeMillis() );
		}
		
	}
	
	
	
	/***
	 * 直接打开本地图片输出
	 * @param os
	 * @param imgfile
	 */
	public void outputImgFile(OutputStream os,String imgfile){
		try {			
			if(bufferedImage == null ){
				tudun007Statistic = ImageIO.read(new File(imgfile));
			    bufferedImage = new BufferedImage(tudun007Statistic.getWidth(null), tudun007Statistic.getHeight(null),BufferedImage.TYPE_INT_RGB);   
			    Graphics g = bufferedImage.createGraphics();   
			    g.drawImage(tudun007Statistic, 0, 0, null);   
			    g.dispose();   
			}else{
				log.info("直接输出 tudun007cate ");
			}
		    
			String format = "gif" ;
			/*输出图象响应处理*/		
			ServletOutputStream output = null ;
			try {
				output = resp.getOutputStream();
				resp.setContentType("image/" + format + "; charset=gb2312");
				ImageIO.write(bufferedImage, format , output);	
				bufferedImage.flush();			
				output.close();
			} catch (IOException e) {
				log.error("##outputImgWithTextWaterMark IOException.");
			} finally{
				output = null ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("##这里其实可以不用管它. outputImg Exception!") ;
		}
	}
	
	/***
	 * 删除过期的访问数据
	 */
	@RequestMapping("/visit/deleteExpiredVisitData.do")
	public void doDeleteExpiredVisitData(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		Visit visitpara = new Visit();
		Date entertime = du.getBeforeDate(new Date() , 30 ) ;
		visitpara.setEntertime(entertime);
		visitService.deleteExpiredVisitData(visitpara);
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
