package com.zqk.stats.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {



	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;   
		 /*设置参数的目的只是当会话不存在时,返回null还是新的session的区别,     
		    参数为true时，返回的是null;     
		    没有参数或是false则返回新的session   
		 */  
		HttpSession session = request.getSession(true);


		String requestURI = request.getRequestURI();  
		String targetURI = requestURI.substring(requestURI.lastIndexOf("/"));   
		
		boolean ignore = false;
		String  igUrl[]={"stats.do","deleteExpiredVisitData.do","notify.do"} ;
		int len = igUrl.length  ; 
		for(int i=0;i<len;i++){
			int pos = targetURI.indexOf(igUrl[i]) ; 
			if(pos != -1 ){
				ignore = true;
				break;
			}
		}
		
		
		if( ignore ){	//如果是不必验证的 URL
			
		}else if (!targetURI.equals("/SessionError.jsp")) { 		//如果访问的不是首页
			// 如果session为空，session中没有user对象
			if (session == null || session.getAttribute("querystr") == null) {
				// 重定向的首页，让用户登录
				response.sendRedirect(request.getContextPath() + "/SessionError.jsp");
				return;
			}
		}
		
		filterChain.doFilter(request, response);   
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("-------AuthFilter.init()---------"); 
	}
	
	public void destroy() {	
	}

}
