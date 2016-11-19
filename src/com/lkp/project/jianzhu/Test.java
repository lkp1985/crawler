/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.jianzhu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lkp.getproxyip.ProxyIpGet;
import com.lkp.project.court.MainProcess;
import com.lkp.solr.SolrUtils;

/**
 * <p>
 * class function description.
 * <p>
 *
 * create 2016年3月10日<br>
 * 
 * @author lkp<br>
 * @version 1.0
 * @since 1.0
 */
public class Test {

	public static void main(String[] args) {
		
		ProxyIpGet ipGet = new ProxyIpGet();
		new Thread(ipGet).start();
		
		String url = "http://219.142.101.185/jianguanfabuweb/handler/GetCompanyData.ashx?method=GetCorpData&corpname=&certid=&endtime=&cert=-1&name=-1&PageSize=";
		List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
		new Thread(new CommitThread(docList)).start();
		
		
		TaskQueue urlqueue = new TaskQueue(); 
		for(int i=1;i<=10;i++){//27511;i++){
			String newurl = url+"&PageIndex="+i; 
			urlqueue.add(newurl);
		}
		for(int i=0;i<10;i++){
			new Thread(new MainThread(urlqueue,docList)).start();
		}
		 
		 
		

	}

	public static void write(String file, String content) {

		try {
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 

	public static String getValueWithNull(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}
}
