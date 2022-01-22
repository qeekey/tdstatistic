package com.zqk.stats.utils;

public class StringUtil {

	/***
	 * 店铺的ID进行加密
	 * @param shopid
	 * @return
	 */
	public String encodeShopId(Long shopid){
		String source = shopid + "" ;
		int len = source.length() ;
		int s[] = new int[len] ;
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<len;i++){
			s[i] = Integer.parseInt( source.substring(i, i+1) );
			switch(s[i]){
				case 0:
					buf.append("a");
					break;
				case 1:
					buf.append("v");
					break;
				case 2:
					buf.append("4");
					break;
				case 3:
					buf.append("5");
					break;
				case 4:
					buf.append("h");
					break;
				case 5:
					buf.append("3");
					break;
				case 6:
					buf.append("6");
					break;
				case 7:
					buf.append("7");
					break;
				case 8:
					buf.append("0");
					break;
				case 9:
					buf.append("9");
					break;
			}
		}	
		return buf.toString();
	}
	
	
	/***
	 * 店铺的ID进行解密
	 * @param shopid
	 * @return
	 */
	public String decodeShopId(String shopid){
		int len = shopid.length() ;
		//String s[] = new String s[len] ;
		StringBuffer buf = new StringBuffer();
		
		for(int i=0;i<len;i++){
			String s = shopid.substring(i, i+1);
			if(s.equalsIgnoreCase("a")){
				buf.append("0");
			}else if(s.equalsIgnoreCase("v")){
				buf.append("1");
			}else if(s.equalsIgnoreCase("4")){
				buf.append("2");
			}else if(s.equalsIgnoreCase("5")){
				buf.append("3");
			}else if(s.equalsIgnoreCase("h")){
				buf.append("4");
			}else if(s.equalsIgnoreCase("3")){
				buf.append("5");
			}else if(s.equalsIgnoreCase("6")){
				buf.append("6");
			}else if(s.equalsIgnoreCase("7")){
				buf.append("7");
			}else if(s.equalsIgnoreCase("0")){
				buf.append("8");
			}else if(s.equalsIgnoreCase("9")){
				buf.append("9");
			}
		}	
		return buf.toString();
	}
	
	/***
	 * 验证解密后的shopid的合法性
	 * @return
	 */
	public boolean verfyShopId(String shopid){
		boolean ret = true;
		if(shopid.length() < 8 ){
			ret = false;
		}
		return ret;
	}
	
	
	public static void main(String[] args) {
		StringUtil su = new StringUtil();
		long shopid = 123456789 ;
		String en = su.encodeShopId(shopid);
		System.out.println(en);
		
		String source = su.decodeShopId("ds343435");
		System.out.println(source);
	}
}
