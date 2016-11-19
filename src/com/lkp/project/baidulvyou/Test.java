/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.baidulvyou;

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

		Map<String, String> params = new HashMap<String, String>();

		params.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		// params.put("Accept-Encoding","gzip, deflate, sdch");
		params.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		// params.put("Host","s.weibo.com");
		// params.put("Referer",
		// "http://www.court.gov.cn/zgcpwsw/List/List?sorttype=1&conditions=searchWord+4+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E8%B5%94%E5%81%BF%E6%A1%88%E4%BB%B6");
		params.put("Cache-Control", "max-age=0");
		params.put("Connection", "keep-alive");
		// conn.setInstanceFollowRedirects(false);
		// params.put("If-Modified-Since","Wed, 30 Dec 2015 09:01:37 GMT");
		// params.put("If-None-Match","");
		// params.put("If-Modified-Since","3f5dfb0e042d11");
		params.put("Upgrade-Insecure-Requests", "1");
		params.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.82 Safari/537.36");	//	test();
			// q=tomcat+中国
			String url = "https://lvyou.baidu.com/jingdian/qiuyeyuan";
			

		 
		 
			 
			String  body = HttpProxy.getHttpsRequestContentByGet(url, null, params,false,"utf-8");
			 System.out.println("body2="+body);
		
	
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
