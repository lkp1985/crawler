package com.lkp.project.court;

public class MainProcess {
	public static void main(String[] args) {
		int year = 2013;
		for(int i=0;i<2;i++){
			//com.lkp.proxy.HttpProxy.setProxy("172.16.106.90", "8119");
			ParseThread p1 = new ParseThread((year+i)+"-01-01",(year+1+i)+"-12-31", "error_"+(year+i));
			new Thread(p1).start(); 
		}
	}
}
