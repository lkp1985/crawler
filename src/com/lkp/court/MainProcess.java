package com.lkp.court;

public class MainProcess {
	public static void main(String[] args) {
		int year = 2000;
		for(int i=0;i<=16;i++){
			ParseThread p1 = new ParseThread((year+i)+"-01-01",(year+i+1)+"-01-01", "error_"+(year+i));
			new Thread(p1).start(); 
		}
		 
	}
}
