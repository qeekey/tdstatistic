package com.zqk.stats.pojo;


public class ItemHot {


	long num_iid ; 
	String title ; 
	long shopid ;
	String pic_url ;
	int  itempv ; 
	
	
	public int getItempv() {
		return itempv;
	}
	public void setItempv(int itempv) {
		this.itempv = itempv;
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
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String toString() {
		return "num_iid=" + num_iid + ",title=" + title +  ",shopid=" + shopid + ",pic_url=" + pic_url ;  
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		final ItemHot other = (ItemHot) obj;

		if ( this.getNum_iid() != other.getNum_iid() )
		{		
			return false;
		}
		
		return true;
	}
}
