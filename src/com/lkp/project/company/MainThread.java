/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.company;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
	public static Map<Proxy,Integer> proxyRequestTimes = new HashMap<Proxy,Integer>();
	public static Map<Proxy,Long> proxyLastRequestTime = new HashMap<Proxy,Long>();
	
	private static final LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>(10);
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, taskQueue,
               new ThreadPoolExecutor.CallerRunsPolicy());
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
			json.put("keywords", "科技"); 
			json.put("pageNo",i);
			json.put("param", "15084947675");
			json.put("grs", i+"");
			json.put("grt", "1000");
			json.put("rc", "*,100");
			json.put("pvn", 430000);
			urlqueue.add(json.toString());
		} 
		
		while(true){
			System.out.println("begin get proxy");
			Proxy proxy  = ProxyUtil.getProxy();
			proxyRequestTimes.put(proxy, 0);  
			proxyLastRequestTime.put(proxy,System.currentTimeMillis());
			if(proxy != null){
				executor.execute(new RequestThread(proxy)); 
			}else{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
	 	new Thread(new ValidateProxyThread()).start();
		TaskQueue urlqueue = new TaskQueue();
		MainThread main = new MainThread(urlqueue, null);
		new Thread(main).start();
	}
}
