/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.getproxyip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月20日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class ProxyIPHolder {
	private static List<String> iplist = new ArrayList<String>();

	/**
	 * @return the iplist
	 */
	public static List<String> getIplist() {
		return iplist;
	}

	/**
	 * @param iplist the iplist to set
	 */
	public static void setIplist(List<String> _iplist) {
		iplist = _iplist;
	}
	
	public static String getProxyIP(){
		try{
			Random r = new Random();
			int i = r.nextInt(iplist.size());
			return iplist.get(i);
		}catch(Exception e){
			return null;
		}
		
	}
}
