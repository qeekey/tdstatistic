package com.zqk.stats.utils.ip;

public class TestIP {


	public static void main(String[] args) {
		IPSeeker ip = new IPSeeker("QQWry.Dat", "C://");   
		// ����IP 58.20.43.13   
		System.out.println(ip.getIPLocation("202.108.25.67").getCountry());
		System.out.println(ip.getIPLocation("202.108.25.67").getArea());
		
	}

}
