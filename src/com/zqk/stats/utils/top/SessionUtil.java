package com.zqk.stats.utils.top;

import org.springframework.stereotype.Service;


@Service
public class SessionUtil {

	public String SessionKey  = null;		//SesssionnKey 最开始设置一个值，在它传入解析时候才不会出错。
	

	
	/*
	 * 得到sessionKey
	 */
	public String getSessionKey(String querystr){
		String[] params = querystr.split("&");
		String sessionKey = null;
		for (String string : params) {
			if(string.startsWith("top_session")){
				sessionKey = string.split("=")[1];
				break;
			}
		}
		return sessionKey;
	}
	
	/*
	 * 得到top_parameters
	 */
	public String getTop_parameters(String querystr){
		String[] params = querystr.split("&");
		String top_parameters = null;
		for (String string : params) {
			if(string.startsWith("top_parameters")){
				top_parameters = string.split("=")[1];
				break;
			}
		}
		return top_parameters;
	}
	/*
	 * 得到top_parameters
	 */
	public String getTop_sign(String querystr){
		String[] params = querystr.split("&");
		String top_sign = null;
		for (String string : params) {
			if(string.startsWith("top_sign")){
				top_sign = string.split("=")[1];
				break;
			}
		}
		return top_sign;
	}

	
	/*
	 * 得到Timestamp
	 */
	public String getTimestamp(String querystr){
		String[] params = querystr.split("&");
		String timestamp = null;
		for (String string : params) {
			if(string.startsWith("timestamp")){
				timestamp = string.split("=")[1];
				break;
			}
		}
		return timestamp;
	}
	
	/*
	 * 得到leaseId
	 */
	public String getLeaseId(String querystr){
		String[] params = querystr.split("&");
		String leaseId = null;
		for (String string : params) {
			if(string.startsWith("leaseId")){
				leaseId = string.split("=")[1];
				break;
			}
		}
		return leaseId;
	}	
	/*
	 * 得到 sign
	 */
	public String getSign(String querystr){
		String[] params = querystr.split("&");
		String sign = null;
		for (String string : params) {
			if(string.startsWith("sign")){
				sign = string.split("=")[1];
				break;
			}
		}
		return sign;
	}

	/*
	 * 得到versionNo
	 */
	public String getVersionNo(String querystr){
		String[] params = querystr.split("&");
		String versionNo = null;
		for (String string : params) {
			if(string.startsWith("versionNo")){
				versionNo = string.split("=")[1];
				break;
			}
		}
		return versionNo;
	}

	
	public String getFrom(String querystr){
		String[] params = querystr.split("&");
		String sessionKey = null;
		for (String string : params) {
			if(string.startsWith("from")){
				sessionKey = string.split("=")[1];
				break;
			}
		}
		return sessionKey;
	}
	
	public String getSessionKey() {
		return SessionKey;
	}

	public void setSessionKey(String sessionKey) {
		SessionKey = sessionKey;
	}


	
	
}
