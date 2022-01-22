package com.zqk.stats.pojo;

import java.util.Date;


public class NotifyPojo {
	long notifyid ; 
	long userId ;
	String nick ;
	long leaseId ;
	
	Date validateDate ;			//生效日期 
	Date invalidateDate ;		//失效日期
	
	double factMoney;			//实收金额
	long subscType ;			//订购类型：1，新订；2，续订；3，升级；4，退订；
	int versionNo;
	
	long oldVersionNo ;
	long status ;
	
	String sign ;
	Date gmtCreateDate;		//订购发生时间 

	String tadgetCode ;
	
	
	Date startDate ;
	Date endDate ;
	
	//分页参数
	int startNum ;
	int pageNum ;
	//
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getNotifyid() {
		return notifyid;
	}
	public void setNotifyid(long notifyid) {
		this.notifyid = notifyid;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public long getLeaseId() {
		return leaseId;
	}
	public void setLeaseId(long leaseId) {
		this.leaseId = leaseId;
	}
	public Date getValidateDate() {
		return validateDate;
	}
	public void setValidateDate(Date validateDate) {
		this.validateDate = validateDate;
	}
	public Date getInvalidateDate() {
		return invalidateDate;
	}
	public void setInvalidateDate(Date invalidateDate) {
		this.invalidateDate = invalidateDate;
	}
	public double getFactMoney() {
		return factMoney;
	}
	public void setFactMoney(double factMoney) {
		this.factMoney = factMoney;
	}
	public long getSubscType() {
		return subscType;
	}
	public void setSubscType(long subscType) {
		this.subscType = subscType;
	}
	public int getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
	public long getOldVersionNo() {
		return oldVersionNo;
	}
	public void setOldVersionNo(long oldVersionNo) {
		this.oldVersionNo = oldVersionNo;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Date getGmtCreateDate() {
		return gmtCreateDate;
	}
	public void setGmtCreateDate(Date gmtCreateDate) {
		this.gmtCreateDate = gmtCreateDate;
	}
	public String getTadgetCode() {
		return tadgetCode;
	}
	public void setTadgetCode(String tadgetCode) {
		this.tadgetCode = tadgetCode;
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
	
	
	

}
