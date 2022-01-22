package com.zqk.stats.controller.top;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.controller.BaseController;
import com.zqk.stats.controller.HistoryVisitController;
import com.zqk.stats.pojo.NotifyPojo;
import com.zqk.stats.service.NotifyService;
import com.zqk.stats.utils.DateUtil;


@Controller
public class NotifyController extends BaseController {
	
	private static final Log log = LogFactory.getLog(HistoryVisitController.class);
	
	@Resource  private NotifyService notifyService;
	
	
	@RequestMapping("/notify.do")
	public String doHistoryVisitList(HttpServletRequest request,HttpServletResponse response) throws IOException{		
		req = request;
		resp = response;
		
		long userId  = ServletRequestUtils.getLongParameter(request, "userId" , -1 );		
		String nick = ServletRequestUtils.getStringParameter(request, "nick" , "tudun007" );
		
		long leaseId  = ServletRequestUtils.getLongParameter(request, "leaseId" , -1 );
		
		String validateDate =  ServletRequestUtils.getStringParameter(request, "validateDate" , "tudun007" );
		
		String invalidateDate =  ServletRequestUtils.getStringParameter(request, "invalidateDate" , "tudun007" );		
		
		double factMoney  =  ServletRequestUtils.getDoubleParameter(request, "factMoney" , -1 );
		log.error("============money=" + factMoney ) ;
		
		long subscType  = ServletRequestUtils.getLongParameter(request, "subscType" , -1 );		
		 
		int versionNo  = ServletRequestUtils.getIntParameter(request, "versionNo" , -1 );
		
		long oldVersionNo  = ServletRequestUtils.getLongParameter(request, "oldVersionNo" , -1);
		
		long status  = ServletRequestUtils.getLongParameter(request, "status" , -1 );
		
		String sign =  ServletRequestUtils.getStringParameter(request, "sign" , "tudun007" );
		
		String gmtCreateDate =  ServletRequestUtils.getStringParameter(request, "gmtCreateDate" , "tudun007" );
		
		String tadgetCode =  ServletRequestUtils.getStringParameter(request, "tadgetCode" , "tudun007" );
		
		//将所有参数param1，param2，……，paramN，按照参数名称字母顺序从小到大排列 
		//顺序为factMoney gmtCreateDate invalidateDate leaseId nick oldVersionNo sign status  subscType  tadgetCode  userId  validateDate  versionNo
		
		//拼串 
		//将排好序的参数拼接成串，param1 + value1 + param2 + value2 + … +paramN + valueN 
		
		StringBuffer buf = new StringBuffer();
		if(factMoney > 0.0 ){
			buf.append("factMoney");
			buf.append(factMoney);
		}
		if(gmtCreateDate.indexOf("tudun007") == -1 ){
			buf.append("gmtCreateDate");	
			buf.append(gmtCreateDate);
		}
		if(invalidateDate.indexOf("tudun007") == -1 ){
			buf.append("invalidateDate");	
			buf.append(invalidateDate);
		}
		if(leaseId > 0 ){
			buf.append("leaseId");
			buf.append(leaseId);
		}
		if(nick.indexOf("tudun007") == -1 ){
			buf.append("nick");
			buf.append(nick);
		}
		if(oldVersionNo > 0 ){
			buf.append("oldVersionNo");
			buf.append(oldVersionNo);
		}
		if(sign.indexOf("tudun007") == -1 ){
			buf.append("sign");
			buf.append(sign);
		}
		if(status > 0 ){
			buf.append("status");
			buf.append(status);
		}
		if(subscType > 0 ){
			buf.append("subscType");
			buf.append(subscType);
		}
		if(tadgetCode.indexOf("tudun007") == -1 ){
			buf.append("tadgetCode");
			buf.append(tadgetCode);
		}
		if(userId > 0 ){
			buf.append("userId");
			buf.append(userId);
		}
		if(validateDate.indexOf("tudun007") == -1 ){
			buf.append("validateDate");
			buf.append(validateDate);
		}
		if(versionNo > 0 ){
			buf.append("versionNo");
			buf.append(versionNo);
		}
		String paraunion = buf.toString();
		
		
		//首尾加密钥 
		//在上述字符串首部和尾部都加上应用的密钥secretCode，即secretCode + param1 + value1 + param2 + value2 + … +paramN + valueN + secretCode 		
		String paraunionAppKey = TaoBaoComm.APP_KEY + paraunion + TaoBaoComm.APP_KEY ;
		log.error("################notifyaction paraunionAppKey=" + paraunionAppKey );
			
		boolean passsign = this.verifySign(paraunionAppKey, sign);
		System.out.println("@@@@@@@notify passsign=" + passsign );
		
		if( passsign || TaoBaoComm.NotifySignFlag ){			//如果通过签名认证
			log.error("Notify URL通过验证 . passign=" + passsign + ", TaoBaoComm.NotifySignFlag =" + TaoBaoComm.NotifySignFlag );
			
			if(factMoney > 0.0){
				log.error("#####收费版的入库-->versionNo=" + versionNo ) ;
				NotifyPojo notifyPojo = new NotifyPojo();
				notifyPojo.setFactMoney(factMoney);
				
				if(gmtCreateDate.indexOf("tudun007") == -1 ){
					notifyPojo.setGmtCreateDate(DateUtil.StrToDate(gmtCreateDate) );	
				}else{
					notifyPojo.setGmtCreateDate(new Date());
				}
				if(invalidateDate.indexOf("tudun007") == -1 ){
					notifyPojo.setInvalidateDate(DateUtil.StrToDate(invalidateDate) );	
				}else{
					notifyPojo.setInvalidateDate(new Date());
				}
				
				notifyPojo.setLeaseId(leaseId);
				notifyPojo.setNick(nick);
				notifyPojo.setOldVersionNo(oldVersionNo);
				notifyPojo.setSign(sign);
				notifyPojo.setStatus(status);
				notifyPojo.setSubscType(subscType);
				notifyPojo.setTadgetCode(tadgetCode);
				notifyPojo.setUserId(userId);
				
				if(validateDate.indexOf("tudun007") == -1 ){
					notifyPojo.setValidateDate(DateUtil.StrToDate(validateDate) );	
				}else{
					notifyPojo.setValidateDate(new Date());
				}
				
				notifyPojo.setVersionNo(versionNo);	
				
				notifyService.insertNotify(notifyPojo);
			}else{//免费试用版的不入库
				log.error("#####不收费的 version=" + TaoBaoComm.NotifyDBVersion + " 版本的不入库") ;
			}
		}else{
			log.error("Notify URL不能通过验证");
		}
		
		
		if(TaoBaoComm.NotifyJspFlag){		//输出结果到 jsp ，可以以便在jsp中执行验证等操作
			//输出到notify.jsp		
			if(factMoney > -1 ){
				request.setAttribute("factMoney", factMoney);
			}
			if(gmtCreateDate.indexOf("tudun007") == -1 ){
				request.setAttribute("gmtCreateDate", gmtCreateDate);
			}
			if(invalidateDate.indexOf("tudun007") == -1 ){
				request.setAttribute("invalidateDate", invalidateDate);
			}
			if(leaseId > 0 ){
				request.setAttribute("leaseId", leaseId);
			}
			if(nick.indexOf("tudun007") == -1 ){
				request.setAttribute("nick", nick);
			}
			if(oldVersionNo > 0 ){
				request.setAttribute("oldVersionNo", oldVersionNo);
			}
			if(sign.indexOf("tudun007") == -1 ){
				request.setAttribute("sign", sign);
			}
			if(status > 0 ){
				request.setAttribute("status", status);
			}
			if(subscType > 0 ){
				request.setAttribute("subscType", subscType);
			}
			if(tadgetCode.indexOf("tudun007") == -1 ){
				request.setAttribute("tadgetCode", tadgetCode);
			}
			if(userId > 0 ){
				request.setAttribute("userId", userId);
			}
			if(validateDate.indexOf("tudun007") == -1 ){
				request.setAttribute("validateDate", validateDate);
			}
			if(versionNo > 0 ){
				request.setAttribute("versionNo", versionNo);
			}
			//end notify.jsp
		}
		
		return "/notify" ;
	}

	
	/***
	 * 验证签名
	 * @param paraunionAppKey
	 * @param sign
	 * @return
	 * @throws  
	 */
	public boolean verifySign(String paraunionAppKey , String sign )  {
		MessageDigest md5;
		byte[] bytes;
		try {
			md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(paraunionAppKey.toString().getBytes("UTF-8"));
			int i;
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < bytes.length; offset++) {
				i = bytes[offset];
				if(i<0) i+= 256;
				if(i<16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			System.out.println("##notifysign: "   + sign);
			System.out.println("##notifyresult: " + buf.toString());//32位的加密
			return buf.toString().toUpperCase().equals(sign);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
