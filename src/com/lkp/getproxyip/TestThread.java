/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.getproxyip;

import java.net.Proxy;

import com.lkp.proxy.HttpProxy;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月18日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class TestThread implements Runnable{

	private String ip ;
	private String port; 
	public TestThread(String testIP,String testPort){
		this.ip = testIP;
		this.port = testPort; 
	}
	@Override
	public void run() {
		Proxy proxy = HttpProxy.getProxy(ip, port);
		String status = HttpProxy.getResponseByGet("http://www.sina.com.cn",null,proxy);
		if(status.length()>100){
			System.out.println("ip="+ip+";port="+port);
			ProxyIPHolder.getIplist().add(ip+"|"+port);
		}
		
	}

}
