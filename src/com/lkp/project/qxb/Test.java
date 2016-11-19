/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.qxb;

import java.net.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.lkp.common.net.HttpProxy;

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

	public static String cookie = "aliyungf_tc=AQAAAMDQ70pDrwMAFd73cZaoxSamm0k7; sid=s%3AZHtO-JBmhlLPKzpL5BRFhqPP3yBuHz1O.sYWqR5RRoByOKd4XftYu6WSAwMABv9YY4lJ08%2FjiOa0; Hm_lvt_52d64b8d3f6d42a2e416d59635df3f71=1477181011,1477224484,1477271636; Hm_lpvt_52d64b8d3f6d42a2e416d59635df3f71=1477287250; _zg=%7B%22uuid%22%3A%20%22157eed9742e1c-0151dbee94324-65111375-15f900-157eed9742f24e%22%2C%22sid%22%3A%201477286200.097%2C%22updated%22%3A%201477287252.362%2C%22info%22%3A%201477181011000%2C%22cuid%22%3A%20%22f5d121c7-4c09-4fd0-9c06-9af03b7ba2b7%22%7D; responseTimeline=3001";
	public static void main(String[] args) {
		
		String url = "http://www.qixin.com/search?key=430103000074318&type=enterprise&source=&isGlobal=Y";
		Map<String,String> headParams = new HashMap<String,String>();
		headParams.put("Host","www.qixin.com");
	 	headParams.put("Proxy-Connection","keep-alive");
	 	headParams.put("Cache-Control","no-cache");
	 	//	 headParams.put("Connection","keep-alive");
	 	headParams.put("Upgrade-Insecure-Requests", "1");
	 	headParams.put("Pragma","no-cache");
	 	headParams.put("Upgrade-Insecure-Requests","1");
	 headParams.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	// headParams.put("Accept-Encoding","gzip, deflate, sdch");  
	 headParams.put("Accept-Language","zh-CN,zh;q=0.8");
		headParams.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		 
	//	 headParams.put("Referer","http://www.qixin.com/search?key=%E7%A7%91%E6%9C%89%E9%99%90&type=enterprise&method=&page=5");
		 
		 
	//	headParams.put("Cookie", Test.cookie);
		//ysudu.com
	 
		Proxy proxy  = HttpProxy.getProxy();
		try{
			for(int i=1;i<=1;i++){ 
				String  body = HttpProxy.getHttpRequestContentByGet(url,null , headParams,false);
				System.out.println("body = " +body);
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
