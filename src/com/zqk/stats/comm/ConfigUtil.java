package com.zqk.stats.comm;

import java.io.File;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ConfigUtil {
	private static final Log log = LogFactory.getLog(ConfigUtil.class);
	private String webroot;
	
	public ConfigUtil(String path){
		webroot = path ;
	}
	
	public void init(){
		log.info("ConfigUtil init start ");
		
		String TopUrlfilename = "" ; 
		if(webroot.lastIndexOf("\\") != -1){
			TopUrlfilename = getPath() + "config\\stats.xml" ;	
		}else{
			TopUrlfilename = getPath() + "config/stats.xml" ;
		}
		
		TaoBaoComm.RootPath = webroot ;

		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build( new File(TopUrlfilename) );
			Element root = document.getRootElement();
			
			TaoBaoComm.TAOBAO_URL = root.getChildText("TAOBAO_URL") ;
			TaoBaoComm.GET_SESSION_URL = root.getChildText("GET_SESSION_URL");
			
			TaoBaoComm.APP_KEY = root.getChildText("APP_KEY");
			TaoBaoComm.APP_SERCET = root.getChildText("APP_SERCET");
			
			TaoBaoComm.StatsDomain = root.getChildText("StatsDomain");
			
			log.info("TaoBaoComm.TAOBAO_URL=" + TaoBaoComm.TAOBAO_URL);
			log.info("TaoBaoComm.GET_SESSION_URL=" + TaoBaoComm.GET_SESSION_URL);
			log.info("TaoBaoComm.APP_KEY=" + TaoBaoComm.APP_KEY);
			log.info("TaoBaoComm.APP_SERCET=" + TaoBaoComm.APP_SERCET);
			log.info("TaoBaoComm.StatsDomain=" + TaoBaoComm.StatsDomain);
			
			TaoBaoComm.RootURL = "http://" + TaoBaoComm.StatsDomain  ;
			log.info("TaoBaoComm.RootURL =" + TaoBaoComm.RootURL );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("ConfigUtil init end ");
	}
	

	public String getPath(){
		return webroot ;
	} 
	
}
