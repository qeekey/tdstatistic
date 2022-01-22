/**
 * 
 */
package com.zqk.stats.utils.top;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author zqk
 *
 */
@Service
public class ParametersUtil {

	public String nick  = null;				//在平台进入时得到的用户Nick
	
	/*
	 * 得到当前用户昵称
	 */
	public String getCurrentNick(String top_parameters){		
		String nick = null ;
		
		
		Map<String, String> map = TopUtil.convertBase64StringtoMap(top_parameters);
		Iterator keyValuePairs = map.entrySet().iterator();
		for (int i = 0; i < map.size(); i++) {
			Map.Entry entry = (Map.Entry) keyValuePairs.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key.equals("visitor_nick")) {				 
				nick = (String) value;
			}
		} 

		
		/*
		
		String[] params = querystr.split("&");
		String top_parameters = null;
		for (String string : params) {
			if(string.startsWith("top_parameters")){
				top_parameters = string.split("=")[1];
				break;
			}
		}
		
		Map<String, String> map = TopUtil.convertBase64StringtoMap(top_parameters);
		Iterator keyValuePairs = map.entrySet().iterator();
		for (int i = 0; i < map.size(); i++) {
			Map.Entry entry = (Map.Entry) keyValuePairs.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key.equals("visitor_nick")) {
				nick = (String) value;
			}
		} 
		
		*/
		
		return nick;
	}

	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
}
