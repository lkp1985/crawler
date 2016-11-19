/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.jianzhu;

import java.net.Proxy;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.getproxyip.ProxyIPHolder;
import com.lkp.proxy.HttpProxy;

/**
 * <p>class function description.<p>
 *
 * create  2016年3月20日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class MainThread implements Runnable {

	TaskQueue urlqueue;
	List<SolrInputDocument> docList;
	public MainThread(TaskQueue _urlqueue,List<SolrInputDocument> _docList){
		urlqueue = _urlqueue;
		docList = _docList;
	}
	@Override
	public void run() {
		int index = 0;
		 while(!urlqueue.isEmpty()){
			 String url = urlqueue.poll();
			 String cookie =  "safedog-flow-item=EA2F4A469F3AA66CAC292311BFA6BADC; ASP.NET_SessionId=cd1xk4x1fcbwzjaurmcjliev";
			 String proxyIP = ProxyIPHolder.getProxyIP();
			 String ip = proxyIP.split("\\|")[0];
			 String port = proxyIP.split("\\|")[1];
			 Proxy proxy = HttpProxy.getProxy(ip,port);
				String ss = com.lkp.proxy.HttpProxy.getResponseByGet(url, cookie,proxy);
				while(ss.length()<100){
					System.out.println("begin return get "+url);
					 proxyIP = ProxyIPHolder.getProxyIP();
					 ip = proxyIP.split("\\|")[0];
					 port = proxyIP.split("\\|")[1];
					   proxy = HttpProxy.getProxy(ip,port);
					 ss = com.lkp.proxy.HttpProxy.getResponseByGet(url, cookie,proxy);
				}
				Document doc = Jsoup.parse(ss);
				
			//	System.out.println("ss="+doc);
				Elements eles = doc.getElementsByTag("a"); 
				for(Element ele : eles){
					SolrInputDocument solrDoc = new SolrInputDocument();
					String el  = ele.attr("href");
					int index1 = el.indexOf("?");
					String id = el.substring(index1+8, el.length()-2);
					 
					 String name = ele.text(); 
					 solrDoc.addField("id",id); 
					 solrDoc.addField("name", name);
					 docList.add(solrDoc);
					 index++;
				}
		 } 
	}
}
