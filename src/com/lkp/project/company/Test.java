/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.company;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import com.lkp.common.net.HttpProxy;
import com.lkp.solr.SolrUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
		get();
	}
	
	
	public static void get(){


		//	test();
			// q=tomcat+中国
			String url = "http://58.56.21.242:88/qysuduApi/company/search";
			

		 
			Proxy proxy   = new Proxy(Proxy.Type.HTTP,   
		            new InetSocketAddress("172.16.106.90",8119 ));
			 
			//new InetSocketAddress("134.168.55.226",8080 ));
		//	String body = getHttpRequestContentByGet(url, null, params);
			String formparams = "keywords=湖南傻蛋&pageNo=1&param=15084947675";
			int sum = 0;
			for(int i=0;i<10000;i++){
				sum ++;
				if(sum>=56){
					sum = 0;
					try {
						Thread.sleep(50000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				JSONObject json = new JSONObject();
				json.put("keywords", "科技"); 
				json.put("pageNo",i);
				json.put("param", "15084947675");
				json.put("grs", "2");
				json.put("grt", "1000");
				json.put("rc", "*,100");
				json.put("pvn", 430000);
				Map<String,String> headParams = new HashMap<String,String>();
				
				
				
				headParams.put("Accept-Encoding","gzip, deflate");
				headParams.put("Accept-Language","zh-CN,zh;q=0.8");
				headParams.put("Connection","keep-alive");
				
				
				headParams.put("Content-Type", "application/json");
				headParams.put("Content-Length",json.toString().getBytes().length+"");
				headParams.put("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
				headParams.put("Host","58.56.21.242:88");
				headParams.put("Origin","http://qysudu.com");
				headParams.put("Referer", "http://qysudu.com/companys/lists?type=name&name=%25E5%2585%25AC%25E5%258F%25B8&mark=1473694656474");
				headParams.put("Accept", "application/json, text/javascript, */*; q=0.01");
		
				
				headParams.put("access_token","06e2c1a0769d42adadf1188a5c45d988");
				headParams.put("app_key","f139b0d83ae843dfaaff750198b48770");
				headParams.put("userInfo","355c07c4d28e4af88d9b41abc14a3e41");
				headParams.put("host","58.56.21.242:88");
				//	headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			 
				//ysudu.com
				try{
					String body = HttpProxy.getHttpRequestContentByPost(url, null, json.toString(), headParams);
					System.out.println("body="+body);
					//{keywords: "公司", pageNo: 1, param: "15084947675"}
					JSONObject bodyJson = JSONObject.fromObject(body);
					/*JSONArray list = bodyJson.getJSONArray("data");
					for(Object obj : list){
						System.out.println("obj="+obj);
					}*/
				}catch(Exception e){
					
				}
				
				
				System.out.println("page="+(i+1));
				//System.out.println("body = " +body);
			}
			
		
	
	}
}
