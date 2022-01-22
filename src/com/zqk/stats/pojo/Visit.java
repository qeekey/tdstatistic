package com.zqk.stats.pojo;

import java.util.Date;

public class Visit {


	long id ; 
	long num_iid ; 
	String title ; 
	long shopid ;
	String pic_url ; 
	
	String ip  ;
	String country  ; 
	
	int refer ; 	
	Date entertime ; 
	Date outtime ; 
	int  timegroup;
	
	//para 参数
	String curday;
	String entertimeOption;
	
	//分页参数
	int startNum ;
	int pageNum ;
	//
	
	
	String expireCookieDate ;		//Cookie失效时间
	
	
	public Visit(){}
	
	public Visit(long num_iid , String pic_url , String title , String country  ){
		this.num_iid = num_iid;
		this.pic_url = pic_url ;
		this.title   = title ; 
		this.country = country; 
	}
	
	public long getNum_iid() {
		return num_iid;
	}
	public void setNum_iid(long num_iid) {
		this.num_iid = num_iid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getShopid() {
		return shopid;
	}
	public void setShopid(long shopid) {
		this.shopid = shopid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getRefer() {
		return refer;
	}
	public void setRefer(int refer) {
		this.refer = refer;
	}
	public Date getEntertime() {
		return entertime;
	}
	public void setEntertime(Date entertime) {
		this.entertime = entertime;
	}
	public Date getOuttime() {
		return outtime;
	}
	public void setOuttime(Date outtime) {
		this.outtime = outtime;
	}
	public int getTimegroup() {
		return timegroup;
	}
	public void setTimegroup(int timegroup) {
		this.timegroup = timegroup;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	} 
	
	public String toString() {
		return "num_iid=" + num_iid + ",title=" + title +  ",shopid=" + shopid + ",pic_url=" + pic_url + ",ip=" + ip + ",refer=" + refer + ",entertime=" + entertime + ",timegroup=" + timegroup ;  
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurday() {
		return curday;
	}

	public void setCurday(String curday) {
		this.curday = curday;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}



	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getEntertimeOption() {
		return entertimeOption;
	}

	public void setEntertimeOption(String entertimeOption) {
		this.entertimeOption = entertimeOption;
	}

	public String getExpireCookieDate() {
		return expireCookieDate;
	}

	public void setExpireCookieDate(String expireCookieDate) {
		this.expireCookieDate = expireCookieDate;
	}




}
