package com.zqk;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.zqk.stats.comm.ConfigUtil;
import com.zqk.stats.comm.TaoBaoComm;
import com.zqk.stats.utils.ip.IPSeeker;



public class Log4jInitServlet extends HttpServlet {

    
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {		
	}

	public void init() throws ServletException {
//		System.setProperty("webappRoot", getServletContext().getRealPath("/"));
//
		String webappHome = getServletContext().getRealPath("");
		System.setProperty("webappHome", webappHome);	
		
        String prefix =  getServletContext().getRealPath("/") ;
        TaoBaoComm.RootPath = prefix ;
        
        //从配置文件中初始化一些固定值
        ConfigUtil configUtil = new ConfigUtil(prefix);
        configUtil.init();   
        
        //初始化IP查找器
		TaoBaoComm.iPSeeker = new IPSeeker("QQWry.Dat", TaoBaoComm.RootPath + "ipdata/" );   
		System.out.println("##############################");
		System.out.println("####Log4jInitServlet init#####");
		System.out.println("##############################");
	}
}