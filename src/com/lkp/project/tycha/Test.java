/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.project.tycha;

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
	public static void main(String[] args) {	
		get();
	}
	
	
	public static void get(){

		String url = "http://www.tianyancha.com/search/ab.json?&pn=2";
	 
		Map<String,String> headParams = new HashMap<String,String>();
		
		
		
		headParams.put("Content-Encoding","gzip");
		headParams.put("Accept-Language","zh-CN,zh;q=0.8");
		headParams.put("Connection","keep-alive");
		
		
		headParams.put("Content-Type", "application/json");
		headParams.put("CheckError","check");
		headParams.put("Tyc-From", "normal");
		headParams.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
		headParams.put("Host","tianyancha.com"); 
		headParams.put("Referer", "http://tianyancha.com/search/p2?key=%E7%99%BE%E5%BA%A6&searchType=company");
		headParams.put("Accept", "application/json, text/javascript, */*; q=0.01");
		headParams.put("Cookie", "175.13.252.57; RTYCID=1d04b7e2aa684870b83ac67c7cb54fe8; TYCID=f7560a5fd44f457ca00bf5d8043f5c7c; _pk_ref.1.e431=%5B%22%22%2C%22%22%2C1473905747%2C%22http%3A%2F%2Ftianyancha.com%2Fsearch%2Fp2%3Fkey%3D%E7%99%BE%E5%BA%A6%26searchType%3Dcompany%22%5D; Hm_lvt_e92c8d65d92d534b0fc290df538b4758=1473862175,1473905853; Hm_lpvt_e92c8d65d92d534b0fc290df538b4758=1473905853; _pk_id.1.e431=683d73af280fe9a3.1473862215.2.1473905963.1473905747.; _pk_ses.1.e431=*; token=ed2e8099d7584a5389b6e9246db884be; _utm=d36df6ccb6eb4236af2929e273a8cd99");
		
	//	headParams.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	 
		//ysudu.com
		try{
			String body = HttpProxy.getHttpRequestContentByGet(url, null, null, headParams);
			System.out.println("body="+body);
			//{keywords: "公司", pageNo: 1, param: "15084947675"}
		 
			/*JSONArray list = bodyJson.getJSONArray("data");
			for(Object obj : list){
				System.out.println("obj="+obj);
			}*/
		}catch(Exception e){
			
		}
	 
		
	
	}
}
