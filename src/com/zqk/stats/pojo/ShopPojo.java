package com.zqk.stats.pojo;


public class ShopPojo {

	long shopid ;
	String nick;
	
	int autosynflag;		//是否自动同步数据
	int voiceflag ;			//提示声音
	
	
	public long getShopid() {
		return shopid;
	}
	public void setShopid(long shopid) {
		this.shopid = shopid;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public int getAutosynflag() {
		return autosynflag;
	}
	public void setAutosynflag(int autosynflag) {
		this.autosynflag = autosynflag;
	}
	public int getVoiceflag() {
		return voiceflag;
	}
	public void setVoiceflag(int voiceflag) {
		this.voiceflag = voiceflag;
	}
	
	
}
