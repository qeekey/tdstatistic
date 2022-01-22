package com.zqk.stats.utils;


import java.io.Serializable;

public class Page implements Serializable{
	private int currentPageNum;	// 当前页数
	private int recordCount;	// 总的记录数
	private int pageSize;		// 每页显示的记录数
	private int pageCount;		// 总页数
	private int startNum;		// 起始记录数
	private int endNum;			// 结束记录数	
	
	public Page() {}
	/**
	 * 算出要显示的页数
	 * currentPageNum 当前页数
	 * pageSize 每页显示的记录数
	 * recordCount 总的纪录数
	 */
	public Page(int currentPageNum, int pageSize) {
		this.currentPageNum = currentPageNum;
		this.pageSize = pageSize;
		if(currentPageNum<=0) 
			this.currentPageNum = 1;
	}

	/**
	 * 总页数
	 */
	private void PageCount() {
		if(this.recordCount % this.pageSize > 0) 
			this.pageCount = this.recordCount/ this.pageSize + 1;
		else
			this.pageCount =  this.recordCount / this.pageSize;
	}

	/**
	 * 取总页数
	 */
	public int getPageCount() {
		return this.pageCount;
	}

	/**
	 * 取当前页数
	 */
	public int getCurrentPageNum() {
		return this.currentPageNum;
	}

	/**
	 * 共多少条记录
	 */
	public int getRecordCount() {
		return this.recordCount;
	}
	public void setRecordCount(int recordCount){
		this.recordCount = recordCount;
		PageCount();	//计算总页数
		if(currentPageNum>this.pageCount) 
			this.currentPageNum = this.pageCount;
	}
	
	/**
	 * 取起始数
	 * @return
	 */
	public int getStartNum() {
		if(this.currentPageNum<=0) 
			this.startNum = 1;
		else 
			this.startNum = (this.currentPageNum-1) * this.pageSize + 1;
		return this.startNum;
	}

	/**
	 * 取结束数
	 */
	public int getEndNum() {
		this.endNum = this.currentPageNum * this.pageSize;
		if(this.endNum >= this.recordCount)
			this.endNum = this.recordCount;
		return this.endNum;
	}


}
