package com.zqk.stats.pojo;

import java.util.ArrayList;
import java.util.List;

public class ItemRspResult {

	private String ErrorCode ;
	private String msg ;
	
	private List<ItemPojo> itemlist = new ArrayList<ItemPojo>();
	private long totalResults ;
	
	
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<ItemPojo> getItemlist() {
		return itemlist;
	}
	public void setItemlist(List<ItemPojo> itemlist) {
		this.itemlist = itemlist;
	}
	public long getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}
	
}
