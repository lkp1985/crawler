/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.tycha;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import com.lkp.proxyutils.ProxyUtil;

import net.sf.json.JSONObject;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月20日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class MainThread implements Runnable {
	public static Map<String,Integer> proxyRequestTimes = new HashMap<String,Integer>();
	public static Map<String,Long> proxyLastRequestTime = new HashMap<String,Long>();
	public static TaskQueue urlqueue;
	List<SolrInputDocument> docList;
	public MainThread(TaskQueue _urlqueue,List<SolrInputDocument> _docList){
		urlqueue = _urlqueue;
		docList = _docList;
	}
	@Override
	public void run() {
		
		
		//{name: "公司", pageNo: 2, param: "15084947675", grs: "2", grt: "1000", rc: "*,100", pvn: 430000}
		//{name: "公司", pageNo: 3, param: "15084947675", grs: "2", grt: "1000", rc: "*,100", pvn: 430000}
		for(int i=1;i<134000;i++){
			JSONObject json = new JSONObject();
			json.put("name", "公司"); 
			json.put("pageNo", i);
			json.put("param", "13467694635");
			json.put("grs", "2");
			json.put("grt", "1000");
			json.put("rc", "*,100");
			json.put("pvn", 430000);
			urlqueue.add(json.toString());
		} 
		proxyRequestTimes.put("null", 0);
		proxyRequestTimes.put("proxy", 0);
		proxyLastRequestTime.put("null",System.currentTimeMillis());
		proxyLastRequestTime.put("proxy",System.currentTimeMillis());
		Proxy proxy   = new Proxy(Proxy.Type.HTTP,   
	            new InetSocketAddress("172.16.106.90",8119 ));
		for(int i=0;i<0;i++){
			new Thread(new RequestThread(proxy)).start();
		}
		for(int i=0;i<1;i++){
			new Thread(new RequestThread(null)).start();
		}
		/*while(true){
			System.out.println("begin get proxy");
			Proxy proxy  = ProxyUtil.getProxy();
			if(proxy != null){
				new Thread(new RequestThread(proxy)).start();
				break;
			}else{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
	}
	public static void main(String[] args) {
	//	new Thread(new ValidateProxyThread()).start();
		TaskQueue urlqueue = new TaskQueue();
		MainThread main = new MainThread(urlqueue, null);
		new Thread(main).start();
	}
}
