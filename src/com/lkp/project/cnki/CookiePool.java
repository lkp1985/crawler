/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.cnki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月11日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class CookiePool {
	public  Map<String,String> userpwd;
	private  List<String> cookielist;
	public void init(){
		cookielist = new ArrayList<String>();
		userpwd = new HashMap<String,String>();
		
		userpwd.put("280740960@qq.com", "pengsir2016");
		userpwd.put("15084947675@163.com", "pengsir2016");
		userpwd.put("pengsir2015@163.com", "pengsir2016");
		userpwd.put("pengsir20151@163.com", "pengsir2016");
		userpwd.put("xuweilkp@163.com", "pengsir2016");
		
		for(String username : userpwd.keySet()){
			SinaLogonDog dog = new SinaLogonDog();
			System.out.println(username+";"+userpwd+" begin login");
			String cookie = dog.logonValidateAndGetCookie(username, userpwd.get(username));
			if(cookie!=null){
				cookielist.add(cookie);
			}
		}
	}
	
	public void setCookie(){
		
	}
	public   String getCookie(){
		Random r = new Random();
		int i = r.nextInt(cookielist.size());
		return cookielist.get(i);
	}
	public static void main(String[] args) {
		CookiePool pool = new CookiePool();
		pool.init();
		for(int i=0;i<10;i++){
			System.out.println(i+"; "+pool.getCookie());
		}
	}
}
