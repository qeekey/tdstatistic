package com.zqk.stats.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseController {
	
	public HttpSession session;
	
	public HttpServletRequest req;
	public HttpServletResponse resp;
	
	/**
	 * 页面转向
	 * @param request
	 * @param response
	 * @param toUrl
	 * @throws Exception
	 */
    public void forward(HttpServletRequest request, HttpServletResponse response, String toUrl){
       try{
    	   request.getRequestDispatcher(toUrl).forward(request, response);
       }catch(Exception e){
    	   e.printStackTrace();
       }
    }

    
    public static String encoder(String str){
    	String s = "";
    	try{
    	    if(str!=null || str.trim().length()>0) 
    	 	s = java.net.URLEncoder.encode(str,"UTF-8");
    	}catch(Exception e){} 
    	return s;
    }
    
    
    /*********************************************************/
	/**关于Json 一系列Function
	 * 
	*/
	public void outJsonString(String str) {
		getResponse().setContentType("text/javascript;charset=UTF-8");
		outString(str);
	}

	public void outJson(Object obj) {
		outJsonString(JSONObject.fromObject(obj).toString());
	}

	public void outJsonArray(Object array) {
		outJsonString(JSONArray.fromObject(array).toString());
	}

	public void outString(String str) {
		try {
			PrintWriter out = getResponse().getWriter();
			System.out.println("PrintWriter ------ outString=" + str );
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outXMLString(String xmlStr) {
		getResponse().setContentType("application/xml;charset=UTF-8");
		outString(xmlStr);
	} 
	
	public HttpServletResponse getResponse() {   
		  return resp;   
	} 
}
