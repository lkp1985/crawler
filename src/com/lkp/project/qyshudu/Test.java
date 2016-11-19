/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.qyshudu;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.lkp.common.net.HttpProxy;

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
			String url = "http://www.qysudu.com/qysuduApi/company/search";
			

		 
			Proxy proxy   = new Proxy(Proxy.Type.HTTP,   
		            new InetSocketAddress("147.75.208.22",27017 ));
			 
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
				json.put("keywords", "游戏");  
				
				Map<String,String> headParams = new HashMap<String,String>();
				headParams.put("Accept", "application/json"); // 设置接收数据的格式  
				headParams.put("Content-Type", "application/json"); // 设置发送数据的格式  
				//	headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			 
				//ysudu.com
				try{
 			String  body = HttpProxy.getHttpRequestContentByPost(url, null, json.toString(), headParams,false);
 			 System.out.println("body2="+body);
//					System.out.println("body="+body);
//					//{keywords: "公司", pageNo: 1, param: "15084947675"}
//					JSONObject bodyJson = JSONObject.fromObject(body);
					/*JSONArray list = bodyJson.getJSONArray("data");
					for(Object obj : list){
						System.out.println("obj="+obj);
					}*/
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
				System.out.println("page="+(i+1));
				//System.out.println("body = " +body);
			}
			
		
	
	}
	
	
	public static String  post(String url,String params){
		try{ 

			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new StringEntity(params));
			/*nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("_", query));
			httppost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));*/
			HttpClient commonClient = new DefaultHttpClient();
			HttpResponse response = commonClient.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			System.out.println("get: " + code);
			InputStream is = response.getEntity().getContent();
			byte[] resbody = new byte[10240];
			is.read(resbody);
		
			return new String(resbody);
		 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ""	;
	}
}
