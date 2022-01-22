package com.zqk.stats.comm;

import java.util.List;

public class PageUtil {
	
	public int curPage = 1; 		// 当前页
//	
	
	public static int tudunShowPage = 10 ;		//
	
//	public static int MaxSize  = 1000; 			// 图盾支持
	
	public static int TopPageSize = 40 ; 		//TOP平台返回的自定义记录数, 平台建议是40条
	public int pageSize = 40; 					//每页行数，这两个取一样的值
	
	public static int NavNumSize = 10; 			// 每页多少行
	
	
	public static int TopMaxPageSize = 200 ; 			//TOP平台返回的最大记录数
	
	public static int TopRemoveMaxPageSize = 100 ; 		//取消宝贝保护时，一页最大的记录数
	
	private int endSize; 		// 用于not in(select top endSize id)不在多少行内
	private int totalRow; 		// 共多少行
	private int totalPage; 		// 共多少页
	
	private String tips ; 		//记录提示信息
	private String ErrorCode ;	//记录错误码
	private String ErrorTips ;	//记录错误提示信息
	private String subErrorCode ;	//记录子错误码
	
	
	/**   
	 * 当前页的记录集   
	 */
	private List datas;

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		int temp = pageSize * (curPage - 1);
		this.setEndSize(temp);
		this.curPage = curPage;
	}

	public int getEndSize() {
		return endSize;
	}

	public void setEndSize(int endSize) {
		this.endSize = endSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		totalPage = totalRow / pageSize;
		if (totalRow % pageSize > 0)
			totalPage = totalPage + 1;
		this.totalRow = totalRow;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}
	
	
	/***
	 * 得到管理页面的 共有 120 条记录，当前第 1/10 页 导航说明　
	 * @return
	 */
	public String getPageNavTips(int pagesize) {
		pageSize = pagesize ;
		setTotalRow(totalRow) ;		
		StringBuffer str = new StringBuffer("");
		str.append("&nbsp;&nbsp;共有 "+ totalRow +" 条记录，当前第 " + curPage + "/"+ totalPage +" 页");
		return str.toString();	
	}
	
	
	
	public String getTudunPageNav(int pagesize) {
		pageSize = pagesize ;
		
		String str="";
		
		setTotalRow(totalRow) ;		
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("<div class=\"badoo\">");
		if(curPage > 1){
			buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + (curPage-1) + ";document.TudunPageNav.submit();return(false)\" >"  + "<span class=\"prev\"></span>" + "</a>");					
		}else{
			buf.append("<span class=\"prevno\"></span>");
		}
		
		if(totalPage <=tudunShowPage ){
			for(int i=1; i<=totalPage; i++){
				if(i == curPage){
					buf.append("<span class=\"current\">" + curPage + "</span>" ) ;
				}else{
					buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + i + ";document.TudunPageNav.submit();return(false)\" >"  + i + "</a>");
				}
			}
		}else{
			if( curPage >= (totalPage - tudunShowPage + 1 + 2 ) ) {
				if(curPage >2 ){
					buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + 1 + ";document.TudunPageNav.submit();return(false)\" >"  + 1 + "</a>");
					buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + 2 + ";document.TudunPageNav.submit();return(false)\" >"  + 2 + "</a>");
				
					buf.append("...");
				}
				//没有...了
				int nochage = totalPage - tudunShowPage + 1 + 2 ;
				for(int i=nochage; i<=totalPage; i++){
					if(i == curPage){
						buf.append("<span class=\"current\">" + curPage + "</span>" ) ;
					}else{
						buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + i + ";document.TudunPageNav.submit();return(false)\" >"  + i + "</a>");
					}  
				}
			}else{
				if(curPage >2 ){
					buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + 1 + ";document.TudunPageNav.submit();return(false)\" >"  + 1 + "</a>");
					buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + 2 + ";document.TudunPageNav.submit();return(false)\" >"  + 2 + "</a>");
					buf.append("...");					
					//有...
					for(int i=curPage; i<=(NavNumSize-4) + (curPage-1)  ; i++){
						if(i == curPage){
							buf.append("<span class=\"current\">" + curPage + "</span>" ) ;
						}else{
							buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + i + ";document.TudunPageNav.submit();return(false)\" >"  + i + "</a>");
						}
					}
				}else{
					//有...
					for(int i=curPage; i<=(NavNumSize-2) + (curPage-1)  ; i++){
						if(i == curPage){
							buf.append("<span class=\"current\">" + curPage + "</span>" ) ;
						}else{
							buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + i + ";document.TudunPageNav.submit();return(false)\" >"  + i + "</a>");
						}
					}
				}
				buf.append("...");
				buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + (totalPage-1) + ";document.TudunPageNav.submit();return(false)\" >"  + (totalPage-1) + "</a>");
				buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + totalPage + ";document.TudunPageNav.submit();return(false)\" >"  + totalPage + "</a>");
			}
		}
		
		if(curPage < totalPage){
			buf.append("<a href=\"getUserTopItem.action#\"" + " onclick=\"document.TudunPageNav.curPage.value=" + (curPage+1) + ";document.TudunPageNav.submit();return(false)\" >"  + "<span class=\"next\"></span>" + "</a>");					
		}else{
			buf.append("<span class=\"nextno\"></span>");
		}
		
		buf.append("</div>");
		
		str = buf.toString();
		
		return str;
		
	}
	

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorTips() {
		return ErrorTips;
	}

	public void setErrorTips(String errorTips) {
		ErrorTips = errorTips;
	}

	public String getSubErrorCode() {
		return subErrorCode;
	}

	public void setSubErrorCode(String subErrorCode) {
		this.subErrorCode = subErrorCode;
	}
		
}
