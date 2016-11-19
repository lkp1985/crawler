/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.getproxyip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.proxy.HttpProxy;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月18日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class ProxyIpGet implements Runnable{
	public static void main(String[] args) {
		String rsp = HttpProxy.getHttpRequestContentByGetWithoutProxy("http://www.xicidaili.com");
		
		Document jsoup = Jsoup.parse(rsp);
		Element ele = jsoup.getElementById("ip_list");
		Elements eles = ele.getElementsByTag("tr"); 
		for(Element el : eles){
			Elements tds = el.getElementsByTag("td");
			if(tds.size()==7){
				String ip = tds.get(1).text();
				String port = tds.get(2).text();
				new Thread(new TestThread(ip, port)).start();
			}
		}
	}
	
	public void run(){
		while(true){
			String rsp = HttpProxy.getHttpRequestContentByGetWithoutProxy("http://www.xicidaili.com");
			Document jsoup = Jsoup.parse(rsp);
			Element ele = jsoup.getElementById("ip_list");
			Elements eles = ele.getElementsByTag("tr"); 
			for(Element el : eles){
				Elements tds = el.getElementsByTag("td");
				if(tds.size()==8){
					String ip = tds.get(1).text();
					String port = tds.get(2).text();
					new Thread(new TestThread(ip, port)).start();
				}
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
